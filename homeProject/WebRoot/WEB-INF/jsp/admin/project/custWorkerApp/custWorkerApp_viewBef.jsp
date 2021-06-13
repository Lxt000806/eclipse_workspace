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
	<title>查看上工种验收情况</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/CustWorkerApp/goWorkTypeBefJqGrid",
		postData:{custCode:"${custCode }",workType12:"${workType12 }"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "worktype12descr", index: "worktype12descr", width: 80, label: "当前工种", sortable: true, align: "left"},
			{name: "befdescr", index: "befdescr", width: 80, label: "上一工种", sortable: true, align: "left"},
			{name: "date", index: "date", width: 80, label: "验收时间", sortable: true, align: "left",formatter:formatDate},   
			{name: "lastupdatedbydescr", index: "lastupdatedbydescr", width: 80, label: "验收人", sortable: true, align: "left"},   
			{name: "ispass", index: "ispass", width: 80, label: "验收情况", sortable: true, align: "left",},
			{name: "prjpassdate", index: "prjpassdate", width: 120, label: "初检时间", sortable: true, align: "left", formatter: formatTime}
		],
	});
});


</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
    			<div class="panel-body" >
      				<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>	
				<div class="query-form" hidden="false">
			  		<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<input type="hidden" id="custCode" name="custCode" value="${custCode }" />
						<input type="hidden" id="workType12" name="workType12" value="${workType12 }" />
					</form>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</body>	
</html>
