<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>拖期明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript"> 
$(function(){
	$("#projectMan").openComponent_employee();

	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/gcxxgl/goDelayDetailJqGrid",
		height:480,
		width:100,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "楼盘地址", index: "楼盘地址", width: 167, label: "楼盘地址", sortable: true, align: "left"},
			{name: "项目经理", index: "项目经理", width: 75, label: "项目经理", sortable: true, align: "left"},
			{name: "工程部", index: "工程部", width: 77, label: "工程部", sortable: true, align: "left"},
			{name: "面积", index: "面积", width: 75, label: "面积", sortable: true, align: "right"},
			{name: "施工状态", index: "施工状态", width: 85, label: "施工状态", sortable: true, align: "left"},
			{name: "planspeeddescr", index: "planspeeddescr", width: 159, label: "计划进度", sortable: true, align: "left"},
			{name: "nowspeeddescr", index: "nowspeeddescr", width: 141, label: "实际进度", sortable: true, align: "left"},
			{name: "更新时间", index: "更新时间", width: 99, label: "更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "节点拖期", index: "节点拖期", width: 76, label: "节点拖期", sortable: true, align: "right"},
			{name: "拖期天数", index: "拖期天数", width: 86, label: "总拖期天数", sortable: true, align: "right"},
			{name: "延误说明", index: "延误说明", width: 182, label: "延误说明", sortable: true, align: "left"},
			{name: "开工令时间", index: "开工令时间", width: 90, label: "开工令时间", sortable: true, align: "left", formatter: formatTime},
			{name: "实际开工时间", index: "实际开工时间", width: 122, label: "实际开工时间", sortable: true, align: "left", formatter: formatTime},
			{name: "工期", index: "工期", width: 64, label: "工期", sortable: true, align: "right"},
			{name: "合同完工时间", index: "合同完工时间", width: 98, label: "合同完工时间", sortable: true, align: "left", formatter: formatTime},
			{name: "实际结算时间", index: "实际结算时间", width: 102, label: "实际结算时间", sortable: true, align: "left", formatter: formatTime},
			{name: "是否拖期", index: "是否拖期", width: 75, label: "是否拖期", sortable: true, align: "left"}
		],
	});
	
	$('#dataTablePager_left').attr('width',90);
    $('#dataTablePager_center').attr('width','850px');
    $('#dataTablePager_right').attr('align','left');
	
});

function doExcel(){
	var url = "${ctx}/admin/gcxxgl/doExcelForDelayDetail";
	doExcelAll(url);
}
	/* $('input','#').keydown(function(e){
		if(e.keyCode==13){
			query();
		}
	}); */

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
							<li>
								<label>工程部</label>
							<house:department2 id="Department2" dictCode="03"  depType="3"  ></house:department2>
							</li>
							<li>
								<label> 项目经理</label>
							<input type="text" id="projectMan" name="projectMan" style="width:160px;"  value="${customer.projectMan}" />
							</li>
							<li>
								<label>	楼盘地址</label>
							<input type="text" id="address" name="address" style="width:160px;"  value="${customer.address}" />
							</li>
							<li>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>	
				</form>
			</div>
			</div>
		<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " onclick="doExcel();"  >
						<span>导出Excel</span>
					</button>
				</div>
		</div>
			<div class="clear_float"></div>
			<!--query-form-->
				<div id="content-list" >
					<table id="dataTable"></table>
					<div id="dataTablePager" ></div>
				</div>
	</body>	
</html>
