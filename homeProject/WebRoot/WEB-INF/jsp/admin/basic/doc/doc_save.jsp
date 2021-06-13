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
		allowFileTypes: 'jpg,doc,pdf,docx,xls,png,jpeg,bmp,xlsx,ppt,pptx',//允许上传文件类型
		allowFileSize: myRound("${docAttMaxSize}")*1024,//允许上传文件大小(KB)
		selectText: '选择附件',//选择文件按钮文案
		multi: true,//是否允许多文件上传
		multiNum: "${docAttMaxCnt}",//多文件上传时允许的文件数
		showNote: true,//是否展示文件上传说明
		note: '提示：单次最多上传'+"${docAttMaxCnt}"+'张，支持格式为:'+"jpg,doc,pdf,docx,xls,png,jpeg,bmp,xlsx,ppt,pptx",//文件上传说明
		noteSize:'最大上传文件大小为:'+"${docAttMaxSize}"+"M",//文件上传大小说明
		showPreview: false,//是否显示文件预览
		url: '${ctx}/admin/doc/uploadDoc',//上传文件地址
		fileName: 'file',//文件filename配置参数
		formParam: {//这种也可以传，只能传页面加载成功时的数据
			
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
			doSave();
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
	$("#folderPK").openComponent_docFolder({showValue:"${doc.folderPK}",callBack:getFolderData});
	$("#openComponent_docFolder_folderPK").blur();
	$("#openComponent_docFolder_folderPK").attr("readonly",true);
	$("#drawUpCZY").openComponent_employee();
	$("#confirmCZY").openComponent_employee();
	$("#approveCZY").openComponent_employee();
	
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			folderPK:{
				validators:{
					notEmpty:{
						message:"目录不能为空"
					}
				}
			},
			docVersion:{
				validators:{
					notEmpty:{
						message:"版本号不能为空"
					}
				}
			},
			docName:{
				validators:{
					notEmpty:{
						message:"文档名称不能为空"
					}
				}
			},
			docCode:{
				validators:{
					notEmpty:{
						message:"文档编号不能为空"
					}
				}
			},
			enableDate:{
				validators:{
					notEmpty:{
						message:"生效时间不能为空"
					}
				}
			},
			drawUpDate:{
				validators:{
					notEmpty:{
						message:"拟定时间不能为空"
					}
				}
			},
			openComponent_employee_drawUpCZY:{  
		        validators: {  
					notEmpty: {
		                message: "拟定人不能为空"
		            },
				}  
		    },
		    isForRegular:{  
		        validators: {  
					notEmpty: {
		                message: "员工可看不能为空"
		            },
				}  
		    },
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
	
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
	    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
		$(".easy_upload_head_btn1").click();
	});
});

function doSave(){
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/doc/doSave",
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

function chgIsForRegular(obj){
	if ($(obj).is(":checked")){
		$("#isForRegular").val("1");
	}else{
		$("#isForRegular").val("0");
	}
}

function chgIsNeedNotice(obj){
	if ($(obj).is(":checked")){
		$("#isNeedNotice").val("1");
	}else{
		$("#isNeedNotice").val("0");
	}
}
function getMinDate(){
	
	return getAddDate($("#enableDate").val(),1);
}
function checkDate(dateName){
	$("#dataForm").data("bootstrapValidator")
    .updateStatus(dateName, "NOT_VALIDATED",null)  
    .validateField(dateName); 
}

function chgVersion(){
	var version = $("#docVersion").val().replace(/[^\d.]/g,'');;
	$("#docVersion").val(version)
}

function chgDateTo(){
	var dateFrom = $("#enableDate").val();
	var dateTo= $("#expiredDate").val();
	
	if(dateFrom != "" && dateTo != "" && dateFrom >dateTo){
		$("#expiredDate").val("");
	}
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
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="isNeedNotice" name="isNeedNotice" value="0"/>
						<input type="hidden" id="dateStr" name="dateStr" value="${doc.dateStr }"/>
						<input type="hidden" id="fullNames" name="fullNames" value=""/>
						<input type="hidden" id="attrDatas" name="attrDatas" value=""/>
						<input type="hidden" id="folderCode" name="folderCode" value="${folderCode }"/>
						<input type="hidden" id="path" name="path" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>文档名称</label>
									<input type="text" id="docName" name="docName" style="width:450px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>目录</label>
									<input type="text" id="folderPK" name=folderPK style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>文档编号</label>
									<input type="text" id="docCode" name="docCode" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>版本号</label>
									<input type="text" id="docVersion" name="docVersion" style="width:160px;" onkeyup="chgVersion()" placeholder="例：1.0"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>员工可看</label>
									<house:xtdm id="isForRegular" dictCode="YESNO"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>生效日期</label>
									<input type="text" id="enableDate" name="enableDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',onpicking:chgDateTo()})" onchange="checkDate('enableDate')"/>
								</li>
								<li class="form-validate">
									<label>失效时间</label>
									<input type="text" id="expiredDate" name="expiredDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate:['#F{getMinDate()}']})"
									onchange="checkDate('expiredDate')"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>拟定人</label>
									<input type="text" id="drawUpCZY" name="drawUpCZY" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>拟定日期</label>
									<input type="text" id="drawUpDate" name="drawUpDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" onchange="checkDate('drawUpDate')"/>
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
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li class="form-validate">
									<label>审批人</label>
									<input type="text" id="approveCZY" name="approveCZY" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>审批日期</label>
									<input type="text" id="approveDate" name="approveDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>关键字</label>
									<input type="text" id="keyWords" name="keyWords" style="width:448px;" placeholder="支持多个关键字，空格隔开"/>
								</li>
								<li class="form-validate">
									<label class="control-textarea">内容简介</label>
									<textarea id="briefDesc" name="briefDesc" rows="3"></textarea>
								</li>
							</div>
							<%--
								<div class="validate-group row">
								<li>	
									<input type="checkbox" id="isNeedNotice_chg" name="isNeedNotice_chg"
							 			 onclick="chgIsNeedNotice(this)"/>
							 		<p style="float:right;margin-top:5px">消息通告</p>
								</li>	
								</div>
							--%>
						</ul>
					</form>
				</div>
			</div>
			<div id="easyContainer"></div>
		</div>
	</body>	
</html>
