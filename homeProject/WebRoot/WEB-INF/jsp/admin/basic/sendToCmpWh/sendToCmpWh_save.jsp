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
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
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
					<input type="hidden" id="expired" name="expired" value="${sendToCmpWh.expired}"/>
					<input type="hidden" id="pk" name="pk" value="${sendToCmpWh.pk}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label>客户类型</label> 
								 <house:DataSelect id="custType" className="CustType" keyColumn="code" valueColumn="desc1" value="${sendToCmpWh.custType }"></house:DataSelect>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>供应商</label>
								<input type="text" id="supplCode" name="supplCode" value="${sendToCmpWh.supplCode}" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>区域</label> 
								<house:dict id="regionCode" dictCode=""
									sql="select a.Code,a.Code+' '+a.descr descr  from tRegion a where a.Expired='F' "
									sqlValueKey="Code" sqlLableKey="Descr"
									value="${sendToCmpWh.regionCode}">
								</house:dict>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>采购金额从</label>
								<input type="text" id="amountFrom" name="amountFrom" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${sendToCmpWh.amountFrom}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>采购金额到</label>
								<input type="text" id="amountTo" name="amountTo" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${sendToCmpWh.amountTo}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label for="itemAppType"><span class="required">*</span>领料单类型</label>
								<house:xtdm id="itemAppType" dictCode="TOCMITEMAPPTYPE" style="width:160px;" 
									value="${sendToCmpWh.itemAppType}"/>
							</li>
						</div>
						<c:if test="${sendToCmpWh.m_umState!='A' && sendToCmpWh.m_umState!='C'}">
							<div class="row" >
								<li>
									<label>过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${sendToCmpWh.expired}" 
										onclick="checkExpired(this)" ${sendToCmpWh.expired=="T"?"checked":""}/>
								</li>
							</div>
						</c:if>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		var url = "", oldCode="";
		$(function() {
			$("#supplCode").openComponent_supplier({showValue:"${sendToCmpWh.supplCode}",showLabel:"${sendToCmpWh.supplDescr}"});
			switch ("${sendToCmpWh.m_umState}") {
				case "M":
					url = "${ctx}/admin/sendToCmpWh/doUpdate";
					break;
				case "V":
					$("#saveBtn").remove();
					disabledForm();
					break;
				default:
					url = "${ctx}/admin/sendToCmpWh/doSave";
					break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					amountFrom:{ 
						validators: {  
							notEmpty: {  
								message: "采购金额从不允许为空"  
							},
						}  
					},
					amountTo:{ 
						validators: {  
							notEmpty: {  
								message: "采购金额到不允许为空"  
							},
						}  
					},
					itemAppType:{ 
						validators: {  
							notEmpty: {  
								message: "领料单类型到不允许为空"  
							},
						}  
					},
				}
			});
			$("#saveBtn").on("click", function () {
				var amountFrom =$("#amountFrom").val();
				var amountTo =$("#amountTo").val();
				if(amountFrom>amountTo){
					art.dialog({
						content: "采购金额从不能大于采购金额到！",
					});
					return;
				}
				if ("V" == "${sendToCmpWh.m_umState}") return;
				$("#page_form").bootstrapValidator("validate");/* 提交验证 */
				if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
				var datas = $("#page_form").jsonForm();
				saveAjax(datas);
			});
			$("#closeBtn").on("click", function () {
				closeWin(false);
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
