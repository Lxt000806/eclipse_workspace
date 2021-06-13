<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改CommiBasePersonal</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function save(){
	//表单校验
	$("#dataForm").bootstrapValidator('validate');
	if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
	
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/commiBasePersonal/doUpdate',
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
					time: 3000,
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
		fields : {
			"baseItemType1": {
				validators : {
					notEmpty : {
						message : '基装类型1不能为空'
					},
				}		
			},
			"commiPer": {
				validators : {
					notEmpty : {
						message : '提成点不能为空'
					},
					numeric: {
						   message: '只能输入数字', 
					}
				}		
			},
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

</script>

</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
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
						<input type="hidden" name="jsonString" value="" /> 
						<input type="hidden" id="m_umState" name="m_umState" value="M" />
						<input type="hidden" id="pk" name="pk" value="${commiBasePersonal.pk}" />
						<ul class="ul-form">	
							<li class="form-validate">
								<label><span class="required">*</span>基装类型1</label> 
								<house:dict id="baseItemType1" dictCode="" sql="select code,code+' '+Descr fd from tBaseItemType1 where Expired='F' order by DispSeq"
					    		 sqlLableKey="fd" sqlValueKey="code" value="${commiBasePersonal.baseItemType1}"></house:dict>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>提成点</label> 
								<input type="text" id="commiPer" name="commiPer" value="${commiBasePersonal.commiPer}" />
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>
