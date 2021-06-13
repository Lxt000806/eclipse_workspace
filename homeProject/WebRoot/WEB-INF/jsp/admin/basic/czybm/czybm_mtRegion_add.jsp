 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>添加CustLoan</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_mtRegion.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
 //校验函数
$(function() {	
	$("#mtRegionCode").openComponent_mtRegion({callBack:function(){	  
   			validateRefresh('openComponent_mtRegion_mtRegionCode')}});
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			mtRegionCode:{  
				validators: {  
					 notEmpty: {  
						message: '麦田区域编号不能为空'  
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
	
	 $("#dataForm").bootstrapValidator("addField", "openComponent_mtRegion_mtRegionCode", {
        validators: {         
            remote: {
	           message: '',
	           url: '${ctx}/admin/mtRegion/getMtRegion',
	           data: getValidateVal,  
	           delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
});

function save(){;	
	if ($.trim($("#mtRegionCode").val())==""){
		art.dialog({
			content: "大区编号不能为空！",
			width: 200
		});
		return false;
	}
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
 	var datas = $("#dataForm").serialize();	
	$.ajax({
		url:'${ctx}/admin/czyMtRegion/doSave',
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
	
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBut" type="button" class="btn btn-system " onclick="save()" >保存</button>
					<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					 <input type="hidden" id="czybh" name="czybh" value="${czyMtRegion.czybh}" readonly="readonly" />
					<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate"><label style="color:#777777"><span class="required">*</span>麦田区域</label> <input type="text"
								id="mtRegionCode" name="mtRegionCode" value="${czyMtRegion.mtRegionCode}" />
							</li>	
						</div>
						
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

