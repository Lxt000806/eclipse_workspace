<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/supplierItemApp/goIntReplenishDetailJqGrid",
			postData:{dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val()},
			height:350,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
				{name: "address", index: "address", width: 180, label: "楼盘", sortable: true, align: "left"},
                {name: "typedescr", index: "typedescr", width: 70, label: "补货类型", sortable: true, align: "left"},
				{name: "date", index: "date", width: 120, label: "补货时间", sortable: true, align: "left", formatter: formatTime},
				{name: "remarks", index: "remarks", width: 100, label: "补货详情", sortable: true, align: "left"},
                {name: "arrivedate", index: "arrivedate", width: 120, label: "实际到货时间", sortable: true, align: "left", formatter: formatTime},
                {name: "appno", index: "appno", width: 70, label: "领料单号", sortable: true, align: "left"},
				{name: "appdate", index: "appdate", width: 120, label: "领料下单时间", sortable: true, align: "left", formatter: formatTime},
                {name: "appstatus", index: "appstatus", width: 80, label: "领料单状态", sortable: true, align: "left"},
				{name: "senddate", index: "senddate", width: 120, label: "领料发货时间", sortable: true, align: "left", formatter: formatTime},
			], 
 		};
	   Global.JqGrid.initJqGrid("dataTable",gridOption);
});
</script>
<body>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
		</div>
	</div>
	<div class="query-form">
		<form action="" method="post" id="page_form" class="form-search">
			<input type="hidden" id="expired" name="expired" value="F" /> <input
				type="hidden" name="jsonString" value="" />
			<ul class="ul-form">
				<li><label>补货时间</label> <input type="text" id="dateFrom"
					name="dateFrom" class="i-date"
					onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
				</li>
				<li><label>至</label> <input type="text" id="dateTo"
					name="dateTo" class="i-date"
					onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
				</li>
				<li><label>楼盘地址</label> <input type="text" id="address"
					name="address" />
				</li>
				<li>
				    <label>状态</label>
				    <house:xtdmMulit id="status" dictCode="IntRepStatus" unShowValue="1"></house:xtdmMulit>
				</li>	
				<li id="loadMore">
					<button type="button" class="btn btn-sm btn-system "
						onclick="goto_query();">查询</button>
				</li>
			</ul>
		</form>
	</div>
	<div class="clear_float"></div>
		<!--query-form-->
			<div id="content-list" >
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
	</div>
</body>
</html>



