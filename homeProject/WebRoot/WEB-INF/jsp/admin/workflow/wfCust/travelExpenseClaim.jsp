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
    		<input id="photoPK" name="photoPK" value="" hidden="true" /><%--有上传图片时必备--%>
    		<input id="url" name="url" value="${url }" hidden="true" /><%--有上传图片时必备--%>
    		<input id="activityId" name="activityId" value="${activityId }" hidden="true" />
    		<input id="confAmount" name="confAmount" value="${datas.fp__tWfCust_TravelExpenseClaim__0__DeductionAmount }" hidden="true"/>
    		<input id="fp__tWfCust_TravelExpenseClaim__0__IsCommon" name="fp__tWfCust_TravelExpenseClaim__0__IsCommon" value="是" hidden="true" />
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row">
	         		<li class="form-validate">
				        <label><span class="required">*</span>报销人</label>
						<input type="text" id="EmpCode" name="EmpCode" style="width:160px;" value="${datas.fp__tWfCust_TravelExpenseClaim__0__EmpCode}"/>
   						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__EmpCode" name="fp__tWfCust_TravelExpenseClaim__0__EmpCode"  style="width:160px;" value="${datas.fp__tWfCust_TravelExpenseClaim__0__EmpCode}" hidden="true"/>
   						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__EmpName" name="fp__tWfCust_TravelExpenseClaim__0__EmpName"  style="width:160px;" value="${datas.fp__tWfCust_TravelExpenseClaim__0__EmpName}" hidden="true"/>
				   		<input id="fp__tWfCust_TravelExpenseClaim__0__EmpType" name="fp__tWfCust_TravelExpenseClaim__0__EmpType" value="${datas.fp__tWfCust_TravelExpenseClaim__0__EmpType}" hidden="true"/>
				    </li>
				    <li class="form-validate">
						<label><span class="required">*</span>分公司</label>
						<select id="fp__tWfCust_TravelExpenseClaim__0__Company" name="fp__tWfCust_TravelExpenseClaim__0__Company" 
								value="${datas.fp__tWfCust_TravelExpenseClaim__0__Company }" onchange="getOperator_()">
							<option value ="福州有家" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Company == '福州有家' ? 'selected' : ''}>福州有家</option>
							<option value ="美第家居" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Company == '美第家居' ? 'selected' : ''}>美第家居</option>
							<option value ="长乐有家" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Company == '长乐有家' ? 'selected' : ''}>长乐有家</option>
							<option value ="福清有家" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Company == '福清有家' ? 'selected' : ''}>福清有家</option>
							<option value ="厦门有家" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Company == '厦门有家' ? 'selected' : ''}>厦门有家</option>
							<option value ="郑州有家" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Company == '郑州有家' ? 'selected' : ''}>郑州有家</option>
							<option value ="福清有家" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Company == '福清有家' ? 'selected' : ''}>福清有家</option>
							<option value ="长乐有家" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Company == '长乐有家' ? 'selected' : ''}>长乐有家</option>
							<option value ="美第奇" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Company == '美第奇' ? 'selected' : ''}>美第奇</option>
						</select>
					</li>
				    <li class="form-validate">
				        <label><span class="required">*</span>抵扣预支</label>
						<select id="fp__tWfCust_TravelExpenseClaim__0__Deduction" name ="fp__tWfCust_TravelExpenseClaim__0__Deduction">
							<option value =""   >请选择...</option>
 							<option value ="否" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Deduction== '否' ? 'selected' : ''}>否</option>
 							<option value ="是" ${ datas.fp__tWfCust_TravelExpenseClaim__0__Deduction== '是' ? 'selected' : ''}>是</option>
 						</select>	
					</li>
					<li class="form-validate">
				        <label>借款余额</label>
   						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__BefAmount" name="fp__tWfCust_TravelExpenseClaim__0__BefAmount" 
   								value="${datas.fp__tWfCust_TravelExpenseClaim__0__BefAmount}" style="width:160px;" readonly="true"/>
				    </li>
				</div>
				<div class="validate-group row">
					<li class="form-validate">
				        <label><span class="required">*</span>账户名</label>
						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__ActName" name="fp__tWfCust_TravelExpenseClaim__0__ActName"  
								style="width:136px;border-top-right-radius:0;border-bottom-right-radius:0" value="${datas.fp__tWfCust_TravelExpenseClaim__0__ActName}"/>
					    <button type="button" class="btn btn-system" data-disabled="false" 
					    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -5px;width:26px;height:24px;padding-top:2.5px" onclick="getEmpAccount()">
					    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
					    </button>
				    </li>	
	         		<li class="form-validate">
				        <label><span class="required">*</span>开户行</label>
						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__Bank" name="fp__tWfCust_TravelExpenseClaim__0__Bank"  style="width:160px;" value="${datas.fp__tWfCust_TravelExpenseClaim__0__Bank}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>支行</label>
						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__SubBranch" name="fp__tWfCust_TravelExpenseClaim__0__SubBranch" 
							 style="width:160px;" value="${datas.fp__tWfCust_TravelExpenseClaim__0__SubBranch}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>卡号</label>
						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__CardId" name="fp__tWfCust_TravelExpenseClaim__0__CardId"  
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{4})|(\d{4}))(?=\d)/g,'$1 ');"
								style="width:160px;" value="${datas.fp__tWfCust_TravelExpenseClaim__0__CardId}"/>
				    </li>
				    
				</div>
				<div class="validate-group row" >	
					<li class="form-validate">
				        <label>报销额</label>
						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__Amount" name="fp__tWfCust_TravelExpenseClaim__0__Amount" 
							onchange="getOperator_()" placeholder="自动计算" style="width:160px;" value="${datas.fp__tWfCust_TravelExpenseClaim__0__Amount}" readonly="true"/>
				    </li>
					<li class="form-validate">
						<label>同行人</label>
						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__Partner" name="fp__tWfCust_TravelExpenseClaim__0__Partner"  
								style="width:136px;border-top-right-radius:0;border-bottom-right-radius:0"
								value="${datas.fp__tWfCust_TravelExpenseClaim__0__Partner}"/>
						<button type="button" class="btn btn-system" data-disabled="false" 
								style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -5px;width:26px;height:24px;padding-top:2.5px" 
									onclick="getPartner()">
							<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
						</button>
					</li>
					<li class="form-validate">
						<label class="control-textarea">出差事由</label>
						<textarea id="fp__tWfCust_TravelExpenseClaim__0__Remarks" name="fp__tWfCust_TravelExpenseClaim__0__Remarks" rows="2">${datas.fp__tWfCust_TravelExpenseClaim__0__Remarks}</textarea>
					</li>
				</div>
				<div class="validate-group row" >	
					<li class="form-validate">
				        <label>抵扣金额</label>
						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__DeductionAmount" name="fp__tWfCust_TravelExpenseClaim__0__DeductionAmount" 
							onchange="calcDeductionAmount() "style="width:160px;" value="${datas.fp__tWfCust_TravelExpenseClaim__0__DeductionAmount}" readonly="true"/>
				    </li>
				    <li class="form-validate">
					    <label><span class="required">*</span>审批金额</label>
						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__ApproveAmount" name="fp__tWfCust_TravelExpenseClaim__0__ApproveAmount" 
								 style="width:160px;" value="${datas.fp__tWfCust_TravelExpenseClaim__0__ApproveAmount}" readonly="true"/>
					</li>
					<li class="form-validate">
				        <label>凭证号</label>
						<input type="text" id="fp__tWfCust_TravelExpenseClaim__0__DocumentNo" name="fp__tWfCust_TravelExpenseClaim__0__DocumentNo" 
							 style="width:160px;" value="${datas.fp__tWfCust_TravelExpenseClaim__0__DocumentNo}" readonly="true"/>
				    </li>
				</div>
				<%--<div id="tWfCust_TravelExpensePartner">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_TravelExpensePartner_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px;margin-left:15px">
								<input id="fp__tWfCust_TravelExpensePartner__temInx__PK" name="fp__tWfCust_TravelExpensePartner__temInx__PK" value="${datas.fp__tWfCust_TravelExpensePartner__temInx__PK }" hidden="true"/>
								<input id="fp__tWfCust_TravelExpensePartner__temInx__EmpCode" name="fp__tWfCust_TravelExpensePartner__temInx__EmpCode" value="${datas.fp__tWfCust_TravelExpensePartner__temInx__EmpCode }" hidden="true"/>
					    		<input id="fp__tWfCust_TravelExpensePartner__temInx__EmpDescr" name="fp__tWfCust_TravelExpensePartner__temInx__EmpDescr"  value="${datas.fp__tWfCust_TravelExpensePartner__temInx__EmpDescr }" hidden="true"/>
								<div class="validate-group row" >	
									<li class="form-validate">
										<label>同行人</label>
										<input type="text" id="empCode_temInx" name="empCode_temInx"  style="width:160px;"/>
									</li>
									<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_TravelExpensePartner')" id="btn_temInx">增加明细</button>
								</div>	
							</div>	
						</div>	
					</div>
				</div>
				--%>
				<div id="tWfCust_TravelExpenseClaimDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_TravelExpenseClaimDtl_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px;margin-left:15px">
								<input id="fp__tWfCust_TravelExpenseClaimDtl__temInx__PK" name="fp__tWfCust_TravelExpenseClaimDtl__temInx__PK" value="${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__PK }" hidden="true"/>
								<div class="validate-group row" >	
									<li class="form-validate">
										<label style="width:75px">出发时间</label>
										<input type="text" style="width:100px;" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__BeginDate" 
												name="fp__tWfCust_TravelExpenseClaimDtl__temInx__BeginDate"
								   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',width:'90px'})" 
								   			 value="<fmt:formatDate value='${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__BeginDate}' 
								   			 pattern='yyyy-MM-dd hh:mm:dd'/>" />
								    </li>
								    <li class="form-validate">
										<label style="width:75px">回程时间</label>
										<input type="text" style="width:100px;" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__EndDate" 
												name="fp__tWfCust_TravelExpenseClaimDtl__temInx__EndDate"
									   			 style="width:100px;"
									   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									   			 value="<fmt:formatDate value='${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__EndDate}' 
									   			 pattern='yyyy-MM-dd hh:mm:dd'/>" />
								    </li>
								    <li class="form-validate">
										<label style="width:75px">出发地</label>
										<input type="text" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__Origin" name="fp__tWfCust_TravelExpenseClaimDtl__temInx__Origin" 
											 style="width:100px;" value="${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__Origin}"/>
								    </li>
								    <li class="form-validate">
										<label style="width:75px">目的地</label>
										<input type="text" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__Destination" name="fp__tWfCust_TravelExpenseClaimDtl__temInx__Destination" 
											 style="width:100px;" value="${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__Destination}"/>
								    </li>
								    <li class="form-validate">
										<label style="width:75px">交通费</label>
										<input type="text" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__TransportFee" name="fp__tWfCust_TravelExpenseClaimDtl__temInx__TransportFee" 
											 style="width:100px;" value="${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__TransportFee}" onchange="changeFee(true)"/>
								    </li>
								    <li class="form-validate">
										<label style="width:75px">餐费</label>
										<input type="text" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__FoodFee" name="fp__tWfCust_TravelExpenseClaimDtl__temInx__FoodFee" 
											 style="width:100px;" value="${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__FoodFee}" onchange="changeFee(true)"/>
								    </li>
								</div>
								<div class="validate-group row" >	
								    <li class="form-validate">
										<label style="width:75px">晚数</label>
										<input type="text" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__NightNumber" name="fp__tWfCust_TravelExpenseClaimDtl__temInx__NightNumber" 
											 style="width:100px;" value="${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__NightNumber}"/>
								    </li>
								    <li class="form-validate">
										<label style="width:75px">住宿费</label>
										<input type="text" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__HotelFee" name="fp__tWfCust_TravelExpenseClaimDtl__temInx__HotelFee" 
											 style="width:100px;" value="${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__HotelFee}" onchange="changeFee(true)"/>
								    </li>
								    <li class="form-validate">
										<label style="width:75px">其他天数</label>
										<input type="text" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__OtherDayNumber" name="fp__tWfCust_TravelExpenseClaimDtl__temInx__OtherDayNumber" 
											 style="width:100px;" value="${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__OtherDayNumber}"/>
								    </li>
								    <li class="form-validate">
										<label style="width:75px">其他费用</label>
										<input type="text" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__OtherFee" name="fp__tWfCust_TravelExpenseClaimDtl__temInx__OtherFee" 
											 style="width:100px;" value="${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__OtherFee}" onchange="changeFee(true)"/>
								    </li>
								    <li class="form-validate">
										<label style="width:75px">小计</label>
										<input type="text" id="fp__tWfCust_TravelExpenseClaimDtl__temInx__Amount" name="fp__tWfCust_TravelExpenseClaimDtl__temInx__Amount" 
											 style="width:100px;" value="${datas.fp__tWfCust_TravelExpenseClaimDtl__temInx__Amount}" readonly="true"/>
								    </li>
									<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_TravelExpenseClaimDtl')" id="btn_temInx">增加明细</button>
								</div>
							</div>	
						</div>	
					</div>
				</div>
				
				<div id="tWfCust_TravelClaimAdvanceDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_TravelClaimAdvanceDtl_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px;margin-left:15px">
								<input type = "text" id="fp__tWfCust_TravelClaimAdvanceDtl__temInx__PK" name="fp__tWfCust_TravelClaimAdvanceDtl__temInx__PK" 
									hidden="true" value="${datas.fp__tWfCust_TravelClaimAdvanceDtl__temInx__PK }" />
								<div class="validate-group row">
									<li class="form-validate">
								        <label><span class="required">*</span>预支单</label>
										<input type="text" id="fp__tWfCust_TravelClaimAdvanceDtl__temInx__WfExpenseAdvanceNo" 
												name="fp__tWfCust_TravelClaimAdvanceDtl__temInx__WfExpenseAdvanceNo"  
												style="width:136px;border-top-right-radius:0;border-bottom-right-radius:0" 
												value="${datas.fp__tWfCust_TravelClaimAdvanceDtl__temInx__WfExpenseAdvanceNo}"/>
									    <button type="button" class="btn btn-system" data-disabled="false" 
									    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -5px;width:26px;height:24px;padding-top:2.5px" onclick="getAdvanceNo(temInx)">
									    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
									    </button>
								    </li>	
					         		<li class="form-validate">
								        <label><span class="required">*</span>预支单金额</label>
										<input type="text" id="fp__tWfCust_TravelClaimAdvanceDtl__temInx__AdvanceAmount" 
													name="fp__tWfCust_TravelClaimAdvanceDtl__temInx__AdvanceAmount"  style="width:160px;"
										 	  value="${datas.fp__tWfCust_TravelClaimAdvanceDtl__temInx__AdvanceAmount}"/>
									</li> 	
									<li class="form-validate">
								        <label><span class="required">*</span>抵扣金额</label>
										<input type="text" id="fp__tWfCust_TravelClaimAdvanceDtl__temInx__DeductionAmount" 
											name="fp__tWfCust_TravelClaimAdvanceDtl__temInx__DeductionAmount"  style="width:160px;"
										 value="${datas.fp__tWfCust_TravelClaimAdvanceDtl__temInx__DeductionAmount}" onchange="calcDeductionAmount()"/>
									</li>
									<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_TravelClaimAdvanceDtl')" id="btn_temInx">增加明细</button>
								</div>
							</div>	
						</div>	
					</div>
				</div>
				
				<c:if test="${((activityId == 'usertask17' || activityId == 'usertask28') && cmpcode == '01') || (cmpcode != '01' && activityId == 'usertask14') }">
					<div class="validate-group row" style="border-top:1px solid rgb(223,223,223);height:45px">
						<li class="form-validate" style="padding-top:10px">
					        <label><span class="required">*</span>流程结束</label>
					        <select id="TaskContinue" name ="TaskContinue">
	 							<option value ="否">否</option>
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
	var object1 = {};

function setElMap(){
	elMaps = {Amount:myRound(Math.floor($("#fp__tWfCust_TravelExpenseClaim__0__Amount").val()),0),
			 IsCommon:$("#fp__tWfCust_TravelExpenseClaim__0__IsCommon").val(),
			 EmpType:$.trim($("#fp__tWfCust_TravelExpenseClaim__0__EmpType").val()==""?"${employee.depType}":$("#fp__tWfCust_TravelExpenseClaim__0__EmpType").val()),		 
			 Company:$.trim($("#fp__tWfCust_TravelExpenseClaim__0__Company").val()),
			 SKIP_NEXTTASK:"否"
		};
}
function validateRefreshData(){
	
 	$("#dataForm").data("bootstrapValidator")
          .updateStatus("openComponent_employee_EmpCode", "NOT_VALIDATED",null)  
          .validateField("openComponent_employee_EmpCode")
          .updateStatus("fp__tWfCust_TravelExpenseClaim__0__ActName", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_TravelExpenseClaim__0__ActName")
          .updateStatus("fp__tWfCust_TravelExpenseClaim__0__CardId", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_TravelExpenseClaim__0__CardId")
          .updateStatus("fp__tWfCust_TravelExpenseClaim__0__Bank", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_TravelExpenseClaim__0__Bank")
          .updateStatus("fp__tWfCust_TravelExpenseClaim__0__Bank", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_TravelExpenseClaim__0__SubBranch")
          .updateStatus("fp__tWfCust_TravelExpenseClaim__0__Amount", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_TravelExpenseClaim__0__Amount"); 
}

$(function(){
	detailVerify = [
		{
			tableId:"tWfCust_TravelExpenseClaim",
			activityId:"usertask17",
			mainTable:"1",
			ApproveAmount:"审批金额不能为空",
		}
	];
	
	// 按公司显示公司下拉选择项
	if("${cmpcode }" != ""){
		if("01" == "${cmpcode }" || "05" == "${cmpcode }" || "03" == "${cmpcode }"){
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").find("option").remove();
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").append("<option value= " + "福州有家" + ">" + "福州有家" + "</option>");
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").append("<option value= " + "美第家居" + ">" + "美第家居" + "</option>");
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").append("<option value= " + "福清有家" + ">" + "福清有家" + "</option>");
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").append("<option value= " + "长乐有家" + ">" + "长乐有家" + "</option>");
		}
		if("02" == "${cmpcode }"){
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").find("option").remove();
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").append("<option value= " + "厦门有家" + ">" + "厦门有家" + "</option>");
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").append("<option value= " + "漳州有家" + ">" + "漳州有家" + "</option>");
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").append("<option value= " + "美第家居" + ">" + "美第家居" + "</option>");
		}
		if("04" == "${cmpcode }"){
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").find("option").remove();
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").append("<option value= " + "郑州有家" + ">" + "郑州有家" + "</option>");
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").append("<option value= " + "美第奇" + ">" + "美第奇" + "</option>");
		}
		if("${m_umState}" != "A"){
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").attr("disabled",true);
		}
		if("${datas.fp__tWfCust_TravelExpenseClaim__0__Company }" != ""){
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").val("${datas.fp__tWfCust_TravelExpenseClaim__0__Company }");
		}
	}
	
	function getEmpData(data){
		if(!data){
			return;
		}
		if(data.cmpDescr){
			$("#fp__tWfCust_TravelExpenseClaim__0__Company").val(data.cmpDescr);
			getOperator_();
		}
	}
	
	document.getElementById("tab_apply_fjView_li").style.display="block";
	$("#fp__tWfCust_TravelExpenseClaim__0__WfExpenseAdvanceNo").attr("readonly",true);
	if("A"=="${m_umState}"){
			$("#EmpCode").openComponent_employee({showValue:"${employee.number }",showLabel:"${employee.nameChi}",readonly:true,callBack:getEmpData});
			$("#openComponent_employee_EmpCode").blur();
			$("#fp__tWfCust_TravelExpenseClaim__0__EmpCode").val("${employee.number }");
			$("#fp__tWfCust_TravelExpenseClaim__0__EmpName").val("${employee.nameChi }");
			$("#fp__tWfCust_TravelExpenseClaim__0__BefAmount").val("${employee.advanceAmount}");
		//	getOperator_();
		$("#fp__tWfCust_TravelExpenseClaim__0__EmpType").val($.trim("${employee.depType}"));
	} else {
		$("#EmpCode").openComponent_employee({callBack:getEmpDetail,showValue:"${datas.fp__tWfCust_TravelExpenseClaim__0__EmpCode}",
							showLabel:"${datas.fp__tWfCust_TravelExpenseClaim__0__EmpName}" });	
		getClaimAmount("${datas.fp__tWfCust_TravelExpenseClaim__0__EmpCode}");
	}
	
	function getEmpDetail(data){
		if(!data) return;
		$("#fp__tWfCust_TravelExpenseClaim__0__EmpCode").val(data.number);
		$("#fp__tWfCust_TravelExpenseClaim__0__EmpName").val(data.namechi);
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
		           		 message: "预支人不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_TravelExpenseClaim__0__Deduction:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "请选择是否抵预支",
		            },
		        }  
		     },
		     fp__tWfCust_TravelExpenseClaim__0__ActName:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "户名不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_TravelExpenseClaim__0__Bank:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "开户行不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_TravelExpenseClaim__0__SubBranch:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "支行不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_TravelExpenseClaim__0__CardId:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "卡号不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_TravelExpenseClaim__0__Amount:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "请填写明细报销额",
		            },
		        }  
		     },
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
		changeFee(false);
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
	if(groupId == "tWfCust_TravelExpenseClaimDtl"){
		if(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__PK"] && flag != "add"){
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__PK").val(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__PK"]);
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__Amount").val(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__Amount"]);
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__BeginDate").val(formatDate(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__BeginDate"]));
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__EndDate").val(formatDate(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__EndDate"]));
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__Origin").val(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__Origin"]);
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__Destination").val(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__Destination"]);
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__TransportFee").val(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__TransportFee"]);
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__NightNumber").val(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__NightNumber"]);
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__HotelFee").val(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__HotelFee"]);
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__FoodFee").val(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__FoodFee"]);
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__OtherDayNumber").val(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__OtherDayNumber"]);
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__OtherFee").val(detailJson["fp__tWfCust_TravelExpenseClaimDtl__"+i+"__OtherFee"]);
		}
		if(flag != "add"){
			$("#empCode_"+i).openComponent_employee({callBack:getEmpDetail,
				 showLabel:detailJson["fp__tWfCust_TravelExpensePartner__"+i+"__EmpDescr"],
				showValue:detailJson["fp__tWfCust_TravelExpensePartner__"+i+"__EmpCode"]});	
		}else{
			$("#empCode_"+i).openComponent_employee({callBack:getEmpDetail,
				showLabel:detailJson["fp__tWfCust_TravelExpensePartner__"+i+"__EmpDescr"],
				showValue:detailJson["fp__tWfCust_TravelExpensePartner__"+i+"__EmpCode"]});	
		}
	}
	if(groupId == "tWfCust_TravelClaimAdvanceDtl"){					   
		$("#fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__PK").val(detailJson["fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__PK"]);
		$("#fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__WfExpenseAdvanceNo").val(detailJson["fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__WfExpenseAdvanceNo"]);
		$("#fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__AdvanceAmount").val(detailJson["fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__AdvanceAmount"]);
		$("#fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__DeductionAmount").val(detailJson["fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__DeductionAmount"]);
	}
}

function getEmpDetail(data){
	var id = this.id.split("openComponent_employee_empCode_")[1];
	$("#fp__tWfCust_TravelExpensePartner__"+id+"__EmpCode").val(data.number);
	$("#fp__tWfCust_TravelExpensePartner__"+id+"__EmpDescr").val(data.namechi);
}

function clearDetail(){
	$("#fp__tWfCust_TravelExpenseClaimDtl__0__EmoCode").val("");
	$("#fp__tWfCust_TravelExpenseClaimDtl__0__EmpDescr").val("");
}

//调用WfProcinst_apply 页面的getOperator方法
function getOperator_(flag){ 
	if(flag != true){
		callSetReadonly();
	}
	$("#TaskContinue").removeAttr("disabled","true");
	if("${m_umState}" == "A"){
		$("#fp__tWfCust_TravelExpenseClaim__0__ApproveAmount").attr("readonly",true);
	}
	var elMap = {Amount:myRound(Math.floor($("#fp__tWfCust_TravelExpenseClaim__0__Amount").val()),0),
				 IsCommon:$("#fp__tWfCust_TravelExpenseClaim__0__IsCommon").val(),
				 EmpType:$.trim($("#fp__tWfCust_TravelExpenseClaim__0__EmpType").val()==""?"${employee.depType}":$("#fp__tWfCust_TravelExpenseClaim__0__EmpType").val()),		 
				 Company:$.trim($("#fp__tWfCust_TravelExpenseClaim__0__Company").val()),
				 SKIP_NEXTTASK:"否"
				};
	getOperator(flag,elMap);
} 

function changeFee(flag){
	var length = $("#tWfCust_TravelExpenseClaimDtl").children("div").length-1;
	var amount = 0.0;
	var dtlAmount = 0.0;
	var transportFee=0.0;
	var foodFee = 0.0;
	var hotelFee = 0.0;
	var otherFee = 0.0;
	for(var i = 0; i < 20; i++){
		if($("#tWfCust_TravelExpenseClaimDtl_"+i).length>0){
			transportFee = $("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__TransportFee").val().replace(/[^\-?\d.]/g,'');
			foodFee = $("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__FoodFee").val().replace(/[^\-?\d.]/g,'');
			hotelFee = $("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__HotelFee").val().replace(/[^\-?\d.]/g,'');
			otherFee = $("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__OtherFee").val().replace(/[^\-?\d.]/g,'');
			dtlAmount = myRound(transportFee,2)+ myRound(foodFee,2)+ myRound(hotelFee ,2)+ myRound(otherFee,2);
			$("#fp__tWfCust_TravelExpenseClaimDtl__"+i+"__Amount").val(dtlAmount);
			amount += dtlAmount;
		};
	}
	$("#fp__tWfCust_TravelExpenseClaim__0__Amount").val(myRound(amount,4));
	if(flag){
		getOperator_(flag);
	}
	validateRefreshData();
}

function chgApproveAmount(){
	$("#confAmount").val(myRound($("#fp__tWfCust_TravelExpenseClaim__0__DeductionAmount").val(),4));
}

function delDetalReturnFn(){
	changeFee(true);
}

function getEmpAccount(){
	Global.Dialog.showDialog("EmpAccount",{
		title:"选择银行账号",
		url:"${ctx}/admin/wfProcInst/goEmpAccount",
		postData:{czybh:$.trim($("#EmpCode").val())},
		height: 640,
		width:900,
		returnFun:function(data){
			$("#fp__tWfCust_TravelExpenseClaim__0__ActName").val(data.actname);
			$("#fp__tWfCust_TravelExpenseClaim__0__Bank").val(data.bank);
			$("#fp__tWfCust_TravelExpenseClaim__0__SubBranch").val(data.subbranch);
			$("#fp__tWfCust_TravelExpenseClaim__0__CardId").val(data.cardid.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{4})|(\d{4}))(?=\d)/g,'$1 '));
			validateRefreshData();
		}
	});
}

function getPartner(){
	Global.Dialog.showDialog("EmpAccount",{
		title:"选择同行人",
		url:"${ctx}/admin/wfProcInst/getEmployeeMult",
		postData:{czybh:"${datas.fp__tWfCust_TravelExpenseClaim__0__EmpCode}"},
		height: 650,
		width:1050,
		returnFun:function(data){
			var partner = $("#fp__tWfCust_TravelExpenseClaim__0__Partner").val();
			$.each(data,function(k,emp){
				if(partner == ""){
					partner = emp.namechi;
				}else{
					partner +=","+emp.namechi;
				}
			});
			$("#fp__tWfCust_TravelExpenseClaim__0__Partner").val(partner);
		}
	});
}

function getAdvanceNo(inx){
	Global.Dialog.showDialog("EmpAccount",{
		title:"选择预支单",
		url:"${ctx}/admin/wfProcInst/getAdvanceNo",
		postData:{czybh:"${datas.fp__tWfCust_TravelExpenseClaim__0__EmpCode}"},
		height: 640,
		width:900,
		returnFun:function(data){
			$("#fp__tWfCust_TravelClaimAdvanceDtl__"+inx+"__WfExpenseAdvanceNo").val(data.no);
			$("#fp__tWfCust_TravelClaimAdvanceDtl__"+inx+"__AdvanceAmount").val(data.amount);
		}
	});
}

function saveAccount(){
	$.ajax({
		url:"${ctx}/admin/wfProcInst/doSaveAccount",
		type: 'post',
		data: {actName:$("#fp__tWfCust_TravelExpenseClaim__0__ActName").val(),
				bank:$("#fp__tWfCust_TravelExpenseClaim__0__Bank").val(),
				subBranch:$("#fp__tWfCust_TravelExpenseClaim__0__SubBranch").val(),
				cardId:$("#fp__tWfCust_TravelExpenseClaim__0__CardId").val()
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

function calcDeductionAmount(){
	var length = $("#tWfCust_ExpenseClaimAdvanceDtl").children("div").length-1;
	var amount = 0;
	var returnAmount =0.0;
	for(var i=0;i<20;i++){
		if($("#fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__DeductionAmount").val()){
			returnAmount = $("#fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__DeductionAmount").val().replace(/[^\-?\d.]/g,'');
			$("#fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__DeductionAmount").val(returnAmount);
			if($("#fp__tWfCust_TravelClaimAdvanceDtl__"+i+"__DeductionAmount").val()){
				amount =myRound(returnAmount,2) + myRound(amount,4);
				console.log(amount);
			} ;
		};
	}
	$("#fp__tWfCust_TravelExpenseClaim__0__DeductionAmount").val(myRound(amount,4));
	$("#confAmount").val(myRound($("#fp__tWfCust_TravelExpenseClaim__0__DeductionAmount").val(),4));
	validateRefreshData();
}

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
			$("#fp__tWfCust_TravelExpenseClaim__0__BefAmount").val(obj);
		}
	});
}

function callSetReadonly(actId){
	setReadonly(actId);
}
</script>
</body>
</html>

