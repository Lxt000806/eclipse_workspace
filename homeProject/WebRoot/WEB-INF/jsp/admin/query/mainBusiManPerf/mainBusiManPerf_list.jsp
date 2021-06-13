<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>主材管家业绩报表</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
function goto_query(){
	if($.trim($("#dateFrom").val())==""){
		art.dialog({
			content: "统计开始日期不能为空"
		});
		return false;
	} 
	if($.trim($("#dateTo").val())==""){
		art.dialog({
			content: "统计结束日期不能为空"
		});
		return false;
	}
    var dateStart = Date.parse($.trim($("#dateFrom").val()));
    var dateEnd = Date.parse($.trim($("#dateTo").val()));
    if(dateStart>dateEnd){
   		art.dialog({content: "统计开始日期不能大于结束日期",width: 200});
 		return false;
    } 
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/mainBusiManPerf/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}				
/**初始化表格*/
$(function(){
	var postData=$("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "zcmanagernumber", index: "zcmanagernumber", width: 70, label: "管家编号", sortable: true, align: "left",hidden:true,frozen : true},
			{name: "zcmanagername", index: "zcmanagername", width: 65, label: "管家姓名", sortable: true, align: "left",frozen : true},
		 	{name: "checkbuilds", index: "checkbuilds", width: 67, label: "结算套数", sortable: true, align: "right", sum: true},
			{name: "innerarea", index: "innerarea", width: 67, label: "套内面积", sortable: true, align: "right", sum: true},
			{name: "area", index: "area", width: 67, label: "签约面积", sortable: true, align: "right", sum: true},
			{name: "maincheck", index: "maincheck", width: 93, label: "主材结算金额", sortable: true, align: "right", sum: true},
			{name: "mainperf", index: "mainperf", width: 93, label: "主材结算业绩", sortable: true, align: "right", sum: true},
			{name: "mainprice",index : "mainprice",width : 80,label:"每平方单价",sortable : true,align : "right"},
			{name: "checkchg", index: "checkchg", width: 93, label: "结算增减金额", sortable: true, align: "right", sum: true},
			{name: "insetmaincheck", index: "insetmaincheck", width: 93, label: "主材套内结算", sortable: true, align: "right",sum:true},
			{name: "outsetmaincheck", index: "outsetmaincheck", width: 93, label: "主材套外结算", sortable: true, align: "right",sum:true},
			{name: "maincost",index : "maincost",width : 67,label:"主材成本",sortable : true,align : "right", sum: true},
			{name: "checkprofit", index: "checkprofit", width: 67, label: "结算利润", sortable: true, align: "right", sum: true},
			{name: "checkprofitrate", index: "checkprofitrate", width: 80, label: "结算利润率", sortable: true, align: "right"},
			{name: "mainprofit", index: "mainprofit", width: 80, label: "每平方毛利", sortable: true, align: "right"},
			{name: "outsetservcheck", index: "outsetservcheck", width: 104, label: "服务性套外结算", sortable: true, align: "right",sum:true},
			{name: "servcost", index: "servcost", width: 80, label: "服务性成本", sortable: true, align: "right",sum:true},
			{name: "servprofit", index: "servprofit", width: 80, label: "服务性利润", sortable: true, align: "right",sum:true},
			{name: "servprofitper", index: "servprofitper", width: 93, label: "服务性利润率", sortable: true, align: "right"},	
			{name: "whamount", index: "whamount", width: 93, label: "集采材料结算", sortable: true, align: "right", sum: true},
			{name: "zcdlxs", index: "zcdlxs", width: 93, label: "主材独立销售", sortable: true, align: "right", sum: true},
			{name: "dqdlxs", index: "dqdlxs", width: 93, label: "电器独立销售", sortable: true, align: "right", sum: true},
	    ],    
	    loadonce: true,
	    rowNum:100000,  
	    pager :'1',
	    gridComplete:function(){
	    	var maincheck = parseFloat($("#dataTable").getCol('maincheck', false, 'sum'));
	  	    var checkprofit = parseFloat($("#dataTable").getCol('checkprofit', false, 'sum'));
	  	    var innerarea = parseFloat($("#dataTable").getCol('innerarea', false, 'sum'));
	  	    var checkprofitrate = checkprofit==0?0:myRound(checkprofit/maincheck*100,1);
	  	    var mainprice=innerarea==0?0:myRound(maincheck/innerarea);
	  	    var mainprofit=innerarea==0?0:myRound(checkprofit/innerarea);
			var outsetservcheck = parseFloat($("#dataTable").getCol('outsetservcheck', false, 'sum'));
	  	    var servprofit = parseFloat($("#dataTable").getCol('servprofit', false, 'sum'));
	  	    var servprofitper = servprofit==0?0:myRound(servprofit/outsetservcheck*100,1);
	  	    
			$("#dataTable").footerData('set', {"checkprofitrate": checkprofitrate+"%"});
	  	    $("#dataTable").footerData('set', {"mainprice": mainprice});
	  	    $("#dataTable").footerData('set', {"mainprofit": mainprofit});
	  	    $("#dataTable").footerData('set', {"servprofitper": servprofitper+"%"});
	    },
	    loadComplete: function(){
          	$('.ui-jqgrid-bdiv').scrollTop(0);
          	frozenHeightReset("dataTable");
    	},           
		
	});
	$("#dataTable").jqGrid("setFrozenColumns");
});
function view(){
	var ret = selectDataTableRow();
	if(ret){
		Global.Dialog.showDialog("view",{
			title:"主材管家业绩报表-查看",
			url:"${ctx}/admin/mainBusiManPerf/goView",
			postData:{dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val(),custType:$("#custType").val(),empCode:ret.zcmanagernumber},
			height:650,
			width:1300,
			returnFun:goto_query
		});
	}else{
	    art.dialog({
			content: "请选择一条记录"
		});
	}
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>统计日期从</label> 
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.dateFrom}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>到</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.dateTo}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="MAINBUSIMANPERF_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="MAINBUSIMANPERF_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcelNow('主材管家业绩报表','dataTable', 'page_form')">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
</body>
</html>


