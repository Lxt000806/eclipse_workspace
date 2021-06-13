<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>合同范本上传</title>
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
				<button type="button" class="btn btn-system " id="viewTemp">
					<span>预览合同范本</span>
				</button>
				<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)" >
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" id="formDiv">  
		<div class="panel-body" id="panelDiv">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<input type="hidden" id="contractTempName" name="contractTempName"  value="${cmpCustType.contractTempName}" />
				<input type="hidden" id="pk" name="pk"  value="${cmpCustType.pk}" />
				<ul class="ul-form">
					<div class="validate-group row" >
						<div class="validate-group">
							<li>
								<label>公司名称</label> 
								<input type="text" id="cmpDescr" name="cmpDescr" style="width:160px;" value="${cmpCustType.cmpDescr}" />
							</li>
							<li>
								<label>客户类型</label> 
								<house:dict id="custType" dictCode="" sql="select code ,code+' '+desc1 descr from tcustType where expired='F'"
									sqlValueKey="Code" sqlLableKey="Descr" value="${cmpCustType.custType}" disabled="true"></house:dict>
							</li>
						</div>
					</div>
				</ul>	
			</form>
		</div>
	</div>
	<div id="easyContainer"></div>
	<script type="text/javascript">
	$(function(){
		$('#easyContainer').easyUpload({
			allowFileTypes: "pdf",//允许上传文件类型，格式'doc,pdf'
			allowFileSize: "5120",//允许上传文件大小(KB)
			selectText: '选择文件',//选择文件按钮文案
			multi: false,//是否允许多文件上传
			multiNum: 5,//多文件上传时允许的文件数
			showNote: true,//是否展示文件上传说明
			note: '提示：仅支持上传单个 pdf 文件',//文件上传说明
			noteSize: '且文件大小在 5M 以内',//文件上传大小说明
			showPreview: true,//是否显示文件预览
			url: '${ctx}/admin/cmpCustType/uploadContractTemp',//上传文件地址
			fileName: 'file',//文件filename配置参数
			formParam: {
				pk:"${cmpCustType.pk}"				
			},//文件filename以外的配置参数，格式：{key1:value1,key2:value2}
			timeout: 60000,//请求超时时间
			okCode: "true",//与后端返回数据code值一致时执行成功回调，不配置默认200
							//okCode没用，我们用res.rs==ture判断
			successFunc: function(res) {
				$("#contractTempName").val(res.msg);
			},//上传成功回调函数
			errorFunc: function(res) {
				console.log('失败了', res);
			},//上传失败回调函数
			deleteFunc: function(res) {
				console.log('删除回调', res);
			},//删除文件回调函数
			formId:"page_form"
		});
		
		$("#viewTemp").on("click",function(){
			if($("#contractTempName").val()==""){
				art.dialog({
					content:"此客户类型未上传合同范本",
				});
				return;
			}
			Global.Dialog.showDialog("viewContractTemp",{ 
       	  		title:"合同范本预览",
       	  		url:"${ctx}/admin/cmpCustType/goViewContractTemp",
       	  		postData: {
       	  		    fileName:$("#contractTempName").val()
       	  		},
       	  		height:755,
       	  		width:937,
       	  		returnFun:goto_query
            });			
		});
		
	});
	</script>	
</body>
</html>
