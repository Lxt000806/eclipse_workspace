<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改课程类型</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
    
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
				<button type="button" class="btn btn-system" id="saveBtn">
					<span>保存</span>
				</button>
				<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info">  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<house:token></house:token>
				<input type="hidden" id="expired" name="expired" value="${courseType.expired }"/>
				<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
				<ul class="ul-form">
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required" id="code_tag_show">*</span>编号</label>
							<input type="text" id="code" name="code" style="width:160px;" value="${courseType.code }" />
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>课程类型名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;" value="${courseType.descr }"/>
						</li>
					</div>
					<div class="validate-group row" id="chekbox_show">
						<li>
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${courseType.expired }",
								onclick="checkExpired(this)" ${courseType.expired=="T"?"checked":"" }/>
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
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			code:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				    stringLength: {
						min: 1,  
						max: 60,  
						message: "长度必须在1-60之间" 
				    }
				}  
			},
			descr:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				    stringLength: {
						min: 1,  
						max: 60,  
						message: "长度必须在1-60之间" 
				    }
				}  
			}
		}
	});
	
	if ("V" == "${courseType.m_umState}") {
		$("#saveBtn").hide();
		$("#code_tag_show").hide();
		dialog_id = "view";
		disabledForm("dataForm");
	} else if("M" == "${courseType.m_umState}") {
		$("#code").prop("readOnly",true);
		$("#code_tag_show").hide();
		dialog_id = "update";
		$("#page_form").bootstrapValidator("validate");
	}else{
		$("#chekbox_show").hide();
		dialog_id = "save";
		$("#page_form").bootstrapValidator("validate");
		
	}
	
	//保存
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");/* 提交验证 */
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return; /* 验证失败return */
		
		var datas = $("#dataForm").serialize();
		
		
		$.ajax({
			url:"M" == "${courseType.m_umState}"?"${ctx}/admin/courseType/doUpdate":"${ctx}/admin/courseType/doSave",
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
});
</script>
</html>
