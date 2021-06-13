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
					<input type="hidden" id="expired" name="expired" value="${projectManQltFeeWithHoldRule.expired}"/>
					<input type="hidden" id="pk" name="pk" value="${projectManQltFeeWithHoldRule.pk}"/>
					<ul class="ul-form">
							<li class="form-validate">
								<label><span class="required">*</span>类型</label>
								<house:xtdm id="type" dictCode="WKQLTFEETYPE" style="width:160px;" value="${projectManQltFeeWithHoldRule.type}" /> 
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>起始质保金余额</label>
								<input type="text" id="qltFeeFrom" name="qltFeeFrom" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectManQltFeeWithHoldRule.qltFeeFrom}" maxlength="16"/>
							</li>
							
							<li class="form-validate">
								<label><span class="required">*</span>截至质保金余额</label>
								<input type="text" id="qltFeeTo" name="qltFeeTo" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectManQltFeeWithHoldRule.qltFeeTo}" maxlength="16"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>起始提成金额</label>
								<input type="text" id="commiAmountFrom" name="commiAmountFrom" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectManQltFeeWithHoldRule.commiAmountFrom}" maxlength="16" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>截至提成金额</label>
								<input type="text" id="commiAmountTo" name="commiAmountTo" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectManQltFeeWithHoldRule.commiAmountTo}" maxlength="16"/>
							</li>
							<li id="isSupvr_li" class="form-validate">
								<label><span class="required">*</span>项目经理类型</label>
								<house:xtdm id="isSupvr" dictCode="PRJMANTYPE" style="width:160px;" value="${projectManQltFeeWithHoldRule.isSupvr}"/>
							</li>
							<li id="qltFeeLimitAmount_li" class="form-validate">
								<label><span class="required">*</span>质保金上限金额</label>
								<input type="text" id="qltFeeLimitAmount" name="qltFeeLimitAmount" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectManQltFeeWithHoldRule.qltFeeLimitAmount}" maxlength="16" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>扣减额</label>
								<input type="text" id="amount" name="amount" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectManQltFeeWithHoldRule.amount}" maxlength="16" />
							</li>
							<c:if test="${projectManQltFeeWithHoldRule.m_umState!='A'}">
								<li>
									<label>过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${projectManQltFeeWithHoldRule.expired}" 
										onclick="checkExpired(this)" ${projectManQltFeeWithHoldRule.expired=="T"?"checked":""}/>
								</li>
							</c:if>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var url = "";
		$(function() {
			switch ("${projectManQltFeeWithHoldRule.m_umState}") {
				case "M":
					url = "${ctx}/admin/projectManQltFeeWithHoldRule/doUpdate";
					break;
				case "V":
					$("#saveBtn").remove();
					disabledForm();
					break;
				default:
					url = "${ctx}/admin/projectManQltFeeWithHoldRule/doSave";
					break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					type:{ 
						validators: {  
							notEmpty: {  
								message: "类型不允许为空"  
							},
						}  
					},
					qltFeeFrom:{ 
						validators: {  
							notEmpty: {  
								message: "起始质保金余额不允许为空"  
							},
							numeric: {
					            message: "起始质保金余额只能是数字" 
					        },
						}  
					},
					qltFeeTo:{ 
						validators: {  
							notEmpty: {  
								message: "截至质保金余额不允许为空"  
							},
							numeric: {
					            message: "截至质保金余额只能是数字" 
					        },
						}  
					},
					commiAmountFrom:{ 
						validators: {  
							notEmpty: {  
								message: "起始提成金额不允许为空"  
							},
							numeric: {
					            message: "起始提成金额只能是数字" 
					        },
						}  
					},
					commiAmountTo:{ 
						validators: {  
							notEmpty: {  
								message: "截至提成金额不允许为空"  
							},
							numeric: {
					            message: "截至提成金额只能是数字" 
					        },
						}  
					},
					amount:{ 
						validators: {  
							notEmpty: {  
								message: "扣减金额不允许为空"  
							},
							numeric: {
					            message: "扣减金额只能是数字" 
					        },
						},
					},
					qltFeeLimitAmount:{ 
						validators: {  
							notEmpty: {  
								message: "质保金上限不允许为空"  
							},
							numeric: {
					            message: "质保金上限只能是数字" 
					        },
						},	
					},
					
				}
			});
			$("#closeBtn").on("click", function () {
				closeWin(false);
			});
		});
		$("#saveBtn").on("click", function () {
			if ("V" == "${projectManQltFeeWithHoldRule.m_umState}") return;
			
			if ($.trim($("#type").val())=="1"){	
				if($.trim($("#qltFeeLimitAmount").val())==""){
					art.dialog({
						content: "请填写质保金上限金额",
						width: 200
					});
					return;
				}
				if($.trim($("#isSupvr").val())==""){
					art.dialog({
						content: "请填写项目经理类型",
						width: 200
					});
					return;
				}	
			}
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
