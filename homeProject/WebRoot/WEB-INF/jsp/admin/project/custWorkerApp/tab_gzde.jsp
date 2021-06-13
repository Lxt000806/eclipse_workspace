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
	<title>工种定额</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
<script type="text/javascript"> 
$(function(){
	
	Global.JqGrid.initJqGrid("dataTable_gzde",{
		url:"${ctx}/admin/CustWorkerApp/goDeJqGrid",
		postData:{custCode:'${customer.code }',workType12:'${workType12}'},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:"worktype12", index:"worktype12", width:80, label:"工种分类12", sortable:true, align:"left", hidden:true}, 
			{name:"baseitemdescr", index:"baseitemdescr", width:200, label:"结算项目名称", sortable:true, align:"left"}, 
			{name:"fixareadescr", index:"fixareadescr", width:140, label:"区域", sortable:true, align:"left"}, 
			{name:"qty", index:"qty", width:60, label:"数量", sortable:true, align:"right"}, 
			{name:"uom", index:"uom", width:60, label:"单位code", sortable:true, align:"left", hidden:true}, 
			{name:"uomdescr", index:"uomdescr", width:60, label:"单位", sortable:true, align:"left"}, 
			{name:"offerpri", index:"offerpri", width:80, label:"人工单价", sortable:true, align:"right"}, 
			{name:"totalprice", index:"totalprice", width:80, label:"总价", sortable:true, align:"right", sum:true}, 
		],
	});
	
}); 


</script>
</head>
	<body>
		<div class="body-box-list">
			<div id="content-list">
				<table id= "dataTable_gzde"></table>
			</div>
		</div>
	</body>	
</html>
