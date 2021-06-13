<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
	String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<style>
	.ui-jqgrid .ui-jqgrid-view{
		margin: 0px;
	}
</style>

<script type="text/javascript" defer>
$(function() {
	var gridOption = {
		url:"${ctx}/admin/workCostDetail/goDeJqGrid",
		postData:{
			custCode:"${preWorkCostDetail.custCode}",workType2:"${preWorkCostDetail.workType2}"
		},
		height : $(document).height() - $("#content-list").offset().top - 70,
		rowNum : 10000000,
		colModel : [
    		{name:"worktype12", index:"worktype12", width:80, label:"工种分类12", sortable:true, align:"left", hidden:true}, 
			{name:"baseitemdescr", index:"baseitemdescr", width:200, label:"结算项目名称", sortable:true, align:"left"}, 
			{name:"fixareadescr", index:"fixareadescr", width:140, label:"区域", sortable:true, align:"left"}, 
			{name:"qty", index:"qty", width:60, label:"数量", sortable:true, align:"right"}, 
			{name:"uom", index:"uom", width:60, label:"单位code", sortable:true, align:"left", hidden:true}, 
			{name:"uomdescr", index:"uomdescr", width:60, label:"单位", sortable:true, align:"left"}, 
			{name:"offerpri", index:"offerpri", width:80, label:"人工单价", sortable:true, align:"right"}, 
			{name:"totalprice", index:"totalprice", width:80, label:"总价", sortable:true, align:"right", sum:true}, 
		],
		gridComplete:function(){
			var totalPrice_sum = $("#dataTable").getCol("totalprice",false,"sum");
			$("#dataTable").footerData("set",{"totalprice":myRound(totalPrice_sum,2)});
		},
	};

	Global.JqGrid.initJqGrid("dataTable",gridOption);

});
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false);">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
</body>	

</html>
