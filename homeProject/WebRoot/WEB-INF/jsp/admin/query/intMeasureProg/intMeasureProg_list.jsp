<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>集成测量进度表</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/intMeasureProg/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}				
/**初始化表格*/
$(function(){
	$("#empCode").openComponent_employee();
	var postData=$("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-90,
		colModel : [
			{name: "楼盘", index: "楼盘", width: 150, label: "楼盘", sortable: true, align: "left"},
			{name: "客户类型", index: "客户类型", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "橱柜设计师", index: "橱柜设计师", width: 100, label: "橱柜设计师", sortable: true, align: "left"},
		 	{name: "集成设计师", index: "集成设计师", width: 100, label: "集成设计师", sortable: true, align: "left"},
			{name: "实际进度", index: "实际进度", width: 120, label: "实际进度", sortable: true, align: "left"},
			{name: "申报集成初测时间", index: "申报集成初测时间", width: 130, label: "申报集成初测时间", sortable: true, align: "left",formatter: formatTime},
	   		{name: "集成初测实际处理时间", index: "集成初测实际处理时间", width: 140, label: "集成初测实际处理时间", sortable: true, align: "left",formatter: formatTime},
	   		{name: "申报橱柜精测时间", index: "申报橱柜精测时间", width: 130, label: "申报橱柜精测时间", sortable: true, align: "left",formatter: formatTime},
	   		{name: "橱柜精测实际处理时间", index: "橱柜精测实际处理时间", width: 140, label: "橱柜精测实际处理时间", sortable: true, align: "left",formatter: formatTime},
	   		{name: "申报衣柜精测时间", index: "申报衣柜精测时间", width: 130, label: "申报衣柜精测时间", sortable: true, align: "left",formatter: formatTime},
	   		{name: "衣柜精测实际处理时间", index: "衣柜精测实际处理时间", width: 140, label: "衣柜精测实际处理时间", sortable: true, align: "left",formatter: formatTime},
	   		{name: "橱柜预计下单时间", index: "橱柜预计下单时间", width: 130, label: "橱柜预计下单时间", sortable: true, align: "left",formatter: formatTime},
	   		{name: "橱柜实际下单时间", index: "橱柜实际下单时间", width: 130, label: "橱柜实际下单时间", sortable: true, align: "left",formatter: formatTime},
	   		{name: "衣柜预计下单时间", index: "衣柜预计下单时间", width: 130, label: "衣柜预计下单时间", sortable: true, align: "left",formatter: formatTime},
	   		{name: "衣柜实际下单时间", index: "衣柜实际下单时间", width: 130, label: "衣柜实际下单时间", sortable: true, align: "left",formatter: formatTime},
	    ],    
	});
});
function doExcel(){
	var url = "${ctx}/admin/intMeasureProg/doExcel";
	doExcelAll(url);
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
						<label>集成设计师</label>
						<input type="text" id="empCode" name="empCode" style="width:160px;" value="${customer.empCode }" />
					</li>
					<li class="form-validate">
						<label>集成部</label>
						<house:dict id="department2" dictCode="" sql="select * from tdepartment2 where expired='F' and DepType='6'" sqlValueKey="Code" sqlLableKey="Desc2" ></house:dict>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" />
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="INTMEASUREPROG_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


