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
	<title>资源客户操作日志</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable_total",{
		url:"${ctx}/admin/gcxxgl/goTotalDelayJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
				styleUI: 'Bootstrap', 
		
		colModel : [
			{name: "工程部", index: "工程部", width: 109, label: "工程部", sortable: true, align: "left"},
			{name: "在建工地", index: "在建工地", width: 106, label: "在建工地", sortable: true, align: "right", sum: true},
			{name: "拖期数", index: "拖期数", width: 110, label: "拖期数", sortable: true, align: "right", sum: true},
			{name: "拖期占比", index: "拖期占比", width: 110, label: "拖期占比", sortable: true, align: "right",avg:true},
			{name: "delayrank", index: "delayrank", width: 121, label: "拖期排名", sortable: true, align: "right"},
			{name: "ondorank", index: "ondorank", width: 105, label: "在建排名", sortable: true, align: "right"}
		],
		gridComplete:function(){
				var delayAvg=0;
				var sumOnDo=0;
				var sumTotal=0;
				var onDo = Global.JqGrid.allToJson("dataTable_total","在建工地");
				var total = Global.JqGrid.allToJson("dataTable_total","拖期数");
				arr = onDo.fieldJson.split(",");	
				arry = total.fieldJson.split(",");
				for(var i = 0;i < arry.length; i++){
					sumOnDo=sumOnDo+parseInt(arr[i]);
					sumTotal=sumTotal+parseInt(arry[i]);
				}
		   	   $("#dataTable_total").footerData("set",{"拖期占比":(sumTotal/sumOnDo).toFixed(3)},false);
		  }
	});
});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " onclick="doExcelNow('工程拖期总汇','dataTable_total');"  >
							<span>导出Excel</span>
						</a>
					</div>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable_total"></table>
			</div>
		</div>
	</body>	
</html>
