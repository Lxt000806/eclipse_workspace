<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_gxr_xgls",{
			url:"${ctx}/admin/customerXx/goJqGrid_gxr_xgls",
			postData:{custCode:'${customer.code}'},
			height:450,
			rowNum: 10000,
			colModel : [
			    {name: "opertypedescr", index: "opertypedescr", width: 80, label: "操作类型", sortable: true, align: "left"},
				{name: "roledescr", index: "roledescr", width: 80, label: "新角色", sortable: true, align: "left"},
				{name: "empcode", index: "empcode", width: 90, label: "新员工编号", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 90, label: "新员工姓名", sortable: true, align: "left"},
				{name: "oldroledescr", index: "oldroledescr", width: 80, label: "原角色", sortable: true, align: "left"},
				{name: "oldempcode", index: "oldempcode", width: 90, label: "原员工编号", sortable: true, align: "left"},
				{name: "oldempname", index: "oldempname", width: 90, label: "原员工姓名", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 150, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 100, label: "操作", sortable: true, align: "left"}
            ]
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_gxr_xgls"></table>
	</div>
</div>
