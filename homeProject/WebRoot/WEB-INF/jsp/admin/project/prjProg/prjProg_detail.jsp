<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>PrjProg明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<link href="${resourceRoot}/css/iss.core.css" rel="stylesheet" />
	<script type="text/javascript" src="${resourceRoot}/js/jquery.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.core.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.panelBar.js"></script>
	
	<script type="text/javascript">

$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
});


</script>
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
${prjProg.pk}							</td>
							<td class="td-label"><span class="required">*</span>custCode</td>
							<td class="td-value">
${prjProg.custCode}							</td>
						</tr>
						<tr>	
							<td class="td-label">prjItem</td>
							<td class="td-value">
${prjProg.prjItem}							</td>
							<td class="td-label"><span class="required">*</span>planBegin</td>
							<td class="td-value">
${prjProg.planBegin}							</td>
						</tr>
						<tr>	
							<td class="td-label">beginDate</td>
							<td class="td-value">
${prjProg.beginDate}							</td>
							<td class="td-label"><span class="required">*</span>planEnd</td>
							<td class="td-value">
${prjProg.planEnd}							</td>
						</tr>
						<tr>	
							<td class="td-label">endDate</td>
							<td class="td-value">
${prjProg.endDate}							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
${prjProg.lastUpdate}							</td>
						</tr>
						<tr>	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
${prjProg.lastUpdatedBy}							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
${prjProg.expired}							</td>
						</tr>
						<tr>	
							<td class="td-label">actionLog</td>
							<td class="td-value" colspan="3">
${prjProg.actionLog}							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>

