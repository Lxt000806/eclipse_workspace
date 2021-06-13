<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
		.panel-info {
			margin: 0px;
		}
		.panel-info .panel-body {
			height: 210px;
		}
		.form-search {
			padding-top: 35px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_act_user.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBtn">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" 
						value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<li class="form-validate">
							<label><span class="required">*</span>操作员编码</label>
							<input type="text" id="id" name="id"/>
						</li>
						<li class="form-validate" hidden="true">
							<label><span class="required">*</span>用户名称</label>
							<input type="text" id="first" name="first" value="${actUser.first}" />
						</li>
						<%-- <li class="form-validate">
							<label><span class="required">*</span>用户姓氏</label>
							<input type="text" id="last" name="last" value="${actUser.last}" />
						</li> --%>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
	<script type="text/javascript"> 
		var selectRows = [];
		var m_umState = "${actUser.m_umState}";
		$(function() {
			switch (m_umState) {
			case "M":
				$("#id").openComponent_czybm({
					showValue:"${actUser.id}",
					showLabel:"${actUser.first}",
					callBack:setName
				});
				replaceCloseEvt("userUpdate", befClose);
				break;
			case "V":
				//不显示保存按钮
				$("#id").openComponent_czybm({
					readonly: true,
					showValue:"${actUser.id}",
					showLabel:"${actUser.first}",
				});
				$("#saveBtn").remove();
				disabledForm();
				break;
			default:
				$("#id").openComponent_czybm({callBack:setName});
				replaceCloseEvt("userSave", befClose);
				break;
			}
			/* $("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {  
					openComponent_employee_id:{  
						validators: {  
							notEmpty: {  
								message: "编号不能为空"  
							},
						}  
					},
				},
				submitButtons : "input[type='submit']"
			}); */
			$("#saveBtn").on("click",function(){
				/* $("#page_form").data("bootstrapValidator").resetForm();
				$("#page_form").bootstrapValidator("validate");
				if(!$("#page_form").data("bootstrapValidator").isValid()){ 
					art.dialog({content: "无法保存，请输入完整的信息",width: 220});
					return;
				} */
				var datas=$("#page_form").jsonForm();
			 	selectRows.push(datas);
			 	if ("M" == m_umState) {
					Global.Dialog.returnData = selectRows;
					closeWin();
			 	}
			 	if ("A" == m_umState) clearForm();
			});
			$("#closeBtn").on("click", function () {
				befClose();
			});
		});
		function befClose() {
			if ("A" == m_umState) {
				Global.Dialog.returnData = selectRows;
			}
			closeWin();
		}
		function setName(data) {
			if (data) {
				$("#first").val(data.zwxm);
				/* $("#page_form").data("bootstrapValidator")  
					.updateStatus("openComponent_employee_id", "NOT_VALIDATED", null)
					.validateField("openComponent_employee_id"); */
			}
		}
	</script>
</html>
