<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加CustContractTemp</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		.easy_upload-container .easy_upload_queue .easy_upload_queue_item .easy_upload_btn .easy_upload_upbtn{
			margin-bottom: 0px !important;
		}
	</style>
<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate'); //验证表单
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
	var rets = $("#dataTable").jqGrid("getRowData");
	var params = {"custContractTempFieldJson": JSON.stringify(rets)};
	Global.Form.submit("dataForm","${ctx}/admin/custContractTemp/doSave",params,function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 1000,
				beforeunload: function () {
			    	closeWin();
				}
			});
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			 art.dialog({
				content: ret.msg,
			});
		}			
	});
};

$(function() {
	$("#builderCode").openComponent_builder({showValue:'${custContractTemp.builderCode}',showLabel:'${custContractTemp.builderDescr}'});

	if("${custContractTemp.m_umState}" != "A" && "${custContractTemp.m_umState}" != "U"){
		var custContractTempFileName = ${custContractTempFileName};
		if(custContractTempFileName.fileName){
			getLiHtml(custContractTempFileName);
			$("#flag").val("1");
		}
	}	
	
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
		},
		fields: {  
			custType:{  
				validators: {  
					notEmpty: {  
						message: '客户类型不能为空'  
					}
				}  
			},
			type:{  
				validators: {  
					notEmpty: {  
						message: '合同类型不能为空'  
					}
				}  
			},
			descr:{  
				validators: {  
					notEmpty: {  
						message: '模板名称不能为空'  
					}
				}  
			},
			version:{  
				validators: {  
					notEmpty: {  
						message: '版本号不能为空'  
					}
				}  
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
	if("${custContractTemp.m_umState}"=="V"){
		$("#saveBtn,input,textarea,select,#addItem,#updateItem,#copyItem,#delItem").attr("disabled",true);
		$("#easyContainer").hide();
	}
	if("${custContractTemp.m_umState}"=="U"){
		$("input,textarea,select").attr("disabled",true);
		$("#version").attr("disabled",false);
		$("#builderCode").attr("disabled",false);
	}
	
	$('#easyContainer').easyUpload({
		allowFileTypes: 'doc,docx',//允许上传文件类型
		allowFileSize: myRound("${docAttMaxSize}")*1024,//允许上传文件大小(KB)
		selectText: '选择附件',//选择文件按钮文案
		multi: false,//是否允许多文件上传
		showNote: true,//是否展示文件上传说明
		note: '提示：单次最多上传一份，支持格式为:'+"doc,docx",//文件上传说明
		noteSize:'最大上传文件大小为:'+"${docAttMaxSize}"+"M",//文件上传大小说明
		showPreview: false,//是否显示文件预览
		url: '${ctx}/admin/custContractTemp/uploadCustContractTemp',//上传文件地址
		fileName: 'file',//文件filename配置参数
		formParam: {//这种也可以传，只能传页面加载成功时的数据
			
		},//文件filename以外的配置参数，格式：{key1:value1,key2:value2}
		timeout: 60000,//请求超时时间
		okCode: "1",//与后端返回数据code值一致时执行成功回调，不配置默认200
		uploadBtn: true, // 是否显示上传按钮
		formId:"dataForm",
		successFunc: function(res) {//上传成功回调函数
			$("#fileName").val(res.msg);
		},
		befSelectFile:function(){
			if($("#flag").val() == "1"){
				art.dialog({
					content:"最多只能上传1个附件",
				});
				return false;
			}else{
				return true;
			}
		},
		befUpload:function(res){ // 上传前触发 返回boolean
			if(res.length<=0){
				art.dialog({
					content:"请至少选择一个文档",
				});
				return false;
			}
			$('.head_check').click();
			return true;
		},
		completeFun:function(res){// 所有图片上传完成后触发
			
		},
		errorFunc: function(res) {
			console.log('失败了', res);
		},//上传失败回调函数
		deleteFunc: function(res) {
			console.log('删除回调', res);
		},//删除文件回调函数
	});
});

function delCustContractTempFile(pk, index){
	art.dialog({
		content:"是否确定删除该附件",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
			$.ajax({
				url:"${ctx}/admin/custContractTemp/doDelFile",
				type: "post",
				data: {pk:pk},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		var li = document.getElementById("li");
						if(li){
							li.parentNode.removeChild(li);
							$("#flag").val("0");
						}
						$("#fileName").val("");
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

function getLiHtml(custContractTempFileName){
	var li="<li id='li"+"' style='float: left;margin-right:20px;margin-top:5px;padding-right:50px'>"
	+ "<img width='14px' height='14px' style='margin-top:-2px;padding-right:1px' src=' ../../images/attIcon.jpg' />"
	+ "<span style='padding-right:5px'>"+custContractTempFileName.fileName+"</span>";
	if("${custContractTemp.m_umState}" != "V" && "${custContractTemp.m_umState}" != "U" ){
		li+= "<a style='font-size:8px' href='javascript:void(0)' onclick='delCustContractTempFile("+custContractTempFileName.pk+")'>删除</a>"
	}
	li+= "</li>";
	$("#attUl").append(li);
}

function chgVersion(){
	var version = $("#version").val().replace(/[^\d.]/g,'');
	$("#version").val(version);
}
</script>

</head>
    
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system "
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system "
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<div class="body-box-form">
					<form role="form" class="form-search" id="dataForm" action="" enctype="multipart/form-data" 
						method="post" target="targetFrame">
						<input type="hidden" id="m_umState" name="m_umState" value="${custContractTemp.m_umState}" />
						<input type="hidden" id="pk" name="pk" value="${custContractTemp.pk}" />
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id=flag name="flag" value=""/>
						<input type="hidden" id="fileName" name="fileName" value="${custContractTempFileName.fileName}"/>
						<input type="hidden" id="expired" name="expired" value="${custContractTemp.expired}"/>
						<house:token></house:token>
						<ul class="ul-form">
							<div class="validate-group row">
					  			<li class="form-validate">
									<label><span class="required">*</span>客户类型</label>
									<house:xtdm id="custType" dictCode="CUSTTYPE" value="${custContractTemp.custType}" ></house:xtdm>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>模板名称</label>
									<input type="text" id="descr" name="descr"  value="${custContractTemp.descr}"  />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>版本号</label>
									<input type="text" id="version" name="version"  value="${custContractTemp.version}" onkeyup="chgVersion()" placeholder="例：1.0"  />
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
					        		<label><span class="required">*</span>合同类型</label>
					        		<house:xtdm id="type" dictCode="CUSTCONTEMPTYPE" value="${custContractTemp.type}"  ></house:xtdm>
								</li>
								<li class="form-validate">
									<label>项目编号</label>
									<input type="text" id="builderCode" name="builderCode" value="${custContractTemp.builderCode}"  />
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">模板描述：</label>
									<textarea id="remarks" name="remarks">${custContractTemp.remarks }</textarea>
								</li>
							</div>
							<c:if test="${custContractTemp.m_umState !='A'}">
								<li>
									<label>过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${custContractTemp.expired}" 
										onclick="checkExpired(this)" ${custContractTemp.expired=="T"?"checked":""}/>
								</li>
							</c:if>
						</ul>
					</form>
				</div>
			</div>
			<c:if test="${not empty custContractTemp.fileName}">
				<div style="padding-bottom: 15px ;display:inline-block;width:100%;border:1px solid rgb(216,216,216);margin-top:-10px">
					<label style="padding-left:8px;margin-top:8px">已上传附件：</label>
					<ul id="attUl" style="padding-left:8px">
					</ul>
				</div>
				<div class="clear_float" style="height:13px"></div>
			</c:if>
			<div id="easyContainer"></div>
		</div>
 		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li id="tabsalesInvoice" class="active"><a href="#tab_detail"
					data-toggle="tab">模板文本域信息</a></li>
			</ul>
			 <div class="tab-content">
				<div id="tab_detail" class="tab-pane fade in active">
					<jsp:include page="custContractTemp_tab_detail.jsp"></jsp:include>
				</div>
			</div> 
		</div>
	</div>
</body>
</html>
