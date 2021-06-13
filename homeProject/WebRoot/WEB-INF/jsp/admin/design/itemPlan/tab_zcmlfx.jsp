<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		//初始化表格
	Global.JqGrid.initJqGrid("dataTable_zcmlfx",{
		url:"${ctx}/admin/itemPlan/goZCProfitJqGrid",
		postData:{custCode:'${customer.code}'},
		height:385,
		rowNum: 10000,
		colModel : [
			{name: "fixareadescr", index: "fixareadescr", width: 114, label: "区域名称", sortable: true, align: "left"},
			{name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
			{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "qty", index: "qty", width: 68, label: "数量", sortable: true, align: "right"},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 69, label: "单价", sortable: true, align: "right"},
			{name: "beflineamount", index: "beflineamount", width: 90, label: "折扣前金额", sortable: true, align: "right", sum: true},
			{name: "markup", index: "markup", width: 62, label: "折扣", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"}},
			{name: "tmplineamount", index: "tmplineamount", width: 80, label: "小计", sortable: true, align: "right", sum: true},
			{name: "processcost", index: "processcost", width: 77, label: "其他费用", sortable: true, align: "right", sum: true},
			{name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable: true, align: "right", sum: true},
			{name: "profit", index: "profit", width: 80, label: "毛利", sortable: true, align: "right", sum: true},
			{name: "profitper", index: "profitper", width: 70, label: "毛利率", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"}},
			{name: "lastupdate", index: "lastupdate", width: 126, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left", hidden: true},
			{name: "dispseq", index: "dispseq", width: 84, label: "dispseq", sortable: true, align: "left", hidden: true}
         ],
         gridComplete:function(){
        	var lineamount = $("#dataTable_zcmlfx").getCol('lineamount', false, 'sum');
       	    var profit = $("#dataTable_zcmlfx").getCol('profit', false, 'sum');
       	    var profitper = myRound(profit/lineamount*100, 2);
       	    $("#dataTable_zcmlfx").footerData('set', {"profitper": profitper});
         }
	});
});
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/itemPlan/doZCmlfxExcel";
	doExcelAll(url, "dataTable_zcmlfx");
}
</script>
</head>
<body>
	<div class="body-box-list">
		<form style="display: none" id="page_form" action="" method="post" target="targetFrame">
			<input type="hidden" name="jsonString" value="" /> <input type="hidden" name="custCode"
				value="${customer.code}" />
		</form>
		<div class="pageContent" style="padding-top: 10px;border-top: 1px solid #dfdfdf;">
			<table id="dataTable_zcmlfx"></table>
		</div>
	</div>
</body>
</html>
