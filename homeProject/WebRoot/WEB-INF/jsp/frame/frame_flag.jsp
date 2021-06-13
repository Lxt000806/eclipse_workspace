<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0"  />
	<title> new document </title>
	<script type="text/javascript" src="${resourceRoot}/js/jquery.js"></script>
<script type="text/javascript">
	<!--
	var fs = parent.document.getElementsByTagName("frameset")[1];
	$(function(){
		$("#flag").toggle(function(){
			fs.setAttribute("cols", "0, 8, *");
			$("#flag").attr("src","${resourceRoot}/images/btn_show.gif");
		},function(){
			fs.setAttribute("cols", "190, 8, *");
			$("#flag").attr("src","${resourceRoot}/images/btn_hide.gif");
		});
	});
	//-->
</script>
</head>

<body style="position:relative; padding:0px; margin:0px;background:#eff5fe;" >
	<img id="flag" style="cursor:pointer;position:absolute;right:0px;top:250px;" src="${resourceRoot}/images/btn_hide.gif"></img>
</body>
</html>


