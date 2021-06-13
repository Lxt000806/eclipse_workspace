<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户结算分析—查看</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/splCheckAnalysis/goJqGridDetail",
			styleUI: "Bootstrap", 
			height: 350,
			rowNum: 10000,
			postData: {
				confirmDateFrom: $("#confirmDateFrom").val(),
				confirmDateTo: $("#confirmDateTo").val(),
				splCode: "${data.splCode}",
				isSetItem: "${data.isSetItem}",
				isIncludePurchIn: "${data.isIncludePurchIn}",
				isService: "${data.isService}"
			},
			colModel : [
				{name: "puno", index: "puno", width: 100, label: "采购单号", sortable: true, align: "left"},
				{name: "iano", index: "iano", width: 100, label: "领料单号", sortable: true, align: "left"},
				{name: "appczydescr", index: "appczydescr", width: 100, label: "开单人", sortable: true, align: "left"},
				{name: "address", index: "address", width: 100, label: "楼盘", sortable: true, align: "left"},
				{name: "iscupboard", index: "iscupboard", width: 70, label: "是否橱柜", sortable: true, align: "left"},
				{name: "issetitemdescr", index: "issetitemdescr", width: 70, label: "是否套餐", sortable: true, align: "left"},
				{name: "sumamount", index: "sumamount", width: 100, label: "结算额", sortable: true, align: "right", sum: true},
				{name: "sumsaleamount", index: "sumsaleamount", width: 100, label: "销售额", sortable: true, align: "right", sum: true}
		    ],
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/splCheckAnalysis/doExcelForView";
		doExcelAll(url);
	}
	</script>
</head>
<body>
	<div class="body-box-list">
	 <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
		<input type="hidden" name="jsonString" value="" />
		<input type="hidden" name="exportData" id="exportData">
		<input type="hidden" id="confirmDateFrom" name="confirmDateFrom" class="i-date" style="width:160px;" 
				value="<fmt:formatDate value='${data.confirmDateFrom}' pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="confirmDateTo" name="confirmDateTo" class="i-date" style="width:160px;" 
				value="<fmt:formatDate value='${data.confirmDateTo}' pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="splCode"  name="splCode" value="${data.splCode }"/>
		<input type="hidden" id="isIncludePurchIn" name="isIncludePurchIn" value="${data.isIncludePurchIn }"/>
		<input type="hidden" id="isSetItem" name="isSetItem" value="${data.isSetItem }"/>
		<input type="hidden" id="isService" name="isService" value="${data.isService}"/>
	</form>
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
		    		<button type="button" class="btn btn-system " onclick="doExcel()" >
						<span>导出excel</span>
					</button>
    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div  class="container-fluid" >  
				<table id="dataTable"></table>
			</div>	
		</div>
	</div>
</body>
</html>


