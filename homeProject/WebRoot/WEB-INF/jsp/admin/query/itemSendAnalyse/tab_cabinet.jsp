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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
<script>
var postData = $("#page_form").jsonForm(); 
$(function(){
	/* 借用itemAPP的itemType来表示是哪个材料  */
	postData.itemType="3";
	Global.JqGrid.initJqGrid("dataTable_cabinet",{
		url:"${ctx}/admin/itemSendAnalyse/goItemJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: "Bootstrap",
		rowNum:100000,
		colModel : [
				{name:"iasno",	index:"iasno",	width:85,	label:"发货单号",	sortable:true,align:"left" ,},
				{name:"iano",	index:"iano",	width:85,	label:"领料单号",	sortable:true,align:"left" ,},
				{name:"itemcode",	index:"itemcode",	width:85,	label:"材料编号",	sortable:true,align:"left" ,},
				{name:"itemdescr",	index:"itemdescr",	width:160,	label:"材料名称",	sortable:true,align:"left" ,},
				{name:"item2descr",	index:"item2descr",	width:85,	label:"材料类型2",	sortable:true,align:"left" ,},
				{name:"item3descr",	index:"item3descr",	width:85,	label:"材料类型3",	sortable:true,align:"left" ,},
				{name:"qty",	index:"qty",	width:60,	label:"数量",	sortable:true,align:"right" ,sum: true},
				{name:"uomdescr",	index:"uomdescr",	width:50,	label:"单位",	sortable:true,align:"left" ,},
				{name:"allweight",	index:"allweight",	width:65,	label:"总重量",	sortable:true,align:"right" ,sum: true},
				//{name:"allweight1",	index:"allweight1",	width:65,	label:"总重量1",	sortable:true,align:"right" ,sum: true, hidden:true},//该列数据是做统计的，将正确的sum赋值给allweight的sum
				{name:"remarks",	index:"remarks",	width:135,	label:"备注",	sortable:true,align:"left" ,},
				{name:"confirmdate",	index:"confirmdate",	width:85,	label:"审核日期",	sortable:true,align:"left" ,formatter:formatDate},
				{name:"senddate",	index:"senddate",	width:85,	label:"发货日期",	sortable:true,align:"left" ,formatter:formatDate}
		],
		gridComplete:function(){
			var needSum1 = $("#dataTable_cabinet").getCol("allweight",false,"sum");
			$("#dataTable_cabinet").footerData("set",{"allweight":myRound(needSum1,2)});
		}
	});
	
}); 
</script>
</head>
<body>
	<div class="body-box-list">
		<div id="content-list">
			<table id= "dataTable_cabinet"></table>
		</div>
	</div>
</body>	
</html>
