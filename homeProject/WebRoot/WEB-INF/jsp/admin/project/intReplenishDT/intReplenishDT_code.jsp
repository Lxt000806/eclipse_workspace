<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>集成补货明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	var postData = $("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/intReplenishDT/goCodeJqGrid",
		postData: postData,
		height:400,
		styleUI: 'Bootstrap', 
		colModel : [
		  {name : "pk",index : "pk",width : 100,label:"pk",sortable : true,align : "left",hidden:true},
		  {name : "address",index : "address",width : 100,label:"楼盘",sortable : true,align : "left"},
		  {name : "iscupboarddescr",index : "iscupboarddescr",width : 100,label:"是否橱柜",sortable : true,align : "left"},
		  {name : "suppldescr",index : "suppldescr",width : 100,label:"供应商",sortable : true,align : "left"},
		  {name : "date",index : "date",width : 200,label:"补货时间",sortable : true,align : "left", formatter: formatTime},
		  {name : "type",index : "type",width : 90,label:"补货类型",sortable : true,align : "left"},
		  {name : "remarks",index : "remarks",width : 90,label:"补件详情",sortable : true,align : "left"},
        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<form action="" method="post" id="page_form" class="form-search" >
			<input type="hidden" id="custCode" name="custCode" style="width:160px;"  value="${intReplenishDT.custCode}" />
			<input type="hidden" id="intSpl" name="intSpl" value="${intReplenishDT.intSpl}"/>
		</form>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</div>	
</body>
</html>
