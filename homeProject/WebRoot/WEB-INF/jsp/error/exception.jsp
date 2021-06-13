<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="org.slf4j.*"%>
<%
 	Logger logger = LoggerFactory.getLogger(this.getClass());
	Exception ex = (Exception)request.getAttribute("exception");
	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html>
<html>
<head>
	<title>系统出错啦~~~~</title>
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
				<li id="closeBut" onclick="window.close(); return false;">
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
					<col  width=""/>
					<tbody>
						<tr>
							<td class="td-label">错误信息</td>
							<td class="td-value"><span style="color: red;">${exception.message }</span></td>
						</tr>
						<tr>
							<td class="td-label">联系人</td>
							<td class="td-value"><span style="color: red;"></span></td>
						</tr>
						<tr>
							<td class="td-label" >错误堆栈</td>
							<td class="td-value" >
								<div  style="height: 250px; width:100%; overflow-y:auto;" >
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

