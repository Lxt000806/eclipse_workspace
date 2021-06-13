<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>客服本人派单分析</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	var postData=$("#page_form").jsonForm();
	var gridOption_kf ={
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		postData:postData,
		url:"${ctx}/admin/custServiceDispatchAnlay/goJqGrid",
		colModel : [
			{name: "BusinessManDescr", index: "BusinessManDescr", width: 77, label: "家装顾问", sortable: true, align: "left",frozen:true,},
			{name: "DispatchNum", index: "DispatchNum", width: 77, label: "派单量", sortable: true, align: "right", sum: true},
			{name: "ValidOrderNum_month", index: "ValidOrderNum_month", width: 87, label: "有效派单量", sortable: true, align: "right", sum: true,excelLabel:"当月有效派单量"},
			{name: "VisitNum_month", index: "VisitNum_month", width: 87, label: "来客量", sortable: true, align: "right", sum: true,excelLabel:"当月来客量"},
			{name: "VisitRate_month", index: "VisitRate_month", width: 90, label: "来客率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"当月来客率"},
			{name: "SetNum_month", index: "SetNum_month", width: 87, label: "订单量", sortable: true, align: "right", sum: true,excelLabel:"当月订单量"},
			{name: "SetRate_month", index: "SetRate_month", width: 87, label: "下定率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"当月下定率"},
			{name: "ConstructNum_month", index: "ConstructNum_month", width: 87, label: "施工单", sortable: true, align: "right", sum: true,excelLabel:"当月施工单"},
			{name: "ConstructFee_month", index: "ConstructFee_month", width: 87, label: "合同额", sortable: true, align: "right", sum: true,excelLabel:"当月合同额"},
			{name: "TransRate_month", index: "TransRate_month", width: 80, label: "转单率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"当月转单率"},
			{name: "VisitNum_history", index: "VisitNum_history", width:90, label: "来客量", sortable: true, align: "right", sum: true,excelLabel:"历史来客量"},
			{name: "SetNum_history", index: "SetNum_history", width: 87, label: "订单量", sortable: true, align: "right", sum: true,excelLabel:"历史订单量"},
			{name: "SetRate_history", index: "SetRate_history", width: 93, label: "下定率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"历史下定率"},
			{name: "ConstructNum_history", index: "ConstructNum_history", width: 85, label: "施工单", sortable: true, align: "right", sum: true,excelLabel:"历史施工单"},
			{name: "ContractFee_history", index: "ContractFee_history", width: 90, label: "合同额", sortable: true, align: "right", sum: true,excelLabel:"历史合同额"},
			{name: "VisitNum_sum", index: "VisitNum_sum", width: 85, label: "总来客", sortable: true, align: "right", sum: true},
			{name: "VisitRate_sum", index: "VisitRate_sum", width: 90, label: "总来客率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "SetNum_sum", index: "SetNum_sum", width: 95, label: "总订单量", sortable: true, align: "right", sum: true},
			{name: "SetRate_sum", index: "SetRate_sum", width: 85, label: "总下定率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "ConstructNum_sum", index: "ConstructNum_sum", width: 95, label: "总施工单", sortable: true, align: "right", sum: true},
			{name: "ContractFee_sum", index: "ContractFee_sum", width: 87, label: "总合同额", sortable: true, align: "right", sum: true},
			{name: "TransRate_sum", index: "TransRate_sum", width: 90, label: "总转单率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
		],
		gridComplete:function(){
			countRate();
        }, 
		rowNum:1000000,
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption_kf);
	$("#dataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[
				{startColumnName: 'ValidOrderNum_month', numberOfColumns: 8, titleText: '当月转化情况'},
				{startColumnName: 'VisitNum_history', numberOfColumns: 5, titleText: '历史预约转换情况'},
				{startColumnName: 'VisitNum_sum', numberOfColumns: 7, titleText: '合计'},
		],
	});
});

function doExcel(){
	var url = "${ctx}/admin/custServiceDispatchAnlay/doExcel";
	doExcelAll(url);
}

//计算合计栏比率
function countRate(){
	var ValidOrderNum_month = parseFloat($("#dataTable").getCol('ValidOrderNum_month', false, 'sum'));
    var VisitNum_month = parseFloat($("#dataTable").getCol('VisitNum_month', false, 'sum'));
    var SetNum_month = parseFloat($("#dataTable").getCol('SetNum_month', false, 'sum'));
    var ConstructNum_month = parseFloat($("#dataTable").getCol('ConstructNum_month', false, 'sum'));
    var VisitNum_history = parseFloat($("#dataTable").getCol('VisitNum_history', false, 'sum'));
    var SetNum_history = parseFloat($("#dataTable").getCol('SetNum_history', false, 'sum'));
    var VisitNum_sum = parseFloat($("#dataTable").getCol('VisitNum_sum', false, 'sum'));
    var SetNum_sum = parseFloat($("#dataTable").getCol('SetNum_sum', false, 'sum'));
    var ConstructNum_sum = parseFloat($("#dataTable").getCol('ConstructNum_sum', false, 'sum'));
 	    
 	var VisitRate_month=ValidOrderNum_month==0?0:myRound(VisitNum_month/ValidOrderNum_month*100,1);
 	var SetRate_month=VisitNum_month==0?0:myRound(SetNum_month/VisitNum_month*100,1);
   	var TransRate_month=SetNum_month==0?0:myRound(ConstructNum_month/SetNum_month*100,1);
 	var SetRate_history=VisitNum_history==0?0:myRound(SetNum_history/VisitNum_history*100,1);
 	var VisitRate_sum=ValidOrderNum_month==0?0:myRound(VisitNum_sum/ValidOrderNum_month*100,1);
 	var SetRate_sum=VisitNum_sum==0?0:myRound(SetNum_sum/VisitNum_sum*100,1);
   	var TransRate_sum=SetNum_sum==0?0:myRound(ConstructNum_sum/SetNum_sum*100,1);
 	
    $("#dataTable").footerData('set', {"VisitRate_month": VisitRate_month});
    $("#dataTable").footerData('set', {"SetRate_month": SetRate_month});
    $("#dataTable").footerData('set', {"TransRate_month": TransRate_month});
    $("#dataTable").footerData('set', {"SetRate_history": SetRate_history});
    $("#dataTable").footerData('set', {"VisitRate_sum": VisitRate_sum});
    $("#dataTable").footerData('set', {"SetRate_sum": SetRate_sum});
    $("#dataTable").footerData('set', {"TransRate_sum": TransRate_sum});
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>统计日期从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
										onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:160px;"
										value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;"
										onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${customer.dateTo }'  pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<!-- panelBar -->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
							<button type="button" class="btn btn-system "  onclick="doExcel()" >
								<span>导出excel</span>
							</button>
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
				</div> 
	</body>	
</html>
