<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>采购报销单</title>
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
				        <label>申请人</label>
						<input type="text" id="EmpCode" name="EmpCode" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseExpense__0__EmpName }"/>
   						<input type="hidden" id="fp__tWfCust_PurchaseExpense__0__EmpCode" name="fp__tWfCust_PurchaseExpense__0__EmpCode"  
   								style="width:160px;" value="${datas.fp__tWfCust_PurchaseExpense__0__EmpCode}"/>
   						<input type="hidden" id="fp__tWfCust_PurchaseExpense__0__EmpName" name="fp__tWfCust_PurchaseExpense__0__EmpName"  
   								style="width:160px;" value="${datas.fp__tWfCust_PurchaseExpense__0__EmpName}"/>
				    </li>
	         		<li class="form-validate">
				        <label>部门</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__DeptDescr" name="fp__tWfCust_PurchaseExpense__0__DeptDescr"  style="width:160px;"  readonly="true"
						 value="${datas.fp__tWfCust_PurchaseExpense__0__DeptDescr }" />
						 <input type="hidden" id="fp__tWfCust_PurchaseExpense__0__DeptCode" name="fp__tWfCust_PurchaseExpense__0__DeptCode"  style="width:160px;" 
						 value="${datas.fp__tWfCust_PurchaseExpense__0__DeptCode }" />
				    </li>
				    <li class="form-validate">
						<label>材料类型1</label>
						<select id="fp__tWfCust_PurchaseExpense__0__ItemType1" name ="fp__tWfCust_PurchaseExpense__0__ItemType1" style="width:160px" disabled="true"
							value=" ${ datas.fp__tWfCust_PurchaseExpense__0__ItemType1}">
							<option value ="">请选择...</option>
							<option value ="主材" ${ datas.fp__tWfCust_PurchaseExpense__0__ItemType1 == '主材' ? 'selected' : ''}>主材</option>
 							<option value ="软装" ${ datas.fp__tWfCust_PurchaseExpense__0__ItemType1 == '软装' ? 'selected' : ''}>软装</option>	
 							<option value ="集成" ${ datas.fp__tWfCust_PurchaseExpense__0__ItemType1 == '集成' ? 'selected' : ''}>集成</option>	
 							<option value ="基装" ${ datas.fp__tWfCust_PurchaseExpense__0__ItemType1 == '基装' ? 'selected' : ''}>基装</option>	
 							<option value ="礼品" ${ datas.fp__tWfCust_PurchaseExpense__0__ItemType1 == '礼品' ? 'selected' : ''}>礼品</option>	
 						</select>
 					</li>
 					<li class="form-validate">
						<label>开支内容</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__ClaimRemarks" name="fp__tWfCust_PurchaseExpense__0__ClaimRemarks" 
									style="width:160px;" value="${datas.fp__tWfCust_PurchaseExpense__0__ClaimRemarks}"/>
					</li>
			    </div>
			    <div class="validate-group row" >	
 					<li class="form-validate">
				        <label>业务单号</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__RefNo" name="fp__tWfCust_PurchaseExpense__0__RefNo" 
									style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseExpense__0__RefNo}"/>
				    </li>
				    <li class="form-validate">
				        <label>采购类型</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__Type" name="fp__tWfCust_PurchaseExpense__0__Type" 
									style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseExpense__0__Type}"/>
				    </li>
				    <li class="form-validate">
				        <label>申请日期</label>
					    <input type="text" style="width:160px;" id="fp__tWfCust_PurchaseExpense__0__ApplyDate" 
										name="fp__tWfCust_PurchaseExpense__0__ApplyDate" disabled="true"
							   			 style="width:100px;" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							   			 value="<fmt:formatDate value='${datas.fp__tWfCust_PurchaseExpense__0__ApplyDate}'  
							   			 pattern='yyyy-MM-dd'/>" />
					</li>
				    <li class="form-validate" >
				        <label>是否月结</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__IsSpecDay" name="fp__tWfCust_PurchaseExpense__0__IsSpecDay" 
									style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseExpense__0__IsSpecDay}"/>
				    </li>
				</div>
			    <div class="validate-group row" >
			   		<li class="form-validate">
				        <label>金额</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__ClaimAmount" name="fp__tWfCust_PurchaseExpense__0__ClaimAmount" 
									style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseExpense__0__ClaimAmount}"/>
				    </li>
			    	<li class="form-validate">
				        <label>已付定金</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__PaidAmount" name="fp__tWfCust_PurchaseExpense__0__PaidAmount" 
									style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseExpense__0__PaidAmount}"/>
				    </li>
				    <li class="form-validate">
				        <label>实付金额</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__RealAmount" name="fp__tWfCust_PurchaseExpense__0__RealAmount" 
									style="width:160px;" readonly="true" value="${datas.fp__tWfCust_PurchaseExpense__0__RealAmount}"/>
				    </li>
			    </div>
			    <div class="validate-group row" >	
				<%-- 	<li class="form-validate">
				        <label><span class="required">*</span>收款户名</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__RcvActName" name="fp__tWfCust_PurchaseExpense__0__RcvActName" 
									style="width:160px;" value="${datas.fp__tWfCust_PurchaseExpense__0__RcvActName}"/>
				    </li> --%>
				    
				    <li class="form-validate">
				        <label><span class="required">*</span>收款户名</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__RcvActName" name="fp__tWfCust_PurchaseExpense__0__RcvActName"  
							style="width:136px;border-top-right-radius:0;border-bottom-right-radius:0" value="${datas.fp__tWfCust_PurchaseExpense__0__RcvActName}"/>
					    <button type="button" class="btn btn-system" data-disabled="false" 
					    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-6px;margin-top: -4px;width:24px;height:24px;padding-top:2.5px" 
					    			onclick="getSupplAccount()">
					    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -11px;padding-top:0;line-height: 20px"></span>
					    </button>
				    </li>
				    
				    <li class="form-validate">
				        <label><span class="required">*</span>收款账户</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__RcvCardId" name="fp__tWfCust_PurchaseExpense__0__RcvCardId" 
									style="width:160px;" value="${datas.fp__tWfCust_PurchaseExpense__0__RcvCardId}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>收款银行</label>
						<input type="text" id="fp__tWfCust_PurchaseExpense__0__RcvBank" name="fp__tWfCust_PurchaseExpense__0__RcvBank" 
									style="width:160px;" value="${datas.fp__tWfCust_PurchaseExpense__0__RcvBank}"/>
				    </li>
				</div>	
				<div class="validate-group row" >	
					<li class="form-validate">
						<label class="control-textarea">备注</label>
						<textarea id="fp__tWfCust_PurchaseExpense__0__Remarks" name="fp__tWfCust_PurchaseExpense__0__Remarks" 
								rows="2">${datas.fp__tWfCust_PurchaseExpense__0__Remarks}</textarea>
					</li>
				</div>

				<div id="tWfCust_PurchaseExpenseDtl" >
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_PurchaseExpenseDtl_div" style="border-top:1px solid #dfdfdf" hidden="true">	
							<div style="margin-top:15px;margin-left:50px">
								<input type="hidden" id="fp__tWfCust_PurchaseExpenseDtl__temInx__PK" name="fp__tWfCust_PurchaseExpenseDtl__temInx__PK"  
									style="width:160px;" readonly="true"/>
								<div class="validate-group row" >	
									<li class="form-validate" hidden="true">
										<label>公司</label>
										<input type="text" id="fp__tWfCust_PurchaseExpenseDtl__temInx__TaxPayeeCode" name="fp__tWfCust_PurchaseExpenseDtl__temInx__TaxPayeeCode"  
												style="width:160px;" readonly="true" hidden="true"/>
										<input type="text" id="fp__tWfCust_PurchaseExpenseDtl__temInx__TaxPayeeDescr" name="fp__tWfCust_PurchaseExpenseDtl__temInx__TaxPayeeDescr"  
												style="width:160px;" readonly="true"/>
									</li>
									<li>
										<label>公司</label>
										<select id="taxPayee_temInx" name="taxPayee_temInx" value="" type="width:160px" onchange="chgTaxPayee(temInx)">
											<option value="">请选择...</option>
										</select>
									</li>
									<li class="form-validate">
										<label>金额</label>
										<input type="text" id="fp__tWfCust_PurchaseExpenseDtl__temInx__Amount" name="fp__tWfCust_PurchaseExpenseDtl__temInx__Amount"  
												onchange="chgAmount()" style="width:160px;"/>
									</li>
									<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_PurchaseExpenseDtl')" id="btn_temInx">增加明细</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- <div id="tWfCust_PurchaseExpenseDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_PurchaseExpenseDtl_div" style="border-top:1px solid #dfdfdf" hidden="true">	
							<div style="margin-top:0px;margin-left:14px">
								<table id = "table_temInx" style="width:100%;">
									<tr id="tr_temInx" style="border-bottom: 1px solid #dfdfdf;padding-top:0px;margin-bottom: 5px">
										<td style=" border:0px; width:120px; text-align: center">公司</td>
										<td style=" border:0px; width:120px; text-align: center">金额</td>
									</tr>
									<tr style="height:35px">
										<td style="border:0px; width:120px;" id="SupplDescr__temInx">
											<input style="border:0px;width:120px;text-align: center" id="fp__tWfCust_PurchaseExpenseDtl__temInx__TaxPayeeCode" 
												name="fp__tWfCust_PurchaseExpenseDtl__temInx__TaxPayeeCode" hidden="true"/>
											<input style="border:0px;width:120px;text-align: center" id="fp__tWfCust_PurchaseExpenseDtl__temInx__TaxPayeeDescr" 
												name="fp__tWfCust_PurchaseExpenseDtl__temInx__TaxPayeeDescr" readonly="true"/>
										</td>
										<td style="border:0px; width:120px; text-align: center" id="PayAmount__temInx">
											<input style="border:0px;width:120px;text-align: center" id="fp__tWfCust_PurchaseExpenseDtl__temInx__Amount" 
												name="fp__tWfCust_PurchaseExpenseDtl__temInx__PAmount" readonly="true"/>
										</td>
									</tr>
								</table>
								<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_PurchaseExpenseDtl')" id="btn_temInx">增加明细</button>
							</div>
						</div>
					</div>
				</div> -->
        	</ul>
        </form>
     </div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//detailJson 查看的时候传回来的明细数据
var html_ = $("#detail_div").html();
var detailJson=${detailJson};
var taxPayeeList;
var badRequest_ = false;
var mistakeMsg_ = "";
$(function(){
	detailVerify = [
		{
			tableId:"tWfCust_PurchaseExpense",
			activityId: "startevent",
			mainTable:"1",
			RcvCardId:"收款账户不能为空",
			RcvBank:"收款银行不能为空",
			RcvActName:"收款户名不能为空",
			ClaimRemarks:"开支内容不能为空",
		}, {
			tableId:"tWfCust_PurchaseExpenseDtl",
			activityId: "startevent",
			TaxPayeeCode: "公司不能为空",
			Amount: "金额不能为空",
		}
	];

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
	
	if("A" != "${m_umState}"){
		$("#fp__tWfCust_PurchaseExpense__0__RcvCardId").attr("readonly", true);
		$("#fp__tWfCust_PurchaseExpense__0__RcvActName").attr("readonly", true);
		$("#fp__tWfCust_PurchaseExpense__0__RcvBank").attr("readonly", true);
	}
	/* if("${datas.fp__tWfCust_PurchaseExpense__0__RcvBank}" == "" || "${datas.fp__tWfCust_PurchaseExpense__0__RcvCardId}" == "" ||
			"${datas.fp__tWfCust_PurchaseExpense__0__RcvActName}" == ""){
		badRequest_ = true;
		mistakeMsg_ = "供应商的银行卡信息为空,先修改供应商银行卡信息";
	} */
	
});

function addDetail_(flag,inx,groupId){
	var html = $("#"+groupId).find("#group_temp").html();
	addDetail(flag,html,inx,"returnFn",groupId);
}


function returnFn(flag, i, groupId){
	if(groupId == "tWfCust_PurchaseExpenseDtl"){
		$("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__PK").val(detailJson["fp__tWfCust_PurchaseExpenseDtl__"+i+"__PK"]);
		$("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__TaxPayeeCode").val(detailJson["fp__tWfCust_PurchaseExpenseDtl__"+i+"__TaxPayeeCode"]);
		$("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__TaxPayeeDescr").val(detailJson["fp__tWfCust_PurchaseExpenseDtl__"+i+"__TaxPayeeDescr"]);
		$("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__Amount").val(detailJson["fp__tWfCust_PurchaseExpenseDtl__"+i+"__Amount"]);
		if("A" != "${m_umState}"){
			$("#taxPayee_"+i).attr("disabled", true);
			$("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__Amount").attr("readonly", true);
			
		}
		if(!taxPayeeList){
			$.ajax({
				url:"${ctx}/admin/taxPayee/getTaxPayeeList",
				type:"post",	
				data:{},
				dataType:"json",
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": "出错~"});
				},
				success:function(obj){
					if (obj){
						taxPayeeList = obj;
						for(var j = 0; j < obj.length; j++){
							$("#taxPayee_"+i).append("<option value= " + obj[j].Code + ">" + obj[j].Descr + "</option>");
							$("#taxPayee_"+i).val($.trim(detailJson["fp__tWfCust_PurchaseExpenseDtl__"+i+"__TaxPayeeCode"]));
						}
					}
				}
			});
		}else{
			for(var j = 0; j < taxPayeeList.length; j++){
				$("#taxPayee_"+i).append("<option value= " + taxPayeeList[j].Code + ">" + taxPayeeList[j].Descr + "</option>");
				$("#taxPayee_"+i).val($.trim(detailJson["fp__tWfCust_PurchaseExpenseDtl__"+i+"__TaxPayeeCode"]));
			}
		}
	}
	chgAmount();
}

function chgAmount(){
	var length = $("#fp__tWfCust_PurchaseExpenseDtl").children("div").length-1;
	var amount = 0;
	var returnAmount =0.0;
	for(var i=0;i<20;i++){
		if($("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__Amount").val()){
			returnAmount = $("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__Amount").val().replace(/[^\-?\d.]/g,'');
			$("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__Amount").val(returnAmount);
			if($("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__Amount").val()){
				amount =myRound(returnAmount,4) + myRound(amount,4);
			} ;
		};
		if(i == 19){
			if(myRound(amount,4)!= myRound($("#fp__tWfCust_PurchaseExpense__0__ClaimAmount").val(),4) - myRound("${datas.PreAmount}",4)){
				console.log(myRound(amount,4));
				console.log(myRound($("#fp__tWfCust_PurchaseExpense__0__ClaimAmount").val(),4) );
				console.log(myRound("${datas.PreAmount}",4));
				badRequest_ = true;
				mistakeMsg_ = "明细金额不等于报销单总金额-预付款支付，无法提交";
			} else {
				badRequest_ = false;
				mistakeMsg_ = "";
			}
		}
	}
}

function chgTaxPayee(temInx){
	var descr = "";
	descr = $("#taxPayee_"+temInx).find("option:selected").text();
	
	$("#fp__tWfCust_PurchaseExpenseDtl__"+temInx+"__TaxPayeeCode").val($.trim($("#taxPayee_"+temInx).val()));
	$("#fp__tWfCust_PurchaseExpenseDtl__"+temInx+"__TaxPayeeDescr").val(descr);
}

function getOperator_(flag){
	var elMap = {Type:$.trim($("#fp__tWfCust_PurchaseExpense__0__Type").val()),
				ClaimAmount:$.trim($("#fp__tWfCust_PurchaseExpense__0__ClaimAmount").val()),
				ItemType1:$.trim($("#fp__tWfCust_PurchaseExpense__0__ItemType1").val()),
				IsSpecDay: $.trim($("#fp__tWfCust_PurchaseExpense__0__IsSpecDay").val()),
				DeptDescr: $.trim($("#fp__tWfCust_PurchaseExpense__0__DeptDescr").val()),
				};
	getOperator(flag,elMap);
} 

function getMistakeMsg(){
	var result = {"badRequest_":badRequest_,"mistakeMsg_":mistakeMsg_};
	var len = $("#tWfCust_PurchaseExpenseDtl").children("div").length;
	var amount = $("#fp__tWfCust_PurchaseExpense__0__ClaimAmount").val();
	/* var dtlAmount = 0.0;
	for(var i=0;i < len; i++){
		if($("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__Amount").val()){
			dtlAmount+=myRound($("#fp__tWfCust_PurchaseExpenseDtl__"+i+"__Amount").val());
		}
		if(i==len && dtlAmount != ){
			
		}
	} */
	
	return result;
}

function getSupplAccount(){
	Global.Dialog.showDialog("SupplAccount",{
		title:"选择银行账号",
		url:"${ctx}/admin/wfProcInst/goSupplAccount",
		postData:{no:$("#fp__tWfCust_PurchaseExpense__0__RefNo").val()},
		height: 640,
		width:900,
		returnFun:function(data){
			$("#fp__tWfCust_PurchaseExpense__0__RcvActName").val(data.rcvactname);
			$("#fp__tWfCust_PurchaseExpense__0__RcvBank").val(data.rcvbank);
			$("#fp__tWfCust_PurchaseExpense__0__RcvCardId").val(data.rcvcardid.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{4})|(\d{4}))(?=\d)/g,'$1 '));
		}
	});
}
</script>
</body>
</html>

