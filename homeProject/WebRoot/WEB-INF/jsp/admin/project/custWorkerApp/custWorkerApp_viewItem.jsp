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
	<title>查看材料到货情况</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/CustWorkerApp/goItemArrJqGrid",
		postData:{custCode:"${custCode }",workType12:"${workType12 }"},
		height:200,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemtype3descr", index: "itemtype3descr", width: 75, label: "材料类型3", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 80, label: "申请类型", sortable: true, align: "left"},
			{name: "lastappday", index: "lastappday", width: 120, label: "最后申请时间距今天小于", sortable: true, align: "right"},
			{name: "date", index: "date", width: 100, label: "申请时间", sortable: true, align: "left",formatter: formatDate},
			{name: "qty", index: "qty", width: 100, label: "申请数量", sortable: true, align: "left",},
			{name: "sendqty", index: "sendqty", width: 100, label: "发货数量", sortable: true, align: "left",},
			{name: "ismanzu", index: "ismanzu", width: 100, label: "是否满足", sortable: true, align: "left",},
			{name: "pk", index: "pk", width: 100, label: "pk", sortable: true, align: "left",hidden:true},
		],
		onSelectRow : function(id) {
         	var row = selectDataTableRow("dataTable");
         	$("#dataTableDetail").jqGrid('setGridParam',{url : "${ctx}/admin/CustWorkerApp/goItemArrDetailJqGrid?pk="+row.pk+"&custCode="+"${custCode }"});
         	$("#dataTableDetail").jqGrid().trigger('reloadGrid');
  	   },
		
	});
	
	Global.JqGrid.initJqGrid("dataTableDetail",{
		height:300,
		rowNum: 10000,
		colModel : [
				{name: "no", index: "no", width: 80, label: "领料单号", sortable: true, align: "left",},
				{name: "statusdescr", index: "statusdescrno", width: 80, label: "状态", sortable: true, align: "left",},
				{name: "descr", index: "descr", width: 120, label: "材料名称", sortable: true, align: "left"},
 				{name: "item1descr", index: "item1descr", width: 80, label: "材料类型1", sortable: true, align: "left", },
				{name: "item2descr", index: "item2descr", width: 80, label: "材料类型2", sortable: true, align: "left", },
				{name: "item3descr", index: "item3descr", width: 80, label: "材料类型3", sortable: true, align: "left", },
				{name: "date", index: "date", width: 100, label: "申请时间", sortable: true, align: "left",formatter: formatDate},
				{name: "datefrom", index: "datefrom", width: 100, label: "申请时间距离今天", sortable: true, align: "right",},
				{name: "qty", index: "qty", width: 100, label: "申请数量", sortable: true, align: "right",},
				{name: "sendqty", index: "sendqty", width: 100, label: "发货数量", sortable: true, align: "right",},
           ]
	});
});


</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
    			<div class="panel-body" >
      				<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
				<div class="query-form" hidden="false">
			  		<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<input type="hidden" id="custCode" name="custCode" value="${custCode }" />
						<input type="hidden" id="workType12" name="workType12" value="${workType12 }" />
					</form>
				</div>	
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<table id= "dataTableDetail"></table>
			</div>
			</div>
		</div>
	</body>	
</html>
