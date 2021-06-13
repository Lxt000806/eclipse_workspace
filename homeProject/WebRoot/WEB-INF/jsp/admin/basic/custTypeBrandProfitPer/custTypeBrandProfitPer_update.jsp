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
	<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		.form-search .ul-form li label {
			width: 140px;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn"
						onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="doClose()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action=""
					method="post" target="targetFrame">
					<house:token></house:token>
					<!-- 按钮只能点一次 -->
					<input type="hidden" id="lastupdatedby" name="lastupdatedby"
						value="${sessionScope.USER_CONTEXT_KEY.czybh}" />
					<input type="hidden" id="expired" name="expired" value="${custTypeBrandProfitPer.expired}"/>
					<input type="hidden" id="pk" name="pk" value="${custTypeBrandProfitPer.pk}"/>
					<ul class="ul-form">
						<li>
							<label>客户类型</label>
							<house:dict id="custType" dictCode="" sql="select code ,code+' '+desc1 descr from tcustType where expired='F' " 
							sqlValueKey="Code" sqlLableKey="Descr"  value="${custTypeBrandProfitPer.custType.trim()}"></house:dict>
						</li>
						<li>	
							<label>品牌</label>
							<input type="text" id="brandCode" name="brandCode"   value="${custTypeBrandProfitPer.brandCode}" />
						</li>	
						<li><label>利润率</label> <input type="text"
							id="profitPer" name="profitPer" style="width:160px;"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
							value="${custTypeBrandProfitPer.profitPer}" />
						</li>
						<li>
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${custTypeBrandProfitPer.expired}" 
								onclick="checkExpired(this)" ${custTypeBrandProfitPer.expired=="T"?"checked":""}/>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" defer>
	$(function() {
		if("${custTypeBrandProfitPer.m_umState}"=="V"){
			disabledForm();
			$("#saveBtn").attr("disabled",true);
		}
		$("#brandCode").openComponent_brand({showValue:"${custTypeBrandProfitPer.brandCode}",showLabel:"${custTypeBrandProfitPer.brandDescr}"});
	});

	function doClose() {
		closeWin();
	}

	function doSave() {
		$("#page_form").bootstrapValidator("validate");/* 提交验证 */
		if (!$("#page_form").data("bootstrapValidator").isValid())
			return; /* 验证失败return */

		var datas = $("#page_form").serialize();
		$.ajax({
			url : "${ctx}/admin/custTypeBrandProfitPer/doUpdate",
			type : "post",
			data : datas,
			dataType : "json",
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : "保存数据出错"
				});
			},
			success : function(obj) {
				if (obj.rs) {
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				} else {
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content : obj.msg,
						width : 200
					});
				}
			}
		});
	}
	function checkDate(){
		var custCheckDateFrom=$("#custCheckDateFrom").val();
		var custCheckDateTo=$("#custCheckDateTo").val();
		if(custCheckDateFrom!="" && custCheckDateTo !="" && custCheckDateFrom>custCheckDateTo){
			return false;
		}
		return true;
	}
</script>
</html>
