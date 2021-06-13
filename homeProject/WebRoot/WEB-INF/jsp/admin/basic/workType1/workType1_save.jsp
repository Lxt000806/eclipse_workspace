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
	<script src="${resourceRoot}/pub/component_workType2.js?v=${v}" type="text/javascript"></script>
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
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search" target="targetFrame">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="text" id="expired" name="expired" hidden="true" value="${workType1.expired}"> 
					<input type="text" id="m_umState" name="m_umState" hidden="true" value="${workType1.m_umState}"> 
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>工种编码</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${workType1.code}" autofocus/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>工种名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${workType1.descr}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>领料控制比例</label>
								<input type="text" id="itemAppCtrl" name="itemAppCtrl" style="width:160px;" value="${workType1.itemAppCtrl}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>显示顺序</label>
								<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="0"/>
							</li>
						</div>
						<div class="row" id="checkBox">
							<li style="margin-left: 90px">
								<lable>过期</lable>
								<input type="checkbox" id="expired_show" name="expired_show"
								 onclick="checkExpired(this)" ${workType1.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {

	if ("C" == "${workType1.m_umState}"||"M"=="${workType1.m_umState}") {
		$("#dispSeq").val("${workType1.dispSeq}");
		$("#code").val("${workType1.code}");
		$("#descr").val("${workType1.descr}");
		if("M"=="${workType1.m_umState}"){
			$("#code").attr("readonly","readonly");
		}
	}else if("A"=="${workType1.m_umState}"){
		$("#dispSeq").val("${workType1.dispSeq}");
		$("#checkBox").remove();
	}else{
		$("#dispSeq").val("${workType1.dispSeq}");
		$("#code").val("${workType1.code}");
		$("#descr").val("${workType1.descr}");
		$("#saveBtn").remove();
		$("#page_form input[type='text']").prop("readonly",true);
		$("#expired_show").prop("disabled",true);
	}
	
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			code:{ 
				validators: {  
					notEmpty: {  
						message: "工种编号不允许为空"  
					}
				}  
			},
			descr:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}
				}  
			},
			dispSeq:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}
				}  
			},
			itemAppCtrl:{  
                validators: {  
                    notEmpty: {  
                        message: '领料控制比例不能为空'  
                    },
                    numeric: {  
                        message: '领料控制比例需为数字'  
                    },
                    greaterThan: {  
                        message: '领料控制比例需为正数',
                        inclusive: true,
                        value: 0
                    }
                }  
            },
		}
	});
});

$("#closeBut").on("click",function(){
		closeWin();
	});

$("#saveBtn").on("click",function(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
		
	var datas = $("#page_form").serialize();

	$.ajax({
		url:"${ctx}/admin/workType1/doSave",
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
});

</script>
</html>
