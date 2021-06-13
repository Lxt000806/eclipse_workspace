<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<script type="text/javascript"> 
$(function(){

	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_timeOutEnd",{
		url:"${ctx}/admin/prjProg/goTimeOutEndJqGrid",
		postData:{department2:$("#department2").val(),projectMan:$("#projectMan").val(),timeOutDays:$("#timeOutDays").val()==''?0:$("#timeOutDays").val(),workType12:$("#workType12").val(),custWorkerCustStatus:$("#CUSTWKSTATUS").val()},
		height:$(document).height()-$("#content-list-4").offset().top-280,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "code", index: "code", width: 140, label: "客户编号", align: "left",hidden:true},
				{name: "address", index: "address", width: 140, label: "楼盘名称", align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 75, label: "客户类型", sortable: true, align: "left"},
				{name: "area", index: "area", width: 50, label: "面积", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 75, label: "项目经理", align: "left"},
				{name: "department2descr", index: "department2descr", width: 75, label: "工程部", sortable: true, align: "left"},
				{name: "worktype12descr", index: "worktype12descr", width: 75, label: "工种", sortable: true, align: "left"},
				{name: "workername", index: "workername", width: 75, label: "工人", sortable: true, align: "left"},
				{name: "comedate", index: "comedate", width: 75, label: "进场时间", sortable: true, align: "left", formatter: formatDate},
				{name: "planend", index: "planend", width: 100, label: "计划完成时间", sortable: true, align: "left", formatter: formatDate},
				{name: "timeoutdays", index: "timeoutdays", width: 75, label: "超时天数", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 100, label: "工人施工备注", sortable: true, align: "left"},
				{name: "worksigndate", index: "worksigndate", width: 100, label: "工人完成时间", sortable: true, align: "left", formatter: formatDate},
				{name: "prjpassdate", index: "prjpassdate", width: 75, label: "初检日期", sortable: true, align: "left", formatter: formatDate},
				{name: "earliestsigndate", index: "earliestsigndate", width: 100, label: "最早签到日期", sortable: true, align: "left", formatter: formatDate},
				{name: "lastsigndate", index: "lastsigndate", width: 100, label: "最后签到日期", sortable: true, align: "left", formatter: formatDate},
				{name: "statusdescr", index: "statusdescr", width: 75, label: "是否停工", sortable: true, align: "left"},
			],
		});
});
</script>
<div class="pageContent">
	<div id="content-list-4">
		<table id= "dataTable_timeOutEnd"></table>
		<div id="dataTable_timeOutEndPager"></div>
	</div> 
</div>
