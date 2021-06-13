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
	<title>未安排工地</title>
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
	
	Global.JqGrid.initJqGrid("dataTable_noArrGd",{
		url:"${ctx}/admin/CustWorkerApp/goNoArrGdJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'CustCode',	index:'CustCode',	width:75,	label:'客户编号',	sortable:true,align:"left" ,},
				{name:'address',	index:'address',	width:110,	label:'楼盘',	sortable:true,align:"left" ,},
				{name:'custdescr',	index:'custdescr',	width:75,	label:'客户名称',	sortable:true,align:"left" ,},
				{name:'Area',	index:'Area',	width:50,	label:'面积',	sortable:true,align:"left" ,},
				{name:'WorkType12',	index:'WorkType12',	width:60,	label:'工种',	sortable:true,align:"left" ,hidden:true},
				{name:'worktype12descr',	index:'worktype12descr',	width:70,	label:'工种',	sortable:true,align:"left" ,},
				{name:'AppComeDate',	index:'AppComeDate',	width:110,	label:'申请进场时间',	sortable:true,align:"left" ,formatter:formatDate},
				{name:'prjnormday',	index:'prjnormday',	width:70,	label:'标准工期天数',	sortable:true,align:"left" ,},
				{name:'CustType',	index:'CustType',	width:70,	label:'客户类型',	sortable:true,align:"left" ,hidden:true},
				{name:'custtypedescr',	index:'custtypedescr',	width:70,	label:'客户类型',	sortable:true,align:"left" ,},
				{name:'ProjectMan',	index:'ProjectMan',	width:70,	label:'项目经理',	sortable:true,align:"left" ,hidden:true},
				{name:'projectmandescr',	index:'projectmandescr',	width:70,	label:'项目经理',	sortable:true,align:"left" ,},
				{name:'IsSupvr',	index:'IsSupvr',	width:70,	label:'是否监理',	sortable:true,align:"left" ,hidden:true},
				{name:'issupdescr',	index:'issupdescr',	width:70,	label:'是否监理',	sortable:true,align:"left" ,},
				{name:'PrjLevel',	index:'PrjLevel',	width:70,	label:'工地星级',	sortable:true,align:"left" ,hidden:true},
				{name:'prjleveldescr',	index:'prjleveldescr',	width:70,	label:'监理星级',	sortable:true,align:"left" ,},
				{name:'Department1',	index:'Department1',	width:80,	label:'事业部',	sortable:true,align:"left" ,hidden:true},
				{name:'department1descr',	index:'department1descr',	width:80,	label:'事业部',	sortable:true,align:"left" ,},
				{name:'SpcBuilder',	index:'SpcBuilder',	width:60,	label:'专盘',	sortable:true,align:"left" ,hidden:true},
				{name:'spcdescr',	index:'spcdescr',	width:60,	label:'专盘',	sortable:true,align:"left" ,},
				{name:'RegionCode',	index:'RegionCode',	width:70,	label:'一级区域',	sortable:true,align:"left" ,hidden:true},
				{name:'region1descr',	index:'region1descr',	width:70,	label:'一级区域',	sortable:true,align:"left" ,},
				{name:'RegIsSpcWorker',	index:'RegIsSpcWorker',	width:70,	label:'指定工人',	sortable:true,align:"left" ,hidden:true},
				{name:'regisspcworkerdescr',	index:'regisspcworkerdescr',	width:70,	label:'指定工人',	sortable:true,align:"left" ,},
				{name:'RegionCode2',	index:'RegionCode2',	width:70,	label:'二级区域',	sortable:true,align:"left" ,hidden:true},
				{name:'region2descr',	index:'region2descr',	width:70,	label:'二级区域',	sortable:true,align:"left" ,},
		],
	});
	
}); 


</script>
</head>
	<body>
		<div class="body-box-list">
			<div id="content-list">
				<table id= "dataTable_noArrGd"></table>
			</div>
		</div>
	</body>	
</html>
