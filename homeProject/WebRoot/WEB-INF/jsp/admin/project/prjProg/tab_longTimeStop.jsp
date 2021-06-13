<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<script type="text/javascript"> 
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_longTimeStop",{
		url:"${ctx}/admin/prjProg/goLongTimeStopJqGrid",
		postData:{department2:$("#department2").val(),projectMan:$("#projectMan").val(),stopDays:$("#stopDays").val()==''?0:$("#stopDays").val(),
		        prgRmkDateTo:$("#prgRmkDateTo").val(),constructStatus:$("#constructStatus").val(),prjItem:$("#prjProgTempNo").val()},
		height:$(document).height()-$("#content-list-1").offset().top-120,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "code", index: "code", width: 140, label: "客户编号", align: "left",hidden:true},
				{name: "address", index: "address", width: 140, label: "楼盘名称", align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 100, label: "项目经理", align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 75, label: "开工日期", sortable: true, align: "left", formatter: formatDate},
				{name: "delaydays", index: "delaydays", width: 75, label: "总拖期", sortable: true, align: "left"},
				{name: "stopdays", index: "stopdays", width: 75, label: "停工天数", sortable: true, align: "left"},
				{name: "lastsigndate", index: "lastsigndate", width: 95, label: "最后签到日期", sortable: true, align: "left", formatter: formatDate},
				{name: "lastsignworktype12descr", index: "lastsignworktype12descr", width: 93, label: "最后签到工种", sortable: true, align: "left"},
				{name: "lastconfirmprjitemdescr", index: "lastconfirmprjitemdescr", width: 100, label: "最后验收节点", sortable: true, align: "left"},
				{name: "prgremark", index: "prgremark", width: 100, label: "停工备注", sortable: true, align: "left"},
				{name: "prgrmkdate", index: "prgrmkdate", width: 95, label: "备注日期", sortable: true, align: "left",formatter: formatDate},
				{name: "custtypedescr", index: "custtypedescr", width: 75, label: "客户类型", sortable: true, align: "left"},
				{name: "area", index: "area", width: 95, label: "面积", sortable: true, align: "left"},
				{name: "constructstatusdescr", index: "constructstatusdescr", width: 95, label: "施工状态", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 75, label: "工程部", sortable: true, align: "left"},
			],
		});
});
</script>
<div class="pageContent">
	<div id="content-list-1">
		<table id= "dataTable_longTimeStop"></table>
		<div id="dataTable_longTimeStopPager"></div>
	</div> 
</div>
