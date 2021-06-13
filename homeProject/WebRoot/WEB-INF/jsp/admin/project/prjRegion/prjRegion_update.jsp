<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>工程大区管理--编辑</title>
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
				url : "${ctx}/admin/prjRegion/doUpdate",
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
							 <li class="form-validate"><label><span
									class="required">*</span>编码</label> <input type="text"
								id="code" name="code" style="width:160px;" required
								data-bv-notempty-message="编码不能为空"
								value="${prjRegion.code}" maxlength="10"/>
								<input type="hidden"
								id="oldCode" name="oldCode" style="width:160px;" 
								value="${prjRegion.code}" />
							</li>
							<li class="form-validate"><label><span
									class="required">*</span>大区名称</label> <input type="text"
								id="descr" name="descr" style="width:160px;" required
								data-bv-notempty-message="大区名称不能为空"
								value="${prjRegion.descr}" />
							</li>
						</div>
							<div class="validate-group row">
							<li class="form-validate"><label>过期</label> <input type="checkbox" id="expired" name="expired" value="${prjRegion.expired }" 
								${prjRegion.expired!='F' ?'checked':'' } 
								onclick="checkExpired(this)">
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
