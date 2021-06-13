<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>集成进度信息</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
//根据材料查询发货天数
function findSendDay(material,itemType12,appDate,materialType,planDate){
	$.ajax({
		url:'${ctx}/admin/custIntProg/findSenddaysByMaterial',
		type: 'post',
		data: {'material':$("#"+material).val(),'itemType12':itemType12},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	$("#"+material+"Day").val(obj);
	    	changeAdd(appDate,materialType,planDate);
	    }
	 });
}
//修改计划发货时间
function changeAdd(appDate,materialType,planDate){
	var appDate=$("#"+appDate).val();
	var sendDay=$("#"+materialType).val();
	var planSendDate=getNewDate(appDate,sendDay);
    planSendDate=planSendDate=='NaN-NaN-NaN'?' ':planSendDate;
    if(sendDay=="0"){
    	$("#"+planDate).val("");
    }else{
  		$("#"+planDate).val(planSendDate);
    }
}

//日期加上天数得到新的日期  
//dateTemp 需要参加计算的日期，days要添加的天数，返回新的日期，日期格式：YYYY-MM-DD  
function getNewDate(dateTemp, days) {  
    var dateTemp = dateTemp.split("-");  
    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
    var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);  
    var rDate = new Date(millSeconds);  
    var year = rDate.getFullYear();  
    var month = rDate.getMonth() + 1;  
    if (month < 10) month = "0" + month;  
    var date = rDate.getDate();  
    if (date < 10) date = "0" + date;  
    return (year + "-" + month + "-" + date);  
}
//日期相减参数计算
function changeSub(dt1,dt2,para1,para2){
	var date1=$("#"+dt1).val();
	var date2=$("#"+dt2).val();
	var days=dateMinus(date2,date1);
	$("#"+para1).val(days);
	if((dt2=='measureCupAppDate' || dt2=='measureAppDate'|| dt2=='intSendDate'|| dt2=='cupSendDate' )&&date2!=''){
		$("#"+para2).val(days-10);
	}
}
//日期相减算法
function dateMinus(date2,date1){ 
   var date22 = new Date(date2.replace(/-/g, "/"));
   var date11 = new Date(date1.replace(/-/g, "/"));
   var days = date11.getTime() - date22.getTime(); 
   var day = parseInt(days / (1000 * 60 * 60 * 24)); 
   if(isNaN(day)){
  	 return "";
  }
  return day; 
}

$(function() {
	  	$("#tableSpl").openComponent_supplier({showValue:'${custIntProg.tablespl }',showLabel:'${custIntProg.tablespldescr }'});
      	$("#cupSpl").openComponent_supplier({showValue:'${custIntProg.cupspl }',showLabel:'${custIntProg.cupspldescr }'}); 
      	$("#intSpl").openComponent_supplier({showValue:'${custIntProg.intspl }',showLabel:'${custIntProg.intspldescr }'});
      	$("#doorSpl").openComponent_supplier({showValue:'${custIntProg.doorspl }',showLabel:'${custIntProg.doorspldescr }'}); 
  		findSendDay('cupMaterial','30','cupAppDate','cupMaterialDay','cupPlanSendDate');
  		findSendDay('intMaterial','31','intAppDate','intMaterialDay','intPlanSendDate');
  		changeAdd('intAppDate','intMaterialDay','intPlanSendDate');//初始化集成计划发货日期
 		changeAdd('cupAppDate','cupMaterialDay','cupPlanSendDate');//初始化橱柜计划发货日期 
 		changeSub('intSendDate','intPlanSendDate','intSendDelayDays');//初始化集成出货拖期
		changeSub('cupSendDate','cupPlanSendDate','cupSendDelayDays');//初始化橱柜出货拖期
		changeSub('cupDeliverDate','cupInstallDate','cupInstallDays');//初始化橱柜安装工期
		changeSub('intDeliverDate','intInstallDate','intInstallDays');//初始化集成安装工期  
}); 

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" style="width:160px;"
								value="${custIntProg.address}" readonly="true" />
							</li>
							<li><label>面积</label> <input type="text" id="area"
								name="area" style="width:160px;" value="${custIntProg.area}"
								readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">平方</span>
							</li>
							<li><label>增减总额</label> <input type="text" id="addreduceAll"
								name="addreduceAll" style="width:160px;"
								value="${custIntProg.addreduceall}" readonly /> <input
								type="hidden" id="custCode" name="custCode" style="width:160px;"
								value="${custIntProg.custcode}" readonly />
							</li>

						</div>
						<div class="validate-group row">
							</li>
							<li><label>户型</label> <input type="text" id="layoutdescr"
								name="layoutdescr" style="width:160px;"
								value="${custIntProg.layoutdescr}" readonly />
							</li>
							<li><label>橱柜制单用时A</label> <input type="text"
								id="cupAppDays" name="cupAppDays" style="width:160px;"
								value="${custIntProg.cupappdays}" readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li><label><font color="blue">其他板材</font></label> <input type="text" id="otherArea"
								name="otherArea" style="width:160px;"
								value="${custIntProg.otherarea}" /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">平方</span>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>集成当前进度</label> <input type="text"
								id="nowIntProgDescr" name="nowIntProgDescr" style="width:160px;"
								value="${custIntProg.nowintprogdescr}" readonly />
							</li>
							<li><label>集成制单用时A</label> <input type="text"
								id="intAppDays" name="intAppDays" style="width:160px;"
								value="${custIntProg.intappdays}" readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li><label><font color="blue">实木生态板</font></label> <input type="text" id="ecoArea"
								name="ecoArea" style="width:160px;"
								value="${custIntProg.ecoarea}" /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">平方</span>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>橱柜当前进度</label> <input type="text"
								id="nowCupProgDescr" name="nowCupProgDescr" style="width:160px;"
								value="${custIntProg.nowcupprogdescr}" readonly />
							</li>
							<li><label>橱柜制单拖期</label> <input type="text"
								id="cupAppDelayDdays" name="cupAppDelayDdays"
								style="width:160px;" value="${custIntProg.cupappdelayddays}"
								readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li><label><font color="blue">实木颗粒板</font></label> <input type="text" id="mdfArea"
								name="mdfArea" style="width:160px;"
								value="${custIntProg.mdfarea}" /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">平方</span>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>集成预算金额</label> <input type="text" id="intYs"
								name="intYs" style="width:160px;" value="${custIntProg.intys}"
								readonly />
							</li>
							<li><label>集成制单拖期</label> <input type="text"
								id="appDelayDdays" name="appDelayDdays" style="width:160px;"
								value="${custIntProg.appdelayddays}" readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li class="form-validate"><label><font color="blue">橱柜下单日期</font></label> <input
								type="text" id="cupAppDate" name="cupAppDate" class="i-date"
								style="width:160px;"
								onchange="changeSub('cupAppDate','measureCupAppDate','cupAppDays','cupAppDelayDdays');
								changeSub('cupSendDate','cupAppDate','cupProduceDays');
								changeAdd('cupAppDate','cupMaterialDay','cupPlanSendDate')"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.cupappdate}" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>橱柜预算金额</label> <input type="text" id="cupYs"
								name="cupYs" style="width:160px;" value="${custIntProg.cupys}"
								readonly />
							</li>
							<li><label>橱柜生产用时B</label> <input type="text"
								id="cupProduceDays" name="cupProduceDays" style="width:160px;"
								value="${custIntProg.cupproducedays}" readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li><label><font color="blue">橱柜供应商</font></label> <input type="text" id="cupSpl"
								name="cupSpl" style="width:160px;"
								value="${custIntProg.cupspl }" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>预算总额</label> <input type="text" id="allPlan"
								name="allPlan" style="width:160px;"
								value="${custIntProg.allplan}" readonly />
							</li>
							<li><label>集成生产用时B</label> <input type="text"
								id="intProduceDays" name="intProduceDays" style="width:160px;"
								value="${custIntProg.intproducedays}" readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li class="form-validate"><label><font color="blue">集成下单日期</font></label> <input
								type="text" id="intAppDate" name="intAppDate" class="i-date"
								style="width:160px;"
								onchange="changeSub('intAppDate','measureAppDate','intAppDays','appDelayDdays');
								changeSub('intSendDate','intAppDate','intProduceDays');
								changeAdd('intAppDate','intMaterialDay','intPlanSendDate')"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.intappdate}" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>集成下单成本</label> <input type="text" id="intXd"
								name="intXd" style="width:160px;"  readonly
								value="${custIntProg.costRight=='1'?custIntProg.intxd:'***'}"
								/>
							</li>
							<li><label>集成出货拖期</label> <input type="text"
								id="intSendDelayDays" name="intSendDelayDays"
								style="width:160px;" value="${custIntProg.intsenddelaydays}"
								readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li><label><font color="blue">集成供应商</font></label> <input type="text" id="intSpl"
								name="intSpl" style="width:160px;"
								value="${custIntProg.intspl }" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>橱柜下单成本</label> <input type="text" id="cupXd"
								name="cupXd" style="width:160px;" readonly
								value="${custIntProg.costRight=='1'?custIntProg.cupxd:'***'}"/>
							</li>
							<li><label>橱柜出货拖期</label> <input type="text"
								id="cupSendDelayDays" name="cupSendDelayDays"
								style="width:160px;" value="${custIntProg.cupsenddelaydays}"
								readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li class="form-validate"><label><font color="blue">推拉门下单日期</font></label> <input
								type="text" id="doorAppDate" name="doorAppDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.doorappdate}" />
							</li>
						</div>

						<div class="validate-group row">
							<li><label>下单总成本</label> <input type="text" id="orderAmount"
								name="orderAmount" style="width:160px;"
								value="${custIntProg.costRight=='1'?custIntProg.orderamount:'***'}" readonly />
							</li>
							<li><label>橱柜交付用时C</label> <input type="text"
								id="intDeliverDays" name="intDeliverDays" style="width:160px;"
								value="${custIntProg.intdeliverdays}" readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li><label><font color="blue">推拉门供应商</font></label> <input type="text" id="doorSpl"
								name="doorSpl" style="width:160px;"
								value="${custIntProg.doorspl }" />
						</div>
						<div class="validate-group row">
							<li><label>集成增减</label> <input type="text" id="intZj"
								name="intZj" style="width:160px;" value="${custIntProg.intzj}"
								readonly />
							</li>
							<li><label>集成交付用时C</label> <input type="text"
								id="cupDeliverDays" name="cupDeliverDays" style="width:160px;"
								value="${custIntProg.cupdeliverdays}" readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li class="form-validate"><label><font color="blue">台面下单日期</font></label> <input
								type="text" id="tableAppDate" name="tableAppDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.tableappdate}" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>橱柜增减</label> <input type="text" id="cupZj"
								name="cupZj" style="width:160px;" value="${custIntProg.cupzj}"
								readonly />
							</li>
							<li><label>橱柜安装拖期</label> <input type="text"
								id="cupInstallDelayDays" name="cupInstallDelayDays"
								style="width:160px;" value="${custIntProg.cupinstalldelaydays}"
								readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li><label><font color="blue">台面供应商</font></label> <input type="text" id="tableSpl"
								name="tableSpl" style="width:160px;"
								value="${custIntProg.tablespl }" />
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>集成测量申报日期</label> <input
								type="text" id="measureAppDate" name="measureAppDate"
								class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.measureappdate==''?'':custIntProg.measureappdate.substring(0,10)}"
								disabled="true" /></li>
							<li><label>集成安装拖期</label> <input type="text"
								id="intInstallDelayDays" name="intInstallDelayDays"
								style="width:160px;" value="${custIntProg.intinstalldelaydays}"
								readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li class="form-validate"><label><font color="blue">橱柜材质</font></label> <house:dict
									id="cupMaterial" dictCode=""
									sql="select code, code+' '+descr fd from tIntMaterial where  Expired='F' "
									sqlValueKey="code" sqlLableKey="fd"
									value="${custIntProg.cupmaterial}"
									onchange="findSendDay('cupMaterial','30','cupAppDate','cupMaterialDay','cupPlanSendDate');
									changeAdd('cupAppDate','cupMaterialDay','cupPlanSendDate')
									">
								</house:dict>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>集成测量申报天数</label> <input
								type="text" id="intMeasureDays" name="intMeasureDays"
								style="width:160px;" readonly
								value="${custIntProg.intmeasuredays }" /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li><label>橱柜安装工期</label> <input type="text"
								id="cupInstallDays" name="cupInstallDays" style="width:160px;"
								readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li class="form-validate"><label><font color="blue">集成材质</font></label> <house:dict
									id="intMaterial" dictCode=""
									sql="select code, code+' '+descr fd from tIntMaterial where  Expired='F' "
									sqlValueKey="code" sqlLableKey="fd"
									value="${custIntProg.intmaterial}"
									onchange="findSendDay('intMaterial','31','intAppDate','intMaterialDay','intPlanSendDate');
									changeAdd('intAppDate','intMaterialDay','intPlanSendDate')
									">
								</house:dict> <input type="hidden" id="intMaterialDay" name="intMaterialDay"
								style="width:160px;" /> <input type="hidden"
								id="cupMaterialDay" name="cupMaterialDay" style="width:160px;" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>集成测量达标日期</label> <input
								type="text" id="dealDate" name="dealDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.dealdate==''?'':custIntProg.dealdate.substring(0,10)}"
								disabled="true" /></li>
							<li><label>集成安装工期</label> <input type="text"
								id="intInstallDays" name="intInstallDays" style="width:160px;"
								readonly /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li class="form-validate"><label><font color="blue">橱柜实际出货日期</font></label> <input
								type="text" id="cupSendDate" name="cupSendDate" class="i-date"
								style="width:160px;"
								onchange="changeSub('cupSendDate','cupAppDate','cupProduceDays');
								changeSub('cupSendDate','cupPlanSendDate','cupSendDelayDays');
								changeSub('cupDeliverDate','cupSendDate','cupDeliverDays','cupInstallDelayDays')"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.cupsenddate}" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>集成测量达标天数</label> <input type="text"
								id="intMeasureStandardDays" name="intMeasureStandardDays"
								style="width:160px;" readonly
								value="${custIntProg.intmeasurestandarddays}" /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							 <li class="form-validate"><label>实际开工日期</label> <input
								type="text" id="confirmBegin" name="confirmBegin" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value='${custIntProg.confirmbegin}'
								disabled="true" /></li>
							<li class="form-validate"><label><font color="blue">集成实际出货日期</font></label> <input
								type="text" id="intSendDate" name="intSendDate" class="i-date"
								style="width:160px;"
								onchange="changeSub('intSendDate','intAppDate','intProduceDays');
								changeSub('intSendDate','intPlanSendDate','intSendDelayDays');
								changeSub('intDeliverDate','intSendDate','intDeliverDays','intInstallDelayDays')"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.intsenddate}" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>橱柜测量申报日期</label> <input
								type="text" id="measureCupAppDate" name="measureCupAppDate"
								class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.measurecupappdate==''?'':custIntProg.measurecupappdate.substring(0,10)}"
								disabled="true" /></li>
							<li class="form-validate"><label>橱柜计划出货日期</label> <input
								type="text" id="cupPlanSendDate" name="cupPlanSendDate"
								class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.cupplansenddate}"
								disabled="true" /></li>
							<li class="form-validate"><label><font color="blue">橱柜安装日期</font></label> <input
								type="text" id="cupInstallDate" name="cupInstallDate"
								class="i-date" style="width:160px;" onchange="changeSub('cupDeliverDate','cupInstallDate','cupInstallDays')"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.cupinstalldate}" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>橱柜测量申报天数</label> <input
								type="text" id="cupMeasureDays" name="cupMeasureDays"
								style="width:160px;" readonly
								value="${custIntProg.cupmeasuredays}" /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li class="form-validate"><label>集成计划出货日期</label> <input
								type="text" id="intPlanSendDate" name="intPlanSendDate"
								class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.intplansenddate}'  pattern='yyyy-MM-dd'/>"
								disabled="true" /></li>
							<li class="form-validate"><label><font color="blue">集成安装日期</font></label> <input
								type="text" id="intInstallDate" name="intInstallDate"
								class="i-date" style="width:160px;" onchange="changeSub('intDeliverDate','intInstallDate','intInstallDays')"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.intinstalldate}" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>橱柜测量达标日期</label> <input
								type="text" id="cupDealDate" name="cupDealDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.cupdealdate==''?'':custIntProg.cupdealdate.substring(0,10)}"
								disabled="true" /></li>
							<li class="form-validate"><label>橱柜质检日期</label> <input
								type="text" id="cupCheckDate" name="cupCheckDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.cupcheckdate==''?'':custIntProg.cupcheckdate.substring(0,10)}"
								disabled="true" /></li>
							<li class="form-validate"><label><font color="blue">橱柜交付日期</font></label> <input
								type="text" id="cupDeliverDate" name="cupDeliverDate"
								class="i-date" style="width:160px;"
								onchange="changeSub('cupDeliverDate','cupSendDate','cupDeliverDays','cupInstallDelayDays');
								changeSub('cupDeliverDate','cupInstallDate','cupInstallDays')"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.cupdeliverdate}" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>橱柜测量达标天数</label> <input type="text"
								id="cupMeasureStandardDays" name="cupMeasureStandardDays"
								style="width:160px;" readonly
								value="${custIntProg.cupmeasurestandarddays}" /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
							</li>
							<li class="form-validate"><label>集成质检日期</label> <input
								type="text" id="intCheckDate" name="intCheckDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.cupcheckdate==''?'':custIntProg.cupcheckdate.substring(0,10)}"
								disabled="true" /></li>
							<li class="form-validate"><label><font color="blue">集成交付日期</font></label> <input
								type="text" id="intDeliverDate" name="intDeliverDate"
								class="i-date" style="width:160px;"
								onchange="changeSub('intDeliverDate','intSendDate','intDeliverDays','intInstallDelayDays');
								changeSub('intDeliverDate','intInstallDate','intInstallDays')"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.intdeliverdate}" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label><font color="blue">台面出货日期</font></label> <input
								type="text" id="tableSendDate" name="tableSendDate"
								class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="${custIntProg.tablesenddate}" />
							</li>
							<li class="form-validate"><label><font color="blue">衣柜设计完成日期</label> 
								<input type="text" id="intDesignDate" name="intDesignDate" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="${custIntProg.intdesigndate}" />
							</li>
							<li class="form-validate"><label><font color="blue">橱柜设计完成日期</label> 
								<input type="text" id="cupDesignDate" name="cupDesignDate" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="${custIntProg.cupdesigndate}" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label class="control-textarea"><font color="blue">备注信息</font></label>
								<textarea id="remarks" name="remarks">${custIntProg.remarks }</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
