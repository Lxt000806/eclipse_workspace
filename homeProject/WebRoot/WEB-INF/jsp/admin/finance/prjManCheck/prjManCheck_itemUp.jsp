<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>材料免费升级--编辑</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
//校验函数
$(function() {
	  $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {  
		        isItemUp: {  
			        validators: {  
			            notEmpty: {  
			            message: '材料免费升级不能为空'  
			            }  
			        }  
		       }  
    		},
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        }).on('success.form.bv', function (e) {
   		 e.preventDefault();
   		 save();	
	});
   		
});
function save(){
    if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
    if($("#isItemUp").val()=="${customer.isItemUp}"){
    	art.dialog({
			content: "您未做任何修改，无需保存！"
		});
		return;
    }         
    var datas = $("#dataForm").serialize();
  	$.ajax({
		url:'${ctx}/admin/prjManCheck/doUpdateItemUp',
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
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="validateDataForm()">保存</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<input type="hidden" id="code" name="code" value="${customer.code}" />
					<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate"><label></span>材料免费升级 </label> <house:xtdm id="isItemUp" dictCode="YESNO"
							value="${customer.isItemUp}"></house:xtdm></li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
