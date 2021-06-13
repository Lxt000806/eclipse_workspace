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
	<title>采购订单管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	parent.$("#iframe_mobileView").attr("height","98%"); //消灭掉无用的滑动条
});

</script>
</head>
	<body>
		<div class="body-box-form">
				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
			</div>
			<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>手机号码</label>
								<input type="text" id="mobile1" name="mobile1" style="width:160px;" value="${resrCust.mobile1 }"/>
							</li>
							<li>
								<label>手机号码2</label>
								<input type="text" id="mobile2" name="mobile2" style="width:160px;" value="${resrCust.mobile2 }"/>
							</li>
							<li hidden="true">
								<label>czybh</label>
								<input type="text" id="czybh" name="czybh" style="width:160px;" value="${resrCust.czybh }"/>
							</li>
						</ul>	
				</form>
				</div>
				</div>
		</div>
	</body>	
</html>
