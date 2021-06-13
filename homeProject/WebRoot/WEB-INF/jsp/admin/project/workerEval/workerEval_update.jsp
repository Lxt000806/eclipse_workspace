<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>

	<!-- bootstrap星星评分插件 -->
	<link rel="stylesheet" href="${resourceRoot}/bootstrap-star-rating/4.05/css/star-rating.min.css?v=20181125" type="text/css">
	<script src="${resourceRoot}/bootstrap-star-rating/4.05/js/star-rating.min.js?v=20181125" type="text/javascript"></script>
	
	<style type="text/css">
		.form-search .ul-form li label {
			width: 140px;
		}
		.panel-info {
			margin: 0px;
		}
		.form-search .ul-form li .control-textarea {
			top: -80px;
		}
		#remark {
			width: 160px;height: 100px;
		}
		.div-star-rating {
			position: relative;
			top: -9px;
			left: 6px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="doClose()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="expired" name="expired" value="${workerEval.expired}"/>
					<input type="hidden" id="PK" name="PK" value="${workerEval.pk}"/>
					<input type="hidden" name="custWkPk" value="${workerEval.custwkpk}">
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>工人编码</label> 
								<input type="text" id="workerCode" name="workerCode" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>来源</label> 
								<house:xtdm id="type" dictCode="EVALSRC" style="width:160px;" value="${workerEval.type}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>客户编码</label> 
								<input type="text" id="custCode" name="custCode" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>卫生评分</label>
								<input type="text" id="healthScore" name="healthScore" class="input-rating" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${workerEval.healthscore}" />
							</li>
							<div class="div-star-rating" hidden>
								<input id="healthScoreStarRating" name="healthScoreStarRating" type="text" class="star-rating" 
									data-min=0 data-max=5 data-step=1 data-size="sm" value="${workerEval.healthscore}" />
							</div>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>工具评分</label>
								<input type="text" id="toolScore" name="toolScore" class="input-rating" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${workerEval.toolscore}" />
							</li>
							<div class="div-star-rating" hidden>
								<input id="toolScoreStarRating" name="toolScoreStarRating" type="text" class="star-rating" 
									data-min=0 data-max=5 data-step=1 data-size="sm" value="${workerEval.toolscore}" />
							</div>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>综合评分</label>
								<input type="text" id="score" name="score" class="input-rating" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${workerEval.score}" />
							</li>
							<div class="div-star-rating" hidden>
								<input id="scoreStarRating" name="scoreStarRating" type="text" class="star-rating" 
									data-min=0 data-max=5 data-step=1 data-size="sm" value="${workerEval.score}" />
							</div>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>评价人</label> 
								<input type="text" id="evaMan" name="evaMan" style="width:160px;" readonly="readonly" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>日期</label>
								<input type="text" id="date" name="date" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${workerEval.date}' pattern='yyyy-MM-dd'/>" disabled />
							</li>
						</div>
						<div class="row">
							<li class="form-validate" hidden="true">
								<label><span class="required">*</span>评价工人</label> 
								<input type="text" id="evalWorker" name="evalWorker" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li style="max-height: 120px;">
								<label class="control-textarea">备注</label>
								<textarea id="remark" name="remark">${workerEval.remark}</textarea>
							</li>
						</div>
						<div class="row">
							<li>
								<label for="expired_show">过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${workerEval.expired}" 
									onclick="checkExpired(this)" ${workerEval.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" defer>
var m_umState = "${workerEval.m_umState}";
$(function() {
	$("#workerCode").openComponent_worker({
		callBack:workerValidateRefresh,
		showValue:"${workerEval.workercode}",
		showLabel:"${workerEval.workerdescr}",
		disabled:true,
	});
	// $("#evaMan").val("${workerEval.evaman}");
	$("#evaMan").openComponent_employee({
		showValue:"${workerEval.evaman}",
		showLabel:"${workerEval.evamandescr}",
		disabled:true,
	});
	$("#evalWorker").openComponent_worker({
		callBack:evalWorkerValidateRefresh,
		showValue:"${workerEval.evalworker}",
		showLabel:"${workerEval.evalworkerdescr}",
	});
	$("#custCode").openComponent_customer({
		callBack:custValidateRefresh,
		showValue:"${workerEval.custcode}",
		showLabel:"${workerEval.custname}",
	});

	if ("V" == m_umState) {
		$("#saveBtn").css("display","none");
		disabledForm();
	}

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			openComponent_worker_workerCode:{ 
				validators: {  
					notEmpty: {  
						message: "工人编码不允许为空"  
					},
				}  
			},
			type:{ 
				validators: {  
					notEmpty: {  
						message: "来源不允许为空"  
					}
				}  
			},
			openComponent_customer_custCode:{ 
				validators: {  
					notEmpty: {  
						message: "客户编号不允许为空"  
					}
				}  
			},
			openComponent_employee_evaMan:{ 
				validators: {  
					notEmpty: {  
						message: "评价人不允许为空"  
					}
				}  
			},
			date:{ 
				validators: {  
					notEmpty: {  
						message: "日期不允许为空"  
					}
				}  
			},
			score:{ 
				validators: {  
					notEmpty: {  
						message: "分数不允许为空"  
					}
				}  
			},
			openComponent_worker_evalWorker:{
				validators: {  
					notEmpty: {  
						message: "评价工人不允许为空"  
					}
				}
			},
		}
	});

	// 星星评分初始化
	$(".star-rating").rating({
		defaultCaption: "{rating} 分",
		starCaptions: function(val) {
			return val+" 分";
		},
		clearButtonTitle: "清零",
		clearCaption: "0分",
		clearValue: 0,
		// hoverChangeCaption: false,
		hoverOnClear: false,
	});

	$(".star-rating").on("rating:clear", function(event) {
		var star_rating_id = event.currentTarget.id;
		var input_rating_id = event.currentTarget.id.replace("StarRating","");
		$("#"+star_rating_id).val(0);
		$("#"+input_rating_id).val(0);
	});

	//caption:span标签
	$(".star-rating").on("rating:change", function(event, value, caption) {
		var input_rating_id = event.currentTarget.id.replace("StarRating","");
		$("#"+input_rating_id).val(value);
	});

	$("#type").on("change",function first(){
		var type = $(this).val();
		typeChange1(type);
		typeChange2(type);		
	});

	//触发来源事件
	typeChange1("${workerEval.type}");
	typeChange3("${workerEval.type}");
	// $("#type").triggerHandler("change");
});

function typeChange1(type){
	var label_first = $("#custCode").parent().find("label:first");
	label_first.find("span").remove(".required"); //移除必填标识
	$("#page_form").bootstrapValidator("addField", "openComponent_customer_custCode", {
		validators: {  
			notEmpty: {  
				message: "客户编号不允许为空",
			}
		}  
	});
	if ("6" == type) {
		$("#page_form").bootstrapValidator("removeField","openComponent_customer_custCode");
	} else {
		label_first.prepend("<span class='required'>*</span>");
	}
	if ("2" == type) {
		$("#evalWorker").parent().prop("hidden",false);
	} else {
		$("#evalWorker").parent().prop("hidden",true);
		$("#evalWorker").val("");
		$("#openComponent_worker_evalWorker").val("");
	}
}

function typeChange2(type) {
	// 初始化评分组件
	$(".input-rating").val("0");
	$(".star-rating").rating("clear");
	$(".div-star-rating").attr("hidden",true);
	$(".input-rating").attr("hidden",true);
	// 当来源为1 客户，2 工人，3 项目经理时，对评分做限制
	if ("1" == type || "2" == type || "3" == type) {
		$(".div-star-rating").removeAttr("hidden");
	} else {
		$(".input-rating").removeAttr("hidden");
	}
}

function typeChange3(type) {
	// 初始化评分组件
	$(".div-star-rating").attr("hidden",true);
	$(".input-rating").attr("hidden",true);
	// 当来源为1 客户，2 工人，3 项目经理时，对评分做限制
	if ("1" == type || "2" == type || "3" == type) {
		$(".div-star-rating").removeAttr("hidden");
	} else {
		$(".input-rating").removeAttr("hidden");
	}
}

function workerValidateRefresh() {
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_worker_workerCode", "NOT_VALIDATED", null)
		.validateField("openComponent_worker_workerCode");
}

function custValidateRefresh() {
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_customer_custCode", "NOT_VALIDATED", null)
		.validateField("openComponent_customer_custCode");
}

function evalWorkerValidateRefresh() {
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_worker_evalWorker", "NOT_VALIDATED", null)
		.validateField("openComponent_worker_evalWorker");
}

function doClose(){
	closeWin();
}

function doSave(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
	var datas = $("#page_form").jsonForm();
	saveAjax(datas);
}

// 保存ajax
function saveAjax(datas) {
	if ("V" == m_umState) return;
	$.ajax({
		url:"${ctx}/admin/workerEval/doUpdate",
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
