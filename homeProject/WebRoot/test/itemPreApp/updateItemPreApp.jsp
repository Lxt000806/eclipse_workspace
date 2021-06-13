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
	var datas = JSON.stringify($("form").serializeObject());
	var url = "<%=path %>/client/itemPreApp/updateItemPreApp";
	ajaxPost(url,datas);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>修改预领料接口</h1>
		<h3>/client/itemPreApp/updateItemPreApp</h3>
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
					<td>预领料编号no</td>
					<td><input type="text" name="no" value="">String</td>
				</tr>
				<tr>
					<td>app操作员appCzy</td>
					<td><input type="text" name="appCzy" value="">String</td>
				</tr>
				<tr>
					<td>备注remarks</td>
					<td><input type="text" name="remarks" value="">String</td>
				</tr>
				<tr>
					<td>图片名称photoNames</td>
					<td><input type="text" name="photoNames" value="">String(多个图片逗号隔开)</td>
				</tr>
				<tr>
					<td>预领料明细列表xmlData</td>
					<td><input type="text" name="xmlData" value="">String(json数据集)</td>
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
