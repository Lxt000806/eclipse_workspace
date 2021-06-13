<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>CustProfit明细</title>
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
							<td class="td-label"><span class="required">*</span>custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${custProfit.custCode}" readonly="readonly"/>
							</td>
							<td class="td-label"><span class="required">*</span>baseDiscPer</td>
							<td class="td-value">
							<input type="text" id="baseDiscPer" name="baseDiscPer" style="width:160px;"  value="${custProfit.baseDiscPer}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>baseDisc1</td>
							<td class="td-value">
							<input type="text" id="baseDisc1" name="baseDisc1" style="width:160px;"  value="${custProfit.baseDisc1}" />
							</td>
							<td class="td-label"><span class="required">*</span>baseDisc2</td>
							<td class="td-value">
							<input type="text" id="baseDisc2" name="baseDisc2" style="width:160px;"  value="${custProfit.baseDisc2}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>designFee</td>
							<td class="td-value">
							<input type="text" id="designFee" name="designFee" style="width:160px;"  value="${custProfit.designFee}" />
							</td>
							<td class="td-label"><span class="required">*</span>gift</td>
							<td class="td-value">
							<input type="text" id="gift" name="gift" style="width:160px;"  value="${custProfit.gift}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>containBase</td>
							<td class="td-value">
							<input type="text" id="containBase" name="containBase" style="width:160px;"  value="${custProfit.containBase}" />
							</td>
							<td class="td-label"><span class="required">*</span>containMain</td>
							<td class="td-value">
							<input type="text" id="containMain" name="containMain" style="width:160px;"  value="${custProfit.containMain}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>containSoft</td>
							<td class="td-value">
							<input type="text" id="containSoft" name="containSoft" style="width:160px;"  value="${custProfit.containSoft}" />
							</td>
							<td class="td-label"><span class="required">*</span>containInt</td>
							<td class="td-value">
							<input type="text" id="containInt" name="containInt" style="width:160px;"  value="${custProfit.containInt}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>containCup</td>
							<td class="td-value">
							<input type="text" id="containCup" name="containCup" style="width:160px;"  value="${custProfit.containCup}" />
							</td>
							<td class="td-label"><span class="required">*</span>containMainServ</td>
							<td class="td-value">
							<input type="text" id="containMainServ" name="containMainServ" style="width:160px;"  value="${custProfit.containMainServ}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>colorDrawFee</td>
							<td class="td-value">
							<input type="text" id="colorDrawFee" name="colorDrawFee" style="width:160px;"  value="${custProfit.colorDrawFee}" />
							</td>
							<td class="td-label"><span class="required">*</span>remoteFee</td>
							<td class="td-value">
							<input type="text" id="remoteFee" name="remoteFee" style="width:160px;"  value="${custProfit.remoteFee}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>baseDisc</td>
							<td class="td-value">
							<input type="text" id="baseDisc" name="baseDisc" style="width:160px;"  value="${custProfit.baseDisc}" />
							</td>
							<td class="td-label"><span class="required">*</span>mainCost</td>
							<td class="td-value">
							<input type="text" id="mainCost" name="mainCost" style="width:160px;"  value="${custProfit.mainCost}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>jobPer</td>
							<td class="td-value">
							<input type="text" id="jobPer" name="jobPer" style="width:160px;"  value="${custProfit.jobPer}" />
							</td>
							<td class="td-label"><span class="required">*</span>basePro</td>
							<td class="td-value">
							<input type="text" id="basePro" name="basePro" style="width:160px;"  value="${custProfit.basePro}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>mainPro</td>
							<td class="td-value">
							<input type="text" id="mainPro" name="mainPro" style="width:160px;"  value="${custProfit.mainPro}" />
							</td>
							<td class="td-label"><span class="required">*</span>servPro</td>
							<td class="td-value">
							<input type="text" id="servPro" name="servPro" style="width:160px;"  value="${custProfit.servPro}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>intPro</td>
							<td class="td-value">
							<input type="text" id="intPro" name="intPro" style="width:160px;"  value="${custProfit.intPro}" />
							</td>
							<td class="td-label"><span class="required">*</span>cupPro</td>
							<td class="td-value">
							<input type="text" id="cupPro" name="cupPro" style="width:160px;"  value="${custProfit.cupPro}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>softPro</td>
							<td class="td-value">
							<input type="text" id="softPro" name="softPro" style="width:160px;"  value="${custProfit.softPro}" />
							</td>
							<td class="td-label"><span class="required">*</span>managePro</td>
							<td class="td-value">
							<input type="text" id="managePro" name="managePro" style="width:160px;"  value="${custProfit.managePro}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>designPro</td>
							<td class="td-value">
							<input type="text" id="designPro" name="designPro" style="width:160px;"  value="${custProfit.designPro}" />
							</td>
							<td class="td-label"><span class="required">*</span>allPro</td>
							<td class="td-value">
							<input type="text" id="allPro" name="allPro" style="width:160px;"  value="${custProfit.allPro}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>designCalPer</td>
							<td class="td-value">
							<input type="text" id="designCalPer" name="designCalPer" style="width:160px;"  value="${custProfit.designCalPer}" />
							</td>
							<td class="td-label"><span class="required">*</span>costPer</td>
							<td class="td-value">
							<input type="text" id="costPer" name="costPer" style="width:160px;"  value="${custProfit.costPer}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>baseCalPer</td>
							<td class="td-value">
							<input type="text" id="baseCalPer" name="baseCalPer" style="width:160px;"  value="${custProfit.baseCalPer}" />
							</td>
							<td class="td-label"><span class="required">*</span>mainCalPer</td>
							<td class="td-value">
							<input type="text" id="mainCalPer" name="mainCalPer" style="width:160px;"  value="${custProfit.mainCalPer}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>servProPer</td>
							<td class="td-value">
							<input type="text" id="servProPer" name="servProPer" style="width:160px;"  value="${custProfit.servProPer}" />
							</td>
							<td class="td-label"><span class="required">*</span>servCalPer</td>
							<td class="td-value">
							<input type="text" id="servCalPer" name="servCalPer" style="width:160px;"  value="${custProfit.servCalPer}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>jobCtrl</td>
							<td class="td-value">
							<input type="text" id="jobCtrl" name="jobCtrl" style="width:160px;"  value="${custProfit.jobCtrl}" />
							</td>
							<td class="td-label"><span class="required">*</span>jobLowPer</td>
							<td class="td-value">
							<input type="text" id="jobLowPer" name="jobLowPer" style="width:160px;"  value="${custProfit.jobLowPer}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>jobHighPer</td>
							<td class="td-value">
							<input type="text" id="jobHighPer" name="jobHighPer" style="width:160px;"  value="${custProfit.jobHighPer}" />
							</td>
							<td class="td-label"><span class="required">*</span>intProPer</td>
							<td class="td-value">
							<input type="text" id="intProPer" name="intProPer" style="width:160px;"  value="${custProfit.intProPer}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>intCalPer</td>
							<td class="td-value">
							<input type="text" id="intCalPer" name="intCalPer" style="width:160px;"  value="${custProfit.intCalPer}" />
							</td>
							<td class="td-label"><span class="required">*</span>cupProPer</td>
							<td class="td-value">
							<input type="text" id="cupProPer" name="cupProPer" style="width:160px;"  value="${custProfit.cupProPer}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>cupCalPer</td>
							<td class="td-value">
							<input type="text" id="cupCalPer" name="cupCalPer" style="width:160px;"  value="${custProfit.cupCalPer}" />
							</td>
							<td class="td-label"><span class="required">*</span>softProPer</td>
							<td class="td-value">
							<input type="text" id="softProPer" name="softProPer" style="width:160px;"  value="${custProfit.softProPer}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>softCalPer</td>
							<td class="td-value">
							<input type="text" id="softCalPer" name="softCalPer" style="width:160px;"  value="${custProfit.softCalPer}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${custProfit.lastUpdate}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${custProfit.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${custProfit.expired}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${custProfit.actionLog}" />
							</td>
							<td class="td-label">prepay</td>
							<td class="td-value">
							<input type="text" id="prepay" name="prepay" style="width:160px;"  value="${custProfit.prepay}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">payType</td>
							<td class="td-value">
							<input type="text" id="payType" name="payType" style="width:160px;"  value="${custProfit.payType}" />
							</td>
							<td class="td-label">position</td>
							<td class="td-value">
							<input type="text" id="position" name="position" style="width:160px;"  value="${custProfit.position}" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${custProfit.expired}" onclick="checkExpired(this)" ${custProfit.expired=="T"?"checked":"" }>
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

