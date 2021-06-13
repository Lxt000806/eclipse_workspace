<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
      //初始化表格
	var beginDate = $.trim($("#beginDate").val());
	var endDate = $.trim($("#endDate").val());
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/builderAnalysis/goJqGridtgphView",
		postData:{custCode:"${customer.code}",beginDate:beginDate,endDate:endDate,tgyy:"${customer.tgyy}"},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [		
				{name: "address", index: "address", width: 119, label: "楼盘地址", sortable: true, align: "left"},
				{name: "begindate", index: "begindate", width: 140, label: "开始日期", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 140, label: "结束日期", sortable: true, align: "left", formatter: formatDate},
				{name: "buildstatusdescr", index: "buildstatusdescr", width: 218, label: "停工原因", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 180, label: "备注", sortable: true, align: "left"},
	          ]
	});
});  

</script>
</head>
    
<body >
<div class="body-box-form" >
             <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<li hidden="true">
				<input type="text" id="beginDate" name="beginDate" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${customer.beginDate}' pattern='yyyy-MM-dd'/>" />
				<input type="text" id="endDate" name="endDate" class="i-date"  
				onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
				value="<fmt:formatDate value='${customer.endDate}' pattern='yyyy-MM-dd'/>" />
				</li>
			</form>
	<div class="clear_float"> </div>
	<!--query-form-->
	<div class="pageContent">
		<!--panelBar-->
		<div class="btn-panel" >
	    	<div class="btn-group-sm"  >
	    	<button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	    		<button type="button" class="btn btn-system " onclick="doExcelNow('工地停工排行明细')">导出excel</button>
	    	</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	
</body>
</html>


