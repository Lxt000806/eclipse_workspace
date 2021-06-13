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
	<script src="${resourceRoot}/pub/component_autoArr.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	
	Global.JqGrid.initJqGrid("dataTable_noArrGr",{
		url:"${ctx}/admin/CustWorkerApp/goNoArrGRJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'WorkerCode',	index:'WorkerCode',	width:65,	label:'工人编号',	sortable:true,align:"left" ,},
				{name:'NameChi',	index:'NameChi',	width:70,	label:'工人姓名',	sortable:true,align:"left" ,},
				{name:'WorkType12',	index:'WorkType12',	width:70,	label:'工种',	sortable:true,align:"left" ,hidden:true},
				{name:'worktype12descr',	index:'worktype12descr',	width:70,	label:'工种',	sortable:true,align:"left" ,},
				{name:'ReqDate',	index:'ReqDate',	width:110,	label:'需求时间',	sortable:true,align:"left" ,formatter:formatDate,},
				{name:'IntroduceEmp',	index:'IntroduceEmp',	width:70,	label:'介绍人',	sortable:true,align:"left" ,hidden:true},
				{name:'introduceempdescr',	index:'introduceempdescr',	width:70,	label:'介绍人',	sortable:true,align:"left" ,},
				{name:'IsSign',	index:'IsSign',	width:70,	label:'是否签约',	sortable:true,align:"left" ,hidden:true},
				{name:'issigndescr',	index:'issigndescr',	width:50,	label:'签约',	sortable:true,align:"left" ,},
				{name:'SignDate',	index:'SignDate',	width:80,	label:'签约时间',	sortable:true,align:"left" ,formatter:formatDate,},
				{name:'leveldescr',	index:'leveldescr',	width:70,	label:'工人星级',	sortable:true,align:"left" ,},
				{name:'LiveRegion',	index:'LiveRegion',	width:80,	label:'一级居住区域',	sortable:true,align:"left" ,hidden:true},
				{name:'livedescr',	index:'livedescr',	width:80,	label:'一级居住区域',	sortable:true,align:"left" ,},
				{name:'LiveRegion2',	index:'LiveRegion2',	width:80,	label:'二级居住区域',	sortable:true,align:"left" ,hidden:true},
				{name:'live2descr',	index:'live2descr',	width:80,	label:'二级居住区域',	sortable:true,align:"left" ,},
				{name:'SpcBuilder',	index:'SpcBuilder',	width:70,	label:'归属专盘',	sortable:true,align:"left" ,hidden:true},
				{name:'spcdescr',	index:'spcdescr',	width:70,	label:'归属专盘',	sortable:true,align:"left" ,},
				{name:'BelongRegion',	index:'BelongRegion',	width:70,	label:'归属区域',	sortable:true,align:"left" ,hidden:true},
				{name:'belongregiondescr',	index:'belongregiondescr',	width:70,	label:'归属区域',	sortable:true,align:"left" ,},
				{name:'Department1',	index:'Department1',	width:80,	label:'事业部',	sortable:true,align:"left" ,hidden:true},
				{name:'department1descr',	index:'department1descr',	width:80,	label:'事业部',	sortable:true,align:"left" ,},
				{name:'Efficiency',	index:'Efficiency',	width:70,	label:'工作效率比',	sortable:true,align:"left" ,},
				{name:'workernum',	index:'workernum',	width:70,	label:'人数',	sortable:true,align:"left" ,},
		],
	});
	
}); 


</script>
</head>
	<body>
		<div class="body-box-list">
			<div id="content-list">
				<table id= "dataTable_noArrGr"></table>
			</div>
		</div>
	</body>	
</html>
