<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>工人问题反馈管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${workerProblem.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>楼盘地址</label>
						<input type="text" id="address" name="address" style="width:160px;"/>
					</li>
					<li>
						<label>反馈日期从</label>
						<input type="text" id="dateFrom" name="dateFrom"
							class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${workerProblem.dateFrom}' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo"
							class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${workerProblem.dateTo}' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>工种分类</label>
						<house:dict id="workType12" dictCode="" sql=" select Code,Code+Descr Descr from tWorkType12 where expired ='F' order by Code " 
							sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
					</li>
					<li>
						<label>工人</label>
						<input type="text" id="workerCode" name="workerCode" style="width:160px;"/>
					</li>
					<li>
						<label>工程大区</label>
						<house:dict id="prjRegionCode" dictCode="" sql="select Code,Code+' '+Descr Descr from tPrjRegion where expired ='F' " 
							sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
					</li>
					<li>
						<label>项目经理</label>
						<input type="text" id="projectMan" name="projectMan" style="width:160px;"/>
					</li>
					<li>
						<label>状态</label>
						<house:xtdmMulit id="status" dictCode="PRJPROMSTATUS" selectedValue="1,2"  unShowValue="0,3"></house:xtdmMulit>                     
					</li>
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" sql="select Code,desc1 desc1  from dbo.tDepartment2 where  deptype='3' and Expired='F' order By dispSeq  " 
						sqlLableKey="desc1" sqlValueKey="code"   ></house:DictMulitSelect>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show"
							onclick="hideExpired(this)" ${workerProblem.expired!='T' ?'checked':''}/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button id="clear" type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="WORKERPROBLEM_VIEW">
					<button type="button" class="btn btn-system" id="viewBtn" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKERPROBLEM_CONFIRM">
					<button type="button" class="btn btn-system" id="confirmBtn" onclick="confirm()">
						<span>确认</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKERPROBLEM_REPLENISH">
					<button type="button" class="btn btn-system" id="replenish" onclick="replenish()">
						<span>确认并补货</span>
					</button>
				</house:authorize>
				<house:authorize authCode="GRWTFK_DEAL">
					<button type="button" class="btn btn-system" id="dealBtn" onclick="deal()">
						<span>处理</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system " onclick="doExcel()"><span>导出excel</span></button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>

<script type="text/javascript">
var postData = $("#page_form").jsonForm();

$(function(){
	/* worker搜索 */
	$("#workerCode").openComponent_worker();
	$("#projectMan").openComponent_employee();	

	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,/* 列重排 */
		url: "${ctx}/admin/workerProblem/goJqGrid",
		postData:{status:"1,2"},
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name: "no", index: "no", width: 70, label: "反馈编号", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 165, label: "楼盘地址", sortable: true, align: "left"},
			{name : "deptdescr",index : "deptdescr",width : 100,label:"二级部门",sortable : true,align : "left"},
			{name: "projectmandescr", index: "projectmandescr", width: 75, label: "项目经理", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 80, label: "反馈单状态", sortable: true, align: "left"},
			{name: "worktype12descr", index: "worktype12descr", width: 50, label: "工种", sortable: true, align: "left"},
			{name: "namechi", index: "namechi", width: 70, label: "工人名字", sortable: true, align: "left"},
			{name: "date", index: "date", width: 142, label: "反馈日期", sortable: true, align: "left", formatter: formatTime},
			{name: "note", index: "note", width: 100, label: "类型", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 165, label: "反馈内容", sortable: true, align: "left"},
			{name: "stopday", index: "stopday", width: 100, label: "预计停工天数", sortable: true, align: "right"},
			{name: "picnum", index: "picnum", width: 100, label: "照片数量", sortable: true, align: "left", hidden:true},
			{name: "confirmremark", index: "confirmremark", width: 130, label: "确认说明", sortable: true, align: "left"},
			{name: "confirmdescr", index: "confirmdescr", width: 80, label: "确认人", sortable: true, align: "left"},
			{name: "confirmdate", index: "confirmdate", width: 80, label: "确认时间", sortable: true, align: "left",formatter: formatDate},
			{name: "dealremark", index: "dealremark", width: 130, label: "处理说明", sortable: true, align: "left"},
			{name: "dealdescr", index: "dealdescr", width: 80, label: "处理人", sortable: true, align: "left"},
			{name: "dealdate", index: "dealdate", width: 80, label: "处理时间", sortable: true, align: "left",formatter: formatDate},
			{name: "lastupdate", index: "lastupdate", width: 142, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
			{name: "status", index: "status", width: 80, label: "反馈单状态", sortable: true, align: "left",hidden:true},
			
		],
		ondblClickRow: function(){/* 双击触发事件 */
			view();
		}
	});
	
	/* 工种分类和工人联动 */
	$("#workType12").on("click",function(){
		workType = $("#workType12").val();
		if (workType!=""&&workType!=null) {
			$("#workerCode").openComponent_worker({condition:{workType12:workType}});
		} else {
			$("#workerCode").openComponent_worker();
		}
	});
	
	/* 清空操作后还原工人栏 */
	$("#clear").on("click",function(){
		$("#workerCode").openComponent_worker();
	});
	
});
/* 查看 */
function view(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	Global.Dialog.showDialog("View",{
		title:"工人问题反馈管理——查看",
		url:"${ctx}/admin/workerProblem/goView",
		postData:{no:ret.no,expired:ret.expired},
		height:600,
		width:1000,
		returnFun:goto_query
	});
}
/* 确认 */
function confirm(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	if(ret.status!="1"){
		art.dialog({
			content: "只有已提报的才能进行确认"
		});
		return;
	}
	Global.Dialog.showDialog("View",{
		title:"工人问题反馈管理——确认",
		url:"${ctx}/admin/workerProblem/goConfirm",
		postData:{no:ret.no,expired:ret.expired},
		height:600,
		width:1000,
		returnFun:goto_query
	});
}
/* 确认并补货 */
function replenish(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	if(ret.status!="1"){
		art.dialog({
			content: "只有已提报的才能进行确认"
		});
		return;
	}
	Global.Dialog.showDialog("View",{
		title:"工人问题反馈管理——确认并补货",
		url:"${ctx}/admin/workerProblem/goReplenish",
		postData:{no:ret.no,expired:ret.expired},
		height:600,
		width:1000,
		returnFun:goto_query
	});
}
/* 处理 */
function deal(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	if(ret.status!="1" && ret.status!="2"){
		art.dialog({
			content: "只有已提报或已确认的才能进行处理"
		});
		return;
	}
	Global.Dialog.showDialog("View",{
		title:"工人问题反馈管理——处理",
		url:"${ctx}/admin/workerProblem/goDeal",
		postData:{no:ret.no,expired:ret.expired},
		height:600,
		width:1000,
		returnFun:goto_query
	});
}
function doExcel(){
	var url = "${ctx}/admin/workerProblem/doExcel";
	doExcelAll(url);
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
}
</script>
</html>
