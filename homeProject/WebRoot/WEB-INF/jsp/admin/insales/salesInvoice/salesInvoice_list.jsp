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
	<title>销售订单管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script src="${resourceRoot}/pub/component_saleCust.js?v=${v}" type="text/javascript" defer></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript" defer></script>
</head>  
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${salesInvoice.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>销售发票号</label> 
						<input type="text" id="no" name="no" style="width:160px;"/>
					</li>
					<li>
						<label>客户编号</label> 
						<input type="text" id="custCode" name="custCode" style="width:160px;"/>
					</li>
					<li>
						<label>销售人员</label> 
						<input type="text" id="saleMan" name="saleMan" style="width:160px;"/>
					</li>
					<li>
						<label>销售类型</label> 
						<house:xtdm id="type" dictCode="SALESINVTYPE" style="width:160px;"></house:xtdm>
					</li>
					<li>
						<label>销售日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>材料类型1</label> 
						<house:dict id="itemType1" dictCode="" 
							sql="select Code,Code+' '+Descr cd from tItemType1 where Expired != 'T'" 
							sqlValueKey="Code" sqlLableKey="cd"></house:dict>
					</li>
					<li>
						<label>销售单状态</label> 
						<house:xtdmMulit id="status" dictCode="SALESINVSTATUS" selectedValue="APLY,OPEN"></house:xtdmMulit>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${salesInvoice.expired}" 
							onclick="hideExpired(this)" ${salesInvoice.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
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
				<house:authorize authCode="SALESINVOICE_CUSTSAVE">
					<button type="button" class="btn btn-system" id="custSave">
						<span>新增客户</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALESINVOICE_SALESORDER">
					<button type="button" class="btn btn-system" id="salesOrder">
						<span>开销售单</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALESINVOICE_RETURN">
					<button type="button" class="btn btn-system" id="return">
						<span>退回</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALESINVOICE_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALESINVOICE_REVIEW">
					<button type="button" class="btn btn-system" id="review">
						<span>审核</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALESINVOICE_UNREVIEW">
					<button type="button" class="btn btn-system" id="unreview">
						<span>反审核</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALESINVOICE_PAY">
					<button type="button" class="btn btn-system" id="pay">
						<span>付款</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALESINVOICE_SENDGOODS">
					<button type="button" class="btn btn-system" id="sendGoods">
						<span>发货</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALESINVOICE_SENDGOODSPERMIT">
					<button type="button" class="btn btn-system" id="sendGoodsPermit">
						<span>发货授权</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALESINVOICE_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" id="detailView">
					<span>明细查询</span>
				</button>
				<button type="button" class="btn btn-system" id="print">
					<span>打印</span>
				</button>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
				<house:authorize authCode="SALESINVOICE_CHANGEDISCOUNT">
					<!-- 修改折扣 -->
					<input type="hidden" id="isEditDiscPercentage" value="1">
				</house:authorize>
				<house:authorize authCode="SALESINVOICE_SIMPLEORDER">
					<!-- 简单开单 -->
					<input type="hidden" id="chageStatus" value="1">
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
<script type="text/javascript" defer>/*defer 做到一边下载JS(还是只能同时下载两个JS)，一边解析HTML。*/
/**初始化表格*/
$(function(){
	var postData = $("#page_form").jsonForm();

	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/salesInvoice/goSalesInvoiceJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top - 98,
		sortable: true,
		styleUI: "Bootstrap",
		colModel : [
			{name : "no",index : "no",width : 80,label : "销售单号",sortable : true,align : "left"},
			{name : "status",index : "status",width : 80,label : "销售单状态",sortable : true,align : "left", hidden : true},
			{name : "statusdescr",index : "statusdescr",width : 80,label : "销售单状态",sortable : true,align : "left",count : true},
			{name : "date",index : "date",width : 80,label : "销售日期",sortable : true,align : "left",formatter : formatDate},
			{name : "itemtype1",index : "itemtype1",width : 80,label : "材料类型1",sortable : true,align : "left",hidden : true},
			{name : "itemtype1descr",index : "itemtype1descr",width : 80,label : "材料类型1",sortable : true,align : "left"},
			{name : "type",index : "type",width : 80,label : "类型",sortable : true,align : "left", hidden:true},
			{name : "typedescr",index : "typedescr",width : 80,label : "销售类型",sortable : true,align : "left"},
			{name : "getitemdate",index : "getitemdate",width : 80,label : "取货日期",sortable : true,align : "left",formatter : formatDate},
			{name : "whcode",index : "whcode",width : 80,label : "仓库编号",sortable : true,align : "left",hidden : true},
			{name : "warehouse",index : "warehouse",width : 120,label : "仓库名称",sortable : true,align : "left"},
			{name : "custcode",index : "custcode",width : 80,label : "客户编号",sortable : true,align : "left",hidden : true},
			{name : "custdescr",index : "custdescr",width : 80,label : "客户名称",sortable : true,align : "left"},
			{name : "address",index : "address",width : 120,label : "楼盘地址",sortable : true,align : "left"},
			{name : "mobile1",index : "mobile1",width : 80,label : "手机号码",sortable : true,align : "left",hidden : true},
			{name : "befamount",index : "befamount",width : 80,label : "折扣前金额",sortable : true,align : "right",sum : true},
			{name : "discpercentage",index : "discpercentage",width : 100,label : "折扣比率（%）",sortable : true,align : "right"},
			{name : "discamount",index : "discamount",width : 80,label : "折扣金额",sortable : true,align : "right",sum : true},
			{name : "otheramount",index : "otheramount",width : 80,label : "其他费用",sortable : true,align : "right"},
			{name : "amount",index : "amount",width : 80,label : "实际总价",sortable : true,align : "right",sum : true},
			{name : "firstamount",index : "firstamount",width : 80,label : "收/退订金",sortable : true,align : "right",sum : true},
			{name : "secondamount",index : "secondamount",width : 100,label : "收/退取货付款",sortable : true,align : "right",sum : true},
			{name : "remainamount",index : "remainamount",width : 100,label : "应收/退余款",sortable : true,align : "right",sum : true},
			{name : "salemandescr",index : "salemandescr",width : 80,label : "业务人员",sortable : true,align : "left"},
			{name : "softdesigndescr",index : "softdesigndescr",width : 80,label : "软装设计师",sortable : true,align : "left"},
			{name : "whcheckoutdocumentno",index : "whcheckoutdocumentno",width : 110,label : "出库记账凭证号",sortable : true,align : "left"},
			{name : "remarks",index : "remarks",width : 200,label : "备注",sortable : true,align : "left"},
			{name : "iscaldescr",index : "iscaldescr",width : 80,label : "提成计算标识",sortable : true,align : "left"},
			{name : "calno",index : "calno",width : 80,label : "提成单号",sortable : true,align : "left"},
			{name : "isauthorizeddescr",index : "isauthorizeddescr",width : 80,label : "发货授权",sortable : true,align : "left"},
			{name : "authorizerdescr",index : "authorizerdescr",width : 80,label : "发货授权人",sortable : true,align : "left"},
			{name : "lastupdate",index : "lastupdate",width : 120,label : "最后修改时间",sortable : true,align : "left", formatter : formatTime},
			{name : "lastupdatedby",index : "lastupdatedby",width : 90,label : "修改人",sortable : true,align : "left"},
			{name : "expired",index : "expired",width : 70,label : "过期标志",sortable : true,align : "left"},
			{name : "actionlog",index : "actionlog",width : 70,label : "修改操作",sortable : true,align : "left"}
        ],
        ondblClickRow: function(){
			view();
		},
	});

	$("#custCode").openComponent_saleCust();
	$("#saleMan").openComponent_employee();

	$("#clear").on("click",function(){
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	});

	$("#custSave").on("click",function(){
		Global.Dialog.showDialog("custSave",{
			title:"销售客户信息——增加",
			url:"${ctx}/admin/salesInvoice/goCustSave",
			height:572,
			width:718,
			returnFun: goto_query
		});
	});

	$("#salesOrder").on("click",function(){
		Global.Dialog.showDialog("salesOrder",{
			title:"销售单——增加",
			url:"${ctx}/admin/salesInvoice/goSalesOrder",
			postData:{
				isEditDiscPercentage:$("#isEditDiscPercentage").val()==1?1:0,
				chageStatus:$("#chageStatus").val()==1?1:0,
			},
			height:759,
			width:1390,
			returnFun: goto_query
		});
	});

	$("#return").on("click",function(){
		Global.Dialog.showDialog("reback",{
			title:"销售单——退回",
			url:"${ctx}/admin/salesInvoice/goReback",
			postData:{
				isEditDiscPercentage:$("#isEditDiscPercentage").val()==1?1:0,
				chageStatus:$("#chageStatus").val()==1?1:0,
			},
			height:759,
			width:1390,
			returnFun: goto_query
		});
	});

	$("#update").on("click",function(){
		var title = "销售单信息--编辑";
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
	  			content:"请选择一条记录",
	  		});
	  		return;
		}
		if ("APLY" != $.trim(ret.status)) {
			art.dialog({content:"只能对申请状态的销售单进行编辑操作。",});
			return;
		}
		if ("R" == ret.type) {
			title = "销售退回--编辑";
		}
		Global.Dialog.showDialog("update",{
			title:title,
			url:"${ctx}/admin/salesInvoice/goUpdate",
			postData:{
				no:ret.no,
				expired:"T",// 查找全部的数据
				isEditDiscPercentage:$("#isEditDiscPercentage").val()==1?1:0,
				chageStatus:$("#chageStatus").val()==1?1:0,
			},
			height:759,
			width:1390,
			returnFun: goto_query
		});
	});

	$("#review").on("click",function(){
		var title = "销售单信息--审核";
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
	  			content:"请选择一条记录",
	  		});
	  		return;
		}
		if ("APLY" != $.trim(ret.status)) {
			art.dialog({content:"只能对申请状态的销售单/退回单进行审核操作。",});
			return;
		}
		if ("R" == ret.type) {
			title = "销售退回--审核";
		}
		Global.Dialog.showDialog("review",{
			title:title,
			url:"${ctx}/admin/salesInvoice/goReview",
			postData:{
				no:ret.no,
				expired:"T",// 查找全部的数据
				isEditDiscPercentage:$("#isEditDiscPercentage").val()==1?1:0,
			},
			height:759,
			width:1390,
			returnFun: goto_query
		});
	});

	$("#unreview").on("click",function(){
		var title = "销售单信息--反审核";
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
	  			content:"请选择一条记录",
	  		});
	  		return;
		}
		if ("R" == ret.type) {
			art.dialog({content:"退回单不能进行反审核操作。",});
			return;
		}
		if ("OPEN" != $.trim(ret.status)) {
			art.dialog({content:"只能对审核状态的销售单进行反审核操作。",});
			return;
		}
		//已存在采购记录的不能反审核，已存在付款记录不能进行反审核。
		$.ajax({
			url:"${ctx}/admin/salesInvoice/getPurchaseCount",
			data:{no:ret.no,},
			type:"post",
			dataType: "text",
			// async: false,
			success: function(result){
				if (!result) {
					art.dialog({content:"该销售单已存在采购记录不能进行反审核操作。"});
					return;
				}
			}
		});
		if (parseFloat(ret.amount) != parseFloat(ret.remainamount)) {
			art.dialog({content:"该销售单已存在付款记录不能进行反审核操作。"});
			return;
		}
		Global.Dialog.showDialog("unreview",{
			title:title,
			url:"${ctx}/admin/salesInvoice/goUnreview",
			postData:{
				no:ret.no,
				expired:"T",// 查找全部的数据
				isEditDiscPercentage:$("#isEditDiscPercentage").val()==1?1:0,
			},
			height:759,
			width:1390,
			returnFun: goto_query
		});
	});

	$("#pay").on("click",function(){
		var title = "销售订单--付款";
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		if ("S" == ret.type && "OPEN" != $.trim(ret.status) && "APLY" != $.trim(ret.status)) {
			art.dialog({content:"只能对申请或审核状态的销售单进行付款操作。",});
			return;
		}
		if ("R" == ret.type && "CONFIRMED" != $.trim(ret.status)) {
			art.dialog({content:"只能对发货状态的回退单进行付款操作。",});
			return;
		}
		if ("R" == ret.type) {
			title = "销售订单--退款";
		}
		Global.Dialog.showDialog("pay",{
			title:title,
			url:"${ctx}/admin/salesInvoice/goPay",
			postData:{
				no:ret.no,
				expired:"T",// 查找全部的数据
				isEditDiscPercentage:$("#isEditDiscPercentage").val()==1?1:0,
				chageStatus:$("#chageStatus").val()==1?1:0,
			},
			height:759,
			width:1390,
			returnFun: goto_query
		});
	});

	$("#sendGoods").on("click",function(){
		var title = "销售单信息--发货";
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		if ("R" == ret.type) {
			art.dialog({content:"退回单不能进行发货操作。",});
			return;
		}
		if ("OPEN" != $.trim(ret.status)) {
			art.dialog({content:"只能对审核状态的销售单进行发货操作。",});
			return;
		}
		Global.Dialog.showDialog("sendGoods",{
			title:title,
			url:"${ctx}/admin/salesInvoice/goSendGoods",
			postData:{
				no:ret.no,
				expired:"T",// 查找全部的数据
				isEditDiscPercentage:$("#isEditDiscPercentage").val()==1?1:0,
			},
			height:759,
			width:1390,
			returnFun: goto_query
		});
	});

	$("#sendGoodsPermit").on("click",function(){
		var title = "发货授权--编辑";
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		if ("R" == ret.type) {
			art.dialog({content:"销售退回不需要做发货授权",});
			return;
		}
		if ("CONFIRMED" == $.trim(ret.status)) {
			art.dialog({content:"该销售单已发货，不需要做发货授权",});
			return;
		}
		Global.Dialog.showDialog("sendGoodsPermit",{
			title:title,
			url:"${ctx}/admin/salesInvoice/goSendGoodsPermit",
			postData:{
				no:ret.no,
				expired:"T",// 查找全部的数据
			},
			height:268,
			width:445,
			returnFun: goto_query
		});
	});

	$("#detailView").on("click",function(){
		Global.Dialog.showDialog("detailView",{
			title:"销售订单明细查询",
			url:"${ctx}/admin/salesInvoice/goDetailView",
			height:759,
			width:1190,
			returnFun: goto_query
		});
	});

	$("#print").on("click",function(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		Global.Dialog.showDialog("print",{
			title:"销售单打印",
			url:"${ctx}/admin/salesInvoice/goPrint",
			height:160,
			width:445,
			postData:{
				no:ret.no,
				expired:"T",// 查找全部的数据
			},
			returnFun: goto_query
		});
	});


});

function view(){
	var title = "销售单信息--查看";
	var ret=selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条记录",
		});
		return;
	}
	if ("R" == ret.type) {
		title = "销售订单--查看";
	}
	Global.Dialog.showDialog("view",{
		title:title,
		url:"${ctx}/admin/salesInvoice/goView",
		postData:{
			no:ret.no,
			expired:"T",// 查找全部的数据
		},
		height:759,
		width:1390,
		returnFun: goto_query
	});
}

//导出EXCEL
function doExcel(){
	doExcelAll("${ctx}/admin/salesInvoice/doExcel");
}

</script>
</body>
</html>
