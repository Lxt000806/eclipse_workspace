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
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
<style>
<!--
.ui-th-column, .ui-jqgrid .ui-jqgrid-htable th.ui-th-column{
vertical-align: middle;
}
-->
</style>
<script type="text/javascript"> 
$(function(){
	
	Global.JqGrid.initJqGrid("dataTable_hasArrGd",{
		url:"${ctx}/admin/CustWorkerApp/goHasArrGdJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
	   useColSpanStyle: true, 
		colModel : [
				{name:'address',	index:'address',	width:110,	label:'楼盘',	sortable:true,align:"left" ,},
				{name:'projectmandescr',	index:'projectmandescr',	width:70,	label:'项目经理',	sortable:true,align:"left" ,},
				{name:'workerdescr',	index:'workerdescr',	width:75,	label:'工人姓名',	sortable:true,align:"left" ,},
				{name:'AppComeDate',	index:'AppComeDate',	width:110,	label:'申请进场时间',	sortable:true,align:"left" ,formatter:formatDate},
				{name:'ComeDate',	index:'ComeDate',	width:100,	label:'安排进场时间',	sortable:true,align:"left" ,formatter:formatDate},
				{name:'CustCode',	index:'CustCode',	width:80,	label:'客户编号',	sortable:true,align:"left" ,},
				{name:'custdescr',	index:'custdescr',	width:80,	label:'客户姓名',	sortable:true,align:"left" ,},
				{name:'ProjectMan',	index:'ProjectMan',	width:70,	label:'项目经理',	sortable:true,align:"left" ,},
				{name:'prjleveldescr',	index:'prjleveldescr',	width:70,	label:'监理星级',	sortable:true,align:"left" ,},
				{name:'custtypedescr',	index:'custtypedescr',	width:70,	label:'客户类型',	sortable:true,align:"left" ,},
				{name:'Area',	index:'Area',	width:50,	label:'面积',	sortable:true,align:"left" ,},
				{name:'worktype12descr',	index:'worktype12descr',	width:50,	label:'工种',	sortable:true,align:"left" ,},
				{name:'issupdescr',	index:'issupdescr',	width:70,	label:'是否监理',	sortable:true,align:"left" ,},
				{name:'region1descr',	index:'region1descr',	width:70,	label:'一级区域',	sortable:true,align:"left" ,},
				{name:'regdescr',	index:'regdescr',	width:70,	label:'指定工人',	sortable:true,align:"left" ,},
				{name:'region2descr',	index:'region2descr',	width:70,	label:'二级区域',	sortable:true,align:"left" ,},
				{name:'department1descr',	index:'department1descr',	width:80,	label:'事业部',	sortable:true,align:"left" ,},
				{name:'spcdescr',	index:'spcdescr',	width:60,	label:'专盘',	sortable:true,align:"left" ,},
				{name:'WorkerCode',	index:'WorkerCode',	width:75,	label:'工人编号',	sortable:true,align:"left" ,},
				{name:'workerworktype12',	index:'workerworktype12',	width:70,	label:'工种',	sortable:true,align:"left" ,},
				{name:'reqdate',	index:'reqdate',	width:100,	label:'需求时间',	sortable:true,align:"left" ,formatter:formatDate},
				{name:'introduceempdescr',	index:'introduceempdescr',	width:70,	label:'介绍人',	sortable:true,align:"left" ,},
				{name:'issigndescr',	index:'issigndescr',	width:50,	label:'签约',	sortable:true,align:"left" ,},
				{name:'signdate',	index:'signdate',	width:100,	label:'签约时间',	sortable:true,align:"left" ,formatter:formatDate},
				{name:'workerlevel',	index:'workerlevel',	width:70,	label:'工人星级',	sortable:true,align:"left" ,},
				{name:'liveregion1',	index:'liveregion1',	width:70,	label:'一级居住区域',	sortable:true,align:"left" ,},
				{name:'liveregion2',	index:'liveregion2',	width:70,	label:'二级居住区域',	sortable:true,align:"left" ,},
				{name:'belongregiondescr',	index:'belongregiondescr',	width:70,	label:'归属区域',	sortable:true,align:"left" ,},
				{name:'workerdepartment1',	index:'workerdepartment1',	width:70,	label:'归属部门',	sortable:true,align:"left" ,},
				{name:'workerspcbuilder',	index:'workerspcbuilder',	width:70,	label:'归属专盘',	sortable:true,align:"left" ,},
				{name:'efficiency',	index:'efficiency',	width:80,	label:'工作效率比',	sortable:true,align:"left" ,},
				{name:'workernum',	index:'workernum',	width:60,	label:'人数',	sortable:true,align:"left" ,},
		],
	});
	
	 $("#dataTable_hasArrGd").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[{startColumnName: 'WorkerCode', numberOfColumns: 14, titleText: '工人信息'},
						{startColumnName: 'CustCode', numberOfColumns: 13, titleText: '工地信息'}
		],
	}); 
	 
}); 


</script>
</head>
	<body>
		<div class="body-box-list">
			<div id="content-list">
				<table id= "dataTable_hasArrGd"></table>
			</div>
		</div>
	</body>	
</html>
