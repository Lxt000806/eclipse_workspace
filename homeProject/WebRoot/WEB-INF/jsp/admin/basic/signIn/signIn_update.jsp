<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改SignIn</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function save(){
	if(!$("#dataForm").valid()) {return false;}//表单校验
	if($("#infoBoxDiv").css("display")!='none'){return false;}
	
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/signIn/doUpdate',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
				$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
}

//校验函数
$(function() {
	$("#dataForm").validate({
		rules: {
				"pk": {
				digits: true,
				required: true,
				maxlength: 10
				},
				"custCode": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"signCzy": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"crtDate": {
				required: true,
				maxlength: 23
				},
				"longitude": {
				number: true,
				required: true,
				maxlength: 12
				},
				"latitude": {
				number: true,
				required: true,
				maxlength: 12
				},
				"address": {
				validIllegalChar: true,
				maxlength: 400
				}
		}
	});
});

</script>

</head>
    
<body>
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
		<div class="panelBar">
			<ul>
				<li >
					<a href="javascript:void(0)" class="a1" id="saveBut" onclick="save()">
					   <span>保存</span>
					</a>	
				</li>
				<li id="closeBut" onclick="closeWin(false)">
					<a href="javascript:void(0)" class="a2">
						<span>关闭</span>
					</a>
				</li>
				<li class="line"></li>
			</ul>
			<div class="clear_float"> </div>
		</div>
		<div class="infoBox" id="infoBoxDiv"></div>
		<div class="edit-form">
			<form action="" method="post" id="dataForm">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label"><span class="required">*</span>pk</td>
							<td class="td-value">
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${signIn.pk}" />
							</td>
							<td class="td-label"><span class="required">*</span>custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${signIn.custCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>signCzy</td>
							<td class="td-value">
							<input type="text" id="signCzy" name="signCzy" style="width:160px;"  value="${signIn.signCzy}" />
							</td>
							<td class="td-label"><span class="required">*</span>crtDate</td>
							<td class="td-value">
							<input type="text" id="crtDate" name="crtDate" style="width:160px;"  value="${signIn.crtDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>longitude</td>
							<td class="td-value">
							<input type="text" id="longitude" name="longitude" style="width:160px;"  value="${signIn.longitude}" />
							</td>
							<td class="td-label"><span class="required">*</span>latitude</td>
							<td class="td-value">
							<input type="text" id="latitude" name="latitude" style="width:160px;"  value="${signIn.latitude}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">address</td>
							<td class="td-value" colspan="3">
							<input type="text" id="address" name="address" style="width:160px;"  value="${signIn.address}" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
