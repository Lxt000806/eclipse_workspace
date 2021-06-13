<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>上传CAD文件（上传、删除、下载）</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
				<button type="button" class="btn btn-system " id="deleteBut" >
					<span>删除文件</span>
				</button>
				<button type="button" class="btn btn-system " id="closeBut" >
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" id="formDiv">  
		<div class="panel-body" id="panelDiv">
			<form role="form" class="form-search" id="page_form"  action="" method="post" 
				target="targetFrame" >
				<input type="hidden" id="docname" name="docname"  value="${intReplenishDT.docName}" />
				<input type="hidden" id="custCode" name="custCode"  value="${intReplenishDT.custCode}" />
				<input type="hidden" id="allowDocSize" name="allowDocSize"  value="10240" />
				<ul class="ul-form">
					<div class="validate-group row" >
						<li >
							<label>文件名称</label>
							<input type="text" id="docdescr" name="docdescr" style="width:160px;" 
								value="${intReplenishDT.docDescr}" readonly="readonly"/>
						</li>
					</div>
				</ul>	
			</form>
		</div>
	</div>
	<div id="easyContainer"></div>
	<script type="text/javascript">
		var custCode=$.trim("${intReplenishDT.custCode}")
		var delCADList=[], newCADList=[];
		
		$(function(){
			$('#easyContainer').easyUpload({
				allowFileTypes: "dwg",//允许上传文件类型，格式'doc,pdf'
				allowFileSize: 10*1024,//允许上传文件大小(KB)
				selectText: '选择文件',//选择文件按钮文案
				multi: false,//是否允许多文件上传
				multiNum: 5,//多文件上传时允许的文件数
				showNote: true,//是否展示文件上传说明
				note: '提示：单文件上传，支持格式为:'+"dwg",//文件上传说明
				noteSize:'文件超过'+10*1024+"KB将被压缩",//文件上传大小说明
				showPreview: true,//是否显示文件预览
				url: '${ctx}/admin/intReplenish/uploadDocNew',//上传文件地址
				fileName: 'file',//文件filename配置参数
				formParam: {
					custCode:custCode,
				},//文件filename以外的配置参数，格式：{key1:value1,key2:value2}
				timeout: 60000,//请求超时时间
				okCode: "true",//与后端返回数据code值一致时执行成功回调，不配置默认200
								//okCode没用，我们用res.rs==ture判断

				successFunc: function(res) {
					if (res.rs) {
					    if ($("#docname").val()) delCAD()
						
						$("#docdescr").val(res.datas.docDescr);
						$("#docname").val(res.datas.docName);

						newCADList.push({custCode: custCode, path: res.datas.docName});
					}
				},
				
				errorFunc: function(res) {
					console.log('失败了', res);
				},
				
				deleteFunc: function(res) {
					console.log('删除回调', res);
				},
				
				formId:"page_form"
			});
			
			$("#deleteBut").on("click", function () {
				if (!$("#docname").val()) {
					art.dialog({content: "CAD文件为空，无需删除",width: 320});
					return false;
				}
				
				art.dialog({
					content:"确定删除文件： " + $("#docdescr").val(),
					width: 320,
					lock: true,
					ok: delCAD,
					cancel: function() {}
				})
			});
			
			$("#closeBut").on("click", function () {
				doClose();
			});
			
			replaceCloseEvt("uploadCAD", doClose);
		})
		
		function delCAD() {
			delCADList.push({custCode: custCode, path: $("#docname").val()});
			$("#docdescr").val("");
			$("#docname").val("");
		}
		
		function doClose() {
			var selectRows = [];
			selectRows.push($("#page_form").jsonForm());
			
			var returnData = {selectRows:selectRows, delCADList: delCADList, newCADList: newCADList};
			Global.Dialog.returnData = returnData;
			closeWin();
		}
	</script>	
</body>
</html>
