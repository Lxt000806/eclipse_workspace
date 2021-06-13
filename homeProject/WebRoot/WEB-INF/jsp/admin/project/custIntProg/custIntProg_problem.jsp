<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
		Global.JqGrid.initJqGrid("dataTable2",{
		url:"${ctx}/admin/custProblem/goJqGridDetail",
		postData : {'custCode' : "${custIntProg.custcode}"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
			{name: "promtype1descr", index: "promtype1descr", width: 116, label: "问题分类", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 126, label: "供应商", sortable: true, align: "left"},
			{name: "promtype2descr", index: "promtype2descr", width: 114, label: "材料分类", sortable: true, align: "left"},
			{name: "promrsndescr", index: "promrsndescr", width: 90, label: "原因", sortable: true, align: "left"},
			{name: "infodate", index: "infodate", width: 82, label: "通知时间", sortable: true, align: "left", formatter: formatTime},
			{name: "dealczydescr", index: "dealczydescr", width: 76, label: "接收人", sortable: true, align: "left"},
			{name: "plandealdate", index: "plandealdate", width: 99, label: "计划处理时间", sortable: true, align: "left", formatter: formatTime},
			{name: "dealdate", index: "dealdate", width: 98, label: "实际处理时间", sortable: true, align: "left", formatter: formatTime},
			{name: "statusdescr", index: "statusdescr", width: 86, label: "是否处理", sortable: true, align: "left"},
			{name: "dealremarks", index: "dealremarks", width: 286, label: "处理结果", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 147, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 76, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 94, label: "操作标志", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left", hidden: true}
			],
		});
	});
</script>
<div class="body-box-list" style="margin-top: 0px;">
			<!--query-form-->
				<div id="content-list" >
					<table id="dataTable2"></table>
				</div>
		</div>
