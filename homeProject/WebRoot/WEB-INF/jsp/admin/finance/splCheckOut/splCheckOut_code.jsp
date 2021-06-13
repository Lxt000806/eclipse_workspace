<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>选择供应商结算单号</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript" defer>
/**初始化表格*/
$(function(){

	$("#splCode").openComponent_supplier({
		showValue:"${splCheckOut.splCode}",
		showLabel:"${splCheckOut.splCodeDescr}",
	});
	
	if("${splCheckOut.disabledEle}"){
		var arr="${splCheckOut.disabledEle}".split(",");
		$.each(arr,function(k,v){
			$("#"+v).prop("disabled","disabled");
		});
	}

	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/splCheckOut/goCodeJqGrid",
		postData:{no:"${splCheckOut.no}", splCode:"${splCheckOut.splCode}",status:"${splCheckOut.status}"},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: "Bootstrap",
		colModel : [
			{name : "no",index : "no",width : 80,label:"结算单号",sortable : true,align : "left"},
			{name : "splcode",index : "splcode",width : 100,label:"供应商",sortable : true,align : "left",hidden : true},
			{name : "spldescr",index : "spldescr",width : 180,label:"供应商",sortable : true,align : "left"},
			{name : "date",index : "date",width : 120,label:"结算日期",sortable : true,align : "left",formatter : formatTime},
			{name : "othercost",index : "othercost",width : 80,label:"其他费用",sortable : true,align : "right"},
			{name : "payamount",index : "payamount",width : 80,label:"应付金额",sortable : true,align : "right"},
			{name : "paidamount",index : "paidamount",width : 80,label:"已付款金额",sortable : true,align : "right"},
			{name : "nowamount",index : "nowamount",width : 80,label:"现付金额",sortable : true,align : "right"},
			{name : "preamount",index : "preamount",width : 80,label:"预付款支付",sortable : true,align : "right"},
			{name : "remark",index : "remark",width : 200,label:"备注",sortable : true,align : "left"},
			{name : "prepaybalance",index : "prepaybalance",width : 80,label:"预付金余额",sortable : true,align : "right",hidden : true},
		],
		ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid("getRowData", rowid);
			Global.Dialog.returnData = selectRow;
			Global.Dialog.closeDialog();
		}
	});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<ul class="ul-form">
					<li>
						<label>结算单号</label> 
						<input type="text" id="no" name="no" style="width:160px;" value="${splCheckOut.no}" />
					</li>
					<li>
						<label>供应商编号</label> 
						<input type="text" id="splCode" name="splCode" style="width:160px;"/>
					</li>
					<li>
						<label>销售单状态</label> 
						<house:xtdmMulit id="status" dictCode="SPLPAYSTATUS" selectedValue="${splCheckOut.status}" 
							unShowValue="${splCheckOut.delStatus}"></house:xtdmMulit>
					</li>
					<li>
						<!-- <input type="checkbox" id="expired_show" name="expired_show" value="${supplierPay.expired}" 
							onclick="hideExpired(this)" ${supplierPay.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">
							隐藏过期
						</label> -->
						<button type="button" class="btn  btn-sm btn-system" onclick="goto_query();">
							查询
						</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


