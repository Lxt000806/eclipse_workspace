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
	<script src="${resourceRoot}/pub/component_workType2.js?v=${v}" type="text/javascript"></script>
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
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="expired" name="expired" value="${segDiscRule.expired}"/>
					<input type="hidden" id="pk" name="pk" value="${segDiscRule.pk}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>客户类型</label>
								<house:dict id="custType" dictCode="" 
									sql="select Code,Code+' '+Desc1 Descr from tCusttype where Expired='F'" 
									sqlValueKey="Code" sqlLableKey="Descr" value="${segDiscRule.custType}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>优惠金额分类</label>
								<house:dict id="discAmtType" dictCode="" 
									sql="select Code,Code+' '+Descr Descr from tItemType1 where Expired='F' order by DispSeq" 
									sqlValueKey="Code" sqlLableKey="Descr" value="${segDiscRule.discAmtType}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>适用最小面积</label>
								<input type="text" id="minArea" name="minArea" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${segDiscRule.minArea}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>适用最大面积</label>
								<input type="text" id="maxArea" name="maxArea" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${segDiscRule.maxArea}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>最高优惠金额</label>
								<input type="text" name="maxDiscAmount" id="maxDiscAmount" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${segDiscRule.maxDiscAmount}" style="width: 160px;">
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>总监优惠金额</label>
								<input type="text" name="directorDiscAmount" id="directorDiscAmount" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${segDiscRule.directorDiscAmount}" style="width: 160px;">
							</li>
							
							<li id="expired_li">
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${segDiscRule.expired}" 
									onclick="checkExpired(this)" ${segDiscRule.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		var url = "", oldCode="";
		$(function() {
			switch ("${segDiscRule.m_umState}") {
				case "M":
					url = "${ctx}/admin/segDiscRule/doUpdate";
					break;
				case "V":
					$("#saveBtn").remove();
					disabledForm();
					break;
				default:
					$("#expired_li").remove();
					url = "${ctx}/admin/segDiscRule/doSave";
					break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					custType:{ 
						validators: {  
							notEmpty: {  
								message: "客户类型不允许为空"  
							},
						}  
					},
					discAmtType:{ 
						validators: {  
							notEmpty: {  
								message: "优惠金额分类不允许为空"  
							},
						}  
					},
					minArea:{ 
						validators: {  
							notEmpty: {  
								message: "适用最小面积不允许为空"  
							},
						}  
					},
					maxArea:{ 
						validators: {  
							notEmpty: {  
								message: "适用最大面积不允许为空"  
							},
						}  
					},
					maxDiscAmount:{
						validators: {  
							notEmpty: {  
								message: "最高优惠金额不允许为空"  
							},
							numeric: {
				            	message: "最高优惠金额只能是数字" 
				            },
						}
					},
					directorDiscAmount:{
						validators: {  
							notEmpty: {  
								message: "总监优惠金额不允许为空"  
							},
						},
						numeric: {
			            	message: "总监优惠金额只能是数字" 
			            },
					},
				}
			});
			$("#saveBtn").on("click", function () {
				if ("V" == "${segDiscRule.m_umState}") return;
				$("#page_form").bootstrapValidator("validate");/* 提交验证 */
				if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
				var datas = $("#page_form").jsonForm();
				saveAjax(datas);
			});
			$("#closeBtn").on("click", function () {
				closeWin();
			});
		});
		function saveAjax(datas) {
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
							time: 700,
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
</body>
</html>
