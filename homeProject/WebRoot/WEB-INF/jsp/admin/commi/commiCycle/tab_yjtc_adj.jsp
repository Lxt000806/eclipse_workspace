<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>业绩提成--调整金额</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#saveBtn").on("click", function() {
			var bootstrapValidator = $("#dataForm").data('bootstrapValidator');
	        bootstrapValidator.validate();
	        if (!bootstrapValidator.isValid()) return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : "${ctx}/admin/commiCustStakeholder/doAdjustAmount",
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
	$(function() {
		$("#dataForm").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
            	adjustAmount: {
                    validators: {
                        notEmpty: {message: '调整金额不能为空'}
                    }
                },
            }
        });
	});
	
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
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
					<input type="hidden" name="jsonString" value="" /> <input
					<input type="hidden" name="pk" value="${commiCustStakeholder.pk }" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label>调整金额</label>
								<input type="number" id="adjustAmount" name="adjustAmount" value="${commiCustStakeholder.adjustAmount }"
									step="0.01"/>
							</li>
						</div>
						<div class="validate-group row" style="height:150px">
							<li class="form-validate"><label class="control-textarea">调整备注</label>
								<textarea id="adjustRemarks" name="adjustRemarks"
									style="overflow-y:scroll; overflow-x:hidden; height:100px; " /></textarea>
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
