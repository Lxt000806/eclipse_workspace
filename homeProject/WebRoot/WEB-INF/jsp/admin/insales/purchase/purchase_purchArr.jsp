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
	<title>采购跟踪——到货</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>


</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
			<!-- panelBar -->
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
			<div class="infoBox" id="infoBoxDiv"></div>
  			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
								<ul class="ul-form">
								<li>
								<label><span class="required">*</span> 采购订单号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }" readonly="readonly"/>
								</li>
								<li>
								<label>到货状态</label>
									<house:xtdm  id="arriveStatus" dictCode="TPURARVSTATUS"   style="width:166px;" value="${purchase.arriveStatus}"></house:xtdm>
							</li>
								<li>
								<label class="control-textarea">到货说明</label>
									<textarea id="arriveRemark" name="arriveRemark" rows="3" value="">${purchase.arriveRemark }</textarea>
							</li>
							</ul>
					<table cellspacing="0" cellpadding="0" width="100%" hidden="true">
						<col width="72"/>
						<col width="150"/>
						<col width="72"/>
						<col width="150"/>
						<tbody>
								
								<td class="td-label">采购类型</td>
								<td class="td-value">
									<house:xtdm id="type" dictCode="PURCHTYPE"  value="${purchase.type }" disabled="true"></house:xtdm>
								</td>
							</tr>
							<tr>
								<td class="td-label">单据状态</td>
								<td class="td-value">
									<house:xtdmMulit id="status" dictCode="PURCHSTATUS" selectedValue="${purchase.status}"></house:xtdmMulit>                     
								</td>
								<td class="td-label">下单员</td>
								<td class="td-value">
									<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }" /> 
								</td>
							</tr>
							<tr>
								<td class="td-label">采购日期</td>
								<td class="td-value">
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" disabled="disable"/>
								</td>
								<td class="td-label">到货日期</td>
								<td>
									<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchase.arriveDate}' pattern='yyyy-MM-dd'/>" disabled="disable"/>
								</td>
							</tr>
							<tr>
								<td class="td-label">材料类型1</td>
								<td class="td-value">
									<select id="itemType1" name="itemType1" style="width: 166px;" disabled="disabled" value="${purchase.itemType1 }"></select>
									</select>
								</td>
								<td class="td-label">材料总价</td>
								<td class="td-value">
									<input type="text" id="amount" name="amount"  style="width:160px;" 
											onblur="changeAmount()"  value="${purchase.amount }" readonly="readonly" />
								</td>
								
							</tr>
							<tr>
								<td class="td-label">客户编号</td>
								<td class="td-value" > 
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${purchase.custCode }"/>
								</td>
								<td class="td-label">其他费用</td>
								<td class="td-value">
									<input type="text" id="otherCost" name="otherCost"  style="width:160px;" value="${purchase.otherCost } "readonly= 'readonl' />
								</td>
								
							</tr>
							<tr>
								<td class="td-label">供应商编号</td>
								<td class="td-value"> 
									<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier }"/>
								</td>
								<td class="td-label">其他费用调整</td>
								<td class="td-value">
									<input type="text" id="otherCostAdj" name="otherCostAdj"  style="width:160px;" value="${purchase.otherCostAdj } " readonly= 'readonl' />
								</td>
							</tr>
							<tr>
								<td class="td-label">仓库编号</td>
								<td class="td-value" > 
									<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }"/>
								</td>
								<td class="td-label">配送地点</td>
								<td class="td-value">
									<house:xtdm id="delivType" dictCode="PURCHDELIVTYPE"  value="${ purchase.delivType}" disabled='true'></house:xtdm>                     

								</td>
							</tr>
							<tr>
								<td class="td-label">是否结账</td>
								<td class="td-value">
									<house:xtdm id="isCheckOut" dictCode="YESNO"  value="${purchase.isCheckOut}" disabled='true'></house:xtdm>                     
								</td>
								
								<td class="td-label">已付定金</td>
								<td class="td-value">
									<input type="text" id="firstAmount" name="firstAmount"  style="width:160px;" value="${purchase.firstAmount }" readonly='readonly'/>
								</td>
							</tr>
							<tr>
								<td class="td-label">结账单号</td>
								<td class="td-value" > 
									<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;"  value="${purchase.checkOutNo }" readonly='readonly'/>
								</td>
								<td class="td-label">已付到货款</td>
								<td class="td-value" > 
									<input type="text" id="secondAmount" name="secondAmount" style="width:160px;" value="${purchase.secondAmount }" readonly='readonly'/>
								</td>
							</tr>
							<tr>
								<td class="td-label">销售单号</td>
								<td class="td-value" > 
									<input type="text" id="sino" name="sino" style="width:160px;" value="${purchase.sino }"/>
								</td>
								<td class="td-label"> 应收余款</td>
								<td class="td-value">
									<input type="text" id="remainAmount" name="remainAmount"  style="width:160px;"
									 		onblur="changeAmount()" value="${purchase.remainAmount }"  readonly='readonly'/>
								</td>
							</tr>
							<tr>
								<td class="td-label"> 原采购单号</td>
								<td class="td-value">
									<input type="text" id="oldPUNo" name="oldPUNo"  style="width:160px;"
									 		 value="${purchase.oldPUNo }"  readonly='readonly'/>
								</td>
								<td class="td-label">备注</td>
								<td class="td-value" colspan="1">
									<textarea id="remarks" name="remarks" rows="3">${purchase.remarks }</textarea>
								</td>
							</tr>
							
				</form>
				</div>
			</div>
	</div>
</div>
<script type="text/javascript">
$("#tabs").tabs();
$(function(){	
	//保存
	$("#saveBtn").on("click",function(){
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/purchase/doGZArrive',
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






