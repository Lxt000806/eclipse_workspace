<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改GuideTopicFolder</title>
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
		url:'${ctx}/admin/guideTopicFolder/doUpdate',
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
					time: 3000,
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
				"parentPk": {
				digits: true,
				required: true,
				maxlength: 10
				},
				"folderName": {
				validIllegalChar: true,
				required: true,
				maxlength: 30
				},
				"folderCode": {
				validIllegalChar: true,
				maxlength: 20
				},
				"path": {
				validIllegalChar: true,
				maxlength: 100
				},
				"adminCzy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"authType": {
				validIllegalChar: true,
				required: true,
				maxlength: 1
				},
				"isAuthWorker": {
				validIllegalChar: true,
				maxlength: 1
				},
				"authWorkerTypes": {
				validIllegalChar: true,
				maxlength: 100
				},
				"createDate": {
				required: true,
				maxlength: 23
				},
				"createCzy": {
				validIllegalChar: true,
				required: true,
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
				},
				"actionLog": {
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
				<input type="hidden" id="expired" name="expired" value="${guideTopicFolder.expired}" />
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label"><span class="required">*</span>pk</td>
							<td class="td-value">
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${guideTopicFolder.pk}" />
							</td>
							<td class="td-label"><span class="required">*</span>parentPk</td>
							<td class="td-value">
							<input type="text" id="parentPk" name="parentPk" style="width:160px;"  value="${guideTopicFolder.parentPk}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>folderName</td>
							<td class="td-value">
							<input type="text" id="folderName" name="folderName" style="width:160px;"  value="${guideTopicFolder.folderName}" />
							</td>
							<td class="td-label">folderCode</td>
							<td class="td-value">
							<input type="text" id="folderCode" name="folderCode" style="width:160px;"  value="${guideTopicFolder.folderCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">path</td>
							<td class="td-value">
							<input type="text" id="path" name="path" style="width:160px;"  value="${guideTopicFolder.path}" />
							</td>
							<td class="td-label">adminCzy</td>
							<td class="td-value">
							<input type="text" id="adminCzy" name="adminCzy" style="width:160px;"  value="${guideTopicFolder.adminCzy}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>authType</td>
							<td class="td-value">
							<input type="text" id="authType" name="authType" style="width:160px;"  value="${guideTopicFolder.authType}" />
							</td>
							<td class="td-label">isAuthWorker</td>
							<td class="td-value">
							<input type="text" id="isAuthWorker" name="isAuthWorker" style="width:160px;"  value="${guideTopicFolder.isAuthWorker}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">authWorkerTypes</td>
							<td class="td-value">
							<input type="text" id="authWorkerTypes" name="authWorkerTypes" style="width:160px;"  value="${guideTopicFolder.authWorkerTypes}" />
							</td>
							<td class="td-label"><span class="required">*</span>createDate</td>
							<td class="td-value">
							<input type="text" id="createDate" name="createDate" style="width:160px;"  value="${guideTopicFolder.createDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>createCzy</td>
							<td class="td-value">
							<input type="text" id="createCzy" name="createCzy" style="width:160px;"  value="${guideTopicFolder.createCzy}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${guideTopicFolder.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${guideTopicFolder.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${guideTopicFolder.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value" colspan="3">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${guideTopicFolder.actionLog}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${guideTopicFolder.expired}" onclick="checkExpired(this)" ${guideTopicFolder.expired=="T"?"checked":"" }>
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
