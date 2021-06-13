<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>工地问题确认</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {    
				promPropCode:{  
					validators: {  
						notEmpty: {  
							message: "问题性质不能为空"  
						}
					}  
				},
				promDeptCode:{  
					validators: {  
						notEmpty: {  
							message: "责任部门不能为空"  
						}
					}  
				},
				promTypeCode:{  
					validators: {  
						notEmpty: {  
							message: "责任分类不能为空"  
						}
					}  
				},
				remarks:{  
					validators: {  
						notEmpty: {  
							message: "描述不能为空"  
						}
					}  
				},
			},
			submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
	
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/prjProblem/promDeptandType","promDeptCode","promTypeCode");
		Global.LinkSelect.setSelect({firstSelect:"promDeptCode",
								firstValue:"${prjProblem.promDeptCode }",
								secondSelect:"promTypeCode",
								secondValue:"${prjProblem.promTypeCode }",});
	
		$("#custCode").openComponent_customer({showValue:"${prjProblem.custCode}",showLabel:"${prjProblem.custDescr}",readonly:true});	
		$("#appCZY").openComponent_employee({showValue:"${prjProblem.appCZY}",showLabel:"${appCzy.nameChi}",readonly:true});	
		$("#confirmCZY").openComponent_employee({showValue:"${prjProblem.confirmCZY}",showLabel:"${confirmCzy.nameChi}",readonly:true});	
		$("#dealCZY").openComponent_employee({readonly:true} );	
		$("#cancelCZY").openComponent_employee({readonly:true} );	
		$("#feedbackCZY").openComponent_employee({readonly:true} );	
	
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
			var postData = $("#dataForm").jsonForm();
			$.ajax({
				url:"${ctx}/admin/prjProblem/doSaveConfirm",
				type: "post",
				data:postData,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
			    				closeWin();
						    }
						});
			    	}else{
			    		$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
			});
		});
	});
	function isExistDept(){
		var deptCode=$.trim($("#promDeptCode").val());
		$.ajax({
			url:"${ctx}/admin/prjProblem/isExistType",
			type: "post",
			data:{deptCode:deptCode},
			dataType: "text",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if("true"==obj){
			    	$("#dataForm").bootstrapValidator("addField", "promTypeCode", {  
			            validators: {  
			            	notEmpty: {  
				           		 message: "责任分类不能为空" ,
				            },
			            }  
					});
		    	}else{
					$("#dataForm").bootstrapValidator("removeField","promTypeCode");
		    	}
		    }
		});
	}
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>问题单号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${prjProblem.no }" readonly="readonly"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${prjProblem.custCode }" readonly="readonly"/>
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${prjProblem.address }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>问题性质</label>
									<house:xtdm id="promPropCode" dictCode="PRJPROMPROP" value="${prjProblem.promPropCode }"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>状态</label>
									<house:xtdm id="status" dictCode="PRJPROMSTATUS" value="${prjProblem.status }" disabled="true"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>责任部门</label>
									<select id="promDeptCode" name="promDeptCode" onChange="isExistDept()" value="${prjProblem.promDeptCode }"></select>
								</li>
								<li>
									<label>提报人</label>
									<input type="text" id="appCZY" name="appCZY" style="width:160px;" value="${prjProblem.appCZY }" readonly="readonly"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>责任分类</label>
									<select id="promTypeCode" name="promTypeCode" value="${prjProblem.promTypeCode }"></select>
								</li> 
								<li class="form-validate">
									<label>提报时间</label>
									<input type="text" id="appDate" name="appDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.appDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>确认人</label>
									<input type="text" id="confirmCZY" name="confirmCZY" value="${prjProblem.confirmCZY }"/>
								</li>
								
								<li class="form-validate">
									<label>反馈人</label>
									<input type="text" id="feedbackCZY" name="feedbackCZY" style="width:160px;" value="${prjProblem.feedbackCZY}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>确认时间</label>
									<input type="text" id="confirmDate" name="confirmDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.confirmDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
								
								<li class="form-validate">
									<label>反馈时间</label>
									<input type="text" id="feedbackDate" name="feedbackDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.feedbackDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>处理人</label>
									<input type="text" id="dealCZY" name="dealCZY" value="${prjProblem.dealCZY }"/>
								</li>
								<li class="form-validate">
									<label>撤销人</label>
									<input type="text" id="cancelCZY" name="cancelCZY" style="width:160px;" value="${prjProblem.cancelCZY}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>处理时间</label>
									<input type="text" id="dealDate" name="dealDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.dealDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
								<li class="form-validate">
									<label>撤销时间</label>
									<input type="text" id="cancelDate" name="cancelDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.cancelDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>导致停工</label>
									<house:xtdm id="isBringStop" dictCode="YESNO" value="${prjProblem.isBringStop }"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label>停工天数</label>
									<input type="number" id="stopDays" name="stopDays" style="width:160px;" value="${prjProblem.stopDays }" readonly/>
								</li>
							</div>	
							<div class="validate-group row">
								<li class="form-validate">
									<label>是否解决</label>
									<house:xtdm id="isDeal" dictCode="YESNO" value="${prjProblem.isDeal }" disabled="true"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label>预计处理时间</label>
									<input type="text" id="planDealDate" name="planDealDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.planDealDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>	
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea"><span class="required">*</span>问题描述</label>
									<textarea id="remarks" name="remarks" rows="3">${prjProblem.remarks }</textarea>
		 						</li>
	 						</div>
	 						<div class="validate-group row">
								<li>
									<label class="control-textarea" >解决方案</label>
									<textarea id="dealRemarks" name="dealRemarks" readonly="true" rows="3">${prjProblem.dealRemarks }</textarea>
		 						</li>
	 						</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
