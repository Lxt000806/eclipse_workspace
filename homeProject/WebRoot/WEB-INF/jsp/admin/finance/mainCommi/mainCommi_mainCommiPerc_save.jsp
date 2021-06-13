<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
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
					<input type="hidden" id="expired" name="expired" value="${mainCommiPerc.expired}"/>
					<input type="hidden" id="m_umState" name="m_umState" value="${mainCommiPerc.m_umState}"/>
					<ul class="ul-form">
							<li class="form-validate">
								<label><span class="required">*</span>职位编号</label>
								<input type="text" id="code" name="code" style="width:160px;" 
									value="${mainCommiPerc.code}" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>职位名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"
									value="${mainCommiPerc.descr}" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>提成比例</label>
								<input type="text" id="commiPerc" name="commiPerc" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${mainCommiPerc.commiPerc}" maxlength="16"/>
							</li>
							<li hidden="true" >
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${mainCommiPerc.expired}" 
									onclick="checkExpired(this)" ${mainCommiPerc.expired=="T"?"checked":""}/>
							</li>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var url = "";
		$(function() {
			switch ("${mainCommiPerc.m_umState}") {
				case "M":
					url = "${ctx}/admin/mainCommiPerc/doUpdate";
					$("#readonly").attr("disabled",true);
					break;
				case "V":
					$("#saveBtn").remove();
					disabledForm();
					break;
				default:
					url = "${ctx}/admin/mainCommiPerc/doSave";
					break;
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
								message: "职位编号不允许为空"  
							},
						}  
					},
					descr:{ 
						validators: {  
							notEmpty: {  
								message: "职位名称不允许为空"  
							},
						}  
					},
					commiPerc:{ 
						validators: {  
							notEmpty: {  
								message: "提成比例不允许为空"  
							},
							numeric: {
					            message: "提成比例只能是数字" 
					        },
						}  
					},
				}
			});
			$("#closeBtn").on("click", function () {
				closeWin(false);
			});
		});
		$("#saveBtn").on("click", function () {
			if ("V" == "${mainCommiPerc.m_umState}") return;
			$("#page_form").bootstrapValidator("validate");/* 提交验证 */
			if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
			var datas = $("#page_form").serialize();
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
		});
		
	</script>
</body>
</html>
