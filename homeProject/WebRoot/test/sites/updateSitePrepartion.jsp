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
	var url = "<%=path %>/client/sites/updateSitePrepartion?"+datas;
	ajaxGet(url);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>更新工地报备信息</h1>
		<h3>/client/sites/updateSitePrepartion</h3>
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
					<td>项目经理编号projectMan</td>
					<td><input type="text" name="projectMan" value="">String</td>
				</tr>
				<tr>
					<td>报备单号pk</td>
					<td><input type="text" name="pk" value="">String</td>
				</tr>
				<tr>
					<td>工地状态buildStatus</td>
					<td><input type="text" name="buildStatus" value="">String,1.正在施工;2.未报备停工;
					3.停工等基材;4.停工等主材;5.停工等集成;6.停工等软装;7.停工等放样;8.缺工人;9.款项未交;
					10.方案变更;11.节假日停工</td>
				</tr>
				<tr>
					<td>开始时间beginDate</td>
					<td><input type="text" name="beginDate" value="">Date</td>
				</tr>
				<tr>
					<td>结束时间endDate</td>
					<td><input type="text" name="endDate" value="">Date</td>
				</tr>
				<tr>
					<td>备注</td>
					<td><input type="text" name="remark" value="">String</td>
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
