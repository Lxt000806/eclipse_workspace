<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>出差申请</title>
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
    		<input id="fp__tWfCust_TravelApply__0__EmpCode" name="fp__tWfCust_TravelApply__0__EmpCode" value="${datas.fp__tWfCust_TravelApply__0__EmpCode}" hidden="true"/>
    		<input id="fp__tWfCust_TravelApply__0__EmpName" name="fp__tWfCust_TravelApply__0__EmpName" value="${datas.fp__tWfCust_TravelApply__0__EmpName}" hidden="true"/>
    		<input id="fp__tWfCust_TravelApply__0__DeptCode" name="fp__tWfCust_TravelApply__0__DeptCode" value="${datas.fp__tWfCust_TravelApply__0__DeptCode}" hidden="true"/>
    		<input id="fp__tWfCust_TravelApply__0__DeptDescr" name="fp__tWfCust_TravelApply__0__DeptDescr" value="${datas.fp__tWfCust_TravelApply__0__DeptDescr}" hidden="true"/>
    		<input id="fp__tWfCust_TravelApply__0__CompanyDescr" name="fp__tWfCust_TravelApply__0__CompanyDescr" value="${datas.fp__tWfCust_TravelApply__0__CompanyDescr}" hidden="true"/>
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label>员工编号</label>
						<input type="text" id="employee" name="employee"  style="width:160px;" value="${datas.fp__tWfCust_TravelApply__0__EmpCode}"/>
				    </li>
	         		<li class="form-validate">
						<label>分公司</label>
						<select id="fp__tWfCust_TravelApply__0__CompanyCode" name="fp__tWfCust_TravelApply__0__CompanyCode" 
								value="${datas.fp__tWfCust_TravelApply__0__CompanyCode }" onchange="changeCmpDescr()">
							<option value ="01" ${datas.fp__tWfCust_TravelApply__0__CompanyCode == '01' ? 'selected' : ''}>福州有家</option>
							<option value ="07" ${datas.fp__tWfCust_TravelApply__0__CompanyCode == '07' ? 'selected' : ''}>美第家居</option>
						</select>
					</li>
					<li class="form-validate">
				        <label>部门</label>
						<input type="text" id="deptCode" name="deptCode"  style="width:160px;" value="${datas.fp__tWfCust_TravelApply__0__DeptDescr}" readonly="true"/>
				    </li>
				    <li class="form-validate">
				        <label>岗位</label>
						<input type="text" id="fp__tWfCust_TravelApply__0__Position" name="fp__tWfCust_TravelApply__0__Position"  
									style="width:160px;" value="${datas.fp__tWfCust_TravelApply__0__Position}" readonly="true"/>
				    </li>
				</div>
				<div class="validate-group row" >
				    <li class="form-validate">
						<label ><span class="required">*</span>申请时间</label>
						<input type="text" id="fp__tWfCust_TravelApply__0__ApplyDate" 
								name="fp__tWfCust_TravelApply__0__ApplyDate" style="width:160px" onchange="checkDate('ApplyDate')"
				   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
				   			 value="<fmt:formatDate value='${datas.fp__tWfCust_TravelApply__0__ApplyDate}' 
				   			 pattern='yyyy-MM-dd'/>" />
				    </li>
					<li class="form-validate">
						<label ><span class="required">*</span>出发时间</label>
						<input type="text" id="fp__tWfCust_TravelApply__0__BeginDate" 
								name="fp__tWfCust_TravelApply__0__BeginDate" style="width:160px"  onchange="checkDate('BeginDate')"
				   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
				   			 value="<fmt:formatDate value='${datas.fp__tWfCust_TravelApply__0__BeginDate}' 
				   			 pattern='yyyy-MM-dd'/>" />
				    </li>
				    <li class="form-validate">
						<label ><span class="required">*</span>回程时间</label>
						<input type="text" id="fp__tWfCust_TravelApply__0__EndDate" 
								name="fp__tWfCust_TravelApply__0__EndDate" style="width:160px"  onchange="checkDate('EndDate')"
					   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
					   			 value="<fmt:formatDate value='${datas.fp__tWfCust_TravelApply__0__EndDate}' 
					   			 pattern='yyyy-MM-dd'/>" />
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>出发地点</label>
						<input type="text" id="fp__tWfCust_TravelApply__0__Address" name="fp__tWfCust_TravelApply__0__Address"  
									style="width:160px;" value="${datas.fp__tWfCust_TravelApply__0__Address}"/>
			    	</li>
			    </div>
				<div class="validate-group row" >
				    <li class="form-validate">
						<label class="control-textarea"><span class="required">*</span>出差事由</label>								
						<textarea id="fp__tWfCust_TravelApply__0__Reason" name="fp__tWfCust_TravelApply__0__Reason" rows="2">${datas.fp__tWfCust_TravelApply__0__Reason}</textarea>
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
		$("#fp__tWfCust_TravelApply__0__EmpCode").val(data.number);
		$("#fp__tWfCust_TravelApply__0__EmpName").val(data.namechi);
		$("#fp__tWfCust_TravelApply__0__Position").val(data.posdesc);
		$("#fp__tWfCust_TravelApply__0__CompanyCode").val(data.cmpcode);
		$("#fp__tWfCust_TravelApply__0__CompanyDescr").val(data.cmpdescr);
		
		if(data.positionDescr != ""){
			$("#fp__tWfCust_TravelApply__0__Position").val(data.positionDescr);
		}
		if(data.cmpCode && data.cmpCode != ""){
			$("#fp__tWfCust_TravelApply__0__CompanyCode").val(data.cmpcode);

		}
		if(data.cmpCode && data.cmpDescr != ""){
			$("#fp__tWfCust_TravelApply__0__CompanyDescr").val(data.cmpDescr);
		}
		
	}
	
	if("${m_umState}" == "A"){
		
		$("#employee").openComponent_employee({
			callBack:getEmpDetail,
			showValue:"${employee.number }",
			showLabel:"${employee.nameChi }",
			readonly:true
		});
		$("#openComponent_employee_employee").blur();
		$("#fp__tWfCust_TravelApply__0__DeptCode").val("${employee.department}");
		$("#deptCode").val("${employee.departmentDescr}");
		$("#fp__tWfCust_TravelApply__0__DeptDescr").val("${employee.departmentDescr}");
		$("#fp__tWfCust_TravelApply__0__ApplyDate").val(getAddDays(new Date(),0));
	
	} else {
		
		$("#employee").openComponent_employee({
			showValue:"${datas.fp__tWfCust_TravelApply__0__EmpCode }",
			showLabel:"${datas.fp__tWfCust_TravelApply__0__EmpName }",
			readonly:true
		});
		
	}
	
	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			fp__tWfCust_TravelApply__0__ApplyDate:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "申请时间不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_TravelApply__0__BeginDate:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "开始时间不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_TravelApply__0__EndDate:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "结束时间不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_TravelApply__0__Address:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "出发地点不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_TravelApply__0__Reason:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "出差事由不能为空",
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
	var elMap = {CompanyCode:$.trim($("#fp__tWfCust_TravelApply__0__CompanyCode").val())};
	getOperator(flag,elMap);
} 

function checkDate(type){
	$("#dataForm").data("bootstrapValidator")
	    .updateStatus("fp__tWfCust_TravelApply__0__"+type, "NOT_VALIDATED",null)  
	    .validateField("fp__tWfCust_TravelApply__0__"+type);
}

function changeCmpDescr(){
	
	getOperator_();
	
	$("#fp__tWfCust_TravelApply__0__CompanyDescr").val($("#fp__tWfCust_TravelApply__0__CompanyCode").find("option:selected").text());
	
}
</script>
</body>
</html>

