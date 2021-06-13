<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/*客户投诉*/
	var gridOption ={
		url:"${ctx}/admin/prjProg/goPrjProblemJqGrid",
		postData:{custCode:"${customer.code}"},
	    rowNum:10000000,
		height: $(document).height()-$("#content-list-6").offset().top-300,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "statusdescr", index: "statusdescr", width: 85, label: "问题单状态", sortable: true, align: "left",},		
			{name: "appczydescr", index: "appczydescr", width: 75, label: "提报人", sortable: true, align: "left",},		
			{name: "appdate", index: "appdate", width: 100, label: "提报时间", sortable: true, align: "left",formatter:formatDate},		
			{name: "constructionstage", index: "constructionstage", width: 80, label: "施工阶段", sortable: true, align: "left",},		
			{name: "deptdescr", index: "deptdescr", width: 90, label: "责任部门", sortable: true, align: "left",},		
			{name: "promtypedescr", index: "promtypedescr", width: 75, label: "责任分类", sortable: true, align: "left",},		
			{name: "prompropdescr", index: "prompropdescr", width: 75, label: "问题性质", sortable: true, align: "left",},		
			{name: "isbringstopdescr", index: "isbringstopdescr", width: 70, label: "导致停工", sortable: true, align: "left",},		
			{name: "stopdays", index: "stopdays", width: 70, label: "停工天数", sortable: true, align: "right",},		
			{name: "remarks", index: "remarks", width: 230, label: "问题描述", sortable: true, align: "left",},		
			{name: "confirmczydescr", index: "confirmczydescr", width: 75, label: "确认人", sortable: true, align: "left",},		
			{name: "confirmdate", index: "confirmdate", width: 100, label: "确认时间", sortable: true, align: "left",formatter:formatDate},		
			{name: "feedbackczydescr", index: "feedbackczydescr", width: 75, label: "反馈人", sortable: true, align: "left",},		
			{name: "feedbackdate", index: "feedbackdate", width: 100, label: "反馈时间", sortable: true, align: "left",formatter:formatDate},		
			{name: "plandealdate", index: "plandealdate", width: 100, label: "预计处理时间", sortable: true, align: "left",formatter:formatDate},		
			{name: "dealremarks", index: "dealremarks", width: 230, label: "解决方案", sortable: true, align: "left",},		
			{name: "dealczydescr", index: "dealczydescr", width: 75, label: "处理人", sortable: true, align: "left",},		
			{name: "dealdate", index: "dealdate", width: 75, label: "处理时间", sortable: true, align: "left",formatter:formatDate},		
			{name: "isdealdescr", index: "isdealdescr", width: 75, label: "是否解决", sortable: true, align: "left",},		
		], 
	};
	//初始化送货申请明细
	Global.JqGrid.initJqGrid("dataTable_prjProblem",gridOption);
});
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list-6" >
		<table id="dataTable_prjProblem"></table>
	</div>
</div>
