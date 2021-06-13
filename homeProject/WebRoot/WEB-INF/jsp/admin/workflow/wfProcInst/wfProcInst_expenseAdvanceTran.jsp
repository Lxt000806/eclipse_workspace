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
	<title>薪酬计算方案适用人员</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-120,
		rowNum:50,
		colModel : [
			{name:"ChgAmount", index:"ChgAmount", width:80, label:"变动金额", sortable:true ,align:"right",},
			{name:"AftAmount", index:"AftAmount", width:80, label:"余额", sortable:true ,align:"right",},
			{name:"AdvanceDate", index:"AdvanceDate", width:130, label:"变动时间", sortable:true ,align:"left",formatter:formatTime},
			{name:"ActName", index:"ActName", width:80, label:"户名", sortable:true ,align:"left",},
			{name:"Bank", index:"Bank", width:180, label:"开户行", sortable:true ,align:"left",},
			{name:"CardId", index:"CardId", width:180, label:"银行卡号", sortable:true ,align:"left",},
			{name:"WfProcInstNo", index:"WfProcInstNo", width:80, label:"流程单号", sortable:true ,align:"left",},
			{name:"descr", index:"descr", width:80, label:"单据类型", sortable:true ,align:"left",},
			{name:"Remarks", index:"Remarks", width:180, label:"备注", sortable:true ,align:"left",},
		],
	}; 
	
	$.extend(gridOption,{
		url:"${ctx}/admin/wfProcInst/getExpenseAdvanceTran",
		postData:{number:$("#number").val()}
	});
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);
});
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" hidden="true">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="text" id="number" name="number" value="${employee.number }"/>
						<ul class="ul-form">
						</ul>
					</form>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<table id="dataTablePager"></table>
			</div>
		</div>
	</body>	
</html>
