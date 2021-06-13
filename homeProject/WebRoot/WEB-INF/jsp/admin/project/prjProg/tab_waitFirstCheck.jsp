<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<script type="text/javascript"> 
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_waitFirstCheck",{
		url:"${ctx}/admin/prjProg/goWaitFirstCheckJqGrid",
		postData:{department2:$("#department2").val(),projectMan:$("#projectMan").val()},
		height:$(document).height()-$("#content-list-2").offset().top-240,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "code", index: "code", width: 140, label: "客户编号", align: "left",hidden:true},
				{name: "address", index: "address", width: 140, label: "楼盘名称", align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 100, label: "项目经理", align: "left"},
				{name: "workername", index: "workername", width: 75, label: "工人", sortable: true, align: "left"},
				{name: "worktype12descr", index: "worktype12descr", width: 75, label: "工种", sortable: true, align: "left"},
				{name: "worksigndate", index: "worksigndate", width: 95, label: "工人完成时间", sortable: true, align: "left", formatter: formatDate},
				{name: "custtypedescr", index: "custtypedescr", width: 93, label: "客户类型", sortable: true, align: "left"},
				{name: "area", index: "area", width: 95, label: "面积", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 75, label: "工程部", sortable: true, align: "left"},
			],
		});
		
			
});
</script>
<div class="pageContent">
	<div id="content-list-2">
		<table id= "dataTable_waitFirstCheck"></table>
		<div id="dataTable_waitFirstCheckPager"></div>
	</div> 
</div>
