<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>配送明细--录入配送费</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		if ("${itemAppSend.sendType}"=="S"){
		   $('#manySendRemarks_show').show();
		   $('#transFeeAdj_show').show();
		};
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				transFee : {
					validators : {
						notEmpty : {
							message : '请填写车费'
						},
						numeric: {
							message: '车费只能输入数字'
						}
					}
				},
				carryFee : {
					validators : {
						notEmpty : {
							message : '请填写搬运费'
						},
						numeric: {
							message: '搬运费只能输入数字'
						}
					}
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});

	$(function() {
		$("#saveBtn").on("click", function() {
			if ("${itemAppSend.sendType}"=="S"&& $('#transFeeAdj').val()==""){
			 	art.dialog({content: "车费补贴不能为空！",width: 200});
				return false;
			};
			$("#dataForm").bootstrapValidator('validate');
			if (!$("#dataForm").data('bootstrapValidator').isValid())
				return;
			var datas = $("#dataForm").jsonForm();
			Global.Dialog.returnData = datas;
			closeWin();
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
							<li class="form-validate"><label>送货单号</label> <input
								type="text" id="no" name="no" style="width:160px;"
								value="${itemAppSend.no }" readonly />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>车费</label> <input
								type="text" id="transFee" name="transFee" style="width:160px;"
								value="${itemAppSend.transFee}" /><span
								style="position: absolute;left:290px;width: 30px;top:3px;">元</span>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>搬运费</label> <input
								type="text" id="carryFee" name="carryFee" style="width:160px;"
								value="${itemAppSend.carryFee}" /><span
								style="position: absolute;left:290px;width: 30px;top:3px;">元</span>
							</li>
						</div>
						<div class="validate-group row" id="transFeeAdj_show" hidden="true">
							<li class="form-validate"><label>车费补贴</label> <input
								type="number" id="transFeeAdj" name="transFeeAdj" style="width:160px;"
								value="${itemAppSend.transFeeAdj}"  step="0.01"/><span
								style="position: absolute;left:290px;width: 30px;top:3px;">元</span>
							</li>
						</div>
						<div class="validate-group row" id="manySendRemarks_show" hidden="true">
							<li class="form-validate">
							<label class="control-textarea">多次配送说明</label>
							 <textarea id="manySendRemarks"
									name="manySendRemarks" style="width:160px; height:55px;">${itemAppSend.manySendRemarks}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
