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
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="printBtn">
							<span>打印</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBtn">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" style="margin: 0px;">  
				<div class="panel-body">
					<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
						<ul class="ul-form">
							<div class="row">
								<li class="form-validate">
									<label for="whCode"><span class="required">*</span>仓库编号</label>
									<input type="text" id="whCode" name="whCode"/>
								</li>
								<li class="form-validate">
									<label for="whDescr">仓库名称</label>
									<input type="text" id="whDescr" name="whDescr" value="${preItemAppSend.whDescr}" readonly/>
								</li>
							</div>
						</ul>	
					</form>
				</div>
			</div>
		</div>
		<script type="text/javascript" defer>
			$(function(){
				parent.$("#iframe_print").attr("height","97%");
				$("#whCode").openComponent_wareHouse({
					showValue: "${preItemAppSend.whCode}",
					showLabel: "${preItemAppSend.whDescr}",
					callBack: setWHDescr,
				});
				$("#page_form").bootstrapValidator({
					message : "请输入完整的信息",
					feedbackIcons : {
						validating : "glyphicon glyphicon-refresh"
					},
					fields : {  
						openComponent_wareHouse_whCode:{
							validators: {
								notEmpty: {
									message: "仓库编号不能为空"
								}
							}
						},
					},
					submitButtons : "input[type='submit']"
				});
				$("#printBtn").on("click", function () {
					$("#page_form").bootstrapValidator("validate");
					if(!$("#page_form").data("bootstrapValidator").isValid()) return;
					var whCode = $("#whCode").val();
					Global.Print.showPrint("preItemAppSend.jasper", 
						{
							no:"${preItemAppSend.no}",
							whcode:whCode,
							SUBREPORT_DIR: "<%=jasperPath%>" 
						}
					);
				});
				$("#closeBtn").on("click", function () {
					closeWin(false);
				})
			});
			function setWHDescr(data) { //设置仓库名称
				$("#whDescr").val(data.desc1);
				$("#page_form").data("bootstrapValidator")  
					.updateStatus("openComponent_wareHouse_whCode", "NOT_VALIDATED", null)
					.validateField("openComponent_wareHouse_whCode");
			}
		</script>
	</body>
</html>
