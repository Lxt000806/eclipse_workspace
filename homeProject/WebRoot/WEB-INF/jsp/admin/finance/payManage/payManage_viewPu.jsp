<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>查看采购记录</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	
	$(function (){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/payManage/getPuJqGrid",
			postData:{puNo:"${supplierPrepay.puNo}"},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name: "itcode", index: "itcode", width: 80, label: "材料编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 150, label: "材料名称", sortable: true, align: "left"},
				{name: "lastunitprice", index: "lastunitprice", width: 80, label: "上次单价", sortable: true, align: "right"},
				{name: "thisunitprice", index: "thisunitprice", width: 80, label: "本次单价", sortable: true, align: "right"},
				{name: "puno", index: "puno", width: 125, label: "采购单号", sortable: true, align: "left"},
				{name: "date", index: "date", width: 138, label: "采购日期", sortable: true, align: "left", formatter: formatDate},
	           ],
		    rowNum:100000,
			pager:1,
			loadonce:true
		});
	});
	
	</script>
</head>

<div class="body-box-form">
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div class="pageContent">
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</div>
</body>
</html>


