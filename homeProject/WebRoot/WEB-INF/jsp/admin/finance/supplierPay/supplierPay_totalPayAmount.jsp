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
		.form-search .ul-form li label {
			width: 140px;
		}
	</style>
	<script type="text/javascript" defer>
		var totalRemainAmount = parseFloat("${totalRemainAmount}");
		$(function() {
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					paidAmount:{
						validators: {  
							notEmpty: {  
								message: "付款总金额应为数字，请重新输入"
							}, 
						}  
					},
				}
			});
		});
		function doSave() {
			var paidAmount = parseFloat($("#paidAmount").val());
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			if (totalRemainAmount < 0 && paidAmount > 0) {
				art.dialog({content: "应付总余额为负，请输入负数",width: 220});
				$("#paidAmount").focus();
				return;
			} else if (totalRemainAmount > 0 && paidAmount < 0) {
				art.dialog({content: "应付总余额为正，请输入正数",width: 220});
				$("#paidAmount").focus();
				return;
			} else if (0 != paidAmount && Math.abs(paidAmount) > Math.abs(totalRemainAmount)) {
				art.dialog({content: "付款总金额不能超过应付总余额",width: 220});
				$("#paidAmount").focus();
				return;
			}
			Global.Dialog.returnData = myRound(paidAmount, 2);
			closeWin();
		}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave();">
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
						<li class="form-validate">
							<label><span class="required">*</span>请输入付款总金额</label>
							<input type="text" id="paidAmount" name="paidAmount" style="width:160px;"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
								value="${paidAmount}"/>
						</li>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
</html>
