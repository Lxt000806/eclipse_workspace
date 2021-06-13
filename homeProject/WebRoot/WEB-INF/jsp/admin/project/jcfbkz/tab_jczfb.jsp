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
	<title>基础总发包</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_prjRegion.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTableJczfb",{
		url:"${ctx}/admin/jcfbkz/goJczfbJqGrid",
		postData:{code:$("#code").val()} ,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: "Bootstrap",
		colModel : [
			{name: "worktype1", index: "worktype1", width: 229, label: "工种分类1", sortable: true, align: "left",hidden:true},
			{name: "worktype1descr", index: "worktype1descr", width: 129, label: "工种分类1", sortable: true, align: "left"},
			{name: "sumunitprice", index: "sumunitprice", width: 108, label: "人工", sortable: true, align: "right", sum: true},
			{name: "summaterial", index: "summaterial", width: 108, label: "材料", sortable: true, align: "right", sum: true},
			{name: "sumlineamount", index: "sumlineamount", width: 108, label: "总价", sortable: true, align: "right", sum: true},
			{name: "sumunitpricectrl", index: "sumunitpricectrl", width: 108, label: "人工费", sortable: true, align: "right", sum: true},
			{name: "summaterialctrl", index: "summaterialctrl", width: 108, label: "材料费", sortable: true, align: "right", sum: true},
			{name: "sumlineamountctrl", index: "sumlineamountctrl", width: 108, label: "总费用", sortable: true, align: "right", sum: true}
		],
		onCellSelect: function(id,iCol,cellParam,e){
			var ret = $("#dataTableJczfb").jqGrid("getRowData",id);
			$("#jczfbWorkType1").val(ret.worktype1);
		},
		gridComplete:function(){
			$("#dataTableJczfb").setSelection("1");
			var rowData = $("#dataTableJczfb").jqGrid("getRowData","1");
			if(rowData){
				$("#jczfbWorkType1").val(rowData.worktype1);
			}
			$("#dataTableJczfb").footerData("set", { "sumunitprice":myRound($("#dataTableJczfb").getCol("sumunitprice", false, "sum") ,2)});   
			$("#dataTableJczfb").footerData("set", { "summaterial":myRound($("#dataTableJczfb").getCol("summaterial", false, "sum") ,2) });   
			$("#dataTableJczfb").footerData("set", { "sumlineamount":myRound($("#dataTableJczfb").getCol("sumlineamount", false, "sum") ,2) });   
			$("#dataTableJczfb").footerData("set", { "sumunitpricectrl":myRound($("#dataTableJczfb").getCol("sumunitpricectrl", false, "sum") ,2) });   
			$("#dataTableJczfb").footerData("set", { "summaterialctrl":myRound($("#dataTableJczfb").getCol("summaterialctrl", false, "sum") ,2) });   
			$("#dataTableJczfb").footerData("set", { "sumlineamountctrl":myRound($("#dataTableJczfb").getCol("sumlineamountctrl", false, "sum") ,2) });  
		},
	});
	 $("#dataTableJczfb").jqGrid("setGroupHeaders", {
	  	useColSpanStyle: true, 
		groupHeaders:[{startColumnName: "sumunitprice", numberOfColumns: 3, titleText: "预算金额"},
						{startColumnName: "sumunitpricectrl", numberOfColumns: 3, titleText: "成本控制金额"},
		],
	});
	
});
function query_jczfb(){
	$("#dataTableJczfb").jqGrid('setGridParam',{url : "${ctx}/admin/jcfbkz/goJczfbJqGrid",postData:{code:$("#code").val()}}).trigger("reloadGrid");
}
</script>
</head>
	<body>
		<div class="query-form" hidden="true">
				<form action="" method="post" id="page_form" class="form-search" >
					<input type="text" id="jczfbWorkType1"  name="jczfbWorkType1"/>
					<ul class="ul-form">
					</ul>	
				</form>
			</div>
		<div id="content-list" style="height:30%">
			<table id= "dataTableJczfb"></table>
		</div>
	</body>	
</html>
