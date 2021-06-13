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
	<title>未下单跟踪备注录入</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<style type="text/css">
		.panel-info {
			margin: 0px;
		}
		.li-textarea {
			max-height: 150px !important;
		}
		.control-textarea {
			top: -80px !important;
		}
		textarea {
			width: 250px !important;
			height: 100px;
		}
	</style>
	<script type="text/javascript" defer>
		parent.$("#iframe_appSoftRemark").attr("height","98%");
		$(function() {
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					appSoftRemark:{
						validators: {  
							notEmpty: {  
								message: "请输入完整的信息"
							}, 
						}  
					},
				}
			});

			$("#saveBtn").on("click", function(){
				$("#page_form").bootstrapValidator("validate");
				if(!$("#page_form").data("bootstrapValidator").isValid()) return;
				Global.Form.submit("page_form", "${ctx}/admin/softNotAppQuery/doAppSoftRemarkSave", 
					{custCode:"${custCode}"}, function(ret){
						if(ret.rs==true){
							art.dialog({
								content: ret.msg,
								time: 1000,
								beforeunload: function () {
									closeWin();
								}
							});
						}else{
							$("#_form_token_uniq_id").val(ret.token.token);
							art.dialog({
								content: ret.msg,
								width: 200
							});
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
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false);">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<ul class="ul-form">
						<li class="form-validate li-textarea">
							<label class="control-textarea"><span class="required">*</span>软装未下单说明</label>
							<textarea id="appSoftRemark" name="appSoftRemark">${appSoftRemark}</textarea>
						</li>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
</html>
