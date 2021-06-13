<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程图导入</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>

<script type="text/javascript">
$(function(){
	$("#easyContainer").easyUpload({
		allowFileTypes: "zip,bar,bpmn,xml",//允许上传文件类型，格式";*.doc;*.pdf"
		allowFileSize: 3072,//允许上传文件大小(KB)
		selectText: "选择文件",//选择文件按钮文案
		multi: false,//是否允许多文件上传
		multiNum: 1,//多文件上传时允许的文件数
		showNote: true,//是否展示文件上传说明
		note: "提示：单文件上传，支持格式为:"+"zip,bar,bpmn,xml",//文件上传说明
		noteSize:"最大上传文件大小为:"+"6000"+"KB",//文件上传大小说明
		showPreview: true,//是否显示文件预览
		url: "${ctx}/admin/workflow/process/loadDeploy",//上传文件地址
		fileName: "file",//文件filename配置参数
		formParam: {//这种也可以传，只能传页面加载成功时的数据
			//custCode:$.trim($("#custCode").val()),
			//no:$.trim($("#no").val()),
		},//文件filename以外的配置参数，格式：{key1:value1,key2:value2}
		timeout: 60000,//请求超时时间
		okCode: "true",//与后端返回数据code值一致时执行成功回调，不配置默认200
		successFunc: function(res) {
			console.log("成功回调", res);
		},//上传成功回调函数
		errorFunc: function(res) {
			console.log("失败了", res);
		},//上传失败回调函数
		deleteFunc: function(res) {
			console.log("删除回调", res);
		},//删除文件回调函数
		formId:"uploadForm"
	});
	
	
});

</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body"> 
			<form id="uploadForm" >
				<house:token></house:token>
				<div id="easyContainer" ></div>
			</form>	
		</div>
	</div>			
</div>
</body>
</html>
