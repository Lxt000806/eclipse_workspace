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
	<title>电子合同机构管理新增</title>
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
<script type="text/javascript"> 
$(function() {
	if("${organization.m_umState}"=="V"){
		$("#saveBtn,input,textarea,select").attr("disabled",true);
	}
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			name:{
				validators:{
					notEmpty:{
						message:"机构名称不能为空"
					}
				}
			},
			idNumber:{
				validators:{
					notEmpty:{
						message:"机构证件号不能为空"
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
    var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/organization/doUpdate",
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
						<input type="hidden" name="pk" value="${organization.pk }"/>
						
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>机构账号</label>
									<input type="text" id="orgId" name="orgId" style="width:160px;" placeholder="保存时生成" readonly="readonly" value="${organization.orgId }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>机构名称</label>
									<input type="text" id="name" name="name" style="width:160px;" value="${organization.name }"/>
								</li>
								<li class="form-validate">
									<label>机构证件号</label>
									<input type="text" id="idNumber" name="idNumber" style="width:160px;" value="${organization.idNumber }" readonly="readonly"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>法人</label>
									<input type="text" id="orgLegalName" name="orgLegalName" style="width:160px;" value="${organization.orgLegalName }"/>
								</li>
								<li class="form-validate">
									<label>法人身份证号</label>
									<input type="text" id="orgLegalIdNumber" name="orgLegalIdNumber" style="width:160px;" value="${organization.orgLegalIdNumber }"/>
								</li>
							</div>
							<c:if test="${organization.m_umState == 'V' }">
								<div class="validate-group row">
									<li class="form-validate">
			                            <label>是否认证</label>
			                            <house:xtdm id="isIdentified" dictCode="YESNO" value="${organization.isIdentified}" readonly="readonly"></house:xtdm>
			                        </li>
									<li class="form-validate">
										<label>认证Url</label>
										<input type="text" id="identifyUrl" name="identifyUrl" style="width:160px;" value="${organization.identifyUrl }" readonly="readonly"/>
									</li>
								</div>
								<div class="validate-group row">
									<li class="form-validate">
			                            <label>是否静默签署</label>
			                            <house:xtdm id="isSilenceSign" dictCode="YESNO" value="${organization.isSilenceSign}" disabled="true"></house:xtdm>
			                        </li>
								</div>
							</c:if>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
