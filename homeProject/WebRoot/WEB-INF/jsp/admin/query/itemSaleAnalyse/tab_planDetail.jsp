<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>预算明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript"> 
$(function(){
	var planGridOption ={
		height:415,
		styleUI: 'Bootstrap',
		url:"${ctx}/admin/itemSaleAnalyse/goPlanJqGrid",
		postData:$("#page_form").jsonForm(), 
		colModel : [
			{name: "address", index: "address", width: 180, label: "楼盘", sortable: true, align: "left",},
			{name: "itemcode", index: "itemcode", width: 90, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 180, label: "材料名称", sortable: true, align: "left"},
			{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 180, label: "供应商名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "left",sum: true},
			{name: "unitprice", index: "unitprice", width: 80, hidden:true,label: "单价", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 80, label: "总价", sortable: true, align: "left", sum: true},
		], 
	};
	Global.JqGrid.initJqGrid("planDataTable",planGridOption);
});
</script>
</head>
	<body>
		<div class="pageContent">
			<div id="content-list">
				<table id="planDataTable" ></table>
				<div id="planDataTablePager"></div>
			</div>
		</div>
	</body>	
</html>
