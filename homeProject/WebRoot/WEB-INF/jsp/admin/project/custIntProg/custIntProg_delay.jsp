<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>集成进度管理--制单拖期录入</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				delayRemarks : {
					validators : {
						notEmpty : {
							message : '超期说明不能为空'
						}
					}
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});

	$(function() {
		$("#saveBtn").on("click", function() {
			$("#dataForm").bootstrapValidator('validate');
			if (!$("#dataForm").data('bootstrapValidator').isValid())
				return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : "${ctx}/admin/custIntProg/doDelayAdd",
				type : "post",
				data : datas,
				dataType : "json",
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : "保存数据出错~"
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
		});
	});
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label>客户编号</label> <input
								type="text" id="custCode" name="custCode" style="width:160px;"
								value="${custIntProg.custcode }" readonly /></li>
							<li class="form-validate"><label>集成下单日期</label> <input
								type="text" id="intAppDate" name="intAppDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.intappdate}"
								disabled="true" /></li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>楼盘</label> <input
								type="text" id="address" name="address" style="width:160px;"
								value="${custIntProg.address}" readonly /></li>
							<li class="form-validate"><label>集成测量申报日期</label> <input
								type="text" id="measureAppDate" name="measureAppDate"
								class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.measureappdate==''?'':custIntProg.measureappdate.substring(0,10)}"
								disabled="true" /></li>
						</div>

						<div class="validate-group row">
							<li><label>集成制单拖期</label> <input type="text"
								id="appDelayDdays" name="appDelayDdays" style="width:160px;"
								value="${custIntProg.appdelayddays}" readonly />
							</li>
							<li class="form-validate"><label>橱柜下单日期</label> <input
								type="text" id="cupAppDate" name="cupAppDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.cupappdate}"
								disabled="true" /></li>
						</div>
						<div class="validate-group row">
							<li><label>橱柜制单拖期</label> <input type="text"
								id="cupAppDelayDdays" name="cupAppDelayDdays"
								style="width:160px;" value="${custIntProg.cupappdelayddays}"
								readonly />
							</li>
							<li class="form-validate"><label>橱柜测量申报日期</label> <input
								type="text" id="measureCupAppDate" name="measureCupAppDate"
								class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.measurecupappdate==''?'':custIntProg.measurecupappdate.substring(0,10)}"
								disabled="true" /></li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label class="control-textarea"><span
									class="required">*</span>超期说明</label> <textarea id="delayRemarks"
									name="delayRemarks"
									style="overflow-y:scroll; overflow-x:hidden; height:75px;">${custIntProg.delayremarks }</textarea>
							</li>
							<li class="form-validate"><label class="control-textarea">  </label>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
