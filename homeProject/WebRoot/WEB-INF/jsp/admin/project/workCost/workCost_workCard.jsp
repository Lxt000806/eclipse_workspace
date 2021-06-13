<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>基础人工成本--按账号汇总</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workCost/goJqGrid3",
		postData:{nos:'${workCost.nos}'},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap',
		rowNum:10000000, 
		onSortColEndFlag:true,
		colModel : [
				{name: "cardid", index: "cardid", width: 204, label: "卡号", sortable: true, align: "left"},
				{name: "actname", index: "actname", width: 124, label: "户名", sortable: true, align: "left"},
				{name: "confirmamount", index: "confirmamount", width: 128, label: "审批金额", sortable: true, align: "right", sum: true},
				{name: "actuallysendamount", index: "actuallysendamount", width: 128, label: "实发金额", sortable: true, align: "right", sum: true},
				{name: "idnum", index: "idnum", width: 140, label: "身份证号", sortable: true, align: "left"},
				{name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种分类12", sortable: true, align: "left"},
				
		],
		onSortCol:function(index, iCol, sortorder){
					var rows = $("#dataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("dataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("dataTable", v);
					});
				}, 
	});
	
	});
	</script>
</head>
<body>
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
			</form>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<button type="button" class="btn btn-system" id="excel" onclick="doExcelNow('按账号汇总','dataTable','page_form')">
				<span>导出Excel</span>
			</button>
				<button type="button" class="btn btn-system" id="close" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
	</div>
</body>
</html>
