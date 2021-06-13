<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料结算分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">	
	/**初始化表格*/
	$(function(){
		var custCheckDateTo = $("#custCheckDateTo").val();
		var custCheckDateFrom = $("#custCheckDateFrom").val();
		Global.JqGrid.initJqGrid("dataTable",{   		 					    
			height:$(document).height()-$("#content-list").offset().top-100,	
			url:"${ctx}/admin/cljsfx/goJqGridDetail",										      
			postData:{department1:"${customer.department1}",department2:"${customer.department2}",custCheckDateFrom:custCheckDateFrom,
						custCheckDateTo:custCheckDateTo,custType:"${customer.custType}",itemType1:"${customer.itemType1}"},
			styleUI: "Bootstrap",
			colModel : [
				{name: "custcode", index: "custcode", width: 105, label: "客户编号", sortable: true, align: "left",count:true},
				{name: "custdescr", index: "custdescr", width: 105, label: "客户名称", sortable: true, align: "left"},
				{name: "address", index: "address", width: 260, label: "楼盘", sortable: true, align: "left"},
				{name: "area", index: "area", width: 105, label: "结算面积", sortable: true, align: "right", sum: true},
				{name: "lineamount", index: "lineamount", width: 105, label: "结算金额", sortable: true, align: "right", sum: true},
				{name: "allcost", index: "allcost", width: 105, label: "结算成本", sortable: true, align: "right", sum: true},
				{name: "profit", index: "profit", width: 105, label: "结算利润", sortable: true, align: "right", sum: true}
			]
		});	
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<input type="hidden" id="expired" name="expired" value="F" />
		<input type="hidden" name="jsonString" value="" />
		<li hidden="true">
			<label >客户结算时间从</label>
			<input type="text" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date"  
			onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
			value="<fmt:formatDate value='${customer.custCheckDateFrom}' pattern='yyyy-MM-dd'/>" />
		</li>
		<li hidden="true">
			<label >至</label>
			<input type="text" id="custCheckDateTo" name="custCheckDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
			value="<fmt:formatDate value='${customer.custCheckDateTo}' pattern='yyyy-MM-dd'/>" />
		</li>	
		<div class="btn-panel" >			
			<div id="content-list">               
				<table id= "dataTable"></table>  
				<div id="dataTablePager"></div>
			</div>			
		</div> 
	</div>
</body>
</html>


