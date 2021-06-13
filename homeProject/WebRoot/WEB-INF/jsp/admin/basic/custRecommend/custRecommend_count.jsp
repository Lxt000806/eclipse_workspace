<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>工人推荐管理-信息统计查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}"type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}"type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}"type="text/javascript"></script>
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/custRecommend/doCountQueryExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custRecommend/goCountJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			rowNum:100000,
			sortable: true,
			colModel : [
				{name: "recommendname", index: "recommendname", width: 90, label: "推荐人", sortable: true, align: "left"},
				{name: "count", index: "count", width: 75, label: "推荐数", sortable: true, align: "right"},
				{name: "arrivecount", index: "arrivecount", width: 75, label: "到店数", sortable: true, align: "right"},
				{name: "setcount", index: "setcount", width: 75, label: "下定数", sortable: true, align: "right"},
				{name: "signcount", index: "signcount", width: 70, label: "签单数", sortable: true, align: "right"},
				{name: "returnsetcount", index: "returnsetcount", width: 75, label: "退订数", sortable: true, align: "right"},
				{name: "returnsigncount", index: "returnsigncount", width: 75, label: "退单数", sortable: true, align: "right"}
			],
		});
		$("#recommender").openComponent_employee();
	});
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-sm btn-system" onclick="doExcel()" title="导出检索条件数据">导出Excel</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>确认时间从 </label>
						<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>员工编号</label>
						<input type="text" id="recommender" name="recommender"/>
					</li>
					<li>
						<label>推荐来源</label>
						<house:DictMulitSelect id="recommendSource" dictCode="RECOMMENDSOURCE" sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div> 
	</div>
</body>
</html>
