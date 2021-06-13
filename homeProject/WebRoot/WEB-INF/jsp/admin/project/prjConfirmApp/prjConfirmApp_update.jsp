<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
  
  	<title>编辑验收申请信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
  	<script type="text/javascript">
  		var oldPrjItem = "";
  		var oldCustCode = "";
  			//校验函数
		$(function() {

			oldCustCode = "${prjConfirmApp.custCode}";
			oldPrjItem = "${prjConfirmApp.prjItem}";
   		   	$("#custCode").openComponent_customer({showValue:"${prjConfirmApp.custCode}",showLabel:"${prjConfirmApp.custDescr}",condition:{Status:'4'},callBack:fillData});	
			//var selected = "${prjConfirmApp.prjItem}";		
			//getConfirmPrjItem(selected);
			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					prjItem:{  
						validators: {  
							notEmpty: {  
								message: '施工项目不能为空'  
							}  
						}  
					},
					openComponent_customer_custCode:{  
						validators: {  
							notEmpty: {  
								message: '客户不能为空'  
							},	             
							remote: {
					            message: '',
					            url: '${ctx}/admin/customer/getCustomer',
					            data: getValidateVal,  
					            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
					        }   
						}  
					},
					remarks:{
						validators:{
							stringLength: {
	                            max: 200,
	                            message: '长度不超过200个字符'
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
  	  	function fillData(ret){
			$("#address").val(ret.address);
			validateRefresh_choice();
			//getConfirmPrjItem();
		}
		function getConfirmPrjItem(selected){
			$.ajax({
				url:'${ctx}/admin/PrjConfirmApp/getConfirmPrjItem?custCode='+$("#custCode").val(),
				type: 'post',
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '页面出错,请重试'});
			    },
			    success: function(obj){
			    	if(obj){
			    		$("#prjItem").html(obj.newPrjItem);
			    		if(selected!=null && selected!=undefined){
							$("option[value='']").removeAttr("selected");
							$("option[value='"+selected+"']").attr("selected","selected");
			    		}
			    	}
			    }
			}); 
		}
  		function save(){
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			
   			var datas = $("#dataForm").serialize();
   			
   			var isChange = false;
   			if(oldCustCode != $("#custCode").val() || oldPrjItem != $("#prjItem").val() ){
   				isChange = true;
   			}
			$.ajax({
				url:'${ctx}/admin/PrjConfirmApp/doUpdate?isChange='+isChange,
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
		                   .updateStatus('openComponent_customer_custCode', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_customer_custCode')
		                   .updateStatus('prjItem', 'NOT_VALIDATED',null)  
		                   .validateField('prjItem')
		                   .updateStatus('remarks', 'NOT_VALIDATED',null)  
		                   .validateField('remarks');                    
		}
		function validateRefresh_choice(){
			 $('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_customer_custCode', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_customer_custCode');                    
		}
  	</script>

  </head>
  
  <body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
     				<button id="saveBut" type="button" class="btn btn-system "   onclick="validateDataForm()">保存</button>
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
							<li class="form-validate">
								<label>客户</label>
								<input type="text" id="custCode" name="custCode" value="${prjConfirmApp.custCode }" />
							</li>
							<li class="form-validate">	
								<label>施工节点</label>
								<house:dict id="prjItem" dictCode="" sql="SELECT Code,Descr FROM tPrjItem1 WHERE IsConfirm='1' AND Expired='F' ORDER BY Seq" sqlValueKey="Code" sqlLableKey="Descr" value="${prjConfirmApp.prjItem }"></house:dict>
							</li>
						</div>
						<div class="validate-group">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" value="${prjConfirmApp.address }" />
							</li>
							<li>	
								<label>申请时间</label>
								<input type="text" id="appDate" name="appDate" name="appDate" value="<fmt:formatDate value='${prjConfirmApp.appDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="true"/>
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate">
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks">${prjConfirmApp.remarks}</textarea>
							</li>
						</div>
						<li hidden>
							<label>pk</label>
							<input id="pk" name="pk" value="${prjConfirmApp.pk }" />
						</li>
					</ul>
				</form>
			</div>
		</div>
  	</div>
  </body>
</html>
