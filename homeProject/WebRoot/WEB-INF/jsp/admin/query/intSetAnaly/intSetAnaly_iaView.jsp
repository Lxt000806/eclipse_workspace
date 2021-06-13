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
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBtn">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#tabView_iaDetail" data-toggle="tab">领料明细</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_iaDetail" class="tab-pane fade in active">
						<div id="content-list">
							<table id="dataTable_iaDetail"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript">
		var formatoption = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2};	
		// 刷新合计和平均值
		$(function() {
			var jqGridOption = {
				url:"${ctx}/admin/intSetAnaly/goIaDetailJqGrid",
				height:$(document).height()-$("#content-list").offset().top-63,
				styleUI: "Bootstrap", 
				rowNum : 10000000,
			};
			var colModel_iaDetail = [
				{name: "no", index: "no", width: 80, label: "领料单号", sortable: true, align: "left", count: true},
				{name: "iscupboard", index: "iscupboard", width: 80, label: "是否橱柜", sortable: true, align: "left", hidden: true},
				{name: "iscupboarddescr", index: "iscupboarddescr", width: 80, label: "是否橱柜", sortable: true, align: "left"},
				{name: "status", index: "status", width: 80, label: "领料单状态", sortable: true, align: "left", hidden: true},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "领料单状态", sortable: true, align: "left"},
				{name: "date", index: "date", width: 80, label: "申请日期", sortable: true, align: "left", formatter:formatDate},
				{name: "confirmdate", index: "confirmdate", width: 80, label: "审批日期", sortable: true, align: "left", formatter:formatDate},
				{name: "senddate", index: "senddate", width: 80, label: "发货日期", sortable: true, align: "left", formatter:formatDate},
				{name: "sendtype", index: "sendtype", width: 80, label: "发货类型", sortable: true, align: "left", hidden: true},
				{name: "sendtypedescr", index: "sendtypedescr", width: 80, label: "发货类型", sortable: true, align: "left"},
				{name: "supplcode", index: "supplcode", width: 80, label: "供应商", sortable: true, align: "left", hidden: true},
				{name: "suppldescr", index: "suppldescr", width: 80, label: "供应商", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"},
			];
			Global.JqGrid.initJqGrid("dataTable_iaDetail",$.extend(jqGridOption, {
				postData:{
					code: "${customer.code}", 
					IsCupboard: "${customer.isCupboard}",
				},
				colModel:colModel_iaDetail,
				page: 1,
			}));
			$("#closeBtn").on("click",function(){
				doClose();
			});
		});
		function doClose() {
			closeWin();
		}
	</script>
</html>
