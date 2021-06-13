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
	<style type="text/css">
		.form-search ul.ul-form div.row label {
			width: 120px;
		}
	</style>
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
					<input type="hidden" id="expired" name="expired" value="${discAmtTran.expired}"/>
					<input type="hidden" id="pk" name="pk" value="${discAmtTran.pk}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label for="custCode"><span class="required">*</span>客户编号</label>
								<input type="text" id="custCode" name="custCode">
							</li>
							<li class="form-validate">
								<label style="margin-left:-5px">剩余优惠额度</label>
								<span class="glyphicon glyphicon-question-sign" id = "remainDisc_span" 
										data-container="body"  
										data-content="设计师优惠额度+总监优惠额度+额外赠送-累计已使用额度" 
										data-placement="right" 
										style="font-size: 15px;color:rgb(25,142,222);margin-left: -5px"></span>
								<input type="text" id="remainDisc" name="remainDisc" readonly= "true" style="width:160px;" />
							</li>
							<li class="form-validate">
								<label style="margin-left:-5px">剩余前端风险金</label>
								<span class="glyphicon glyphicon-question-sign" id = "remainDesignRiskFund_span" 
										data-container="body"  
										data-content="设计师风控基金-累计已使用" 
										data-placement="right"
										style="font-size: 15px;color:rgb(25,142,222);margin-left: -5px"></span>
								<input type="text" id="remainDesignRiskFund" name="remainDesignRiskFund" style="width:160px;" readonly="true"/>
							</li>
							<li class="form-validate">
								<label>总优惠余额</label>
								<input type="text" id="totalRemainDisc" name="totalRemainDisc" readonly= "true" style="width:160px;" />
							</li>
							<li class="form-validate">
								<label for="type"><span class="required">*</span>优惠额度变动类型</label>
								<house:xtdm id="type" dictCode="DISCAMTTRANTYPE" style="width:160px;" value="${discAmtTran.type}"
									onchange="chgType()"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>金额</label>
								<input type="text" id="amount" name="amount" style="width:160px;" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\-?\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\-?\d]/g,'');"
									value="${discAmtTran.amount}"/>
							</li>
							<li class="form-validate">
								<label for="isRiskFund"><span class="required">*</span>是否风控基金</label>
								<house:xtdm id="isRiskFund" dictCode="YESNO" style="width:160px;" value="${discAmtTran.isRiskFund}"/>
							</li>
							<li class="form-validate">
								<label id="isExtra_label" >
									<span class="required">*</span>是否额外赠送
									<span class="glyphicon glyphicon-question-sign" id = "isExtra_span" 
											data-content="额外赠送增加最高优惠额度" data-placement="right"
											style="font-size: 15px;color:rgb(25,142,222);"></span>
								</label>
								<house:xtdm id="isExtra" dictCode="YESNO" style="width:160px;" value="${discAmtTran.isExtra}"/>
							</li>
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -60px;">说明</label>
								<textarea id="remarks" name="remarks" 
									style="height: 80px;width: 160px;">${discAmtTran.remarks}</textarea>
							</li>
							<c:if test="${discAmtTran.m_umState!='A'}">
								<div class="row" >
									<li>
										<label>过期</label>
										<input type="checkbox" id="expired_show" name="expired_show" value="${discAmtTran.expired}" 
											onclick="checkExpired(this)" ${discAmtTran.expired=="T"?"checked":""}/>
									</li>
								</div>
							</c:if>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
	<script type="text/javascript" defer>
		$(function() {
			$("#isExtra_span").popover({trigger:"hover"});
			$("#remainDisc_span").popover({trigger:"hover"});
			$("#remainDesignRiskFund_span").popover({trigger:"hover"});
			
			switch ("${discAmtTran.m_umState}") {
			case "V":
				$("#saveBtn").remove();
				parent.$("#iframe_view").attr("height","98%"); //消灭掉无用的滑动条
				$("#custCode").openComponent_customer({
					showLabel:"${discAmtTran.custName}",
					showValue:"${discAmtTran.custCode}",
					callBack:getData
				});
				$("#openComponent_customer_custCode").blur();
				disabledForm();
				break;
				
			case "M":
				parent.$("#iframe_view").attr("height","98%"); //消灭掉无用的滑动条
				$("#custCode").openComponent_customer({
					showLabel:"${discAmtTran.custName}",
					showValue:"${discAmtTran.custCode}",
					callBack:getData
				});
				$("#openComponent_customer_custCode").blur();
				
				if ("${discAmtTran.type}" === "1") {
	                $("#isExtra").removeAttr("disabled", "disabled")
	                $("#isRiskFund").attr("disabled", "disabled")
	            } else if ("${discAmtTran.type}" === "2") {
	                $("#isExtra").attr("disabled", "disabled")
	                $("#isRiskFund").removeAttr("disabled", "disabled")
	            }
				break;
				
			default:
				$("#custCode").openComponent_customer({
					condition:{status:"4"},
					callBack:getData
				});
				$("#amount").val(0);
				$("#isRiskFund").val("0");
				$("#isExtra").val("0");
				parent.$("#iframe_save").attr("height","98%");
				break;
			}
			
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					openComponent_customer_custCode:{
						validators: {
							notEmpty: {
								message:"楼盘不允许为空"
							}
						}
					},
					type:{ 
						validators: {  
							notEmpty: {  
								message: "优惠额度变动类型不允许为空"  
							},
						}  
					},
					amount:{ 
						validators: {  
							notEmpty: {  
								message: "金额不允许为空"  
							},
						}  
					},
					isRiskFund:{ 
						validators: {  
							notEmpty: {  
								message: "是否风控基金不允许为空"  
							},
						}  
					},
					isExtra:{ 
						validators: {  
							notEmpty: {  
								message: "是否额外赠送不允许为空"  
							},
						}  
					},
				}
			});
			$("#saveBtn").on("click", function () {
				if ("V" == "${discAmtTran.m_umState}") return false;
				$("#page_form").bootstrapValidator("validate");
				if(!$("#page_form").data("bootstrapValidator").isValid()) return; 
				var datas = $("#page_form").jsonForm();
				saveAjax(datas);
			});
			$("#closeBtn").on("click", function () {
				closeWin(false);
			});
		});
		
		function getData(data) {
			if(!data) return;
			
			$.ajax({
				url:"${ctx}/admin/discAmtTran/getMaxDiscAmount",
				type:'post',
				data:{custCode:data.code},
				dataType:'json',
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": '出错~'});
				},
				success:function(obj){
					if (obj){ 
						var remainDisc = myRound(obj.DesignerMaxDiscAmount) 
										+ myRound(obj.DirectorMaxDiscAmount) 
							+ myRound(obj.ExtraDiscChgAmount) + myRound(obj.UsedDiscAmount) + myRound(obj.LpExpense);
						// 前端剩余风险金 = 设计师风险金 + 已使用风险金(使用风险金在数据库填负数)
						var designRemainRiskFund = myRound(obj.DesignRiskFund) + myRound(obj.UsedRiskFund);
						
						console.log(obj.DesignerMaxDiscAmount+obj.DirectorMaxDiscAmount);
						console.log(obj.ExtraDiscChgAmount);
						console.log(obj.UsedDiscAmount);
						console.log(obj.LpExpense);
						//总优惠余额
						var totalRemainDisc = myRound(remainDisc) + myRound(designRemainRiskFund);
						
						$("#remainDisc").val(remainDisc);
						$("#remainDesignRiskFund").val(designRemainRiskFund);
						$("#totalRemainDisc").val(totalRemainDisc);					
					}						
				}
			});
			
			
			$("#page_form").data("bootstrapValidator")  
				.updateStatus("openComponent_customer_custCode", "NOT_VALIDATED", null)
				.validateField("openComponent_customer_custCode");
		}
		function saveAjax(datas) {
			var requestMap="doSave";
	        if("${discAmtTran.m_umState}"=="M"){
	        	requestMap="doUpdate";
	        }
			$.ajax({
				url:"${ctx}/admin/discAmtTran/"+requestMap,
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
		
		function chgType(){
			var type = $.trim($("#type").val());
			
			if (type === "1") {
			    $("#isExtra").val("1")
			    $("#isRiskFund").val("0")
				$("#isExtra").removeAttr("disabled", "disabled")
			    $("#isRiskFund").attr("disabled", "disabled")
			} else if (type === "2") {
				$("#isExtra").val("0")
				$("#isRiskFund").val("0")
				$("#isExtra").attr("disabled", "disabled")
				$("#isRiskFund").removeAttr("disabled", "disabled")
			}
		}
	</script>
</html>
