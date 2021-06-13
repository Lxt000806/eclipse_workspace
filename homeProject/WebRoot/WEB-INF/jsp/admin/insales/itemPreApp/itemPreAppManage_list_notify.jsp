<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>通知监理</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			function validateRefresh(){
				$("#dataForm").data("bootstrapValidator")
		                   	  .updateStatus("remarks", "NOT_VALIDATED", null)  
		                      .validateField("remarks");                    
			}
			$(function(){			
				$("#dataForm").bootstrapValidator({
					message : "This value is not valid",
					feedbackIcons : {/*input状态样式图片*/
						invalid : "glyphicon glyphicon-remove",
						validating : "glyphicon glyphicon-refresh"
					},
					fields: {  
						remarks:{
							validators:{
								stringLength: {
		                            max: 200,
		                            message: "长度不超过200个字符"
		                        } 
							}
						}
					},
					submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
				});	
				$("#msgText").focus().val("${data.msgText}");
				$("#projectMan").openComponent_employee({
	   				showValue:"${data.projectMan}",
	   				showLabel:"${data.projectManDescr}",
	   				readonly:true
				});
				if("${data.projectManDescr}"){
	  				$("#openComponent_employee_projectMan").val("${data.projectManDescr}");
	  			}
			});
			function save(){
				var msgText=$("#msgText").val();
				if(msgText==""){
					art.dialog({
						content: "消息内容不能为空！",
					});
					return;
				}
			 	$.ajax({
					url:"${ctx}/admin/itemPreApp/doNotify",
					type: "post",
					data: {appNo:"${data.appNo}",msgText:$("#msgText").val()},
					dataType: "json",
					cache: false,
					error: function(obj){
						art.dialog({
							content: "保存出错,请重试",
							time: 3000,
							beforeunload: function () {
							}
						});
					},
					success: function(obj){
						art.dialog({
							content: "操作成功",
							time: 1000,
							beforeunload: function () {
								closeWin();
							}
						});
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
						<button id="saveBut" type="button" class="btn btn-system " onclick="save()">保存</button>
						<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>	
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
						<ul class="ul-form">
							<div id="row"> 
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" value="${data.address}" readonly/>
								</li>
							</div>
							<div class="row"> 
								<li>
									<label>项目经理</label>
									<input type="text" id="projectMan" name="projectMan" value="${data.projectMan}" />
								</li>
							</div>
							<div class="row"> 
								<li class="row">
									<label class="control-textarea">消息内容</label>
									<textarea id="msgText" name="msgText" rows="4"></textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>

