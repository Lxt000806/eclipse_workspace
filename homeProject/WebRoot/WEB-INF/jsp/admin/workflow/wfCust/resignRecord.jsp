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
	   		<input id="photoPK" name="photoPK" value="" hidden="true" />
	   		<input id="url" name="url" value="${url }" hidden="true" />
			<input id="fp__tWfCust_ResignRecord__0__EmpCode" name="fp__tWfCust_ResignRecord__0__EmpCode" value="${datas.fp__tWfCust_ResignRecord__0__EmpCode}"  hidden="true"/>
    		<input id="fp__tWfCust_ResignRecord__0__EmpName" name="fp__tWfCust_ResignRecord__0__EmpName" value="${datas.fp__tWfCust_ResignRecord__0__EmpName}" hidden="true"/>
    		<input id="fp__tWfCust_ResignRecord__0__EmpType" name="fp__tWfCust_ResignRecord__0__EmpType" value="${datas.fp__tWfCust_ResignRecord__0__EmpType}" hidden="true"/>
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >
					<div class="validate-group row">
						<li class="form-validate">
					        <label><span class="required">*</span>员工编号</label>
							<input type="text" id="employee" name="employee"  style="width:160px;" value="${datas.fp__tWfCust_ResignRecord__0__EmpCode}"/>
					    </li>
						<li class="form-validate">
					        <label><span class="required">*</span>部门</label>
							<input type="text" id="fp__tWfCust_ResignRecord__0__Department2" name="fp__tWfCust_ResignRecord__0__Department2"  
									style="width:160px;" value="${datas.fp__tWfCust_ResignRecord__0__Department2}" readonly="true"/>
					    </li>
					    <li class="form-validate">
					        <label><span class="required">*</span>岗位</label>
							<input type="text" id="fp__tWfCust_ResignRecord__0__Position" name="fp__tWfCust_ResignRecord__0__Position"  
									style="width:160px;" value="${datas.fp__tWfCust_ResignRecord__0__Position}" readonly="true"/>
					    </li>
						<li class="form-validate">
							<label><span class="required">*</span>入职时间</label>
							<input type="text" style="width:160px;" id="fp__tWfCust_ResignRecord__0__JoinDate" 
								name="fp__tWfCust_ResignRecord__0__JoinDate" disabled="true"
					   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
					   			 value="<fmt:formatDate value='${datas.fp__tWfCust_ResignRecord__0__JoinDate}' 
					   			 pattern='yyyy-MM-dd hh:mm:dd'/>" />
					    </li>
					</div>
					<div class="validate-group row">
					    <li class="form-validate">
							<label><span class="required">*</span>离职时间</label>
							<input type="text" style="width:160px;" id="fp__tWfCust_ResignRecord__0__LeaveDate" name="fp__tWfCust_ResignRecord__0__LeaveDate"
					   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
					   			 value="<fmt:formatDate value='${datas.fp__tWfCust_ResignRecord__0__LeaveDate}' pattern='yyyy-MM-dd hh:mm:dd'/>" />
					    </li>
					    <li class="form-validate">
					        <label><span class="required">*</span>司龄</label>
							<input type="text" id="fp__tWfCust_ResignRecord__0__CmpYears" name="fp__tWfCust_ResignRecord__0__CmpYears"  
									style="width:160;" value="${datas.fp__tWfCust_ResignRecord__0__CmpYears}" readonly="true"/>
					    </li>
					    <li class="form-validate">
					        <label>车牌号</label>
							<input type="text" id="fp__tWfCust_ResignRecord__0__CarNo" name="fp__tWfCust_ResignRecord__0__CarNo"  
									style="width:160px;" value="${datas.fp__tWfCust_ResignRecord__0__CarNo}"/>
					    </li>
					    <li class="form-validate">
					        <label>微信号</label>
							<input type="text" id="fp__tWfCust_ResignRecord__0__WeChatNo" name="fp__tWfCust_ResignRecord__0__WeChatNo"  
									style="width:160px;" value="${datas.fp__tWfCust_ResignRecord__0__WeChatNo}"/>
					    </li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label>类型</label>
							<select id="fp__tWfCust_ResignRecord__0__LeaveType" name="fp__tWfCust_ResignRecord__0__LeaveType" value="${datas.fp__tWfCust_ResignRecord__0__LeaveType}">
	 							<option value ="">请选择...</option>
	 							<option value ="试用期内" ${ datas.fp__tWfCust_ResignRecord__0__LeaveType == '试用期内' ? 'selected' : ''}>试用期内</option>
	 							<option value ="合同期内" ${ datas.fp__tWfCust_ResignRecord__0__LeaveType == '合同期内' ? 'selected' : ''}>合同期内</option>
	 							<option value ="合同到期" ${ datas.fp__tWfCust_ResignRecord__0__LeaveType == '合同到期' ? 'selected' : ''}>合同到期</option>
							</select>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>申请时间</label>
							<input type="text" style="width:160px;" id="fp__tWfCust_ResignRecord__0__ApplyDate" name="fp__tWfCust_ResignRecord__0__ApplyDate"
					   			 class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  disabled="true"
					   			 value="<fmt:formatDate value='${datas.fp__tWfCust_ResignRecord__0__ApplyDate}' pattern='yyyy-MM-dd hh:mm:dd'/>" />
					    </li>
					    <li class="form-validate">
							<label class="control-textarea" style="margin-top:25px"><span class="required">*</span>行政物品(电脑、电话卡 等)</label>
							<textarea id="fp__tWfCust_ResignRecord__0__CompanyItems" name="fp__tWfCust_ResignRecord__0__CompanyItems" 
									rows="2" >${datas.fp__tWfCust_ResignRecord__0__CompanyItems}</textarea>
						</li>
						
						
						<li class="form-validate" hidden="true">
					        <label>公司编号</label>
							<input type="text" id="fp__tWfCust_ResignRecord__0__Company" name="fp__tWfCust_ResignRecord__0__Company"  
									style="width:160px;" value="${datas.fp__tWfCust_ResignRecord__0__Company}"/>
					    </li>
						<li class="form-validate" hidden="true">
					        <label>公司名称</label>
							<input type="text" id="fp__tWfCust_ResignRecord__0__CompanyDescr" name="fp__tWfCust_ResignRecord__0__CompanyDescr"  
									style="width:160px;" value="${datas.fp__tWfCust_ResignRecord__0__CompanyDescr}"/>
					    </li>
					    
						
					</div>	
					<!-- 离职原因 -->
					<div style = " border-top:1px solid #dfdfdf;border-bottom:1px solid #dfdfdf;margin-top:10px" >
						<div class="validate-group row" style="margin-top:10px">
							<li class="form-validate">
								<label>离职原因</label>
								<select id="fp__tWfCust_ResignRecord__0__ReasonType" name="fp__tWfCust_ResignRecord__0__ReasonType" 
											onchange="changeReason()" value="${datas.fp__tWfCust_ResignRecord__0__ReasonType}">
		 							<option value ="">请选择...</option>
		 							<option value ="管理原因" ${ datas.fp__tWfCust_ResignRecord__0__ReasonType == '管理原因' ? 'selected' : ''}>管理原因</option>
		 							<option value ="个人原因" ${ datas.fp__tWfCust_ResignRecord__0__ReasonType == '个人原因' ? 'selected' : ''}>个人原因</option>
		 							<option value ="家庭原因" ${ datas.fp__tWfCust_ResignRecord__0__ReasonType == '家庭原因' ? 'selected' : ''}>家庭原因</option>
		 							<option value ="公司原因" ${ datas.fp__tWfCust_ResignRecord__0__ReasonType == '公司原因' ? 'selected' : ''}>公司原因</option>
								</select>
							</li>
							<li class="form-validate" id="manage_li">
								<label>管理原因</label>
								<house:DictMulitSelect id="manageReason" dictCode="" 
									sql=" select '工作缺少人指导' code ,'' descr ,1 seq
										  union select '没有业绩' code ,'' descr ,1 seq
										  union select '上下级关系不合' code ,'' descr ,1 seq
										  union select '工作压力大，频繁加班' code ,'' descr ,1 seq
										  union select '不适应本职' code ,'' descr ,1 seq
										  union select '其他' code ,'' descr ,2 seq
										  order by seq
										  " 
									 	onCheck="updateReasonList('manageReason')"
								sqlValueKey="code" sqlLableKey="descr" selectedValue="${datas.fp__tWfCust_ResignRecord__0__Reason}"></house:DictMulitSelect>
							</li> 
							<li class="form-validate" id="person_li">
								<label>个人原因</label>
								<house:DictMulitSelect id="personReason" dictCode="" 
									sql=" select '健康原因' code ,'' descr ,1 seq
										  union select '换新工作' code ,'' descr ,1 seq
										  union select '自离' code ,'' descr ,1 seq
										  union select '结婚/订婚/怀孕' code ,'' descr ,1 seq
										  union select '继续升学' code ,'' descr ,1 seq
										  union select '创业' code ,'' descr ,1 seq
										  union select '转项目经理' code ,'' descr ,1 seq
										  union select '其他' code ,'' descr ,2 seq
										  order by seq
										  " 
									 	onCheck="updateReasonList('personReason')"
								sqlValueKey="code" sqlLableKey="descr" selectedValue="${datas.fp__tWfCust_ResignRecord__0__Reason}"></house:DictMulitSelect>
							</li> 
							<li class="form-validate" id="family_li">
								<label>家庭原因</label>
								<house:DictMulitSelect id="familyReason" dictCode="" 
									sql=" select '家里有人生病' code ,'' descr ,1 seq
										  union select '回家带孩子' code ,'' descr ,1 seq
										  union select '家里有急事' code ,'' descr ,1 seq
										  union select '其他' code ,'' descr ,2 seq
										  order by seq
										  " 
									 	onCheck="updateReasonList('familyReason')"
								sqlValueKey="code" sqlLableKey="descr" selectedValue="${datas.fp__tWfCust_ResignRecord__0__Reason}"></house:DictMulitSelect>
							</li> 
							<li class="form-validate" id="company_li">
								<label>公司原因</label>
								<house:DictMulitSelect id="companyReason" dictCode="" 
									sql=" select '薪酬待遇低，福利不完善' code ,'' descr ,1 seq
										  union select '工作环境（噪音、办公地方）及设施' code ,'' descr ,1 seq
										  union select '培训、学习机会少' code ,'' descr ,1 seq
										  union select '不适应公司的文化' code ,'' descr ,1 seq
										  union select '劝退/辞退' code ,'' descr ,1 seq
										  union select '发展、晋升空间小' code ,'' descr ,1 seq
										  union select '其他' code ,'' descr ,2 seq
										  order by seq
										  " 
									 	onCheck="updateReasonList('companyReason')"
								sqlValueKey="code" sqlLableKey="descr" selectedValue="${datas.fp__tWfCust_ResignRecord__0__Reason}"></house:DictMulitSelect>
							</li> 
							<li class="form-validate" id="remarks_li">
								<label class="control-textarea">其他原因说明</label>
								<textarea id="fp__tWfCust_ResignRecord__0__Remarks" name="fp__tWfCust_ResignRecord__0__Remarks" rows="2">${datas.fp__tWfCust_ResignRecord__0__Remarks}</textarea>
							</li>
							<li class="form-validate" hidden="true">
								<textarea id="fp__tWfCust_ResignRecord__0__Reason" name="fp__tWfCust_ResignRecord__0__Reason" rows="2">${datas.fp__tWfCust_ResignRecord__0__Reason}</textarea>
								<label class="control-textarea">其他原因说明</label>
							</li>
						</div>
					</div>	
					<div class="validate-group row" style="margin-top:15px">
						<li class="form-validate">
							<label>是否愿意回公司</label>
							<house:DictMulitSelect id="comeBackReason" dictCode="" 
								sql=" select '取决于工资福利' code ,'' descr ,1 seq
									  union select '取决于公司文化' code ,'' descr ,1 seq
									  union select '取决于提供的岗位' code ,'' descr ,1 seq
									  union select '再也不愿意回来' code ,'' descr ,1 seq
									  union select '其他' code ,'' descr ,2 seq
									  order by seq
									  " 
								 	onCheck="updateComeBackReasonList()"
							sqlValueKey="code" sqlLableKey="descr" selectedValue="${datas.fp__tWfCust_ResignRecord__0__ComeBackReason}"></house:DictMulitSelect>
						</li> 
						<input hidden="true" type="text" id="fp__tWfCust_ResignRecord__0__ComeBackReason" name="fp__tWfCust_ResignRecord__0__ComeBackReason"  style="width:160px;"
							 value="${datas.fp__tWfCust_ResignRecord__0__ComeBackReason}"/>
						<li class="form-validate">
							<label class="control-textarea">说明</label>
							<textarea id="fp__tWfCust_ResignRecord__0__ComeBackRemarks" name="fp__tWfCust_ResignRecord__0__ComeBackRemarks"
							 rows="2">${datas.fp__tWfCust_ResignRecord__0__ComeBackRemarks}</textarea>
						</li>
					</div>
					<div class="validate-group row">
		         		<li class="form-validate">
							<label class="control-textarea">建议/意见</label>
							<textarea id="fp__tWfCust_ResignRecord__0__Suggestions" name="fp__tWfCust_ResignRecord__0__Suggestions" 
							rows="2">${datas.fp__tWfCust_ResignRecord__0__Reason}</textarea>
						</li>
					</div>
				</div>
				<div id="tips" style="border-top:1px solid #dfdfdf" >
					<div style="margin-left:60px;margin-top:8px">
						${wfProcess.remarks}
						<!-- <p style="color:red">1.该流程前端部门由部门经理发起。</p>
						<p style="color:red">2.正常单需提前7天申请。</p>
						<p style="color:red">3.活动策划需求需先进行活动策划案审批。</p> -->
					</div>
				</div>
        	</ul>
        </form>
     </div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function getFormatDate(time) { 
	var date = new Date(time); 
	var Str=date.getFullYear() + '-' + 
	((date.getMonth() + 1).toString().length==1?'0'+(date.getMonth()+1):date.getMonth()+1) + '-' + 
	(date.getDate().toString().length==1?'0'+date.getDate():date.getDate() ) + ' ' + 
	(date.getHours().toString().length==1?'0'+date.getHours():date.getHours() )+ ':' + 
	(date.getMinutes().toString().length==1?'0'+date.getMinutes():date.getMinutes())+ ':' + 
	(date.getSeconds().toString().length==1?'0'+date.getSeconds():date.getSeconds()); 
	return Str; 
} 
function setElMap(){
	elMaps = {"EmpType":$("#fp__tWfCust_ResignRecord__0__EmpType").val(),Company:$.trim($("#fp__tWfCust_ResignRecord__0__Company").val())};
}
function getCompanyYears(time){
	var date = new Date(); 
	var	dateYear = date.getFullYear();
	var	dateMonth = date.getMonth();
	var	empDate = new Date(time);
	var	empYear = empDate.getFullYear();
	var empMonth = empDate.getMonth();	
	var year = 0;   //2010-05    //2011-04
	var month = 0;
	if(dateMonth >= empMonth ){
		month = dateMonth - empMonth;
		year = dateYear - empYear;
	}else{
		month = dateMonth+12 - empMonth;
		year = dateYear - 1 - empYear;
	}
	return year+"年"+month+"月"
	
}

$(function(){
	function getEmpData(data){
		if(!data) return;
		if(data.cmpCode && data.cmpCode != null){
			$("#fp__tWfCust_ResignRecord__0__Company").val(data.cmpCode);
			$("#fp__tWfCust_ResignRecord__0__CompanyDescr").val(data.cmpDescr);
		}
		getOperator_();
	}
	
	if("${m_umState}"=="A"){
		$("#employee").openComponent_employee({showValue:"${employee.number }",showLabel:"${employee.nameChi}",callBack:getEmpData,readonly:true});
		$("#fp__tWfCust_ResignRecord__0__Department2").val("${employee.department2Descr }");
		$("#fp__tWfCust_ResignRecord__0__Position").val("${employee.positionDescr}");
		$("#fp__tWfCust_ResignRecord__0__JoinDate").val("${employee.joinDate }");
		$("#fp__tWfCust_ResignRecord__0__EmpCode").val("${employee.number }");
		$("#fp__tWfCust_ResignRecord__0__EmpName").val("${employee.nameChi }");
		$("#fp__tWfCust_ResignRecord__0__EmpType").val("${employee.type }");
		//$("#fp__tWfCust_ResignRecord__0__LeaveDate").val(getFormatDate(new Date()));
		$("#fp__tWfCust_ResignRecord__0__ApplyDate").val(getFormatDate(new Date()));
		$("#fp__tWfCust_ResignRecord__0__CmpYears").val(getCompanyYears("${employee.joinDate}"));
		$("#openComponent_employee_employee").blur();
		//
	}else{
		$("#employee").openComponent_employee({showValue:"${datas.fp__tWfCust_ResignRecord__0__EmpCode }",
		showLabel:"${datas.fp__tWfCust_ResignRecord__0__EmpName }"});
	}
	
	//附件页签显示
	document.getElementById("tab_apply_fjView_li").style.display="block";

	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			fp__tWfCust_ResignRecord__0__LeaveType:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "离职类型不能为空",
		            },
		        }  
		    },
		    fp__tWfCust_ResignRecord__0__LeaveDate:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "离职时间不能为空",
		            },
		        }  
		    },
		    fp__tWfCust_ResignRecord__0__CompanyItems:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "行政物品不能为空",
		            },
		        }  
		    },
		},
		submitButtons : "input[type='submit']"
	});
	changeReason();
	
});

function updateReasonList(id){
	$("#fp__tWfCust_ResignRecord__0__Reason").val($("#"+id).val());
}

function updateComeBackReasonList(){
	$("#fp__tWfCust_ResignRecord__0__ComeBackReason").val($("#comeBackReason").val());
}

function getOperator_(flag){ 
	var elMap = {"EmpType":$("#fp__tWfCust_ResignRecord__0__EmpType").val(),Company:$.trim($("#fp__tWfCust_ResignRecord__0__Company").val())};
	getOperator(flag,elMap);
} 

function changeReason(){
	var reasonType = $("#fp__tWfCust_ResignRecord__0__ReasonType").val();
	
	if(reasonType=="管理原因"){
		$("#person_li").hide();
		$("#family_li").hide();
		$("#company_li").hide();
		$("#manage_li").show();
		$("#remarks_li").show();
	}else if(reasonType=="个人原因"){
		$("#manage_li").hide();
		$("#family_li").hide();
		$("#company_li").hide();
		$("#person_li").show();
		$("#remarks_li").show();
		
	}else if(reasonType=="家庭原因"){
		$("#manage_li").hide();
		$("#person_li").hide();
		$("#company_li").hide();
		$("#family_li").show();
		$("#remarks_li").show();
	}else if(reasonType=="公司原因"){
		$("#manage_li").hide();
		$("#person_li").hide();
		$("#family_li").hide();
		$("#company_li").show();
		$("#remarks_li").show();
	}else{
		$("#manage_li").hide();
		$("#person_li").hide();
		$("#family_li").hide();
		$("#company_li").hide();
		$("#remarks_li").hide();
	}
	if("${m_umState}"=="A"){
		$("#personReason").val("");
		$("#manageReason").val("");
		$("#familyReason").val("");
		$("#companyReason").val("");
		$("#fp__tWfCust_ResignRecord__0__Remarks").val("");
		$("#fp__tWfCust_ResignRecord__0__Reason").val("");
		$.fn.zTree.getZTreeObj("tree_manageReason").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_personReason").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_familyReason").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_companyReason").checkAllNodes(false);
		$("#companyReason_NAME").val("");
		$("#manageReason_NAME").val("");
		$("#familyReason_NAME").val("");
		$("#personReason_NAME").val("");
	}
}
</script>
</body>
</html>

