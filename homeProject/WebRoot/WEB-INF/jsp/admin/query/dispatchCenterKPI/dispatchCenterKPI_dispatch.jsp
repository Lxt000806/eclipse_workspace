<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>调度下单明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
function doExcel(){
    if ($("ul li a[href='#tab1']").parent().hasClass("active")){
			var url = "${ctx}/admin/dispatchCenterKPI/doExcelDispatchDetail";
	    doExcelAll(url,'dispatchDataTable');
	}else if  ($("ul li a[href='#tab2']").parent().hasClass("active")){
			var url = "${ctx}/admin/dispatchCenterKPI/doExcelMainCheckDetail";
	    doExcelAll(url,'mainCheckDataTable');
	}	
}
  
$(function(){ 
	//初始化表格
	Global.JqGrid.initJqGrid("dispatchDataTable",{
		url:"${ctx}/admin/dispatchCenterKPI/goJqGridDispatchDetail",
		postData:$("#page_form").jsonForm(), 
		height:500-$("#content-list2").offset().top-70,  
        styleUI: 'Bootstrap',
        autowidth:false,
        width:800,  
        rowNum:100000,  
		pager :'1', 
		colModel : [
		  	{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
			{name: "custcount", index: "custcount", width: 80, label: "套数计算", sortable: true, align: "right", sum: true},
			{name: "comedate", index: "comedate", width: 100, label: "工种进场时间", sortable: true, align: "left", formatter: formatTime},
			{name: "confirmbegin", index: "confirmbegin", width: 100, label: "开工时间", sortable: true, align: "left",formatter: formatTime},
	    	{name: "ispartdecoratedescr", index: "ispartdecoratedescr", width: 100, label: "是否局装", sortable: true, align: "left"},
	   		{name: "contractfee", index: "contractfee", width: 80, label: "合同金额", sortable: true, align: "right", sum: true},
	    ]
	});
	 Global.JqGrid.initJqGrid("mainCheckDataTable",{
			url:"${ctx}/admin/dispatchCenterKPI/goJqGridMainCheckDetail",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list2").offset().top-70,
			styleUI: 'Bootstrap',
			autowidth:false,
		    width:800,
		    rowNum:100000,  
			pager :'1',   
			colModel : [
			  {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
		  	  {name: 'checkamount', index: 'checkamount', width: 80, label: "结算金额", sortable: true, align: "right", sum: true},
		  	  {name : 'custcheckdate',index : 'custcheckdate',width : 90,label:'结算时间',sortable : true,align : "left",formatter: formatDate},
            ]
		});
    
});

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="query-form" hidden>
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="dateFrom" name="dateFrom"  value="<fmt:formatDate value="${customer.dateFrom}" pattern='yyyy-MM-dd'/>" />
				<input type="hidden" id="dateTo" name="dateTo"  value="<fmt:formatDate value="${customer.dateTo}" pattern='yyyy-MM-dd'/>" />
				<input type="hidden" id="department2" name="department2" value="${customer.department2}" /> 
				<input type="hidden" id="prjRegionCode" name="prjRegionCode" value="${customer.prjRegionCode}" /> 
				<input type="hidden" name="jsonString" value="" />
			</form>
		</div>
		<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	     					<button type="button" class="btn btn-system "   onclick="doExcel()">导出excel</button>
							<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
		  </div>
		<div  class="container-fluid" >  
		     <ul class="nav nav-tabs" >
		        <li id="tab1_li" class="active"><a href="#tab1" data-toggle="tab" >调度明细</a></li>  
		        <li id="tab2_li"  class=""><a href="#tab2" data-toggle="tab">主材结算明细</a></li>
		      
		    </ul>  
		    <div class="tab-content">  
				<div id="tab1"  class="tab-pane fade in active"> 
					<div id="content-list">
						<table id= "dispatchDataTable" ></table>
						<div id="dispatchDataTablePager"></div>
					</div> 
				</div>  
	
				<div id="tab2"  class="tab-pane fade " > 
					<div id="content-list2">
					  <table id= "mainCheckDataTable" ></table>
					   <div id="mainCheckDataTablePager"></div>
				   </div> 
				</div>
			</div>	
		</div>
	</div>
</body>
</html>

