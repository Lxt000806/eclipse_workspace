<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<style>
		.table-responsive {
			margin: 0px;
		}
		.ui-jqgrid-hdiv {
			width: auto !important;
		}
		.ui-jqgrid-bdiv {
			width: auto !important;
		}
	</style>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<div class="body-box-form">
			<input type="hidden" name="itemCode" value="${itemChg.itemCode}">
			<input type="hidden" name="custCode" value="${itemChg.custCode}">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBtn">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="pageContent">
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript">
		$(function() {
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/itemChg/getItemStatusJqGrid",
				postData:postData,
				height:$(document).height()-$("#content-list").offset().top-35,
				styleUI: "Bootstrap", 
				rowNum: 100000,
				colModel : [
					{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
					{name: "itemtype2", index: "itemtype2", width: 80, label: "材料类型2", sortable: true, align: "left", hidden: true},
					{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
					{name: "itemtype3", index: "itemtype3", width: 80, label: "材料类型3", sortable: true, align: "left", hidden: true},
					{name: "itemtype3descr", index: "itemtype3descr", width: 80, label: "材料类型3", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 120, label: "材料名称", sortable: true, align: "left"},
					{name: "sqlcode", index: "sqlcode", width: 80, label: "品牌", sortable: true, align: "left", hidden: true},
					{name: "sqldescr", index: "sqldescr", width: 80, label: "品牌", sortable: true, align: "left"},
					{name: "puqtycal", index: "puqtycal", width: 80, label: "采购数量", sortable: true, align: "right"},
					{name: "appqty", index: "appqty", width: 80, label: "订货数量", sortable: true, align: "right"},
					{name: "iasendqty", index: "iasendqty", width: 80, label: "发货数量", sortable: true, align: "right"},
				],
			});
			$("#closeBtn").on("click", function() {
				closeWin(false);
			});
		});
	</script>
</html>
