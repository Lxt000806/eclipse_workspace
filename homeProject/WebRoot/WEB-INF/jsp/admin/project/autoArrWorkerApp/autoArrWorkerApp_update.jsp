<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改AutoArrWorkerApp</title>
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
		url:'${ctx}/admin/autoArrWorkerApp/doUpdate',
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
				"arrPk": {
				digits: true,
				maxlength: 10
				},
				"appPk": {
				digits: true,
				maxlength: 10
				},
				"custCode": {
				validIllegalChar: true,
				maxlength: 20
				},
				"workType12": {
				validIllegalChar: true,
				maxlength: 10
				},
				"appComeDate": {
				maxlength: 23
				},
				"custType": {
				validIllegalChar: true,
				maxlength: 10
				},
				"projectMan": {
				validIllegalChar: true,
				maxlength: 5
				},
				"prjLevel": {
				validIllegalChar: true,
				maxlength: 10
				},
				"isSupvr": {
				validIllegalChar: true,
				maxlength: 1
				},
				"spcBuilder": {
				validIllegalChar: true,
				maxlength: 10
				},
				"spcBldExpired": {
				validIllegalChar: true,
				maxlength: 1
				},
				"regionCode": {
				validIllegalChar: true,
				maxlength: 10
				},
				"regionCode2": {
				validIllegalChar: true,
				maxlength: 10
				},
				"regIsSpcWorker": {
				validIllegalChar: true,
				maxlength: 1
				},
				"department1": {
				validIllegalChar: true,
				maxlength: 10
				},
				"area": {
				digits: true,
				maxlength: 10
				},
				"workerCode": {
				validIllegalChar: true,
				maxlength: 10
				},
				"comeDate": {
				maxlength: 23
				},
				"lastUpdate": {
				required: true,
				maxlength: 23
				},
				"lastUpdatedBy": {
				validIllegalChar: true,
				required: true,
				maxlength: 10
				},
				"expired": {
				validIllegalChar: true,
				required: true,
				maxlength: 1
				},
				"actionLog": {
				validIllegalChar: true,
				required: true,
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
				<input type="hidden" id="expired" name="expired" value="${autoArrWorkerApp.expired}" />
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label"><span class="required">*</span>pk</td>
							<td class="td-value">
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${autoArrWorkerApp.pk}" />
							</td>
							<td class="td-label">arrPk</td>
							<td class="td-value">
							<input type="text" id="arrPk" name="arrPk" style="width:160px;"  value="${autoArrWorkerApp.arrPk}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">appPk</td>
							<td class="td-value">
							<input type="text" id="appPk" name="appPk" style="width:160px;"  value="${autoArrWorkerApp.appPk}" />
							</td>
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${autoArrWorkerApp.custCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">workType12</td>
							<td class="td-value">
							<input type="text" id="workType12" name="workType12" style="width:160px;"  value="${autoArrWorkerApp.workType12}" />
							</td>
							<td class="td-label">appComeDate</td>
							<td class="td-value">
							<input type="text" id="appComeDate" name="appComeDate" style="width:160px;"  value="${autoArrWorkerApp.appComeDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">custType</td>
							<td class="td-value">
							<input type="text" id="custType" name="custType" style="width:160px;"  value="${autoArrWorkerApp.custType}" />
							</td>
							<td class="td-label">projectMan</td>
							<td class="td-value">
							<input type="text" id="projectMan" name="projectMan" style="width:160px;"  value="${autoArrWorkerApp.projectMan}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">prjLevel</td>
							<td class="td-value">
							<input type="text" id="prjLevel" name="prjLevel" style="width:160px;"  value="${autoArrWorkerApp.prjLevel}" />
							</td>
							<td class="td-label">isSupvr</td>
							<td class="td-value">
							<input type="text" id="isSupvr" name="isSupvr" style="width:160px;"  value="${autoArrWorkerApp.isSupvr}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">spcBuilder</td>
							<td class="td-value">
							<input type="text" id="spcBuilder" name="spcBuilder" style="width:160px;"  value="${autoArrWorkerApp.spcBuilder}" />
							</td>
							<td class="td-label">spcBldExpired</td>
							<td class="td-value">
							<input type="text" id="spcBldExpired" name="spcBldExpired" style="width:160px;"  value="${autoArrWorkerApp.spcBldExpired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">regionCode</td>
							<td class="td-value">
							<input type="text" id="regionCode" name="regionCode" style="width:160px;"  value="${autoArrWorkerApp.regionCode}" />
							</td>
							<td class="td-label">regionCode2</td>
							<td class="td-value">
							<input type="text" id="regionCode2" name="regionCode2" style="width:160px;"  value="${autoArrWorkerApp.regionCode2}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">regIsSpcWorker</td>
							<td class="td-value">
							<input type="text" id="regIsSpcWorker" name="regIsSpcWorker" style="width:160px;"  value="${autoArrWorkerApp.regIsSpcWorker}" />
							</td>
							<td class="td-label">department1</td>
							<td class="td-value">
							<input type="text" id="department1" name="department1" style="width:160px;"  value="${autoArrWorkerApp.department1}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">area</td>
							<td class="td-value">
							<input type="text" id="area" name="area" style="width:160px;"  value="${autoArrWorkerApp.area}" />
							</td>
							<td class="td-label">workerCode</td>
							<td class="td-value">
							<input type="text" id="workerCode" name="workerCode" style="width:160px;"  value="${autoArrWorkerApp.workerCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">comeDate</td>
							<td class="td-value">
							<input type="text" id="comeDate" name="comeDate" style="width:160px;"  value="${autoArrWorkerApp.comeDate}" />
							</td>
							<td class="td-label"><span class="required">*</span>lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${autoArrWorkerApp.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${autoArrWorkerApp.lastUpdatedBy}" />
							</td>
							<td class="td-label"><span class="required">*</span>expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${autoArrWorkerApp.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>actionLog</td>
							<td class="td-value" colspan="3">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${autoArrWorkerApp.actionLog}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${autoArrWorkerApp.expired}" onclick="checkExpired(this)" ${autoArrWorkerApp.expired=="T"?"checked":"" }>
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
