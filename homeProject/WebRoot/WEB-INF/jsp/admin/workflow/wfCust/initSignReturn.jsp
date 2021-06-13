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
    		<input id="fp__tWfCust_InitSignReturn__0__CustCode" name="fp__tWfCust_InitSignReturn__0__CustCode" value="${datas.fp__tWfCust_InitSignReturn__0__CustCode }"  hidden="true"/>
    		<input id="fp__tWfCust_InitSignReturn__0__CustDescr" name="fp__tWfCust_InitSignReturn__0__CustDescr" value="${datas.fp__tWfCust_InitSignReturn__0__CustDescr}" hidden="true"/>
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
						<input type="text" id="fp__tWfCust_InitSignReturn__0__Address" name="fp__tWfCust_InitSignReturn__0__Address"  style="width:160px;" value="${datas.fp__tWfCust_InitSignReturn__0__Address}"/>
				    </li>
				    <li class="form-validate">
						<label><span class="required">*</span>类型</label>
						<select id="fp__tWfCust_InitSignReturn__0__Type" name ="fp__tWfCust_InitSignReturn__0__Type" style="width:160px" 
							value=" ${ datas.fp__tWfCust_InitSignReturn__0__Type}" onchange="getOperator_()">
 							<option value ="" >选择类型...</option>
 							<option value ="草签" ${ datas.fp__tWfCust_InitSignReturn__0__Type == '草签' ? 'selected' : ''}>草签</option>
 							<option value ="重签" ${ datas.fp__tWfCust_InitSignReturn__0__Type == '重签' ? 'selected' : ''}>重签</option>
 						</select>
 					</li>
				</div>
				<div class="validate-group row" >	
					<li>
						<label class="control-textarea">备注</label>
						<textarea id="fp__tWfCust_InitSignReturn__0__Remarks" name="fp__tWfCust_InitSignReturn__0__Remarks" rows="2">${datas.fp__tWfCust_InitSignReturn__0__Remarks}</textarea>
					</li>
					<li style="margin-left:60px" id="info_li">
 						<span style="color:red;font-size: 18px;font-weight:bold" id="notify_span"></span>
 					</li>
				</div>
        	</ul>
        </form>
     </div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function getResignNotify(custCode){
	$.ajax({
		url:"${ctx}/admin/customerSghtxx/getResignNotify?custCode="+custCode,
		type: "post",
		data: {},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	console.log(obj);
	    	if($.trim(obj.msg)!="" && $.trim(obj.msg)!="notify"){
				$("#notify_span").text(obj.msg);
	    	}
	    }
	});
}
$(function(){
	function getCustDetail(data){
		if(data){
			if("0" == data.isinitsign){
				$("#fp__tWfCust_InitSignReturn__0__Type").val("重签");
			}
			if("1" == data.isinitsign){
				$("#fp__tWfCust_InitSignReturn__0__Type").val("草签");
			}
			$("#fp__tWfCust_InitSignReturn__0__Type").attr("disabled","true");
			getOperator_();
			$.ajax({
				url:"${ctx}/admin/customerSghtxx/resignNotify?custCode="+data.code+"&status="+data.status+"&endCode="+data.endcode,
				type: "post",
				data: {},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if($.trim(obj.msg)=="" || $.trim(obj.msg)=="notify"){
						$("#fp__tWfCust_InitSignReturn__0__CustCode").val(data.code); 
						$("#fp__tWfCust_InitSignReturn__0__CustDescr").val(data.descr); 
						$("#fp__tWfCust_InitSignReturn__0__Address").val(data.address); 
						$("#fp__tWfCust_InitSignReturn__0__BusinessMan").val(data.businessmandescr); 
						$("#fp__tWfCust_InitSignReturn__0__DesignMan").val(data.designmandescr); 
						$("#dataForm").data("bootstrapValidator")
			                .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
			                .validateField("openComponent_customerOA_custCode")
			                .updateStatus("fp__tWfCust_InitSignReturn__0__Address", "NOT_VALIDATED",null)  
			                .validateField("fp__tWfCust_InitSignReturn__0__Address"); 
			    	}else{
			    		$("#openComponent_customerOA_custCode").val("");
			    		art.dialog({
			    			content:obj.msg,
			    		});
			    		return ;
			    	}
			    }
			});
		}
	}
	$("#custCode").openComponent_customerOA({callBack:getCustDetail,showValue:"${datas.fp__tWfCust_InitSignReturn__0__CustCode}"
								,showLabel:"${datas.fp__tWfCust_InitSignReturn__0__CustDescr}",
								condition:{status:"4",authorityCtrl:"1"}});	
	
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
		     fp__tWfCust_InitSignReturn__0__Address:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "楼盘不能为空"  ,
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']"
	});
	
	if("${m_umState}" == "A" && $.trim($("#fp__tWfCust_InitSignReturn__0__Type").val()) != ""){
		getOperator_();
	} else {
		$("#fp__tWfCust_InitSignReturn__0__Type").attr("disabled","true");
		if("" != $.trim($("#fp__tWfCust_InitSignReturn__0__Type").val())){
			getResignNotify($.trim($("#fp__tWfCust_InitSignReturn__0__CustCode").val()));
		}
	}

});

function getOperator_(flag){ 
	var elMap = {Type:$("#fp__tWfCust_InitSignReturn__0__Type").val()};
	getOperator(flag,elMap);
} 

function callSetReadonly(){
	$("#fp__tWfCust_InitSignReturn__0__Type").attr("disabled","true");
}
</script>
</body>
</html>

