<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<title>质保金明细</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjManCheck/goJqGrid_qualityDetail",
		postData:{projectMan:"${prjCheck.projectMan}"},
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		colModel : [
		    {name: "documentno", index: "documentno", width: 101, label: "档案号", sortable: true, align: "left", count: true},
			{name: "address", index: "address", width: 216, label: "楼盘", sortable: true, align: "left"},
			{name: "projectmandescr", index: "projectmandescr", width: 136, label: "项目经理姓名", sortable: true, align: "left"},
			{name: "qualityfee", index: "qualityfee", width: 100, label: "质保金", sortable: true, align: "right", sum: true},
			{name: "accidentfee", index: "accidentfee", width: 100, label: "意外险", sortable: true, align: "right", sum: true}
        ],
        rowNum:100000,  
    	pager :'1',                     
	});				 
});  
</script>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<input type="hidden" name="jsonString" value="" /> <input type="hidden" id="projectMan" name="projectMan"
			value="${prjCheck.projectMan}" />
	</form>

	<div class="clear_float"></div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>

</body>

</html>


