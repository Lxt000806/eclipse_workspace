<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_workType2.js?v=${v}" type="text/javascript"></script>
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
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="expired" name="expired" value="${workingDate.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label for="date">日期</label>
								<input type="text" id="date" name="date" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${workingDate.date}' pattern='yyyy-MM-dd HH:mm:ss'/>"
									disabled/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>节假日类型</label>
								<house:xtdm id="holiType" dictCode="HoliType" style="width:160px;" 
									value="${workingDate.holiType}"/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		var url = "";
		$(function() {
			switch ("${workingDate.m_umState}") {
				case "M":
					url = "${ctx}/admin/workingDate/doUpdate";
					$("#custType").prop("disabled", true);
					break;
				default:
					$("#saveBtn").remove();
					disabledForm();
					break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					holiType: { 
						validators: {  
							notEmpty: {  
								message: "节假日类型不允许为空"  
							},
						}  
					},
				}
			});

			$("#saveBtn").on("click", function () {
				if ("V" == "${workingDate.m_umState}") return;
				$("#page_form").data("bootstrapValidator").validate();
				if(!$("#page_form").data("bootstrapValidator").isValid()) return;	
				var datas = $("#page_form").jsonForm();
				saveAjax(datas);
			});
			$("#closeBtn").on("click", function () {
				closeWin(false);
			});
		});

		function saveAjax(datas) {
			$.ajax({
				url:url,
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				},
				success: function(obj){
					if(obj.rs){
						art.dialog({
							content: obj.msg,
							time: 700,
							beforeunload: function () {
								closeWin();
							}
						});
					}else{
						$("#_form_token_uniq_id").val(obj.token.token);
						art.dialog({
							content: obj.msg,
							width: 200
						});
					}
				}
			});
		}
	</script>
</body>
</html>
