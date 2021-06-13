<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
	String fromClcbmx = request.getParameter("fromClcbmx");
	String fromJcclcbmx = request.getParameter("fromJcclcbmx");
	String fromTcnzccbmx = request.getParameter("fromTcnzccbmx");
	String fromTcnjccbmx = request.getParameter("fromTcnjccbmx");
	String from = (fromClcbmx == null) ? fromJcclcbmx : fromClcbmx;
	from = (from == null) ? fromTcnzccbmx : from;
	from = (from == null) ? fromTcnjccbmx : from;
%>
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_llmx_<%=from%>",{
        autowidth: false,
        height:215,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right"},
			{name: "projectcost", index: "projectcost", width: 120, label: "项目经理结算单价", sortable: true, align: "right"},
			{name: "xmjljszj", index: "xmjljszj", width: 120, label: "项目经理结算金额", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"}
        ]
	});
});
</script>

<div class="panel panel-system">
	<div class="panel-body">
		<div class="btn-group-xs">
			领料明细<button type="button" class="btn btn-system" onclick="doExcel('dataTable_llmx_<%=from%>')">导出excel</button>
		</div>
	</div>
</div>

<table id="dataTable_llmx_<%=from%>"></table>
