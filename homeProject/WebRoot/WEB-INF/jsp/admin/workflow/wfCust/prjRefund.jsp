<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>工程退款</title>
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
    		<input id="photoPK" name="photoPK" value=""  hidden="true"/><%--有上传图片时必备--%>
    		<input id="url" name="url" value="${url }" hidden="true" /><%--有上传图片时必备--%>
    		<input id="activityId" name="activityId" value="${activityId }" hidden="true" />
    		<input id="fp__tWfCust_PrjRefund__0__CustCode" name="fp__tWfCust_PrjRefund__0__CustCode" value="${datas.fp__tWfCust_PrjRefund__0__CustCode }"  hidden="true"/>
    		<input id="fp__tWfCust_PrjRefund__0__CustDescr" name="fp__tWfCust_PrjRefund__0__CustDescr" value="${datas.fp__tWfCust_PrjRefund__0__CustDescr}" hidden="true"/>
			<input id="fp__tWfCust_PrjRefund__0__Company" name="fp__tWfCust_PrjRefund__0__Company" value="${datas.fp__tWfCust_PrjRefund__0__Company }" hidden="true"/>
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
						<input type="text" id="fp__tWfCust_PrjRefund__0__Address" name="fp__tWfCust_PrjRefund__0__Address"  style="width:160px;" value="${datas.fp__tWfCust_PrjRefund__0__Address}"/>
				    </li>
				</div>
				<div class="validate-group row" >	
	         		<li class="form-validate">
						<label><span class="required">*</span>是否收回收据</label>
						<select id="fp__tWfCust_PrjRefund__0__IsReturnReceipt" name="fp__tWfCust_PrjRefund__0__IsReturnReceipt" value="${datas.fp__tWfCust_StakeholderChgDtl__0__IsReturnReceipt }">
								<option value ="">请选择...</option>
								<option value ="是" ${ datas.fp__tWfCust_PrjRefund__0__IsReturnReceipt == '是' ? 'selected' : ''}>是</option>
								<option value ="否" ${ datas.fp__tWfCust_PrjRefund__0__IsReturnReceipt == '否' ? 'selected' : ''}>否</option>
						</select>
					</li>
	         		<li class="form-validate">
						<label><span class="required">*</span>是否开出收据</label>
						<select id="fp__tWfCust_PrjRefund__0__HasNewReceipt" name="fp__tWfCust_PrjRefund__0__HasNewReceipt" value="${datas.fp__tWfCust_PrjRefund__0__HasNewReceipt }">
								<option value ="">请选择...</option>
								<option value ="是" ${ datas.fp__tWfCust_PrjRefund__0__HasNewReceipt == '是' ? 'selected' : ''}>是</option>
								<option value ="否" ${ datas.fp__tWfCust_PrjRefund__0__HasNewReceipt == '否' ? 'selected' : ''}>否</option>
						</select>
					</li>
					<li>
				        <label>新收据号</label>
						<input type="text" id="fp__tWfCust_PrjRefund__0__NewReceiptNo" name="fp__tWfCust_PrjRefund__0__NewReceiptNo"  style="width:160px;"
						 value="${datas.fp__tWfCust_PrjRefund__0__NewReceiptNo}"/>
					</li>
	         		<li class="form-validate">
						<label><span class="required">*</span>退款额</label>
						<input type="text" id="fp__tWfCust_PrjRefund__0__Amount" readonly="true" onkeyup="moneyToCapital(this,'capital')" name="fp__tWfCust_PrjRefund__0__Amount"  style="width:160px;"
						 placeholder="自动计算" value="${datas.fp__tWfCust_PrjRefund__0__Amount}"/>
						 <div style="float:buttom">
							 <label></label>			<!-- 大写的ID 要为 capital -->
							<input type="text" id="capital" name="capital"  style="width:160px;border:0px;float:buttom" readonly="true" disabled="true"/>
						 </div>
					</li>
				</div>	
				<div class="validate-group row" >	
	         		<li class="form-validate">
						<label class="control-textarea"><span class="required">*</span>备注</label>
						<textarea id="fp__tWfCust_PrjRefund__0__Reason" name="fp__tWfCust_PrjRefund__0__Reason" rows="2">${datas.fp__tWfCust_PrjRefund__0__Reason}</textarea>
					</li>
					<li class="form-validate">
				        <label><span class="required">*</span>凭证号</label>
						<input type="text" id="fp__tWfCust_PrjRefund__0__DocumentNo" name="fp__tWfCust_PrjRefund__0__DocumentNo"  
								style="width:160px;" value="${datas.fp__tWfCust_PrjRefund__0__DocumentNo}"/>
				    </li>
				</div>
				<div id="tWfCust_PrjRefundDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_PrjRefundDtl_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px">
								<div class="validate-group row" >
									<input type="text" id="fp__tWfCust_PrjRefundDtl__temInx__PK" name="fp__tWfCust_PrjRefundDtl__temInx__PK" style="width:160px;"
									 value="${datas.fp__tWfCust_PrjRefundDtl__temInx__PK}" hidden="true"/>
					         		<li class="form-validate">
								        <label><span class="required">*</span>账户名</label>
										<input type="text" id="fp__tWfCust_PrjRefundDtl__temInx__ActName" name="fp__tWfCust_PrjRefundDtl__temInx__ActName"  style="width:160px;"
										 value="${datas.fp__tWfCust_PrjRefundDtl__temInx__ActName}"/>
									</li>
					         		<li class="form-validate">
										<label><span class="required">*</span>账号</label>
										<input type="text" id="fp__tWfCust_PrjRefundDtl__temInx__CardId" name="fp__tWfCust_PrjRefundDtl__temInx__CardId"  style="width:160px;"
										 value="${datas.fp__tWfCust_PrjRefundDtl__temInx__CardId}"/>
									</li>
					         		<li class="form-validate">
								        <label><span class="required">*</span>开户行</label>
										<input type="text" id="fp__tWfCust_PrjRefundDtl__temInx__Bank" name="fp__tWfCust_PrjRefundDtl__temInx__Bank"  style="width:160px;"
										 value="${datas.fp__tWfCust_PrjRefundDtl__temInx__Bank}"/>
									</li>
					         		<li class="form-validate">
										<label><span class="required">*</span>支行</label>
										<input type="text" id="fp__tWfCust_PrjRefundDtl__temInx__SubBranch" name="fp__tWfCust_PrjRefundDtl__temInx__SubBranch"  style="width:160px;"
										 value="${datas.fp__tWfCust_PrjRefundDtl__temInx__SubBranch}"/>
									</li>
								</div>
								<div class="validate-group row" >
									<li class="form-validate">
										<label><span class="required">*</span>退款额</label>
										<input type="text" id="fp__tWfCust_PrjRefundDtl__temInx__Amount" name="fp__tWfCust_PrjRefundDtl__temInx__Amount"  style="width:160px;"
											onkeyup = "chgMainAmount()"
										 value="${datas.fp__tWfCust_PrjRefundDtl__temInx__Amount}"/>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>审批金额</label>
										<input type="text" id="fp__tWfCust_PrjRefundDtl__temInx__ConfirmAmount" name="fp__tWfCust_PrjRefundDtl__temInx__ConfirmAmount"  style="width:160px;"
										 value="${datas.fp__tWfCust_PrjRefundDtl__temInx__ConfirmAmount}" onchange="checkConfirmAmount(temInx)"/>
									</li>
									<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_PrjRefundDtl')" id="btn_temInx">增加明细</button>
								</div>
							</div>	
						</div>	
					</div>
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
var html_ = $("#detail_div").html();
var detailJson=${detailJson};
function validateRefresh(){
 	$("#dataForm").data("bootstrapValidator")
                .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
                .validateField("openComponent_customerOA_custCode"); 
}
$(function(){
	document.getElementById("tab_apply_fjView_li").style.display="block";
	detailVerify = [
		{
    		tableId:"tWfCust_PrjRefundDtl",
			activityId:"usertask8",
    		ConfirmAmount:"审核金额不能为空",
    	}
	];
	function getCustDetail(data){
		if(data){
			$("#fp__tWfCust_PrjRefund__0__CustCode").val(data.code); 
			$("#fp__tWfCust_PrjRefund__0__CustDescr").val(data.descr); 
			$("#fp__tWfCust_PrjRefund__0__Address").val(data.address); 
		}
		$("#dataForm").data("bootstrapValidator")
                .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
                .validateField("openComponent_customerOA_custCode")
                .updateStatus("fp__tWfCust_PrjRefund__0__Address", "NOT_VALIDATED",null)  
                .validateField("fp__tWfCust_PrjRefund__0__Address");
		delAllDetail("tWfCust_PrjRefundDtl");
		clearDetail();
	}
	$("#custCode").openComponent_customerOA({callBack:getCustDetail,showValue:"${datas.fp__tWfCust_PrjRefund__0__CustCode}",showLabel:"${datas.fp__tWfCust_PrjRefund__0__CustDescr}"
				,condition:{"authorityCtrl":"1",status:"4"}
	});	
	$("#openComponent_customerOA_custCode").attr("readonly",true);
	
	// 如果有明细 则添加明细 ，如果没有 则判断页面是否有明细模板 有的话添加 第一个模板
	var detailList = ${detailList}
	if(detailList != ""){
		for (var tableName in detailList) {
			if($("#"+tableName).find("#group_temp").html()){
		       	addDetail_("",detailList[tableName]==0?0:detailList[tableName]-1,tableName);
			}
	    }
	}
	/* if("${detailNum}" > 1){
		addDetail_("",myRound("${detailNum}")-1);
	}else{
		if(html_){
			addDetail_("add",0);
		}
	} */
	
	// 出纳出款 财务会计审核 增加客服付款查看功能
	if("${taskName}" == "出纳出款" || "${taskName}" == "财务会计审核"){
		$("#topBtnDiv").append("<button type=\"button\" class=\"btn btn-system\" onclick=\"goCustPay()\">客户付款</button>");
	}
	
	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
		     fp__tWfCust_PrjRefund__0__IsReturnReceipt:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "是否收回收据"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjRefund__0__HasNewReceipt:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "是否开出新收据不能为空"  ,
		            },
		        }  
		     },
		     openComponent_customerOA_custCode:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "客户编号不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjRefund__0__Address:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "楼盘不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjRefund__0__Reason:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "退款原因不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjRefundDtl__0__ActName:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "账户名不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjRefundDtl__0__CardId:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "账号不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjRefundDtl__0__Bank:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "开户行不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjRefundDtl__0__SubBranch:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "支行不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjRefund__0__Amount:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "退款额不能为空"  ,
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
	var elMap={};
	
	if("${m_umState}" == "A"){
		$("#fp__tWfCust_PrjRefund__0__Company").val("${employee.cmpCode}");
		$("#fp__tWfCust_PrjRefund__0__CompanyDescr").val("${employee.cmpDescr}");
		getOperator_("",elMap);
	}
	callSetReadonly();
});

function getOperator_(flag,elMap){
	var elMap={"Company":$.trim($("#fp__tWfCust_PrjRefund__0__Company").val())}

	getOperator(flag,elMap);
}

function addDetail_(flag,inx,groupId){
	var html = $("#"+groupId).find("#group_temp").html();
	addDetail(flag,html,inx,"returnFn",groupId);
}

function returnFn(flag,i){
	if(flag != "add"){
		moneyToCapital(document.getElementById("fp__tWfCust_PrjRefund__0__Amount"),"capital");
		$("#fp__tWfCust_PrjRefundDtl__"+i+"__ActName").val(detailJson["fp__tWfCust_PrjRefundDtl__"+i+"__ActName"]);
		$("#fp__tWfCust_PrjRefundDtl__"+i+"__CardId").val(detailJson["fp__tWfCust_PrjRefundDtl__"+i+"__CardId"]);
		$("#fp__tWfCust_PrjRefundDtl__"+i+"__Bank").val(detailJson["fp__tWfCust_PrjRefundDtl__"+i+"__Bank"]);
		$("#fp__tWfCust_PrjRefundDtl__"+i+"__SubBranch").val(detailJson["fp__tWfCust_PrjRefundDtl__"+i+"__SubBranch"]);/* 
		$("#fp__tWfCust_PrjRefundCustDtl__"+i+"__CustCode").val(detailJson["fp__tWfCust_PrjRefundCustDtl__"+i+"__CustCode"]);
		$("#fp__tWfCust_PrjRefundCustDtl__"+i+"__CustName").val(detailJson["fp__tWfCust_PrjRefundCustDtl__"+i+"__CustName"]); */
		$("#fp__tWfCust_PrjRefundDtl__"+i+"__Amount").val(detailJson["fp__tWfCust_PrjRefundDtl__"+i+"__Amount"]);
		$("#fp__tWfCust_PrjRefundDtl__"+i+"__PK").val(detailJson["fp__tWfCust_PrjRefundDtl__"+i+"__PK"]);
		if(!detailJson["fp__tWfCust_PrjRefundDtl__"+i+"__ConfirmAmount"] || 
					detailJson["fp__tWfCust_PrjRefundDtl__"+i+"__ConfirmAmount"] == ""){
			$("#fp__tWfCust_PrjRefundDtl__"+i+"__ConfirmAmount").val(detailJson["fp__tWfCust_PrjRefundDtl__"+i+"__Amount"]);
		} else {								 
			$("#fp__tWfCust_PrjRefundDtl__"+i+"__ConfirmAmount").val(detailJson["fp__tWfCust_PrjRefundDtl__"+i+"__ConfirmAmount"]);
		}
	}
}

function chgMainAmount(){
	var length = $("#tWfCust_PrjRefundDtl").children("div").length-1;
	var amount = 0;
	for(var i=0;i<20;i++){
		if($("#fp__tWfCust_PrjRefundDtl__"+i+"__Amount").val()){
			amount =myRound($("#fp__tWfCust_PrjRefundDtl__"+i+"__Amount").val(),4) + myRound(amount,4);
		}
	}
	
	$("#fp__tWfCust_PrjRefund__0__Amount").val(amount);
	moneyToCapital(document.getElementById("fp__tWfCust_PrjRefund__0__Amount"),"capital");
	$("#dataForm").data("bootstrapValidator")
	    .updateStatus("fp__tWfCust_PrjRefund__0__Amount", "NOT_VALIDATED",null)  
	    .validateField("fp__tWfCust_PrjRefund__0__Amount");
}
function delDetalReturnFn(){
	chgMainAmount();
}

function clearDetail(){
	$("#fp__tWfCust_PrjRefundDtl__0__ActName").val("");
	$("#fp__tWfCust_PrjRefundDtl__0__CardId").val("");
	$("#fp__tWfCust_PrjRefundDtl__0__Bank").val("");
	$("#fp__tWfCust_PrjRefundDtl__0__SubBranch").val("");
}

function callSetReadonly(actId){
	setReadonly(actId);
}

function checkConfirmAmount(i){
	var amount = myRound($("#fp__tWfCust_PrjRefundDtl__"+i+"__Amount").val());
	var confirmAmount = myRound($("#fp__tWfCust_PrjRefundDtl__"+i+"__ConfirmAmount").val());
	if(confirmAmount > amount){
		art.dialog({
			content:"审核金额不能大于退款额",
		});
		$("#fp__tWfCust_PrjRefundDtl__"+i+"__ConfirmAmount").val(amount);
		return;
	}
}

function goCustPay() {
	Global.Dialog.showDialog("custPay",{
		title:"客户付款",
		url:"${ctx}/admin/custPay/goReturnPayCustPay?code="+$("#fp__tWfCust_PrjRefund__0__CustCode").val(),
		postData:{m_umState:"V"},
		height:750,
		width:1300,
		returnFun: goto_query
	});
}
</script>
</body>
</html>

