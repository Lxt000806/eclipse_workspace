<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>结算浮动规则明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	/**初始化表格*/
	$(function(){
		
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/commiCustStakeholderRule/goRuleJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			postData:{crtNo:"${commiCustStakeholderRule.crtNo}",checkFloatRulePk:"${commiCustStakeholderRule.checkFloatRulePk}"},
			styleUI: "Bootstrap", 
			colModel : [
				{name: "cardinalfrom", index: "cardinalfrom", width: 90, label: "提成基数从", sortable: true, align: "right"},
				{name: "cardinalto", index: "cardinalto", width: 90, label: "提成基数到", sortable: true, align: "right"},
				{name: "commiper", index: "commiper", width: 70, label: "提成点", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 200, label: "说明", sortable: true, align: "left"},
			],
		});
	});
	
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
