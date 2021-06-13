<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>供应商付款管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script src="${resourceRoot}/pub/component_splCheckOut.js?v=${v}" type="text/javascript" defer></script>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript" defer></script>
</head>  
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${supplierPay.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>付款单号</label> 
						<input type="text" id="no" name="no" style="width:160px;"/>
					</li>
					<li>
						<label>凭证号</label> 
						<input type="text" id="documentNo" name="documentNo" style="width:160px;"/>
					</li>
					<li>
						<label>结算单号</label> 
						<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;"/>
					</li>
					<li>
						<label>申请日期从</label>
						<input type="text" id="appDateFrom" name="appDateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="appDateTo" name="appDateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>供应商编号</label> 
						<input type="text" id="splCode" name="splCode" style="width:160px;"/>
					</li>
					<li>
						<label>销售单状态</label> 
						<house:xtdmMulit id="status" dictCode="SPLPAYSTATUS" selectedValue="1,2,3"></house:xtdmMulit>
					</li>
					<li>
						<label>备注</label> 
						<input type="text" id="remarks" name="remarks" style="width:160px;"/>
					</li>
					<li>
						<!-- <input type="checkbox" id="expired_show" name="expired_show" value="${supplierPay.expired}" 
							onclick="hideExpired(this)" ${supplierPay.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label> -->
						<button type="button" class="btn  btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" id="clear" 
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SUPPLIERPAY_ADD">
					<button type="button" class="btn btn-system" id="add">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIERPAY_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIERPAY_REVIEW">
					<button type="button" class="btn btn-system" id="review">
						<span>审核</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIERPAY_UNREVIEW">
					<button type="button" class="btn btn-system" id="unreview">
						<span>反审核</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIERPAY_CASHIERSIGN">
					<button type="button" class="btn btn-system" id="cashierSign">
						<span>出纳签字</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIERPAY_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
<script type="text/javascript" defer>
$(function(){
	var postData = $("#page_form").jsonForm();

	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierPay/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top - 98,
		sortable: true,
		styleUI: "Bootstrap",
		colModel : [
			{name : "no",index : "no",width : 80,label : "付款单号",sortable : true,align : "left"},
			{name : "checkoutno",index : "checkoutno",width : 80,label : "结算单号",sortable : true,align : "left"},
			{name : "spldescr",index : "spldescr",width : 100,label : "供应商",sortable : true,align : "left"},
			{name : "suppltypedescr",index : "suppltypedescr",width : 80,label : "供应商类型",sortable : true,align : "left"},
			{name : "status",index : "status",width : 60,label : "状态",sortable : true,align : "left",hidden : true},
			{name : "statusdescr",index : "statusdescr",width : 60,label : "状态",sortable : true,align : "left"},
			{name : "paidamount",index : "paidamount",width : 80,label : "付款总金额",sortable : true,align : "right",sum : true},
			{name : "nowamount",index : "nowamount",width : 80,label : "现金支付",sortable : true,align : "right",sum : true},
			{name : "preamount",index : "preamount",width : 80,label : "预付金支付",sortable : true,align : "right",sum : true},
			{name : "documentno",index : "documentno",width : 90,label : "凭证号",sortable : true,align : "left"},
			{name : "appempdescr",index : "appempdescr",width : 80,label : "申请人",sortable : true,align : "left"},
			{name : "appdate",index : "appdate",width : 120,label : "申请日期",sortable : true,align : "left",formatter : formatTime},
			{name : "confirmempdescr",index : "confirmempdescr",width : 80,label : "审核人",sortable : true,align : "left"},
			{name : "confirmdate",index : "confirmdate",width : 120,label : "审核日期",sortable : true,align : "left",formatter : formatTime},
			{name : "wfprocinstno",index : "wfprocinstno",width : 80,label : "报销单号",sortable : true,align : "left",formatter: clickOpt},
			{name : "payempdescr",index : "payempdescr",width : 80,label : "付款人",sortable : true,align : "left"},
			{name : "paydate",index : "paydate",width : 120,label : "付款日期",sortable : true,align : "left",formatter : formatTime},
			{name : "remarks",index : "remarks",width : 220,label : "备注",sortable : true,align : "left"},
			{name : "lastupdate",index : "lastupdate",width : 120,label : "最后修改时间",sortable : true,align : "left",formatter : formatTime},
			{name : "lastupdatedby",index : "lastupdatedby",width : 80,label : "修改人",sortable : true,align : "left"},
			{name : "expired",index : "expired",width : 70,label : "过期标志",sortable : true,align : "left"},
			{name : "actionlog",index : "actionlog",width : 70,label : "修改操作",sortable : true,align : "left"},
        ],
        ondblClickRow: function(){
			view();
		},
		gridComplete:function(){
			var paidamount_sum = $("#dataTable").getCol("paidamount",false,"sum");
			var nowamount_sum = $("#dataTable").getCol("nowamount",false,"sum");
			var preamount_sum = $("#dataTable").getCol("preamount",false,"sum");
			$("#dataTable").footerData("set",{"paidamount":myRound(paidamount_sum,2)});
			$("#dataTable").footerData("set",{"nowamount":myRound(nowamount_sum,2)});
			$("#dataTable").footerData("set",{"preamount":myRound(preamount_sum,2)});
		},
	});
	
	function clickOpt(cellvalue, options, rowObject){
		if(rowObject==null){
	       	return "";
		}
		if(cellvalue == null){
			return "";
		}
		
		return "<a href=\"javascript:void(0)\" style=\"\" onclick=\"viewProc('"
	     			+rowObject.wfprocinstno+"')\">"+cellvalue+"</a>";//goViewByWfProcInstNo
	}


	$("#checkOutNo").openComponent_splCheckOut({
		condition:{
			status:"2",
			delStatus:"1,3",
			disabledEle:"status_NAME",
		},
	});
	$("#splCode").openComponent_supplier();

	$("#clear").on("click",function(){
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	});

	$("#add").on("click",function(){
		Global.Dialog.showDialog("add",{
			title:"供应商付款管理--新增",
			url:"${ctx}/admin/supplierPay/goAdd",
			postData:{m_umState:"A"},
			height:727,
			width:1097,
			returnFun: goto_query
		});
	});

	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
	  			content:"请选择一条记录",
	  		});
	  		return;
		}
		if ("1" != $.trim(ret.status)) {
			art.dialog({content:"付款单状态为"+ret.statusdescr+",不允许进行编辑",});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"供应商付款管理--编辑",
			url:"${ctx}/admin/supplierPay/goUpdate",
			postData:{
				m_umState:"M",
				no:ret.no,
			},
			height:727,
			width:1097,
			returnFun: goto_query
		});
	});

	$("#review").on("click",function(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
	  			content:"请选择一条记录",
	  		});
	  		return;
		}
		if ("1" != $.trim(ret.status)) {
			art.dialog({content:"付款单状态为"+ret.statusdescr+",不允许进行审核",});
			return;
		}
		Global.Dialog.showDialog("review",{
			title:"供应商付款管理--审核",
			url:"${ctx}/admin/supplierPay/goReview",
			postData:{
				m_umState:"C",
				no:ret.no,
			},
			height:727,
			width:1097,
			returnFun: goto_query
		});
	});

	$("#unreview").on("click",function(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
	  			content:"请选择一条记录",
	  		});
	  		return;
		}
		if ("2" != $.trim(ret.status)) {
			art.dialog({content:"付款单状态为"+ret.statusdescr+",不允许进行反审核",});
			return;
		}
		if ("" != ret.payempdescr) {
			art.dialog({content:"该付款单已经进行出纳签字操作，不允许反审核",});
			return;
		}
		Global.Dialog.showDialog("unreview",{
			title:"供应商付款管理--反审核",
			url:"${ctx}/admin/supplierPay/goUnreview",
			postData:{
				no:ret.no,
				m_umState:"B",
			},
			height:727,
			width:1097,
			returnFun: goto_query
		});
	});

	$("#cashierSign").on("click",function(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		if ("2" != $.trim(ret.status)) {
			art.dialog({content:"付款单状态为"+ret.statusdescr+",不允许进行出纳签字",});
			return;
		}
		if ("" != ret.payempdescr) {
			art.dialog({content:"该付款单已经进行出纳签字操作",});
			return;
		}
		Global.Dialog.showDialog("cashierSign",{
			title:"供应商付款管理--出纳签字",
			url:"${ctx}/admin/supplierPay/goCashierSign",
			postData:{
				no:ret.no,
				m_umState:"W",
			},
			height:727,
			width:1097,
			returnFun: goto_query
		});
	});

});

function view(){
	var ret=selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条记录",
		});
		return;
	}
	Global.Dialog.showDialog("view",{
		title:"供应商付款管理--查看",
		url:"${ctx}/admin/supplierPay/goView",
		postData:{
			no:ret.no,
			m_umState:"V",
		},
		height:727,
		width:1097,
		returnFun: goto_query
	});
}

function viewProc(wfProcInstNo){
	Global.Dialog.showDialog("viewProc",{
		title:"采购报销单--查看",
		url:"${ctx}/admin/wfProcInst/goViewByWfProcInstNo",
		postData:{
			wfProcInstNo: wfProcInstNo,
		},
		height:727,
		width:1280,
		returnFun: goto_query
	});
}

//导出EXCEL
function doExcel(){
	doExcelAll("${ctx}/admin/supplierPay/doExcel");
}

</script>
</body>
</html>
