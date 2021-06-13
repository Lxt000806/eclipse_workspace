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
	<title>砌墙工作量</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_autoArr.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable_qqgzl",{
		url:"${ctx}/admin/CustWorkerApp/getWorkerArrJqGrid",
		postData:{code:'${customer.code }'},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'namechi',	index:'namechi',	width:85,	label:'工人名称',	sortable:true,align:"left" ,},
				{name:'worktype12descr',	index:'worktype12descr',	width:115,	label:'工种',	sortable:true,align:"left" ,},
				{name:'comedate',	index:'comedate',	width:75,	label:'进场时间',	sortable:true,align:"left" ,formatter:formatDate},

		],
	});
	
}); 


</script>
</head>
	<body>
		<div class="panel panel-system" >
		    <div class="panel-body" >
		      <div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
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
						<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }"/>
					</li>
				</ul>
			</form>
		</div>
		<div class="body-box-list">
			<div id="content-list">
				<table id= "dataTable_qqgzl"></table>
			</div>
		</div>
	</body>	
</html>
