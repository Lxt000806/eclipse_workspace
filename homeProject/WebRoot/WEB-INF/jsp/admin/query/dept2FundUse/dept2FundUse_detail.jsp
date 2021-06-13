<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>资金占用明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
function goto_query(table,form){
	$("#"+table).jqGrid("setGridParam",{postData:$("#"+form).jsonForm(),page:1}).trigger("reloadGrid");
}

function doExcel(){
    if ($(".nav-tabs li a[href='#tab1']").parent().hasClass("active")){
			var url = "${ctx}/admin/dept2FundUse/doPrepayFeeExcel";
	    doExcelAll(url,'prepayFeedataTable');
	}else if  ($(".nav-tabs li a[href='#tab2']").parent().hasClass("active")){
			var url = "${ctx}/admin/dept2FundUse/doPurArrFeeExcel";
	    doExcelAll(url,'purArrFeedataTable');
	}else if  ($(".nav-tabs li a[href='#tab3']").parent().hasClass("active")){
			var url = "${ctx}/admin/dept2FundUse/doOtherFeeExcel";
	    doExcelAll(url,'otherFeedataTable');
	}else if  ($(".nav-tabs li a[href='#tab4']").parent().hasClass("active")){
			var url = "${ctx}/admin/dept2FundUse/doPreAmountExcel";
	    doExcelAll(url,'preAmountdataTable');
   	}else if  ($(".nav-tabs li a[href='#tab5']").parent().hasClass("active")){
			var url = "${ctx}/admin/dept2FundUse/doLaborFeeExcel";
	    doExcelAll(url,'laborFeedataTable');   
	}
	
}
$(function(){
	//初始化表格
    var  height=$(document).height()-$("#content-list").offset().top-100;
	Global.JqGrid.initJqGrid("prepayFeedataTable",{
		url:"${ctx}/admin/dept2FundUse/goPrepayFeeJqGrid",
		postData:$("#page_form").jsonForm(), 
		height:height,  
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970,  
		colModel : [
		  {name : 'no',index : 'no',width : 120,label:'预付单号',sortable : true,align : "left"},
		  {name : 'typedescr',index : 'typedescr',width : 100,label:'预付类型',sortable : true,align : "left"},
		  {name : 'supplierdescr',index : 'supplierdescr',width : 90,label:'供应商',sortable : true,align : "left"},
		  {name: "amount", index: "amount", width: 80, label: "金额", sortable: true, align: "right", sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
		  {name : 'paydate',index : 'paydate',width : 140,label:'付款时间',sortable : true,align : "left",formatter: formatTime},
		  {name : 'puno',index : 'puno',width : 90,label:'采购单',sortable : true,align : "left"},
		  {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left"}
	          ]
	});
	Global.JqGrid.initJqGrid("purArrFeedataTable",{
		url:"${ctx}/admin/dept2FundUse/goPurArrFeeJqGrid",
		postData:$("#page_form").jsonForm(),
		height:height, 
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970, 
		colModel : [
		  {name : 'puno',index : 'puno',width : 100,label:'采购单号',sortable : true,align : 'left'},
		  {name : 'supplierdescr',index : 'supplierdescr',width : 150,label:'供应商',sortable : true,align : 'left'},
		  {name: "itemamount", index: "itemamount", width: 100, label: "材料金额", sortable: true, align: "right", sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
		  {name: "remainfirstamount", index: "remainfirstamount", width: 100, label: "定金抵扣", sortable: true, align: "right", sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
		  {name: "purarrfee", index: "purarrfee", width: 100, label: "入库金额", sortable: true, align: "right", sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
		  {name : 'checkoutno',index : 'checkoutno',width : 100,label:'结算单号',sortable : true,align : 'left'}
	          ]
	});
	Global.JqGrid.initJqGrid("otherFeedataTable",{
		url:"${ctx}/admin/dept2FundUse/goOtherFeeJqGrid",
		postData:$("#page_form").jsonForm(),
		height:height, 
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970, 
		colModel : [
	        {name : 'puno',index : 'puno',width : 100,label:'采购单号',sortable : true,align : 'left'},
	        {name : 'supplierdescr',index : ' supplierdescr',width : 100,label:'供应商',sortable : true,align : 'left'},
	        {name: 'othercost', index: 'othercost', width: 100, label: '其他费用', sortable: true, align: 'right', sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
	        {name: 'othercostadj', index: 'othercostadj', width: 150, label: '其他费用调整', sortable: true, align: 'right', sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
	        {name: 'otherfee', index: 'otherfee', width: 80, label: '合计', sortable: true, align: 'right', sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
	        {name : 'checkoutno',index : 'checkoutno',width : 100,label:'结算单号',sortable : true,align : 'left'},
	        {name : 'paydate',index : 'paydate',width : 140,label:'付款时间',sortable : true,align : "left",formatter: formatTime}
	   
	          ]
	});				
	Global.JqGrid.initJqGrid("preAmountdataTable",{
		url:"${ctx}/admin/dept2FundUse/goPreAmountJqGrid",
		postData:$("#page_form").jsonForm(),
		height:height,   
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970, 
		colModel : [
	        {name : 'no',index : 'no',width : 100,label:'付款单号',sortable : true,align : 'left'},
	        {name : 'supplierdescr',index : ' supplierdescr',width : 100,label:'供应商',sortable : true,align : 'left'},
	        {name: 'preamount', index: 'preamount', width: 100, label: '预付抵扣', sortable: true, align: 'right', sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
	        {name : 'checkoutno',index : 'checkoutno',width : 100,label:'结算单号',sortable : true,align : 'left'},
	              {name : 'paydate',index : 'paydate',width : 140,label:'付款时间',sortable : true,align : "left",formatter: formatTime},
	          ]
	});
	Global.JqGrid.initJqGrid("laborFeedataTable",{
		url:"${ctx}/admin/dept2FundUse/goLaborFeeJqGrid",
		postData:$("#page_form").jsonForm(),
		height:height,   
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970, 
		colModel : [
			{name : 'no',index : 'no',width : 100,label:'费用单号',sortable : true,align : "left"},
		    {name : 'feetypedescr',index : 'feetypedescr',width : 100,label:'费用类型',sortable : true,align : "left"},
		    {name : 'appsendno',index : 'appsendno',width : 100,label:'送货单号',sortable : true,align : "left"},
	        {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : 'left'},
	        {name: 'amount', index: 'amount', width: 80, label: '金额', sortable: true, align: 'right', sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
	              {name : 'paydate',index : 'paydate',width : 140,label:'付款时间',sortable : true,align : "left",formatter: formatTime}
	          
	          ]
	});
});


</script>
</head>
<body>
	<div class="body-box-form">
		<div class="query-form" hidden>
			<form action="" method="post" id="page_form" >
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden"  id="dateFrom1" name="dateFrom1"  value="<fmt:formatDate value='${purchase.dateFrom1}' pattern='yyyy-MM-dd'/>" />
				<input  type="hidden" id="dateTo1" name="dateTo1"  value="<fmt:formatDate value='${purchase.dateTo1}' pattern='yyyy-MM-dd'/>" />
				<input  type="hidden" id="supplierDepartment2" name="supplierDepartment2" value="${purchase.supplierDepartment2}" />		
			</form>
		</div>
		
		<div class="clear_float"> </div>
		
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
               		<house:authorize authCode="DEPT2FUNDUSE_EXCEL">
     					<button type="button" class="btn btn-system "   onclick="doExcel()">导出excel</button>
     					<button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</house:authorize>
				</div>
			</div>
		</div>
		<div  class="container-fluid" >  
		     <ul class="nav nav-tabs" >
		        <li id="tab1_li" class="active"><a href="#tab1" data-toggle="tab">预付金</a></li>  
		        <li id="tab2_li"  class=""><a href="#tab2" data-toggle="tab">采购入库</a></li>
		        <li id="tab3_li" class=""><a href="#tab3" data-toggle="tab">采购单结算差价</a></li>  
		        <li id="tab4_li" class=""><a href="#tab4" data-toggle="tab">预付抵扣</a></li> 
		        <li id="tab5_li" class=""><a href="#tab5" data-toggle="tab">人工费用</a></li> 
		    </ul>  
		    <div class="tab-content">  
				<div id="tab1"  class="tab-pane fade in active"> 
					<div id="content-list">
						<table id= "prepayFeedataTable"></table>
						<div id="prepayFeedataTablePager"></div>
					</div> 
				</div>  
				<div id="tab2"  class="tab-pane fade "> 
					<div id="content-list2">
					  <table id= "purArrFeedataTable"></table>
					   <div id="purArrFeedataTablePager"></div>
				   </div> 
				</div>
				<div id="tab3"  class="tab-pane fade "> 
					<div id="content-list3">
						<table id= "otherFeedataTable"></table>
						<div id="otherFeedataTablePager"></div>
					</div> 
				</div>
				<div id="tab4"  class="tab-pane fade "> 
					<div id="content-list4">
						<table id= "preAmountdataTable"></table>
						<div id="preAmountdataTablePager"></div>
					</div> 
				</div>
				<div id="tab5"  class="tab-pane fade "> 
					<div id="content-list5">
						<table id= "laborFeedataTable"></table>
						<div id="laborFeedataTablePager"></div>
					</div> 
				</div>
			</div>	
		</div>
	</div>
</body>
</html>

