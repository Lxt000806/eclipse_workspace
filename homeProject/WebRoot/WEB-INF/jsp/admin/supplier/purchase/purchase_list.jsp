<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";

%>
<!DOCTYPE html>
<html>
<head>
	<title>采购订单管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/supplierPurchase/doExcel";
	doExcelAll(url);
}

$(function(){
	$("#whcode").openComponent_wareHouse({condition: {czybh:'${czybh}'}});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierPurchase/goJqGrid",
		postData:{status:"${purchase.status}"},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'no',			index:'no',			width:90,	label:'采购单号',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'status', 	index:'status',		width:80,	label:'采购单状态',sortable:true,align:"left",hidden:true,},// hidden:true
			{name:'statusdescr', 	index:'statusdescr',		width:80,	label:'采购单状态',sortable:true,align:"left",},
			{name:'supplier',		index:'supplier',		width:60,	label:'供应商',	sortable:true,align:"left",hidden:true},//,hidden:true
			{name:'type',		index:'type',		width:60,	label:'采购类型',	sortable:true,align:"left",hidden:true,},//,hidden:true
			{name:'typedescr',		index:'typedescr',		width:60,	label:'采购类型',	sortable:true,align:"left",},
			{name:'date',		index:'date',		width:150,	label:'采购日期',	sortable:true,align:"left",	formatter:formatDate},
			{name:'arrivedate',	index:'arrivedate',	width:150,	label:'到货日期',	sortable:true,align:"left",	formatter:formatDate},
			{name:'sumarrivqty',	index:'sumarrivqty',	width:150,	label:'sumArrivQty',	sortable:true,align:"left",	hidden:true},
		 	{name:'whcode',index:'whcode',width:200,	label:'仓库编号',	sortable:true,	align:"left",hidden:true	},
		 	{name:'whcodedescr',index:'whcodedescr',width:120,	label:'仓库名称',	sortable:true,	align:"left",	},
			{name:'remarks',	index:'remarks',	width:290,	label:'备注',		sortable:true,align:"left",}, 
			{name:'confirmdate',	index:'confirmdate',	width:150,	label:'入库时间',		sortable:true,align:"left",formatter: formatTime}, 
			{name:'confirmczy',	index:'confirmczy',	width:50,	label:'入库人员',		sortable:true,align:"left",}, 
			{name:'delivtype',	index:'delivtype',	width:150,	label:'配送地点',		sortable:true,align:"left",hidden:true}, 
		],
		
	});

	//采购入库
	$("#save").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"采购入库",
			url:"${ctx}/admin/supplierPurchase/goSave",
			height:800,
			width:1050,
			returnFun:goto_query
		});
	}); 
	//采购退回
	$("#returnSave").on("click",function(){
		Global.Dialog.showDialog("ReturnSave",{
			title:"采购退回",
			url:"${ctx}/admin/supplierPurchase/goReturnSave",
			height:800,
			width:1050,
			returnFun:goto_query
		});
	}); 
	
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
        	  if (ret) {			
            	Global.Dialog.showDialog("Update",{ 
             	  title:"部分到货编辑",
             	  url:"${ctx}/admin/supplierPurchase/goUpdate?id="+ret.no,
             	  height: 700,
             	  width:1000,
             	  returnFun:goto_query
             	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
           }
	});
	
	//采购退回确认
	$("#purchReturnCheckOut").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
           	 	if ( $.trim(ret.status)!='OPEN'){
           		art.dialog({
           			content: "订单已生效，不能更改"
           		});
           		return;
           	}else{
				if($.trim(ret.type)!='R' || $.trim(ret.delivtype)!='1'){
					art.dialog({
						content:"只能对采购退回进行修改"
					});
					return;  
				}
           	}
            	Global.Dialog.showDialog("purchReturnCheckOut",{ 
             	  title:"采购退回--确认",
             	  url:"${ctx}/admin/supplierPurchase/goPurchReturnCheckOut?id="+ret.no,
             	  height: 700,
             	  width:1000,
             	  returnFun:goto_query
             	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
           }
           });

	//部分到货
	$("#partArrive").on("click",function(){
		var ret = selectDataTableRow();
        	  if (ret) {			
           	 	if ($.trim(ret.type)!='S' || $.trim(ret.delivtype)!='1' || $.trim(ret.status)!='OPEN'){
           		art.dialog({
           			content: "只有未全部到货的【采购入库单】才允许做部分到货"
           		});
           		return;
           	}   
            	Global.Dialog.showDialog("purchasePartArriv",{ 
             	  title:"部分到货编辑",
             	  url:"${ctx}/admin/supplierPurchase/goPartArrive?id="+ret.no,
             	  height: 700,
             	  width:1000,
             	  returnFun:goto_query
             	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
           }
	});
	
	//全部到货 订单类型为采购   到货地点为仓库   订单状态为开   **  sumarrivqty
	$("#allArrive").on("click",function(){
		var ret = selectDataTableRow();
		if (ret) {	
     	 	if ($.trim(ret.type)!='S'|| $.trim(ret.delivtype)!='1' || $.trim(ret.status)!='OPEN'|| $.trim(ret.sumarrivqty)!= 0 ){
	     		art.dialog({
	     			content: "只有未到货的【采购入库单】才允许做全部到货！"
	     		});
	     		return;
     		}   
			Global.Dialog.showDialog("purchaseAllArriv",{
				title:"全部到货编辑",
				url:"${ctx}/admin/supplierPurchase/goAllArrive?id="+ret.no,
				height: 700,
				width:1000,
				returnFun:goto_query
	       	});
		}else {
     		art.dialog({
 				content: "请选择一条记录"
 			});
    	 }
	});
			
	//查看
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
	 	if(ret){
		Global.Dialog.showDialog("PurchaseView",{
			title:"查看采购信息",
			url:"${ctx}/admin/supplierPurchase/goView?id=" + ret.no,
			height:700,
			width:1000,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	});
});
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value="${purchase.expired }"/>
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
							<li>
								<label>采购单号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }"/>
							</li>
							<li >
								<label>采购单状态</label>
								<house:xtdmMulit id="status" dictCode="PURCHSTATUS" selectedValue="${purchase.status}"></house:xtdmMulit>                     
							</li>
							<li>
								<label>仓库编号</label>
								<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }"/>
							</li>
							<li>	
								<label>采购类型</label>
								<house:xtdm id="type" dictCode="PURCHTYPE"  style="width:160px;"></house:xtdm>
							</li>
							<li >
								<label>采购日期</label>
								<input type="text" id="dateFrom1" name="dateFrom1" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo1" name="dateTo1" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li> 
							<li>
								<label>到货日期</label>
								<input type="text" id="arriveDateFrom" name="arriveDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="arriveDateTo" name="arriveDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li class="search-group">					
								<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${purchase.expired }" onclick="hideExpired(this)" 
								 ${purchase.expired!='T'?'checked':'' } /><p>隐藏过期</p>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
				</form>
			</div>
		</div>
			<div class="clear_float"></div>
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<house:authorize authCode="SUPPLIER_PURCHASE_BACK">
							<button type="button" class="btn btn-system " id="purchReturnCheckOut"><span>采购退回确认</span>
						</house:authorize>
						
						<house:authorize authCode="SUPPLIER_PURCHASE_PART">
							<button type="button" class="btn btn-system " id="partArrive"><span>部分到货</span>
						</house:authorize>
						
						<house:authorize authCode="SUPPLIER_PURCHASE_ALL">
							<button type="button" class="btn btn-system " id="allArrive"><span>全部到货</span>
						</house:authorize>
						
						 <house:authorize authCode="SUPPLIER_PURCHASE_VIEW">
							<button type="button" class="btn btn-system " id="view"><span>查看</span>
						</house:authorize>
						<!-- <li>
							<a href="javascript:void(0)" class="a3" onclick="doExcel()" title="导出检索条件数据">
								<span>导出excel</span>
							</a>
						</li> -->
				</div>
			</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
	</body>	
</html>
