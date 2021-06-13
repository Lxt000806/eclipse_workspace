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
	<div class="container-fluid" id="id_detail">
	    <ul class="nav nav-tabs" >
	        <li class="active"><a href="#supplCheckConfirm_tab_lld_zcll" data-toggle="tab">主材领料</a></li>
	        <li class=""><a href="#supplCheckConfirm_tab_lld_JZll" data-toggle="tab">基础领料</a></li>
	        <li class=""><a href="#supplCheckConfirm_tab_lld_JCll" data-toggle="tab">集成领料</a></li>
	    </ul>
	     <div class="tab-content">  
	        <div id="supplCheckConfirm_tab_lld_zcll" class="tab-pane fade in active"> 
	         	<jsp:include page="supplCheckConfirm_tab_lld_zcll.jsp">
	         		<jsp:param name="title" value="主材"/>
	         		<jsp:param name="itemType1" value="ZC" />
	         	</jsp:include>
	        </div>   
	        <div id="supplCheckConfirm_tab_lld_JZll" class="tab-pane fade"> 
	         	<jsp:include page="supplCheckConfirm_tab_lld_zcll.jsp">
	         		<jsp:param name="title" value="基础"/>
	         		<jsp:param name="itemType1" value="JZ" />
	         	</jsp:include>
	        </div>
	        <div id="supplCheckConfirm_tab_lld_JCll" class="tab-pane fade"> 
	         	<jsp:include page="supplCheckConfirm_tab_lld_zcll.jsp">
	         		<jsp:param name="title" value="集成"/>
	         		<jsp:param name="itemType1" value="JC" />
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
