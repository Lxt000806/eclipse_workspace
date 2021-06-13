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
	
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="submit" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="${m_umState}">
					<input type="hidden" name="no" id="no" value="${custService.no}">
					<input type="hidden" name="expired" id="expired" value="${custService.expired}">
					<input type="hidden" name="custProblemPK" id="custProblemPK" value="${custService.custProblemPK}">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-xs-6">
								<li class="form-validate">
									<label for="custCode">客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;">
								</li>
								<li class="form-validate">
									<label for="address"><span class="required">*</span>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" 
										value="${customer.Address}" readonly="true">
								</li>
								<li>
									<label>客户结算时间</label>
									<input type="text" id="custCheckDate" name="custCheckDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${customer.custCheckDate}' pattern='yyyy-MM-dd'/>" disabled />
								</li>
								<li>
									<label for="consEndDate">竣工时间</label>
									<input type="text" id="consEndDate" name="consEndDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${customer.ConsEndDate}' pattern='yyyy-MM-dd'/>" disabled />
								</li>
								<li>
									<label for="mobile1">联系方式</label>
									<input type="text" id="mobile1" name="mobile1" style="width:160px;" 
										value="${customer.Mobile1}" readonly="readonly">
								</li>
								<li>
									<label for="mobile1">联系方式2</label>
									<input type="text" id="mobile2" name="mobile2" style="width:160px;" 
										value="${customer.Mobile2}" readonly="readonly">
								</li>
								<li>
									<label for="projectMan">项目经理</label>
									<input type="text" id="projectMan" name="projectMan" style="width:160px;">
									<span id="isWork">${customer.ProManStatusDescr}</span>
								</li>
								<li>
									<label for="department2">工程部</label>
									<input type="text" id="department2" name="department2" style="width:160px;" 
										value="${customer.Department2Descr}" readonly="readonly">
								</li>
								<li class="form-validate">
									<label for="repMan"><span class="required">*</span>报备人</label>
									<input type="text" id="repMan" name="repMan" style="width:160px;">
								</li>
							</div>
							<div class="col-xs-6">
								<li class="form-validate">
									<label for="repDate"><span class="required">*</span>报备日期</label>
									<input type="text" id="repDate" name="repDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${custService.repDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled="disabled"/>
								</li>
								<li>
									<label for="serviceMan">售后跟踪人员</label>
									<input type="text" id="serviceMan" name="serviceMan" style="width:160px;">
								</li>
								<li class="form-validate">
									<label for="dealMan">处理人</label>
									<input type="text" id="dealMan" name="dealMan" style="width:160px; " value="${custService.dealMan}">
								</li>
								<li class="form-validate">
									<label for="dealDate">处理日期</label>
									<input type="text" id="dealDate" name="dealDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${custService.dealDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="form-validate">
									<label for="type"><span class="required">*</span>类型</label>
									<house:xtdm id="type" dictCode="CUSTSRVTYPE" style="width:160px;" value="${custService.type}"/>
								</li>
								<li>
									<label for="status">状态</label>
									<house:xtdm id="status" dictCode="CUSTSRVSTATUS" value="${custService.status}" disabled="true"></house:xtdm>
								</li>
								<li class="form-validate">
									<label for="undertaker"><span class="required">*</span>承担方</label>
									<house:xtdm id="undertaker" dictCode="CUSTSRVUNTKER" value="${custService.undertaker}"></house:xtdm>
								</li>
							</div>
							<div class="col-sm-12">
								<li style="max-height: 120px;">
									<label class="control-textarea" style="top: -80px;">售后内容</label>
									<textarea id="remarks" name="remarks"
										style="height: 100px;width: 520px;">${custService.remarks}</textarea>
								</li>
								<li style="max-height: 120px;">
									<label class="control-textarea" style="top: -80px;">售后反馈</label>
									<textarea id="feedBackRemark" name="feedBackRemark"
										style="height: 100px;width: 520px;">${custService.feedBackRemark}</textarea>
								</li>
								<li>
									<label for="expired_show">过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${custService.expired}" 
										onclick="checkExpired(this)" ${custService.expired=="T"?"checked":""}/>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>	
<script>
$(function() {
	$(".form-search").unbind("keydown");// 解决textarea无法回车换行的问题 ——add by zb
	// 根据是否在岗变色
	if (null != "${customer.ProManStatusDescr}") {
		if ("SUS" == $.trim("${customer.ProManStatus}")) {
			$("#isWork").addClass("text-danger");
		} else {
			$("#isWork").addClass("text-primary");
		}
	}
	$("#custCode").openComponent_customer({
		callBack:setCustData,
		showValue:"${customer.Code}",
		showLabel:"${customer.Descr}",
		condition:{
			status:"5",
			laborFeeCustStatus:"1,2,3"
		}
	});// 员工搜索控件
	$("#projectMan").openComponent_employee({
		readonly:true,
		showValue:"${customer.ProjectMan}",
		showLabel:"${customer.ProjectManDescr}"
	});
	// 禁用搜索按钮
	$("#openComponent_employee_projectMan").next().attr("disabled",true);
	$("#repMan").openComponent_employee({
		callBack: validateRefresh_repMan,
		showValue:"${custService.repMan}",
		showLabel:"${repManDescr}"
	});
	$("#serviceMan").openComponent_employee({
		showValue:"${custService.serviceMan}",
		showLabel:"${serviceManDescr}"
	});

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			address:{
				validators: {  
					notEmpty: {  
						message: "楼盘不允许为空"  
					}
				}  
			},
			openComponent_employee_repMan:{ 
				validators: {  
					notEmpty: {  
						message: "报备人不允许为空"  
					}
				}  
			},
			repDate: {
				validators: {
					notEmpty: {  
						message: "报备日期不允许为空"  
					}
				}
			},
			type: {
				validators: {
					notEmpty: {
						message:"类型不允许为空"
					}
				}
			},
			dealMan: {
				validators: {  
					notEmpty: {  
						message: "处理人不允许为空",
					}
				}  
			},
			dealDate: {
				validators: {  
					notEmpty: {  
						message: "处理时间不允许为空",
					}
				}  
			},
			undertaker: {
				validators: {
					notEmpty: {
						message:"承担方不允许为空"
					}
				}
			},
		}
	});
	switch ("${m_umState}") {
	case "C":// complete
		// 禁用客户搜索输入框和按钮
		$("#openComponent_customer_custCode").attr("readonly",true).next().attr("disabled",true);
		$("#repMan").openComponent_employee({
			showValue:"${custService.repMan}",
			showLabel:"${repManDescr}",
			disabled:true
		});
		$("#openComponent_employee_repMan").next().attr("disabled",true);
		$("#serviceMan").openComponent_employee({
			showValue:"${custService.serviceMan}",
			showLabel:"${serviceManDescr}",
			disabled:true
		});
		$("#openComponent_employee_serviceMan").attr("readonly",true).next().attr("disabled",true);
		$("#type").prop("disabled",true);
		$("#expired_show").prop("disabled",true);
		$("#remarks").prop("disabled",true);
		$("#undertaker").prop("disabled",true);
		$("label").children(".required").remove();// 在每个label中查找 .required 的类，并将它移除。
		$("#dealMan").prev().prepend("<span class='required'>*</span>");
		$("#dealDate").prev().prepend("<span class='required'>*</span>");
		// 设置‘解决日期’刷新验证
		$("#dealDate").attr("onFocus","WdatePicker({onpicked:validateRefresh_dealDate(), skin:'whyGreen',dateFmt:'yyyy-MM-dd'})");
		break;
	case "V":
		$("#openComponent_customer_custCode").attr("readonly",true).next().attr("disabled",true);
		$("#custCode").prevAll("label").prepend("<span class='required'>*</span>");
		$("#repMan").openComponent_employee({
			showValue:"${custService.repMan}",
			showLabel:"${repManDescr}",
			disabled:true
		});
		$("#openComponent_employee_repMan").attr("readonly",true).next().attr("disabled",true);
		$("#serviceMan").openComponent_employee({
			showValue:"${custService.serviceMan}",
			showLabel:"${serviceManDescr}",
			disabled:true
		});
		$("#openComponent_employee_serviceMan").attr("readonly",true).next().attr("disabled",true);
		$("#type").prop("disabled",true);
		$("#expired_show").prop("disabled",true);
		$("#dealMan").prop("disabled",true);
		$("#dealDate").prop("disabled",true);
		$("#remarks").prop("disabled",true);
		$("#undertaker").prop("disabled",true);
		$("#saveBtn").hide();
		$("#feedBackRemark").prop("disabled",true);
		break;
	default:
		// 如果客户编号有填，跟以前一样，楼盘取自客户表的，不允许改。
		$("#openComponent_customer_custCode").on("blur", function () {
			$("#address").prop("readonly", false);
			if ("" != $(this).val()) {
				$("#address").prop("readonly", true);
			} 
		});
		// $("#custCode").prevAll("label").prepend("<span class='required'>*</span>");
		$("#openComponent_customer_custCode").trigger("blur");
		$("#page_form").bootstrapValidator("removeField","dealMan");/* 删除指定字段校验 */
		$("#page_form").bootstrapValidator("removeField","dealDate");
		break;
	}
	// 获得原始数据
	var originalData = $("#page_form").serialize();
	/* 关闭按钮绑定数据是否更改校验 */
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
	
	/* 保存 */
	$("#saveBtn").on("click",function(){
		doSave();
	});
	
});

// 报备人验证刷新
function validateRefresh_repMan(){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_employee_repMan", "NOT_VALIDATED", null)// 重置某一单一验证字段验证规则
		.validateField("openComponent_employee_repMan");// 触发指定字段的验证
}

function validateRefresh_dealDate(){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("dealDate", "NOT_VALIDATED", null)
		.validateField("dealDate");
}

function doSave(){
	$("#page_form").bootstrapValidator("validate");
	if(!$("#page_form").data("bootstrapValidator").isValid()) return;
	var datas = $("#page_form").jsonForm();
	$.ajax({
		url:"${ctx}/admin/custService/doUpdate",
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

// 设置客户信息
function setCustData(data){
	if (!data) return;
	if (data.code) {
		$.ajax({
			url:"${ctx}/admin/custService/GetCustDetailByCode",
			type:"post",
			data:{code:data.code},
			dataType:"json",
			cache:false,
			async:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
			},
			success:function(obj) {
				if (obj) {
					$("#address").val(obj.Address);
					$("#mobile1").val(obj.Mobile1);
					$("#consEndDate").val(formatDate(obj.ConsEndDate));// 格式化日期后放入consEndDate
					$("#department2").val(obj.Department2Descr);
					if (null == obj.ProManStatus) {
						$("#isWork").text("");
					} else {
						if ("SUS" == $.trim(obj.ProManStatus)) {
							$("#isWork").addClass("text-danger");
						} else {
							$("#isWork").addClass("text-primary");
						}
						$("#isWork").text(obj.ProManStatusDescr);
					}
				}
			}
		});
	}
	if(data.projectman==""||data.projectman==null){
		$("#openComponent_employee_projectMan").val("");
	}else{
		$("#projectMan").openComponent_employee({showValue:data.projectman,
			showLabel:data.projectmandescr,readonly:true});
	}
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("address", "NOT_VALIDATED", null)// 重置某一单一验证字段验证规则
		.validateField("address");// 触发指定字段的验证
	$("#openComponent_customer_custCode").trigger("blur");
}

</script>
</html>
