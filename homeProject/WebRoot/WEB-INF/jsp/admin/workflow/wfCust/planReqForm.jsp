<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ERP服务</title>
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
	   		<input id="comment" name="comment" value="" hidden=""/>
	   		<input id="empComment" name="empComment" value="" hidden=""/><!-- 评论   -->
	   		<input id="wfProcInstNo" name="wfProcInstNo" value="${wfProcInstNo }" hidden=""/>
	   		<input id="processInstId" name="processInstId" value="${piId }" hidden=""/>
	   		<input id="photoPK" name="photoPK" value="" hidden="true" />
	   		<input id="url" name="url" value="${url }" hidden="true" />
	   		
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
						<label class="control-textarea"><span class="required">*</span>需求描述</label>
						<textarea id="fp__tWfCust_PlanReqForm__0__Reason" name="fp__tWfCust_PlanReqForm__0__Reason" rows="2">${datas.fp__tWfCust_PlanReqForm__0__Reason}</textarea>
					</li>
					<li hidden="true">
						<label class="control-textarea">Date</label>
						<textarea id="fp__tWfCust_PlanReqForm__0__Date" name="fp__tWfCust_PlanReqForm__0__Date" rows="2">${datas.fp__tWfCust_PlanReqForm__0__Reason}</textarea>
					</li>
	         		<li class="form-validate">
						<label><span class="required">*</span>要求交付时间</label>
						<input type="text" style="width:160px;" id="fp__tWfCust_PlanReqForm__0__DeliveryDate" name="fp__tWfCust_PlanReqForm__0__DeliveryDate"
				   			readonly="true" class="i-date" onfocus="WdatePicker({onpicked:getOperator_(),skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${datas.fp__tWfCust_PlanReqForm__0__DeliveryDate}' pattern='yyyy-MM-dd hh:mm:dd'/>" />
				    </li>
				    <li class="form-validate">
						<label>类型</label>
						<select id="fp__tWfCust_PlanReqForm__0__Type" name="fp__tWfCust_PlanReqForm__0__Type" value="${datas.fp__tWfCust_PlanReqForm__0__Type}">
 							<option value ="">请选择...</option>
 							<option value ="正常" ${ datas.fp__tWfCust_PlanReqForm__0__Type == '正常' ? 'selected' : ''}>正常</option>
 							<option value ="加急" ${ datas.fp__tWfCust_PlanReqForm__0__Type == '加急' ? 'selected' : ''}>加急</option>
						</select>
					</li> 
				</div>
				<c:if test="${activityId == 'usertask4' ||activityId == 'usertask5' }">
					<div class="validate-group row">	
						<li >
							<label>策划执行</label>
							<input type="text" id="nextOperator" name="nextOperator" style="width:160px;"/>
						</li>
					</div>
				</c:if>
				<div id="tips" style="border-top:1px solid #dfdfdf" >
					<div style="margin-left:60px;margin-top:8px">
						${wfProcess.remarks}
						<!-- <p style="color:red">1.该流程前端部门由部门经理发起。</p>
						<p style="color:red">2.正常单需提前7天申请。</p>
						<p style="color:red">3.活动策划需求需先进行活动策划案审批。</p> -->
					</div>
				</div>
        	</ul>
        </form>
     </div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#nextOperator").openComponent_employee();	
	
	document.getElementById("tab_apply_fjView_li").style.display="block";

	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			fp__tWfCust_PlanReqForm__0__Reason:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "诉求描述不能为空",
		            },
		        }  
		    },
		    fp__tWfCust_PlanReqForm__0__DeliveryDate:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "交付时间不能为空",
		            },
		        }  
		    },
		},
		submitButtons : "input[type='submit']"
	});
/* 	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
		     openComponent_employee_employee:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "员工编号不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ErpAccount__0__Type:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "员工编号不能为空",
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']"
	});
 */	
});

function getOperator_(flag){ 
	$("#fp__tWfCust_PlanReqForm__0__Type").attr("disabled",true);
	var days = 0;
	var date1 = new Date();
	if($("#fp__tWfCust_PlanReqForm__0__DeliveryDate").val()==""){
		return;
	}
	var date2 = new Date($("#fp__tWfCust_PlanReqForm__0__DeliveryDate").val());
	days = Math.ceil((date2-date1)/(1000*24*60*60));
	$("#fp__tWfCust_PlanReqForm__0__Date").val(days);
	//if("${m_umState}" == "A" || ($("#reApplyYesBtn").length > 0 && $("#reApplyYesBtn").css("display") != "none")){
		if(days<7){
			$("#fp__tWfCust_PlanReqForm__0__Type").val("加急");
		}else{
			$("#fp__tWfCust_PlanReqForm__0__Type").val("正常");
		}
	//}
	var elMap = {Date:days};
	getOperator(flag,elMap);
} 
</script>
</body>
</html>

