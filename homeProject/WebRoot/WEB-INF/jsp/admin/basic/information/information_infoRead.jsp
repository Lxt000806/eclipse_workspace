<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>接收明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript"> 
	$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/information/goInfoReadJqGrid",
			postData:{number:"${information.number}"},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "department1", index: "department1", width: 90, label: "一级部门", sortable: true, align: "left"},
				{name: "department2", index: "department2", width: 90, label: "二级部门", sortable: true, align: "left"},
				{name: "department3", index: "department3", width: 90, label: "三级部门", sortable: true, align: "left"},
				{name: "namechi", index: "namechi", width: 90, label: "接收人", sortable: true, align: "left"},
				{name: "statusdesc", index: "statusdesc", width: 150, label: "阅读状态", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 150, label: "阅读时间", sortable: true, align: "left",formatter:formatTime},
			],
		});

	});
	</script>
</head>
<body>
		<div class="clear_float"></div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
</body>	
</html>
