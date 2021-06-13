<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
function closeWin(isCallBack,isPrevent){
	if(!isPrevent )return;
	Global.Dialog.closeDialog(isCallBack);
}
var hasCustProfit='${custProfit.custCode}';
var baseFee=0;
var itemFee=0;
var designFee=0;
var stdDesignFee=0;
var  manageFee=0;
 
$(function(){
	if(hasCustProfit){       
        manageFee=myRound(${customer.manageFeeBase}*${custProfit.containBase}+${customer.manageFeeSoft}*${custProfit.containSoft}
          				 +${customer.manageFeeMain}*${custProfit.containMain}+${customer.manageFeeServ}*${custProfit.containMainServ}
          				 +${customer.manageFeeInt}*${custProfit.containInt}+${customer.manageFeeCup}*${custProfit.containCup});
        baseFee=(myRound(${customer.baseFeeDirct}*${custProfit.baseDiscPer})+parseFloat(${customer.baseFeeComp})+parseFloat(manageFee)-parseFloat(${custProfit.baseDisc1})-parseFloat(${custProfit.baseDisc2}))*parseInt(${custProfit.containBase});  			
		itemFee=(parseFloat(${customer.mainFee})-parseFloat(${customer.mainDisc}))*parseInt(${custProfit.containMain})+${customer.mainServFee}*parseInt(${custProfit.containMainServ})
					+(parseFloat(${customer.softFee})-parseFloat(${customer.softDisc}))*parseInt(${custProfit.containSoft})
					+(parseFloat(${customer.integrateFee})-parseFloat(${customer.integrateDisc}))*parseInt(${custProfit.containInt})
					+(parseFloat(${customer.cupBoardFee})-parseFloat(${customer.cupBoardDisc}))*parseInt(${custProfit.containCup});
	
		designFee=parseInt(${custProfit.designFee});
		stdDesignFee=parseInt(${custProfit.stdDesignFee});
	}else{
		manageFee=parseFloat(${customer.manageFee});
		baseFee=parseFloat(${customer.baseFee})+parseFloat(${customer.baseFeeComp})+parseFloat(${customer.manageFee});
		itemFee=parseFloat(${customer.mainFee})-parseFloat(${customer.mainDisc})+parseFloat(${customer.mainServFee})
					+parseFloat(${customer.softFee})-parseFloat(${customer.softDisc})
					+parseFloat(${customer.integrateFee})-parseFloat(${customer.integrateDisc})
					+parseFloat(${customer.cupBoardFee})-parseFloat(${customer.cupBoardDisc});
	}
	$("#manageFee").val(manageFee);
	$("#baseFee").val(baseFee);
	$("#itemFee").val(itemFee);
	$("#contractSum").val(baseFee+itemFee);
	if ("${payRemark.DesignFeeType}"=="2"){   //1:实收设计师 ,2标准设计费
		$("#desinFeeName").text("标准设计费");
	}else{
		$("#desinFeeName").text("设计费");
	}      
	calculateTax();
	if('${itemPlan.isAuto}'=='1') autoProduce();
	isEqual();
})


function doCustPaypreSave(isAuto){
	var datas = $("#custPaypreDataForm").serialize()+"&"+ $("#contract_form").serialize();
	$.ajax({
		url:'${ctx}/admin/itemPlan/doCustPaypreSave',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		if(!isAuto){
	    			art.dialog({
					content: obj.msg,
					time: 1000
					
					});
	    		}
	    		
	    	}else{
				art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
}

function autoProduce(){
	if(!'${custProfit.position}'){
		art.dialog({
			content: "请选择设计费标准！"
		});
		return;
	}
	if("${payRemark}"=="") {
		art.dialog({
			content: "该客户无对应付款规则，生成付款计划失败！"
		});
		return;
	}
	var basePay1= parseFloat("${fourCustPay.firstpay}");
	var basePay2= parseFloat("${fourCustPay.secondpay}");
	var basePay3= parseFloat("${fourCustPay.thirdpay}");
	var basePay4=parseFloat("${fourCustPay.fourpay}");
	
	var payRemark1= "${payRemark.PayRemark1}";
	var payRemark2= "${payRemark.PayRemark2}";
	var payRemark3= "${payRemark.PayRemark3}";
	var payRemark4= "${payRemark.PayRemark4}";
	
	$("#basePay1").val(basePay1);
	$("#basePay2").val(basePay2);
	$("#basePay3").val(basePay3);
	$("#basePay4").val(basePay4);
	
	$("#payRemark1").val(payRemark1);
	$("#payRemark2").val(payRemark2);
	$("#payRemark3").val(payRemark3);
	$("#payRemark4").val(payRemark4);
	
	
	var designFee1=0   //parseFloat($("#designFee1").val());
	var designFee2=0   //parseFloat($("#designFee2").val());
	var designFee3=0  //parseFloat($("#designFee3").val());
	var designFee4=0  //parseFloat($("#designFee4").val());
	 
    if ("${payRemark.DesignFeeType}"==1 ){ //1:实收设计师 ,2标准设计费
    	designFee1= parseInt("${payRemark.IsRcvDesignFee1}")* designFee;
    	designFee2= parseInt("${payRemark.IsRcvDesignFee2}")* designFee;
    	designFee3= parseInt("${payRemark.IsRcvDesignFee3}")* designFee;
    	designFee4= parseInt("${payRemark.IsRcvDesignFee4}")* designFee;
    }else{
    	designFee1= parseInt("${payRemark.IsRcvDesignFee1}")* stdDesignFee;
    	designFee2= parseInt("${payRemark.IsRcvDesignFee2}")* stdDesignFee;
    	designFee3= parseInt("${payRemark.IsRcvDesignFee3}")* stdDesignFee;
    	designFee4= parseInt("${payRemark.IsRcvDesignFee4}")* stdDesignFee;
    }
    $("#designFee1").val(designFee1);
    $("#designFee2").val(designFee2);
    $("#designFee3").val(designFee3);
    $("#designFee4").val(designFee4);
	
	var prePay1=parseFloat($("#prePay1").val());
	var prePay2=parseFloat($("#prePay2").val());
	var prePay3=parseFloat($("#prePay3").val());
	var prePay4=parseFloat($("#prePay4").val());
	
	var sumAllPay1=callSumFee("sumAllPay1",basePay1,designFee1,prePay1);
	var sumAllPay2=callSumFee("sumAllPay2",basePay2,designFee2,prePay2);
	var sumAllPay3=callSumFee("sumAllPay3",basePay3,designFee3,prePay3);
	var sumAllPay4=callSumFee("sumAllPay4",basePay4,designFee4,prePay4);
	
	$("#sumAllPay").val(sumAllPay1+sumAllPay2+sumAllPay3+sumAllPay4);
	$("#basePaySum").val(basePay1+basePay2+basePay3+basePay4);
	doCustPaypreSave(true);
	isEqual(); 
}	

function callSumFee(id,basePay,designFee,prePay){
	if(!prePay) prePay=0;
	$("#"+id).val(myRound(basePay+designFee-prePay));
	return myRound(basePay+designFee-prePay);

}
function isEqual(){
	var designFeeReturn=parseFloat($("#returnDesignFee").val());
	var basePaySum=parseFloat($("#basePaySum").val());
	var tax=parseFloat($("#tax").val());
	if ("${payRemark.DesignFeeType}"=="2"){   //1:实收设计师 ,2标准设计费
		if((parseFloat(baseFee)+parseFloat(itemFee)+parseFloat(tax))!=parseFloat(basePaySum)+parseFloat(designFeeReturn)){
			$("#isEqual").html('付款计划、设计费返还总和不等于工程总价+税金！');
			$("#isEqual").show();
			return false;
		}else{
			$("#isEqual").hide();
			return true;
		}
	}else if ("${payRemark.DesignFeeType}"=="1"){
		if((parseFloat(baseFee)+parseFloat(itemFee)+parseFloat(tax))!=parseFloat(basePaySum)){
			$("#isEqual").html('付款计划总和不等于工程总价+税金');
			$("#isEqual").show();
			return false;
		}else{
			$("#isEqual").hide();
			return true;
		}
	}else{
		if((parseFloat(baseFee)+parseFloat(itemFee)+parseFloat(designFee)+parseFloat(tax))!=parseFloat(basePaySum)){
			$("#isEqual").html('付款计划总和不等于工程总价+实收设计费+税金！');
			$("#isEqual").show();
			return false;
		}else{
			$("#isEqual").hide();
			return true;	
		}
	}      			
}
function doChange(){
	var basePay1=parseFloat($("#basePay1").val());
	var basePay2=parseFloat($("#basePay2").val());
	var basePay3=parseFloat($("#basePay3").val());
	var basePay4=parseFloat($("#basePay4").val());
	var prePay1=parseFloat($("#prePay1").val());
	var prePay2=parseFloat($("#prePay2").val());
	var prePay3=parseFloat($("#prePay3").val());
	var prePay4=parseFloat($("#prePay4").val());
  	var designFee1=parseFloat($("#designFee1").val());
	var designFee2=parseFloat($("#designFee2").val());
	var designFee3=parseFloat($("#designFee3").val());
	var designFee4=parseFloat($("#designFee4").val());		
	var sumAllPay1=callSumFee("sumAllPay1",basePay1,designFee1,prePay1);
	var sumAllPay2=callSumFee("sumAllPay2",basePay2,designFee2,prePay2);
	var sumAllPay3=callSumFee("sumAllPay3",basePay3,designFee3,prePay3);
	var sumAllPay4=callSumFee("sumAllPay4",basePay4,designFee4,prePay4);
	$("#sumAllPay").val(sumAllPay1+sumAllPay2+sumAllPay3+sumAllPay4);
	$("#basePaySum").val(basePay1+basePay2+basePay3+basePay4);
	isEqual();
}
function calculateTax(){
	
    discAmount= myRound(${customer.baseFeeDirct} * (1.0 - ${custProfit.baseDiscPer} ) + parseFloat(${custProfit.baseDisc1}) + parseFloat(${custProfit.baseDisc2}) ) * parseInt(${custProfit.containBase}) 			
     			 + parseFloat(${customer.mainDisc})*parseInt(${custProfit.containMain})
     			 + parseFloat(${customer.softDisc})*parseInt(${custProfit.containSoft})
     			 + parseFloat(${customer.integrateDisc})*parseInt(${custProfit.containInt})
     			 + parseFloat(${customer.cupBoardDisc})*parseInt(${custProfit.containCup}) 
    $.ajax({
		url:"${ctx}/admin/itemPlan/getTax",
		type:"post",
		data:{code:"${customer.code}", contractFee:parseFloat($("#contractSum").val()==""?0:$("#contractSum").val()),
			  designFee:parseFloat("${custProfit.stdDesignFee}"==""? 0:"${custProfit.stdDesignFee}")-parseFloat($("#returnDesignFee").val()==""? 0:$("#returnDesignFee").val()),
			  discAmount:discAmount, setAdd:parseFloat(${customer.setAdd})* parseInt(${custProfit.containBase}),
			  manageFeeBase: parseFloat(${customer.manageFeeBase}) * parseInt(${custProfit.containBase}),
			  baseFeeComp: parseFloat(${customer.baseFeeComp}) * parseInt(${custProfit.containBase}),
			  baseFeeDirct: parseFloat(${customer.baseFeeDirct}) * parseInt(${custProfit.containBase}),
		},
		dataType:"json",
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
		},
		success:function(obj){
			if(obj){
				$("#tax").val(obj);
			}
		}
	});         
}	 
</script>
</head>
<body>
	<div class="body-box-list">
	<div class="query-form" style="border: 0px;">
		<form role="form" class="form-search" id="contract_form"  action="" method="post" target="targetFrame">
				<ul class="ul-form">
						<li><label>基础合计</label> <input type="text" id="baseFee" name="baseFee" 
							disabled="disabled" /></li>
						<li><label>材料合计</label> <input type="text" id="itemFee" name="itemFee" 
							disabled="disabled" /></li>
						<li><label>工程总价</label> <input type="text" id="contractSum" name="contractSum" "
							disabled="disabled" /></li>
						<li><label>管理费</label> <input type="text" id="manageFee" name="manageFee" disabled="disabled" /></li>
						<li><label>远程费</label> <input type="text" id="longFee" name="longFee"
						value="${customer.longFee}" disabled="disabled" /></li>
						<li><label>设计费返还</label> <input type="text" id="returnDesignFee" name="returnDesignFee"
						value="${custProfit.returnDesignFee}" disabled="disabled" /></li>
						<li ><label>税金</label> <input type="text" id="tax" name="tax" readonly="true" 
						value="${custProfit.tax}" /></li>
				</ul>
					
		</form>
	</div>
	<!--query-form-->
	<div class="pageContent" style="padding-top: 10px;border-bottom: 1px solid #dfdfdf;">
		<table id="dataTable_dhfx_main"></table>
	</div>

	<div class="panel" style="box-shadow: none;">
		<div class="panel-body">
			<form role="form" class="form-search" id="custPaypreDataForm" action="" method="post"
				target="targetFrame">
				<input type="hidden" id="custCode" name="custCode" value="${customer.code}" />
				<table class="table table-bordered col-sm-offset-2" style="width: 650px" width="" height="">
					<thead>
						<tr>
							<th style="width: 50px">期数</th>
							<th style="width: 200px">付款计划说明</span>）</th>
							<th style="width: 100px">付款计划</th>
							<th style="width: 80px"><span id="desinFeeName"></span></th>
							<th style="width: 80px">预交费</th>
							<th style="width: 80px">合计</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>首期数</td>
							<td><input type="text" id="payRemark1" name="payRemark1" 
								style="border: none" readonly="readonly" value="${payRemark.PayRemark1}">
							</td>
							<td><input type="text" id="basePay1" name="basePay1" value="${custPayPre.basePay }"
								 onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="border: none" onchange="doChange()" >
							</td>
							<td><input type="text" id="designFee1" name="designFee1" value="${custPayPre.designFee }"
								style="border: none" readonly="readonly" >
							</td>
							<td><input type="text" id="prePay1" name="prePay1" value="${custPayPre.prePay }"
								style="border: none" readonly="readonly">
							</td>
							<td><input type="text" id="sumAllPay1" value="${custPayPre.sumAllPay }" style="border: none"
								readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>二期数</td>
							<td><input type="text" id="payRemark2" name="payRemark2" 
								style="border: none" readonly="readonly" value="${payRemark.PayRemark2 }">
							</td>
							<td><input type="text" id="basePay2" name="basePay2" value="${custPayPre2.basePay }"
								onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="border: none" onchange="doChange()">
							</td>
							<td><input type="text" id="designFee2" name="designFee2" value="${custPayPre2.designFee }"
								style="border: none" readonly="readonly">
							</td>
							<td><input type="text" id="prePay2" name="prePay2" value="${custPayPre2.prePay }"
								style="border: none" readonly="readonly">
							</td>
							<td><input type="text" id="sumAllPay2" value="${custPayPre2.sumAllPay }" style="border: none"
								readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>三期数</td>
							<td><input type="text" id="payRemark3" name="payRemark3" 
								style="border: none" readonly="readonly" value="${payRemark.PayRemark3 }">
							</td>
							<td><input type="text" id="basePay3" name="basePay3" value="${custPayPre3.basePay }"
								onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="border: none" onchange="doChange()" >
							</td>
							<td><input type="text" id="designFee3" name="designFee3" value="${custPayPre3.designFee }"
								style="border: none" readonly="readonly">
							</td>
							<td><input type="text" id="prePay3" name="prePay3" value="${custPayPre3.prePay }"
								style="border: none" readonly="readonly">
							</td>
							<td><input type="text" id="sumAllPay3" value="${custPayPre3.sumAllPay }" style="border: none"
								readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>四期数</td>
							<td><input type="text" id="payRemark4" name="payRemark4"  value="${payRemark.PayRemark4 }"
								style="border: none" readonly="readonly">
							</td>
							<td><input type="text" id="basePay4" name="basePay4" value="${custPayPre4.basePay}"
								onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="border: none" onchange="doChange()" >
							</td>
							<td><input type="text" id="designFee4" name="designFee4" value="${custPayPre4.designFee }"
								style="border: none" readonly="readonly">
							</td>
							<td><input type="text" id="prePay4" name="prePay4" value="${custPayPre4.prePay }"
								style="border: none" readonly="readonly">
							</td>
							<td><input type="text" id="sumAllPay4" value="${custPayPre4.sumAllPay }" style="border: none"
								readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>汇总</td>
							<td><input type="text" style="border: none" readonly="readonly">
							<td><input type="text" id="basePaySum" name="basePaySum" 
								style="border: none" readonly="readonly"
								value="${custPayPre.basePay+custPayPre2.basePay+custPayPre3.basePay+custPayPre4.basePay }">
							</td>
							<td><input type="text" style="border: none" readonly="readonly">
							</td>
							<td><input type="text" style="border: none" readonly="readonly">
							</td>
							<td><input type="text" id="sumAllPay"
								value="${custPayPre.sumAllPay+custPayPre2.sumAllPay+custPayPre3.sumAllPay+custPayPre4.sumAllPay }"
								style="border: none" readonly="readonly">
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<div class="row">
				<div class="col-sm-12  ">
					<li style="text-align: center;"><label> <span id="isEqual" style="color: red;display: none;">基础和材料付款总和不等于基础和材料预算！</span>
					</label></li>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12  ">
					<li style="text-align: center;"><label></label>
						<button id="saveBut" type="button" class="btn btn-system btn-xs" onclick="autoProduce();"
							${customer.status=='4'||customer.status=='5'||!isProfitEdit||contractStatus=='2'||contractStatus=='3'||contractStatus=='4' ?'disabled':'' }>自动生成</button>
						<button style="margin-left: 10px" id="saveBut" type="button" class="btn btn-system btn-xs"
							onclick="doCustPaypreSave();"   ${customer.status=='4'||customer.status=='5'||!isProfitEdit||contractStatus=='2'||contractStatus=='3'||contractStatus=='4'?'disabled':'' } >保 存</button></li>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
