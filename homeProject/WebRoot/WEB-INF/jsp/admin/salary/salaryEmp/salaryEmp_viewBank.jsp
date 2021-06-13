<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>银行卡信息</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_worker.js?v=${v}"
	type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_workCard.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	}	
	$(function(){
		var gridOption = {
			height:250,
			rowNum:10000000,
			url:"${ctx}/admin/salaryEmp/goBankJqGrid",
			postData: {empCode:"${salaryEmp.empCode}"},
			styleUI: "Bootstrap", 
			colModel : [
				{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "banktype", index: "banktype", width: 100, label: "银行", sortable: true, align: "left", hidden: true},
				{name: "banktypedescr", index: "banktypedescr", width: 120, label: "银行", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 180, label: "卡号", sortable: true, align: "left"},
				{name: "actname", index: "actname", width: 80, label: "户名", sortable: true, align: "left",},
				{name: "remarks", index: "remarks", width: 250, label: "备注", sortable: true, align: "left"},
			],
			loadonce:true
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	});
	</script>
</head>
<body >
	<div class="panel panel-system">
	    <div class="panel-body">
	    	<div class="btn-group-xs">
				<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="body-box-list">
		<div id="content-list">
			<table id="dataTable"></table>
		</div>
	</div>
</body>
</html>
