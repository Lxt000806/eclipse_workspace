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
	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_baseCheckItem.js?v=${v}" type="text/javascript"></script>
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
					<input type="hidden" id="pk" name="pk" value="${baseItemToCheckItem.pk}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>基础项目</label>
								<input type="text" id="baseItemCode" name="baseItemCode" style="width:160px;" autofocus/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>基础结算项目</label>
								<input type="text" id="baseCheckItemCode" name="baseCheckItemCode" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>映射算法</label> 
								<house:xtdm id="calType" dictCode="CKITEMCALTYPE" style="width:160px;" value="${baseItemToCheckItem.calType}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>数量</label>
								<input type="text" id="qty" name="qty" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="${baseItemToCheckItem.qty}" readonly="readonly" />
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
var oldBaseItemCode = "${baseItemToCheckItem.baseItemCode}";
var oldBaseCheckItemCode = "${baseItemToCheckItem.baseCheckItemCode}";
$(function() {
	if ("3" == $.trim("${baseItemToCheckItem.calType}") || "2" == $.trim("${baseItemToCheckItem.calType}")) {
		$("#qty").prop("readonly", false);
	}

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			openComponent_baseItem_baseItemCode:{ 
				validators: {  
					notEmpty: {  
						message: "基础项目不允许为空"  
					},
					/*remote: {
			            message: "该基础项目对应基础结算项目已重复",
			            url: "${ctx}/admin/baseItemToCheckItem/checkCode",
			            data: function() {
						    return {
						    	openComponent_baseCheckItem_baseCheckItemCode:$("#openComponent_baseCheckItem_baseCheckItemCode").val(),
						    	oldBaseItemCode:oldBaseItemCode,
						    	oldBaseCheckItemCode:oldBaseCheckItemCode,
						    };
						},
			            delay:1000, 	//每输入一个字符，就发ajax请求，服务器压力还是太大，
			            type:"post",	// 设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
			        },*/
				}  
			},
			openComponent_baseCheckItem_baseCheckItemCode:{ 
				validators: {  
					notEmpty: {  
						message: "基础结算项目不允许为空"  
					},
					/*remote: {
			            message: "该基础结算项目对应基础项目已重复",
			            url: "${ctx}/admin/baseItemToCheckItem/checkCode",
			            data: function() {
						    return {
						    	openComponent_baseItem_baseItemCode:$("#openComponent_baseItem_baseItemCode").val(),
						    	oldBaseItemCode:oldBaseItemCode,
						    	oldBaseCheckItemCode:oldBaseCheckItemCode,
						    };
						},
			            delay:1000, 	
			            type:"post",	
			            crossDomain: true,
			        }*/
				}  
			},
			calType:{ 
				validators: {  
					notEmpty: {  
						message: "映射算法不允许为空"  
					}
				}  
			},
			qty:{ 
				validators: {  
					notEmpty: {  
						message: "数量不允许为空"  
					}
				}  
			},
		}
	});

	$("#baseItemCode").openComponent_baseItem({
		callBack:validateRefresh_baseItem,
		showValue:"${baseItemToCheckItem.baseItemCode}",
		showLabel:"${baseItemToCheckItem.baseItemDescr}",
	});
	$("#baseCheckItemCode").openComponent_baseCheckItem({
		callBack:validateRefresh_baseCheckItem,
		showValue:"${baseItemToCheckItem.baseCheckItemCode}",
		showLabel:"${baseItemToCheckItem.baseCheckItemDescr}",
	});

	if ("V" == "${baseItemToCheckItem.m_umState}") {
		$("#saveBtn").hide();
		dialog_id = "view";
		$("#page_form input[type='text']").prop("readonly",true);
		$("#calType").prop("disabled",true);
		$("#openComponent_baseItem_baseItemCode").next().prop("disabled",true);
		$("#openComponent_baseCheckItem_baseCheckItemCode").next().prop("disabled",true);
	} else {
		dialog_id = "update";
		$("#page_form").bootstrapValidator("validate");
	}
	
	parent.$("#iframe_"+dialog_id).attr("height","98%");

	$("#calType").on("change",function(){
		var calType = $(this).val();
		if ("2"== $.trim(calType)) {
			$("#qty").val("1");
			$("#qty").removeProp("readonly");
		}else if("3"== $.trim(calType)){
			$("#qty").val("0");
			$("#qty").removeProp("readonly");
		}else{
			$("#qty").val("0");
			$("#qty").prop("readonly", true);
		}
		$("#page_form").data("bootstrapValidator")  
			.updateStatus("qty", "NOT_VALIDATED", null)
			.validateField("qty");
	});

});

function doClose(){
	closeWin();
}

function validateRefresh_baseItem(){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_baseItem_baseItemCode", "NOT_VALIDATED", null)
		.validateField("openComponent_baseItem_baseItemCode");
}

function validateRefresh_baseCheckItem(){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_baseCheckItem_baseCheckItemCode", "NOT_VALIDATED", null)
		.validateField("openComponent_baseCheckItem_baseCheckItemCode");
}

function doSave(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
		
	if ("V" == "${baseItemToCheckItem.m_umState}") return;	
	var datas = $("#page_form").serialize();

	$.ajax({
		url:"${ctx}/admin/baseItemToCheckItem/checkCode",
		type: "post",
		data: {
			openComponent_baseItem_baseItemCode:$("#openComponent_baseItem_baseItemCode").val(),
			openComponent_baseCheckItem_baseCheckItemCode:$("#openComponent_baseCheckItem_baseCheckItemCode").val(),
			oldBaseItemCode:oldBaseItemCode,
	    	oldBaseCheckItemCode:oldBaseCheckItemCode,
		},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
	    },
	    success: function(obj){
	    	if(obj.valid){
	    		saveAjax(datas);
	    	}else{
	    		art.dialog({
					content: "该基础结算项目对应基础项目已重复",
					width: 200
				});
				return;
	    	}
    	}
	});
}

function saveAjax(datas) {
	var calType=$("#calType").val();
	var qty=$("#qty").val();
	if("2"== $.trim(calType) && 0==qty){
		art.dialog({
			content: "映射算法为【套内面积】时数量不能为0！",
		});
		return;
	}
	$.ajax({
		url:"${ctx}/admin/baseItemToCheckItem/doUpdate",
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
