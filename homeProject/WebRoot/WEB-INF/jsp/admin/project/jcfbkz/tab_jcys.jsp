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
	<title>基础预算</title>
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
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/jcfbkz/goJcysJqGrid",
		postData:{code:$("#code").val()} ,
		height:400,
		styleUI: "Bootstrap",
		colModel : [
			{name: "projectctrladj", index: "projectctrladj", width: 229, label: "projectctrladj", sortable: true, align: "left",hidden:true},
			{name: "ctrladjremark", index: "ctrladjremark", width: 229, label: "ctrladjremark", sortable: true, align: "left",hidden:true},
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
			var ret = $("#dataTable").jqGrid("getRowData",id);
			$("#jcysWorkType1").val(ret.worktype1);
		},
		gridComplete:function(){
			$("#dataTable").setSelection("1");
			var rowData = $("#dataTable").jqGrid("getRowData","1");
			if(rowData){
				$("#workType1").val(rowData.worktype1);
				$("#jcysWorkType1").val(rowData.worktype1);
				$("#projectCtrlAdj").val(rowData.projectctrladj);
				$("#ctrlAdjRemark").val(rowData.ctrladjremark);
			}
			$("#dataTable").footerData("set", { "sumunitprice":myRound($("#dataTable").getCol("sumunitprice", false, "sum") ,2)});   
			$("#dataTable").footerData("set", { "summaterial":myRound($("#dataTable").getCol("summaterial", false, "sum") ,2) });   
			$("#dataTable").footerData("set", { "sumlineamount":myRound($("#dataTable").getCol("sumlineamount", false, "sum") ,2) });   
			$("#dataTable").footerData("set", { "sumunitpricectrl":myRound($("#dataTable").getCol("sumunitpricectrl", false, "sum") ,2) });   
			$("#dataTable").footerData("set", { "summaterialctrl":myRound($("#dataTable").getCol("summaterialctrl", false, "sum") ,2) });   
			$("#dataTable").footerData("set", { "sumlineamountctrl":myRound($("#dataTable").getCol("sumlineamountctrl", false, "sum") ,2) });   
		},
	});
	
	 $("#dataTable").jqGrid("setGroupHeaders", {
	  	useColSpanStyle: true, 
		groupHeaders:[{startColumnName: "sumunitprice", numberOfColumns: 3, titleText: "预算金额"},
						{startColumnName: "sumunitpricectrl", numberOfColumns: 3, titleText: "成本控制金额"},
		],
	});
});
function query_jcys(){
	$("#dataTable").jqGrid('setGridParam',{url : "${ctx}/admin/jcfbkz/goJcysJqGrid",postData:{code:$("#code").val()}}).trigger("reloadGrid");
}

</script>
</head>
	<body>
		<div id="content-list" style="height:30%">
			<table id= "dataTable"></table>
		</div>
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="jcysWorkType1"  name="jcysWorkType1"/>
				<ul class="ul-form">
					<li>
						<label>发包补贴</label>
						<input type="text" id="projectCtrlAdj" name="projectCtrlAdj" style="width:160px;" value="${customer.prjRegionCode }" 
							readonly="true"/>
					</li>
					<li>
						<label class="control-textarea" style="float:left;;clear:top;position:	ablosute;margin-top:20px" >补贴说明</label>
						<textarea id="ctrlAdjRemark" name="ctrlAdjRemark" rows="4" readonly="true">${customer.ctrlAdjRemark }</textarea>
					</li>
				</ul>
			</form>
	</body>	
</html>
