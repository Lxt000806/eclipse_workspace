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
	var url = "<%=path %>/client/prjJob/getDealPrjJobList?"+datas;
	ajaxGet(url);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>待处理项目任务列表接口</h1>
		<h3>/client/prjJob/getDealPrjJobList</h3>
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
					<td>处理人dealCzy</td>
					<td><input type="text" name="dealCzy" value="1">String</td>
				</tr>
				<tr>
					<td>楼盘address</td>
					<td><input type="text" name="address" value="">String</td>
				</tr>
				<tr>
					<td>状态status</td>
					<td><input type="text" name="status" value="">String</td>
				</tr>
				<tr>
					<td>每页条数pageSize</td>
					<td><input type="text" name="pageSize" value="10">int</td>
				</tr>
				<tr>
					<td>页数pageNo</td>
					<td><input type="text" name="pageNo" value="1">int</td>
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
