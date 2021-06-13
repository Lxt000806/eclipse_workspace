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
	<title>工程信息管理新增</title>
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
			$("#page_form").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					constructStatus:{  
						validators: {  
							notEmpty: {  
								message: '施工状态不能为空'  
							}
						}  
					},
				},
				submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
}); 
function changeIntDlyDay(){
	var  intMsrDate=new Date($("#intMsrDate").val());
	var  confirmBegin=new Date($("#confirmBegin").val());
	var dateSpan,
        tempDate,
        iDays;
	if(confirmBegin){
		if($("#intMsrDate").val()!=''&&$("#confirmBegin").val()!=''){
	        sDate1 = Date.parse(confirmBegin);
	        sDate2 = Date.parse(intMsrDate);
	        dateSpan = sDate2 - sDate1;
	        iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
			$("#intDlyDay").val(iDays-35);
		}else{
			$("#intDlyDay").val(0);
		}
	}
}
function chengeDelayDays(){ 
	//var  checkOutDate=new Date($("#checkOutDate").val());
	var  confirmBegin=new Date($("#confirmBegin").val());
	var  constructDay=$.trim($("#constructDay").val());
	var  getConstDay=$.trim($("#getConstDay").val());
	if(constructDay==''){
		constructDay=0;
	}
	var dateSpan,
        tempDate,
        iDays;
    if($.trim($("#confirmBegin").val())!=''){
		/* if($("#checkOutDate").val()!=''&&$("#confirmBegin").val()!=''){
			sDate1 = Date.parse(confirmBegin);
	        sDate2 = Date.parse(checkOutDate);
			dateSpan = sDate2 - sDate1;
			iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
			$("#delayDay").val(iDays-constructDay-getConstDay);
		}else{
			sDate1 = Date.parse(confirmBegin);
	        sDate2 = Date.parse(new Date());
			dateSpan = sDate2 - sDate1;
			iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
			$("#delayDay").val(iDays-constructDay-getConstDay);
		} */
		sDate1 = Date.parse(confirmBegin);
        sDate2 = Date.parse(new Date());
		dateSpan = sDate2 - sDate1;
		iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
		$("#delayDay").val(iDays-constructDay-getConstDay);
    } else{
    	$("#delayDay").val(0);
    }  
}

$(function(){
	
	if('${updateConfirmDate}'=='false'){
		$("#beginComDate").attr("disabled","true");
		
	}
	/* if('${updateCustCheckDate}'=='false'){
		$("#checkOutDate").attr("disabled","true");
	} */

	$("#projectMan").openComponent_employee({readonly:true,showValue:'${customer.projectMan}',showLabel:'${customer.projectManDescr}',condition:{prjManCustCount:'1'}});
	if('${customer.status}'=='5'){
		$("#checkMan").openComponent_employee({showValue:'${customer.checkMan}',showLabel:'${customer.checkManDescr}',readonly:true});
	}else{
		$("#checkMan").openComponent_employee({showValue:'${customer.checkMan}',showLabel:'${customer.checkManDescr}'});
	}
	
	$("#preloftsMan").openComponent_employee({showValue:'${customer.preloftsMan}',showLabel:'${customer.preloftsManDescr}'});
	$("#designMan").openComponent_employee({showValue:'${customer.designMan}',showLabel:'${customer.designManDescr}',readonly:true});

	$("#saveBtn").on("click",function(){
		if($("#intDlyDay").val()==null){
			art.dialog({
				content:'集成拖期时间为NaN，不能保存'
			});
			return false;
		}
		$("#page_form").bootstrapValidator('validate');
		if(!$("#page_form").data('bootstrapValidator').isValid()) return;
		var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/gcxxgl/doSave',
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
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


});

</script>
</head>
	<body>
		<div class="body-box-list">
				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
					</div>
			<div class="panel panel-info" >  
        	 <div class="panel-body">		
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
			  		<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden"  style="width:160px;" value="${updateCustCheckDate }" readonly="true"/>
					<input type="hidden" style="width:160px;" value="${updateConfirmDate}" readonly="true"/>
					<input type="hidden"  id="getConstDay" style="width:160px;" value="${getConstDay}" readonly="true"/>
							<ul class="ul-form">
							<li>
								<label>客户名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${customer.descr }" readonly="true"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" readonly="true" />
							</li>
							<li>
								<label>电话</label>
								<input type="text" id="mobile1" name="mobile1" style="width:160px;" value="${customer.mobile1 }" readonly="true" />
							</li>
							<li>
								<label>设计师</label>
								<input type="text" id="designMan" name="designMan" style="width:160px;" value="${customer.designMan }" />
							</li>
							<li>
								<label>设计费</label>
								<input type="text" id="designFee" name="designFee" style="width:160px;" value="${customer.designFee }" readonly="true" />
							</li>
							<li>
								<label>管理费预算</label>
								<input type="text" id="manageFee" name="manageFee" style="width:160px;" value="${customer.manageFee }" readonly="true" />
							</li>
							<li>
								<label>直接费用预算</label>
								<input type="text" id="baseFeeDirct" name="baseFeeDirct" style="width:160px;" value="${customer.baseFeeDirct }" readonly="true"/>
							</li>
							<li>
								<label>基础合同额</label>
								<input type="text" id="baseCost" name="baseCost" style="width:160px;" value="${customer.baseCost }" readonly="true" />
							</li>
							<li>
								<label>主材合同额</label>
								<input type="text" id="mainCost" name="mainCost" style="width:160px;" value="${customer.mainCost }" readonly="true" />
							</li>
							<li>
								<label>软装合同额</label>
								<input type="text" id="softCost" name="softCost" style="width:160px;" value="${customer.softCost }" readonly="true"/>
							</li>
							<li>
								<label>集成合同额</label>
								<input type="text" id="inteCost" name="inteCost" style="width:160px;" value="${customer.inteCost }" readonly="true" />
							</li>
							<li>
								<label>服务性费用</label>
								<input type="text" id="mainServFee" name="mainServFee" style="width:160px;" value="${customer.mainServFee }" readonly="true" />
							</li>
							<li>
								<label>工程总造价</label>
								<input type="text" id="contractFee" name="contractFee" style="width:160px;" value="${customer.contractFee }" readonly="true"/>
							</li>
							<li>
								<label>施工方式</label>
								<house:xtdm id="constructType" dictCode="CONSTRUCTTYPE"  style="width:160px;" value="${customer.constructType }" disabled="true"></house:xtdm>
							</li>
							<li>
								<label>户型</label>
								<house:xtdm id="layout" dictCode="LAYOUT"  style="width:160px;" value="${customer.layout }" disabled="true"></house:xtdm>
							</li>
							<li>
								<label>面积</label>
								<input type="text" id="area" name="area" style="width:160px;" value="${customer.area }" readonly="true"/>
							</li>
							<li>
								<label>计划进度</label>
								<input type="text" id="planPrjDescr" name="planPrjDescr" style="width:160px;" value="${customer.planPrjDescr}" readonly="true" />
							</li>
							<li>
								<label>当前实际进度</label>
								<input type="text" id="nowPrjDescr" name="nowPrjDescr" style="width:160px;" value="${customer.nowPrjDescr}" readonly="true" />
							</li>
							<li>
								<label>已付款</label>
								<input type="text" id="havePay" name="havePay" style="width:160px;" value="${customer.havePay }" readonly="true"/>
							</li>
							<li>
								<label>首付款</label>
								<input type="text" id="firstPay" name="firstPay" style="width:160px;" value="${customer.firstPay }" readonly="true" />
							</li>
							<li>
								<label>二批付款</label>
								<input type="text" id="secondPay" name="secondPay" style="width:160px;" value="${customer.secondPay }" readonly="true" />
							</li>
							<li>
								<label>三批付款</label>
								<input type="text" id="thirdPay" name="thirdPay" style="width:160px;" value="${customer.thirdPay }" readonly="true"/>
							</li>
							<li>
								<label>尾款</label>
								<input type="text" id="fourPay" name="fourPay" style="width:160px;" value="${customer.fourPay }" readonly="true" />
							</li>
							<li>
								<label>增减项合计</label>
								<input type="text" id="zjxhj" name="zjxhj" style="width:160px;" value="${customer.zjxhj }" readonly="true" />
							</li>
							<li>
								<label>基础增减</label>
								<input type="text" id="jczj" name="jczj" style="width:160px;" value="${customer.jczj }" readonly="true" />
							</li>
							<li>
								<label>客户结算金额</label>
								<input type="text" id="custCountCost" name="custCountCost" style="width:160px;" value="${customer.custCountCost }" readonly="true" />
							</li>
							<li>
								<label>项目经理结算金额</label>
								<input type="text" id="prjManCost" name="prjManCost" style="width:160px;" value="${customer.prjManCost }" readonly="true" />
							</li>
							<li>
								<label>工程经理结算金额</label>
								<input type="text" id="consManCost" name="consManCost" style="width:160px;" value="${customer.consManCost }" readonly="true"/>
							</li>
							<li>
								<label>业务跟单人员</label>
								<input type="text" id="businessFlowDescr" name="businessFlowDescr" style="width:160px;" value="${customer.businessFlowDescr }" readonly="true" />
							</li>
							<li>
								<label>客户来源</label>
								<house:xtdm id="source" dictCode="CUSTOMERSOURCE"  style="width:160px;" value="${customer.source }" disabled="true"></house:xtdm>
							</li>
							<li>
								<label>设计图纸状态</label>
								<house:xtdm id="designPicStatus" dictCode="PICPRGSTS"  style="width:160px;" value="${designPicStatus }" disabled="true"></house:xtdm>
							</li>
						</ul>
				</form>	
			</div>
			</div>
					<div class="panel panel-info" >  
         <div class="panel-body">		
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			  				<house:token></house:token>
							<ul class="ul-form">
							<li hidden="true">
								<label>客户编号hidden</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }"   />
							</li>
							<li>
								<label>项目经理</label>
								<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${customer.projectMan }" />
							</li>
							<li>
								<label>工程部</label>
								<input type="text" id="department2" name="department2" style="width:160px;" value="${customer.department2 }" readonly="true" />
							</li>
							<li>
								<label>巡检员</label>
								<input type="text" id="checkMan" name="checkMan" style="width:160px;" value="${customer.checkMan }"   />
							</li>
							<li>
								<label>工程接收日期</label>
								<input type="text" style="width:160px;" id="consRcvDate" name="consRcvDate" class="i-date" value="<fmt:formatDate value='${customer.consRcvDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>派单时间</label>
								<input type="text" style="width:160px;" id="sendJobDate" name="sendJobDate" class="i-date" value="<fmt:formatDate value='${customer.sendJobDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>开工令时间</label>
								<input type="text" style="width:160px;" id="beginComDate" name="beginComDate" class="i-date" value="<fmt:formatDate value='${customer.beginComDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>合同开工时间</label>
								<input type="text" id="signDate" name="signDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${customer.signDate}' pattern='yyyy-MM-dd '/>" disabled="true" />
							</li>
							<li>
								<label>实际开工日期</label>
								<input type="text" style="width:160px;" id="confirmBegin" name="confirmBegin" onchange="chengeDelayDays()" onblur="changeIntDlyDay()" class="i-date" value="<fmt:formatDate value='${customer.confirmBegin}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>施工工期</label>  
								<input type="text" id="constructDay" name="constructDay" style="width:160px;" value="${customer.constructDay }" readonly="true" />
							</li>
							<li>
								<label for="isHoliConstruct">是否周末施工</label>
								<house:xtdm id="isHoliConstruct" dictCode="YESNO"  
									style="width:160px;" value="${customer.isHoliConstruct}" >
								</house:xtdm>
							</li>
							<li class="form-validate">
								<label>施工状态</label>
								<house:xtdm id="constructStatus" dictCode="CONSTRUCTSTATUS"  style="width:160px;" value="${customer.constructStatus }" ></house:xtdm>
							</li>
							<li>
								<label>竣工日期</label>
								<input type="text" style="width:160px;" id="consEndDate" name="consEndDate" class="i-date" value="<fmt:formatDate value='${customer.consEndDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>拖期天数</label>
								<input type="text" id="delayDay" name="delayDay" style="width:160px;" value="${customer.delayDay }"  readonly="true"/>
							</li>
							<li>
								<label>预算结算日期</label>
								<input type="text" style="width:160px;" id="planCheckOut" name="planCheckOut" class="i-date" value="<fmt:formatDate value='${customer.planCheckOut}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<%-- <li>
								<label>实际结算日期</label>
								<input type="text" style="width:160px;" id="checkOutDate" name="checkOutDate" onblur="chengeDelayDays()" class="i-date" value="<fmt:formatDate value='${customer.checkOutDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li> --%>
							<li>
								<label>是否客诉</label>
								<house:xtdm id="isComplain" dictCode="YESNO"  style="width:160px;" value="${customer.isComplain }" ></house:xtdm>
							</li>
							<li>
								<label>片区</label>
								<input type="text" id="consArea" name="consArea" style="width:160px;" value="${customer.consArea }" />
							</li>
							<li>
								<label>集成测量时间</label>
								<input type="text" style="width:160px;" id="intMsrDate" name="intMsrDate" onblur="changeIntDlyDay()" class="i-date" value="<fmt:formatDate value='${customer.intMsrDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>集成拖期时间</label>
								<input type="text" id="intDlyDay" name="intDlyDay" style="width:160px;" value="${customer.intDlyDay }" readonly="true" />
							</li>
							<li>
								<label>回执单</label>
								<house:xtdm id="haveCheck" dictCode="HAVENO"  style="width:160px;" value="${customer.haveCheck }" ></house:xtdm>
							</li>
							<li>
								<label>交房验收单</label>
								<house:xtdm id="haveReturn" dictCode="HAVENO"  style="width:160px;" value="${customer.haveReturn }" ></house:xtdm>
							</li>
							<li>
								<label>交房相片</label>
								<house:xtdm id="havePhoto" dictCode="HAVENO"  style="width:160px;" value="${customer.havePhoto }" ></house:xtdm>
							</li>
							<li>
								<label>预放样员</label>
								<input type="text" id="preloftsMan" name="preloftsMan" style="width:160px;" value="${customer.preloftsMan }" readonly="true" />
							</li>
							<li hidden="true">
								<label>状态</label>
								<input type="text" id="status" name="status" style="width:160px;" value="${customer.status }" readonly="true" />
							</li>
							<li hidden="true">
								<label>预放样员hidden</label>
								<input type="text" id="oldPreloftsMan" name="oldPreloftsMan" style="width:160px;" value="${customer.oldPreloftsMan }" readonly="true" />
 							</li>
							<li hidden="true">
								<label>预放样员hidden</label>
								<input type="text" id="preloftsManDescr" name="preloftsManDescr" style="width:160px;" value="${customer.preloftsManDescr }" readonly="true" />
							</li>
							<li>
								<label>主材是否备齐</label>
								<house:xtdm id="mainItemOk" dictCode="YESNO"  style="width:160px;" value="${customer.mainItemOk }"></house:xtdm>
							</li>
							<li>
								<label>关系客户</label>
								<house:xtdm id="relCust" dictCode="RELCUST"  style="width:160px;" value="${customer.relCust }"></house:xtdm>
							</li>
						</ul>	
				</form>
				</div>
				</div>
			</div>
	</body>	
</html>
