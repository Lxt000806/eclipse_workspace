<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>客户查询详细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">

</script>
<style type="text/css">
.ui-jqgrid{
width: 2000px;
}
</style>
</head>
<body onunload="closeWin()">
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	   </div>
	</div>
	<!--标签页内容 -->
	<div class="container-fluid" id="id_clxq">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_clxq_zcxq" data-toggle="tab">主材需求</a></li>
	        <li class=""><a href="#tab_clxq_fwxq" data-toggle="tab">服务性产品需求</a></li>
	        <li class=""><a href="#tab_clxq_rzxq" data-toggle="tab">软装需求</a></li>
	        <li class=""><a href="#tab_clxq_jcxq" data-toggle="tab">集成需求</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_clxq_zcxq" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_clxq_zcxq.jsp">
	         		<jsp:param name="itemType1" value="ZC" />
	         		<jsp:param name="isService" value="0" />
	         	</jsp:include>
	        </div>  
	        <div id="tab_clxq_fwxq" class="tab-pane fade"> 
	         	<jsp:include page="tab_clxq_zcxq.jsp">
	         		<jsp:param name="itemType1" value="ZC" />
	         		<jsp:param name="isService" value="1" />
	         	</jsp:include>
	        </div>
	        <div id="tab_clxq_rzxq" class="tab-pane fade"> 
	         	<jsp:include page="tab_clxq_zcxq.jsp">
	         		<jsp:param name="itemType1" value="RZ" />
	         		<jsp:param name="isService" value="0" />
	         	</jsp:include>
	        </div>  
	        <div id="tab_clxq_jcxq" class="tab-pane fade"> 
	         	<jsp:include page="tab_clxq_zcxq.jsp">
	         		<jsp:param name="itemType1" value="JC" />
	         		<jsp:param name="isService" value="0" />
	         	</jsp:include>
	        </div>
	    </div>  
	</div>
</div>
<form action="" method="post" id="page_form_excel" >
	<input type="hidden" name="jsonString" value="" />
</form>
<script type="text/javascript">
</script>
</body>
</html>
