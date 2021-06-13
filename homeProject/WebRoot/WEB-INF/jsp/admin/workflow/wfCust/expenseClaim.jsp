<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>费用预支流程</title>
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
    		<input id="wfProcNo" name="wfProcNo" value="${wfProcNo}" hidden="true"/>
    		<input id="taskId" name="taskId" value="${taskId}"  hidden="true"/>
    		<input id="department" name="department" hidden="true" />
	   		<input id="comment" name="comment" value="" hidden=""/>
	   		<input id="procKey" name="procKey" value="${procKey }" hidden=""/>
	   		<input id="empComment" name="empComment" value="" hidden=""/><!-- 评论   -->
	   		<input id="wfProcInstNo" name="wfProcInstNo" value="${wfProcInstNo }" hidden=""/>
	   		<input id="processInstId" name="processInstId" value="${piId }" hidden=""/>
    		<input id="photoPK" name="photoPK" value=""  hidden="true"/><%--有上传图片时必备--%>
    		<input id="url" name="url" value="${url }" hidden="true" /><%--有上传图片时必备--%>
    		<input id="activityId" name="activityId" value="${activityId }" hidden="true" />
    		<input id="confAmount" name="confAmount" value="${datas.fp__tWfCust_ExpenseClaim__0__ApproveAmount }" hidden="true" />
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row">
	         		<li class="form-validate">
				        <label><span class="required">*</span>报销人</label>
						<input type="text" id="EmpCode" name="EmpCode" style="width:160px;" value="${datas.fp__tWfCust_ExpenseClaim__0__EmpCode}"/>
   						<input type="text" id="fp__tWfCust_ExpenseClaim__0__EmpCode" name="fp__tWfCust_ExpenseClaim__0__EmpCode"  style="width:160px;" hidden="true" value="${datas.fp__tWfCust_ExpenseClaim__0__EmpCode}"/>
   						<input type="text" id="fp__tWfCust_ExpenseClaim__0__EmpName" name="fp__tWfCust_ExpenseClaim__0__EmpName"  style="width:160px;"hidden="true" value="${datas.fp__tWfCust_ExpenseClaim__0__EmpName}"/>
				   		<input id="fp__tWfCust_ExpenseClaim__0__EmpType" name="fp__tWfCust_ExpenseClaim__0__EmpType" value="${datas.fp__tWfCust_ExpenseClaim__0__EmpType}" hidden="true"/>
				    </li>
				    <li class="form-validate">
						<label><span class="required">*</span>分公司</label>
						<select id="fp__tWfCust_ExpenseClaim__0__Company" name="fp__tWfCust_ExpenseClaim__0__Company" 
								value="${datas.fp__tWfCust_ExpenseClaim__0__Company }" onchange="getOperator_()">
							<option value ="福州有家" ${ datas.fp__tWfCust_ExpenseClaim__0__Company == '福州有家' ? 'selected' : ''}>福州有家</option>
							<option value ="美第家居" ${ datas.fp__tWfCust_ExpenseClaim__0__Company == '美第家居' ? 'selected' : ''}>美第家居</option>
							<option value ="长乐有家" ${ datas.fp__tWfCust_ExpenseClaim__0__Company == '长乐有家' ? 'selected' : ''}>长乐有家</option>
							<option value ="福清有家" ${ datas.fp__tWfCust_ExpenseClaim__0__Company == '福清有家' ? 'selected' : ''}>福清有家</option>
							<option value ="厦门有家" ${ datas.fp__tWfCust_ExpenseClaim__0__Company == '厦门有家' ? 'selected' : ''}>厦门有家</option>
							<option value ="郑州有家" ${ datas.fp__tWfCust_ExpenseClaim__0__Company == '郑州有家' ? 'selected' : ''}>郑州有家</option>
							<option value ="福清有家" ${ datas.fp__tWfCust_ExpenseClaim__0__Company == '福清有家' ? 'selected' : ''}>福清有家</option>
							<option value ="长乐有家" ${ datas.fp__tWfCust_ExpenseClaim__0__Company == '长乐有家' ? 'selected' : ''}>长乐有家</option>
							<option value ="美第奇" ${ datas.fp__tWfCust_ExpenseClaim__0__Company == '美第奇' ? 'selected' : ''}>美第奇</option>
						</select>
					</li>
				    <li class="form-validate">
				        <label>借款余额</label>
   						<input type="text" id="fp__tWfCust_ExpenseClaim__0__BefAmount" name="fp__tWfCust_ExpenseClaim__0__BefAmount" 
   								value="${datas.fp__tWfCust_ExpenseClaim__0__BefAmount}" style="width:160px;" readonly="true"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>抵扣预支</label>
						<select id="fp__tWfCust_ExpenseClaim__0__Deduction" name ="fp__tWfCust_ExpenseClaim__0__Deduction">
							<option value =""   >请选择...</option>
 							<option value ="否" ${ datas.fp__tWfCust_ExpenseClaim__0__Deduction== '否' ? 'selected' : ''}>否</option>
 							<option value ="是" ${ datas.fp__tWfCust_ExpenseClaim__0__Deduction== '是' ? 'selected' : ''}>是</option>
 						</select>	
					</li>
				</div>
				<div class="validate-group row" >
					<li class="form-validate">
				        <label><span class="required">*</span>账户名</label>
						<input type="text" id="fp__tWfCust_ExpenseClaim__0__ActName" name="fp__tWfCust_ExpenseClaim__0__ActName"  
								style="width:136px;border-top-right-radius:0;border-bottom-right-radius:0" value="${datas.fp__tWfCust_ExpenseClaim__0__ActName}"/>
					    <button type="button" class="btn btn-system" data-disabled="false" 
					    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -5px;width:26px;height:24px;padding-top:2.5px" onclick="getEmpAccount()">
					    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
					    </button>
				    </li>	
	         		<li class="form-validate">
				        <label><span class="required">*</span>开户行</label>
						<input type="text" id="fp__tWfCust_ExpenseClaim__0__Bank" name="fp__tWfCust_ExpenseClaim__0__Bank"  style="width:160px;" value="${datas.fp__tWfCust_ExpenseClaim__0__Bank}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>支行</label>
						<input type="text" id="fp__tWfCust_ExpenseClaim__0__SubBranch" name="fp__tWfCust_ExpenseClaim__0__SubBranch"  
								style="width:160px;" value="${datas.fp__tWfCust_ExpenseClaim__0__SubBranch}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>卡号</label>
						<input type="text" id="fp__tWfCust_ExpenseClaim__0__CardId" name="fp__tWfCust_ExpenseClaim__0__CardId" 
						onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{4})|(\d{4}))(?=\d)/g,'$1 ');"
						 style="width:160px;" value="${datas.fp__tWfCust_ExpenseClaim__0__CardId}"/>
				    </li>
				</div>
				<div class="validate-group row" >
					<li class="form-validate">
				        <label><span class="required">*</span>常规列支</label>
						<select id="fp__tWfCust_ExpenseClaim__0__IsCommon" name ="fp__tWfCust_ExpenseClaim__0__IsCommon" onchange = "chgType()">
							<option value =""   >请选择...</option>
 							<option value ="否" ${ datas.fp__tWfCust_ExpenseClaim__0__IsCommon== '否' ? 'selected' : ''}>否</option>
 							<option value ="是" ${ datas.fp__tWfCust_ExpenseClaim__0__IsCommon== '是' ? 'selected' : ''}>是</option>
 						</select>	
					</li>
				    <li class="form-validate">
						<label class="control-textarea">备注</label>
						<textarea id="fp__tWfCust_ExpenseClaim__0__Remarks" 
							name="fp__tWfCust_ExpenseClaim__0__Remarks" rows="2">${datas.fp__tWfCust_ExpenseClaim__0__Remarks}</textarea>
					</li>
				</div>
				<div class="validate-group row" >
				    
					<li class="form-validate">
				        <label>报销额</label>
						<input type="text" id="fp__tWfCust_ExpenseClaim__0__Amount" name="fp__tWfCust_ExpenseClaim__0__Amount" 
							onchange="getOperator_()" placeholder="自动计算" style="width:160px;" value="${datas.fp__tWfCust_ExpenseClaim__0__Amount}" readonly="true"/>
				    </li>
				    <li class="form-validate" >
				        <label>抵扣金额</label>
						<input type="text" id="fp__tWfCust_ExpenseClaim__0__DeductionAmount" name="fp__tWfCust_ExpenseClaim__0__DeductionAmount" 
							onchange="calcDeductionAmount()"style="width:160px;" value="${datas.fp__tWfCust_ExpenseClaim__0__DeductionAmount}" readonly="true"/>
				    </li>
					<li class="form-validate" >
				        <label>审批金额</label>
						<input type="text" id="fp__tWfCust_ExpenseClaim__0__ApproveAmount" name="fp__tWfCust_ExpenseClaim__0__ApproveAmount" 
							 style="width:160px;" value="${datas.fp__tWfCust_ExpenseClaim__0__ApproveAmount}" readonly="true"/>
				    </li>
				    <li class="form-validate" >
				        <label><span class="required">*</span>凭证号</label>
						<input type="text" id="fp__tWfCust_ExpenseClaim__0__DocumentNo" name="fp__tWfCust_ExpenseClaim__0__DocumentNo" 
							 style="width:160px;" value="${datas.fp__tWfCust_ExpenseClaim__0__DocumentNo}" readonly="true"/>
				    </li>
				</div>
				    <%--
				<div class="validate-group row" >
				     <li class="form-validate" hidden="true">
				        <label><span class="required">*</span>付款账号</label>
						<input type="text" id="fp__tWfCust_ExpenseClaim__0__PayAccount" name="fp__tWfCust_ExpenseClaim__0__PayAccount"  style="width:160px;" value="${datas.fp__tWfCust_ExpenseClaim__0__PayAccount}"/>
				    </li>
				</div>
				     --%>
				
				<div id="tWfCust_ExpenseClaimDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_ExpenseClaimDtl_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px;margin-left:15px">
								<input id="fp__tWfCust_ExpenseClaimDtl__temInx__PK" name="fp__tWfCust_ExpenseClaimDtl__temInx__PK" value="${datas.fp__tWfCust_ExpenseClaimDtl__temInx__PK }" hidden="true"/>
								<input id="fp__tWfCust_ExpenseClaimDtl__temInx__DeptCode" name="fp__tWfCust_ExpenseClaimDtl__temInx__DeptCode" value="${datas.fp__tWfCust_ExpenseClaimDtl__temInx__DeptCode }" hidden="true"/>
					    		<input id="fp__tWfCust_ExpenseClaimDtl__temInx__DeptDescr" name="fp__tWfCust_ExpenseClaimDtl__temInx__DeptDescr"  value="${datas.fp__tWfCust_ExpenseClaimDtl__temInx__DeptDescr }" hidden="true"/>
								<div class="validate-group row" >	
									<li class="form-validate">
										<label><span class="required">*</span>报销部门</label>
										<input type="text" id="deptCode_temInx" name="deptCode_temInx"  style="width:160px;"/>
									</li>
					         		<li class="form-validate">
								        <label><span class="required">*</span>报销额</label>
										<input type="text" id="fp__tWfCust_ExpenseClaimDtl__temInx__DtlAmount" name="fp__tWfCust_ExpenseClaimDtl__temInx__DtlAmount"  style="width:160px;"
										 onchange="chgMainAmount(true)" value="${datas.fp__tWfCust_ExpenseClaimDtl__temInx__DtlAmount}"/>
									</li>
								</div>	
								<div class="validate-group row" >	
									<li class="form-validate">
										<label class="control-textarea"><span class="required">*</span>报销内容</label>
										<textarea id="fp__tWfCust_ExpenseClaimDtl__temInx__Remarks" name="fp__tWfCust_ExpenseClaimDtl__temInx__Remarks" rows="2">${datas.fp__tWfCust_ExpenseClaimDtl__temInx__Remarks}</textarea>
									</li>
									<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_ExpenseClaimDtl')" id="btn_temInx">增加明细</button>
								</div>
							</div>	
						</div>	
					</div>
				</div>
				
				<div id="tWfCust_ExpenseClaimAdvanceDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_ExpenseClaimAdvanceDtl_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px;margin-left:15px">
								<input id="fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__PK" name="fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__PK" value="${datas.fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__PK }" hidden="true"/>
								<div class="validate-group row">
									<li class="form-validate">
								        <label><span class="required">*</span>预支单</label>
										<input type="text" id="fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__WfExpenseAdvanceNo" 
												name="fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__WfExpenseAdvanceNo"  
												style="width:136px;border-top-right-radius:0;border-bottom-right-radius:0" 
												value="${datas.fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__WfExpenseAdvanceNo}"/>
									    <button type="button" class="btn btn-system" data-disabled="false" 
									    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -5px;width:26px;height:24px;padding-top:2.5px" onclick="getAdvanceNo(temInx)">
									    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
									    </button>
								    </li>	
					         		<li class="form-validate">
								        <label><span class="required">*</span>预支单金额</label>
										<input type="text" id="fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__AdvanceAmount" 
													name="fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__AdvanceAmount"  style="width:160px;"
										 	  value="${datas.fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__AdvanceAmount}"/>
									</li> 	
									<li class="form-validate">
								        <label><span class="required">*</span>抵扣金额</label>
										<input type="text" id="fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__DeductionAmount" 
											name="fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__DeductionAmount"  style="width:160px;"
										 value="${datas.fp__tWfCust_ExpenseClaimAdvanceDtl__temInx__DeductionAmount}" onchange="calcDeductionAmount()"/>
									</li>
									<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_ExpenseClaimAdvanceDtl')" id="btn_temInx">增加明细</button>
								</div>
							</div>	
						</div>	
					</div>
				</div>
				
				<div id="tWfCust_ExpenseClaimPayDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_ExpenseClaimPayDtl_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px;margin-left:15px">
								<input id="fp__tWfCust_ExpenseClaimPayDtl__temInx__PK" name="fp__tWfCust_ExpenseClaimPayDtl__temInx__PK" value="${datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PK }" hidden="true"/>
								<div class="validate-group row">
									<input type="text" id="fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompany"
											name="fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompany"  style="width:160px;"
										 value="${datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompany}" hidden="true"/>
									<li class="form-validate">
										<label><span class="required">*</span>出款公司</label>
										<select id="fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompanyCode" name="fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompanyCode" 
												value="${datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompanyCode }" onchange="chgPayCmp(temInx)">
											<!-- <option value ="" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayType == '' ? 'selected' : ''}>请选择</option>
											<option value ="福州有家" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompany == '福州有家' ? 'selected' : ''}>福州有家</option>
											<option value ="美第家居" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompany == '美第家居' ? 'selected' : ''}>美第家居</option>
											<option value ="长乐有家" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompany == '长乐有家' ? 'selected' : ''}>长乐有家</option>
											<option value ="福清有家" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompany == '福清有家' ? 'selected' : ''}>福清有家</option>
											<option value ="厦门有家" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompany == '厦门有家' ? 'selected' : ''}>厦门有家</option>
											<option value ="郑州有家" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompany == '郑州有家' ? 'selected' : ''}>郑州有家</option>
											<option value ="美第奇" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayCompany == '美第奇' ? 'selected' : ''}>美第奇</option>
										 -->
										</select>
									</li>
									<li class="form-validate" hidden="true">
										<label><span class="required">*</span>出款公司</label>
										<house:dict id="conSignCmp_temInx" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
								    		sql="select Code, Descr from tConSignCmp " onchange="chgConSignCmp()"></house:dict>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>付款方式</label>
										<select id="fp__tWfCust_ExpenseClaimPayDtl__temInx__PayType" name="fp__tWfCust_ExpenseClaimPayDtl__temInx__PayType" 
												value="${datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayType }" onchange="chgPayAmount()">
											<option value ="" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayType == '' ? 'selected' : ''}>请选择</option>
											<option value ="现金支付" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayType == '现金支付' ? 'selected' : ''}>现金支付</option>
											<option value ="银行转账" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayType == '银行转账' ? 'selected' : ''}>银行转账</option>
											<option value ="借款冲销" ${ datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayType == '借款冲销' ? 'selected' : ''}>借款冲销</option>
										</select>
									</li>	
									
					         		<li class="form-validate">
								        <label><span class="required">*</span>出款用途</label>
										<input type="text" id="fp__tWfCust_ExpenseClaimPayDtl__temInx__PayPurpose" 
													name="fp__tWfCust_ExpenseClaimPayDtl__temInx__PayPurpose"  style="width:160px;"
										 	  value="${datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayPurpose	}"/>
									</li> 	
									<li class="form-validate">
								        <label><span class="required">*</span>出款金额</label>
										<input type="text" id="fp__tWfCust_ExpenseClaimPayDtl__temInx__PayAmount" onchange="chgPayAmount(temInx)"
											name="fp__tWfCust_ExpenseClaimPayDtl__temInx__PayAmount"  style="width:160px;"
										 value="${datas.fp__tWfCust_ExpenseClaimPayDtl__temInx__PayAmount}"/>
									</li>
									<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_ExpenseClaimPayDtl')" id="tWfCust_ExpenseClaimPayDtl_btn_temInx">增加明细</button>
								</div>
							</div>	
						</div>	
					</div>
				</div>
				
				<c:if test="${((activityId == 'usertask17' || activityId == 'usertask28') && cmpcode == '01') || (activityId == 'usertask13' && cmpcode != '01')}">
					<div class="validate-group row" style="border-top:1px solid rgb(223,223,223);height:45px">
						<li class="form-validate" style="padding-top:10px">
					        <label><span class="required">*</span>流程结束</label>
					        <select id="TaskContinue" name ="TaskContinue">
	 							<option value ="否" checked>否</option>
	 							<option value ="是">是</option>
	 						</select>
					    </li>
					</div>
				</c:if>
				<c:if test="${wfProcess.remarks !=''}">
					<div id="tips" style="border-top:1px solid #dfdfdf">
						<div style="margin-left:60px;margin-top:8px;color:red;font-weight:bold">
							${wfProcess.remarks}
						</div>
					</div>
				</c:if>
			</ul>
		</form>
	</div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var html_ = $("#detail_div").html();
var detailJson=${detailJson};

function setElMap(){
	elMaps = {Amount:myRound(Math.floor($("#fp__tWfCust_ExpenseClaim__0__Amount").val()),0),
		 	 IsCommon:$("#fp__tWfCust_ExpenseClaim__0__IsCommon").val(),
			 EmpType:$.trim($("#fp__tWfCust_ExpenseClaim__0__EmpType").val()==""?"${employee.depType}":$("#fp__tWfCust_ExpenseClaim__0__EmpType").val()),		 
			 Company:$.trim($("#fp__tWfCust_ExpenseClaim__0__Company").val()),
			 Company:$.trim($("#fp__tWfCust_ExpenseClaim__0__Company").val()),
			 SKIP_NEXTTASK:"否"
		};
}

function validateRefreshData(){
 	$("#dataForm").data("bootstrapValidator")
          .updateStatus("openComponent_employee_EmpCode", "NOT_VALIDATED",null)  
          .validateField("openComponent_employee_EmpCode")
          .updateStatus("fp__tWfCust_ExpenseClaim__0__ActName", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_ExpenseClaim__0__ActName")
          .updateStatus("fp__tWfCust_ExpenseClaim__0__CardId", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_ExpenseClaim__0__CardId")
          .updateStatus("fp__tWfCust_ExpenseClaim__0__Bank", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_ExpenseClaim__0__Bank")
          .updateStatus("fp__tWfCust_ExpenseClaim__0__Amount", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_ExpenseClaim__0__Amount"); 
}

$(function(){
	detailVerify = [
		{
			tableId:"tWfCust_ExpenseClaim",
			activityId:"usertask17",
			mainTable:"1",
			ApproveAmount:"审批金额不能为空",
		}
	];

	if("${cmpcode }" != ""){
		if("01" == "${cmpcode }" || "05" == "${cmpcode }" || "03" == "${cmpcode }"){
			$("#fp__tWfCust_ExpenseClaim__0__Company").find("option").remove();
			$("#fp__tWfCust_ExpenseClaim__0__Company").append("<option value= " + "福州有家" + ">" + "福州有家" + "</option>");
			$("#fp__tWfCust_ExpenseClaim__0__Company").append("<option value= " + "美第家居" + ">" + "美第家居" + "</option>");
			$("#fp__tWfCust_ExpenseClaim__0__Company").append("<option value= " + "福清有家" + ">" + "福清有家" + "</option>");
			$("#fp__tWfCust_ExpenseClaim__0__Company").append("<option value= " + "长乐有家" + ">" + "长乐有家" + "</option>");
			detailVerify = [
				{
					tableId:"tWfCust_ExpenseClaim",
					activityId:"usertask17",
					mainTable:"1",
					ApproveAmount:"审批金额不能为空",
					DocumentNo: "凭证号为空",
				}
			];
		}
		if("02" == "${cmpcode }"){
			$("#fp__tWfCust_ExpenseClaim__0__Company").find("option").remove();
			$("#fp__tWfCust_ExpenseClaim__0__Company").append("<option value= " + "厦门有家" + ">" + "厦门有家" + "</option>");
			$("#fp__tWfCust_ExpenseClaim__0__Company").append("<option value= " + "漳州有家" + ">" + "漳州有家" + "</option>");
			$("#fp__tWfCust_ExpenseClaim__0__Company").append("<option value= " + "美第家居" + ">" + "美第家居" + "</option>");
			$("#fp__tWfCust_ExpenseClaim__0__Company").append("<option value= " + "美第奇" + ">" + "美第奇" + "</option>");
		}
		if("04" == "${cmpcode }"){
			$("#fp__tWfCust_ExpenseClaim__0__Company").find("option").remove();
			$("#fp__tWfCust_ExpenseClaim__0__Company").append("<option value= " + "郑州有家" + ">" + "郑州有家" + "</option>");
			$("#fp__tWfCust_ExpenseClaim__0__Company").append("<option value= " + "美第奇" + ">" + "美第奇" + "</option>");
		}
		if("${m_umState}" != "A"){
			$("#fp__tWfCust_ExpenseClaim__0__Company").attr("disabled",true);
		}
		if("${datas.fp__tWfCust_ExpenseClaim__0__Company }" != ""){
			$("#fp__tWfCust_ExpenseClaim__0__Company").val("${datas.fp__tWfCust_ExpenseClaim__0__Company }");
		}
	}
	
	function getEmpData(data){
		if(!data){
			return;
		}
		if(data.cmpDescr){
			$("#fp__tWfCust_ExpenseClaim__0__Company").val(data.cmpDescr);
		}
	}
	document.getElementById("tab_apply_fjView_li").style.display="block";
	$("#fp__tWfCust_ExpenseClaim__0__WfExpenseAdvanceNo").attr("readonly",true);
	if("A"=="${m_umState}"){
		$("#EmpCode").openComponent_employee({showValue:"${employee.number }",showLabel:"${employee.nameChi}",readonly:true,callBack:getEmpData});
		$("#openComponent_employee_EmpCode").blur();
		$("#fp__tWfCust_ExpenseClaim__0__EmpCode").val("${employee.number }");
		$("#fp__tWfCust_ExpenseClaim__0__EmpName").val("${employee.nameChi }");
		$("#fp__tWfCust_ExpenseClaim__0__BefAmount").val("${employee.advanceAmount}");
		getOperator_();
		$("#fp__tWfCust_ExpenseClaim__0__EmpType").val("${employee.depType}");
	} else {
		$("#EmpCode").openComponent_employee({callBack:getEmpDetail,showValue:"${datas.fp__tWfCust_ExpenseClaim__0__EmpCode}",
							showLabel:"${datas.fp__tWfCust_ExpenseClaim__0__EmpName}" });	
		getClaimAmount("${datas.fp__tWfCust_ExpenseClaim__0__EmpCode}");
	}
	
	function getEmpDetail(data){
		if(!data) return;
		$("#fp__tWfCust_ExpenseClaim__0__EmpCode").val(data.number);
		$("#fp__tWfCust_ExpenseClaim__0__EmpName").val(data.namechi);
		getClaimAmount(data.number);
	}
	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
		     openComponent_employee_EmpCode:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "报销人不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseClaim__0__ActName:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "户名不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseClaim__0__Bank:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "开户行不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseClaim__0__Deduction:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "请选择是否抵扣预支",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseClaim__0__CardId:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "卡号不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseClaim__0__SubBranch:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "开户行不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseClaim__0__Amount:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "请填写明细报销额",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseClaim__0__Company: {
		        validators: {  
		            notEmpty: {  
		           		 message: "分公司不能为空",
		            },
		        }  
		     }
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
	// 如果有明细 则添加明细 ，如果没有 则判断页面是否有明细模板 有的话添加 第一个模板
	var detailList = ${detailList};
	if(detailList != ""){
		for (var tableName in detailList) {
			if($("#"+tableName).find("#group_temp").html()){
		       	addDetail_("",detailList[tableName]==0?0:detailList[tableName]-1,tableName);
			}
	    }
	}
	if("A" != "${m_umState}"){
		chgMainAmount(false);
	} else {
		$("#fp__tWfCust_ExpenseClaim__0__IsCommon").val("是");
	}
});

function chgType(){
	getOperator_();
}

function addDetail_(flag,inx,groupId){
	var html = $("#"+groupId).find("#group_temp").html();
	addDetail(flag,html,inx,"returnFn",groupId);
}

function returnFn(flag, i, groupId){
	if(flag != "add"){
		if(groupId == "tWfCust_ExpenseClaimDtl"){
			$("#fp__tWfCust_ExpenseClaimDtl__"+i+"__PK").val(detailJson["fp__tWfCust_ExpenseClaimDtl__"+i+"__PK"]);
			$("#fp__tWfCust_ExpenseClaimDtl__"+i+"__DtlAmount").val(detailJson["fp__tWfCust_ExpenseClaimDtl__"+i+"__DtlAmount"]);
			$("#fp__tWfCust_ExpenseClaimDtl__"+i+"__Remarks").val(detailJson["fp__tWfCust_ExpenseClaimDtl__"+i+"__Remarks"]);
			$("#fp__tWfCust_ExpenseClaimDtl__"+i+"__DeptDescr").val(detailJson["fp__tWfCust_ExpenseClaimDtl__"+i+"__DeptDescr"]);
			$("#fp__tWfCust_ExpenseClaimDtl__"+i+"__DeptCode").val(detailJson["fp__tWfCust_ExpenseClaimDtl__"+i+"__DeptCode"]);
			$("#deptCode_"+i).openComponent_department({callBack:getDeptDetail,
				 showLabel:detailJson["fp__tWfCust_ExpenseClaimDtl__"+i+"__DeptDescr"],
				showValue:detailJson["fp__tWfCust_ExpenseClaimDtl__"+i+"__DeptCode"]});	
			$("#openComponent_department_deptCode_"+i).attr("readonly","true");
		}
		if(groupId == "tWfCust_ExpenseClaimAdvanceDtl"){
			$("#fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__PK").val(detailJson["fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__PK"]);
			$("#fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__WfExpenseAdvanceNo").val(detailJson["fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__WfExpenseAdvanceNo"]);
			$("#fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__AdvanceAmount").val(detailJson["fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__AdvanceAmount"]);
			$("#fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__DeductionAmount").val(detailJson["fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__DeductionAmount"]);
		}
		if(groupId == "tWfCust_ExpenseClaimPayDtl"){
			var options = $("#conSignCmp_temInx option").clone();
			$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompanyCode").append(options);
			$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PK").val(detailJson["fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PK"]);
			$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__WfExpenseAdvanceNo").val(detailJson["fp__tWfCust_ExpenseClaimPayDtl__"+i+"__WfExpenseAdvanceNo"]);
			$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompanyCode").val(detailJson["fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompanyCode"]);
			$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompany").val(detailJson["fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompany"]);
			$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayType").val(detailJson["fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayType"]);
			$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayPurpose").val(detailJson["fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayPurpose"]);
			$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayAmount").val(detailJson["fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayAmount"]);
			if("${taskName}" == "财务会计审核" && groupId == "tWfCust_ExpenseClaimPayDtl" && i == 0){
				$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompanyCode").val("${conSignCmp }");
				var selectText = $("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompanyCode").find("option:selected").text();
				$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompany").val(selectText);
				$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayAmount").val("${datas.fp__tWfCust_ExpenseClaim__0__Amount}");
				$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayType").val("银行转账");
				$("#fp__tWfCust_ExpenseClaim__0__ApproveAmount").val("${datas.fp__tWfCust_ExpenseClaim__0__Amount}");
			}
		}
	}else{
		$("#deptCode_"+i).openComponent_department({callBack:getDeptDetail,
			showLabel:detailJson["fp__tWfCust_ExpenseClaimDtl__"+i+"__DeptDescr"],
			showValue:detailJson["fp__tWfCust_ExpenseClaimDtl__"+i+"__DeptCode"]});	
		$("#openComponent_department_deptCode_"+i).attr("readonly","true");
		
		if(groupId == "tWfCust_ExpenseClaimPayDtl"){
			var options = $("#conSignCmp_temInx option").clone();
			$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompanyCode").append(options);
		}
	}
	
	$("#dataForm").bootstrapValidator("addField", "deptCode_"+i, {
        validators: {  
            notEmpty: {  
           		 message: "报销部门不能为空",
            },
        }  
	}).bootstrapValidator("addField", "fp__tWfCust_ExpenseClaimDtl__" + i + "__Remarks", {
        validators: {  
            notEmpty: {  
           		 message: "报销内容不能为空",
            },
        }  
	}).bootstrapValidator("addField", "fp__tWfCust_ExpenseClaimDtl__" + i + "__DtlAmount", {
        validators: {  
            notEmpty: {  
           		 message: "报销金额不能为空",
            },
        }  
	});
}

function getDeptDetail(data){
	var id = this.id.split("openComponent_department_deptCode_")[1];
	$("#fp__tWfCust_ExpenseClaimDtl__"+id+"__DeptCode").val(data.code);
	$("#fp__tWfCust_ExpenseClaimDtl__"+id+"__DeptDescr").val(data.desc2);

	$("#dataForm").data("bootstrapValidator")
	                 .updateStatus("deptCode_" + id, "NOT_VALIDATED",null)  
	                 .validateField("deptCode_" + id);   
}

function clearDetail(){
	$("#fp__tWfCust_ExpenseClaimDtl__0__DeptCode").val("");
	$("#fp__tWfCust_ExpenseClaimDtl__0__DeptDescr").val("");
	$("#fp__tWfCust_ExpenseClaimDtl__0__DtlAmount").val("");
	$("#fp__tWfCust_ExpenseClaimDtl__0__Remarks").val("");
}

//调用WfProcinst_apply 页面的getOperator方法
function getOperator_(flag){
	if(flag != true){
		callSetReadonly();
	}
	$("#TaskContinue").removeAttr("disabled","true");
	if("${m_umState}" == "A"){
		$("#fp__tWfCust_ExpenseClaim__0__ApproveAmount").attr("readonly",true);
	}
	var elMap = {Amount:myRound(Math.floor($("#fp__tWfCust_ExpenseClaim__0__Amount").val()),0),
			 	IsCommon:$("#fp__tWfCust_ExpenseClaim__0__IsCommon").val(),
				 EmpType:$.trim($("#fp__tWfCust_ExpenseClaim__0__EmpType").val()==""?"${employee.depType}":$("#fp__tWfCust_ExpenseClaim__0__EmpType").val()),		 
				 Company:$.trim($("#fp__tWfCust_ExpenseClaim__0__Company").val()),
				 SKIP_NEXTTASK:"否"
				};
	getOperator(flag,elMap);
} 

function chgMainAmount(flag){
	var length = $("#tWfCust_ExpenseClaimDtl").children("div").length-1;
	var amount = 0;
	var returnAmount =0.0;
	for(var i=0;i<20;i++){
		if($("#fp__tWfCust_ExpenseClaimDtl__"+i+"__DtlAmount").val()){
			returnAmount = $("#fp__tWfCust_ExpenseClaimDtl__"+i+"__DtlAmount").val().replace(/[^\-?\d.]/g,'');
			$("#fp__tWfCust_ExpenseClaimDtl__"+i+"__DtlAmount").val(returnAmount);
			if($("#fp__tWfCust_ExpenseClaimDtl__"+i+"__DtlAmount").val()){
				amount =myRound(returnAmount,2) + myRound(amount,4);
			} ;
		};
	}
	$("#fp__tWfCust_ExpenseClaim__0__Amount").val(myRound(amount,4));
	if(flag){
		getOperator_(flag);
	}
	validateRefreshData();
	//moneyToCapital(document.getElementById("fp__tWfCust_PrjReturnSet__0__ReturnAmount"),"capital");
}

function calcDeductionAmount(){
	var length = $("#tWfCust_ExpenseClaimAdvanceDtl").children("div").length-1;
	var amount = 0;
	var returnAmount =0.0;
	for(var i=0;i<20;i++){
		if($("#fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__DeductionAmount").val()){
			returnAmount = $("#fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__DeductionAmount").val().replace(/[^\-?\d.]/g,'');
			$("#fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__DeductionAmount").val(returnAmount);
			if($("#fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__DeductionAmount").val()){
				amount =myRound(returnAmount,2) + myRound(amount,4);
			} ;
		};
	}
	$("#fp__tWfCust_ExpenseClaim__0__DeductionAmount").val(myRound(amount,4));
	$("#confAmount").val(myRound($("#fp__tWfCust_ExpenseClaim__0__DeductionAmount").val(),4));
	validateRefreshData();
}

function chgApproveAmount(){
	
	$("#confAmount").val(myRound($("#fp__tWfCust_ExpenseClaim__0__DeductionAmount").val(),4));
}

function delDetalReturnFn(index, groupId){
	if("tWfCust_ExpenseClaimDtl" == groupId){
		chgMainAmount(true);
		$("#dataForm").bootstrapValidator("removeField", "deptCode_" + index)
					  .bootstrapValidator("removeField", "fp__tWfCust_ExpenseClaimDtl__" + index + "__Remarks")
					  .bootstrapValidator("removeField", "fp__tWfCust_ExpenseClaimDtl__" + index + "__DtlAmount");
	}
	if("tWfCust_ExpenseClaimAdvanceDtl" == groupId){
		calcDeductionAmount();
	}
	if("tWfCust_ExpenseClaimPayDtl" == groupId){
		chgPayAmount();
	}
}

function getEmpAccount(){
	Global.Dialog.showDialog("EmpAccount",{
		title:"选择银行账号",
		url:"${ctx}/admin/wfProcInst/goEmpAccount",
		postData:{czybh:$.trim($("#EmpCode").val())},
		height: 640,
		width:900,
		returnFun:function(data){
			$("#fp__tWfCust_ExpenseClaim__0__ActName").val(data.actname);
			$("#fp__tWfCust_ExpenseClaim__0__Bank").val(data.bank);
			$("#fp__tWfCust_ExpenseClaim__0__CardId").val(data.cardid.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{4})|(\d{4}))(?=\d)/g,'$1 '));
			$("#fp__tWfCust_ExpenseClaim__0__SubBranch").val(data.subbranch);
			validateRefreshData();
		}
	});
}

function getAdvanceNo(inx){
	Global.Dialog.showDialog("EmpAccount",{
		title:"选择预支单",
		url:"${ctx}/admin/wfProcInst/getAdvanceNo",
		postData:{czybh:"${datas.fp__tWfCust_ExpenseClaim__0__EmpCode}"},
		height: 640,
		width:900,
		returnFun:function(data){
			$("#fp__tWfCust_ExpenseClaimAdvanceDtl__"+inx+"__WfExpenseAdvanceNo").val(data.no);
			$("#fp__tWfCust_ExpenseClaimAdvanceDtl__"+inx+"__AdvanceAmount").val(data.amount);
		}
	});
}

function saveAccount(){
	$.ajax({
		url:"${ctx}/admin/wfProcInst/doSaveAccount",
		type: 'post',
		data: {actName:$("#fp__tWfCust_ExpenseClaim__0__ActName").val(),
				bank:$("#fp__tWfCust_ExpenseClaim__0__Bank").val(),
				cardId:$("#fp__tWfCust_ExpenseClaim__0__CardId").val(),
				subBranch:$("#fp__tWfCust_ExpenseClaim__0__SubBranch").val()
			},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
			
	    }
	});
}
/*
function doApprovalTask_(){
	if("usertask18" == "${activityId}"){
		$("#comment").val($("#artDialogComment").val());
		$("#dataForm input[type='text']").removeAttr("disabled","true");
		$("#dataForm select").removeAttr("disabled","true");
		var datas = $("#dataForm").serialize();
		if("" == $.trim($("#fp__tWfCust_ExpenseClaim__0__ApproveAmount").val())){
			art.dialog({
				content:"请填写审批金额！",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/wfProcInst/doApprFinanceTask",
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
	   				$("#_form_token_uniq_id").val(obj.datas.token);
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
							closeWin();
					    }
					});
		    	}else{
		    		$("#dataForm input[type='text']").attr("disabled","true");
		    		$("#dataForm select").attr("disabled","true");
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
		});
	} else {
		doApprovalTask();
	}
}
*/
//获取借款余额
function getClaimAmount(empCode){
	$.ajax({
		url:"${ctx}/admin/wfProcInst/getAdvanceAmount",
		type:"post",	
		data:{empCode:empCode},
		dataType:"json",
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "出错~"});
		},
		success:function(obj){
			$("#fp__tWfCust_ExpenseClaim__0__BefAmount").val(obj);
		}
	});
}

function callSetReadonly(actId){
	setReadonly(actId);
	$("#fp__tWfCust_ExpenseClaim__0__ApproveAmount").attr("readonly",true);
}

function chgPayAmount(){
	var length = $("#tWfCust_ExpenseClaimPayDtl").children("div").length-1;
	var amount = 0;
	var confirmAmount =0.0;
	for(var i=0;i<20;i++){
		if($("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayAmount").val()){
			confirmAmount = $("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayAmount").val().replace(/[^\-?\d.]/g,'');
			$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayAmount").val(confirmAmount);
			
			if($("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayAmount").val() && $("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayType").val()!="借款冲销"){
				amount =myRound(confirmAmount, 2) + myRound(amount, 4);
			};
			
		};
	}
	$("#fp__tWfCust_ExpenseClaim__0__ApproveAmount").val(myRound(amount,4));
}

function chgPayCmp(i){
	var selectText = $("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompanyCode").find("option:selected").text();
	$("#fp__tWfCust_ExpenseClaimPayDtl__"+i+"__PayCompany").val(selectText);
} 

</script>
</body>
</html>

