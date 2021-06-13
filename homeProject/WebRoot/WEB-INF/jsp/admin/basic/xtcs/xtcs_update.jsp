<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>系统参数--编辑</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>

<script type="text/javascript">
	function save() {
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url : "${ctx}/admin/xtcs/doUpdate",
			type : "get",
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
						//time : 1000,
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

	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				id : {
					validators : {
						notEmpty : {
							message : '参数代码不能为空'
						}
					}
				},
				qz : {
					validators : {
						notEmpty : {
							message : '取值不能为空'
						}
					}
				},
				sm : {
					validators : {
						notEmpty : {
							message : '说明不能为空'
						}
					}
				},
				smE : {
					validators : {
						notEmpty : {
							message : '英文说明不能为空'
						}
					}
				},
			},
		});
	});
</script>

</head>

<body>
		<div class="body-box-form">
			<div class="panel panel-system">
				<!--panelBar-->
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="submit" class="btn btn-system" id="saveBut"
							onclick="save()">保存</button>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>

			<div class="panel panel-info">
				<div class="panel-body">
					<form method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<ul class="ul-form">
						<div class="validate-group row"> 
								<li class="form-validate">
									<label>参数代码 </label> 
									<input type="text" " id="id" name="id" value="${xtcs.id}" readonly="readonly" />
								</li>
								<li class="form-validate">
									<label>说明</label> 
									<input type="text" id="sm" name="sm" value="${xtcs.sm }" />
								</li>
								<li class="form-validate" >
									<label>英文说明</label>
									<input type="text" id="smE" name="smE" value="${xtcs.smE }" />
								</li>
							</div>
							<div class="validate-group row" style="padding-bottom: 5px;">	
								<li class="form-validate">
									<label class="control-textarea">取值</label>
									<textarea id="qz" name="qz" rows="5">${xtcs.qz}</textarea>
								</li>	
							</div>	
						</ul>
					</form>
				</div>
			</div>
		</div>
</body>
</html>
