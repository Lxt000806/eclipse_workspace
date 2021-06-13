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
	var url = "<%=path %>/client/customer/getCustomerListByCustRight?"+datas;
	ajaxGet(url);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>根据客户权限获取客户列表接口</h1>
		<h3>/client/customer/getCustomerListByCustRight</h3>
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
					<td>操作员编号czybh</td>
					<td><input type="text" name="czybh" value="00220">String</td>
				</tr>
				<tr>
					<td>楼盘地址address</td>
					<td><input type="text" name="address" value="">String</td>
				</tr>
				<tr>
					<td>干系人stakeholder</td>
					<td><input type="text" name="stakeholder" value="">String</td>
				</tr>
				<tr>
					<td>包含归档客户haveGd</td>
					<td><input type="text" name="haveGd" value="">String 0:不包含 1:包含</td>
				</tr>
				<tr>
					<td>包含独立销售saleIndependence</td>
					<td><input type="text" name="saleIndependence" value="">String 0:不包含 1:包含</td>
				</tr>
				<tr>
					<td>距离distance</td>
					<td><input type="text" name="distance" value="">Double </td>
				</tr>
				<tr>
					<td>经度lontitude</td>
					<td><input type="text" name="lontitude" value="">Double</td>
				</tr>
				<tr>
					<td>经度latitude</td>
					<td><input type="text" name="latitude" value="">Double</td>
				</tr>
				<tr>
					<td>施工项目prjItem</td>
					<td><input type="text" name="prjItem" value="">String</td>
				</tr>
				<tr>
					<td>干系人角色stakeholderRoll</td>
					<td><input type="text" name="stakeholderRoll" value="">String</td>
				</tr>
				<tr>
					<td>二级部门department2Descr</td>
					<td><input type="text" name="department2Descr" value="">String</td>
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