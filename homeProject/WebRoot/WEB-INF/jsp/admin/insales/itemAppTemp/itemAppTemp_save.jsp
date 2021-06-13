<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>添加ItemAppTemp</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript" defer>
function save(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
	var datas = $("#page_form").serialize()+"&m_umState=A";

	$.ajax({
		url:"${ctx}/admin/itemAppTemp/doSave",
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

//校验函数
$(function() {
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			itemType1:{ 
				validators: {  
					notEmpty: {  
						message: "请选择材料类型1"  
					}
				}  
			},
		}
	});

	// 获得原始数据
	var originalData = $("#page_form").serialize();

	$("#closeBut").on("click",function(){
		var changeData = $("#page_form").serialize();
		if (originalData != changeData) {
			art.dialog({
				content: "数据已变动,是否保存？",
				width: 200,
				okValue: "确定",
				ok: function () {
					save();
				},
				cancelValue: "取消",
				cancel: function () {
					closeWin();
				}
			});
		} else {
			closeWin();
		}
		
	});
});

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="save()">
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
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label>模板编号</label>
								<input type="text" id="no" name="no" style="width:160px;" placeholder="保存自动生成" 
									readonly="readonly"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>材料类型1</label>
								<house:dict id="itemType1" dictCode="" sql="select Code,Code+' '+Descr cd from tItemType1 where Expired != 'T'" 
									sqlValueKey="Code" sqlLableKey="cd"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>工种分类1-2</label>
								<house:dict id="workType12" dictCode="" sql="select Code,Code+' '+Descr cd from tWorkType12 where Expired != 'T'" 
									sqlValueKey="Code" sqlLableKey="cd"/>
							</li>
						</div>
						<div class="row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -60px;">备注</label>
								<textarea id="remarks" name="remarks" style="height: 80px;width: 160px;"></textarea>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
</html>
