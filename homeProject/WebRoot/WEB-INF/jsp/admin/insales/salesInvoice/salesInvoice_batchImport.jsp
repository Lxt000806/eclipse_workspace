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
	$("#ibdno").openComponent_itemBatchHeader({
		callBack:getJqGrid,
		showValue:"${map.no}",
		showLabel:"${map.remarks}",
		condition:{
			crtCzy:"${czybhCode}",
			ItemType1:"${itemType1}",
			disabledEle:"itemType1",
		}
	});

	var gridOption = {
		url:"${ctx}/admin/salesInvoice/goItemBatchJqGrid",
		postData:{ibdno:"${map.no}"},
		height : $(document).height()-$("#content-list").offset().top - 64,
		rowNum : 10000000,
		colModel : [
			{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left", hidden:true},
    		{name : "itcode",index : "itcode",width : 80,label:"材料编号",sortable : true,align : "left"},
    		{name : "itcodedescr",index : "itcodedescr",width : 200,label:"材料名称",sortable : true,align : "left",count:true},
			{name : "qty",index : "qty",width : 60,label:"数量",sortable : true,align : "right",sum:true},
			{name : "uom",index : "uom",width : 60,label:"单位code",sortable : true,align : "left",hidden:true},
			{name : "uomdescr",index : "uomdescr",width : 60,label:"单位",sortable : true,align : "left"},
			{name : "price",index : "price",width : 60,label:"单价",sortable : true,align : "right"},
			{name : "marketprice",index : "marketprice",width : 60,label:"市场价",sortable : true,align : "right",hidden:true},
			{name : "beflineamount",index : "beflineamount",width : 80,label:"总价",sortable : true,align : "right",sum:true},
    		{name : "remarks",index : "remarks",width : 220,label:"材料描述",sortable : true,align : "left"},
    		{name : "lastupdate",index : "lastupdate",width : 120,label:"最后修改时间",sortable : true,
    			align : "left", formatter:formatTime},
			{name : "lastupdatedby",index : "lastupdatedby",width : 90,label:"修改人",sortable : true,align : "left"},
			{name : "actionlog",index : "actionlog",width : 70,label:"操作",sortable : true,align : "left"},
			{name : "avgcost",index : "avgcost",width : 70,label:"操作",sortable : true,align : "right",hidden:true},
		],
	};

	Global.JqGrid.initJqGrid("dataTable",gridOption);

});
function getJqGrid(data) {
	$("#dataTable").jqGrid("setGridParam", {
    	postData: {ibdno:data.no},
	}).trigger("reloadGrid");
}

function doSave() {
	selectRows = [];
	var itCodes = Global.JqGrid.allToJson("dataTable","itcode");
	var itCodeArr = "${itCodes}".split(",");
	for (var j = 0; j < itCodes.keys.length; j++) {
		/*for (var i = 0; i < itCodeArr.length; i++) {
			if(itCodeArr[i] == $.trim(itCodes.keys[j])){
				art.dialog({
					content: "该产品已经存在,请勿重复插入",
					width: 200,
				});
				return;
			}
		}*/
		itCodes.datas[j].lastupdatedby = $("#lastupdatedby").val();
		selectRows.push(itCodes.datas[j]);
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
						<span>导入</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search" target="targetFrame">
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>批次编号</label>
							<input type="text" id="ibdno" name="ibdno" style="width:160px;"/>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<!-- <div id="dataTablePager"></div> -->
			</div>
		</div>
	</div>
</body>	

</html>
