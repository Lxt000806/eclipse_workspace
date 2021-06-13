<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    	<title>ItemApp明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">

$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
});


</script>
</head>
<body>
<div class="body-box-form">
<div class="content-form">
	<!--panelBar-->
	<div class="panelBar">
		<ul>
			<li id="closeBut">
				<a href="javascript:void(0)" class="a2" onclick="closeWin(false)">
					<span>关闭</span>
				</a>
			</li>
			<li class="line"></li>
		</ul>
		<div class="clear_float"> </div>
	</div>
		<div class="edit-form">
			<form action="" method="post" id="dataForm" enctype="multipart/form-data">
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label"><span class="required">*</span>No</td>
							<td class="td-value">
${itemApp.no}							</td>
							<td class="td-label">类型</td>
							<td class="td-value">
${itemApp.type}							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>材料类型1</td>
							<td class="td-value">
${itemApp.itemType1}							</td>
							<td class="td-label"><span class="required">*</span>客户编号</td>
							<td class="td-value">
${itemApp.custCode}							</td>
						</tr>
						<tr>	
							<td class="td-label">Status</td>
							<td class="td-value">
${itemApp.status}							</td>
							<td class="td-label">申请人员</td>
							<td class="td-value">
${itemApp.appCzy}							</td>
						</tr>
						<tr>	
							<td class="td-label">申请日期</td>
							<td class="td-value">
${itemApp.date}							</td>
							<td class="td-label">confirmCzy</td>
							<td class="td-value">
${itemApp.confirmCzy}							</td>
						</tr>
						<tr>	
							<td class="td-label">confirmDate</td>
							<td class="td-value">
${itemApp.confirmDate}							</td>
							<td class="td-label">发货人员</td>
							<td class="td-value">
${itemApp.sendCzy}							</td>
						</tr>
						<tr>	
							<td class="td-label">发货日期</td>
							<td class="td-value">
${itemApp.sendDate}							</td>
							<td class="td-label">发货类型</td>
							<td class="td-value">
${itemApp.sendType}							</td>
						</tr>
						<tr>	
							<td class="td-label">供应商代码</td>
							<td class="td-value">
${itemApp.supplCode}							</td>
							<td class="td-label">采购单号</td>
							<td class="td-value">
${itemApp.puno}							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>仓库编号</td>
							<td class="td-value">
${itemApp.whcode}							</td>
							<td class="td-label">Remarks</td>
							<td class="td-value">
${itemApp.remarks}							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>LastUpdate</td>
							<td class="td-value">
${itemApp.lastUpdate}							</td>
							<td class="td-label">LastUpdatedBy</td>
							<td class="td-value">
${itemApp.lastUpdatedBy}							</td>
						</tr>
						<tr>	
							<td class="td-label">Expired</td>
							<td class="td-value">
${itemApp.expired}							</td>
							<td class="td-label">ActionLog</td>
							<td class="td-value">
${itemApp.actionLog}							</td>
						</tr>
						<tr>	
							<td class="td-label">配送方式</td>
							<td class="td-value">
${itemApp.delivType}							</td>
							<td class="td-label">项目经理</td>
							<td class="td-value">
${itemApp.projectMan}							</td>
						</tr>
						<tr>	
							<td class="td-label">电话号码</td>
							<td class="td-value">
${itemApp.phone}							</td>
							<td class="td-label">原批次号</td>
							<td class="td-value">
${itemApp.oldNo}							</td>
						</tr>
						<tr>	
							<td class="td-label">其它费用（付供应商）</td>
							<td class="td-value">
${itemApp.otherCost}							</td>
							<td class="td-label">其它费用调整（付供应商）</td>
							<td class="td-value">
${itemApp.otherCostAdj}							</td>
						</tr>
						<tr>	
							<td class="td-label">是否服务性产品</td>
							<td class="td-value">
${itemApp.isService}							</td>
							<td class="td-label">材料类型2</td>
							<td class="td-value">
${itemApp.itemType2}							</td>
						</tr>
						<tr>	
							<td class="td-label">是否记账</td>
							<td class="td-value">
${itemApp.isCheckOut}							</td>
							<td class="td-label">仓库发货记账单号</td>
							<td class="td-value">
${itemApp.whcheckOutNo}							</td>
						</tr>
						<tr>	
							<td class="td-label">结算顺序号</td>
							<td class="td-value">
${itemApp.checkSeq}							</td>
							<td class="td-label">项目经理结算类型</td>
							<td class="td-value">
${itemApp.prjCheckType}							</td>
						</tr>
						<tr>	
							<td class="td-label">项目经理其它费用</td>
							<td class="td-value">
${itemApp.projectOtherCost}							</td>
							<td class="td-label">是否套餐材料</td>
							<td class="td-value">
${itemApp.isSetItem}							</td>
						</tr>
						<tr>	
							<td class="td-label">材料总价</td>
							<td class="td-value">
${itemApp.amount}							</td>
							<td class="td-label">项目经理材料总价</td>
							<td class="td-value">
${itemApp.projectAmount}							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>

