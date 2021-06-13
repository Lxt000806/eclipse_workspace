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
	<title>固定资产管理调整单</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builderNum.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function() {
	function checkValidator(){
		$("#dataForm").data("bootstrapValidator")
	       .updateStatus("openComponent_department_aftValue", "NOT_VALIDATED",null)  
	       .validateField("openComponent_department_aftValue")
	       .updateStatus("openComponent_department_befValue", "NOT_VALIDATED",null)  
	       .validateField("openComponent_department_befValue"); 
	}
	
	switch ("${asset.chgType}"){
		case "1":
			$("#dataForm").bootstrapValidator({
				message : "This value is not valid",
				feedbackIcons : {/*input状态样式图片*/
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {
					chgAmount:{
						validators:{
							notEmpty:{
								message:"调整金额不能为空"
							}
						}
					}, 
					remarks:{
						validators:{
							notEmpty:{
								message:"备注不能为空"
							}
						}
					}, 
				},
		        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		    });	
			
			break;
		case "2":
			$("#dataForm").bootstrapValidator({
				message : "This value is not valid",
				feedbackIcons : {/*input状态样式图片*/
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {
					befValue:{
						validators:{
							notEmpty:{
								message:"变动前累计折旧不能为空"
							}
						}
					}, 
					aftValue:{
						validators:{
							notEmpty:{
								message:"变动后累计折旧不能为空"
							}
						}
					}, 
					remarks:{
						validators:{
							notEmpty:{
								message:"备注不能为空"
							}
						}
					}, 
				},
		        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		    });
			
			break;
		case "3":
			$("#dataForm").bootstrapValidator({
				message : "This value is not valid",
				feedbackIcons : {/*input状态样式图片*/
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {
					befValue:{
						validators:{
							notEmpty:{
								message:"变动前年限不能为空"
							}
						}
					}, 
					aftValue:{
						validators:{
							notEmpty:{
								message:"变动后年限不能为空"
							}
						}
					}, 
					remarks:{
						validators:{
							notEmpty:{
								message:"备注不能为空"
							}
						}
					}, 
				},
		        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		    });
			
			break;
		case "4":
			$("#dataForm").bootstrapValidator({
				message : "This value is not valid",
				feedbackIcons : {/*input状态样式图片*/
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {
					befValue:{
						validators:{
							notEmpty:{
								message:"变动前折旧方法不能为空"
							}
						}
					}, 
					aftValue:{
						validators:{
							notEmpty:{
								message:"变动后折旧方法不能为空"
							}
						}
					}, 
					remarks:{
						validators:{
							notEmpty:{
								message:"变动原因不能为空"
							}
						}
					}, 
				},
		        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		    });
			
			break;
		case "5":
			$("#befValue").openComponent_department({showValue:"${asset.department}",showLabel:"${asset.deptDescr}",callBack:checkValidator});	
			$("#aftValue").openComponent_department({callBack:checkValidator});	
			$("#befValue2").openComponent_employee({showValue:"${asset.useMan}",showLabel:"${asset.useManDescr}",readonly:true});	
			$("#aftValue2").openComponent_employee();	
			
			$("#dataForm").bootstrapValidator({
				message : "This value is not valid",
				feedbackIcons : {/*input状态样式图片*/
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {
					openComponent_department_befValue:{
						validators:{
							notEmpty:{
								message:"变动前部门不能为空"
							}
						}
					}, 
					openComponent_department_aftValue:{
						validators:{
							notEmpty:{
								message:"变动后部门不能为空"
							}
						}
					}, 
					aftAddress:{
						validators:{
							notEmpty:{
								message:"新存放地址不能为空"
							}
						}
					}, 
				},
		        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		    });
			$("#remarks_label").hide();
			break;
	}

	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
	    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;

		var datas = $("#dataForm").serialize();
		$.ajax({
			url:"${ctx}/admin/asset/doChg",
			type: "post",
			data: datas,
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
		    				closeWin(true);
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

// 计算变动后原值
function calcAftAmount(){
	var chgAmount=$("#chgAmount").val().replace(/[^\-?\d.]/g,"");
	var befValue=$("#befValue").val().replace(/[^\-?\d.]/g,"");
	$("#chgAmount").val(chgAmount);
	if(chgAmount > 0 || chgAmount < 0){
		$("#aftValue").val(myRound(befValue)+myRound(chgAmount));
	} else {
		$("#aftValue").val(myRound(befValue));
	}
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="chgType" name="chgType" value="${asset.chgType }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>资产编号</label>
									<input type="text" id="assetCode" name="assetCode" style="width:160px;" value="${asset.code}" readonly="readonly"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>资产名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${asset.descr}" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>规格型号</label>
									<input type="text" id="model" name="model" style="width:160px;" value="${asset.model}" readonly="true"/>
								</li>
								<li>
									<label>开始使用时间</label>
									<input type="text" id="beginDate" name="beginDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${asset.beginDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<c:if test="${asset.chgType == '1'}">
								<div class="validate-group row">
									<li class="form-validate" id="chgAmount_li">
										<label ><span class="required">*</span>调整金额</label>
										<input type="text" id="chgAmount" name="chgAmount" style="width:160px;" onkeyup="calcAftAmount()"/>
									</li>
									<li class="form-validate">
										<label id="befLabel"><span class="required">*</span>变动前原值</label>
										<input type="text" id="befValue" name="befValue" style="width:160px;" value="${asset.originalValue }" readonly="true"/>
									</li>
								</div>
								<div class="validate-group row">
									<li class="form-validate">
										<label id="aftLabel"><span class="required">*</span>变动后原值</label>
										<input type="text" id="aftValue" name="aftValue" style="width:160px;" readonly="true" placeholder="自动计算"/>
									</li>
								</div>
							</c:if>
							<c:if test="${asset.chgType == '2'}">
								<div class="validate-group row">
									<li class="form-validate">
										<label id="befLabel"><span class="required">*</span>变动前折旧</label>
										<input type="text" id="befValue" name="befValue" style="width:160px;" value="${asset.totalDeprAmount }"/>
									</li>
									<li class="form-validate">
										<label id="aftLabel"><span class="required">*</span>变动后折旧</label>
										<input type="text" id="aftValue" name="aftValue" style="width:160px;"/>
									</li>
								</div>
							</c:if>
							<c:if test="${asset.chgType == '3'}">
								<div class="validate-group row">
									<li class="form-validate">
										<label id="befLabel"><span class="required">*</span>变动前年限</label>
										<input type="text" id="befValue" name="befValue" style="width:160px;" value="${asset.useYear}"/>
									</li>
									<li class="form-validate">
										<label id="aftLabel"><span class="required">*</span>变动后年限</label>
										<input type="text" id="aftValue" name="aftValue" style="width:160px;"/>
									</li>
								</div>
							</c:if>
							<c:if test="${asset.chgType == '4'}">
								<div class="validate-group row">
									<li class="form-validate">
										<label id="befLabel"><span class="required">*</span>变动前折旧方法</label>
										<house:xtdm id="befValue" dictCode="ASSETDEPRTYPE" value="${asset.deprType }"></house:xtdm>
									</li>
									<li class="form-validate">
										<label id="aftLabel"><span class="required">*</span>变动后折旧方法</label>
										<house:xtdm id="aftValue" dictCode="ASSETDEPRTYPE"></house:xtdm>
									</li>
								</div>
							</c:if>
							<c:if test="${asset.chgType == '5'}">
								<div class="validate-group row">
									<li class="form-validate">
										<label id="befLabel"><span class="required">*</span>变动前部门</label>
										<input type="text" id="befValue" name="befValue" style="width:160px;" value="${asset.department }"/>
									</li>
									<li class="form-validate">
										<label id="aftLabel"><span class="required">*</span>变动后部门</label>
										<input type="text" id="aftValue" name="aftValue" style="width:160px;"/>
									</li>
								</div>
								<div class="validate-group row">
									<li class="form-validate">
										<label id="befLabel2">变动前使用人</label>
										<input type="text" id="befValue2" name="befValue2" style="width:160px;"/>
									</li>
									<li class="form-validate">
										<label id="aftLabel2">变动后使用人</label>
										<input type="text" id="aftValue2" name="aftValue2" style="width:160px;"/>
									</li>
								</div>
								<div class="validate-group row">
									<li class="form-validate" id="aftAddress_li">
										<label id="aftLabel"><span class="required">*</span>新存放地址</label>
										<input type="text" id="aftAddress" name="aftAddress" style="width:160px;"/>
									</li>
								</div>
							</c:if>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea"><span class="required" id="remarks_label">*</span>变动原因</label>
									<textarea id="remarks" name="remarks"></textarea>
								</li>
							</div>	
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">资产备注</label>
									<textarea id="assetRemarks" name="assetRemarks">${asset.remarks }</textarea>
								</li>
							</div>	
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
