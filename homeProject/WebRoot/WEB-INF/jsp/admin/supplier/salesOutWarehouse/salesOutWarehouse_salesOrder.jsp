<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript" defer></script>
<script src="${resourceRoot}/pub/component_saleCust.js?v=${v}" type="text/javascript" defer></script>
<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript" defer></script>
<script type="text/javascript" defer> 

var m_umState = "${salesInvoice.m_umState}";
$(function() {
	var postData = $("#dataForm").jsonForm();
	var gridOption_order = {
		url: "${ctx}/admin/salesInvoice/goDetailJqGrid",	
		postData: {no:"${salesInvoice.no}"},
		sortable: true,
		height : $(document).height()-$("#content-list").offset().top - 64,
		rowNum : 10000000,
		loadonce: true,
		colModel : [
			{name : "pk", index: "pk", width: 80, label : "pk", sortable: true, align: "left", hidden : true},
    		{name : "sino",index : "sino",width : 80,label :"销售发票号",sortable : true,align : "left", hidden : true},
    		{name : "itcode",index : "itcode",width : 80,label : "产品编号",sortable : true,align : "left"},
    		{name : "itemtype2descr",index : "itemtype2descr",width : 80,label : "材料类型2",sortable : true,align : "left"},
    		{name : "itdescr",index : "itdescr",width : 200,label : "产品名称",sortable : true,align : "left",count : true},
			{name : "qty",index : "qty",width : 80,label : "数量",sortable : true,align : "right"},
			{name : "saleqty",index : "saleqty",width : 80,label : "原数量",sortable : true,align : "right",hidden : true},
			{name : "uom",index : "uom",width : 60,label : "单位code",sortable : true,align : "left",hidden : true},
			{name : "unitdescr",index : "unitdescr",width : 60,label : "单位",sortable : true,align : "left"},
			{name : "bcost",index : "bcost",width : 100,label : "移动平均成本",sortable : true,align : "right",hidden : true},
			{name : "marketprice",index : "marketprice",width : 70,label : "市场价",sortable : true,align : "right"}, 
			{name : "unitprice",index : "unitprice",width : 60,label : "单价",sortable : true,align : "right"},
			{name : "beflineamount",index : "beflineamount",width : 80,label : "折扣前金额",sortable : true,align : "right",sum : true},
			{name : "markup",index : "markup",width : 60,label : "折扣",sortable : true,align : "right"},
			{name : "lineamount",index : "lineamount",width : 70,label : "总价",sortable : true,align : "right",sum : true},
    		{name : "remarks",index : "remarks",width : 220,label : "备注",sortable : true,align : "left"},
    		{name : "lastupdate",index : "lastupdate",width : 120,label : "最后修改时间",sortable : true, align : "left", formatter:formatTime, hidden : true},
			{name : "lastupdatedby",index : "lastupdatedby",width : 90,label : "修改人",sortable : true,align : "left", hidden : true},
			{name : "expired",index : "expired",width : 70,label : "过期标志",sortable : true,align : "left", hidden : true},
			{name : "actionlog",index : "actionlog",width : 70,label : "修改操作",sortable : true,align : "left", hidden : true},
		],
		ondblClickRow: function(){
			view();
		},
		gridComplete:function(){
			var lineAmount_sum = $("#dataTable").getCol("lineamount",false,"sum");
			$("#befAmount").val(lineAmount_sum);
			$("#discPercentage").trigger("change");
			disabledItemType1();
			disabledOldNo();
		},
		loadComplete: function(){
			original_data = $("#dataForm").serialize();
			original_table_data = Global.JqGrid.allToJson("dataTable").detailJson;
		},
	};
	
	Global.JqGrid.initJqGrid("dataTable",gridOption_order);

	$("#type").prop("disabled",true);
	$("#custCode").openComponent_saleCust({
		showValue:"${salesInvoice.custcode}",
		showLabel:"${salesInvoice.custdescr}",
		condition:{returnCount: '10', mobileHidden:'1'},
		callBack:validateRefresh_custCode
	});

	$("#whcode").openComponent_wareHouse({
		showValue:"${salesInvoice.whcode}",
		showLabel: "${salesInvoice.warehouse}",
		condition:{czybh: "${czybm.czybh}"},
	 	callBack:function(){
	 		validateRefresh('openComponent_wareHouse_whcode')
	 	}
		
	});
	
	 //初始化材料类型1
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
	Global.LinkSelect.setSelect({
		firstSelect:'itemType1',
		firstValue:"${salesInvoice.itemtype1}"
	});
	
	focusSelect("itemType1");

	$("#dataForm").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {// input状态样式图片
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			itemType1:{
				validators: {  
					notEmpty: {  
						message: "请选择材料类型1"
					}, 
				}  
			},
			status:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
			date:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
			getItemDate:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
			openComponent_saleCust_custCode:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
			custName:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
			openComponent_salesInvoice_oldNo:{
				validators: {  
					notEmpty: {  
						message: "请先选择原销售单"  
					}, 
				}  
			},
			befAmount:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
			discPercentage:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
			otherAmount:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
			amount:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
			openComponent_wareHouse_whcode:{  
				validators: {  
					 remote: {
				           message: '',
				           url: '${ctx}/admin/wareHouse/getWareHouse',
				           data: getValidateVal,  
				           delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
				    },
					notEmpty: {  
						message:"仓库不能为空"  
					}
				}  
			},
		},
		submitButtons : "input[type='submit']",
	});
	
	// 控制折扣比率权限
	if (0 == "${salesInvoice.isEditDiscPercentage}") {
		$("#discPercentage").prop("disabled",true);
		$("#secondCash").prop("disabled",true);
		$("#remainAmount").prop("disabled",true);
	}

	// 控制成本查看权限
	if (1 == "${czybm.costRight}") {
		jQuery("#dataTable").setGridParam().showCol("bcost").trigger("reloadGrid");
	}

	// 销售退回
	if ("R" == "${salesInvoice.type}") {
		$("#openComponent_saleCust_custCode").prop("readonly",true).next().prop("disabled",true);
		$("#custName").prop("disabled", true);
		$("#itemType1").prop("disabled",true);
		$("#oldNo").openComponent_salesInvoice({
			callBack:setItemType1,
			condition:{
				type:"S",
				status:"CONFIRMED",
			}
		});
		$("#openComponent_salesInvoice_oldNo").parent().parent().attr("hidden",false);

		jQuery("#dataTable").setGridParam().showCol("saleqty").trigger("reloadGrid");
		// 改变某一列的Label显示
		$("#dataTable").setLabel("qty","退回数量");

		$("#firstCash").prev().text("退订金");
		$("#secondCash").prev().text("退到货款");
		$("#remainAmount").prev().text("应退余款");
	}
	
	if ("A" != m_umState && "R" != m_umState) {
		$("#isCal").val("${salesInvoice.iscal}");
		
		if ("S" == "${salesInvoice.type}") {
			$("#befAmount").val("${salesInvoice.befamount}");
			$("#discAmount").val("${salesInvoice.discamount}");
			$("#otherAmount").val("${salesInvoice.otheramount}");
			$("#amount").val("${salesInvoice.amount}");
			$("#firstCash").val("${salesInvoice.firstcash}");
			$("#secondCash").val("${salesInvoice.secondcash}");
			$("#remainAmount").val("${salesInvoice.remainamount}");
		} else {
			// 退回在主界面被转换为负数
			$("#befAmount").val(-parseFloat("${salesInvoice.befamount}"));
			$("#discAmount").val(-parseFloat("${salesInvoice.discamount}"));
			$("#otherAmount").val(-parseFloat("${salesInvoice.otheramount}"));
			$("#amount").val(-parseFloat("${salesInvoice.amount}"));
			$("#firstCash").val(-parseFloat("${salesInvoice.firstcash}"));
			$("#secondCash").val(-parseFloat("${salesInvoice.secondcash}"));
			$("#remainAmount").val(-parseFloat("${salesInvoice.remainamount}"));
		}
		$("#discPercentage").val(Math.round(parseFloat("${salesInvoice.discpercentage}") * 100) / 100);

		if ("0" == "${salesInvoice.calondiscamt}") {
			$("#calOnDiscAmt").val(0);
		} else {
			$("#calOnDiscAmt").val(1);
		}
		if ("0" == "${salesInvoice.iscal}") {
			$("#isCal").val(0);
		} else {
			$("#isCal").val(1);
		}
		$("#oldNo").openComponent_salesInvoice({
			callBack:setItemType1,
			showValue:"${salesInvoice.oldno}",
			condition:{
				type:"S",
				status:"CONFIRMED",
			}
		});

		if ($("#amount").val() != $("#remainAmount").val() && "ADMIN" != "${czybm.jslx}") {
			$("#detailSave").hide();
			$("#saveQuickly").hide();
			$("#detailUpdate").hide();
			$("#detailDelete").hide();
			$("#batchImport").hide();
			disabledForm();
			$("$status").prop("disabled",false);
		}
	}

	if ("V" == m_umState) {
		disabledForm();
	}else{
		$("#detailSave").show();
		$("#saveQuickly").show();
		$("#detailUpdate").show();
		$("#detailDelete").show();
	}

	if ("C" == m_umState) {
		//SC销售出库审核
		$("#review").show();
		$("#cancel").show();
	}
	
	if ("A" == m_umState || "M" == m_umState || "R" == m_umState) {
		$("#saveBtn").show();	
	}

	// 是否允许修改状态
	if ("0" == "${salesInvoice.chageStatus}") {
		$("#status").prop("disabled",true);
	} else {
		$("#status").prop("disabled",false);
	}

	if ("1" == $("#calOnDiscAmt").val()) {
		$("#discAmount").prev().prepend("<span class='required'>*</span>");
		$("#dataForm").bootstrapValidator("addField", "discAmount", {
	       	validators: {  
	        	notEmpty: {  
	        		message: "请输入完整的信息",
	        	}
	        }  
	    });
	} else {
		$("#discPercentage").prev().prepend("<span class='required'>*</span>");
		$("#dataForm").bootstrapValidator("addField", "discPercentage", {
	       	validators: {  
	        	notEmpty: {  
	        		message: "请输入完整的信息",
	        	}
	        }  
	    });
	}

	var itemType1 = $("#itemType1").val();

	// 如果客户编号为空，就清除客户名称等信息
	$("#openComponent_saleCust_custCode").on("blur",function(){
		if ("" == $.trim($("#openComponent_saleCust_custCode").val())) {
			$("#custName").val("");
		}
	});

	// 折扣比率
	$("#discPercentage").on("change",function(){
		var discPercentage =  $("#discPercentage").val();
		if ("" != $.trim(discPercentage)){
			if (isNaN(discPercentage)) {
				art.dialog({
					content: "此框必须为数字，请重新输入",
					width: 200,
				});
				focusSelect("discPercentage");
				return;
			}
			if ("" == $("#discAmount").val()) {
				$("#discAmount").val("0");
			}
			$("#amount").val(
				Math.round(parseFloat($("#befAmount").val()) * parseFloat(discPercentage) / 100 - 
					parseFloat($("#discAmount").val()) + parseFloat($("#otherAmount").val()))
			);
			$("#amount").trigger("change");
		} 

	});

	// 折扣金额
	$("#discAmount").on("change",function(){
		var discAmount = $("#discAmount").val();
		var befAmount = $("#befAmount").val();
		if ("" == $.trim(discAmount)) {
			$("#discAmount").val("0");
		} else {
			if (isNaN(discAmount)) {
				art.dialog({
					content: "此框必须为数字，请重新输入",
					width: 200,
				});
				focusSelect("discAmount");
				return;
			}
			if (eval(discAmount) > eval(befAmount)) {
				art.dialog({
					content: "折扣金额不允许大于折扣前金额",
					width: 200,
				});
				focusSelect("discAmount");
			} else {
				$("#amount").val(
					Math.round(
						parseFloat(befAmount) * parseFloat($("#discPercentage").val()) / 100 
						- parseFloat(discAmount) + parseFloat($("#otherAmount").val())
					)
				);
				$("#amount").trigger("change");
			}
		}
	});

	// 其他费用
	$("#otherAmount").on("change",function(){
		var otherAmount =  $("#otherAmount").val();
		if ("" != $.trim(otherAmount)){
			if (isNaN(otherAmount)) {
				art.dialog({
					content: "此框必须为数字，请重新输入",
					width: 200,
				});
				focusSelect("otherAmount");
				return;
			} else {
				if ("" == $("#discAmount").val()) {
					$("#discAmount").val("0");
					// 赋值为0后重置校验
					$("#dataForm").data("bootstrapValidator")  
						.updateStatus("discAmount", "NOT_VALIDATED", null)
						.validateField("discAmount");
				}
				$("#amount").val(
					Math.round(parseFloat($("#befAmount").val()) * parseFloat($("#discPercentage").val()) / 100 - 
						parseFloat($("#discAmount").val()) + parseFloat($("#otherAmount").val()))
				);
				$("#amount").trigger("change");
			}
		} 
	});

	// 实际总价
	$("#amount").on("change",function(){
		var amount = $("#amount").val();
		var firstCash = $("#firstCash").val();
		var secondCash = $("#secondCash").val();
		var Famount,FFirstAmount,FSecondAmount;
		if ("" != $.trim(amount)) {
			Famount = parseFloat(amount);
			if ("" != $.trim(firstCash)) {
				FFirstAmount = parseFloat(firstCash);
			} else {
				FFirstAmount = 0;
			}
			if ("" != $.trim(secondCash)) {
				FSecondAmount = parseFloat(secondCash);
			} else {
				FSecondAmount = 0;
			}
			$("#remainAmount").val(Famount - FFirstAmount - FSecondAmount);
		}
	});

	// 定金
	$("#firstCash").on("change",function(){
		var firstCash = $("#firstCash").val();
		if ("" == $.trim(firstCash)) {
			$("#firstCash").val("0");
		} else {
			if (isNaN(firstCash)) {
				art.dialog({
					content: "此框必须为数字，请重新输入",
					width: 200,
				});
				focusSelect("firstCash");
				return;
			}
			$("#remainAmount").val(
				parseFloat($("#amount").val()) - parseFloat(firstCash) - parseFloat($("#secondCash").val())
			);
		}
	});

	// 取货付款
	$("#secondCash").on("change",function(){
		var secondCash = $("#secondCash").val();
		if ("" == $.trim(secondCash)) {
			$("#secondCash").val("0");
		} else {
			if (isNaN(secondCash)) {
				art.dialog({
					content: "此框必须为数字，请重新输入",
					width: 200,
				});
				focusSelect("secondCash");
				return;
			}
			$("#remainAmount").val(
				Math.round(
					(parseFloat($("#amount").val()) - parseFloat($("#firstCash").val()) - parseFloat(secondCash)) * 100
				) / 100
			);
		}
	});
	
	
	

	$("#detailSave").on("click",function(){
		if ("V" == m_umState) return;

		var itCodes = Global.JqGrid.allToJson("dataTable","itcode");
		var itCodeKeys = itCodes.keys;

		if ("A" == m_umState || ("M" == m_umState && "S" == "${salesInvoice.type}")) {
			var itemType1 = $("#itemType1").val();
			
			$("#dataForm").data("bootstrapValidator")  
				.updateStatus("itemType1", "NOT_VALIDATED", null)
				.validateField("itemType1");
			
			if("" == $("#itemType1").val()) {
				focusSelect("itemType1");
				return;
			}

			Global.Dialog.showDialog("detailSave",{
				title:"销售明细——增加",
				url:"${ctx}/admin/salesOutWarehouse/goDetailSave",
				postData:{
					itCodes:itCodeKeys,
					m_umState:"A",
					itemType1:itemType1,
					m_sType:"${salesInvoice.type}",
					costRight:"${czybm.costRight}",
				},
				width:723,
				height:629,
				returnFun : function(data){
					if(data){
						$.each(data,function(k,v){
							var itemInfo = getItemInfo(v.itCode);
							var json = {
								itcode:v.itCode,
								itemtype2descr:itemInfo.ItemType2Descr,
								itdescr:v.itDescr,
								qty:v.qty,
								unitdescr:itemInfo.UnitDescr,
								bcost:v.bcost,
								marketprice:v.marketprice,
								unitprice:v.unitPrice,
								beflineamount:v.befLineAmount,
								markup:v.markup,
								lineamount:v.lineAmount,
								remarks:v.remarks,
								lastupdate:new Date(),
								lastupdatedby:v.lastupdatedby,
								expired:"F",
								actionlog:"ADD",
						  	};
						  	Global.JqGrid.addRowData("dataTable",json);
						});
						disabledItemType1();
						var rowIds = $("#dataTable").jqGrid("getDataIDs");
						$("#dataTable").jqGrid("setSelection",rowIds[rowIds.length-1]);
						scrollToPosi(100000);
					}
				}
			});
		} else if ("R" == m_umState || ("M" == m_umState && "R" == "${salesInvoice.type}")) {
			itemType1 = $("#itemType1").val();
			$("#dataForm").data("bootstrapValidator")  
				.updateStatus("openComponent_salesInvoice_oldNo", "NOT_VALIDATED", null)
				.validateField("openComponent_salesInvoice_oldNo");
			if("" == $("#openComponent_salesInvoice_oldNo").val()) {
				focusSelect("openComponent_salesInvoice_oldNo");
				return;
			}

			Global.Dialog.showDialog("rebackDetailSave",{
				title:"销售退回明细——增加",
				url:"${ctx}/admin/salesOutWarehouse/goRebackDetailSave",
				postData:{
					sino:$.trim($("#openComponent_salesInvoice_oldNo").val()),
					itCodes:itCodeKeys,
					m_umState:"R",
					itemType1:itemType1,
					m_sType:"${salesInvoice.type}",
				},
				width:1081,
				height:556,
				returnFun : function(data){
					if(data){
						$.each(data,function(k,v){
							var itemInfo = getItemInfo(v.itcode);
							var json = {
								itcode:v.itcode,
								itemtype2descr:itemInfo.ItemType2Descr,
								itdescr:v.itemdescr,
								qty:v.qty,
								saleqty:v.qty,
								unitdescr:itemInfo.UnitDescr,
								bcost:v.bcost,
								marketprice:v.marketprice,
								unitprice:v.unitprice,
								beflineamount:v.beflineamount,
								markup:v.markup,
								lineamount:v.lineamount,
								remarks:v.remarks,
								lastupdate:new Date(),
								lastupdatedby:v.lastupdatedby,
								expired:"F",
								actionlog:"ADD",
						  	};
						  	Global.JqGrid.addRowData("dataTable",json);
						});
						disabledOldNo();
						var rowIds = $("#dataTable").jqGrid("getDataIDs");
						$("#dataTable").jqGrid("setSelection",rowIds[rowIds.length-1]);
						scrollToPosi(100000);
					}
				}
			});
		}
	});
	
	$("#saveQuickly").on("click",function(){
		if ("V" == m_umState) return;

		if ("R" == m_umState || ("M" == m_umState && "R" == "${salesInvoice.type}")) {
			// 触发被选元素指定事件
			$("#detailSave").triggerHandler("click");
		} else {
			var itemType1 = $("#itemType1").val();
			$("#dataForm").data("bootstrapValidator")  
				.updateStatus("itemType1", "NOT_VALIDATED", null)
				.validateField("itemType1");
			if("" == itemType1) {
				focusSelect("itemType1");
				return;
			}

			if ("0" == $("#calOnDiscAmt").val() && "" == $.trim($("#discPercentage").val())) {
				art.dialog({
					content: "非按折扣金额计算时,折扣比率不能为空",
					width: 200,
				});
				return;
			}

			var itCodes = Global.JqGrid.allToJson("dataTable","itcode");
			var itCodeKeys = itCodes.keys;

			Global.Dialog.showDialog("saveQuickly",{
				title:"销售明细——增加",
				url:"${ctx}/admin/salesOutWarehouse/goDetailSave",
				postData:{
					itCodes:itCodeKeys,
					m_umState:"P",
					itemType1:itemType1,
					m_sType:"${salesInvoice.type}",
					costRight:"${czybm.costRight}",
				},
				width:723,
				height:629,
				returnFun : function(data){
					if(data){
						$.each(data,function(k,v){
							var itemInfo = getItemInfo(v.itCode);
							var json = {
								itcode:v.itCode,
								itemtype2descr:itemInfo.ItemType2Descr,
								itdescr:v.itDescr,
								qty:v.qty,
								unitdescr:itemInfo.UnitDescr,
								bcost:v.bcost,
								marketprice:v.marketprice,
								unitprice:v.unitPrice,
								beflineamount:v.befLineAmount,
								markup:v.markup,
								lineamount:v.lineAmount,
								remarks:v.remarks,
								lastupdate:new Date(),
								lastupdatedby:v.lastupdatedby,
								expired:"F",
								actionlog:"ADD",
						  	};
						  	Global.JqGrid.addRowData("dataTable",json);
						});
						disabledItemType1();
						var rowIds = $("#dataTable").jqGrid("getDataIDs");
						$("#dataTable").jqGrid("setSelection",rowIds[rowIds.length-1]);
						scrollToPosi(100000);
					}
				}
			});
		}
	});

	$("#detailUpdate").on("click",function(){
		if ("V" == m_umState) return;

		var ret=selectDataTableRow();
		// 选择数据的id
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!ret){
			art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
			return false;
		}
		
		if ("0" == $("#calOnDiscAmt").val() && "" == $.trim($("#discPercentage").val())) {
			art.dialog({
				content: "非按折扣金额计算时,折扣比率不能为空",
				width: 200,
			});
			return;
		}
		
		var itemType1 = $("#itemType1").val();
		var itCodes = Global.JqGrid.allToJson("dataTable","itcode");
		var itCodeKeys = itCodes.keys;
		var index = itCodeKeys.indexOf(ret.itcode);
		if (index > -1) {
		    itCodeKeys.splice(index, 1);
		}

		Global.Dialog.showDialog("detailUpdate",{
			title:"销售明细——修改",
			url:"${ctx}/admin/salesOutWarehouse/goDetailUpdate",
			postData:{
				itCodes:itCodeKeys,
				m_umState:"M",
				itemType1:itemType1,
				m_sType:"${salesInvoice.type}",
				sino:ret.sino,
				itcode:ret.itcode,
				itDescr:ret.itdescr,
				qty:ret.qty,
				bcost:ret.bcost,
				marketPrice:ret.marketprice,
				unitPrice:ret.unitprice,
				befLineAmount:ret.beflineamount,
				markup:ret.markup,
				lineAmount:ret.lineamount,
				remarks:ret.remarks,
				costRight:"${czybm.costRight}",
			},
			width:723,
			height:629,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var itemInfo = getItemInfo(v.itCode);
						var json = {
							itcode:v.itCode,
							itemtype2descr:itemInfo.ItemType2Descr,
							itdescr:v.itDescr,
							qty:v.qty,
							unitdescr:itemInfo.UnitDescr,
							bcost:v.bcost,
							marketprice:v.marketprice,
							unitprice:v.unitPrice,
							beflineamount:v.befLineAmount,
							markup:v.markup,
							lineamount:v.lineAmount,
							remarks:v.remarks,
							lastupdate:new Date(),
							lastupdatedby:v.lastupdatedby,
							expired:"F",
							actionlog:"Edit",
					  	};
					  	Global.JqGrid.updRowData("dataTable",rowId,json);
					  	// 编辑数据之后无法触发gridComplete，所以直接将过程添加到编辑里面
					  	var needSum1 = $("#dataTable").getCol("lineamount",false,"sum");
						$("#befAmount").val(needSum1);
						$("#discPercentage").trigger("change");

					});
					if ("A" == m_umState) {
				  		disabledItemType1();
					} else {
					  	disabledOldNo();
					}
				}
			}
		});
	});

	$("#detailDelete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作",width: 220});
			return false;
		}
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				if ("A" == m_umState) {
			  		disabledItemType1();
				} else {
				  	disabledOldNo();
				}
				var rowIds = $("#dataTable").jqGrid("getDataIDs");
				$("#dataTable").jqGrid("setSelection",rowIds[0]);
			},
			cancel: function () {
				return true;
			}
		});
		
	});

	$("#review").on("click",function(){
	    m_umState="C";
		doSave();
	});

	$("#cancel").on("click",function(){
		m_umState="CB";
		doSave();
	});

	$("#unreview").on("click",function(){
		m_umState="B";
		doSave();
	});

});
// 聚焦
function focusSelect(id){
	$("#"+id).focus();
}
// 根据表格内是否有数据对材料类型1进行限制
function disabledItemType1(){
	// 对折扣前金额合计进行保留2位小数
	var beflineAmount_sum = $("#dataTable").getCol("beflineamount",false,"sum");
	$("#dataTable").footerData("set",{"beflineamount":myRound(beflineAmount_sum,2)}); 

	var ids = $("#dataTable").getDataIDs();
	if (ids.length != 0) {
		$("#itemType1").prop("disabled",true);
	} else if ("R" != m_umState) {
		$("#itemType0").prop("disabled",false);
	}
}

function disabledOldNo(){
	// 对折扣前金额合计进行保留2位小数
	var beflineAmount_sum = $("#dataTable").getCol("beflineamount",false,"sum");
	$("#dataTable").footerData("set",{"beflineamount":myRound(beflineAmount_sum,2)}); 

	var ids = $("#dataTable").getDataIDs();
	$("#openComponent_salesInvoice_oldNo").prop("readonly",false).next().prop("disabled",false);
	if (ids.length != 0) {
		$("#openComponent_salesInvoice_oldNo").prop("readonly",true).next().prop("disabled",true);
	}
}


function validateRefresh_custCode(data){
	$("#dataForm").data("bootstrapValidator")  
		.updateStatus("openComponent_saleCust_custCode", "NOT_VALIDATED", null)
		.updateStatus("custName", "NOT_VALIDATED", null)
		.validateField("openComponent_saleCust_custCode");
	if (!data.Desc1) {
		$("#custName").val(data.desc1);
	} else {
		$("#custName").val(data.Desc1);
	}
}


// 验证、选择原销售单后设置材料类型等信息
function setItemType1(data) {
	$("#dataForm").data("bootstrapValidator").resetForm();
	$("#dataForm").data("bootstrapValidator").validateField("openComponent_salesInvoice_oldNo");

	$("#itemType1").val($.trim(data.itemtype1));
	$("#custCode").openComponent_saleCust({
		showValue:data.custcode,
		showLabel:data.custname,
	});
	$("#custName").val(data.custname);
	
	$("#whcode").setComponent_wareHouse({
		showValue: data.whcode,
		showLabel: data.warehouse,
		condition:{czybh: "${czybm.czybh}"},
	});
}
// 根据材料编号补全材料信息
function getItemInfo(itCode) {
	var json;
	$.ajax({
		url:"${ctx}/admin/salesInvoice/getItemInfo",
		type: "post",
		data: {itCode:itCode},
		dataType: "json",
		async: false,
		success:function(obj){
			json = obj.data;
		},
	});
	return json;
}
// close动作
function doClose(){
	if ("V" == m_umState) {
		closeWin();
	}
	var newData = $("#dataForm").serialize();
	var param= Global.JqGrid.allToJson("dataTable");
	//去掉token
	var arr = original_data.split("&");
	arr.splice(0,1);
	original_data = arr.toString();
	arr = newData.split("&");
	arr.splice(0,1);
	newData = arr.toString();

	if (param.detailJson != original_table_data||newData != original_data) {
		art.dialog({
			content: "数据已变动,是否保存？",
			width: 200,
			okValue: "确定",
			ok: function () {
				doSave();
			},
			cancelValue: "取消",
			cancel: function () {
				closeWin();
			}
		});
	} else {
		closeWin();
	}
}

function view(){
	var ret=selectDataTableRow();
	if(!ret){
		art.dialog({content: "请选择一条记录进行查看操作",width: 220});
		return false;
	}
	
	Global.Dialog.showDialog("detailView",{
		title:"销售明细——查看",
		url:"${ctx}/admin/salesOutWarehouse/goDetailUpdate",
		postData:{
			itCodes:"",
			m_umState:"V",
			itemType1:"",
			m_sType:"${salesInvoice.type}",
			sino:ret.sino,
			itcode:ret.itcode,
			itDescr:ret.itdescr,
			qty:ret.qty,
			bcost:ret.bcost,
			unitPrice:ret.unitprice,
			befLineAmount:ret.beflineamount,
			markup:ret.markup,
			lineAmount:ret.lineamount,
			remarks:ret.remarks,
			costRight:"${czybm.costRight}",
		},
		height:629,
		width:723,
	});
}

/* 主页面保存方法 */
function doSave(){
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").data("bootstrapValidator").isValid()) return;

	var datas = $("#dataForm").jsonForm();
	var param= Global.JqGrid.allToJson("dataTable");

	if(param.datas.length == 0){
		art.dialog({content: "请输入销售单详细信息",width: 220});
		return;
	}

	datas.type = $("#type").val();

	if ("C" == m_umState) {
		if ("S" == "${salesInvoice.type}") {
			datas.status="OPEN";
		} else {
			datas.status="CONFIRMED";
		}
	} else if ("CB" == m_umState) {
		datas.status = "CANCEL";
	}else {
		datas.status = "APLY";
	}
	
	if("R"==m_umState){
		param.m_umState = "A";
	}else if ("R" == "${salesInvoice.type}"  && "C" == m_umState) {
		param.m_umState = "M";
	}else if("S" == "${salesInvoice.type}" && "C" == m_umState) {
		param.m_umState = "SC";
	}else{
		param.m_umState = m_umState;
	} 
	
	// 将datas（json）合并到param（json）中
	$.extend(param,datas);

	$.ajax({
		url:"${ctx}/admin/salesInvoice/doSalesOrder",
		type: "post",
		data: param,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
    	}
	});
}
</script>

</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
		    		<button type="button" class="btn btn-system" id="saveBtn" onclick="doSave()" style="display: none">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="review" style="display: none">
						<span>审核通过</span>
					</button>
					<button type="button" class="btn btn-system" id="cancel" style="display: none">
						<span>审核取消</span>
					</button>
					<button type="button" class="btn btn-system" id="unreview" style="display: none">
						<span>反审核</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)" >
						<span>关闭</span>
					</button>
					<label>销售类型</label> 
					<house:xtdm id="type" dictCode="SALESINVTYPE" style="width:160px;" value="${salesInvoice.type}"></house:xtdm>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${czybm.czybh}"/>
					<input type="hidden" name="calOnDiscAmt" id="calOnDiscAmt" value="1">
					<input type="hidden" name="calNo" id="calNo" value="${salesInvoice.calno}">
					<input type="hidden" name="isCal" id="isCal" value="0">
					<input type="hidden" name="saleDirt" id="saleDirt" value="${salesInvoice.saledirt}">
					<input type="hidden" name="expired" id="expired" value="${salesInvoice.expired}">
					<ul class="ul-form">
						<div class="row">
							<div class="col-xs-6">
								<li>
									<label><span class="required">*</span>销售发票号</label>
									<input type="text" id="no" name="no" style="width:160px;" placeholder="保存自动生成" value="${salesInvoice.no}" readonly="true" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>状态</label> 
									<house:xtdm id="status" dictCode="SALESINVSTATUS" style="width:160px;" value="${salesInvoice.status}"></house:xtdm>
								</li>
								
								<li class="form-validate">
									<label>材料类型1</label> <select id="itemType1" name="itemType1"></select>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>销售日期</label>
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${salesInvoice.date}' pattern='yyyy-MM-dd'/>" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>取货日期</label>
									<input type="text" id="getItemDate" name="getItemDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${salesInvoice.getitemdate}' pattern='yyyy-MM-dd'/>" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>仓库编号</label>
									<input type="text" id="whcode" name="whcode" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>客户名称</label>
									<input type="text" id="custName" name="custName" style="width:160px;" value="${salesInvoice.custdescr}" />
								</li>
				
								<li class="form-validate" hidden="true">
									<label><span class="required">*</span>原销售单</label>
									<input type="text" id="oldNo" name="oldNo" style="width:160px;"/>
								</li>
							</div>
							<div class="col-xs-6">
								<li class="form-validate">
									<label><span class="required">*</span>折扣前总价</label>
									<input type="text" id="befAmount" name="befAmount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="0" disabled="disabled" />
									<span>元</span>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>折扣比率</label>
									<input type="text" id="discPercentage" name="discPercentage" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\-\d]/g,'');"
										value="100" />
									<span>%</span>
								</li>
								<li class="form-validate">
									<label>折扣金额</label>
									<input type="text" id="discAmount" name="discAmount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="0" />
									<span>元</span>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>其他费用</label>
									<input type="text" id="otherAmount" name="otherAmount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="0" />
									<span>元</span>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>实际总价</label>
									<input type="text" id="amount" name="amount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="0" disabled="true" />
									<span>元</span>
								</li>
								<li hidden="true">
									<label>定金</label>
									<input type="text" id="firstCash" name="firstCash" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="0" disabled="true" />
									<span>元</span>
								</li>
								<li hidden="true">
									<label>取货付款</label>
									<input type="text" id="secondCash" name="secondCash" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="0" disabled="true" />
									<span>元</span>
								</li>
								<li hidden="true"> 
									<label>应收余款</label>
									<input type="text" id="remainAmount" name="remainAmount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="0" disabled="true" />
									<span>元</span>
								</li>
								<li style="max-height: 120px;">
									<label class="control-textarea" style="top: -40px;">备注</label>
									<textarea id="remarks" name="remarks" style="height: 60px;width: 160px;">${salesInvoice.remarks}</textarea>
								</li>
							</div>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#orderDetail_tabView" data-toggle="tab">销售单明细</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="orderDetail_tabView"  class="tab-pane fade in active"> 
		         	<div class="pageContent">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="detailSave" style="display: none"  >
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="saveQuickly" style="display: none" >
									<span>快速新增</span>
								</button>
								<button type="button" class="btn btn-system" id="detailUpdate " style="display: none">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="detailDelete" style="display: none">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="view" onclick="view()" >
									<span>查看</span>
								</button>
								<button type="button" class="btn btn-system" id="excel" onclick="doExcelNow('销售单明细')">
									<span>导出Excel</span>
								</button>
							</div>
						</div>
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
