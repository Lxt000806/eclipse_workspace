<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>修改共享人</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		parent.$("#iframe_update").attr("height","98%"); //消灭掉无用的滑动条
		$("#shareCzy").openComponent_employee({
			showValue:"${resrCust.shareCzy}",
			showLabel:"${resrCust.shareCzyDescr}",
			condition:{
				empAuthority:"1"
			},
		});
		$("#saveBtn").on("click", function() {
			var data={};
			data.shareCzy=$("#shareCzy").val();
			data.shareCzyDescr=$("#openComponent_employee_shareCzy").val().split("|")[1];
			Global.Dialog.returnData = data;
			closeWin();
		});
	});
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
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
							<li><label>共享人</label> <input type="text" id="shareCzy"
								name="shareCzy" />
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
