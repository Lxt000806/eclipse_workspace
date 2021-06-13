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
	<title>设计提成规则新增</title>
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
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function() {
	$("#department").openComponent_department({});	
	$("#empCode").openComponent_employee({});	

	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			role:{
				validators:{
					notEmpty:{
						message:"角色从不能为空"
					}
				}
			},
			checkCommiType:{
				validators:{
					notEmpty:{
						message:"结算提成类型不能为空"
					}
				}
			},
			preCommiPer:{
				validators:{
					notEmpty:{
						message:"预发提成点不能为空"
					}
				}
			},
			prior:{
				validators:{
					notEmpty:{
						message:"补贴点不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
});

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
	var checkCommiType = $("#checkCommiType").val();
	var floatRulePK = $("#floatRulePK").val();
	var preCommiPer = $("#preCommiPer").val();
	var checkCommiPer = $("#checkCommiPer").val();
	var subsidyPer = $("#subsidyPer").val();
	
	if("2" == checkCommiType && floatRulePK==""){
		art.dialog({
			content:"类型为浮动时，提成浮动规则不能为空",
		});
		return;
	}
	
	if("1" == checkCommiType && checkCommiPer==""){
		art.dialog({
			content:"类型为固定时，结算提成点不能为空",
		});
		return;
	}

	if(preCommiPer != "" && preCommiPer > 1){
		art.dialog({
			content:"预发提成点数只能在0-1之间",
		});
		return;
	}
	if(checkCommiPer != "" && checkCommiPer > 1){
		art.dialog({
			content:"结算提成点数只能在0-1之间",
		});
		return;
	}
	if(subsidyPer != "" && subsidyPer > 1){
		art.dialog({
			content:"补贴点数只能在0-1之间",
		});
		return;
	}
    var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/designCommiRule/doSave",
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

function goAddFloatRule(){
	Global.Dialog.showDialog("floatRuleSave",{
		title:"设计提成规则管理--浮动规则新增",
		postData:{},
		url:"${ctx}/admin/designCommiFloatRule/goSave",
		height:548,
		width:1058,
        resizable: true,
		returnFun:function(){
			retFloatRuleSelection();
		}
	});	
}

function retFloatRuleSelection(){
	$.ajax({
		url:"${ctx}/admin/designCommiFloatRule/getFloatRuleSelection",
		type: "post",
		data: {},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){ 
	    	if(obj){
	    		console.log(obj);
		    	$("#floatRulePK").find("option").remove();
		    	$("#floatRulePK").append("<option value=\"\">"+"请选择..."+"</option>");
	    		for(var i = 0; i < obj.length; i++){
	    			$("#floatRulePK").append("<option value=\""+obj[i]["PK"]+"\">"+obj[i]["Descr"]+"</option>");
	    			if(i == obj.length - 1){
	    				$("#floatRulePK").searchableSelect();
	    			} 
	    		}
	    	}
	    }
	});
}

function chgType(){
	var checkCommiType = $("#checkCommiType").val();
	if("1" == checkCommiType){
		$("#checkCommiPer").removeAttr("readonly","readonly");
		$("#floatRulePK").val("");
		$("#floatRulePK").attr("disabled","disabled");
	} else {
		$("#floatRulePK").removeAttr("disabled","disabled");
		$("#checkCommiPer").attr("readonly","readonly");
		$("#checkCommiPer").val("0.0");
	}
}
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body btn-group-xs" >
					<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>角色</label>
									<house:dict id="role" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
									    sql="select Code,code+' '+Descr Descr from tRoll where Expired = 'F' and code in ('00','63')"></house:dict>
								</li>
								<li class="form-validate">
									<label>部门</label>
									<input type="text" id="department" name="department" style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>员工编号</label>
									<input type="text" id="empCode" name="empCode" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>优先级</label>
									<house:dict id="prior" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
												where p.type = 'p' and p.number between 0 and 9 " 
												sqlValueKey="code" sqlLableKey="descr" ></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>提成预发点</label>
									<input type="text" id="preCommiPer" name="preCommiPer" onkeyup="value=value.replace(/[^\?\d.]/g,'')" style="width:160px;" value="0.0"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>结算提成类型</label>
									<house:xtdm id="checkCommiType" dictCode="COMMIRULETYPE" onchange="chgType()"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>结算提成点</label>
									<input type="text" id="checkCommiPer" name="checkCommiPer" onkeyup="value=value.replace(/[^\?\d.]/g,'')" style="width:160px;" value="0.0"/>
								</li>
								<li class="form-validate">
									<label>提成浮动规则</label>
									<house:dict id="floatRulePK" dictCode="" sql="select pk, Descr from tDesignCommiFloatRule where expired = 'F' " 
												sqlValueKey="pk" sqlLableKey="Descr"></house:dict>
									<!-- <button type="button" class="btn btn-system" data-disabled="false" 
								    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-8px;margin-top: -5px;width:26px;height:24px;padding-top:2.5px" onclick="goAddFloatRule()">
								    	<span class="glyphicon glyphicon-plus" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
								    </button> -->
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>补贴点</label>
									<input type="text" id="subsidyPer" name="subsidyPer" style="width:160px;" value="0.0"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2">${designCommiRule.remarks }</textarea>
	 							</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
