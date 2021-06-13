<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改Supplier</title>
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
		url:'${ctx}/admin/supplier/doUpdate',
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
				"code": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"descr": {
				validIllegalChar: true,
				required: true,
				maxlength: 60
				},
				"address": {
				validIllegalChar: true,
				maxlength: 200
				},
				"contact": {
				validIllegalChar: true,
				maxlength: 50
				},
				"phone1": {
				validIllegalChar: true,
				maxlength: 20
				},
				"phone2": {
				validIllegalChar: true,
				maxlength: 20
				},
				"fax1": {
				validIllegalChar: true,
				maxlength: 20
				},
				"fax2": {
				validIllegalChar: true,
				maxlength: 20
				},
				"mobile1": {
				validIllegalChar: true,
				maxlength: 20
				},
				"mobile2": {
				validIllegalChar: true,
				maxlength: 20
				},
				"email1": {
				validIllegalChar: true,
				maxlength: 100
				},
				"email2": {
				validIllegalChar: true,
				maxlength: 100
				},
				"isSpecDay": {
				validIllegalChar: true,
				required: true,
				maxlength: 1
				},
				"specDay": {
				digits: true,
				required: true,
				maxlength: 10
				},
				"billCycle": {
				digits: true,
				required: true,
				maxlength: 10
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
    
<body onunload="closeWin()">
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
							<td class="td-label"><span class="required">*</span>Code</td>
							<td class="td-value">
							<input type="text" id="code" name="code" style="width:160px;"  value="${supplier.code}" readonly="readonly"/>
							</td>
							<td class="td-label"><span class="required">*</span>名称</td>
							<td class="td-value">
							<input type="text" id="descr" name="descr" style="width:160px;"  value="${supplier.descr}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">地址</td>
							<td class="td-value">
							<input type="text" id="address" name="address" style="width:160px;"  value="${supplier.address}" />
							</td>
							<td class="td-label">联系人</td>
							<td class="td-value">
							<input type="text" id="contact" name="contact" style="width:160px;"  value="${supplier.contact}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">电话1</td>
							<td class="td-value">
							<input type="text" id="phone1" name="phone1" style="width:160px;"  value="${supplier.phone1}" />
							</td>
							<td class="td-label">Phone2</td>
							<td class="td-value">
							<input type="text" id="phone2" name="phone2" style="width:160px;"  value="${supplier.phone2}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">传真1</td>
							<td class="td-value">
							<input type="text" id="fax1" name="fax1" style="width:160px;"  value="${supplier.fax1}" />
							</td>
							<td class="td-label">Fax2</td>
							<td class="td-value">
							<input type="text" id="fax2" name="fax2" style="width:160px;"  value="${supplier.fax2}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">手机</td>
							<td class="td-value">
							<input type="text" id="mobile1" name="mobile1" style="width:160px;"  value="${supplier.mobile1}" />
							</td>
							<td class="td-label">Mobile2</td>
							<td class="td-value">
							<input type="text" id="mobile2" name="mobile2" style="width:160px;"  value="${supplier.mobile2}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">邮件地址1</td>
							<td class="td-value">
							<input type="text" id="email1" name="email1" style="width:160px;"  value="${supplier.email1}" />
							</td>
							<td class="td-label">Email2</td>
							<td class="td-value">
							<input type="text" id="email2" name="email2" style="width:160px;"  value="${supplier.email2}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>是否指定结算日期</td>
							<td class="td-value">
							<input type="text" id="isSpecDay" name="isSpecDay" style="width:160px;"  value="${supplier.isSpecDay}" />
							</td>
							<td class="td-label"><span class="required">*</span>指定结算日期</td>
							<td class="td-value">
							<input type="text" id="specDay" name="specDay" style="width:160px;"  value="${supplier.specDay}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>结算周期</td>
							<td class="td-value">
							<input type="text" id="billCycle" name="billCycle" style="width:160px;"  value="${supplier.billCycle}" />
							</td>
							<td class="td-label"><span class="required">*</span>LastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${supplier.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>LastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${supplier.lastUpdatedBy}" />
							</td>
							<td class="td-label"><span class="required">*</span>Expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${supplier.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>ActionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${supplier.actionLog}" />
							</td>
							<td class="td-label">材料类型1</td>
							<td class="td-value">
							<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${supplier.itemType1}" />
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
