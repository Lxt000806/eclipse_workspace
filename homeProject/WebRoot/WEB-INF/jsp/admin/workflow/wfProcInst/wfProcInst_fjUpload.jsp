<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>附件上传</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
    <div class="query-form" style="border-bottom:0px;margin-top:0px">
    	<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
	   		<input id="wfProcInstNo" name="wfProcInstNo" value="${wfProcInstNo }" hidden=""/>
        </form>
     </div>
	<div id="easyContainer"></div>
<script type="text/javascript">
$(function(){
	$("#page_form").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			
		},
		submitButtons : "input[type='submit']"
	});
	$('#easyContainer').easyUpload({
		allowFileTypes: 'jpg,doc,pdf,docx,xls,png,jpeg,bmp',//允许上传文件类型，格式';*.doc;*.pdf'
		allowFileSize: 3072,//允许上传文件大小(KB)
		selectText: '选择文件',//选择文件按钮文案
		multi: true,//是否允许多文件上传
		multiNum: 5,//多文件上传时允许的文件数
		showNote: true,//是否展示文件上传说明
		note: '提示：单文件上传，支持格式为:'+"jpg,doc,pdf,docx,xls,png,jpeg,bmp",//文件上传说明
		noteSize:'最大上传文件大小为:'+"3072"+"KB",//文件上传大小说明
		showPreview: true,//是否显示文件预览
		url: '${ctx}/admin/wfProcInst/uploadWfProcPic',//上传文件地址
		fileName: 'file',//文件filename配置参数
		formParam: {//这种也可以传，只能传页面加载成功时的数据
			wfProcInstNo:$.trim($("#wfProcInstNo").val()),
		},//文件filename以外的配置参数，格式：{key1:value1,key2:value2}
		timeout: 60000,//请求超时时间
		okCode: "true",//与后端返回数据code值一致时执行成功回调，不配置默认200
		successFunc: function(res) {
			console.log('成功回调', res);
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

