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
			docType2:{  
				validators: {  
					notEmpty: {  
						message: '资料类型必填'  
					}
				}  
			},
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
	$("#closeBut").on("click",close);
	//改写窗口右上角关闭按钮事件
	var titleCloseEle = parent.$("div[aria-describedby=dialog_VolumeAdd]").children(".ui-dialog-titlebar");
	$(titleCloseEle[0]).children("button").remove();
	$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
								+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
	$(titleCloseEle[0]).children("button").on("click", close); 
});
function changeType2(){
	$('#easyContainer').easyUpload({
		allowFileTypes: 'jpg,doc,pdf,docx,xls,png,jpeg,bmp',//允许上传文件类型，格式';*.doc;*.pdf'
		allowFileSize: 3072,//允许上传文件大小(KB)
		selectText: '选择文件',//选择文件按钮文案
		multi: true,//是否允许多文件上传
		multiNum: 5,//多文件上传时允许的文件数
		showNote: true,//是否展示文件上传说明
		note: '提示：单文件上传，支持格式:jpg,doc,pdf,docx,xls,png,jpeg,bmp',//文件上传说明
		noteSize:'最大文件大小:3072KB',//文件上传大小说明
		showPreview: true,//是否显示文件预览
		url: '${ctx}/admin/custDoc/uploadCustDoc',//上传文件地址
		fileName: 'file',//文件filename配置参数
		formParam: {
			custCode:$.trim($("#custCode").val()),
			descr:descr,
			docType2:$.trim($("#docType2").val()),
			docType1:$.trim($("#docType1").val()),
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
	$.ajax({
		url:'${ctx}/admin/custDoc/getDocType2Info',
		type: 'post',
		data:{docType2:$.trim($("#docType2").val())},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
			$("#allowDocType").val(obj["FileType"]);
			$("#allowDocSize").val(obj["MaxLen"]);
			if(obj["FileType"]!=""){
				$("#noteSpan").html("提示:单文件上传,支持格式为"+obj["FileType"]+",最大文件大小为:"+obj["MaxLen"]+"KB");
				//$("#noteSpan").val("提示:单文件上传,支持格式为"+obj["FileType"]+",最大文件大小为:"+obj["MaxLen"]+"KB");
			}
		}
	});
}

function changeNum(){
	var firstNumOld = $("#firstNumOld").val().replace(/[^\?\d]/g,'');
	$("#firstNumOld").val(firstNumOld);
	$("#firstNum").val(firstNumOld);
}

function close(){
	$.ajax({
		url:'${ctx}/admin/custDoc/getIsAllowAdd',
		type: 'post',
		data:{custCode:"${custDoc.custCode}" },
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
			var isAllowAdd = obj;
			if(isAllowAdd){
				closeWin();
			}else{
				art.dialog({
					content:'请上传CAD文件！',
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
				<button type="button" class="btn btn-system " id="closeBut">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" id="formDiv">  
		<div class="panel-body" id="panelDiv">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<input type="hidden" id="docName" name="docName"  value="${custDoc.docName }" />
				<input type="hidden" id="docType1" name="docType1"  value="${custDoc.docType1 }" />
				<input type="hidden" id="allowDocType" name="allowDocType"  value="${custDoc.fileType }" />
				<input type="hidden" id="allowDocSize" name="allowDocSize"  value="${custDoc.maxLen }" />
				<input type="hidden" id="upDocType2" name="upDocType2"  value="${custDoc.upDocType2}" />
				<input type="hidden" id="firstNum" name="firstNum" />
				<ul class="ul-form">
					<div class="validate-group row" >
						<li >
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:120px;"  value="${custDoc.custCode }" readonly="readonly"/>                                             
						</li>
						<li >
							<label>名称前缀</label>
							<input type="text" id="descr" name="descr" style="width:80px;"  value="${custDoc.descr }" />                                             
						</li>
						<li>
							<label style="width:50px">起始编号</label>
							<input type="text" id="firstNumOld" name="firstNumOld" style="width:80px;" onkeyup="changeNum()"/>                                             
						</li>	
						<li class="form-validate">
							<label><span class="required">*</span>资料类型2</label>
							<house:dict id="docType2" dictCode=""  width="100"
								sql=" select code,Descr from tCustDocType2 where DocType1='${custDoc.docType1 }' and expired='F' " 
								sqlValueKey="Code" sqlLableKey="Descr" onchange="changeType2()" value="${custDoc.minDocType2 }"
								></house:dict>
						</li>
					</div>
				</ul>	
			</form>
		</div>
	</div>
	<div id="easyContainer"></div>
	<script>
	$(function(){
		if ("" != "${custDoc.upDocType2}") $("#docType2").prop("disabled", true); //add by zb on 20200103
		$('#easyContainer').easyUpload({
			allowFileTypes: 'jpg,doc,pdf,docx,xls,png,jpeg,bmp',//允许上传文件类型，格式';*.doc;*.pdf'
			allowFileSize: 3072,//允许上传文件大小(KB)
			selectText: '选择文件',//选择文件按钮文案
			multi: true,//是否允许多文件上传
			multiNum: 5,//多文件上传时允许的文件数
			showNote: true,//是否展示文件上传说明
			note: '提示：单文件上传，支持格式为:'+"${custDoc.fileType }",//文件上传说明
			noteSize:'最大上传文件大小为:'+"${custDoc.maxLen }"+"KB",//文件上传大小说明
			showPreview: true,//是否显示文件预览
			url: '${ctx}/admin/custDoc/uploadCustDoc',//上传文件地址
			fileName: 'file',//文件filename配置参数
			formParam: {//这种也可以传，只能传页面加载成功时的数据
				custCode:$.trim($("#custCode").val()),
				descr:$.trim($("#descr").val()),
				docType2:$.trim($("#docType2").val()),
				docType1:$.trim($("#docType1").val()),
			},//文件filename以外的配置参数，格式：{key1:value1,key2:value2}
			timeout: 60000,//请求超时时间
			okCode: "true",//与后端返回数据code值一致时执行成功回调，不配置默认200
			successFunc: function(res) {
				if(res.success && res.success.length > 0){
					var length = res.success.length;
					if(res.success[length-1].datas.nowNum){
						if(myRound(res.success[length-1].datas.nowNum) != -1){
							$("#firstNum").val(myRound(res.success[length-1].datas.nowNum)+1);
						}
					}
				}
				
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
