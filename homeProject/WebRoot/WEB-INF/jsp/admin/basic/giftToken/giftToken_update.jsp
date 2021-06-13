<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
  
  	<title>编辑礼品券信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>
  	<script type="text/javascript">
  	
  			//校验函数
		$(function() {
   			$("#custCode").openComponent_customer({
   				showValue:"${giftToken.custCode}",
   				showLabel:"${giftToken.custDescr}",
   				callBack:validateRefresh_cust,
   				condition:{
   					ignoreCustRight:"1"
   				}
   			});	
   			// $("#openComponent_customer_custCode").attr("readonly",true);	
   			$("#itemCode").openComponent_item({
   				showValue:"${giftToken.itemCode}",
   				showLabel:"${giftToken.itemDescr}",
   				condition:{
   					itemType1:"LP",
   					actNo:"Null"
   				},
   				callBack:validateRefresh_item
   			});
   			// $("#openComponent_item_itemCode").attr("readonly",true);	
   			$("#no").openComponent_cmpactivity({
   				showValue:"${giftToken.no}",
   				showLabel:"${giftToken.noDescr}",
	   			callBack:function () {
					$('#dataForm').data("bootstrapValidator")
		                   .updateStatus("openComponent_cmpactivity_no", "NOT_VALIDATED",null)   
		                   .validateField("openComponent_cmpactivity_no");  
	   				$("#itemCode").openComponent_item({
	   					showValue:"${giftToken.itemCode}",
	   					showLabel:"${giftToken.itemDescr}",
	   					condition:{
	   						itemType1:"LP",
	   						actNo:$("#no").val()
	   					},
	   					callBack:validateRefresh_item
	   				});
	   				// $("#openComponent_item_itemCode").attr("readonly",true);
	   				$("#itemCode").val("");
	   				$("#openComponent_item_itemCode").val("");
	   			}
   			}); 
   			$("#openComponent_cmpactivity_no").attr("readonly",true);	
			$("#dataForm").bootstrapValidator({
				message : "This value is not valid",
				feedbackIcons : {/*input状态样式图片*/
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {  
					tokenNo:{  
						validators: {  
							notEmpty: {  
								message: "券号不能为空"  
							},
							stringLength: {
	                            max: 20,
	                            message: "长度不超过20个字符"
	                        }
						}  
					},
					qty:{  
						validators: {  
							notEmpty: {  
								message: "数量不能为空"  
							},
							numeric: {
	                            message: "只能输入数字"
	                        },
	                        callback: {  
		                         message: "数量必须大于0",  
		                         callback: function(value, validator) {  
		                            if(value!="" && value<=0){
		                            	return false;
		                            }else{
		                            	return true;
		                            }  
		                        }  
		                    } 
						}  
					},
					openComponent_customer_custCode:{  
						validators: {  
							notEmpty: {  
								message: "客户不能为空"  
							} ,             
							remote: {
					            message: "",
					            url: "${ctx}/admin/customer/getCustomer",
					            data: getValidateVal,  
					            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
					        }
						}  
					},
					openComponent_item_itemCode:{  
						validators: {  
							notEmpty: {  
								message: "礼品不能为空"  
							} ,
				            callback: {  
				            	message: "请输入有效的材料编号"  
				            }  
						}  
					},
					status:{
						validators:{ 
							notEmpty: {  
								message: "状态不能为空"  
							},
							stringLength: {
	                            max: 1,
	                            message: "长度不超过1个字符"
	                        }
						}
					},
					openComponent_cmpactivity_no:{
						validators:{ 
							notEmpty: {  
								message: "活动编号不能为空"  
							}
						}
					}
				},
				submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
			}).on("success.form.bv", function (e) {
			   		e.preventDefault();
			   		save();	
				});		
		});
		function clearForm(){
			$("#dataForm input[type='text']").val("");
			$("#dataForm select").val("");
		}
  		function save(){
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
			
   			var datas = $("#dataForm").serialize();
   			
			$.ajax({
				url:"${ctx}/admin/giftToken/doEdit",
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({
						"hidden": false, 
						"msg": "保存数据出错~"
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
								if(obj.msg.trim() != "该券号已经登记过"){
			    					closeWin();
								}
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
			 $("#dataForm").data("bootstrapValidator")
		                   .updateStatus("openComponent_customer_custCode", "NOT_VALIDATED",null)   
		                   .validateField("openComponent_customer_custCode")
		                   .updateStatus("openComponent_item_itemCode", "NOT_VALIDATED",null)  
		                   .validateField("openComponent_item_itemCode")
		                   .updateStatus("status", "NOT_VALIDATED",null)  
		                   .validateField("status")
		                   .updateStatus("qty", "NOT_VALIDATED",null)  
		                   .validateField("qty")
		                   .updateStatus("tokenNo", "NOT_VALIDATED",null)  
		                   .validateField("tokenNo")
		                   .updateStatus("openComponent_cmpactivity_no", "NOT_VALIDATED",null)  
		                   .validateField("openComponent_cmpactivity_no");                    
		}
		function validateRefresh_cust(){
			 $("#dataForm").data("bootstrapValidator")
		                   .updateStatus("openComponent_customer_custCode", "NOT_VALIDATED",null)   
		                   .validateField("openComponent_customer_custCode");                    
		}
		function validateRefresh_item(){
			 $("#dataForm").data("bootstrapValidator")
		                   .updateStatus("openComponent_item_itemCode", "NOT_VALIDATED",null)  
		                   .validateField("openComponent_item_itemCode");                    
		}
  	</script>

  </head>
  
  <body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
     				<button id="saveBut" type="button" class="btn btn-system "   onclick="validateDataForm()">保存</button>
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate">
								<label><span class="required">*</span>客户编号</label>
								<input type="text" id="custCode" name="custCode" value="${giftToken.custCode }" />
							</li>
							<li class="form-validate">	
								<label><span class="required">*</span>活动编号</label>
								<input type="text" id="no" name="no" value="${giftToken.no}" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>礼品编号</label>
								<input type="text" id="itemCode" name="itemCode" value="${giftToken.itemCode }" />
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate">
								<label><span class="required">*</span>券号</label>
								<input type="text" id="tokenNo" name="tokenNo" value="${giftToken.tokenNo}" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>数量</label>
								<input type="text" id="qty" name="qty" value="${giftToken.qty}" />
							</li>
							<li class="form-validate">
								<label>状态</label>
								<house:xtdm id="status" dictCode="GIFTTOKENSTATUS" value="${giftToken.status }" disabled="true"></house:xtdm>
							</li>
						</div>
						<div class="validate-group">
							<li hidden>
								<label>pk</label>
								<input type="text" id="pk" name="pk" value="${giftToken.pk}" />
							</li>
						</div>
					</ul>
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>
