<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>录音</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<style>
			.audioClass {
				margin: 0 auto;
			}
			.audioDiv {
				width: 100%;
			}
		</style>
	</head>
	<body>
		<div class="audioDiv">
			<audio class="audioClass" src="${path}" controls="controls"></audio>
		</div>
	</body>
</html>
