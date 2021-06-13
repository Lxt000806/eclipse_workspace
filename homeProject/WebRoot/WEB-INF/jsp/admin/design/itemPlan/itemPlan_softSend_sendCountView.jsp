<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>领料预申请 已发货次数 查看页面</title>
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
				height:200,
				styleUI:"Bootstrap",
				rowNum:10000,
				colModel:[
					{name: "itdescr", index: "itdescr", width: 220, label: "材料名称", sortable: true, align: "left"},
					{name: "sendqty", index: "sendqty", width: 109, label: "发货数量", sortable: true, align: "right", sum: true}
				]
			});
			Global.JqGrid.initJqGrid("dataTable",{
				height:200,
				styleUI:"Bootstrap",
				url:"${ctx}/admin/itemPreApp/goJqGridItemPlanSoftSend",
				postData:{
					custCode:"${itemPreMeasure.custCode}",
					itemType1:"${itemPreMeasure.itemType1}",
				},
				rowNum:10000,
				colModel:[
					{name: "no", index: "no", width: 130, label: "发货单号", sortable: true, align: "left"},
					{name: "iano", index: "iano", width: 120, label: "领料单号", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 90, label: "材料类型1", sortable: true, align: "left"},
					{name: "itemtype2descr", index: "itemtype2descr", width: 150, label: "材料类型2", sortable: true, align: "left"},
					{name: "date", index: "date", width: 140, label: "发货日期", sortable: true, align: "left", formatter: formatTime}
				],
				gridComplete:function(){
					var ret = selectDataTableRow("dataTable");
					if(ret){
						$("#dataTableMx").jqGrid("setGridParam",{
							url:"${ctx}/admin/itemPreApp/goJqGridSendMx",
							postData:{no:ret.no},
							page:1,
							sortname:""
						}).trigger("reloadGrid");
					}
				},
				beforeSelectRow:function(id){
					var ret = $("#dataTable").jqGrid("getRowData", id);
					if(ret){
						$("#dataTableMx").jqGrid("setGridParam",{
							url:"${ctx}/admin/itemPreApp/goJqGridSendMx",
							postData:{no:ret.no},
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
