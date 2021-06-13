<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>施工合同信息录入</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builderNum.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var firstLoad=0;
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			area:{  
				validators: {  
					notEmpty: {  
						message: '平方数不能为空'  
					}
				}  
			},
			designFee:{  
				validators: {  
					notEmpty: {  
						message: '实收设计费不能为空'  
					}
				}  
			},
			achievDed:{  
				validators: {  
					notEmpty: {  
						message: '业绩调整数不能为空'  
					}
				}  
			},
			measureFee:{  
				validators: {  
					notEmpty: {  
						message: '量房费不能为空'  
					}
				}  
			},
			drawFee:{  
				validators: {  
					notEmpty: {  
						message: '制图费不能为空'  
					}
				}  
			},
			colorDrawFee:{  
				validators: {  
					notEmpty: {  
						message: '效果图费不能为空'  
					}
				}  
			},
			baseDisc:{  
				validators: {  
					notEmpty: {  
						message: '基础协议优惠不能为空'  
					}
				}  
			},
			gift:{  
				validators: {  
					notEmpty: {  
						message: '实物赠送不能为空'  
					}
				}  
			},
			softTokenAmount:{  
				validators: {  
					notEmpty: {  
						message: '软装券金额不能为空'  
					}
				}  
			},
			
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
});

$(function(){
	if("${customer.status}"=="4"||"${customer.status}"=="5"){
		$("#dataForm input[type='checkbox']").attr("disabled",true);
		$("#baseDisc").attr("readonly",true);
	}
	if("${customer.custType}"=="1"){
		$("#custTypeHide_4").hide();
		$(".custTypeHide_4").hide();
	}
	if (${customer.containMain}==1){
		$("#ck_mainFee").prop("checked",true);
	}
	if (${customer.containMainServ}==1){
		$("#ck_mainServFee").prop("checked",true);
	}
	if (${customer.containInt}==1){
		$("#ck_integrateFee").prop("checked",true);
	}
	if (${customer.containBase}==1){
		$("#ck_baseFee").prop("checked",true);
	}
	if (${customer.containCup}==1){
		$("#ck_cupBoardFee").prop("checked",true);
	}
	if (${customer.containSoft}==1){
		$("#ck_softFee").prop("checked",true);
	}
	$("#jzyjjs").val("${customer.jzyjjs}");
	
	var obj=new Object();
	obj.id="ck_mainServFee";
	checkClick(obj);
	obj.id="ck_integrateFee";
	checkClick(obj);
	obj.id="ck_baseFee";
	checkClick(obj);
	obj.id="ck_cupBoardFee";
	checkClick(obj);
	obj.id="ck_softFee";
	checkClick(obj);
	obj.id="ck_mainFee";
	checkClick(obj);
	firstLoad=1;
});
function checkClick(obj){
	if (obj.id=="ck_mainServFee"){
		if ($("#ck_mainServFee").prop("checked")){//服务产品勾选
			$("#containMainServ").val("1");
			$("#mainServFee").val("${customer.mainServFee}");  //服务产品费
			$("#manageFeeServ").val("${customer.manageFeeServ}");//服务管理费
		}else{
			$("#containMainServ").val("0"); 
			$("#mainServFee").val("0"); 
			$("#manageFeeServ").val("0");
		}
	}
	if (obj.id=="ck_integrateFee"){
		if ($("#ck_integrateFee").prop("checked")){
			$("#containInt").val("1");
			$("#integrateFee").val("${customer.integrateFee}");//集成费用
			$("#integrateDisc").val("${customer.integrateDisc}");//集成优惠
			$("#manageFeeInt").val("${customer.manageFeeInt}");//集成管理费
		}else{
			$("#containInt").val("0");
			$("#integrateFee").val("0");
			$("#integrateDisc").val("0");
			$("#manageFeeInt").val("0");
		}
	}
	/* if (obj.id=="ck_baseFee"){
		if ($("#ck_baseFee").prop("checked")){
			$("#containBase").val("1");
			$("#baseFee").val("${customer.baseFee}");//基础费用
			$("#baseDisc").val("${customer.baseDisc}");//基础优惠
			$("#baseFeeDirct").val("${customer.baseFeeDirct}");//基础直接费
			$("#baseFeeComp").val("${customer.baseFeeComp}"); //基础综合费
			$("#manageFeeBase").val("${customer.manageFeeBase}");//基础管理费
			$("#baseDisc").attr("readonly",true); 
		}else{
			$("#containBase").val("0");
			$("#baseFee").val("0");
			$("#baseDisc").val("0");
			$("#baseFeeDirct").val("0");
			$("#baseFeeComp").val("0");
			$("#manageFeeBase").val("0");
			$("#baseDisc").attr("readonly",false);
		}
	} */
	if (obj.id=="ck_cupBoardFee"){
		if ($("#ck_cupBoardFee").prop("checked")){
			$("#containCup").val("1");  
			$("#cupBoardFee").val("${customer.cupBoardFee}");//橱柜费
			$("#cupBoardDisc").val("${customer.cupBoardDisc}");//橱柜优惠
			$("#manageFeeCup").val("${customer.manageFeeCup}");//橱柜管理费
		}else{
			$("#containCup").val("0");
			$("#cupBoardFee").val("0");
			$("#cupBoardDisc").val("0");
			$("#manageFeeCup").val("0");
		}
	}
	if (obj.id=="ck_softFee"){
		if ($("#ck_softFee").prop("checked")){
			$("#containSoft").val("1");
			$("#softFee").val("${customer.softFee}"); //软装费
			$("#softDisc").val("${customer.softDisc}");//软装优惠
			$("#softOther").val("${customer.softOther}");//软装其他费用
			$("#manageFeeSoft").val("${customer.manageFeeSoft}");//软装管理费
		}else{
			$("#containSoft").val("0");
			$("#softFee").val("0");
			$("#softDisc").val("0");
			$("#softOther").val("0");
			$("#manageFeeSoft").val("0");
		}
	}
	if (obj.id=="ck_mainFee"){
		if ($("#ck_mainFee").prop("checked")){
			$("#containMain").val("1");
			$("#mainFee").val("${customer.mainFee}"); //
			$("#mainDisc").val("${customer.mainDisc}");//
			$("#manageFeeMain").val("${customer.manageFeeMain}");//
			
		}else{
			$("#containMain").val("0");
			$("#mainFee").val("0");
			$("#mainDisc").val("0");
			$("#manageFeeMain").val("0");
		}
	}
	
	$("#manageFee").val(calculateManageFee());
	$("#jzyjjs").val(calculateJzyjjs());
	$("#baseFee").val(calculateBaseFee());
	$("#contractFee").val(calculateContract());
	
	return false;
}

function calculateManageFee(){
	var result = 0;
	result = parseFloat($("#manageFeeBase").val()==""?0:$("#manageFeeBase").val())
		+parseFloat($("#manageFeeMain").val()==""?0:$("#manageFeeMain").val())
		+parseFloat($("#manageFeeInt").val()==""?0:$("#manageFeeInt").val())
		+parseFloat($("#manageFeeServ").val()==""?0:$("#manageFeeServ").val())
		+parseFloat($("#manageFeeSoft").val()==""?0:$("#manageFeeSoft").val())
		+parseFloat($("#manageFeeCup").val()==""?0:$("#manageFeeCup").val());
	return result;
}
function calculateBaseFee(){
	var result = 0;
	
	result = parseFloat($("#baseFeeDirct").val()==""?0:$("#baseFeeDirct").val())
		+parseFloat($("#baseFeeComp").val()==""?0:$("#baseFeeComp").val())
		+parseFloat($("#manageFee").val()==""?0:$("#manageFee").val());
		
	return result;
}
function calculateContract(){
	var result = 0;
	result = parseFloat($("#baseFee").val()==""?0:$("#baseFee").val())-parseFloat($("#baseDisc").val()==""?0:$("#baseDisc").val())
		+parseFloat($("#mainFee").val()==""?0:$("#mainFee").val())-parseFloat($("#mainDisc").val()==""?0:$("#mainDisc").val())
		+parseFloat($("#softFee").val()==""?0:$("#softFee").val())-parseFloat($("#softDisc").val()==""?0:$("#softDisc").val())
		+parseFloat($("#softOther").val()==""?0:$("#softOther").val())
		+parseFloat($("#integrateFee").val()==""?0:$("#integrateFee").val())-parseFloat($("#integrateDisc").val()==""?0:$("#integrateDisc").val())
		+parseFloat($("#cupBoardFee").val()==""?0:$("#cupBoardFee").val())-parseFloat($("#cupBoardDisc").val()==""?0:$("#cupBoardDisc").val())
		+parseFloat($("#mainServFee").val()==""?0:$("#mainServFee").val());
	$("#contractFee").val(result);
	if("${customer.tax}"==0 || firstLoad != 0){
		$("#tax").val(myRound(calculateTax()));
	}
	
	return result;
}

function calculateTax(){
	var result = 0;
	result = (parseFloat($("#contractFee").val()==""?0:$("#contractFee").val())-parseFloat($("#returnDesignFee").val()==""?0:$("#returnDesignFee").val())
		+parseFloat($("#stdDesignFee").val()==""?0:$("#stdDesignFee").val()))*${taxRate};
	return myRound(result);
}
function resetConstructType(constructType){
	var containMain=0,containSoft=0;
	var mainFee=0,softFee=0;
	var result = constructType;
	if ("${customer.custType}"=="1" && constructType!="4"){
		if ($("#ck_containMan").prop("checked")){
			containMan = 1;
		}else{
			containMan = 0;
		}
		mainFee = $("#mainFee").val();
		if ($("#ck_containSoft").prop("checked")){
			containSoft = 1;
		}else{
			containSoft = 0;
		}
		softFee = $("#softFee").val();
		if (containMain==0 || mainFee==0){
			result = "1";
		}else if ((containMain==1 && mainFee!=0) && (containSoft==0 || softFee==0)){
			result = "2";
		}else if ((containMain==1 && mainFee!=0) && (containSoft!=0 && softFee!=0)){
			result = "3";
		}else{
			result = "0";
		}
	}
	return result;
}
function calculateJzyjjs(){
	var jzyjjs=0;
	var containMain=0,containSoft=0,containInt=0,containCup=0,containBase=0;
	
	if ($("#ck_mainFee").prop("checked")){
		containMan = 1;
	}else{
		containMan = 0;
	}
	if ($("#ck_softFee").prop("checked")){
		containSoft = 1;
	}else{
		containSoft = 0;
	}
	if ($("#ck_integrateFee").prop("checked")){
		containInt = 1;
	}else{
		containInt = 0;
	}
	if ($("#ck_cupBoardFee").prop("checked")){
		containCup = 1;
	}else{
		containCup = 0;
	}
	if ($("#ck_containBase").prop("checked")){
		containBase = 1;
	}else{
		containBase = 0;
	}
  	jzyjjs =
  	(parseFloat($("#baseFeeDirct").val()==""?0:$("#baseFeeDirct").val())+
  	parseFloat($("#baseFeeComp").val()==""?0:$("#baseFeeComp").val())-
  	parseFloat($("#baseDisc").val()==""?0:$("#baseDisc").val())-
  	parseFloat($("#mainDisc").val()==""?0:$("#mainDisc").val())*containMan-
  	parseFloat($("#softDisc").val()==""?0:$("#softDisc").val())*containSoft-
  	parseFloat($("#integrateDisc").val()==""?0:$("#integrateDisc").val())*containInt-
  	parseFloat($("#cupBoardDisc").val()==""?0:$("#cupBoardDisc").val())*containCup)*1-//containBase- //原本不知道为什么要判断基础费是否勾选
  	parseFloat($("#marketFund").val()==""?0:$("#marketFund").val());
  	return jzyjjs;
}
function changeDesignFee(e){
	if(e){
		$("#"+e.id).val(e.value.replace(/[^\-?\d.]/g,''));
	}
	var position=$.trim($("#position").val());
	var area =$.trim($("#area").val());
	var designFee =$.trim($("#designFee").val());//实收设计费
	var posiDesignFee=0;
	if(position!=""){
		$.ajax({
			url:"${ctx}/admin/customerSghtxx/getPosiDesignFee",
			data: {position:position, custType:"${customer.custType}"},
			type: "post",
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	posiDesignFee=myRound(obj,2);
		    	if(position==""||area==""||area=="0"){//||designFee=="0"||designFee==""
		    		if(designFee==""){
		    			designFee=0;
		    			$("#stdDesignFee").val(0);//设计费
						$("#returnDesignFee").val(0);//设计费返还
		    		}
		    		if(area==""){
		    			area=0;
		    			$("#stdDesignFee").val(0);//设计费
						$("#returnDesignFee").val(0);//设计费返还	
		    		}
					//$("#stdDesignFee").val(posiDesignFee*area);//设计费
					//$("#returnDesignFee").val(posiDesignFee*area-designFee);//设计费返还
					$("#tax").val(calculateTax());
				}else{
					$("#stdDesignFee").val(posiDesignFee*area);//设计费
					$("#returnDesignFee").val(posiDesignFee*area-designFee);//设计费返还
					$("#tax").val(calculateTax());
				}
		    }
		});
	}else{
		$("#stdDesignFee").val(0);//设计费
		$("#returnDesignFee").val(0);//设计费返还
		$("#tax").val("");
	}
}

function changeDisc(){
	/* var baseDisc=$.trim($("#baseDisc").val());
	var contractFee=$.trim($("#contractFee").val());
	var jzyjjs=$.trim($("#jzyjjs").val());
	if("${customer.status}"!="4"&&"${customer.status}"!="5"){
		$("#contractFee").val(contractFee-baseDisc);
		$("#jzyjjs").val(jzyjjs-baseDisc);
	} */
	var baseDisc=$.trim($("#baseDisc").val());
	var marketFund=$.trim($("#marketFund").val());
	if(baseDisc!=""){
		$("#baseDisc").val(baseDisc.replace(/[^\-?\d.]/g,''));
	}
	if(marketFund!=""){
		$("#marketFund").val(marketFund.replace(/[^\-?\d.]/g,''));
	}
	checkClick(this);
}

function changeConDay(){
	var contractDay=$.trim($("#contractDay").val());
	var constructDay=$.trim($("#constructDay").val());
	if(contractDay!=""){
		return;
		if("${customer.status}"!="4"&&"${customer.status}"!="5"){
			$("#contractDay").val(contractDay.replace(/[^\-?\d.]/g,''));
			$("#constructDay").val(contractDay.replace(/[^\-?\d.]/g,''));
		}
	}
}

function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	var constructType=$.trim($("#constructType").val());
	var perfPercCode=$.trim($("#perfPercCode").val());
	var signDate=$.trim($("#signDate").val());
	if(constructType==""){
		art.dialog({
			content:"施工方式必填",
		});
		return;
	}
	if(perfPercCode==""){
		art.dialog({
			content:"业绩比例必填",
		});
		return;
	}
	if(new Date($("#signDate").val())>=new Date()){
		art.dialog({
			content:"合同签订时间不能大于当前时间",
		});
		return;
	}
	
	$.ajax({
		url:"${ctx}/admin/customerSghtxx/doUpdate",
		data:datas,
		type:"post",
		//dataType:"jsonType",
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		},
		success:function(obj){
			if(obj.rs==true){
				art.dialog({
					content:obj.msg,
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});				
			}else{
				$("#_form_token_uniq_id").val(obj.token.token);
				art.dialog({
					content:obj.msg,
					width:200
				});
			}
		}
	});
}
//onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
            	<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="M"/>
					<input type="hidden" name="containMain" id="containMain" value="${customer.containMain }"/>
					<input type="hidden" name="containMainServ" id="containMainServ" value="${customer.containMainServ }"/>
					<input type="hidden" name="containInt" id="containInt" value="${customer.containInt }"/>
					<input type="hidden" name="containBase" id="containBase" value="${customer.containBase }"/>
					<input type="hidden" name="containCup" id="containCup" value="${customer.containCup }"/>
					<input type="hidden" name="containSoft" id="containSoft" value="${customer.containSoft }"/>
					<ul class="ul-form">
					<div class="validate-group row">
						<div class="col-sm-3">
						<li class="form-validate">
							<label>客户编号</label>
							<input type="text" style="width:160px;" id="code" name="code" value="${customer.code }" readonly="readonly"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>客户状态</label>
							<house:xtdm id="status" dictCode="CUSTOMERSTATUS" value="${customer.status }" disabled="true"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${customer.address}" readonly="readonly"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>合同签订时间</label>
							<input type="text" style="width:160px;" id="signDate" name="signDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${customer.signDate }' pattern='yyyy-MM-dd'/>"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><input type="checkbox" id="ck_mainFee" name="ck_mainFee">主材费</label>
							<input type="text" id="mainFee" name="mainFee" style="width:160px;" value="${customer.mainFee}" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><input type="checkbox" id="ck_mainServFee" name="ck_mainServFee">服务性产品费</label>
							<input type="text" id="mainServFee" name="mainServFee" style="width:160px;" value="${customer.mainServFee}" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>主套餐费</label>
							<input type="text" id="mainSetFee" name="mainSetFee" style="width:160px;" value="${customer.mainSetFee}" readonly="true"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
							<li class="form-validate">
								<label><span class="required">*</span>平方数</label>
								<input type="text" style="width:160px;" id="area"  name="area" value="${customer.area }" />
							</li>
						</div>
						<div class="col-sm-3">
						<li class="form-validate">
							<label>主材优惠</label>
							<input type="text" style="width:160px;" id="mainDisc" name="mainDisc" value="${customer.mainDisc }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
	            			<li class="form-validate">
								<label>工程造价</label>
								<input type="text" style="width:160px;" id="contractFee" name="contractFee" value="${customer.contractFee }" readonly="true" title="工程造价=基础直接费+基础综合费+主材费+软装费+软装其他费+集成费+橱柜费+服务性产品费-基础协议优惠"/>
							</li>
						</div>
						<div class="col-sm-3">
            			<li style="position: relative;">
							<label>套餐内减项</label>
							<input type="text" style="width:160px;" id="setMinus" name="setMinus" value="${customer.setMinus }" readonly="true"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>实收设计费</label>
							<input type="text" id="designFee" name="designFee" style="width:160px;"  value="${customer.designFee}"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><input type="checkbox" id="ck_integrateFee" name="ck_integrateFee">集成费</label>
							<input type="text" id="integrateFee" name="integrateFee" style="width:160px;" value="${customer.integrateFee}" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>管理费</label>
							<input type="text" id="manageFee" name="manageFee" style="width:160px;" value="${customer.manageFee}" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>套餐外基础增项</label>
							<input type="text" style="width:160px;" id="setAdd" name="setAdd" value="${customer.setAdd }" readonly="true"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>业绩调整数</label>
							<input type="text" style="width:160px;" id="achievDed" name="achievDed" value="${customer.achievDed }" />
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>集成优惠</label>
							<input type="text" style="width:160px;" id="integrateDisc" name="integrateDisc" value="${customer.integrateDisc }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><input type="checkbox" id="ck_baseFee" name="ck_baseFee" hidden="true">基础费</label>
							<input type="text" id="baseFee" name="baseFee" style="width:160px;" value="${customer.baseFee}" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>远程费</label>
							<input type="text" style="width:160px;" id="longFee" name="longFee" value="${customer.longFee }" readonly="true"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>量房费</label>
							<input type="text" style="width:160px;" id="measureFee" name="measureFee" value="${customer.measureFee }" />
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><input type="checkbox" id="ck_cupBoardFee" name="ck_cupBoardFee">橱柜费</label>
							<input type="text" id="cupBoardFee" name="cupBoardFee" style="width:160px;" value="${customer.cupBoardFee}" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>基础直接费</label>
							<input type="text" id="baseFeeDirct" name="baseFeeDirct" style="width:160px;" value="${customer.baseFeeDirct}" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>基础管理费</label>
							<input type="text" id="manageFeeBase" name="manageFeeBase" style="width:160px;" value="${customer.manageFeeBase}" readonly="true"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>制图费</label>
							<input type="text" style="width:160px;" id="drawFee" name="drawFee" value="${customer.drawFee }" />
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>橱柜优惠</label>
							<input type="text" style="width:160px;" id="cupBoardDisc" name="cupBoardDisc" value="${customer.cupBoardDisc }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">	
            			<li class="form-validate">
							<label>基础综合费</label>
							<input type="text" style="width:160px;" id="baseFeeComp" name="baseFeeComp" value="${customer.baseFeeComp }"readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>主材管理费</label>
							<input type="text" style="width:160px;" id="manageFeeMain" name="manageFeeMain" value="${customer.manageFeeMain }" readonly="true"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>效果图费</label>
							<input type="text" style="width:160px;" id="colorDrawFee" name="colorDrawFee" value="${customer.colorDrawFee }" />
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><input type="checkbox" id="ck_softFee" name="ck_softFee">软装费</label>
							<input type="text" id="softFee" name="softFee" style="width:160px;" value="${customer.softFee}" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>客户名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;" value="${customer.descr}" readonly="readonly"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>集成管理费</label>
							<input type="text" style="width:160px;" id="manageFeeInt" name="manageFeeInt" value="${customer.manageFeeInt }"readonly="true"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>基础协议优惠</label>
							<input type="text" style="width:160px;" id="baseDisc" name="baseDisc"  value="${customer.baseDisc }"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>软装优惠</label>
							<input type="text" style="width:160px;" id="softDisc" name="softDisc" value="${customer.softDisc }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>业务员</label>
							<input type="text" style="width:160px;" id="businessManDescr" name="businessManDescr" value="${customer.businessManDescr }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>服务性产品管理费</label>
							<input type="text" style="width:160px;" id="manageFeeServ" name="manageFeeServ" value="${customer.manageFeeServ }" readonly="true"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>实物赠送</label>
							<input type="text" style="width:160px;" id="gift" name="gift" value="${customer.gift }" />
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>软装其他费用</label>
							<input type="text" style="width:160px;" id="softOther" name="softOther" value="${customer.softOther }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>设计师</label>
							<input type="text" style="width:160px;" id="designManDescr" name="designManDescr" value="${customer.designManDescr }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>软装管理费</label>
							<input type="text" style="width:160px;" id="manageFeeSoft" name="manageFeeSoft" value="${customer.manageFeeSoft }" readonly="true"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>二级部门</label>
							<input type="text" style="width:160px;" id="department2" name="department2" value="${department2.desc1 }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>一级部门</label>
							<input type="text" style="width:160px;" id="department1" name="department1" value="${department1.desc1 }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>翻单员</label>
							<input type="text" style="width:160px;" id="againManDescr" name="againManDescr" readonly value="${customer.againManDescr }"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>橱柜管理费</label>
							<input type="text" style="width:160px;" id="manageFeeCup" name="manageFeeCup" value="${customer.manageFeeCup }" readonly="true"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>合同工期</label>
							<input type="text" style="width:160px;" id="contractDay" name="contractDay" value="${customer.contractDay }" onchange="changeConDay()" />
							<span style="position: absolute;left:290px;width: 30px;top:5px;text-align: left;">天</span>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>砌墙面积</label>
							<input type="text" style="width:160px;" id="wallArea" name="wallArea" value="${customer.wallArea }" />
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>业绩比例</label>
							<house:dict id="perfPercCode" dictCode="" sqlLableKey="fd" sqlValueKey="code" value="${customer.perfPercCode }"
							sql="select rtrim(Code)+' '+Desc1 fd,code from tPerfPerc ORDER BY Code ASC"></house:dict>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>施工方式</label>
							<house:xtdm id="constructType" dictCode="CONSTRUCTTYPE" value="${customer.constructType }"></house:xtdm>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>设计费标准</label>
							<house:dict id="position" dictCode="" sqlLableKey="fd" sqlValueKey="position" value="${customer.position }" onchange="changeDesignFee()"
							sql="select  rtrim(Position) + ' ' + cast(a.DesignFee as varchar(20)) + ' ' + b.Desc2 fd, 
									a.position
								from tDesignFeeSd a
								left join tPosition b on a.Position = b.Code
								where a.CustType is null or a.CustType = '' or a.CustType='${customer.custType}'
								order by a.DispSeq">
							</house:dict>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>设计费</label>
							<input type="text" style="width:160px;" id="stdDesignFee" name="stdDesignFee" value="${customer.stdDesignFee }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>设计费返还</label>
							<input type="text" style="width:160px;" id="returnDesignFee" name="returnDesignFee" value="${customer.returnDesignFee }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>施工工期</label>
							<input type="text" style="width:160px;" id="constructDay" name="constructDay" value="${customer.constructDay }" readonly="true"/>
							<span style="position: absolute;left:290px;width: 30px;top:5px;text-align: left;">天</span>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate" id="custTypeHide_4">
							<label><span class="required">*</span>软装基础整改</label>
							<input type="text" style="width:160px;" id="softBaseFee" name="softBaseFee" value="${customer.softBaseFee }" />
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="custTypeHide_4">
							<label>壁纸款</label>
							<input type="text" style="width:160px;" id="softFeeWallPaper" name="softFeeWallPaper" value="${customer.softFeeWallPaper }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="custTypeHide_4">
							<label>窗帘款</label>
							<input type="text" style="width:160px;" id="softFeeCurtain" name="softFeeCurtain" value="${customer.softFeeCurtain }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>软装券金额</label>
							<input type="text" style="width:160px;" id="softTokenAmount" name="softTokenAmount" value="${customer.softTokenAmount }" />
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="custTypeHide_4" >
							<label>装饰及其他款</label>
							<input type="text" style="width:160px;" id="softFeeAdornment" name="softFeeAdornment" value="${customer.softFeeAdornment }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="custTypeHide_4">
							<label>灯具款</label>
							<input type="text" style="width:160px;" id="softFeeLight" name="softFeeLight" value="${customer.softFeeLight }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="custTypeHide_4">
							<label>家具款</label>
							<input type="text" style="width:160px;" id="softFeeFurniture" name="softFeeFurniture" value="${customer.softFeeFurniture }" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>营销基金</label>
							<input type="text" style="width:160px;" id="marketFund" name="marketFund" value="${customer.marketFund }" />
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-3">
            			<li class="form-validate">
							<label><span class="required">*</span>税金</label>
							<input type="text" style="width:160px;" id="tax" name="tax" value="${customer.tax }" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
						</li>
						</div>
						<div class="col-sm-3">
            			<li class="form-validate">
							<label>基础业绩基数</label>
							<input type="text" style="width:160px;" id="jzyjjs" name="jzyjjs" value="" readonly="true"/>
						</li>
						</div>
						<div class="col-sm-3">
	            			<li class="form-validate">
								<label>设计师电话</label>
								<input type="text" style="width:160px;" id="designPhone" name="designPhone" value="${customer.designPhone }"/>
							</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-6">
            			<li class="form-validate">
							<label>软装券券号</label>
							<input type="text" style="width:485px;" id="softTokenNo" name="softTokenNo" value="${customer.softTokenNo }"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-12">
            			<li class="form-validate">
							<label class="control-textarea">优惠说明</label>
							<textarea id="discRemark" name="discRemark" style="width:1100px;">${customer.discRemark }</textarea>
						</li>
						</div>
						<div class="col-sm-12">
            			<li class="form-validate">
							<label class="control-textarea">施工要求</label>
							<textarea id="constructRemark" name="constructRemark" style="width:1100px;">${customer.constructRemark }</textarea>
						</li>
						</div>
						<div class="col-sm-12">
            			<li class="form-validate">
            				<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" style="width:1100px;">${customer.remarks }</textarea>
						</li>
						</div>
					</div>
				</ul>
            </form>
         </div>
     </div>
</div>
</body>
<script type="text/javascript">
$(function(){
	$("#dataForm input[type='text']").each(function(){
		if ($(this).val() && !isNaN($(this).val())){
			$(this).val(parseFloat($(this).val()));
		}
	});
});
</script>
</html>
