<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>到货明细</title>
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
		Global.JqGrid.initJqGrid("dataTable_dhmx_mx",{
			height:210,
			rowNum: 10000,
			colModel : [
			    {name: "itcode", index: "itemcode", width: 200, label: "材料编号", sortable: true, align: "left", count: true},
				{name: "itemdescr", index: "itemdescr", width: 250, label: "材料名称", sortable: true, align: "left"},
				{name: "arrivqty", index: "arrivqty", width: 100, label: "到货数量", sortable: true, align: "left", sum: true},
				{name: "lastupdate", index: "lastupdate", width: 150, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "更新人员", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 100, label: "操作", sortable: true, align: "left"}
	        ]
		});
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_dhmx_main",{
			url:"${ctx}/admin/purchArr/goJqGrid_dhmx",
			postData: {puno: '${puno}'},
			height:150,
			rowNum: 10000,
			colModel : [
			    {name: "no", index: "no", width: 200, label: "采购到货流水号", sortable: true, align: "left", count: true},
				{name: "puno", index: "puno", width: 200, label: "采购单号", sortable: true, align: "left"},
				{name: "date", index: "date", width: 200, label: "日期", sortable: true, align: "left", formatter: formatTime},
				{name: "remarks", index: "remarks", width: 310, label: "备注", sortable: true, align: "left"}
            ],
            onSelectRow : function(id) {
            	var row = selectDataTableRow("dataTable_dhmx_main");
            	$("#dataTable_dhmx_mx").jqGrid('setGridParam',{url : "${ctx}/admin/purchArrDetail/goJqGrid_dhmx?pano="+row.no});
            	$("#dataTable_dhmx_mx").jqGrid().trigger('reloadGrid');
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
		<table id="dataTable_dhmx_main"></table>
	</div>
	<div class="pageContent" style="margin-top: 10px;">
		<table id="dataTable_dhmx_mx"></table>
	</div>
</div>
</body>
