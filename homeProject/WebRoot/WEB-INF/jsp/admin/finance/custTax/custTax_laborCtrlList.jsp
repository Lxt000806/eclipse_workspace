<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>劳务分包查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
		.frozen-div .jqg-third-row-header {
			height: 53px;
		}
	</style>
<script type="text/javascript"> 
var formatSum2 = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2};
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custTax/goLaborCtrlListJqGrid",
		postData:$("#page_form").jsonForm(),
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		rowNum:10000,
		colModel : [
			{name: "address", index: "address", excelLabel: "楼盘", width: 150, label: "楼盘", align: "left", frozen: true, count: true},
			{name: "custcheckdate", index: "custcheckdate", excelLabel: "结算时间", width: 90 , label: "结算时间", align: "left", frozen: true, formatter:formatDate},
			{name: "payeedescr", index: "payeedescr", excelLabel: "收款单位", width: 80, label: "收款单位", align: "left", frozen: true, count: true},
			{name: "hygienefee01", index: "hygienefee01", excelLabel: "拆除及其他|卫生费", width: 60, label: "卫生费", sorttype: "number", align: "right", formatter:"integer", formatoptions: {thousandsSeparator: ""}, sum: true},
			{name: "finishproductprotection01", index: "finishproductprotection01", excelLabel: "拆除及其他|成品保护", width: 70, label: "成品保护", sorttype: "number", align: "right", formatter:"integer", formatoptions: {thousandsSeparator: ""}, sum: true},
			{name: "secondhandling01", index: "secondhandling01", excelLabel: "拆除及其他|二次搬运", width: 70, label: "二次搬运", sorttype: "number", align: "right", formatter:"integer", formatoptions: {thousandsSeparator: ""}, sum: true},
			{name: "demolitionlabor01", index: "demolitionlabor01", excelLabel: "拆除及其他|拆除人工", width: 70, label: "拆除人工", sorttype: "number", align: "right", formatter:"integer", formatoptions: {thousandsSeparator: ""}, sum: true},
			{name: "simulationlabor01", index: "simulationlabor01", excelLabel: "拆除及其他|模拟放样人工", width: 90, label: "模拟放样人工", sorttype: "number", align: "right", formatter:"integer", formatoptions: {thousandsSeparator: ""}, sum: true},
			{name: "lateprotect01", index: "lateprotect01", excelname: "拆除及其他|后期成品保护", width: 90, label: "后期成品保护", sorttype: "number", align: "right", formatter:"integer", formatoptions: {thousandsSeparator: ""}, sum: true},
			{name: "labor02", index: "labor02", excelLabel: "水电|水电人工", width: 70, label: "水电人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "setlabor02", index: "setlabor02", excelLabel: "水电|后期安装人工", width: 90, label: "后期安装人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemrorp02", index: "itemrorp02", excelLabel: "水电|水电材料奖惩", width: 90, label: "水电材料奖惩", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "slottinglabor02", index: "slottinglabor02", excelname: "水电|开槽人工", width: 70, label: "开槽人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "healthlabor02", index: "healthlabor02", excelname: "水电|卫生人工", width: 70, label: "卫生人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "walllabor03", index: "walllabor03", excelLabel: "土建|砌墙人工", width: 70, label: "砌墙人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "finishlabor03", index: "finishlabor03", excelLabel: "土建|饰面人工", width: 70, label: "饰面人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "waterprooflabor03", index: "waterprooflabor03", excelLabel: "土建|防水人工", width: 70, label: "防水人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "waterproofsubsidy03", index: "waterproofsubsidy03", excelLabel: "土建|防水饰面补贴", width: 90, label: "防水饰面补贴", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "levelinglabor03", index: "levelinglabor03", excelLabel: "土建|找平人工", width: 70, label: "找平人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "wallLoftinglabor03", index: "wallLoftinglabor03", excelname: "土建|墙体放样人工", width: 85, label: "墙体放样人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "makepartylabor03", index: "makepartylabor03", excelname: "土建|找方人工", width: 70, label: "找方人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "seamLabor03", index: "seamLabor03", excelname: "土建|美缝人工", width: 70, label: "美缝人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "groundbeamlabor03", index: "groundbeamlabor03", excelname: "土建|地梁模板人工", width: 85, label: "地梁模板人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "woodlabor04", index: "woodlabor04", excelLabel: "木作|木作人工", width: 70, label: "木作人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "paintlabor05", index: "paintlabor05", excelLabel: "油漆|油漆人工", width: 70, label: "油漆人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "screedlabor05", index: "screedlabor05", excelLabel: "油漆|冲筋人工", width: 70, label: "冲筋人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "makelevellabor05", index: "makelevellabor05", excelname: "油漆|找平人工", width: 70, label: "找平人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "repairlabor05", index: "repairlabor05", excelname: "油漆|修补人工", width: 70, label: "修补人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "manned01", index: "manned01", excelname: "项目经理提成|已领人工", width: 70, label: "已领人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "recvfeefixduty01", index: "recvfeefixduty01", excelname: "项目经理提成|已领定责", width: 70, label: "已领定责", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "totalrecv", index: "totalrecv", excelname: "合计", width: 80, label: "合计", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "intlaber", index: "intlaber", excelname: "集成人工费", width: 80, label: "集成人工费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "zclaberfee", index: "zclaberfee", excelname: "主材人工费", width: 80, label: "主材人工费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "totalamount", index: "totalamount", excelname: "合计金额", width: 80, label: "合计金额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
		],
		gridComplete:function(){
           	$(".ui-jqgrid-bdiv").scrollTop(0);
        	frozenHeightReset("dataTable");
        },
	});
	$("#dataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[
				{startColumnName: 'hygienefee01', numberOfColumns: 6, titleText: "拆除及其他"},
				{startColumnName: 'labor02', numberOfColumns: 5, titleText: "水电"},
				{startColumnName: 'walllabor03', numberOfColumns: 9, titleText: "土建"},
				{startColumnName: 'woodlabor04', numberOfColumns: 1, titleText: "木作"},
				{startColumnName: 'paintlabor05', numberOfColumns: 4, titleText: "油漆"},
				{startColumnName: 'manned01', numberOfColumns: 3, titleText: "项目经理提成"},
		],
	}); 
	$("#dataTable").jqGrid("setFrozenColumns");
});

function clearForm(){
 	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#checkStatus").val("");
	$.fn.zTree.getZTreeObj("tree_checkStatus").checkAllNodes(false);
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					 <button type="button" class="btn btn-system" id="excel" onclick="doExcelNow('劳务分包查询')">导出excel</button>
					<button type="button" class="btn btn-system "
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label for="address">楼盘</label>
						<input type="text" id="address" name="address">
					</li>
					<li>
						<label>结算日期从</label>
						<input type="text" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>到</label>
						<input type="text" id="custCheckDateTo" name="custCheckDateTo" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label for="payeeCode">收款单位</label>
						<house:dict id="payeeCode" dictCode="" 
							sql=" select rtrim(Code) code,rtrim(Code)+' '+rtrim(Descr) descr from tTaxPayee where Expired = 'F' "
							sqlLableKey="descr" sqlValueKey="code">
						</house:dict>
					</li>
					<li>
						<label>客户结算状态</label>
						<house:xtdmMulit id="checkStatus" dictCode="CheckStatus" />
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							id="clear" onclick="clearForm();">清空</button>
					</li>		
				</ul>
			</form>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
		</div>
	</div>
</body>
</html>
