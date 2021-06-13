<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加CommiStdDesignRule</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">

function save(){
	//表单校验
	$("#dataForm").bootstrapValidator('validate');
	if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
	sUrl='${ctx}/admin/commiStdDesignRule/doSave'
	if ("${commiStdDesignRule.m_umState}"=='M'){
		sUrl='${ctx}/admin/commiStdDesignRule/doUpdate'
	}
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:sUrl,
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
	if ("${commiStdDesignRule.m_umState}"!='V'){
		$("#saveBtn").show();
	}
	if("${commiStdDesignRule.m_umState}"=='A'){
		$("#expiredli_show").hide();	
	}
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			"descr": {
				notEmpty : {
					message : '名称不能为空'
				},
			},
			"stdDesignFeeAmount": {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					numeric: {
						   message: '只能输入数字', 
					}
				}		
			},
			"stdDesignFeePrice": {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					numeric: {
						   message: '只能输入数字', 
					}
				}		
			},
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

</script>

</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn" onclick="save()" hidden="hidden">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value="" /> 
						<input type="hidden" id="m_umState" name="m_umState" value="${commiStdDesignRule.m_umState}"/>
						<input type="hidden" id="expired" name="expired" value="${commiStdDesignRule.expired}"/>
						<input type="hidden" id="pk" name="pk" value="${commiStdDesignRule.pk}" />
						<ul class="ul-form">	
							<li class="form-validate">
								<label style="width:160px"><span class="required">*</span>名称</label> 
								<input type="text" id="descr" name="descr" value="${commiStdDesignRule.descr}" />
							</li>
							<li class="form-validate">
								<label style="width:160px"><span class="required">*</span>收设计费标准-总金额</label> 
								<input type="text" id="stdDesignFeeAmount" name="stdDesignFeeAmount" value="${commiStdDesignRule.stdDesignFeeAmount}" />
							</li>
							<li class="form-validate">
								<label style="width:160px"><span class="required">*</span>收设计费标准-每平米</label> 
								<input type="text" id="stdDesignFeePrice" name="stdDesignFeePrice" value="${commiStdDesignRule.stdDesignFeePrice}" />
							</li>
							<li id="expiredli_show" class="form-validate">
								<label style="width:160px">过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${commiStdDesignRule.expired }" onclick="checkExpired(this)" ${commiStdDesignRule.expired=='T'?'checked':'' } >
						   </li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>
