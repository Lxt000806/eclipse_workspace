<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>税务信息登记——开票查询</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<!-- 修改bootstrap中各个row的边距 -->
	<style>
		.row{
			margin: 0px;
		}
		.col-xs-4{
			padding: 0px;
		}
		.col-xs-8{
			padding: 0px;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="excelBtn" onclick="doExcel();">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="body-box-list">  
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="expired" name="expired"/>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width: 160px;"/>
						</li>
						<li>
							<label>开票日期从</label>
							<input type="text" id="dateFrom" name="dateFrom"
								class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value=""/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo"
								class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value=""/>
						</li>
						<li>
							<label>发票号</label>
							<input type="text" id="invoiceNo" name="invoiceNo" style="width: 160px;"/>
						</li>
						<li>
							<label for="payeeCode">收款单位</label>
							<house:dict id="payeeCode" dictCode="" 
								sql=" select rtrim(Code) code,rtrim(Code)+' '+rtrim(Descr) descr from tTaxPayee where Expired = 'F' "
								sqlLableKey="descr" sqlValueKey="code">
							</house:dict>
						</li>
						<li>
							<label>劳务分包公司</label>
							<house:xtdmMulit id="laborCompny" dictCode="LABORCMP"/>
						</li>
						<li>
							<label>应税服务名称</label>
							<input type="text" id="taxService" name="taxService" style="width: 160px;"/>
						</li>
						<li>
							<input type="checkbox" id="expired_show" name="expired_show" value="${custVisit.expired}" 
								onclick="hideExpired(this)" ${custVisit.expired!='T' ?'checked':''}/>
							<!-- <label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label> -->
							<span>隐藏过期</span>
						</li>
						<li>
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
							<button type="button" id="clear" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		<div class="container-fluid" > 
			<div class="pageContent">
				<div id="content-list">
					<table id="dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
			</div> 
		</div>
	</div>
</body>
<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,
		url: "${ctx}/admin/custTax/goInvoiceJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 100,
		styleUI: "Bootstrap", 
		colModel: [
			{name: "date", index: "date", width: 80, label: "开票日期", sortable: true, align: "left", formatter: formatDate},
			{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
			{name: "invoiceno", index: "invoiceno", width: 80, label: "发票编号", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 80, label: "开票金额", sortable: true, align: "right", sum: true, formatter: number},
			{name: "taxper", index: "taxper", width: 70, label: "税率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return cellvalue*100+"%";}},
			{name: "notaxamount", index: "notaxamount", width: 80, label: "不含税金额", sortable: true, align: "right", formatter: number},
			{name: "taxamount", index: "taxamount", width: 70, label: "税额", sortable: true, align: "right", formatter: number},	
			{name: "payeecodedescr", index: "payeecodedescr", width: 70, label: "收款单位", sortable: true, align: "left"},
			{name: "taxservice", index: "taxservice", width: 95, label: "应税服务名称", sortable: true, align: "left"},
			{name: "laborcompnydescr", index: "laborcompnydescr", width: 100, label: "劳务分包公司", sortable: true, align: "left"},
			{name: "laboramount", index: "laboramount", width: 100, label: "劳务分包金额", sortable: true, align: "right",sum: true},
		],
		gridComplete:function(){
			// 获取列中sum，avg，count值的方法
			//var tileweightSum = parseFloat($("#dataTable").getCol("tileweight",false,"sum")).toFixed(4);
			var needSum1 = $("#dataTable").getCol("amount",false,"sum");
			// footerData-设置或者取得底部数据。myRound-四舍五入,修改负数规则。
			$("#dataTable").footerData("set",{"amount":myRound(needSum1,2)});
		}
	});
	// 清空下拉选择树checked状态
	$("#clear").on("click",function(){
		$("#laborCompny").val("");
		$.fn.zTree.getZTreeObj("tree_laborCompny").checkAllNodes(false);
	});
	
});
// 省略小数后两位并消掉某位的0
function number(cellvalue, options, rowObject){
	return Math.round(parseFloat(cellvalue) * 100) / 100;
}
function doExcel(){
	var url = "${ctx}/admin/custTax/doInvoiceExcel";
	doExcelAll(url);
}
</script>
</html>
