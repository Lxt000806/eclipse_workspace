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
					<ul class="ul-form">
						<li class="form-validate"><label><span
								class="required">*</span>客户结算时间从</label> <input
							type="text" id="custCheckDateFrom" name="custCheckDateFrom"
							class="i-date"
							onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${itemCommiRule.custCheckDateFrom }'  pattern='yyyy-MM-dd'/>" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>客户结算时间到</label> <input
							type="text" id="custCheckDateTo" name="custCheckDateTo"
							class="i-date"
							onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${itemCommiRule.custCheckDateTo }'  pattern='yyyy-MM-dd'/>" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>非独立销售</label> <house:xtdm id="isAddAllInfo"
								dictCode="YESNO" style="width:160px;"
								value="${itemCommiRule.isAddAllInfo}" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>主材提成比例</label> <input type="text"
							id="mainCommiPer" name="mainCommiPer" style="width:160px;"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
							value="0" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>服务性产品提成比例</label> <input type="text"
							id="servCommiPer" name="servCommiPer" style="width:160px;"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
							value="0" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>软装提成比例</label> <input type="text"
							id="softCommiPer" name="softCommiPer" style="width:160px;"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
							value="0" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>窗帘提成比例</label> <input type="text"
							id="curtainCommiPer" name="curtainCommiPer" style="width:160px;"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
							value="0" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>家具提成比例</label> <input type="text"
							id="furnitureCommiPer" name="furnitureCommiPer"
							style="width:160px;"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
							value="0" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>营销费用比例</label> <input type="text"
							id="marketFundPer" name="marketFundPer" style="width:160px;"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
							value="0" />
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" defer>
	$(function() {
		$("#page_form").bootstrapValidator({
			message : "请输入完整的信息",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields : {
				custCheckDateFrom : {
					validators : {
						notEmpty : {
							message : "客户结算时间从不允许为空"
						},
						callback: { 
							message: "客户结算开始时间不能大于结束时间",
							callback: function(value, validator, $field) {
								return checkDate();
							}
						},
					}
				},
				custCheckDateTo : {
					validators : {
						notEmpty : {
							message : "客户结算时间到不允许为空"
						},
						callback: { 
							message: "客户结算结束时间不能小于结束时间",
							callback: function(value, validator, $field) {
								return checkDate();
							}
						},
					}
				},
				isAddAllInfo : {
					validators : {
						notEmpty : {
							message : "非独立销售不允许为空"
						}
					}
				},
				mainCommiPer : {
					validators : {
						notEmpty : {
							message : "主材提成比例不允许为空"
						}
					}
				},
				servCommiPer : {
					validators : {
						notEmpty : {
							message : "服务性产品提成比例不允许为空"
						}
					}
				},
				curtainCommiPer : {
					validators : {
						notEmpty : {
							message : "窗帘提成比例不允许为空"
						}
					}
				},
				softCommiPer : {
					validators : {
						notEmpty : {
							message : "软装提成比例不允许为空"
						}
					}
				},
				marketFundPer : {
					validators : {
						notEmpty : {
							message : "营销费用比例不允许为空"
						}
					}
				},
				furnitureCommiPer : {
					validators : {
						notEmpty : {
							message : "家具提成比例不允许为空"
						}
					}
				},
			}
		});

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
			url : "${ctx}/admin/itemCommiRule/doSave",
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
	function checkDate(){
		var custCheckDateFrom=$("#custCheckDateFrom").val();
		var custCheckDateTo=$("#custCheckDateTo").val();
		if(custCheckDateFrom!="" && custCheckDateTo !="" && custCheckDateFrom>custCheckDateTo){
			return false;
		}
		return true;
	}
</script>
</html>
