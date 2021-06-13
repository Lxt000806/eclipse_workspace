<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
<!--
table {
	border: 0px double;
}
-->
</style>
<script type="text/javascript" src="${resourceRoot}/jquery/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${resourceRoot}/js/test.js"></script>
<script type="text/javascript">
function formSubmit(){
	var formData = new FormData($( "#uploadForm" )[0]);
	var datas = $("form").serialize();
	var url = "<%=path %>/client/prjProg/prjProgUploadNew";
	ajaxGet(url);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>上传图片接口New</h1>
		<h3>/client/prjProg/prjProgUploadNew</h3>
		<hr noshade size="3" width=80% />
		<%
			String respString = "";
			int port = request.getServerPort();
		%>
		<form id="uploadForm" method="post" enctype="multipart/form-data" action="<%=path %>/client/prjProg/prjProgUploadNew">
			<table border="1" align="center" bordercolordark="#EBEBEB"
				bordercolorlight="#ADAAAD">
				<tr>
					<td>图片类型type</td>
					<td colspan="2"><input type="text" name="type" value="0">Integer 0：工程进度图片 1：其他图片</td>
				</tr>
				<tr>
					<td>图片1</td>
					<td><input type="file" name="file1"></td>
					<td><input type="text" name="file1Descr" placeholder="图片描述"></td>
				</tr>
				<tr>
					<td>图片2</td>
					<td><input type="file" name="file2"></td>
					<td><input type="text" name="file2Descr" placeholder="图片描述"></td>
				</tr>
				<tr>
					<td>图片3</td>
					<td><input type="file" name="file3"></td>
					<td><input type="text" name="file3Descr" placeholder="图片描述"></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2"><input type="submit" value="提交"><input
					type="reset" value="重填"></td>
				</tr>
			</table>
		</form>
		-----------------------------<br/>
		请求端口：<%=port%><br/>
		-----------------------------<br/>
		返回结果：<%=respString%><div id="respString"></div>
	</center>
</body>
</html>
