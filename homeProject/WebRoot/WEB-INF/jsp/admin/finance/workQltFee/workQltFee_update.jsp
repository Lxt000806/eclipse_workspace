<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>工人质保金管理--变动</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if (!$("#dataForm").data('bootstrapValidator').isValid())
		return;
	//验证
	var datas = $("#dataForm").serialize();
	var tryFee=$("#tryFee").val();
	if(tryFee=="0"){
		art.dialog({
			content: "变动金额不能为0",
			width: 200
		});
		return;
	}
	$.ajax({
		url:'${ctx}/admin/workQltFee/doSave',
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
		fields:{
			tryFee:{
				validators:{
        			notEmpty: {message: '金额不能为空'},
        			 numeric: {message: '金额只能输入数字'}
        		}
			},
			type:{
				validators:{
        			notEmpty: {message: '类型不能为空'},
        		}
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	$("#qualityFee_show").show();
	$("#accidentFee_show").hide();
});
function wkTypeChange(){
	if ($.trim($("#type").val())=="1"){
	   $("#qualityFee_show").show();
	   $("#accidentFee_show").hide(); 
	}else{
	    $("#qualityFee_show").hide();
	   $("#accidentFee_show").show(); 
	}	   
}
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
				<input type="hidden"
								style="width:160px;" id="workerCode" name="workerCode"
								value="${workQltFeeTran.workerCode}" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate" id="qualityFee_show"><label>质保金余额</label> <input type="text"
								style="width:160px;" id="qualityFee" name="qualityFee"
								value="${workQltFeeTran.qualityFee}" readonly="readonly"/>
							</li>
							<li class="form-validate"  id="accidentFee_show"><label>意外险余额</label> <input type="text"
								style="width:160px;" id="accidentFee" name="accidentFee"
								value="${workQltFeeTran.accidentFee}" readonly="readonly"/>
							</li>
							<li class="form-validate"><label>变动日期</label> <input
								type="text" id="date" name="date" class="i-date"
								style="width:160px;"  readonly="readonly"
								value="<fmt:formatDate value='${workQltFeeTran.date}' pattern='yyyy-MM-dd'/>"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>变动人员</label> <input
								type="text" style="width:160px;" id="lastUpdatedBy" name="lastUpdatedBy" value="${workQltFeeTran.lastUpdatedBy }"
								 readonly="readonly"
								 />
							</li>
							<li class="form-validate"><label>类型</label> <house:xtdm id="type" dictCode="WKQLTFEETYPE"
								 onchange="wkTypeChange()"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>金额</label> <input type="text"
								style="width:160px;" id="tryFee" name="tryFee"
								value="${workQltFeeTran.tryFee }" /><font color='red'>(正数存入，负数取出)</font>
							</li>
							<li><label class="control-textarea">说明</label> <textarea
									id="remarks" name="remarks">${workQltFeeTran.remarks }</textarea>
							</li>
						</div>
					</ul>
				</form>
		</div>
	</div>
</div>
</body>
</html>
