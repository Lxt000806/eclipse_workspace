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
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		.form-search .ul-form li label {
			width: 140px;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn"
						onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="doClose()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action=""
					method="post" target="targetFrame">
					<house:token></house:token>
					<!-- 按钮只能点一次 -->
					<input type="hidden" id="lastupdatedby" name="lastupdatedby"
						value="${sessionScope.USER_CONTEXT_KEY.czybh}" />
					<input type="hidden" id="expired" name="expired" value="${intSplPerfPer.expired}"/>
					<input type="hidden" id="pk" name="pk" value="${intSplPerfPer.pk}"/>
					<ul class="ul-form">
						<li class="form-validate"><label>供应商</label> <input type="text" id="supplCode"
							name="supplCode" style="width:160px;"  />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>业绩比例</label> <input type="text" id="per"
							name="per" style="width:160px;"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
							value="${intSplPerfPer.per}" />
						</li>
						<li>
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${intSplPerfPer.expired}" 
								onclick="checkExpired(this)" ${intSplPerfPer.expired=="T"?"checked":""}/>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" defer>
	$(function() {
		$("#supplCode").openComponent_supplier({showValue:"${intSplPerfPer.supplCode}",showLabel:"${intSplPerfPer.supplDescr}"});
		$("#page_form").bootstrapValidator({
			message : "请输入完整的信息",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields : {
				openComponent_supplier_supplCode : {
					validators : {
						notEmpty : {
							message : "供应商编号不允许为空"
						}
					}
				},
				per : {
					validators : {
						notEmpty : {
							message : "业绩比例不允许为空"
						}
					}
				},
			}
		});
		if("${intSplPerfPer.m_umState}"=="V"){
			disabledForm();
			$("#saveBtn").attr("disabled",true);
		}
	});

	function doClose() {
		closeWin();
	}

	function doSave() {
		$("#page_form").bootstrapValidator("validate");/* 提交验证 */
		if (!$("#page_form").data("bootstrapValidator").isValid())
			return; /* 验证失败return */

		var datas = $("#page_form").serialize();
		$.ajax({
			url : "${ctx}/admin/intSplPerfPer/doUpdate",
			type : "post",
			data : datas,
			dataType : "json",
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : "保存数据出错"
				});
			},
			success : function(obj) {
				if (obj.rs) {
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				} else {
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content : obj.msg,
						width : 200
					});
				}
			}
		});
	}
</script>
</html>
