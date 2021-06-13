<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>提成计算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	} 
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/commiCycle/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "no", index: "no", width: 100, label: "统计周期编号", sortable: true, align: "left"},
				{name: "mon", index: "mon", width: 80, label: "月份", sortable: true, align: "left"},
				{name: "floatbeginmon", index: "floatbeginmon", width: 120, label: "浮动业绩开始月份", sortable: true, align: "left"},
				{name: "floatendmon", index: "floatendmon", width: 120, label: "浮动业绩截止月份", sortable: true, align: "left"},
				{name: "status", index: "status", width: 80, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "状态", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow: function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
			}
		});
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>月份</label>
						<input type="text" id="mon" name="mon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"  />
					</li>
					<li>
						<label>浮动业绩开始月份</label>
						<input type="text" id="floatBeginMon" name="floatBeginMon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"  />
					</li>
					<li>
						<label>浮动业绩截止月份</label>
						<input type="text" id="floatEndMon" name="floatEndMon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"  />
					</li>
					<li><label>统计周期编号</label> <input type="text" id="no" name="no"
						style="width:160px;" />
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
