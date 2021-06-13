<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>交房批次信息--修改</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function save(){
	var delivDate=$("#delivDate").val();
	if(delivDate=="" || delivDate==null){
		art.dialog({
			content: "请填写完整的信息"
		});
		return;
	}
	$("#dataForm").bootstrapValidator('validate');
	if (!$("#dataForm").data('bootstrapValidator').isValid())
		return;
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/builderDeliv/doUpdate',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
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
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
});
</script>
<style type="text/css">

</style>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
	      </div>
	   	</div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
        	<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
				<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group row">
						<input type="hidden" id="builderCode" name="builderCode" value="${builderDeliv.builderCode}" >
							<li class="form-validate"><label>编号
							</label> <input type="text" style="width:160px;" id="code" name="code"
								readonly="readonly" value="${builderDeliv.code }"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>交房时间</label> <input
								type="text" id="delivDate" name="delivDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${builderDeliv.delivDate}' pattern='yyyy-MM-dd'/>" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>交房户数</label> <input type="text"
								style="width:160px;" id="delivNum" name="delivNum" value="${builderDeliv.delivNum }"/></li>
						</div>
						<div class="validate-group row">
							<li><label>项目类型</label> <house:xtdm id="builderType"
									dictCode="BUILDERTYPE" value="${builderDeliv.builderType }"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li><label class="control-textarea">备注</label> <textarea
									id="remarks" name="remarks">${builderDeliv.remarks }</textarea>
							</li>
						</div>
					</ul>
				</form>
		</div>
	</div>
</div>
</body>
</html>
