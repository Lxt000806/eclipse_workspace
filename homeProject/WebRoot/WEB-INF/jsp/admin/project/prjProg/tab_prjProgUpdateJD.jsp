<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/prjProg/goPrjProgUpdateJDJqGrid",
			height:520,
			colModel : [
				{name: "Address", index: "address", width: 140, label: "楼盘地址", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 60, label: "项目经理", sortable: true, align: "left"},
				{name: "prjitemdescr", index: "prjitemdescr", width: 140, label: "施工项目", sortable: true, align: "left"},
				{name: "planbegin", index: "planbegin", width: 100, label: "计划开始时间", sortable: true, align: "left", formatter: formatDate},
				{name: "begindate", index: "begindate", width: 100, label: "实际开始时间", sortable: true, align: "left", formatter: formatDate},
				{name: "planend", index: "planend", width: 100, label: "计划结束时间", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 135, label: "实际结束时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdate", index: "lastupdate", width: 135, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime}
			], 
 		};
       //初始化送货申请明细
	   Global.JqGrid.initEditJqGrid("dataTable",gridOption);


});
 </script>
<div class="body-box-list" style="margin-top: 0px;">

	<div class="pageContent">
		<table id="dataTable" ></table>
	</div>
</div>




