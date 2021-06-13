<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>项目大类--新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").data("bootstrapValidator").isValid()) return;//表单校验
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/builderGroup/doCopy',
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
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			descr : {
				validators : {
					notEmpty : {
						message : '名称不能为空',
					}
				}
			},
		},
		submitButtons : 'input[type="submit"]'
	}); 
});
</script>
</head>   
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
    		<div class="btn-group-xs" >
    			<button type="submit" class="btn btn-system" onclick="save()">保存</button>
   				<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<ul class="ul-form">	
					<input type="text" id="expired" name="expired" value="${builderGroup.expired}" hidden="true"/>
					<div class="validate-group row">				  
						<li>
							<label>项目大类编号</label>
							<input type="text" id="code" name="code" placeholder="保存自动生成" readonly="readonly" />
						</li>
						<li class="form-validate">
							<label>项目大类名称</label>
							<input type="text" id="descr" name="descr" value="${builderGroup.descr }"/>
						</li>
					</div>
				    <div class="validate-group row">
						<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" rows="4">${builderGroup.remarks }</textarea>
						</li>
					</div>
				</ul>		
			</form>
		</div>
  	</div>
</div>
</body>
</html>
