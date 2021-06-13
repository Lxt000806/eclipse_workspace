<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<title>明细查询</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
	.panelBar{
		height:26px;
	}
	</style>
<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/giftApp/goDetailJqGrid",
		postData:{status:"OPEN,SEND,RETURN"},
		ondblClickRow: function(){
           	view();
           },
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		colModel : [
		    {name : 'no',index : 'no',width : 80,label:'领用单号',sortable : true,align : "left"},
		    {name : 'status',index : 'status',width : 100,label:'状态',sortable : true,align : "left",hidden:true},
		    {name : 'statusdescr',index : 'statusdescr',width : 60,label:'状态',sortable : true,align : "left"},
		    {name : 'date',index : 'date',width : 80,label:'申请日期',sortable : true,align : "left",formatter:formatDate},
		    {name : 'senddate',index : 'senddate',width : 80,label:'发货时间',sortable : true,align : "left",formatter:formatDate},
		    {name : 'type',index : 'type',width : 70,label:'领用类型',sortable : true,align : "left",hidden:true},
	        {name : 'typedescr',index : 'typedescr',width : 75,label:'领用类型',sortable : true,align : "left",count:true},
	        {name : 'outtype',index : 'outtype',width :  65,label:'出库类型',sortable : true,align : "left",hidden:true},
	        {name : 'outtypedescr',index : 'outtypedescr',width : 65,label:'出库类型',sortable : true,align : "left",count:true},
	        {name : 'sendtype',index : 'sendType',width : 100,label:'发货类型',sortable : true,align : "left",hidden:true},
	        {name : 'sendtypedescr',index : 'sendtype',width : 70,label:'发货类型',sortable : true,align : "left"},
	        {name : 'whcode',index : 'whcode',width : 100,label:'仓库编号',sortable : true,align : "left",hidden:true},
		    {name : 'whdescr',index : 'whdescr',width : 80,label:'仓库名称',sortable : true,align : "left"},
	        {name : 'actdescr',index : 'actdescr',width : 100,label:'活动名称',sortable : true,align : "left"},
	        {name : 'custcode',index : 'custCode',width :  65,label:'客户编号',sortable : true,align : "left"},
	        {name : 'address',index : 'address',width : 120,label:'楼盘',sortable : true,align : "left"},
	        {name : 'itemcode',index : 'itemcode',width : 70,label:'礼品编号',sortable : true,align : "left"},
	        {name : 'itemdescr',index : 'itemdescr',width : 120,label:'礼品名称',sortable : true,align : "left"},
	        {name : 'tokenno',index : 'tokenno',width : 70,label:'券号',sortable : true,align : "left"},
	        {name : 'qty',index : 'qty',width : 50,label:'数量',editable:true,sum: true,editrules: {number:true,required:true},sortable : true,sortable : true,align : "right"},	
	        {name : 'uomdescr',index : 'uomdescr',width : 50,label:'单位',sortable : true,align : "right"},		
	        {name : 'cost',index : 'cost',width : 50,label:'单价',sortable : true,align : "right"},	
	        {name : 'sumcost',index : 'sumcost',width : 75,label:'总金额',sortable : true,align : "right",sum: true},
	        {name : 'splcheckoutamount',index : 'splcheckoutamount',width : 100,label:'供应商结算金额',sortable : true,align : "right",sum: true},
	        {name : 'remarks',index : 'remarks',width : 200,label:'备注',editrules: {required:true},editable:true,sortable : true,align : "left"},	     	 	
	        {name : 'appczydescr',index : 'appczydescr',width : 65,label:'开单人',sortable : true,align : "left"},
	        {name : 'suppldescr',index : 'suppldescr',width : 100,label:'供应商',sortable : true,align : "left"},
	        {name : 'checkoutno',index : 'checkoutno',width : 110,label:'出库记账单号',sortable : true,align : "left"},
          	{name: "gcdocumentno", index: "gcdocumentno", width: 103, label: "记账单凭证号", sortable: true, align: "left"},
          	{name: "splcheckoutno", index: "splcheckoutno", width: 103, label: "供应商结算单号", sortable: true, align: "left"}
        ]                        
	});
	 $("#useMan").openComponent_employee();
	 $("#supplCode").openComponent_supplier({condition:{itemType1:'LP',readonly:"1",ReadAll:"1"}});  
	 $("#actNo").openComponent_cmpactivity(); 
	 $("#itemCode").openComponent_item({condition:{itemType1:'LP',readonly:"1"}});		 
});  
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}
 //导出EXCEL
 function doExcel(){
  	var url = "${ctx}/admin/giftApp/doDetailExcel";
  	doExcelAll(url);

 }
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="${giftApp.expired}" />
				<ul class="ul-form">
					<li>
						<label>领用单号</label>
					    <input type="text" id="no" name="no"   value="${gifApp.no }" />
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${gifApp.address }" />
					</li>
					<li>
						<label>状态</label>
						<house:xtdmMulit id="status" dictCode="GIFTAPPSTATUS" selectedValue="OPEN,SEND,RETURN"></house:xtdmMulit>
					</li>
					<li>
						<label>发货类型</label>
						<house:xtdm id="sendType" dictCode="GIFTAPPSENDTYPE"  value="${giftApp.sendType}"></house:xtdm>
					</li>
				     <li>
						<label>活动编号</label>
						<input type="text" id="actNo" name="actNo" style="width:160px;" value="${gifApp.actNo }" />
					</li>
					 <li>
						<label>供应商</label>
						<input type="text" id="supplCode" name="supplCode" style="width:160px;" value="${giftApp.supplCode}"/>
					</li>
					 <li>
						<label>开单时间从</label>
						<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateFrom}' pattern='yyyy-MM-dd'/>"/>
					</li>														
					 <li>
						<label>到</label>
						<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>		
					 <li>
						<label>发货时间从</label>
						<input type="text" style="width:160px;" id="sendDateFrom" name="sendDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.sendDateFrom }' pattern='yyyy-MM-dd'/>"/>
					</li>					
					<li>
						<label>到</label>
						<input type="text" style="width:160px;" id="sendDateTo" name="sendDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.sendDateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>	
					 <li>
						<label>材料编号</label>
						<input type="text" id="itemCode" name="itemCode" style="width:160px;" value="${giftApp.itemCode}"/>
					</li>	
					<li >
						<label> 领用类型</label>
						<house:xtdm id="type" dictCode="GIFTAPPTYPE"  value="${giftApp.type }" ></house:xtdm>
					</li>
					<li >
						<label>出库类型</label>
						<house:xtdm id="outType" dictCode="GIFTAPPOUTTYPE"  value="${giftApp.outType} "></house:xtdm>
					</li>	
					<li>
						<label>出库记账单号</label>
						<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;" value="${giftApp.checkOutNo}"/>
					</li>	
					<li>
						<label>供应商结算单号</label>
						<input type="text" id="splCheckOutNo" name="splCheckOutNo" style="width:160px;" value="${giftApp.splCheckOutNo}"/>
					</li>
					<li>
						<label></label>						
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<div class="btn-panel" >
		<div class="btn-group-sm"  > 
	     <house:authorize authCode="GIFTAPP_EXCEL">
	    		<button type="button" class="btn btn-system " onclick="doExcel()">输出至excel</button>
		 </house:authorize>	
		</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
</body>

</html>


