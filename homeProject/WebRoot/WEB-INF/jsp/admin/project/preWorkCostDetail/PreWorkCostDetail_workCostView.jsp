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
		url:"${ctx}/admin/preWorkCostDetail/goWorkCostDetailJqGrid",
		postData:{
			custCode:"${preWorkCostDetail.custCode}",
			workType2:"${preWorkCostDetail.workType2}",
			hasBaseCheckItemPlan: "${hasBaseCheckItemPlan}",
			workType1: "${pWorkCostDetail.workType1}"
		},
		height : $(document).height() - $("#content-list").offset().top - 70,
		rowNum : 10000000,
		colModel : [
    		{name:"address", index:"address", width:260, label:"楼盘", sortable:true, align:"left"},
    		{name:"wroktype1descr", index:"wroktype1descr", width:100, label:"工种分类1", sortable:true, align:"left"}, 
    		{name:"worktype2descr", index:"worktype2descr", width:100, label:"工种分类2", sortable:true, align:"left"}, 
			{name:"cfmamount", index:"cfmamount", width:70, label:"金额", sortable:true, align:"right", sum: true}
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
