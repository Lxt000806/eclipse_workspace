<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>BaseItemReq明细</title>
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
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${baseItemReq.pk}" />
							</td>
							<td class="td-label"><span class="required">*</span>custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${baseItemReq.custCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>fixAreaPk</td>
							<td class="td-value">
							<input type="text" id="fixAreaPk" name="fixAreaPk" style="width:160px;"  value="${baseItemReq.fixAreaPk}" />
							</td>
							<td class="td-label">baseItemCode</td>
							<td class="td-value">
							<input type="text" id="baseItemCode" name="baseItemCode" style="width:160px;"  value="${baseItemReq.baseItemCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">qty</td>
							<td class="td-value">
							<input type="text" id="qty" name="qty" style="width:160px;"  value="${baseItemReq.qty}" />
							</td>
							<td class="td-label">cost</td>
							<td class="td-value">
							<input type="text" id="cost" name="cost" style="width:160px;"  value="${baseItemReq.cost}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">unitPrice</td>
							<td class="td-value">
							<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"  value="${baseItemReq.unitPrice}" />
							</td>
							<td class="td-label">lineAmount</td>
							<td class="td-value">
							<input type="text" id="lineAmount" name="lineAmount" style="width:160px;"  value="${baseItemReq.lineAmount}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">remark</td>
							<td class="td-value">
							<input type="text" id="remark" name="remark" style="width:160px;"  value="${baseItemReq.remark}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${baseItemReq.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${baseItemReq.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${baseItemReq.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${baseItemReq.actionLog}" />
							</td>
							<td class="td-label">material</td>
							<td class="td-value">
							<input type="text" id="material" name="material" style="width:160px;"  value="${baseItemReq.material}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">dispSeq</td>
							<td class="td-value">
							<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"  value="${baseItemReq.dispSeq}" />
							</td>
							<td class="td-label">isOutSet</td>
							<td class="td-value">
							<input type="text" id="isOutSet" name="isOutSet" style="width:160px;"  value="${baseItemReq.isOutSet}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">prjCtrlType</td>
							<td class="td-value">
							<input type="text" id="prjCtrlType" name="prjCtrlType" style="width:160px;"  value="${baseItemReq.prjCtrlType}" />
							</td>
							<td class="td-label">offerCtrl</td>
							<td class="td-value">
							<input type="text" id="offerCtrl" name="offerCtrl" style="width:160px;"  value="${baseItemReq.offerCtrl}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">materialCtrl</td>
							<td class="td-value" colspan="3">
							<input type="text" id="materialCtrl" name="materialCtrl" style="width:160px;"  value="${baseItemReq.materialCtrl}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${baseItemReq.expired}" onclick="checkExpired(this)" ${baseItemReq.expired=="T"?"checked":"" }>
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

