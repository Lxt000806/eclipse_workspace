	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
	$(function(){
		Global.JqGrid.initJqGrid("dataTable1",{
		url:"${ctx}/admin/prjJob/goJqGridDetail",
		postData : {'custCode' : "${custIntProg.custcode}"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "address", index: "address", width: 176, label: "楼盘", sortable: true, align: "left"},
			{name: "date", index: "date", width: 136, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
			{name: "statusdescr", index: "statusdescr", width: 87, label: "状态", sortable: true, align: "left"},
			{name: "jobtypedescr", index: "jobtypedescr", width: 99, label: "任务类型", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 236, label: "备注", sortable: true, align: "left"},
			{name: "enddescr", index: "enddescr", width: 85, label: "处理结果", sortable: true, align: "left"},
			{name: "dealczydescr", index: "dealczydescr", width: 101, label: "处理人", sortable: true, align: "left"},
			{name: "dealdate", index: "dealdate", width: 153, label: "实际处理时间", sortable: true, align: "left", formatter: formatTime}
			],
		});

	});
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
		          </div> 		
			<div class="clear_float"></div>
			<!--query-form-->
				<div id="content-list" >
					<table id="dataTable1"></table>
				</div>
		</div>
