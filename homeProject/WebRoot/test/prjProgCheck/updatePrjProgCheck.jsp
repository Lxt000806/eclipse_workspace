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
	var url = "<%=path %>/client/prjProgCheck/updatePrjProgCheck?"+datas;
	ajaxGet(url);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>编辑工地巡检接口</h1>
		<h3>/client/prjProgCheck/updatePrjProgCheck</h3>
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
					<td>工地巡检编号no</td>
					<td><input type="text" name="no" value="">String</td>
				</tr>
				<tr>
					<td>项目编号prjItem</td>
					<td><input type="text" name="prjItem" value="">String</td>
				</tr>
				<tr>
					<td>备注remarks</td>
					<td><input type="text" name="remarks" value="">String</td>
				</tr>
				<tr>
					<td>图片photoString</td>
					<td><input type="text" name="photoString" value="">String,多个图片逗号隔</td>
				</tr>
				<tr>
					<td>是否整改isModify</td>
					<td><input type="text" name="isModify" value="">String</td>
				</tr>
				<tr>
					<td>整改时限modifyTime</td>
					<td><input type="text" name="modifyTime" value="">String</td>
				</tr>
				<tr>
					<td>工地状态buildStatus</td>
					<td><input type="text" name="buildStatus" value="">String</td>
				</tr>
				<tr>
					<td>安全问题safeProm</td>
					<td><input type="text" name="safeProm" value="">String</td>
				</tr>
				<tr>
					<td>形象问题visualProm</td>
					<td><input type="text" name="visualProm" value="">String</td>
				</tr>
				<tr>
					<td>工艺问题artProm</td>
					<td><input type="text" name="artProm" value="">String</td>
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
