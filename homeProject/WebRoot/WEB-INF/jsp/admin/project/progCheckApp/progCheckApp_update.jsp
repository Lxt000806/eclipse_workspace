<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加ProgCheckApp</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/progCheckApp/doUpdate',
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
	$("#custCode").openComponent_customer({callBack:validateRefresh});

			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					address:{  
						validators: {  
							notEmpty: {  
								message: '楼盘不能为空'  
							}
						}  
					},
			/* 	openComponent_customer_custCode:{  
			        validators: {  
			            notEmpty: {  
			            message: '客户编号不能为空'  
			            },
			             remote: {
				            message: '',
				            url: '${ctx}/admin/customer/getCustomer',
				            data: getValidateVal,  
				            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
				        }   
			        }  
			     }, */
				},
				submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
			});
	$("#custCode").openComponent_customer({showLabel:"${progCheckApp.descr}",showValue:"${progCheckApp.custCode}",
		callBack: function(data){$("#address").val(data["address"])},condition:{status:'4'}});
	// $("#openComponent_customer_custCode").attr("readonly",true);
		
});
function validateRefresh(){
	 $('#dataForm').data('bootstrapValidator')  
                   .updateStatus('openComponent_customer_custCode', 'NOT_VALIDATED',null)  
                    .validateField('openComponent_customer_custCode');  
}
</script>

</head>
    
<body>
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
      				<button type="button" class="btn btn-system " href="#"  id="saveBut" onclick="save()">
					   <span>保存</span>
					   </button>
				<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin()">
					<a href="#" class="a2">
						<span>关闭</span>
					</a>
				</button>
				</div>
			</div>	
		</div>
		<div class="infoBox" id="infoBoxDiv"></div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<ul class="ul-form">
						<li class="form-validate">
							<label><span class="required">*</span>客户编号</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;" value="${progCheckApp.custCode}" />
						</li>
						<li class="form-validate">
							<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${progCheckApp.address}" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label class="control-textarea">说明</label>
							<textarea rows="2" id="remarks" name="remarks" maxlength="200">${progCheckApp.remarks }</textarea>
						</li>
					</ul>
			</form>
		</div>
		</div>
	</div>
</div>
</body>
</html>
