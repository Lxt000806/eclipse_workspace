<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>查看衣柜平方数</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script type="text/javascript">
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/laborFee/goIntQtyJqGrid",
			postData:{no:"${laborFee.no}",nos:"${laborFee.nos}",dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val(),workerCode:"${laborFee.workerCode}"},
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			sortable: true,
			rowNum:10000000,
			colModel : [
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 180, label: "楼盘", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 81, label: "客户类型", sortable: true, align: "left"},
				{name: "itemtype3descr", index: "itemtype3descr", width: 150, label: "材料类型3", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 80, label: "发货数量", sortable: true, align: "right"},
				{name: "appqty", index: "appqty", width: 80, label: "领料数量", sortable: true, align: "right"},
				{name: "intinstallfee", index: "intinstallfee", width: 80, label: "安装费单价", sortable: true, align: "right"},
			],
		});
	});
	</script>
</head>
<body>
	<div class="content-form">
		<div class="panel panel-system" >
    		<div class="panel-body" >
     			<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " onclick="doExcelNow('衣柜平方数')">
						<span>导出excel</span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="body-box-list">
		<div class="pageContent">
		  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;"value="<fmt:formatDate value='${laborFee.dateFrom}' pattern='yyyy-MM-dd'/>"  />
			<input type="hidden" id="dateTo" name="dateTo" class="i-date" style="width:160px;" value="<fmt:formatDate value='${laborFee.dateTo}' pattern='yyyy-MM-dd'/>"  />
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</form>
		</div> 
	</div>
</body>
</html>
