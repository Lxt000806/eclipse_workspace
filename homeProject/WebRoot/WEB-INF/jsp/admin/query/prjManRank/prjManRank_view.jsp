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
						<a href="#tabView_checkOut" data-toggle="tab">完工明细</a>
					</li>
					<li>
						<a href="#tabView_again" data-toggle="tab">翻单明细</a>
					</li>
					<li>
						<a href="#tabView_twoSale" data-toggle="tab">二次销售明细</a>
					</li>
					<li>
						<a href="#tabView_setUp" data-toggle="tab">安排单量明细</a>
					</li>
					<li>
						<a href="#tabView_working" data-toggle="tab">在建明细</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_checkOut" class="tab-pane fade in active">
						<div id="content-list">
							<table id="dataTable_checkOut"></table>
						</div>
					</div>
					<div id="tabView_again" class="tab-pane fade">
						<div id="content-list2">
							<table id="dataTable_again"></table>
						</div>
					</div>
					<div id="tabView_twoSale" class="tab-pane fade">
						<div id="content-list3">
							<table id="dataTable_twoSale"></table>
						</div>
					</div>
					<div id="tabView_setUp" class="tab-pane fade">
						<div id="content-list4">
							<table id="dataTable_setUp"></table>
						</div>
					</div>
					<div id="tabView_working" class="tab-pane fade">
						<div id="content-list5">
							<table id="dataTable_working"></table>
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
			refreshSum("dataTable_checkOut", "itemchg");
			refreshSum("dataTable_checkOut", "baseitemchg");
			refreshSum("dataTable_again", "contractfee");
			refreshSum("dataTable_twoSale", "amount");
			refreshSum("dataTable_again", "quantity", 0);
		}
		$(function() {
			var jqGridOption = {
				url:"${ctx}/admin/prjManRank/goDetailJqGrid",
				height:$(document).height()-$("#content-list").offset().top-63,
				styleUI: "Bootstrap", 
				rowNum : 10000000,
			};
			var colModel_checkOut = [
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left", count: true},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "left"},
				{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left", hidden: true},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 80, label: "开工时间", sortable: true, align: "left", formatter:formatDate},
				{name: "begincheckdate", index: "begincheckdate", width: 100, label: "考核开工时间", sortable: true, align: "left", formatter:formatDate},
				{name: "endcheckdate", index: "endcheckdate", width: 100, label: "竣工验收时间", sortable: true, align: "left", formatter:formatDate},
				{name: "enddate", index: "enddate", width: 80, label: "结束时间", sortable: true, align: "left", formatter:formatDate},
				{name: "custcheckdate", index: "custcheckdate", width: 80, label: "结算时间", sortable: true, align: "left", formatter:formatDate},
				{name: "workday", index: "workday", width: 80, label: "实际工期", sortable: true, align: "left"},
				{name: "constructday", index: "constructday", width: 80, label: "标准工期", sortable: true, align: "left"},
				{name: "isontime", index: "isontime", width: 100, label: "是否按时完工", sortable: true, align: "left"},
				{name: "baseitemchg", index: "baseitemchg", width: 100, label: "基础增减金额", sortable: true, align: "right", formatter:"number", formatoptions: formatoption},
				{name: "itemchg", index: "itemchg", width: 100, label: "材料增减金额", sortable: true, align: "right", formatter:"number", formatoptions: formatoption},
			],
			colModel_again = [
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left", count: true},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "left"},
				{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left", hidden: true},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "type", index: "type", width: 80, label: "业绩类型", sortable: true, align: "left", hidden: true},
				{name: "typedescr", index: "typedescr", width: 80, label: "业绩类型", sortable: true, align: "left"},
				{name: "achievedate", index: "achievedate", width: 80, label: "业绩日期", sortable: true, align: "left", formatter:formatDate},
				{name: "quantity", index: "quantity", width: 60, label: "单量", sortable: true, align: "right"},
				{name: "contractfee", index: "contractfee", width: 80, label: "翻单金额", sortable: true, align: "right", formatter:"number", formatoptions: formatoption},
				{name: "leadercode", index: "leadercode", width: 80, label: "归属领导", sortable: true, align: "left", hidden: true},
				{name: "leadername", index: "leadername", width: 80, label: "归属领导", sortable: true, align: "left"},
				{name: "department2", index: "department2", width: 70, label: "部门", sortable: true, align: "left", hidden: true},
				{name: "department2descr", index: "department2descr", width: 70, label: "部门", sortable: true, align: "left"},
			],
			colModel_twoSale = [
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left", count: true},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "left"},
				{name: "signdate", index: "signdate", width: 80, label: "销售日期", sortable: true, align: "left", formatter:formatDate},
				{name: "amount", index: "amount", width: 80, label: "销售金额", sortable: true, align: "right", formatter:"number", formatoptions: formatoption},
			],
			colModel_setUp = [
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left", count: true},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "left"},
				{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left", hidden: true},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 80, label: "安排时间", sortable: true, align: "left", formatter:formatDate},
			],
			colModel_working = [
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left", count: true},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "left"},
				{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left", hidden: true},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 80, label: "开工时间", sortable: true, align: "left", formatter:formatDate},
				{name: "begincheckdate", index: "begincheckdate", width: 100, label: "考核开工时间", sortable: true, align: "left", formatter:formatDate},
				{name: "constructday", index: "constructday", width: 80, label: "施工工期", sortable: true, align: "left"},
				{name: "islate", index: "islate", width: 80, label: "是否拖期", sortable: true, align: "left"},
			];
			Global.JqGrid.initJqGrid("dataTable_checkOut",$.extend(jqGridOption, {
				postData:{
					number: "${employee.number}", 
					dateFrom: dateFrom, 
					dateTo: dateTo,
					viewType: "C",
				},
				colModel:colModel_checkOut,
				page: 1,
				loadComplete: function(){
					refreshSumAndAvg();
				}
			}));
			Global.JqGrid.initJqGrid("dataTable_again",$.extend(jqGridOption, {
				postData:{
					number: "${employee.number}", 
					dateFrom: dateFrom, 
					dateTo: dateTo,
					viewType: "A",
				},
				colModel:colModel_again,
				page: 1,
				loadComplete: function(){
					refreshSumAndAvg();
				}
			}));
			Global.JqGrid.initJqGrid("dataTable_twoSale",$.extend(jqGridOption, {
				postData:{
					number: "${employee.number}", 
					dateFrom: dateFrom, 
					dateTo: dateTo,
					viewType: "T",
				},
				colModel:colModel_twoSale,
				page: 1,
				loadComplete: function(){
					refreshSumAndAvg();
				}
			}));
			Global.JqGrid.initJqGrid("dataTable_setUp",$.extend(jqGridOption, {
				postData:{
					number: "${employee.number}", 
					dateFrom: dateFrom, 
					dateTo: dateTo,
					viewType: "S",
				},
				colModel:colModel_setUp,
				page: 1,
				loadComplete: function(){
					refreshSumAndAvg();
				}
			}));
			Global.JqGrid.initJqGrid("dataTable_working",$.extend(jqGridOption, {
				postData:{
					number: "${employee.number}", 
					viewType: "W",
				},
				colModel:colModel_working,
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
