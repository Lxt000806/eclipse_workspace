<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加ItemApp</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
var parentWin=window.opener;
$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
});

function save(){
	if(!$("#dataForm").valid()) {return false;}//表单校验
	if($("#infoBoxDiv").css("display")!='none'){return false;}
	
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/itemApp/doSave',
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
						if(parentWin != null)
				        	parentWin.goto_query();
	    				closeWin();
				    }
				});
	    	}else{
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
				"type": {
				validIllegalChar: true,
				maxlength: 1
				},
				"itemType1": {
				validIllegalChar: true,
				required: true,
				maxlength: 10
				},
				"custCode": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
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
				"sendCzy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"sendDate": {
				maxlength: 23
				},
				"sendType": {
				validIllegalChar: true,
				maxlength: 1
				},
				"supplCode": {
				validIllegalChar: true,
				maxlength: 20
				},
				"puno": {
				validIllegalChar: true,
				maxlength: 20
				},
				"whcode": {
				validIllegalChar: true,
				required: true,
				maxlength: 15
				},
				"remarks": {
				validIllegalChar: true,
				maxlength: 1000
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
				"delivType": {
				validIllegalChar: true,
				maxlength: 1
				},
				"projectMan": {
				validIllegalChar: true,
				maxlength: 30
				},
				"phone": {
				validIllegalChar: true,
				maxlength: 30
				},
				"oldNo": {
				validIllegalChar: true,
				maxlength: 20
				},
				"otherCost": {
				number: true,
				maxlength: 19
				},
				"otherCostAdj": {
				number: true,
				maxlength: 19
				},
				"isService": {
				digits: true,
				maxlength: 10
				},
				"itemType2": {
				validIllegalChar: true,
				maxlength: 10
				},
				"isCheckOut": {
				validIllegalChar: true,
				maxlength: 1
				},
				"whcheckOutNo": {
				validIllegalChar: true,
				maxlength: 20
				},
				"checkSeq": {
				digits: true,
				maxlength: 10
				},
				"prjCheckType": {
				validIllegalChar: true,
				maxlength: 1
				},
				"projectOtherCost": {
				number: true,
				maxlength: 19
				},
				"isSetItem": {
				validIllegalChar: true,
				maxlength: 1
				},
				"amount": {
				number: true,
				maxlength: 19
				},
				"projectAmount": {
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
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label">类型</td>
							<td class="td-value">
							<input type="text" id="type" name="type" style="width:160px;"  value="${itemApp.type}" />
							</td>
							<td class="td-label"><span class="required">*</span>材料类型1</td>
							<td class="td-value">
							<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemApp.itemType1}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>客户编号</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${itemApp.custCode}" />
							</td>
							<td class="td-label">Status</td>
							<td class="td-value">
							<input type="text" id="status" name="status" style="width:160px;"  value="${itemApp.status}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">申请人员</td>
							<td class="td-value">
							<input type="text" id="appCzy" name="appCzy" style="width:160px;"  value="${itemApp.appCzy}" />
							</td>
							<td class="td-label">申请日期</td>
							<td class="td-value">
							<input type="text" id="date" name="date" style="width:160px;"  value="${itemApp.date}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">confirmCzy</td>
							<td class="td-value">
							<input type="text" id="confirmCzy" name="confirmCzy" style="width:160px;"  value="${itemApp.confirmCzy}" />
							</td>
							<td class="td-label">confirmDate</td>
							<td class="td-value">
							<input type="text" id="confirmDate" name="confirmDate" style="width:160px;"  value="${itemApp.confirmDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">发货人员</td>
							<td class="td-value">
							<input type="text" id="sendCzy" name="sendCzy" style="width:160px;"  value="${itemApp.sendCzy}" />
							</td>
							<td class="td-label">发货日期</td>
							<td class="td-value">
							<input type="text" id="sendDate" name="sendDate" style="width:160px;"  value="${itemApp.sendDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">发货类型</td>
							<td class="td-value">
							<input type="text" id="sendType" name="sendType" style="width:160px;"  value="${itemApp.sendType}" />
							</td>
							<td class="td-label">供应商代码</td>
							<td class="td-value">
							<input type="text" id="supplCode" name="supplCode" style="width:160px;"  value="${itemApp.supplCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">采购单号</td>
							<td class="td-value">
							<input type="text" id="puno" name="puno" style="width:160px;"  value="${itemApp.puno}" />
							</td>
							<td class="td-label"><span class="required">*</span>仓库编号</td>
							<td class="td-value">
							<input type="text" id="whcode" name="whcode" style="width:160px;"  value="${itemApp.whcode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">Remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${itemApp.remarks}" />
							</td>
							<td class="td-label"><span class="required">*</span>LastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemApp.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">LastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemApp.lastUpdatedBy}" />
							</td>
							<td class="td-label">Expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemApp.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">ActionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemApp.actionLog}" />
							</td>
							<td class="td-label">配送方式</td>
							<td class="td-value">
							<input type="text" id="delivType" name="delivType" style="width:160px;"  value="${itemApp.delivType}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">项目经理</td>
							<td class="td-value">
							<input type="text" id="projectMan" name="projectMan" style="width:160px;"  value="${itemApp.projectMan}" />
							</td>
							<td class="td-label">电话号码</td>
							<td class="td-value">
							<input type="text" id="phone" name="phone" style="width:160px;"  value="${itemApp.phone}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">原批次号</td>
							<td class="td-value">
							<input type="text" id="oldNo" name="oldNo" style="width:160px;"  value="${itemApp.oldNo}" />
							</td>
							<td class="td-label">其它费用（付供应商）</td>
							<td class="td-value">
							<input type="text" id="otherCost" name="otherCost" style="width:160px;"  value="${itemApp.otherCost}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">其它费用调整（付供应商）</td>
							<td class="td-value">
							<input type="text" id="otherCostAdj" name="otherCostAdj" style="width:160px;"  value="${itemApp.otherCostAdj}" />
							</td>
							<td class="td-label">是否服务性产品</td>
							<td class="td-value">
							<input type="text" id="isService" name="isService" style="width:160px;"  value="${itemApp.isService}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">材料类型2</td>
							<td class="td-value">
							<input type="text" id="itemType2" name="itemType2" style="width:160px;"  value="${itemApp.itemType2}" />
							</td>
							<td class="td-label">是否记账</td>
							<td class="td-value">
							<input type="text" id="isCheckOut" name="isCheckOut" style="width:160px;"  value="${itemApp.isCheckOut}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">仓库发货记账单号</td>
							<td class="td-value">
							<input type="text" id="whcheckOutNo" name="whcheckOutNo" style="width:160px;"  value="${itemApp.whcheckOutNo}" />
							</td>
							<td class="td-label">结算顺序号</td>
							<td class="td-value">
							<input type="text" id="checkSeq" name="checkSeq" style="width:160px;"  value="${itemApp.checkSeq}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">项目经理结算类型</td>
							<td class="td-value">
							<input type="text" id="prjCheckType" name="prjCheckType" style="width:160px;"  value="${itemApp.prjCheckType}" />
							</td>
							<td class="td-label">项目经理其它费用</td>
							<td class="td-value">
							<input type="text" id="projectOtherCost" name="projectOtherCost" style="width:160px;"  value="${itemApp.projectOtherCost}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">是否套餐材料</td>
							<td class="td-value">
							<input type="text" id="isSetItem" name="isSetItem" style="width:160px;"  value="${itemApp.isSetItem}" />
							</td>
							<td class="td-label">材料总价</td>
							<td class="td-value">
							<input type="text" id="amount" name="amount" style="width:160px;"  value="${itemApp.amount}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">项目经理材料总价</td>
							<td class="td-value" colspan="3">
							<input type="text" id="projectAmount" name="projectAmount" style="width:160px;"  value="${itemApp.projectAmount}" />
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
