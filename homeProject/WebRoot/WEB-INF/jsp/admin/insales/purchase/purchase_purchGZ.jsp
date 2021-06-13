<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE html>
<html>
<head>
	<title>搜寻——采购单号</title>
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
<style type="text/css">
      .SelectBG{
          background-color:pink;
            }
      .SelectBG_yellow{
          background-color:yellow;
         }
 </style>
<script type="text/javascript"> 
$(function(){
	$("#supplier").openComponent_supplier();
	$("#applyMan").openComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}' });

	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	
	//初始化表格
		Global.JqGrid.initJqGrid("dataTable_item",{
			height:150,
			rowNum: 10000,
			colModel : [
			    {name: "pk", 			index: "pk", 			width: 50, label: "编号", sortable: true, align: "left", hidden: true},
				{name: "itcode", 		index: "itcode", 		width: 86, label: "材料编号", sortable: true, align: "left"},
				{name: "itdescr", 		index: "itdescr", 		width: 211, label: "材料名称", sortable: true, align: "left"},
				{name: "sqlcodedescr", 	index: "sqlcodedescr", 	width: 100, label: "品牌", sortable: true, align: "left"},
				{name: "color", 		index: "color", 		width: 69, label: "颜色", sortable: true, align: "left"},
				{name: "allqty", 		index: "allqty", 		width: 88, label: "当前库存量", sortable: true, align: "left"},
				{name: "arrivqty", 		index: "arrivqty", 		width: 88, label: "已到货数量", sortable: true, align: "left"},
				{name: "qtycal",		index: "qtycal", 		width: 77, label: "采购数量", sortable: true, align: "left"},
				{name: "unidescr", 		index: "unidescr", 		width: 70, label: "单位", sortable: true, align: "left"},
				{name: "unitprice", 	index: "unitprice", 	width: 79, label: "单价", sortable: true, align: "left"},
				{name: "amount", 		index: "amount", 		width: 85, label: "总价", sortable: true, align: "left", sum: true},
				{name: "remarks", 		index: "remarks", 		width: 244, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", 	index: "lastupdate", 	width: 84, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 84, label: "更新人员", sortable: true, align: "left", hidden: true},
				{name: "expired",		index: "expired", 		width: 84, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", 	index: "actionlog", 	width: 84, label: "操作", sortable: true, align: "left", hidden: true}
            ]
		});
		
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchase/goPurchGZJqGrid",
		postData:{applyMan:'${purchase.applyMan}',remainTime:-3,arriveStatus:'0',status:'OPEN'},	
		height:180,
		rowNum:10000000,
		colModel : [
			{name: "maxpk", index: "maxpk", width: 100, label: "maxpk", sortable: true, align: "left",count:true,hidden:true},
			{name: "no", index: "no", width: 100, label: "采购单号", sortable: true, align: "left",count:true},
			{name: "statusdescr", index: "statusdescr", width: 100, label: "采购单状态", sortable: true, align: "left",},
			{name: "date", index: "date", width: 90, label: "采购日期", sortable: true, align: "left", formatter: formatDate},
			{name: "advancepaydate", index: "advancepaydate", width: 90, label: "预付款日期", sortable: true, align: "left", formatter: formatDate},
			{name: "arrivedate", index: "arrivedate", width: 90, label: "预计到货日期", sortable: true, align: "left", formatter: formatDate},
			{name: "arrivediff", index: "arrivediff", width: 100, label: "到货剩余日期", sortable: true, align: "left",  },
			{name: "arrivestatusdescr", index: "arrivestatusdescr", width: 80, label: "到货状态", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 80, label: "采购类型", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 87, label: "材料类型1", sortable: true, align: "left"},
			{name: "isservicedescr", index: "isservicedescr", width: 100, label: "是否服务产品", sortable: true, align: "left"},
			{name: "delivtypedescr", index: "delivtypedescr", width: 100, label: "配送地点", sortable: true, align: "left"},
			{name: "warehouse", index: "warehouse", width: 100, label: "仓库名称", sortable: true, align: "left"},
			{name: "custdescr", index: "custdescr", width: 100, label: "客户名称", sortable: true, align: "left"},
			{name: "address", index: "address", width: 180, label: "楼盘地址", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 200, label: "供应商名称", sortable: true, align: "left"},
			{name: "phone1", index: "phone1", width: 119, label: "供应商电话", sortable: true, align: "left"},
			{name: "amountshow", index: "amountshow", width: 80, label: "金额", sortable: true, align: "left", sum: true},
			{name: "remainamountshow", index: "remainamountshow", width: 93, label: "应付/付退余款", sortable: true, align: "left", sum: true},
			{name: "firstamountshow", index: "firstamountshow", width: 91, label: "付/付退订金", sortable: true, align: "left", sum: true},
			{name: "secondamountshow", index: "secondamountshow", width: 94, label: "付/付退到货款", sortable: true, align: "left", sum: true},
			{name: "remarks", index: "remarks", width: 100, label: "备注", sortable: true, align: "left"},
			{name: "ischeckoutdescr", index: "ischeckoutdescr", width: 80, label: "是否结账", sortable: true, align: "left"},
			{name: "ischeckoutstatusdescr", index: "ischeckoutstatusdescr", width: 94, label: "结算单状态", sortable: true, align: "left"},
			{name: "checkoutno", index: "checkoutno", width: 100, label: "结账单号", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 140, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"}				
		],
			gridComplete:function(){
            	var ids = $("#dataTable").getDataIDs();
            	 for(var i=0;i<ids.length;i++){
              	   var rowData = $("#dataTable").getRowData(ids[i]);
              	   if(rowData.arrivediff<=-3){
                     $('#'+ids[i]).find("td").addClass("SelectBG");
              	   }
              	   if(-2<=rowData.arrivediff&&rowData.arrivediff<=0){
                     $('#'+ids[i]).find("td").addClass("SelectBG_yellow");
              	   }
             	 }
         	},
			 onSelectRow : function(id) {
            	var row = selectDataTableRow("dataTable");
            	$("#dataTable_item").jqGrid('setGridParam',{url : "${ctx}/admin/purchase/goPurchJqGrid?puno="+row.no});
            	$("#dataTable_item").jqGrid().trigger('reloadGrid');
            		if('${costRight}'=='0'){
						jQuery("#dataTable_item").setGridParam().hideCol("unitprice").trigger("reloadGrid");
						jQuery("#dataTable_item").setGridParam().hideCol("amount").trigger("reloadGrid");
					}
     	      }
	});
	
	//延期
	$("#delay").on("click",function(){
		var ret = selectDataTableRow();
        	  if (ret) {	
            	Global.Dialog.showDialog("Update",{ 
             	  title:"采购跟踪——延期",
             	  url:"${ctx}/admin/purchase/goPurchDelay",
             	  postData:{pk:ret.maxpk,puno:ret.no},
             	  height: 600,
             	  width:800,
             	  returnFun:goto_query
             	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
           }
	});
	
	$("#print").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("print",{
			title:"采购单——打印",
			url:"${ctx}/admin/purchase/goPrint?no="+ret.no,
			height:300,
			width:500,
		});
	});
	//到货
	$("#arrive").on("click",function(){
		var ret = selectDataTableRow();
       	  if (ret) {	
          	Global.Dialog.showDialog("Update",{ 
            	  title:"采购跟踪——到货",
            	  url:"${ctx}/admin/purchase/goPurchArrive?no="+ret.no,
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
       	  if (ret) {	
       	  var tit;
		 	if(ret.typedescr=='退回'){
		 		tit="采购退回——查看"
		 	}else{
		 		tit="采购单——查看"
		 	}
           		Global.Dialog.showDialog("Update",{ 
	           	  	title:tit,
				  	url:"${ctx}/admin/purchase/goViewNew?id=" + ret.no,
		           	height: 700,
		           	width:1250,
		           	returnFun:goto_query
            	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
           }
	});	
		
		$("#ui-jqgrid-bdiv").css("background-color","yellow");
		
});
/* function doExcel(){
	$.form_submit($("#page_form").get(0), {
		"action": "${ctx}/admin/supplierPurchase/doExcel"
	});
} */

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
								<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
									<span>关闭</span>
								</button>
					</div>
				</div>
			</div>
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value="${purchase.expired }"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">	
							</li>
								<li>
								<label>下单员</label>
								<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }"/>
							</li>
								<li>
								<label>供应商编号</label>
								<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier }"/>
							</li>
								<li>
								<label>剩余到货时间</label>
								<input type="text" id="remainTime" name="remainTime" style="width:160px;" value="-3"/>
							</li>
								<li>
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width: 160px;"  ></select>
							</li>
								<li>
								<label>材料类型2</label>
									<select id="itemType2" name="itemType2" style="width: 160px;" ></select>
							</li>
								<li>
								<label>楼盘地址</label>
								<input type="text" id="address" name="address" 	   style="width:160px;" value="${purchase.address }"  />
							</li>
								<li>
								<label>到货状态</label>
								<house:xtdmMulit id="arriveStatus" dictCode="TPURARVSTATUS" selectedValue="0"></house:xtdmMulit>                     
								</li>
							<li class="search-group">					
								<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${purchase.expired }" onclick="hideExpired(this)" 
								 ${purchase.expired!='T'?'checked':'' } /><p>隐藏过期</p>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							</li>
						</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
				<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<button type="button" class="btn btn-system " id="delay">
							<span>延期</span></button>
						<button type="button" class="btn btn-system " id="arrive">
							<span>到货</span></button>
						<button type="button" class="btn btn-system " id="view">
							<span>查看</span></button>
						<button type="button" class="btn btn-system " onclick="doExcelNow('采购跟踪表','dataTable');">
							<span>输出到Execl</span></button>
						<button type="button" class="btn btn-system " id="print">
							<span>预览</span></button>
				</div>	
			</div>	
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>
				<div id="content-list">
					<table id= "dataTable_item"></table>
				</div> 
			</div>
	</body>	
</html>
