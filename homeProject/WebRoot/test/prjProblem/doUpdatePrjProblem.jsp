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
	var url = "<%=path %>/client/prjProblem/doUpdatePrjProblem?"+datas;
	ajaxGet(url);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>更新工地问题接口</h1>
		<h3>/client/prjProblem/doUpdatePrjProblem</h3>
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
					<td>操作标记opSign</td>
					<td><input type="text" name="opSign" value="">String,0更新状态,1编辑修改</td>
				</tr>
				<tr>
					<td>工地问题单号no</td>
					<td><input type="text" name="no" value="">String</td>
				</tr>
				<tr>
					<td>工地问题状态status</td>
					<td><input type="text" name="status" value="">String,xtdm.PRJPROMSTATUS</td>
				</tr>
				<tr>
					<td>责任部门promDeptCode</td>
					<td><input type="text" name="promDeptCode" value="">String,表tPrjPromDept</td>
				</tr>
				<tr>
					<td>责任分类promTypeCode</td>
					<td><input type="text" name="promTypeCode" value="">String,表tPrjPromType</td>
				</tr>
				<tr>
					<td>责任性质promPropCode</td>
					<td><input type="text" name="promPropCode" value="">String,xtdm.PRJPROMPROP</td>
				</tr>
				<tr>
					<td>问题描述remarks</td>
					<td><input type="text" name="remarks" value="">String</td>
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
