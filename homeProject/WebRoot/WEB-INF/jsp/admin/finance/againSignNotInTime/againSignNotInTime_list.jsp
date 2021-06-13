<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>未及时重签客户</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">

</style>
<script type="text/javascript">
	//导出EXCEL
	function doExcel(){
		var url = "${ctx}/admin/againSignNotInTime/doExcel";
		doExcelAll(url);
	}
/**初始化表格*/
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/againSignNotInTime/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 70, label: "客户姓名", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "newsigndate",index : "newsigndate",width : 120,label:"重签时间",sortable : true,align : "left", formatter:formatTime},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
				{name: "designmandescr", index: "designmandescr", width: 80, label: "设计师", sortable: true, align: "left"},
				{name: "departmentdescr", index: "departmentdescr", width: 150, label: "设计部门", sortable: true, align: "left"}
		],
	});

});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
			<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>重签时间从</label> 
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							/>
					</li>
					<li>
						<label>到</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							/>
					</li>
					<li class="search-group-shrink">
							<button type="button" class="btn  btn-sm btn-system "
								onclick="goto_query();">查询</button>
							<button type="button" id="clear" class="btn btn-sm btn-system "
								onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="AGAINSIGNNOTINTIME_EXCEL">
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
			</house:authorize>
		</div>
	</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
	</div>
</body>
</html>


