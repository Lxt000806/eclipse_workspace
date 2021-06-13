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
	<title>特定发包——明细</title>
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
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTableDetail",{
		height:220,
		rowNum:10000000,
		colModel :[
			{name: "baseitemdescr", index: "baseitemdescr", width: 230, label: "基础项目", sortable: true, align: "left", frozen: true},
			{name: "fixareapkdescr", index: "fixareapkdescr", width: 149, label: "装修区域", sortable: true, align: "left", frozen: true},
			{name: "qty", index: "qty", width: 82, label: "预算数量", sortable: true, align: "right", frozen: true},
			{name: "uomdescr", index: "uomdescr", width: 70, label: "单位", sortable: true, align: "left", frozen: true},
			{name: "sumunitprice", index: "sumunitprice", width: 90, label: "人工", sortable: true, align: "right", sum: true, frozen: true},
			{name: "summaterial", index: "summaterial", width: 90, label: "材料", sortable: true, align: "right", sum: true, frozen: true},
			{name: "sumlineamount", index: "sumlineamount", width: 90, label: "总价", sortable: true, align: "right", sum: true},
			{name: "sumunitpricectrl", index: "sumunitpricectrl", width: 90, label: "人工费", sortable: true, align: "right", sum: true},
			{name: "summaterialctrl", index: "summaterialctrl", width: 90, label: "材料费", sortable: true, align: "right", sum: true},
			{name: "sumlineamountctrl", index: "sumlineamountctrl", width: 90, label: "总价", sortable: true, align: "right", sum: true}
		],
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/jcfbkz/goDetailReport",
		postData:{no:"${customer.no}",code:"${customer.code}",m_umState:"${customer.m_umState }",workType1:"${customer.workType1 }"} ,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: "Bootstrap", 
		height:220,
		colModel : [
			{name: "code", index: "code", width: 231, label: "工种分类2", sortable: true, align: "left", frozen: true,hidden:true},
			{name: "worktype2descr", index: "worktype2descr", width: 231, label: "工种分类2", sortable: true, align: "left", frozen: true},
			{name: "sumunitprice", index: "sumunitprice", width: 90, label: "人工", sortable: true, align: "right", sum: true, frozen: true},
			{name: "summaterial", index: "summaterial", width: 90, label: "材料", sortable: true, align: "right", sum: true, frozen: true},
			{name: "sumlineamount", index: "sumlineamount", width: 90, label: "总价", sortable: true, align: "right", sum: true, frozen: true},
			{name: "sumunitpricectrl", index: "sumunitpricectrl", width: 90, label: "人工费", sortable: true, align: "right", sum: true, frozen: true},
			{name: "summaterialctrl", index: "summaterialctrl", width: 90, label: "材料费", sortable: true, align: "right", sum: true, frozen: true},
			{name: "sumlineamountctrl", index: "sumlineamountctrl", width: 90, label: "总价", sortable: true, align: "right", sum: true}
		],
		onSelectRow : function(id) {
        	var row = selectDataTableRow("dataTable");
           	$("#dataTableDetail").jqGrid("setGridParam",{url : "${ctx}/admin/jcfbkz/goDetailReportMX?m_umState=${customer.m_umState }&code=${customer.code}&no=${customer.no}&workType1=${customer.workType1}&workType2="+row.code});
           	$("#dataTableDetail").jqGrid().trigger("reloadGrid");
        },
        gridComplete:function(){
			$("#dataTableDetail").footerData("set", { "sumunitprice":myRound($("#dataTableDetail").getCol("sumunitprice", false, "sum") ,2)});   
			$("#dataTableDetail").footerData("set", { "summaterial":myRound($("#dataTableDetail").getCol("summaterial", false, "sum") ,2) });   
			$("#dataTableDetail").footerData("set", { "sumlineamount":myRound($("#dataTableDetail").getCol("sumlineamount", false, "sum") ,2) });   
			$("#dataTableDetail").footerData("set", { "sumunitpricectrl":myRound($("#dataTableDetail").getCol("sumunitpricectrl", false, "sum") ,2) });   
			$("#dataTableDetail").footerData("set", { "summaterialctrl":myRound($("#dataTableDetail").getCol("summaterialctrl", false, "sum") ,2) });   
			$("#dataTableDetail").footerData("set", { "sumlineamountctrl":myRound($("#dataTableDetail").getCol("sumlineamountctrl", false, "sum") ,2) }); 
		},
	});
	$("#dataTable").jqGrid("setGroupHeaders", {
	  	useColSpanStyle: true, 
		groupHeaders:[{startColumnName: "sumunitprice", numberOfColumns: 3, titleText: "预算金额"},
						{startColumnName: "sumunitpricectrl", numberOfColumns: 3, titleText: "成本控制金额"},
		],
	});
	$("#dataTableDetail").jqGrid("setGroupHeaders", {
	  	useColSpanStyle: true, 
		groupHeaders:[{startColumnName: "sumunitprice", numberOfColumns: 3, titleText: "预算金额"},
						{startColumnName: "sumunitpricectrl", numberOfColumns: 3, titleText: "成本控制金额"},
		],
	});
});
function query_jczj(){
	$("#dataTableJczj").jqGrid("setGridParam",{url : "${ctx}/admin/jcfbkz/goJczjJqgrid",postData:{code:$("#custCode").val()}}).trigger("reloadGrid");
}
</script>
</head>
	<body >
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form" hidden="true">
				<form action="" method="post" id="page_form" class="form-search" hidden="true">
					<ul class="ul-form">
					</ul>	
				</form>
			</div>
			<!--query-form-->
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
				</div> 
				<div id="content-list">
					<table id="dataTableDetail"></table>
				</div>
			</div>
		</div>
	</body>	
</html>
