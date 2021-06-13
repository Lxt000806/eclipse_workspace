<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript" defer>
$(function() {

	var gridOption = {
		url:"${ctx}/admin/salesInvoice/goSalesInvoiceDetailJqGrid",
		postData:{sino:"${sino}"},
		height : $(document).height()-$("#content-list").offset().top - 40,
		multiselect : true,
		rowNum : 10000000,
		colModel : [
			{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left", hidden:true},
    		{name : "sino",index : "sino",width : 80,label:"编号",sortable : true,align : "left"},
    		{name : "itcode",index : "itcode",width : 80,label:"材料编号",sortable : true,align : "left"},
    		{name : "itemdescr",index : "itemdescr",width : 200,label:"材料名称",sortable : true,align : "left"},
			{name : "qty",index : "qty",width : 80,label:"销售数量",sortable : true,align : "right"},
			{name : "bcost",index : "bcost",width : 80,label:"移动平均成本",sortable : true,align : "left",hidden:true},
			{name : "unitprice",index : "unitprice",width : 60,label:"单价",sortable : true,align : "right"},
			{name : "beflineamount",index : "beflineamount",width : 90,label:"折扣前金额",sortable : true,align : "right"},
			{name : "markup",index : "markup",width : 80,label:"折扣",sortable : true,align : "right"},
			{name : "lineamount",index : "lineamount",width : 80,label:"总价",sortable : true,align : "right"},
    		{name : "remarks",index : "remarks",width : 220,label:"备注",sortable : true,align : "left"},
		],
	};

	Global.JqGrid.initJqGrid("dataTable",gridOption);

});
//全选
function selectAll(){
	Global.JqGrid.jqGridSelectAll("dataTable",true);
}

//不选
function selectNone(){
	Global.JqGrid.jqGridSelectAll("dataTable",false);
}
function doSave() {
	selectRows = [];
	var itCodeArr = "${itCodes}".split(",");
	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	for (var j = 0; j < ids.length; j++) {
		var row = $("#dataTable").jqGrid("getRowData", ids[j]);
		for (var i = 0; i < itCodeArr.length; i++) {
			if(itCodeArr[i] == $.trim(row.itcode)){
				art.dialog({
					content: "该产品已经存在,请勿重复插入",
					width: 200,
				});
				return;
			}
		}
		row.lastupdatedby = $("#lastupdatedby").val();
		selectRows.push(row);
	}
	Global.Dialog.returnData = selectRows;
	closeWin();
}
</script>
<script src="${resourceRoot}/pub/component_itemBatchHeader.js?v=${v}" type="text/javascript" defer></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
		    		<button type="button" class="btn btn-system" id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="selectAll" onclick="selectAll()">
						<span>全选</span>
					</button>
					<button type="button" class="btn btn-system" id="selectNone" onclick="selectNone()">
						<span>不选</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>退出</span>
					</button>
				</div>
			</div>
		</div>
		<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<!-- <div id="dataTablePager"></div> -->
			</div>
		</div>
	</div>
</body>	

</html>
