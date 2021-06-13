<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>付款信息--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		//初始化一二级区域联动
		Global.LinkSelect.initSelect("${ctx}/admin/custPay/actAndPosByAuthority","rcvAct","posCode");
		Global.LinkSelect.setSelect({firstSelect:'rcvAct',
								firstValue:'${custPay.rcvAct}',
								secondSelect:'posCode',
								secondValue:'${custPay.posCode}'
								});
		if("${custPay.m_umState}"=="V"){
		 	$("input").attr("disabled",true);
		 	$("select").attr("disabled",true);
		 	$("textarea").attr("disabled",true);
		 	$("#saveBtn").attr("disabled",true);
		 	$("#discTokenNo_li").show();	 
		}
		//实时修改付款金额	 
		$('#amount').bind('input propertychange', function() {  
		   changePos();
		});
		if("${custPay.m_umState}"!="V")$("#amount").focus();		
		$("#saveBtn").on("click", function() {
			var payNo=$("#payNo").val();
			var rcvAct=$("#rcvAct").val();
			var amount=$("#amount").val();
			var type=$("#type").val();
			if(amount==""){
				art.dialog({
					content: "输入的付款金额无效！"
				});
				return;
			}
			if(rcvAct==""){
				art.dialog({
					content: "请选择付款账号！"
				});
				return;
			}
			if(type==""){
				art.dialog({
					content: "请选择类型！"
				});
				return;
			}
			if(payNo!=""){
				$.ajax({
					url : "${ctx}/admin/custPay/checkPayNo",
					type : "post",
					data :{'payNo':payNo,pk:'${custPay.pk}'},
					dataType : "json",
					cache : false,
					async :false,
					error : function(obj) {
						showAjaxHtml({
							"hidden" : false,
							"msg" : "保存数据出错~"
						});
					},
					success : function(obj) {
						if(obj.length>0){
							art.dialog({
								 content:"收款单号【"+payNo+"】已存在，是否保存？",
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
					}
				});
			}else{
				doSave();
			}
		});
	});
	//保存
	function doSave(){	
		 var save="doSave";
		 if("${custPay.m_umState}"=="M"){
		 	save="doUpdate";
		 }
		 var datas = $("#dataForm").serialize();
			$.ajax({
				url : "${ctx}/admin/custPay/"+save,
				type : "post",
				data : datas,
				dataType : "json",
				cache : false,
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
								Global.Dialog.returnData=$("#amount").val();
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
	//修改pos
	function changePos(){
		var posCode=$("#posCode").val();
		if(posCode=="")
			$("#procedureFee").val(0.0);
		else	
		$.ajax({
			url : "${ctx}/admin/custPay/findBankPos",
			type : "post",
			data : {
				'code' : posCode
			},
			dataType : "json",
			cache : false,
			async : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : "保存数据出错~"
				});
			},
			success : function(obj) {
				if (obj.length > 0) {
					var MinFee=parseFloat(obj[0].MinFee);
					var MaxFee=parseFloat(obj[0].MaxFee);
					var FeePerc=parseFloat(obj[0].FeePerc);
					
					var AcquireFeePerc=parseFloat(obj[0].AcquireFeePerc);
					var amount=$("#amount").val();
					if(amount<0){
						$("#procedureFee").val(0);
						return;
					}
					var ProcedureFee=FeePerc*1000*amount/1000;
					if(ProcedureFee<MinFee){
						ProcedureFee=MinFee;
					}
					if(ProcedureFee>MaxFee){
						ProcedureFee=MaxFee;
					}
					if(ProcedureFee<0){
						ProcedureFee=0;
					}
					ProcedureFee+=AcquireFeePerc*amount;
					$("#procedureFee").val(Math.round(accMul(ProcedureFee,100)) / 100);
				} 
			}
		});
	}
	//修复js小数乘法精度
	function accMul(arg1,arg2){
		var m=0,s1=arg1.toString(),s2=arg2.toString();
		try{m+=s1.split(".")[1].length;}catch(e){}
		try{m+=s2.split(".")[1].length;}catch(e){}
		return (Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)).toFixed(10);
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
					<input type="hidden" name="wfProcInstNo" value="${custPay.wfProcInstNo}" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label style="width:185px;">客户编号</label>
								<input type="text" id="custCode" maxlength="10" name="custCode"
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
								<house:token></house:token>
								<div class="validate-group row">
									<li><label>客户名称</label> <input type="text" id="descr"
										name="descr" style="width:160px;"
										value="${customerPayMap.descr }" readonly="readonly" /></li>
									<li><label>首批付款</label> <input type="text" id="firstpay"
										name="firstpay" style="width:160px;"
										value="${customerPayMap.firstpay }" readonly="readonly" /></li>
								</div>
								<div class="validate-group row">
									<li><label>楼盘</label> <input type="text" id="address"
										name="address" style="width:160px;"
										value="${customerPayMap.address }" readonly="readonly" /></li>
									<li><label>二批付款</label> <input type="text" id="secondpay"
										name="secondpay" style="width:160px;"
										value="${customerPayMap.secondpay }" readonly="readonly" /></li>
								</div>
								<div class="validate-group row">
									<li><label>工程造价</label> <input type="text"
										id="contractfee" name="contractfee" style="width:160px;"
										value="${customerPayMap.contractfee }" readonly="readonly" />
									</li>
									<li><label>三批付款</label> <input type="text" id="thirdpay"
										name="thirdpay" style="width:160px;"
										value="${customerPayMap.thirdpay }" readonly="readonly" /></li>
								</div>
								<div class="validate-group row">
									<li><label>设计费</label> <input type="text" id="designfee"
										name="designfee" style="width:160px;"
										value="${customerPayMap.designfee }" readonly="readonly" /></li>
									<li><label>尾批付款</label> <input type="text" id="fourpay"
										name="fourpay" style="width:160px;"
										value="${customerPayMap.fourpay }" readonly="readonly" /></li>
								</div>
								<div class="validate-group row">
									<li><label>已付款</label> <input type="text" id="haspay"
										name="haspay" style="width:160px;"
										value="${customerPayMap.haspay }" readonly="readonly" /></li>
									<li><label>合计</label> <input type="text" id="sumpay"
										name="sumpay" style="width:160px;"
										value="${customerPayMap.sumpay }" readonly="readonly" /></li>
								</div>
								<div class="validate-group row">
									<li><label>付款金额</label> <input type="number" id="amount"
										name="amount" style="width:160px;" value="${custPay.amount }" />
									</li>
									<li><label>登记日期</label> <input type="text" id="addDate" tabindex="-1"
										name="addDate" class="i-date" style="width:160px;"
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										value="<fmt:formatDate value='${custPay.addDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
									</li>
								</div>
								<div class="validate-group row">
									<li><label>收款账号</label> <select id="rcvAct" name="rcvAct"></select>
									</li>
									<li><label>付款日期</label> <input type="text" id="date" tabindex="-1"
										name="date" class="i-date" style="width:160px;"
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										value="<fmt:formatDate value='${custPay.date}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
									</li>
								</div>
								<div class="validate-group row">
									<li><label>手续费</label> <input type="number"
										id="procedureFee" name="procedureFee" style="width:160px;"
										value="${custPay.procedureFee }" /></li>
									<li><label>POS机</label> <select id="posCode" name="posCode" onchange="changePos()"></select>
									</li>
								</div>
								<div class="validate-group row">
									<li><label>收款单号</label> <input type="text" id="payNo"
										name="payNo" style="width:160px;" value="${custPay.payNo }" />
									</li>
									<li><label>类型</label> <house:xtdm
										id="type" dictCode="CPTRANTYPE"  value="${custPay.type }"></house:xtdm>
									</li>
								</div>
								<div class="validate-group row">
									<li id="discTokenNo_li" hidden="true"><label>优惠券号</label> <input type="text" id="discTokenNo"
										name="discTokenNo" style="width:160px;" value="${custPay.discTokenNo }" readonly="readonly" />
									</li>
								</div>
								<div class="validate-group row">
									
									<li><label class="control-textarea">备注</label> <textarea
											id="remarks" name="remarks"
											style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${custPay.remarks
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
