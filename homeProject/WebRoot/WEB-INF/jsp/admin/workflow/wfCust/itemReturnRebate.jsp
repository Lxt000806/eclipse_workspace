<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>退项及返点扣回</title>
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
    		<input id="fp__tWfCust_ItemReturnRebate__0__CustCode" name="fp__tWfCust_ItemReturnRebate__0__CustCode" value="${datas.fp__tWfCust_ItemReturnRebate__0__CustCode }"  hidden="true"/>
    		<input id="fp__tWfCust_ItemReturnRebate__0__CustDescr" name="fp__tWfCust_ItemReturnRebate__0__CustDescr" value="${datas.fp__tWfCust_ItemReturnRebate__0__CustDescr}" hidden="true"/>
    		<input id="fp__tWfCust_ItemReturnRebate__0__ItemType" name="fp__tWfCust_ItemReturnRebate__0__ItemType" value="${datas.fp__tWfCust_ItemReturnRebate__0__ItemType}" hidden="true"/>
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
						<input type="text" id="fp__tWfCust_ItemReturnRebate__0__Address" name="fp__tWfCust_ItemReturnRebate__0__Address"  style="width:160px;" value="${datas.fp__tWfCust_ItemReturnRebate__0__Address}"/>
				    </li>
				</div>
				<div class="validate-group row" >	
					<li class="form-validate">
				        <label><span class="required">*</span>业务员</label>
						<input type="text" id="fp__tWfCust_ItemReturnRebate__0__BusinessMan" name="fp__tWfCust_ItemReturnRebate__0__BusinessMan"  style="width:160px;" value="${datas.fp__tWfCust_ItemReturnRebate__0__BusinessMan}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>设计师</label>
						<input type="text" id="fp__tWfCust_ItemReturnRebate__0__DesignMan" name="fp__tWfCust_ItemReturnRebate__0__DesignMan"  style="width:160px;" value="${datas.fp__tWfCust_ItemReturnRebate__0__DesignMan}"/>
				    </li>
	         		<li class="form-validate">
						<label><span class="required">*</span>材料类型</label>
						<select id="fp__tWfCust_ItemReturnRebate__0__ItemType1" name="fp__tWfCust_ItemReturnRebate__0__ItemType1" 
							onchange="getOperator_()"
						value="${datas.fp__tWfCust_ItemReturnRebate__0__ItemType1 }">
								<option value ="">请选择...</option>
								<option value ="软装" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType1 == '软装' ? 'selected' : ''}>软装</option>
								<option value ="集成" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType1 == '集成' ? 'selected' : ''}>集成</option>
								<option value ="主材" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType1 == '主材' ? 'selected' : ''}>主材</option>
						</select>
					</li>
					<li class="form-validate">
						<label>材料品类</label>
						<house:DictMulitSelect id="itemType" dictCode="" 
							sql=" select '瓷砖' code ,'' descr union select '地板' code ,'' descr union select '卫浴' code ,'' descr 
								union select '木门' code ,'' descr union select '吊顶' code ,'' descr union select '五金配件' code ,'' descr 
								union select '开关面板' code ,'' descr union select '服务性产品' code ,'' descr union select '橱柜' code ,'' descr 
								union select '衣柜' code ,'' descr union select '推拉门' code ,'' descr "  onCheck="updateItemType()"
						sqlValueKey="code" sqlLableKey="descr" selectedValue="${datas.fp__tWfCust_ItemReturnRebate__0__ItemType }"></house:DictMulitSelect>
					</li>
	         		<%-- <li class="form-validate">
						<label><span class="required">*</span>材料品类</label>
						<select id="fp__tWfCust_ItemReturnRebate__0__ItemType" name="fp__tWfCust_ItemReturnRebate__0__ItemType" 
							value="${datas.fp__tWfCust_ItemReturnRebate__0__ItemType }">
								<option value ="">请选择...</option>
								<option value ="瓷砖" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '瓷砖' ? 'selected' : ''}>瓷砖</option>
								<option value ="地板" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '地板' ? 'selected' : ''}>地板</option>
								<option value ="卫浴" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '卫浴' ? 'selected' : ''}>卫浴</option>
								<option value ="木门" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '木门' ? 'selected' : ''}>木门</option>
								<option value ="吊顶" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '吊顶' ? 'selected' : ''}>吊顶</option>
								<option value ="五金配件" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '五金配件' ? 'selected' : ''}>五金配件</option>
								<option value ="开关面板" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '开关面板' ? 'selected' : ''}>开关面板</option>
								<option value ="服务型产品" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '服务型产品' ? 'selected' : ''}>服务型产品</option>
								<option value ="橱柜" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '橱柜' ? 'selected' : ''}>橱柜</option>
								<option value =衣柜 ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '衣柜' ? 'selected' : ''}>衣柜</option>
								<option value ="推拉门" ${ datas.fp__tWfCust_ItemReturnRebate__0__ItemType == '推拉门' ? 'selected' : ''}推拉门</option>
						</select>
					</li> --%>
				</div>	
				<div class="validate-group row" >	
					<li class="form-validate">
				        <label><span class="required">*</span>退项金额</label>
						<input type="text" id="fp__tWfCust_ItemReturnRebate__0__DisChgAmount" name="fp__tWfCust_ItemReturnRebate__0__DisChgAmount"  
										style="width:160px;" value="${datas.fp__tWfCust_ItemReturnRebate__0__DisChgAmount}"/>
				    </li>
	         		<li class="form-validate">
						<label class="control-textarea"><span class="required">*</span>退项说明</label>
						<textarea id="fp__tWfCust_ItemReturnRebate__0__Reason" name="fp__tWfCust_ItemReturnRebate__0__Reason" rows="2">${datas.fp__tWfCust_ItemReturnRebate__0__Reason}</textarea>
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
	if ("" == $.trim("${wfProcess.remarks}")) {
		$("#tips").remove();
	}
	function getCustDetail(data){
		if(data){
			$("#fp__tWfCust_ItemReturnRebate__0__CustCode").val(data.code); 
			$("#fp__tWfCust_ItemReturnRebate__0__CustDescr").val(data.descr); 
			$("#fp__tWfCust_ItemReturnRebate__0__Address").val(data.address); 
			$("#fp__tWfCust_ItemReturnRebate__0__BusinessMan").val(data.businessmandescr); 
			$("#fp__tWfCust_ItemReturnRebate__0__DesignMan").val(data.designmandescr); 
			$("#dataForm").data("bootstrapValidator")
                .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
                .validateField("openComponent_customerOA_custCode")
                .updateStatus("fp__tWfCust_ItemReturnRebate__0__Address", "NOT_VALIDATED",null)  
                .validateField("fp__tWfCust_ItemReturnRebate__0__Address")
                .updateStatus("fp__tWfCust_ItemReturnRebate__0__BusinessMan", "NOT_VALIDATED",null)  
                .validateField("fp__tWfCust_ItemReturnRebate__0__BusinessMan")
                .updateStatus("fp__tWfCust_ItemReturnRebate__0__DesignMan", "NOT_VALIDATED",null)  
                .validateField("fp__tWfCust_ItemReturnRebate__0__DesignMan"); 
		}
	}
	$("#custCode").openComponent_customerOA({callBack:getCustDetail,showValue:"${datas.fp__tWfCust_ItemReturnRebate__0__CustCode}"
								,showLabel:"${datas.fp__tWfCust_ItemReturnRebate__0__CustDescr}"
								,condition:{authorityCtrl:"1"}
								});	

	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
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
		     fp__tWfCust_ItemReturnRebate__0__Address:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "楼盘不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_ItemReturnRebate__0__BusinessMan:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "业务员不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_ItemReturnRebate__0__DesignMan:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "设计师不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_ItemReturnRebate__0__ItemType1:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "材料类型不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_ItemReturnRebate__0__Reason:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "退项说明不能为空"  ,
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']"
	});

});

function getOperator_(flag){ 
	var elMap = {ItemType1:$("#fp__tWfCust_ItemReturnRebate__0__ItemType1").val()};
	getOperator(flag,elMap);
} 

function updateItemType(){
	$("#fp__tWfCust_ItemReturnRebate__0__ItemType").val($("#itemType").val());
}
</script>
</body>
</html>

