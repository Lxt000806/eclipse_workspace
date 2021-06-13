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
	<title>工地问题处理</title>
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
		$("#confirmCZY").openComponent_employee({showValue:"${prjProblem.confirmCZY}",showLabel:"${prjProblem.confirmCZYDescr}"});	
		$("#appCZY").openComponent_employee({showValue:"${prjProblem.confirmCZY}",showLabel:"${prjProblem.confirmCZYDescr}"});
		$("#custCode").openComponent_customer({
			showValue:"${prjProblem.custCode}",
			showLabel:"${prjProblem.custDescr}",
			callBack:function(data){
				$("#address").val(data.address);
			}
		});	
		
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {
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
				remarks:{  
					validators: {  
						notEmpty: {  
							message: "描述不能为空"  
						}
					}  
				},
				custCode:{  
					validators: {  
						notEmpty: {  
							message: "客户编号不能为空"  
						}
					}  
				},
				openComponent_customer_custCode:{  
					validators: {  
						notEmpty: {  
							message: "客户编号不能为空"  
						}
					}  
				},
				isBringStop:{  
					validators: {  
						notEmpty: {  
							message: "导致停工不能为空"  
						}
					}  
				},
				stopDays:{  
					validators: {  
						notEmpty: {  
							message: "停工天数不能为空"  
						}
					}  
				},
			},
			submitButtons : "input[type='submit']" 
		});
	});
	
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/prjProblem/promDeptandType","promDeptCode","promTypeCode");
		Global.LinkSelect.setSelect({firstSelect:"promDeptCode",
								firstValue:"${prjProblem.promDeptCode }",
								secondSelect:"promTypeCode",
								secondValue:"${prjProblem.promTypeCode }",});
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
			var postData = $("#dataForm").jsonForm();
			$.ajax({
				url:"${ctx}/admin/prjProblem/doSave",
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
									<input type="text" id="no" name="no" style="width:160px;" placeholder="保存时自动生成" readonly="readonly"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${prjProblem.custCode }" />
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${prjProblem.address }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>问题性质</label>
									<house:xtdm id="promPropCode" dictCode="PRJPROMPROP" value="${prjProblem.promPropCode }" ></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>状态</label>
									<house:xtdm id="status" dictCode="PRJPROMSTATUS" value="2" disabled="true"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>责任部门</label>
									<select id="promDeptCode" name="promDeptCode" value="${prjProblem.promDeptCode }" ></select>
								</li>
								<li class="form-validate">
									<label>提报人</label>
									<input type="text" id="appCZY" name="appCZY" style="width:160px;" value="${prjProblem.appCZY }" readonly="readonly"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>责任分类</label>
									<select id="promTypeCode" name="promTypeCode" value="${prjProblem.promTypeCode }" ></select>
								</li> 
								<li class="form-validate">
									<label>提报时间</label>
									<input type="text" id="appDate" name="appDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.confirmDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>确认人</label>
									<input type="text" id="confirmCZY" name="confirmCZY" value="${prjProblem.confirmCZY }"/>
								</li>
								<li class="form-validate">
									<label>确认时间</label>
									<input type="text" id="confirmDate" name="confirmDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.confirmDate}' pattern='yyyy-MM-dd'/>" />
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>是否停工</label>
									<house:xtdm id="isBringStop" dictCode="YESNO" value="${prjProblem.isBringStop }"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>停工天数</label>
									<input type="number" id="stopDays" name="stopDays" style="width:160px;" value="0" />
								</li>
							</div>	
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea"><span class="required">*</span>问题描述</label>
									<textarea id="remarks" name="remarks" rows="3" >${prjProblem.remarks }</textarea>
		 						</li>
	 						</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
