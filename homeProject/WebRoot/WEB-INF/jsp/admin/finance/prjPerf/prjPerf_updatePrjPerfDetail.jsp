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
	<title>结算工地业绩结算明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	$(function() {
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:"${ctx}/admin/prjPerf/doUpdatePrjPerfDetail",
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
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
		
		$("#dataForm").bootstrapValidator({
			excluded:[":disabled"],
	        message : 'This value is not valid',
	        feedbackIcons : {/*input状态样式图片*/
	            validating : 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	checkPerf: {
			        validators: { 
			            notEmpty: { 
			            	message: '结算业绩不能为空'  
			            },
			             numeric: {
			            	message: '结算业绩只能是数字' 
			            },
			        }
		      	},
		      	sceneDesignerCheck: {
		      	    validators: {
		      	        greaterThan: {
		      	            message: "现场设计师结算业绩须大于等于0",
		      	            value: 0
		      	        }
		      	    }
		      	},
		      	noSceneDesignerCheck: {
                    validators: {
                        greaterThan: {
                            message: "非现场设计师结算业绩须大于等于0",
                            value: 0
                        }
                    }
                }
			},
	    	submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	    
		});	
			
	});
	
	//
	function calcAllPlan(){
		//dBasePlan+dManageFee+dMainPlan+dIntPlan+dCupPlan+dSoftPlan+dServPlan;
		$("#allPlan").val(getData("basePlan")+getData("manageFee")+getData("mainPlan")+
				getData("intPlan")+getData("cupPlan")+getData("softPlan")+getData("servPlan"));
	}
	//计算合同总价
	function calcContractFee(){
		//预算小计-基础协议优惠
		$("#contractFee").val(getData("allPlan")-getData("baseDisc"));
	}
	
	//计算结算金额   
	function calcCheckAmount(){
		//合同总价+基础增减+主材增减+管理费增减+服务性增减+软装增减+集成增减-增减优惠
		//dContractFee+dBaseChg+dManageChg+dMainChg+dServChg+dSoftChg+dIntChg-dChgDisc
		$("#checkAmount").val(myRound(getData("contractFee")+getData("baseChg")+getData("manageChg")+getData("mainChg")+
						getData("servChg")+getData("softChg")+getData("intChg")-getData("chgDisc"),2)
			);
	}
	
	//计算扣减金额
	function calcPerfDisc(){
		//基础预算-远程费-基础优惠+基础增减-增减优惠)*(1-业绩比例)
		//(dBasePlan - dLongFee - dBaseDisc + dBaseChg - dChgDisc) * (1-dPerfPerc)
		$("#perfDisc").val((getData("basePlan")-getData("longFee")-getData("baseDisc")+getData("baseChg")-getData("chgDisc"))
					*(1-getData("perfPerc")));
	}
	
	//计算结算业绩
	//(dBaseChg-dChgDisc+dMainChg+dIntChg+dSoftChg)
	//+dServChg/3-dFurnChg/2+dBasePlan+
    //  dMainPlan+dIntPlan+dCupPlan+dSoftPlan+dServPlan/3-dFurnPlan/2-dBaseDisc-
    //  dLongFee+dDesignFee+dDesignChg-dPerfDisc-dBaseDeduction-dItemDeduction, -2)
	
    /* 不根据公式计算结算业绩 by zjf20201013
    function calcCheckPerf(){
	    var checkPerf = myRound((getData("baseChg")-getData("chgDisc")+getData("mainChg")+getData("intChg")+getData("softChg"))
			+getData("servChg")/3-getData("furnChg")/2+getData("basePlan")
			+getData("mainPlan")+getData("intPlan")+getData("cupPlan")+getData("softPlan")+getData("servPlan")/3-getData("furnPlan")/2-getData("baseDisc")
			-getData("longFee")
			+getData("designFee")+getData("designChg")-getData("perfDisc")-getData("baseDeduction")-getData("itemDeduction")
			,2);
		return checkPerf;
	}
	*/
	
	//计算提成
	function calcProvide(){
		// $("#provideCard").val(calcCheckPerf()-getData("designFee")-getData("designChg"));
		$("#provideCard").val(getData("checkPerf"));
	}
	
	function getData(data_id){
		return myRound($("#"+data_id).val(),4);
	}
	
	function calcData(){
		$("#isModified").val("1");
		calcAllPlan();
		calcContractFee();
		calcCheckAmount(); 
		calcPerfDisc();
		//calcCheckPerf();
		calcProvide();
		// $("#checkPerf").val(calcCheckPerf());  不根据公式计算结算业绩 by zjf20201013
		
		/*
		if($.trim("${map.perfexpr }") != ""){
			var perfExpr = $.trim($("#perfExpr").val());
			perfExpr = perfExpr.replace("@ContractFee@",getData("checkAmount")); 
			perfExpr = perfExpr.replace("@BasePlan@",getData("basePlan")+getData("baseChg")); 
			perfExpr = perfExpr.replace("@ManageFee@",getData("manageFee")+getData("manageChg")); 
			perfExpr = perfExpr.replace("@SoftFee_Furniture@",getData("furnPlan")+getData("furnChg")); 
			perfExpr = perfExpr.replace("@MainServPlan@",getData("servPlan")+getData("servChg")); 
			perfExpr = perfExpr.replace("@OtherDisc@",getData("softTokenAmount")+getData("gift")); 
			perfExpr = perfExpr.replace("@BaseDeduction@",getData("baseDeduction")); 
			perfExpr = perfExpr.replace("@ItemDeduction@",getData("itemDeduction"));
			perfExpr = perfExpr.replace("@LongFee@",getData("longFee"));
			perfExpr = perfExpr.replace("@PerfMarkup@",getData("perfmarkup"));
			perfExpr = perfExpr.replace("@Tax@",getData("tax")+getData("taxChg"));  
			perfExpr = perfExpr.replace("@SoftPlan@",getData("softPlan"));  
			console.log(perfExpr);
			$("#checkPerf").val(myRound(eval(perfExpr),4));
			$("#preperf").val(myRound(eval(perfExpr),4));
			$("#provideCard").val($("#checkPerf").val());
		}
		*/
		if("1"==$.trim("{map.custtype}")){
			$("#provideAmount").val(myRound($("#provideCard").val()*0.007,2));
		}else{
			$("#provideAmount").val(myRound($("#provideCard").val()*0.005,2));
		}
	}
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
   				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
						<%-- &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<span>${map.perfexprremarks }</span>--%>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
        		<div class="panel-body">		
					<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
						<input type="hidden" id="pk" name="pk" style="width:160px;" value="${map.pk }" readonly="true"/>
						<input type="hidden" id="perfExpr" name="perfExpr" style="width:160px;" value="${map.perfexpr }" readonly="true"/>
						<input type="hidden" id="isModified" name="isModified" style="width:160px;" value="${map.ismodified }" readonly="true"/>
						<ul class="ul-form">
							<div class="validate-group row" >
								<li>
									<label>客户名称</label>																					
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${map.custcode }|${map.custdescr }" readonly="true"/>
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:450px;" value="${map.address }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label>发包金额</label>
									<input type="text" id="baseCtrlAmount" name="baseCtrlAmount" style="width:160px;" value="${map.basectrlamount }" readonly="true"/>
								</li>
								<li>
									<label>面积</label>
									<input type="text" id="area" name="area" style="width:160px;" value="${map.area }" readonly="true"/>
								</li>
								<li>
									<label>客户类型</label>
									<input type="text" id="custTypeDescr" name="custTypeDescr" style="width:160px;" value="${map.custtypedescr }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label>设计费</label>
									<input type="text" id="designFee" name="designFee" onkeyup="calcData()" style="width:160px;" value="${map.designfee }" />
								</li>
								<li>
									<label>基础预算</label>
									<input type="text" id="basePlan" name="basePlan" onkeyup="calcData()" style="width:160px;" value="${map.baseplan }" />
								</li>
								<li>
									<label>基础个性化预算</label>
									<input type="text" id="basePersonalPlan" name="basePersonalPlan"  style="width:160px;" value="${map.basepersonalplan }" />
								</li>
								<li>
									<label>基础个性化增减</label>
									<input type="text" id="basePersonalChg" name="basePersonalChg"  style="width:160px;" value="${map.basepersonalchg }" />
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label>远程费</label>
									<input type="text" id="longFee" name="longFee" onkeyup="calcData()" style="width:160px;" value="${map.longfee }" />
								</li>
								<li>
									<label>管理费</label>
									<input type="text" id="manageFee" name="manageFee" onkeyup="calcData()" style="width:160px;" value="${map.managefee }" />
								</li>
								<li>
									<label>套餐外基础增项</label>
									<input type="text" id="setAdd" name="setAdd" style="width:160px;" value="${map.setadd }" />
								</li>
								<li>
									<label>套餐内减项</label>
									<input type="text" id="setMinus" name="setMinus" style="width:160px;" value="${map.setminus }" />
								</li>
								
								</div>
							<div class="validate-group row" >
								<li>
									<label>主材预算</label>
									<input type="text" id="mainPlan" name="mainPlan" onkeyup="calcData()" style="width:160px;" value="${map.mainplan }" />
								</li>
								<li>
									<label>集成预算</label>
									<input type="text" id="intPlan" name="intPlan" onkeyup="calcData()" style="width:160px;" value="${map.intplan }" />
								</li>
								<li>
									<label>橱柜预算</label>
									<input type="text" id="cupPlan" name="cupPlan" onkeyup="calcData()" style="width:160px;" value="${map.cupplan }" />
								</li>
								<li>
									<label>软装预算</label>
									<input type="text" id="softPlan" name="softPlan" onkeyup="calcData()" style="width:160px;" value="${map.softplan }" />
								</li>
								
							</div>
							<div class="validate-group row" >
								<li>
									<label>服务性产品预算</label>
									<input type="text" id="servPlan" name="servPlan" onkeyup="calcData()" style="width:160px;" value="${map.servplan }" />
								</li>
								<li>
									<label>家具预算</label>
									<input type="text" id=furnPlan name="furnPlan" onkeyup="calcData()" style="width:160px;" value="${map.furnplan }" />
								</li>
								<li>
									<label>预算小计</label>
									<input type="text" id="allPlan" name="allPlan" style="width:160px;" value="${map.allplan }" readonly="true"/>
								</li>
								<li>
									<label>协议优惠额</label>
									<input type="text" id="baseDisc" name="baseDisc" onkeyup="calcData()" style="width:160px;" value="${map.basedisc }" />
								</li>
								
							</div>
							<div class="validate-group row" >
								<li>
									<label>税金</label>
									<input type="text" id="tax" name="tax" onkeyup="calcData()" style="width:160px;" value="${map.tax }" />
								</li>
								<li>
									<label>合同总价</label>
									<input type="text" id="contractFee" name="contractFee" style="width:160px;" value="${map.contractfee }" readonly="true"/>
								</li>
								<li>
									<label>基础增减</label>
									<input type="text" id="baseChg" name="baseChg" onkeyup="calcData()" style="width:160px;" value="${map.basechg }" />
								</li>
								<li>
									<label>管理费增减</label>
									<input type="text" id="manageChg" name="manageChg" onkeyup="calcData()" style="width:160px;" value="${map.managechg }"/>
								</li>
								
							</div>
							<div class="validate-group row" >
								<li>
									<label>税金增减</label>
									<input type="text" id="taxChg" name="taxChg" onkeyup="calcData()" style="width:160px;" value="${map.taxchg }" />
								</li>
								<li>
									<label>设计费增减</label>
									<input type="text" id="designChg" name="designChg" onkeyup="calcData()" style="width:160px;" value="${map.designchg }"/>
								</li>
								<li>
									<label>主材增减</label>
									<input type="text" id="mainChg" name="mainChg" onkeyup="calcData()" style="width:160px;" value="${map.mainchg }"/>
								</li>
								<li>
									<label>集成增减</label>
									<input type="text" id="intChg" name="intChg" onkeyup="calcData()" style="width:160px;" value="${map.intchg }"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label>软装增减</label>
									<input type="text" id="softChg" name="softChg" onkeyup="calcData()" style="width:160px;" value="${map.softchg }" />
								</li>
								<li>
									<label>家具增减</label>
									<input type="text" id="furnChg" name="furnChg" onkeyup="calcData()" style="width:160px;" value="${map.furnchg }" />
								</li>
								<li>
									<label>服务性产品增减</label>
									<input type="text" id="servChg" name="servChg" onkeyup="calcData()" style="width:160px;" value="${map.servchg }" />
								</li>
								<li>
									<label>优惠变更</label>
									<input type="text" id="chgDisc" name="chgDisc" onkeyup="calcData()" style="width:160px;" value="${map.chgdisc }"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label>结算金额</label>
									<input type="text" id="checkAmount" name="checkAmount" style="width:160px;" value="${map.checkamount }" readonly="true"/>
								</li>
								<li>
									<label>业绩比例</label>
									<input type="text" id="perfPerc" name="perfPerc" style="width:160px;" value="${map.perfperc }" readonly="true"/>
								</li>
								<li>
									<label>扣减金额</label>
									<input type="text" id="perfDisc" name="perfDisc" style="width:160px;" value="${map.perfdisc }" readonly="true"/>
								</li>
								<li>
									<label>基础单项扣减</label>
									<input type="text" id="baseDeduction" name="baseDeduction" onkeyup="calcData()" style="width:160px;" value="${map.basededuction}" />
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label>材料单项扣减</label>
									<input type="text" id="itemDeduction" name="itemDeduction" onkeyup="calcData()" style="width:160px;" value="${map.itemdeduction}" />
								</li>
								<li>
									<label>软装券金额</label>
									<input type="text" id="softTokenAmount" name="softTokenAmount" onkeyup="calcData()" style="width:160px;" value="${map.softtokenamount }" />
								</li>
								<li>
									<label>实物赠送</label>
									<input type="text" id="gift" name="gift" onkeyup="calcData()" style="width:160px;" value="${map.gift }" />
								</li>
								<li class="form-validate">
									<label>结算业绩</label>
									<input type="text" id="checkPerf" name="checkPerf" style="width:160px;" value="${map.checkperf }" />
								</li>
							</div>
							<div class="validate-group row" >	
								<li>
									<label>提成基数</label>
									<input type="text" id="provideCard" name="provideCard" style="width:160px;" value="${map.providecard }" readonly="true"/>
								</li><li>
									<label>提成金额</label>
									<input type="text" id="provideAmount" name="provideAmount" style="width:160px;" value="${map.provideamount }" readonly="true"/>
								</li>
								<li>
									<label>质检员</label>
									<input type="text" id="checkManDescr" name="checkManDescr" style="width:160px;" value="${map.checkmandescr }" readonly="true"/>
								</li>
								<li>
									<label>拖期天数</label>
									<input type="text" id="delayDay" name="delayDay" style="width:160px;" value="${map.delayday }" />
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label>项目经理</label>
									<input type="text" id="projectManDescr" name="projectManDescr" style="width:160px;" value="${map.projectmandescr }" readonly="true"/>
								</li>
								<li>
									<label>工程部经理</label>
									<input type="text" id="prjDeptLeaderDescr" name="prjDeptLeaderDescr" style="width:160px;" value="${map.prjdeptleaderdescr }" readonly="true"/>
								</li>
								<li>
									<label>工程部</label>
									<input type="text" id="dept2Descr" name="dept2Descr" style="width:160px;" value="${map.dept2descr }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label>基础变更次数</label>
									<input type="text" id="baseChgCnt" name="baseChgCnt" style="width:160px;" value="${map.basechgcnt }" readonly="true"/>
								</li>
								<li>
									<label>主材变更次数</label>
									<input type="text" id="mainChgCnt" name="mainChgCnt" style="width:160px;" value="${map.mainchgcnt }" readonly="true"/>
								</li>
								<li>
									<label>集成变更次数</label>
									<input type="text" id="intChgCnt" name="intChgCnt" style="width:160px;" value="${map.intchgcnt }" readonly="true"/>
								</li>
								<li>
									<label>软装变更次数</label>
									<input type="text" id="softChgCnt" name="softChgCnt" style="width:160px;" value="${map.softchgcnt }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label>服务性变更次数</label>
									<input type="text" id="servChgCnt" name="servChgCnt" style="width:160px;" value="${map.servchgcnt }" readonly="true"/>
								</li>
								<li>
									<label>合计变更次数</label>
									<input type="text" id="allChgCnt" name="allChgCnt" style="width:160px;" value="${map.allchgcnt }" readonly="true"/>
								</li>
								<li>
									<label>人工修改</label>
									<input type="text" id="isModifiedDescr" name="isModifiedDescr" style="width:160px;" value="${map.ismodifieddescr }" readonly="true"/>
								</li>
								<li>
									<label>业绩折扣</label>
									<input type="text" id="perfmarkup" name="perfmarkup" style="width:160px;" value="${map.perfmarkup }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li >
									<label>折扣前业绩</label>
									<input type="text" id="preperf" name="preperf" style="width:160px;" value="${map.preperf }" readonly="true"/>
								</li>
								<li class="form-validate">
									<label>现场设计师结算业绩</label>
									<input type="text" id="sceneDesignerCheck" name="sceneDesignerCheck" value="${map.scenedesignercheck}"/>
								</li>
								<li class="form-validate">
									<label>非现场设计师结算额</label>
									<input type="text" id="noSceneDesignerCheck" name="noSceneDesignerCheck" value="${map.noscenedesignercheck}"/>
								</li>
							</div>
						</ul>
					</form>	
				</div>
			</div>
		</div>
	</body>	
</html>
