<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
  
  	<title>添加客户资源分配信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
  	<script type="text/javascript">
  	
  			//校验函数
		$(function() {
   			$("#builderCode").openComponent_builder({callBack:fillData});		
   			$("#openComponent_builder_builderCode").attr("readonly",true);
   			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					department2:{  
						validators: {  
							notEmpty: {  
								message: '二级部门不能为空'  
							}  
						}  
					},
					rightType:{  
						validators: {  
							notEmpty: {  
								message: '权限类型不能为空'  
							}  
						}  
					},
					openComponent_builder_builderCode:{  
						validators: {  
							notEmpty: {  
								message: '项目编号不能为空'  
							} ,	             
							remote: {
					            message: '',
					            url: '${ctx}/admin/builder/getBuilder',
					            data: getValidateVal,  
					            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
					        } 
						}  
					}
				},
				submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
			});	
		});
  	
  		function save(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			
   			var datas = $("#dataForm").serialize();
			$.ajax({
				url:'${ctx}/admin/ResrCustRight/doSave',
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
		function validateRefresh(){
			 $('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_builder_builderCode', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_builder_builderCode')
		                   .updateStatus('rightType', 'NOT_VALIDATED',null)  
		                   .validateField('rightType')
		                   .updateStatus('department2', 'NOT_VALIDATED',null)  
		                   .validateField('department2');                    
		}
		function validateRefresh_choice(){
			 $('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_builder_builderCode', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_builder_builderCode');                    
		}
		function fillData(ret){
			validateRefresh_choice();
		}
  	</script>

  </head>
  
  <body>
 	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
     				<button id="saveBut" type="button" class="btn btn-system "   onclick="save()">保存</button>
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group">
							<li >
								<label><span class="required">*</span>一级部门</label>
								<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${resrCustRight.department1 }"></house:department1>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>二级部门</label>
								<house:department2  id="department2" dictCode="${resrCustRight.department1 }" value="${resrCustRight.department2 }"></house:department2>
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate">
								<label><span class="required">*</span>项目编码</label>
								<input type="text" id="builderCode" name="builderCode" value="${resrCustRight.builderCode}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>权限类型</label>
								<house:xtdm id="rightType"  dictCode="CUSTRIGHTTYPE" value="${resrCustRight.rightType }"></house:xtdm>
							</li>
						</div>
					</ul>
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>
