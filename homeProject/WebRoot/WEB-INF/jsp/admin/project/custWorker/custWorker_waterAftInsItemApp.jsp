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
	<title>工地工人安排</title>
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
			url:"${ctx}/admin/custWorker/goWaterAftInsItemAppJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: "Bootstrap", 
			postData:{custCode:"${custWorker.custCode}",workerCode:"${custWorker.workerCode}"},
			colModel : [
				{name: "itemtype2descr", index: "itemtype2descr", width: 120, label: "材料类型2", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 120, label: "安装材料", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 120, label: "数量", sortable: true, align: "left"},
				{name: "uomdescr", index: "uomdescr", width: 90, label: "单位", sortable: true, align: "left"},
				{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatDate},
			],
		});
		
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>	
		</div>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${custWorker.address }" readonly/>
						</li>
						<li>
							<label>工人</label>
							<input type="text" id="workerCode" name="workerCode" style="width:160px;" value="${custWorker.workerName }" readonly/>
						</li>
						
					</ul>
				</form>
			</div>
		</div>
	</div>
	<div id="content-list">
		<table id= "dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
	</body>	
</html>
