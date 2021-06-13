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
	<title>薪酬方案新增</title>
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
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum:50,
		colModel : [
			{name:"label", index:"label", width:150, label:"项目", sortable:true ,align:"center",},
			{name:"value", index:"value", width:200, label:"值", sortable:true ,align:"center",formatter:diyFormat},
		],
	}; 
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	function diyFormat(c, o, r){
		if (c == "null"){
			return "";
		}
		return c;
	}
	var detailObj = ${detailValue};
	if(detailObj){
		for(var key in detailObj){
			var map = {};
			if(key.split("_hid").length > 1 || key.split("_tip").length > 1){
				continue;
			}
			console.log(key);
			map["label"] = key;
			map["value"] = detailObj[key];
			$("#dataTable").addRowData(1,map,"last");
		}
	}
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
			<div class="container-fluid">  
				<div id="content-list">
					<div id="tab_detail" class="tab-pane fade in active">
						<div class="pageContent">
							<div class="panel panel-system" >
							</div>
							<div id="content-list">
								<table id="dataTable"></table>
							</div>
						</div>
					</div>
				</div>	
			</div>	
		</div>
	</body>	
</html>
