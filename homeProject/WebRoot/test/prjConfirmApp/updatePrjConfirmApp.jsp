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
	var url = "<%=path %>/client/prjConfirmApp/updatePrjConfirmApp?"+datas;
	ajaxGet(url);
}

</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>更新验收申请信息</h1>
		<h3>/client/prjConfirmApp/updatePrjConfirmApp</h3>
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
					<td>楼盘address</td>
					<td><input type="text" name="address" value="">String</td>
				</tr>
        		<tr>
					<td>状态status</td>
					<td><input type="text" name="status" value="">String :0 草稿;1 申请;2 已安排;3 取消</td>
				</tr>
        		<tr>
					<td>pk</td>
					<td><input type="text" name="pk" value="">String</td>
				</tr>
        		<tr>
					<td>操作标志opSign</td>
					<td><input type="text" name="opSign" value="">String :1 编辑;2 更新状态</td>
				</tr>
        		<tr>
					<td>客户编号custCode</td>
					<td><input type="text" name="custCode" value="">String</td>
				</tr>
        		<tr>
					<td>施工项目prjItem</td>
					<td><input type="text" name="prjItem" value="">String</td>
				</tr>
        		<tr>
					<td>备注remarks</td>
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
