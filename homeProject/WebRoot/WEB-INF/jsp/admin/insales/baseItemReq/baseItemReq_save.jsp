<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加BaseItemReq</title>
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
		url:'${ctx}/admin/baseItemReq/doSave',
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
				"custCode": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"fixAreaPk": {
				digits: true,
				required: true,
				maxlength: 10
				},
				"baseItemCode": {
				validIllegalChar: true,
				maxlength: 20
				},
				"qty": {
				number: true,
				maxlength: 19
				},
				"cost": {
				number: true,
				maxlength: 19
				},
				"unitPrice": {
				number: true,
				maxlength: 19
				},
				"lineAmount": {
				number: true,
				maxlength: 19
				},
				"remark": {
				validIllegalChar: true,
				maxlength: 400
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
				"material": {
				number: true,
				maxlength: 19
				},
				"dispSeq": {
				digits: true,
				maxlength: 10
				},
				"isOutSet": {
				validIllegalChar: true,
				maxlength: 1
				},
				"prjCtrlType": {
				validIllegalChar: true,
				maxlength: 1
				},
				"offerCtrl": {
				number: true,
				maxlength: 19
				},
				"materialCtrl": {
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
							<td class="td-label"><span class="required">*</span>custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${baseItemReq.custCode}" />
							</td>
							<td class="td-label"><span class="required">*</span>fixAreaPk</td>
							<td class="td-value">
							<input type="text" id="fixAreaPk" name="fixAreaPk" style="width:160px;"  value="${baseItemReq.fixAreaPk}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">baseItemCode</td>
							<td class="td-value">
							<input type="text" id="baseItemCode" name="baseItemCode" style="width:160px;"  value="${baseItemReq.baseItemCode}" />
							</td>
							<td class="td-label">qty</td>
							<td class="td-value">
							<input type="text" id="qty" name="qty" style="width:160px;"  value="${baseItemReq.qty}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">cost</td>
							<td class="td-value">
							<input type="text" id="cost" name="cost" style="width:160px;"  value="${baseItemReq.cost}" />
							</td>
							<td class="td-label">unitPrice</td>
							<td class="td-value">
							<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"  value="${baseItemReq.unitPrice}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lineAmount</td>
							<td class="td-value">
							<input type="text" id="lineAmount" name="lineAmount" style="width:160px;"  value="${baseItemReq.lineAmount}" />
							</td>
							<td class="td-label">remark</td>
							<td class="td-value">
							<input type="text" id="remark" name="remark" style="width:160px;"  value="${baseItemReq.remark}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${baseItemReq.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${baseItemReq.lastUpdatedBy}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${baseItemReq.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${baseItemReq.actionLog}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">material</td>
							<td class="td-value">
							<input type="text" id="material" name="material" style="width:160px;"  value="${baseItemReq.material}" />
							</td>
							<td class="td-label">dispSeq</td>
							<td class="td-value">
							<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"  value="${baseItemReq.dispSeq}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">isOutSet</td>
							<td class="td-value">
							<input type="text" id="isOutSet" name="isOutSet" style="width:160px;"  value="${baseItemReq.isOutSet}" />
							</td>
							<td class="td-label">prjCtrlType</td>
							<td class="td-value">
							<input type="text" id="prjCtrlType" name="prjCtrlType" style="width:160px;"  value="${baseItemReq.prjCtrlType}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">offerCtrl</td>
							<td class="td-value">
							<input type="text" id="offerCtrl" name="offerCtrl" style="width:160px;"  value="${baseItemReq.offerCtrl}" />
							</td>
							<td class="td-label">materialCtrl</td>
							<td class="td-value">
							<input type="text" id="materialCtrl" name="materialCtrl" style="width:160px;"  value="${baseItemReq.materialCtrl}" />
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
