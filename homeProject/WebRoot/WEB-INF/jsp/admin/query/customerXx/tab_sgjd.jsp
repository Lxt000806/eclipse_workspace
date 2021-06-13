<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_sgjd",{
			url:"${ctx}/admin/customerXx/goJqGrid_sgjd",
			postData:{custCode:'${customer.code}'},
			height:500,
			rowNum: 10000,
			colModel : [
			    {name: "worktype12descr", index: "worktype12descr", width: 150, label: "工种", sortable: true, align: "left"},
			    {name: "workerdescr", index: "workerdescr", width: 150, label: "工人", sortable: true, align: "left"},
			    {name: "comedate", index: "comedate", width: 150, label: "安排进场时间", sortable: true, align: "left",formatter:formatDate},
			    {name: "enddate", index: "enddate", width: 150, label: "完工时间", sortable: true, align: "left",formatter:formatDate},
			    {name: "工期", index: "工期", width: 150, label: "实际工期", sortable: true, align: "left"},
			    {name: "constructday", index: "constructday", width: 150, label: "计划工期", sortable: true, align: "left"},
			    {name: "mincrtdate", index: "mincrtdate", width: 150, label: "最早签到时间", sortable: true, align: "left",formatter:formatDate},
			    {name: "maxcrtdate", index: "maxcrtdate", width: 150, label: "最后签到时间", sortable: true, align: "left",formatter:formatDate},
            ]
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_sgjd"></table>
	</div>
</div>
