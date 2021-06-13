<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加BaseItemChg</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
	.commiColor{
		background-color: yellow;
	}
	</style>
	<script type="text/javascript">
	var data_iRow;
	var data_iCol;
	var editRow;
	var maxDispSeq = 0;
	var reqpk = [];
	var iChangeAmount = 0;
	//干系人确认标识，已有项干系人不可修改标识
	var baseChgStakeholderConfirm = true, transActionStakeholderDisabled = false;
	function closeWin(isCallBack,isPrevent){
		if(!isPrevent )return;
		Global.Dialog.closeDialog(isCallBack);
	}
	
	function doClose(){
		if ("${operType}"!="V" && "${operType}"!="S" && iChangeAmount>1){
			art.dialog({
			 	content:"是否保存被更改的数据",
			 	lock: true,
			 	ok: function () {
			 	 	save("");
			 	 	closeWin(true,true);
				},
				cancel: function () {
					closeWin(false,true);
				}
			});
		}else{
			closeWin(false,true);
		}
	}
	
	function quickSave(){
	    var datas = $("#dataForm").jsonForm();
	    var ret = selectDataTableRow();
	    if(ret){
	       	datas.fixAreaPk = ret.fixareapk;
	    } 
		Global.Dialog.showDialog("quickSave",{
			title:"增减明细--快速新增",
			url:"${ctx}/admin/baseItemChg/goQuickSave",
			height:750,
			width:1380,
			resizable:false, 
			postData:datas,
			returnFun: function(data){
			  	if(data.length){
		  		    $.each(data,function(k,v){
		  		    	maxDispSeq++;
		  				v.lastupdate = getNowFormatDate();
		  				v.lastupdatedby = "${baseItemChg.lastUpdatedBy}";
		  				v.actionlog = "ADD";
		  				v.dispseq = maxDispSeq;
		  				v.preqty = 0;
		  				Global.JqGrid.addRowData("dataTable",v);
		  			});
		  		  	changeAmount();
		  	   		refreshDataTable();
				}
			}
		});
	}
	
	function refreshDataTable(){
		var mydata = JSON.stringify($("#dataTable").jqGrid("getRowData"));
      	$("#dataTable").jqGrid("setGridParam", {
        	url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
        	postData: {"params": mydata, "orderBy": "fixareapk"},
        	page: 1
      	}).trigger("reloadGrid");
	}
	
	function doSave(operStatus){
		var datas = $("#dataForm").jsonForm();
		datas.detailJson = JSON.stringify($("#dataTable").jqGrid("getRowData"));
		datas.chgStakeholderList = Global.JqGrid.allToJson("baseChgStakeholderDataTable").detailJson;
		$.ajax({
			url:"${ctx}/admin/baseItemChg/doSave?operStatus="+operStatus,
			type: "post",
			data:datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    	},
	     	success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin(true,true);
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
	
	function save(operStatus){
		var manageFee = parseInt($("#manageFee").val());
		var manageFeePer = parseInt($("#manageFeePer").val());
		var afterDiscPer = $("#afterDiscPer").val();
		if($("#isAddAllInfo").val()==""){
			art.dialog({
				content: "常规变更不允许为空"
			});
			return;
		} 
		if (baseChgStakeholderConfirm) {
			if ("" != $("#baseChgStakeholder").val() && "1" == $("#isAddAllInfo").val()) {
				art.dialog({
					content: "常规变更增减单，不能设置干系人！"
				});
				return;
			} else if ("" == $("#baseChgStakeholder").val() && "0" == $("#isAddAllInfo").val()) {
				art.dialog({
					content: "非常规变更增减单，请设置干系人！"
				});
				return;
			}
		}
		if($("#dataTable").jqGrid("getGridParam","records")==0 && $("#m_umState").val()=="A"){
			art.dialog({
				content: "新增时不允许增减明细为空！"
			});
			return;
		}
		if(($("#m_umState").val()=="A" || "${operType}"=="J") && $("#status").val()=="3"){
			art.dialog({
				content: "不能新增取消状态的增减项！"
			});
			return;
		}
		if ("${operType}"=="J" && !isSetMinus()){//套餐内减项
			art.dialog({
				content: "明细数据只能包含套餐内减项！"
			});
			return;
		}
		if (!isSetMinus() && !${haveBaseChgRight}){
			art.dialog({
				content: "该增减单含有非套餐内减项，您没有权限进行编辑操作！"
			});
			return;
		}
		if (afterDiscPer > 1){//套餐内减项
			art.dialog({
				content: "变动后折扣不能大于100%！"
			});
			return;
		}
		if(operStatus==""){
			doSave(operStatus);
		}else{
			var msg = "";
			if (operStatus=="2"){
				msg = "是否要审核通过该增减单？"
			}else{
				msg = "是否要审核取消该增减单？"
			}
			art.dialog({
	 			content: msg,
	 			lock: true,
				width: 260,
	 			height: 100,
	 			ok: function () {
	 				doSave(operStatus);
				},
				cancel: function () {
					return true;
				}
			});
		}
	}
	
	function changeAmount(){
		iChangeAmount = iChangeAmount + 1;
		if ("${custType.type}"=="1"){
			var dBefAmount = myRound($("#dataTable").getCol("lineamount",false,"sum"));
		   	var dDiscAmount = parseFloat($("#discAmount").val());
		   	if (dDiscAmount){
		   		if (dBefAmount >= 0){
		   			$("#amount").val(dBefAmount - dDiscAmount);
		   		} else {
		   			$("#amount").val(dBefAmount + dDiscAmount);
		   		}
		   	} else{
		   		$("#amount").val(dBefAmount);
		   	}
		   	$("#befAmount").val(dBefAmount);
		   	
		   	var dZhFee=0;
		   	var gridDatas = $("#dataTable").jqGrid("getRowData");
			$.each(gridDatas,function(k,v){
				if (v.fixareadescr=="综合项目"){
					dZhFee = dZhFee + parseFloat(v.lineamount);
				}
			});
			$("#zhFee").val(dZhFee);
		}else{
			changeAmountTc();
		}
		calculateTax();
	}
	
	function changeAmountTc(){
		var dManageFee_BasePer = parseFloat("${custType.manageFeeBasePer}");
		var dChgManageFeePer = parseFloat("${custType.chgManageFeePer}");
		var dLongFeePer = parseFloat("${custType.longFeePer}");
		var dBefAmount = myRound($("#dataTable").getCol("lineamount",false,"sum"));
	   	var dDiscAmount = parseFloat($("#discAmount").val());
	   	if (dDiscAmount){
	   		if (dBefAmount >= 0){
	   			$("#amount").val(dBefAmount - dDiscAmount);
	   		} else {
	   			$("#amount").val(dBefAmount + dDiscAmount);
	   		}
	   	} else{
	   		$("#amount").val(dBefAmount);
	   	}
	   	$("#befAmount").val(dBefAmount);
	   	var dSetMinus=0,dCalManageFee=0,dLongFee=0,dManageFee=0,dNoManageFee=0,dZhFee=0;
	   	var arrLongFee = "${longFeeCode}".split(",");
	   	var gridDatas = $("#dataTable").jqGrid("getRowData");
		$.each(gridDatas,function(k,v){
			if (v.category=="4"){
				dSetMinus = dSetMinus + parseFloat(v.lineamount);
			}
			if (v.lineamount > 0){
				if (v.iscalmangefee=="1"){
					dCalManageFee = dCalManageFee + parseFloat(v.lineamount);
				}else{
					dNoManageFee = dNoManageFee + parseFloat(v.lineamount);
				}
				if (arrLongFee && arrLongFee.indexOf(v.baseitemcode)>0){
					dLongFee = dLongFee + parseFloat(v.lineamount);
				}
			}
			if (v.fixareadescr=="综合项目"){
				dZhFee = dZhFee + parseFloat(v.lineamount);
			}
		});
		
		if ("${operType}"!="V" && iChangeAmount>=2){
		   dManageFee = (dCalManageFee + dLongFee * dLongFeePer) * dManageFee_BasePer * dChgManageFeePer;
		   $("#manageFee").val(myRound(dManageFee));
		}   
		$("#setMinus").val(dSetMinus);
		$("#noManageFee").val(myRound(dNoManageFee));
		$("#zhFee").val(dZhFee);
	}
	;
	function getReqPk() {
		return reqpk;
	}
	
	function goTransAction(){
		if($("#isAddAllInfo").val()==""){
			art.dialog({
				content: "常规变更不允许为空"
			});
			return;
		}
		var gridDatas = $("#dataTable").jqGrid("getRowData");
		reqpk = [];
		$.each(gridDatas,function(k,v){
			if(v.reqpk && v.reqpk!="0") reqpk.push(v.reqpk);
		});
		var empCodesDataTable = Global.JqGrid.allToJson("baseChgStakeholderDataTable", "empcode");
		Global.Dialog.showDialog("baseItemChg_transAction",{
			title:"材料增减明细--已有项变动",
			url:"${ctx}/admin/baseItemChg/goTransAction?custCode=${baseItemChg.custCode}&m_umState=M",
			postData: {empCode: empCodesDataTable.fieldJson},
			height: 600,
			width:1000,
			resizable:false,
			returnFun: function(data){
			  	if(data.selectRows.length){
		  	    	$.each(data.selectRows,function(k,v){
		  	    		maxDispSeq++;
		  				v.lastupdate = getNowFormatDate();
		  				v.lastupdatedby = "${baseItemChg.lastUpdatedBy}";
		  				v.actionlog = "ADD";
		  				v.dispseq = maxDispSeq;
		  				v.algorithm = v.basealgorithm;
		  				v.algorithmdescr = v.basealgorithmdescr;
		  				Global.JqGrid.addRowData("dataTable",v);
		  			});
		  	    	changeAmount();
		  	   		refreshDataTable();
				}
				setStakeholder(data);
			}
		});
	}
	/*
	如果未设置，需求单有对应增减干系人，须带出增减干系人，需求单无增减干系人，添加后不能再设置干系人。
	add by zb on 20191225
	*/
	function setStakeholder(data) {
		if (!baseChgStakeholderConfirm) return;
		var stakeholderJson = {};
		if (data.reqEmpCodesArray && data.reqEmpNamesArray) {
			$("#baseChgStakeholderDataTable").clearGridData();
			$.each(data.reqEmpCodesArray, function(i,val){
				stakeholderJson = {
					empcode: val,
					empname: data.reqEmpNamesArray[i],
					role: "01",
					lastupdatedby: "${sessionScope.USER_CONTEXT_KEY.czybh}",
					lastupdate: new Date(),
					actionlog: "ADD",
					expired: "F"
				};
				Global.JqGrid.addRowData("baseChgStakeholderDataTable", stakeholderJson);
			});
		}
		if (data.isaddallinfo) $("#isAddAllInfo").val(data.isaddallinfo);
	}
	/*
	判断是否锁定干系人
	add by zb on 20191225
	*/
	/*function judgeStakeholderIsDisabled() {
		if (!baseChgStakeholderConfirm) return;
		var reqPKDataTable = Global.JqGrid.allToJson("dataTable", "reqpk");
		if (0 == reqPKDataTable.datas.length) {//当主项目无明细时，重置非独立销售和干系人
			$("#isAddAllInfo").val("1");
			transActionStakeholderDisabled = false;
			changeStakeholderDisabled();
			return;
		}
		if ("" != reqPKDataTable.fieldJson) {//需求单不为空
			$("#baseChgStakeholderBtn").prop("disabled", true);
			$("#baseChgStakeholderRequired").prop("hidden", true);
			transActionStakeholderDisabled = true;
		} else {
			transActionStakeholderDisabled = false;
			changeStakeholderDisabled();
		}
	}*/

	function del(){
		var rowId = $("#dataTable").jqGrid("getGridParam", "selrow");
		var ret = selectDataTableRow();
		var sMsg="确定删除该记录吗？";
		if(ret.ismainitem=="1"&&ret.baseIitemsetno!=""){
    		sMsg="删除套餐包主项目的同时将会删除该相关套餐包项目,确定删除该记录吗？";
    	}
	    if (rowId) {
	    	art.dialog({
			 	content:sMsg,
			 	lock: true,
			 	ok: function () {
			 	  	 if(ret.ismainitem=="1"&&ret.baseitemsetno!=""){
	    				var rowData=$("#dataTable").jqGrid("getRowData");
				  		$.each(rowData,function(k,v){
				  			if(v.baseitemsetno==ret.baseitemsetno){
				  				$('#dataTable').delRowData(k+1);
				  			}		
				  		});
	    			 }else{
	    			 	$('#dataTable').delRowData(rowId);
	    			 }
	    			 changeAmount();
		  	   		 refreshDataTable();
					 setTimeout(function(){moveToNext(rowId,rowNum);$("#delBtn").focus();},100);
				},
				cancel: function () {
					return true;
				}
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function goEdit(operType){
		var ret = selectDataTableRow("dataTable");
		var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');			
	    if (ret) {
	    	var datas = {
		    	operType: operType,
	    		custCode: "${baseItemChg.custCode}",
	    		pk: ret.pk,
	    		fixAreaPk: ret.fixareapk,
	    		fixAreaDescr: ret.fixareadescr,
	    		baseItemCode: ret.baseitemcode,
	    		baseItemDescr: ret.baseitemdescr,
	    		isCalMangeFee: ret.iscalmangefee,
	    		category: ret.category,
	    		qty: ret.qty,
	    		uom: ret.uom,
	    		cost: ret.cost,
	    		unitPrice: ret.unitprice,
	    		material: ret.material,
	    		lineAmount: ret.lineamount,
	    		remarks: ret.remarks,
	    		reqPk: ret.reqpk,
	    		custTypeType: "${baseItemChg.custTypeType}",
	    		custType: "${baseItemChg.custType}",
	    		canUseComBaseItem: "${baseItemChg.canUseComBaseItem}",
	    		prePlanAreaDescr:ret.preplanareadescr,
	    		prePlanAreaPK:ret.preplanareapk,
	    		algorithm:ret.algorithm,
	    	};
	    	var str = operType=="V"?"查看":"编辑";
		    Global.Dialog.showDialog("baseItemChg_detailEdit",{
				title:"增减明细--"+str,
				url:"${ctx}/admin/baseItemChg/goBaseItemChg_detailEdit",
				postData:datas,
				height:600,
				width:1000,
				resizable:false, 
				returnFun: function(data){
				  	if(data.fixareapk) {
				   		$("#dataTable").setRowData(rowId,data);
				   		changeAmount();
			  	   		refreshDataTable();
				  	}
				}
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	//导入excel
	function goImport(){
	   	Global.Dialog.showDialog("baseItemChg_importExcel",{
			title:"增减明细--从excel导入",
			url:"${ctx}/admin/baseItemChg/goImport?custCode=${baseItemChg.custCode}&canUseComBaseItem=${baseItemChg.canUseComBaseItem}",
			height:600,
			width:1000,
			returnFun: function(data){
		  		$.each(data,function(k,v){
		  			maxDispSeq++;
		  			v.iscommi = "1";
		  			v.lastupdate = getNowFormatDate();
		  			v.lastupdatedby = "${baseItemChg.lastUpdatedBy}";
		  			v.actionlog = "ADD";
		  			v.dispseq = maxDispSeq;
		  			Global.JqGrid.addRowData("dataTable",v);
		  		});
		  		changeAmount();
	  	   		refreshDataTable();
			}
		});
	}
	
	function refreshGroup(e){
		var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
		if($(e).is(":checked")){
			$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/baseItemChgDetail/goTmpJqGrid",postData:{"params":params,"orderBy":"itemtype2descr"},page:1,grouping : true}).trigger("reloadGrid"); 
		}else{
		 	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/baseItemChgDetail/goTmpJqGrid",postData:{"params":params,"orderBy":"dispseq"},page:1,grouping : false}).trigger("reloadGrid"); 
		}
	}
	
	function goTop(table){
		if(!table) table = "dataTable";
		var rowId = $("#"+table).jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)-1;
		if(rowId){
			if(rowId>1){
			    var ret1 = $("#"+table).jqGrid("getRowData", rowId);
				var ret2 = $("#"+table).jqGrid("getRowData", replaceId);
				if (ret1.fixareadescr == ret2.fixareadescr) {
					replace(rowId,replaceId);
				    scrollToPosi(replaceId);
				    $("#dataTable").setCell(rowId,"dispseq",ret1.dispseq);
					$("#dataTable").setCell(replaceId,"dispseq",ret2.dispseq);
				}
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	
	function goBottom(table){
		if(!table) table = "dataTable";
		var rowId = $("#"+table).jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)+1;
		var rowNum = $("#"+table).jqGrid("getGridParam","records");
		if(rowId){
			if(rowId<rowNum){
				var ret1 = $("#"+table).jqGrid("getRowData", rowId);
				var ret2 = $("#"+table).jqGrid("getRowData", replaceId);
				if (ret1.fixareadescr == ret2.fixareadescr) {
				    scrollToPosi(replaceId);
					replace(rowId,replaceId);
					$("#dataTable").setCell(rowId,"dispseq",ret1.dispseq);
					$("#dataTable").setCell(replaceId,"dispseq",ret2.dispseq);
				}
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	
	function goInSetChg(){
		var gridDatas = $("#dataTable").jqGrid("getRowData");
		reqpk = [];
		$.each(gridDatas,function(k,v){
			if(v.reqpk && v.reqpk!="0") reqpk.push(v.reqpk);
		});
		Global.Dialog.showDialog("baseItemChg_InsetChg",{
			title:"材料增减明细--套内项目减项",
			url:"${ctx}/admin/baseItemChg/goInSetChg?custCode=${baseItemChg.custCode}&custTypeType=${baseItemChg.custTypeType}",
			height: 600,
			width:1000,
			resizable:false,
			returnFun: function(data){
			  	if(data.selectRows.length){
		  	    	$.each(data.selectRows,function(k,v){
		  	    		maxDispSeq++;
		  				v.lastupdate = getNowFormatDate();
		  				v.lastupdatedby = "${baseItemChg.lastUpdatedBy}";
		  				v.actionlog = "ADD";
		  				v.dispseq = maxDispSeq;
		  				v.pk = "";
		  				v.fixareaseq = "1";
		  				v.reqpk ="",
		  				v.lineamount = myRound(myRound(v.qty * myRound(v.material))+ myRound(v.qty * myRound(v.unitprice)),0);
		  				Global.JqGrid.addRowData("dataTable",v);
		  			});
		  	    	changeAmount();
		  	   		refreshDataTable();
				}
			}
		});
	}
	
	window.onfocus = function() {
	 	var colModel = $("#dataTable")[0].p.colModel;
	 	var index;
	 	$.each(colModel,function(k,v){
	 		if(v.name=="qty"){
		 		index = k;
		 		return false;
	 		}
	 	});
	 	if(data_iCol==index && editRow){
	 		$("#dataTable").jqGrid("editCell",data_iRow,data_iCol,true);
	 	}
	};
	
	window.onblur = function() {
		editRow = data_iRow;
	};
	
	function isSetMinus(){
		var result = true;
		var gridDatas = $("#dataTable").jqGrid("getRowData");
		$.each(gridDatas,function(k,v){
			if (v.category!="4"){
				result = false;
			}
		});
		return result;
	}
	
	function discRemark(){
      	Global.Dialog.showDialog("baseItemDiscRemark",{
		  	title:"优惠说明",
		  	url:"${ctx}/admin/baseItemChg/goDiscRemark?custCode=${baseItemChg.custCode}",
		  	height:350,
		  	width:800,
		  	returnFun: goto_query
		});
	}
	
	function calcDiscPer(){
		var baseDiscPer = $.trim($("#baseDiscPer").val());
		var chgBaseDiscPer = $.trim($("#chgBaseDiscPer").val());
		var referenceDisc = $.trim($("#referenceDisc").val()); //参考优惠金额
		var befAmount = $.trim($("#befAmount").val()); //总费用
		var zhFee = $.trim($("#zhFee").val()); //综合费用
		var baseFeeDirct = $.trim("${baseItemChg.baseFeeDirct }"); //基础需求的直接费
		$("#afterDiscPer").val(myRound(myRound(baseDiscPer,4)-myRound(chgBaseDiscPer,4),4));//计算变动后折扣
		//参考优惠金额
		var referenceDisc = myRound(baseFeeDirct*chgBaseDiscPer+(1-$("#afterDiscPer").val())*(befAmount-zhFee));
		$("#referenceDisc").val(referenceDisc<0?-1*referenceDisc:referenceDisc);
	}
	function calculateTax(manageChg){
		if (("${operType}"=="V" || iChangeAmount<2) && manageChg!="1") return;
		if ($("#amount").val()!=""){
		    var dContractFee=0;
		    var dSetAdd=0.0;
		    if ("${custType.type}"=="1"){
		   	    dContractFee=parseFloat($("#amount").val());
		   	 	dSetAdd=parseFloat($("#amount").val());
		    }else{
		    	dContractFee=parseFloat($("#amount").val())+parseFloat($("#manageFee").val())
		    	dSetAdd=parseFloat($("#amount").val())-parseFloat($("#setMinus").val());
		    } 
		    var dBefAmount = myRound($("#dataTable").getCol("lineamount",false,"sum"));
		   	var dDiscAmount = parseFloat($("#discAmount").val()=="" ? 0 : $("#discAmount").val() );
	   		if (dBefAmount < 0){
	   			dDiscAmount= - dDiscAmount;	
	   		}
	   		var dBaseFeeComp=parseFloat($("#zhFee").val());
	   		var dBaseFeeDirct=myRound(parseFloat($("#amount").val())-parseFloat($("#zhFee").val()));
			$.ajax({
				url:"${ctx}/admin/itemPlan/getTax",
				type:"post",
				data:{code:"${baseItemChg.custCode}", contractFee:dContractFee,
					designFee:0,taxCallType:'2', discAmount:dDiscAmount,
					setAdd: dSetAdd,
					manageFeeBase: 0.0, //预算管理费 
					baseFeeComp:dBaseFeeComp,
					baseFeeDirct:dBaseFeeDirct,
				},
				dataType:"json",
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
				},
				success:function(obj){
					if(obj){
						$("#tax").val(obj);
					}else{
						$("#tax").val(0);
					}	
				}
			}); 
		}else{
			$("#tax").val(0);
		}      	
	}
	function goDiscAmtTran(){
		var custCode = $.trim($("#custCode").val());
		Global.Dialog.showDialog("DiscAmtTran",{
			title:"优惠额度调整",
			url:"${ctx}/admin/itemChg/goDiscAmtTran",
			postData:{custCode:custCode},
			height:700,
			width:1050,
			returnFun:goto_query
		});
	}
	
	function goBaseFromTemp(){
		Global.Dialog.showDialog("chgStakeholder",{
			title: "基础增减——选择模板",
			url: "${ctx}/admin/baseItemChg/goBaseFromTemp",
			postData: {custType:"${baseItemChg.custType}",custCode:"${baseItemChg.custCode}"},		
			height:260,
			width:430,
			returnFun : function(data){
				if(data){
					$("#dataTable").clearGridData();
					$.each(data.datas, function(i,v){
						Global.JqGrid.addRowData("dataTable", v);
					});
					$("#tempNo").val(data.tempNo);
					$("#tempDescr").val(data.tempDescr);
					changeAmount();
		  	   		refreshDataTable();
				}
			}
		});
	}
	
	function loadRegen(){
	  	var rowData=$('#dataTable').jqGrid("getRowData");
		var datas=$("#dataForm").jsonForm();
		datas.detailJson=JSON.stringify(rowData);
	   	art.dialog({
			//content:"此操作将生成新加空间预算项目,原有预算重新计算数量，是否确定要重新生成？",
			content:'<input type="checkbox" id="checkCanreplace" />可替换项重新生成：勾选可替换项将被覆盖<br/> '
					+'<input type="checkbox" id="checkCanmodiQty" />重置可修改数量项目<br/> '
					+' 此操作将生成新加空间预算项目,原有预算重新计算数量，<br/>是否确定要重新生成 ？',
			ok: function () {
				var isCheckCanreplace=$("#checkCanreplace").is(':checked');
			 	if(isCheckCanreplace){
			 		datas.isRegenCanreplace='1';
			 	}else{
			 		datas.isRegenCanreplace='0';
			 	}
			 	var isCheckCanreplace=$("#checkCanmodiQty").is(':checked');
			 	if(isCheckCanreplace){
			 		datas.isRegenCanmodiQty='1';
			 	}else{
			 		datas.isRegenCanmodiQty='0';
			 	}
				$.ajax({
					url:"${ctx}/admin/baseItemChg/getRegenBaseItemChgFromPrePlanTemp",
					type:"post",
			        dataType:"json",
			        data:datas,
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '重新生成数据出错~'});
				    },
			      success: function(obj){
					    if(obj.rs){
					  		hasEdit=true;
					  		var params=obj.datas;
					  		$("#dataTable").jqGrid("setGridParam", {
					        	url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
					        	postData: {"params": params, "orderBy": "fixareapk"},
					        	page: 1
					      	}).trigger("reloadGrid");
					  		changeAmount();
					  		art.dialog({
								content: obj.msg,
								time: 1000,
							});
				  		}else{
				  			art.dialog({
								content: obj.msg
							});
				  		}
				    }
			 	});
			},
	 		cancel:function(){
	 		}
		});
	}
	
	function viewChg(){
      	Global.Dialog.showDialog("viewChg",{
		  	title:"查看材料需求",
		  	url:"${ctx}/admin/baseItemChg/goViewChg?code=${baseItemChg.custCode}",
		  	height:750,
		    width:1430,
		  	returnFun: goto_query
		});
	}
	
	</script>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
    	<div class="panel-body">
      		<div class="btn-group-xs">
      			<c:if test="${operType!='S' && operType!='V' }">
      			<button type="button" id="saveBtn" class="btn btn-system" onclick="save('')">保存</button>
      			</c:if>
      			<c:if test="${operType=='S' }">
       			<button type="button" class="btn btn-system" onclick="save('2')">审核通过</button>
       			<button type="button" class="btn btn-system" onclick="save('3')">审核取消</button>
        		<button type="button" class="btn btn-system" onclick="goDiscAmtTran()">优惠额度调整</button>
        		<button type="button" class="btn btn-system" onclick="viewChg()">查看材料需求</button>
       			</c:if>
       			<button type="button" class="btn btn-system" onclick="discRemark()">优惠说明</button>
      			<button type="button" class="btn btn-system" onclick="doClose()">关闭</button>
      			<c:if test="${operType=='S' }">
      				<span id="totalRemainDisc" style="color:red;margin:0 20px">总优惠余额：${totalRemainDisc }</span>
      				<span style="color:red;margin-left:50px">${notice }</span>
      			</c:if>
      		</div>
   		</div>
	</div>
	<div class="panel panel-info">  
    	<div>
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<input type="hidden" id="expired" name="expired" value="${baseItemChg.expired}" />
				<input type="hidden" id="canUseComBaseItem" name="canUseComBaseItem" value="${baseItemChg.canUseComBaseItem}" />
				<input type="hidden" id="custType" name="custType" style="width:160px;" value="${baseItemChg.custType}" />
				<input type="hidden" id="custTypeType" name="custTypeType" style="width:160px;" value="${baseItemChg.custTypeType}" />
				<input type="hidden" id="tempNo" name="tempNo" style="width:160px;" value="${baseItemChg.tempNo}" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>批次号</label>
						<input type="text" id="no" name="no" value="${baseItemChg.no}" readonly="readonly"/>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" readonly="readonly" value="${baseItemChg.address}" />
					</li>
					<li>
						<label>楼盘面积</label>
						<input type="text" id="area" name="area" readonly="readonly" value="${baseItemChg.area}" />
					</li>
					<li>
						<label>档案编号</label>
						<input type="text" id="documentNo" name="documentNo" readonly="readonly" value="${baseItemChg.documentNo}" />
					</li>
					<li id="befAmountLi">
						<label>优惠前金额</label>
						<input type="text" id="befAmount" name="befAmount" readonly="readonly" value="${baseItemChg.befAmount}"/>
					</li>
					<li id="discAmountLi">	
						<label><span class="required">*</span>优惠金额</label>
						<input type="text" id="discAmount" name="discAmount" onblur="changeAmount()" value="${baseItemChg.discAmount}" />
					</li>
					<li>	
						<label>实际总价</label>
						<input type="text" id="amount" name="amount" readonly="readonly" value="${baseItemChg.amount }" />
					</li>
					<c:if test="${baseItemChg.custTypeType=='2' }">
					<li>	
						<label>基础管理费</label>
						<input type="text" id="manageFee" name="manageFee"  value="${baseItemChg.manageFee }" onchange="calculateTax('1')" />
					</li>
					</c:if>
					<li>
						<label>税金</label>
						<input type="text" id="tax" name="tax" value="${baseItemChg.tax }" />
					</li>
					<li>	
						<label>综合费用</label>
						<input type="text" id="zhFee" name="zhFee" readonly="readonly" value="0" />
					</li>
					<c:if test="${custType.type=='1' && custType.isAddAllInfo == '1' }">
						<li>	
							<label>原基础折扣</label>
							<input type="text" id="baseDiscPer" onkeyup="calcDiscPer()" name="baseDiscPer" value="${baseItemChg.baseDiscPer }" />
						</li>
						<li>	
							<label>基础折扣变动</label>
							<input type="text" id="chgBaseDiscPer" onkeyup="calcDiscPer()" name="chgBaseDiscPer" value="${baseItemChg.chgBaseDiscPer }" />
						</li>
						<li>	
							<label>变动后折扣</label>
							<input type="text" id="afterDiscPer" name="afterDiscPer" readonly="readonly" value="${baseItemChg.baseDiscPer - baseItemChg.chgBaseDiscPer }" />
						</li>
						<li>	
							<label>参考优惠金额</label>
							<input type="text" id="referenceDisc" name="referenceDisc" readonly="readonly" value="0"/>
						</li>
					</c:if>
					<li>
						<label for="isAddAllInfo"><span class="required">*</span>常规变更</label>
						<house:xtdm id="isAddAllInfo" dictCode="YESNO" style="width:160px;" value="${baseItemChg.isAddAllInfo}"
							onchange="changeStakeholderDisabled()"/>
					</li>
					<li>
						<label><span class="required" id="baseChgStakeholderRequired" hidden="true">*</span>干系人</label>
						<div class="input-group">
							<input type="text" class="form-control" id="baseChgStakeholder" style="width: 121px;" readonly="readonly">
							<button type="button" class="btn btn-system" id="baseChgStakeholderBtn" style="font-size: 12px;width: 40px;">编辑</button>
						</div>
					</li>
					<li>	
						<label>报价模板</label>
						<input type="text" id="tempDescr" name="tempDescr" readonly="readonly" value="${baseItemChg.tempDescr }"/>
					</li>
					<li id="loadMore">
						<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">更多</button>
					</li>
					<div class="collapse" id="collapse">
					<ul>
						<c:if test="${baseItemChg.custTypeType=='2' }">
						<li>	
							<label>套餐内减项</label>
							<input type="text" id="setMinus" name="setMinus" readonly="readonly" value="${baseItemChg.setMinus }" />
						</li>
						<li>	
							<label>不计管理费金额</label>
							<input type="text" id="noManageFee" name="noManageFee" readonly="readonly" />
						</li>
						</c:if>
						<li>	
							<label>基装增减状态</label>
							<house:xtdm id="status" dictCode="ITEMCHGSTATUS" value="${baseItemChg.status}" disabled="true"></house:xtdm>
						</li>
						<li>	
							<label>变更日期</label>
							<input type="text" style="width:160px;" id="date" name="date" class="i-date" 
							onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
							value="<fmt:formatDate value='${baseItemChg.date }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
						</li>
						<li>
							<label>申请人</label>
							<input type="text" id="appCzyDescr" name="appCzyDescr" value="${baseItemChg.appCzyDescr}" readonly="readonly" />
						</li>
						<li>	
							<label>审核日期</label>
							<input type="text" id="confirmDate" name="confirmDate" readonly="readonly" 
							value="<fmt:formatDate value='${baseItemChg.confirmDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
						</li>
						<li>
							<label>审核人</label>
							<input type="text" id="confirmCzyDescr" name="confirmCzyDescr" value="${baseItemChg.confirmCzyDescr}" readonly="readonly" />
						</li>
						<li>
							<label><span class="required">*</span>客户编号</label>
							<input type="text" id="custCode" name="custCode" value="${baseItemChg.custCode}" readonly="readonly" />
						</li>
						<li>	
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks">${baseItemChg.remarks }</textarea>
						</li>
						<li class="search-group-shrink">
							<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">收起</button>
						</li>
					</ul>
					</div>
				</ul>
			</form>
			<form style="display: none" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
			</form>
		</div>
	</div>
	<div class="btn-panel">
      	<div class="btn-group-sm">
      		<c:if test="${operType!='V' }">
      		<button type="button" class="btn btn-system" onclick="quickSave()">快速新增</button>
      		<button type="button" class="btn btn-system" onclick="goTransAction()">已有项变动</button>
      		<button type="button" class="btn btn-system" id="inSetChg" onclick="goInSetChg()">套内项目减项</button>
      		<button id="delBtn" type="button" class="btn btn-system" onclick="del()">删除</button>
      		</c:if>
      		<button type="button" class="btn btn-system" onclick="goEdit('V')">查看</button>
      		<c:if test="${operType!='V' }">
      		<button type="button" class="btn btn-system" onclick="goEdit('M')">编辑</button>
      		</c:if>
      		<button type="button" class="btn btn-system" onclick="doExcelNow('基装增减')">导出excel</button>
      		<button type="button" class="btn btn-system" onclick="goTop()">向上</button>
      		<button type="button" class="btn btn-system" onclick="goBottom()">向下</button>
      		<c:if test="${baseItemChg.signQuoteType =='2' && operType!='V' && existsTemp=='false'}">
	      		<button type="button" class="btn btn-system" onclick="goBaseFromTemp()">从模板导入</button>
	      		<button type="button" class="btn btn-system" onclick="loadRegen()">重新生成</button>
      		</c:if>
      		<button type="button" class="btn btn-system" onclick="goImport()">导入excel</button>
      	</div>
	</div>
    <!--标签页内容 -->
	<div class="container-fluid">
	    <ul class="nav nav-tabs">
	      	<li class="active"><a href="#tab_mainDetail" data-toggle="tab">主项目</a></li>
	        <li class="nav-checkbox" style="display: none;"> <input type="checkbox" id="itemType2Group" name="itemType2Group" onclick="refreshGroup(this)"><label for="itemType2Group">按材料类型2分类</label></li>
	    </ul>
		<div class="tab-content">
	       <div id="tab_mainDetail" class="tab-pane fade in active"> 
			    <div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div>
		</div>
	</div>
	<!-- 增减干系人列表 -->
	<div style="display: none">
		<table id= "baseChgStakeholderDataTable"></table>
	</div>
</div>
<script type="text/javascript">
$(function() {
	if("1"=="${custType.type}" && "0"=="${custType.isPartDecorate}"){
		$("#inSetChg").show();
	}else{
		$("#inSetChg").hide();
	}
	changeStakeholderDisabled();
	if("${operType }"=="S" ||"${operType }"=="V"){
		$("#isAddAllInfo").prop("disabled", true);
		$("#baseChgStakeholderBtn").prop("disabled", true);
		$("#baseChgStakeholderRequired").prop("hidden", true);
		baseChgStakeholderConfirm = false;//干系人只有新增、修改时才能修改 add by zb on 20191224
		$("#baseDiscPer").attr("readonly",true);
		$("#chgBaseDiscPer").attr("readonly",true);
	}
	$("*","#dataForm").bind("focus",function(){data_iRow = 0;});  
	$("#itemType1").attr("disabled","disabled");
	var gridHeight = $(document).height()-$("#content-list").offset().top-67;
	var jqGridOption = {
		url: "${ctx}/admin/baseItemChgDetail/goJqGrid?no=${baseItemChg.no}",
		height:gridHeight,
		cellEdit:true,
		cellsubmit:"clientArray",
		rowNum:10000,
		colModel : [
			{name: "pk", index: "pk", width: 30, label: "pk", sortable: true, align: "left", hidden: true},
			{name: "baseitemdescr", index: "baseitemdescr", width: 250, label: "基装项目名称", sortable: true, align: "left"},
			{name: "preqty", index: "preqty", width: 80, label: "已有数量", sortable: true, align: "right"},
			{name: "algorithmdescr", index: "algorithmdescr", width: 90, label: "算法", sortable: true, align: "left",editable:true,
		  		edittype:'select',
		  		editoptions:{ 
		  			dataUrl: '${ctx}/admin/baseItem/getBaseAlgorithm',
					postData: function(){
						var ret = selectDataTableRow("dataTable");
						console.log(ret);
						if(ret){
							return {
								code: ret.baseitemcode
							};
						}
						return {code: ""};
					},
					buildSelect: function(e){
						var lists = JSON.parse(e);
						var html = "<option value=\"\" ></option>";
						for(var i = 0; lists && i < lists.length; i++){
							html += "<option value=\""+ lists[i].Code +"\">" + lists[i].Descr + "</option>"
						}
						return "<select style=\"font-family:宋体;font-size:16px\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						algorithmClick(e)
	  					}
		  			}, 
		  		]}
	 		},
			{name: "qty", index: "qty", width: 80, label: "变动数量", sortable: true, align: "right",editable:true,edittype:"text",editrules:{number:true,required:true},sum:true},
			{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width:80, label: "人工单价", sortable: true, align: "right",editable: true,edittype:"text",editrules:{number:true,required:true}},
			{name: "material", index: "material", width:80, label: "材料单价", sortable: true, align: "right",editable: true,edittype:"text",editrules:{number:true,required:true}},
			{name: "lineamount", index: "lineamount", width: 100, label: "总价", sortable: true, align: "right", sum: true, summaryTpl: "小计：<strong style='text-align: right;margin-right: -4px;'>{0}</strong>", summaryType: "sum"},
			{name: "isoutset", index: "isoutset", width: 80, label: "套餐外项目", sortable: true, align: "left",hidden:true},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 60, label: "套外", sortable: true, align: "left",},
			{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left",editable: true,edittype:"textarea"},
	 		{name: "algorithm", index: "algorithm", width: 85, label: "算法编号", sortable:false, align: "left",hidden: true},
			{name: "baseitemsetno", index: "baseitemsetno", width: 68, label: "套餐包", sortable: true, align: "left"},
            {name: "ismainitem", index: "ismainitem", width: 68, label: "主项目", sortable: true, align: "left", hidden: true},
            {name: "fixareapk", index: "fixareapk", width: 92, label: "区域pk", sortable: true, align: "left", hidden: true},
		    {name: "baseitemcode", index: "baseitemcode", width: 100, label: "基装项目编号", sortable: true, align: "left"},
		    {name: "fixareadescr", index: "fixareadescr", width: 200, label: "区域名称", sortable: true, align: "left"},
		    {name: "preplanareadescr", index: "preplanareadescr", width: 120, label: "空间", sortable: true, align: "left"},
		    {name: "preplanareapk", index: "preplanareapk", width: 120, label: "空间pk", sortable: true, align: "left",hidden:true},
            {name: "ismainitemdescr", index: "ismainitemdescr", width: 68, label: "主项目", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
			{name: "category", index: "category", width: 180, label: "项目类型", sortable: true, align: "left",hidden:true },
	  		{name: "cost", index: "cost", width: 80, label: "成本", sortable: true, align: "left", hidden: true},
	  		{name: "prjctrltype", index: "prjctrltype", width: 80, label: "发包方式", sortable: true, align: "left", hidden: true},
	  		{name: "offerctrl", index: "offerctrl", width: 80, label: "人工发包", sortable: true, align: "left", hidden: true},
	  		{name: "materialctrl", index: "materialctrl", width: 80, label: "材料发包", sortable: true, align: "left", hidden: true},
	  		{name: "reqpk", index: "reqpk", width: 80, label: "需求pk", sortable: true, align: "left", hidden: true},
	  		{name: "dispseq", index: "dispseq", width: 80, label: "排序", sortable: true, align: "left", hidden: true},
	  		{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true}
        ],
        grouping : true,
        groupingView: {
            groupField: ["fixareadescr"],
            groupColumnShow: [true],
            groupText: ["<b title=\"{0}({1})\">{0}({1})</b>"],
            groupOrder: ["asc"],
            groupSummary: [false],
            groupCollapse: false
        },
		gridComplete:function(){
			changeAmount();
		  	if(!maxDispSeq && $("#dataTable").jqGrid("getGridParam","records")>0){
		  		var rowData = $("#dataTable").jqGrid("getRowData",$("#dataTable").jqGrid("getGridParam","records"));
		    	maxDispSeq = rowData.dispseq;
		  	}
		  	var datas = $("#dataTable").jqGrid("getRowData");
		  	$.each(datas, function (k, v) {
		  		if (v.reqpk) {
	                $("#dataTable").jqGrid("setCell", k + 1, "unitprice", "", "not-editable-cell");
	                $("#dataTable").jqGrid("setCell", k + 1, "material", "", "not-editable-cell");
	                $("#dataTable").jqGrid("setCell", k + 1, "remarks", "", "not-editable-cell");
	        	}
		  	});
		  	calcDiscPer();
		  	// judgeStakeholderIsDisabled();	//已有项新增，不需要判断独立销售干系人和之前是否一致	modify by zb on 20200420
		},
		beforeSaveCell: function (id, name, val, iRow, iCol) {
    		var rowData = $("#dataTable").jqGrid("getRowData", id);
      		if (name=="qty" && "${baseItemChg.custTypeType}"=="2" 
      				&& !rowData.reqpk && rowData.category!="4" && val<0){
   				art.dialog({
                 	content: "套餐类客户，只有基础项目是套餐类减项的才允许预算为负数！"
               	});
   				return "0";
      		}
      		
      		if(name=="qty" && "${baseItemChg.custTypeType}"=="2" 
      				&& rowData.reqpk && rowData.category!="4"
      				&& myRound(rowData.preqty) + myRound(val) < 0){
      			art.dialog({
                 	content: "套餐客户，基础项目类型是非套餐类减项不能减成负数。"
               	});
   				return "0";
      		}
      		
      		if (name=="qty" && "${baseItemChg.custTypeType}"=="1"  //清单客户 	套餐内项目  已有项增减   数量小于0  不允许操作
      				&& rowData.reqpk && val<0 && rowData.isoutset == '0' ){
   				art.dialog({
                 	content: "清单客户套餐内已有项项目不允许做减项！",
               	});
   				return "0";
      		}
    	},
        afterSaveCell:function(id,name,val,iRow,iCol){
            var rowData = $("#dataTable").jqGrid("getRowData",id);
            switch (name){
            	case "qty":
                	$("#dataTable").setCell(id, "lineamount", myRound(myRound(val * parseFloat(rowData.unitprice)) + myRound(val * parseFloat(rowData.material)), 0));
                	changeAmount();
                	break;
            	case "unitprice":
            		$("#dataTable").setCell(id, "lineamount", myRound(myRound(rowData.qty * parseFloat(val)) + myRound(rowData.qty * parseFloat(rowData.material)), 0));
            		changeAmount();
            		break;
            	case "material":
            		$("#dataTable").setCell(id, "lineamount", myRound(myRound(rowData.qty * parseFloat(rowData.unitprice)) + myRound(rowData.qty * parseFloat(val)), 0));
            		changeAmount();
            		break;
            	default :
                	break;
            }
            var lineamount = $("#dataTable").getCol("lineamount",false,"sum");
            $("#dataTable").footerData("set", { "lineamount": lineamount });
            calcDiscPer();
            //refreshDataTable();
        },
        beforeSelectRow:function(id){
        	data_iCol = 0;
         	$(".search-suggest").hide();
     	 	setTimeout(function(){
         		relocate(id);
          	},100);
      	},
       	beforeEditCell:function(rowid,cellname,value,iRow,iCol){
        	data_iRow = iRow;
        	data_iCol = iCol;
    	}
	};
	Global.JqGrid.initJqGrid("dataTable",jqGridOption);
	var offset = 68;
	onCollapse(offset);
	$("#discAmount").val(myRound($("#discAmount").val()));
	// 基础增减干系人
	var baseChgStakeholderJqGridOption = {
		url: "${ctx}/admin/baseItemChg/getBaseChgStakeholderList",
		postData: {no: "${baseItemChg.no}"},
		rowNum:10000,
		colModel : [
			{name: "pk", index: "pk", width: 30, label: "pk", sortable: true, align: "left"},
			{name: "basechgno", index: "basechgno", width: 100, label: "基础增减单号", sortable: true, align: "left"},
			{name: "role", index: "role", width: 80, label: "角色", sortable: true, align: "left"},
			{name: "roledescr", index: "roledescr", width: 80, label: "角色", sortable: true, align: "left"},
			{name: "empcode", index: "empcode", width: 80, label: "员工编号", sortable: true, align: "left"},
			{name: "empname", index: "empname", width: 80, label: "员工姓名", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width:85, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "最后更新人员", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 68, label: "操作标志", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left"}
		],
		gridComplete:function(){
			updateStakeholder();
		}
	};
	Global.JqGrid.initJqGrid("baseChgStakeholderDataTable",baseChgStakeholderJqGridOption);
	$("#baseChgStakeholderBtn").on("click",function () {
		var param = Global.JqGrid.allToJson("baseChgStakeholderDataTable");
		Global.Dialog.showDialog("chgStakeholder",{
			title: "基装增减干系人编辑",
			url: "${ctx}/admin/baseItemChg/goChgStakeholderEdit",
			postData: {chgStakeholderList: param.detailJson},		
			height: 458,
			width: 660,
			returnFun : function(data){
				if(data){
					$("#baseChgStakeholderDataTable").clearGridData();
					$.each(data, function(i,v){
						Global.JqGrid.addRowData("baseChgStakeholderDataTable", v);
					});
				}
			}
		});
	});
	/*$("#isAddAllInfo").on("change", function () {
		changeStakeholderDisabled();
	});*/
});
//判断是否干系人必录入
function changeStakeholderDisabled() {
	if (transActionStakeholderDisabled) return;
	if ("0" != $("#isAddAllInfo").val()) {
		$("#baseChgStakeholder").val("");
		$("#baseChgStakeholderDataTable").clearGridData();
		$("#baseChgStakeholderBtn").prop("disabled", true);
		$("#baseChgStakeholderRequired").prop("hidden", true);
	} else {
		$("#baseChgStakeholderBtn").prop("disabled", false);
		$("#baseChgStakeholderRequired").prop("hidden", false);
	}
}
//更新干系人
function updateStakeholder() {
	var rows = $("#baseChgStakeholderDataTable").jqGrid("getRowData");
	var baseChgStakeholderName = "";
	$.each(rows, function (i, val) {
		if (0!=i) baseChgStakeholderName += ",";
		baseChgStakeholderName += val.empname;
	});
	$("#baseChgStakeholder").val(baseChgStakeholderName);
}

function algorithmClick(e){
	var rowid = $("#dataTable").getGridParam("selrow");
	console.log($(e.target).attr("rowid"));
    var rowData = $("#dataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
    $("#dataTable").setRowData($(e.target).attr("rowid"), {
    	algorithm:$(e.target).val(),
    });

    changeAlgorithm($(e.target).attr("rowid"),$(e.target).val());
}

function changeAlgorithm(id,val){
	var ret = selectDataTableRow("dataTable");
	var datas=$("#page_form").jsonForm();
		datas.baseItemCode=ret.baseitemcode;
		datas.baseAlgorithm = val;
		datas.prePlanAreaPK=ret.preplanareapk;
		datas.custType = "";
		datas.custCode = "${baseItemChg.custCode}";
	$.ajax({
		url:"${ctx}/admin/baseItemPlan/getBaseItemPlanAutoQty",
			type:"post",
	        dataType:"json",
	        async:false,
	        data:datas,
			cache: false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				$("#dataTable").setRowData(id, {
					qty:obj
				}); 
				$("#dataTable").setCell(id, "lineamount", 
      						myRound(myRound(myRound(obj ,4)* parseFloat(ret.unitprice)) + myRound(myRound(obj ,4) * parseFloat(ret.material), 0)));
				var lineamount=$("#dataTable").getCol('lineamount',false,'sum');   
	              $("#dataTable").footerData('set', { "lineamount": lineamount });  
	              changeAmount();
			}
	});	
}
</script>
</body>
</html>


