<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>工资类型</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${salaryType.expired }"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>工资名称</label> 
						<input type="text" id="descr" name="descr" style="width:160px;" value="${salaryType.descr }"/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${salaryType.expired }" onclick="hideExpired(this)" ${salaryType.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="copy">
					<span>复制</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<house:authorize authCode="SALARYTYPE_VIEW">
					<button type="button" class="btn btn-system" id="view">
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
var postData = $("#page_form").jsonForm();
$(function(){
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/salaryType/goJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name : "code", index:"code", width:100, label:"工资类型编号", sortable:true, align:"left"},
			{name : "descr", index:"descr", width:100, label:"工资类型名称", sortable:true, align:"left"},
			{name : "iscalcost",index : "iscalcost",width : 90,label:"是否计成本",sortable : true,align : "left",hidden:true},
			{name : "iscalcostdescr",index : "iscalcostdescr",width : 90,label:"是否计成本",sortable : true,align : "left"},
			{name : "issign",index : "issign",width : 100,label:"是否需要出纳签字",sortable : true,align : "left",hidden:true},
			{name : "issigndescr",index : "issigndescr",width : 130,label:"是否需要出纳签字",sortable : true,align : "left"},
			{name : "code1",index : "code1",width : 100,label:"工种分类1",sortable : true,align : "left",hidden:true},
			{name : "worktype1descr",index : "worktype1descr",width : 100,label:"工种分类1",sortable : true,align : "left"},
			{name : "code2",index : "code2",width : 100,label:"工种分类2",sortable : true,align : "left",hidden:true},
			{name : "worktype2descr",index : "worktype2descr",width : 100,label:"工种分类2",sortable : true,align : "left"},
			{name : "iscalprojectcost",index : "iscalprojectcost",width : 100,label:"是否计项目经理成本",sortable : true,align : "left",hidden:true},
			{name : "iscalprojectcostdescr",index : "iscalprojectcostdescr",width : 130,label:"是否计项目经理成本",sortable : true,align : "left"},
			{name : "lastupdate",index : "lastupdate",width : 150,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
      		{name : "lastupdatedby",index : "lastupdatedby",width : 70,label:"修改人",sortable : true,align : "left"},
     		{name : "expired",index : "expired",width : 70,label:"过期标志",sortable : true,align : "left"},
    		{name : "actionlog",index : "actionlog",width : 70,label:"操作日志",sortable : true,align : "left"}
		]
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("save", {
			title : "工资类型——新增",
			url : "${ctx}/admin/salaryType/goUpdate",
			postData:{
				m_umState:"A",
				isCalCost:0,
				isSign:0,
				isCalProjectCost:0
			},
			height : 420,
			width : 400,
			returnFun : goto_query
		});
	});
	
	$("#copy").on("click", function() {
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("copy", {
			title : "工资类型——复制",
			url : "${ctx}/admin/salaryType/goUpdate",
			postData:{
				m_umState:"C",
				code:$.trim(ret.code)
			},
			height : 420,
			width : 400,
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
			title:"工资类型——编辑",
			url:"${ctx}/admin/salaryType/goUpdate",
			postData:{
				m_umState:"M",
				code:$.trim(ret.code)
			},
			height:420,
			width:400,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"工资类型——查看",
			url:"${ctx}/admin/salaryType/goUpdate",
			postData:{
				m_umState:"V",
				code:$.trim(ret.code)
			},
			height:420,
			width:400,
			returnFun:goto_query
		});
	});
});

function doExcel(){
	var url = "${ctx}/admin/salaryType/doExcel";
	doExcelAll(url);
}
</script>
</html>
