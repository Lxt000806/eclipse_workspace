<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加模型</title>
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
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/workflow/model/doCreate',
			type: 'post',
			data: datas,
			dataType: 'html',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	$("#id_design").html(obj);
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
	      	name: {
	      		validators: { 
		            notEmpty: { 
		            	message: '名称不能为空'  
		            }
		        }
	      	},
	      	key: {
	      		validators: { 
		            notEmpty: { 
		            	message: 'KEY不能为空'  
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
<div class="body-box-form" id="id_design">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
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
            			<label><span class="required">*</span>名称</label>
						<input type="text" id="name" name="name" style="width:160px;" value=""/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>KEY</label>
						<input type="text" id="key" name="key" style="width:160px;" value=""/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label class="control-textarea">描述</label>
						<textarea id="description" name="description" maxlength="200"></textarea>
            		</li>
            	</div>
            	</ul>
            </form>
         </div>
     </div>
</div>
</body>
</html>

