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
	var url = "<%=path %>/client/worker/addWorkerCost?"+datas;
	ajaxGet(url);
}

</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>工人申请工资接口</h1>
		<h3>/client/worker/addWorkerCost</h3>
		<hr noshade size="3" width=80% />
		<%
			String respString = "";
			int port = request.getServerPort();
		%>
		<form>
			<table border="1" align="center" bordercolordark="#EBEBEB"
				bordercolorlight="#ADAAAD">
				<!--         data.portalType = 1;
        data.custCode = custCode;
        data.salaryType = salaryType;
        data.workType2 = workType2;
        data.workerCode = localStorage.czyNumber;
        data.actName = actName;
        data.cardID = cardID;
        data.status = status;
        data.applyMan = applyMan;
        data.isWorkApp = isWorkApp;
        data.workAppAmount = workAppAmount;
        data.custWkPk = custWkPk;
        data.isAutoConfirm = isAutoConfirm; -->
				<tr>
					<td>接入Portal类型portalType</td>
					<td><input type="text" name="portalType" value="1">String</td>
				</tr>
        		<tr>
					<td>工人编号workerCode</td>
					<td><input type="text" name="workerCode" value="">String</td>
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
					<td>工种分类2 workType2</td>
					<td><input type="text" name="workType2" value="">String</td>
				</tr>
        		<tr>
					<td>工人姓名actName</td>
					<td><input type="text" name="actName" value="">String</td>
				</tr>
				<tr>
					<td>工人银行卡账号cardID</td>
					<td><input type="text" name="cardID" value="">String</td>
				</tr>
				<tr>
					<td>状态status</td>
					<td><input type="text" name="status" value="">String:1草稿;2提交;3助理已审核;4经理已审核;5财务待审核;6财务已审核;7款项已发放;8财务取消;9取消</td>
				</tr>
								<tr>
					<td>申请人(项目经理编号)applyMan</td>
					<td><input type="text" name="applyMan" value="">String</td>
				</tr>
								<tr>
					<td>是否工人申请isWorkApp</td>
					<td><input type="text" name="isWorkApp" value="1">String:1是;0否</td>
				</tr>
        		<tr>
					<td>申请金额workAppAmount</td>
					<td><input type="text" name="workAppAmount" value="">Double</td>
				</tr>
				<tr>
					<td>客户工人表PK custWkPk</td>
					<td><input type="text" name="custWkPk" value="">String</td>
				</tr>
				<tr>
					<td>是否自动确认isAutoConfirm</td>
					<td><input type="text" name="isAutoConfirm" value="0">String:1是;0否</td>
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
