<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_jzzj_htfyzj",{
			url:"${ctx}/admin/customerXx/goJqGrid_jzzj_htfyzj",
			postData:{custCode:'${customer.code}'},
			rowNum: 10000,
			height:450,
			colModel : [
				{name: "pk", index: "pk", width: 100, label: "增减编号", sortable: true, align: "left", count: true, hidden: true},
				{name: "code", index: "code", width: 100, label: "客户编号", sortable: true, align: "left", hidden: true},
				{name: "descr", index: "descr", width: 100, label: "客户名称", sortable: true, align: "left", hidden: true},
				{name: "address", index: "address", width: 200, label: "楼盘地址", sortable: true, align: "left", hidden: true},
				{name: "chgtypedescr", index: "chgtypedescr", width: 150, label: "费用类型", sortable: true, align: "left"},
				{name: "chgamount", index: "chgamount", width: 100, label: "增减金额", sortable: true, align: "right", sum: true},
				{name: "statusdescr", index: "statusdescr", width: 100, label: "状态", sortable: true, align: "left"},
				{name: "confirmczydescr", index: "confirmczydescr", width: 100, label: "审核人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 150, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "appczydescr", index: "appczydescr", width: 100, label: "申请人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 150, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
				{name: "lastupdatedbydescr", index: "lastupdatedbydescr", width: 70, label: "最后修改人", sortable: true, align: "left", hidden: true}
            ]
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_jzzj_htfyzj"></table>
	</div>
</div>
