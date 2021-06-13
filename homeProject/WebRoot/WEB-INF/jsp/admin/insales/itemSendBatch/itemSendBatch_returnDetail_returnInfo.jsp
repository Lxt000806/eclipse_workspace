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
			Global.JqGrid.initJqGrid("returnInfoDataTable",{
				url:"${ctx}/admin/itemReturnDetail/goReturnDetailJqGrid",
				postData:{
					no:"${itemReturnDetail.no}",
				},
				height:300,
				rowNum:10000000,
	        	styleUI: "Bootstrap",
				colModel : [
					{name: "lpaddress", index: "lpaddress", width: 112, label: "楼盘地址", sortable: true, align: "left"},
					{name: "appczyname", index: "appczyname", width: 80, label: "申请人", sortable: true, align: "left"},
					{name: "phone", index: "phone", width: 106, label: "电话", sortable: true, align: "left"},
					{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden: true},
					{name: "itemcode", index: "itemcode", width: 96, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 158, label: "材料名称", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "right"},
					{name: "realqty", index: "realqty", width: 75, label: "实收数量", sortable: true, align: "right"},
					{name: "uomdescr", index: "uomdescr", width: 77, label: "单位", sortable: true, align: "left"},
					{name: "totalperweight", index: "totalperweight", width: 77, label: "总重量", sortable: true, align: "right", sum: true},
					{name: "appdate", index: "appdate", width: 137, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name: "senddate", index: "senddate", width: 145, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
					{name: "itemtype1descr", index: "itemtype1descr", width: 98, label: "材料类型1", sortable: true, align: "left"},
					{name: "returnaddress", index: "returnaddress", width: 167, label: "退货地址", sortable: true, align: "left"},
					{name: "address", index: "address", width: 201, label: "到货地址", sortable: true, align: "left"},
					{name: "arrivedate", index: "arrivedate", width: 91, label: "到货日期", sortable: true, align: "left", formatter: formatTime},
					{name: "driverremark", index: "driverremark", width: 178, label: "司机反馈", sortable: true, align: "left"}	            ],
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
						onclick="doExcelNow('退货明细','returnInfoDataTable','page_form')">导出Excel</button>
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div id="content-list">
			<table id="returnInfoDataTable"></table>
		</div>
</body>
</html>
