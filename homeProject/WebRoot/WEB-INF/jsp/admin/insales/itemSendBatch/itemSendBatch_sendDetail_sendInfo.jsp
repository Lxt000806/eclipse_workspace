<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>发货详情</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemApp.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("sendInfoDataTable",{
				url:"${ctx}/admin/itemAppSendDetail/goSendDetailJqGrid",
				postData:{
					no:"${itemAppSendDetail.no}",
				},
				height:300,
				rowNum:10000000,
	        	styleUI: "Bootstrap",
				colModel : [
					{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden: true},
					{name: "itemcode", index: "itemcode", width: 117, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 199, label: "材料名称", sortable: true, align: "left"},
					{name: "sendqty", index: "sendqty", width: 90, label: "数量", sortable: true, align: "right"},
					{name: "uomdescr", index: "uomdescr", width: 80, label: "单位", sortable: true, align: "left"},
					{name: "totalperweight", index: "totalperweight", width: 76, label: "总重量", sortable: true, align: "right", sum: true},
					{name: "itemtype1descr", index: "itemtype1descr", width: 130, label: "材料类型1", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 225, label: "备注", sortable: true, align: "left"}
	            ],
			});
		});
	</script>
	</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
			</form>
				<div class="btn-group-xs" style="float:left">
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="doExcelNow('发货明细','sendInfoDataTable','page_form')">导出Excel</button>
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div id="content-list">
			<table id="sendInfoDataTable"></table>
		</div>
</body>
</html>
