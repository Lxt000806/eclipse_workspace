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
	<title>跟踪记录</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/ResrCust/goConJqGrid",
		postData:{code:"${resrCust.code}"},
		height:$(document).height()-$("#content-list").offset().top-82,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'custcode',	index:'custcode',	width:85,	label:'客户编号',	sortable:true,align:"left",},
			{name:'typedescr',	index:'typedescr',	width:90,	label:'跟踪类型',	sortable:true,align:"left",},
			{name:'conmandescr',	index:'conmandescr',	width:90,	label:'跟踪人',	sortable:true,align:"left",},
			{name:'condate',	index:'condate',	width:140,	label:'跟踪日期',	sortable:true,align:"left",formatter: formatTime},
			{name:'nextcondate',	index:'nextcondate',	width:95,	label:'下次联系时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'conwaydescr',	index:'conwaydescr',	width:80,	label:'跟踪方式',	sortable:true,align:"left",},
			{name:'remarks',	index:'remarks',	width:400,	label:'跟踪说明',	sortable:true,align:"left",},
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
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
		</div>
	</div>
</body>
</html>
