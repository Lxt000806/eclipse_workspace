<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>付款计划</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		//实时修改金额	 
		$('#firstPay').bind('input propertychange', function() {  
		   countSum();
		});
		$('#secondPay').bind('input propertychange', function() {  
		   countSum();
		});
		$('#thirdPay').bind('input propertychange', function() {  
		   countSum();
		});
		$('#fourPay').bind('input propertychange', function() {  
		   countSum();
		});
		$("#saveBtn").on("click", function() {
			var designFeeType="${customerPayMap.designFeeType}";
			var tax=parseFloat("${customerPayMap.tax}");
			var contractfee=parseFloat($("#contractfee").val());
			var sumPay=parseFloat($("#sumPay").val());
			var returndesignfee=parseFloat("${customerPayMap.returndesignfee }");
			var designfee=parseFloat($("#designfee").val());
			var firstPay=$("#firstPay").val();
			var secondPay=$("#secondPay").val();
			var thirdPay=$("#thirdPay").val();
			var fourPay=$("#fourPay").val();
			var lblerror="";
			if(firstPay=="" || secondPay=="" || thirdPay=="" || fourPay==""){
				art.dialog({
					content : "请输入完整信息！",
					width : 200
				});
				return;
			}
			if(designFeeType=="2"){
				if(contractfee+tax!=sumPay+returndesignfee){
					lblerror="工程造价【"+contractfee+"】+税金【"+tax+"】与【付款计划总额【"+sumPay+"】和设计费返还【"+returndesignfee+"】之和】不相等！";
				}
			}else if(designFeeType=="3"){
				if(contractfee+tax!=sumPay-designfee){
					lblerror="工程造价【"+contractfee+"】+税金【"+tax+"】与【付款计划总额【"+sumPay+"】减设计费【"+returndesignfee+"】之差】不相等！";
				}
			}else{
				if(contractfee+tax!=sumPay){
					lblerror="工程造价【"+contractfee+"】+税金【"+tax+"】与【付款计划总额【"+sumPay+"】不相等！";
				}
			}
			if(lblerror!=""){
				art.dialog({
					 content:lblerror+"，确定要保存？",
					 lock: true,
					 width: 200,
					 height: 100,
					 ok: function () {
					 	doSave();
					 },
					 cancel: function () {
							return true;
					 }
				}); 
			}else{
				doSave();
			}
		});
	});	
	
	function countSum(){
		var firstPay=parseFloat($("#firstPay").val());
		var secondPay=parseFloat($("#secondPay").val());
		var thirdPay=parseFloat($("#thirdPay").val());
		var fourPay=parseFloat($("#fourPay").val());
		$("#sumPay").val(firstPay+secondPay+thirdPay+fourPay);
	}
	function doSave(){
		var datas = $("#dataForm").serialize();
			$.ajax({
				url : "${ctx}/admin/custPay/updatePayPlan",
				type : "post",
				data : datas,
				dataType : "json",
				cache : false,
				async:true,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : "保存数据出错~"
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
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="pk" value="${custPay.pk}" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label style="width:185px;">客户编号</label>
								<input type="text" id="code" maxlength="10" name="code"
								style="width:160px;" value="${customerPayMap.code }" readonly="readonly"/></li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_info" data-toggle="tab">主项目</a>
					</li>
				</ul>
				<ul class="ul-form">
					<div class="tab-content">
						<div id="tab_info" class="tab-pane fade in active">
							<div class="body-box-list"><br>
								<div class="validate-group row">
									<li><label>客户名称</label> <input type="text" id="descr"
										name="descr" style="width:160px;"
										value="${customerPayMap.descr }" readonly="readonly" /></li>
									<li><label><font color="blue">首批付款</font></label> <input type="number" id="firstPay"
										name="firstPay" style="width:160px;"
										value="${customerPayMap.firstpay }" /></li>
								</div>
								<div class="validate-group row">
									<li><label>楼盘</label> <input type="text" id="address"
										name="address" style="width:160px;"
										value="${customerPayMap.address }" readonly="readonly" /></li>
									<li><label><font color="blue">二批付款</font></label> <input type="number" id="secondPay"
										name="secondPay" style="width:160px;"
										value="${customerPayMap.secondpay }"/></li>
								</div>
								<div class="validate-group row">
									<li><label>工程造价</label> <input type="text"
										id="contractfee" name="contractfee" style="width:160px;"
										value="${customerPayMap.contractfee }" readonly="readonly" />
									</li>
									<li><label><font color="blue">三批付款</font></label> <input type="number" id="thirdPay"
										name="thirdPay" style="width:160px;"
										value="${customerPayMap.thirdpay }"/></li>
								</div>
								<div class="validate-group row">
									<li><label>设计费</label> <input type="text" id="designfee"
										name="designfee" style="width:160px;"
										value="${customerPayMap.designfee }" readonly="readonly" /></li>
									<li><label><font color="blue">尾批付款</font></label> <input type="number" id="fourPay"
										name="fourPay" style="width:160px;"
										value="${customerPayMap.fourpay }" /></li>
								</div>
								<div class="validate-group row">
									<li><label>已付款</label> <input type="text" id="haspay"
										name="haspay" style="width:160px;"
										value="${customerPayMap.haspay }" readonly="readonly" /></li>
									<li><label>合计</label> <input type="text" id="sumPay"
										name="sumPay" style="width:160px;"
										value="${customerPayMap.sumpay }" readonly="readonly" /></li>
								</div>
<%-- 								<div class="validate-group row">
									<li><label>收款单号</label> <input type="text" id="payNo"
										name="payNo" style="width:160px;" value="${custPay.payNo }" />
									</li>
								</div> --%>
								<div class="validate-group row">
									<li><label class="control-textarea">备注</label> <textarea
											id="payRemark" name="payRemark"
											style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${customerPayMap.payremark
										}</textarea>
									</li>
								</div>
							</div>
						</div>
					</div>
				</ul>
			</div>
		</div>
	</form>
</body>
</html>
