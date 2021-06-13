<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/perfCycle/goGxrxglsJqGrid",
			postData:{custCode:$("#custCode").val()},
		    rowNum:10000000,
			height:210,
			colModel : [
				{name: "opertypedescr", index: "opertypedescr", width: 88, label: "操作类型", sortable: true, align: "left"},
				{name: "roledescr", index: "roledescr", width: 96, label: "新角色", sortable: true, align: "left"},
				{name: "empcode", index: "empcode", width: 93, label: "新员工编号", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 113, label: "新员工姓名", sortable: true, align: "left"},
				{name: "oldroledescr", index: "oldroledescr", width: 96, label: "原角色", sortable: true, align: "left"},
				{name: "oldempcode", index: "oldempcode", width: 93, label: "原员工编号", sortable: true, align: "left"},
				{name: "oldempname", index: "oldempname", width: 113, label: "原员工姓名", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 115, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 92, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 130, label: "操作", sortable: true, align: "left"}
			], 
			loadonce:true
 		};
	   Global.JqGrid.initJqGrid("gxrxglsDataTable",gridOption);
});
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="gxrxglsDataTable"></table>
	</div>
</div>



