<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>干系人调整</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
    <div class="query-form" style="border-bottom:0px;margin-top:0px">
    	<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
    		<input id="processDefinitionId" name="processDefinitionId" value="${processDefinitionKey }" hidden="true"/>
    		<input id="wfProcNo" name="wfProcNo" value="${wfProcNo }" hidden="true"/>
    		<input id="taskId" name="taskId" value="${taskId }"  hidden="true"/>
    		<input id="address" name="address"  hidden="true"/>
    		<input id="custDescr" name="custDescr"  hidden="true"/>
    		<input id="department" name="department" hidden="true" />
	   		<input id="comment" name="comment" value="" hidden=""/>
	   		<input id="empComment" name="empComment" value="" hidden=""/><!-- 评论   -->
	   		<input id="wfProcInstNo" name="wfProcInstNo" value="${wfProcInstNo }" hidden=""/>
	   		<input id="processInstId" name="processInstId" value="${piId }" hidden=""/>
    		<input id="photoPK" name="photoPK" value=""  hidden="true"/><%--有上传图片时必备--%>
    		<input id="url" name="url" value="${url }" hidden="true" /><%--有上传图片时必备--%>
    		<input id="activityId" name="activityId" value="${activityId }" hidden="true" /> 
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>申请人</label>
						<input type="text" id="EmpCode" name="EmpCode" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_CustGiftApp__0__EmpName}"/>
   						<input type="hidden" id="fp__tWfCust_CustGiftApp__0__EmpCode" name="fp__tWfCust_CustGiftApp__0__EmpCode"  style="width:160px;" value="${datas.fp__tWfCust_CustGiftApp__0__EmpCode}"/>
   						<input type="hidden" id="fp__tWfCust_CustGiftApp__0__EmpName" name="fp__tWfCust_CustGiftApp__0__EmpName"  style="width:160px;" value="${datas.fp__tWfCust_CustGiftApp__0__EmpName}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>客户</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_CustGiftApp__0__CustDescr}"/>
   						<input type="hidden" id="fp__tWfCust_CustGiftApp__0__CustCode" name="fp__tWfCust_CustGiftApp__0__CustCode"  style="width:160px;" value="${datas.fp__tWfCust_CustGiftApp__0__CustCode}"/>
   						<input type="hidden" id="fp__tWfCust_CustGiftApp__0__CustDescr" name="fp__tWfCust_CustGiftApp__0__CustDescr"  style="width:160px;" value="${datas.fp__tWfCust_CustGiftApp__0__CustDescr}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>楼盘</label>
   						<input type="text" readonly="true" id="fp__tWfCust_CustGiftApp__0__Address" name="fp__tWfCust_CustGiftApp__0__Address" 
   							 style="width:160px;" value="${datas.fp__tWfCust_CustGiftApp__0__Address}"/>
				    </li>
 					<li class="form-validate">
				        <label><span class="required">*</span>工程造价</label>
						<input type="text" id="fp__tWfCust_CustGiftApp__0__ContractFee" name="fp__tWfCust_CustGiftApp__0__ContractFee" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_CustGiftApp__0__ContractFee}"/>
				    </li>
				</div>
				
				<div class="validate-group row" >	
				    <li class="form-validate">
						<label><span class="required">*</span>审批人</label>
						<select id="fp__tWfCust_CustGiftApp__0__ApproveLine" name ="fp__tWfCust_CustGiftApp__0__ApproveLine" style="width:160px"
							 onchange = "chgApproveLine()" value=" ${ datas.fp__tWfCust_CustGiftApp__0__ApproveLine}">
							<option value ="">请选择...</option>
							<option value ="1" ${ datas.fp__tWfCust_CustGiftApp__0__ApproveLine== '1' ? 'selected' : ''}>小巴巴长审批</option>
 							<option value ="2" ${ datas.fp__tWfCust_CustGiftApp__0__ApproveLine== '2' ? 'selected' : ''}>总经理审批</option>	
 							<option value ="3" ${ datas.fp__tWfCust_CustGiftApp__0__ApproveLine== '3' ? 'selected' : ''}>总裁审批</option>	
 						</select>
 					</li>
	         		<li>
						<label>申请时间</label>
						<input type="text" id="fp__tWfCust_CustGiftApp__0__AppDate" name="fp__tWfCust_CustGiftApp__0__AppDate" class="i-date"  
								style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true" 
								value="<fmt:formatDate value='${datas.fp__tWfCust_CustGiftApp__0__AppDate}' pattern='yyyy-MM-dd hh:mm:ss'/>" />
					</li>
				</div>
				
				<div class="validate-group row" style="max-height:999px">	
					<li class="form-validate" style="max-height:999px">
						<label class="control-textarea"><span class="required">*</span>优惠说明</label>
						<textarea id="fp__tWfCust_CustGiftApp__0__DiscountRemarks" name="fp__tWfCust_CustGiftApp__0__DiscountRemarks" 
								readonly="true" rows="5" style="width: 370px">${datas.fp__tWfCust_CustGiftApp__0__DiscountRemarks}</textarea>
					</li>
					<li class="form-validate" style="max-height:999px">
						<label class="control-textarea"><span class="required" id="manager_req">*</span>总经理审批条款</label>
						<textarea id="fp__tWfCust_CustGiftApp__0__ManagerItems" name="fp__tWfCust_CustGiftApp__0__ManagerItems" 
								 rows="5" style="width: 370px">${datas.fp__tWfCust_CustGiftApp__0__ManagerItems}</textarea>
						<button type="button" class="btn btn-system" data-disabled="false" onclick="selItems('fp__tWfCust_CustGiftApp__0__ManagerItems')"
					    		style ="margin-top: -20px">
					    	<span style="font-size: 12px;">选择条款</span>
					    </button>
					</li>
				</div>
				<div class="validate-group row" style="max-height:999px">	
					<li class="form-validate" style="max-height:999px">
						<label class="control-textarea">备注</label>
						<textarea id="fp__tWfCust_CustGiftApp__0__Remarks" name="fp__tWfCust_CustGiftApp__0__Remarks" 
								 rows="5" style="width: 370px">${datas.fp__tWfCust_CustGiftApp__0__Remarks}</textarea>
					</li>
					<li class="form-validate" style="max-height:999px">
						<label class="control-textarea"><span class="required" id="president_req">*</span>总裁审批条款</label>
						<textarea id="fp__tWfCust_CustGiftApp__0__PresidentItems" name="fp__tWfCust_CustGiftApp__0__PresidentItems" 
								 rows="5" style="width: 370px">${datas.fp__tWfCust_CustGiftApp__0__PresidentItems}</textarea>
						<button type="button" class="btn btn-system" data-disabled="false" onclick="selItems('fp__tWfCust_CustGiftApp__0__PresidentItems')" 
					    		style ="margin-top: -20px">
					    	<span style="font-size: 12px">选择条款</span>
					    </button>
					</li>
				</div>
        	</ul>
        </form>
     </div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//detailJson 查看的时候传回来的明细数据
var html_ = $("#detail_div").html();
var detailJson=${detailJson};

$(function(){
	var detailList = ${detailList};
	document.getElementById("tab_apply_fjView_li").style.display="block";
	$("#president_req").hide();
	$("#manager_req").hide();
	
	if("A"=="${m_umState}"){
		$("#fp__tWfCust_CustGiftApp__0__AppDate").val(formatTime(new Date));
	}
	
	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
		     fp__tWfCust_CustGiftApp__0__ApproveLine:{
		        validators: {  
		            notEmpty: {  
		           		 message: "审批人不能为空",
		            },
		        }, 
		     },
		},
		submitButtons : "input[type='submit']"
	});
	
});

function chgApproveLine(){
	var approveLine = $.trim($("#fp__tWfCust_CustGiftApp__0__ApproveLine").val());
	
	$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustGiftApp__0__PresidentItems", {  
		validators: {  
			notEmpty: {  
				message: '总裁审批不能为空', 
			},
		},
	});	
	$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustGiftApp__0__ManagerItems", {  
		validators: {  
			notEmpty: {  
				message: '总经理审批不能为空',
			},
		}, 
	});	
	// 设置总裁审批 和 总经理审批是否必填
	$("#president_req").hide();
	$("#manager_req").hide();
	if(approveLine == "2"){
		$("#manager_req").show();
		$("#dataForm").data("bootstrapValidator")
   			.updateStatus("fp__tWfCust_CustGiftApp__0__PresidentItems", "VALIDATED",null);
		$("#dataForm").bootstrapValidator("removeField","fp__tWfCust_CustGiftApp__0__PresidentItems");
		$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustGiftApp__0__PresidentItems", {  
			validators: {  

			}, 
		});	
		$("#dataForm").bootstrapValidator("removeField","fp__tWfCust_CustGiftApp__0__ManagerItems");
		$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustGiftApp__0__ManagerItems", {  
			validators: {  

			},  
		});
		$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustGiftApp__0__ManagerItems", {  
			validators: {  
				notEmpty: {  
					message: '总经理审批不能为空',  
				},
			},  
		});	
	} else 	if(approveLine == "3"){
		$("#president_req").show();
		$("#manager_req").show();
		$("#dataForm").data("bootstrapValidator")
   			.updateStatus("fp__tWfCust_CustGiftApp__0__ManagerItems", "VALIDATED",null);
		$("#dataForm").bootstrapValidator("removeField","fp__tWfCust_CustGiftApp__0__ManagerItems");
		$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustGiftApp__0__ManagerItems", {  
			validators: {  

			},  
		});	
		$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustGiftApp__0__PresidentItems", {  
			validators: {  
				notEmpty: {  
					message: '总裁审批不能为空',  
				},
			},  
		});	
		$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustGiftApp__0__ManagerItems", {  
			validators: {  
				notEmpty: {  
					message: '总经理审批不能为空',  
				},
			},  
		});	
	} else {
		$("#dataForm").data("bootstrapValidator")
   			.updateStatus("fp__tWfCust_CustGiftApp__0__ManagerItems", "VALIDATED",null);
		$("#dataForm").bootstrapValidator("removeField","fp__tWfCust_CustGiftApp__0__ManagerItems");
		$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustGiftApp__0__ManagerItems", {  
			validators: {  

			},  
		});
		$("#dataForm").data("bootstrapValidator")
   			.updateStatus("fp__tWfCust_CustGiftApp__0__PresidentItems", "VALIDATED",null);
		$("#dataForm").bootstrapValidator("removeField","fp__tWfCust_CustGiftApp__0__PresidentItems");
		$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustGiftApp__0__PresidentItems", {  
			validators: {  

			},  
		});
	}
		
	getOperator_();
}

function getOperator_(flag){
	var elMap = {ApproveLine: $.trim($("#fp__tWfCust_CustGiftApp__0__ApproveLine").val())};
	getOperator(flag,elMap);
}

function selItems(id){
	var approveLine = $.trim($("#fp__tWfCust_CustGiftApp__0__ApproveLine").val());
	
	Global.Dialog.showDialog("EmpAccount",{
		title:"选择补充协议",
		url:"${ctx}/admin/itemPlan/goRepClauseDetail",
		postData:{code: "${datas.fp__tWfCust_CustGiftApp__0__CustCode}"},
		height: 640,
		width:900,
		returnFun:function(datas){
			if(datas.length>0){
				$("#"+id).val("");
				$.each(datas,function(k,data){
					var val = $.trim($("#"+id).val());
					if($.trim($("#"+id).val()) == ""){
						$("#"+id).val(data);
					} else {
						$("#"+id).val(val+",\r\n"+data);
					}
					if(approveLine == "3" || (approveLine == "2" && id == "fp__tWfCust_CustGiftApp__0__ManagerItems")){
						setValidateField(id);
					}
				});
			}
		}
	});
}
function setValidateField(id){
	$("#dataForm").data("bootstrapValidator")
          .updateStatus(id, "NOT_VALIDATED",null)  
          .validateField(id); 
}

</script>
</body>
</html>

