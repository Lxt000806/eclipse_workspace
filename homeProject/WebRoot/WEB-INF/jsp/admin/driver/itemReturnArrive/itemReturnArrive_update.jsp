<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改ItemReturnArrive</title>
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
		url:'${ctx}/admin/itemReturnArrive/doUpdate',
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
				"no": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"returnNo": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"driverCode": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"address": {
				validIllegalChar: true,
				maxlength: 400
				},
				"driverRemark": {
				validIllegalChar: true,
				maxlength: 200
				},
				"arriveDate": {
				required: true,
				maxlength: 23
				},
				"actionLog": {
				validIllegalChar: true,
				maxlength: 10
				},
				"lastUpdate": {
				maxlength: 23
				},
				"lastUpdatedBy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"expired": {
				validIllegalChar: true,
				maxlength: 1
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
					<a href="#" class="a1" id="saveBut" onclick="save()">
					   <span>保存</span>
					</a>	
				</li>
				<li id="closeBut" onclick="closeWin()">
					<a href="#" class="a2">
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
				<input type="hidden" id="expired" name="expired" value="${itemReturnArrive.expired}" />
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label"><span class="required">*</span>no</td>
							<td class="td-value">
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemReturnArrive.no}" readonly="readonly"/>
							</td>
							<td class="td-label"><span class="required">*</span>returnNo</td>
							<td class="td-value">
							<input type="text" id="returnNo" name="returnNo" style="width:160px;"  value="${itemReturnArrive.returnNo}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>driverCode</td>
							<td class="td-value">
							<input type="text" id="driverCode" name="driverCode" style="width:160px;"  value="${itemReturnArrive.driverCode}" />
							</td>
							<td class="td-label">address</td>
							<td class="td-value">
							<input type="text" id="address" name="address" style="width:160px;"  value="${itemReturnArrive.address}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">driverRemark</td>
							<td class="td-value">
							<input type="text" id="driverRemark" name="driverRemark" style="width:160px;"  value="${itemReturnArrive.driverRemark}" />
							</td>
							<td class="td-label"><span class="required">*</span>arriveDate</td>
							<td class="td-value">
							<input type="text" id="arriveDate" name="arriveDate" style="width:160px;"  value="${itemReturnArrive.arriveDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemReturnArrive.actionLog}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemReturnArrive.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemReturnArrive.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemReturnArrive.expired}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${itemReturnArrive.expired}" onclick="checkExpired(this)" ${itemReturnArrive.expired=="T"?"checked":"" }>
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
