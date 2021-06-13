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
	<title>工人在建工地查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/worker/doOnDoDetailExcel";
	doExcelAll(url);
}
$(function(){
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData:{workerCode:'${workerCode}',department2:'${department2}'},
		url:"${ctx}/admin/worker/goOnDoDetailJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'CustCode',	index:'CustCode',	width:80,	label:'客户编号',	sortable:true,align:"left",hidden:true},
			{name:'address',	index:'address',	width:130,	label:'楼盘地址',	sortable:true,align:"left",},
			{name:'custtypedescr',	index:'custtypedescr',	width:130,	label:'客户类型',	sortable:true,align:"left",},
			{name:'layoutdescr',	index:'layoutdescr',	width:130,	label:'户型',	sortable:true,align:"left",},
			{name:'area',	index:'area',	width:130,	label:'面积',	sortable:true,align:"right",},
			{name:'worktypedescr',	index:'worktypedescr',	width:80,	label:'工种',	sortable:true,align:"left",},
			{name:'begindate',	index:'begindate',	width:110,	label:'开工时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'ComeDate',	index:'ComeDate',	width:110,	label:'进场时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'PlanEnd',	index:'PlanEnd',	width:110,	label:'计划结束时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'LastUpdate',	index:'LastUpdate',	width:110,	label:'最后修改时间',	sortable:true,align:"left",formatter: formatTime},
		
		],
	});
});
	

</script>
</head>
	<body>
	<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
								<span>导出excel</span>
							</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
	<div class="body-box-list">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li hidden="true">
							<label>工人编号</label>
							<input type="text" id="workerCode" name="workerCode" style="width:160px;" value="${workerCode }"/>
						</li>
					</ul>
				</form>
			</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
