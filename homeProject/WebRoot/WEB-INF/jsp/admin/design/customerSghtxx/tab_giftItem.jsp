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
	<title>合同施工管理——计算实物赠送</title>
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
		url:"${ctx}/admin/customerSghtxx/getCountDiscDetail",
		postData:{code:"${custCode }"},
		height:$(document).height()-$("#content-list").offset().top-100,
		colModel : [
			{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left", },
			{name: "itemdescr", index: "itemdescr", width: 220, label: "材料名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right",sum:true},
			{name: "cost", index: "cost", width: 70, label: "成本单价", sortable: true, align: "right"},
			{name: "costamount", index: "costamount", width: 70, label: "成本总价", sortable: true, align: "right",sum:true},
			{name: "projectcosttotal", index: "projectcosttotal", width: 120, label: "项目经理结算总价", sortable: true, align: "right", sum: true},
			{name: "unitprice", index: "unitprice", width: 70, label: "单价", sortable: true, align: "right", },
			{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right",sum:true },
			{name: "markup", index: "markup", width: 50, label: "折扣", sortable: true, align: "right", },
			{name: "tmplineamount", index: "tmplineamount", width: 70, label: "小计", sortable: true, align: "right",sum:true },
			{name: "processcost", index: "processcost", width: 70, label: "其他费用", sortable: true, align: "right", sum:true},
			{name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "right",sum:true },
		],
	};
	Global.JqGrid.initJqGrid("dataTableItem",gridOption);
});

</script>
</head>
	<body>
		<div class="clear_float"></div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTableItem"></table>
				<div id="dataTableItemPager"></div>
			</div> 
		</div>
	</body>	
</html>
