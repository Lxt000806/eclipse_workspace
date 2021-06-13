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
});

$(function(){
	$("#uploadify").uploadify({
		　'swf': '${resourceRoot}/uploadify/uploadify.swf', 
		  'uploader':'${ctx}/admin/custDoc/uploadCustDoc;',                  
		　'queueID' : 'fileQueue', //和存放队列的DIV的id一致  
		　//'fileDataName': 'fileupload', //必须，和以下input的name属性一致                   
		　'auto'  : true, //是否自动开始  
		　'multi': true, //是否支持多文件上传  
		  'buttonText': '选择文件', //按钮上的文字  
		  'rollover':true,
		  'width':108,
		  'height':25, 
		  formData:{fileCustCode:'${custDoc.custCode }'}, 
		  'method':'get',
		  'wmode' :'transparent',
		 // 'onDialogOpen':isSelectType, //打开文件窗口时触发
		  //'onUploadStart':isSelectType, //开始上传时触发
		 // 'onSelectError' : errorSelect, //选择错误时触发
		  'cancelImg' : '${resourceRoot}/uploadify/uploadify-cancel.png',
		　'simUploadLimit' : 2, //一次同步上传的文件数目  
		　//'sizeLimit': 19871202, //设置单个文件大小限制，单位为byte  
		　'fileSizeLimit' : '10240KB',
		  'queueSizeLimit' : 10, //队列中同时存在的文件个数限制  
		 //'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp',//允许的格式
		 // 'fileTypeDesc': '支持格式:jpg/gif/jpeg/png/bmp.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的  
		  'onUploadSuccess':function(event, response, data) {
				var json = eval("("+response+")");
			  	$("#docName").val(event.name)
				var docName = $.trim($("#docName").val());
				var descr = $.trim($("#descr").val());
				var docType2 = $.trim($("#docType2").val());
				$.ajax({
					url:"${ctx}/admin/custDoc/doSaveDocName",
					type:'post',
					data:{docName:docName,custCode:'${custDoc.custCode}',descr:descr,docType2:docType2,docType1:'${custDoc.docType1}'},
					dataType:'json',
					cache:false,
					error:function(object){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(object){
						art.dialog({
							content:'上传成功',
							time:500,
							beforeunload: function () {
			    				closeWin();
						    }
						}); 
					}
				}); 
		  }
	　});
	var sub = document.getElementById("SWFUpload_0");
	sub.style.cssText = "position:absolute;left:1px;";
});


function deleteImg() {
	$("tbody tr:eq(14) td:eq(1) img").remove();
}

function isSelect(){
	return false;	
}

function hidButton(){
	/* var docType2=$.trim($("#docType2").val());
	if(docType2==''){
		$("#uploadify").removeAttr("hidden","true");
	} */
}


</script>
</head>
<body>
	<div class="panel panel-system" >
					<div class="panel-body" >
						<div class="btn-group-xs" >
									<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
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
						<ul class="ul-form">
							<div class="validate-group row" >
							<li >
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${custDoc.custCode }" readonly="readonly"/>                                             
							</li>
							<li >
								<label>图片名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"  value="${custDoc.descr }" />                                             
							</li>								
							<li class="form-validate">
								<label><span class="required">*</span>资料类型2</label>
								<house:dict id="docType2" dictCode="" sql=" 
														select code,Descr from tCustDocType2 where DocType1='${custDoc.docType1 }' and expired='F' " 
									sqlValueKey="Code" sqlLableKey="Descr" onchange="hidButton()" value="${custDoc.minDocType2 }"></house:dict>
							</li>								
							</div>
							<div class="validate-group row" id="uploadDiv">
							<li>
								<label>上传图片</label>
							</li>
							<li>
								<!-- <input type="file" accept="/" multiple>  -->
								<input type="file" style="width:153px"  name="uploadify" id="uploadify"  multiple/>
								 
							</li>	
							<li>
								<div id="fileQueue" style="float:right;width:350px" hidden="true"></div>
							</li>							
							</div>
						</ul>	
				</form>
				</div>
		</div>
</body>
</html>
