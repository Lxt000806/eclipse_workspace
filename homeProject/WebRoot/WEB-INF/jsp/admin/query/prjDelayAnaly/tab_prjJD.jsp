<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/*工种施工进度*/
$(function(){
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable_sgjd",{
		url:"${ctx}/admin/prjDelayAnaly/goJqGrid_sgjd",
		postData: {custCode:'${customer.code}'},
		rowNum: 10000,
		height: $(document).height()-$("#content-list-2").offset().top-300,
		colModel : [
		    {name: "worktype12descr", index: "worktype12descr", width: 75, label: "工种", sortable: true, align: "left"},
		    {name: "workerdescr", index: "workerdescr", width: 75, label: "工人", sortable: true, align: "left"},
			{name: "beginday", index: "beginday", width: 75, label: "开工日", sortable: true, align: "left"},
		    {name: "endday", index: "endday", width: 75, label: "结束日", sortable: true, align: "left"},
		    {name: "工期", index: "工期", width: 90, label: "实际工期", sortable: true, align: "left"},
		    {name: "constructday", index: "constructday", width: 75, label: "计划工期", sortable: true, align: "left"},
		    {name: "signtimes", index: "signtimes", width: 75, label: "签到天数", sortable: true, align: "left"},
		    {name: "delaydays", index: "delaydays", width: 75, label: "延误天数", sortable: true, align: "left",cellattr: addCellAttr},
		    {name: "appcomedate", index: "appcomedate", width: 120, label: "申请进场时间", sortable: true, align: "left",formatter:formatDate},
		    {name: "comedate", index: "comedate", width: 120, label: "安排进场时间", sortable: true, align: "left",formatter:formatDate},
		    {name: "enddate", index: "enddate", width: 120, label: "完工时间", sortable: true, align: "left",formatter:formatDate},
		    {name: "mincrtdate", index: "mincrtdate", width: 120, label: "最早签到时间", sortable: true, align: "left",formatter:formatDate},
		    {name: "maxcrtdate", index: "maxcrtdate", width: 120, label: "最后签到时间", sortable: true, align: "left",formatter:formatDate},
		    {name: "prjpassdate", index: "prjpassdate", width: 120, label: "初检日期", sortable: true, align: "left",formatter:formatDate},
		    {name: "workcompletedate", index: "workcompletedate", width: 120, label: "工人完工日期", sortable: true, align: "left",formatter:formatDate},
		    {name: "comfirmpassdate", index: "comfirmpassdate", width: 120, label: "验收日期", sortable: true, align: "left",formatter:formatDate},
        ]
	});
});

function addCellAttr(rowId, val, rawObject, cm, rdata) {
    if (rawObject.delaydays>0) {
        return "style='color:red'";
    }
}

</script>
<div class="body-box-list">
	<div id="content-list-2" >
		<table id="dataTable_sgjd"></table>
	</div>
</div>







