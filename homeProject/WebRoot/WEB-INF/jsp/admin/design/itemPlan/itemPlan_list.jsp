<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>ItemPlan列表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$("#custType").val('');
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
}
function goItemPlan_jcys(containItemPlan){
	var ret = selectDataTableRow();
  	if (ret) {
  		var title="基础预算";
  		if(ret.isoutset=="2") title+="套餐类";
		Global.Dialog.showDialog("itemPlan_jcys",{
			title:title,
		  	url:"${ctx}/admin/itemPlan/goItemPlan_jcys?id="+ret.code,
		  	height:window.screen.height-130,
		 	width:window.screen.width-40,
		 	//returnFun: goto_query
		 	close : function(){	
				if (containItemPlan) {
					goItemPlan_ys("ZC","主材预算")
				}  
			}
		});
	}else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function goItemPlan_jcys_confirm(){
var ret = selectDataTableRow();
  if (ret) {
  	var title="基础预算";
  	if(ret.isoutset=="2") title+="套餐类";
	Global.Dialog.showDialog("itemPlan_jcys_confirm",{
		  title:title+"审核",
		  url:"${ctx}/admin/itemPlan/goItemPlan_jcys_confirm?id="+ret.code,
		  height:window.screen.height-130,
		  width:window.screen.width-40,
		  returnFun: goto_query
		});
	}else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function goItemPlan_ys(itemType1,title){
	var ret = selectDataTableRow();
    if (ret) {
	  	if(ret.isoutset=="2") title+="套餐类";
		Global.Dialog.showDialog("itemPlan_ys",{
			  title:title,
			  url:"${ctx}/admin/itemPlan/goItemPlan_ys?id="+ret.code+"&itemType1="+itemType1,
			  height:window.screen.height-130,
			  width:window.screen.width-40,
			  returnFun: goto_query
		});
	}else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemPlanUpdate",{
		  title:"修改ItemPlan",
		  url:"${ctx}/admin/itemPlan/goUpdate?id="+ret.PK,
		  height:600,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function copy(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemPlanCopy",{
		  title:"复制ItemPlan",
		  url:"${ctx}/admin/itemPlan/goSave?id="+ret.PK,
		  height:600,
		  width:1000,
		  returnFun: goto_query
		});
    }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
     	Global.Dialog.showDialog("itemPlanView",{
		   title:"查看ItemPlan",
		   url:"${ctx}/admin/itemPlan/goDetail?id="+ret.PK,
		   height:600,
		   width:1000
		});
    }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/itemPlan/doDelete";
	beforeDel(url);
}

/**初始化表格*/
$(function(){
	var postData = $("#page_form").jsonForm();
	postData.status="2,3";
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemPlan/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-100,
		postData:postData,
		colModel : [
		    {name: "code", index: "code", width: 70, label: "客户编号", sortable: true, align: "left", frozen: true},
			{name: "descr", index: "descr", width: 70, label: "客户名称", sortable: true, align: "left", frozen: true},
			{name: "address", index: "address", width: 148, label: "楼盘", sortable: true, align: "left", frozen: true},
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left", frozen: true},
			{name: "isoutset", index: "isoutset", width: 70, label: "是否套餐", sortable: true, align: "left",  hidden: true},
			{name: "layout", index: "layout", width: 60, label: "户型", sortable: true, align: "left", hidden: true},
			{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "left", hidden: true},
			{name: "statusdescr", index: "statusdescr", width: 70, label: "客户状态", sortable: true, align: "left"},
			{name: "constatusdescr", index: "constatusdescr", width: 70, label: "电子合同", sortable: true, align: "left"},
			{name: "discapprovestatus", index: "discapprovestatus", width: 90, label: "优惠审批状态", sortable: true, align: "left",formatter: clickOpt },
			{name: "wfprocinstno", index: "wfprocinstno", width: 79, label: "流程审批状态", sortable: true, align: "left",hidden: true },
			{name: "procstatusdescr", index: "procstatusdescr", width: 79, label: "流程审批状态", sortable: true, align: "left", hidden: true},
			{name: "contractfee", index: "contractfee", width: 70, label: "工程造价", sortable: true, align: "right", sum: true},
			{name: "designfee", index: "designfee", width: 70, label: "设计费", sortable: true, align: "right", sum: true},
			{name: "managefee", index: "managefee", width: 70, label: "管理费", sortable: true, align: "right", sum: true},
			{name: "basefee", index: "basefee", width: 70, label: "基础费", sortable: true, align: "right", sum: true},
			{name: "basedisc", index: "basedisc", width: 90, label: "基础协议优惠", sortable: true, align: "right", sum: true},
			{name: "basefee_dirct", index: "basefee_dirct", width: 80, label: "基础直接费", sortable: true, align: "right", sum: true},
			{name: "basefee_comp", index: "basefee_comp", width: 80, label: "基础综合费", sortable: true, align: "right", sum: true},
			{name: "mainfee", index: "mainfee", width: 70, label: "主材费", sortable: true, align: "right", sum: true},
			{name: "mainfee_per", index: "mainfee_per", width: 110, label: "主材费(费按比率提成)", sortable: true, align: "right", sum: true},
			{name: "maindisc", index: "maindisc", width: 80, label: "主材优惠", sortable: true, align: "right", sum: true},
			{name: "mainservfee", index: "mainservfee", width: 80, label: "服务产品费", sortable: true, align: "right"},
			{name: "softfee", index: "softfee", width: 70, label: "软装费", sortable: true, align: "right", sum: true},
			{name: "softother", index: "softother", width: 90, label: "软装其他费用", sortable: true, align: "right"},
			{name: "softdisc", index: "softdisc", width: 70, label: "软装优惠", sortable: true, align: "right", sum: true},
			{name: "integratefee", index: "integratefee", width: 70, label: "集成费", sortable: true, align: "right", sum: true},
			{name: "integratedisc", index: "integratedisc", width: 70, label: "集成优惠", sortable: true, align: "right", sum: true},
			{name: "cupboardfee", index: "cupboardfee", width: 70, label: "橱柜费", sortable: true, align: "right", sum: true},
			{name: "cupboarddisc", index: "cupboarddisc", width: 70, label: "橱柜优惠", sortable: true, align: "right", sum: true},
			{name: "designmandescr", index: "designmandescr", width: 80, label: "设计师", sortable: true, align: "left"},
			{name: "businessmandescr", index: "businessmandescr", width: 80, label: "业务员", sortable: true, align: "left"},
			{name: "maindesignman", index: "maindesignman", width: 80, label: "主材预算员", sortable: true, align: "left"},
			{name: "mainbusinessman", index: "mainbusinessman", width: 70, label: "主材管家", sortable: true, align: "left"},
			{name: "jcdesignman", index: "jcdesignman", width: 80, label: "集成设计师", sortable: true, align: "left"},
			{name: "cgdesignman", index: "cgdesignman", width: 80, label: "橱柜设计师", sortable: true, align: "left"},
			{name: "mainmandate", index: "mainmandate", width: 130, label: "管家设定时间", sortable: true, align: "left", formatter: formatTime},
			{name: "softplanremark", index: "softplanremark", width: 123, label: "软装备注", sortable: true, align: "left"},
			{name: "mappingdescr", index: "mappingdescr", width: 70, label: "绘图员", sortable: true, align: "left"},
			{name: "sketchdescr", index: "sketchdescr", width: 70, label: "效果图员", sortable: true, align: "left"},
			{name: "designdescr", index: "designdescr", width: 90, label: "深化设计师", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left" ,},
			{name: "contracttempname", index: "contracttempname", width: 70, label: "合同范本名称", sortable: true, align: "left",hidden:true},
           ],
           loadComplete: function(){
              	$('.ui-jqgrid-bdiv').scrollTop(0);
              	frozenHeightReset("dataTable");
           }
	});
	
	$("#designMan").openComponent_employee();
	$("#businessMan").openComponent_employee();
	$("#custCode").openComponent_customer();
	$("#empCode").openComponent_employee();
	$("#mainBusinessMan").openComponent_employee();
	$("#dataTable").jqGrid('setFrozenColumns');
	onCollapse(34);
	
	$("#printContractTemp").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		if(ret.contracttempname ==""){
			art.dialog({
				content:"此客户类型未上传合同范本",
			});
			return;
		}
		Global.Dialog.showDialog("PrintContractTemp",{ 
   	  		title:"打印合同范本",
   	  		url:"${ctx}/admin/itemPlan/goPrintContractTemp",
   	  		postData:{fileName:ret.contracttempname},
   	  		height:755,
   	  		width:937,
   	  		returnFun:goto_query
        });			
	});
});
function updateCustType(){
	var ret = selectDataTableRow();
    if (ret) {
    	if(ret.constatusdescr == "已申请" || ret.constatusdescr == "已审核" || ret.constatusdescr == "已签约"){
    		art.dialog({
    			content:"合同状态为已申请、已审核、已签约不予许修改客户类型"
    		});
    		return;
    	}
    
     	$.ajax({
			url:"${ctx}/admin/itemPlan/checkCustType?custCode="+ret.code,
			dataType: 'json',
			cache: false,
		    success: function(obj){
		    	if(obj.rs){
		   	        Global.Dialog.showDialog("itemPlan_custTypeView",{
						title:"客户类型--编辑",
						url:"${ctx}/admin/itemPlan/goUpdateCustType?custCode="+ret.code,
						height:300,
						width:700,
						returnFun: goto_query
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
	}else {
    	art.dialog({
			content: "请选择一条记录"
		});
    } 
}
function addStakeholder(title,type){
	var ret = selectDataTableRow();
    if (ret) {
   	      Global.Dialog.showDialog("itemPlan_stakeholderView",{
			  title:title,
			  url:"${ctx}/admin/itemPlan/goAddStakeholder?custCode="+ret.code+"&&itemType1="+type,
			  height:400,
			  width:700,
			  returnFun: goto_query
		  });
	 }else {
    	art.dialog({
			content: "请选择一条记录"
		});
     }
}
function analyse(){
	var ret = selectDataTableRow();
    if (ret) {
   	      Global.Dialog.showDialog("itemPlan_profitView",{
			  title:"造价分析",
			  url:"${ctx}/admin/itemPlan/goProfitAnalyse?custCode="+ret.code,
			  height:700,
			  width:1000,
			  returnFun: goto_query
		 });
	 }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function prePlanArea(){
	var ret = selectDataTableRow();
    if (ret) {
		Global.Dialog.showDialog("itemPlan_prePlanArea",{
			title:"空间管理",
			url:"${ctx}/admin/itemPlan/goPrePlanArea",
			postData:{custCode:ret.code},
			height:700,
			width:1030,
			//returnFun: goto_query
			close : function(){
			 	var sDiffType = getPlanDiffByArea(ret.code) // 差异类型 ：1基础、2主材、3基础和主材
			 	if (sDiffType){
			 		art.dialog({
						 content:"空间信息变动需重新生成预算数量，是否继续?",
						 lock: true,
						 width: 260,
						 height: 100,
						 ok: function () {
							 if (sDiffType=="1"){
								 goItemPlan_jcys();
							 }else if(sDiffType=="3" ){
								 goItemPlan_jcys("1");		
							 }else if(sDiffType=="2"){
								 goItemPlan_ys('ZC','主材预算')	
							 } 
						 },
						cancel: function () {
							goto_query;
						}
					});	
			 	}else{
			 		goto_query
			 	}	
			}
		
		});
		
	 }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function getPlanDiffByArea(custCode){
	var sType;
	$.ajax({
		url:"${ctx}/admin/itemPlan/getPlanDiffByArea",
        type: "post",
        data: {
        	custCode: custCode, hasAuthorityJudge: true
        },
        dataType: "json",
        cache: false,
        async: false,
        success: function(data) {
        	if (data){
        		sType = data  
			}
        }
    })
    return sType;	
}

function giftManage(){
	var ret = selectDataTableRow();
    if (ret) {
		Global.Dialog.showDialog("itemPlan_giftManage",{
			title:"预算管理——赠品管理",
			url:"${ctx}/admin/itemPlan/goGiftManage",
			postData:{custCode:ret.code},
			height:700,
			width:1130,
			returnFun: goto_query
			
		});
	 }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function contract(){
	var ret = selectDataTableRow();
    if (ret) {
		Global.Dialog.showDialog("itemPlan_contract",{
			title:"预算管理——合同管理",
			url:"${ctx}/admin/itemPlan/goContract",
			postData:{custCode:ret.code},
			height:700,
			width:1130,
			returnFun: goto_query
			
		});
	 }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function makeOrder(){
	var ret = selectDataTableRow();
    if (ret) {
		Global.Dialog.showDialog("itemPlan_makeOrder",{
			title:"预算管理——下定管理",
			url:"${ctx}/admin/itemPlan/goMakeOrder",
			postData:{custCode:ret.code},
			height:700,
			width:1130,
			returnFun: goto_query
			
		});
	 }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function doPrint(){
	var row = selectDataTableRow();
	if(!row){
		art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
		return false;
	}
	Global.Dialog.showDialog("itemPlan_print",{
	    title:"预算管理--打印",
	    url:"${ctx}/admin/itemPlan/goPrint",
	    postData:{'isOutSet':row.isoutset,'custCode':row.code},
	    height:320,
	    resizable:false, 
	    width:800
	});		
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/itemPlan/doExcel";
	doExcelAll(url);
}
function goSoftPlanVerify(m_umState,title){
	var rowDate = selectDataTableRow("dataTable");
	if(rowDate){
		$.ajax({
			url : "${ctx}/admin/itemPlan/goSoftPlanVerify",
			data: {
				custCode:rowDate.code,
				m_umState:m_umState,
				title:title,
			},
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			dataType: "json",
			type: "post",
			cache: false,
			error: function(){
		        art.dialog({
					content: "软装发货出现异常"
				});
		    },
		    success:function(obj){
		    	if(obj.rs){
		    		goItemPlan_SoftSales(m_umState,title,rowDate);
		    	}else{
		    		art.dialog({
		    			content:obj.msg
		    		});
		    	}
		    }
		});
    }else{
    	art.dialog({
    		content:"请选择一条记录"
    	});
    }	     		
}
function goItemPlan_SoftSales(m_umState,title,rowDate){
	var url="${ctx}/admin/itemPlan/goItemPlan_softSales";
	if(m_umState=='M'){
		url="${ctx}/admin/itemPlan/goItemPlan_softSales?custCode="+rowDate.code;	
	}
	if(m_umState=='A'||m_umState=='M'){
		Global.Dialog.showDialog("itemPlan_ys",{
			  title:title,
			  url:url,
			  height:window.screen.height-130,
			  width:window.screen.width-40,
			  returnFun: goto_query
			});
		
	}else{
		Global.Dialog.showDialog("itemPlanSoftSend",{
			title:"预算管理 - 软装发货",
		 	url:"${ctx}/admin/itemPlan/goItemPlan_softSend",
		  	postData:{
		  		custCode:rowDate.code,
		  	},
		  	height: 650,
		  	width:1250,
		  	returnFun:goto_query
		});
	}
}

function clickOpt(cellvalue, options, rowObject){
	if(rowObject==null){
       	return "";
	}
	if(cellvalue == null){
		return "";
	}
	
	return "<a href=\"javascript:void(0)\" style=\"\" onclick=\"viewProc('"
     			+rowObject.code+"')\">"+cellvalue+"</a>";
}
   	
function viewProc(code){
	Global.Dialog.showDialog("goViewProcTrack",{ 
		title:"流程审批情况",
		url:"${ctx}/admin/itemPlan/goViewProcTrack",
		postData:{code: code},
		height: 702,
		width:1130,
		returnFun:goto_query
	});
}

function commiConstruct(){
	var ret = selectDataTableRow();
	if("0" == $.trim(ret.contractfee)){
		art.dialog({
			content: "工程造价为0，不允许转施工",
		});
		return;
	}
	if("软装客户" != $.trim(ret.custtypedescr)){
		art.dialog({
			content:"软装客户才能进行提交转施工",
		});
		return;
	}
	if("未到公司" != $.trim(ret.statusdescr) && "已到公司" != $.trim(ret.statusdescr) && "订单跟踪" != $.trim(ret.statusdescr)){
		art.dialog({
			content:"未到公司、已到公司、 订单跟踪才能进行提交转施工",
		});
		return;
	}
	
	art.dialog({
		content:"是否确定将提交转施工操作？",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
			$.ajax({
				url:"${ctx}/admin/itemPlan/doCommiConstruct",
				type:'post',
				data:{custCode: ret.code, itemType1: "RZ"},
				dataType:'json',
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": '出错~'});
				},
				success:function(obj){
					console.log(obj);
					if(obj.rs == true){
						art.dialog({
							content: obj.msg,
						});
						$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
						return;
					}
					
					if(obj.rs == false && obj.msg == "提交审批"){
						Global.Dialog.showDialog("goSaleDiscProc",{ 
							title:"独立销售折扣申请单",
							url:"${ctx}/admin/itemPlan/goSaleDiscProc",
							postData:{custCode: ret.code, itemType1Code: "RZ"},
							height: 702,
							width:1200,
							returnFun:goto_query
						});
					} 
					if(obj.rs == false && obj.msg != "提交审批"){
						art.dialog({
							content: obj.msg,
						});
						return;
					} 
				}
			});
		},
		cancel: function () {
			return true;
		}
	});	
}

</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${itemPlan.expired=='T'?'T':'F'}" /> 
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>客户名称</label> <input type="text" id="descr" name="descr" value="${itemPlan.descr}" />
					</li>
					<li><label>楼盘</label> <input type="text" id="address" name="address" value="${itemPlan.address}" />
					</li>
					<li><label>客户编号</label> <input type="text" id="custCode" name="custCode"
						value="${itemPlan.custCode}" />
					</li>
					<li><label>客户状态</label> <house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="2,3"></house:xtdmMulit>
					</li>
					<li><label>设计师编号</label> <input type="text" id="designMan" name="designMan"
						value="${itemPlan.designManDescr}" />
					</li>
					<li><label>业务员编号</label> <input type="text" id="businessMan" name="businessMan"
						value="${itemPlan.businessManDescr}" />
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  selectedValue="${itemPlan.custType }"></house:custTypeMulit>
					</li>
					<li><label>软装备注</label> <input type="text" id="softPlanRemark" name="softPlanRemark" value="${itemPlan.softPlanRemark}" />
					</li>
					<li>
						<label>电子合同</label>
						<house:xtdm  id="contractStatus" dictCode="CONTRACTSTAT"   style="width:160px;"></house:xtdm>
					</li>
					<li id="loadMore">
						<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
					<div class="collapse " id="collapse">
						<ul>
							<li><label>主材预算员</label> <input type="text" id="empCode" name="empCode" />
							</li>
							<li><label>主材管家</label> <input type="text" id="mainBusinessMan" name="mainBusinessMan" />
							</li>
							<li><label>管家设定日期</label> <input type="text" id="mainManDateFrom" name="mainManDateFrom"
								class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${itemPlan.mainManDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>至</label> <input type="text" id="mainManDateTo" name="mainManDateTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${itemPlan.mainManDateTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="search-group-shrink">
								<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">收起</button> <input
								type="checkbox" id="expired_show" name="expired_show" value="${itemPlan.expired=='T'?'T':'F'}"
								onclick="hideExpired(this)" ${itemPlan.expired== 'T'?'':'checked' }/>
								<p>隐藏过期</p>
								<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();">查询</button>
								<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
							</li>
						</ul>
					</div>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="ITEMPLAN_JZYS">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="goItemPlan_jcys()">基础预算</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_AUDIT">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="goItemPlan_jcys_confirm()">基础预算审核</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_ZCYS">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="goItemPlan_ys('ZC','主材预算')">主材预算</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_RZYS">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="goItemPlan_ys('RZ','软装预算')">软装预算</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_ADDSOFTSALES">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="goItemPlan_SoftSales('A','新增软装销售')">新增软装销售</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_UPDATESOFTSALES">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="goSoftPlanVerify('M','编辑软装销售')">编辑软装销售</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_SOFTSEND">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="goSoftPlanVerify('S','软装发货')">软装发货</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_JCYS">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="goItemPlan_ys('JC','集成预算')">集成预算</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_CUSTTYPE">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="updateCustType()">修改客户类型</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_ZCGXR">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="addStakeholder('添加主材干系人','ZC')">主材干系人</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_SJGXR">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="addStakeholder('添加设计干系人','Design')">设计干系人</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_JCGXR">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="addStakeholder('添加集成干系人','JC')">集成干系人</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_RZGXR">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="addStakeholder('添加软装干系人','RZ')">软装干系人</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_MLFX">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="analyse()">造价分析</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_PREPLANAREA">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="prePlanArea()">空间管理</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_GIFTMANAGE">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="giftManage()">赠品管理</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_CONTRACT">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="contract()">合同管理</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_MAKEORDER">
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="makeOrder()">下定管理</button>
				</house:authorize>
				<house:authorize authCode="ITEMPLAN_TOCONSTRUCT">
					<button type="button" class="btn btn-system" style="margin-top:5px" onclick="commiConstruct()">提交转施工</button>
				</house:authorize>
			<%-- 	<house:authorize authCode="ITEMPLAN_PRINTCONTRACTTEMP">
					<button type="button" class="btn btn-system " style="margin-top:5px" id="printContractTemp">打印合同范本</button>
				</house:authorize> --%>
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="doPrint()">打印</button>
					<button type="button" class="btn btn-system " style="margin-top:5px" onclick="doExcel()">导出excel</button>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


