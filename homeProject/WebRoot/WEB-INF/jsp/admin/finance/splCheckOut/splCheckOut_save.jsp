<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加SplCheckOut</title>
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
		url:'${ctx}/admin/splCheckOut/doSave',
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
				"splCode": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"date": {
				required: true,
				maxlength: 23
				},
				"payType": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"beginDate": {
				required: true,
				maxlength: 23
				},
				"endDate": {
				required: true,
				maxlength: 23
				},
				"payAmount": {
				number: true,
				required: true,
				maxlength: 19
				},
				"remarks": {
				validIllegalChar: true,
				maxlength: 200
				},
				"lastUpdate": {
				required: true,
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
				"remark": {
				validIllegalChar: true,
				maxlength: 200
				},
				"otherCost": {
				number: true,
				maxlength: 19
				},
				"status": {
				validIllegalChar: true,
				maxlength: 10
				},
				"confirmCzy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"confirmDate": {
				maxlength: 23
				},
				"documentNo": {
				validIllegalChar: true,
				maxlength: 20
				},
				"payCzy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"payDate": {
				maxlength: 23
				},
				"paidAmount": {
				number: true,
				maxlength: 19
				},
				"nowAmount": {
				number: true,
				maxlength: 19
				},
				"preAmount": {
				number: true,
				maxlength: 19
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
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label"><span class="required">*</span>splCode</td>
							<td class="td-value">
							<input type="text" id="splCode" name="splCode" style="width:160px;"  value="${splCheckOut.splCode}" />
							</td>
							<td class="td-label"><span class="required">*</span>date</td>
							<td class="td-value">
							<input type="text" id="date" name="date" style="width:160px;"  value="${splCheckOut.date}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>payType</td>
							<td class="td-value">
							<input type="text" id="payType" name="payType" style="width:160px;"  value="${splCheckOut.payType}" />
							</td>
							<td class="td-label"><span class="required">*</span>beginDate</td>
							<td class="td-value">
							<input type="text" id="beginDate" name="beginDate" style="width:160px;"  value="${splCheckOut.beginDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>endDate</td>
							<td class="td-value">
							<input type="text" id="endDate" name="endDate" style="width:160px;"  value="${splCheckOut.endDate}" />
							</td>
							<td class="td-label"><span class="required">*</span>payAmount</td>
							<td class="td-value">
							<input type="text" id="payAmount" name="payAmount" style="width:160px;"  value="${splCheckOut.payAmount}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${splCheckOut.remarks}" />
							</td>
							<td class="td-label"><span class="required">*</span>lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${splCheckOut.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${splCheckOut.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${splCheckOut.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${splCheckOut.actionLog}" />
							</td>
							<td class="td-label">remark</td>
							<td class="td-value">
							<input type="text" id="remark" name="remark" style="width:160px;"  value="${splCheckOut.remark}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">otherCost</td>
							<td class="td-value">
							<input type="text" id="otherCost" name="otherCost" style="width:160px;"  value="${splCheckOut.otherCost}" />
							</td>
							<td class="td-label">status</td>
							<td class="td-value">
							<input type="text" id="status" name="status" style="width:160px;"  value="${splCheckOut.status}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">confirmCzy</td>
							<td class="td-value">
							<input type="text" id="confirmCzy" name="confirmCzy" style="width:160px;"  value="${splCheckOut.confirmCzy}" />
							</td>
							<td class="td-label">confirmDate</td>
							<td class="td-value">
							<input type="text" id="confirmDate" name="confirmDate" style="width:160px;"  value="${splCheckOut.confirmDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">documentNo</td>
							<td class="td-value">
							<input type="text" id="documentNo" name="documentNo" style="width:160px;"  value="${splCheckOut.documentNo}" />
							</td>
							<td class="td-label">payCzy</td>
							<td class="td-value">
							<input type="text" id="payCzy" name="payCzy" style="width:160px;"  value="${splCheckOut.payCzy}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">payDate</td>
							<td class="td-value">
							<input type="text" id="payDate" name="payDate" style="width:160px;"  value="${splCheckOut.payDate}" />
							</td>
							<td class="td-label">paidAmount</td>
							<td class="td-value">
							<input type="text" id="paidAmount" name="paidAmount" style="width:160px;"  value="${splCheckOut.paidAmount}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">nowAmount</td>
							<td class="td-value">
							<input type="text" id="nowAmount" name="nowAmount" style="width:160px;"  value="${splCheckOut.nowAmount}" />
							</td>
							<td class="td-label">preAmount</td>
							<td class="td-value">
							<input type="text" id="preAmount" name="preAmount" style="width:160px;"  value="${splCheckOut.preAmount}" />
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
