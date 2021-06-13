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
	<title>查看申请退回</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#custCode").openComponent_customer();	

	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/CustWorkerApp/getReturnDetail",
		postData:{dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val()},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: "Bootstrap", 
		colModel : [
				{name:"custcode",	index:"custcode",	width:85,	label:"客户编号",	sortable:true,align:"left" ,},
				{name:"address",	index:"address",	width:160,	label:"楼盘",	sortable:true,align:"left" ,},
				{name:"msgtext",	index:"msgtext",	width:500,	label:"退回原因",	sortable:true,align:"left" ,},
				{name:"crtdate",	index:"crtdate",	width:130,	label:"退回时间",	sortable:true,align:"left" ,formatter:formatTime},
		],
	});
	
}); 
function clearForm(){
	$("#page_form input[type='text']").val("");
		$("#openComponent_customer_custCode").val("");
		$("#custCode").val("");
}

function doExcel(){
	var url = "${ctx}/admin/CustWorkerApp/doReturnDetailExcel";
		doExcelAll(url);
}

</script>
</head>
	<body>
		<div class="panel panel-system" >
		    <div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
					<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
						<span>导出excel</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>客户编号</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;"/>
					</li>
					<li class="form-validate">
						<label>退回时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  
						style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorkerApp.dateFrom }' pattern='yyyy-MM-dd'/>" />
					</li>
					<li class="form-validate">
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"  
						style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorkerApp.dateTo }' pattern='yyyy-MM-dd'/>" />
					</li>
					<li >
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="body-box-list">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</body>	
</html>
