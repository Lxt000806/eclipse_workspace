<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加ConfItemTime</title>
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
		url:'${ctx}/admin/confItemTime/doSave',
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
				"descr": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"prjItem": {
				validIllegalChar: true,
				required: true,
				maxlength: 10
				},
				"dayType": {
				validIllegalChar: true,
				required: true,
				maxlength: 1
				},
				"addDay": {
				digits: true,
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
				"actionLog": {
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
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label"><span class="required">*</span>descr</td>
							<td class="td-value">
							<input type="text" id="descr" name="descr" style="width:160px;"  value="${confItemTime.descr}" />
							</td>
							<td class="td-label"><span class="required">*</span>prjItem</td>
							<td class="td-value">
							<input type="text" id="prjItem" name="prjItem" style="width:160px;"  value="${confItemTime.prjItem}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>dayType</td>
							<td class="td-value">
							<input type="text" id="dayType" name="dayType" style="width:160px;"  value="${confItemTime.dayType}" />
							</td>
							<td class="td-label"><span class="required">*</span>addDay</td>
							<td class="td-value">
							<input type="text" id="addDay" name="addDay" style="width:160px;"  value="${confItemTime.addDay}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${confItemTime.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${confItemTime.lastUpdatedBy}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${confItemTime.actionLog}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${confItemTime.expired}" />
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
