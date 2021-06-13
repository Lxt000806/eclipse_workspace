<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>客户类型明细--分段业绩公式</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
		});
	</script>
	</head>

<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
							<button id="closeBut" type="button" class="btn btn-system"
								onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<form action="" method="post" id="data_form" class="form-search">
				<input
					type="hidden" name="isExitTip" id="isExitTip" value="0" /> <input
					type="hidden" name="m_umState" id="m_umState" />
				<ul class="ul-form">
					<div class="validate-group row">
						<li><label>编号</label> <input type="text" id="code"
							name="code" value="${custType.code}" readonly="readonly" />
						</li>
						<li><label>说明</label> <input type="text" id="desc1" name="no"
							value="${custType.desc1}" readonly="readonly" />
						</li>
					</div>
					<div class="validate-group row">
						<li><label>业绩公式</label> <input type="text" id="perfExpr"
							name="perfExpr" style="width:800px;" value="${custType.perfExpr}"
							readonly="readonly" />
						</li>
					</div>
					<div class="validate-group row">
						<li style="max-height: 120px;">
							<label class="control-textarea" style="top: -35px;">业绩公式说明</label>
							<textarea id="perfExprRemarks" name="perfExprRemarks" style="height: 50px;width: 800px;">${custType.perfExprRemarks}</textarea>
						</li>
					</div>
					<div class="validate-group row">
						<li><label>增减业绩公式</label> <input type="text" id="chgPerfExpr"
							name="chgPerfExpr" style="width:800px;" value="${custType.chgPerfExpr}"
							readonly="readonly" />
						</li>
					</div>
					<div class="validate-group row">
						<li style="max-height: 120px;">
							<label class="control-textarea" style="top: -35px;">增减业绩公式说明</label>
							<textarea id="chgPerfExprRemarks" name="chgPerfExprRemarks" style="height: 50px;width: 800px;">${custType.chgPerfExprRemarks}</textarea>
						</li>
					</div>
				</ul>
		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabCtrlExprDetail" class="active"><a
				href="#tab_CtrlExprDetail" data-toggle="tab">分段业绩公式明细</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_CtrlExprDetail" class="tab-pane fade in active">
				<jsp:include page="custType_custTypePerfExprDetail.jsp"></jsp:include>
			</div>
		</div>
	</div>
</body>
</html>
