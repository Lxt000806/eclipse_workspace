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
				        <label><span class="required">*</span>报销人</label>
						<input type="text" id="EmpCode" name="EmpCode" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_ConstructionExpenseClaim__0__EmpName}"/>
   						<input type="hidden" id="fp__tWfCust_ConstructionExpenseClaim__0__EmpCode" name="fp__tWfCust_ConstructionExpenseClaim__0__EmpCode"  style="width:160px;" value="${datas.fp__tWfCust_ConstructionExpenseClaim__0__EmpCode}"/>
   						<input type="hidden" id="fp__tWfCust_ConstructionExpenseClaim__0__EmpName" name="fp__tWfCust_ConstructionExpenseClaim__0__EmpName"  style="width:160px;" value="${datas.fp__tWfCust_ConstructionExpenseClaim__0__EmpName}"/>
				    </li>
	         		<li class="form-validate">
				        <label><span class="required">*</span>部门</label>
						<input type="text" id="fp__tWfCust_ConstructionExpenseClaim__0__DeptDescr" name="fp__tWfCust_ConstructionExpenseClaim__0__DeptDescr"  style="width:160px;"  readonly="true"
						 value="${datas.fp__tWfCust_ConstructionExpenseClaim__0__DeptDescr }" />
						 <input type="hidden" id="fp__tWfCust_ConstructionExpenseClaim__0__DeptCode" name="fp__tWfCust_ConstructionExpenseClaim__0__DeptCode"  style="width:160px;" 
						 value="${datas.fp__tWfCust_ConstructionExpenseClaim__0__DeptCode }" />
				    </li>
				    <li class="form-validate">
						<label><span class="required">*</span>分公司</label>
						<select id="fp__tWfCust_ConstructionExpenseClaim__0__Company" name="fp__tWfCust_ConstructionExpenseClaim__0__Company" 
								value="${datas.fp__tWfCust_ConstructionExpenseClaim__0__Company }" onchange="getOperator_()">
							<option value ="福州有家" ${ datas.fp__tWfCust_ConstrcutionExpenseClaim__0__Company == '福州有家' ? 'selected' : ''}>福州有家</option>
							<option value ="美第家居" ${ datas.fp__tWfCust_ConstrcutionExpenseClaim__0__Company == '美第家居' ? 'selected' : ''}>美第家居</option>
							<option value ="长乐有家" ${ datas.fp__tWfCust_ConstrcutionExpenseClaim__0__Company == '长乐有家' ? 'selected' : ''}>长乐有家</option>
							<option value ="福清有家" ${ datas.fp__tWfCust_ConstrcutionExpenseClaim__0__Company == '福清有家' ? 'selected' : ''}>福清有家</option>
							<option value ="厦门有家" ${ datas.fp__tWfCust_ConstrcutionExpenseClaim__0__Company == '厦门有家' ? 'selected' : ''}>厦门有家</option>
							<option value ="郑州有家" ${ datas.fp__tWfCust_ConstrcutionExpenseClaim__0__Company == '郑州有家' ? 'selected' : ''}>郑州有家</option>
						</select>
					</li>
				    <li class="form-validate">
						<label><span class="required">*</span>材料类型1</label>
						<select id="fp__tWfCust_ConstructionExpenseClaim__0__ItemType1" name ="fp__tWfCust_ConstructionExpenseClaim__0__ItemType1" style="width:160px" disabled="true"
							value=" ${ datas.fp__tWfCust_ConstructionExpenseClaim__0__ItemType1}">
							<option value ="">请选择...</option>
							<option value ="主材" ${ datas.fp__tWfCust_ConstructionExpenseClaim__0__ItemType1 == '主材' ? 'selected' : ''}>主材</option>
 							<option value ="软装" ${ datas.fp__tWfCust_ConstructionExpenseClaim__0__ItemType1 == '软装' ? 'selected' : ''}>软装</option>	
 							<option value ="集成" ${ datas.fp__tWfCust_ConstructionExpenseClaim__0__ItemType1 == '集成' ? 'selected' : ''}>集成</option>	
 							<option value ="基装" ${ datas.fp__tWfCust_ConstructionExpenseClaim__0__ItemType1 == '基装' ? 'selected' : ''}>基装</option>	
 							<option value ="礼品" ${ datas.fp__tWfCust_ConstructionExpenseClaim__0__ItemType1 == '礼品' ? 'selected' : ''}>礼品</option>	
 						</select>
 					</li>
 					<li class="form-validate">
				        <label><span class="required">*</span>常规列支</label>
						<select id="fp__tWfCust_ConstructionExpenseClaim__0__IsCommon" name ="fp__tWfCust_ConstructionExpenseClaim__0__IsCommon" onchange = "chgType()"
							value = "是" disabled="true">
 							<option value ="是" ${ datas.fp__tWfCust_ConstructionExpenseClaim__0__IsCommon== '是' ? 'selected' : ''}>是</option>
 						</select>	
					</li>
				    <li class="form-validate">
				        <label><span class="required">*</span>报销金额</label>
						<input type="text" id="fp__tWfCust_ConstructionExpenseClaim__0__Amount" readonly="true"
								 name="fp__tWfCust_ConstructionExpenseClaim__0__Amount"  style="width:160px;"
								 value="${datas.fp__tWfCust_ConstructionExpenseClaim__0__Amount}"/>
				    </li> 
				    <li class="form-validate">
				        <label><span class="required">*</span>业务单号</label>
						<input type="text" id="fp__tWfCust_ConstructionExpenseClaim__0__RefNo" name="fp__tWfCust_ConstructionExpenseClaim__0__RefNo" 
									readonly="true" style="width:160px;"
								 value="${datas.fp__tWfCust_ConstructionExpenseClaim__0__RefNo}"/>
				    </li> 
				</div>	
				<div class="validate-group row" >	
					<li class="form-validate">
						<label class="control-textarea"><span class="required">*</span>备注</label>
						<textarea id="fp__tWfCust_ConstructionExpenseClaim__0__Reason" name="fp__tWfCust_ConstructionExpenseClaim__0__Reason" 
								readonly="true" rows="2">${datas.fp__tWfCust_ConstructionExpenseClaim__0__Reason}</textarea>
					</li>
				</div>
				<div id="tWfCust_ConstructionExpenseClaimDtl" >
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_ConstructionExpenseClaimDtl_div" style="border-top:1px solid #dfdfdf" hidden="true">	
							<div style="margin-top:15px">
								<input type="hidden" id="fp__tWfCust_ConstructionExpenseClaimDtl__temInx__PK" name="fp__tWfCust_ConstructionExpenseClaimDtl__temInx__PK"  
									style="width:160px;" readonly="true"/>
								<li class="form-validate">
									<label><span class="required">*</span>报销额</label>
									<input type="text" id="fp__tWfCust_ConstructionExpenseClaimDtl__temInx__DtlAmount" name="fp__tWfCust_ConstructionExpenseClaimDtl__temInx__DtlAmount"  
									style="width:160px;" readonly="true"/>
								</li>
								<li class="form-validate">
									<label class="control-textarea"><span class="required">*</span>报销内容</label>
									<textarea id="fp__tWfCust_ConstructionExpenseClaimDtl__temInx__Remarks" name="fp__tWfCust_ConstructionExpenseClaimDtl__temInx__Remarks" 
											rows="2" readonly="true"></textarea>
								</li>
							</div>
						</div>
					</div>
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
	if("${cmpcode }" != ""){
		if("01" == "${cmpcode }" || "05" == "${cmpcode }" || "03" == "${cmpcode }"){
			$("#fp__tWfCust_ConstructionExpenseClaim__0__Company").find("option").remove();
			$("#fp__tWfCust_ConstructionExpenseClaim__0__Company").append("<option value= " + "福州有家" + ">" + "福州有家" + "</option>");
			$("#fp__tWfCust_ConstructionExpenseClaim__0__Company").append("<option value= " + "美第家居" + ">" + "美第家居" + "</option>");
		}
		if("02" == "${cmpcode }"){
			$("#fp__tWfCust_ConstructionExpenseClaim__0__Company").find("option").remove();
			$("#fp__tWfCust_ConstructionExpenseClaim__0__Company").append("<option value= " + "厦门有家" + ">" + "厦门有家" + "</option>");
			$("#fp__tWfCust_ConstructionExpenseClaim__0__Company").append("<option value= " + "漳州有家" + ">" + "漳州有家" + "</option>");
		}
		if("04" == "${cmpcode }"){
			$("#fp__tWfCust_ConstructionExpenseClaim__0__Company").find("option").remove();
			$("#fp__tWfCust_ConstructionExpenseClaim__0__Company").append("<option value= " + "郑州有家" + ">" + "郑州有家" + "</option>");
		}
		
		if("${m_umState}" != "A"){
			$("#fp__tWfCust_ConstructionExpenseClaim__0__Company").attr("disabled",true);
		}
		if("${datas.fp__tWfCust_ConstructionExpenseClaim__0__Company }" != ""){
			$("#fp__tWfCust_ConstructionExpenseClaim__0__Company").val("${datas.fp__tWfCust_ConstructionExpenseClaim__0__Company }");
		}
	}

	var detailList = ${detailList};
	if(detailList != ""){
		for (var tableName in detailList) {
			if($("#"+tableName).find("#group_temp").html()){
		       	addDetail_("",detailList[tableName]==0?0:detailList[tableName]-1,tableName);
			}
	    }
	}
	document.getElementById("tab_apply_fjView_li").style.display="block";
	
	getOperator_();
});

function addDetail_(flag,inx,groupId){
	var html = $("#"+groupId).find("#group_temp").html();
	addDetail(flag,html,inx,"returnFn",groupId);
}


function returnFn(flag, i, groupId){
	if(groupId == "tWfCust_ConstructionExpenseClaimDtl"){
		$("#fp__tWfCust_ConstructionExpenseClaimDtl__"+i+"__PK").val(detailJson["fp__tWfCust_ConstructionExpenseClaimDtl__"+i+"__PK"]);
		$("#fp__tWfCust_ConstructionExpenseClaimDtl__"+i+"__DtlAmount").val(detailJson["fp__tWfCust_ConstructionExpenseClaimDtl__"+i+"__DtlAmount"]);
		$("#fp__tWfCust_ConstructionExpenseClaimDtl__"+i+"__Remarks").val(detailJson["fp__tWfCust_ConstructionExpenseClaimDtl__"+i+"__Remarks"]);
	}
}

function getOperator_(flag){
	var elMap = {Company:$.trim($("#fp__tWfCust_ConstructionExpenseClaim__0__Company").val()),
				ItemType1:$.trim($("#fp__tWfCust_ConstructionExpenseClaim__0__ItemType1").val())};
	getOperator(flag,elMap);
} 


</script>
</body>
</html>

