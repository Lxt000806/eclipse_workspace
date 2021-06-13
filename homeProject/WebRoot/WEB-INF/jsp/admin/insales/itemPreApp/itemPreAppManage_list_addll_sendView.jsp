<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>领料预申请 新增领料 发货明细页面</title>
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
					{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left", hidden: true},
					{name: "itemcode", index: "itemcode", width: 100, label: "材料编号", sortable: true, align: "left"},
					{name: "itdescr", index: "itdescr", width: 220, label: "材料名称", sortable: true, align: "left"},
					{name: "sendqty", index: "sendqty", width: 109, label: "发货数量", sortable: true, align: "right", sum: true},
					{name: "color", index: "color", width: 84, label: "色号", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 115, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "lastupdatedby", index: "lastupdatedby", width: 85, label: "更新人员", sortable: true, align: "left", hidden: true},
					{name: "expired", index: "expired", width: 84, label: "是否过期", sortable: true, align: "left", hidden: true},
					{name: "actionlog", index: "actionlog", width: 84, label: "操作", sortable: true, align: "left", hidden: true}
				]
			});
			Global.JqGrid.initJqGrid("dataTable",{
				height:200,
				styleUI:"Bootstrap",
				url:"${ctx}/admin/itemPreApp/goJqGridSend",
				postData:{
					no:"${data.no}"
				},
				rowNum:10000,
				colModel:[
					{name: "No", index: "No", width: 130, label: "领料发货流水号", sortable: true, align: "left"},
					{name: "whcodedescr", index: "whcodedescr", width: 160, label: "发货仓库", sortable: true, align: "left"},
					{name: "Date", index: "Date", width: 140, label: "日期", sortable: true, align: "left", formatter: formatTime},
					{name: "SendBatchNo", index: "SendBatchNo", width: 120, label: "配送单号", sortable: true, align: "left"},
					{name: "delivtypedescr", index: "delivtypedescr", width: 120, label: "配送方式", sortable: true, align: "left"},
					{name: "drivername", index: "drivername", width: 90, label: "送货司机", sortable: true, align: "left"},
					{name: "Remarks", index: "Remarks", width: 150, label: "备注", sortable: true, align: "left"},
					{name: "LastUpdate", index: "LastUpdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 90, label: "更新人员", sortable: true, align: "left", hidden: true},
					{name: "Expired", index: "Expired", width: 90, label: "是否过期", sortable: true, align: "left", hidden: true},
					{name: "ActionLog", index: "ActionLog", width: 50, label: "操作", sortable: true, align: "left", hidden: true}
				],
				gridComplete:function(){
					var ret = selectDataTableRow("dataTable");
					if(ret){
						$("#dataTableMx").jqGrid("setGridParam",{
							url:"${ctx}/admin/itemPreApp/goJqGridSendMx",
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
							url:"${ctx}/admin/itemPreApp/goJqGridSendMx",
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
