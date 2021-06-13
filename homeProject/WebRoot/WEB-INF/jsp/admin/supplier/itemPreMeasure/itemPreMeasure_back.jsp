<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>退回ItemPreMeasure</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
div.ui-jqgrid-bdiv{
	height: 200px;
}
</style>
<script type="text/javascript">
//校验函数
$(function() {
	$("#dataForm").validate({
		rules: {
			"cancelRemark": {
			validIllegalChar: true,
			required: true,
			maxlength: 200
			}
		}
	});
});
function back(){
	if(!$("#dataForm").valid()) {return false;}//表单校验
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/supplierItemPreMeasure/doBack',
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
</script>
</head>
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="backBut" class="btn btn-system" onclick="back()">退回</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
           	<house:token></house:token>
			<input type="hidden" name="pk" id="pk" value="${itemPreMeasure.pk}"/>
				<ul class="ul-form">
					<li>
						<label class="control-textarea"><span class="required">*</span>退回原因</label>
						<textarea id="cancelRemark" name="cancelRemark"></textarea>
					</li>
				</ul>
           	</form>
         </div>
     </div>
</div>
</body>
</html>
