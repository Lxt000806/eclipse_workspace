<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ERP服务</title>
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
	   		<input id="activityId" name="activityId" value="${activityId }" hidden=""/>
	   		<input id="processInstId" name="processInstId" value="${piId }" hidden=""/>
    		<input id="fp__tWfCust_CustPayConfirm__0__EmpCode" name="fp__tWfCust_CustPayConfirm__0__EmpCode" value="${datas.fp__tWfCust_CustPayConfirm__0__EmpCode }"  hidden="true"/>
    		<input id="fp__tWfCust_CustPayConfirm__0__EmpName" name="fp__tWfCust_CustPayConfirm__0__EmpName" value="${datas.fp__tWfCust_CustPayConfirm__0__EmpName}" hidden="true"/>
    		<input id="fp__tWfCust_CustPayConfirm__0__CustCode" name="fp__tWfCust_CustPayConfirm__0__CustCode" value="${datas.fp__tWfCust_CustPayConfirm__0__CustCode}" hidden="true"/>
    		<input id="fp__tWfCust_CustPayConfirm__0__CustDescr" name="fp__tWfCust_CustPayConfirm__0__CustDescr" value="${datas.fp__tWfCust_CustPayConfirm__0__CustDescr}" hidden="true"/>
    		<input id="fp__tWfCust_CustPayConfirm__0__ReceiveActName" name="fp__tWfCust_CustPayConfirm__0__ReceiveActName" value="${datas.fp__tWfCust_CustPayConfirm__0__ReceiveActName}" hidden="true"/>
    		<input id="fp__tWfCust_CustPayConfirm__0__RcvActCode" name="fp__tWfCust_CustPayConfirm__0__RcvActCode" value="${datas.fp__tWfCust_CustPayConfirm__0__RcvActCode}" hidden="true"/>
    		<input id="checkPayInfo" name="checkPayInfo" value="0" hidden="true"/>
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>员工编号</label>
						<input type="text" id="employee" name="employee"  style="width:160px;" value="${datas.fp__tWfCust_CustPayConfirm__0__EmpCode}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>申请人手机号</label>
   						<input type="text" id="fp__tWfCust_CustPayConfirm__0__Phone" name="fp__tWfCust_CustPayConfirm__0__Phone" 
   								value="${datas.fp__tWfCust_CustPayConfirm__0__Phone}" style="width:160px;"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>客户编号</label>
						<input type="text" id="custCode" name="custCode"  style="width:160px;" value="${datas.fp__tWfCust_CustPayConfirm__0__CustCode}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>楼盘</label>
						<input type="text" id="fp__tWfCust_CustPayConfirm__0__Address" readonly="true"
							name="fp__tWfCust_CustPayConfirm__0__Address"  style="width:160px;" value="${datas.fp__tWfCust_CustPayConfirm__0__Address}"/>
				    </li>
				    <li class="form-validate">
						<label ><span class="required">*</span>到账日期</label>
						<input type="text" style="width:160px;" id="fp__tWfCust_CustPayConfirm__0__PayDate" 
								name="fp__tWfCust_CustPayConfirm__0__PayDate"
				   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',width:'160px'})" 
				   			 value="${datas.fp__tWfCust_CustPayConfirm__0__PayDate}" onchange="checkInfo('1')"/>
				    </li>
				    <li class="form-validate">
						<label ><span class="required">*</span>到账时间</label>
						<input type="text" style="width:160px;" id="fp__tWfCust_CustPayConfirm__0__PayTime" 
								name="fp__tWfCust_CustPayConfirm__0__PayTime"
				   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm',width:'160px'})" 
				   			 value="${datas.fp__tWfCust_CustPayConfirm__0__PayTime}" />
				    </li>
				    <li class="form-validate">
						<label><span class="required">*</span>收款账户</label>
						<house:dict id="rcvAct" dictCode="" sqlValueKey="code" sqlLableKey="descr"
						    sql=" select code code,descr descr from tRcvAct where AllowTrans = '1' and Expired = 'F' " onchange = "chgRcvAct()"
						    value="${datas.fp__tWfCust_CustPayConfirm__0__RcvActCode.trim()}" ></house:dict>
					</li>
				    <li class="form-validate">
				        <label><span class="required">*</span>付款户名</label>
						<input type="text" id="fp__tWfCust_CustPayConfirm__0__PayActName" name="fp__tWfCust_CustPayConfirm__0__PayActName"  style="width:160px;" value="${datas.fp__tWfCust_CustPayConfirm__0__PayActName}" onchange="checkInfo('1')"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>转账金额</label>
						<input type="text" id="fp__tWfCust_CustPayConfirm__0__PayAmount" name="fp__tWfCust_CustPayConfirm__0__PayAmount" 
							onkeyup="this.value =this.value.replace(/[^\-?\d.]/g,'')"	
							style="width:160px;" value="${datas.fp__tWfCust_CustPayConfirm__0__PayAmount}" onchange="checkInfo('1')"/>
				    </li>
				    <li class="form-validate">
						<label><span class="required">*</span>转账内容</label>
						<select id="fp__tWfCust_CustPayConfirm__0__PayDetail" name ="fp__tWfCust_CustPayConfirm__0__PayDetail" style="width:160px"
							value=" ${ datas.fp__tWfCust_CustPayConfirm__0__PayDetail}" onchange="detailChanged()">
							<option value ="工程款" ${ datas.fp__tWfCust_CustPayConfirm__0__PayDetail == '工程款' ? 'selected' : ''}>工程款</option>
 							<option value ="设计定金" ${ datas.fp__tWfCust_CustPayConfirm__0__PayDetail == '设计定金' ? 'selected' : ''}>设计定金</option>	
 						</select>
 					</li>
 					<li class="form-validate">
						<label><span class="required" id="roansCardReq">*</span>装修贷款卡</label>
						<select id="fp__tWfCust_CustPayConfirm__0__LoansCard" name ="fp__tWfCust_CustPayConfirm__0__LoansCard" style="width:160px"
							value=" ${ datas.fp__tWfCust_CustPayConfirm__0__LoansCard}">
							<option value ="" ${ datas.fp__tWfCust_CustPayConfirm__0__LoansCard == '' ? 'selected' : ''}>请选择...</option>
							<option value ="否" ${ datas.fp__tWfCust_CustPayConfirm__0__LoansCard == '否' ? 'selected' : ''}>否</option>
 							<option value ="是" ${ datas.fp__tWfCust_CustPayConfirm__0__LoansCard == '是' ? 'selected' : ''}>是</option>	
 						</select>
 					</li>
 					<li>
						<label class="control-textarea">备注</label>
						<textarea id="fp__tWfCust_CustPayConfirm__0__Remarks" name="fp__tWfCust_CustPayConfirm__0__Remarks" rows="2">${datas.fp__tWfCust_CustPayConfirm__0__Remarks}</textarea>
					</li>
 					<li style="margin-left:60px" id="info_li" hidden="true">
 						<span style="color:red;font-size: 20px;font-weight:bold">存在相同记录，注意核对是否重复查账。</span>
 						<button type="button" class="btn btn-sm btn-system" style="height:25px;width:55px" onclick="goViewProcInfo()" id="btn_temInx">查看</button>
 						
 					</li>
				</div>
				<div id="tips" style="border-top:1px solid #dfdfdf" >
					<div style="margin-left:60px;margin-top:8px;color: red">
						${wfProcess.remarks}
					</div>
				</div>
        	</ul>
        </form>
     </div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function validateRefreshCust(){
	$("#dataForm").data("bootstrapValidator")
    .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
    .validateField("openComponent_customerOA_custCode")
    .updateStatus("fp__tWfCust_CustPayConfirm__0__Address", "NOT_VALIDATED",null)  
    .validateField("fp__tWfCust_CustPayConfirm__0__Address"); 
}
$(function(){

	if ("" == $.trim("${wfProcess.remarks}")) {
		$("#tips").remove();
	}

	function getCustDetail(data){
		if(!data) return;
		
		$("#fp__tWfCust_CustPayConfirm__0__Address").val(data.address);
		$("#fp__tWfCust_CustPayConfirm__0__CustCode").val(data.code);
		$("#fp__tWfCust_CustPayConfirm__0__CustDescr").val(data.descr);
		validateRefreshCust();
		checkInfo('1');
	}

	if("A"=="${m_umState}"){
		$("#employee").openComponent_employee({showValue:"${employee.number }",showLabel:"${employee.nameChi}",readonly:true});
		$("#openComponent_employee_employee").blur();
		$("#fp__tWfCust_CustPayConfirm__0__EmpCode").val("${employee.number }");
		$("#fp__tWfCust_CustPayConfirm__0__EmpName").val("${employee.nameChi }");
		$("#fp__tWfCust_CustPayConfirm__0__Phone").val("${employee.phone }");
		$("#fp__tWfCust_CustPayConfirm__0__LoansCard").val("否");
		$("#custCode").openComponent_customerOA({callBack:getCustDetail,condition:{"authorityCtrl":"1"}});
		$("#openComponent_customerOA_custCode").attr("readonly",true);
		
	} else {
		$("#employee").openComponent_employee({showValue:"${datas.fp__tWfCust_CustPayConfirm__0__EmpCode}",
						showLabel:"${datas.fp__tWfCust_CustPayConfirm__0__EmpName}" ,readonly:true});	
		$("#custCode").openComponent_customerOA({showValue:"${datas.fp__tWfCust_CustPayConfirm__0__CustCode}",
						showLabel:"${datas.fp__tWfCust_CustPayConfirm__0__CustDescr}",callBack:getCustDetail });	
		$("#openComponent_customerOA_custCode").attr("readonly",true);
		checkInfo('0');
		setReadonly();
		if("${activityId }" == "applyman"){
			$("#rcvAct").removeAttr("disabled","true");
		}
	}
	
	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
		     openComponent_employee_employee:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "员工编号不能为空",
		            },
		        }  
		     },
		     openComponent_customerOA_custCode:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "客户编号不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_CustPayConfirm__0__Address:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "楼盘不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_CustPayConfirm__0__PayDate:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "到账日期不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_CustPayConfirm__0__PayTime:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "到账时间不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_CustPayConfirm__0__Phone:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "员工手机号不能为空",
		            },
		        }  
		     },
		     rcvAct:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "收款户名不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_CustPayConfirm__0__PayActName:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "转账户名不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_CustPayConfirm__0__PayAmount:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "转账金额不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_CustPayConfirm__0__PayDetail:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "转账内容不能为空",
		            },
		        }  
		     },
		     fp__tWfCust_CustPayConfirm__0__LoansCard:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "装修贷款卡不能为空",
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']"
	});
	
	if("${m_umState}" == "A"){
		getOperator_();
	} 

});

function chgRcvAct(){
	$("#fp__tWfCust_CustPayConfirm__0__RcvActCode").val($("#rcvAct").val());
	$("#fp__tWfCust_CustPayConfirm__0__ReceiveActName").val($("#rcvAct option:selected").text());
	checkInfo('1');
}

function detailChanged(){
	var payDetail = $("#fp__tWfCust_CustPayConfirm__0__PayDetail").val();
	if(payDetail == "工程款"){
		$("#fp__tWfCust_CustPayConfirm__0__LoansCard").val("否");
		$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustPayConfirm__0__LoansCard", {  
			validators: {  
				notEmpty: {  
					message: '装修贷款卡不能为空'  
				},
			}  
		});	
		$("#roansCardReq").show();
		$("#dataForm").data("bootstrapValidator")
		    .updateStatus("fp__tWfCust_CustPayConfirm__0__LoansCard", "NOT_VALIDATED",null)  
		    .validateField("fp__tWfCust_CustPayConfirm__0__LoansCard")
	} else {
		$("#dataForm").data("bootstrapValidator")
   			.updateStatus("fp__tWfCust_CustPayConfirm__0__LoansCard", "VALIDATED",null);
		$("#dataForm").bootstrapValidator("removeField","fp__tWfCust_CustPayConfirm__0__LoansCard");
		$("#dataForm").bootstrapValidator("addField", "fp__tWfCust_CustPayConfirm__0__LoansCard", {  
			validators: {  

			}  
		});	
		$("#roansCardReq").hide();
	}
}

function doApprovalTask_(){
	console.log($("#checkPayInfo").val()=="1");
	if($("#checkPayInfo").val()=="1"){
		art.dialog({
			content:"存在相同记录,注意核对是否重复查账,是否审批通过？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				doApprovalTask();
			},
			cancel: function () {
				return true;
			}
		});
	}else{
		doApprovalTask();
	}
}

function checkInfo(flag){// flag == '1' 判断审批中的
	var custCode = $.trim($("#fp__tWfCust_CustPayConfirm__0__CustCode").val());
	var payDate = $.trim($("#fp__tWfCust_CustPayConfirm__0__PayDate").val());
	var receiveActName = $.trim($("#fp__tWfCust_CustPayConfirm__0__ReceiveActName").val());
	var payAmount = $.trim($("#fp__tWfCust_CustPayConfirm__0__PayAmount").val());
	var payActName = $.trim($("#fp__tWfCust_CustPayConfirm__0__PayActName").val());
	var rcvActCode = $.trim($("#fp__tWfCust_CustPayConfirm__0__RcvActCode").val());
	
	if("C"!= "${m_umState}" && "A" != "${m_umState}"){
		return;
	}
	
	if(payDate != "" && receiveActName != "" && payAmount != "" && payActName != ""){
		$.ajax({
			url:"${ctx}/admin/custPay/getCheckPayInfo",
			type:"post",	
			data:{custCode: custCode, payDate: payDate, rcvAct: rcvActCode,
					 payAmount: payAmount, payActName: payActName,flag: flag},
			dataType:"json",
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "出错~"});
			},
			success:function(obj){
				if (!obj){
					console.log(obj);
					$("#info_li").removeAttr("hidden","true");
					$("#checkPayInfo").val("1");
				}else {
					$("#info_li").attr("hidden","true");
					$("#checkPayInfo").val("0");
				}
			}
		});
	}
}

function goViewProcInfo(){
	var custCode = $.trim($("#fp__tWfCust_CustPayConfirm__0__CustCode").val());
	var payDate = $.trim($("#fp__tWfCust_CustPayConfirm__0__PayDate").val());
	var receiveActName = $.trim($("#fp__tWfCust_CustPayConfirm__0__ReceiveActName").val());
	var payAmount = $.trim($("#fp__tWfCust_CustPayConfirm__0__PayAmount").val());
	var payActName = $.trim($("#fp__tWfCust_CustPayConfirm__0__PayActName").val());
	var flag="0";
	if("A"=="${m_umState}"){
		flag = "1";
	} 
	
	Global.Dialog.showDialog("goViewProcTrack",{ 
   		title:"流程审批情况",
		url:"${ctx}/admin/custPay/goViewProcTrack",
		postData:{custCode: custCode, payDate: payDate, receiveActName: receiveActName,
					 payAmount: payAmount, payActName: payActName, flag: flag},
       	height: 572,
       	width:1030,
       	returnFun:goto_query
	});
}

function getOperator_(flag){ 
	var elMap = {};
	getOperator(flag,elMap);
} 
</script>
</body>
</html>

