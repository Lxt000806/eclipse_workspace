<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<title>仓库信息--增加</title>
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_builderNum.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		function save(){
			$("#dataForm").bootstrapValidator("validate");
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:'${ctx}/admin/customer/doSoftSalesCust',
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
							width: 200
						});
			    	    var datas=$("#dataForm").jsonForm();
			    	    if($("#m_umState").val()=="A"){
			    	    	 datas.code=obj.datas;
			    	    } 
			    	    if ($("#designman").val()!=""){
			    	    	datas.designmandescr=datas.openComponent_employee_designMan.substring(datas.openComponent_employee_designMan.indexOf("|")+1);
			    		}else{
			    			datas.designmandescr="";
			    		}
			    	    if ($("#businessMan").val()!=""){
			    			var businessmandescr=$("#openComponent_employee_businessMan").val().trim().split("|");
			    			businessmandescr=businessmandescr[1];
			    			datas.businessmandescr=businessmandescr;
			    		}
			    		Global.Dialog.returnData =datas; 
			    		closeWin();
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
		$(function() {
			$("#builderCode").openComponent_builder({showLabel:"${customer.builderCodeDescr}",showValue:"${customer.builderCode}",
				callBack:function(data){$("#address").val(data.descr);validateRefresh('openComponent_builder_builderCode');}});
			$("#businessMan").openComponent_employee({showLabel:"${customer.businessManDescr}",showValue:"${customer.businessMan}",
				callBack:function(){validateRefresh('openComponent_employee_businessMan');}});
			$("#designMan").openComponent_employee({showLabel:"${customer.designManDescr}",showValue:"${customer.designMan}",
				callBack:function(){validateRefresh('openComponent_employee_designMan');}});
		
			$("#againMan").openComponent_employee({
			    showLabel: "${customer.againManDescr}",
			    showValue: "${customer.againMan}",
		    })
			
			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			    },
				fields : {
					descr: {
				         validators: { 
				            notEmpty: { 
				            	message: '客户名称不能为空'  
				            },  
			        	}
		      	    },
		      	  address : {
						validators : {
					      	proStringLength: {
				                 max: 200,
				                 message: '长度必须小于200个字符'
				            }
						},
					},
					layout : {
						validators : {
							notEmpty : {
								message : '户型不能为空'
							}
						}
					},
					mobile1: {
				        validators: { 
				            notEmpty: { 
				            	message: '手机号码不能为空'  
				            },
				            regexp: {
	                            regexp: /^1\d{10}$/,
	                            message: '请填写11位数字'
	                        },
				        }
			      	},
			      	openComponent_employee_businessMan:{  
							validators: {  
								notEmpty: {  
									message: '业务员不能为空'  
								},             
								remote: {
						            message: '',
						            url: '${ctx}/admin/employee/getEmployee',
						            data: getValidateVal,  
						            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
						        }
							}  
					},
					openComponent_builder_builderCode:{  
						validators: {  
				            remote: {
					            message: '',
					            url: '${ctx}/admin/builder/getBuilder',
					            data: getValidateVal,  
					            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
					        }
						}  
					},
					
					openComponent_employee_designMan:{  
						validators: {             
							remote: {
					            message: '',
					            url: '${ctx}/admin/employee/getEmployee',
					            data: getValidateVal,  
					            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
					        }
						}  
				    },
					
				},
				
				submitButtons : 'input[type="submit"]'
			});
		});
	</script>
	</head>
	<body>
	 	<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
			    		<button type="submit" class="btn btn-system" id="saveBut" onclick="save()">保存</button>
	     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="m_umState" id="m_umState" value="${customer.m_umState }"/>
						<ul class="ul-form">					  
							<li hidden="true" class="form-validate">
								<label >客户编号</label>
								<input type="text" id="code" name="code" value="${customer.code }" readonly="readonly" placeholder="保存时生成" />
							</li>
							<li class="form-validate">	
								<label><span class="required">*</span>客户名称</label>
								<input type="text" id="descr" name="descr" value="${customer.descr }"  />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>手机号码</label>
								<input type="text" style="width:160px;" id="mobile1" name="mobile1" maxlength="11" value="${customer.mobile1 }"  />
							</li>	
							<li class="form-validate">
								<label>项目名称</label>
								<input type="text" id="builderCode" name="builderCode" value="${customer.builderCode}"/>
							</li>			
							<li class="form-validate">
								<label><span class="required">*</span>户型</label>
								<house:xtdm id="layout" dictCode="LAYOUT" value="${customer.layout}" ></house:xtdm>
							</li>	
							<li class="form-validate">
								<label>楼盘地址</label>
								<input type="text" style="width:450px;" id="address" name="address" value="${customer.address }" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>业务员</label>
								<input type="text" id="businessMan" name="businessMan" value="${customer.businessMan}" />
							</li>
							<li class="form-validate">
								<label>设计师</label>
								<input type="text" id="designMan" name="designMan" value="${customer.designMan}" />
							</li>
							<li class="form-validate">
                                <label>翻单员/导客</label>
                                <input type="text" id="againMan" name="againMan" value="${customer.againMan}" />
                            </li>
						</ul>		
					</form>
				</div>
	  		</div>
	  	</div>
	  </body>
</html>


