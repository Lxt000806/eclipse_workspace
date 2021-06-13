<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>三级部门明细</title>
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
							<td class="td-label"><span class="required">*</span>三级部门编号</td>
							<td class="td-value"><input type="text" style="width:160px;" id="code" name="code" value="${department3.code }"/></td>
							<td class="td-label"><span class="required">*</span>三级部门名称</td>
							<td class="td-value"><input type="text" style="width:160px;" id="desc2" name="desc2" value="${department3.desc2 }"/></td>
						</tr>
						<tr>
							<td class="td-label"><span class="required">*</span>一级部门</td>
							<td class="td-value" colspan="1">
							<house:department1 id="department1" value="${department3.department1 }" onchange="changeDepartment1(this,'department2','${ctx }')"></house:department1>
							</td>
							<td class="td-label"><span class="required">*</span>二级部门</td>
							<td class="td-value" colspan="1">
							<house:department2 id="department2" dictCode="${department3.department1 }" value="${department3.department2 }"></house:department2>
							</td>
						</tr>
						<tr>
							<td class="td-label" >过期</td>
							<td class="td-value" colspan="3">
								<input type="checkbox" id="expired_show" name="expired_show" value="${department3.expired }" 
								onclick="checkExpired(this)" ${department3.expired=='T'?'checked':'' }>
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

