<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加字典</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	
	$.ajax({
		url:'${ctx}/admin/dict/doSave',
		type: 'post',
		data: $("#dataForm").serialize(),
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		$("#infoBoxDiv").css("display","none");
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
	$("#dataForm").bootstrapValidator({
        message : 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
            validating : 'glyphicon glyphicon-refresh'
        },
        fields: {
        dictName: {
	        validators: { 
	            notEmpty: { 
	            	message: '字典名称不能为空'  
	            },
	            stringLength:{
               	 	min: 0,
          			max: 100,
               		message:'字典名称不能超过100字' 
               	}
	        }
      	},
      	dictCode: {
	        validators: {
	        	notEmpty: { 
	            	message: '字典编码不能为空'  
	            },
	            stringLength:{
               	 	min: 0,
          			max: 30,
               		message:'字典编码不能超过30字' 
               	}
	        }
      	},
      	remark: {
	        validators: { 
	            stringLength:{
               	 	min: 0,
          			max: 200,
               		message:'备注不能超过200字' 
               	}
	        }
      	}
        },
        submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
});

</script>

</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" class="btn btn-system" onclick="save()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
            	<house:token></house:token>
				<input type="hidden" id="dictType" name="dictType" value="${dictType }" />
				<ul class="ul-form">
				<div class="validate-group row">
					<div class="col-sm-6">
					<li class="form-validate">
						<label><span class="required">*</span>字典名称</label>
						<input type="text" style="width:160px;" id="dictName" name="dictName"/>
					</li>
					</div>
					<div class="col-sm-6">
					<li class="form-validate">
						<label><span class="required">*</span>字典编码</label>
						<input type="text" style="width:160px;" id="dictCode" name="dictCode"/>
					</li>
					</div>
				</div>
				<div class="validate-group row">
					<div class="col-sm-12">
					<li class="form-validate">
						<label class="control-textarea">备注</label>
						<textarea id="remark" name="remark"></textarea>
					</li>
					</div>
				</div>
				</ul>
			</form>
		</div>
	</div>
</div>
</body>
</html>
