<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>${flag=='doc'?'修改档案号':'修改客户'}</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builderNum.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var str_address="";
var str;
function save(){
	function isAddAllInfo(){
		var arr = eval(${custTypeJson});
		for (var i=0;i<arr.length;i++){
			if (arr[i].code==$("#custType").val()){
				if (arr[i].isAddAllInfo=='0'){
					return false;
				}
			}
		}
		return true;
	}
	if ($("#saleType").val()=='2' && $.trim($("#oldCode").val())==''){
		art.dialog({
			content: "请输入完整的信息,原客户号未选！",
			width: 200
		});
		return false;
	}
	if ($("#netChanel").val()=='1' && isAddAllInfo() && $.trim($("#againMan").val())==''){
		art.dialog({
			content: "请输入完整的信息,翻单员未选！",
			width: 200
		});
		return false;
	}
	// 	软装客户，客户来源为网络客户的，需要填翻单员
	if (($("#custType").val()=='2' && isNetCust() && $("#netChanel").val()=='1') && $.trim($("#againMan").val())=='' && $("#netChanel").val()=='1'){
		art.dialog({
			content: "请输入完整的信息,翻单员未选！",
			width: 200
		});
		return false;
	}
	//网络渠道表配置的是否需要翻单员
	if ($("#needAgainMan").val()=="1" && $.trim($("#againMan").val())==''){
		art.dialog({
			content: "请输入完整的信息,翻单员未选！",
			width: 200
		});
		return false;
	}
	if (($("#netChanel").val()=='1' || $("#status").val()=='1') || ($("#custType").val()=='2' && isNetCust() )){
		$('#designMan_star').hide();
		$('#dataForm').data('bootstrapValidator').updateStatus('openComponent_employee_designMan', 'VALID');
	}else{
		$('#designMan_star').show();
		if ($.trim($("#openComponent_employee_designMan").val())==''){
			art.dialog({
				content: "请输入完整的信息,设计师未选！",
				width: 200
			});
			return false;
		}
	}
	if ($("#custType").val()=='2' && isNetCust() ){
		$('#businessMan_star').hide();
		$('#dataForm').data('bootstrapValidator').updateStatus('openComponent_employee_businessMan', 'VALID');
	}else{
		$('#businessMan_star').show();
		if ($.trim($("#openComponent_employee_businessMan").val())==''){
			art.dialog({
				content: "请输入完整的信息,业务员未选！",
				width: 200
			});
			return false;
		}
	}
	$("#openComponent_employee_againMan").trigger("blur");
	$("#openComponent_employee_designMan").trigger("blur");
	
	//$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	$("#source").removeAttr("disabled");
	$("#layout").removeAttr("disabled");
	$("#saleType").removeAttr("disabled");
	$("#custType").removeAttr("disabled");
	$("#crtDate").removeAttr("disabled");
	$("#status").removeAttr("disabled");
	$("#constructType").removeAttr("disabled");
	$("#netChanel").removeAttr("disabled");
	
	var builderNumSave=$.trim($("#openComponent_builderNum_builderNum").val());
	var builderNumSaveArr=builderNumSave.split("#");
	if(builderNumSave!=""&&builderNumSaveArr.length<2){
		$("#builderNum").val(builderNumSave+"#");
	}
	if("${customer.expired}"=="F" && $("#expired").val()=="T"){
		var result=hasPay($("#code").val());
		if(result=="1"){
			art.dialog({
				content: "存在客户付款，不允许过期！",
			});
			return false;
		}
	}
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/customer/doUpdate?flag=${flag}',
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
	    		$("#source").attr("disabled",true);
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
	$("#dataForm").bootstrapValidator({
        message : 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
        },
        fields: {
       	saleType: {
	        validators: { 
	            notEmpty: { 
	            	message: '销售类型不能为空'  
	            }
	        }
      	},
      	custType: {
	        validators: { 
	            notEmpty: { 
	            	message: '客户类型不能为空'  
	            }
	        }
      	},
      	descr: {
	        validators: { 
	            notEmpty: { 
	            	message: '客户名称不能为空'  
	            },
	            stringLength:{
               	 	min: 0,
          			max: 60,
               		message:'客户名称长度必须在0-60之间' 
               	}
	        }
      	},
      	source: {
	        validators: { 
	            notEmpty: { 
	            	message: '客户来源不能为空'  
	            }
	        }
      	},
      	netChanel: {
	        validators: { 
	            notEmpty: { 
	            	message: '渠道不能为空'  
	            }
	        }
      	},
      	gender: {
	        validators: { 
	            notEmpty: { 
	            	message: '性别不能为空'  
	            }
	        }
      	},
      	area: {
	        validators: { 
	            notEmpty: { 
	            	message: '面积不能为空'  
	            },
	            digits: { 
	            	message: '面积只能输入数字'  
	            },
	            between:{
               	 	min: '1',
          			max: '9999',
               		message:'面积必须在1-9999之间的数值' 
               	}
	        }
      	},
      	address: {
	        validators: { 
	            notEmpty: { 
	            	message: '楼盘不能为空'  
	            },
	            stringLength:{
               	 	min: 0,
          			max: 200,
               		message:'楼盘长度必须在0-200之间' 
               	}
	        }
      	},
      	mobile1: {
	        validators: { 
	            notEmpty: { 
	            	message: '手机号码1不能为空'  
	            },
	            digits: { 
	            	message: '手机号码1只能输入数字'  
	            },
	            stringLength:{
               	 	min: 0,
          			max: 20,
               		message:'手机号码1长度必须在0-20之间' 
               	}
	        }
      	},
      	layout: {
	        validators: { 
	            notEmpty: { 
	            	message: '户型不能为空'  
	            }
	        }
      	},
      	mobile2: {
	        validators: { 
	            digits: { 
	            	message: '手机号码2只能输入数字'  
	            },
	            stringLength:{
               	 	min: 0,
          			max: 20,
               		message:'手机号码2长度必须在0-20之间' 
               	}
	        }
      	},
      	qq: {
	        validators: { 
	            digits: { 
	            	message: 'qq只能输入数字'  
	            },
	            stringLength:{
               	 	min: 0,
          			max: 20,
               		message:'qq长度必须在0-20之间' 
               	}
	        }
      	},
      	status: {
	        validators: { 
	            notEmpty: { 
	            	message: '客户状态不能为空'  
	            }
	        }
      	},
      	email2: {
	        validators: { 
                stringLength:{
               	 	min: 0,
          			max: 50,
               		message:'Email/微信长度必须在0-50之间' 
               	}
	        }
      	},
      	constructType: {
	        validators: { 
	            notEmpty: { 
	            	message: '施工方式不能为空'  
	            }
	        }
      	},
      	planAmount: {
	        validators: { 
	            numeric: {
	            	message: '意向金额只能是数字' 
	            }
	        }
      	},
      	remarks: {
	        validators: { 
	        	stringLength:{
               	 	min: 0,
          			max: 200,
               		message:'备注长度必须在0-200之间' 
               	}
	        }
      	},
      	comeTimes: {
	        validators: { 
	        	digits: { 
	            	message: '到店次数只能输入数字'  
	            }
	        }
      	}
      	},
        submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    }).on('success.form.bv', function (e) {
   		 e.preventDefault();
   		 save();
	}).on('error.form.bv', function (e) {
		var str = $("small[data-bv-result='INVALID']").html();
		if (str){
			art.dialog({
				content: str,
				width: 200
			});
		}
	});
			
		changeNetChanel();
		var builderNumString=$.trim($("#builderNum").val());
		var addressString=$.trim($("#address").val());
		var doolNum=$.trim($("#address").val()).split(builderNumString);
		if(doolNum.length<=1){
			doolNum.push("");
		}
		function getBuilderNum(data){
			var builderCode=$.trim($("#builderCode").val());
			var builderNum=$.trim($("#builderNum").val());
			$.ajax({
				url:'${ctx}/admin/customer/getIsDelivBuilder',
				type: 'post',
				data: {builderCode:builderCode},
				dataType: 'text',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			        if("true"==obj){
						$.ajax({
							url:'${ctx}/admin/customer/getIsExistBuilderNum',
							type: 'post',
							data: {builderCode:builderCode,builderNum:builderNum},
							dataType: 'text',
							cache: false,
							error: function(obj){
								showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
						    },
						    success: function(obj){
						        if("true"!=obj){
						        	var arr=builderNum.split("#");
									if(arr.length=='2'&&arr[1]!=""){
										art.dialog({
											content:"房号请在楼盘位置录入，不要录在楼号里",
										});
										return;
									}else{
										art.dialog({
											content:"该楼栋号不存在",
										});
									}
					    		}	
						    }	
						});
		    		}	
			    }	
			 });
			validateRefresh('openComponent_builderNum_builderNum');
			if("${customer.builderNum}"!=""){
				var builderDescr=data["builderdescr"];	
				 if(data["BuilderNum"]!=null){
					$("#address").val($("#openComponent_builder_builderCode").val().split("|")[1]+data["BuilderNum"]+doolNum[1]);
					str_address=$("#openComponent_builder_builderCode").val().split("|")[1]+data["BuilderNum"]+doolNum[1];
					//str[1]=data["builderdescr"]; 
				}else{
					if(data["builderNum"].split("#").length<2){
						$("#address").val($("#openComponent_builder_builderCode").val().split("|")[1]+data["builderNum"]+"#"+doolNum[1]);
						str_address=$("#openComponent_builder_builderCode").val().split("|")[1]+data["builderNum"]+"#"+doolNum[1];
						
					}else{
						$("#address").val($("#openComponent_builder_builderCode").val().split("|")[1]+data["builderNum"]+doolNum[1]);
						str_address=$("#openComponent_builder_builderCode").val().split("|")[1]+data["builderNum"]+doolNum[1];
					}
				} 
			} 
		}
/* 	if (!${hasAddress}){//楼栋号权限控制改成跟楼盘一样 20200628 by cjg
		$("#builderNum").openComponent_builderNum({condition:{builderCode:"${customer.builderCode}"},showValue:"${customer.builderNum}",callBack:getBuilderNum,readonly:true});
	}else{
		$("#builderNum").openComponent_builderNum({condition:{builderCode:"${customer.builderCode}"},showValue:"${customer.builderNum}",callBack:getBuilderNum});
	} */
	//客户来源，渠道联动
	Global.LinkSelect.initSelect("${ctx}/admin/ResrCust/sourceByAuthority","source","netChanel",null,false,true,false,true);
	Global.LinkSelect.setSelect({firstSelect:'source',
						firstValue:'${customer.source}',
						secondSelect:'netChanel',
						secondValue:'${customer.netChanel}'
						});
	(function isDeliveBuilder(){
		$.ajax({
			url:'${ctx}/admin/customer/getIsDelivBuilder',
			type: 'post',
			data: {builderCode:"${customer.builderCode}"},
			dataType: 'text',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if("true"==obj){
		    		$("#dataForm").bootstrapValidator("addField", "openComponent_builderNum_builderNum", {  
			            validators: {  
			                notEmpty: {  
			                    message: '楼栋号不能为空'  
			                },
			            }  
			        });
		    	}else{
					$("#dataForm").bootstrapValidator("addField", "openComponent_builderNum_builderNum", {  
			            validators: {  
			               
			            }  
			        });	
			        $("#dataForm").bootstrapValidator("removeField","openComponent_builderNum_builderNum");
			        $("#dataForm").bootstrapValidator("addField", "openComponent_builderNum_builderNum", {  
			            validators: {  													

			            }  
			        });
		    	}	
		    }	
		 });
	})();
	
	$("#builderCode").openComponent_builder({showLabel:"${customer.builderCodeDescr}",showValue:"${customer.builderCode}",
		callBack:function(data){validateRefresh('openComponent_builder_builderCode');isDelivBuilder(data["code"])}});
	if ($.trim('${customer.designMan}')){
		$("#designMan").openComponent_employee({showLabel:"${customer.designManDescr}",showValue:"${customer.designMan}",disabled:true,
			callBack:function(){validateRefresh('openComponent_employee_designMan');}});
	}else{
		$("#designMan").openComponent_employee({showLabel:"${customer.designManDescr}",showValue:"${customer.designMan}",
			callBack:function(){validateRefresh('openComponent_employee_designMan');}});
	}
	$("#businessMan").openComponent_employee({showLabel:"${customer.businessManDescr}",showValue:"${customer.businessMan}",disabled:true,
		callBack:function(){validateRefresh('openComponent_employee_businessMan');}});
	$("#againMan").openComponent_employee({showLabel:"${customer.againManDescr}",showValue:"${customer.againMan}",disabled:true,
		callBack:function(){validateRefresh('openComponent_employee_againMan');}});
	$("#oldCode").openComponent_customer({showLabel:"${customer.oldCodeDescr}",showValue:"${customer.oldCode}",condition:{ignoreCustRight:"1"},
		callBack: fillData});
	$("#dataForm").bootstrapValidator("addField", "openComponent_builder_builderCode", {
        validators: {
            notEmpty: {
                message: '项目名称不能为空'
            },
            remote: {
	            message: '',
	            url: '${ctx}/admin/builder/getBuilder',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
	$("#dataForm").bootstrapValidator("addField", "openComponent_employee_designMan", {
        validators: {
            remote: {
	            message: '',
	            url: '${ctx}/admin/employee/getEmployee',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
	$("#dataForm").bootstrapValidator("addField", "openComponent_employee_businessMan", {
        validators: {
            remote: {
	            message: '',
	            url: '${ctx}/admin/employee/getEmployee',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
	$("#dataForm").bootstrapValidator("addField", "openComponent_employee_againMan", {
        validators: {
            remote: {
	            message: '',
	            url: '${ctx}/admin/employee/getEmployee',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
	$("#dataForm").bootstrapValidator("addField", "openComponent_customer_oldCode", {  
        validators: {  
            remote: {
	            message: '',
	            url: '${ctx}/admin/customer/getCustomer',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }  
    });
	if ('${czybm.jslx}'=='ADMIN'){
		$("#crtDate").removeAttr("disabled");
	}else{
		$("#crtDate").attr("disabled",true);
	}
	$("#edtAgainMan").attr("readonly",true);
	$("#btnAgainMan").attr("disabled",true);
	$("#saleType").attr("disabled",true);
	if ('${czybm.saleType}'!='0'){
		$("#saleType").val('${czybm.saleType}');
	}
	if ('${customer.documentNo}'=='' && '${flag}'=='doc'){
		$("#documentNo").val(new Date().getYear());
	}
	changeSaleType();
	$("#againMan").openComponent_employee({showLabel:"${customer.againManDescr}",showValue:"${customer.againMan}"});
	if (!${hasAddress}){
		if (${hasBaseItemPlan} || ${hasItemPlan}){
			$("#custType").attr("disabled",true);
		}
		if ('${customer.status}'=='4' || '${customer.status}'=='5'){
			$("#descr").attr("readonly",true);
			$("#area").attr("readonly",true);
			$("#remarks").attr("readonly",true);
			if ('${customer.status}'=='4'){
				$("#constructType").attr("disabled",true);
				$("#source").attr("disabled",true);
				$("#netChanel").attr("disabled",true);
			}
		}
		if (${hasCustPay}){
			$("#address").attr("readonly",true);
			$("#builderCode").openComponent_builder({callBack:function(data){isDelivBuilder(data["code"])},showLabel:"${customer.builderCodeDescr}",showValue:"${customer.builderCode}",disabled:true});
			$("#oldCode").openComponent_customer({showLabel:"${customer.oldCodeDescr}",showValue:"${customer.oldCode}",disabled:true});
			$("#builderNum").openComponent_builderNum({condition:{builderCode:"${customer.builderCode}"},showValue:"${customer.builderNum}",disabled:true});
		}
	}
	if ($("#netChanel").val()=='1'){
		$(".againMan_show").show();
		/* if ('${czybm.custRight}'!='2' && '${czybm.custRight}'!='3'){ //翻单员不控制查看客户权限 20200628 by cjg
			$("#againMan").openComponent_employee({disabled:true});
		} */
		if (!${hasSwt}){
			if ($("#designMan").val()!=''){
				$("#designMan").openComponent_employee({disabled:true});
			}
		}
	}
	//软装客户，客户来源为网络客户的，需要填翻单员。业务员和设计师由后续跟单员填写
	if ($("#custType").val()=='2' && isNetCust() && $("#netChanel").val()=='1'){
		$('.againMan_show').show();
		$('.againMan_stat').show();
		$('#businessMan_star').hide();
		$('#designMan_star').hide();
		$("#businessMan").openComponent_employee({disabled:false});
	}
	if ($("#source").val()=='6' & $.trim($("#hasAgainMan").val())=='1' ){
		$('.againMan_show').show();
		$('.againMan_stat').show();
		$("#businessMan").openComponent_employee({disabled:false});
	}
	if($("#custType").val()=='2' && $("#custType").val()=='2'){
		$('.againMan_show').show();
	}
	if ('${customer.status}'=='1' || '${customer.status}'=='2'){
		$("#status").removeAttr("disabled");
	}else{
		$("#status").attr("disabled",true);
	}
	if ('${czybm.custType}'!='0'){
		$("#custType").attr("disabled",true);
	}
	if ($("#openComponent_employee_businessMan").val()==''){
		$("#businessMan").openComponent_employee({disabled:false});
	}else{
		$("#businessMan").openComponent_employee({disabled:true});
	}
	$("#againMan").openComponent_employee({disabled:true});
	changeStatus();
	if("${customer.mtCustInfoPK}"!=""){
		$("#source").attr("disabled",true);
	}
});
function showAgainMan(){
	//商务通 || (软装客户 && 网络客户 && 商务通) || (工程转单 && 是否需填翻单员)
	function isAddAllInfo(){
		var arr = eval(${custTypeJson});
		for (var i=0;i<arr.length;i++){
			if (arr[i].code==$("#custType").val()){
				if (arr[i].isAddAllInfo=='0'){
					return false;
				}
			}
		}
		return true;
	}
	if (($('#netChanel').val()=='1' && isAddAllInfo()) || 
		 ($('#custType').val()=='2' && isNetCust() && $('#netChanel').val()=='1') 
			|| (($('#source').val()=='6') && $.trim($("#hasAgainMan").val())=='1')|| $("#needAgainMan").val()=="1"){
			
		$('.againMan_show').show();
		$('.againMan_stat').show();
		$("#dataForm").bootstrapValidator("removeField","openComponent_employee_againMan");
		$("#dataForm").bootstrapValidator("addField", "openComponent_employee_againMan", {  
            validators: {  
                notEmpty: {  
                    message: '翻单员不能为空'  
                },
                remote: {
    	            message: '',
    	            url: '${ctx}/admin/employee/getEmployee',
    	            data: getValidateVal,  
    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
    	        }
            }  
        });
	}else if($("#needAgainMan").val()=="2" || !isAddAllInfo()){
		$('.againMan_show').show();
		$("#againMan").val('');
		$("#dataForm").bootstrapValidator("removeField","openComponent_employee_againMan");
		$("#dataForm").bootstrapValidator("addField", "openComponent_employee_againMan", {  
            validators: {  
                remote: {
    	            message: '',
    	            url: '${ctx}/admin/employee/getEmployee',
    	            data: getValidateVal,  
    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
    	        }
            }  
        });
	}
	else{
		$('.againMan_show').hide();
		$("#againMan").val('');
		$("#dataForm").bootstrapValidator("removeField","openComponent_employee_againMan");
		$("#dataForm").bootstrapValidator("addField", "openComponent_employee_againMan", {  
            validators: {  
                remote: {
    	            message: '',
    	            url: '${ctx}/admin/employee/getEmployee',
    	            data: getValidateVal,  
    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
    	        }
            }  
        });
	}
	/*
	if ($('#netChanel').val()=='1'){
		$('.againMan_show').show();
		$('.againMan_stat').show();
		$("#dataForm").bootstrapValidator("removeField","openComponent_employee_againMan");
		$("#dataForm").bootstrapValidator("addField", "openComponent_employee_againMan", {  
            validators: {  
                notEmpty: {  
                    message: '翻单员不能为空'  
                },
                remote: {
    	            message: '',
    	            url: '${ctx}/admin/employee/getEmployee',
    	            data: getValidateVal,  
    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
    	        }
            }  
        });
	}else{
		if ($('#saleType').val()=='1' && $('#source').val()=='6'){
			var arr = eval(${custTypeJson});
			for (var i=0;i<arr.length;i++){
				if (arr[i].code==$("#custType").val()){
					if (arr[i].isAddAllInfo=='0'){
						$('.againMan_show').show();
						$('.againMan_stat').hide();
					}else{
						$('.againMan_show').hide();
						$("#againMan").val('');
						$("#dataForm").bootstrapValidator("removeField","openComponent_employee_againMan");
						$("#dataForm").bootstrapValidator("addField", "openComponent_employee_againMan", {  
				            validators: {  
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
			}
		}else{
			if (($('#custType').val()=='2' && $('#source').val()=='3') || $('#source').val()=='6'){
				$('.againMan_show').show();
				$('.againMan_stat').show();
				$("#dataForm").bootstrapValidator("removeField","openComponent_employee_againMan");
				$("#dataForm").bootstrapValidator("addField", "openComponent_employee_againMan", {  
		            validators: {  
		                notEmpty: {  
		                    message: '翻单员不能为空'  
		                },
		                remote: {
		    	            message: '',
		    	            url: '${ctx}/admin/employee/getEmployee',
		    	            data: getValidateVal,  
		    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
		    	        }
		            }  
		        });
			}else{
				$('.againMan_show').hide();
				$("#againMan").val('');
				$("#dataForm").bootstrapValidator("removeField","openComponent_employee_againMan");
				$("#dataForm").bootstrapValidator("addField", "openComponent_employee_againMan", {  
		            validators: {  
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
	}
	*/
}
var clickSource=0;
function changeSource(){
	clickSource++;
	if(clickSource<=5){//下拉组件会自动触发5次，前5次不执行这个方法
		return;
	}
	var hasAgainMan=$.trim($("#hasAgainMan").val());

	if ($("#source").val()=='9' && "${customer.mtCustInfoPK}"!="" ){
		$("#netChanel").attr("disabled",true);
	}else{
		$("#netChanel").removeAttr("disabled");
	}
	//软装客户，客户来源为网络客户的，需要填翻单员。业务员和设计师由后续跟单员填写
	if ($("#custType").val()=='2' && isNetCust() && $("#netChanel").val()=='1' || $("#needAgainMan").val()=='1'){
		$('.againMan_stat').show();
		if ($("#openComponent_employee_againMan").val()==''){
			$("#againMan").openComponent_employee({showLabel:"${czybm.zwxm}",showValue:"${czybm.czybh}"});
		}
		$('#businessMan_star').hide();
		$('#designMan_star').hide();
	}else{
		//客户类型为软装客户时，增加翻单员录入，非必填。 modify by zb on 20191111
		// if($("#saleType").val().trim()=="2"){
			if($("#custType").val().trim()=="2"){
				$(".againMan_show").show();
			}else{
				$(".againMan_show").hide();
				showAgainMan();
			}
		// }
		$('.againMan_stat').hide();
		$("#openComponent_employee_againMan").val('');
		$("#againMan").val('');
		$('#businessMan_star').show();
		$('#designMan_star').show();
	}
	if ($("#source").val()=='6' && hasAgainMan=='1'){
			$('.againMan_show').show();
			$('.againMan_stat').show();
			//$("#businessMan").openComponent_employee({disabled:false});
	}
}
function updateAddress(data){
	var builderDescr=data["builderdescr"];	
	$("#address").val(builderDescr+data["BuilderCode"]);
	str_address=builderDescr+data["BuilderCode"];
	str[1]=data["builderdescr"];
	validateRefreshBuilderNum();
}
function fillData(data){
	if ($("#saleType").val()=='2' && $.trim($("#custType").val())==''){
		art.dialog({
			content: "请先选择客户类型！",
			width: 200
		});
		$("#oldCode").val("");
		$("#openComponent_customer_oldCode").val("");
		return false;
	}
	$("#builderCode").openComponent_builder({showLabel:data["buildercodedescr"],showValue:data["buildercode"],disabled:true});
	var doorNum=data["address"].split(data["buildercodedescr"]);
	var builderCode=$.trim($("#builderCode").val());
	var builderNumStr=$.trim($("#builderNum").val());
	var addressStr=$.trim($("#address").val());
	
	$("#builderNum").openComponent_builderNum({callBack:updateAddress,showValue:data["buildernum"]});
	isDelivBuilder(data["buildercode"]);
	var arr = eval(${custTypeJson});
	$("#address").val(data["address"]);
	$("#addressString").val(data["address"]);
	$("#builderNum").val(data["buildernum"]);
	for (var i=0;i<arr.length;i++){
		if (arr[i].code==$("#custType").val()){
			if (arr[i].isAddAllInfo=='0'){
				$("#address").val(data["address"]+arr[i].desc1);
			}
		}
	}
	$("#layout").val(data["layout"]);
	$("#area").val(data["area"]);
	validateRefresh('openComponent_customer_oldCode');
	validateRefresh('address');
	validateRefresh('area');
	validateRefresh("layout");
	validateRefresh("openComponent_builder_builderCode");
}
function changeSaleType(){
	if ($("#saleType").val()=='2'){
		$("#oldCode").openComponent_customer({showLabel:"${customer.oldCodeDescr}",showValue:"${customer.oldCode}",
			condition:{status:'4,5',ignoreCustRight:"1"},callBack: fillData});
		$("#openComponent_customer_oldCode").trigger("blur");
		$("#dataForm").bootstrapValidator("removeField","openComponent_customer_oldCode");
		$("#dataForm").bootstrapValidator("addField", "openComponent_customer_oldCode", {  
            validators: {  
                notEmpty: {  
                    message: '原客户编号不能为空'  
                },
                remote: {
    	            message: '',
    	            url: '${ctx}/admin/customer/getCustomer',
    	            data: getValidateVal,  
    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
    	        }
            }  
        });
		$("#oldCode_star").show();
		$("#address").attr("readonly",false);
		$("#layout").attr("disabled",true);
		$("#area").attr("readonly",true);
		$("#builderCode").openComponent_builder({showLabel:"${customer.builderCodeDescr}",showValue:"${customer.builderCode}",disabled:true});
	}else{
		$("#oldCode").openComponent_customer({showLabel:"${customer.oldCodeDescr}",showValue:"${customer.oldCode}",disabled:true});
		$("#oldCode").val('');
		$("#openComponent_customer_oldCode").val('');
		$("#dataForm").bootstrapValidator("removeField","openComponent_customer_oldCode");
		$("#dataForm").bootstrapValidator("addField", "openComponent_customer_oldCode", {  
            validators: {  
                remote: {
    	            message: '',
    	            url: '${ctx}/admin/customer/getCustomer',
    	            data: getValidateVal,  
    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
    	        }
            }  
        });
		$("#openComponent_customer_oldCode").trigger("blur");
		$("#oldCode_star").hide();
		$("#address").removeAttr("readonly");
		$("#layout").removeAttr("disabled");
		$("#area").removeAttr("readonly");
		var str_builderCode = $("#openComponent_builder_builderCode").val();
		str_address = $("#address").val();
		str = str_builderCode.split("|");
		$("#builderCode").openComponent_builder({showLabel:"${customer.builderCodeDescr}",showValue:"${customer.builderCode}",
				callBack: function(data){
					if ($("#address").val()){
						$("#address").val(str_address.replace(str[1],data["descr"]));
					}else{
						$("#address").val(data["descr"]);
					}
					isDelivBuilder(data["code"]);
					validateRefresh('openComponent_builder_builderCode');
					validateRefresh('address');
				}});
	}
	changeSource();
}
function changeCustType(){
	changeSource();
	var arr = eval(${custTypeJson});
	console.log(arr);
	for (var i=0;i<arr.length;i++){
		if (arr[i].code==$("#custType").val()){
			if (arr[i].isAddAllInfo=='0'){
				$("#layout").val('0');
				//$("#area").val('0');
				//$("#status").val('2');
				$("#constructType").val('1');
			}
		}
	}
	//$("#openComponent_customer_oldCode").trigger("blur");
}

var clickNetChanel=0;
function changeNetChanel(){
	clickNetChanel++;
	if(clickNetChanel<=7){//下拉组件会自动触发7次，前7次不执行这个方法
		return;
	}
	isNeedAgainMan($("#netChanel").val());
	if ($("#netChanel").val()=='1'){
		if (!${hasSwt}){
			art.dialog({
				content: "您没有商务通的权限！",
				width: 200
			});
			$("#netChanel").val('').focus();
			$("#againMan").openComponent_employee();
			showAgainMan();
			if ('${czybm.custRight}'!='2' && '${czybm.custRight}'!='3'){
				$("#againMan").openComponent_employee({disabled: false});
			}
			return false;
		}
		if ($("#openComponent_employee_againMan").val()==''){
			$("#againMan").openComponent_employee({showValue:'${czybm.czybh}'});
		}
		showAgainMan();
		if ('${czybm.custRight}'!='2' && '${czybm.custRight}'!='3'){
			$("#againMan").openComponent_employee({disabled: true});
		}
		$("#dataForm").bootstrapValidator("removeField","openComponent_employee_designMan");
		$("#dataForm").bootstrapValidator("addField", "openComponent_employee_designMan", {  
            validators: {  
                remote: {
    	            message: '',
    	            url: '${ctx}/admin/employee/getEmployee',
    	            data: getValidateVal,  
    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
    	        }
            }  
        });
		$('#designMan_star').hide();
	}else{
		$("#againMan").openComponent_employee();
		showAgainMan();
		$("#dataForm").bootstrapValidator("removeField","openComponent_employee_designMan");
		$("#dataForm").bootstrapValidator("addField", "openComponent_employee_designMan", {  
            validators: {  
                notEmpty: {  
                    message: '设计师不能为空'  
                },
                remote: {
    	            message: '',
    	            url: '${ctx}/admin/employee/getEmployee',
    	            data: getValidateVal,  
    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
    	        }
            }  
        });
		$('#designMan_star').show();
		//软装客户，客户来源为网络客户的，需要填翻单员。业务员和设计师由后续跟单员填写
		if ($("#custType").val()=='2' && isNetCust() && $("#netChanel").val()=='1'){
			$('.againMan_stat').show();
			if ($("#openComponent_employee_againMan").val()==''){
				$("#againMan").openComponent_employee({showLabel:"${czybm.zwxm}",showValue:"${czybm.czybh}"});
			}
			$('#businessMan_star').hide();
			$('#designMan_star').hide();
		}else if($("#needAgainMan").val()=="1"){
			$('.againMan_stat').show();
		}else{
			$('.againMan_stat').hide();
			$("#openComponent_employee_againMan").val('');
			$("#againMan").val('');
			$('#businessMan_star').show();
			$('#designMan_star').show();
		}
	}
}
function changeStatus(){
	if ($("#status").val()=='1'){
		$('#designMan_star').hide();
		$("#dataForm").bootstrapValidator("removeField","openComponent_employee_designMan");
		$("#dataForm").bootstrapValidator("addField", "openComponent_employee_designMan", {  
            validators: {  
                remote: {
    	            message: '',
    	            url: '${ctx}/admin/employee/getEmployee',
    	            data: getValidateVal,  
    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
    	        }
            }  
        });
	}else{
		$('#designMan_star').show();
		$("#dataForm").bootstrapValidator("removeField","openComponent_employee_designMan");
		$("#dataForm").bootstrapValidator("addField", "openComponent_employee_designMan", {  
            validators: {  
                notEmpty: {  
                    message: '设计师不能为空'  
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
function isDelivBuilder(builderCode){
	var builderCode=$.trim($("#builderCode").val());
	var builderNumStr=$.trim($("#builderNum").val());
	var addressStr=$.trim($("#address").val());
	function getBuilderNum(data){
		var builderCode=$.trim($("#builderCode").val());
		var builderNum=$.trim($("#builderNum").val());
		$.ajax({
			url:'${ctx}/admin/customer/getIsDelivBuilder',
			type: 'post',
			data: {builderCode:builderCode},
			dataType: 'text',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		        if("true"==obj){
					$.ajax({
						url:'${ctx}/admin/customer/getIsExistBuilderNum',
						type: 'post',
						data: {builderCode:builderCode,builderNum:builderNum},
						dataType: 'text',
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
					    },
					    success: function(obj){
					        if("true"!=obj){
					        	var arr=builderNum.split("#");
								if(arr.length=='2'&&arr[1]!=""){
									art.dialog({
										content:"房号请在楼盘位置录入，不要录在楼号里",
									});
									return;
								}else{
									art.dialog({
										content:"该楼栋号不存在",
									});
								}
				    		}	
					    }	
					});
	    		}	
		    }	
		});
		if("${customer.builderNum}"!=""){
			var builderDescr=data["builderdescr"];	
			$("#address").val(addressStr.replace(builderNumStr,data["BuilderNum"]));
			str_address=addressStr.replace(builderNumStr,data["BuilderNum"]);
			str[1]=data["builderdescr"];
		}
			validateRefreshBuilderNum();
	}
	$("#builderNum").openComponent_builderNum({condition:{builderCode:builderCode},
			callBack:getBuilderNum});
	
	$.ajax({
		url:'${ctx}/admin/customer/getIsDelivBuilder',
		type: 'post',
		data: {builderCode:builderCode},
		dataType: 'text',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if("true"==obj){
	    		$("#dataForm").bootstrapValidator("addField", "openComponent_builderNum_builderNum", {  
		            validators: {  
		                notEmpty: {  
		                    message: '楼栋号不能为空'  
		                },
		            }  
		        });
	    	}else{
				$("#dataForm").bootstrapValidator("addField", "openComponent_builderNum_builderNum", {  
		            validators: {  
		               
		            }  
		        });	
		        $("#dataForm").bootstrapValidator("removeField","openComponent_builderNum_builderNum");
		        $("#dataForm").bootstrapValidator("addField", "openComponent_builderNum_builderNum", {  
		            validators: {  													

		            }  
		        });	
	    	}	
	    }	
	 });
}
function validateRefreshBuilderNum(){
		$('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_builderNum_builderNum', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_builderNum_builderNum') ;
}
function isNeedAgainMan(code){
	$.ajax({
		url:'${ctx}/admin/customer/isNeedAgainMan',
		type: 'post',
		data: {code:code},
		dataType: 'json',
		cache: false,
		async:false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
			$("#needAgainMan").val(obj.NeedAgainMan);
	    }	
	 });
}
function hasPay(code){
	var result="";
	$.ajax({
		url:'${ctx}/admin/customer/hasPay',
		type: 'post',
		data: {code:code},
		dataType: 'text',
		cache: false,
		async:false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
			result=obj;
	    }	
	 });
	 return result;
}

//是否网络客户
function isNetCust(){
	var source=$("#source").val();
	if(source=="3" || source=="12" || source=="13" || source=="14"){
		return true;
	}
	return false;
}
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="validateDataForm()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<house:token></house:token>
				<input type="hidden" id="expired" name="expired" value="${customer.expired }" />
				<input type="hidden" name="hasAgainMan" id="hasAgainMan" value="${hasAgainMan}"/>
				<input type="hidden" name="needAgainMan" id="needAgainMan"/>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<input type="hidden" name="mtCustInfoPK" id="mtCustInfoPK" value="${customer.mtCustInfoPK}"/>
					<ul class="ul-form">
					<div class="validate-group row">
						<div class="col-sm-4">
						<li class="form-validate">
							<label><span class="required">*</span>客户编号</label>
							<input type="text" style="width:160px;" id="code" name="code" value="${customer.code }" readonly="readonly"/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>销售类型</label>
							<house:xtdm id="saleType" dictCode="SALETYPE" value="${customer.saleType }" onchange="changeSaleType()" disabled="true" unShowValue="0"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required" id="oldCode_star" style="display: none">*</span>原客户号</label>
							<input type="text" id="oldCode" name="oldCode" style="width:160px;" value="${customer.oldCode}" readonly="${flag=='doc'?true:false }"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>客户类型</label>
								<house:dict id="custType" dictCode="" onchange="changeCustType()"
								sql="select code,code+' '+desc1 desc1 from tCustType where expired= 'F' or code = '${customer.custType }' order by dispSeq " 
								sqlValueKey="Code" sqlLableKey="Desc1" value="${customer.custType }" ></house:dict>
						</li>
						</div>
						<div class="col-sm-4">
							<li class="form-validate">
								<label><span class="required">*</span>客户来源</label> 
								<select id="source" name="source" onchange="changeSource()"></select>
							</li>
						</div>
						<div class="col-sm-4">
							<li class="form-validate">
								<label><span class="required">*</span>渠道</label> 
								<select id="netChanel" name="netChanel" onchange="changeNetChanel()"></select>
							</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>项目名称</label>
							<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="${customer.builderCode}" readonly="${flag=='doc'?true:false }"/>
						</li>
						</div>
						<div class="col-sm-4">
							<li class="form-validate">
								<label><span class="required">*</span>楼栋号</label>
								<input type="text" style="width:160px;" id="builderNum" name="builderNum" value="${customer.builderNum }" />
							</li>
						</div>
						<div class="col-sm-4">
						<li class="form-validate">
							<label><span class="required">*</span>楼盘</label>
							<input type="text" style="width:160px;" id="address" name="address" value="${customer.address }" ${flag=='doc'?'readonly':'' }/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-4">
	            			<li class="form-validate">
								<label><span class="required">*</span>户型</label>
								<house:xtdm id="layout" dictCode="LAYOUT" value="${customer.layout }" disabled="${flag=='doc'?true:false }"></house:xtdm>
							</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>面积</label>
							<input type="text" style="width:160px;" id="area" name="area" value="${customer.area }" ${flag=='doc'?'readonly':'' }/>
							<span style="position: absolute;left:290px;width: 30px;top:5px;">平方</span>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required" id="designMan_star">*</span>设计师编号</label>
							<input type="text" id="designMan" name="designMan" style="width:160px;" value="${customer.designMan}" readonly="${flag=='doc'?true:false }"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required" id="businessMan_star">*</span>业务员编号</label>
							<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${customer.businessMan}" readonly="${flag=='doc'?true:false }"/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="againMan_show">
							<label><div><span class="required againMan_stat">*</span>翻单员/导客</div></label>
							<input type="text" id="againMan" name="againMan" style="width:160px;" value="${customer.againMan}" readonly="${flag=='doc'?true:false }"/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>客户名称</label>
							<input type="text" style="width:160px;" id="descr" name="descr" value="${customer.descr }" ${flag=='doc'?'readonly':'' }/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>性别</label>
							<house:xtdm id="gender" dictCode="GENDER" value="${customer.gender }" disabled="${flag=='doc'?true:false }"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>手机号码1</label>
							<c:if test="${czybm.emnum==customer.businessMan or czybm.emnum==customer.designMan}">
							<input type="text" style="width:160px;" id="mobile1" name="mobile1" maxlength="11" value="${customer.mobile1 }" readonly="readonly"/>
							</c:if>
							<c:if test="${czybm.emnum!=customer.businessMan and czybm.emnum!=customer.designMan}">
							<input type="password" style="width:160px;" id="mobile1" name="mobile1" maxlength="11" value="${customer.mobile1 }" readonly="readonly"/>
							</c:if>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>手机号码2</label>
							<c:if test="${czybm.emnum==customer.businessMan or czybm.emnum==customer.designMan}">
							<input type="text" style="width:160px;" id="mobile2" name="mobile2" maxlength="11" value="${customer.mobile2 }" readonly="readonly"/>
							</c:if>
							<c:if test="${czybm.emnum!=customer.businessMan and czybm.emnum!=customer.designMan}">
							<input type="password" style="width:160px;" id="mobile2" name="mobile2" maxlength="11" value="${customer.mobile2 }" readonly="readonly"/>
							</c:if>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>QQ</label>
							<input type="text" style="width:160px;" id="qq" name="qq" value="${customer.qq }" ${flag=='doc'?'readonly':'' }/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>微信/Email</label>
							<input type="text" style="width:160px;" id="email2" name="email2" value="${customer.email2 }" ${flag=='doc'?'readonly':'' }/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>施工方式</label>
							<house:xtdm id="constructType" dictCode="CONSTRUCTTYPE" value="${customer.constructType }" disabled="${flag=='doc'?true:false }"></house:xtdm>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>设计风格</label>
							<house:xtdm id="designStyle" dictCode="DESIGNSTYLE" value="${customer.designStyle }" disabled="${flag=='doc'?true:false }"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>开工时间</label>
							<input type="text" style="width:160px;" id="beginDate" name="beginDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.beginDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>意向金额</label>
							<input type="text" style="width:160px;" id="planAmount" name="planAmount" value="${customer.planAmount }" ${flag=='doc'?'readonly':'' }/>
							<span style="position: absolute;left:285px;width: 30px;top:5px;">元</span>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>到店次数</label>
							<input type="text" style="width:160px;" id="comeTimes" name="comeTimes" value="${customer.comeTimes }"/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>创建时间</label>
							<input type="text" style="width:160px;" id="crtDate" name="crtDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.crtDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly" ${flag=='doc'?'disabled':'' }/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>档案号</label>
							<input type="text" style="width:160px;" id="documentNo" name="documentNo" maxlength="8" value="${customer.documentNo }" ${flag=='doc'?'':'readonly' }/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>客户状态</label>
							<house:xtdm id="status" dictCode="CUSTOMERSTATUS" unShowValue="${unShowValue }" value="${customer.status }" 
							disabled="true" onchange="changeStatus()"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>到店时间</label>
							<input type="text" style="width:160px;" id="visitDate" name="visitDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.visitDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly" ${flag=='doc'?'disabled':'' }/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li style="display: ${customer.status=='1'||customer.status=='2'?'':'none' }">
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${customer.expired }" onclick="checkExpired(this)" ${customer.expired=='T'?'checked':'' } ${flag=='doc'?'disabled':'' }>
						</li>
						</div>
						<div class="col-sm-8">
            			<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" ${flag=='doc'?'readonly':'' }>${customer.remarks }</textarea>
						</li>
						</div>
					</div>
				</ul>
            </form>
         </div>
     </div>
</div>
</body>
</html>
