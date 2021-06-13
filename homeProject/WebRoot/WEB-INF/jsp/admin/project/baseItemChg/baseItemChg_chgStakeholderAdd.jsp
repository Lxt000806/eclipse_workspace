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
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<!-- <button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button> -->
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
								<label for="empcode">员工</label>
								<input type="text" id="empcode" name="empcode">
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
	<script type="text/javascript" defer>
		$(function() {
			$("#empcode").openComponent_employee({
				showValue: "${baseItemChg.empCode}",
				showLabel: "${baseItemChg.empDescr}",
				callBack:validateRefresh_local
			});
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {  
					openComponent_employee_empcode:{  
						validators: {  
							notEmpty: {  
								message: "员工不允许为空"  
							},
							callback: {
								message: "员工重复",
								callback: function(data){
									if ("${keys}" == "") {
										return true;
									}
									var keys = $.trim("${keys}").split(",");// String 转 array
									var arr = data.split("|");
									var code = arr[0];
									for ( var i in keys) {
										if (keys[i] == code) {
											return false;
										}
									}
									return true;
								}
							}
						}  
					},
				}
			});
			var dialog_id = "${baseItemChg.m_umState}"!=""?"empSave":"empEdit";
			parent.$("#iframe_"+dialog_id).attr("height","98%"); //消灭掉无用的滑动条
			$("#saveBtn").on("click", function () {
				save();
			});
			$("#closeBtn").on("click", function () {
				closeWin(false);
			});
		});
		function validateRefresh_local() {
			$("#page_form").data("bootstrapValidator")  
				.updateStatus("openComponent_employee_empcode", "NOT_VALIDATED", null)
				.validateField("openComponent_employee_empcode");
			setTimeout(function(){
				save();
			},50);
		}
		function save() {
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var datas = $("#page_form").jsonForm();
			var arr2 = datas.openComponent_employee_empcode.split("|");
			datas.empname = arr2[arr2.length-1];
			var selectRows = [];
			selectRows.push(datas);
			Global.Dialog.returnData = selectRows;
			closeWin();
		}
	</script>
</html>
