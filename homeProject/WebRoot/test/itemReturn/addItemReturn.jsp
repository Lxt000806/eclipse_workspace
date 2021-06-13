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
	var url = "<%=path %>/client/itemReturn/addItemReturn";
	ajaxPost(url,datas);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>新增退货申请接口</h1>
		<h3>/client/itemReturn/addItemReturn</h3>
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
					<td>材料类型1 itemType1</td>
					<td><input type="text" name="itemType1" value="">String</td>
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
					<td>状态status</td>
					<td><input type="text" name="status" value="">String 1：草稿 2：提交</td>
				</tr>
				<tr>
					<td>退货申请明细列表xmlData</td>
					<td><input type="text" name="xmlData" value="[{'expired':'F','actionLog':'ADD','remarks':'备注'}]">String(json数据集)</td>
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
