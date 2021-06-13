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
    		<input id="fp__tWfCust_ErpAccount__0__EmpCode" name="fp__tWfCust_ErpAccount__0__EmpCode" value="${datas.fp__tWfCust_ErpAccount__0__EmpCode }"  hidden="true"/>
    		<input id="fp__tWfCust_ErpAccount__0__EmpName" name="fp__tWfCust_ErpAccount__0__EmpName" value="${datas.fp__tWfCust_ErpAccount__0__EmpName}" hidden="true"/>
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>员工编号</label>
						<input type="text" id="employee" name="employee"  style="width:160px;" value="${datas.fp__tWfCust_ErpAccount__0__EmpCode}"/>
				    </li>
	         		<li class="form-validate">
						<label><span class="required">*</span>服务类型</label>
						<select id="fp__tWfCust_ErpAccount__0__Type" name ="fp__tWfCust_ErpAccount__0__Type" style="width:160px" 
							value="${ datas.fp__tWfCust_ErpAccount__0__Type}">
 							<option value ="">请选择...</option>
 							<option value ="Erp密码重置" ${ datas.fp__tWfCust_ErpAccount__0__Type == 'Erp密码重置' ? 'selected' : ''}>Erp密码重置</option>
 							<option value ="Erp权限申请" ${ datas.fp__tWfCust_ErpAccount__0__Type == 'Erp权限申请' ? 'selected' : ''}>Erp权限申请</option>
 						</select>
 						
 					</li>
					<li>
						<label class="control-textarea">备注</label>
						<textarea id="fp__tWfCust_ErpAccount__0__Reason" name="fp__tWfCust_ErpAccount__0__Reason" rows="2">${datas.fp__tWfCust_ErpAccount__0__Reason}</textarea>
					</li>
				</div>
				<div id="tips" style="border-top:1px solid #dfdfdf" >
					<div style="margin-left:60px;margin-top:8px">
						${wfProcess.remarks}
					</div>
				</div>
        	</ul>
        </form>
     </div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	if ("" == $.trim("${wfProcess.remarks}")) {
		$("#tips").remove();
	}
	function getEmpDetail(data){
		$("#fp__tWfCust_ErpAccount__0__EmpCode").val(data.number);
		$("#fp__tWfCust_ErpAccount__0__EmpName").val(data.namechi);
	}

	$("#employee").openComponent_employee({callBack:getEmpDetail,showValue:"${datas.fp__tWfCust_ErpAccount__0__EmpCode }",showLabel:"${datas.fp__tWfCust_ErpAccount__0__EmpName }",
		condition:{startMan:$.trim("${startMan}")}}
	);

	$("#dataForm").bootstrapValidator({
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
	
	if("${m_umState}" == "A"){
		getOperator_();
	}

});

function getOperator_(flag){ 
	var elMap = {};
	getOperator(flag,elMap);
} 
</script>
</body>
</html>

