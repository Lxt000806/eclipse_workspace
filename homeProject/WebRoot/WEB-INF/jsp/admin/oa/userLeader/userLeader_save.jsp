<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加UserLeader</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function save(flag){
	validateRefresh("openComponent_czybm_userId");
	validateRefresh("openComponent_czybm_leaderId");
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/userLeader/doSave',
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
						if (flag){
							closeWin();
						}else{
							$("#userId").val("");
							$("#openComponent_czybm_userId").val("");
							$("#_form_token_uniq_id").val(obj.datas.token);
						}
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
	$("#leaderId").openComponent_czybm();
	$("#userId").openComponent_czybm();
	$("#dataForm").bootstrapValidator({
        message : 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
            validating : 'glyphicon glyphicon-refresh'
        },
        fields: {
        openComponent_czybm_leaderId: {
      		validators: { 
	            notEmpty: { 
	            	message: '上级领导不能为空'  
	            }
	        }
      	},
      	openComponent_czybm_userId: {
      		validators: { 
      			notEmpty: { 
	            	message: '下属不能为空'  
	            }
	        }
      	}
      	},
        submitButtons : 'input[type="submit"]'
    });
});

</script>

</head>
    
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save(false)">保存</button>
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save(true)">保存并关闭</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(true)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
        	<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<house:token></house:token>
            	<ul class="ul-form">
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>上级领导</label>
            			<input type="text" id="leaderId" name="leaderId" style="width:160px;" value="${userLeader.leaderId}" />
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>下属</label>
            			<input type="text" id="userId" name="userId" style="width:160px;" value="${userLeader.userId}" />
            		</li>
            	</div>
            	</ul>
            </form>
         </div>
     </div>
</div>
</body>
</html>
