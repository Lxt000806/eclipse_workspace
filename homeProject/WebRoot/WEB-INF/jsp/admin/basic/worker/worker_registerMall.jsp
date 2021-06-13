<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>整改验收确认</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
$(function() {
	$("#page_form").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			isRegisterMall:{  
				validators: {  
					notEmpty: {  
						message: '请选择是否用于自装通'  
					}
				}  
			},
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	$("#closeBut").on("click",function(){
		closeWin();
	});
	
	$("#saveBtn").on("click",function(){
		doSave();
	});
	
});

function doSave(){
	$("#page_form").bootstrapValidator("validate");
	if(!$("#page_form").data("bootstrapValidator").isValid()) return; 
	if("1" == $("#isRegisterMall").val()) {
		if(!$("#avatarPic").val()){
			art.dialog({
				content: "请上传头像",
				width: 200
			});
			return;
		}
		
		if(!$("#personalProfile").val()) {
			art.dialog({
				content: "请填写个人简介",
				width: 200
			});
			return;
		}
	}
	
	var datas = $("#page_form").serialize();
	$.ajax({
		url:"${ctx}/admin/worker/doRegisterMall",
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
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
    	}
	});
}
</script>
</head>
<body>
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
				<button type="button" class="btn btn-system " id="saveBtn">
					<span>保存</span>
				</button>
				<button type="button" class="btn btn-system " id="closeBut" >
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" id="formDiv">  
		<div class="panel-body" id="panelDiv">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="avatarPic" id="avatarPic" value="${worker.avatarPic }"/>
				<ul class="ul-form">
					<div class="validate-group row" >
						<li>
							<label>工人姓名</label>
							<input type="text" id="nameChi" name=""nameChi"" style="width:160px;"  value="${worker.nameChi }" />                                             
						</li>	
						<li>
							<label>工人编号</label>
							<input type="text" id="code" name="code" style="width:160px;"  value="${worker.code }" readonly="readonly"/>                                             
						</li>					
						<li class="form-validate">
							<label><span class="required">*</span>是否用于自装通</label>
							<house:xtdm id="isRegisterMall" dictCode="YESNO" value="${worker.isRegisterMall }"></house:xtdm>
						</li>	
						<li id="remark_li">						
							<label class="control-textarea">个人简介</label>
							<textarea id="personalProfile" name="personalProfile" >${worker.personalProfile}</textarea>
						</li>							
					</div>
				</ul>	
			</form>
		</div>
	</div>
	<div id="easyContainer"></div>
	<script>
	$(function(){
		$('#easyContainer').easyUpload({
			allowFileTypes: 'jpg,png,jpeg', //允许上传文件类型，格式'doc,pdf'
			allowFileSize: 1129,//允许上传文件大小(KB)
			selectText: '选择文件',//选择文件按钮文案
			multi: false,//是否允许多文件上传
			multiNum: 5,//多文件上传时允许的文件数
			showNote: true,//是否展示文件上传说明
			note: '提示：单文件上传，支持格式为:jpg,png,jpeg',//文件上传说明
			noteSize:'图片超过1129KB将被压缩',//文件上传大小说明
			showPreview: true,//是否显示文件预览
			url: '${ctx}/admin/worker/uploadWorkerAvatarPic',//上传文件地址
			fileName: 'file',//文件filename配置参数
			formParam: {
				workerCode:$.trim($("#code").val())
			},//文件filename以外的配置参数，格式：{key1:value1,key2:value2}
			timeout: 60000,//请求超时时间
			okCode: "true",//与后端返回数据code值一致时执行成功回调，不配置默认200
							//okCode没用，我们用res.rs==ture判断
			successFunc: function(res) {
				console.log(res);
				$("#avatarPic").val(res.datas.avatarPic);
				//closeWin();
			},//上传成功回调函数
			errorFunc: function(res) {
				console.log('失败了', res);
			},//上传失败回调函数
			deleteFunc: function(res) {
				console.log('删除回调', res);
			},//删除文件回调函数
			formId:"page_form"
		});
	});
	</script>	
</body>
</html>
