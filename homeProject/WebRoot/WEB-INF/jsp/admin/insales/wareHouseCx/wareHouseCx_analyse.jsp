<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>客户查询详细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">

</style>
<script type="text/javascript">
//计算可用量=所有仓库数量+未到货采购数量-审核状态领料数量-申请状态领料数量-未发货销售数量-样品库数量
function calAvailableNum() {
    $("#availableNum").val(parseFloat($("#qtyCal").val()?$("#qtyCal").val():0)
        +parseFloat($("#purchaseDetailDataTable_qtycal_sum").val()?$("#purchaseDetailDataTable_qtycal_sum").val():0)
        -parseFloat($("#salesInvoiceDetailDataTable_qty_sum").val()?$("#salesInvoiceDetailDataTable_qty_sum").val():0)
        -parseFloat($("#itemAppDetailAppliedDataTable_qty_sum").val()?$("#itemAppDetailAppliedDataTable_qty_sum").val():0)
        -parseFloat($("#itemAppDetailCheckedDataTable_qty_sum").val()?$("#itemAppDetailCheckedDataTable_qty_sum").val():0)
        -parseFloat($("#itemSampleDetailDataTable_qtycal_sum").val()?$("#itemSampleDetailDataTable_qtycal_sum").val():0)
    );
}

$(function(){
        //初始化表格
        var height=$(document).height()-$("#content-list").offset().top-65;
		Global.JqGrid.initJqGrid("salesInvoiceDetailDataTable",{
			url:"${ctx}/admin/wareHouseCx/goSalesInvoiceDetailJqGrid?itcode="+$("#code").val(),
			height:height,
			rowNum:10000,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'no',index : 'no',width : 100,label:'销售单号',align : "left",sortable : false},
		      {name : 'custname',index : 'custname',width : 200,label:'楼盘',align : "left",sortable : false},
		      {name : 'date',index : 'date',width : 150,label:'开单时间',align : "left",formatter: formatTime,sortable : false},
		      {name : 'qty',index : 'qty',width : 100,label:'数量',align : "right",sortable : false,sum:true}
            ],
            gridComplete: function () {
                calAvailableNum(); //计算可用量
            }
		});
			Global.JqGrid.initJqGrid("itemAppDetailCheckedDataTable",{
			url:"${ctx}/admin/wareHouseCx/goItemAppDetailCheckedJqGrid?itemCode="+$("#code").val(),
			height:height,
			width:940,
			rowNum:10000,  
            autowidth: false,
            shrinkToFit: true,
            styleUI: 'Bootstrap',    
			colModel : [
			  {name : 'no',index : 'no',width : 100,label:'领料单号',align : "left",sortable : false},
		      {name : 'address',index : 'address',width : 200,label:'楼盘',align : "left",sortable : false},
		      {name : 'date',index : 'date',width : 150,label:'开单时间',align : "left",formatter: formatTime,sortable : false},
		      {name : 'fixareadescr',index : 'fixareadescr',width : 100,label:'装修区域',align : "left",sortable : false},
		      {name : 'qty',index : 'qty',width : 100,label:'领料数量',align : "right",sortable : false,sum:true},
		      {name : 'sendqty',index : 'sendqty',width : 100,label:'已发货数量',align : "right",sortable : false,sum:true}
            ],
            gridComplete: function () {
                calAvailableNum(); //计算可用量
            }
		});
				Global.JqGrid.initJqGrid("purchaseDetailDataTable",{
			url:"${ctx}/admin/wareHouseCx/goPurchaseDetailJqGrid?itcode="+$("#code").val(),
			height:height,
			width:940,
			rowNum:10000,  
            autowidth: false,
            shrinkToFit: true,
            styleUI: 'Bootstrap',    
			colModel : [
			  {name : 'no',index : 'no',width : 100,label:'采购单号',align : "left",sortable : false},
		      {name : 'address',index : 'address',width : 200,label:'楼盘',align : "left",sortable : false},
		      {name : 'date',index : 'date',width : 150,label:'开单时间',align : "left",formatter: formatTime,sortable : false},
		      {name : 'arrivedate',index : 'arrivedate',width : 150,label:'到货时间',align : "left",formatter: formatTime,sortable : false},
		      {name : 'qtycal',index : 'qtycal',width : 100,label:'采购数量',align : "right",sortable : false,sum:true},
		      {name : 'arrivqty',index : 'arrivqty',width : 100,label:'已到货数量',align : "right",sortable : false,sum:true}
            ],
            gridComplete: function () {
                calAvailableNum(); //计算可用量
            }
		});
				Global.JqGrid.initJqGrid("itemAppDetailAppliedDataTable",{
			url:"${ctx}/admin/wareHouseCx/goItemAppDetailAppliedJqGrid?itemCode="+$("#code").val(),
			height:height,
			width:940,
			rowNum:10000,  
            autowidth: false,
            shrinkToFit: true,
            styleUI: 'Bootstrap',    
			colModel : [
			  {name : 'no',index : 'no',width : 100,label:'领料单号',align : "left",sortable : false},
		      {name : 'address',index : 'address',width : 200,label:'楼盘',align : "left",sortable : false},
		      {name : 'date',index : 'date',width : 150,label:'开单时间',align : "left",formatter: formatTime,sortable : false},
		      {name : 'fixareadescr',index : 'fixareadescr',width : 100,label:'装修区域',align : "left",sortable : false},
		      {name : 'qty',index : 'qty',width : 100,label:'领料数量',align : "right",sortable : false,sum:true},
		      {name : 'sendqty',index : 'sendqty',width : 100,label:'已发货数量',align : "right",sortable : false,sum:true}
            ],
            gridComplete: function () {
                calAvailableNum(); //计算可用量
            }
           
		});
			Global.JqGrid.initJqGrid("itemSampleDetailDataTable",{
			url:"${ctx}/admin/wareHouseCx/goItemSampleDetailJqGrid?itCode="+$("#code").val(),
			height:height,
			width:940,
			rowNum:10000,  
            autowidth: false,
            shrinkToFit: true,
            styleUI: 'Bootstrap',    
			footerrow:false,
			colModel : [
		      {name : 'qtycal',index : 'qtycal',width : 100,label:'样品数量',align : "right",sortable : false,sum:true}
            ],
            gridComplete: function () {
                calAvailableNum(); //计算可用量
            }
		});
		// 库位余额表格
		Global.JqGrid.initJqGrid("WHPosiBalDataTable", {
			url:"${ctx}/admin/wareHouseCx/goWHPosiBalJqGrid",
			postData:{itCode:$("#code").val()},
			height:height,
			// width:940,
			rowNum:10000,
			autowidth: false,
			shrinkToFit: true,
			styleUI: "Bootstrap",
			// footerrow:false,
			colModel : [
				{name : "code", index : "code", width : 50, label : "仓库编号", sortable : true, align : "left", hidden : true},
				{name : "whdescr", index : "whdescr", width : 100, label : "仓库名称", sortable : true, align : "left"},
				{name : "pk", index : "pk", width : 50, label : "库位pk", sortable : true, align : "left", hidden : true},
				{name : "whposidescr", index : "whposidescr", width : 100, label : "库位名称", sortable : true, align : "left"},
				{name : "itcode", index : "itcode", width : 50, label : "材料编号", sortable : true, align : "left", hidden : true},
				{name : "qtycal",index : "qtycal",width : 100,label:"已上架数量",align : "right",sortable : false,sum : true}
			],
    	});
	/* 20180429 mark by xzp 异步执行可能造成审核状态领料数量等还没算完就去计算可用量，造成数据不对，改在表格的gridComplete事件计算可用量
	setTimeout(function(){
	//计算可用量
	$("#availableNum").val(parseFloat($("#qtyCal").val()?$("#qtyCal").val():0)+parseFloat($("#purchaseDetailDataTable_qtycal_sum").val()?$("#purchaseDetailDataTable_qtycal_sum").val():0)-parseFloat($("#salesInvoiceDetailDataTable_qty_sum").val()?$("#salesInvoiceDetailDataTable_qty_sum").val():0)-parseFloat($("#itemAppDetailAppliedDataTable_qty_sum").val()?$("#itemAppDetailAppliedDataTable_qty_sum").val():0)-parseFloat($("#itemAppDetailCheckedDataTable_qty_sum").val()?$("#itemAppDetailCheckedDataTable_qty_sum").val():0)-parseFloat($("#itemSampleDetailDataTable_qtycal_sum").val()?$("#itemSampleDetailDataTable_qtycal_sum").val():0));
	},400);	
	*/
 
});
</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
   	 <div class="panel-body">
      <div class="btn-group-xs" >
      <button type="button" class="btn btn-system"  onclick="closeWin(false)">关闭</button>
      </div>
  		 </div>
	</div>

		 <div class="panel panel-info">  
                <div class="panel-body">
		<form role="form" class="form-search"  >
	    	<ul  class="ul-form">
	    	<li>
	    		<label>产品编号</label>
	    		<input type="text" id="code" name="code"  value="${item.code}" readonly="readonly"/>
	    	</li>
	    	<li>
	    		<label>未发货销售数量</label>
	    		<input type="text" id="salesInvoiceDetailDataTable_qty_sum"    readonly="readonly"/>
	    	</li>
	    	<li>
	    		<label>申请状态领料数量</label>
	    		<input type="text" id="itemAppDetailAppliedDataTable_qty_sum"  readonly="readonly"/>
	    	</li>
	    	<li>
	    		<label>产品名称</label>
	    			<input type="text" id="descr" name="descr" style="width:160px;" value="${item.descr}" readonly="readonly"/>
	    	</li>
						
						
				<li>
					<label>审核状态领料数量</label>
					
					<input type="text" id="itemAppDetailCheckedDataTable_qty_sum"  readonly="readonly"/>
					</li>
					<li>
					<label>可用数量</label>
					
					<input type="text" id="availableNum"    readonly="readonly"/>
					</li>
				
					<li>
					<label>所有仓库数量</label>
					
					<input type="text" id="qtyCal" name="qtyCal"  value="${item.allQty }" readonly="readonly"/>
					</li>
					<li>
					<label>未到货采购数量</label>
					
					<input type="text" id="purchaseDetailDataTable_qtycal_sum"    readonly="readonly"/>
					</li>
					<li>
					<label>样品库数量</label>
					
					<input type="text" id="itemSampleDetailDataTable_qtycal_sum"   readonly="readonly"  />
					</li>
				</ul>
			</form>
		 </div>
		</div>
			<!--标签页内容 -->
	<div class="container-fluid" >
    <ul class="nav nav-tabs" >
        <li class="active"><a href="#tab_salesInvoiceDetail" data-toggle="tab">未发货销售数量明细</a></li>
        <li class=""><a href="#tab_itemAppDetailChecked" data-toggle="tab">审核状态领料数量明细</a></li>
        <li class=""><a href="#tab_purchaseDetail" data-toggle="tab">未到货采购数量明细</a></li>
        <li class=""><a href="#tab_itemAppDetailApplied" data-toggle="tab">申请状态领料数量明细</a></li>
        <li class=""><a href="#tab_itemSampleDetail" data-toggle="tab" style="display: none">样品库数量明细</a></li>
        <li class=""><a href="#tab_WHPosiBal" data-toggle="tab">库存余额</a></li>
    </ul>
<div class="tab-content" >
    <div id="tab_salesInvoiceDetail" class="tab-pane fade in active">
   <div id="content-list">
				<table id= "salesInvoiceDetailDataTable"></table>
			</div> 
</div>
    <div id="tab_itemAppDetailChecked" class="tab-pane fade "> 		
	  <div id="content-list2">
				<table id= "itemAppDetailCheckedDataTable"></table>
			</div> 
	    </div>
	     <div id="tab_purchaseDetail" class="tab-pane fade "> 		
	          <div id="content-list3">
				<table id= "purchaseDetailDataTable"></table>
			</div> 
	    </div>
	     <div id="tab_itemAppDetailApplied" class="tab-pane fade "> 		
         <div id="content-list4" >
       
				<table id= "itemAppDetailAppliedDataTable"></table>
			</div> 
	    </div>
	      <div id="tab_itemSampleDetail" class="tab-pane fade "> 		
         <div id="content-list5" >
				<table id= "itemSampleDetailDataTable"></table>
			</div> 
	    </div>
		<div id="tab_WHPosiBal" class="tab-pane fade"><!-- 新增库存余额页签 add by zb -->	
			<div id="content-list6" >
				<table id= "WHPosiBalDataTable"></table>
			</div>
		</div>
</div>
</div>
</div>
</body>
</html>
