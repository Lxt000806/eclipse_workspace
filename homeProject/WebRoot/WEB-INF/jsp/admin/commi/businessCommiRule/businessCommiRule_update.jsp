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
	<title>业务提成规则编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function() {
	$("#department").openComponent_department({
		showValue:"${businessCommiRule.department}", 
		showLabel:"${department.desc1}"
	});	
	$("#empCode").openComponent_employee({
		showValue:"${businessCommiRule.empCode}" , 
		showLabel:"${employee.nameChi}"
	});	

	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			role:{
				validators:{
					notEmpty:{
						message:"角色不能为空"
					}
				}
			},
			type:{
				validators:{
					notEmpty:{
						message:"类型不能为空"
					}
				}
			},
			isBearAgainCommi:{
				validators:{
					notEmpty:{
						message:"承担翻单提成不能为空"
					}
				}
			},
			prior:{
				validators:{
					notEmpty:{
						message:"优先级不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
    
	chgRole();
});

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
	var type = $("#type").val();
	var commiPer = $("#commiPer").val();
	var designAgainSubsidyPer = $("#designAgainSubsidyPer").val();
	var subsidyPer = $("#designAgainSubsidyPer").val();
	var floatRulePK = $("#floatRulePK").val();
	
	if("1" == type && commiPer==""){
		art.dialog({
			content:"类型为固定时，提成点数不能为空",
		});
		return;
	}
	
	if("2" == type && floatRulePK==""){
		art.dialog({
			content:"类型为浮动时，提成浮动规则不能为空",
		});
		return;
	}
	
	if(designAgainSubsidyPer != "" && designAgainSubsidyPer > 1){
		art.dialog({
			content:"设计师翻单补贴点只能是0-1之间",
		});
		return;
	}
	if(subsidyPer != "" && subsidyPer > 1){
		art.dialog({
			content:"补贴点只能是0-1之间",
		});
		return;
	}
	if(commiPer != "" && commiPer > 1){
		art.dialog({
			content:"业绩点只能是0-1之间",
		});
		return;
	}
    var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/businessCommiRule/doUpdate",
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
}

function chgEmpCode(){
	var empCode = $("#empCode").val();
	if(empCode == ""){
		$("#posiType").attr("distabled","disabled");
	} else {
		$("#posiType").removeAttr("distabled","disabled");
	}
}

function chgType(){
	var type = $("#type").val();
	if("1" == type){
		$("#commiPer").removeAttr("readonly","readonly");
		$("#floatRulePK").val("");
		$("#floatRulePK").attr("disabled","disabled");
	} else {
		$("#floatRulePK").removeAttr("disabled","disabled");
		$("#commiPer").attr("readonly","readonly");
		$("#commiPer").val("0.0");
	}
}

function chgRole(){
	var role = $("#role").val();
	if(role == "24"){
		$("#recordRightCommiPer,#recordCommiPer").removeAttr("readonly");
	} else {
		$("#recordRightCommiPer,#recordCommiPer").val(0.0).attr("readonly","readonly");
	}
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="pk" id="pk" value="${businessCommiRule.pk }"/>
						<input type="hidden" name="expired" id="expired" value="${businessCommiRule.expired }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
								<label><span class="required">*</span>角色</label>
								<house:dict id="role" dictCode="" sqlValueKey="Code" sqlLableKey="Descr" onchange="chgRole()"
								    sql="select Code,code+' '+Descr Descr from tRoll where Expired = 'F' and Code in ('01','24')" value="${businessCommiRule.role}"></house:dict>
							</li>
								<li class="form-validate">
									<label>职位</label>
									<house:dict id="posiType" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
									    sql="select Code, Code+' '+Desc2 Descr from tPosition where Expired = 'F'" value="${businessCommiRule.posiType}"></house:dict>   
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>部门</label>
									<input type="text" id="department" name="department" style="width:160px;" value="${businessCommiRule.department}"/>
								</li>
								<li class="form-validate">
									<label>员工编号</label>
									<input type="text" id="empCode" name="empCode" style="width:160px;" value="${businessCommiRule.empCode}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>优先级</label>
									<house:dict id="prior" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
												where p.type = 'p' and p.number between 0 and 9 " 
												sqlValueKey="code" sqlLableKey="descr" value="${businessCommiRule.prior }"></house:dict>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>类型</label>
									<house:xtdm id="type" dictCode="COMMIRULETYPE" onchange="chgType()"
											value="${businessCommiRule.type}"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>提成点</label>
									<input type="text" id="commiPer" name="commiPer" style="width:160px;" value="${businessCommiRule.commiPer}"/>
								</li>
								<li class="form-validate">
									<label>补贴点</label>
									<input type="text" id="subsidyPer" name="subsidyPer" style="width:160px;" value="${businessCommiRule.subsidyPer}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>设计师翻单补贴点</label>
									<input type="text" id="designAgainSubsidyPer" name="designAgainSubsidyPer" style="width:160px;" value="${businessCommiRule.designAgainSubsidyPer}"/>
								</li>
								<li class="form-validate">
									<label>录单提成点</label>
									<input type="text" id="recordCommiPer" name="recordCommiPer" style="width:160px;" value="${businessCommiRule.recordCommiPer}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>右边提成点</label>
									<input type="text" id="rightCommiPer" name="rightCommiPer" style="width:160px;" value="${businessCommiRule.rightCommiPer}"/>
								</li>
								<li class="form-validate">
									<label>录单右边提成点</label>
									<input type="text" id="recordRightCommiPer" name="recordRightCommiPer" style="width:160px;" value="${businessCommiRule.recordRightCommiPer}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>承担翻单提成</label>
									<house:xtdm id="isBearAgainCommi" dictCode="YESNO" value="${businessCommiRule.isBearAgainCommi}"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label>提成浮动规则</label>
									<house:dict id="floatRulePK" dictCode="" sql="select pk, Descr from tBusinessCommiFloatRule where expired = 'F' " 
												sqlValueKey="pk" sqlLableKey="Descr" value="${businessCommiRule.floatRulePK }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2">${businessCommiRule.remarks }</textarea>
	 							</li>
							</div>
							<div class="validate-group row">
								<li>
		                            <label>过期</label>
		                            <input type="checkbox" name="expiredCheckbox"
		                                   ${businessCommiRule.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
		                        </li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
