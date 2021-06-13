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

</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label>编号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${sendRegion.no}" 
									readonly="readonly" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${sendRegion.descr}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>主材配送费</label>
								<input type="text" id="transFee" name="transFee" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="${sendRegion.transFee}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>软装配送费</label>
								<input type="text" id="softTransFee" name="softTransFee" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="${sendRegion.softTransFee}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>发货类型</label>
								<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" style="width:160px;" value="${sendRegion.sendType}"/>
							</li>
						</div>
						<div class="row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -100px;">备注</label>
								<textarea id="remarks" name="remarks" 
									style="height: 120px;width: 160px;">${sendRegion.remarks}</textarea>
							</li>
						</div>
						<div class="row">
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired" name="expired" value="${sendRegion.expired}" 
									onclick="checkExpired(this)" ${sendRegion.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var oldDescr = $("#descr").val();
$(function() {
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			descr:{ 
				validators: {  
					notEmpty: {  
						message: "名称不允许为空"  
					},
					remote: {
			            message: "该名称重复",
			            data:{oldDescr:oldDescr},
			            url: "${ctx}/admin/sendRegion/checkDescr",
			            delay:1000, //每输入一个字符，就发ajax请求，服务器压力还是太大，
			            type:"post", //设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
			        }
				}  
			},
			transFee:{ 
				validators: {  
					notEmpty: {  
						message: "运费不允许为空"  
					}
				}  
			},
		}
	});
	
	$("#page_form").data("bootstrapValidator").validateField("descr");//在操作之前先做校验避免点两次保存按钮

	if ("V" == "${sendRegion.m_umState}") {
		$("#page_form input[type='text']").prop("readonly",true);
		$("#remarks").prop("readonly",true);
		$("#expired").prop("disabled",true);
		$("#saveBtn").remove();
	} else {
		$("#descr").focus();
	}

	// 获得原始数据
	var originalData = $("#page_form").serialize();

	$("#closeBut").on("click",function(){
		var changeData = $("#page_form").serialize();
		if (originalData != changeData) {
			art.dialog({
				content: "数据已变动,是否保存？",
				width: 200,
				okValue: "确定",
				ok: function () {
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

	//保存
	$("#saveBtn").on("click",function(){
		doSave();
	});
});

function doSave(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
		
	var datas = $("#page_form").jsonForm();

	$.ajax({
		url:"${ctx}/admin/sendRegion/doUpdate",
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
