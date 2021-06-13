<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增领料 已领材料</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
		$(function(){
			Global.JqGrid.initJqGrid("dataTable_ylcl",{
				height:450,
				rowNum: 10000,
				styleUI:"Bootstrap",
				url:"${ctx}/admin/itemPreApp/goYlclJqGrid",
				postData:{
					custCode:$("#custCode").val(),
					appNo:$("#appNo").val(),
					itemCodes:$("#itemCodes").val()
				},
				colModel:[
					{name: "itemcode", index: "itemcode", width: 95, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 160, label: "材料名称", sortable: true, align: "left"},
					{name: "itemtype2descr", index: "itemtype2descr", width: 100, label: "材料类型2", sortable: true, align: "left"},
					{name: "uomdescr", index: "uomdescr", width: 70, label: "单位", sortable: true, align: "left"},
					{name: "totalqty", index: "totalqty", width: 130, label: "累计已发货数量", sortable: true, align: "left"},
					{name: "totalcost", index: "totalcost", width: 120, label: "累计成本总价", sortable: true, align: "left", hidden: true}
				]
			});
			if($("#costRight").val() == "1"){
				$("#dataTable_ylcl").jqGrid("showCol", "totalcost");
			}
		});
	</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
	   				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		
		<div class="clear_float"> </div>

		<input type="hidden" id="custCode" name="custCode" value="${data.custCode} " />
		<input type="hidden" id="appNo" name="appNo" value="${data.appNo}" />
		<input type="hidden" id="itemCodes" name="itemCodes" value="${data.itemCodes }" />
		<input type="hidden" id="costRight" name="costRight" value="${data.costRight }" />
		<div id="content-list">
			<table id= "dataTable_ylcl"></table>
		</div>
	</div>
</body>
</html>


