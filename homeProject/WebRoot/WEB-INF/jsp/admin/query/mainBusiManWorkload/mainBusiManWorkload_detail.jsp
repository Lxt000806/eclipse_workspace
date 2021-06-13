<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>主材管家工作量明细</title>
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
    if ($("ul li a[href='#tab1']").parent().hasClass("active")){	
		doExcelNow("主材管家工作量_开工明细","beginDataTable","page_form");
	}else if  ($("ul li a[href='#tab2']").parent().hasClass("active")){
		doExcelNow("主材管家工作量_在建明细","buildingDataTable","page_form");
	}else if  ($("ul li a[href='#tab3']").parent().hasClass("active")){
		doExcelNow("主材管家工作量_完工明细","completedDataTable","page_form");
	}else if  ($("ul li a[href='#tab4']").parent().hasClass("active")){
		doExcelNow("主材管家工作量_第一次确认明细","firstConfirmDataTable","page_form");
   	}else if  ($("ul li a[href='#tab5']").parent().hasClass("active")){
		doExcelNow("主材管家工作量_第二次确认明细","secondConfirmDataTable","page_form"); 
	}else if  ($("ul li a[href='#tab6']").parent().hasClass("active")){
		doExcelNow("主材管家工作量_变更明细","chgDetailDataTable","page_form"); 
	}	
}
$(function(){ 
	//初始化表格
	Global.JqGrid.initJqGrid("beginDataTable",{
			url:"${ctx}/admin/mainBusiManWorkload/goBeginJqGrid",
		postData:$("#page_form").jsonForm(), 
		height:500-$("#content-list2").offset().top-100,  
        width:1170,  
        rowNum:100000,  
		pager :'1',
		colModel : [
		 	 {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left",count: true},
		 	 {name : 'custtypedescr',index : 'custtypedescr',width : 100,label:'客户类型',sortable : true,align : "left"},
		     {name : 'area',index : 'area',width : 100,label:'面积',sortable : true,align : "left"},
		     {name : 'confirmbegin',index :'confirmbegin',width : 100,label:'分配时间',sortable : true,align : "left",formatter: formatDate},
		     {name : 'prjitemdescr',index :'prjitemdescr',width : 100,label:'当前阶段',sortable : true,align : "left"},
		     {name : 'confirmdate',index :'confirmdate',width : 100,label:'第一次确认时间',sortable : true,align : "left",formatter: formatDate},
	    ]
	});
	 Global.JqGrid.initJqGrid("buildingDataTable",{
			url:"${ctx}/admin/mainBusiManWorkload/goBuildingJqGrid",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list2").offset().top-100,
		    width:1170,  
		    rowNum:100000,  
			pager :'1',
			colModel : [
			 	 {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left",count: true},
			 	 {name : 'custtypedescr',index : 'custtypedescr',width : 100,label:'客户类型',sortable : true,align : "left"},
			     {name : 'area',index : 'area',width : 100,label:'面积',sortable : true,align : "left"},
			     {name : 'confirmbegin',index :'confirmbegin',width : 100,label:'开工时间',sortable : true,align : "left",formatter: formatDate},
			     {name : 'prjitemdescr',index :'prjitemdescr',width : 100,label:'当前阶段',sortable : true,align : "left"},
				 {name : 'chgnum',index :'chgnum',width : 100,label:'变更次数',sortable : true,align : "left"},
				 {name : 'confitemdescr',index :'confitemdescr',width : 100,label:'当前确认阶段',sortable : true,align : "left"},
		    
		    ]
		});
		Global.JqGrid.initJqGrid("completedDataTable",{
			url:"${ctx}/admin/mainBusiManWorkload/goCompletedJqGrid",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list3").offset().top-100,
		    width:1170,  
		    rowNum:100000,  
			pager :'1',
			colModel : [
			 	 {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left",count: true},
			 	 {name : 'custtypedescr',index : 'custtypedescr',width : 100,label:'客户类型',sortable : true,align : "left"},
			     {name : 'area',index : 'area',width : 100,label:'面积',sortable : true,align : "left"},
			     {name : 'confirmbegin',index :'confirmbegin',width : 100,label:'开工时间',sortable : true,align : "left",formatter: formatDate},
			   	 {name : 'enddate',index :'enddate',width : 100,label:'完工时间',sortable : true,align : "left",formatter: formatDate}, 
		    
		    ]
		});
        Global.JqGrid.initJqGrid("firstConfirmDataTable",{
			url:"${ctx}/admin/mainBusiManWorkload/goFirstConfirmJqGrid",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list4").offset().top-100,
			width:1170, 
			rowNum:100000,  
			pager :'1', 
			colModel : [
			 	 {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left",count: true},
			 	 {name : 'custtypedescr',index : 'custtypedescr',width : 100,label:'客户类型',sortable : true,align : "left"},
			     {name : 'area',index : 'area',width : 100,label:'面积',sortable : true,align : "left"},
			   	 {name : 'confirmdate',index :'confirmdate',width : 120,label:'一次确认完成时间',sortable : true,align : "left",formatter: formatDate},
			   	 {name : 'enddate',index :'enddate',width : 120,label:'一次确认应完成时间',sortable : true,align : "left",formatter: formatDate},
			   	 {name : 'isontime',index :'isontime',width : 100,label:'是否及时',sortable : true,align : "left"}
		    ]
		});
   
        Global.JqGrid.initJqGrid("secondConfirmDataTable",{
			url:"${ctx}/admin/mainBusiManWorkload/goSecondConfirmJqGrid", 
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list5").offset().top-100,
			width:1170,  
		    rowNum:100000,  
			pager :'1',
			colModel : [
				 {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left",count: true},
			 	 {name : 'custtypedescr',index : 'custtypedescr',width : 100,label:'客户类型',sortable : true,align : "left"},
			     {name : 'area',index : 'area',width : 100,label:'面积',sortable : true,align : "left"},
			   	 {name : 'confirmdate',index :'confirmdate',width : 120,label:'二次确认完成时间',sortable : true,align : "left",formatter: formatDate},
			   	 {name : 'enddate',index :'enddate',width : 120,label:'二次确认应完成时间',sortable : true,align : "left",formatter: formatDate},
           		 {name : 'isontime',index :'isontime',width : 100,label:'是否及时',sortable : true,align : "left"},
            ]
		});
         Global.JqGrid.initJqGrid("chgDetailDataTable",{
			url:"${ctx}/admin/mainBusiManWorkload/goChgJqGrid",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list6").offset().top-100,
			width:1170, 
		    rowNum:100000,  
			pager :'1', 
			colModel : [
				 {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left",count: true},
			 	 {name : 'custtypedescr',index : 'custtypedescr',width : 100,label:'客户类型',sortable : true,align : "left"},
			     {name : 'area',index : 'area',width : 80,label:'面积',sortable : true,align : "right"},
			     {name : 'no',index : 'no',width : 100,label:'变更单号',sortable : true,align : "left"},
			   	 {name : 'confirmdate',index :'confirmdate',width : 120,label:'变更审核时间',sortable : true,align : "left",formatter: formatDate},
			   	 {name: 'amount', index:'amount', width: 85, label: '变更金额', sortable: true,sorttype:'float', align: "right", sum: true},
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
				<input  id="expired" name="expired" value="${customer.expired }" />
				<input  id="empCode" name="empCode" value="${customer.empCode}" />
				<input  id="custType" name="custType" value="${customer.custType }" />
				<input  id="dateFrom" name="dateFrom"  value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
				<input  id="dateTo" name="dateTo"  value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
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
		        <li id="tab1_li" class="active"><a href="#tab1" data-toggle="tab" >开工明细</a></li>  
		        <li id="tab2_li"  class=""><a href="#tab2" data-toggle="tab" >在建明细</a></li>
		        <li id="tab3_li" class=""><a href="#tab3" data-toggle="tab" >完工明细</a></li>  
		        <li id="tab4_li" class=""><a href="#tab4" data-toggle="tab" >第一次确认明细</a></li> 
		        <li id="tab5_li" class=""><a href="#tab5" data-toggle="tab" >第二次确认明细</a></li> 
		        <li id="tab6_li" class=""><a href="#tab6" data-toggle="tab" >变更明细</a></li> 
		    </ul>  
		    <div class="tab-content">  
				<div id="tab1"  class="tab-pane fade in active"> 
					<div id="content-list">
						<table id= "beginDataTable" ></table>
						<div id="beginDataTablePager"></div>
					</div> 
				</div>  
	
				<div id="tab2"  class="tab-pane fade " > 
					<div id="content-list2">
					  <table id= "buildingDataTable" ></table>
					   <div id="buildingDataTablePager"></div>
				   </div> 
				</div>
				<div id="tab3"  class="tab-pane fade " > 
					<div id="content-list3">
						<table id= "completedDataTable"></table>
						<div id="completedDataTablePager"></div>
					</div> 
				</div>
				<div id="tab4"  class="tab-pane fade " > 
					<div id="content-list4">
						<table id= "firstConfirmDataTable"></table>
						<div id="firstConfirmDataTablePager"></div>
					</div> 
				</div>
				<div id="tab5"  class="tab-pane fade " > 
					<div id="content-list5">
						<table id= "secondConfirmDataTable"></table>
						<div id="secondConfirmDataTablePager"></div>
					</div> 
				</div>
				<div id="tab6"  class="tab-pane fade " > 
					<div id="content-list6">
						<table id= "chgDetailDataTable"></table>
						<div id="chgDetailDataTablePager"></div> 
					</div> 
				</div>
			</div>	
		</div>
	</div>
</body>
</html>

