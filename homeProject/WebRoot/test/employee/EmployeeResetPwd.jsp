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
	var url = "<%=path %>/client/employee/resetEmployeePwd?"+datas;
	ajaxGet(url);
}

</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>用户忘记密码接口</h1>
		<h3>/client/employee/resetEmployeePwd</h3>
		<hr noshade size="3" width=80% />
		<%
			String respString = "";
			int port = request.getServerPort();
		%>
		<form>
			<table border="1" align="center" bordercolordark="#EBEBEB"
				bordercolorlight="#ADAAAD">
				<tr>
					<td>短信请求类型smsType</td>
					<td><input type="text" name="smsType" value="2">String</td>
				</tr>
				<tr>
					<td>帐号portalAccount</td>
					<td><input type="text" name="portalAccount" value="">String</td>
				</tr>
				<tr>
					<td>新密码portalPwd</td>
					<td><input type="text" name="portalPwd" value="">String</td>
				</tr>
				<tr>
					<td>确认密码confirmPwd</td>
					<td><input type="text" name="confirmPwd" value="">String</td>
				</tr>
					<tr>
					<td>验证码smsPwd</td>
					<td><input type="text" name="smsPwd" value="">String</td>
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
