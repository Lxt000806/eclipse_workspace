<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>PurchaseDetail明细</title>
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
		<!--panelBar-->
		<div class="panelBar">
			<ul>
				<li id="closeBut">
					<a href="#" class="a2" onclick="closeWin()">
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
							<td class="td-label"><span class="required">*</span>pk</td>
							<td class="td-value">
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${purchaseDetail.pk}" />
							</td>
							<td class="td-label">puno</td>
							<td class="td-value">
							<input type="text" id="puno" name="puno" style="width:160px;"  value="${purchaseDetail.puno}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>itcode</td>
							<td class="td-value">
							<input type="text" id="itcode" name="itcode" style="width:160px;"  value="${purchaseDetail.itcode}" />
							</td>
							<td class="td-label">qtyCal</td>
							<td class="td-value">
							<input type="text" id="qtyCal" name="qtyCal" style="width:160px;"  value="${purchaseDetail.qtyCal}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">unitPrice</td>
							<td class="td-value">
							<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"  value="${purchaseDetail.unitPrice}" />
							</td>
							<td class="td-label">amount</td>
							<td class="td-value">
							<input type="text" id="amount" name="amount" style="width:160px;"  value="${purchaseDetail.amount}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${purchaseDetail.remarks}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${purchaseDetail.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${purchaseDetail.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${purchaseDetail.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${purchaseDetail.actionLog}" />
							</td>
							<td class="td-label">arrivQty</td>
							<td class="td-value">
							<input type="text" id="arrivQty" name="arrivQty" style="width:160px;"  value="${purchaseDetail.arrivQty}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">projectCost</td>
							<td class="td-value" colspan="3">
							<input type="text" id="projectCost" name="projectCost" style="width:160px;"  value="${purchaseDetail.projectCost}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${purchaseDetail.expired}" onclick="checkExpired(this)" ${purchaseDetail.expired=="T"?"checked":"" }>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>

