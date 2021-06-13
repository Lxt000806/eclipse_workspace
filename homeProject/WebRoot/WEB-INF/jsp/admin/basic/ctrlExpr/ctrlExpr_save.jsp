<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>分段发包--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	.panel-info {
		margin: 0px;
	}
</style>
<script type="text/javascript">
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
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
				url : "${ctx}/admin/ctrlExpr/doSave",
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
						<input type="hidden" id="custType"
								name="custType" style="width:160px;" 
								value="${ctrlExpr.custType}" />
						<div class="validate-group row">
							<li class="form-validate"><label>开始日期从</label> <input type="text" id="beginDate"
								name="beginDate" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${ctrlExpr.beginDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate"><label>结束日期</label> <input type="text" id="endDate"
								name="endDate" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${ctrlExpr.endDate}' pattern='yyyy-MM-dd'/>" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>发包价公式</label> <input type="text" id="ctrlExpr"
								name="ctrlExpr" style="width:500px;" required
								data-bv-notempty-message="发包价公式不能为空"
								value="${ctrlExpr.ctrlExpr}" />
							</li>
						</div>
						<div class="validate-group row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -35px;">发包公式说明</label>
								<textarea id="ctrlExprRemarks" name="ctrlExprRemarks" style="height: 50px;width: 500px;">${ctrlExpr.ctrlExprRemarks}</textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>指定发包价公式</label> <input
								type="text" id="setCtrlExpr" name="setCtrlExpr"
								style="width:500px;" required
								data-bv-notempty-message="指定发包价公式不能为空"
								value="${ctrlExpr.setCtrlExpr}" />
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
