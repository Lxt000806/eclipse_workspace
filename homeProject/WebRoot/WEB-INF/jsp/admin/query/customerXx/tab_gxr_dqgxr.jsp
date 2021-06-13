<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_gxr_dqgxr",{
			url:"${ctx}/admin/customerXx/goJqGrid_gxr_dqgxr",
			postData:{custCode:'${customer.code}'},
			height:450,
			rowNum: 10000,
			colModel : [
			    {name: "roledescr", index: "roledescr", width: 150, label: "角色", sortable: true, align: "left"},
				{name: "empcode", index: "empcode", width: 100, label: "员工编号", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 100, label: "员工姓名", sortable: true, align: "left"},
				{name: "positiondescr", index: "positiondescr", width: 100, label: "职位", sortable: true, align: "left"},
				{name: "phone", index: "phone", width: 100, label: "电话", sortable: true, align: "left"},
				{name: "empstatusdescr", index: "empstatusdescr", width: 80, label: "员工状态", sortable: true, align: "left"},
				{name: "department1descr", index: "department1descr", width: 100, label: "一级部门", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 100, label: "二级部门", sortable: true, align: "left"},
				{name: "leaderdescr", index: "leaderdescr", width: 100, label: "部门经理", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 150, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"}
            ]
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_gxr_dqgxr"></table>
	</div>
</div>
