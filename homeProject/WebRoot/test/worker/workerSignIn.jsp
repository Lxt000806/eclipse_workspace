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
	var url = "<%=path %>/client/worker/workerSignIn?"+datas;
	ajaxGet(url);
}

</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>工人签到接口</h1>
		<h3>/client/worker/workerSignIn</h3>
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
					<td>客户工人表PK custWkPk</td>
					<td><input type="text" name="custWkPk" value="">String</td>
				</tr>
				<tr>
					<td>客户编号custCode</td>
					<td><input type="text" name="custCode" value="">String</td>
				</tr>
				<tr>
					<td>工人编号workerCode</td>
					<td><input type="text" name="workerCode" value="">String</td>
				</tr>
				        		<tr>
					<td>施工项目2 prjItem2</td>
					<td><input type="text" name="prjItem2" value="">String</td>
				</tr>
				<tr>
					<td>签到地址address</td>
					<td><input type="text" name="address" value="">String</td>
				</tr>
				<tr>
					<td>经度longitude</td>
					<td><input type="text" name="longitude" value="">String</td>
				</tr>
				<tr>
					<td>纬度latitude</td>
					<td><input type="text" name="latitude" value="">String</td>
				</tr>
				<tr>
					<td>操作标志status</td>
					<td><input type="text" name="status" value="">String:1判断签到范围;2签到且阶段完成;Other签到且阶段未完成</td>
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
