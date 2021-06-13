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
								<li  class="form-validate" style="max-height: 200px;">
									<label class="control-textarea" style="top: -90px;"><span class="required">*</span>重打说明</label>
									<textarea id="reprintRemarks" name="reprintRemarks" style="height: 112px;width: 516px;"></textarea>
								</li>
							</div>
						</ul>	
					</form>
				</div>
			</div>
		</div>
		<script type="text/javascript" defer>
			$(function(){
				parent.$("#iframe_rePrint").attr("height","97%");
				$("#page_form").bootstrapValidator({
					message : "请输入完整的信息",
					feedbackIcons : {
						validating : "glyphicon glyphicon-refresh"
					},
					fields : {  
						reprintRemarks:{
							validators: {
								notEmpty: {
									message: "重打说明不能为空"
								}
							}
						},
					},
					submitButtons : "input[type='submit']"
				});
				$("#printBtn").on("click", function () {
					$("#page_form").bootstrapValidator("validate");
					if(!$("#page_form").data("bootstrapValidator").isValid()) return;
					Global.Print.showPrint("custPayTran.jasper", 
						{
							pk:"${custPayTran.pk}",
							LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
							SUBREPORT_DIR: "<%=jasperPath%>" 
						}, null, 
						function(){
							art.dialog({
								content:"是否已打印报表?",
								ok:function(){
									$.ajax({
										url:"${ctx}/admin/custPayTran/doRePrint",
										type:"post",
										data:{pk:"${custPayTran.pk}", reprintRemarks:$("#reprintRemarks").val()},
										cache:false,
										async:false,
										error:function(obj){
											showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
										},
										success:function(obj){
											art.dialog({
												content : "操作成功！",
												time : 1000,
												beforeunload : function() {
													closeWin();
												}
											});
										}
									});
								},
								cancel:function(){},
								okValue:"是",
								cancelValue:"否"
							});
						}
					);
				});
				$("#closeBtn").on("click", function () {
					closeWin(false);
				})
			});
		</script>
	</body>
</html>
