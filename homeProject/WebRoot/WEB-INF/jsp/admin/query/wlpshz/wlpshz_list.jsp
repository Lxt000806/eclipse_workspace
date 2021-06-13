<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>主材物流配送汇总</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	$(function(){
		var postData = $("#page_form").jsonForm();
		var gridOption ={
			postData:postData,
			rowNum:10000000,
			height:$(document).height()-$("#content-list").offset().top-102,
			colModel : [
				{name: "Date", index: "Date", width: 80, label: "时间" , align: "left",formatter:formatTime,count:true,hidden:true},
				{name: "DayDescr", index: "DayDescr", width: 80, label: "时间" , align: "left",count:true},
				{name: "SendCount_Tile", index: "SendCount_Tile", width: 110, label: "已配送瓷砖楼盘" , align: "right", sum: true},
				{name: "TileWeight", index: "TileWeight", width: 108, label: "已配送瓷砖重量" , align: "right", sum: true},
				{name: "SendCount_Floor", index: "SendCount_Floor", width: 108, label: "已配送地板楼盘" , align: "right", sum: true},
				{name: "FloorArea", index: "FloorArea", width: 108, label: "已配送地板面积" , align: "right", sum: true},
				{name: "SendCount_Bath", index: "SendCount_Bath", width: 108, label: "已配送卫浴楼盘" , align: "right", sum: true},
				{name: "CabinetCount", index: "CabinetCount", width: 110, label: "已配送浴室柜" , align: "right", sum: true},
				{name: "ToiletCount", index: "ToiletCount", width: 110, label: "已配送坐便器" , align: "right", sum: true},
				{name: "OneDayCount", index: "OneDayCount", width: 85, label: "24" , align: "right", sum: true},
				{name: "TwoDayCount", index: "TwoDayCount", width: 85, label: "48" , align: "right", sum: true},
				{name: "ThreeDayCount", index: "ThreeDayCount", width: 85, label: "72" , align: "right", sum: true},
				{name: "DelayCount", index: "DelayCount", width: 85, label: "超时" , align: "right", sum: true},
				{name: "OneDayCountFloor", index: "OneDayCountFloor", width: 85, label: "24" , align: "right", sum: true},
				{name: "TwoDayCountFloor", index: "TwoDayCountFloor", width: 85, label: "48" , align: "right", sum: true},
				{name: "ThreeDayCountFloor", index: "ThreeDayCountFloor", width: 85, label: "72" , align: "right", sum: true},
				{name: "DelayCountFloor", index: "DelayCountFloor", width: 85, label: "超时" , align: "right", sum: true},
				{name: "OneDayCountCabinet", index: "OneDayCountCabinet", width: 85, label: "24" , align: "right", sum: true},
				{name: "TwoDayCountCabinet", index: "TwoDayCountCabinet", width: 85, label: "48" , align: "right", sum: true},
				{name: "ThreeDayCountCabinet", index: "ThreeDayCountCabinet", width: 85, label: "72" , align: "right", sum: true},
				{name: "DelayCountCabinet", index: "DelayCountCabinet", width: 85, label: "超时" , align: "right", sum: true}
			],//OneDayCountCabinet=0.0000, TwoDayCountCabinet=0.0000, ThreeDayCountCabinet=0.0000, DelayCountCabinet=0.0000}]
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		$("#dataTable").jqGrid('setGroupHeaders', {
		  	useColSpanStyle: true, 
			groupHeaders:[{startColumnName: 'OneDayCount', numberOfColumns: 4, titleText: '瓷砖'},
							{startColumnName: 'OneDayCountFloor', numberOfColumns: 4, titleText: '地板'},
							{startColumnName: 'OneDayCountCabinet', numberOfColumns: 4, titleText: '卫浴'}
			],
		});
		window.goto_query = function(){
			var dateFrom=$.trim($("#dateFrom").val());
			var dateTo=$.trim($("#dateTo").val());
			if(dateFrom==""){
				art.dialog({
					content:"下定开始日期不能为空",
				});
				return;
			}
			if(dateTo==""){
				art.dialog({
					content:"结束日期不能为空",
				});
				return;
			}
			if(dateTo<dateFrom){
				art.dialog({
					content:"结束日期不能小于下定开始日期！",
				});
				return;
			}
			var	sDate1 = Date.parse(dateFrom);
		    var sDate2 = Date.parse(dateTo);
			var	dateSpan = sDate2 - sDate1;
			var	iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
			if(iDays>99){
				art.dialog({
					content:"统计周期不能超过100天！",
				});
				return;
			}
			$("#dataTable").jqGrid("setGridParam",{datatype:"json",postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/wlpshz/goJqGrid",sortname:""}).trigger("reloadGrid");
		}
	});	
	
	function view() {
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("view",{ 
			title:"主材物流配送明细",
			postData:{date:ret.Date},
			url:"${ctx}/admin/wlpshz/goView",
			height: 700,
			width:1250,
			returnFun:goto_query
		});
	}
	//查看明细
	function doExcel(){
		var url = "${ctx}/admin/wlpshz/doExcel";
		doExcelAll(url);
	}
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li>	
								<label style="width:75px">开始日期</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${item.dateFrom}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label style="width:75px">至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${item.dateTo}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li class="search-group">
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
								<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
							</li>
						</div>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" onclick="view()">
					<span>查看明细</span>
				</button>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</body>	
</html>
