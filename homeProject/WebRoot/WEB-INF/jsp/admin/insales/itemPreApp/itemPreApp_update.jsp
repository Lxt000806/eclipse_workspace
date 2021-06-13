<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改ItemPreApp</title>
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
		url:'${ctx}/admin/itemPreApp/doUpdate',
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
				"custCode": {
				validIllegalChar: true,
				maxlength: 20
				},
				"isSetItem": {
				validIllegalChar: true,
				maxlength: 1
				},
				"status": {
				validIllegalChar: true,
				maxlength: 10
				},
				"appCzy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"date": {
				maxlength: 23
				},
				"confirmCzy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"confirmDate": {
				maxlength: 23
				},
				"remarks": {
				validIllegalChar: true,
				maxlength: 1000
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
				},
				"actionLog": {
				validIllegalChar: true,
				maxlength: 10
				},
				"itemType1": {
				validIllegalChar: true,
				maxlength: 10
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
				<input type="hidden" id="expired" name="expired" value="${itemPreApp.expired}" />
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label"><span class="required">*</span>no</td>
							<td class="td-value">
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemPreApp.no}" readonly="readonly"/>
							</td>
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${itemPreApp.custCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">isSetItem</td>
							<td class="td-value">
							<input type="text" id="isSetItem" name="isSetItem" style="width:160px;"  value="${itemPreApp.isSetItem}" />
							</td>
							<td class="td-label">status</td>
							<td class="td-value">
							<input type="text" id="status" name="status" style="width:160px;"  value="${itemPreApp.status}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">appCzy</td>
							<td class="td-value">
							<input type="text" id="appCzy" name="appCzy" style="width:160px;"  value="${itemPreApp.appCzy}" />
							</td>
							<td class="td-label">date</td>
							<td class="td-value">
							<input type="text" id="date" name="date" style="width:160px;"  value="${itemPreApp.date}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">confirmCzy</td>
							<td class="td-value">
							<input type="text" id="confirmCzy" name="confirmCzy" style="width:160px;"  value="${itemPreApp.confirmCzy}" />
							</td>
							<td class="td-label">confirmDate</td>
							<td class="td-value">
							<input type="text" id="confirmDate" name="confirmDate" style="width:160px;"  value="${itemPreApp.confirmDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${itemPreApp.remarks}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemPreApp.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemPreApp.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemPreApp.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemPreApp.actionLog}" />
							</td>
							<td class="td-label">itemType1</td>
							<td class="td-value">
							<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemPreApp.itemType1}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${itemPreApp.expired}" onclick="checkExpired(this)" ${itemPreApp.expired=="T"?"checked":"" }>
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
