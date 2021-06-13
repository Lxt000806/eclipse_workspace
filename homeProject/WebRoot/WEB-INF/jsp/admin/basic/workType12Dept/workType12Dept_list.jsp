<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>专盘信息管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workType12Dept/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'Code',	index:'Code',	width:70,	label:'编号',	sortable:true,align:"left" ,},
			{name:'Descr',	index:'Descr',	width:110,	label:'班组名称',	sortable:true,align:"left" ,},
			{name:'WorkType12',	index:'WorkType12',	width:90,	label:'工种编号',	sortable:true,align:"left" ,hidden:true},
			{name:'worktype12descr',	index:'worktype12descr',	width:90,	label:'工种',	sortable:true,align:"left" ,},
			{name:'LastUpdatedBy',	index:'LastUpdatedBy',	width:100,	label:'最后修改人员',	sortable:true,align:"left" ,},
			{name:'LastUpdate',	index:'LastUpdate',	width:105,	label:'最后修改时间',	sortable:true,align:"left" ,formatter: formatTime},
			{name:'Expired',	index:'Expired',	width:105,	label:'是否过期',	sortable:true,align:"left" ,},
			{name:'ActionLog',	index:'ActionLog',	width:105,	label:'操作日志',	sortable:true,align:"left" ,},
		
		],
	});
	
	$("#save").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"工种分组——新增",
			url:"${ctx}/admin/workType12Dept/goSave",
			height:400,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#copy").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("Copy",{
			title:"工种分组——新增",
			url:"${ctx}/admin/workType12Dept/goCopy",
			postData:{code:ret.Code},
			height:400,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("update",{
			title:"工种分组——编辑",
			url:"${ctx}/admin/workType12Dept/goUpdate",
			postData:{code:ret.Code},
			height:400,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("view",{
			title:"工种分组——查看",
			url:"${ctx}/admin/workType12Dept/goView",
			postData:{code:ret.Code},
			height:400,
			width:700,
			returnFun:goto_query
		});
	});		
	
});
function doExcel(){
	var url = "${ctx}/admin/workType12Dept/doExcel";
	doExcelAll(url);
}
function clearForm(){
	$("#workType12_NAME").val('');
	$("#workType12").val('');
	$.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
} 

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="expired" name="expired" />
					<ul class="ul-form">
						<li>
							<label>工种</label>
							<house:DictMulitSelect id="workType12" dictCode="" sql="select Code,Descr from tWorkType12 a " 
							sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
						</li>
						<li>
							<input type="checkbox" id="expired_show" name="expired_show"
								onclick="hideExpired(this)" checked/>
							<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
					</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<house:authorize authCode="WORKTYPE12DEPT_ADD">
							<button type="button" class="btn btn-system " id="save"><span>新增</span> 
						</house:authorize>
						<house:authorize authCode="WORKTYPE12DEPT_COPY">
							<button type="button" class="btn btn-system " id="copy"><span>复制</span> 
						</house:authorize>
						<house:authorize authCode="WORKTYPE12DEPT_UPDATE">
							<button type="button" class="btn btn-system "  id="update"><span>编辑</span> 
						</house:authorize>
						<house:authorize authCode="WORKTYPE12DEPT_VIEW">
							<button type="button" class="btn btn-system "  id="view"><span>查看</span> 
						</house:authorize>
						<house:authorize authCode="WORKTYPE12DEPT_EXCEL">
							<button type="button" class="btn btn-system "  onclick="doExcel()" >
								<span>导出excel</span>
						</house:authorize>
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
	</body>	
</html>
