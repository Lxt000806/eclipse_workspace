<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>CustPayPre明细</title>
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
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${custPayPre.pk}" />
							</td>
							<td class="td-label"><span class="required">*</span>custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${custPayPre.custCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>payType</td>
							<td class="td-value">
							<input type="text" id="payType" name="payType" style="width:160px;"  value="${custPayPre.payType}" />
							</td>
							<td class="td-label"><span class="required">*</span>basePer</td>
							<td class="td-value">
							<input type="text" id="basePer" name="basePer" style="width:160px;"  value="${custPayPre.basePer}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>itemPer</td>
							<td class="td-value">
							<input type="text" id="itemPer" name="itemPer" style="width:160px;"  value="${custPayPre.itemPer}" />
							</td>
							<td class="td-label"><span class="required">*</span>designFee</td>
							<td class="td-value">
							<input type="text" id="designFee" name="designFee" style="width:160px;"  value="${custPayPre.designFee}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>prePay</td>
							<td class="td-value">
							<input type="text" id="prePay" name="prePay" style="width:160px;"  value="${custPayPre.prePay}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${custPayPre.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${custPayPre.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${custPayPre.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${custPayPre.actionLog}" />
							</td>
							<td class="td-label">basePay</td>
							<td class="td-value">
							<input type="text" id="basePay" name="basePay" style="width:160px;"  value="${custPayPre.basePay}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">itemPay</td>
							<td class="td-value" colspan="3">
							<input type="text" id="itemPay" name="itemPay" style="width:160px;"  value="${custPayPre.itemPay}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${custPayPre.expired}" onclick="checkExpired(this)" ${custPayPre.expired=="T"?"checked":"" }>
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

