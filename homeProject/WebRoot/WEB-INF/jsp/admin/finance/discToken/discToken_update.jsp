
 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>预算区域</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_prePlanArea.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
 //校验函数
$(function() {	
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			expiredDate:{  
				validators: {  
					 notEmpty: {  
						message: '失效时间不能为空'  
					},
					stringLength: {
                           max: 20,
                           message: '长度不超过20个字符'
                    }
				}  
			},	    
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});


function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
 	var datas = $("#dataForm").serialize();
	$.ajax({		
		type: 'post',
		url: '${ctx}/admin/discTokenQuery/doUpdate',
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
					<button id="saveBut" type="button" class="btn btn-system " onclick="save()">保存</button>
					<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="m_umState" name="m_umState" value="${discToken.m_umState}" />
					<input type="hidden" id="no" name="no" value="${discToken.no}" />
					<ul class="ul-form">		
						<li class="form-validate">
							<label>失效日期</label>
							<input type="text" style="width:160px;" id="expiredDate" name="expiredDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${discToken.expiredDate }' pattern='yyyy-MM-dd'/>" />
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
