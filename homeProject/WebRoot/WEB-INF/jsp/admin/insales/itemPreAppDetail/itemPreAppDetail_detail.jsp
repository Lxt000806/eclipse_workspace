<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ItemPreAppDetail明细</title>
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
							<td class="td-label"><span class="required">*</span>pk</td>
							<td class="td-value">
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${itemPreAppDetail.pk}" />
							</td>
							<td class="td-label">no</td>
							<td class="td-value">
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemPreAppDetail.no}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">itemCode</td>
							<td class="td-value">
							<input type="text" id="itemCode" name="itemCode" style="width:160px;"  value="${itemPreAppDetail.itemCode}" />
							</td>
							<td class="td-label">qty</td>
							<td class="td-value">
							<input type="text" id="qty" name="qty" style="width:160px;"  value="${itemPreAppDetail.qty}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">reqPk</td>
							<td class="td-value">
							<input type="text" id="reqPk" name="reqPk" style="width:160px;"  value="${itemPreAppDetail.reqPk}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemPreAppDetail.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemPreAppDetail.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemPreAppDetail.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemPreAppDetail.actionLog}" />
							</td>
							<td class="td-label">remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${itemPreAppDetail.remarks}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${itemPreAppDetail.expired}" onclick="checkExpired(this)" ${itemPreAppDetail.expired=="T"?"checked":"" }>
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

