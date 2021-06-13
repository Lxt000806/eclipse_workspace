<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<script type="text/javascript"> 
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_waitCustWorkApp",{
		url:"${ctx}/admin/prjProg/goWaitCustWorkAppJqGrid",
		postData:{department2:$("#department2").val(),projectMan:$("#projectMan").val(),constructStatus:$("#constructStatus").val()},
		height:$(document).height()-$("#content-list-3").offset().top-280,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "code", index: "code", width: 140, label: "客户编号", align: "left",hidden:true},
				{name: "address", index: "address", width: 140, label: "楼盘名称", align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 93, label: "客户类型", sortable: true, align: "left"},
				{name: "area", index: "area", width: 95, label: "面积", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 100, label: "项目经理", align: "left"},
				{name: "department2descr", index: "department2descr", width: 75, label: "工程部", sortable: true, align: "left"},
				{name: "waitapplyworktype12descr", index: "waitapplyworktype12descr", width: 105, label: "待申请工人工种", sortable: true, align: "left"},
				{name: "befworktype12date", index: "befworktype12date", width: 120, label: "上一工种完成时间", sortable: true, align: "left", formatter: formatDate},
				{name: "befworktype12descr", index: "befworktype12descr", width: 75, label: "上一工种", sortable: true, align: "left"},
				{name: "prgremark", index: "prgremark", width: 100, label: "停工备注", sortable: true, align: "left"},
				{name: "prgrmkdate", index: "prgrmkdate", width: 95, label: "备注日期", sortable: true, align: "left",formatter: formatDate},
				
			],
		});
});
</script>
<div class="pageContent">
	<div id="content-list-3">
		<table id= "dataTable_waitCustWorkApp"></table>
		<div id="dataTable_waitCustWorkAppPager"></div>
	</div> 
</div>
