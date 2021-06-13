<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>入库明细</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("dataTableMx",{
				height:280,
				styleUI:"Bootstrap",
				rowNum:10000,
				colModel:[
					{name: "iano", index: "iano", width: 80, label: "领料单号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
					{name: "itemcode", index: "itemcode", width: 84, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
					{name: "fixareadescr", index: "fixareadescr", width: 107, label: "区域", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 55, label: "数量", sortable: true, align: "right",},
					{name: "cuttypedescr", index: "cuttypedescr", width: 55, label: "切割方式", sortable: false, align: "left"},
					{name: "cutfee", index: "cutfee", width: 72, label: "加工单价", sortable: true, align: "right"},
					{name: "total", index: "total", width: 60, label: "总价", sortable: true, align: "right", sum: true},		
				]
			});
			Global.JqGrid.initJqGrid("dataTable",{
				height:120,
				styleUI:"Bootstrap",
				url:"${ctx}/admin/cutCheckOut/goJqGridCheckIn",
				postData:{
					no:"${cutCheckOut.no}"
				},
				rowNum:10000,
				colModel:[
					{name: "No", index: "No", width: 130, label: "入库批次号", sortable: true, align: "left"},
					{name: "Date", index: "Date", width: 140, label: "日期", sortable: true, align: "left", formatter: formatTime},
					{name: "Remarks", index: "Remarks", width: 200, label: "备注", sortable: true, align: "left"},
				],
				gridComplete:function(){
					var ret = selectDataTableRow("dataTable");
					if(ret){
						$("#dataTableMx").jqGrid("setGridParam",{
							url:"${ctx}/admin/cutCheckOut/goJqGridCheckInDtl",
							postData:{no:ret.No},
							page:1,
							sortname:""
						}).trigger("reloadGrid");
					}
				},
				beforeSelectRow:function(id){
					var ret = $("#dataTable").jqGrid("getRowData", id);
					if(ret){
						$("#dataTableMx").jqGrid("setGridParam",{
							url:"${ctx}/admin/cutCheckOut/goJqGridCheckInDtl",
							postData:{no:ret.No},
							page:1,
							sortname:""
						}).trigger("reloadGrid");
					}
				}
			});
		});
		</script>
	</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>	
					</div>
				</div>
			</div>
			<table id="dataTable"></table>
			<table id="dataTableMx"></table>
		</div>
	</body>
</html>
