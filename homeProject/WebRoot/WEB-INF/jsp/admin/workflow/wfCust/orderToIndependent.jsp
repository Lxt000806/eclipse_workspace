<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>下定转独立销售申请</title>
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
	   		<input id="fp__tWfCust_OrderToIndependent__0__Company" name="fp__tWfCust_OrderToIndependent__0__Company" value="${datas.fp__tWfCust_OrderToIndependent__0__Company }" hidden="true"/>
    		<input id="fp__tWfCust_OrderToIndependent__0__CustCode" name="fp__tWfCust_OrderToIndependent__0__CustCode" value="${datas.fp__tWfCust_OrderToIndependent__0__CustCode }"  hidden="true"/>
    		<input id="fp__tWfCust_OrderToIndependent__0__CustDescr" name="fp__tWfCust_OrderToIndependent__0__CustDescr" value="${datas.fp__tWfCust_OrderToIndependent__0__CustDescr}" hidden="true"/>
        	<input id="fp__tWfCust_OrderToIndependent__0__NewCustCode" name="fp__tWfCust_OrderToIndependent__0__NewCustCode" value="${datas.fp__tWfCust_OrderToIndependent__0__NewCustCode }"  hidden="false"/>
    		<input id="fp__tWfCust_OrderToIndependent__0__NewCustDescr" name="fp__tWfCust_OrderToIndependent__0__NewCustDescr" value="${datas.fp__tWfCust_OrderToIndependent__0__NewCustDescr}" hidden="true"/>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>转出客户</label>
						<input type="text" id="custCode" name="custCode"  style="width:160px;" value="${datas.fp__tWfCust_PrjRefund__0__CustCode}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>转出楼盘</label>
						<input type="text" id="fp__tWfCust_OrderToIndependent__0__Address" name="fp__tWfCust_OrderToIndependent__0__Address"  style="width:160px;" value="${datas.fp__tWfCust_OrderToIndependent__0__Address}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>转出金额</label>
						<input type="text" id="fp__tWfCust_OrderToIndependent__0__Amount" name="fp__tWfCust_OrderToIndependent__0__Amount" 
								 style="width:160px;" value="${datas.fp__tWfCust_OrderToIndependent__0__Amount}" onkeyup="setNewAmount()"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>分公司</label>
						<input type="text" id="fp__tWfCust_OrderToIndependent__0__CompanyDescr" name="fp__tWfCust_OrderToIndependent__0__CompanyDescr"  readonly="true"
						style="width:160px;" value="${datas.fp__tWfCust_OrderToIndependent__0__CompanyDescr}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>转出审批金额</label>
						<input type="text" id="fp__tWfCust_OrderToIndependent__0__ConfirmAmount" name="fp__tWfCust_OrderToIndependent__0__ConfirmAmount"  readonly="true"
						style="width:160px;" value="${datas.fp__tWfCust_OrderToIndependent__0__ConfirmAmount}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>凭证号</label>
						<input type="text" id="fp__tWfCust_OrderToIndependent__0__DocumentNo" name="fp__tWfCust_OrderToIndependent__0__DocumentNo" 
								 style="width:160px;" value="${datas.fp__tWfCust_OrderToIndependent__0__DocumentNo}"/>
				    </li>
				</div>
				
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>转入客户编号</label>
						<input type="text" id="newCustCode" name="newCustCode"  style="width:160px;" value="${datas.fp__tWfCust_PrjRefund__0__NewCustCode}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>转入楼盘</label>
						<input type="text" id="fp__tWfCust_OrderToIndependent__0__NewAddress" name="fp__tWfCust_OrderToIndependent__0__NewAddress"  
								style="width:160px;" value="${datas.fp__tWfCust_OrderToIndependent__0__NewAddress}" />
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>转入金额</label>
						<input type="text" id="fp__tWfCust_OrderToIndependent__0__NewAmount" name="fp__tWfCust_OrderToIndependent__0__NewAmount" 
								 style="width:160px;" value="${datas.fp__tWfCust_OrderToIndependent__0__NewAmount}" onchange="checkNewAmount()" />
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>转入审批金额</label>
						<input type="text" id="fp__tWfCust_OrderToIndependent__0__NewConfirmAmount" name="fp__tWfCust_OrderToIndependent__0__NewConfirmAmount"  readonly="true"
						style="width:160px;" value="${datas.fp__tWfCust_OrderToIndependent__0__NewConfirmAmount}"/>
				    </li>
				</div>
				<div class="validate-group row" >	
	         		<li class="form-validate">
						<label><span class="required">*</span>ERP退定</label>
						<select id="fp__tWfCust_OrderToIndependent__0__ErpReturn" name="fp__tWfCust_OrderToIndependent__0__ErpReturn" 
							 value="${datas.fp__tWfCust_OrderToIndependent__0__ErpReturn }">
								<option value ="">请选择...</option>
								<option value ="未操作" ${ datas.fp__tWfCust_OrderToIndependent__0__ErpReturn == '未操作' ? 'selected' : ''}>未操作</option>
								<option value ="已退定归档" ${ datas.fp__tWfCust_OrderToIndependent__0__ErpReturn == '已退定归档' ? 'selected' : ''}>已退定归档</option>
						</select>
					</li>
	         		<li class="form-validate">
						<label><span class="required">*</span>独立销售</label>
						<house:DictMulitSelect id="itemType1" dictCode="" 
							sql=" select Descr,'' Desc1 from tItemType1  where code in ('RZ','JC','ZC')"  onCheck="updateItemType1()"
						sqlValueKey="Descr" sqlLableKey="Desc1" selectedValue="${datas.fp__tWfCust_OrderToIndependent__0__ItemType1 }"></house:DictMulitSelect>
					</li>
					<input hidden="true" type="text" id="fp__tWfCust_OrderToIndependent__0__ItemType1" name="fp__tWfCust_OrderToIndependent__0__ItemType1"  style="width:160px;" value="${datas.fp__tWfCust_OrderToIndependent__0__ItemType1}"/>
				</div>	
				<div class="validate-group row" >	
	         		<li class="form-validate">
						<label class="control-textarea"><span class="required">*</span>退定的原因</label>
						<textarea id="fp__tWfCust_OrderToIndependent__0__Reason" name="fp__tWfCust_OrderToIndependent__0__Reason" rows="2">${datas.fp__tWfCust_OrderToIndependent__0__Reason}</textarea>
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
var html_ = $("#detail_div").html();
var detailJson=${detailJson};
$(function(){
	detailVerify = [
		{
    		tableId:"tWfCust_OrderToIndependent",
			activityId:"usertask8",
    		ConfirmAmount:"转出审核金额不能为空",
    	},{
    		tableId:"tWfCust_OrderToIndependent",
			activityId:"usertask7",
    		NewConfirmAmount:"转入审核金额不能为空",
    	}
	];

	document.getElementById("tab_apply_fjView_li").style.display="block";
	callSetReadonly();
	function getCustDetail(data){
		if(data){
			$("#fp__tWfCust_OrderToIndependent__0__CustCode").val(data.code); 
			$("#fp__tWfCust_OrderToIndependent__0__CustDescr").val(data.descr); 
			$("#fp__tWfCust_OrderToIndependent__0__Address").val(data.address); 
			$("#fp__tWfCust_OrderToIndependent__0__Company").val(data.company); 
			$("#fp__tWfCust_OrderToIndependent__0__CompanyDescr").val(data.companydescr); 
			$("#dataForm").data("bootstrapValidator")
                .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
                .validateField("openComponent_customerOA_custCode")
                .updateStatus("fp__tWfCust_OrderToIndependent__0__Address", "NOT_VALIDATED",null)  
                .validateField("fp__tWfCust_OrderToIndependent__0__Address"); 
            getOperator_();
		}
	}
	
	function getNewCustDetail(data){
		if(data){
			$("#fp__tWfCust_OrderToIndependent__0__NewCustCode").val(data.code); 
			$("#fp__tWfCust_OrderToIndependent__0__NewCustDescr").val(data.descr); 
			$("#fp__tWfCust_OrderToIndependent__0__NewAddress").val(data.address); 
			$("#dataForm").data("bootstrapValidator")
                .updateStatus("openComponent_customerOA_newCustCode", "NOT_VALIDATED",null)  
                .validateField("openComponent_customerOA_newCustCode")
                .updateStatus("fp__tWfCust_OrderToIndependent__0__NewAddress", "NOT_VALIDATED",null)  
                .validateField("fp__tWfCust_OrderToIndependent__0__NewAddress"); 
            getOperator_();
		}
	}
	$("#custCode").openComponent_customerOA({callBack:getCustDetail,showValue:"${datas.fp__tWfCust_OrderToIndependent__0__CustCode}"
								,showLabel:"${datas.fp__tWfCust_OrderToIndependent__0__CustDescr}"
								,condition:{authorityCtrl:"1",status:"5"}
	});	
	$("#newCustCode").openComponent_customerOA({callBack:getNewCustDetail,showValue:"${datas.fp__tWfCust_OrderToIndependent__0__NewCustCode}"
								,showLabel:"${datas.fp__tWfCust_OrderToIndependent__0__NewCustDescr}"
								,condition:{isAddAllInfo:"0"}
	});	
					
	// 出纳出款 财务会计审核 增加客服付款查看功能
	if("${taskName}" == "出纳出款" || "${taskName}" == "财务会计审核"){
		$("#topBtnDiv").append("<button type=\"button\" class=\"btn btn-system\" onclick=\"goCustPay()\">客户付款</button>");
		
		if($("#fp__tWfCust_OrderToIndependent__0__NewConfirmAmount").val()==""){
			$("#fp__tWfCust_OrderToIndependent__0__NewConfirmAmount").val($("#fp__tWfCust_OrderToIndependent__0__NewAmount").val());
		}
		
		if($("#fp__tWfCust_OrderToIndependent__0__ConfirmAmount").val()==""){
			$("#fp__tWfCust_OrderToIndependent__0__ConfirmAmount").val($("#fp__tWfCust_OrderToIndependent__0__Amount").val());
		}
	}
	
	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
		     openComponent_customerOA_newCustCode:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "转如客户编号不能为空"  ,
		            },
		        }  
		     },
		     openComponent_customerOA_custCode:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "转出客户编号不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_OrderToIndependent__0__Address:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "转出楼盘不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_OrderToIndependent__0__Amount:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "转出金额不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_OrderToIndependent__0__NewAmount:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "转入金额不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_OrderToIndependent__0__NewAddress:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "转出楼盘不能为空"  ,
		            },
		        }  
		     },
		     itemType1:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "独立销售不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_OrderToIndependent__0__Reason:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "退定原因不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_OrderToIndependent__0__ErpReturn:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "Erp退定不能为空"  ,
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']"
	});
	if("${m_umState}" =="A"){
		getOperator_();
	}
	
});
function setNewAmount(){
	var newAmount = $("#fp__tWfCust_OrderToIndependent__0__NewAmount").val();
	var amount = myRound($("#fp__tWfCust_OrderToIndependent__0__Amount").val());
	
	$("#fp__tWfCust_OrderToIndependent__0__NewAmount").val(amount);
}
function checkNewAmount(){
	var newAmount = myRound($("#fp__tWfCust_OrderToIndependent__0__NewAmount").val());
	var amount = myRound($("#fp__tWfCust_OrderToIndependent__0__Amount").val());
	if(newAmount > amount){
		art.dialog({
			content:"转入金额不能大于转出金额",
		});
		$("#fp__tWfCust_OrderToIndependent__0__NewAmount").val(amount);
		return;
	}
}
function getOperator_(flag){ 
	var elMap = {Company:$("#fp__tWfCust_OrderToIndependent__0__Company").val()};
	getOperator(flag,elMap);
} 
function updateItemType1(){
	$("#fp__tWfCust_OrderToIndependent__0__ItemType1").val($("#itemType1").val());
	$("#dataForm").data("bootstrapValidator")
                .updateStatus("itemType1", "NOT_VALIDATED",null)  
                .validateField("itemType1"); 
}
function callSetReadonly(actId){
	setReadonly(actId);
	$("#itemType1_NAME").removeAttr("disabled","true");
}

function goCustPay() {
	
	Global.Dialog.showDialog("custPay",{
		title:"客户付款",
		url:"${ctx}/admin/custPay/goReturnPayCustPay?code="+$("#fp__tWfCust_OrderToIndependent__0__CustCode").val(),
		postData:{m_umState:"V"},
		height:750,
		width:1300,
		returnFun: goto_query
	});
}
</script>
</body>
</html>

