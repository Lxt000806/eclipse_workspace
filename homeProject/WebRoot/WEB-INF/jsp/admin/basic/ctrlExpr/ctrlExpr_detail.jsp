<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>分段发包--查看</title>
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
								value="${ctrlExpr.custType}" />
						<div class="validate-group row">
							<li class="form-validate"><label>开始日期从</label> <input type="text" id="beginDate"
								name="beginDate" class="i-date" style="width:160px;"
								value="<fmt:formatDate value='${ctrlExpr.beginDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate"><label>结束日期</label> <input type="text" id="endDate"
								name="endDate" class="i-date" style="width:160px;"
								value="<fmt:formatDate value='${ctrlExpr.endDate}' pattern='yyyy-MM-dd'/>" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>发包价公式</label> <input type="text" id="ctrlExpr"
								name="ctrlExpr" style="width:500px;" required
								data-bv-notempty-message="发包价公式不能为空"
								value="${ctrlExpr.ctrlExpr}" />
							</li>
						</div>
						<div class="validate-group row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -35px;">发包公式说明</label>
								<textarea id="ctrlExprRemarks" name="ctrlExprRemarks" style="height: 50px;width: 500px;">${ctrlExpr.ctrlExprRemarks}</textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>指定发包价公式</label> <input
								type="text" id="setCtrlExpr" name="setCtrlExpr"
								style="width:500px;" required
								data-bv-notempty-message="指定发包价公式不能为空"
								value="${ctrlExpr.setCtrlExpr}" />
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
