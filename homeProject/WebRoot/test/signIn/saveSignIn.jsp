<%@ page contentType="text/html; charset=UTF-8"%>
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
	var url = "<%=path %>/client/signIn/saveSignIn?"+datas;
	ajaxGet(url);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>地图签到保存接口</h1>
		<h3>/client/signIn/saveSignIn</h3>
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
					<td>客户编号custCode</td>
					<td><input type="text" name="custCode" value="">String</td>
				</tr>
				<tr>
					<td>签到操作员signCzy</td>
					<td><input type="text" name="signCzy" value="">String</td>
				</tr>
				<tr>
					<td>经度longitude</td>
					<td><input type="text" name="longitude" value="">Double</td>
				</tr>
				<tr>
					<td>纬度latitude</td>
					<td><input type="text" name="latitude" value="">Double</td>
				</tr>
				<tr>
					<td>地址address</td>
					<td><input type="text" name="address" value="">String</td>
				</tr>
				<tr>
					<td>签到类型2 signInType2</td>
					<td><input type="text" name="signInType2" value="">String</td>
				</tr>
				<tr>
					<td>图片名称 photoString</td>
					<td><input type="text" name="photoString" value="">String : 多张图片之间用逗号隔开</td>
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
