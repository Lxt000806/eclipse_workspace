<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<title>用户未登录</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache">
<meta content="width=device-width; initial-scale=1.0; minimum-scale=1.0; maximum-scale=2.0" name ="viewport">
<link href="${resourceRoot}/wap/css/requestH.css" rel="stylesheet">
<link href="${resourceRoot}/wap/css/site.css" rel="stylesheet">
</head>

<body>
	${headDiv}
	<div class="wrap">
		<div class="detail2">
			<div class="p1">
				<img src="${resourceRoot}/wap/images/biao2.png" />
			</div>
			<div class="p2">
				您好，请先登录后进行操作！
			</div>
			<div class="p3">
				<div style="width: 140px" class="bt1" onclick="location.href='${ctx}/wap/login'">登录</div>
				<div class="cc"></div>
			</div>
		</div> 
	</div>
	${buttomDiv}
</body>
</html>
