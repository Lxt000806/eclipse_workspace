<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>修改用户密码</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/jsencrypt/jsencrypt.min.js"></script>

<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;

	$("#saveBut").attr("disabled", "disabled");
	//验证
	//var datas = $("#dataForm").serialize();
	var rsaEncrypt = new JSEncrypt();
	rsaEncrypt.setPublicKey("${publicKey}");
	var datas = {
		oldPassword: $("#oldPassword").val(),
		newPassword1: $("#newPassword1").val(),
		newPassword2: $("#newPassword2").val()
	};
	datas.oldPassword = rsaEncrypt.encrypt(datas.oldPassword);
	datas.newPassword1 = rsaEncrypt.encrypt(datas.newPassword1);
	datas.newPassword2 = rsaEncrypt.encrypt(datas.newPassword2);
	
	$.ajax({
		url:'${ctx}/admin/czybm/doChangePassword',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			$("#saveBut").removeAttr("disabled");
	        alert('修改密码连接出错~');
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: '修改密码成功！',
					time: 1000,
					beforeunload: function () {
						closeWin();
				    }
				});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		$("#saveBut").removeAttr("disabled");
	    		art.dialog({
					content: obj.msg
				});
	    	}
	    	
	    }
	 });
}

String.prototype.trim = function() {
    return this.replace(/(^\s+)|(\s+$)/g, "");
}

function close(){
	art.dialog({
		content:"请修改密码",
	})
	return;
}

//校验函数
$(function() {
	if("${isPasswordExpired}" == "true"){
		 var titleCloseEle = parent.$("div[aria-describedby=dialog_changePassword]").children(".ui-dialog-titlebar");
		$(titleCloseEle[0]).children("button").remove();
		$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
									+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
		$(titleCloseEle[0]).children("button").on("click", close);  
	}
	$("#saveBut").attr("style","display:block");
	$(".ui-button-icon-primary ui-icon ui-icon-closethick").hide();

	  $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
              
                validating : 'glyphicon glyphicon-refresh'
            },
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });
});
</script>

</head>
    
<body>
<div style="width:100%;margin-top:10px;" align="center">
	<div style="width:600px;">
		<form id="dataForm" name="dataForm" class="form-search">
			<house:token></house:token>
			<ul class="ul-form">
				<div class="validate-group row" >
					<div class="col-sm-12" >
						<li class="form-validate">
						<label><span class="required">*</span>旧密码</label>
						<input type="password" id="oldPassword" name="oldPassword" data-bv-notempty data-bv-notempty-message="旧密码不能为空" maxlength="12" style="width:250px;" />
						</li>
					</div>
				</div>
				<div class="validate-group row" >
					<div class="col-sm-12" >
						<li class="form-validate">
						<label><span class="required">*</span>新密码</label>
							<input type="password" id="newPassword1" name="newPassword1" data-bv-notempty data-bv-notempty-message="新密码不能为空" maxlength="12" style="width:250px;" />
						</li>
					</div>
				</div>
				<div class="validate-group row" >
					<div class="col-sm-12" >
						<li class="form-validate">
						<label><span class="required">*</span>确认新密码</label>
							<input type="password" id="newPassword2" name="newPassword2"  data-bv-notempty data-bv-notempty-message="确认新密码不能为空" maxlength="12" style="width:250px;" />
						</li>
					</div>
				</div>
				<div class="validate-group row" >
					<div class="col-sm-12  col-sm-offset-1" >
						<li style="text-align: center;" >
						<label></label>
							  <button  id="saveBut"  type="button" class="btn btn-system btn-xs"   onclick="save();">保 存</button>
						</li>
					</div>
				</div>
					<div class="validate-group row" >
					<div class="col-sm-12" >
						<li style="text-align: center;" >
						<font color="red" size="4" style="display: ${mmLength<6?'':'none'}">您的密码小于6位数，为了您的帐号安全，请您及时修改密码！</font>
						<font color="red" size="4" style="display: ${isPasswordExpired?'':'none'}">您的密码已过期，请重新设置！</font>	
						</li>
					</div>
				</div>										
			</ul>
				
			
			
			
		</form>
	</div>
</div>
</body>
</html>
