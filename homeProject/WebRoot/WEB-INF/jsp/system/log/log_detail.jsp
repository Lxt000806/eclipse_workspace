<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>日志明细</title>
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
				<table cellspacing="0" cellpadding="0">
					<col width="72"/>
					<col width="250"/>
					<col width="72"/>
					<col width="250"/>
					<tbody>
						<tr>
							<td class="td-label">模块名称</td>
							<td class="td-value">
							<house:dict id="tt" dictCode="${ABSTRACT_DICT_LOG_MODULE}" readonly="true" value="${page.result[0].model_code}"></house:dict>
							</td>
							<td class="td-label">ip地址</td>
							<td class="td-value">${page.result[0].ip}</td>
							
						</tr>
						<tr>
							<td class="td-label">CLAZZ</td>
							<td class="td-value">${page.result[0].clazz}</td>
							<td class="td-label">调用方法</td>
							<td class="td-value">${page.result[0].java_fun}</td>
						</tr>
						<tr>
							<td class="td-label">操作人</td>
							<td class="td-value">
								${page.result[0].username}
							</td>
							<td class="td-label">操作时间</td>
							<td class="td-value">${page.result[0].addtime}</td>
						</tr>
						<tr>
							<td class="td-label" >操作内容</td>
							<td class="td-value" colspan="3" >
								<div id="remark" class="textAreaDiv">${page.result[0].oprcontent}</div>
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

