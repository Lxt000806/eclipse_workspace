<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ItemReturn明细</title>
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
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemReturn.no}" readonly="readonly"/>
							</td>
							<td class="td-label"><span class="required">*</span>itemType1</td>
							<td class="td-value">
							<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemReturn.itemType1}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${itemReturn.custCode}" />
							</td>
							<td class="td-label"><span class="required">*</span>status</td>
							<td class="td-value">
							<input type="text" id="status" name="status" style="width:160px;"  value="${itemReturn.status}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>appCzy</td>
							<td class="td-value">
							<input type="text" id="appCzy" name="appCzy" style="width:160px;"  value="${itemReturn.appCzy}" />
							</td>
							<td class="td-label"><span class="required">*</span>date</td>
							<td class="td-value">
							<input type="text" id="date" name="date" style="width:160px;"  value="${itemReturn.date}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${itemReturn.remarks}" />
							</td>
							<td class="td-label">driverCode</td>
							<td class="td-value">
							<input type="text" id="driverCode" name="driverCode" style="width:160px;"  value="${itemReturn.driverCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">sendBatchNo</td>
							<td class="td-value">
							<input type="text" id="sendBatchNo" name="sendBatchNo" style="width:160px;"  value="${itemReturn.sendBatchNo}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemReturn.actionLog}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemReturn.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemReturn.lastUpdatedBy}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">expired</td>
							<td class="td-value" colspan="3">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemReturn.expired}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${itemReturn.expired}" onclick="checkExpired(this)" ${itemReturn.expired=="T"?"checked":"" }>
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

