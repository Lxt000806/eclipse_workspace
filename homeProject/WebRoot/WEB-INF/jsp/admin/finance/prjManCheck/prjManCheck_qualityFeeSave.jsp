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
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
 //校验函数
$(function() {		
	$("#custCode").openComponent_customer({showLabel:"${prjCheck.custDescr}",showValue:"${prjCheck.custCode}" ,readonly:"true"});
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			custCode:{  
				validators: {  
					 notEmpty: {  
						message: '客户编号不能为空'  
					},
					stringLength: {
                           max: 20,
                           message: '长度不超过20个字符'
                    }
				}  
			},	
			qualityFee:{  
				validators: { 
					 notEmpty: {  
						message: '金额不能为空'  
					},
					numeric: {		
		            	message: '金额只能输入数字' 
		            },
               		stringLength: {
                           max: 10,
                           message: '长度不超过10个字符'
                    }	
				}  
			},	
			accidentFee:{  
				validators: { 
					 notEmpty: {  
						message: '金额不能为空'  
					},
					numeric: {		
		            	message: '金额只能输入数字' 
		            },
               		stringLength: {
                           max: 10,
                           message: '长度不超过10个字符'
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
		url:'${ctx}/admin/prjManCheck/doSaveQualityFee',
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
					<button id="saveBut" type="button" class="btn btn-system " onclick="save()">保存</button>
					<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate"><label style="color:#777777"><span class="required">*</span>客户编号</label> <input type="text"
								id="custCode" name="custCode" value="${prjCheck.custCode}" />
							</li>
							<li class="form-validate"><label style="color:#777777">楼盘</label> <input type="text" id="address"
								name="address" value="${prjCheck.address}" readonly="readonly" />
							</li>
						</div>
						<div class="validate-group">
							
							<li class="form-validate"><label>质保金</label> <input type="text" id="qualityFee"
								name="qualityFee"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								value="${prjCheck.qualityFee}" />
							</li>
							<li class="form-validate"><label>意外险</label> <input type="text" id="accidentFee"
								name="accidentFee"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								value="${prjCheck.accidentFee}" />
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label>质保金余额</label> <input type="text" id="remainQualityFee"
								name="remainQualityFee" readonly="readonly"
								value="${prjCheck.remainQualityFee}" />
							</li>
							<li class="form-validate"><label>意外险余额</label> 
								<input type="text" id="remainAccidentFee" name="remainAccidentFee" readonly="readonly"
								value="${prjCheck.remainAccidentFee}" />
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

