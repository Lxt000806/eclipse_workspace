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
    <title>客户联系方式</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
	<script type="text/javascript">
	
	</script>
</head>
<body>
<div class="body-box-form">
	<div class="content-form">
		<div class="panel panel-system">
		    <div class="panel-body" >
		      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
		      </div>
		    </div>
		</div>
		<div class="panel panel-info">  
	        <div class="panel-body">
	            <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
	            	<ul class="ul-form">
	            		<li>
	            			<label>手机号码1</label>
							<input type="text" id="mobile1" name="mobile1" style="width:160px;" value="${customer.mobile1}" readonly="readonly"/>
	            		</li>
	            		<li>
	            			<label>手机号码2</label>
	            			<input type="text" id="mobile2" name="mobile2" style="width:160px;" value="${customer.mobile2}" readonly="readonly"/>
						</li>
	            	</ul>
	            </form>
	         </div>
	     </div>
	</div>
</div>
</body>
</html>

