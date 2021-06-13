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
	<style type="text/css">
		.form-search .ul-form li .sort-label {
			width: 80px;
		}
	</style>
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
					<input type="hidden" id="expired" name="expired" value="${company.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-xs-5">
								<li class="form-validate">
									<label><span class="required">*</span>公司编号</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${company.code}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>公司名</label>
									<input type="text" id="cmpnyName" name="cmpnyName" style="width:160px;" value="${company.cmpnyName}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>公司全称</label>
									<input type="text" id="desc1" name="desc1" style="width:160px;" value="${company.desc1}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>公司简称</label>
									<input type="text" id="desc2" name="desc2" style="width:160px;" value="${company.desc2}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>公司地址</label>
									<input type="text" id="cmpnyAddress" name="cmpnyAddress" style="width:160px;" value="${company.cmpnyAddress}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>公司类型</label>
									<house:xtdm id="type" dictCode="CMPTYP" style="width:160px;" value="${company.type}"/>
								</li>
								<li class="form-validate">
									<label>公司电话</label>
									<input type="text" id="cmpnyPhone" name="cmpnyPhone" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^*\-\d]/g,'');" 
										value="${company.cmpnyPhone}"/>
								</li>
								<li class="form-validate">
									<label>公司传真</label>
									<input type="text" id="cmpnyFax" name="cmpnyFax" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^*\-\d]/g,'');" 
										value="${company.cmpnyFax}"/>
								</li>
								<li class="form-validate">
									<label>公司网站</label>
									<input type="text" id="cmpnyURL" name="cmpnyURL" style="width:160px;" value="${company.cmpnyURL}"/>
								</li>
							</div>
							<div class="col-xs-7">
								<li class="form-validate">
									<label class="sort-label">软装电话</label>
									<input type="text" id="softPhone" name="softPhone" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^*\-\d]/g,'');" 
										value="${company.softPhone}"/>
								</li>
								<li style="max-height: 120px;">
									<label class="control-textarea sort-label" style="top: -50px;">预算打印基础报价说明</label>
									<textarea id="pricRemark" name="pricRemark" 
										style="height: 80px;width: 290px;">${company.pricRemark}</textarea>
								</li>
								<li style="max-height: 120px;">
									<label class="control-textarea sort-label" style="top: -50px;">预算打印基础物料说明</label>
									<textarea id="itemRemark" name="itemRemark" 
										style="height: 80px;width: 290px;">${company.itemRemark}</textarea>
								</li>
								<li style="max-height: 120px;">
									<label class="control-textarea sort-label" style="top: -50px;">预算打印服务性产品说明</label>
									<textarea id="serviceRem" name="serviceRem" 
										style="height: 80px;width: 290px;">${company.serviceRem}</textarea>
								</li>
								<li id="expired_li">
									<label class="sort-label">过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${company.expired}" 
										onclick="checkExpired(this)" ${company.expired=="T"?"checked":""}/>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
	<script type="text/javascript" defer>
		var url = "";
		$(function() {
			switch ("${company.m_umState}") {
				case "M":
					$("#code").prop("readonly", "true");
					url = "${ctx}/admin/company/doUpdate";
					break;
				case "V":
					$("#saveBtn").remove();
					disabledForm();
					break;
				default:
					url = "${ctx}/admin/company/doSave";
					$("#expired_li").remove();
					break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					code:{ 
						validators: {  
							notEmpty: {  
								message: "编号不允许为空"  
							},
						}  
					},
					cmpnyName:{ 
						validators: {  
							notEmpty: {  
								message: "公司名称不允许为空"  
							},
						}  
					},
					desc1:{ 
						validators: {  
							notEmpty: {  
								message: "公司全称不允许为空"  
							},
						}  
					},
					desc2:{ 
						validators: {  
							notEmpty: {  
								message: "公司简称不允许为空"  
							},
						}  
					},
					cmpnyAddress:{ 
						validators: {  
							notEmpty: {  
								message: "公司地址不允许为空"  
							},
						}  
					},
					type:{ 
						validators: {  
							notEmpty: {  
								message: "类型不允许为空"  
							},
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
							time: 1000,
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
