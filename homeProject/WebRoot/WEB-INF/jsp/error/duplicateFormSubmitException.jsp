<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


<!DOCTYPE html>
<html>
<head>
	<title></title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />

	<script type="text/javascript" src="${resourceRoot}/js/jquery.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.core.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.panelBar.js"></script>
	<link href="${resourceRoot}/css/iss.core.css" rel="stylesheet" />

<script type="text/javascript">

$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
});
</script>

</head>
<body>
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
		<div class="panelBar">
			<ul>
				<li >
					<a href="javascript:void(0)" class="a1" id="saveBut" onclick="save()">
					   <span>保存</span>
					</a>	
				</li>
				<li id="closeBut" onclick="closeWin(false)">
					<a href="javascript:void(0)" class="a2">
						<span>关闭</span>
					</a>
				</li>
				<li class="line"></li>
			</ul>
			<div class="clear_float"> </div>
		</div>

		<div class="edit-form">
			<form action="" method="post" id="dataForm">
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="250"/>
					<tbody>
						<tr>
							<td class="td-label">错误信息</td>
							<td class="td-value">${exception.message }</td>
						</tr>
						<tr>
							<td class="td-label" >错误堆栈</td>
							<td class="td-value" colspan="3">
								<div class="infoBox" id="infoBoxDiv">
									<%=org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace((Throwable)request.getAttribute("exception"))%>
								</div>
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

