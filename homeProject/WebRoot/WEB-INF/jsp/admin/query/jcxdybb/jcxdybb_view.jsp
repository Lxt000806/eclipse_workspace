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
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_llmx_jcll",{
		url:"${ctx}/admin/jcxdybb/goJcllJqGrid",
		postData:$("#page_form").jsonForm(),
		height:450,
		rowNum: 10000,
		pager:"1",
		styleUI:"Bootstrap",
		colModel : [
	   		{name: "no", index: "no", width: 80, label: "领料单号", sortable: true, align: "left", count: true},
			{name: "confirmdate", index: "confirmdate", width: 125, label: "审核时间", sortable: true, align: "left" ,formatter: formatTime},
			{name: "jccpname", index: "jccpname", width: 90, label: "集成成品名称", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
			{name: "clname", index: "clname", width: 300, label: "材料名称", sortable: true, align: "left"},
			{name: "llqty", index: "llqty", width: 80, label: "领料数量", sortable: true, align: "right"},
			{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable: true, align: "right"},
			{name: "markup", index: "markup", width: 60, label: "折扣", sortable: true, align: "right"},
			{name: "jsje", index: "jsje", width: 70, label: "决算金额", sortable: true, align: "right",sum:true},
			{name: "clxqqty", index: "clxqqty", width: 90, label: "材料需求数量", sortable: true, align: "right"},
			{name: "lineamount", index: "lineamount", width: 90, label: "材料需求总价", sortable: true, align: "right", sum: true},
         ]
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_llmx_cgll",{
		url:"${ctx}/admin/jcxdybb/goCgllJqGrid",
		postData:$("#page_form").jsonForm(),
		height:450,
		rowNum: 10000,
		styleUI:"Bootstrap",
		rowNum: 10000,
		pager:"1",
		colModel : [
		    {name: "no", index: "no", width: 80, label: "领料单号", sortable: true, align: "left", count: true},
			{name: "confirmdate", index: "confirmdate", width: 125, label: "审核时间", sortable: true, align: "left" ,formatter: formatTime},
			{name: "jccpname", index: "jccpname", width: 90, label: "集成成品名称", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
			{name: "clname", index: "clname", width: 300, label: "材料名称", sortable: true, align: "left"},
			{name: "llqty", index: "llqty", width: 80, label: "领料数量", sortable: true, align: "right"},
			{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable: true, align: "right"},
			{name: "markup", index: "markup", width: 60, label: "折扣", sortable: true, align: "right"},
			{name: "jsje", index: "jsje", width: 70, label: "决算金额", sortable: true, align: "right",sum:true},
			{name: "clxqqty", index: "clxqqty", width: 90, label: "材料需求数量", sortable: true, align: "right"},
			{name: "lineamount", index: "lineamount", width: 90, label: "材料需求总价", sortable: true, align: "right", sum: true},
        ]
	});
	
	Global.JqGrid.initJqGrid("dataTable_zjmx_jczj",{
		url:"${ctx}/admin/jcxdybb/goJczjJqGrid",
		postData:$("#page_form").jsonForm(),
		height:450,
		rowNum: 10000,
		pager:"1",
		styleUI:"Bootstrap",
		colModel : [
		    {name: "no", index: "no", width: 93, label: "增减单号", sortable: true, align: "left", count: true},
			{name: "date", index: "date", width: 125, label: "变更时间", sortable: true, align: "left" ,formatter: formatTime},
			{name: "befamount", index: "befamount", width: 91, label: "优惠前金额", sortable: true, align: "right",sum:true},
			{name: "discamount", index: "discamount", width: 83, label: "优惠金额", sortable: true, align: "right",sum:true},
			{name: "amount", index: "amount", width: 75, label: "总价", sortable: true, align: "right",sum:true},
        ]
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_zjmx_cgzj",{
		url:"${ctx}/admin/jcxdybb/goCgzjJqGrid",
		postData:$("#page_form").jsonForm(),
		height:450,
		rowNum: 10000,
		styleUI:"Bootstrap",
		pager:"1",
		rowNum: 10000,
		colModel : [
		    {name: "no", index: "no", width: 93, label: "增减单号", sortable: true, align: "left", count: true},
			{name: "date", index: "date", width: 125, label: "变更时间", sortable: true, align: "left" ,formatter: formatTime},
			{name: "befamount", index: "befamount", width: 91, label: "优惠前金额", sortable: true, align: "right",sum:true},
			{name: "discamount", index: "discamount", width: 83, label: "优惠金额", sortable: true, align: "right",sum:true},
			{name: "amount", index: "amount", width: 75, label: "总价", sortable: true, align: "right",sum:true},
        ]
	});
});

function doExcelDetail(){
	if ($("#id_tcmx>ul li a[href='#tab_llmx_jcll']").parent().hasClass("active")){
		doExcelNow('集成领料','dataTable_llmx_jcll','page_form_excel');
	}
	else if ($("#id_tcmx>ul li a[href='#tab_llmx_cgll']").parent().hasClass("active")){
		doExcelNow('橱柜领料','dataTable_llmx_cgll','page_form_excel');
	}
	else if ($("#id_tcmx>ul li a[href='#tab_zjmx_jczj']").parent().hasClass("active")){
		doExcelNow('集成增减','dataTable_zjmx_jczj','page_form_excel');
	}else if ($("#id_tcmx>ul li a[href='#tab_zjmx_cgzj']").parent().hasClass("active")){
		doExcelNow('橱柜增减','dataTable_zjmx_cgzj','page_form_excel');
	}
}

</script>
<style type="text/css">
.ui-jqgrid{
width: 2000px;
}
</style>
</head>
<body onunload="closeWin()">
	<div class="panel panel-system">
		<div class="panel-body">
		    <div class="btn-group-xs">
				<button type="button" class="btn btn-system" id="closeBut" onclick="doExcelDetail()">
					<span>输出到Excel</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
             	<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="exportData" id="exportData">
				<ul class="ul-form">
					<li>
						<label>客户编号</label>
						<input type="text" id="code" name="code" value="${customer.code}" readonly="true" />
					</li>
					<li>
						<label>客户姓名</label>
						<input type="text" id="descr" name="descr" value="${customer.descr}" disabled="true"/>	
					</li>
						
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" value="${customer.address}" disabled="true"/>	
					</li>
					<input type="text" id="dateTo" name="dateTo" value="<fmt:formatDate value='${customer.dateTo }' pattern='yyyy-MM-dd'/>" hidden/>	
					<input type="text" id="dateFrom" name="dateFrom"  value="<fmt:formatDate value='${customer.dateFrom }' pattern='yyyy-MM-dd'/>" hidden/>	
				</ul>
			</form>
		</div>
	</div>
<div class="body-box-list">
	<div class="container-fluid" id="id_tcmx">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_llmx_jcll" data-toggle="tab">集成领料</a></li>
	        <li class=""><a href="#tab_llmx_cgll" data-toggle="tab">橱柜领料</a></li>
	        <li class=""><a href="#tab_zjmx_jczj" data-toggle="tab">集成增减</a></li>
	        <li class=""><a href="#tab_zjmx_cgzj" data-toggle="tab">橱柜增减</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_llmx_jcll" class="tab-pane fade in active"> 
	         	<div class="pageContent">
					<div id="content-list" style="padding-top: 10px;">
						<table id="dataTable_llmx_jcll"></table>
					</div>
				</div>
	        </div>  
	        <div id="tab_llmx_cgll" class="tab-pane fade"> 
	         	<div class="pageContent">
					<div id="content-list" style="padding-top: 10px;">
						<table id="dataTable_llmx_cgll"></table>
					</div>
				</div>
	        </div>
	        <div id="tab_zjmx_jczj" class="tab-pane fade"> 
	         	<div class="pageContent">
					<div id="content-list" style="padding-top: 10px;">
						<table id="dataTable_zjmx_jczj"></table>
					</div>
				</div>
	        </div>  
	        <div id="tab_zjmx_cgzj" class="tab-pane fade"> 
	         	<div class="pageContent">
					<div id="content-list" style="padding-top: 10px;">
						<table id="dataTable_zjmx_cgzj"></table>
					</div>
				</div>
	        </div>
	    </div>   
	</div>
</div>
<form action="" method="post" id="page_form_excel" >
	<input type="hidden" name="jsonString" value="" />
</form>
<script type="text/javascript">
</script>
</body>
</html>
