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
	<title>修改日志</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custWorker/goLogJqGrid",
		postData:{custCode:"${custWorker.custCode}",workType12:"${custWorker.workType12}"},
		height:$(document).height()-$("#content-list").offset().top-82,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'workername',	index:'workername',	width:90,	label:'工人姓名',	sortable:true,align:"left",},
				{name:'opertypedescr',	index:'opertypedescr',	width:70,	label:'修改类型',	sortable:true,align:"left",},
				{name:'comedate',	index:'comedate',	width:90,	label:'进场时间',	sortable:true,align:"left",formatter: formatDate},
				{name:'lastupdatedby',	index:'lastupdatedby',	width:95,	label:'最后操作人员',	sortable:true,align:"left",hidden:"${resrCust.type}" =="1"?true:false},
				{name:'lastupdate',	index:'lastupdate',	width:150,	label:'最后修改时间',	sortable:true,align:"left",formatter: formatTime},
				{name:'expired',	index:'expired',	width:60,	label:'过期',	sortable:true,align:"left",},
				{name:'actionlog',	index:'actionlog',	width:80,	label:'操作日志',	sortable:true,align:"left",hidden:"${resrCust.type}" =="1"?true:false},
		],
		loadonce:true
	});
});

</script>
</head>
<body>
	<a id="downloadElem" download style="display: none"></a>
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
