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
$(function(){
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData:{workerCode:'${custWorker.workerCode }'},
		url:"${ctx}/admin/worker/goOnDoDetailJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'CustCode',	index:'CustCode',	width:80,	label:'客户编号',	sortable:true,align:"left",},
			{name:'address',	index:'address',	width:130,	label:'楼盘地址',	sortable:true,align:"left",},
			{name:'workTypedescr',	index:'workTypedescr',	width:80,	label:'工种类型',	sortable:true,align:"left",},
			{name:'statusDescr',	index:'statusDescr',	width:80,	label:'施工状态',	sortable:true,align:"left",},
			{name:'ComeDate',	index:'ComeDate',	width:110,	label:'进场时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'PlanEnd',	index:'PlanEnd',	width:110,	label:'计划结束时间',	sortable:true,align:"left",formatter: formatDate},
			{name: "MinCrtDate", index: "MinCrtDate", width: 125, label: "最早签到时间", sortable: true, align: "left", formatter: formatTime},
            {name: "MaxCrtDate", index: "MaxCrtDate", width: 125, label: "最晚签到时间", sortable: true, align: "left", formatter: formatTime},
			{name: "SignPrjItem2", index: "SignPrjItem2", width: 100, label: "签到阶段", sortable: true, align: "left"},
			{name: "IsCompleteDescr", index: "IsCompleteDescr", width: 70, label: "阶段完成", sortable: true, align: "left"},
			{name:'Remarks',	index:'Remarks',	width:150,	label:'备注',	sortable:true,align:"left"},
			{name:'LastUpdate',	index:'LastUpdate',	width:110,	label:'最后修改时间',	sortable:true,align:"left",formatter: formatTime},
		],
	});
});
	

</script>
</head>
	<body>
	<div class="body-box-list">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li hidden="true">
								<label>工人编号</label>
								<input type="text" id="workerCode" name="workerCode" style="width:160px;" value="${custWorker.workerCode }"/>
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
