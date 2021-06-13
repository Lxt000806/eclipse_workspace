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
	<title>文档管理编辑</title>
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
	var docAttrs ;
	if(${docAttrs}){
		docAttrs = ${docAttrs};
	}
	
	if(docAttrs && docAttrs.length>0){
		getLiHtml(docAttrs);
	}

	$('#easyContainer').easyUpload({
		allowFileTypes: 'jpg,doc,pdf,docx,xls,png,jpeg,bmp,xlsx,ppt,pptx',//允许上传文件类型，
		allowFileSize: myRound("${docAttMaxSize}")*1024,//允许上传文件大小(KB)
		selectText: '选择附件',//选择文件按钮文案
		multi: true,//是否允许多文件上传
		multiNum: "${docAttMaxCnt}",//多文件上传时允许的文件数
		showNote: true,//是否展示文件上传说明
		note: '提示：最多上传'+"${docAttMaxCnt}"+'个附件，支持格式为:'+"jpg,doc,pdf,docx,xls,png,jpeg,bmp,xlsx,ppt,pptx",//文件上传说明
		noteSize:'最大上传文件大小为:'+"${docAttMaxSize}"+"M",//文件上传大小说明
		showPreview: true,//是否显示文件预览
		url: '${ctx}/admin/doc/uploadDoc',//上传文件地址
		fileName: 'file',//文件filename配置参数
		formParam: {//这种也可以传，只能传页面加载成功时的数据 
			
		},//文件filename以外的配置参数，格式：{key1:value1,key2:value2}
		timeout: 60000,//请求超时时间
		okCode: "true",//与后端返回数据code值一致时执行成功回调，不配置默认200
		uploadBtn: true, // 是否显示上传按钮
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
			if($("#multiNum").val() == 0){
				art.dialog({
					content:"最多只能上传"+"${docAttMaxCnt}"+"个附件",
				});
				return false;
			}
			return true;
		},
		aftSelectFile:function(){
			$('.head_check').click();
		},
		completeFun:function(res){// 所有图片上传完成后触发
			$.ajax({
				url:"${ctx}/admin/doc/getDocAtt",
				type: "post",
				data: {pk:$("#pk").val()},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj){
			    		$("#attUl").find("li").remove();
			    		if(obj.length>0){
			    			getLiHtml(obj);
			    		}
			    		$(".easy_upload_head_btn2").click();
			    	}
			    }
			});
		},
		errorFunc: function(res) {
			console.log('失败了', res);
		},//上传失败回调函数
		deleteFunc: function(res) {
			console.log('删除回调', res);
		},//删除文件回调函数
	});
	
	if("true" == "${hasFiledDoc }"){
		$("#docName").attr("readonly",true);
		$("#docCode").attr("readonly",true);
		$("#docVersion").attr("readonly",true);
	}
	
	function getFolderData(data){
		if(!data) return;
		// $("#path").val(data.path);
		if(data.folderCode){
			$("#folderCode").val($.trim(data.folderCode));
		} 
		if(data.foldercode){
			$("#folderCode").val($.trim(data.foldercode));
		}
	}
	$("#folderPK").openComponent_docFolder({showValue:"${doc.folderPK}",readonly:true,callBack:getFolderData});
	$("#openComponent_docFolder_folderPK").blur();
	$("#openComponent_docFolder_folderPK").attr("readonly",true);
	$("#drawUpCZY").openComponent_employee({showValue:"${doc.drawUpCZY}"});
	$("#confirmCZY").openComponent_employee({showValue:"${doc.confirmCZY}"});
	$("#approveCZY").openComponent_employee({showValue:"${doc.approveCZY}"});
	$("#openComponent_employee_drawUpCZY").blur();
	$("#openComponent_employee_confirmCZY").blur();
	$("#openComponent_employee_approveCZY").blur();
	
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
		                message: "拟定人别不能为空"
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
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:"${ctx}/admin/doc/doUpdate",
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
	});
});

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

function checkDate(){
	$("#dataForm").data("bootstrapValidator")
    .updateStatus("expiredDate", "NOT_VALIDATED",null)  
    .validateField("expiredDate"); 
	$("#dataForm").data("bootstrapValidator")
    .updateStatus("enableDate", "NOT_VALIDATED",null)  
    .validateField("enableDate");
	$("#dataForm").data("bootstrapValidator")
    .updateStatus("drawUpDate", "NOT_VALIDATED",null)  
    .validateField("drawUpDate");
}

function chgVersion(){
	var version = $("#docVersion").val().replace(/[^\d.]/g,'');;
	$("#docVersion").val(version);
}

function delAtt(pk, index){
	art.dialog({
		content:"是否确定删除该附件",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
			$.ajax({
				url:"${ctx}/admin/doc/doDelAtt",
				type: "post",
				data: {pk:pk},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		var li = document.getElementById("li_"+index);
						if(li){
							li.parentNode.removeChild(li);
							var liLen = $("#attUl").find("li").length;
							$("#multiNum").val(myRound("${docAttMaxCnt}") - liLen);
						}
						
			    		art.dialog({
							content: "删除成功",
							time: 1000,
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
			
		},
		cancel: function () {
			return true;
		}
	});
}

function getLiHtml(docAttrs){
	for(var i = 0; i<docAttrs.length; i++){
		var li="<li id='li_"+i+"' style='float: left;margin-right:20px;margin-top:5px;padding-right:50px'>"
		+ "<img width='14px' height='14px' style='margin-top:-2px;padding-right:1px' src=' ../../images/attIcon.jpg' />"
		+ "<span style='padding-right:5px'>"+docAttrs[i].fileName+"</span>"
		+ "<a style='font-size:8px' href='javascript:void(0)' onclick='delAtt("+docAttrs[i].pk+","+i+")'>删除</a>"
		+ "</li>";
		$("#attUl").append(li);
	}
	$("#multiNum").val(myRound("${docAttMaxCnt}") - docAttrs.length);
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
						<input type="hidden" id="isNeedNotice" name="isNeedNotice" value="${doc.isNeedNotice }"/>
						<input type="hidden" id="dateStr" name="dateStr" value="${doc.dateStr }"/>
						<input type="hidden" id="attrDatas" name="attrDatas" value=""/>
						<input type="hidden" id="pk" name="pk" value="${doc.pk }"/>
						<input type="hidden" id="folderCode" name="folderCode" value=""/>
						<input type="hidden" id="path" name="path" value=""/>
						<input type="hidden" id="multiNum" name="multiNum" value="5"/>
						<input type="hidden" id="u_umState" name="u_umState" value="M"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>文档名称</label>
									<input type="text" id="docName" name="docName" style="width:450px;" value="${doc.docName }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>目录</label>
									<input type="text" id="folderPK" name=folderPK style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>文档编号</label>
									<input type="text" id="docCode" name="docCode" 
										style="width:160px;" value="${doc.docCode }"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>版本号</label>
									<input type="text" id="docVersion" name="docVersion" style="width:160px;"
									 value="${doc.docVersion }" placeholder="例：1.0" onkeyup="chgVersion()"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>员工可看</label>
									<house:xtdm id="isForRegular" dictCode="YESNO" value="${doc.isForRegular }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>生效日期</label>
									<input type="text" id="enableDate" name="enableDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
									value="<fmt:formatDate value='${doc.enableDate }' pattern='yyyy-MM-dd'/>" onchange="checkDate()"/>
								</li>
								<li class="form-validate">
									<label>失效时间</label>
									<input type="text" id="expiredDate" name="expiredDate" class="i-date"
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd', minDate:getMinDate()})"
									value="<fmt:formatDate value='${doc.expiredDate }' pattern='yyyy-MM-dd'/>" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>拟定人</label>
									<input type="text" id="drawUpCZY" name="drawUpCZY" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>拟定日期</label>
									<input type="text" id="drawUpDate" name="drawUpDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${doc.drawUpDate }' pattern='yyyy-MM-dd'/>" onchange="checkDate()"/>
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
										value="<fmt:formatDate value='${doc.confirmDate }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="form-validate">
									<label>审批人</label>
									<input type="text" id="approveCZY" name="approveCZY" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>审批日期</label>
									<input type="text" id="approveDate" name="approveDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${doc.approveDate }' pattern='yyyy-MM-dd'/>"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>关键字</label>
									<input type="text" id="keyWords" name="keyWords" style="width:448px;" 
											value="${doc.keyWords }" placeholder="支持多个关键字，空格隔开"/>
								</li>
								<li class="form-validate">
									<label class="control-textarea">内容简介</label>
									<textarea id="briefDesc" name="briefDesc">${doc.briefDesc }</textarea>
								</li>
							</div>
								<%-- 暂时没使用消息通告功能
								<div class="validate-group row">
								<li>	
									<input type="checkbox" id="isNeedNotice_chg" name="isNeedNotice_chg"
							 			 onclick="chgIsNeedNotice(this)" ${doc.isNeedNotice == 1 ? 'checked':'' }/>
							 		<p style="float:right;margin-top:5px">消息通告</p>
								</li>	
								</div>
							--%>
						</ul>
					</form>
				</div>
			</div>
			<div style="padding-bottom: 15px ;display:inline-block;width:100%;border:1px solid rgb(216,216,216);margin-top:-10px">
				<label style="padding-left:8px;margin-top:8px">已上传附件：</label>
				<ul id="attUl" style="padding-left:8px">
				</ul>
			</div>
			<div class="clear_float" style="height:13px"></div>
			<div id="easyContainer"></div>
		</div>
	</body>	
</html>
