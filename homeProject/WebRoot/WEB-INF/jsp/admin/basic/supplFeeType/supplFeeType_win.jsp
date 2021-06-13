<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
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
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${supplFeeType.code}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${supplFeeType.descr}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>是否默认值</label>
								<house:xtdm id="isDefault" dictCode="YESNO" style="width:160px;" value="${supplFeeType.isDefault}"/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
	<script type="text/javascript" defer>
		var url = "${supplFeeType.m_umState}"!="A"?"${ctx}/admin/supplFeeType/doUpdate":"${ctx}/admin/supplFeeType/doSave";
		$("#isDefault option[value='']").remove();
		$(function() {
			var dialog_id = "view";
			switch ("${supplFeeType.m_umState}") {
				case "A":
					dialog_id = "add";
					$("#isDefault").val(1);
					break;
				case "M":
					dialog_id = "update";
					break;
				default:
					break;
			}
			parent.$("#iframe_"+dialog_id).attr("height","98%"); //消灭掉无用的滑动条
			if ("M" == "${supplFeeType.m_umState}") {
				$("#code").prop("readonly", "true");
			}
			if ("V" == "${supplFeeType.m_umState}") {
				$("#saveBtn").remove();
				$("#descr").prop("readonly", "true");
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					code:{ 
						validators: {  
							notEmpty: {  
								message: "编号不允许为空"  
							},
						}  
					},
					descr:{ 
						validators: {  
							notEmpty: {  
								message: "名称不允许为空"  
							},
						}  
					}
				}
			});
			$("#saveBtn").on("click", function () {
				$("#page_form").bootstrapValidator("validate");/* 提交验证 */
				if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
				var datas = $("#page_form").jsonForm();
				saveAjax(datas);
			});
			$("#closeBtn").on("click", function () {
				closeWin();
			})
		});
		function saveAjax(datas) {
			$.ajax({
				url:url,
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				},
				success: function(obj){
					if(obj.rs){
						art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
								closeWin();
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
</html>
