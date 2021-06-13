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
	<title>客户回访管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>	
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>回访日期从</label>
						<input type="text" id="dateFrom" name="dateFrom"
							class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${custVisit.dateFrom}' pattern='yyyy-MM-dd'/>"/>
						
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo"
							class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${custVisit.dateTo}' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>回访状态</label>
						<house:xtdm id="status" dictCode="VISITSTATUS" style="width:160px;" value=""/>
					</li>
					<li>
						<label>回访人</label>
						<input type="text" id="visitCZY" name="visitCZY" style="width:160px;"/>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width: 160px;"/>
					</li>
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>回访类型</label>
						<house:xtdm id="visitType" dictCode="VISITTYPE" style="width:160px;" value=""/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${custVisit.expired}" 
							onclick="hideExpired(this)" ${custVisit.expired!='T' ?'checked':''}/>
						<label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
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
				<house:authorize authCode="CUSTVISIT_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增计划</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTVISIT_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>回访处理</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTVISIT_DELETE">
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTVISIT_VIEW">
					<button type="button" class="btn btn-system" id="viewBtn" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>输出到Excel</span>
				</button>
				<button type="button" class="btn btn-system" id="detailList">
					<span>明细列表</span>
				</button>
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
	$("#visitCZY").openComponent_employee();

	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,/* 列重排 */
		url: "${ctx}/admin/custVisit/goJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name: "no", index: "no", width: 80, label: "回访编号", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
			{name: "custdescr", index: "custdescr", width: 70, label: "业主姓名", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "visittypedescr", index: "visittypedescr", width: 70, label: "回访类型", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 140, label: "回访问题详情", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 70, label: "回访状态", sortable: true, align: "left"},
			{name: "satisfactiondescr", index: "satisfactiondescr", width: 70, label: "满意度", sortable: true, align: "left"},
			{name: "visitczydescr", index: "visitczydescr", width: 70, label: "回访人", sortable: true, align: "left"},
			{name: "visitdate", index: "visitdate", width: 130, label: "回访时间", sortable: true, align: "left", formatter: formatTime},
			{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
			{name: "gcdeptdescr", index: "gcdeptdescr", width: 70, label: "工程部", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
			{name: "designdeptdescr", index: "designdeptdescr", width: 70, label: "设计部", sortable: true, align: "left"}
		],
		ondblClickRow: function(){/* 双击触发事件 */
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("Save", {
			title : "客户回访--增加",
			url : "${ctx}/admin/custVisit/goSave",
			height : 550,
			width : 800,
			returnFun : goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"客户回访--编辑",
			url:"${ctx}/admin/custVisit/goUpdate",
			postData:{no:ret.no, custCode: ret.custcode,expired:"T"},
			height:700,
			width:1250,
			returnFun:goto_query
		});
	});
	
	$("#delete").on("click",function(){
		var url = "${ctx}/admin/custVisit/doDelete";
		beforeDel(url,"no","删除该客户回访的信息");
		returnFun: goto_query;
		return true;
	});
	
	/* 明细列表 */
	$("#detailList").on("click",function(){
		Global.Dialog.showDialog("detailList",{
			title:"客户回访--明细列表",
			url:"${ctx}/admin/custVisit/goDetailList",
			height:740,
			width:1250,
			returnFun:goto_query
		});
	});
	
	/* 清空下拉选择树checked状态 */
	$("#clear").on("click",function(){
		$("#department2").val("");
		$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	});
	
});

function view(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	Global.Dialog.showDialog("view",{
		title:"客户回访——查看",
		url:"${ctx}/admin/custVisit/goView",
		postData:{no:ret.no, custCode: ret.custcode,expired:"T"},
		height:700,
		width:1250,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/custVisit/doExcel";
	doExcelAll(url);
}
</script>
</html>
