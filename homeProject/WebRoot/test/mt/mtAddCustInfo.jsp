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
<script src="${resourceRoot}/jqGrid/5.0.0/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
function formSubmit(){
	var datas = $("form").jsonForm();
	// var url = "<%=path %>/client/api/mtAddCustInfo?"+datas;
	// ajaxGet(url);
	var encryptData = ""
	
	$.ajax({
		url: "<%=path %>/client/api/mtEncryptTest",
		type:"post",
		contentType: 'application/json',
	   	data: "{PostData:\""+JSON.stringify(datas).replace(/\"/g, "\\\"")+"\"}",
		dataType:"json",
		cache:false,
		async:false,
		error:function(obj){			    		
			art.dialog({
				content:"访问出错,请重试",
				time:3000,
				beforeunload: function () {}
			});
		},
		success: function(data){
			if(data.success){
				encryptData = data.postData;
			}
		}
	});
	
	if(!encryptData || encryptData == ""){
		return;
	}
	var result = "";
	$.ajax({
		url: "<%=path %>/client/api/mtAddCustInfo",
		type:"post",
	   	data: "PostData="+encodeURI(encryptData),
		cache:false,
		async:false,
		error:function(obj){			    		
			art.dialog({
				content:"访问出错,请重试",
				time:3000,
				beforeunload: function () {}
			});
		},
		success: function(data){
			result = data;
		}
	});
	
	if(!result || result == ""){
		return;
	}
	
	$.ajax({
		url: "<%=path %>/client/api/mtDecryptTest",
		type:"post",
		contentType: 'application/json',
	   	data: "{PostData:\""+result+"\"}",
		dataType:"json",
		cache:false,
		async:false,
		error:function(obj){			    		
			art.dialog({
				content:"访问出错,请重试",
				time:3000,
				beforeunload: function () {}
			});
		},
		success: function(data){
			if(data.success){
				$("#respString").html(data.postData);
			}
		}
	});
}
</script>
</head>
<body bgcolor="#ffffff">
	<center>
		<a href="../interfaces.jsp"><div style="color: red;">返回所有接口</div></a>
		<h1>新增麦田客户接口</h1>
		<h3>/client/api/mtAddCustInfo</h3>
		<hr noshade size="3" width=80% />
		<%
			String respString = "";
			int port = request.getServerPort();
		%>
		<form>
			<table border="1" align="center" bordercolordark="#EBEBEB"
				bordercolorlight="#ADAAAD">
				<tr>
					<td>麦田大区编码</td>
					<td><input type="text" name="regionCode" value="FZ000079">String</td>
				</tr>
				<tr>
					<td>麦田大区名称（中文）</td>
					<td><input type="text" name="regionDescr" value="北部大区">String</td>
				</tr>
				<tr>
					<td>麦田区域编码</td>
					<td><input type="text" name="region2Code" value="FZ000022">String</td>
				</tr>
				<tr>
					<td>麦田区域名称（中文）</td>
					<td><input type="text" name="region2" value="大儒世家区（周丽丽）">String</td>
				</tr>
				<tr>
					<td>麦田店名（中文）</td>
					<td><input type="text" name="shopName" value="东方名城店A组（春春测试）">String</td>
				</tr>
				<tr>
					<td>麦田经纪人</td>
					<td><input type="text" name="manage" value="胡伟">String</td>
				</tr>
				<tr>
					<td>麦田经纪人电话</td>
					<td><input type="text" name="managePhone" value="15806043687">String</td>
				</tr>
				<tr>
					<td>麦田客户编号</td>
					<td><input type="text" name="custCodeMT" value="MY3000018">String</td>
				</tr>
				<tr>
					<td>客户楼盘</td>
					<td><input type="text" name="address" value="测试楼盘地址">String</td>
				</tr>
				<tr>
					<td>客户楼盘面积</td>
					<td><input type="text" name="area" value="126">number</td>
				</tr>
				<tr>
					<td>是否装修（中文）</td>
					<td><input type="text" name="isFixtures" value="是">String：是/否</td>
				</tr>
				<tr>
					<td>客户姓名</td>
					<td><input type="text" name="custDescr" value="测试客户名称">String</td>
				</tr>
				<tr>
					<td>客户电话</td>
					<td><input type="text" name="custPhone" value="18888888888">String</td>
				</tr>
				<tr>
					<td>户型（中文）</td>
					<td><input type="text" name="layout" value="平层">String：平层/复式/别墅</td>
				</tr>
				<tr>
					<td>客户性别（中文）</td>
					<td><input type="text" name="gender" value="男">String：男/女</td>
				</tr>
				<tr>
					<td>备注</td>
					<td><input type="text" name="remark" value="">String：最大长度400，可以为空</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="button" value="提交" onclick="formSubmit()">
						<input type="reset" value="重填">
					</td>
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
