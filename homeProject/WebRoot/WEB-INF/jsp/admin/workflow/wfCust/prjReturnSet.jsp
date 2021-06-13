<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>工程退定申请</title>
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
    		<input id="fp__tWfCust_PrjReturnSet__0__CustCode" name="fp__tWfCust_PrjReturnSet__0__CustCode" value="${datas.fp__tWfCust_PrjReturnSet__0__CustCode }"  hidden="true"/>
        	<input id="fp__tWfCust_PrjReturnSet__0__Company" name="fp__tWfCust_PrjReturnSet__0__Company" value="${datas.fp__tWfCust_PrjReturnSet__0__Company }" hidden="true"/>
        	<input type="hidden" name="fp__tWfCust_PrjReturnSet__0__CustDescr" id="fp__tWfCust_PrjReturnSet__0__CustDescr"  value="${datas.fp__tWfCust_PrjReturnSet__0__CustDescr }" />
        	<input type="hidden" name="fp__tWfCust_PrjReturnSet__0__CustType" id="fp__tWfCust_PrjReturnSet__0__CustType"  value="${datas.fp__tWfCust_PrjReturnSet__0__CustType }" />
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>客户编号</label>
						<input type="text" id="custCode" name="custCode"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnSet__0__CustCode}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>工程名称</label>
						<input type="text" id="fp__tWfCust_PrjReturnSet__0__Address" name="fp__tWfCust_PrjReturnSet__0__Address"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnSet__0__Address}"/>
				    </li>
				     <li class="form-validate">
				        <label><span class="required">*</span>业主电话</label>
						<input type="text" id="fp__tWfCust_PrjReturnSet__0__Phone" name="fp__tWfCust_PrjReturnSet__0__Phone"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnSet__0__Phone}" maxlength="11"/>
				    </li>
				</div>
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>业务员</label>
						<input type="text" id="fp__tWfCust_PrjReturnSet__0__BusinessMan" name="fp__tWfCust_PrjReturnSet__0__BusinessMan"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnSet__0__BusinessMan}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>设计师</label>
						<input type="text" id="fp__tWfCust_PrjReturnSet__0__DesignMan" name="fp__tWfCust_PrjReturnSet__0__DesignMan"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnSet__0__DesignMan}"/>
				    </li>
				     <li class="form-validate">
				        <label><span class="required">*</span>下定时间</label>
				   		<input type="text" style="width:160px;" id="fp__tWfCust_PrjReturnSet__0__SetDate" name="fp__tWfCust_PrjReturnSet__0__SetDate"
				   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${datas.fp__tWfCust_PrjReturnSet__0__SetDate}' pattern='yyyy-MM-dd hh:mm:dd'/>" />
				    </li> 
				</div>
				<div class="validate-group row" >	
	         		<li class="form-validate">
						<label class="control-textarea"><span class="required">*</span>退单原因</label>
						<textarea id="fp__tWfCust_PrjReturnSet__0__Reason" name="fp__tWfCust_PrjReturnSet__0__Reason" rows="2">${datas.fp__tWfCust_PrjReturnSet__0__Reason}</textarea>
					</li>
					<li class="form-validate">
				        <label><span class="required">*</span>分公司</label>
						<input type="text" id="fp__tWfCust_PrjReturnSet__0__CompanyDescr" name="fp__tWfCust_PrjReturnSet__0__CompanyDescr"  readonly="true"
						style="width:160px;" value="${datas.fp__tWfCust_PrjReturnSet__0__CompanyDescr}"/>
				    </li>
				</div>
				<div class="validate-group row" >
	         		<li class="form-validate">
						<label>合计退款</label>
						<input type="text" id="fp__tWfCust_PrjReturnSet__0__ReturnAmount" name="fp__tWfCust_PrjReturnSet__0__ReturnAmount"  style="width:160px;"
						 placeholder="自动计算"
						 readonly="true" value="${datas.fp__tWfCust_PrjReturnSet__0__ReturnAmount}"/> 
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>退单收回资料</label>
						<house:DictMulitSelect id="takeBackDateList" dictCode="" 
							sql=" select '设计协议' code ,'' descr 
								  union select '收款收据' code ,'' descr " 
							 	onCheck="updateTakeBackDateList()"
						sqlValueKey="code" sqlLableKey="descr" selectedValue="${datas.fp__tWfCust_PrjReturnSet__0__TakeBackDateList }"></house:DictMulitSelect>
					</li>
					<input hidden="true" type="text" id="fp__tWfCust_PrjReturnSet__0__TakeBackDateList" name="fp__tWfCust_PrjReturnSet__0__TakeBackDateList"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnSet__0__TakeBackDateList}"/>
					<li class="form-validate">
				        <label><span class="required">*</span>凭证号</label>
						<input type="text" id="fp__tWfCust_PrjReturnSet__0__DocumentNo" name="fp__tWfCust_PrjReturnSet__0__DocumentNo"  style="width:160px;" value="${datas.fp__tWfCust_PrjReturnSet__0__DocumentNo}"/>
				    </li>
				</div>	
				<div class="validate-group row" >	
	         		<li class="form-validate">
						<label class="control-textarea">备注</label>
						<textarea id="fp__tWfCust_PrjReturnSet__0__Remarks" name="fp__tWfCust_PrjReturnSet__0__Remarks" rows="2">${datas.fp__tWfCust_PrjReturnSet__0__Remarks}</textarea>
					</li>
				</div>
				<div id="tWfCust_PrjReturnSetDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_PrjReturnSetDtl_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px">
								<input type="hidden" id="fp__tWfCust_PrjReturnSetDtl__temInx__PK" name="fp__tWfCust_PrjReturnSetDtl__temInx__PK" style="width:160px;"
									 value="${datas.fp__tWfCust_PrjReturnSetDtl__temInx__PK}" />
				         		<li class="form-validate">
							        <label><span class="required">*</span>账户名</label>
									<input type="text" id="fp__tWfCust_PrjReturnSetDtl__temInx__ActName" name="fp__tWfCust_PrjReturnSetDtl__temInx__ActName"  style="width:160px;"
									 value="${datas.fp__tWfCust_PrjReturnSetDtl__temInx__ActName}"/>
								</li>
				         		<li class="form-validate">
									<label><span class="required">*</span>账号</label>
									<input type="text" id="fp__tWfCust_PrjReturnSetDtl__temInx__CardId" name="fp__tWfCust_PrjReturnSetDtl__temInx__CardId"  style="width:160px;"
									 value="${datas.fp__tWfCust_PrjReturnSetDtl__temInx__CardId}"/>
								</li>
				         		<li class="form-validate">
							        <label><span class="required">*</span>开户行</label>
									<input type="text" id="fp__tWfCust_PrjReturnSetDtl__temInx__Bank" name="fp__tWfCust_PrjReturnSetDtl__temInx__Bank"  style="width:160px;"
									 value="${datas.fp__tWfCust_PrjReturnSetDtl__temInx__Bank}"/>
								</li>
								<li class="form-validate">
							        <label><span class="required">*</span>支行</label>
									<input type="text" id="fp__tWfCust_PrjReturnSetDtl__temInx__SubBranch" name="fp__tWfCust_PrjReturnSetDtl__temInx__SubBranch"  style="width:160px;"
									 value="${datas.fp__tWfCust_PrjReturnSetDtl__temInx__SubBranch}"/>
								</li>
								<li class="form-validate">
							        <label><span class="required">*</span>退款额</label>
									<input type="text" id="fp__tWfCust_PrjReturnSetDtl__temInx__ReturnAmount" name="fp__tWfCust_PrjReturnSetDtl__temInx__ReturnAmount"  style="width:160px;"
									 onkeyup="chgMainAmount(this)" value="${datas.fp__tWfCust_PrjReturnSetDtl__temInx__ReturnAmount}"/>
								</li>
								<li class="form-validate">
							        <label><span class="required">*</span>审核金额</label>
									<input type="text" id="fp__tWfCust_PrjReturnSetDtl__temInx__ConfirmAmount" name="fp__tWfCust_PrjReturnSetDtl__temInx__ConfirmAmount"  style="width:160px;"
									 		value="${datas.fp__tWfCust_PrjReturnSetDtl__temInx__ConfirmAmount}" onchange="checkConfirmAmount(temInx)"/>
								</li>
								<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_PrjReturnSetDtl')" id="btn_temInx">增加明细</button>
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
	callSetReadonly();
	detailVerify = [
		{
			tableId:"tWfCust_PrjReturnSetDtl",
			ActName:"账户名不能为空",
			SubBranch:"支行不能为空",
			CardId:"卡号不能为空",
			ReturnAmount:"退款额不能为空",
			activityId:"startevent",
        }, {
    		tableId:"tWfCust_PrjReturnSetDtl",
			activityId:"usertask16",
    		ConfirmAmount:"审核金额不能为空",
    	}
	];
	function getCustDetail(data){
		if(data){
			$("#fp__tWfCust_PrjReturnSet__0__CustCode").val(data.code); 
			$("#fp__tWfCust_PrjReturnSet__0__CustDescr").val(data.descr); 
			$("#fp__tWfCust_PrjReturnSet__0__Address").val(data.address); 
			$("#fp__tWfCust_PrjReturnSet__0__BusinessMan").val(data.businessmandescr); 
			$("#fp__tWfCust_PrjReturnSet__0__DesignMan").val(data.designmandescr);
			$("#fp__tWfCust_PrjReturnSet__0__Company").val(data.company); 
			$("#fp__tWfCust_PrjReturnSet__0__CompanyDescr").val(data.companydescr); 
			$("#fp__tWfCust_PrjReturnSet__0__SetDate").val(data.setdate);  
			$("#fp__tWfCust_PrjReturnSet__0__CustType").val(data.custtypedescr);  
			getOperator_();
		}	 
		delAllDetail("tWfCust_PrjReturnSetDtl");
		clearDetail();
		validateRefresh();
	}
	
	$("#custCode").openComponent_customerOA({
		callBack:getCustDetail,
		showValue:"${datas.fp__tWfCust_PrjReturnSet__0__CustCode}",
		showLabel:"${datas.fp__tWfCust_PrjReturnSet__0__CustDescr}",
		condition:{
			authorityCtrl:"1",
			status: "5"	//限制只有客户状态：归档，结束代码：退定金，的楼盘可以申请 modify by zb on 20200319
			//endCode: "6"  // 20200417 modify by xzy
		}
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
		     fp__tWfCust_PrjReturnSet__0__Address:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "工程名称不能为空"  ,
		            },
		        }  
		     },
		   
		     fp__tWfCust_PrjReturnSet__0__BusinessMan:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "业务员不能为空"  ,
		            },
		        }  
		     },
		 
		     fp__tWfCust_PrjReturnSet__0__DesignMan:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "设计师不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnSet__0__SetDate:{ 
		      validators: {  
		            notEmpty: {  
		           		 message: "下定时间不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnSet__0__Reason:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "退单原因不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnSet__0__ReturnAmount:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "退定金额不能为空"  ,
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
		    fp__tWfCust_PrjReturnSet__0__Phone:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "电话不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnSetDtl__0__ActName:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "账户名不能为空"  ,
		            },
		        }  
		     },   
		     fp__tWfCust_PrjReturnSetDtl__0__CardId:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "账号不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnSetDtl__0__Bank:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "开户行不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_PrjReturnSetDtl__0__SubBranch:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "支行不能为空"  ,
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	if("${m_umState}" == "A"){
		getOperator_();
	}
});

function validateRefresh(){
	 	$("#dataForm").data("bootstrapValidator")
               //.updateStatus("takeBackDateList", "NOT_VALIDATED",null)  
               //.validateField("takeBackDateList")
               .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
               .validateField("openComponent_customerOA_custCode")
               .updateStatus("fp__tWfCust_PrjReturnSet__0__Address", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnSet__0__Address")
               .updateStatus("fp__tWfCust_PrjReturnSet__0__BusinessMan", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnSet__0__BusinessMan")
               .updateStatus("fp__tWfCust_PrjReturnSet__0__DesignMan", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnSet__0__DesignMan")
               .updateStatus("fp__tWfCust_PrjReturnSet__0__SetDate", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnSet__0__SetDate")
               .updateStatus("fp__tWfCust_PrjReturnSet__0__Reason", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnSet__0__Reason")
               .updateStatus("fp__tWfCust_PrjReturnSet__0__ReturnAmount", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnSet__0__ReturnAmount")
               .updateStatus("fp__tWfCust_PrjReturnSet__0__Phone", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnSet__0__Phone")
               .updateStatus("fp__tWfCust_PrjReturnSetDtl__0__ActName", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnSetDtl__0__ActName")
               .updateStatus("fp__tWfCust_PrjReturnSetDtl__0__CardId", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnSetDtl__0__CardId")
               .updateStatus("fp__tWfCust_PrjReturnSetDtl__0__Bank", "NOT_VALIDATED",null)  
               .validateField("fp__tWfCust_PrjReturnSetDtl__0__Bank") 
               ; 
	}
	
function getOperator_(flag){ 
	var elMap = {Company:$("#fp__tWfCust_PrjReturnSet__0__Company").val(),
				CustType:$("#fp__tWfCust_PrjReturnSet__0__CustType").val(), 
				};
	getOperator(flag,elMap);
} 

function addDetail_(flag,inx,groupId){
	var html = $("#"+groupId).find("#group_temp").html();
	addDetail(flag,html,inx,"returnFn",groupId);
}

function returnFn(flag,i){
	if(flag != "add"){
		$("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ActName").val(detailJson["fp__tWfCust_PrjReturnSetDtl__"+i+"__ActName"]);
		$("#fp__tWfCust_PrjReturnSetDtl__"+i+"__CardId").val(detailJson["fp__tWfCust_PrjReturnSetDtl__"+i+"__CardId"]);
		$("#fp__tWfCust_PrjReturnSetDtl__"+i+"__Bank").val(detailJson["fp__tWfCust_PrjReturnSetDtl__"+i+"__Bank"]);
		$("#fp__tWfCust_PrjReturnSetDtl__"+i+"__SubBranch").val(detailJson["fp__tWfCust_PrjReturnSetDtl__"+i+"__SubBranch"]);	
		$("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount").val(detailJson["fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount"]);	
		$("#fp__tWfCust_PrjReturnSetDtl__"+i+"__PK").val(detailJson["fp__tWfCust_PrjReturnSetDtl__"+i+"__PK"]);	
		if(!detailJson["fp__tWfCust_PrjReturnSetDtl__"+i+"__ConfirmAmount"] || 
					detailJson["fp__tWfCust_PrjReturnSetDtl__"+i+"__ConfirmAmount"] == ""){
			$("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ConfirmAmount").val(detailJson["fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount"]);
		} else {
			$("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ConfirmAmount").val(detailJson["fp__tWfCust_PrjReturnSetDtl__"+i+"__ConfirmAmount"]);
		}
		callSetReadonly();
	}
}
function clearDetail(){
	$("#fp__tWfCust_PrjReturnSetDtl__0__ActName").val("");
	$("#fp__tWfCust_PrjReturnSetDtl__0__CardId").val("");
	$("#fp__tWfCust_PrjReturnSetDtl__0__Bank").val("");
	$("#fp__tWfCust_PrjReturnSetDtl__0__SubBranch").val("");

}

function updateTakeBackDateList(){
	$("#fp__tWfCust_PrjReturnSet__0__TakeBackDateList").val($("#takeBackDateList").val());
	$("#dataForm").data("bootstrapValidator")
              .updateStatus("takeBackDateList", "NOT_VALIDATED",null)  
              .validateField("takeBackDateList")
}

function chgMainAmount(e){
	/* if(e.value < 0){
		art.dialog({
			content:"退款额不能小于0",
		});
		e.value = 0;
		return;
	} */
	//value.replace(/[^\-?\d.]/g,'')
	var length = $("#tWfCust_PrjReturnSetDtl").children("div").length-1;
	var amount = 0;
	var returnAmount =0.0;
	for(var i=0;i<20;i++){
		if($("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount").val()){
			returnAmount = $("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount").val().replace(/[^\-?\d.]/g,'');
			$("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount").val(returnAmount);
			if($("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount").val()){
				amount =myRound(returnAmount,2) + myRound(amount,4);
			} 
		}
	}
	$("#fp__tWfCust_PrjReturnSet__0__ReturnAmount").val(myRound(amount,4));
	moneyToCapital(document.getElementById("fp__tWfCust_PrjReturnSet__0__ReturnAmount"),"capital");
}
function checkConfirmAmount(i){
	console.log(i);
	var returnAmount = myRound($("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount").val());
	var confirmAmount = myRound($("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ConfirmAmount").val());
	if(confirmAmount > returnAmount){
		art.dialog({
			content:"审核金额不能大于退款额",
		});
		$("#fp__tWfCust_PrjReturnSetDtl__"+i+"__ConfirmAmount").val(returnAmount);
		return;
	}
}
function delDetalReturnFn(){
	chgMainAmount();
}
function callSetReadonly(actId){
	setReadonly(actId);
	$("#takeBackDateList_NAME").removeAttr("disabled","true");
}

function goCustPay() {
	
	Global.Dialog.showDialog("custPay",{
		title:"客户付款",
		url:"${ctx}/admin/custPay/goReturnPayCustPay?code="+$("#fp__tWfCust_PrjReturnSet__0__CustCode").val(),
		postData:{m_umState:"V"},
		height:750,
		width:1300,
		returnFun: goto_query
	});
}
</script>
</body>
</html>

