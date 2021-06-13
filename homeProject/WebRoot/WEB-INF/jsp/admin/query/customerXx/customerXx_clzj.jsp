<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>客户查询材料增減</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<div class="body-box-list">
	<div class="container-fluid" id="id_clzj">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_clzj_zczj" data-toggle="tab">主材增减</a></li>
	        <li class=""><a href="#tab_clzj_fwzj" data-toggle="tab">服务性产品增减</a></li>
	        <li class=""><a href="#tab_clzj_rzzj" data-toggle="tab">软装增减</a></li>
	        <li class=""><a href="#tab_clzj_jczj" data-toggle="tab">集成增减</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_clzj_zczj" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_clzj_zczj.jsp">
	         		<jsp:param name="title" value="主材"/>
	         		<jsp:param name="itemType1" value="ZC" />
	         		<jsp:param name="isService" value="0" />
	         	</jsp:include>
	        </div>  
	        <div id="tab_clzj_fwzj" class="tab-pane fade"> 
	         	<jsp:include page="tab_clzj_zczj.jsp">
	         		<jsp:param name="title" value="服务性产品"/>
	         		<jsp:param name="itemType1" value="ZC" />
	         		<jsp:param name="isService" value="1" />
	         	</jsp:include>
	        </div>
	        <div id="tab_clzj_rzzj" class="tab-pane fade"> 
	         	<jsp:include page="tab_clzj_zczj.jsp">
	         		<jsp:param name="title" value="软装"/>
	         		<jsp:param name="itemType1" value="RZ" />
	         		<jsp:param name="isService" value="0" />
	         	</jsp:include>
	        </div>
	        <div id="tab_clzj_jczj" class="tab-pane fade"> 
	         	<jsp:include page="tab_clzj_zczj.jsp">
	         		<jsp:param name="title" value="集成"/>
	         		<jsp:param name="itemType1" value="JC" />
	         		<jsp:param name="isService" value="0" />
	         	</jsp:include>
	        </div>  
	    </div>  
	</div>
</div>
</body>
</html>
