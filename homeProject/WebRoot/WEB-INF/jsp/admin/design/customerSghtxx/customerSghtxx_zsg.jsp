<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>施工合同管理转施工</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		.li-flex {
			display: flex;
			justify-content: space-between;
			width: 291px;
		}
	</style>
	<script type="text/javascript">
	var DesignFType="";
	
	$(function(){
		if("${map}" != ""){
			// 最大优惠额度(含总监优惠)
			$("#totalMaxDiscAmount").val(myRound("${map.DesignerMaxDiscAmount}") + myRound("${map.DirectorMaxDiscAmount}"));
			$("#designerMaxDiscAmount").val(myRound("${map.DesignerMaxDiscAmount}"));
			$("#directorMaxDiscAmount").val(myRound("${map.DirectorMaxDiscAmount}"));

			// 已使用优惠额度
			$("#usedDiscAmount").val(myRound("${map.zskzsjyh}"));
			calcRemainDisc();
		}
		//设计师优惠额度 总监优惠额度 公式不为空 不允许修改 
		if($.trim("${custType.designerMaxDiscAmtExpr}") != ""){
			$("#designerMaxDiscAmount").attr("readonly",true);
		}
		
		if($.trim("${custType.directorMaxDiscAmtExpr}") != ""){
			$("#directorMaxDiscAmount").attr("readonly",true);
		}
		if("${custType.isPartDecorate}"=="3"){
			$("#dicProvideRemarks_li").hide();
			$("#discTokenAmount_li").show();
			$("#wdzAmount_li").show();
			getCustomerPay("${customer.code}");
		}else{
			$("#dicProvideRemarks_li").show();
			$("#discTokenAmount_li").hide();
			$("#wdzAmount_li").hide();
			if("${customer.discTokenAmount}">0){
				$("#dicProvideRemarks").val("转施工后发放软装券，金额"+"${customer.discTokenAmount}"+"元 "); 
			}else{
				$("#dicProvideRemarks").val("无相关信息 "); 
			}
		}
	});
	
	function save(){
		var datas = $("#dataForm").serialize();
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var contractFee=myRound($.trim($("#contractFee").val()),2);
		var firstPay=myRound($.trim($("#firstPay").val()),2);
		var secondPay=myRound($.trim($("#secondPay").val()),2);
		var thirdPay=myRound($.trim($("#thirdPay").val()),2);
		var fourPay=myRound($.trim($("#fourPay").val()),2);
		var isInternal=$.trim($("#isInternal").val());
		var payeeCode=$.trim($("#payeeCode").val());
		var returnDesignFee=myRound($.trim($("#returnDesignFee").val()),2);
		var designFee=myRound($.trim("${customer.designFee }"),2);
		var tax=myRound($.trim("${customer.tax}"),2);
		var total=contractFee+tax;
		var payTotal=firstPay+secondPay+thirdPay+fourPay;
		var returnDesignPayTotal=firstPay+secondPay+thirdPay+fourPay+returnDesignFee;
		var designPayTotal=firstPay+secondPay+thirdPay+fourPay-designFee;
		if($.trim(DesignFType)=="2"){
			if(total!=returnDesignPayTotal){
				art.dialog({
					content:"付款计划合计+设计费返还不等于工程造价+税金，不允许转施工!",
				});
				return;
			}
		}else if($.trim(DesignFType)=="3"){
			if(total!=designPayTotal){
				console.log(total);
				console.log(designPayTotal);
				art.dialog({
					content:"付款计划合计-设计费不等于工程造价+税金，不允许转施工!",
				});
				return;
			}
		}else{
			if(payTotal!=total){
				art.dialog({
					content:"付款计划合计不等于工程造价+税金，不允许转施工!",
				});
				return;
			}
		}
		if(isInternal==""){
			art.dialog({
				content:"请选择是否内部客户！",				
			});
			return;
		}
		if(payeeCode==""){
			art.dialog({
				content:"请选择收款单位！",				
			});
			return;
		}
		if($("#isInitSign").val()==""){
			art.dialog({
				content:"请选择是否草签！",				
			});
			return;
		}
        /* if((parseFloat("${riskFund}")+parseFloat($("#prjManRiskFund").val()))
			>parseFloat($("#riskFundTotal").val())){
			art.dialog({
				content:"‘已使用风控基金’和‘超优惠使用风控基金’的和要小于等于‘风控基金总额’",				
			});
			return;
		} */
		// 判断自动转订单有无付款记录
		$.ajax({                           
			url: "${ctx}/admin/customerSghtxx/getAutoToOrderHasPay", 
			type: "post",
			data: {custCode :"${customer.code}"},
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "请求数据出错"});
		    },
		    success: function(obj){
		    	if(obj.code=="0"){
		    		art.dialog({
						content: obj.data,
						width: 200,
						okValue: "确定",
						ok: function () {
							doSaveZsg(datas);
						},
						cancelValue: "取消",
						cancel: function () {
							return ;
						}
					});	
		    	}else{
		    		doSaveZsg(datas)
		    	}
	    	}
		});
	}	
	function doSaveZsg(datas){	
		$.ajax({
			url:"${ctx}/admin/customerSghtxx/doSaveZsg",
			type: "post",
			data: datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs==true){
					art.dialog({
						content:obj.msg,
						time:500,
						beforeunload:function(){
							$("#saveBtn").removeAttr("disabled",true);
							closeWin();
						}
					});				
				}else{
					$("#_form_token_uniq_id").val(obj.token.token);
					$("#saveBtn").removeAttr("disabled",true);
					art.dialog({
						content:obj.msg,
						width:200
					});
				}
		    }
		});
	}
			
$(function(){
	DesignFType=$.trim("${designFeeType }");
	$("#payType").val(${customer.payType});
	changePayType();
	if (0=="${riskFund}") {
		$("#riskFundSpan").remove();
		$("#riskFundDetail").remove();
	}

	$("#dataForm").bootstrapValidator({
		message: "请输入完整的信息",
		feedbackIcons: {
			validating: "glyphicon glyphicon-refresh"
		},
		fields: {
			payType:{  
				validators: {  
					notEmpty: {  
						message: "付款方式不能为空"  
					},
				}  
			},
			payeeCode:{  
				validators: {  
					notEmpty: {  
						message: "收款单位不能为空"  
					},
				}  
			},
			isInitSign:{  
				validators: {  
					notEmpty: {  
						message: "草签不能为空"  
					},
				}  
			},
		},
		submitButtons: "input[type='submit']"
	});
	//风控基金明细查看
	$("#riskFundDetail").on("click", function () {
		Global.Dialog.showDialog("riskFundDetail",{
			title: "查看风控基金明细",
			url: "${ctx}/admin/customerSghtxx/goRiskFundDetail",
			postData: {custCode: "${customer.code}"},
			height: 548,
			width: 927
		});
	});

	$("#designRiskFund").on("blur", function () {
		var val = this.value;
		$(this).val(val?val:0);
	});

	$("#prjManRiskFund").on("blur", function () {
		var val = this.value;
		$(this).val(val?val:0);
	});
	
	$("#frontEndDiscAmount").on("blur", function () {
		var val = this.value;
		$(this).val(val?val:0);
	});

	$("#cmpDiscAmount").on("blur", function () {
		var val = this.value;
		$(this).val(val?val:0);
	});
});
function changePayType(){
	var payType= $.trim($("#payType").val());
	
	$.ajax({
		url:"${ctx}/admin/customerSghtxx/getFourPay",
		type: "post",
		data:{custCode:"${customer.code }",payType:payType},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	if(obj){
	    		$("#firstPay").val(obj[0]);
	    		$("#secondPay").val(obj[1]);
	    		$("#thirdPay").val(obj[2]);
	    		$("#fourPay").val(obj[3]);
	    		DesignFType=$.trim(obj[4]);
	    	}
		}
	});
	
}
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
		 .updateStatus('toConstructDate', 'NOT_VALIDATED',null)
		 .validateField('toConstructDate'); 
}
function calcRemainDisc(){
	var remainDisc = $("#remainDisc").val(); //剩余优惠额度
	var totalMaxDiscAmount = $("#totalMaxDiscAmount").val(); // 最高优惠额度含总监
	var cmpDiscAmount = $("#cmpDiscAmount").val().replace(/\s/g,'').replace(/[^\.\d]/g,''); //公司优惠额度
	var riskFundTotal = $("#riskFundTotal").val();// 前端风险金
	var usedDiscAmount = $("#usedDiscAmount").val();
	var frontEndDiscAmount = $("#frontEndDiscAmount").val().replace(/\s/g,'').replace(/[^\.\d]/g,'');
	if(!riskFundTotal){
		riskFundTotal = 0;
	}
	if(!cmpDiscAmount){
		cmpDiscAmount = 0;
	}
	
	$("#remainDisc").val(myRound(totalMaxDiscAmount) + myRound(cmpDiscAmount) +
				myRound(riskFundTotal) + myRound(frontEndDiscAmount)-myRound(usedDiscAmount));
	
}

function calcTotalMaxDiscAmount(id, otherId){
	var chgVal =  $("#"+id).val().replace(/\s/g,'').replace(/[^\.\d]/g,'');
	var val = $("#"+otherId).val();
	$("#"+id).val(chgVal);
	
	$("#totalMaxDiscAmount").val(myRound(chgVal,2)+myRound(val,2));
	calcRemainDisc();
}
// 获取未达账金额
function getCustomerPay(custCode){
	$.ajax({
		url:'${ctx}/admin/cljsgl/getCustomerPay',
		type: 'post',
		data: {'custCode':custCode},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if (parseFloat(obj.wdz)!=0){
	    		$("#wdzAmount").val(obj.wdz);
	    	}else{
	    		$("#wdzAmount").val("");
	    	}
	    }
	});
}
//优惠券选择
function discTokenSel(){
	var hasSelectNo ="${customer.discTokenNo }" ;
    Global.Dialog.showDialog("discToken",{
		title: "优惠券信息编辑",
		url: "${ctx}/admin/discTokenQuery/goDiscTokenSelect",
		postData: {
		    hasSelectNo: hasSelectNo,
            custCode: "${customer.code}",
            itemType1:"RZ",
            status:"2",
	    },
		height: 458,
		width: 660,
    });
} 

	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
						<span id="riskFundSpan" style="color: red;">该楼盘已使用风控基金${riskFund}元</span>
						<button type="button" class="btn btn-system" id="riskFundDetail">
							<span>查看明细</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
					    <house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="status" value="${customer.status}"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>客户编号</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }" readonly="true"/>
								</li>
								<li>
									<label>内部客户</label>
									<house:xtdm id="isInternal" dictCode="YESNO" value="${customer.isInternal }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" readonly="true"/>
								</li>
								<li class="form-validate">
								    <label><span class="required">*</span>草签</label>
								    <house:xtdm id="isInitSign" dictCode="YESNO" ></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
							    <li>
                                    <label>瓷砖状态</label>
                                    <house:xtdm id="tileStatus" dictCode="MaterialStatus" value="${customer.tileStatus }"></house:xtdm>
                                </li>
                                <li>
                                    <label>卫浴状态</label>
                                    <house:xtdm id="bathStatus" dictCode="MaterialStatus" value="${customer.bathStatus }"></house:xtdm>
                                </li>
							</div>
							<div class="validate-group row">
								<li>
									<label>工程造价</label>
									<input type="text" id="contractFee" name="contractFee" style="width:160px;" value="${customer.contractFee }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>实收设计费</label>
									<input type="text" id="designFee" name="designFee" style="width:160px;" value="${customer.designFee }" readonly="true"/>
								</li>
								<li>
									<label>设计费返还</label>
									<input type="text" id="returnDesignFee" name="returnDesignFee" style="width:160px;" value="${customer.returnDesignFee }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>手机号码</label>
									<input type="text" id="mobile1" name="mobile1" style="width:160px;" value="${customer.mobile1 }" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>付款方式</label>
									<!-- <house:xtdm id="payType" dictCode="TIMEPAYTYPE" onchange="changePayType()" value="${customer.payType }"></house:xtdm> -->
									<!-- 改成从付款规则里获取 modify by zb -->
									<house:dict id="payType" dictCode="" sql="select a.PayType val,a.PayType+' '+b.NOTE descr from tPayRule a left join tXTDM b on b.CBM=a.PayType and b.ID='TIMEPAYTYPE' where a.CustType=${customer.custType} and a.Expired = 'F'" 
										sqlValueKey="val" sqlLableKey="descr" onchange="changePayType()"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>首批付款</label>
									<input type="text" id="firstPay" name="firstPay" style="width:160px;" value="${customer.firstPay }" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>收款单位</label>
									<house:dict id="payeeCode" dictCode="" 
										sql=" select code val,code+' '+Descr descr from tTaxPayee 
											where Expired='F' " 
										sqlValueKey="val" sqlLableKey="descr" value="${customer.payeeCode}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>二批付款</label>
									<input type="text" id="secondPay" name="secondPay" style="width:160px;" value="${customer.secondPay }" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
								</li>
								<li>
									<label>身份证号</label>
									<input type="text" id="conId" name="conId" style="width:160px;"
										
										value="${customer.conId}"/>	
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>三批付款</label>
									<input type="text" id="thirdPay" name="thirdPay" style="width:160px;" value="${customer.thirdPay }" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
								</li>
								<li class="form-validate">
									<label>业绩折扣</label>
									<input type="text" id="perfMarkup" name="perfMarkup" style="width:160px;" value="${customer.perfMarkup }" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>尾批付款</label>
									<input type="text" id="fourPay" name="fourPay" style="width:160px;" value="${customer.fourPay }" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>转施工时间</label>
									<input type="text" id="toConstructDate" name="toConstructDate" class="i-date"  style="width:160px;" 
										required data-bv-notempty-message="转施工时间不能为空"
										onFocus="WdatePicker({onpicked:validateRefresh(),skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${nowDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>设计师优惠额度</label>
									<input type="text" id="designerMaxDiscAmount" name="designerMaxDiscAmount" style="width:160px;" 
										onkeyup="calcTotalMaxDiscAmount('designerMaxDiscAmount','directorMaxDiscAmount')"/>
								</li>
								<li> 
									<label>总监优惠额度</label>
									<input type="text" id="directorMaxDiscAmount" name="directorMaxDiscAmount" style="width:160px;" 
										onkeyup="calcTotalMaxDiscAmount('directorMaxDiscAmount','designerMaxDiscAmount')"
										/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>最高优惠额度</label>
									<input type="text" id="totalMaxDiscAmount" name="totalMaxDiscAmount" style="width:160px;" 
										value="0.0" readonly = "true"/>
								</li>
								<li> 
									<label>前端风险金</label>
									<input type="text" id="riskFundTotal" name="riskFundTotal" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
										value="${custType.designRiskFund==null?0:custType.designRiskFund}" readonly/>
								</li>
								<!--<li class="li-flex">
									<label for="designRiskFund">超优惠</br>设计师承担</label>
									<input type="text" id="designRiskFund" name="designRiskFund" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
										value="0"/>
								</li>-->
							</div>
							<div class="validate-group row">
								<!-- <li class="li-flex">
									<label for="prjManRiskFund">超优惠</br>使用风控基金</label>
									<input type="text" id="prjManRiskFund" name="prjManRiskFund" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
										value="0"/>
								</li>-->
								<li>
									<label>已使用优惠额度</label>
									<input type="text" id="usedDiscAmount" name="usedDiscAmount" style="width:160px;" 
										value="0.0" readonly = "true"/>
								</li>
								<li class="li-flex">
									<label for="frontEndDiscAmount">前端承担</br>赠送金额</label>
									<input type="text" id="frontEndDiscAmount" name="frontEndDiscAmount" style="width:160px;" 
										onkeyup="calcRemainDisc()"
										onafterpaste="calcRemainDisc()"
										value="0"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<!-- this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,''); -->
									<label for="cmpDiscAmount">公司额外赠送金额</label>
									<input type="text" id="cmpDiscAmount" name="cmpDiscAmount" style="width:160px;" 
										onkeyup="calcRemainDisc()"
										onafterpaste="calcRemainDisc()"
										value="0"/>
								</li>
								<li>
									<label>剩余优惠额度</label>
									<input type="text" id="remainDisc" name="remainDisc" style="width:160px;" 
										value="0.0" readonly = "true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
								    <label>已付款</label>
								    <input type="text" id="haspay" name="haspay" style="width:160px;"
								        value="${hasPay}" readonly="readonly" />
			                    </li>
			                    <li id="discTokenAmount_li">
								    <label>优惠券金额</label>
								    <input type="text" id="discTokenAmount" name="discTokenAmount" style="width:160px;"
								        value="${customer.discTokenAmount}"  readonly="readonly" />
								     <button type="button" class="btn btn-system" id="discTokenBtn"  onclick="discTokenSel()" style="font-size: 12px;margin-left: -1px;width: 35px;">查看</button>
			                    </li>
			                </div>
							<div class="validate-group row">
			                    <li id="wdzAmount_li">
								    <label>未达账金额</label>
								    <input type="text" id="wdzAmount" name="wdzAmount" style="width:160px;"
								         readonly="readonly" />
			                    </li>   
							</div>
							<div class="validate-group row">
			                    <li id="dicProvideRemarks_li">
								    <label>优惠券发放</label>
								    <input type="text" id="dicProvideRemarks" name="dicProvideRemarks" style="width:450px;"
								         readonly="readonly" />
			                    </li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
