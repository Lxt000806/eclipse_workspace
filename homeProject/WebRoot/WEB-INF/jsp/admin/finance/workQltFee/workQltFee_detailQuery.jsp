<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工人质保金管理--明细查询</title>
	<%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		$("#code").openComponent_worker({showValue:"${workQltFee.code}",showLabel:"${workQltFee.nameChi}"});
		$("#openComponent_worker_code").attr("readonly",true);
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workQltFee/goJqGrid2",
		postData:{code:"${workQltFee.code}"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "worktype12", index: "worktype12", width: 110, label: "工种类型12", sortable: true, align: "left",hidden:true},
				{name: "code", index: "code", width: 75, label: "工人编号", sortable: true, align: "left"},
				{name: "namechi", index: "namechi", width: 75, label: "姓名", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 80, label: "工种", sortable: true, align: "left"},
				{name: "date", index: "date", width: 80, label: "变动日期", sortable: true, align: "left", formatter: formatDate},
				{name: "typedescr", index: "typedescr", width: 80, label: "变动类型", sortable: true, align: "left"},
				{name: "tryfee", index: "tryfee", width: 80, label: "变动金额", sortable: true, align: "right"},
				{name: "aftfee", index: "aftfee", width: 80, label: "变动后金额", sortable: true, align: "right"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "refaddress", index: "refaddress", width: 120, label: "关联楼盘", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 80, label: "说明", sortable: true, align: "left"},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "变动人员", sortable: true, align: "left"},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left"}
			],
		});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="F" /> <input
					type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>工人</label> <input type="text" id="code"
						name="code" style="width:160px;" value="${workQltFee.code}"/>
					</li>
					<li><label>变动日期从</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					 </li>
					<li class="form-validate"><label>类型</label> <house:xtdm id="type" dictCode="WKQLTFEETYPE"></house:xtdm>
				    </li>
					<li class="search-group">
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="doExcelNow('工人质保金管理--明细查询');">导出excel</button>
					</li>
				</ul>
			</form>
		</div>
	
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
	</div>
</body>
</html>


