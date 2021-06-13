<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>一级部门明细</title>
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
							<td class="td-label"><span class="required">*</span>一级部门编号</td>
							<td class="td-value"><input type="text" style="width:160px;" id="code" name="code" value="${department1.code }" readonly="readonly"/></td>
							<td class="td-label"><span class="required">*</span>一级部门名称</td>
							<td class="td-value"><input type="text" style="width:160px;" id="desc2" name="desc2" value="${department1.desc2 }"/></td>
						</tr>
						<tr>
							<td class="td-label"><span class="required">*</span>公司名称</td>
							<td class="td-value" colspan="3">
							<input type="text" id="cmpCode" name="cmpCode" style="width:160px;" value="${department1.cmpCode }"/>
							<a href="javascript:void(0)" class="a1" onclick="Global.Dialog.fetch('cmpCode','${ctx}/admin/company/goCode','选择公司','Code')">【选取】</a>
							</td>
							
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${department1.expired }" 
								onclick="checkExpired(this)" ${department1.expired=='T'?'checked':'' }>
							</td>
						</tr>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>

