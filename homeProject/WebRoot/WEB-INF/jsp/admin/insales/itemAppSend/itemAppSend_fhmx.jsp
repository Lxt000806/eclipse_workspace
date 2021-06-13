<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>分批发货明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
<script type="text/javascript">
/**初始化表格*/
$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_fhmx_mx",{
			height:210,
			rowNum: 10000,
			colModel : [
			    {name: "itemcode", index: "itemcode", width: 200, label: "材料编号", sortable: true, align: "left", count: true},
				{name: "itemdescr", index: "itemdescr", width: 300, label: "材料名称", sortable: true, align: "left"},
				{name: "sendqty", index: "sendqty", width: 400, label: "发货数量", sortable: true, align: "left", sum: true}
            ]
		});
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_fhmx_main",{
			url:"${ctx}/admin/itemAppSend/goJqGrid_fhmx",
			postData: {iaNo: '${iaNo}'},
			height:150,
			rowNum: 10000,
			colModel : [
			    {name: "no", index: "no", width: 200, label: "领料发货流水号", sortable: true, align: "left", count: true},
				{name: "whcodedescr", index: "whcodedescr", width: 200, label: "发货仓库", sortable: true, align: "left"},
				{name: "date", index: "date", width: 200, label: "日期", sortable: true, align: "left", formatter: formatTime},
				{name: "remarks", index: "remarks", width: 310, label: "备注", sortable: true, align: "left"}
            ],
            onSelectRow : function(id) {
            	var row = selectDataTableRow("dataTable_fhmx_main");
            	$("#dataTable_fhmx_mx").jqGrid('setGridParam',{url : "${ctx}/admin/itemAppSendDetail/goJqGrid_fhmx?no="+row.no});
            	$("#dataTable_fhmx_mx").jqGrid().trigger('reloadGrid');
            }
		});
});
</script>
</head>
<body onunload="closeWin()">
<div class="body-box-list">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="pageContent">
		<table id="dataTable_fhmx_main"></table>
	</div>
	<div class="pageContent" style="margin-top: 10px;">
		<table id="dataTable_fhmx_mx"></table>
	</div>
</div>
</body>
