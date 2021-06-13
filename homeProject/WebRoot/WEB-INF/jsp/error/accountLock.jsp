<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<title>帐号被锁</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache">
<meta content="width=device-width; initial-scale=1.0; minimum-scale=1.0; maximum-scale=2.0" name ="viewport">
<link href="${resourceRoot}/wap/css/requestH.css" rel="stylesheet">
<link href="${resourceRoot}/wap/css/site.css" rel="stylesheet">
</head>

<body>
	<div class="wrap">
		<div class="detail2">
			<div class="p1">
				<img src="${resourceRoot}/wap/images/biao1.png" />
			</div>
			<div class="p2">
			 	您的帐号目前处于<c:if test="${status == null}">锁定</c:if><c:if test="${status == 2}">暂时关闭</c:if><c:if test="${status == 3}">永久关闭</c:if>状态，无法登录，请联系管理员！
			</div>
		</div>
	</div>  
</body>
</html>
