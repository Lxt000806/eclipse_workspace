<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>配送明细管理</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_driver.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
		var excelName="send";
		var queryType="s_qr";
		function sendDetail(){
			excelName="send";
			queryType="s_qr";
		}
		function returnDetail(){
			excelName="return";
			queryType="r_qr";
		}
		function doExcel(){
			if(excelName=="send"){
				doExcelAll("${ctx}/admin/itemAppSend/doSendDetailQryExcel","sendDetailDataTable","send_form");
			}else if (excelName="return"){
				doExcelAll("${ctx}/admin/itemReturn/doReturnDetailQryExcel","returnDetailDataTable","return_form");
			}
		}
	//回车键搜索
	function keyQuery(queryType){
	 	if (event.keyCode==13)  //回车键的键值为13
	  	$("#"+queryType).click(); //调用登录按钮的登录事件
	}
	</script>
	</head>
<body onkeydown="keyQuery(queryType);">
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button type="button" id="doExcel" class="btn btn-system" onclick="doExcel()">导出Excel</button>
					<button type="button" id="closeWin" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabSendDetail" class="active" onclick="sendDetail()"><a
				href="#tab_SendDetail" data-toggle="tab">送货明细</a>
			</li>
			<li id="tabReturnDetail" class="" onclick="returnDetail()"><a
				href="#tab_ReturnDetail" data-toggle="tab">退货明细</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_SendDetail" class="tab-pane fade in active">
				<jsp:include page="itemSendBatch_detailQuery_sendDetail.jsp"></jsp:include>
			</div>
			<div id="tab_ReturnDetail" class="tab-pane fade ">
				<jsp:include page="itemSendBatch_detailQuery_returnDetail.jsp"></jsp:include>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
