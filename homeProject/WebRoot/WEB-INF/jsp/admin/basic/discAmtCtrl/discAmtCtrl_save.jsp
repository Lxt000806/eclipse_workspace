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
					<input type="hidden" id="expired" name="expired" value="${discAmtCtrl.expired}"/>
					<input type="hidden" id="pk" name="pk" value="${discAmtCtrl.pk}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>客户类型</label>
								<house:dict id="custType" dictCode="" 
									sql="select Code,Code+' '+Desc1 Descr from tCusttype where Expired='F'" 
									sqlValueKey="Code" sqlLableKey="Descr" value="${discAmtCtrl.custType}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>优惠金额分类</label>
								<house:dict id="discAmtType" dictCode="" 
									sql="select Code,Code+' '+Descr Descr from tItemType1 where Expired='F' order by DispSeq" 
									sqlValueKey="Code" sqlLableKey="Descr" value="${discAmtCtrl.discAmtType}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>最高优惠金额公式</label>
								<input type="text" name="maxDiscAmtExpr" id="maxDiscAmtExpr" 
									value="${discAmtCtrl.maxDiscAmtExpr}" style="width: 450px;">
							</li>
							<li class="form-validate">
								<label>是否橱柜</label>
								<house:xtdm id="isCupBoard" dictCode="YESNO" style="width:160px;" value="${discAmtCtrl.isCupBoard}"/>
							</li>
							<li class="form-validate">
								<label>是否服务型产品</label>
								<house:xtdm id="isService" dictCode="YESNO" style="width:160px;" value="${discAmtCtrl.isService}"/>
							</li>
							<li id="expired_li">
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${discAmtCtrl.expired}" 
									onclick="checkExpired(this)" ${discAmtCtrl.expired=="T"?"checked":""}/>
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
			switch ("${discAmtCtrl.m_umState}") {
				case "M":
					url = "${ctx}/admin/discAmtCtrl/doUpdate";
					break;
				case "V":
					$("#saveBtn").remove();
					disabledForm();
					break;
				default:
					$("#expired_li").remove();
					url = "${ctx}/admin/discAmtCtrl/doSave";
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
					maxDiscAmtExpr:{
						validators: {  
							notEmpty: {  
								message: "最高优惠金额公式不允许为空"  
							},
						}
					},
				}
			});
			$("#saveBtn").on("click", function () {
				if ("V" == "${discAmtCtrl.m_umState}") return;
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
