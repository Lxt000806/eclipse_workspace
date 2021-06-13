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
	<title>工程部工人安排</title>
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
		url:"${ctx}/admin/CustWorkerApp/goZFDetailJqGrid",
		postData:{code:"${custCode}"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "fixareadescr", index: "fixareadescr", width: 150, label: "区域名称", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 150, label: "材料名称", sortable: true, align: "left"},
			{name: "item2descr", index: "item2descr", width: 150, label: "材料类型2", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 75, label: "数量", sortable: true, align: "right"},
			{name: "remark", index: "remark", width: 180, label: "材料描述", sortable: true, align: "left"},
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
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</body>	
</html>
