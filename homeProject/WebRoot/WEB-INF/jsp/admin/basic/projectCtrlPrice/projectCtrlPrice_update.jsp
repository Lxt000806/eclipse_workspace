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
		.form-search .ul-form li label {
			width: 140px;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="doClose()">
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
					<input type="hidden" id="expired" name="expired" value="${projectCtrlPrice.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label><span class="required">*</span>编码</label>
								<input type="text" id="pK" name="pK" style="width:160px;"
									placeholder="保存自动生成" value="${projectCtrlPrice.pK}" readonly="readonly"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>客户类型</label> 
								<house:dict id="custType" dictCode="" sql="select rtrim(Code)+' '+Desc1 fd,Code from tCustType where Expired='F' order by Code" 
									sqlValueKey="Code" sqlLableKey="fd" value="${projectCtrlPrice.custType}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>面积从</label>
								<input type="text" id="fromArea" name="fromArea" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectCtrlPrice.fromArea}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>面积到</label>
								<input type="text" id="toArea" name="toArea" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectCtrlPrice.toArea}" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>价格</label>
								<input type="text" id="price" name="price" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectCtrlPrice.price}" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>管理费</label>
								<input type="text" id="manageFee" name="manageFee" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectCtrlPrice.manageFee}" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>基础考核定额</label>
								<input type="text" id="baseQuotaPrice" name="baseQuotaPrice" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectCtrlPrice.baseQuotaPrice}" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>保底面积</label>
								<input type="text" id="minArea" name="minArea" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${projectCtrlPrice.minArea}" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${projectCtrlPrice.expired}" 
									onclick="checkExpired(this)" ${projectCtrlPrice.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" defer>
var dialog_id;
$(function() {
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			custType:{ 
				validators: {  
					notEmpty: {  
						message: "请选择客户类型"  
					},
				}  
			},
			fromArea:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					},
				}  
			},
			toArea:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}
				}  
			},
			price:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}
				}  
			},
		}
	});

	if ("V" == "${projectCtrlPrice.m_umState}") {
		$("#saveBtn").hide();
		dialog_id = "view";
		disabledForm();
	} else {
		dialog_id = "update";
	}
	
	parent.$("#iframe_"+dialog_id).attr("height","98%");

});

function doClose(){
	closeWin();
}

function doSave(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
		
	if ("V" == "${projectCtrlPrice.m_umState}") return;	
	var datas = $("#page_form").jsonForm();

	saveAjax(datas);
}

function saveAjax(datas) {
	$.ajax({
		url:"${ctx}/admin/projectCtrlPrice/doUpdate",
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
