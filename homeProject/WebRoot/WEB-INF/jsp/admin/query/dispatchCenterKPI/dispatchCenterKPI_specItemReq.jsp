<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>调度定责明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
function doExcel(){
    if ($("ul li a[href='#tab1']").parent().hasClass("active")){
			var url = "${ctx}/admin/dispatchCenterKPI/doExcelSpecItemReqDetail";
	    doExcelAll(url,'specItemReqDataTable');
	}else if  ($("ul li a[href='#tab2']").parent().hasClass("active")){
			var url = "${ctx}/admin/dispatchCenterKPI/doExcelFixDutyDetail";
	    doExcelAll(url,'fixDutyDataTable');
	}	
	
}
  
$(function(){ 
	//初始化表格
	Global.JqGrid.initJqGrid("specItemReqDataTable",{
			url:"${ctx}/admin/dispatchCenterKPI/goJqGridSpecItemReqDetail",
		postData:$("#page_form").jsonForm(), 
		height:500-$("#content-list2").offset().top-80,  
        styleUI: 'Bootstrap',
        width:800,
		rowNum:100000,  
		pager :'1',  
		colModel : [
		  {name : 'address',index : 'address',width : 160,label:'楼盘',sortable : true,align : "left"},
		  {name : 'specitemreqdate',index : 'specitemreqdate',width : 90,label:'解单时间',sortable : true,align : "left",formatter: formatDate},
	    ]
	});
	 Global.JqGrid.initJqGrid("fixDutyDataTable",{
			url:"${ctx}/admin/dispatchCenterKPI/goJqGridFixDutyDetail",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list2").offset().top-80,
			styleUI: 'Bootstrap',
		    width:800,  
			rowNum:100000,  
			pager :'1', 
			colModel : [
			  	{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
				{name: "cfmdate", index: "cfmdate", width: 100, label: "质检复核时间", sortable: true, align: "left", formatter: formatTime},
				{name: "dutydate", index: "dutydate", width: 100, label: "定责时间", sortable: true, align: "left", formatter: formatTime},
		    	{name: "isontime", index: "isontime", width: 100, label: "是否及时", sortable: true, align: "left"}
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
				<input  id="department2" name="department2" value="${customer.department2 }" />
				<input  id="dateFrom" name="dateFrom"  value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
				<input  id="dateTo" name="dateTo"  value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
			</form>
		</div>
		<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	               		<house:authorize authCode="QDPH_EXCEL">
	     					<button type="button" class="btn btn-system "   onclick="doExcel()">导出excel</button>
							<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
						</house:authorize>
					</div>
				</div>
		  </div>
		<div  class="container-fluid" >  
		     <ul class="nav nav-tabs" >
		        <li id="tab1_li" class="active"><a href="#tab1" data-toggle="tab" >解单明细</a></li>  
		        <li id="tab2_li"  class=""><a href="#tab2" data-toggle="tab">定责明细</a></li>
		      
		    </ul>  
		    <div class="tab-content">  
				<div id="tab1"  class="tab-pane fade in active"> 
					<div id="content-list">
						<table id= "specItemReqDataTable" ></table>
						<div id="specItemReqDataTablePager"></div>
					</div> 
				</div>  
	
				<div id="tab2"  class="tab-pane fade " > 
					<div id="content-list2">
					  <table id= "fixDutyDataTable" ></table>
					   <div id="fixDutyDataTablePager"></div>
				   </div> 
				</div>
			</div>	
		</div>
	</div>
</body>
</html>

