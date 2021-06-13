<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>失败任务</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/ResrCust/goFailedExcelJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		postData:{taskPK:"${resrCustExcelFailed.taskPK}"},
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'filerowsdescr',	index:'filerowsdescr',	width:90,	label:'源文件位置',	sortable:true,align:"left",},
				{name:'emps',	index:'emps',	width:150,	label:'导入给何人',	sortable:true,align:"left"},
				{name:'failedreason',	index:'failedreason',	width:150,	label:'失败原因',	sortable:true,align:"left"},
				{name:'failedfieldname',	index:'failedfieldname',	width:120,	label:'导致失败的字段名',	sortable:true,align:"left"},
				{name:'failedfieldcontent',	index:'failedfieldcontent',	width:120,	label:'错误字段的内容',	sortable:true,align:"left"},
				{name:'existscustname',	index:'existscustname',	width:120,	label:'已存在的客户名',	sortable:true,align:"left"},
				{name:'existsbusinessman',	index:'existsbusinessman',	width:120,	label:'已存在客户跟单员',	sortable:true,align:"left"},
				{name:'lastupdate',	index:'lastupdate',	width:120,	label:'创建时间',	sortable:true,align:"left",formatter: formatTime},
		],
	});
});

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " 
						onclick="doExcelNow('失败任务明细','dataTable','page_form')">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		 <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			<input type="hidden" name="jsonString" value="" />
		</form>
		<div id="content-list">
			<table id= "dataTable"></table>
		</div>
	</div>
</body>
</html>
