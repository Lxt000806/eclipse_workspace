<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>材料签单统计</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
//导出EXCEL
function doExcel(){
    var url = "${ctx}/admin/itemSignCount/doExcelDetail";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
	var postData=$("#page_form").jsonForm();
     //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemSignCount/goJqGridDetail",
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
		 	{name: "code", index: "code", width: 100, label: "客户编号", sortable: true, align: "left", count: true},
			{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
			{name: "designdescr", index: "designdescr", width: 100, label: "设计师", sortable: true, align: "left"},
			{name: "signdate", index: "signdate", width: 100, label: "签订时间", sortable: true, align: "left", formatter: formatTime},
			{name: "area", index: "area", width: 100, label: "面积", sortable: true, align: "right", sum: true},
			{name: "lineamount", index: "lineamount", width: 100, label: "合同金额", sortable: true, align: "right", sum: true},
			{name: "lr", index: "lr", width: 100, label: "利润", sortable: true, align: "right", sum: true},
			{name: "fwxje", index: "fwxje", width: 100, label: "服务性金额", sortable: true, align: "right", sum: true}
	    ],    
	    loadonce: true,
	    rowNum:100000,  
	    pager :'1',	
	});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form" hidden="true">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="itemType1" name="itemType1" value="${customer.itemType1}" />
				<input type="hidden" id="custType" name="custType" value="${customer.custType}" />
				<input type="hidden" id="dateFrom" name="dateFrom"  value="<fmt:formatDate value="${customer.dateFrom}" pattern='yyyy-MM-dd'/>" />
				<input type="hidden" id="dateTo" name="dateTo"  value="<fmt:formatDate value="${customer.dateTo}" pattern='yyyy-MM-dd'/>" />
				<input type="hidden" id="signMonth" name="signMonth" value="${customer.signMonth}" />
				<input type="hidden" id="department1" name="department1" value="${customer.department1}" />  
				<input type="hidden" id="statistcsMethod" name="statistcsMethod" value="${customer.statistcsMethod}" />
				<input type="hidden" name="jsonString" value="" />
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="ITEMSIGNCOUNT_EXCEL">
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				</house:authorize>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


