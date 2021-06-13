<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>图纸审核员</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
//导出EXCEL
function doExcel(){
    var url = "${ctx}/admin/dispatchCenterKPI/doExcelPicConfirmDetail";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
	var postData=$("#page_form").jsonForm();
     //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/dispatchCenterKPI/goJqGridPicConfirmDetail",
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum:100000,  
		pager :'1', 
		colModel : [
			{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
			{name: "submitdate", index: "submitdate", width: 100, label: "提交时间", sortable: true, align: "left", formatter: formatTime},
			{name: "toconstructdate", index: "toconstructdate", width: 100, label: "转施工时间", sortable: true, align: "left", formatter: formatTime},
			{name: "confirmdate", index: "confirmdate", width: 100, label: "审核日期", sortable: true, align: "left",formatter: formatTime},
			{name: "enddate", index: "enddate", width: 100, label: "归档时间", sortable: true, align: "left",formatter: formatTime},
	    	{name: "isontime", index: "isontime", width: 100, label: "是否及时", sortable: true, align: "left"},
	    ],    

	});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form" hidden="true">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="dateFrom" name="dateFrom"  value="<fmt:formatDate value="${customer.dateFrom}" pattern='yyyy-MM-dd'/>" />
				<input type="hidden" id="dateTo" name="dateTo"  value="<fmt:formatDate value="${customer.dateTo}" pattern='yyyy-MM-dd'/>" />
				<input type="hidden" id="department2" name="department2" value="${customer.department2}" /> 
				<input type="hidden" id="empCode" name="empCode" value="${customer.empCode}" /> 
				<input type="hidden" name="jsonString" value="" />
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
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


