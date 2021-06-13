<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    	<title>Brand明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
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
							<td class="td-label"><span class="required">*</span>Code</td>
							<td class="td-value">
${brand.code}							</td>
							<td class="td-label">descr</td>
							<td class="td-value">
${brand.descr}							</td>
						</tr>
						<tr>	
							<td class="td-label">材料类型2</td>
							<td class="td-value">
${brand.itemType2}							</td>
							<td class="td-label">LastUpdate</td>
							<td class="td-value">
${brand.lastUpdate}							</td>
						</tr>
						<tr>	
							<td class="td-label">LastUpdatedBy</td>
							<td class="td-value">
${brand.lastUpdatedBy}							</td>
							<td class="td-label">Expired</td>
							<td class="td-value">
${brand.expired}							</td>
						</tr>
						<tr>	
							<td class="td-label">ActionLog</td>
							<td class="td-value" colspan="3">
${brand.actionLog}							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>

