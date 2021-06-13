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
	<title>活动门票发放</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_activity.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_ticket.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
	$(function() {
		$("#actNo").openComponent_activity({showValue:'${hdmpgl.actNo }',showLabel:'${hdmpgl.actDescr }',callBack:getData,condition:{validAct:'1'}});
		if('${hdmpgl.provideType}'=='2' ){
			$("#provideType option[value='2']").attr("selected","selected");
			$("#businessMan").openComponent_employee({readonly:true});	
			$("#designMan").openComponent_employee({readonly:true});
			$('input[name=openComponent_employee_designMan]').css('background','#CCCCCC');
			$('input[name=openComponent_employee_businessMan]').css('background','#CCCCCC');
			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					address:{  
						validators: {  
							notEmpty: {  
								message: '楼盘不能为空'  ,
							}
						}  
					},
					descr:{  
						validators: {  
							notEmpty: {  
								message: '业主姓名不能为空'  ,
							}
						}  
					},
					phone:{  
						validators: {  
							notEmpty: {  
								message: '业主电话不能为空'  
							},
							 numeric: {
				            	message: '业主电话只能是数字' 
				            },
				            stringLength:{
			               	 	min: 0,
			          			max: 11,
			               		message:'业主电话长度必须在0-11之间' 
			               	}
						}  
					},
					ticketNo:{  
						validators: {  
							notEmpty: {  
								message: '门票号不能为空'  
							}
						}  
					},
					openComponent_activity_actNo:{  
				        validators: {  
				            notEmpty: {  
				           		 message: '活动编号不能为空'  ,
				            },
				            remote: {
			    	            message: '活动编号无效',
			    	            url: '${ctx}/admin/activity/getActivity',
			    	            data: getValidateVal,  
			    	            delay: 1000 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
			    	        }
				        }  
				     },
				     openComponent_employee_designMan:{  
				        validators: {  
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
		}else{
			$("#provideType option[value='1']").attr("selected","selected");
			$("#busiManName").attr("readonly","readonly");
			$("#busiManPhone").attr("readonly","readonly");
			$('input[name=busiManPhone]').css('background','#CCCCCC');
			$('input[name=busiManName]').css('background','#CCCCCC');
			$("#businessMan").openComponent_employee({callBack:validateRefresh_choice} );
			$("#designMan").openComponent_employee( );
			
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
					descr:{  
						validators: {  
							notEmpty: {  
								message: '业主姓名不能为空'  ,
							}
						}  
					},
					phone:{  
						validators: {  
							notEmpty: {  
								message: '业主电话不能为空'  
							},
							 numeric: {
				            	message: '业主电话只能是数字' 
				            },
				            stringLength:{
			               	 	min: 0,
			          			max: 11,
			               		message:'业主电话长度必须在0-11之间' 
			               	}
						}  
					},
					ticketNo:{  
						validators: {  
							notEmpty: {  
								message: '门票号不能为空'  
							}
						}  
					},
					openComponent_activity_actNo:{  
				        validators: {  
				            notEmpty: {  
				           		 message: '活动编号不能为空'  ,
				            },
				            remote: {
				            	message: '活动编号无效',
				            	url: '${ctx}/admin/activity/getActivity',
				            	data: getValidateVal,
			    	            delay: 1000,
			    	            type:'post',
			    	        }
				        }  
				     },
				     openComponent_employee_designMan:{  
				        validators: {  
				            remote: {
			    	            message: '',
			    	            url: '${ctx}/admin/employee/getEmployee',
			    	            data: getValidateVal,  
			    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
			    	        }
				        }  
				     },
				     openComponent_employee_businessMan:{  
				        validators: {  
				            notEmpty: {  
				           		 message: '业务员不能为空'  ,
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
		}
		function getPk(dataPk){
			if(!dataPk) return;
			$("#pk").val(dataPk.PK);
			
		}
		function getData(data){
			if (!data) return;
			validateRefresh_choice();
			$("#ticketNo").openComponent_ticketNo({callBack:getPk,condition:{actNo:data.no,notSend:'1'}});	
		}
});	
$(function(){
	
	if('${hasAuthByCzybh}'!="true"){
		$("#provideType").attr("disabled","true");
	} 

	$("#provideCZY").openComponent_employee({showValue:'${hdmpgl.provideCZY }',showLabel:'${hdmpgl.provideDescr }',readonly:true});
		
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var provideType=$.trim($("#provideType").val());
		var businessMan=$.trim($("#businessMan").val());
		var designMan=$.trim($("#designMan").val());
		var planSupplType=$.trim($("#planSupplType").val());
		if(planSupplType==""){
			art.dialog({
				content:"购买意向不能为空",
			});
			return;
		}
		if(provideType=='1'){
			if(businessMan==""||businessMan==null){
				art.dialog({
					content:"请填写完整信息。",
				});
				$("#businessMan").focus();
				return false;
			}
		}
		$("#provideType").removeAttr("disabled","true");
		var datas = $("#dataForm").serialize();
		
		$.ajax({
			url:'${ctx}/admin/supplierHdmpgl/doProvide',
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
							$("#descr").val("");
							$("#address").val("");
							$("#phone").val("");
							$("#ticketNo").val("");
							$("#busiManName").val("");
							$("#busiManPhone").val("");
							$("#designMan").val("");
							$("#openComponent_employee_designMan").val('');
							$("#businessMan").val("");
							$("#openComponent_employee_businessMan").val('');
							$("#provideType").attr("disabled","true");
							$("#planSupplType_NAME").val("");
							$("#planSupplType").val("");
							$.fn.zTree.getZTreeObj("tree_planSupplType").checkAllNodes(false);
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		$("#provideType").attr("disabled","true");
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
		 });
	});
			
});
function changeType(){
	var provideType=$.trim($("#provideType").val());
	if(provideType=='2'){
		$("#businessMan").openComponent_employee({readonly:true});	
		$("#designMan").openComponent_employee({readonly:true});
		$("#busiManName").removeAttr("readonly","readonly");
		$("#busiManPhone").removeAttr("readonly","readonly");
		$("#businessMan").val('');
		$("#openComponent_employee_businessMan").val('');
		$("#designMan").val('');
		$("#openComponent_employee_designMan").val('');
		$('input[name=openComponent_employee_designMan]').css('background','#CCCCCC');
		$('input[name=openComponent_employee_businessMan]').css('background','#CCCCCC');
		$('input[name=busiManPhone]').css('background','#FFFFFF');
		$('input[name=busiManName]').css('background','#FFFFFF');
		$('#dataForm').data('bootstrapValidator') .updateStatus('openComponent_employee_businessMan', 'NOT_VALIDATED', null)
			.validateField('businessMan');	
		$("#dataForm").bootstrapValidator("removeField","openComponent_employee_businessMan");
		
	}else{
		$("#businessMan").openComponent_employee({callBack:validateRefresh_choice} );	
		$("#designMan").openComponent_employee( );
		$("businessMan_li").attr("hidden",true);
		$("businessMan_li").show();
		$("#openComponent_employee_businessMan").removeAttr("readonly",true);
		$("#openComponent_employee_designMan").removeAttr("readonly",true);
		$("#busiManName").attr("readonly","readonly");
		$("#busiManPhone").attr("readonly","readonly");
		$('input[name=openComponent_employee_designMan]').css('background','#FFFFFF');
		$('input[name=openComponent_employee_businessMan]').css('background','#FFFFFF');
		$('input[name=busiManPhone]').css('background','#CCCCCC');
		$('input[name=busiManName]').css('background','#CCCCCC');
		$("#dataForm").bootstrapValidator("addField", "openComponent_employee_businessMan", {  
            validators: {  
            	notEmpty: {  
	           		 message: '业务员不能为空'  ,
	            },
                remote: {
    	            message: '',
    	            url: '${ctx}/admin/employee/getEmployee',
    	            data: getValidateVal,  
    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
    	        }
            }  
        });
	}
}
function validateRefresh_choice(){
	 $('#dataForm').data('bootstrapValidator')
                 .updateStatus('openComponent_activity_actNo', 'NOT_VALIDATED',null)  
                 .validateField('openComponent_activity_actNo')
                 .updateStatus('openComponent_employee_businessMan', 'NOT_VALIDATED',null)  
                 .validateField('openComponent_employee_businessMan'); 
}
function changeTic(){
	var actNo=$.trim($('#actNo').val());
	if(actNo==''){
		actNo='****';
	}
	function getPk(dataPk){
		if(!dataPk) return;
		$("#pk").val(dataPk.PK);
	}
	$("#ticketNo").openComponent_ticketNo({callBack:getPk,condition:{actNo:actNo,notSend:'1'}});	
	
}
</script>
</head>
	<body>
<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="pk" id="pk"  value=""  />
					<input type="hidden" name="hasAuthByCzybh" id="hasAuthByCzybh"  value="${hasAuthByCzybh}"  />
						<ul class="ul-form">
							<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>活动编号</label>
								<input type="text" id="actNo" name="actNo" onchange="changeAct()" style="width:160px;" />
							</li>
							<li>
								<label>状态</label>
								<house:xtdm id="status" dictCode="TICKETSTATUS" value="1" disabled="true"></house:xtdm>                     
							</li>
							</div>
							<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>门票号</label>
								<input type="text" id="ticketNo" name="ticketNo"  onclick="changeTic()" style="width:160px;" />
							</li>
							<li > 
								<label>发放时间</label>
								<input type="text" id="provideDate" name="provideDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${hdmpgl.provideDate}' pattern='yyyy-MM-dd hh:MM:ss'/>" disabled="true"/>
							</li>
							</div>
							<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>业主姓名</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${hdmpgl.descr }"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${hdmpgl.address }"/>
							</li>
							</div>
							<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span> 业主电话</label>
								<input type="text" id="phone" name="phone" style="width:160px;" value="${hdmpgl.phone }"/>
							</li>
							<li> 
								<label>发放操作员</label>
								<input type="text" id="provideCZY" name="provideCZY" style="width:160px;" value="${hdmpgl.provideCZY }"/>
							</li>
							</div>
							<div class="validate-group row" >
							<li>
								<label>发放类型</label>
 								<select id="provideType" name="provideType" onchange="changeType()" style="width: 160px;" >
 									<option value="1">1 有家发放</option>
 									<option value="2">2 供应商发放</option>
 								</select>
							</li>
							<li >
								<label>业务员姓名</label>
								<input type="text" id="busiManName" name="busiManName" style="width:160px;" value="${hdmpgl.busiManName }"/>
							</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate" id="businessMan_li">
									<label><span class="required">*</span>业务员</label>
									<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${hdmpgl.businessMan }"/>
								</li>
								<li > 
									<label>业务员电话</label>
									<input type="text" id="busiManPhone" name="busiManPhone" style="width:160px;" value="${hdmpgl.busiManPhone }"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate"> 
									<label>设计师</label>
									<input type="text" id="designMan" name="designMan" style="width:160px;" value="${hdmpgl.designMan }"/>
								</li>
								<li class="form-validate"> 
									<label><span class="required">*</span>购买品类意向</label>
									<house:xtdmMulit id="planSupplType" dictCode="ACTSPLTYPE" selectedValue="${hdmpgl.planSupplType}"></house:xtdmMulit>                     
								</li>
							</div>
						</ul>	
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>
