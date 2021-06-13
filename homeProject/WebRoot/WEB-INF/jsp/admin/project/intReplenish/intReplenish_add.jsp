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
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
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
							<li>
								<label>单号</label>
								<input type="text" id="no" name="no" style="width:160px;"
									placeholder="保存自动生成" readonly/>
							</li>
							<li class="form-validate">
								<label for="custCode"><span class="required">*</span>楼盘</label>
								<input type="text" id="custCode" name="custCode">
							</li>
							<li class="form-validate">
								<label for="isCupboard"><span class="required">*</span>是否橱柜</label>
								<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;"/>
							</li>
							<li class="form-validate">
								<label for="source"><span class="required">*</span>补货来源</label>
								<house:xtdm id="source" dictCode="IntRepSource" style="width:160px;"/>
							</li>
							<li class="form-validate">
								<label for="resPart">责任人</label>
								<house:xtdm id="resPart" dictCode="IntRepResPart" style="width:160px;"/>
							</li>
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -60px;">说明</label>
								<textarea id="remarks" name="remarks" 
									style="height: 80px;width: 160px;">${intReplenish.remarks}</textarea>
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
			$("#custCode").openComponent_customer({
				condition:{
					status:"4,5",
					// endCode:"5",//不限制了 20191209
					disabledEle:"status_NAME"
				},
				callBack:validateRefresh_local
			});
			// var dialog_id = "${intReplenish.m_umState}"!=""?"update":"save";
			// parent.$("#iframe_"+dialog_id).attr("height","98%"); //消灭掉无用的滑动条
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					isCupboard:{ 
						validators: {  
							notEmpty: {  
								message: "是否橱柜不允许为空"  
							},
						}  
					},
					source:{ 
						validators: {  
							notEmpty: {  
								message: "补货来源不允许为空"  
							},
						}  
					},
					openComponent_customer_custCode:{
						validators: {
							notEmpty: {
								message:"楼盘不允许为空"
							}
						}
					},
				}
			});
			$("#saveBtn").on("click", function () {
				$("#page_form").bootstrapValidator("validate");/* 提交验证 */
				if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
				var datas = $("#page_form").jsonForm();
				saveAjax(datas);
			});
			$("#closeBtn").on("click", function () {
				closeWin();
			})
		});
		function validateRefresh_local() {
			$("#page_form").data("bootstrapValidator")  
				.updateStatus("openComponent_customer_custCode", "NOT_VALIDATED", null)
				.validateField("openComponent_customer_custCode");
		}
		function saveAjax(datas) {
			$.ajax({
				url:"${ctx}/admin/intReplenish/doAdd",
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
</html>
