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
	<script src="${resourceRoot}/pub/component_sendTime.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript" defer>
		var m_umState = "${m_umState}",no = "${no}";
		$(function(){
			switch (m_umState) {
			case "M":
				$("#no").openComponent_sendTime({
					showValue:no,
				});
				break;
			case "V":
				$("#saveBtn").remove();
				$("#no").openComponent_sendTime({
					showValue:no,
				});
				disabledForm();
				break;
			default:
				$("#no").openComponent_sendTime();
				break;
			}

			$("#page_form").bootstrapValidator({
				message: "请输入完整的信息",
				feedbackIcons: {
					validating: "glyphicon glyphicon-refresh"
				},
				fields: {
					openComponent_sendTime_no:{  
						validators: {  
							notEmpty: {  
								message: "发货时限编号不能为空"  
							},
						}  
					},
				},
				submitButtons: "input[type='submit']"
			});

		});
		function doSave() {
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var keyArr = "${keys}".split(",");
			var datas=$("#page_form").jsonForm();
			for (var i = 0; i < keyArr.length; i++) {
				if (datas.no == keyArr[i]) {
					art.dialog({content: "该发货时限编号已存在", width: 220,});
					return false;
				}
			}
			Global.Dialog.returnData = datas;
			closeWin();
		}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
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
							<label><span class="required">*</span>发货时限编号</label>
							<input type="text" id="no" name="no" />
						</li>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
</html>
