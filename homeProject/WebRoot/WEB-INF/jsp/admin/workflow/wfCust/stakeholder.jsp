<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>退项及返点扣回</title>
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
    		<input id="department" name="department" hidden="true" />
	   		<input id="comment" name="comment" value="" hidden=""/><!-- 同意说明  -->
	   		<input id="empComment" name="empComment" value="" hidden=""/><!-- 评论   -->
	   		<input id="wfProcInstNo" name="wfProcInstNo" value="${wfProcInstNo }" hidden=""/>
	   		<input id="processInstId" name="processInstId" value="${piId }" hidden=""/>
    		<input id="fp__tWfCust_Stakeholder__0__CustCode" name="fp__tWfCust_Stakeholder__0__CustCode" value="${datas.fp__tWfCust_Stakeholder__0__CustCode }"  hidden="true"/>
    		<input id="fp__tWfCust_Stakeholder__0__CustDescr" name="fp__tWfCust_Stakeholder__0__CustDescr" value="${datas.fp__tWfCust_Stakeholder__0__CustDescr}" hidden="true"/>
    		<input id="fp__tWfCust_Stakeholder__0__OldEmpNumber" name="fp__tWfCust_Stakeholder__0__OldEmpNumber" value="${datas.fp__tWfCust_Stakeholder__0__OldEmpNumber}" hidden="true"/>
    		<input id="fp__tWfCust_Stakeholder__0__OldEmpNameChi" name="fp__tWfCust_Stakeholder__0__OldEmpNameChi" value="${datas.fp__tWfCust_Stakeholder__0__OldEmpNameChi}" hidden="true"/>
    		<input id="fp__tWfCust_Stakeholder__0__NewEmpNumber" name="fp__tWfCust_Stakeholder__0__NewEmpNumber" value="${datas.fp__tWfCust_Stakeholder__0__NewEmpNumber}" hidden="true"/>
    		<input id="fp__tWfCust_Stakeholder__0__NewEmpNameChi" name="fp__tWfCust_Stakeholder__0__NewEmpNameChi" value="${datas.fp__tWfCust_Stakeholder__0__NewEmpNameChi}" hidden="true"/>
			<input id="fp__tWfCust_Stakeholder__0__NewEmpNumber2" name="fp__tWfCust_Stakeholder__0__NewEmpNumber2" value="${datas.fp__tWfCust_Stakeholder__0__NewEmpNumber2}" hidden="true"/>
    		<input id="fp__tWfCust_Stakeholder__0__NewEmpNameChi2" name="fp__tWfCust_Stakeholder__0__NewEmpNameChi2" value="${datas.fp__tWfCust_Stakeholder__0__NewEmpNameChi2}" hidden="true"/>
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>客户编号</label>
						<input type="text" id="custCode" name="custCode"  style="width:160px;" value="${datas.fp__tWfCust_PrjRefund__0__CustCode}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>楼盘</label>
						<input type="text" id="fp__tWfCust_Stakeholder__0__Address" name="fp__tWfCust_Stakeholder__0__Address"  style="width:160px;" value="${datas.fp__tWfCust_Stakeholder__0__Address}"/>
				    </li>
				    <li class="form-validate">
						<label><span class="required">*</span>状态</label>
						<select id="fp__tWfCust_Stakeholder__0__Status" name ="fp__tWfCust_Stakeholder__0__Status" style="width:160px" 
							value=" ${ datas.fp__tWfCust_Stakeholder__0__Status}" onchange="getOperator_()" disabled="true">
 							<option value ="" >选择施工状态...</option>
 							<option value ="合同施工" ${ datas.fp__tWfCust_Stakeholder__0__Status == '合同施工' ? 'selected' : ''}>合同施工</option>
 							<option value ="订单跟踪" ${ datas.fp__tWfCust_Stakeholder__0__Status == '订单跟踪' ? 'selected' : ''}>订单跟踪</option>
 							<option value ="已到公司" ${ datas.fp__tWfCust_Stakeholder__0__Status == '已到公司' ? 'selected' : ''}>已到公司</option>
 						</select>
 					</li>
					<li class="form-validate">
						<label><span class="required">*</span>类型</label>
						<select id="fp__tWfCust_Stakeholder__0__Type" name ="fp__tWfCust_Stakeholder__0__Type" style="width:160px" 
							onchange="getOperator_()"	value="${ datas.fp__tWfCust_Stakeholder__0__Type}">
 							<option value ="" >选择类型...</option>
 							<option value ="撞单" ${ datas.fp__tWfCust_Stakeholder__0__Type == '撞单' ? 'selected' : ''}>撞单</option>
 							<option value ="专盘,同事业部" ${ datas.fp__tWfCust_Stakeholder__0__Type == '专盘,同事业部' ? 'selected' : ''}>专盘,同事业部</option>
 							<option value ="专盘,跨事业部" ${ datas.fp__tWfCust_Stakeholder__0__Type == '专盘,跨事业部' ? 'selected' : ''}>专盘,跨事业部</option>
 							<option value ="人员离职调整" ${ datas.fp__tWfCust_Stakeholder__0__Type == '人员离职调整' ? 'selected' : ''}>人员离职调整</option>
 							<option value ="设计师离职调整" ${ datas.fp__tWfCust_Stakeholder__0__Type == '设计师离职调整' ? 'selected' : ''}>设计师离职调整</option>
 							<option value ="现场设计师离职调整" ${ datas.fp__tWfCust_Stakeholder__0__Type == '现场设计师离职调整' ? 'selected' : ''}>现场设计师离职调整</option>
 							<option value ="业主不满意,更换设计师" ${ datas.fp__tWfCust_Stakeholder__0__Type == '业主不满意,更换设计师' ? 'selected' : ''}>业主不满意,更换设计师</option>
 							<option value ="干系人不变,部门业绩归属调整" ${ datas.fp__tWfCust_Stakeholder__0__Type == '干系人不变,部门业绩归属调整' ? 'selected' : ''}>干系人不变,部门业绩归属调整</option>
 						</select>
 					</li>
 				</div>
				<div class="validate-group row" >	
 					<li class="form-validate">
						<label><span class="required">*</span>角色</label>
						<select id="fp__tWfCust_Stakeholder__0__Roll" name="fp__tWfCust_Stakeholder__0__Roll" onchange="selRole()" value="${datas.fp__tWfCust_Stakeholder__0__Roll}">
 							<option value ="">请选择...</option>
 							<option value ="设计师" ${ datas.fp__tWfCust_Stakeholder__0__Roll == '设计师' ? 'selected' : ''}>设计师</option>
 							<option value ="业务员" ${ datas.fp__tWfCust_Stakeholder__0__Roll == '业务员' ? 'selected' : ''}>业务员</option>
 							<option value ="翻单员" ${ datas.fp__tWfCust_Stakeholder__0__Roll == '翻单员' ? 'selected' : ''}>翻单员</option>
 							<option value ="现场设计师" ${ datas.fp__tWfCust_Stakeholder__0__Roll == '现场设计师' ? 'selected' : ''}>现场设计师</option>
 							<option value ="深化设计师" ${ datas.fp__tWfCust_Stakeholder__0__Roll == '深化设计师' ? 'selected' : ''}>深化设计师</option>
						</select>
					</li>
					<li class="form-validate" id="oldEmpChg"><!-- 这里加ID是有其他用处  -->
						<label><span class="required">*</span>原干系人编号</label>
						<input type="text" id="oldEmpNumber" name="oldEmpNumber" style="width:160px;"/>
					</li>
					<li class="form-validate" id="newEmpChg">
						<label><span class="required">*</span>新干系人编号</label>
						<input type="text" id="newEmpNumber" name="newEmpNumber" style="width:160px;"/>
					</li>
				</div>
				<div class="validate-group row" id="roll_2" hidden="true">	
 					<li class="form-validate">
						<label>角色2</label>
						<select id="fp__tWfCust_Stakeholder__0__Roll2" name="fp__tWfCust_Stakeholder__0__Roll2" value="${datas.fp__tWfCust_Stakeholder__0__Roll}">
 							<option value ="">请选择...</option>
 							<option value ="设计师" ${ datas.fp__tWfCust_Stakeholder__0__Roll2 == '设计师' ? 'selected' : ''}>设计师</option>
 							<option value ="业务员" ${ datas.fp__tWfCust_Stakeholder__0__Roll2 == '业务员' ? 'selected' : ''}>业务员</option>
 							<option value ="翻单员" ${ datas.fp__tWfCust_Stakeholder__0__Roll2 == '翻单员' ? 'selected' : ''}>翻单员</option>
 							<option value ="现场设计师" ${ datas.fp__tWfCust_Stakeholder__0__Roll2 == '现场设计师' ? 'selected' : ''}>现场设计师</option>
						</select>
					</li>
					<li class="form-validate" id="oldEmpChg"><!-- 这里加ID是有其他用处  -->
						<label>原干系人编号2</label>
						<input type="text" id="oldEmpNumber2" name="oldEmpNumber2" style="width:160px;"/>
					</li>
					<li class="form-validate" id="newEmpChg">
						<label>新干系人编号2</label>
						<input type="text" id="newEmpNumber2" name="newEmpNumber2" style="width:160px;"/>
					</li>
				</div>
				<div class="validate-group row" >	
					<li class="form-validate">
						<label class="control-textarea"><span class="required">*</span>备注</label>
						<textarea id="fp__tWfCust_Stakeholder__0__Reason" name="fp__tWfCust_Stakeholder__0__Reason" rows="2">${datas.fp__tWfCust_Stakeholder__0__Reason}</textarea>
					</li>
				</div>
        	</ul>
        </form>
     </div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	
	function getCustDetail(data){
		if(data){
			$("#fp__tWfCust_Stakeholder__0__CustCode").val(data.code); 
			$("#fp__tWfCust_Stakeholder__0__CustDescr").val(data.descr); 
			$("#fp__tWfCust_Stakeholder__0__Address").val(data.address); 
			$("#fp__tWfCust_Stakeholder__0__Status").val($.trim(data.statusdescr)); 
			$("#fp__tWfCust_Stakeholder__0__Status").attr("disabled","true");
			$("#dataForm").data("bootstrapValidator")
		             .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
		             .validateField("openComponent_customerOA_custCode")
		             .updateStatus("fp__tWfCust_Stakeholder__0__Address", "NOT_VALIDATED",null)  
		             .validateField("fp__tWfCust_Stakeholder__0__Address");
			chgTypeOptions(data.statusdescr);
			selRole();
		}
	}
	function getOldEmpDetail(data){
		$("#fp__tWfCust_Stakeholder__0__OldEmpNumber").val(data.number);
		$("#fp__tWfCust_Stakeholder__0__OldEmpNameChi").val(data.namechi);
	}
	
	function getEmpDetail(data){
		$("#fp__tWfCust_Stakeholder__0__NewEmpNumber").val(data.number);
		$("#fp__tWfCust_Stakeholder__0__NewEmpNameChi").val(data.namechi);
	}
	
	function getOldEmpDetail2(data){
		$("#fp__tWfCust_Stakeholder__0__OldEmpNumber2").val(data.number);
		$("#fp__tWfCust_Stakeholder__0__OldEmpNameChi2").val(data.namechi);
	}
	
	function getEmpDetail2(data){
		$("#fp__tWfCust_Stakeholder__0__NewEmpNumber2").val(data.number);
		$("#fp__tWfCust_Stakeholder__0__NewEmpNameChi2").val(data.namechi);
	}
	
	$("#custCode").openComponent_customerOA({callBack:getCustDetail,showValue:"${datas.fp__tWfCust_Stakeholder__0__CustCode}"
								,showLabel:"${datas.fp__tWfCust_Stakeholder__0__CustDescr}",
								condition:{status:"2,4,3"}
								});	
	$("#oldEmpNumber").openComponent_employee({callBack:getOldEmpDetail,showValue:"${datas.fp__tWfCust_Stakeholder__0__OldEmpNumber}"
								,showLabel:"${datas.fp__tWfCust_Stakeholder__0__OldEmpNameChi}"});
		
	$("#newEmpNumber").openComponent_employee({callBack:getEmpDetail,showValue:"${datas.fp__tWfCust_Stakeholder__0__NewEmpNumber}"
								,showLabel:"${datas.fp__tWfCust_Stakeholder__0__NewEmpNameChi}"});
	
	$("#oldEmpNumber2").openComponent_employee({callBack:getOldEmpDetail2,showValue:"${datas.fp__tWfCust_Stakeholder__0__OldEmpNumber2}"
								,showLabel:"${datas.fp__tWfCust_Stakeholder__0__OldEmpNameChi2}"});
		
	$("#newEmpNumber2").openComponent_employee({callBack:getEmpDetail2,showValue:"${datas.fp__tWfCust_Stakeholder__0__NewEmpNumber2}"
								,showLabel:"${datas.fp__tWfCust_Stakeholder__0__NewEmpNameChi2}"});
	
	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
		     openComponent_customerOA_custCode:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "客户编号不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_Stakeholder__0__Address:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "楼盘不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_Stakeholder__0__Type:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "类型不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_Stakeholder__0__Reason:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "备注不能为空"  ,
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']"
	});
	
});
function selRole(){
	getOperator_();
	$.ajax({
		url:"${ctx}/admin/wfProcInst/getCustStakeholder",
		type:"post",	
		data:{roll:$("#fp__tWfCust_Stakeholder__0__Roll").val(),custCode:$("#custCode").val()},
		dataType:"json",
		cache:false,
		error:function(obj){
				$("#fp__tWfCust_Stakeholder__0__OldEmpNumber").val("");
				$("#fp__tWfCust_Stakeholder__0__OldEmpNameChi").val("");
				$("#openComponent_employee_oldEmpNumber").val("");
				$("#oldEmpNumber").openComponent_employee({callBack:getOldEmpDetail,
					showValue:obj.EmpCode,showLabel:obj.NameChi,condition:{role:($("#fp__tWfCust_Stakeholder__0__Roll").val()==""?"***":$("#fp__tWfCust_Stakeholder__0__Roll").val()),custCode:$("#custCode").val()}});	
			showAjaxHtml({"hidden": false, "msg": "出错~"});
		},
		success:function(obj){
			if (obj){
				$("#fp__tWfCust_Stakeholder__0__OldEmpNumber").val(obj.EmpCode);
				$("#fp__tWfCust_Stakeholder__0__OldEmpNameChi").val(obj.NameChi);
					$("#oldEmpNumber").openComponent_employee({callBack:getOldEmpDetail,
						showValue:obj.EmpCode,showLabel:obj.NameChi,condition:{role:($("#fp__tWfCust_Stakeholder__0__Roll").val()==""?"***":$("#fp__tWfCust_Stakeholder__0__Roll").val()),custCode:$("#custCode").val()}});	
			}
		}
	});
}

function getOperator_(flag){ 
	if($("#fp__tWfCust_Stakeholder__0__Type").val() =="撞单"){
		$("#roll_2").removeAttr("hidden","true");
	}else{
		$("#roll_2").attr("hidden","true");
		$("#fp__tWfCust_Stakeholder__0__OldEmpNumber2").val("");
		$("#fp__tWfCust_Stakeholder__0__OldEmpNameChi2").val("");
		$("#fp__tWfCust_Stakeholder__0__NewEmpNumber2").val("");
		$("#fp__tWfCust_Stakeholder__0__NewEmpNameChi2").val("");
	}
	if("干系人不变,部门业绩归属调整"==$("#fp__tWfCust_Stakeholder__0__Type").val()){
		$("#fp__tWfCust_Stakeholder__0__Roll").val("");
		$("#fp__tWfCust_Stakeholder__0__Roll").attr("disabled","true");
		$("#openComponent_employee_newEmpNumber").val("");
		$("#openComponent_employee_newEmpNumber").attr("readonly",true);
		$("#openComponent_employee_oldEmpNumber").val("");
		$("#openComponent_employee_oldEmpNumber").attr("readonly",true);
		$("#fp__tWfCust_Stakeholder__0__OldEmpNumber").val("");
		$("#fp__tWfCust_Stakeholder__0__OldEmpNameChi").val("");
		$("#fp__tWfCust_Stakeholder__0__NewEmpNumber").val("");
		$("#fp__tWfCust_Stakeholder__0__NewEmpNameChi").val("");
		$("#newEmpChg button").attr("disabled","true");
		$("#oldEmpChg button").attr("disabled","true");
	}else{
		$("#openComponent_employee_newEmpNumber").removeAttr("readonly",true);
		$("#openComponent_employee_oldEmpNumber").removeAttr("readonly",true);
		$("#fp__tWfCust_Stakeholder__0__Roll").removeAttr("disabled","true");
		$("#newEmpChg button").removeAttr("disabled","true");
		$("#oldEmpChg button").removeAttr("disabled","true");
	}
	var elMap = {Status:$("#fp__tWfCust_Stakeholder__0__Status").val(),Type:$("#fp__tWfCust_Stakeholder__0__Type").val(),Roll:$("#fp__tWfCust_Stakeholder__0__Roll").val()};
	getOperator(flag,elMap);
} 

function getOldEmpDetail(data){
	$("#fp__tWfCust_Stakeholder__0__OldEmpNumber").val(data.number);
	$("#fp__tWfCust_Stakeholder__0__OldEmpNameChi").val(data.namechi);
}

function chgTypeOptions(statusDescr){
	if(statusDescr == "订单跟踪"){
		if($("#fp__tWfCust_Stakeholder__0__Type option[value='设计师离职调整']").length > 0){
			$("#fp__tWfCust_Stakeholder__0__Type option[value='设计师离职调整']").remove();
			$("#fp__tWfCust_Stakeholder__0__Type option[value='现场设计师离职调整']").remove();
		} 
	} else {
		if($("#fp__tWfCust_Stakeholder__0__Type option[value='设计师离职调整']").length == 0){
			$("#fp__tWfCust_Stakeholder__0__Type").append("<option value= " + "现场设计师离职调整" + ">" + "现场设计师离职调整" + "</option>");
			$("#fp__tWfCust_Stakeholder__0__Type").append("<option value= " + "设计师离职调整" + ">" + "设计师离职调整" + "</option>");
		}
	}
	
	if(statusDescr == "合同施工" ){
		if($("#fp__tWfCust_Stakeholder__0__Type option[value='人员离职调整']").length > 0){
			$("#fp__tWfCust_Stakeholder__0__Type option[value='人员离职调整']").remove();
		} 
	} else {
		if($("#fp__tWfCust_Stakeholder__0__Type option[value='人员离职调整']").length == 0){
			$("#fp__tWfCust_Stakeholder__0__Type").append("<option value= " + "人员离职调整" + ">" + "人员离职调整" + "</option>");
		}
	}
}
</script>
</body>
</html>

