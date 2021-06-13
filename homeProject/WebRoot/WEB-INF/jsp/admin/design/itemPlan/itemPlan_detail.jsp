<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>ItemPlan明细</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
			<!--panelBar-->
			<div class="panelBar">
				<ul>
					<li id="closeBut"><a href="#" class="a2" onclick="closeWin()"> <span>关闭</span> </a>
					</li>
					<li class="line"></li>
				</ul>
				<div class="clear_float"></div>
			</div>
			<div class="edit-form">
				<form action="" method="post" id="dataForm" enctype="multipart/form-data">
					<table cellspacing="0" cellpadding="0" width="100%">
						<col width="72" />
						<col width="150" />
						<col width="72" />
						<col width="150" />
						<tbody>
							<tr>
								<td class="td-label"><span class="required">*</span>pk</td>
								<td class="td-value"><input type="text" id="pk" name="pk" style="width:160px;"
									value="${itemPlan.pk}" />
								</td>
								<td class="td-label"><span class="required">*</span>custCode</td>
								<td class="td-value"><input type="text" id="custCode" name="custCode" style="width:160px;"
									value="${itemPlan.custCode}" />
								</td>
							</tr>
							<tr>
								<td class="td-label"><span class="required">*</span>fixAreaPk</td>
								<td class="td-value"><input type="text" id="fixAreaPk" name="fixAreaPk" style="width:160px;"
									value="${itemPlan.fixAreaPk}" />
								</td>
								<td class="td-label">intProdPk</td>
								<td class="td-value"><input type="text" id="intProdPk" name="intProdPk" style="width:160px;"
									value="${itemPlan.intProdPk}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">itemCode</td>
								<td class="td-value"><input type="text" id="itemCode" name="itemCode" style="width:160px;"
									value="${itemPlan.itemCode}" />
								</td>
								<td class="td-label"><span class="required">*</span>itemType1</td>
								<td class="td-value"><input type="text" id="itemType1" name="itemType1" style="width:160px;"
									value="${itemPlan.itemType1}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">projectQty</td>
								<td class="td-value"><input type="text" id="projectQty" name="projectQty" style="width:160px;"
									value="${itemPlan.projectQty}" />
								</td>
								<td class="td-label">qty</td>
								<td class="td-value"><input type="text" id="qty" name="qty" style="width:160px;"
									value="${itemPlan.qty}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">cost</td>
								<td class="td-value"><input type="text" id="cost" name="cost" style="width:160px;"
									value="${itemPlan.cost}" />
								</td>
								<td class="td-label">unitPrice</td>
								<td class="td-value"><input type="text" id="unitPrice" name="unitPrice" style="width:160px;"
									value="${itemPlan.unitPrice}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">befLineAmount</td>
								<td class="td-value"><input type="text" id="befLineAmount" name="befLineAmount"
									style="width:160px;" value="${itemPlan.befLineAmount}" />
								</td>
								<td class="td-label">markup</td>
								<td class="td-value"><input type="text" id="markup" name="markup" style="width:160px;"
									value="${itemPlan.markup}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">lineAmount</td>
								<td class="td-value"><input type="text" id="lineAmount" name="lineAmount" style="width:160px;"
									value="${itemPlan.lineAmount}" />
								</td>
								<td class="td-label">remark</td>
								<td class="td-value"><input type="text" id="remark" name="remark" style="width:160px;"
									value="${itemPlan.remark}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">lastUpdate</td>
								<td class="td-value"><input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"
									value="${itemPlan.lastUpdate}" />
								</td>
								<td class="td-label">lastUpdatedBy</td>
								<td class="td-value"><input type="text" id="lastUpdatedBy" name="lastUpdatedBy"
									style="width:160px;" value="${itemPlan.lastUpdatedBy}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">expired</td>
								<td class="td-value"><input type="text" id="expired" name="expired" style="width:160px;"
									value="${itemPlan.expired}" />
								</td>
								<td class="td-label">actionLog</td>
								<td class="td-value"><input type="text" id="actionLog" name="actionLog" style="width:160px;"
									value="${itemPlan.actionLog}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">dispSeq</td>
								<td class="td-value"><input type="text" id="dispSeq" name="dispSeq" style="width:160px;"
									value="${itemPlan.dispSeq}" />
								</td>
								<td class="td-label">processCost</td>
								<td class="td-value"><input type="text" id="processCost" name="processCost" style="width:160px;"
									value="${itemPlan.processCost}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">isService</td>
								<td class="td-value"><input type="text" id="isService" name="isService" style="width:160px;"
									value="${itemPlan.isService}" />
								</td>
								<td class="td-label">isCommi</td>
								<td class="td-value"><input type="text" id="isCommi" name="isCommi" style="width:160px;"
									value="${itemPlan.isCommi}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">isOutSet</td>
								<td class="td-value" colspan="3"><input type="text" id="isOutSet" name="isOutSet"
									style="width:160px;" value="${itemPlan.isOutSet}" />
								</td>
							</tr>
							<tr>
								<td class="td-label">过期</td>
								<td class="td-value" colspan="3"><input type="checkbox" id="expired_show" name="expired_show"
									value="${itemPlan.expired}" onclick="checkExpired(this)" ${itemPlan.expired=="T"?"checked":"" }>
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

