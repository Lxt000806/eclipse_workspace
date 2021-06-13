<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/*客户投诉*/
	var gridOption ={
		url:"${ctx}/admin/prjProg/goCustWorkerAppJqGrid",
		postData:{custCode:"${customer.code}"},
	    rowNum:10000000,
		height: $(document).height()-$("#content-list-6").offset().top-300,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "address", index: "address", width: 230, label: "楼盘", sortable: true, align: "left",} 	,		
			{name: "projectdescr", index: "projectdescr", width: 100, label: "项目经理", sortable: true, align: "left",} 	,		
			{name: "worktype12descr", index: "worktype12descr", width: 100, label: "工种", sortable: true, align: "left",} 	,		
			{name: "appdate", index: "appdate", width: 100, label: "申请时间", sortable: true, align: "left",formatter:formatDate} 	,		
			{name: "appcomedate", index: "appcomedate", width: 100, label: "申请进场时间", sortable: true, align: "left",formatter:formatDate} 	,		
		], 
	};
	//初始化送货申请明细
	Global.JqGrid.initJqGrid("dataTable_custWorkerApp",gridOption);
});
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list-6" >
		<table id="dataTable_custWorkerApp"></table>
	</div>
</div>



