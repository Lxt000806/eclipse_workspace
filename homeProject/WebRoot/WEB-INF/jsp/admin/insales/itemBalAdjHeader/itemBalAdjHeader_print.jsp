<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
	 <title>仓库调整-打印</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
	function print(){
	   	var reportName = "itemBalAdjHeader.jasper";
	   	Global.Print.showPrint(reportName, {
	   		No:"${itemBalAdjHeader.no }",
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
	}
</script>
</head>
  
<body>
<div class="body-box-form">
	<div class="content-form">
	<!--panelBar-->
		<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
				<button type="button" class="btn btn-system " onclick="print()">
						<span>打印</span>
						</button>
				<button type="button" class="btn btn-system " onclick="closeWin(false)">
					<a href="javascript:void(0)" class="a2">
						<span>关闭</span>
						</a>
						</button>
		</div>
		</div>
		</div>
	</div>
</div>
</body>
</html>
