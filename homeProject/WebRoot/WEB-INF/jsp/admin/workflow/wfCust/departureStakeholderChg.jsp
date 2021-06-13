<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>离职调整干系人</title>
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
    		<input id="fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder" name="fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder" value="${datas.fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder }"  hidden="true"/>
        	<input id="fp__tWfCust_DepartureStakeholderChg__0__Stakeholder" name="fp__tWfCust_DepartureStakeholderChg__0__Stakeholder" value="${datas.fp__tWfCust_DepartureStakeholderChg__0__Stakeholder }" hidden="true"/>
        	<input id="fp__tWfCust_DepartureStakeholderChg__0__OldStakeholderDescr" name="fp__tWfCust_DepartureStakeholderChg__0__OldStakeholderDescr" value="${datas.fp__tWfCust_DepartureStakeholderChg__0__OldStakeholderDescr }"  hidden="true"/>
        	<input id="fp__tWfCust_DepartureStakeholderChg__0__StakeholderDescr" name="fp__tWfCust_DepartureStakeholderChg__0__StakeholderDescr" value="${datas.fp__tWfCust_DepartureStakeholderChg__0__StakeholderDescr }" hidden="true"/> 
         
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
						<label><span class="required">*</span>状态</label>
						<select id="fp__tWfCust_DepartureStakeholderChg__0__Status" name ="fp__tWfCust_DepartureStakeholderChg__0__Status" style="width:160px" 
							value=" ${ datas.fp__tWfCust_DepartureStakeholderChg__0__Status}" onchange="getOperator_();changeStatus()">
 							<option value ="" >选择施工状态...</option>
 							<option value ="订单跟踪" ${ datas.fp__tWfCust_DepartureStakeholderChg__0__Status == '订单跟踪' ? 'selected' : ''}>订单跟踪</option>
 							<option value ="合同施工" ${ datas.fp__tWfCust_DepartureStakeholderChg__0__Status == '合同施工' ? 'selected' : ''}>合同施工</option>
 						</select>
 					</li>
				    <li class="form-validate" >
						<label><span class="required">*</span>原干系人编号</label>
						<input type="text" id="oldStakeholder" name="oldStakeholder" style="width:160px;"/>
					</li>
					<li class="form-validate" >
						<label><span class="required">*</span>新干系人编号</label>
						<input type="text" id="stakeholder" name="stakeholder" style="width:160px;"/>
					</li>
				</div>
				<div id="tWfCust_DepartureStakeholderChgDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_DepartureStakeholderChgDtl_div" style="border-top:1px solid #dfdfdf" hidden="true" disabled"=true">
							<div style="margin-top:15px">
					    		<input id="fp__tWfCust_DepartureStakeholderChgDtl__temInx__CustDescr" 
					    			name="fp__tWfCust_DepartureStakeholderChgDtl__temInx__CustDescr"  
					    			value="${datas.fp__tWfCust_DepartureStakeholderChgDtl__temInx__OldDescr}" hidden="true"/>
					    		<input id="fp__tWfCust_DepartureStakeholderChgDtl__temInx__CustCode" 
					    			name="fp__tWfCust_DepartureStakeholderChgDtl__temInx__CustCode"  
					    			value="${datas.fp__tWfCust_DepartureStakeholderChgDtl__temInx__CustCode}" hidden="true"/>
				         		<li class="form-validate">
							        <label><span class="required">*</span>客户编号</label>
									<input type="text" id="CustCode_temInx" 
										name="CustCode_temInx"  
										style="width:160px;"
										value="${datas.fp__tWfCust_DepartureStakeholderChgDtl__temInx__CustCode}"/>
								</li>
								<li class="form-validate">
							        <label>楼盘</label>
									<input type="text" id="fp__tWfCust_DepartureStakeholderChgDtl__temInx__Address" 
										name="fp__tWfCust_DepartureStakeholderChgDtl__temInx__Address"  
										style="width:372px;"
										value="${datas.fp__tWfCust_DepartureStakeholderChgDtl__temInx__Address}" readonly/>
								</li>
								<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_DepartureStakeholderChgDtl')" id="btn_temInx">增加明细</button>
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
function validateRefresh_oldStakeholder(){
 	$("#dataForm").data("bootstrapValidator")
                .updateStatus("openComponent_employee_oldStakeholder", "NOT_VALIDATED",null)  
                .validateField("openComponent_employee_oldStakeholder")
}
function validateRefresh_stakeholder(){
 	$("#dataForm").data("bootstrapValidator")
                .updateStatus("openComponent_employee_stakeholder", "NOT_VALIDATED",null)  
                .validateField("openComponent_employee_stakeholder")
}
function validateRefresh_custCode(){
 	$("#dataForm").data("bootstrapValidator")
                .updateStatus("openComponent_customerOA_CustCode_0", "NOT_VALIDATED",null)  
                .validateField("openComponent_customerOA_CustCode_0");
}    
$(function(){
	function getOldStakeholderDetail(data){
		if(data){
			$("#fp__tWfCust_DepartureStakeholderChg__0__OldStakeholderDescr").val(data.namechi); 
			$("#fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder").val(data.number);
			//修改原干系人，需要重新传参
			$("#group_temp").nextAll("div").each(function(i,obj){
				clearDetail(i);
				refreshCustomer(i);
				changeStatus(i);
			});
			getOperator_();
		}	 
	}
	function getStakeholderDetail(data){
		if(data){
			$("#fp__tWfCust_DepartureStakeholderChg__0__StakeholderDescr").val(data.namechi); 
			$("#fp__tWfCust_DepartureStakeholderChg__0__Stakeholder").val(data.number); 
			getOperator_();
		}	 
	}
	$("#oldStakeholder").openComponent_employee({callBack:getOldStakeholderDetail,showValue:"${datas.fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder}",showLabel:"${datas.fp__tWfCust_DepartureStakeholderChg__0__OldStakeholderDescr}",callback:validateRefresh_oldStakeholder});	
	$("#stakeholder").openComponent_employee({callBack:getStakeholderDetail,showValue:"${datas.fp__tWfCust_DepartureStakeholderChg__0__Stakeholder}",showLabel:"${datas.fp__tWfCust_DepartureStakeholderChg__0__StakeholderDescr}",callback:validateRefresh_stakeholder});	
	
	// 如果有明细 则添加明细 ，如果没有 则判断页面是否有明细模板 有的话添加 第一个模板
	var detailList = ${detailList}
	if(detailList != ""){
		for (var tableName in detailList) {
			if($("#"+tableName).find("#group_temp").html()){
		       	addDetail_("",detailList[tableName]==0?0:detailList[tableName]-1,tableName);
			}
	    }
	}

	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
		     openComponent_employee_oldStakeholder:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "原干系人编号不能为空"  ,
		            },
		        }  
		     },
		     openComponent_employee_stakeholder:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "新干系人编号不能为空"  ,
		            },
		        }  
		     },
		   
		     fp__tWfCust_DepartureStakeholderChg__0__Status:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "状态不能为空"  ,
		            },
		        }  
		     },
		     openComponent_customerOA_CustCode_0:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "客户编号不能为空"  ,
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
function getOperator_(flag){ 
	var elMap = {Status:$("#fp__tWfCust_DepartureStakeholderChg__0__Status").val()};
	getOperator(flag,elMap);
} 

function addDetail_(flag,inx,groupId){
	var html = $("#"+groupId).find("#group_temp").html();
	addDetail(flag,html,inx,"returnFn",groupId);
}

//添加明细后的返回方法
//新增明细不能直接生成我们自己的openComponent控件，所以得卸载这里面加载一次
//flag:判断是自动新增还是我们点击按钮新增 ；i:第几条明细；groupId：明细表名
function returnFn(flag,i){
	if(flag != "add"){
		$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustCode").
			val(detailJson["fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustCode"]);
		$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustDescr").
			val(detailJson["fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustDescr"]);
		$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__Address").
			val(detailJson["fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__Address"]);
		$("#CustCode_"+i).openComponent_customerOA({
			showValue:detailJson["fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustCode"],
			showLabel:detailJson["fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustDescr"],
			condition:{
				designMan:$("#fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder").val(),
				//status:$("#fp__tWfCust_DepartureStakeholderChg__0__Status").val()=="订单跟踪"?"3":"4"
			},
			callBack: function(data){
				setCustDetail(data,i);
			}
		});
	} else {
		$("#CustCode_"+i).openComponent_customerOA({
			condition:{
				designMan:$("#fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder").val(),
				//status:$("#fp__tWfCust_DepartureStakeholderChg__0__Status").val()=="订单跟踪"?"3":"4"
			},
			callBack: function(data){
				setCustDetail(data,i);
			}
		});
	}
	changeStatus(i);
}
//刷新客户组件传递的参数
function refreshCustomer(i){
	$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustCode").
	val(detailJson["fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustCode"]);
	$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustDescr").
	val(detailJson["fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustDescr"]);
	$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__Address").
	val(detailJson["fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__Address"]);
	$("#CustCode_"+i).openComponent_customerOA({
		condition:{
			designMan:$("#fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder").val(),
			//status:$("#fp__tWfCust_DepartureStakeholderChg__0__Status").val()=="订单跟踪"?"3":"4"
		},
		callBack: function(data){
			setCustDetail(data,i);
		}
	});
}
//修改干洗人或状态触发，影响客户组件前置条件弹框提示
function changeStatus(i){
	if(i!=undefined){
		$("#CustCode_"+i).next().unbind("click",checkFun);
		if($("#fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder").val()=="" || $("#fp__tWfCust_DepartureStakeholderChg__0__Status").val()==""){
			$("#openComponent_customerOA_CustCode_"+i).next().attr("data-disabled",true);
			$("#openComponent_customerOA_CustCode_"+i).next().click(checkFun);
		}else{
			$("#openComponent_customerOA_CustCode_"+i).next().attr("data-disabled",false);
		}
	}else{
		$("#group_temp").nextAll("div").each(function(j,obj){
			clearDetail(j);
			refreshCustomer(j);
			$("#CustCode_"+j).next().unbind("click",checkFun);
			if($("#fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder").val()=="" || $("#fp__tWfCust_DepartureStakeholderChg__0__Status").val()==""){
				$("#openComponent_customerOA_CustCode_"+j).next().attr("data-disabled",true);
				$("#openComponent_customerOA_CustCode_"+j).next().click(checkFun);
			}else{
				$("#openComponent_customerOA_CustCode_"+j).next().attr("data-disabled",false);
			}
		});
	}
}
function setCustDetail(data,i) {
	for(var j=0;j<i;j++){
		if(data.code==$("#fp__tWfCust_DepartureStakeholderChgDtl__"+j+"__CustCode").val()){
			art.dialog({
				content: "客户编号重复！",
			});
			$("#openComponent_customerOA_CustCode_"+i).val("");
			$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustCode").val("");
			return false;
		}
    } 
	$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__Address").val(data.address);
	$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustCode").val(data.code);
	$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustDescr").val(data.descr);
	validateRefresh_custCode();
}
function clearDetail(i){
	$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__Address").val("");
	$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustCode").val("");
	$("#fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustDescr").val("");
	$("#openComponent_customerOA_CustCode_"+i).val("");
}
function checkFun(){
	var oldStakeholder= $("#fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder").val();
	var status= $("#fp__tWfCust_DepartureStakeholderChg__0__Status").val();
	var contents="请先选择";
	if(status=="" && oldStakeholder!=""){
		contents+="状态！";
	}else if(status!="" && oldStakeholder==""){
		contents+="原干系人！";
	}else if(status=="" && oldStakeholder==""){
		contents+="状态和原干系人！";
	}
	art.dialog({
		content: contents
	});
}
</script>
</body>
</html>

