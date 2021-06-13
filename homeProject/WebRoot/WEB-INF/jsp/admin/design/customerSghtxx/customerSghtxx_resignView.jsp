<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";

%>
<!DOCTYPE html>
<html>
<head>
	<title>合同施工管理——重签查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_activity.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/customerSghtxx/doResignExcel";
	doExcelAll(url);
}
$(function(){
	var gridOption ={
		url:"${ctx}/admin/customerSghtxx/getResignDetail",
		height:$(document).height()-$("#content-list").offset().top-82,
		colModel : [
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "newdocumentno", index: "documentno", width: 110, label: "重签后档案编号", sortable: true, align: "left"},
			{name: "olddocumentno", index: "documentno", width: 110, label: "重签前档案编号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
			{name: "signdatenow", index: "signdatenow", width: 100, label: "当前签订时间", sortable: true, align: "left", formatter: formatDate},
			{name: "custtypedescr", index: "custtypedescr", width: 100, label: "原客户类型", sortable: true, align: "left"},
			{name: "signdate", index: "signdate", width: 100, label: "原签订时间", sortable: true, align: "left", formatter: formatDate},
			{name: "contracttypedescr", index: "contracttypedescr", width: 100, label: "原合同类型", sortable: true, align: "left"},
			{name: "contractfee", index: "contractfee", width: 100, label: "原合同总金额", sortable: true, align: "right"},
			{name: "designfee", index: "designfee", width: 100, label: "原合同设计费", sortable: true, align: "right"},
			{name: "newsigndate", index: "newsigndate", width: 130, label: "重签时间", sortable: true, align: "left", formatter: formatTime},
			{name: "remarks", index: "remarks", width: 220, label: "重签说明", sortable: true, align: "left"}
		],
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>

						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">	
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;"/>
							</li>
							<li>
								<label>当前签订时间从</label>
									<input type="text" id="signDateFrom" name="signDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
								</li>
								<li>
									<label>至</label>
									<input type="text" id="signDateTo" name="signDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
								</li>
							<li class="search-group-shrink" >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>	
				</form>
			</div>
		</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
		
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
