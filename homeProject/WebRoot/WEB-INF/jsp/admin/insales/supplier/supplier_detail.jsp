<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    	<title>Supplier明细</title>
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
${supplier.code}							</td>
							<td class="td-label"><span class="required">*</span>名称</td>
							<td class="td-value">
${supplier.descr}							</td>
						</tr>
						<tr>	
							<td class="td-label">地址</td>
							<td class="td-value">
${supplier.address}							</td>
							<td class="td-label">联系人</td>
							<td class="td-value">
${supplier.contact}							</td>
						</tr>
						<tr>	
							<td class="td-label">电话1</td>
							<td class="td-value">
${supplier.phone1}							</td>
							<td class="td-label">Phone2</td>
							<td class="td-value">
${supplier.phone2}							</td>
						</tr>
						<tr>	
							<td class="td-label">传真1</td>
							<td class="td-value">
${supplier.fax1}							</td>
							<td class="td-label">Fax2</td>
							<td class="td-value">
${supplier.fax2}							</td>
						</tr>
						<tr>	
							<td class="td-label">手机</td>
							<td class="td-value">
${supplier.mobile1}							</td>
							<td class="td-label">Mobile2</td>
							<td class="td-value">
${supplier.mobile2}							</td>
						</tr>
						<tr>	
							<td class="td-label">邮件地址1</td>
							<td class="td-value">
${supplier.email1}							</td>
							<td class="td-label">Email2</td>
							<td class="td-value">
${supplier.email2}							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>是否指定结算日期</td>
							<td class="td-value">
${supplier.isSpecDay}							</td>
							<td class="td-label"><span class="required">*</span>指定结算日期</td>
							<td class="td-value">
${supplier.specDay}							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>结算周期</td>
							<td class="td-value">
${supplier.billCycle}							</td>
							<td class="td-label"><span class="required">*</span>LastUpdate</td>
							<td class="td-value">
${supplier.lastUpdate}							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>LastUpdatedBy</td>
							<td class="td-value">
${supplier.lastUpdatedBy}							</td>
							<td class="td-label"><span class="required">*</span>Expired</td>
							<td class="td-value">
${supplier.expired}							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>ActionLog</td>
							<td class="td-value">
${supplier.actionLog}							</td>
							<td class="td-label">材料类型1</td>
							<td class="td-value">
${supplier.itemType1}							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>

