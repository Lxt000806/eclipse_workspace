<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/mainCommi/goDlReportJqGrid",
			height:320,
			rowNum:10000000,
			colModel : [    
				//{name: "busimandescr", index: "busimandescr", width: 90, label: "业务员姓名", sortable: true, align: "left"},
				//{name: "businessmancommi", index: "businessmancommi", width: 70, label: "提成金额", sortable: true, align: "right"},
				{name: "name", index: "name", width: 70, label: "姓名", sortable: true, align: "left"},
				{name: "role", index: "role", width: 70, label: "角色", sortable: true, align: "left"},
				{name: "commi", index: "amount", width: 70, label: "提成金额", sortable: true, align: "right"},
				{name: "department1descr", index: "department1descr", width: 70, label: "一级部门", sortable: true, align: "left"},
			],
	        loadonce:true
 		};
	   Global.JqGrid.initJqGrid("dlDataTable",gridOption);
});


</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="dlDataTable"></table>
	</div>
</div>



