<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>SplCheckOut明细</title>
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
							<td class="td-label"><span class="required">*</span>no</td>
							<td class="td-value">
							<input type="text" id="no" name="no" style="width:160px;"  value="${splCheckOut.no}" readonly="readonly"/>
							</td>
							<td class="td-label"><span class="required">*</span>splCode</td>
							<td class="td-value">
							<input type="text" id="splCode" name="splCode" style="width:160px;"  value="${splCheckOut.splCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>date</td>
							<td class="td-value">
							<input type="text" id="date" name="date" style="width:160px;"  value="${splCheckOut.date}" />
							</td>
							<td class="td-label"><span class="required">*</span>payType</td>
							<td class="td-value">
							<input type="text" id="payType" name="payType" style="width:160px;"  value="${splCheckOut.payType}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>beginDate</td>
							<td class="td-value">
							<input type="text" id="beginDate" name="beginDate" style="width:160px;"  value="${splCheckOut.beginDate}" />
							</td>
							<td class="td-label"><span class="required">*</span>endDate</td>
							<td class="td-value">
							<input type="text" id="endDate" name="endDate" style="width:160px;"  value="${splCheckOut.endDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>payAmount</td>
							<td class="td-value">
							<input type="text" id="payAmount" name="payAmount" style="width:160px;"  value="${splCheckOut.payAmount}" />
							</td>
							<td class="td-label">remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${splCheckOut.remarks}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${splCheckOut.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${splCheckOut.lastUpdatedBy}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${splCheckOut.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${splCheckOut.actionLog}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">remark</td>
							<td class="td-value">
							<input type="text" id="remark" name="remark" style="width:160px;"  value="${splCheckOut.remark}" />
							</td>
							<td class="td-label">otherCost</td>
							<td class="td-value">
							<input type="text" id="otherCost" name="otherCost" style="width:160px;"  value="${splCheckOut.otherCost}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">status</td>
							<td class="td-value">
							<input type="text" id="status" name="status" style="width:160px;"  value="${splCheckOut.status}" />
							</td>
							<td class="td-label">confirmCzy</td>
							<td class="td-value">
							<input type="text" id="confirmCzy" name="confirmCzy" style="width:160px;"  value="${splCheckOut.confirmCzy}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">confirmDate</td>
							<td class="td-value">
							<input type="text" id="confirmDate" name="confirmDate" style="width:160px;"  value="${splCheckOut.confirmDate}" />
							</td>
							<td class="td-label">documentNo</td>
							<td class="td-value">
							<input type="text" id="documentNo" name="documentNo" style="width:160px;"  value="${splCheckOut.documentNo}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">payCzy</td>
							<td class="td-value">
							<input type="text" id="payCzy" name="payCzy" style="width:160px;"  value="${splCheckOut.payCzy}" />
							</td>
							<td class="td-label">payDate</td>
							<td class="td-value">
							<input type="text" id="payDate" name="payDate" style="width:160px;"  value="${splCheckOut.payDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">paidAmount</td>
							<td class="td-value">
							<input type="text" id="paidAmount" name="paidAmount" style="width:160px;"  value="${splCheckOut.paidAmount}" />
							</td>
							<td class="td-label">nowAmount</td>
							<td class="td-value">
							<input type="text" id="nowAmount" name="nowAmount" style="width:160px;"  value="${splCheckOut.nowAmount}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">preAmount</td>
							<td class="td-value" colspan="3">
							<input type="text" id="preAmount" name="preAmount" style="width:160px;"  value="${splCheckOut.preAmount}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${splCheckOut.expired}" onclick="checkExpired(this)" ${splCheckOut.expired=="T"?"checked":"" }>
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

