<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>

	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;height: 221px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="expired" name="expired" value="${wareHousePosi.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label for="pk">编码</label>
								<input type="text" id="pk" name="pk" style="width:160px;" value="${wareHousePosi.pk}" 
									placeholder="保存时生成" readonly="readonly"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>名称</label>
								<input type="text" id="desc1" name="desc1" style="width:160px;" value="${wareHousePosi.desc1}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>仓库</label>
								<input type="text" id="whcode" name="whcode" style="width:160px;" value="${wareHousePosi.whcode}"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${wareHousePosi.expired}" 
									onclick="checkExpired(this)" ${wareHousePosi.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var url,originalData;
$(function() {
	$("#whcode").openComponent_wareHouse({
		callBack:validateRefresh_whcode,
		showValue:"${wareHousePosi.whcode}",
		showLabel:"${wareHousePosi.whdescr}",
		condition:{
			isManagePosi:"1",
			czybh:"${sessionScope.USER_CONTEXT_KEY.czybh}"
		}
	});

	$("#page_form").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			desc1:{  
				validators: {  
					notEmpty: {  
						message: "名称不能为空"  
					},
					remote: {
			            message: "已经存在相同名称",
			            url: "${ctx}/admin/wareHousePosi/checkDesc1",
			            data:{desc1:$("#desc1").val(),pk:$("#pk").val()},
			            delay:1000, //每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
			            type:"post",
			            // cache: false
			        }
				}  
			},
			openComponent_wareHouse_whcode:{ 
				validators: {  
					notEmpty: {  
						message: "仓库不允许为空"  
					}
				}  
			}
		}
	});
	
	switch ("${wareHousePosi.m_umState}") {
	case "M":
		$("#openComponent_wareHouse_whcode").prop("disabled",true);
		// 禁用搜索按钮
		$("#openComponent_wareHouse_whcode").next().prop("disabled",true);
		url = "${ctx}/admin/wareHousePosi/doUpdate";
		// 解决remote里面ajax后无法继续走下去的问题
		$("#page_form").data("bootstrapValidator").validateField("desc1");
		break;
	case "V":
		$("#desc1").prop("disabled",true);
		$("#openComponent_wareHouse_whcode").prop("disabled",true);
		// 禁用搜索按钮
		$("#openComponent_wareHouse_whcode").next().prop("disabled",true);
		$("#expired_show").prop("disabled",true);
		// 不显示保存按钮
		$("#saveBtn").hide();
		break;
	default:
		$("li").last().prop("hidden",true);
		url = "${ctx}/admin/wareHousePosi/doSave";
		break;
	}

	// 获得原始数据
	originalData = $("#page_form").serialize();
	
	/* 关闭按钮绑定数据是否更改校验 */
	$("#closeBut").on("click",function(){
		var changeData = $("#page_form").serialize();
		if (originalData != changeData) {
			art.dialog({
				content: "数据已变动,是否保存？",
				width: 200,
				okValue: "确定",
				ok: function () {
					$("#page_form").bootstrapValidator("validate");/* 提交验证 */
					doSave();
				},
				cancelValue: "取消",
				cancel: function () {
					closeWin();
				}
			});
		} else {
			closeWin();
		}
		
	});

});

function validateRefresh_whcode(){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_wareHouse_whcode", "NOT_VALIDATED", null)
		.validateField("openComponent_wareHouse_whcode");
}

function doSave(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()){
		return;
	}  /* 验证失败return */	

	var changeData = $("#page_form").serialize();
	if (changeData == originalData) {
		art.dialog({
			content: "数据无任何变动",
			time: 1000
		});
		return;
	}
	var datas = $("#page_form").jsonForm();

	$.ajax({
		url:url,
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
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
</html>
