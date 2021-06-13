<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_khgz",{
			url:"${ctx}/admin/customerXx/goJqGrid_khgz",
			postData:{custCode:'${customer.code}'},
			height:500,
			colModel : [
			    {name: "condate", index: "condate", width: 120, label: "联系时间", sortable: true, align: "left", formatter: formatDate},
				{name: "conmandescr", index: "conmandescr", width: 100, label: "联系人", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 229, label: "联系内容", sortable: true, align: "left"},
				{name: "designmandescr", index: "designmandescr", width: 100, label: "设计师", sortable: true, align: "left"},
				{name: "businessmandescr", index: "businessmandescr", width: 100, label: "业务员", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 150, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"}
            ]
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_khgz" style="overflow: auto;"></table>
	</div>
</div>
