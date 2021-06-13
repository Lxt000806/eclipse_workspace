<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加系统代码</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	if($("#infoBoxDiv").css("display")!='none'){return false;}
	
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/xtdm/doSave',
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
				fields: {  
					id:{  
						validators: {  
							notEmpty: {  
								message: '必填字段'  
							}
						}  
					},
					note:{  
						validators: {  
							notEmpty: {  
								message: '必填字段'  
							}
						}  
					},
					idnoteE:{  
						validators: {  
							notEmpty: {  
								message: '必填字段'  
							}
						}  
					},
					idnote:{  
						validators: {  
							notEmpty: {  
								message: '必填字段'  
							}
						}  
					},
					noteE:{  
						validators: {  
							notEmpty: {  
								message: '必填字段'  
							},
						}  
					},
					ibm:{  
						validators: {  
							notEmpty: {  
								message: '必填字段'  
							}
						}  
					},
					cbm:{  
						validators: {  
							notEmpty: {  
								message: '必填字段'  
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
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
		<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
			</div>
			</div>
		<div class="infoBox" id="infoBoxDiv"></div>
		<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
				<house:token></house:token>
				<ul class="ul-form">
					<li class="form-validate">
						<label><span class="required">*</span>对象ID</label>
						<input type="text" style="width:160px;" id="id" name="id" value="${xtdm.id }"/> 
					</li>	
					<li class="form-validate">
						<label><span class="required">*</span>字母编码</label>
						<input type="text" style="width:160px;" id="cbm" name="cbm" value="${xtdm.cbm }"/> 
					</li>	
					<li class="form-validate">
						<label><span class="required">*</span>数字编码</label>
						<input type="text" style="width:160px;" id="ibm" name="ibm" value="${xtdm.ibm }"/> 
					</li>	
					<li class="form-validate">
						<label><span class="required">*</span>中文对象说明</label>
						<input type="text" style="width:160px;" id="idnote" name="idnote" value="${xtdm.idnote }"/> 
					</li>	
					<li class="form-validate">
						<label><span class="required">*</span>中文含义</label>
						<input type="text" style="width:160px;" id="note" name="note" value="${xtdm.note }"/> 
					</li>	
					<li class="form-validate">
						<label><span class="required">*</span>英文含义</label>
						<input type="text" style="width:160px;" id="noteE" name="noteE" value="${xtdm.noteE }"/> 
					</li>	
					<li class="form-validate">
						<label><span class="required">*</span>英文对象说明</label>
						<input type="text" style="width:160px;" id="idnoteE" name="idnoteE" value="${xtdm.idnoteE }"/>
					</li>
				</ul>
			</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>
