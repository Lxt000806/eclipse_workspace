<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_discToken.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="query-form" hidden="true">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<ul class="ul-form">
					<input id="discTokenNo" name="discTokenNo" />
				</ul>
			</form>
		</div>
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		$(function() {
			var gridOption = {
				url: "${ctx}/admin/discTokenQuery/goHasSelectJqGrid",
				postData: {hasSelectNo: "${discToken.hasSelectNo}"},
				height: $(document).height()-$("#content-list").offset().top-50,
				rowNum: 10000000,
				colModel: [
					{name: "chgno", index: "chgno", width: 100, label: "材料增减单号", sortable: true, align: "left", hidden: true},
					{name: "no", index: "no", width: 80, label: "券号", sortable: true, align: "left"},
					{name:"descr", index:"descr", width:120, label:"券名", sortable:true, align:"left"},
					{name: "amount", index: "amount", width: 80, label: "金额", sortable: true, align: "left"},
					{name:"statusdescr", index:"statusdescr", width:60, label:"状态", sortable:true, align:"left"},
					{name:"enabledate", index:"enabledate", width:80, label:"生效日期", sortable:true, align:"left", formatter:formatDate},
				],
			};
			Global.JqGrid.initJqGrid("dataTable",gridOption);
		});
	</script>
</body>	
</html>
