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
	.col-xs-6{
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
</style>
<script type="text/javascript" defer> 
// 将要返回的数据
var selectRows = [];
var m_umState = "${m_umState}";
var m_sType = "${m_sType}";
var whCode = "${whCode}";
$(function() {
	var gridOption = {
		sortable: true,
		height : $(document).height()-$("#content-list").offset().top - 70,
		rowNum : 10000000,
		colModel : [
    		{name : "whcode",index : "whcode",width : 100,label:"仓库编号",sortable : true,align : "left"},
    		{name : "whdescr",index : "whdescr",width : 200,label:"仓库名称",sortable : true,align : "left"},
			{name : "qtycal",index : "qtycal",width : 90,label:"现有数量",sortable : true,align : "right", sum:true},
		],
		gridComplete:function(){
			var sum = $("#dataTable").getCol("qtycal",false,"sum");
			totalQty = sum;
		},
	};

	switch (m_umState) {
	case "M":
		$("#itCode").openComponent_item({
			callBack:setItDescr,
			showValue:"${salesInvoiceDetail.itcode}",
			showLabel:"${salesInvoiceDetail.itDescr}",
			condition:{
				itemType1:"${itemType1}",
				disabledEle:"itemType1",
				isItemTran:1,
				isItemProcess:"1",
			}
		});
		gridOption.url = "${ctx}/admin/salesInvoice/goItemWHBalJqGrid";
		gridOption.postData = {itCode: "${salesInvoiceDetail.itcode}"};
		$("#markup").val("${salesInvoiceDetail.markup}");
		break;
	case "V":
		$("#itCode").openComponent_item({
			callBack:setItDescr,
			showValue:"${salesInvoiceDetail.itcode}",
			showLabel:"${salesInvoiceDetail.itDescr}",
			condition:{
				itemType1:"${itemType1}",
				disabledEle:"itemType1",
				isItemTran:1,
				isItemProcess:"1",
			}
		});

		$("#saveBtn").hide();
		$("#page_form input[style='text']").prop("readonly",true);
		$("#openComponent_item_itCode").next().prop("disabled",true);
		$("textarea").prop("readonly",true);
		gridOption.url = "${ctx}/admin/salesInvoice/goItemWHBalJqGrid";
		gridOption.postData = {itCode: "${salesInvoiceDetail.itcode}"};
		$("#markup").val("${salesInvoiceDetail.markup}");
		break;
	default:
		$("#itCode").openComponent_item({
			callBack:setItDescr,
			condition:{
				itemType1:"${itemType1}",
				disabledEle:"itemType1",
				isItemTran:1,
				isItemProcess:"1",
			}
		});
		break;
	}
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	$("#openComponent_item_itCode").prop("disabled",true);
	setQtyNow();

	// 控制成本查看
	if (1 == "${costRight}") {
		$("#bcost").parent().attr("hidden",false);
	}

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {// input状态样式图片
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			openComponent_item_itCode:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"
					}, 
				}  
			},
			unitPrice:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"
					}, 
				}  
			},
			qty:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"
					}, 
				}  
			},
			befLineAmount:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"
					}, 
				}  
			},
			markup:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"
					}, 
				}  
			},
			lineAmount:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"
					}, 
				}  
			},
		}
	});

	$("#unitPrice").on("change",function(){
		$("#page_form").data("bootstrapValidator").resetForm();
		var unitPrice = $("#unitPrice").val();
		var qty = $("#qty").val();
		var markup = $("#markup").val();
		if (("A" == m_umState || "M" == m_umState || "P" == m_umState) && "" != unitPrice){
			if (isNaN(unitPrice)) {
				art.dialog({
					content: "此框必须为数字，请重新输入",
					width: 200,
				});
				focusSelect("unitPrice");
				return;
			}
			if (eval(unitPrice) < 0) {
				art.dialog({
					content: "此框必须为正数，请重新输入",
					width: 200,
				});
				focusSelect("unitPrice");
			}
			if ("" != $("#openComponent_item_itCode").val() && "" != qty) {
				$("#befLineAmount").val(Math.round(parseFloat(unitPrice) * parseFloat(qty) * 100) / 100);
				if ("" != markup) {
					$("#lineAmount").val(Math.round($("#befLineAmount").val() * markup / 100));
				} else {
					$("#lineAmount").val($("#befLineAmount").val());
				}
			}
		}
		
	});

	$("#markup").on("change",function(){
		$("#page_form").data("bootstrapValidator").resetForm();
		var markup = $("#markup").val();
		var qty = $("#qty").val();
		if (("A" == m_umState || "M" == m_umState || "P" == m_umState) && "" != markup){
			if (isNaN(markup)) {
				art.dialog({
					content: "此框必须为数字，请重新输入",
					width: 200,
				});
				focusSelect("markup");
				return;
			}
			if (eval(markup) < 0) {
				art.dialog({
					content: "此框不能为负数,请重新输入",
					width: 200,
				});
				focusSelect("markup");
				return;
			}
			if ("" != $("#befLineAmount").val()) {
				$("#lineAmount").val(Math.round($("#befLineAmount").val() * markup / 100));
			}
		}
		
	});

	$("#qty").on("change",function(){
		$("#page_form").data("bootstrapValidator").resetForm();
		var qty = $("#qty").val();
		var itCode = $("#openComponent_item_itCode").val();
		var unitPrice = $("#unitPrice").val();
		var markup = $("#markup").val();

		if (("A" == m_umState || "M" == m_umState || "P" == m_umState) && "" != qty){
			if (isNaN(qty)) {
				art.dialog({
					content: "数量输入有错，请重新输入",
					width: 200,
				});
				focusSelect("qty");
				return;
			}
			if (eval(qty) < 0) {
				art.dialog({
					content: "此框必须为正数，请重新输入",
					width: 200,
				});
				focusSelect("qty");
			}
			if ("" != itCode && "S" == m_sType) {
				if ((totalQty - parseFloat(qty)) < 0) {
					art.dialog({
						content: "变动数量大于现存数量",
						width: 200,
					});
				}
			}
			if ("" != itCode && "" != unitPrice) {
				$("#befLineAmount").val(Math.round(parseFloat(unitPrice) * parseFloat(qty) * 100) / 100);
				if ("" != markup) {
					$("#lineAmount").val(Math.round(parseFloat($("#befLineAmount").val()) * parseFloat(markup) / 100));
				} else {
					$("#lineAmount").val($("#befLineAmount").val());
				}
			}
		}
	});

	$("#openComponent_item_itCode").on("change",function(){
		$("#itDescr").val("");
		$("#unitPrice").val("");
		$("#lineAmount").val("");
		$("#qty").val("");
		$("#remarks").val("");
		
		setQtyNow();
	});

	// 先删除窗口的关闭按钮，再去添加关闭事件
	var titleCloseEle = parent.$("div[aria-describedby=dialog_saveQuickly]").children(".ui-dialog-titlebar");
	$(titleCloseEle[0]).children("button").remove();

	var childBtn=$(titleCloseEle).children("button");
	$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
		+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
	$(titleCloseEle[0]).children("button").on("click", function(){
		doClose();
	});

});
// 获取当前材料数量
function setQtyNow(){
	var originalData = $("#page_form").jsonForm();
	$.ajax({
		url:"${ctx}/admin/salesInvoice/getQtyNow",
		type: "post",
		data: {itCode:originalData.itCode, whCode:"${whCode}"},
		dataType: "json",
		async: false,
		success:function(obj){
			$("#qtyNow").val(obj.data.QtyCal);
		},
	});
}
// 聚焦全选
function focusSelect(id){
	$("#"+id).focus();
}

function setItDescr(data){
	//当触发item的blur后，就返回不执行
	if (data.lastUpdate && data.lastupdate) return;
	// 重置所有表单
	$("#page_form").data("bootstrapValidator").resetForm();

	$("#itDescr").val(data.descr);
	$("#unitPrice").val(data.price);
	$("#bcost").val(data.avgcost);
	$("#marketprice").val(data.marketprice);

	$("#dataTable").jqGrid("setGridParam", {
		url: "${ctx}/admin/salesInvoice/goItemWHBalJqGrid",
    	postData: {itCode: data.code},
	}).trigger("reloadGrid");
}

function doSave(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()) return;

	if ("" == $.trim($("#bcost").val())) {
		$("#bcost").val("");
	}

	var datas = $("#page_form").jsonForm();
	if ("A" == "${m_umState}" || "P" == "${m_umState}") {
		var itCodeArr = "${itCodes}".split(",");
		for (var i = 0; i < itCodeArr.length; i++) {
			if(itCodeArr[i] == datas.itCode){
				art.dialog({
					content: "该产品已经存在,请勿重复插入",
					width: 200,
				});
				clearFormTable("page_form","dataTable");
				$("#markup").val(100);
				return;
			}
		}
		
		selectRows.push(datas);
		if ("P" == "${m_umState}") {
			clearFormTable("page_form","dataTable");
			$("#markup").val(100);
			return;
		}

		Global.Dialog.returnData = selectRows;
		closeWin();
	} else if ("M" == "${m_umState}") {
		selectRows.push(datas);
		Global.Dialog.returnData = selectRows;
		closeWin();
	}
}

function doClose(){
	if ("P" == "${m_umState}") {
		Global.Dialog.returnData = selectRows;
	}
		
	closeWin();
}
</script>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript" defer></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
		    		<button type="button" class="btn btn-system" id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="doClose()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" name="qtyNow" id="qtyNow">
					<input type="hidden" name="marketprice" id="marketprice">
					<ul class="ul-form">
						<div class="row">
							<div class="col-xs-6">
								<li class="form-validate">
									<label><span class="required">*</span>产品编号</label>
									<input type="text" id="itCode" name="itCode" style="width:160px;"/>
								</li>
								<li>
									<label>产品名称</label>
									<input type="text" id="itDescr" name="itDescr" style="width:160px;"
										value="${salesInvoiceDetail.itDescr}" readonly="readonly" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>单价</label>
									<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="${salesInvoiceDetail.unitPrice}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>数量</label>
									<input type="text" id="qty" name="qty" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="${salesInvoiceDetail.qty}"/>
								</li>
							</div>
							<div class="col-xs-6">
								<li hidden="true">
									<label>移动平均成本</label>
									<input type="text" id="bcost" name="bcost" style="width:160px;"
										value="${salesInvoiceDetail.bcost}" readonly="readonly" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>折扣前金额</label>
									<input type="text" id="befLineAmount" name="befLineAmount" style="width:160px;"
										value="${salesInvoiceDetail.befLineAmount}" readonly="readonly" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>折扣</label>
									<input type="text" id="markup" name="markup" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="100"/>
									<span>%</span>
								</li>
								<li class="form-validate">
									<label>总价</label>
									<input type="text" id="lineAmount" name="lineAmount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="${salesInvoiceDetail.lineAmount}" readonly="readonly" />
								</li>
							</div>
						</div>
						<div class="row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -40px;">备注</label>
								<textarea id="remarks" name="remarks" style="height: 60px;">${salesInvoiceDetail.remarks}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#whBalance_tabView" data-toggle="tab">仓库结存</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="whBalance_tabView"  class="tab-pane fade in active"> 
		         	<div class="pageContent">
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
			</div>	
		</div>
	</div>
</body>	

</html>
