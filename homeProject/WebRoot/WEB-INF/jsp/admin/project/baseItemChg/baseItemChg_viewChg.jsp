<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看材料需求</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>
<body onunload="closeWin()">
<div class="body-box-form">
	<!--标签页内容 -->
	<div class="container-fluid" id="id_detail">
	    <ul class="nav nav-tabs" >
	        <li class="active"><a href="#tab_clxq" data-toggle="tab" onclick="">材料需求</a></li>
	        <li class=""><a href="#tab_clzj" data-toggle="tab" onclick="goClzj()">材料增减</a></li>
	    </ul>
	    <div class="tab-content">
	        <div id="tab_clxq" class="tab-pane fade in active"> 
	        	<jsp:include page="../../query/customerXx/tab_clxq.jsp"></jsp:include>
	        </div>
	        <div id="tab_clzj" class="tab-pane fade">  
	        </div>
    	</div>
    </div>
</div>
<form action="" method="post" id="page_form_excel" >
	<input type="hidden" name="jsonString" value="" />
</form>
<script>
function goClzj(){
	$("#tab_clzj").load("${ctx}/admin/baseItemChg/goClzj?code=${customer.code}");
}
</script> 
</body>
</html>
