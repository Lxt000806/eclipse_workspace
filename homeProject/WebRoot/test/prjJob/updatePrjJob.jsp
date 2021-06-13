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
	var url = "<%=path %>/client/prjJob/updatePrjJob?"+datas;
	ajaxGet(url);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>修改项目任务接口</h1>
		<h3>/client/prjJob/updatePrjJob</h3>
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
					<td>项目任务编号no</td>
					<td><input type="text" name="no" value="">String</td>
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
					<td>任务类型jobType</td>
					<td><input type="text" name="jobType" value="">String</td>
				</tr>
				<tr>
					<td>app操作员appCzy</td>
					<td><input type="text" name="appCzy" value="">String</td>
				</tr>
				<tr>
					<td>处理人员dealCzy</td>
					<td><input type="text" name="dealCzy" value="">String</td>
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
					<td>项目任务图片photoString</td>
					<td><input type="text" name="photoString" value="">String(多个图片逗号隔开)</td>
				</tr>
				<tr>
					<td>衣柜品牌warBrand</td>
					<td><input type="text" name="warBrand" value="">String</td>
				</tr>
				<tr>
					<td>橱柜品牌cupBrand</td>
					<td><input type="text" name="cupBrand" value="">String</td>
				</tr>
				<tr>
					<td>是否需求控制isNeedReq</td>
					<td><input type="text" name="isNeedReq" value="">String</td>
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
