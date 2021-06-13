<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>公司明细</title>
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
			<form action="" method="post" id="dataForm">
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>
							<td class="td-label"><span class="required">*</span>公司编号</td>
							<td class="td-value"><input type="text" style="width:160px;" id="code" name="code" value="${company.code }" readonly="readonly"/></td>
							<td class="td-label"><span class="required">*</span>英文名称</td>
							<td class="td-value"><input type="text" style="width:160px;" id="desc1" name="desc1" value="${company.desc1 }"/></td>
						</tr>
						<tr>
							<td class="td-label"><span class="required">*</span>中文名称</td>
							<td class="td-value"><input type="text" style="width:160px;" id="desc2" name="desc2" value="${company.desc2 }"/></td>
							<td class="td-label"><span class="required">*</span>公司类型</td>
							<td class="td-value"><house:xtdm id="type" dictCode="CMPTYP" value="${company.type }"></house:xtdm></td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${company.expired }" 
								onclick="checkExpired(this)" ${company.expired=='T'?'checked':'' }>
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

