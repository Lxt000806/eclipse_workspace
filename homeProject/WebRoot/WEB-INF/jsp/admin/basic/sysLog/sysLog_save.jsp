<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加SysLog</title>
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
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/sysLog/doSave',
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
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			title:{  
				validators: {  
					stringLength: {  
						max: 200,
						message: '长度不超过200个字符'  
					}
				}  
			},
			type:{  
				validators: {  
					notEmpty: {  
						message: 'type不能为空'  
					} , 
					stringLength: {  
						max: 2,
						message: '长度不超过2个字符'  
					} 
				}  
			},
			appType:{
				validators:{
					notEmpty: {  
						message: 'appType不能为空'  
					} , 
					stringLength: {
                        max: 1,
                        message: '长度不超过1个字符'
                    } 
				}
			},
			requestUrl:{
				validators:{
					notEmpty: {  
						message: 'requestUrl不能为空'  
					} , 
					stringLength: {
                        max: 300,
                        message: '长度不超过300个字符'
                    } 
				}
			},
			userAgent:{
				validators:{
					stringLength: {
                        max: 200,
                        message: '长度不超过200个字符'
                    } 
				}
			},
			method:{
				validators:{
					notEmpty: {  
						message: 'method不能为空'  
					} , 
					stringLength: {
                        max: 30,
                        message: '长度不超过30个字符'
                    } 
				}
			},
			params:{
				validators:{ 
					stringLength: {
                        max: 4000,
                        message: '长度不超过4000个字符'
                    } 
				}
			},
			description:{
				validators:{ 
					stringLength: {
                        max: 4000,
                        message: '长度不超过4000个字符'
                    } 
				}
			},
			remoteAddr:{
				validators:{ 
					stringLength: {
                        max: 15,
                        message: '长度不超过15个字符'
                    } 
				}
			},
			operId:{
				validators:{
					notEmpty: {  
						message: 'operId不能为空'  
					} , 
					stringLength: {
                        max: 30,
                        message: '长度不超过30个字符'
                    } 
				}
			},
			operDate:{
				validators:{
					notEmpty: {  
						message: 'operDate不能为空'  
					} , 
					stringLength: {
                        max: 23,
                        message: '长度不超过23个字符'
                    } 
				}
			},
			clazz:{
				validators:{ 
					stringLength: {
                        max: 100,
                        message: '长度不超过100个字符'
                    } 
				}
			},
			responseContent:{
				validators:{ 
					stringLength: {
                        max: 4000,
                        message: '长度不超过4000个字符'
                    } 
				}
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
function validateRefresh(){
	 $('#dataForm').data('bootstrapValidator')
                   .updateStatus('title', 'NOT_VALIDATED',null)  
                   .validateField('title')
                   .updateStatus('type', 'NOT_VALIDATED',null)  
                   .validateField('type')
                   .updateStatus('appType', 'NOT_VALIDATED',null)  
                   .validateField('appType')
                   .updateStatus('requestUrl', 'NOT_VALIDATED',null)  
                   .validateField('requestUrl')
                   .updateStatus('userAgent', 'NOT_VALIDATED',null)  
                   .validateField('userAgent')
                   .updateStatus('method', 'NOT_VALIDATED',null)  
                   .validateField('method')
                   .updateStatus('params', 'NOT_VALIDATED',null)  
                   .validateField('params')
                   .updateStatus('description', 'NOT_VALIDATED',null)  
                   .validateField('description')
                   .updateStatus('remoteAddr', 'NOT_VALIDATED',null)  
                   .validateField('remoteAddr')
                   .updateStatus('operId', 'NOT_VALIDATED',null)  
                   .validateField('operId')
                   .updateStatus('operDate', 'NOT_VALIDATED',null)  
                   .validateField('operDate')
                   .updateStatus('clazz', 'NOT_VALIDATED',null)  
                   .validateField('clazz')
                   .updateStatus('responseContent', 'NOT_VALIDATED',null)  
                   .validateField('responseContent');                    
}
</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
   				<button id="saveBut" type="button" class="btn btn-system "   onclick="save()">保存</button>
   				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<div class="validate-group">
						<li class="form-validate">
							<label>title</label>
							<input type="text" id="title" name="title" value="${sysLog.title}" />
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>type</label>
							<input type="text" id="type" name="type" value="${sysLog.type}" />
						</li>
						<li class="form-validate">	
							<label><span class="required">*</span>appType</label>
							<input type="text" id="appType" name="appType" value="${sysLog.appType}" />
						</li>
					</div>
					<div class="validate-group">
						<li class="form-validate">
							<label><span class="required">*</span>requestUrl</label>
							<input type="text" id="requestUrl" name="requestUrl" value="${sysLog.requestUrl}" />
						</li>
						<li class="form-validate">
							<label>userAgent</label>
							<input type="text" id="userAgent" name="userAgent" value="${sysLog.userAgent}" />
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>method</label>
							<input type="text" id="method" name="method" value="${sysLog.method}" />
						</li>
					</div>
					<div class="validate-group">
						<li class="form-validate">
							<label>params</label>
							<input type="text" id="params" name="params" value="${sysLog.params}" />
						</li>
						<li class="form-validate">
							<label>description</label>
							<input type="text" id="description" name="description" value="${sysLog.description}" />
						</li>
						<li class="form-validate">
							<label>remoteAddr</label>
							<input type="text" id="remoteAddr" name="remoteAddr" value="${sysLog.remoteAddr}" />
						</li>
					</div>
					<div class="validate-group">
						<li class="form-validate">
							<label><span class="required">*</span>operId</label>
							<input type="text" id="operId" name="operId" value="${sysLog.operId}" />
						</li>
						<li class="form-validate">	
							<label><span class="required">*</span>operDate</label>
							<input type="text" id="operDate" name="operDate" value="${sysLog.operDate}" />
						</li>
						<li class="form-validate">
							<label>clazz</label>
							<input type="text" id="clazz" name="clazz" value="${sysLog.clazz}" />
						</li>
					</div>
					<div class="validate-group">
						<li class="form-validate">
							<label>responseContent</label>
							<input type="text" id="responseContent" name="responseContent" value="${sysLog.responseContent}" />
						</li>
					</div>
				</ul>	
			</form>
		</div>
	</div>
</div>
</body>
</html>
