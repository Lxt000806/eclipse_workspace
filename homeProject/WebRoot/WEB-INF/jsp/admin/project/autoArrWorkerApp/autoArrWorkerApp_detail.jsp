<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>AutoArrWorkerApp明细</title>
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
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${autoArrWorkerApp.pk}" />
							</td>
							<td class="td-label">arrPk</td>
							<td class="td-value">
							<input type="text" id="arrPk" name="arrPk" style="width:160px;"  value="${autoArrWorkerApp.arrPk}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">appPk</td>
							<td class="td-value">
							<input type="text" id="appPk" name="appPk" style="width:160px;"  value="${autoArrWorkerApp.appPk}" />
							</td>
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${autoArrWorkerApp.custCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">workType12</td>
							<td class="td-value">
							<input type="text" id="workType12" name="workType12" style="width:160px;"  value="${autoArrWorkerApp.workType12}" />
							</td>
							<td class="td-label">appComeDate</td>
							<td class="td-value">
							<input type="text" id="appComeDate" name="appComeDate" style="width:160px;"  value="${autoArrWorkerApp.appComeDate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">custType</td>
							<td class="td-value">
							<input type="text" id="custType" name="custType" style="width:160px;"  value="${autoArrWorkerApp.custType}" />
							</td>
							<td class="td-label">projectMan</td>
							<td class="td-value">
							<input type="text" id="projectMan" name="projectMan" style="width:160px;"  value="${autoArrWorkerApp.projectMan}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">prjLevel</td>
							<td class="td-value">
							<input type="text" id="prjLevel" name="prjLevel" style="width:160px;"  value="${autoArrWorkerApp.prjLevel}" />
							</td>
							<td class="td-label">isSupvr</td>
							<td class="td-value">
							<input type="text" id="isSupvr" name="isSupvr" style="width:160px;"  value="${autoArrWorkerApp.isSupvr}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">spcBuilder</td>
							<td class="td-value">
							<input type="text" id="spcBuilder" name="spcBuilder" style="width:160px;"  value="${autoArrWorkerApp.spcBuilder}" />
							</td>
							<td class="td-label">spcBldExpired</td>
							<td class="td-value">
							<input type="text" id="spcBldExpired" name="spcBldExpired" style="width:160px;"  value="${autoArrWorkerApp.spcBldExpired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">regionCode</td>
							<td class="td-value">
							<input type="text" id="regionCode" name="regionCode" style="width:160px;"  value="${autoArrWorkerApp.regionCode}" />
							</td>
							<td class="td-label">regionCode2</td>
							<td class="td-value">
							<input type="text" id="regionCode2" name="regionCode2" style="width:160px;"  value="${autoArrWorkerApp.regionCode2}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">regIsSpcWorker</td>
							<td class="td-value">
							<input type="text" id="regIsSpcWorker" name="regIsSpcWorker" style="width:160px;"  value="${autoArrWorkerApp.regIsSpcWorker}" />
							</td>
							<td class="td-label">department1</td>
							<td class="td-value">
							<input type="text" id="department1" name="department1" style="width:160px;"  value="${autoArrWorkerApp.department1}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">area</td>
							<td class="td-value">
							<input type="text" id="area" name="area" style="width:160px;"  value="${autoArrWorkerApp.area}" />
							</td>
							<td class="td-label">workerCode</td>
							<td class="td-value">
							<input type="text" id="workerCode" name="workerCode" style="width:160px;"  value="${autoArrWorkerApp.workerCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">comeDate</td>
							<td class="td-value">
							<input type="text" id="comeDate" name="comeDate" style="width:160px;"  value="${autoArrWorkerApp.comeDate}" />
							</td>
							<td class="td-label"><span class="required">*</span>lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${autoArrWorkerApp.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${autoArrWorkerApp.lastUpdatedBy}" />
							</td>
							<td class="td-label"><span class="required">*</span>expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${autoArrWorkerApp.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>actionLog</td>
							<td class="td-value" colspan="3">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${autoArrWorkerApp.actionLog}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${autoArrWorkerApp.expired}" onclick="checkExpired(this)" ${autoArrWorkerApp.expired=="T"?"checked":"" }>
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

