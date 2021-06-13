<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    	<title>ItemAppDetail明细</title>
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
							<td class="td-label"><span class="required">*</span>PK</td>
							<td class="td-value">
${itemAppDetail.pk}							</td>
							<td class="td-label">批次号</td>
							<td class="td-value">
${itemAppDetail.no}							</td>
						</tr>
						<tr>	
							<td class="td-label">领料需求标识</td>
							<td class="td-value">
${itemAppDetail.reqPk}							</td>
							<td class="td-label"><span class="required">*</span>区域编码</td>
							<td class="td-value">
${itemAppDetail.fixAreaPk}							</td>
						</tr>
						<tr>	
							<td class="td-label">集成成品PK</td>
							<td class="td-value">
${itemAppDetail.intProdPk}							</td>
							<td class="td-label">材料编号</td>
							<td class="td-value">
${itemAppDetail.itemCode}							</td>
						</tr>
						<tr>	
							<td class="td-label">数量</td>
							<td class="td-value">
${itemAppDetail.qty}							</td>
							<td class="td-label">Remarks</td>
							<td class="td-value">
${itemAppDetail.remarks}							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>LastUpdate</td>
							<td class="td-value">
${itemAppDetail.lastUpdate}							</td>
							<td class="td-label">LastUpdatedBy</td>
							<td class="td-value">
${itemAppDetail.lastUpdatedBy}							</td>
						</tr>
						<tr>	
							<td class="td-label">Expired</td>
							<td class="td-value">
${itemAppDetail.expired}							</td>
							<td class="td-label">ActionLog</td>
							<td class="td-value">
${itemAppDetail.actionLog}							</td>
						</tr>
						<tr>	
							<td class="td-label">移动平均成本</td>
							<td class="td-value">
${itemAppDetail.cost}							</td>
							<td class="td-label">变动后数量</td>
							<td class="td-value">
${itemAppDetail.aftQty}							</td>
						</tr>
						<tr>	
							<td class="td-label">变动后平均成本</td>
							<td class="td-value">
${itemAppDetail.aftCost}							</td>
							<td class="td-label">发货数量</td>
							<td class="td-value">
${itemAppDetail.sendQty}							</td>
						</tr>
						<tr>	
							<td class="td-label">项目经理结算价</td>
							<td class="td-value" colspan="3">
${itemAppDetail.projectCost}							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>

