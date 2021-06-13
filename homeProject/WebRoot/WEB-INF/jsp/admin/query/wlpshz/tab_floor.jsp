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
	<title>未安排工地</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_autoArr.js?v=${v}" type="text/javascript"></script>
			<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	
	Global.JqGrid.initJqGrid("dataTable_floor",{
		url:"${ctx}/admin/wlpshz/goFloorJqGrid",
		postData:{date:"${date }"},
		height:$(document).height()-$("#content-list").offset().top-160	,
		styleUI: "Bootstrap", 
		rowNum:100000,
		colModel : [
				{name:"iasno",	index:"iasno",	width:85,	label:"发货单号",	sortable:true,align:"left" ,},
				{name:"iano",	index:"iano",	width:85,	label:"领料单号",	sortable:true,align:"left" ,},
				{name:"itemcode",	index:"itemcode",	width:85,	label:"材料编号",	sortable:true,align:"left" ,},
				{name:"itemdescr",	index:"itemdescr",	width:85,	label:"材料名称",	sortable:true,align:"left" ,},
				{name:"item2descr",	index:"item2descr",	width:85,	label:"材料类型2",	sortable:true,align:"left" ,},
				{name:"item3descr",	index:"item3descr",	width:85,	label:"材料类型3",	sortable:true,align:"left" ,},
				{name:"qty",	index:"qty",	width:65,	label:"数量",	sortable:true,align:"right" ,},
				{name:"uomdescr",	index:"uomdescr",	width:65,	label:"单位",	sortable:true,align:"left" ,},
				{name:"allweight",	index:"allweight",	width:65,	label:"总重量",	sortable:true,align:"right" ,},
				{name:"remarks",	index:"remarks",	width:135,	label:"备注",	sortable:true,align:"left" ,},
				{name:"confirmdate",	index:"confirmdate",	width:85,	label:"审核日期",	sortable:true,align:"left" ,formatter:formatDate},
				{name:"senddate",	index:"senddate",	width:85,	label:"发货日期",	sortable:true,align:"left" ,formatter:formatDate},
				{name:"sendday",	index:"sendday",	width:85,	label:"发货时限（小时）",	sortable:true,align:"right" ,},
				{name:"custcode",	index:"custcode",	width:85,	label:"客户编号",	sortable:true,align:"left" ,},
				{name:"address",	index:"address",	width:222,	label:"到货地址",	sortable:true,align:"left" ,},				
		],
	});
	
}); 


</script>
</head>
	<body>
		<div class="body-box-list">
			<div id="content-list">
				<table id= "dataTable_floor"></table>
			</div>
		</div>
	</body>	
</html>
