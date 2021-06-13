<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>干系人调整</title>
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
    		<input type="text" id="fp__tWfCust_StakeholderChg__0__custCode" name="fp__tWfCust_StakeholderChg__0__custCode" value="${datas.fp__tWfCust_StakeholderChg__0__CustCode}"hidden="true"/>
			<input type="text" id="fp__tWfCust_StakeholderChg__0__custDescr" name="fp__tWfCust_StakeholderChg__0__custDescr" hidden="true" value="${datas.fp__tWfCust_StakeholderChg__0__CustDescr}"/>
			<input type="text" id="fp__tWfCust_StakeholderChg__0__address" name="fp__tWfCust_StakeholderChg__0__address" hidden="true" value="${datas.fp__tWfCust_StakeholderChg__0__Address}"/>
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
	         		<li class="form-validate">
				        <label><span class="required">*</span>客户编号</label>
						<input type="text" id="custCode" name="custCode"  style="width:160px;" value="${datas.fp__tWfCust_StakeholderChg__0__CustCode}"/>
				    </li>
				    <li>
						<label><span class="required">*</span>状态</label>
						<select id="fp__tWfCust_StakeholderChg__0__Status" name ="fp__tWfCust_StakeholderChg__0__Status" style="width:160px" disabled="true"
							value=" ${ datas.fp__tWfCust_StakeholderChg__0__Status}">
 							<option value ="" >选择施工状态...</option>
 							<option value ="合同施工" ${ datas.fp__tWfCust_StakeholderChg__0__Status == '合同施工' ? 'selected' : ''}>合同施工</option>
 							<option value ="订单跟踪" ${ datas.fp__tWfCust_StakeholderChg__0__Status == '订单跟踪' ? 'selected' : ''}>订单跟踪</option>
 							<option value ="已到公司" ${ datas.fp__tWfCust_StakeholderChg__0__Status == '已到公司' ? 'selected' : ''}>已到公司</option>
 						</select>
 					</li>
					<li>
						<label><span class="required">*</span>类型</label>
						<select id="fp__tWfCust_StakeholderChg__0__Type" name ="fp__tWfCust_StakeholderChg__0__Type" style="width:160px" 
							onchange="getOperator_()"	value="${ datas.fp__tWfCust_StakeholderChg__0__Type}">
 							<option value ="撞单/跨部门" ${ datas.fp__tWfCust_StakeholderChg__0__Type == '撞单/跨部门'|| m_umState == 'A' ? 'selected' : ''}>撞单/跨部门</option>
 							<option value ="干系人不变，部门业绩归属调整" ${ datas.fp__tWfCust_StakeholderChg__0__Type == '干系人不变，部门业绩归属调整' ? 'selected' : ''}>干系人不变，部门业绩归属调整</option>
 							<option value ="跨事业部专盘干系人调整" ${ datas.fp__tWfCust_StakeholderChg__0__Type == '跨事业部专盘干系人调整' ? 'selected' : ''}>跨事业部专盘干系人调整</option>
 							<option value ="同事业部专盘干系人调整" ${ datas.fp__tWfCust_StakeholderChg__0__Type == '同事业部专盘干系人调整' ? 'selected' : ''}>同事业部专盘干系人调整</option>
 							<option value ="设计师调整" ${ datas.fp__tWfCust_StakeholderChg__0__Type == '设计师调整' ? 'selected' : ''}>设计师调整</option>
 							<option value ="业务员调整" ${ datas.fp__tWfCust_StakeholderChg__0__Type == '业务员调整' ? 'selected' : ''}>业务员调整</option>
 							<option value ="翻单员调整" ${ datas.fp__tWfCust_StakeholderChg__0__Type == '翻单员调整' ? 'selected' : ''}>翻单员调整</option>
 						</select>
 					</li>
				</div>	
				<div class="validate-group row" >	
					<li>
						<label class="control-textarea">备注</label>
						<textarea id="fp__tWfCust_StakeholderChg__temInx__reason" name="fp__tWfCust_StakeholderChg__0__reason" rows="2">${datas.fp__tWfCust_StakeholderChg__0__Reason}</textarea>
					</li>
				</div>
				<div id="tWfCust_StakeholderChgDtl" >
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_StakeholderChgDtl_div" style="border-top:1px solid #dfdfdf" hidden="true">	
							<div style="margin-top:15px">
								<input id="fp__tWfCust_StakeholderChgDtl__temInx__OldEmpCode" name="fp__tWfCust_StakeholderChgDtl__temInx__OldEmpCode" value="${datas.fp__tWfCust_StakeholderChgDtl__temInx__OldEmpCode }" hidden="true"/>
					    		<input id="fp__tWfCust_StakeholderChgDtl__temInx__EmpCode" name="fp__tWfCust_StakeholderChgDtl__temInx__EmpCode"  value="${datas.fp__tWfCust_StakeholderChgDtl__temInx__EmpCode }" hidden="true"/>
					    		<input id="fp__tWfCust_StakeholderChgDtl__temInx__OldEmpName" name="fp__tWfCust_StakeholderChgDtl__temInx__OldEmpName"  value="${datas.fp__tWfCust_StakeholderChgDtl__temInx__OldEmpName }" hidden="true"/>
					    		<input id="fp__tWfCust_StakeholderChgDtl__temInx__EmpName" name="fp__tWfCust_StakeholderChgDtl__temInx__EmpName"  value="${datas.fp__tWfCust_StakeholderChgDtl__temInx__EmpName }" hidden="true"/>
								<li class="form-validate">
									<label><span class="required">*</span>角色</label>
									<select id="fp__tWfCust_StakeholderChgDtl__temInx__Role" name="fp__tWfCust_StakeholderChgDtl__temInx__Role" onchange="selRole(temInx)" value="${datas.fp__tWfCust_StakeholderChgDtl__temInx__Role }">
			 							<option value ="">请选择...</option>
			 							<option value ="设计师" ${ datas.fp__tWfCust_StakeholderChgDtl__temInx__Role == '设计师' ? 'selected' : ''}>设计师</option>
			 							<option value ="业务员" ${ datas.fp__tWfCust_StakeholderChgDtl__temInx__Role == '业务员' ? 'selected' : ''}>业务员</option>
			 							<option value ="翻单员" ${ datas.fp__tWfCust_StakeholderChgDtl__temInx__Role == '翻单员' ? 'selected' : ''}>翻单员</option>
									</select>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>原干系人编号</label>
									<input type="text" id="oldEmpCode_temInx" name="oldEmpCode_temInx"  style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>新干系人编号</label>
									<input type="text" id="empCode_temInx" name="empCode_temInx"  style="width:160px;"/>
								</li>
								<button type="button" class="btn btn-sm btn-system" style="height:25px" onclick="addDetail_('add',-1,'tWfCust_StakeholderChgDtl');" id="btn_temInx">增加明细</button>
							</div>
						</div>
					</div>
				</div>
        	</ul>
        </form>
     </div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//detailJson 查看的时候传回来的明细数据
var detailJson=${detailJson};

//控件的验证
function validateRefresh(){
 	$("#dataForm").data("bootstrapValidator")
              .updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED",null)  
              .validateField("openComponent_customerOA_custCode")
              .updateStatus("openComponent_employee_oldEmpCode_0", "NOT_VALIDATED",null)  
              .validateField("openComponent_employee_oldEmpCode_0")
              .updateStatus("openComponent_employee_empCode_0", "NOT_VALIDATED",null)  
              .validateField("openComponent_employee_empCode_0"); 
}
$(function(){
	//detailList:{明细表1:明细表数据数量1,明细表2:明细表数据数量2}
	//用来加载明细数据
	var detailList = ${detailList}
	if(detailList != ""){
		for (var tableName in detailList) {
			if($("#"+tableName).find("#group_temp").html()){
		       	addDetail_("",detailList[tableName]==0?0:detailList[tableName]-1,tableName);
			}
	    }
	}
	
	$("#custCode").openComponent_customerOA({callBack:getCustDetail,showValue:"${datas.fp__tWfCust_StakeholderChg__0__CustCode}",showLabel:"${datas.fp__tWfCust_StakeholderChg__0__CustDescr}",});	
	
	// 如果有明细 则添加明细 ，如果没有 则判断页面是否有明细模板 有的话添加 第一个模板
	/* if("${detailNum}" >= 1){
		addDetail_("",myRound("${detailNum}")-1,"group_1");
	}else{
		for(var i=1;i<5;i++){
			if($("#group_"+i).html()){
				addDetail_("add",0,"group_"+i);
			}else{
				break;
			}
		}
	} */
	
	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			openComponent_employee_oldEmpCode_0:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "原干系人编号不能为空"  ,
		            },
		        }  
		     },
		     openComponent_employee_empCode_0:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "新干系人编号不能为空"  ,
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
		},
		submitButtons : "input[type='submit']"
	});
	
	//控件callBack方法	
	function getCustDetail(data){ 
		$("#fp__tWfCust_StakeholderChg__0__custDescr").val(data.descr);
		$("#fp__tWfCust_StakeholderChg__0__custCode").val(data.code);
		$("#fp__tWfCust_StakeholderChg__0__address").val(data.address);
		$("#fp__tWfCust_StakeholderChg__0__Status").val(data.statusdescr);
		$("#fp__tWfCust_StakeholderChgDtl__0__oldEmpCode").val("");
		$("#fp__tWfCust_StakeholderChgDtl__0__oldEmpName").val();
		$("#fp__tWfCust_StakeholderChgDtl__0__empCode").val("");
		$("#fp__tWfCust_StakeholderChgDtl__0__empName").val("");
		$("#openComponent_employee_empCode_0").val("");
		$("#openComponent_employee_oldEmpCode_0").val("");
		$("#fp__tWfCust_StakeholderChgDtl__0__Role").val("");
			
		delAllDetail("","tWfCust_StakeholderChgDtl");//当重新选择客户，清空所有明细
		getOperator_();//选择客户 重新加载流程审批人
	}
	
});

//新增明细方法
function addDetail_(flag,inx,groupId){
	var html = $("#"+groupId).find("#group_temp").html();
	addDetail(flag,html,inx,"returnFn",groupId);
}

//添加明细后的返回方法
//新增明细不能直接生成我们自己的openComponent控件，所以得写在这里面加载一次
//flag:判断是自动新增还是我们点击按钮新增 ；i:第几条明细；groupId：明细表名
function returnFn(flag,i,groupId){
	if(flag == "add"){
		$("#oldEmpCode_"+i).openComponent_employee({callBack:getOldEmpCodeDetail,
			condition:{role:($("#fp__tWfCust_StakeholderChgDtl__"+i+"__Role").val()==""?"***":$("#fp__tWfCust_StakeholderChgDtl__"+i+"__Role").val()),custCode:$("#custCode").val()}});	
		$("#empCode_"+i).openComponent_employee({callBack:getEmpCodeDetail});
		$("#oldEmpCode1_"+i).openComponent_employee({callBack:getOldEmpCodeDetail,
			condition:{role:($("#fp__tWfCust_StakeholderChgDtl__"+i+"__Role").val()==""?"***":$("#fp__tWfCust_StakeholderChgDtl__"+i+"__Role").val()),custCode:$("#custCode").val()}});	
		$("#empCode1_"+i).openComponent_employee({callBack:getEmpCodeDetail});
	}else{
	 	var optionText = detailJson["fp__tWfCust_StakeholderChgDtl__"+i+"__Role"];
		$("#"+groupId+"_"+i).find("select").find("option:contains("+optionText+")").attr("selected",true);
		$("#fp__tWfCust_StakeholderChgDtl__"+i+"__OldEmpCode").val(detailJson["fp__tWfCust_StakeholderChgDtl__"+i+"__OldEmpCode"]);
		$("#fp__tWfCust_StakeholderChgDtl__"+i+"__OldEmpName").val(detailJson["fp__tWfCust_StakeholderChgDtl__"+i+"__OldEmpName"]);
		$("#fp__tWfCust_StakeholderChgDtl__"+i+"__EmpCode").val(detailJson["fp__tWfCust_StakeholderChgDtl__"+i+"__EmpCode"]);
		$("#fp__tWfCust_StakeholderChgDtl__"+i+"__EmpName").val(detailJson["fp__tWfCust_StakeholderChgDtl__"+i+"__EmpName"]);
		$("#oldEmpCode_"+i).openComponent_employee({callBack:getOldEmpCodeDetail,
		condition:{role:($("#fp__tWfCust_StakeholderChgDtl__"+i+"__Role").val()==""?"***":$("#fp__tWfCust_StakeholderChgDtl__"+i+"__Role").val()),custCode:$("#custCode").val()},
			showValue:detailJson["fp__tWfCust_StakeholderChgDtl__"+i+"__OldEmpCode"],
			showLabel:detailJson["fp__tWfCust_StakeholderChgDtl__"+i+"__OldEmpName"]
		});	
		$("#empCode_"+i).openComponent_employee({callBack:getEmpCodeDetail,
			showValue:detailJson["fp__tWfCust_StakeholderChgDtl__"+i+"__EmpCode"],
			showLabel:detailJson["fp__tWfCust_StakeholderChgDtl__"+i+"__EmpName"]
		});
	}
}

function getOldEmpCodeDetail(data){
	var id = this.id.split("openComponent_employee_oldEmpCode_")[1];
	$("#fp__tWfCust_StakeholderChgDtl__"+id+"__OldEmpCode").val(data.number);
	$("#fp__tWfCust_StakeholderChgDtl__"+id+"__OldEmpName").val(data.namechi);
}

function getEmpCodeDetail(data){
	var id = this.id.split("openComponent_employee_empCode_")[1];
	$("#fp__tWfCust_StakeholderChgDtl__"+id+"__EmpCode").val(data.number);
	$("#fp__tWfCust_StakeholderChgDtl__"+id+"__EmpName").val(data.namechi);
}

//调用WfProcinst_apply.jsp 页面的getOperator方法
function getOperator_(flag){ 
	var elMap = {Status:$("#fp__tWfCust_StakeholderChg__0__Status").val(),Type:$("#fp__tWfCust_StakeholderChg__0__Type").val()};
	getOperator(flag,elMap);
} 

function selRole(inx){
	$.ajax({
		url:"${ctx}/admin/wfProcInst/getCustStakeholder",
		type:"post",	
		data:{roll:$("#fp__tWfCust_StakeholderChgDtl__"+inx+"__Role").val(),custCode:$("#custCode").val()},
		dataType:"json",
		cache:false,
		error:function(obj){
				$("#fp__tWfCust_StakeholderChgDtl__"+inx+"__oldEmpCode").val("");
				$("#fp__tWfCust_StakeholderChgDtl__"+inx+"__oldEmpName").val("");
			if(inx == 0){
				$("#oldEmpCode_0").openComponent_employee({callBack:getOldEmpCodeRole,
					showValue:obj.EmpCode,showLabel:obj.NameChi,
					condition:{role:($("#fp__tWfCust_StakeholderChgDtl__0__Role").val()==""?"***":$("#fp__tWfCust_StakeholderChgDtl__0__Role").val()),custCode:$("#custCode").val()}});	
				$("#openComponent_employee_oldEmpCode_0").val("");
			}else{
				$("#openComponent_employee_oldEmpCode_"+inx).val("");
				$("#oldEmpCode_"+inx).openComponent_employee({callBack:getOldEmpCodeRole,
					showValue:obj.EmpCode,showLabel:obj.NameChi,condition:{role:($("#fp__tWfCust_StakeholderChgDtl__"+inx+"__Role").val()==""?"***":$("#fp__tWfCust_StakeholderChgDtl__"+inx+"__Role").val()),custCode:$("#custCode").val()}});	
			}
			validateRefresh();
			showAjaxHtml({"hidden": false, "msg": "出错~"});
		},
		success:function(obj){
			if (obj){
				$("#fp__tWfCust_StakeholderChgDtl__"+inx+"__OldEmpCode").val(obj.EmpCode);
				$("#fp__tWfCust_StakeholderChgDtl__"+inx+"__OldEmpName").val(obj.NameChi);
				if(inx == 0){
					$("#oldEmpCode_0").openComponent_employee({callBack:getOldEmpCodeRole,
						showValue:obj.EmpCode,showLabel:obj.NameChi,condition:{role:($("#fp__tWfCust_StakeholderChgDtl__0__Role").val()==""?"**":$("#fp__tWfCust_StakeholderChgDtl__0__Role").val()),custCode:$("#custCode").val()}});	
				}else{
					$("#oldEmpCode_"+inx).openComponent_employee({callBack:getOldEmpCodeRole,
						showValue:obj.EmpCode,showLabel:obj.NameChi,condition:{role:($("#fp__tWfCust_StakeholderChgDtl__"+inx+"__Role").val()==""?"***":$("#fp__tWfCust_StakeholderChgDtl__"+inx+"__Role").val()),custCode:$("#custCode").val()}});	
				}
				validateRefresh();
			}
		}
	});
}

function getOldEmpCodeRole(data){
	$("#fp__tWfCust_StakeholderChgDtl__0__Role").val("");
	$("#fp__tWfCust_StakeholderChgDtl__0__oldEmpCode").val(data.number);
	$("#fp__tWfCust_StakeholderChgDtl__0__oldEmpName").val(data.namechi);
	validateRefresh();
}
	
</script>
</body>
</html>

