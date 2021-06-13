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
	
	Global.JqGrid.initJqGrid("dataTable_mzgzl",{
		url:"${ctx}/admin/CustWorkerApp/goMZGZLJqGrid",
		postData:{code:'${customer.code }'},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'areadescr',	index:'areadescr',	width:85,	label:'区域',	sortable:true,align:"left" ,},
				{name:'basedescr',	index:'basedescr',	width:115,	label:'基础项目名称',	sortable:true,align:"left" ,},
				{name:'Qty',	index:'Qty',	width:75,	label:'数量',	sortable:true,align:"left" ,sum:true},
				{name:'uomdescr',	index:'uomdescr',	width:75,	label:'单位',	sortable:true,align:"left" ,},				
				{name:'UnitPrice',	index:'UnitPrice',	width:75,	label:'单价',	sortable:true,align:"left" ,},				
				{name:'woodamount',	index:'woodamount',	width:75,	label:'总价',	sortable:true,align:"left" ,sum:true},				
		],
	});
	
}); 


</script>
</head>
	<body>
		<div class="body-box-list">
			<div id="content-list">
				<table id= "dataTable_mzgzl"></table>
			</div>
		</div>
	</body>	
</html>
