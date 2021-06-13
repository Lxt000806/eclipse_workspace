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
	<title>工人信息管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${rcvAct.expired }"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>姓名</label>
						<input type="text" id="name_chi" name="nameChi" style="width: 160px;" value="${worker.nameChi}"/>
					</li>
					<li>
						<label>电话</label>
						<input type="text" id="phone" name="Phone" style="width:160px;" value="${worker.phone}"/>
					</li>
					<li>
						<label>归属区域</label>
						<house:dict id="belongRegion" dictCode="" sql="select Code,(Code+' '+Descr) Descr1 from tRegion" sqlValueKey="Code" 
							sqlLableKey="Descr1" value="${worker.belongRegion}"/>
					</li>
					<li>
						<label>归属事业部</label>
						<house:dict id="department1" dictCode="" sql="select Code,Code+' '+Desc1 Department1 from tdepartment1 where DepType='3'" 
							sqlValueKey="Code" sqlLableKey="Department1" value="${worker.department1}"/>
					</li>
					<li>
						<label>所属工程部</label>
						<house:department2 id="department2" dictCode="" depType="3"></house:department2>
					</li>
					<li>	
						<label>工种分类</label>
						<select id="work_type12" name="workType12" value="${worker.workType12}"></select>
					</li>
					<li>
						<label>所属分组</label>
						<select id="work_type12_dept" name="workType12Dept"></select>
					</li>
					<li>
						<label>归属专盘</label>
						<house:dict id="spcBuilder" dictCode="" sql="select Code,Code+' '+Descr SpcBuilder from tSpcBuilder" 
							sqlValueKey="Code" sqlLableKey="SpcBuilder" value="${worker.spcBuilder}"/>
					</li>
					<li>
						<label>签约类型</label>
						<house:xtdmMulit id="isSign" dictCode="WSIGNTYPE" selectedValue="1"></house:xtdmMulit>
					</li>
					<li>
						<label>工人级别</label>
						<house:dict id="level" dictCode="" sql="select CBM,CBM+' '+NOTE Level from tXTDM where ID='WORKERLEVEL'" 
							sqlValueKey="CBM" sqlLableKey="Level" value="${worker.level}"/>
					</li>
					<li>
						<label>状态</label>
						<house:xtdm id="isLeave" dictCode="WORKERSTS"></house:xtdm>
					</li>
					<li>
						<label>工程大区</label>
						<house:DictMulitSelect id="prjRegionCode" dictCode="" 
							sql="select Code,Descr from tPrjRegion where Expired='F' order by Code " 
							sqlLableKey="Descr" sqlValueKey="Code">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>工人分类</label>
						<house:xtdmMulit id="workerClassify" dictCode="WORKERCLASSIFY"/>
					</li>
					<li>
					    <label>劳务公司</label>
					    <house:dict id="laborCmpCode" dictCode=""
					        sql="select Code SQL_VALUE_KEY, Descr SQL_LABEL_KEY from tLaborCompny where Expired = 'F'"></house:dict>
					</li>
					<li>
						<label>班组成员姓名</label>
						<input type="text" id="memberName" name="memberName" style="width: 160px;"/>
					</li>
					<!--<li>
						<label>是否用于自装通</label>
						<house:xtdm id="isRegisterMall" dictCode="YESNO"></house:xtdm>
					</li>-->
					<li id="load_more">
						<input type="checkbox" id="expired_show" name="expired_show" value="${worker.expired}" 
							onclick="hideExpired(this)" ${worker.expired!='T' ?'checked':''}/>
						<label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-system btn-sm" id="clear" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="WORKER_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKER_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>修改</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKER_UPDATEPROP">
					<button type="button" class="btn btn-system" id="update_attribute">
						<span>修改工人属性</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKER_WORKERWORKTYPE12">
					<button type="button" class="btn btn-system" id="worker_workerType12">
						<span>设置可承接工种</span>
					</button>
				</house:authorize>	
				<house:authorize authCode="WORKER_UPDATELABORCMP">
					<button type="button" class="btn btn-system" id="updateLaborCmp">
						<span>修改劳务公司</span>
					</button>
				</house:authorize>	
				<house:authorize authCode="WORKER_REGISTERMALL">
					<button type="button" class="btn btn-system" id="registerMall">
						<span>设置自装通信息</span>
					</button>
				</house:authorize>	
				<house:authorize authCode="WORKER_VIEW">
					<button type="button" class="btn btn-system" id="viewBtn" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
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
$(function(){
    var postData = $("#page_form").jsonForm();
    
	Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12","work_type12","work_type12_dept");/* 工种分类、所属分组下拉联动 */

	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,/* 列重排 */
		url: "${ctx}/admin/worker/goJqGridList",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name: "code", index: "code", width: 50, label: "编号", sortable: true, align: "left"},
			{name: "namechi", index: "namechi", width: 70, label: "姓名", sortable: true, align: "left"},
			{name: "leveldescr", index: "leveldescr", width: 70, label: "工人级别", sortable: true, align: "left"},
			{name: "idnum", index: "idnum", width: 140, label: "身份证号", sortable: true, align: "left"},
			{name: "cardid", index: "cardid", width: 160, label: "卡号", sortable: true, align: "left"},
			{name: "cardid2", index: "cardid2", width: 160, label: "卡号2", sortable: true, align: "left"},
			{name: "worktype12descr", index: "worktype12descr", width: 70, label: "工种分类", sortable: true, align: "left"},
			{name: "worktype12deptdescr", index: "worktype12deptdescr", width: 70, label: "所属分组", sortable: true, align: "left"},
			{name: "introduceempdescr", index: "introduceempdescr", width: 70, label: "介绍人", sortable: true, align: "left"},
			{name: "issigndescr", index: "issigndescr", width: 70, label: "签约类型", sortable: true, align: "left"},
			{name: "signdate", index: "signdate", width: 80, label: "签约时间", sortable: true, align: "left", formatter: formatDate},
			{name: "empcode", index: "empcode", width: 70, label: "员工编号", sortable: true, align: "left"},
			{name: "liveregiondescr", index: "liveregiondescr", width: 100, label: "一级居住区域", sortable: true, align: "left"},
			{name: "liveregion2descr", index: "liveregion2descr", width: 100, label: "二级居住区域", sortable: true, align: "left"},
			{name: "rcvprjtypedescr", index: "rcvprjtypedescr", width: 100, label: "承接工地类型", sortable: true, align: "left"},
			{name: "vehicledescr", index: "vehicledescr", width: 65, label: "交通工具", sortable: true, align: "left"},
			{name: "num", index: "num", width: 80, label: "班组人数", sortable: true, align: "right"},
			{name: "department1descr", index: "department1descr", width: 70, label: "事业部", sortable: true, align: "left"},
			{name: "department2descr", index: "department2descr", width: 80, label: "所属工程部", sortable: true, align: "left"},
			{name: "spcbuilderdescr", index: "spcbuilderdescr", width: 120, label: "归属专盘", sortable: true, align: "left"},
			{name: "isautoarrangedescr", index: "isautoarrangedescr", width: 100, label: "是否自动安排", sortable: true, align: "left"},
			{name: "belongregiondescr", index: "belongregiondescr", width: 70, label: "归属区域", sortable: true, align: "left"},
			{name: "isleavedescr", index: "isleavedescr", width: 70, label: "是否离职", sortable: true, align: "left", hidden: true},
			{name: "efficiency", index: "efficiency", width: 100, label: "工作效率比例", sortable: true, align: "right"},
			{name: "issupvrdescr", index: "issupvrdescr", width: 70, label: "项目经理类型", sortable: true, align: "left"},
			{name: "prjleveldescr", index: "prjleveldescr", width: 70, label: "监理级别", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
			/*{name: "personalprofile", index: "personalprofile", width: 120, label: "个人简介", sortable: true, align: "left"},
			{name: "isregistermall", index: "isregistermall", width: 80, label: "是否用于自装通", sortable: true, align: "left", hidden: true},
			{name: "isregistermalldescr", index: "isregistermalldescr", width: 120, label: "是否用于自装通", sortable: true, align: "left"},*/
			{name: "leavedate", index: "leavedate", width: 80, label: "离职日期", sortable: true, align: "left", formatter: formatDate},
			{name: "laborcmpdescr", index: "laborcmpdescr", width: 80, label: "劳务公司", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"}
		],
		ondblClickRow: function(){
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("Save", {
			title : "工人信息明细--新增",
			url : "${ctx}/admin/worker/goSave",
			height : 715,
			width : 1050,
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
			title:"工人信息明细——修改",
			url:"${ctx}/admin/worker/goUpdate",
			postData:{id:ret.code},
			height:715,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#update_attribute").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"工人信息明细——修改工人属性",
			url:"${ctx}/admin/worker/goUpdateAttribute",
			postData:{id:ret.code},
			height:615,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#worker_workerType12").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"工人信息明细——设置可承接工种",
			url:"${ctx}/admin/worker/goWorkerWorkType12",
			postData:{id:ret.code},
			height:615,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#updateLaborCmp").on("click", function() {
	    var row = selectDataTableRow()
        if (!row) {
            art.dialog({content: "请选择一条记录"})
            return
        }
	
        Global.Dialog.showDialog("updateLaborCmp", {
            title: "工人信息管理--修改劳务公司",
            url: "${ctx}/admin/worker/goUpdateLaborCmp",
            postData: {workerCode: row.code},
            height: 350,
            width: 450,
            returnFun: goto_query
        })
    })
    
    $("#registerMall").on("click", function() {
    	var row = selectDataTableRow()
        if (!row) {
            art.dialog({content: "请选择一条记录"})
            return
        }
	
        Global.Dialog.showDialog("updateLaborCmp", {
            title: "工人信息管理--自装通信息",
            url: "${ctx}/admin/worker/goRegisterMall",
            postData: {workerCode: row.code},
            height: 500,
            width: 800,
            returnFun: goto_query
        })
    })
	
	$("#clear").on("click",function(){
		zTreeCheckFalse("prjRegionCode");
		zTreeCheckFalse("workerClassify");
		zTreeCheckFalse("isSign")
	});
});
// 清空下拉选择树选项
function zTreeCheckFalse(id) {
	$("#"+id).val("");
	$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
}

function view(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	Global.Dialog.showDialog("update",{
		title:"工人信息明细——查看",
		url:"${ctx}/admin/worker/goView",
		postData:{id:ret.code},
		height:680,
		width:1050,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/worker/doExcel";
	doExcelAll(url);
}
</script>
</html>
