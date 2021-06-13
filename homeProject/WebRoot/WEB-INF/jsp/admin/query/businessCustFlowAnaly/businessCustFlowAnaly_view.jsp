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
						<a href="#tabView_crt" data-toggle="tab">派单明细</a>
					</li>
					<li>
						<a href="#tabView_set" data-toggle="tab">下定明细</a>
					</li>
					<li>
						<a href="#tabView_sign" data-toggle="tab">签单明细</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_crt" class="tab-pane fade in active">
						<div id="content-list">
							<table id="dataTable_crt"></table>
						</div>
					</div>
					<div id="tabView_set" class="tab-pane fade">
						<div id="content-list2">
							<table id="dataTable_set"></table>
						</div>
					</div>
					<div id="tabView_sign" class="tab-pane fade">
						<div id="content-list3">
							<table id="dataTable_sign"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript">
		var dateFrom = formatDate("${employee.dateFrom}"),
		dateTo = formatDate("${employee.dateTo}");
		var formatoption = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2};	
		//刷新sum并取两位小数
		function refreshSum(tableId, colModel_name, decimalPlaces) {
			decimalPlaces = decimalPlaces===undefined?2:decimalPlaces;
			var colModel_name_sum = myRound($("#"+tableId).getCol(colModel_name,false,"sum"), decimalPlaces);
			var sumObj = {}; //json先要用{}定义，再传参
			sumObj[colModel_name] = colModel_name_sum;//要用参数作为名字时，用[]
			$("#"+tableId).footerData("set", sumObj);
		}
		// 刷新合计和平均值
		function refreshSumAndAvg() {
			refreshSum("dataTable_sign", "contractfee");
		}
		$(function() {
			var jqGridOption = {
				url:"${ctx}/admin/businessCustFlowAnaly/goDetailJqGrid",
				height:$(document).height()-$("#content-list").offset().top-63,
				styleUI: "Bootstrap", 
				rowNum : 10000000,
			};
			var colModel_crt = [
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left", count: true},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
				{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left", hidden: true},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "visitdate", index: "visitdate", width: 80, label: "到店时间", sortable: true, align: "left", formatter:formatDate},
				{name: "designman", index: "designman", width: 80, label: "设计师", sortable: true, align: "left"},
				{name: "designdepartment1", index: "designdepartment1", width: 80, label: "设计部门1", sortable: true, align: "left", hidden: true},
				{name: "designdepartment1descr", index: "designdepartment1descr", width: 80, label: "设计部门1", sortable: true, align: "left"},
				{name: "designdepartment2", index: "designdepartment2", width: 80, label: "设计部门2", sortable: true, align: "left", hidden: true},
				{name: "designdepartment2descr", index: "designdepartment2descr", width: 80, label: "设计部门2", sortable: true, align: "left"},
				{name: "businessman", index: "businessman", width: 80, label: "业务员", sortable: true, align: "left"},
				{name: "department1", index: "department1", width: 80, label: "业务部门1", sortable: true, align: "left", hidden: true},
				{name: "department1descr", index: "department1descr", width: 80, label: "业务部门1", sortable: true, align: "left"},
				{name: "department2", index: "department2", width: 80, label: "业务部门2", sortable: true, align: "left", hidden: true},
				{name: "department2descr", index: "department2descr", width: 80, label: "业务部门2", sortable: true, align: "left"},
			],
			colModel_set = [
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left", count: true},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
				{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left", hidden: true},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "setdate", index: "setdate", width: 80, label: "下定时间", sortable: true, align: "left", formatter:formatDate},
				{name: "designman", index: "designman", width: 80, label: "设计师", sortable: true, align: "left"},
				{name: "designdepartment1", index: "designdepartment1", width: 80, label: "设计部门1", sortable: true, align: "left", hidden: true},
				{name: "designdepartment1descr", index: "designdepartment1descr", width: 80, label: "设计部门1", sortable: true, align: "left"},
				{name: "designdepartment2", index: "designdepartment2", width: 80, label: "设计部门2", sortable: true, align: "left", hidden: true},
				{name: "designdepartment2descr", index: "designdepartment2descr", width: 80, label: "设计部门2", sortable: true, align: "left"},
				{name: "businessman", index: "businessman", width: 80, label: "业务员", sortable: true, align: "left"},
				{name: "department1", index: "department1", width: 80, label: "业务部门1", sortable: true, align: "left", hidden: true},
				{name: "department1descr", index: "department1descr", width: 80, label: "业务部门1", sortable: true, align: "left"},
				{name: "department2", index: "department2", width: 80, label: "业务部门2", sortable: true, align: "left", hidden: true},
				{name: "department2descr", index: "department2descr", width: 80, label: "业务部门2", sortable: true, align: "left"},
			],
			colModel_sign = [
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left", count: true},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
				{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left", hidden: true},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "signdate", index: "signdate", width: 80, label: "签单时间", sortable: true, align: "left", formatter:formatDate},
				{name: "designman", index: "designman", width: 80, label: "设计师", sortable: true, align: "left"},
				{name: "designdepartment1", index: "designdepartment1", width: 80, label: "设计部门1", sortable: true, align: "left", hidden: true},
				{name: "designdepartment1descr", index: "designdepartment1descr", width: 80, label: "设计部门1", sortable: true, align: "left"},
				{name: "designdepartment2", index: "designdepartment2", width: 80, label: "设计部门2", sortable: true, align: "left", hidden: true},
				{name: "designdepartment2descr", index: "designdepartment2descr", width: 80, label: "设计部门2", sortable: true, align: "left"},
				{name: "businessman", index: "businessman", width: 80, label: "业务员", sortable: true, align: "left"},
				{name: "department1", index: "department1", width: 80, label: "业务部门1", sortable: true, align: "left", hidden: true},
				{name: "department1descr", index: "department1descr", width: 80, label: "业务部门1", sortable: true, align: "left"},
				{name: "department2", index: "department2", width: 80, label: "业务部门2", sortable: true, align: "left", hidden: true},
				{name: "department2descr", index: "department2descr", width: 80, label: "业务部门2", sortable: true, align: "left"},
				{name: "contractfee", index: "contractfee", width: 80, label: "签单金额", sortable: true, align: "right", formatter:"number", formatoptions: formatoption},
			];
			Global.JqGrid.initJqGrid("dataTable_crt",$.extend(jqGridOption, {
				postData:{
					department1: "${employee.department1}", 
					department2: "${employee.department2}", 
					department1Code: "${employee.department1Code}",
					department2Code: "${employee.department2Code}",
					number: "${employee.number}",
					dateFrom: dateFrom, 
					dateTo: dateTo,
					viewType: "CRT",
				},
				colModel:colModel_crt,
				page: 1,
				loadComplete: function(){
					refreshSumAndAvg();
				}
			}));
			Global.JqGrid.initJqGrid("dataTable_set",$.extend(jqGridOption, {
				postData:{
					department1: "${employee.department1}", 
					department2: "${employee.department2}", 
					department1Code: "${employee.department1Code}",
					department2Code: "${employee.department2Code}",
					number: "${employee.number}",
					dateFrom: dateFrom, 
					dateTo: dateTo,
					viewType: "SET",
				},
				colModel:colModel_set,
				page: 1,
				loadComplete: function(){
					refreshSumAndAvg();
				}
			}));
			Global.JqGrid.initJqGrid("dataTable_sign",$.extend(jqGridOption, {
				postData:{
					department1: "${employee.department1}", 
					department2: "${employee.department2}",  
					department1Code: "${employee.department1Code}",
					department2Code: "${employee.department2Code}",
					number: "${employee.number}",
					dateFrom: dateFrom, 
					dateTo: dateTo,
					viewType: "SIGN",
				},
				colModel:colModel_sign,
				page: 1,
				loadComplete: function(){
					refreshSumAndAvg();
				}
			}));
			$("#closeBtn").on("click",function(){
				doClose();
			});
			// replaceCloseEvt("update", doClose);
		});
		function doClose() {
			closeWin();
		}
	</script>
</html>
