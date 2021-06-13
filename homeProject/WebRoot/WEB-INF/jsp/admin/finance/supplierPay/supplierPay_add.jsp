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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
<style type="text/css">
	.SelectBG{
      	background-color:#FF7575!important;
    }  
    .row{
		margin: 0px;
	}
	.col-xs-4{
		padding: 0px;
	}
	.ui-jqgrid{
		width: auto !important;
	}
	.table-responsive {
		margin: 0px;
		width: auto !important;
	}
	.ui-jqgrid-hdiv {
		width: auto !important;
	}
	.ui-jqgrid-bdiv {
		width: auto !important;
	}
	.ui-jqgrid-sdiv {
		width: auto !important;
	}
	.panel-info {
		margin-bottom: 7px;
	}
	.li-textarea {
		max-height: 120px;
	}
	.control-textarea {
		top: -70px !important;
	}
	textarea {
		width: 160px !important;
		height: 95px;
	}
	.btn-group-sm {
		padding: 5px 0px !important;
	}
</style>
<script type="text/javascript" defer> 
var m_umState = "${supplierPay.m_umState}";
var dateNow = formatTime(new Date()); //现在时间带上时分
var czyNameChi, gridOption, returnBoolean;; //操作员中文姓名、表格属性、返回数据
var oldCheckOutNo = $("#checkOutNo").val(); //老结算单号
var czybh = "${sessionScope.USER_CONTEXT_KEY.czybh}";
$.ajax({
	url:"${ctx}/admin/employee/getEmployee",
	type:"post",
	data:{id:"${sessionScope.USER_CONTEXT_KEY.emnum}"},
	dataType:"json",
	async:false, //同步请求
	success:function (obj) {
		if("0" == obj.code){
			czyNameChi = obj.data.nameChi;
		}else{
			czyNameChi = obj.data;
		}
	}
});

$(function() {
	gridOption = {
		sortable: true,
		cellEdit: true,
		cellsubmit:"clientArray",
		height: $(document).height()-$("#content-list").offset().top - 70,
		colModel: [
    		{name:"no",index:"no",width:80,label:"no",sortable:true,align:"left",hidden:true},
    		{name:"pk",index:"pk",width:80,label:"pk",sortable:true,align:"left",hidden:true},
			{name:"puno",index:"puno",width:80,label:"采购单号",sortable:true,align:"left"},
			{name:"putype",index:"putype",width:80,label:"采购单",sortable:true,align:"left",hidden:true},
			{name:"putypedescr",index:"putypedescr",width:80,label:"采购单类型",sortable:true,align:"left"},
			{name:"delivtype",index:"delivtype",width:80,label:"配送地点",sortable:true,align:"left",hidden:true},
			{name:"pudate",index:"pudate",width:120,label:"采购日期",sortable:true,align:"left",formatter:formatTime},
			{name:"address",index:"address",width:180,label:"楼盘",sortable:true,align:"left"},
			{name:"documentno",index:"documentno",width:80,label:"档案号",sortable:true,align:"left"},
			{name:"amount",index:"amount",width:80,label:"本次付款",sortable:true,align:"right",editable:true,editrules:{number:true,required:true},sum:true},
			{name:"remainamount",index:"remainamount",width:80,label:"应付余款",sortable:true,align:"right",sum:true,formatter:"number", formatoptions:{decimalPlaces: 2}},
			{name:"puamount",index:"puamount",width:80,label:"材料余款",sortable:true,align:"right",sum:true},
			{name:"othercost",index:"othercost",width:80,label:"其他费用",sortable:true,align:"right",sum:true},
			{name:"othercostadj",index:"othercostadj",width:100,label:"其他费用调整",sortable:true,align:"right",sum:true},
			{name:"sumamount",index:"sumamount",width:80,label:"总金额",sortable:true,align:"right",sum:true},
			{name:"firstamount",index:"firstamount",width:80,label:"已付定金",sortable:true,align:"right",sum:true},
			{name:"secondamount",index:"secondamount",width:80,label:"已付到货款",sortable:true,align:"right",sum:true},
			{name:"lastupdate",index:"lastupdate",width:120,label:"最后修改时间",sortable:true,align:"left",formatter:formatTime,hidden:true},
			{name:"lastupdatedby",index:"lastupdatedby",width:90,label:"修改人",sortable:true,align:"left",hidden:true},
			{name:"expired",index:"expired",width:70,label:"过期标志",sortable:true,align:"left",hidden:true},
			{name:"actionlog",index:"actionlog",width:70,label:"修改操作",sortable:true,align:"left",hidden:true},
		],
		rowNum:100000,  
    	pager :'1',
		onCellSelect:function(rowid){// 单击之后选择整列
			// $("#dataTableDetail").jqGrid("setSelection",rowid);
			$("#dataTableDetail").setSelection(rowid);
		},
		gridComplete:function(){
			refreshSum("remainamount");
			refreshSum("puamount");
			refreshSum("othercost");
			refreshSum("othercostadj");
			refreshSum("sumamount");
			refreshSum("firstamount");
			refreshSum("secondamount");
		},
		beforeSaveCell : function(rowid,celname,value,iRow,iCol) {
			if ("amount" == celname) {
				var amount = value;
				var remainAmount = $("#dataTableDetail").getCell(rowid, iCol+1); //应付余款 //.jqGrid("getCell",rowid,iCol+1);
				var PUType = $("#dataTableDetail").getCell(rowid, iCol-6); //采购单类型
				if ("S" == PUType) {
					if (amount < 0) {
						art.dialog({content: "采购单付款请输入正数",width: 220});
						return $("#dataTableDetail").restoreCell(iRow,iCol);
					}
				} else {
					if (amount > 0) {
						art.dialog({content: "采购退回单付款请输入负数",width: 220});
						return $("#dataTableDetail").restoreCell(iRow,iCol);
					}
				}
				if (Math.abs(amount) > Math.abs(remainAmount)) {
					art.dialog({content: "本次付款金额不能超过应付余额",width: 220});
					return $("#dataTableDetail").restoreCell(iRow,iCol);
				}
			}
		}, 
		afterSaveCell:function(rowid,celname,val,iRow,iCol) {
			if("amount" == celname) {
				purchaseAfterPost();
			}
		},
	};
// 冻结列
	$("#dataTable").jqGrid("setFrozenColumns");

	$("#checkOutNo").openComponent_splCheckOut({
		callBack:setSplCheckOut,
		showValue:"${supplierPay.checkoutno}",
		condition:{
			status:"2",
			delStatus:"1,3",
			disabledEle:"status_NAME",
		},
	});

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			documentNo:{
				validators: {  
					notEmpty: {  
						message: "凭证号不能为空"
					}, 
				}  
			},
			openComponent_splCheckOut_checkOutNo:{
				validators: {  
					notEmpty: {  
						message: "结算单号不能为空"
					}, 
				}  
			},
		}
	});
	
	if ("A" != m_umState) {
		$("#preAmount").val("${supplierPay.preamount}");
		$("#nowAmount").val("${supplierPay.nowamount}");
		$("#paidAmount").val("${supplierPay.paidamount}");
		Global.JqGrid.initJqGrid("dataTableDetail",$.extend(gridOption, {
			url: "${ctx}/admin/supplierPay/goDetailJqGrid",
			postData: {no:"${supplierPay.no}"},
		}));
	}

	switch (m_umState) {
	case "A"://新增
		$("#review").remove();
		$("#cancel").remove();
		$("#unreview").remove();
		$("#cashierSign").remove();
		$("#statusShow").val("1");
		//初始化申请人
		$("#appEmp").val("${sessionScope.USER_CONTEXT_KEY.emnum}");
		$("#appMan").val(czyNameChi);
		//初始化申请日期
		$("#appDate").val(dateNow);
		//重新设置某一条col的属性
		$("#dataTableDetail").setColProp("amount",{editable:true});
		Global.JqGrid.initJqGrid("dataTableDetail",gridOption);
		break;
	case "M": //编辑
		$("#review").remove();
		$("#cancel").remove();
		$("#unreview").remove();
		$("#cashierSign").remove();
		$("#dataTableDetail").setColProp("amount",{editable:true});
		break;
	case "C": //审核
		$("#saveBtn").remove();
		$("#unreview").remove();
		$("#cashierSign").remove();
		$("#totalPayAmount").remove();
		$("#nowAmount").prop("readonly",true);
		$("#preAmount").prop("readonly", true);
		$("#checkOutNo").openComponent_splCheckOut({
			callBack:setSplCheckOut,
			showValue:"${supplierPay.checkoutno}",
			condition:{
				status:"2",
				delStatus:"1,3",
				disabledEle:"status_NAME",
			},
			readonly:true,
		});
		$("#documentNo").prop("readonly", true);
		//初始化审核人
		$("#confirmEmp").val("${sessionScope.USER_CONTEXT_KEY.emnum}");
		$("#confirmMan").val(czyNameChi);
		//初始化审核日期
		$("#confirmDate").val(dateNow);
		break;
	case "B": //反审核
		$("#saveBtn").remove();
		$("#review").remove();
		$("#cancel").remove();
		$("#cashierSign").remove();
		$("#documentNo").prop("readonly", true);
		$("#totalPayAmount").remove();
		$("#nowAmount").prop("readonly",true);
		$("#preAmount").prop("readonly", true);
		$("#checkOutNo").openComponent_splCheckOut({
			callBack:setSplCheckOut,
			showValue:"${supplierPay.checkoutno}",
			condition:{
				status:"2",
				delStatus:"1,3",
				disabledEle:"status_NAME",
			},
			readonly:true,
		});
		$("#remarks").prop("readonly", true);
		break;
	case "W": //出纳签字
		$("#saveBtn").remove();
		$("#review").remove();
		$("#cancel").remove();
		$("#unreview").remove();
		$("#documentNo").prop("readonly", true);
		$("#totalPayAmount").remove();
		$("#nowAmount").prop("readonly",true);
		$("#preAmount").prop("readonly", true);
		$("#checkOutNo").openComponent_splCheckOut({
			callBack:setSplCheckOut,
			showValue:"${supplierPay.checkoutno}",
			condition:{
				status:"2",
				delStatus:"1,3",
				disabledEle:"status_NAME",
			},
			readonly:true,
		});
		$("#remarks").prop("readonly", true);
		//初始化付款人
		$("#payEmp").val("${sessionScope.USER_CONTEXT_KEY.emnum}");
		$("#payMan").val(czyNameChi);
		//初始化付款日期
		$("#payDate").val(dateNow);
		$("#payDate").removeAttr("disabled");
		break;
	default:
		$("#saveBtn").remove();
		$("#review").remove();
		$("#cancel").remove();
		$("#unreview").remove();
		$("#cashierSign").remove();
		$("#totalPayAmount").remove();
		disabledForm();
		break;
	}
	// 禁用状态选择
	$("#statusShow").prop("disabled", true);

	// 原CS上SetSplInfo方法
	$.ajax({
		url:"${ctx}/admin/supplierPay/getSplInfo",
		type:"post",
		data:{checkOutNo: $("#checkOutNo").val()},
		dataType:"json",
		async:false, //同步请求
		success:function (map) {
			if (map.success) {
				$("#splDescr").val(map.Descr);
				$("#prepayBalance").val(map.PrepayBalance);
			}
		}
	});

	$("#preAmount").on("blur", function () {
		var preAmount = $(this).val();
		if ("" == preAmount) {
			$(this).val(0);
		}
	});

	$("#nowAmount").on("blur", function () {
		var nowAmount = $(this).val();
		if ("" == nowAmount) {
			$(this).val(0);
		}
	});

	$("#totalPayAmount").on("click",function(){
		var rets = $("#dataTableDetail").jqGrid("getRowData");
		var param = {"detailJson": JSON.stringify(rets)}; //var param = Global.JqGrid.allToJson("dataTableDetail");
		var remainamountSum = $("#dataTableDetail").getCol("remainamount",false,"sum").toFixed(2);
		Global.Dialog.showDialog("totalPayAmount",{
			title:"付款总金额",
			url:"${ctx}/admin/supplierPay/goTotalPayAmount",
			postData:{
				paidAmount:$("#paidAmount").val(),
				totalRemainAmount:remainamountSum,
			},
			width:500,
			height:300,
			returnFun:function(data){
				param.paidAmount = data;
				$.ajax({
					url:"${ctx}/admin/supplierPay/doSetPaidAmount",
					type:"post",
					data:param,
					dataType:"json",
					cache:false,
					async:false,
					success:function (map) {
						var list = map.list;
						var result = map.result;
						if (result.success) {
							// 将新的amount值赋给表格中的数据
							// var rowIDs = $("#dataTableDetail").jqGrid("getDataIDs");
							// 遍历list，根据puno对应数据，找到一条删一条
							for (var i = 0; i < rets.length; i++) {
								for (var j = 0; j < list.length; j++) {
									if (rets[i].puno == list[j].puno) {
										$("#dataTableDetail").setRowData(i+1, {amount: list[j].amount,});
										list.splice(j, 1);
										break;
									}
								}
							}
							refreshSum("amount");
							// amount的sum值改变后调用该方法
							purchaseAfterPost();
						} else {
							return false;
						}
						var amountSum = myRound($("#dataTableDetail").getCol("amount",false,"sum"), 2);
						var prepayBalance = myRound($("#prepayBalance").val(), 2);
						if (0 > amountSum) {
							$("#preAmount").val(0);
							$("#nowAmount").val(amountSum);
						} else {
							if (prepayBalance >= amountSum) {
								$("#preAmount").val(amountSum);
								$("#nowAmount").val(0);
							} else {
								$("#preAmount").val(prepayBalance);
								$("#nowAmount").val(amountSum - prepayBalance);
							}
						}
					}
				});
			}
		});
	});

});
// 根据供应商结算设置信息
function setSplCheckOut(data) {
	if (oldCheckOutNo == data.no) return;
	$("#splDescr").val(data.spldescr);
	$("#prepayBalance").val(data.prepaybalance);
	$("#preAmount").val(0);
	$("#nowAmount").val(0);
	$("#paidAmount").val(0);
	// 刷新详细表格
	$("#dataTableDetail").jqGrid("setGridParam",{
		page:1,
		url:"${ctx}/admin/supplierPay/goDetailJqGrid",
    	postData:{checkOutNo:data.no, lastUpdatedBy:czybh},
	}).trigger("reloadGrid");
	oldCheckOutNo = $("#openComponent_splCheckOut_checkOutNo").val();
	purchaseAfterPost();
}
//当amount的sum改变时使用方法
function purchaseAfterPost() {
	var amountSum = myRound($("#dataTableDetail").getCol("amount",false,"sum"), 2);
	var prepayBalance = myRound($("#prepayBalance").val(), 2);
	$("#dataTableDetail").footerData("set",{"amount":amountSum});
	$("#paidAmount").val(amountSum);
	if (amountSum < 0) {
		$("#preAmount").val(0);
		$("#nowAmount").val(amountSum);
	} else {
		if (prepayBalance >= amountSum) {
			$("#preAmount").val(amountSum);
			$("#nowAmount").val(0);
		} else {
			$("#preAmount").val(prepayBalance);
			$("#nowAmount").val(amountSum - prepayBalance);
		}
	}
}
//刷新sum并取两位小数
function refreshSum(colModel_name) {
	var colModel_name_sum = myRound($("#dataTableDetail").getCol(colModel_name,false,"sum"), 2);
	var sumObj = {}; //json先要用{}定义，再传参
	sumObj[colModel_name] = colModel_name_sum;//要用参数作为名字时，用[]
	$("#dataTableDetail").footerData("set",sumObj);
}

// (Boolean类型方法) 付款金额是否大于应付余额 保存时用到
function isPaidGreaterThenRemain(checkOutNo, param) {
	$.ajax({
		url:"${ctx}/admin/supplierPay/isPaidGreaterThenRemain",
		type:"post",
		data:{checkOutNo: checkOutNo, no: $("#no").val(), paidAmount: $("#paidAmount").val()},
		dataType:"json",
		async:false, //同步请求
		success:function (isGreaterThen) {
			returnBoolean = isGreaterThen;
		}
	});
}
// 付款金额是否大于应付余额 审核通过时用到 
function isPaidGreaterThenRemain2(checkOutNo) {
	$.ajax({
		url:"${ctx}/admin/supplierPay/isPaidGreaterThenRemain2",
		type:"post",
		data:{checkOutNo: checkOutNo, no: $("#no").val(), paidAmount: $("#paidAmount").val()},
		dataType:"json",
		async:false, //同步请求
		success:function (isGreaterThen) {
			returnBoolean = isGreaterThen;
		}
	});
}
// 保存按钮
function doSave() {
	var rets = $("#dataTableDetail").jqGrid("getRowData");
	var param = {"detailJson": JSON.stringify(rets)};
	var amounts = Global.JqGrid.allToJson("dataTableDetail","amount");
	var dataForm = $("#page_form").jsonForm();
	var paidAmount = parseFloat(dataForm.paidAmount);
	var nowAmount = parseFloat(dataForm.nowAmount);
	var preAmount = parseFloat(dataForm.preAmount);
	var prepayBalance = parseFloat(dataForm.prepayBalance); 
	var isAllZero = true;
	$("#page_form").bootstrapValidator("validate");
	if(!$("#page_form").data("bootstrapValidator").isValid()) return;
	if ("[]" == param.detailJson) {
		art.dialog({content: "请先增加要付款的相应采购单信息", width: 200,});
		return;
	}
	for (var i = 0; i < amounts.keys.length; i++) {
		if (!("0" == amounts.keys[i])) {
			isAllZero = false;
		}
	}
	if (isAllZero) {
		art.dialog({content: "本次付款都是0，无法保存", width: 200,});
		return;
	}
	if (myRound(paidAmount, 2) != myRound(nowAmount+preAmount, 2)) {
		art.dialog({content: "付款金额不等于现付金额+预付款支付", width: 200,});
		return;
	}
	if (preAmount > prepayBalance) {
		art.dialog({content: "预付款支付不能大于预付款余额", width: 200,});
		$("#preAmount").focus();
		return;
	}
	isPaidGreaterThenRemain(dataForm.checkOutNo);
	if (returnBoolean) {
		art.dialog({
			content: "本次付款总金额超过该结算单的应付余额。" +
			"<br/>可能存在未进行出纳签字的付款单，是否继续？",//<br/> 换行
			width: 200,
			okValue: "确定",
			ok: function () {
				saveAjax(param);
			},
			cancelValue: "取消",
			cancel: function () {
				return;
			},
		});
	} else {
		saveAjax(param);
	}
}

function saveAjax(param) {
	param.m_umState = m_umState;
	param.status = "1";
	param.expired = "F";
	Global.Form.submit("page_form", "${ctx}/admin/supplierPay/doSave", param, function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 500,
				beforeunload: function () {
					closeWin();
				}
			});
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			art.dialog({
				content: ret.msg,
				width: 200
			});
		}
	});
}

function doPass() {
	var rets = $("#dataTableDetail").jqGrid("getRowData");
	var param = {"detailJson": JSON.stringify(rets)};
	art.dialog({
		content: "确定要进行审核通过操作吗？",
		width: 200,
		okValue: "确定",
		ok: function () {
			isPaidGreaterThenRemain2($("#checkOutNo").val())
			if (returnBoolean) {
				art.dialog({content: "本次付款总金额超过该结算单的应付余额，可能存在未进行出纳签字的付款单", width: 200,});
				return;
			}
			param.m_umState = m_umState;
			param.status = "2";
			param.expired = "F";
			Global.Form.submit("page_form", "${ctx}/admin/supplierPay/doSave", param, function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 500,
						beforeunload: function () {
							closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
		},
		cancelValue: "取消",
		cancel: function () {
			return;
		},
	});
}

function doCancel() {
	var rets = $("#dataTableDetail").jqGrid("getRowData");
	var param = {"detailJson": JSON.stringify(rets)};
	art.dialog({
		content: "确定要进行审核取消操作吗？",
		width: 200,
		okValue: "确定",
		ok: function () {
			param.m_umState = m_umState;
			param.status = "3";
			param.expired = "F";
			Global.Form.submit("page_form", "${ctx}/admin/supplierPay/doSave", param, function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 500,
						beforeunload: function () {
							closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
		},
		cancelValue: "取消",
		cancel: function () {
			return;
		},
	});
}

function doUnreview() {
	var rets = $("#dataTableDetail").jqGrid("getRowData");
	var param = {"detailJson": JSON.stringify(rets)};
	art.dialog({
		content: "确定要进行反审核操作吗？",
		width: 200,
		okValue: "确定",
		ok: function () {
			param.m_umState = m_umState;
			param.status = "1";
			param.expired = "F";
			Global.Form.submit("page_form", "${ctx}/admin/supplierPay/doSave", param, function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 500,
						beforeunload: function () {
							closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
		},
		cancelValue: "取消",
		cancel: function () {
			return;
		},
	});
}

function doCashierSign() {
	var rets = $("#dataTableDetail").jqGrid("getRowData");
	var param = {"detailJson": JSON.stringify(rets)};
	art.dialog({
		content: "确定要进行出纳签字操作吗？",
		width: 200,
		okValue: "确定",
		ok: function () {
			param.m_umState = m_umState;
			param.status = "2";
			param.expired = "F";
			Global.Form.submit("page_form", "${ctx}/admin/supplierPay/doSave", param, function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 500,
						beforeunload: function () {
							closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
		},
		cancelValue: "取消",
		cancel: function () {
			return;
		},
	});
}

function doClose(){
	closeWin(false);
}
function view() {
	var ret = selectDataTableRow("dataTableDetail");
	if(ret){
		var tit;
		if(ret.type=="R"){
			if(ret.delivtype=="2"){
				tit="工地退回——查看"
			}else{
				tit="采购退回——查看"
			}
		}else{
			if(ret.delivtype=="2"){
				tit="采购到工地——查看"
			} else {
				tit="采购单——查看"
			}
		}
		if(ret.delivtype=="1"){
			url="${ctx}/admin/purchase/goViewNew?id=";
		}else{
			url="${ctx}/admin/purchase/goViewNew2?id=";
		}
		Global.Dialog.showDialog("PurchaseView",{
			title:tit,
			url: url + ret.puno,
			height:730,
			width:1250,
		});
	}else{
		art.dialog({
			content:"请选择一条记录"
		});		
	}
}
</script>
<script src="${resourceRoot}/pub/component_splCheckOut.js?v=${v}" type="text/javascript" defer></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
		    		<button type="button" class="btn btn-system" id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="review" onclick="doPass()">
						<span>审核通过</span>
					</button>
					<button type="button" class="btn btn-system" id="cancel" onclick="doCancel()">
						<span>审核取消</span>
					</button>
					<button type="button" class="btn btn-system" id="unreview" onclick="doUnreview()">
						<span>反审核</span>
					</button>
					<button type="button" class="btn btn-system" id="cashierSign" onclick="doCashierSign()">
						<span>出纳签字</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="doClose()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-xs-4">
								<li class="form-validate">
									<label><span class="required">*</span>付款单号</label>
									<input type="text" id="no" name="no" style="width:160px;" 
										placeholder="保存自动生成" value="${supplierPay.no}" readonly/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>结算单号</label>
									<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>预付款支付</label>
									<input type="text" id="preAmount" name="preAmount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
										value="0"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>现金支付</label>
									<input type="text" id="nowAmount" name="nowAmount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
										value="0"/>
								</li>
								<li class="form-validate">
									<label>付款总金额</label>
									<input type="text" id="paidAmount" name="paidAmount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
										value="0" readonly />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>凭证号</label>
									<input type="text" id="documentNo" name="documentNo" style="width:160px;" 
										value="${supplierPay.documentno}"/>
								</li>
							</div>
							<div class="col-xs-4">
								<li>
									<label>状态</label> 
									<house:xtdm id="statusShow" dictCode="SPLPAYSTATUS" value="${supplierPay.status}"></house:xtdm>
								</li>
								<li>
									<label>供应商</label>
									<input type="text" id="splDescr" name="splDescr" style="width:160px;" value="${supplierPay.spldescr}" 
										disabled/>
								</li>
								<li>
									<label>预付款余额</label>
									<input type="text" id="prepayBalance" name="prepayBalance" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
										value="0" disabled />
								</li>
								<li class="li-textarea">
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks">${supplierPay.remarks}</textarea>
								</li>
							</div>
							<div class="col-xs-4">
								<li>
									<label>申请日期</label>
									<input type="text" id="appDate" name="appDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${supplierPay.appdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
										disabled/>
								</li>
								<li>
									<label>申请人</label>
									<input type="text" id="appEmp" name="appEmp" style="width:77px;" value="${supplierPay.appemp}" 
									readonly />
									<input type="text" id="appMan" name="appMan" style="width:77px;" value="${supplierPay.appempdescr}" 
									readonly />
								</li>
								<li>
									<label>审核日期</label>
									<input type="text" id="confirmDate" name="confirmDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${supplierPay.confirmdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
										disabled/>
								</li>
								<li>
									<label>审核人</label>
									<input type="text" id="confirmEmp" name="confirmEmp" style="width:77px;" value="${supplierPay.confirmemp}" 
									readonly />
									<input type="text" id="confirmMan" name="confirmMan" style="width:77px;" value="${supplierPay.confirmempdescr}" 
									readonly />
								</li>
								<li>
									<label>出纳日期</label>
									<input type="text" id="payDate" name="payDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
										value="<fmt:formatDate value='${supplierPay.paydate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
										disabled/>
								</li>
								<li>
									<label>出纳人</label>
									<input type="text" id="payEmp" name="payEmp" style="width:77px;" value="${supplierPay.payemp}" 
									readonly />
									<input type="text" id="payMan" name="payMan" style="width:77px;" value="${supplierPay.payempdescr}" 
									readonly />
								</li>
							</div>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#main_tabView" data-toggle="tab">主项目</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="main_tabView"  class="tab-pane fade in active"> 
		         	<div class="pageContent">
						<div class="btn-panel">
							<div class="btn-group-sm">
								<button type="button" class="btn btn-system" id="totalPayAmount">
									<span>录入付款总金额</span>
								</button>
								<button type="button" class="btn btn-system" onclick="doExcelNow('供应商付款单明细', 'dataTableDetail');">
									<span>导出Excel</span>
								</button>
								<button type="button" class="btn btn-system" onclick="view();">
									<span>查看</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTableDetail"></table>
						</div>
					</div>
				</div>
			</div>	
		</div>
	</div>
</body>	

</html>
