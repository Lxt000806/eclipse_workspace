<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ItemReturnArrive明细</title>
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
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemReturnArrive.no}" readonly="readonly"/>
							</td>
							<td class="td-label"><span class="required">*</span>returnNo</td>
							<td class="td-value">
							<input type="text" id="returnNo" name="returnNo" style="width:160px;"  value="${itemReturnArrive.returnNo}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>driverCode</td>
							<td class="td-value">
							<input type="text" id="driverCode" name="driverCode" style="width:160px;"  value="${itemReturnArrive.driverCode}" />
							</td>
							<td class="td-label">address</td>
							<td class="td-value">
							<input type="text" id="address" name="address" style="width:160px;"  value="${itemReturnArrive.address}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">driverRemark</td>
							<td class="td-value">
							<input type="text" id="driverRemark" name="driverRemark" style="width:160px;"  value="${itemReturnArrive.driverRemark}" />
							</td>
							<td class="td-label"><span class="required">*</span>arriveDate</td>
							<td class="td-value">
							<input type="text" id="arriveDate" name="arriveDate" style="width:160px;"  value="${itemReturnArrive.arriveDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemReturnArrive.actionLog}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemReturnArrive.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemReturnArrive.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemReturnArrive.expired}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${itemReturnArrive.expired}" onclick="checkExpired(this)" ${itemReturnArrive.expired=="T"?"checked":"" }>
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

