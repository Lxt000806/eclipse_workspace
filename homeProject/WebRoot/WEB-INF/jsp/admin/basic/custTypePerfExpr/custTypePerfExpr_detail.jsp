<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>分段业绩公式--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	.panel-info {
		margin: 0px;
	}
</style>
<script type="text/javascript">
	$(function() {
		$("input").attr("readonly","readonly");
		$("select").attr("disabled",true);
	});
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<input type="hidden" id="custType"
								name="custType" style="width:160px;" 
								value="${custTypePerfExpr.custType}" />
						<div class="validate-group row">
							<li class="form-validate"><label>开始日期从</label> <input type="text" id="beginDate"
								name="beginDate" class="i-date" style="width:160px;"
								value="<fmt:formatDate value='${custTypePerfExpr.beginDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate"><label>结束日期</label> <input type="text" id="endDate"
								name="endDate" class="i-date" style="width:160px;"
								value="<fmt:formatDate value='${custTypePerfExpr.endDate}' pattern='yyyy-MM-dd'/>" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>业绩公式</label> <input type="text" id="perfExpr"
								name="perfExpr" style="width:500px;" required
								data-bv-notempty-message="业绩公式不能为空"
								value="${custTypePerfExpr.perfExpr}" />
							</li>
						</div>
						<div class="validate-group row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -35px;">业绩公式说明</label>
								<textarea id="perfExprRemarks" name="perfExprRemarks" style="height: 50px;width: 500px;">${custTypePerfExpr.perfExprRemarks}</textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>增减业绩公式</label> <input type="text" id="chgPerfExpr"
								name="chgPerfExpr" style="width:500px;" required
								data-bv-notempty-message="增减业绩公式不能为空"
								value="${custTypePerfExpr.chgPerfExpr}" />
							</li>
						</div>
						<div class="validate-group row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -35px;">增减业绩公式说明</label>
								<textarea id="chgPerfExprRemarks" name="chgPerfExprRemarks" style="height: 50px;width: 500px;">${custTypePerfExpr.chgPerfExprRemarks}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
