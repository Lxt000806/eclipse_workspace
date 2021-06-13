<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
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
<script type="text/javascript" src="${resourceRoot}/js/jquery.js"></script>
<script type="text/javascript" src="${resourceRoot}/js/test.js"></script>
<script type="text/javascript">
function formSubmit(){
	var datas = $("form").serialize();
	var url = "<%=path %>/client/version/updateVersion?"+datas;
	$("#versionNo").val('');
	$("#compatible").val('');
	ajaxGet(url);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>更新版本接口</h1>
		<h3>/client/version/updateVersion</h3>
		<hr noshade size="3" width=80% />
		<%
			String respString = "";
			int port = request.getServerPort();
		%>
		<form>
			<table border="1" align="center" bordercolordark="#EBEBEB"
				bordercolorlight="#ADAAAD">
				<tr>
					<td>接入Portal类型portalType</td>
					<td><input type="text" name="portalType" value="1">String</td>
				</tr>
				<tr>
					<td>应用名name</td>
					<td>
					<house:DataSelect id="name" className="Version" keyColumn="name" valueColumn="descr"></house:DataSelect>
					</td>
				</tr>
				<tr>
					<td>版本号versionNo</td>
					<td><input type="text" id="versionNo" name="versionNo" value=""></td>
				</tr>
				<tr>
					<td>兼容版本compatible</td>
					<td><input type="text" id="compatible" name="compatible" value=""></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="button" name="tijiao" value="提交" onclick="formSubmit()"><input
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
