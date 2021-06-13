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
	<title>基础增减</title>
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
	Global.JqGrid.initJqGrid("dataTableJczjDetail",{
		height:220,
		rowNum:10000000,
		colModel :[
			{name: "worktype1", index: "worktype1", width: 229, label: "工种分类1", sortable: true, align: "left",hidden:true},
			{name: "worktype1descr", index: "worktype1descr", width: 229, label: "工种分类1", sortable: true, align: "left"},
			{name: "sumunitprice", index: "sumunitprice", width: 88, label: "人工", sortable: true, align: "right", sum: true},
			{name: "summaterial", index: "summaterial", width: 88, label: "材料", sortable: true, align: "right", sum: true},
			{name: "sumlineamount", index: "sumlineamount", width: 88, label: "总价", sortable: true, align: "right", sum: true},
			{name: "sumunitpricectrl", index: "sumunitpricectrl", width: 88, label: "人工费", sortable: true, align: "right", sum: true},
			{name: "summaterialctrl", index: "summaterialctrl", width: 88, label: "材料费", sortable: true, align: "right", sum: true},
			{name: "sumlineamountctrl", index: "sumlineamountctrl", width: 88, label: "总价", sortable: true, align: "right", sum: true}
		],
		onCellSelect: function(id,iCol,cellParam,e){
			var ret = $("#dataTableJczjDetail").jqGrid("getRowData",id);
			$("#jczjWorkType1").val(ret.worktype1);
		},
		gridComplete:function(){
			$("#dataTableJczfb").setSelection("1");
			var rowData = $("#dataTableJczjDetail").jqGrid("getRowData","1");
			if(rowData){
				$("#jczjWorkType1").val(rowData.worktype1);
			}
			$("#dataTableJczjDetail").footerData("set", { "sumunitprice":myRound($("#dataTableJczjDetail").getCol("sumunitprice", false, "sum") ,2)});   
			$("#dataTableJczjDetail").footerData("set", { "summaterial":myRound($("#dataTableJczjDetail").getCol("summaterial", false, "sum") ,2) });   
			$("#dataTableJczjDetail").footerData("set", { "sumlineamount":myRound($("#dataTableJczjDetail").getCol("sumlineamount", false, "sum") ,2) });   
			$("#dataTableJczjDetail").footerData("set", { "sumunitpricectrl":myRound($("#dataTableJczjDetail").getCol("sumunitpricectrl", false, "sum") ,2) });   
			$("#dataTableJczjDetail").footerData("set", { "summaterialctrl":myRound($("#dataTableJczjDetail").getCol("summaterialctrl", false, "sum") ,2) });   
			$("#dataTableJczjDetail").footerData("set", { "sumlineamountctrl":myRound($("#dataTableJczjDetail").getCol("sumlineamountctrl", false, "sum") ,2) });  
		},
	});
	$("#dataTableJczjDetail").jqGrid("setGroupHeaders", {
	  	useColSpanStyle: true, 
		groupHeaders:[{startColumnName: "sumunitprice", numberOfColumns: 3, titleText: "预算金额"},
						{startColumnName: "sumunitpricectrl", numberOfColumns: 3, titleText: "成本控制金额"},
		],
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTableJczj",{
		url:"${ctx}/admin/jcfbkz/goJczjJqgrid",
		postData:{code:$("#code").val()} ,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: "Bootstrap", 
		height:220,
		colModel : [
			{name: "no", index: "no", width: 90, label: "增减单号", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 69, label: "客户编号", sortable: true, align: "left"},
			{name: "customerdescr", index: "customerdescr", width: 70, label: "客户名称", sortable: true, align: "left"},
			{name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 89, label: "基装增减状态", sortable: true, align: "left"},
			{name: "date", index: "date", width: 100, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
			{name: "befamount", index: "befamount", width: 76, label: "优惠前金额", sortable: true, align: "right"},
			{name: "discamount", index: "discamount", width: 65, label: "优惠金额", sortable: true, align: "right"},
			{name: "amount", index: "amount", width: 65, label: "实际总价", sortable: true, align: "right"},
			{name: "remarks", index: "remarks", width: 140, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 93, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 62, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 62, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 62, label: "操作", sortable: true, align: "left"}
		],
		onSelectRow : function(id) {
        	var row = selectDataTableRow("dataTableJczj");
        	var ret = $("#dataTableJczj").jqGrid("getRowData",id);
			$("#no").val(ret.no);
           	$("#dataTableJczjDetail").jqGrid("setGridParam",{url : "${ctx}/admin/jcfbkz/getJczjDetail?itemChgNo="+row.no});
           	$("#dataTableJczjDetail").jqGrid().trigger("reloadGrid");
        },
        gridComplete:function(){
			$("#dataTableJczj").setSelection("1");
			var rowData = $("#dataTableJczj").jqGrid("getRowData","1");
			if(rowData){
				$("#no").val(rowData.no);
			}
		},
	});
});
function query_jczj(){
	$("#dataTableJczj").jqGrid("setGridParam",{url : "${ctx}/admin/jcfbkz/goJczjJqgrid",postData:{code:$("#code").val()}}).trigger("reloadGrid");
}
</script>
</head>
	<body >
		<div class="body-box-list">
			<div class="query-form" hidden="true" >
				<form action="" method="post" id="page_form" class="form-search">
					<input type="text" id="jczjWorkType1"  name="jczjWorkType1"/>
					<input type="text" id="no"  name="no"/>
					<ul class="ul-form">
					</ul>	
				</form>
			</div>
			<!--query-form-->
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTableJczj"></table>
				</div> 
				<div id="content-list">
					<table id="dataTableJczjDetail"></table>
				</div>
			</div>
		</div>
	</body>	
</html>
