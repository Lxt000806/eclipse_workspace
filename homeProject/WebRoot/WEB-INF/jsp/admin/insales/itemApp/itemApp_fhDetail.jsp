<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>领料管理发货明细页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
		
		<script type="text/javascript">
		function doExcel(){
			doExcelAll("${ctx}/admin/itemApp/doExcelFhDetail?fhNo=${data.fhNo}", "fhDetailDataTable", "page_form"); 
		}
		/**初始化表格*/
		$(function(){
	        //初始化表格
			Global.JqGrid.initJqGrid("fhDetailDataTable",{
				height:300,
				rowNum: 10000,
				url:"${ctx}/admin/itemApp/goJqGridFhDetail",
				postData:{
					fhNo:"${data.fhNo}"
				},
				colModel : [
					{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden: true},
					{name: "itemcode", index: "itemcode", width: 117, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 205, label: "材料名称", sortable: true, align: "left"},
					{name: "sendqty", index: "sendqty", width: 90, label: "数量", sortable: true, align: "right"},
					{name: "uomdescr", index: "uomdescr", width: 68, label: "单位", sortable: true, align: "left"},
					{name: "fixareadescr", index: "fixareadescr", width: 130, label: "装修区域", sortable: true, align: "left"},
					{name: "color", index: "color", width: 72, label: "色号", sortable: true, align: "left", sum: true},
					{name: "itemtype1descr", index: "itemtype1descr", width: 111, label: "材料类型1", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 276, label: "备注", sortable: true, align: "left"}
	            ]
			});
		});
		</script>
	</head>
	    
	<body>
		<div class="body-box-list" style="overflow-x:hidden">
			<div class="body-box-form">
				<div class="panel panel-system">
				    <div class="panel-body">
				    	<div class="btn-group-xs">
							<button id="excelBut" type="button" class="btn btn-system" onclick="doExcel()">输出到Excel</button>	
							<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>	
						</div>
					</div>
				</div>
			</div>
			<div class="query-form" style="display:none">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
				</form>
			</div>
			<div id="content-list">
				<table id="fhDetailDataTable"></table>
				<div id="fhDetailDataTablePager"></div>
			</div> 
		</div>
	</body>
</html>


