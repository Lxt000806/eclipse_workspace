<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>明细查询</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_worker.js?v=${v}"
	type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_workCard.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	}	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/cutCheckOut/goDetailJqGrid",
		height:400,
		styleUI: 'Bootstrap', 
		onSortColEndFlag:true,
		colModel : [
			{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 180, label: "材料名称", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 107, label: "区域", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 55, label: "数量", sortable: true, align: "right",editable:true},
			{name: "cuttypedescr", index: "cuttypedescr", width: 80, label: "切割方式", sortable: false, align: "left",},
			{name: "cutfee", index: "cutfee", width: 72, label: "加工单价", sortable: true, align: "right",editable:true},
			{name: "total", index: "total", width: 60, label: "总价", sortable: true, align: "right", sum: true,},
			{name: "remarks", index: "remarks", width: 150, label: "加工备注", sortable: true, align: "left"},
			{name: "iscompletedescr", index: "iscompletedescr", width: 70, label: "是否完成", sortable: true, align: "left",},
			{name: "completedate", index: "completedate", width: 120, label: "完成时间", sortable: true, align: "left", formatter: formatTime},
			{name: "no", index: "no", width: 80, label: "加工批次号", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 80, label: "状态", sortable: true, align: "left"},
			{name: "submitdate", index: "submitdate", width: 120, label: "出库时间", sortable: true, align: "left", formatter: formatTime},
			{name: "outremarks", index: "outremarks", width: 150, label: "批次备注", sortable: true, align: "left"},
			
			
		],
			});
		});
		
	function doExcel(){
		var url = "${ctx}/admin/cutCheckOut/doDetailExcel";
		doExcelAll(url);
	}
	</script>
</head>
<body >
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>加工批次号</label> 
						<input type="text" id="no" name="no"  />
					</li>
					<li>
						<label>状态</label>
						<house:xtdmMulit id="status" dictCode="CUTCHECKOUTSTAT" ></house:xtdmMulit>
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" />
					</li>
					<li>
					<label>出库时间从</label> 
						<input type="text" id="submitDateFrom" name="submitDateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="submitDateTo" name="submitDateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
					<label>完成时间从</label> 
						<input type="text" id="completeDateFrom" name="completeDateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="completeDateTo" name="completeDateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>领料单号</label> 
						<input type="text" id="iano" name="iano"  />
					</li>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm" >
				<button type="button" class="btn btn-system" id="excel"
					onclick="doExcel()">
					<span>导出Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager" style="height:25px;width:1000px"></div>
		</div>
	</div>
</body>
</html>
