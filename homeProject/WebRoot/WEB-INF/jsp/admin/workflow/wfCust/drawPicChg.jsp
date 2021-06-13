<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>绘图效果图调整</title>
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
    		<input type="text" id="fp__tWfCust_DrawPicChg__0__custCode" name="fp__tWfCust_DrawPicChg__0__custCode" value="${datas.fp__tWfCust_DrawPicChg__0__CustCode}"hidden="true"/>
			<input type="text" id="fp__tWfCust_DrawPicChg__0__custDescr" name="fp__tWfCust_DrawPicChg__0__custDescr" hidden="true" value="${datas.fp__tWfCust_DrawPicChg__0__CustDescr}"/>
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>客户编号</label>
						<input type="text" id="custCode" name="custCode"  style="width:160px;" value="${datas.fp__tWfCust_DrawPicChg__0__CustCode}"/>
				    </li>
				    <li class="form-validate">
				        <label><span class="required">*</span>工地名称</label>
						<input type="text" id="fp__tWfCust_DrawPicChg__0__address" name="fp__tWfCust_DrawPicChg__0__address"  style="width:160px;" value="${datas.fp__tWfCust_DrawPicChg__0__Address}"/>
				    </li>   
				    <li class="form-validate">
						<label><span class="required">*</span>状态</label>
						<select id="fp__tWfCust_DrawPicChg__0__Status" name ="fp__tWfCust_DrawPicChg__0__Status" style="width:160px" disabled="true"
							value=" ${ datas.fp__tWfCust_DrawPicChg__0__Status}">
 							<option value ="" >选择施工状态...</option>
 							<option value ="订单跟踪" ${ datas.fp__tWfCust_DrawPicChg__0__Status == '订单跟踪' ? 'selected' : ''}>订单跟踪</option>
 							<option value ="合同施工" ${ datas.fp__tWfCust_DrawPicChg__0__Status == '合同施工' ? 'selected' : ''}>合同施工</option>
 						</select>
 					</li>
				</div>	
				<div class="validate-group row" >
					<li class="form-validate">
						<label><span class="required">*</span>月份</label>
						<select id="fp__tWfCust_DrawPicChg__0__MonthType" name ="fp__tWfCust_DrawPicChg__0__MonthType" style="width:160px" 
							onchange="getOperator_()"	value="${ datas.fp__tWfCust_DrawPicChg__0__Type}">
 							<option value ="当月" ${ datas.fp__tWfCust_DrawPicChg__0__MonthType == '当月' ? 'selected' : ''}>当月</option>
 							<option value ="跨月" ${ datas.fp__tWfCust_DrawPicChg__0__MonthType == '跨月' ? 'selected' : ''}>跨月</option>
 						</select>
 					</li>	
					<li class="form-validate">
						<label><span class="required">*</span>调整类型</label>
						<select id="fp__tWfCust_DrawPicChg__0__Type" name ="fp__tWfCust_DrawPicChg__0__Type" style="width:160px" 
							value="${ datas.fp__tWfCust_DrawPicChg__0__Type}">
 							<option value ="制图费调整" ${ datas.fp__tWfCust_DrawPicChg__0__Type == '制图费调整' ? 'selected' : ''}>制图费调整</option>
 							<option value ="效果图费调整" ${ datas.fp__tWfCust_DrawPicChg__0__Type == '效果图费调整' ? 'selected' : ''}>效果图费调整</option>
 							<option value ="绘图员调整" ${ datas.fp__tWfCust_DrawPicChg__0__Type == '绘图员调整' ? 'selected' : ''}>绘图员调整</option>
 							<option value ="效果图员调整" ${ datas.fp__tWfCust_DrawPicChg__0__Type == '效果图员调整' ? 'selected' : ''}>效果图员调整</option>
 						</select>
 					</li>
				</div>	
				<div class="validate-group row" >
					<li class="form-validate"><label><span class="required">*</span>调整前</label> <input type="text"
						id="fp__tWfCust_DrawPicChg__0__BeforeAdjust" 
						name="fp__tWfCust_DrawPicChg__0__BeforeAdjust" style="width:160px;"
						value="${datas.fp__tWfCust_DrawPicChg__0__BeforeAdjust}" />
					</li>

					<li class="form-validate"><label><span class="required">*</span>调整后</label> <input type="text"
						id="fp__tWfCust_DrawPicChg__0__AfterAdjust" 
						name="fp__tWfCust_DrawPicChg__0__AfterAdjust" style="width:160px;"
						value="${datas.fp__tWfCust_DrawPicChg__0__AfterAdjust}" />
					</li>
				</div>
				<div class="validate-group row" >	
					<li class="form-validate">
						<label class="control-textarea"><span class="required">*</span>调整原因</label>
						<textarea id="fp__tWfCust_DrawPicChg__0__Reason" name="fp__tWfCust_DrawPicChg__0__Reason" rows="2">${datas.fp__tWfCust_DrawPicChg__0__Reason}</textarea>
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

//控件的验证
function validateRefresh(){
 	$("#dataForm").data("bootstrapValidator")
             .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
             .validateField("openComponent_customerOA_custCode") ; 
}
$(function(){
	
	if("${m_umState}" != "A"){
		var chgType = $.trim("${datas.fp__tWfCust_DrawPicChg__0__Type}");
		if(chgType == "绘图调整" || chgType == "效果图调整"){
			$("#fp__tWfCust_DrawPicChg__0__Type").append("<option value='"+chgType+"' selected>"+chgType+"</option>");
		}
	}
	
	if ("" == $.trim("${wfProcess.remarks}")) {
		$("#tips").remove();
	}
	$("#custCode").openComponent_customerOA({callBack:getCustDetail,showValue:"${datas.fp__tWfCust_DrawPicChg__0__CustCode}",showLabel:"${datas.fp__tWfCust_DrawPicChg__0__CustDescr}",
		condition:{'status':'3,4',authorityCtrl:"1"}
	});	
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
		     fp__tWfCust_DrawPicChg__0__Status:{ 
		      validators: {  
		            notEmpty: {  
		           		 message: "状态不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_DrawPicChg__0__MonthType:{ 
		      validators: {  
		            notEmpty: {  
		           		 message: "月份不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_DrawPicChg__0__Type:{ 
		      validators: {  
		            notEmpty: {  
		           		 message: "调整类型不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_DrawPicChg__0__Reason:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "调整原因不能为空"  ,
		            },
		        }  
		     },
		     fp__tWfCust_DrawPicChg__0__BeforeAdjust:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "调整前不能为空" ,
		            },
		            
		        },
		         
		     },
		     fp__tWfCust_DrawPicChg__0__AfterAdjust:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "调整后不能为空"  ,
		            },
		          
		        },   
		     },    
		     
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
	//控件callBack方法	
	function getCustDetail(data){ 
		$("#fp__tWfCust_DrawPicChg__0__custDescr").val(data.descr);
		$("#fp__tWfCust_DrawPicChg__0__custCode").val(data.code);
		$("#fp__tWfCust_DrawPicChg__0__address").val(data.address);
		$("#fp__tWfCust_DrawPicChg__0__Status").val(data.statusdescr);
		
		if($.trim(data.statusdescr) == "合同施工"){
			if($("#fp__tWfCust_DrawPicChg__0__Type").find("option[value='绘图员调整']").length>0){
				$("#fp__tWfCust_DrawPicChg__0__Type").find("option[value='绘图员调整']").remove();
			}
			if($("#fp__tWfCust_DrawPicChg__0__Type").find("option[value='效果图员调整']").length>0){
				$("#fp__tWfCust_DrawPicChg__0__Type").find("option[value='效果图员调整']").remove();
			}
		} else {
			if($("#fp__tWfCust_DrawPicChg__0__Type").find("option[value='绘图员调整']").length==0){
				$("#fp__tWfCust_DrawPicChg__0__Type").append("<option value= " + "绘图员调整" + ">" + "绘图员调整" + "</option>");
			}
			if($("#fp__tWfCust_DrawPicChg__0__Type").find("option[value='效果图员调整']").length==0){
				$("#fp__tWfCust_DrawPicChg__0__Type").append("<option value= " + "效果图员调整" + ">" + "效果图员调整" + "</option>");
			}
		}
		
		
		getOperator_();//选择客户 重新加载流程审批人
	}
});

//调用WfProcinst_apply.jsp 页面的getOperator方法
function getOperator_(flag){ 
	var elMap = {Status:$("#fp__tWfCust_DrawPicChg__0__Status").val(),MonthType:$("#fp__tWfCust_DrawPicChg__0__MonthType").val()};
	getOperator(flag,elMap);
} 

</script>
</body>
</html>

