<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>干系人管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function() {
		$("#custCode").openComponent_customer({callBack:validateRefresh});	
		$("#empCode").openComponent_employee({callBack:validateRefresh_emp});	
		$("#role").openComponent_roll({callBack:validateRefresh_roll});
			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					openComponent_customer_custCode:{  
				        validators: {  
				            notEmpty: {  
				           		 message: '客户编号不能为空'  
				            }
				        }  
				     },
				     openComponent_roll_role:{  
				        validators: {  
				            notEmpty: {  
				           		 message: '角色不能为空'  
				            },
					        remote: {
			    	            message: '',
			    	            url: '${ctx}/admin/roll/getRoll',
			    	            data: getValidateVal,  
			    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
			    	        } 
				        } 
				     },
				     openComponent_employee_empCode:{  
				        validators: {  
				            notEmpty: {  
				           		 message: '员工编号不能为空'  
				            },
				            remote: {
			    	            message: '',
			    	            url: '${ctx}/admin/employee/getEmployee',
			    	            data: getValidateVal,  
			    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
			    	        }
				        }  
				     },
				},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
	$(function(){
	
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			var datas = $("#dataForm").serialize();
			var code=$.trim($("#code").val());
			var custType=$.trim($("#custType").val());
			
			var custSceneDesi=$.trim($("#custSceneDesi").val());
			$.ajax({
				url:"${ctx}/admin/custStakeholder/doSave",
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
			    				$("#openComponent_employee_empCode").val('');
								$("#empCode").val("");
								$("#openComponent_roll_role").val('');
								$("#role").val("");
								$('#dataForm').data('bootstrapValidator')
								                   .updateStatus('openComponent_employee_empCode', 'NOT_VALIDATED',null) 
								                   .updateStatus('openComponent_roll_role', 'NOT_VALIDATED',null)  ;
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
		});
	});
	function validateRefresh(){
		$('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_customer_custCode', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_customer_custCode');
	}function validateRefresh_emp(){
		$('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_employee_empCode', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_employee_empCode');
	}function validateRefresh_roll(){
		$('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_roll_role', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_roll_role');
	}
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${custStakeholder.custCode}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>角色</label>
									<input type="text" id="role" name="role" style="width:160px;" value="${custStakeholder.role }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>员工编号</label>
									<input type="text" id="empCode" name="empCode" style="width:160px;" value="${custStakeholder.empCode }" />
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
