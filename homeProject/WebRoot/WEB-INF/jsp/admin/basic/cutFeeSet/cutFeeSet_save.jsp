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
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>切割类型</label> 
								<house:xtdm id="cutType" dictCode="CUTTYPE" style="width:160px;" value="${cutFeeSet.cutType}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>瓷砖尺寸</label>
								<input type="text" id="size" name="size" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="0" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>客户加工费</label>
								<input type="text" id="cutFee" name="cutFee" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="0" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>供应商加工费</label>
								<input type="text" id="supplCutFee" name="supplCutFee" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="0" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>工厂加工费</label>
								<input type="text" id="factCutFee" name="factCutFee" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="0" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>是否允许修改单价</label> 
								<house:xtdm id="allowModify" dictCode="YESNO" style="width:160px;" value="${cutFeeSet.allowModify}"/>
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
	dialog_id = "save";
	parent.$("#iframe_"+dialog_id).attr("height","98%");

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			cutType:{ 
				validators: {  
					notEmpty: {  
						message: "切割类型不允许为空"  
					},
				}  
			},
			size:{ 
				validators: {  
					notEmpty: {  
						message: "瓷砖尺寸不允许为空"  
					}
				}  
			},
			cutFee:{ 
				validators: {  
					notEmpty: {  
						message: "切割费不允许为空"  
					}
				}  
			},
			supplCutFee:{ 
				validators: {  
					notEmpty: {  
						message: "供应商加工费不允许为空"  
					}
				}  
			},
			factCutFee:{ 
				validators: {  
					notEmpty: {  
						message: "工厂加工费不允许为空"  
					}
				}  
			},
			allowModify:{ 
				validators: {  
					notEmpty: {  
						message: "是否允许修改单价不允许为空"  
					},
				}  
			},
		}
	});

});

function doClose(){
	closeWin();
}

function doSave(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
		
	var datas = $("#page_form").serialize();

	$.ajax({
		url:"${ctx}/admin/cutFeeSet/checkCode",
		type: "post",
		data: {
			cutType:$("#cutType").val(),
			size:$("#size").val(),
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
					content: "该切割类型和瓷砖尺寸已重复",
					width: 200
				});
				return;
	    	}
    	}
	});
	
}

function saveAjax(datas) {
	$.ajax({
		url:"${ctx}/admin/cutFeeSet/doSave",
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
