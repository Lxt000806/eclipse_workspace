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
	<title>印章新增</title>
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
	if("${orgSeal.m_umState}"=="V"){
		$("#saveBtn,input,textarea,select").attr("disabled",true);
	}
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			type:{
				validators:{
					notEmpty:{
						message:"模板类型不能为空"
					}
				}
			},
			color:{
				validators:{
					notEmpty:{
						message:"颜色不能为空"
					}
				}
			},
			central:{
				validators:{
					notEmpty:{
						message:"中心图案类型不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
	$("#color").val("${orgSeal.color}");
	$("#type").val("${orgSeal.type}");
	$("#central").val("${orgSeal.central}");
});

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
    var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/orgSeal/doUpdate",
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
						<input type="hidden" name="orgId" value="${orgSeal.orgId }"/>
						<input type="hidden" name="sealId" value="${orgSeal.sealId }"/>
						<input type="hidden" name="pk" value="${orgSeal.pk }"/>
						
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
		                            <label>模板类型</label>
		                            <house:xtdm id="type" dictCode="SEALTYPE" value="${orgSeal.type}"></house:xtdm>
		                        </li>
								<li class="form-validate">
		                            <label>中心图案</label>
		                            <house:xtdm id="central" dictCode="SEALCENTRAL" value="${orgSeal.central}"></house:xtdm>
		                        </li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
		                           <label>颜色</label>
		                            <house:xtdm id="color" dictCode="SEALCOLOR" value="${orgSeal.color}"></house:xtdm>
		                        </li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>横向文</label>
									<input type="text" id="htext" name="htext" maxlength="8" style="width:160px;" value="${orgSeal.htext }"/>
								</li>
								<li class="form-validate">
									<label>下玄文</label>
									<input type="text" id="qtext" name="qtext" maxlength="20" style="width:160px;" value="${orgSeal.qtext }"/>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
