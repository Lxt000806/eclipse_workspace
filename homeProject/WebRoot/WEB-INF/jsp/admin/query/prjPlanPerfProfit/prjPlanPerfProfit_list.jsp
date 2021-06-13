<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>工地预算利润分析</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
function goto_query(){
	if($.trim($("#signDateFrom").val())=="" && $.trim($("#address").val()) ==""){
		art.dialog({
			content: "统计开始日期不能为空"
		});
		return false;
	} 
	if($.trim($("#signDateTo").val())==""&& $.trim($("#address").val()) ==""){
		art.dialog({
			content: "统计结束日期不能为空"
		});
		return false;
	}
    var dateStart = Date.parse($.trim($("#dateFrom").val()));
    var dateEnd = Date.parse($.trim($("#dateTo").val()));
    if($.trim($("#address").val()) =="" && dateStart>dateEnd){
   		art.dialog({content: "统计开始日期不能大于结束日期",width: 200});
 		return false;
    } 
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/prjPlanPerfProfit/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}				
/**初始化表格*/
$(function(){
	var postData=$("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum : 10000000,
		pager:1,
		colModel : [
			{name: "address", index: "address", width: 180, label: "楼盘", sortable: true, align: "left"},
		 	{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "signdate", index: "signdate", width: 80, label: "签单时间", sortable: true, align: "left", formatter:formatDate},
			{name: "designmandescr", index: "designmandescr", width: 80, label: "设计师", sortable: true, align: "left"},
			{name: "department2descr", index: "department2descr", width: 80, label: "设计部", sortable: true, align: "left"},
			{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
			{name: "contractfee", index: "contractfee", width: 80, label: "合同总造价", sortable: true, align: "right", sum: true},
			//{name: "outsetbaseplan", index: "outsetbaseplan", width: 80, label: "套外基础", sortable: true, align: "right", sum: true},
			{name: "outsetmainplan", index: "outsetmainplan", width: 80, label: "套外主材", sortable: true, align: "right", sum: true},
			{name: "outsetsoftplan", index: "outsetsoftplan", width: 80, label: "软装预算", sortable: true, align: "right", sum: true},
			{name: "outsetjcplan", index: "outsetjcplan", width: 80, label: "套外集成", sortable: true, align: "right", sum: true},
			{name: "alldisc", index: "alldisc", width: 80, label: "合计优惠", sortable: true, align: "right", sum: true},
			{name: "avgdisc", index: "avgdisc", width: 80, label: "单方优惠", sortable: true, align: "right"},
			{name: "avgmainsetcost", index: "avgmainsetcost", width: 115, label: "单方套内主材成本", sortable: true, align: "right"},
			{name: "baseplan", index: "baseplan", width: 80, label: "基础预算", sortable: true, align: "right", sum: true},
			{name: "setminus", index: "setminus", width: 90, label: "基础套内减项", sortable: true, align: "right", sum: true},
			{name: "setadd", index: "setadd", width: 95, label: "基础套外增项", sortable: true, align: "right", sum: true},
			{name: "outctrlamt", index: "outctrlamt", width: 80, label: "套外发包", sortable: true, align: "right", sum: true},
			{name: "basedisc", index: "basedisc", width: 80, label: "基础优惠", sortable: true, align: "right", sum: true},
			{name: "maindisc", index: "maindisc", width: 80, label: "主材优惠", sortable: true, align: "right", sum: true},
			{name: "intdisc", index: "intdisc", width: 80, label: "集成优惠", sortable: true, align: "right", sum: true},
			{name: "softdisc", index: "softdisc", width: 80, label: "软装优惠", sortable: true, align: "right", sum: true},
			{name: "basectrlamt", index: "basectrlamt", width: 80, label: "发包额", sortable: true, align: "right", sum: true},
			{name: "costall_zc", index: "costall_zc", width: 100, label: "主材预估成本", sortable: true, align: "right", sum: true},
			{name: "costall_jc", index: "costall_jc", width: 100, label: "集成预估成本", sortable: true, align: "right", sum: true},
			{name: "costall_rz", index: "costall_rz", width: 100, label: "软装预估成本", sortable: true, align: "right", sum: true},
			{name: "profit", index: "profit", width: 80, label: "预算利润率", sortable: true, align: "right" , sum: true,formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"}},
	    ],    
	    gridComplete:function(){
			var contractfee = parseFloat($("#dataTable").getCol('contractfee', false, 'sum'));
 		 	var basectrlamt = parseFloat($("#dataTable").getCol('basectrlamt', false, 'sum'));
 		 	var costall_zc = parseFloat($("#dataTable").getCol('costall_zc', false, 'sum'));
 		 	var costall_jc = parseFloat($("#dataTable").getCol('costall_jc', false, 'sum'));
 		 	var costall_rz = parseFloat($("#dataTable").getCol('costall_rz', false, 'sum'));
		  	var profit=contractfee==0?0:myRound((contractfee-basectrlamt-costall_zc-costall_jc-costall_rz)/contractfee*100,2);
		 	$("#dataTable").footerData('set', {"profit": profit});
		}
	});
});

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/prjPlanPerfProfit/doExcel";
	doExcelAll(url);
}

function includInitSign(obj){
	if ($(obj).is(':checked')){
		$('#isInitSign').val('1');
	}else{
		$('#isInitSign').val('0');
	}
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
				    <li>
				    	<label>楼盘</label> <input type="text" id="address" name="address" value="${itemPlan.address}" />
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li>
						<label>签单时间从</label> 
						<input type="text" id="signDateFrom" name="signDateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.dateFrom}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>到</label>
						  <input type="text" id="signDateTo" name="signDateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.dateTo}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li id="loadMore">
					    <input type="checkbox" id="isInitSign" name="isInitSign" onclick="includInitSign(this)" value="0"/>包含草签&nbsp
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="PRJPLANPERFPROFIT_EXCEL">
						<button type="button" class="btn btn-system"  onclick="doExcel()">导出excel</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
		<%-- <div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="PRJPLANPERFPROFIT_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">输出到Excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
		
 --%>	</div>
</body>
</html>


