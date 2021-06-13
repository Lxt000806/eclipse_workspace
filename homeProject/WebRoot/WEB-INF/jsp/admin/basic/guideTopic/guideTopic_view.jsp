<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>问答管理查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_guideTopicFolder.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctx}/commons/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
		UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	    UE.Editor.prototype.getActionUrl = function(action) {
	        if (action =="uploadimage" || action =="uploadscrawl") {
	            return"${ctx}/admin/advert/uploadImage";//这就是自定义的上传地址
	        } else if (action =="uploadvideo") {
	            return"";
	        } else {
	            return this._bkGetActionUrl.call(this, action);
	        }
	    };
    </script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
var editor ;
$(function() {
	editor = UE.getEditor("editor", {
		autoHeightEnabled: false,
		focus: true,
		toolbars:[[
            'fullscreen', 'source', 
            '|', 'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat',
            'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', 
            '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
            'rowspacingtop', 'rowspacingbottom', 'lineheight', 
            '|', 'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
            'directionalityltr', 'directionalityrtl', 'indent', 
            '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 
            'touppercase', 'tolowercase', 
            '|','anchor','pagebreak', 'template',  '|', 
            'horizontal', 'date', 'time', 'spechars', 'wordimage',
            '|', 'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 
            'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
            'print', 'preview', 'searchreplace', 'drafts', 'help'
        ]],
	});
	
	editor.ready(function(){
		editor.execCommand("insertHtml", '${guideTopic.content }');
	});
	
	$("#folderPK").openComponent_guideTopicFolder({showValue:"${guideTopic.folderPK}",readonly:true});
	$("#openComponent_guideTopicFolder_folderPK").blur();
	
	$("#saveBtn").on("click",function(){
		$.ajax({
			url:"${ctx}/admin/guideTopic/doSave",
			type: "post",
			data: {topic:$("#topic").val(),content: UE.getEditor("editor").getContent(),
					folderPK:$("#folderPK").val(),keyWords:$("#keyWords").val()},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin(true);
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
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="folderCode" name="folderCode" value="${folderCode }"/>
						<input type="hidden" id="path" name="path" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>类目</label>
									<input type="text" id="folderPK" name=folderPK style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>主题</label>
									<input type="text" id="topic" name="topic" style="width:448px;" value="${guideTopic.topic }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>关键字</label>
									<input type="text" id="keyWords" name="keyWords" style="width:448px;" readonly="true" value="${guideTopic.keyWords }" placeholder="支持多个关键字，空格隔开"/>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
		<label >内容:</label>
		<script id="editor" type="text/plain" style="width: 100%;height: 280px"></script>
	</body>	
</html>
