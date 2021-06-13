<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>印章图片</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
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
        	<div style="position:absolute;left:70px;" >
				<div id="showPictureDiv" style="width:500px;margin-top:10px"> 
					<img id="showPicture" src="${url}" alt="没有相关图片" width="350px" height="350px">  
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>

