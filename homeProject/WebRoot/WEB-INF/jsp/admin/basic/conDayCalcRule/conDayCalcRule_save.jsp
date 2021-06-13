<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
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
					<button type="button" class="btn btn-system " id="closeBtn">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="expired" name="expired" value="${conDayCalcRule.expired}"/>
					<input type="hidden" id="pk" name="pk" value="${conDayCalcRule.pk}"/>
					<ul class="ul-form">
						<div class="row" >
							<li class="form-validate">
								<label><span class="required">*</span>工人分类</label>
								<house:xtdm id="workerClassify" dictCode="WORKERCLASSIFY" style="width:160px;" value="${conDayCalcRule.workerClassify}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>工种分类12</label>
								<house:dict id="workType12" dictCode="" 
									sql="select Code,Code+' '+Descr Descr from tWorkType12 where Expired='F' order by Code" 
									sqlValueKey="Code" sqlLableKey="Descr" value="${conDayCalcRule.workType12.trim()}"/>
							</li>
						</div>
						<div class="row" >
							<li class="form-validate">
								<label><span class="required">*</span>计算类型</label>
								<house:xtdm id="type" dictCode="DAYCALCTYPE" style="width:160px;" value="${conDayCalcRule.type}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>基础天数</label>
								<input type="text" id="baseConDay" name="baseConDay" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${conDayCalcRule.baseConDay}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>基础工作量</label>
								<input type="text" id="baseWork" name="baseWork" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${conDayCalcRule.baseWork}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>每日工作量</label>
								<input type="text" id="dayWork" name="dayWork" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${conDayCalcRule.dayWork}"/>
							</li>
						</div>
						<c:if test="${conDayCalcRule.m_umState!='A'}">
							<div class="row" >
								<li>
									<label>过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${conDayCalcRule.expired}" 
										onclick="checkExpired(this)" ${conDayCalcRule.expired=="T"?"checked":""}/>
								</li>
							</div>
						</c:if>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var url = "";
		$(function() {
			switch ("${conDayCalcRule.m_umState}") {
				case "M":
					url = "${ctx}/admin/conDayCalcRule/doUpdate";
					break;
				case "V":
					$("#saveBtn").remove();
					disabledForm();
					break;
				default:
					url = "${ctx}/admin/conDayCalcRule/doSave";
					break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					workerClassify:{ 
						validators: {  
							notEmpty: {  
								message: "工人分类不允许为空"  
							},
						}  
					},
					workType12:{ 
						validators: {  
							notEmpty: {  
								message: "工种分类12不允许为空"  
							},
						}  
					},
					type:{ 
						validators: {  
							notEmpty: {  
								message: "计算类型不允许为空"  
							},
						}  
					},
					baseConDay:{
						validators: {  
							notEmpty: {  
								message: "基础天数不允许为空"  
							},
						}
					},
					baseWork:{
						validators: {  
							notEmpty: {  
								message: "基础工作量不允许为空"  
							},
						}
					},
					dayWork:{
						validators: {  
							notEmpty: {  
								message: "每日工作量不允许为空"  
							},
						}
					},
				}
			});
			$("#closeBtn").on("click", function () {
				closeWin(false);
			});
		});
		$("#saveBtn").on("click", function () {
				if ("V" == "${conDayCalcRule.m_umState}") return;
				$("#page_form").bootstrapValidator("validate");/* 提交验证 */
				if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
				var datas = $("#page_form").serialize();
				$.ajax({
					url:url,
					type: "post",
					data: datas,
					dataType: "json",
					cache: false,
					error: function(obj){
						alert("error");
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
			});
	</script>
</body>
</html>
