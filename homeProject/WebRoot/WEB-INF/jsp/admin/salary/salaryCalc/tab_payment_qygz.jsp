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
	<title>薪酬计算方案适用人员</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-120,
		rowNum:50,
		multiselect:true,
		colModel : [
			{name:"cmpdescr", index:"cmpdescr", width:80, label:"公司", sortable:true ,align:"left",},
			{name:"dept1descr", index:"dept1descr", width:80, label:"一级部门", sortable:true ,align:"left",},
			{name:"dept2descr", index:"dept2descr", width:80, label:"二级部门", sortable:true ,align:"left",},
			{name:"empname", index:"empname", width:80, label:"姓名", sortable:true ,align:"left",},
			{name:"empcode", index:"empcode", width:80, label:"工号", sortable:true ,align:"left",},
			{name:"posiclassdescr", index:"posiclassdescr", width:80, label:"职位类别", sortable:true ,align:"left",},
			{name:"joindate", index:"joindate", width:80, label:"入职时间", sortable:true ,align:"left",formatter:formatDate},
			{name:"leavedate", index:"leavedate", width:80, label:"离职时间", sortable:true ,align:"left",formatter:formatDate},
		],
	}; 
	
	$.extend(gridOption,{
		url:"${ctx}/admin/salaryScheme/goEmpListJqgrid",
		postData:{pk:$.trim($("#salaryScheme").val())}
	});
	
	Global.JqGrid.initJqGrid("dataTableQYGZ",gridOption);
});


</script>
</head>
	<body>
		<div class="body-box-form">
			<div id="content-list">
				<table id="dataTableQYGZ"></table>
				<table id="dataTableQYGZPager"></table>
			</div>
		</div>
	</body>	
</html>
