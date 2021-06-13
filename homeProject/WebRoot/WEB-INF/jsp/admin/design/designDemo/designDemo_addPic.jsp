<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>设计模板新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	$(function() {
		var titleCloseEle = parent.$("div[aria-describedby=dialog_Add]").children(".ui-dialog-titlebar");
		$(titleCloseEle[0]).children("button").remove();
		var childBtn=$(titleCloseEle).children("button");
		$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
									+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
		$(titleCloseEle[0]).children("button").on("click", closeWin); 
	
	
		
		$("#closeBut").on("click",function(){
			closeWin();
		});
	});
	function changeDetail(){
		$("#easyContainer").easyUpload({
			allowFileTypes: "jpg,png,jpeg,bmp",//允许上传文件类型，格式";*.doc;*.pdf"
			allowFileSize: 3072,//允许上传文件大小(KB)
			selectText: "选择文件",//选择文件按钮文案
			multi: true,//是否允许多文件上传
			multiNum: 5,//多文件上传时允许的文件数
			showNote: true,//是否展示文件上传说明
			note: "提示：单文件上传，支持格式:jpg,png,jpeg,bmp",//文件上传说明
			noteSize:"最大文件大小:3072KB",//文件上传大小说明
			showPreview: true,//是否显示文件预览
			url: "${ctx}/admin/designDemo/uploadDesignDemoPic",//上传文件地址
			fileName: "file",//文件filename配置参数
			formParam: {
				custCode:$.trim($("#custCode").val()),
				no:$.trim($("#no").val()),
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
			formId:"page_form"
		});
	}
	
	</script>
</head>
<body>
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<button type="button" class="btn btn-system" id="closeBut">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" id="formDiv" hidden="true">  
		<div class="panel-body" id="panelDiv">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<input type="hidden" name="no" id="no" value="${designDemo.no }"/>
				<input type="hidden" name="custCode" id="custCode" value="${designDemo.custCode }"/>
				<input type="hidden" name="builderCode" id="builderCode" value="${designDemo.builderCode }"/>
				<input type="hidden" name="designMan" id="designMan" value="${designDemo.designMan }"/>
				<input type="hidden" name="layout" id="layout" value="${designDemo.layout }"/>
				<input type="hidden" name="designSty" id="designSty" value="${designDemo.designSty }"/>
				<input type="hidden" name="area" id="area" value="${designDemo.area }"/>
				<input type="hidden" name="amount" id="amount" value="${designDemo.amount }"/>
				<input type="hidden" name="isPushCust" id="isPushCust" value="${designDemo.isPushCust }"/>
				<input type="hidden" name="designRemark" id="designRemark" value="${designDemo.designRemark }"/>
			</form>
		</div>
	</div>
	<div id="easyContainer" ></div>
	<script>
	$(function(){
		$("#easyContainer").easyUpload({
			allowFileTypes: "jpg,png,jpeg,bmp",//允许上传文件类型，格式";*.doc;*.pdf"
			allowFileSize: 3072,//允许上传文件大小(KB)
			selectText: "选择文件",//选择文件按钮文案
			multi: true,//是否允许多文件上传
			multiNum: 5,//多文件上传时允许的文件数
			showNote: true,//是否展示文件上传说明
			note: "提示：单文件上传，支持格式为:"+"jpg,png,jpeg,bmp",//文件上传说明
			noteSize:"最大上传文件大小为:"+"1024"+"KB",//文件上传大小说明
			showPreview: true,//是否显示文件预览
			url: "${ctx}/admin/designDemo/uploadDesignDemoPic",//上传文件地址
			fileName: "file",//文件filename配置参数
			formParam: {//这种也可以传，只能传页面加载成功时的数据
				custCode:$.trim($("#custCode").val()),
				no:$.trim($("#no").val()),
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
			formId:"page_form"
		});
	});
	</script>
</body>
</html>
