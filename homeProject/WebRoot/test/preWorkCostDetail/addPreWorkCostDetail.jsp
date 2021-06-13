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
	var url = "<%=path %>/client/preWorkCostDetail/addPreWorkCostDetail?"+datas;
	ajaxGet(url);
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>新增工资预申请接口</h1>
		<h3>/client/preWorkCostDetail/addPreWorkCostDetail</h3>
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
					<td>工资类型salaryType</td>
					<td><input type="text" name="salaryType" value="">String</td>
				</tr>
				<tr>
					<td>工种类型1 workType1</td>
					<td><input type="text" name="workType1" value="">String</td>
				</tr>
				<tr>
					<td>工种类型2 workType2</td>
					<td><input type="text" name="workType2" value="">String</td>
				</tr>
				<tr>
					<td>工人编号workerCode</td>
					<td><input type="text" name="workerCode" value="">String</td>
				</tr>
				<tr>
					<td>工人姓名actName</td>
					<td><input type="text" name="actName" value="">String</td>
				</tr>
				<tr>
					<td>工人卡号cardId</td>
					<td><input type="text" name="cardId" value="">String</td>
				</tr>
				<tr>
					<td>申请金额appAmount</td>
					<td><input type="text" name="appAmount" value="">Integer</td>
				</tr>
				<tr>
					<td>申请人applyMan</td>
					<td><input type="text" name="applyMan" value="">String</td>
				</tr>
				<tr>
					<td>状态status</td>
					<td><input type="text" name="status" value="">String</td>
				</tr>
				<tr>
					<td>请款备注remarks</td>
					<td><input type="text" name="remarks" value="">String</td>
				</tr>
				<tr>
					<td>是否预扣isWithHold</td>
					<td><input type="text" name="isWithHold" value="">String</td>
				</tr>
				<tr>
					<td>预扣编号withHoldNo</td>
					<td><input type="text" name="withHoldNo" value="">Integer</td>
				</tr>
				<tr>
					<td>工人卡号2 cardId2</td>
					<td><input type="text" name="cardId2" value="">String</td>
				</tr>
				
				<tr>
					<td>来自消息新增fromInfoAdd</td>
					<td><input type="text" name="fromInfoAdd" value="">String 1:是 其他值或空:不是</td>
				</tr>
				<tr>
					<td>对应消息表PK infoPk</td>
					<td><input type="text" name="infoPk" value="">Integer </td>
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
