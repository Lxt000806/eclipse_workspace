<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>设计协议楼盘地址调整</title>
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
	   		<input id="fp__tWfCust_DesignAddrAdjust__0__CustCode" name="fp__tWfCust_DesignAddrAdjust__0__CustCode" value="${datas.fp__tWfCust_DesignAddrAdjust__0__CustCode}"  hidden="true"/>
    		<input id="fp__tWfCust_DesignAddrAdjust__0__CustDescr" name="fp__tWfCust_DesignAddrAdjust__0__CustDescr" value="${datas.fp__tWfCust_DesignAddrAdjust__0__CustDescr}"  hidden="true"/>
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >
					<li class="form-validate">
				        <label><span class="required">*</span>协议原楼盘</label>
						<input type="text" id="fp__tWfCust_DesignAddrAdjust__0__DesignAddress" name="fp__tWfCust_DesignAddrAdjust__0__DesignAddress"  style="width:160px;" value="${datas.fp__tWfCust_DesignAddrAdjust__0__DesignAddress}"/>
				    </li>
	         		<li class="form-validate">
				        <label><span class="required">*</span>变更后楼盘</label>
						<input type="text" id="custCode" name="custCode"  style="width:160px;" value="${datas.fp__tWfCust_DesignAddrAdjust__0__CustCode}"/>
				    </li>
				    <li class="form-validate">
						<input type="text" id="fp__tWfCust_DesignAddrAdjust__0__Address" name="fp__tWfCust_DesignAddrAdjust__0__Address" readonly style="width:160px;" value="${datas.fp__tWfCust_DesignAddrAdjust__0__Address}"/>
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
<script type="text/javascript">
$(function(){
	if ("" == $.trim("${wfProcess.remarks}")) {
		$("#tips").remove();
	}
	function getCustDetail(data){
		if(data){
			$("#fp__tWfCust_DesignAddrAdjust__0__CustCode").val(data.code); 
			$("#fp__tWfCust_DesignAddrAdjust__0__CustDescr").val(data.descr); 
			$("#fp__tWfCust_DesignAddrAdjust__0__Address").val(data.address); 
			$("#dataForm").data("bootstrapValidator")
                .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
                .validateField("openComponent_customerOA_custCode")
                .updateStatus("fp__tWfCust_DesignAddrAdjust__0__DesignAddress", "NOT_VALIDATED",null)  
                .validateField("fp__tWfCust_DesignAddrAdjust__0__DesignAddress"); 
		}
	}
	$("#custCode").openComponent_customerOA({callBack:getCustDetail,showValue:"${datas.fp__tWfCust_DesignAddrAdjust__0__CustCode}"
								,showLabel:"${datas.fp__tWfCust_DesignAddrAdjust__0__CustDescr}",
								condition:{authorityCtrl:"1"}});	
	$("#openComponent_customerOA_custCode").attr("readonly",true);
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
		           		 message: "变更后楼盘不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_DesignAddrAdjust__0__DesignAddress:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "协议原楼盘不能为空"  ,
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

function getOperator_(flag){ 
	var elMap = {};
	getOperator(flag,elMap);
} 
</script>
</body>
</html>

