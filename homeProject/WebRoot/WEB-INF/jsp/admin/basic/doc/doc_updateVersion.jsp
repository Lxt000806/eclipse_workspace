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
	<title>文档管理新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}

	</style>
<script type="text/javascript"> 
$(function() {
	
	$('#easyContainer').easyUpload({
		allowFileTypes: 'jpg,doc,pdf,docx,xls,png,jpeg,bmp,xlsx',//允许上传文件类型，格式';*.doc;*.pdf'
		allowFileSize: myRound("${docAttMaxSize}")*1024,//允许上传文件大小(KB)
		selectText: '选择附件',//选择文件按钮文案
		multi: true,//是否允许多文件上传
		multiNum: "${docAttMaxCnt}",//多文件上传时允许的文件数
		showNote: true,//是否展示文件上传说明
		note: '提示：单次最多上传'+"${docAttMaxCnt}"+'张，支持格式为:'+"jpg,doc,pdf,docx,xls,png,jpeg,bmp,xlsx",//文件上传说明
		noteSize:'最大上传文件大小为:'+"${docAttMaxSize}"+"M",//文件上传大小说明
		showPreview: false,//是否显示文件预览
		url: '${ctx}/admin/doc/uploadDoc',//上传文件地址
		fileName: 'file',//文件filename配置参数
		formParam: {//这种也可以传，只能传页面加载成功时的数据
			//wfProcInstNo:$.trim("${wfProcInstNo }"),
		},//文件filename以外的配置参数，格式：{key1:value1,key2:value2}
		timeout: 60000,//请求超时时间
		okCode: "true",//与后端返回数据code值一致时执行成功回调，不配置默认200
		uploadBtn: false, // 是否显示上传按钮
		formId:"dataForm",
		successFunc: function(res) {//上传成功回调函数
			var fullNames = "";
			var arry = new Array();

			for(var i = 0; i < res.success.length; i++){
				if(fullNames == ""){
					fullNames = res.success[i].msg;
				}else{
					fullNames = fullNames + "," + res.success[i].msg;
				}
				arry[i] = res.success[i].datas;
			}
			$("#attrDatas").val(JSON.stringify(arry));
		},
		befUpload:function(res){ // 上传前触发 返回boolean
			if(res.length<=0){
				art.dialog({
					content:"请至少选择一个文档",
				});
				return false;
			}
			return true;
		},
		befSelectFile:function(){ // 选择文件前判断条件 返回boolean
			if($.trim($("#folderCode").val()) == ""){
				art.dialog({
					content:"请先选择目录",
				});
				return false;
			}
			return true;
		},
		aftSelectFile:function(){
			$('.head_check').click();
		},
		completeFun:function(res){// 所有图片上传完成后触发
			doUpdateVersion();
		},
		errorFunc: function(res) {
			console.log('失败了', res);
		},//上传失败回调函数
		deleteFunc: function(res) {
			console.log('删除回调', res);
		},//删除文件回调函数
	});
	
	function getFolderData(data){
		if(!data) return;
		if(data.folderCode){
			$("#docCode").val($.trim(data.folderCode)+"_"+"${doc.dateStr }");
			$("#folderCode").val($.trim(data.folderCode));
		} 
		if(data.foldercode){
			$("#docCode").val($.trim(data.foldercode)+"_"+"${doc.dateStr }");
			$("#folderCode").val($.trim(data.foldercode));
		}
	}
	$("#folderPK").openComponent_docFolder({showValue:"${doc.folderPK}",readonly:true,callBack:getFolderData});
	$("#openComponent_docFolder_folderPK").blur();
	$("#openComponent_docFolder_folderPK").attr("readonly",true);
	$("#drawUpCZY").openComponent_employee({showValue:"${doc.drawUpCZY}",readonly:true});
	$("#confirmCZY").openComponent_employee({showValue:"${doc.confirmCZY}",readonly:true});
	$("#approveCZY").openComponent_employee({showValue:"${doc.approveCZY}",readonly:true});
	$("#openComponent_employee_drawUpCZY").blur();
	$("#openComponent_employee_confirmCZY").blur();
	$("#openComponent_employee_approveCZY").blur();
	(function setNewVersion(){
		var oldVersion = $("#oldVersion").val();
		var newVersion = "";
		var versionArr = $("#oldVersion").val().split(".");
		var  len = versionArr.length;
		if(len > 1){
			versionArr[len-1] = myRound(versionArr[len-1])+1;		
			for(var i = 0; i < len; i++){
				if(newVersion == ""){
					newVersion = versionArr[i];
				} else {
					newVersion += "." + versionArr[i];
				}
			}
			$("#docVersion").val(newVersion);
		}
	})();
	
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
	    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var oldVersion = $("#oldVersion").val();
		var docVersion = $("#docVersion").val();
		// 校验新版本有没有大于旧版本
		var result = checkVersion(oldVersion, docVersion);
		
		if(!result.success){
			art.dialog({
				content:result.msg,
			});
			
			return
		}
		$(".easy_upload_head_btn1").click();
	});
});

// 校验新版本号是否大于旧版本号
function checkVersion(oldVersion, docVersion){
	var result = {};
	result["msg"] = "";
	result["success"] = true;
	if($.trim(docVersion) == ""){
		result["msg"] = "新版本号为空,保存失败";
		result["success"] = false;
		return result;
	}
	var oldArray = oldVersion.split(".");
	var newArray = docVersion.split(".");
	var index =0;
	index = oldArray.length > newArray.length ? newArray.length : oldArray.length;
	for(var i = 0 ; i < index ; i++){
		if(newArray[i] < oldArray[i]){
			result["msg"] = "新版本号必须大于旧版本号";
			result["success"] = false;
			return result;
		} 
		
		if(newArray[i] > oldArray[i]){
			result["msg"] = "";
			result["success"] = true;
			return result;
		}
		
		if(i == index-1 && oldArray.length >= newArray.length && oldArray[i] == newArray[i]){
			result["msg"] = "新版本号必须大于旧版本号";
			result["success"] = false;
			return result;
		}
	}
	
	return result;
}

function chgVersion(){
	var version = $("#docVersion").val().replace(/[^\d.]/g,'');;
	$("#docVersion").val(version)
}

function doUpdateVersion(){
	
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/doc/doUpdateVersion",
		type: "post",
		data: datas,
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
}
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="isForRegular" name="isForRegular" value="0"/>
						<input type="hidden" id="isNeedNotice" name="isNeedNotice" value="0"/>
						<input type="hidden" id="dateStr" name="dateStr" value="${doc.dateStr }"/>
						<input type="hidden" id="pk" name="pk" value="${doc.pk }"/>
						<input type="hidden" id="path" name="path" value=""/>
						<input type="hidden" id="folderCode" name="folderCode"/>
						<input type="hidden" id="attrDatas" name="attrDatas" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>文档名称</label>
									<input type="text" id="docName" name="docName" style="width:450px;" value="${doc.docName }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>目录</label>
									<input type="text" id="folderPK" name=folderPK style="width:160px;"/>
								</li>
								<li>
									<label>文档编号</label>
									<input type="text" id="docCode" name="docCode" 
										style="width:160px;" value="${doc.docCode }" readonly="true"/>
								</li>
								<li>
									<label>旧版本号</label>
									<input type="text" id="oldVersion" name="oldVersion" style="width:160px;"
									 value="${doc.docVersion }" placeholder="例：1.0" readonly="true" onkeyup="chgVersion()"/>
								</li>
								<li>
									<label><span class="required">*</span>新版本号</label>
									<input type="text" id="docVersion" name="docVersion" style="width:160px;"
									  placeholder="例：1.0"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>生效日期</label>
									<input type="text" id="enableDate" name="enableDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
									value="<fmt:formatDate value='${doc.enableDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
								<li class="form-validate">
									<label>失效时间</label>
									<input type="text" id="expiredDate" name="expiredDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
									value="<fmt:formatDate value='${doc.expiredDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
								<li class="form-validate">
									<label>拟定人</label>
									<input type="text" id="drawUpCZY" name="drawUpCZY" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>拟定日期</label>
									<input type="text" id="drawUpDate" name="drawUpDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${doc.drawUpDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>审核人</label>
									<input type="text" id="confirmCZY" name="confirmCZY" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>审核日期</label>
									<input type="text" id="confirmDate" name="confirmDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${doc.confirmDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
								<li class="form-validate">
									<label>审批人</label>
									<input type="text" id="approveCZY" name="approveCZY" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>审批日期</label>
									<input type="text" id="approveDate" name="approveDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${doc.approveDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>关键字</label>
									<input type="text" id="keyWords" name="keyWords" style="width:450px;" 
											value="${doc.keyWords }" placeholder="支持多个关键字，空格隔开" readonly="true"/>
								</li>
								<li class="form-validate">
									<label class="control-textarea">内容简介</label>
									<textarea id="briefDesc" name="briefDesc" readonly="true">${doc.briefDesc }</textarea>
								</li>
							</div>
							<div class="validate-group row">
								<li style="padding-left:130px">
									<input type="checkbox" id="isForRegular_chg" name="isForRegular_chg"
							 			${doc.isForRegular == 1 ? 'checked':'' } disabled="true"/>
							 		<p style="float:right;margin-top:5px">员工可看</p>
								</li>
								<%--<li>	
									<input type="checkbox" id="isNeedNotice_chg" name="isNeedNotice_chg"
							 			 ${doc.isNeedNotice == 1 ? 'checked':'' } disabled="true"/>
							 		<p style="float:right;margin-top:5px">消息通告</p>
								</li>	
							--%></div>
						</ul>
					</form>
				</div>
			</div>
			<div id="easyContainer"></div>
		</div>
	</body>	
</html>
