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
    		
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >
	         		<li class="form-validate">
				        <label><span class="required">*</span>预支人</label>
						<input type="text" id="EmpCode" name="EmpCode"  style="width:160px;" value="${datas.fp__tWfCust_ExpenseAdvance__0__EmpCode}"/>
   						<input type="text" id="fp__tWfCust_ExpenseAdvance__0__EmpCode" name="fp__tWfCust_ExpenseAdvance__0__EmpCode"  style="width:160px;" hidden="true" value="${datas.fp__tWfCust_ExpenseAdvance__0__EmpCode}"/>
   						<input type="text" id="fp__tWfCust_ExpenseAdvance__0__EmpName" name="fp__tWfCust_ExpenseAdvance__0__EmpName"  style="width:160px;"hidden="true" value="${datas.fp__tWfCust_ExpenseAdvance__0__EmpName}"/>
				    </li>
				    <li class="form-validate">
						<label><span class="required">*</span>分公司</label>
						<select id="fp__tWfCust_ExpenseAdvance__0__Company" name="fp__tWfCust_ExpenseAdvance__0__Company" 
								value="${datas.fp__tWfCust_ExpenseAdvance__0__Company }" onchange="getOperator_()">
							<option value ="福州有家" ${ datas.fp__tWfCust_ExpenseAdvance__0__Company == '福州有家' ? 'selected' : ''}>福州有家</option>
							<option value ="美第家居" ${ datas.fp__tWfCust_ExpenseAdvance__0__Company == '美第家居' ? 'selected' : ''}>美第家居</option>
							<option value ="长乐有家" ${ datas.fp__tWfCust_ExpenseAdvance__0__Company == '长乐有家' ? 'selected' : ''}>长乐有家</option>
							<option value ="福清有家" ${ datas.fp__tWfCust_ExpenseAdvance__0__Company == '福清有家' ? 'selected' : ''}>福清有家</option>
							<option value ="厦门有家" ${ datas.fp__tWfCust_ExpenseAdvance__0__Company == '厦门有家' ? 'selected' : ''}>厦门有家</option>
							<option value ="郑州有家" ${ datas.fp__tWfCust_ExpenseAdvance__0__Company == '郑州有家' ? 'selected' : ''}>郑州有家</option>
						</select>
					</li>
				    <li class="form-validate">
				        <label>借款余额</label>
   						<input type="text" id="fp__tWfCust_ExpenseAdvance__0__BefAmount" name="fp__tWfCust_ExpenseAdvance__0__BefAmount" 
   								value="${datas.fp__tWfCust_ExpenseAdvance__0__BefAmount}" style="width:160px;" readonly="true"/>
				    </li>
				    <li class="form-validate">
				        <label>预支额</label>
						<input type="text" id="fp__tWfCust_ExpenseAdvance__0__Amount" name="fp__tWfCust_ExpenseAdvance__0__Amount"  placeholder="自动计算"
							onchange="getOperator_()" style="width:160px;" value="${datas.fp__tWfCust_ExpenseAdvance__0__Amount}" readonly="true"/>
				    </li>
		     		<!-- <button type="button" class="btn btn-sm btn-system" onclick="getEmpAccount()" style="height:25px">
		     			选择账户
		     		</button> -->
				</div>
				<div class="validate-group row" >	
					<li class="form-validate">
				        <label><span class="required">*</span>账户名</label>
						<input type="text" id="fp__tWfCust_ExpenseAdvance__0__ActName" name="fp__tWfCust_ExpenseAdvance__0__ActName"  
							style="width:136px;border-top-right-radius:0;border-bottom-right-radius:0" value="${datas.fp__tWfCust_ExpenseAdvance__0__ActName}"/>
					    <button type="button" class="btn btn-system" data-disabled="false" 
					    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-6px;margin-top: -4px;width:24px;height:24px;padding-top:2.5px" 
					    			onclick="getEmpAccount()">
					    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -11px;padding-top:0;line-height: 20px"></span>
					    </button>
				    </li>
	         		<li class="form-validate">
				        <label><span class="required">*</span>开户行</label>
						<input type="text" id="fp__tWfCust_ExpenseAdvance__0__Bank" name="fp__tWfCust_ExpenseAdvance__0__Bank"  style="width:160px;" value="${datas.fp__tWfCust_ExpenseAdvance__0__Bank}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>支行</label>
						<input type="text" id="fp__tWfCust_ExpenseAdvance__0__SubBranch" name="fp__tWfCust_ExpenseAdvance__0__SubBranch"  style="width:160px;" value="${datas.fp__tWfCust_ExpenseAdvance__0__SubBranch}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>卡号</label>
						<input type="text" id="fp__tWfCust_ExpenseAdvance__0__CardId" name="fp__tWfCust_ExpenseAdvance__0__CardId" 
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{4})|(\d{4}))(?=\d)/g,'$1 ');"
						 	style="width:160px;" value="${datas.fp__tWfCust_ExpenseAdvance__0__CardId}"/>
				    </li>
				    <li class="form-validate">
						<label class="control-textarea">备注</label>
						<textarea id="fp__tWfCust_ExpenseAdvance__0__Remarks" 
							name="fp__tWfCust_ExpenseAdvance__0__Remarks" rows="2">${datas.fp__tWfCust_ExpenseAdvance__0__Remarks}</textarea>
					</li>
				    <%-- <li class="form-validate" hidden="true">
				        <label><span class="required">*</span>付款账号</label>
						<input type="text" id="fp__tWfCust_ExpenseAdvance__0__PayAccount" name="fp__tWfCust_ExpenseAdvance__0__PayAccount"  style="width:160px;" value="${datas.fp__tWfCust_ExpenseAdvance__0__PayAccount}"/>
				    </li> --%>
				</div>
				
				<div id="tWfCust_ExpenseAdvanceDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_ExpenseAdvanceDtl_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px;margin-left:15px">
								<input id="fp__tWfCust_ExpenseAdvanceDtl__temInx__DeptCode" name="fp__tWfCust_ExpenseAdvanceDtl__temInx__DeptCode" value="${datas.fp__tWfCust_ExpenseAdvanceDtl__temInx__DeptCode }" hidden="true"/>
								<input id="fp__tWfCust_ExpenseAdvanceDtl__temInx__PK" name="fp__tWfCust_ExpenseAdvanceDtl__temInx__PK" value="${datas.fp__tWfCust_ExpenseAdvanceDtl__temInx__PK }" hidden="true" />
					    		<input id="fp__tWfCust_ExpenseAdvanceDtl__temInx__DeptDescr" name="fp__tWfCust_ExpenseAdvanceDtl__temInx__DeptDescr"  value="${datas.fp__tWfCust_ExpenseAdvanceDtl__temInx__DeptDescr }" hidden="true"/>
								<div class="validate-group row">	
									<li class="form-validate">
										<label>开支部门</label>
										<input type="text" id="deptCode_temInx" name="deptCode_temInx"  style="width:160px;"/>
									</li>
					         		<li class="form-validate">
								        <label><span class="required">*</span>开支额</label>
										<input type="text" id="fp__tWfCust_ExpenseAdvanceDtl__temInx__DtlAmount" name="fp__tWfCust_ExpenseAdvanceDtl__temInx__DtlAmount"  style="width:160px;"
										 onchange="chgMainAmount(true)" value="${datas.fp__tWfCust_ExpenseAdvanceDtl__temInx__DtlAmount}"/>
									</li>
								</div>	
								<div class="validate-group row" >	
									<li class="form-validate">
										<label class="control-textarea"><span class="required">*</span>开支内容</label>
										<textarea id="fp__tWfCust_ExpenseAdvanceDtl__temInx__Remarks" name="fp__tWfCust_ExpenseAdvanceDtl__temInx__Remarks" rows="2">${datas.fp__tWfCust_ExpenseAdvanceDtl__temInx__Remarks}</textarea>
									</li>
									<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_ExpenseAdvanceDtl')" id="btn_temInx">增加明细</button>
								</div>
							</div>	
						</div>	
					</div>
				</div>
				<c:if test="${((activityId == 'usertask12' || activityId == 'usertask13' || activityId == 'usertask26' ) && cmpcode == '01') || type == '-1' 
								|| (cmpcode != '01' && (activityId == 'usertask8' || activityId == 'usertask9')) }">
					<div class="validate-group row" style="border-top:1px solid rgb(223,223,223);height:45px">
					    <li class="form-validate" style="padding-top:10px">
					        <label><span class="required">*</span>审批金额</label>
							<input type="text" id="fp__tWfCust_ExpenseAdvance__0__ApproveAmount" name="fp__tWfCust_ExpenseAdvance__0__ApproveAmount" 
								 style="width:160px;" value="${datas.fp__tWfCust_ExpenseAdvance__0__ApproveAmount}" readonly="true"/>
					    </li>
					    <li class="form-validate" style="padding-top:10px">
					        <label>凭证号</label>
							<input type="text" id="fp__tWfCust_ExpenseAdvance__0__DocumentNo" name="fp__tWfCust_ExpenseAdvance__0__DocumentNo" 
								 style="width:160px;" value="${datas.fp__tWfCust_ExpenseAdvance__0__DocumentNo}" readonly="true"/>
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
function validateRefreshData(){
 	$("#dataForm").data("bootstrapValidator")
          .updateStatus("openComponent_employee_EmpCode", "NOT_VALIDATED",null)  
          .validateField("openComponent_employee_EmpCode")
 		 .updateStatus("fp__tWfCust_ExpenseAdvance__0__ActName", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_ExpenseAdvance__0__ActName")
          .updateStatus("fp__tWfCust_ExpenseAdvance__0__CardId", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_ExpenseAdvance__0__CardId")
          .updateStatus("fp__tWfCust_ExpenseAdvance__0__Bank", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_ExpenseAdvance__0__Bank")
          .updateStatus("fp__tWfCust_ExpenseAdvance__0__SubBranch", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_ExpenseAdvance__0__SubBranch")
          .updateStatus("fp__tWfCust_ExpenseAdvance__0__Amount", "NOT_VALIDATED",null)  
          .validateField("fp__tWfCust_ExpenseAdvance__0__Amount"); 
}

$(function(){
	// 设置明细表保存验证
	detailVerify = [
		{
			tableId:"tWfCust_ExpenseAdvanceDtl",
			Remarks:"开支内容不能为空", 
			DtlAmount:"预支额不能为空"
		},
		{
			tableId:"tWfCust_ExpenseAdvance",
			activityId:"usertask12",
			mainTable:"1",
			ApproveAmount:"审批金额不能为空",
		}
	];

	if("${cmpcode }" != ""){
		if("01" == "${cmpcode }" || "05" == "${cmpcode }" || "03" == "${cmpcode }"){
			$("#fp__tWfCust_ExpenseAdvance__0__Company").find("option").remove();
			$("#fp__tWfCust_ExpenseAdvance__0__Company").append("<option value= " + "福州有家" + ">" + "福州有家" + "</option>");
			$("#fp__tWfCust_ExpenseAdvance__0__Company").append("<option value= " + "美第家居" + ">" + "美第家居" + "</option>");
		}
		if("02" == "${cmpcode }"){
			$("#fp__tWfCust_ExpenseAdvance__0__Company").find("option").remove();
			$("#fp__tWfCust_ExpenseAdvance__0__Company").append("<option value= " + "厦门有家" + ">" + "厦门有家" + "</option>");
			$("#fp__tWfCust_ExpenseAdvance__0__Company").append("<option value= " + "漳州有家" + ">" + "漳州有家" + "</option>");
			$("#fp__tWfCust_ExpenseAdvance__0__Company").append("<option value= " + "美第家居" + ">" + "美第家居" + "</option>");
		}
		if("04" == "${cmpcode }"){
			$("#fp__tWfCust_ExpenseAdvance__0__Company").find("option").remove();
			$("#fp__tWfCust_ExpenseAdvance__0__Company").append("<option value= " + "郑州有家" + ">" + "郑州有家" + "</option>");
		}
		if("${m_umState}" != "A"){
			$("#fp__tWfCust_ExpenseAdvance__0__Company").attr("disabled",true);
		}
		if("${datas.fp__tWfCust_ExpenseAdvance__0__Company }" != ""){
			$("#fp__tWfCust_ExpenseAdvance__0__Company").val("${datas.fp__tWfCust_ExpenseAdvance__0__Company }");
		}
	}
	
	
	document.getElementById("tab_apply_fjView_li").style.display="block";
	function getEmpData(data){
		if(!data){
			return;
		} 
		if(data.cmpDescr){
			$("#fp__tWfCust_ExpenseAdvance__0__Company").val(data.cmpDescr);
		}
	}
	
	if("A"=="${m_umState}"){
		$("#EmpCode").openComponent_employee({showValue:"${employee.number }",showLabel:"${employee.nameChi}",callBack:getEmpData,readonly:true});
		$("#openComponent_employee_EmpCode").blur();
		$("#fp__tWfCust_ExpenseAdvance__0__EmpCode").val("${employee.number }");
		$("#fp__tWfCust_ExpenseAdvance__0__EmpName").val("${employee.nameChi }");
		$("#fp__tWfCust_ExpenseAdvance__0__EmpType").val("${employee.depType}");
		$("#fp__tWfCust_ExpenseAdvance__0__BefAmount").val("${employee.advanceAmount}");
	} else {
		$("#EmpCode").openComponent_employee({callBack:getEmpDetail,showValue:"${datas.fp__tWfCust_ExpenseAdvance__0__EmpCode}",
						showLabel:"${datas.fp__tWfCust_ExpenseAdvance__0__EmpName}" });	
		getAdvanceAmount("${datas.fp__tWfCust_ExpenseAdvance__0__EmpCode}");
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
		     fp__tWfCust_ExpenseAdvance__0__ActName:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "户名不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseAdvance__0__Bank:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "开户行不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseAdvance__0__SubBranch:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "支行不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseAdvance__0__CardId:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "卡号不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseAdvance__0__Amount:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "预支额不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_ExpenseAdvance__0__ApproveAmount:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "审批金额不能为空",
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});

	function getEmpDetail(data){
		if(!data) return;
		$("#fp__tWfCust_ExpenseAdvance__0__EmpCode").val(data.number);
		$("#fp__tWfCust_ExpenseAdvance__0__EmpName").val(data.namechi);
		var expenseDate = new Date(data.expensedate);
		var date = new Date();
		if(expenseDate.getMonth()<date.getMonth() || expenseDate.getFullYear()<date.getFullYear()){
			art.dialog({
				content:"该员工存在跨月预支单未核销",
			});
		}
		getAdvanceAmount(data.number);
		validateRefreshData();
	}
	
	// 如果有明细 则添加明细 ，如果没有 则判断页面是否有明细模板 有的话添加 第一个模板
	var detailList = ${detailList};
	if(detailList != ""){
		for (var tableName in detailList) {
			if($("#"+tableName).find("#group_temp").html()){
		       	addDetail_("",detailList[tableName]==0?0:detailList[tableName]-1,tableName);
			}
	    }
	    chgMainAmount(false);
	}

});

function addDetail_(flag,inx,groupId){
	var html = $("#"+groupId).find("#group_temp").html();
	addDetail(flag,html,inx,"returnFn",groupId);
}

function returnFn(flag,i){
	if(flag != "add"){
		$("#fp__tWfCust_ExpenseAdvanceDtl__"+i+"__PK").val(detailJson["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__PK"]);
		$("#fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DtlAmount").val(detailJson["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DtlAmount"]);
		$("#fp__tWfCust_ExpenseAdvanceDtl__"+i+"__Remarks").val(detailJson["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__Remarks"]);
		$("#fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DeptCode").val(detailJson["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DeptCode"]);
		$("#fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DeptDescr").val(detailJson["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DeptDescr"]);
		$("#deptCode_"+i).openComponent_department({callBack:getDeptDetail,condition:{salfDept:"1"},
			 showLabel:detailJson["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DeptDescr"],
			showValue:detailJson["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DeptCode"]});	
		$("#openComponent_department_deptCode_"+i).attr("readonly","true");
	}else{
		$("#deptCode_"+i).openComponent_department({callBack:getDeptDetail,condition:{salfDept:"1"},
			showLabel:detailJson["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DeptDescr"],
			showValue:detailJson["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DeptCode"]});
		$("#openComponent_department_deptCode_"+i).attr("readonly","true");
	}
}

function getDeptDetail(data){
	var id = this.id.split("openComponent_department_deptCode_")[1];
	$("#fp__tWfCust_ExpenseAdvanceDtl__"+id+"__DeptCode").val(data.code);
	$("#fp__tWfCust_ExpenseAdvanceDtl__"+id+"__DeptDescr").val(data.desc2);
}

function clearDetail(){
	$("#fp__tWfCust_ExpenseAdvanceDtl__0__DeptCode").val("");
	$("#fp__tWfCust_ExpenseAdvanceDtl__0__DeptDescr").val("");
	$("#fp__tWfCust_ExpenseAdvanceDtl__0__DtlAmount").val("");
	$("#fp__tWfCust_ExpenseAdvanceDtl__0__Remarks").val("");
}

//调用WfProcinst_apply 页面的getOperator方法
function getOperator_(flag){ 
	
	if("A" != "${m_umState}"){
		callSetReadonly();
	}
	var elMap = {Amount:myRound(Math.floor($("#fp__tWfCust_ExpenseAdvance__0__Amount").val()),0)
				,Company:$.trim($("#fp__tWfCust_ExpenseAdvance__0__Company").val())};
	getOperator(flag,elMap);
} 

function setElMap(){
	elMaps = {Amount:myRound(Math.floor($("#fp__tWfCust_ExpenseAdvance__0__Amount").val()),0)
				,Company:$.trim($("#fp__tWfCust_ExpenseAdvance__0__Company").val())
			};
}

function chgMainAmount(flag){
	var length = $("#tWfCust_ExpenseAdvanceDtl").children("div").length-1;
	var amount = 0;
	var returnAmount =0.0;
	for(var i=0;i<20;i++){
		if($("#fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DtlAmount").val()){
			returnAmount = $("#fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DtlAmount").val().replace(/[^\-?\d.]/g,'');
			$("#fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DtlAmount").val(returnAmount);
			if($("#fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DtlAmount").val()){
				amount =myRound(returnAmount,2) + myRound(amount,4);
			} 
		}
	}
	$("#fp__tWfCust_ExpenseAdvance__0__Amount").val(myRound(amount,4));
	if(flag){
		getOperator_(flag);
		validateRefreshData();
	}
	//moneyToCapital(document.getElementById("fp__tWfCust_PrjReturnSet__0__ReturnAmount"),"capital");
}

function delDetalReturnFn(){
	chgMainAmount(true);
}

function getEmpAccount(){
	Global.Dialog.showDialog("EmpAccount",{
		title:"选择银行账号",
		url:"${ctx}/admin/wfProcInst/goEmpAccount",
		postData:{czybh:$.trim($("#EmpCode").val())},
		height: 640,
		width:900,
		returnFun:function(data){
			$("#fp__tWfCust_ExpenseAdvance__0__ActName").val(data.actname);
			$("#fp__tWfCust_ExpenseAdvance__0__Bank").val(data.bank);
			$("#fp__tWfCust_ExpenseAdvance__0__CardId").val(data.cardid.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{4})|(\d{4}))(?=\d)/g,'$1 '));
			$("#fp__tWfCust_ExpenseAdvance__0__SubBranch").val(data.subbranch);
			validateRefreshData()
		}
	});
}

function saveAccount(){
	$.ajax({
		url:"${ctx}/admin/wfProcInst/doSaveAccount",
		type: 'post',
		data: {actName:$("#fp__tWfCust_ExpenseAdvance__0__ActName").val(),
				bank:$("#fp__tWfCust_ExpenseAdvance__0__Bank").val(),
				cardId:$("#fp__tWfCust_ExpenseAdvance__0__CardId").val(),
				subBranch:$("#fp__tWfCust_ExpenseAdvance__0__SubBranch").val()
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
	if("usertask13" == "${activityId}"){
		$("#comment").val($("#artDialogComment").val());
		$("#dataForm input[type='text']").removeAttr("disabled","true");
		$("#dataForm select").removeAttr("disabled","true");
		var datas = $("#dataForm").serialize();
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
function getAdvanceAmount(empCode){
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
			$("#fp__tWfCust_ExpenseAdvance__0__BefAmount").val(obj);
		}
	});
}

function callSetReadonly(actId){
	setReadonly(actId);
}
</script>
</body>
</html>

