<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>干系人信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_commiCycle.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/commiCustStakeholder/goStakeholderJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			postData:{
				pk:"${commiCustStakeholder.pk}"
			},
			colModel : [
				{name: "empname", index: "empname", width: 70, label: "干系人", sortable: true, align: "left"},
				{name: "roledescr", index: "roledescr", width: 70, label: "角色", sortable: true, align: "left"},
				{name: "weightper", index: "weightper", width: 70, label: "权重", sortable: true, align: "right"},
				{name: "provideper", index: "provideper", width: 70, label: "比例", sortable: true, align: "right"},
				{name: "department1descr", index: "department1descr", width: 80, label: "一级部门", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 80, label: "二级部门", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"}
			],
		});
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
