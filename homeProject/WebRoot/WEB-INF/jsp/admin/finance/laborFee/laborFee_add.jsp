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
	<title>人工费用管理新增明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_workCard.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemSendNo.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
	//责任人类型修改次数
	var clickNum=0;
	$(function() {
		var custCode="1";
		$("#appSendNo").openComponent_itemSendNo({condition:{custCode:custCode,itemType1:$.trim("${laborFee.itemType1}")},readonly:true});	 
		$("#openComponent_itemSendNo_appSendNo").attr("readonly",true);
		
		$("#refCustCode").openComponent_customer({
			showValue:"${laborFee.refCustCode}",
			showLabel:"${laborFee.refCustDescr}",
			condition:{
				status:"4,5",
				disabledEle:"status_NAME"
			},
			callBack:function(data){
				var custCode=$("#custCode").val();
				$("#faultMan").val(data["projectman"]);
				$("#faultManDescr").val(data["projectmandescr"]);
				$("#prjQualityFee").val(data.prjqualityfee);
				$("#projectMan").val(data.projectman);
				$("#projectManDescr").val(data.projectmandescr);
				$("#refCustAddress").val(data.address);
				if($("#faultType").val()=="1"){
					$("#openComponent_worker_faultWorker").val("");
					$("#faultEmp").parent().removeClass("hidden");
					$("#faultWorker").parent().addClass("hidden");
					$("#faultEmp").openComponent_employee({
						showValue:data["projectman"],
						showLabel:data["projectmandescr"],
						condition:{
							isStakeholder:"1",custCode:$("#refCustCode").val()
						},
						callBack:function(obj){
							$("#faultMan").val(obj.number);
							$("#faultManDescr").val(obj.namechi);
							if(obj.prjqualityfee!=""){
								$("#prjQualityFee").parent().removeClass("hidden");
								$("#prjQualityFee").val(obj.prjqualityfee);
							}else{
								$("#prjQualityFee").parent().addClass("hidden");
							}
						}
					});	
					if(data.projectmandescr==""){
						$("#openComponent_employee_faultEmp").val("");
					}	
				}else if($("#faultType").val()=="2"){
					$("#openComponent_employee_faultEmp").val("");
					$("#faultWorker").parent().removeClass("hidden");
					$("#faultEmp").parent().addClass("hidden");
					$("#faultWorker").openComponent_worker({
						showValue:clickNum==1?"${laborFee.faultMan}":"",
						showLabel:clickNum==1?"${laborFee.faultManDescr}":"",
						condition:{
							refCustCode:data.code
						},
						callBack:function(obj){
							$("#faultMan").val(obj.Code);
							$("#faultManDescr").val(obj.NameChi);
						}
					});
				}
			}
   		});
   		changeFaultType();
		function getCustomer(data){
			if(!data) {
				return false;
			}
			$('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_customer_custCode', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_customer_custCode');
			var feeType=$.trim($("#feeType").val());
			if(feeType!=""){
				$.ajax({
					url:"${ctx}/admin/laborFee/getFeeTypeDescr",
					type: "get",
					data:{feeType:feeType},
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				    },
				    success: function(obj){
					if("1"==obj[1]){
						$("#iaNo").val('');
						$('input[name=openComponent_itemSendNo_appSendNo]').css('background','#FFFFFF');
						$("#appSendNo").openComponent_itemSendNo({callBack:getIaNo,condition:{custCode:data.code,itemType1:$.trim("${laborFee.itemType1}")}});	 
						$("#openComponent_itemSendNo_appSendNo").attr("readonly",true);
						$("#dataForm").bootstrapValidator("addField", "openComponent_itemSendNo_appSendNo", {  
				            validators: {  
				                notEmpty: {  
				                    message: '发货单号不能为空'  
				                },
				            }  
				        });
					}else{
						$("#iaNo").val('');
						$("#designMan").val('');
						$("#openComponent_employee_designMan").val('');
						$('input[name=openComponent_itemSendNo_appSendNo]').css('background','#CCCCCC');
						$("#appSendNo").openComponent_itemSendNo({readonly:true});	 
						$('#dataForm').data('bootstrapValidator').updateStatus('openComponent_itemSendNo_appSendNo', 'NOT_VALIDATED', null)
							.validateField('appSendNo');	 
						$("#dataForm").bootstrapValidator("removeField","openComponent_itemSendNo_appSendNo");	
					}
				}
				});
				$.ajax({
					url:"${ctx}/admin/laborFee/getNoHaveAmount",
					type: "get",
					data:{custCode:data.custCode,feeType:feeType,},
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				    },
				    success: function(obj){
						$("#sendNoHaveAmount").val(obj);
					}
				});
			}else{
				$("#appSendNo").openComponent_itemSendNo({readonly:true,callBack:getIaNo,condition:{custCode:custCode,itemType1:$.trim("${laborFee.itemType1}")}});	 
				$("#openComponent_itemSendNo_appSendNo").attr("readonly",true);
			}
			//$("#appSendNo").openComponent_itemSendNo({callBack:getIaNo,condition:{custCode:data.code,itemType1:$.trim("${laborFee.itemType1}")}});	 
			//$("#openComponent_itemSendNo_appSendNo").attr("readonly",true);
			$("#address").val(data.address);
			$("#documentNo").val(data.documentno);
			$("#checkStatus").val(data.checkstatusdescr);
			$("#checkDate").val(data.custcheckdate);
			
			$("#address").val(data.address);
			$("#documentNo").val(data.documentno);
			$("#checkStatus").val(data.checkstatusdescr);
			$("#checkDate").val(data.custcheckdate);
			$("#refCustDescr").val(data.descr);
			if("${AftCustCode}".indexOf(data.code)!=-1){
				$("#refCustCode").parent().removeClass("hidden");
				$("#faultType").parent().parent().removeClass("hidden");
				$("#faultType").val("1");
			}else{
				$("#refCustCode").parent().addClass("hidden");
				$("#refCustCode").val("");
				$("#openComponent_customer_refCustCode").val("");
				$("#faultType").parent().parent().addClass("hidden");
				$("#faultType").val("");
				$("#faultMan").val("");
				$("#faultTypeDescr").val("");
				$("#faultManDescr").val("");
				$("#prjQualityFee").val(0);
			} 
		}
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				feeType:{  
					validators: {  
						notEmpty: {  
							message: '费用类型不能为空'  
						}
					}  
				},
				amount:{  
					validators: {  
						notEmpty: {  
							message: '付款金额不能为空'  
						}
					}  
				},
				openComponent_customer_custCode:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '客户编号不能为空'  
			            }
			        }  
			    },
			    openComponent_itemSendNo_appSendNo:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '领料单号不能为空'  
			            }
			        }  
			    },
			     
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
		
	});

$(function(){
	var custCode="1";
	function getCustomer(data){
		if(!data) {
			return false;
		}
		$('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_customer_custCode', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_customer_custCode')
		var feeType=$.trim($("#feeType").val());
		if(feeType!=""){
			$.ajax({
				url:"${ctx}/admin/laborFee/getFeeTypeDescr",
				type: "get",
				data:{feeType:feeType},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					if("1"==obj[1]){
						$("#iaNo").val('');
						$('input[name=openComponent_itemSendNo_appSendNo]').css('background','#FFFFFF');
						$("#appSendNo").openComponent_itemSendNo({callBack:getIaNo,condition:{custCode:data.code,itemType1:$.trim("${laborFee.itemType1}")}});	 
						$("#openComponent_itemSendNo_appSendNo").attr("readonly",true);
						$("#dataForm").bootstrapValidator("addField", "openComponent_itemSendNo_appSendNo", {  
				            validators: {  
				                notEmpty: {  
				                    message: '发货单号不能为空'  
				                },
				            }  
				        });
					}else{
						$("#iaNo").val('');
						$("#designMan").val('');
						$("#openComponent_employee_designMan").val('');
						$('input[name=openComponent_itemSendNo_appSendNo]').css('background','#CCCCCC');
						$("#appSendNo").openComponent_itemSendNo({readonly:true});	 
				        $('#dataForm').data('bootstrapValidator').updateStatus('openComponent_itemSendNo_appSendNo', 'NOT_VALIDATED', null)
							.validateField('appSendNo');	 
						$("#dataForm").bootstrapValidator("removeField","openComponent_itemSendNo_appSendNo");	
					}
				}
			});
			$.ajax({
				url:"${ctx}/admin/laborFee/getNoHaveAmount",
				type: "get",
				data:{custCode:data.custCode,feeType:feeType,},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					$("#sendNoHaveAmount").val(obj);
				}
			});
		}else{
			$("#appSendNo").openComponent_itemSendNo({readonly:true,callBack:getIaNo,condition:{custCode:custCode,itemType1:$.trim("${laborFee.itemType1}")}});	 
			$("#openComponent_itemSendNo_appSendNo").attr("readonly",true);
		}
		//$("#appSendNo").openComponent_itemSendNo({callBack:getIaNo,condition:{custCode:data.code,itemType1:$.trim("${laborFee.itemType1}")}});	 
		//$("#openComponent_itemSendNo_appSendNo").attr("readonly",true);
		$("#address").val(data.address);
		$("#documentNo").val(data.documentno);
		$("#checkStatus").val(data.checkstatusdescr);
		$("#checkDate").val(data.custcheckdate);
		$("#refCustDescr").val(data.descr);
		$("#regionDescr").val(data.regiondescr);
		if("${AftCustCode}".indexOf(data.code)!=-1){
			$("#refCustCode").parent().removeClass("hidden");
			$("#faultType").parent().parent().removeClass("hidden");
			$("#faultType").val("1");
		}else{
			$("#refCustCode").parent().addClass("hidden");
			$("#refCustCode").val("");
			$("#openComponent_customer_refCustCode").val("");
			$("#faultType").parent().parent().addClass("hidden");
			$("#faultType").val("");
			$("#faultMan").val("");
			$("#faultTypeDescr").val("");
			$("#faultManDescr").val("");
			$("#prjQualityFee").val(0);
		} 
		
		function getIaNo(data){
			if(!data){
				return false;
			}
			$('#dataForm').data('bootstrapValidator')
                 .updateStatus('openComponent_itemSendNo_appSendNo', 'NOT_VALIDATED',null)  
                 .validateField('openComponent_itemSendNo_appSendNo'); 
			$("#iaNo").val(data.iano);
			$("#isSetItem").val(data.issetitem);
			feeType=$.trim($("#feeType").val());
			$.ajax({
				url:"${ctx}/admin/laborFee/getHaveAmount",
				type: "get",
				data:{sendNo:data.no,feeType:feeType},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					$("#haveAmount").val(obj);
				}
			});
		}
	}
	function getWorkCard(data){
		if(!data){
			return false;
		}
		$("#cardID").val(data.CardID);
	} 
	$("#custCode").openComponent_customer({callBack:getCustomer,condition:{laborFeeCustStatus:"1,2,3"}});	
	$("#actName").openComponent_workCard({callBack:getWorkCard});	 
	$("#appSendNo").openComponent_itemSendNo({condition:{custCode:custCode,itemType1:$.trim("${laborFee.itemType1}")},readonly:true});	 
	$("#openComponent_itemSendNo_appSendNo").attr("readonly",true);
	
	$("#saveBtn").on("click",function(){
		if("${AftCustCode}".indexOf($("#custCode").val())!=-1 && $("#refCustCode").val()==""){
			art.dialog({
       			content: "请选择关联客户",
       		});
       		return;
		}
		if("${AftCustCode}".indexOf($("#custCode").val())!=-1 && $("#faultType").val()==""){
			art.dialog({
       			content: "请选择责任人类型",
       		});
       		return;
		}
		if("${AftCustCode}".indexOf($("#custCode").val())!=-1 && $("#faultType").val()!="3" && $("#faultMan").val()==""){
			art.dialog({
       			content: "请选择责任人",
       		});
       		return;
		}
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var no=$.trim($("#appSendNo").val());
		if($.trim($("#actName").val())=="" && $.trim($("#openComponent_workCard_actName").val())!="" ){
			$("#actName").val($.trim($("#openComponent_workCard_actName").val()));
		}
		var selectRows = [];
		var datas=$("#dataForm").jsonForm();	
		$.ajax({
			url:"${ctx}/admin/laborFee/getIsSetItem",
			type: "get",
			data:{no:no},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				if(obj){
					art.dialog({
						content:"送货单为套餐内材料的项目经理已领、未领状态,是否保存!",
					 	lock: true,
					 	width: 200,
					 	height: 100,
					 	ok: function () {
						 	selectRows.push(datas);
							Global.Dialog.returnData = selectRows;
							closeWin();
						},
						cancel: function () {
								return true;
						}
					});		
				}else{
				 	selectRows.push(datas);
					Global.Dialog.returnData = selectRows;
					closeWin();
				}
			}
		});
	}); 
	
});

function getIaNo(data){
	if(!data){
		return false;
	}
	$('#dataForm').data('bootstrapValidator')
                .updateStatus('openComponent_itemSendNo_appSendNo', 'NOT_VALIDATED',null)  
                .validateField('openComponent_itemSendNo_appSendNo'); 
	$("#iaNo").val(data.iano);
	$("#isSetItem").val(data.issetitem);
	feeType=$.trim($("#feeType").val());
	$.ajax({
		url:"${ctx}/admin/laborFee/getHaveAmount",
		type: "get",
		data:{sendNo:data.no,feeType:feeType},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			$("#haveAmount").val(obj);
		}
	});
}

function getFeeTypeDescr(){
	var feeType=$.trim($("#feeType").val());
	var custCode=$.trim($("#custCode").val());
	var appSendNo=$.trim($("#appSendNo").val());
	function getIaNo(data){
		if(!data){
			return false;
		}
		$('#dataForm').data('bootstrapValidator')
                 .updateStatus('openComponent_itemSendNo_appSendNo', 'NOT_VALIDATED',null)  
                 .validateField('openComponent_itemSendNo_appSendNo'); 
		$("#iaNo").val(data.iano);
		$("#isSetItem").val(data.issetitem);
		$.ajax({
			url:"${ctx}/admin/laborFee/getHaveAmount",
			type: "get",
			data:{sendNo:data.no,feeType:feeType},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				$("#haveAmount").val(obj);
			}
		});
	}
	if(appSendNo!=""){
		$.ajax({
			url:"${ctx}/admin/laborFee/getHaveAmount",
			type: "get",
			data:{sendNo:appSendNo,feeType:feeType},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				$("#haveAmount").val(obj);
			}
		});
	}
	$.ajax({
		url:"${ctx}/admin/laborFee/getNoHaveAmount",
		type: "get",
		data:{custCode:custCode,feeType:feeType},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			$("#sendNoHaveAmount").val(obj);
		}
	});
	$.ajax({
		url:"${ctx}/admin/laborFee/getFeeTypeDescr",
		type: "get",
		data:{feeType:feeType},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			$("#feeTypeDescr").val(obj[0]);
			if("1"==obj[1]){
				$("#iaNo").val('');
				$('input[name=openComponent_itemSendNo_appSendNo]').css('background','#FFFFFF');
				$("#appSendNo").openComponent_itemSendNo({callBack:getIaNo,condition:{custCode:custCode,itemType1:$.trim("${laborFee.itemType1}")}});	 
				$("#openComponent_itemSendNo_appSendNo").attr("readonly",true);
				$("#dataForm").bootstrapValidator("addField", "openComponent_itemSendNo_appSendNo", {  
		            validators: {  
		                notEmpty: {  
		                    message: '发货单号不能为空'  
		                },
		            }  
		        });
			
			}else{
				$("#iaNo").val('');
				$('input[name=openComponent_itemSendNo_appSendNo]').css('background','#CCCCCC');
				$("#appSendNo").openComponent_itemSendNo({readonly:true});	
		        $('#dataForm').data('bootstrapValidator').updateStatus('openComponent_itemSendNo_appSendNo', 'NOT_VALIDATED', null)
					.validateField('appSendNo');	 
				$("#dataForm").bootstrapValidator("removeField","openComponent_itemSendNo_appSendNo");
			}
		}
	});
}

/* function validateRefresh(){
		$('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_itemSendNo_appSendNo', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_itemSendNo_appSendNo')
                    .updateStatus('crtDate', 'NOT_VALIDATED',null)  
                    .validateField('crtDate')  ;
} */

function validateRefresh_choice(){
	 $('#dataForm').data('bootstrapValidator')
                 .updateStatus('openComponent_itemSendNo_appSendNo', 'NOT_VALIDATED',null)  
                 .validateField('openComponent_itemSendNo_appSendNo');                    
}
function fillData(ret){
	validateRefresh_choice();
}
function changeFaultType(){
	clickNum++;
	var faultType=$("#faultType").val();
	if(clickNum!=1){
		$("#faultMan").val(faultType=="1"?$("#projectMan").val():"");
		$("#faultManDescr").val(faultType=="1"?$("#projectManDescr").val():"");
	}
	if(faultType=="1"){
		$("#openComponent_worker_faultWorker").val("");
		$("#faultEmp").parent().removeClass("hidden");
		$("#faultWorker").parent().addClass("hidden");
		$("#faultEmp").openComponent_employee({
			showValue:clickNum==1?"${laborFee.faultMan}":$("#projectMan").val(),
			showLabel:clickNum==1?"${laborFee.faultManDescr}":$("#projectManDescr").val(),
			condition:{
				isStakeholder:"1",custCode:$("#refCustCode").val()
			},
			callBack:function(data){
				$("#faultMan").val(data.number);
				$("#faultManDescr").val(data.namechi);
				if(obj.prjqualityfee!=""){
					$("#prjQualityFee").parent().removeClass("hidden");
					$("#prjQualityFee").val(data.prjqualityfee);
				}else{
					$("#prjQualityFee").parent().addClass("hidden");
				}
			}
		});
	}else if(faultType=="2"){
		$("#openComponent_employee_faultEmp").val("");
		$("#faultWorker").parent().removeClass("hidden");
		$("#faultEmp").parent().addClass("hidden");
		$("#faultWorker").openComponent_worker({
			showValue:clickNum==1?"${laborFee.faultMan}":"",
			showLabel:clickNum==1?"${laborFee.faultManDescr}":"",
			condition:{
				refCustCode:$("#refCustCode").val()
			},
			callBack:function(data){
				$("#faultMan").val(data.Code);
				$("#faultManDescr").val(data.NameChi);
			}
		});
	}else{
		$("#faultWorker").parent().addClass("hidden");
		$("#faultEmp").parent().addClass("hidden");
		$("#openComponent_worker_faultWorker").val("");
		$("#openComponent_employee_faultEmp").val("");
	}
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
						<input type="hidden" id="checkStatus" name="checkStatus"   />
						<input type="hidden" id="checkDate" name="checkDate"   />
						<input type="hidden" id="feeTypeDescr" name="feeTypeDescr"   />
						<input type="hidden" id="iaNo" name="iaNo"   />
						<input type="hidden" id="isSetItem" name="isSetItem"   />
						<input type="hidden" name="faultTypeDescr" id="faultTypeDescr" value="员工（项目经理）"   />
						<input type="hidden" name="faultMan" id="faultMan" value="${laborFee.faultMan}"   />
						<input type="hidden" name="faultManDescr" id="faultManDescr" value="${laborFee.faultManDescr}"   />
						<input type="hidden" name="refCustDescr" id="refCustDescr" value="${laborFee.refCustDescr}"   />
						<input type="hidden" name="projectMan" id="projectMan"   />
       					<input type="hidden" name="projectManDescr" id="projectManDescr" />
       					<input type="hidden" name="regionDescr" id="regionDescr" />
       					<input type="hidden" name="refCustAddress" id="refCustAddress" />
						<ul class="ul-form">
							<li class="form-validate">
								<label><span class="required">*</span>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${laborFee.custCode }"/>
							</li>
							<li class="hidden">
								<label>关联客户</label>
								<input type="text" id="refCustCode" name="refCustCode" value="${laborFee.refCustCode}" readonly="true"/>
							</li> 
							<div class="hidden">
								<li><label>责任人类型</label> <house:xtdm id="faultType" 
									dictCode="FAULTTYPE" unShowValue="4" onchange="changeFaultType();getDescrByCode('faultType','faultTypeDescr')"
									value="1"></house:xtdm>
								</li>
								<li><label>员工</label> <input type="text" id="faultEmp"
									name="faultEmp" />
								</li>
								<li class="hidden"><label>工人</label> <input type="text" id="faultWorker"
									name="faultWorker" />
								</li>
								<li><label>项目经理质保余额</label> <input type="text"
									id="prjQualityFee" name="prjQualityFee" style="width:160px;"
									value="${ laborFee.prjQualityFee}" readonly/>
								</li>
							</div>
							<li>
								<label><span class="required">*</span>户名</label>
								<input type="text" id="actName" name="actName" style="width:160px;" value="${laborFee.actName }"/>
							</li>
							<li class="form-validate">
								<label>档案号</label>
								<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${laborFee.documentNo }" readonly="true"/>
							</li>
							<li>
								<label><span class="required">*</span>卡号</label>
								<input type="text" id="cardID" name="cardID" style="width:160px;" value="${laborFee.cardID }"/>
							</li>
							<li class="form-validate">
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${laborFee.address }" readonly="true"/>
							</li>
							<li  >
								<label>备注</label>
								<input type="text" id="detailRemarks" name="detailRemarks"  value="${laborFee.detailRemarks }"/>
							</li>
							<li class="form-validate">
								<label ><span class="required">*</span>费用类型</label>
								<house:dict id="feeType" dictCode=""  onchange="getFeeTypeDescr()"
								sql="select code,code+' '+descr descr from tLaborFeeType where expired='F' and ItemType1 = '${laborFee.itemType1 }'" 
								sqlLableKey="descr" sqlValueKey="code">
								</house:dict>                 
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>发货单号</label>
								<input type="text" id=appSendNo name="appSendNo"  value="${laborFee.appSendNo }"/>
							</li>
							<li class="form-validate">
								<label>本单已报销</label>
								<input type="text" id="haveAmount" name="haveAmount" style="width:160px;" value="${laborFee.haveAmount }" readonly="true"/>
							</li>
							<li class="form-validate">
								<label>已报销金额</label>
								<input type="text" id="sendNoHaveAmount" name="sendNoHaveAmount" style="width:160px;" value="0" readonly="true"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>付款金额</label>
								<input type="text" id="amount" name="amount" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" 
										style="width:160px;" value="${laborFee.amount }"/>
							</li> 
						</ul>
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>
