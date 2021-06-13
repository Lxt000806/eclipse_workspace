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
	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
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
								<label><span class="required">*</span>基础项目</label>
								<input type="text" id="baseItemCode" name="baseItemCode" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>数量</label>
								<input type="text" id="qty" name="qty" style="width:160px;" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="${baseItemTempDetail.qty}"/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
	<script type="text/javascript" defer>
		var m_umState = "${baseItemTempDetail.m_umState}", baseItemDescr = "${baseItemTempDetail.baseItemDescr}", selectRows = [];
		function refreshCode(data) {
			baseItemDescr = data.descr;
			$("#page_form").data("bootstrapValidator")
				.updateStatus("openComponent_baseItem_baseItemCode", "NOT_VALIDATED", null)
				.validateField("openComponent_baseItem_baseItemCode");
		}
		$(function() {
			var dialog_id = "";
			switch (m_umState) {
			case "A":
			case "Q":
				dialog_id = "detailSave";
				$("#baseItemCode").openComponent_baseItem({
					callBack:refreshCode
				});
				break;
			case "M":
				dialog_id = "detailUpdate";
				$("#baseItemCode").openComponent_baseItem({
					showValue:"${baseItemTempDetail.baseItemCode}",
					showLabel: "${baseItemTempDetail.baseItemDescr}",
					callBack:refreshCode
				});
				break;
			default:
				$("#saveBtn").remove();
				dialog_id = "detailView";
				$("#baseItemCode").openComponent_baseItem({
					showValue:"${baseItemTempDetail.baseItemCode}",
					showLabel: "${baseItemTempDetail.baseItemDescr}",
					callBack:refreshCode
				});
				disabledForm();
				break;
			}
			parent.$("#iframe_"+dialog_id).attr("height","98%"); //消灭掉无用的滑动条
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					openComponent_baseItem_baseItemCode:{ 
						validators: {  
							notEmpty: {  
								message: "基装项目不允许为空"
							},
						}  
					},
					qty:{ 
						validators: {  
							notEmpty: {
								message: "数量不允许为空"
							},
						}  
					}
				}
			});
			$("#qty").on("blur",function () {
				if ($(this).val()=="") {
					$(this).val(0);
					$("#page_form").data("bootstrapValidator")
						.updateStatus("qty", "NOT_VALIDATED", null)
						.validateField("qty");
				}
			});
			$("#qty").trigger("blur");
			$("#saveBtn").on("click", function () {
				if ("V" == m_umState) return;
				$("#page_form").bootstrapValidator("validate");/* 提交验证 */
				if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */
				var datas = $("#page_form").jsonForm();
				datas.baseItemDescr = baseItemDescr;
				selectRows.push(datas);
				Global.Dialog.returnData = selectRows;
				if ("Q" == m_umState) {
					clearFormTable();
					$("#qty").trigger("blur");
				} else {
					closeWin();
				}
			});
			$("#closeBtn").on("click", function () {
				closeWin();
			})
		});
	</script>
</html>
