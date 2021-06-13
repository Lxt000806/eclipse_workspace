<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>修改PrjProg</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	

<script type="text/javascript"> 
$(function(){
          
		
});
</script>
</head>
<body >
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
			<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
			</div>
		<div class="infoBox" id="infoBoxDiv"></div>
							 <div class="panel-body">
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
		        <li class="active"><a href="#tab_totalDelay"  data-toggle="tab">拖期汇总</a></li>
		        <li class=""><a href="#tab_delayDetail"  data-toggle="tab">拖期明细</a></li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_totalDelay" class="tab-pane fade in active"> 
		         	<jsp:include page="tab_totalDelay.jsp"></jsp:include>
		        </div> 
		        <div id="tab_delayDetail"  class="tab-pane fade " > 
		         	<jsp:include page="tab_delayDetail.jsp"></jsp:include>
		        </div> 
		    </div>  
		</div>
		</div>
	</div>
</div>
</body>
</html>
    
