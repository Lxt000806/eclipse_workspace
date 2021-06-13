<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>采购预付</title>
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
    		<input id="m_umState" name="m_umState" value="${m_umState}" hidden="true" /> 
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label>报销人</label>
						<input type="text" id="EmpCode" name="EmpCode" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseAdvance__0__EmpName }"/>
   						<input type="hidden" id="fp__tWfCust_PurchaseAdvance__0__EmpCode" name="fp__tWfCust_PurchaseAdvance__0__EmpCode"  
   								style="width:160px;" value="${datas.fp__tWfCust_PurchaseAdvance__0__EmpCode}"/>
   						<input type="hidden" id="fp__tWfCust_PurchaseAdvance__0__EmpName" name="fp__tWfCust_PurchaseAdvance__0__EmpName"  
   								style="width:160px;" value="${datas.fp__tWfCust_PurchaseAdvance__0__EmpName}"/>
				    </li>
	         		<li class="form-validate">
				        <label>部门</label>
						<input type="text" id="fp__tWfCust_PurchaseAdvance__0__DeptDescr" name="fp__tWfCust_PurchaseAdvance__0__DeptDescr"  style="width:160px;"  readonly="true"
						 value="${datas.fp__tWfCust_PurchaseAdvance__0__DeptDescr }" />
						 <input type="hidden" id="fp__tWfCust_PurchaseAdvance__0__DeptCode" name="fp__tWfCust_PurchaseAdvance__0__DeptCode"  style="width:160px;" 
						 value="${datas.fp__tWfCust_PurchaseAdvance__0__DeptCode }" />
				    </li>
				    <li class="form-validate">
						<label>材料类型1</label>
						<select id="fp__tWfCust_PurchaseAdvance__0__ItemType1" name ="fp__tWfCust_PurchaseAdvance__0__ItemType1" style="width:160px" disabled="true"
							value=" ${ datas.fp__tWfCust_PurchaseAdvance__0__ItemType1}">
							<option value ="">请选择...</option>
							<option value ="主材" ${ datas.fp__tWfCust_PurchaseAdvance__0__ItemType1 == '主材' ? 'selected' : ''}>主材</option>
 							<option value ="软装" ${ datas.fp__tWfCust_PurchaseAdvance__0__ItemType1 == '软装' ? 'selected' : ''}>软装</option>	
 							<option value ="集成" ${ datas.fp__tWfCust_PurchaseAdvance__0__ItemType1 == '集成' ? 'selected' : ''}>集成</option>	
 							<option value ="基装" ${ datas.fp__tWfCust_PurchaseAdvance__0__ItemType1 == '基装' ? 'selected' : ''}>基装</option>	
 							<option value ="礼品" ${ datas.fp__tWfCust_PurchaseAdvance__0__ItemType1 == '礼品' ? 'selected' : ''}>礼品</option>	
 						</select>
 					</li>
 					<li class="form-validate">
				        <label>业务单号</label>
						<input type="text" id="fp__tWfCust_PurchaseAdvance__0__RefNo" name="fp__tWfCust_PurchaseAdvance__0__RefNo" 
									style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseAdvance__0__RefNo}"/>
				    </li>
				    <li class="form-validate">
				        <label>预付类型</label>
						<input type="text" id="fp__tWfCust_PurchaseAdvance__0__Type" name="fp__tWfCust_PurchaseAdvance__0__Type" 
									style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseAdvance__0__Type}"/>
				    </li>
				    <li class="form-validate">
				        <label>申请日期</label>
					    <input type="text" style="width:160px;" id="fp__tWfCust_PurchaseAdvance__0__ApplyDate" 
										name="fp__tWfCust_PurchaseAdvance__0__ApplyDate" disabled="true"
							   			 style="width:100px;" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							   			 value="<fmt:formatDate value='${datas.fp__tWfCust_PurchaseAdvance__0__ApplyDate}'  
							   			 pattern='yyyy-MM-dd'/>" />
					</li>
					<li class="form-validate">
				        <label>预支总额</label>
						<input type="text" id="fp__tWfCust_PurchaseAdvance__0__AdvanceAmount" name="fp__tWfCust_PurchaseAdvance__0__AdvanceAmount" 
									style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseAdvance__0__AdvanceAmount}"/>
				    </li>
				    <li class="form-validate">
						<label>开支内容</label>
						<input type="text" id="fp__tWfCust_PurchaseAdvance__0__AdvanceRemarks" name="fp__tWfCust_PurchaseAdvance__0__AdvanceRemarks" 
									style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseAdvance__0__AdvanceRemarks}"/>
					</li>
				</div>	
				<div class="validate-group row" >	
					<li class="form-validate">
						<label class="control-textarea">备注</label>
						<textarea id="fp__tWfCust_PurchaseAdvance__0__Remarks" name="fp__tWfCust_PurchaseAdvance__0__Remarks" 
								rows="2">${datas.fp__tWfCust_PurchaseAdvance__0__Remarks}</textarea>
					</li>
				</div>
				<!-- <div id="tWfCust_PurchaseAdvanceDtl" >
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_PurchaseAdvanceDtl_div" style="border-top:1px solid #dfdfdf" hidden="true">	
							<div style="margin-top:15px;margin-left:50px">
								<input type="hidden" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__PK" name="fp__tWfCust_PurchaseAdvanceDtl__temInx__PK"  
									style="width:160px;" readonly="true"/>
								<div class="validate-group row" >	
									<li class="form-validate">
										<label>供应商</label>
										<input type="text" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__SupplCode" name="fp__tWfCust_PurchaseAdvanceDtl__temInx__SupplCode"  
										style="width:160px;" readonly="true" hidden="true"/>
										<input type="text" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__SupplDescr" name="fp__tWfCust_PurchaseAdvanceDtl__temInx__SupplDescr"  
										style="width:160px;" readonly="true"/>
									</li>
									<li class="form-validate">
										<label>付款金额</label>
										<input type="text" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__PayAmount" name="fp__tWfCust_PurchaseAdvanceDtl__temInx__PayAmount"  
										style="width:160px;" readonly="true"/>
									</li>
								</div>
								<div class="validate-group row" >	
									<li class="form-validate">
										<label>收款户名</label>
										<input type="text" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvActName" name="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvActName"  
										style="width:160px;" readonly="true"/>
									</li>
									<li class="form-validate">
										<label>收款账户</label>
										<input type="text" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvCardId" name="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvCardId"  
										style="width:280px;" readonly="true"/>
									</li>
								</div>
								<div class="validate-group row" >	
									<li class="form-validate">
										<label>收款银行</label>
										<input type="text" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvBank" name="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvBank"  
										style="width:160px;" readonly="true"/>
									</li>
									<li class="form-validate">
										<label class="control-textarea">报销内容</label>
										<textarea id="fp__tWfCust_PurchaseAdvanceDtl__temInx__Reason" name="fp__tWfCust_PurchaseAdvanceDtl__temInx__Reason" 
												rows="2"></textarea>
									</li>
								</div>
							</div>
						</div>
					</div>
				</div> -->
				<div id="tWfCust_PurchaseAdvanceDtl" >
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_PurchaseAdvanceDtl_div" style="border-top:1px solid #dfdfdf" hidden="true">	
							<div style="margin-top:0px;margin-left:14px">
								<table id = "table_temInx" style="width:100%;">
									<tr id="tr_temInx" style="border-bottom: 1px solid #dfdfdf;padding-top:0px;margin-bottom: 5px">
										<td style=" border:0px; width:120px; text-align: center">供应商</td>
										<td style=" border:0px; width:100px; text-align: center">付款金额</td>
										<td style=" border:0px; width:140px; text-align: center">收款户名</td>
										<td style=" border:0px; width:180px; text-align: center">收款账户</td>
										<td style=" border:0px; width:180px; text-align: center">收款银行</td>
										<td style=" border:0px; width:220px; text-align: center">报销内容</td>
									</tr>
									<tr style="height:35px">
										<td style="border:0px; width:120px;" id="SupplDescr__temInx">
											<input style="border:0px;width:120px;text-align: center" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__SupplCode" 
												name="fp__tWfCust_PurchaseAdvanceDtl__temInx__SupplCode" hidden="true"/>
											<input style="border:0px;width:120px;text-align: center" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__SupplDescr" 
												name="fp__tWfCust_PurchaseAdvanceDtl__temInx__SupplDescr" readonly="true"/>
										</td>
										<td style="border:0px; width:100px; text-align: center" id="PayAmount__temInx">
											<input style="border:0px;width:100px;text-align: center" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__PayAmount" 
												name="fp__tWfCust_PurchaseAdvanceDtl__temInx__PayAmount" readonly="true"/>
										</td>
										<td style="border:0px;width:140px; text-align: center" id="RcvActName__temInx">
											<input style="width:140px;height:24px;text-align: center" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvActName" 
												name="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvActName"/>
											<button type="button" class="btn btn-system" data-disabled="false" 
										    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -2px;width:26px;height:24px;padding-top:2.5px" onclick="getSupplAccount(temInx)">
										    	<span class="glyphicon glyphicon-search" style="margin-left: -7px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
										    </button>
										</td>
										<td style="border:0px; width:180px; text-align: center" id="RcvCardId__temInx">
											<input style="width:180px;height:24px;text-align: center" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvCardId" 
												name="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvCardId"/>
										</td>
										<td style="border:0px; width:180px; text-align: center" id="RcvBank__temInx">
											<input style="width:180px;height:24px;text-align: center" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvBank" 
												name="fp__tWfCust_PurchaseAdvanceDtl__temInx__RcvBank"/>
										</td>
										<td style="border:0px; width:220px; text-align: center" id="reasontr">
											<input style="height:24px;width:220px" id="fp__tWfCust_PurchaseAdvanceDtl__temInx__Reason" name="fp__tWfCust_PurchaseAdvanceDtl__temInx__Reason"/>
										</td>
									</tr>
								</table>
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
var badRequest_ = false;
var mistakeMsg_ = "";
$(function(){
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
	if(groupId == "tWfCust_PurchaseAdvanceDtl"){
		$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__PK").val(detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__PK"]);
		$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__SupplCode").val(detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__SupplCode"]);
		$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__SupplDescr").val(detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__SupplDescr"]);
		$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__PayAmount").val(detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__PayAmount"]);
		$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvActName").val(detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvActName"]);
		$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvCardId").val(detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvCardId"]);
		$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvBank").val(detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvBank"]);
		$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__Reason").val(detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__Reason"]);
		if("${m_umState}" != "A"){
			$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvActName").attr("readonly",true);
			$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvCardId").attr("readonly",true);
			$("#fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvBank").attr("readonly",true);
		}
		if(i>0){
			$("#tr_"+i).hide();
		}
		/* if(detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvCardId"] == "" || detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvActName"] == "" ||
				detailJson["fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvBank"] == ""){
			badRequest_ = true;
  			mistakeMsg_ = "存在收款信息为空的明细,先修改供应商银行卡信息";
		} */
	}
}
function getMistakeMsg(){
	var result = {"badRequest_":badRequest_,"mistakeMsg_":mistakeMsg_};
	var len = $("#tWfCust_PurchaseAdvanceDtl").find("table").length;
	var table = "#fp__tWfCust_PurchaseAdvanceDtl__";
	var col = {"__RcvCardId":"存在卡号为空的供应商信息", "__RcvBank": "存在银行为空的供应商信息","__RcvActName": "存在户名为空的供应商信息"};
	for(var i = 0; i < len-1; i++){
		for (var key in col) {  
			if($(table+i+key).val()==""){
				result["badRequest_"]=true;
				result["mistakeMsg_"]=col[key];
				break;
			}
		}
	}
	
	return result;
}
function getOperator_(flag){
	var elMap = {Type:$.trim($("#fp__tWfCust_PurchaseAdvance__0__Type").val()),
				AdvanceAmount:$.trim($("#fp__tWfCust_PurchaseAdvance__0__AdvanceAmount").val()),
				ItemType1:$.trim($("#fp__tWfCust_PurchaseAdvance__0__ItemType1").val()),
				DeptDescr:$.trim($("#fp__tWfCust_PurchaseAdvance__0__DeptDescr").val()),
				};
	getOperator(flag,elMap);
} 

function getSupplAccount(index){
	var m_umState = $("#m_umState").val();
	if(m_umState != "A"){
		return;
	}

	var  splCode = $("#fp__tWfCust_PurchaseAdvanceDtl__"+index+"__SupplCode").val();
	
	Global.Dialog.showDialog("SupplAccount",{
		title:"选择银行账号",
		url:"${ctx}/admin/payManage/goSupplAccount",
		postData:{appEmp: $("#fp__tWfCust_PurchaseAdvance__0__EmpCode").val(), splCode: splCode},
		height: 640,
		width:900,
		returnFun:function(data){
			$("#fp__tWfCust_PurchaseAdvanceDtl__"+index+"__RcvBank").val(data.rcvbank);
			$("#fp__tWfCust_PurchaseAdvanceDtl__"+index+"__RcvCardId").val(data.rcvcardid);
			$("#fp__tWfCust_PurchaseAdvanceDtl__"+index+"__RcvActName").val(data.rcvactname);
		}
	});
}

function callSetReadonly(actId){
	$("#m_umState").val("A");
	setReadonly(actId);
}
</script>
</body>
</html>

