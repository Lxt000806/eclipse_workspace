<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_gcjd",{
			url:"${ctx}/admin/customerXx/goJqGrid_gcjd",
			postData:{custCode:'${customer.code}'},
			height:500,
			rowNum: 10000,
			colModel : [
			    {name: "prjitemdescr", index: "prjitemdescr", width: 150, label: "施工项目", sortable: true, align: "left"},
				{name: "PlanBegin", index: "PlanBegin", width: 120, label: "计划开始日", sortable: true, align: "left", formatter: formatDate},
				{name: "BeginDate", index: "BeginDate", width: 120, label: "开始日期", sortable: true, align: "left", formatter: formatDate},
				{name: "PlanEnd", index: "PlanEnd", width: 120, label: "计划结束日", sortable: true, align: "left", formatter: formatDate},
				{name: "EndDate", index: "EndDate", width: 120, label: "结束日期", sortable: true, align: "left", formatter: formatDate},
				{name: "zwxm", index: "zwxm", width: 90, label: "验收人", sortable: true, align: "left"},
				{name: "ConfirmDate", index: "ConfirmDate", width: 120, label: "验收日期", sortable: true, align: "left", formatter: formatDate},
				{name: "LastUpdate", index: "LastUpdate", width: 150, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 100, label: "操作人员", sortable: true, align: "left"},
				{name: "Expired", index: "Expired", width: 100, label: "是否过期", sortable: true, align: "left"},
				{name: "ActionLog", index: "ActionLog", width: 100, label: "操作日志", sortable: true, align: "left"}
            ]
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_gcjd"></table>
	</div>
</div>
