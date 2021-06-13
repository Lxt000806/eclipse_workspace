<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>工程退单申请</title>
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
	   		<input id="empComment" name="empComment" value="" hidden=""/><!-- 评论   -->
	   		<input id="wfProcInstNo" name="wfProcInstNo" value="${wfProcInstNo }" hidden=""/>
	   		<input id="processInstId" name="processInstId" value="${piId }" hidden=""/>
    		<input id="photoPK" name="photoPK" value=""  hidden="true"/><%--有上传图片时必备--%>
    		<input id="url" name="url" value="${url }" hidden="true" /><%--有上传图片时必备--%>
    		<input id="activityId" name="activityId" value="${activityId }" hidden="true" />
    		<input id="fp__tWfCust_PrjReturnOrder__0__CustCode" name="fp__tWfCust_PrjReturnOrder__0__CustCode" value="${datas.fp__tWfCust_PrjReturnOrder__0__CustCode }"  hidden="true"/>
    		<input id="fp__tWfCust_PrjReturnOrder__0__Company" name="fp__tWfCust_PrjReturnOrder__0__Company" value="${datas.fp__tWfCust_PrjReturnOrder__0__Company }" hidden="true"/>
    		<input id="fp__tWfCust_PrjReturnOrder__0__CustType" name="fp__tWfCust_PrjReturnOrder__0__CustType" value="${datas.fp__tWfCust_PrjReturnOrder__0__CustType }" hidden="true"/>
    
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>客户编号</label>
						<input type="text" id="custCode" name="custCode"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnOrder__0__CustCode}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>楼盘</label>
						<input type="text" id="fp__tWfCust_PrjReturnOrder__0__Address" name="fp__tWfCust_PrjReturnOrder__0__Address"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnOrder__0__Address}"/>
				    </li>
				     <li class="form-validate">
				        <label><span class="required">*</span>业主姓名</label>
						<input type="text" id="fp__tWfCust_PrjReturnOrder__0__CustDescr" name="fp__tWfCust_PrjReturnOrder__0__CustDescr"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnOrder__0__CustDescr}"/>
				    </li>
				</div>
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>业务员</label>
						<input type="text" id="fp__tWfCust_PrjReturnOrder__0__BusinessMan" name="fp__tWfCust_PrjReturnOrder__0__BusinessMan"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnOrder__0__BusinessMan}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>设计师</label>
						<input type="text" id="fp__tWfCust_PrjReturnOrder__0__DesignMan" name="fp__tWfCust_PrjReturnOrder__0__DesignMan"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnOrder__0__DesignMan}"/>
				    </li>
				    <li class="form-validate">
						<label><span class="required">*</span>施工状态</label>
						<select id="fp__tWfCust_PrjReturnOrder__0__Status" name="fp__tWfCust_PrjReturnOrder__0__Status" value="${datas.fp__tWfCust_PrjReturnOrder__0__Status}" onchange="getOperator_()">
								<option value ="">请选择...</option>
								<option value ="未开工" ${ datas.fp__tWfCust_PrjReturnOrder__0__Status == '未开工' ? 'selected' : ''}>未开工</option>
								<option value ="已开工" ${ datas.fp__tWfCust_PrjReturnOrder__0__Status == '已开工' ? 'selected' : ''}>已开工</option>
						</select>
					</li>    
				</div>
				<div class="validate-group row" >	
	         		<li class="form-validate">
						<label class="control-textarea"><span class="required">*</span>退单原因</label>
						<textarea id="fp__tWfCust_PrjReturnOrder__0__Reason" name="fp__tWfCust_PrjReturnOrder__0__Reason" rows="2">${datas.fp__tWfCust_PrjReturnOrder__0__Reason}</textarea>
					</li>
					<li class="form-validate">
				        <label><span class="required">*</span>分公司</label>
						<input type="text" id="fp__tWfCust_PrjReturnOrder__0__CompanyDescr" name="fp__tWfCust_PrjReturnOrder__0__CompanyDescr"  readonly="true"
						style="width:160px;" value="${datas.fp__tWfCust_PrjReturnOrder__0__CompanyDescr}"/>
				    </li>
				</div>
				<div class="validate-group row" >	
	         		<li class="form-validate">
						<label><span class="required">*</span>合同造价</label>
						<input type="text" id="fp__tWfCust_PrjReturnOrder__0__ContractFee"  name="fp__tWfCust_PrjReturnOrder__0__ContractFee"  style="width:160px;"
						 readonly="true" value="${datas.fp__tWfCust_PrjReturnOrder__0__ContractFee}"/> 
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>设计费</label>
						<input type="text" id="fp__tWfCust_PrjReturnOrder__0__DesignFee"  name="fp__tWfCust_PrjReturnOrder__0__DesignFee"  style="width:160px;"
						 readonly="true" value="${datas.fp__tWfCust_PrjReturnOrder__0__DesignFee}"/> 
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>是否收设计费</label>
						<select id="fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee" name="fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee"
								onchange="setRealDesignFee(this)" value="${datas.fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee}">
								<option value ="">请选择...</option>
								<option value ="是" ${ datas.fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee == '是' ? 'selected' : ''}>是</option>
								<option value ="否" ${ datas.fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee == '否' ? 'selected' : ''}>否</option>
						</select>
					</li>
					
				</div>	
				<div class="validate-group row" >
					<li class="form-validate">
						<label><span class="required">*</span>实收设计费</label>
						<input type="text" id="fp__tWfCust_PrjReturnOrder__0__RealDesignFee" name="fp__tWfCust_PrjReturnOrder__0__RealDesignFee"  
							style="width:160px;" onchange="chgRealDesignFee(this)"
						 value="${datas.fp__tWfCust_PrjReturnOrder__0__RealDesignFee }"/> 
					</li>	
	         		<li class="form-validate">
						<label><span class="required">*</span>退款额</label>
						<input type="text" id="fp__tWfCust_PrjReturnOrder__0__ReturnAmount" name="fp__tWfCust_PrjReturnOrder__0__ReturnAmount"  style="width:160px;"
						 placeholder="自动计算"
						 readonly="true" value="${datas.fp__tWfCust_PrjReturnOrder__0__ReturnAmount}"/> 
					</li>
					
					<li class="form-validate">
						<label><span class="required">*</span>退单收回资料</label>
						<house:DictMulitSelect id="takeBackDateList" dictCode="" 
							sql=" select '设计协议' code ,'' descr 
								  union select '甲方合同' code ,'' descr
								  union select '收款收据' code ,'' descr " 
							 	onCheck="updateTakeBackDateList()"
						sqlValueKey="code" sqlLableKey="descr" selectedValue="${datas.fp__tWfCust_PrjReturnOrder__0__TakeBackDateList }"></house:DictMulitSelect>
					</li>
					<input hidden="true" type="text" id="fp__tWfCust_PrjReturnOrder__0__TakeBackDateList" name="fp__tWfCust_PrjReturnOrder__0__TakeBackDateList"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnOrder__0__TakeBackDateList}"/>
					<li class="form-validate">
						<label><span class="required">*</span>凭证号</label>
						<input type="text" id="fp__tWfCust_PrjReturnOrder__0__DocumentNo" name="fp__tWfCust_PrjReturnOrder__0__DocumentNo"  style="width:160px;"
						 readonly="true" value="${datas.fp__tWfCust_PrjReturnOrder__0__DocumentNo}"/> 
					</li>
				</div>	
				<div id="tWfCust_PrjReturnOrderDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_PrjReturnOrderDtl_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px">
								<input type="text" id="fp__tWfCust_PrjReturnOrderDtl__temInx__PK" name="fp__tWfCust_PrjReturnOrderDtl__temInx__PK" style="width:160px;"
									 value="${datas.fp__tWfCust_PrjReturnOrderDtl__temInx__PK}" hidden="true"/>
				         		<li class="form-validate">
							        <label><span class="required">*</span>账户名</label>
									<input type="text" id="fp__tWfCust_PrjReturnOrderDtl__temInx__ActName" name="fp__tWfCust_PrjReturnOrderDtl__temInx__ActName"  style="width:160px;"
									 value="${datas.fp__tWfCust_PrjReturnOrderDtl__temInx__ActName}"/>
								</li>
				         		<li class="form-validate">
									<label><span class="required">*</span>账号</label>
									<input type="text" id="fp__tWfCust_PrjReturnOrderDtl__temInx__CardId" name="fp__tWfCust_PrjReturnOrderDtl__temInx__CardId"  style="width:160px;"
									 value="${datas.fp__tWfCust_PrjReturnOrderDtl__temInx__CardId}"/>
								</li>
				         		<li class="form-validate">
							        <label><span class="required">*</span>开户行</label>
									<input type="text" id="fp__tWfCust_PrjReturnOrderDtl__temInx__Bank" name="fp__tWfCust_PrjReturnOrderDtl__temInx__Bank"  style="width:160px;"
									 value="${datas.fp__tWfCust_PrjReturnOrderDtl__temInx__Bank}"/>
								</li>
								<li class="form-validate" id="subBranch">
									<label><span class="required">*</span>支行</label>
									<input type="text" id="fp__tWfCust_PrjReturnOrderDtl__temInx__SubBranch" name="fp__tWfCust_PrjReturnOrderDtl__temInx__SubBranch"  style="width:160px;"
									 value="${datas.fp__tWfCust_PrjReturnOrderDtl__temInx__SubBranch}"/>
								</li>
								<li class="form-validate" id="subBranch">
									<label>退款额</label>
									<input type="text" id="fp__tWfCust_PrjReturnOrderDtl__temInx__ReturnAmount" name="fp__tWfCust_PrjReturnOrderDtl__temInx__ReturnAmount"  style="width:160px;"
									  onkeyup="chgMainAmount(this)" value="${datas.fp__tWfCust_PrjReturnOrderDtl__temInx__ReturnAmount}"/>
								</li>
								<li class="form-validate">
							        <label><span class="required">*</span>审核金额</label>
									<input type="text" id="fp__tWfCust_PrjReturnOrderDtl__temInx__ConfirmAmount" name="fp__tWfCust_PrjReturnOrderDtl__temInx__ConfirmAmount"  style="width:160px;"
									 		value="${datas.fp__tWfCust_PrjReturnOrderDtl__temInx__ConfirmAmount}" onchange="checkConfirmAmount(temInx)"/>
								</li>
								<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_PrjReturnOrderDtl')" id="btn_temInx">增加明细</button>
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
var badRequest_ = false;
var mistakeMsg_ = "";
$(function(){
	document.getElementById("tab_apply_fjView_li").style.display="block";
	detailVerify = [
    	{
    		tableId:"tWfCust_PrjReturnOrderDtl",
    		ActName:"账户名不能为空",
    		Bank:"开户行不能为空",
    		SubBranch:"支行不能为空",
    		CardId:"卡号不能为空",
    		ReturnAmount:"退款额不能为空",
    		activityId:"startevent",
    	}, {
    		tableId:"tWfCust_PrjReturnOrderDtl",
			activityId:"usertask42",
    		ConfirmAmount:"审核金额不能为空",
    	}
	];
	callSetReadonly();
	function getCustDetail(data){
		if(data){
			console.log(data.custtypedescr);
			$("#fp__tWfCust_PrjReturnOrder__0__CustCode").val(data.code); 
			$("#fp__tWfCust_PrjReturnOrder__0__CustDescr").val(data.descr); 
			$("#fp__tWfCust_PrjReturnOrder__0__Address").val(data.address); 
			$("#fp__tWfCust_PrjReturnOrder__0__BusinessMan").val(data.businessmandescr); 
			$("#fp__tWfCust_PrjReturnOrder__0__DesignMan").val(data.designmandescr); 
			$("#fp__tWfCust_PrjReturnOrder__0__Company").val(data.company); 
			$("#fp__tWfCust_PrjReturnOrder__0__CompanyDescr").val(data.companydescr); 
			$("#fp__tWfCust_PrjReturnOrder__0__DesignFee").val(data.designfee); 
			$("#fp__tWfCust_PrjReturnOrder__0__CustType").val(data.custtypedescr); 
			
			if(data.confirmbegin != "" && data.confirmbegin != "undefined" && data.confirmbegin){
				$("#fp__tWfCust_PrjReturnOrder__0__Status").val("已开工"); 
			} else{
				$("#fp__tWfCust_PrjReturnOrder__0__Status").val("未开工"); 
			}
			if(data.realdesignfee != 0){
				$("#fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee").val("是");
				$("#fp__tWfCust_PrjReturnOrder__0__RealDesignFee").removeAttr("readonly",true);
				var val = $("#fp__tWfCust_PrjReturnOrder__0__RealDesignFee").val();
				if(val == 0 || val == ""){
					badRequest_ = true;
					mistakeMsg_ = "是否收设计费为'是'时,实收设计费不能为0";
				} else {
					badRequest_ = false;
					mistakeMsg_ = "";
				}
			}else{
				$("#fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee").val("否");
				$("#fp__tWfCust_PrjReturnOrder__0__RealDesignFee").val(0);
				$("#fp__tWfCust_PrjReturnOrder__0__RealDesignFee").attr("readonly",true);
				badRequest_ = false;
				mistakeMsg_ = "";
			}
			$("#fp__tWfCust_PrjReturnOrder__0__ContractFee").val(myRound(data.contractfee,4) + myRound(data.tax,4)); 
			$("#fp__tWfCust_PrjReturnOrder__0__Status").attr("disabled","true");
			$("#fp__tWfCust_PrjReturnOrder__0__CustDescr").attr("readonly",true);
			$("#fp__tWfCust_PrjReturnOrder__0__Address").attr("readonly",true);
			$("#fp__tWfCust_PrjReturnOrder__0__BusinessMan").attr("readonly",true);
			$("#fp__tWfCust_PrjReturnOrder__0__DesignMan").attr("readonly",true);
			$("#fp__tWfCust_PrjReturnOrder__0__Company").attr("readonly",true);
			$("#fp__tWfCust_PrjReturnOrder__0__CompanyDescr").attr("readonly",true);
			$("#fp__tWfCust_PrjReturnOrder__0__ContractFee").attr("readonly",true);
			$("#fp__tWfCust_PrjReturnOrder__0__DesignFee").attr("readonly",true);
		}	 
		
		validateRefresh();
		delAllDetail("tWfCust_PrjReturnOrderDtl");
		clearDetail();
		getOperator_();
	}
	function validateRefresh(){
	 	$("#dataForm").data("bootstrapValidator")
	                //.updateStatus("takeBackDateList", "NOT_VALIDATED",null)  
	                //.validateField("takeBackDateList")
	                .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
	                .validateField("openComponent_customerOA_custCode")
	                .updateStatus("fp__tWfCust_PrjReturnOrder__0__Status", "NOT_VALIDATED",null)  
	                .validateField("fp__tWfCust_PrjReturnOrder__0__Status")
	                .updateStatus("fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee", "NOT_VALIDATED",null)  
	                .validateField("fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee")
	                .updateStatus("fp__tWfCust_PrjReturnOrder__0__ContractFee", "NOT_VALIDATED",null)  
	                .validateField("fp__tWfCust_PrjReturnOrder__0__ContractFee")
	                .updateStatus("fp__tWfCust_PrjReturnOrder__0__DesignFee", "NOT_VALIDATED",null)  
	                .validateField("fp__tWfCust_PrjReturnOrder__0__DesignFee")
	                .updateStatus("fp__tWfCust_PrjReturnOrder__0__RealDesignFee", "NOT_VALIDATED",null)  
	                .validateField("fp__tWfCust_PrjReturnOrder__0__RealDesignFee")
	                .updateStatus("fp__tWfCust_PrjReturnOrder__0__DesignMan", "NOT_VALIDATED",null)  
	                .validateField("fp__tWfCust_PrjReturnOrder__0__DesignMan")
	                .updateStatus("fp__tWfCust_PrjReturnOrder__0__BusinessMan", "NOT_VALIDATED",null)  
	                .validateField("fp__tWfCust_PrjReturnOrder__0__BusinessMan")
	                .updateStatus("fp__tWfCust_PrjReturnOrder__0__Address", "NOT_VALIDATED",null)  
	                .validateField("fp__tWfCust_PrjReturnOrder__0__Address")
	                .updateStatus("fp__tWfCust_PrjReturnOrder__0__CustDescr", "NOT_VALIDATED",null)  
	                .validateField("fp__tWfCust_PrjReturnOrder__0__CustDescr")
	                .updateStatus("fp__tWfCust_PrjReturnOrder__0__ReturnAmount", "NOT_VALIDATED",null)  
	                .validateField("fp__tWfCust_PrjReturnOrder__0__ReturnAmount")
	                ; 
	}
	$("#custCode").openComponent_customerOA({callBack:getCustDetail,showValue:"${datas.fp__tWfCust_PrjReturnOrder__0__CustCode}",
						showLabel:"${datas.fp__tWfCust_PrjReturnOrder__0__CustDescr}"
						,condition:{authorityCtrl:"1", status: "4,5"}
	});	
	
	// 如果有明细 则添加明细 ，如果没有 则判断页面是否有明细模板 有的话添加 第一个模板
	var detailList = ${detailList}
	if(detailList != ""){
		for (var tableName in detailList) {
			if($("#"+tableName).find("#group_temp").html()){
		       	addDetail_("",detailList[tableName]==0?0:detailList[tableName]-1,tableName);
			}
	    }
	}
	
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
		     openComponent_customerOA_custCode:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "客户编号不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrder__0__Address:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "楼盘不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrder__0__CustDescr:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "业主姓名不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrder__0__BusinessMan:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "业务员不能为空"  ,
		            },
		        }  
		     },
		 
		     fp__tWfCust_PrjReturnOrder__0__DesignMan:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "设计师不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrder__0__Status:{ 
		      validators: {  
		            notEmpty: {  
		           		 message: "施工状态不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrder__0__Reason:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "退单原因不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrder__0__ContractFee:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "合同造价不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrder__0__DesignFee:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "设计费不能为空"  ,
		            },
		        }  
		     },
		      fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "是否收设计费不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrder__0__ReturnAmount:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "退款额不能为空"  ,
		            },
		        }  
		     },
		      fp__tWfCust_PrjReturnOrder__0__RealDesignFee:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "实收设计费不能为空"  ,
		            },
		        }  
		     },
		     takeBackDateList: {  
		        validators: {  
		            notEmpty: {  
		           		 message: "退单收回资料不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrderDtl__0__ActName:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "账户名不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrderDtl__0__CardId:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "账号不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnOrderDtl__0__Bank:{  
			        validators: {  
			            notEmpty: {  
			           		 message: "开户行不能为空"  ,
			            },
			        }  
			     },
		     fp__tWfCust_PrjReturnOrderDtl__0__SubBranch:{  
		     	validators: {  
		            notEmpty: {  
		           		 message: "支行不能为空"  ,
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

function addDetail_(flag,inx,groupId){
	var html = $("#"+groupId).find("#group_temp").html();
	addDetail(flag,html,inx,"returnFn",groupId);
}

function returnFn(flag,i){
	if(flag != "add"){
		$("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__ActName").val(detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ActName"]);
		$("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__CardId").val(detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__CardId"]);
		$("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__Bank").val(detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__Bank"]);
		$("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__SubBranch").val(detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__SubBranch"]);
		$("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__ReturnAmount").val(detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ReturnAmount"]);
		$("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__PK").val(detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__PK"]);
		if(detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ConfirmAmount"] != 0&& (!detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ConfirmAmount"] || 
					detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ConfirmAmount"] == "")){
			$("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__ConfirmAmount").val(detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ReturnAmount"]);
		} else {
			$("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__ConfirmAmount").val(detailJson["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ConfirmAmount"]);
		}
	}
}
function clearDetail(){
	$("#fp__tWfCust_PrjReturnOrderDtl__0__ActName").val("");
	$("#fp__tWfCust_PrjReturnOrderDtl__0__CardId").val("");
	$("#fp__tWfCust_PrjReturnOrderDtl__0__Bank").val("");
	$("#fp__tWfCust_PrjReturnOrderDtl__0__SubBranch").val("");
}
//调用WfProcinst_apply 页面的getOperator方法
function getOperator_(flag){ 
	var elMap = {Status: $("#fp__tWfCust_PrjReturnOrder__0__Status").val(),
					Company: $("#fp__tWfCust_PrjReturnOrder__0__Company").val(),
					CustType: $("#fp__tWfCust_PrjReturnOrder__0__CustType").val()};
	getOperator(flag,elMap);
}
function updateTakeBackDateList(){
	$("#fp__tWfCust_PrjReturnOrder__0__TakeBackDateList").val($("#takeBackDateList").val());
	$("#dataForm").data("bootstrapValidator")
              .updateStatus("takeBackDateList", "NOT_VALIDATED",null)  
              .validateField("takeBackDateList")
}

function chgMainAmount(e){
	if(e && e.value < 0){
		art.dialog({
			content:"退款额不能小于0",
		});
		e.value = 0;
		return;
	}
	var length = $("#tWfCust_PrjReturnOrderDtl").children("div").length-1;
	var amount = 0;
	for(var i=0;i<20;i++){
		if($("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__ReturnAmount").val()){
			amount =myRound($("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__ReturnAmount").val(),4) + myRound(amount,4);
		}
	}
	$("#fp__tWfCust_PrjReturnOrder__0__ReturnAmount").val(amount);
	if(document.getElementById("fp__tWfCust_PrjReturnOrder__0__ReturnAmount")){
		moneyToCapital(document.getElementById("fp__tWfCust_PrjReturnOrder__0__ReturnAmount"),"capital");
	}
	$("#dataForm").data("bootstrapValidator")
               .updateStatus("fp__tWfCust_PrjReturnOrder__0__ReturnAmount", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnOrder__0__ReturnAmount");
}
function delDetalReturnFn(){
	chgMainAmount();
}

function setRealDesignFee(e){
	var val = e.value;
	if(val == "否"){
		$("#fp__tWfCust_PrjReturnOrder__0__RealDesignFee").val(0.0);
		$("#fp__tWfCust_PrjReturnOrder__0__RealDesignFee").attr("readonly",true);
		$("#dataForm").data("bootstrapValidator")
               .updateStatus("fp__tWfCust_PrjReturnOrder__0__RealDesignFee", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnOrder__0__RealDesignFee");
		badRequest_ = false;
		mistakeMsg_ = "";
	} else {
		$("#fp__tWfCust_PrjReturnOrder__0__RealDesignFee").removeAttr("readonly",true);
		if($("#fp__tWfCust_PrjReturnOrder__0__RealDesignFee").val() == 0){
			badRequest_ = true;
			mistakeMsg_ = "是否收设计费为'是'时,实收设计费不能为0";
		} else {
			badRequest_ = false;
			mistakeMsg_ = "";
		}
	}
}

function getMistakeMsg(){
	var result = {"badRequest_":badRequest_,"mistakeMsg_":mistakeMsg_};
	return result;
}

function chgRealDesignFee(e){
	var val = e.value;
	var isChargeDesignFee = $("#fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee").val();
	if((val == 0 || val == "") && isChargeDesignFee == "是"){
		badRequest_ = true;
		mistakeMsg_ = "是否收设计费为'是'时,实收设计费不能为0";
	} else {
		badRequest_ = false;
		mistakeMsg_ = "";
	}
}

function callSetReadonly(actId){
	setReadonly(actId);
	$("#takeBackDateList_NAME").removeAttr("disabled","true");
	$("#fp__tWfCust_PrjReturnOrder__0__ReturnAmount").attr("readonly","true");
}

function checkConfirmAmount(i){
	var returnAmount = myRound($("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__ReturnAmount").val());
	var confirmAmount = myRound($("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__ConfirmAmount").val());
	if(confirmAmount > returnAmount){
		art.dialog({
			content:"审核金额不能大于退款额",
		});
		$("#fp__tWfCust_PrjReturnOrderDtl__"+i+"__ConfirmAmount").val(returnAmount);
		return;
	}
}

function goCustPay() {
	
	Global.Dialog.showDialog("custPay",{
		title:"客户付款",
		url:"${ctx}/admin/custPay/goReturnPayCustPay?code="+$("#fp__tWfCust_PrjReturnOrder__0__CustCode").val(),
		postData:{m_umState:"V"},
		height:750,
		width:1300,
		returnFun: goto_query
	});
}
</script>
</body>
</html>

