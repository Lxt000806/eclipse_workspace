<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>采购部分到货统计明细</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url : "${ctx}/admin/purchasePartArrive/goJqGridDetail",
			postData: {
				whcode:'${purchase.whcode}',
				itCode:'${purchase.itCode}'.trim(),
				itemType2: '${itemType2}',
				arriveDate: '${purchase.arriveDate}',
				statistcsMethod: '${statistcsMethod}'
			},
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			colModel : [
			    {name: "itemcode", index: "itemcode", width: 90, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 280, label: "材料名称", sortable: true, align: "left"},
				{name: "no", index: "no", width: 90, label: "采购单号", sortable: true, align: "left"},
				{name: "arrivedate", index: "arrivedate", width: 120, label: "到货日期", sortable: true, align: "left", formatter:formatTime},
				{name: "arrivqty", index: "arrivqty", width: 80, label: "到货数量", sortable: true, align: "right"},
				{name: "cost", index: "cost", width: 95, label: "库存成本单价", sortable: true, align: "right", formatter: 'number', formatoptions: { decimalPlaces: 2 },hidden:true},
				{name: "allcost", index: "allcost", width: 95, label: "库存成本总价", sortable: true, align: "right", formatter: 'number', formatoptions: { decimalPlaces: 2 },hidden:true}
			]
		});	
		if('${costRight}'=='1'){
			$("#dataTable").jqGrid('showCol', "cost");
			$("#dataTable").jqGrid('showCol', "allcost");
		}		
	});
	
	function doExcel(){
		var url = "${ctx}/admin/purchasePartArrive/doDetailExcel";
		doExcelAll(url);
	}

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form" style="display: none">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="whcode" value="${purchase.whcode }" />
				<input type="hidden" name="itCode" value="${purchase.itCode }" />
				<input type="hidden" name="itemType2" value="${itemType2 }" />
				<input type="hidden" name="statistcsMethod" value="${purchase.statistcsMethod }" />
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="PURCHASEPARTARRIVE_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>


