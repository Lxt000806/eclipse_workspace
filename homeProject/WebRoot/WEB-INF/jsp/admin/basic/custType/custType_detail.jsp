              <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>客户类型明细--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		$("input").attr("readonly","readonly");
		$("select,textarea").attr("disabled",true);
	});
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
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
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label>编号</label> <input type="text" id="code"
								maxlength="10" name="code" style="width:160px;"
								value="${custType.code}" />
							</li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_info" data-toggle="tab">主项目</a>
					</li>
					<li><a href="#tab_manage" data-toggle="tab">管理费系数</a>
					</li>
					<li><a href="#tab_perf" data-toggle="tab">发包/业绩</a>
					</li>
				</ul>
				<ul class="ul-form">
					<div class="tab-content">
						<div id="tab_info" class="tab-pane fade in active">
							<jsp:include page="custType_detailTab.jsp"></jsp:include>
						</div>
						<div id="tab_manage" class="tab-pane fade>
							<jsp:include page="custType_detailManageTab.jsp"></jsp:include>
						</div>
						<div id="tab_perf" class="tab-pane fade>
							<jsp:include page="custType_updatePerfTab.jsp"></jsp:include>
						</div> 
					</div>
				</ul>
			</div>
		</div>
	</form>
</body>
</html>
