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
	<title>员工挂证管理新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_salaryEmp.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
function validateRefresh(){
	$("#dataForm").data("bootstrapValidator")
    .updateStatus("openComponent_salaryEmp_salaryEmp", "NOT_VALIDATED",null)  
    .validateField("openComponent_salaryEmp_salaryEmp");
}

$(function() {
	$("#salaryEmp").openComponent_salaryEmp({callBack:validateRefresh});	

	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			openComponent_salaryEmp_salaryEmp:{
				validators:{
					notEmpty:{
						message:"编号不能为空"
					}
				}
			},
			socialInsurParam:{
				validators:{
					notEmpty:{
						message:"挂证补贴参数不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
});

function save(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
	
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/salaryEmpReg/checkInfo",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			if(obj.rs){
				doSave(datas);
			}else{
				art.dialog({
					content:obj.msg,
					lock: true,
					width: 200,
					height: 100,
					ok: function () {
						doSave(datas);
					},
					cancel: function () {
						return true;
					}
				});	
			}
	    }
	});
}

function doSave(datas){
	$.ajax({
		url:"${ctx}/admin/salaryEmpReg/doSave",
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
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="save()">
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
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>员工编号</label>
									<input type="text" id="salaryEmp" name="salaryEmp" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>挂证补贴参数</label>
									<house:dict id="socialInsurParam" dictCode="" sql="select pk code ,descr from tSocialInsurParam " 
												sqlValueKey="code" sqlLableKey="descr"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="3"></textarea>
								</li>
							</div>
							
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
