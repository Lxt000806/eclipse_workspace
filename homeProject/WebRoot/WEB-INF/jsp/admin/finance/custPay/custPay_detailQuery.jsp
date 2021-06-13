<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户付款明细查询</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_custPay.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#rcvAct").val('');
	$.fn.zTree.getZTreeObj("tree_rcvAct").checkAllNodes(false);
	$("#custType").val('');
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/custPay/doDetailQueryExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custPay/goDetailQueryJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [
				{name: "documentno", index: "documentno", width: 80, label: "档案编号", sortable: true, align: "left"},
				{name: "code", index: "code", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
				{name: "address", index: "address", width: 100, label: "楼盘", sortable: true, align: "left"},
				{name: "regiondescr", index: "regiondescr", width: 60, label: "片区", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "客户状态", sortable: true, align: "left"},
				{name: "endcodedescr", index: "endcodedescr", width: 80, label: "结束代码", sortable: true, align: "left"},
				{name: "adddate", index: "adddate", width: 120, label: "登记日期", sortable: true, align: "left", formatter: formatTime},
				{name: "fkdate", index: "fkdate", width: 120, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
				{name: "setdate", index: "setdate", width: 120, label: "下定时间", sortable: true, align: "left", formatter: formatTime},
				{name: "rcvactdescr", index: "rcvactdescr", width: 100, label: "收款账号", sortable: true, align: "left"},
				{name: "posdescr", index: "posdescr", width: 144, label: "POS机", sortable: true, align: "left"},
				{name: "fkamount", index: "fkamount", width: 78, label: "付款金额", sortable: true, align: "right",sum:true},
				{name: "procedurefee", index: "procedurefee", width: 78, label: "手续费", sortable: true, align: "right"},
				{name: "actualamount", index: "actualamount", width: 78, label: "实际到账", sortable: true, align: "right",sum:true},
				{name: "payno", index: "payno", width: 90, label: "收款单号", sortable: true, align: "left"},
				{name: "paycheckoutdocumentno", index: "paycheckoutdocumentno", width: 129, label: "凭证号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "操作人员", sortable: true, align: "left"},
            ],
            gridComplete:function(){
				var needSum1 = $("#dataTable").getCol("fkamount",false,"sum");
				$("#dataTable").footerData("set",{"fkamount":myRound(needSum1,2)});
				var needSum2 = $("#dataTable").getCol("actualamount",false,"sum");
				$("#dataTable").footerData("set",{"actualamount":myRound(needSum2,2)});
			}
		});
});
</script>
<style type="text/css">

</style>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form" >
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"
					value="${custPay.expired }" /> <input type="hidden"
					name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>楼盘地址</label> <input type="text" id="address"
						name="address" style="width:160px;" value="${custPay.address }" />
					</li>
					<li>
					    <label>收款账号</label>
					    <house:xtdmMulit id="rcvAct" dictCode="" sql="select Code SQL_VALUE_KEY,Descr SQL_LABEL_KEY from tRcvAct where Expired='F' " ></house:xtdmMulit>
					</li>	
					<li><label>付款日期从</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="" />
					</li>
					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="" />
					</li>
					<li><label>最后修改时间从</label> <input type="text"
						id="lastUpdateFrom" name="lastUpdateFrom" class="i-date"
						style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="" />
					</li>
					<li><label>至</label> <input type="text" id="lastUpdateTo"
						name="lastUpdateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="" />
					</li>
					<li><label>登记日期从</label> <input type="text" id="addDateFrom"
						name="addDateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="" />
					</li>
					<li><label>至</label> <input type="text" id="addDateTo"
						name="addDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="" />
					</li>
					<li>
						<label>类型</label>
						<house:xtdm id="type" dictCode="CPTRANTYPE" value="${custPay.type}"></house:xtdm>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li>
						<label>是否记账</label>
						<house:xtdm id="isCheckOut" dictCode="YESNO" value="${custPay.isCheckOut}"></house:xtdm>
					</li>
					<li>
					    <label>记账单状态</label>
					    <house:xtdm id="payCheckoutStatus" dictCode="WHChkOutStatus"></house:xtdm>
					</li>
					<li class="search-group-shrink">
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
			<div class="btn-panel">
			 <div class="btn-group-sm">
				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


