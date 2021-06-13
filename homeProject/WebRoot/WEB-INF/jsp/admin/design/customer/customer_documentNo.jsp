<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改档案号</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	//验证
	var datas = {code : '${customer.code}',documentNo : $("#documentNo").val()};
	$.ajax({
		url:'${ctx}/admin/customer/doUpdate?flag=${flag}',
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
        feedbackIcons : {/*input状态样式图片*/
            validating : 'glyphicon glyphicon-refresh'
        },
        fields: {
        documentNo: {
	        validators: { 
	            notEmpty: { 
	            	message: '档案号不能为空'  
	            },
	            stringLength:{
               	 	min: 8,
          			max: 8,
               		message:'档案号长度必须为8' 
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
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
					<ul class="ul-form">
						<li>
							<label><span class="required">*</span>客户编号</label>
							<input type="text" style="width:160px;" id="code" name="code" value="${customer.code }" readonly="readonly"/>
						</li>
						<li>
							<label><span class="required">*</span>客户名称</label>
							<input type="text" style="width:160px;" id="descr" name="descr" value="${customer.descr }" readonly="readonly"/>
						</li>
						<li>
							<label><span class="required">*</span>楼盘</label>
							<input type="text" style="width:160px;" id="address" name="address" value="${customer.address }" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>档案号</label>
							<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${customer.documentNo }"/>
						</li>
					</ul>
            	</form>
            </div>
          </div>
</div>
</body>
</html>
