<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>IntProd查询code</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/intProd/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			postData:postData,
			styleUI: 'Bootstrap',   
			colModel : [
		     {name: "PK", index: "PK", width: 100, label: "集成成品编号", sortable: true, align: "left"},
			{name: "Descr", index: "Descr", width: 100, label: "集成成品名称", sortable: true, align: "left"},
			{name: "iscupboarddescr", index: "iscupboarddescr", width: 70, label: "是否橱柜", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 84, label: "区域名称", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 90, label: "客户编号", sortable: true, align: "left"},
			{name: "custname", index: "custname", width: 80, label: "客户名称", sortable: true, align: "left"},
			{name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left"},
			  
            ],
			ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
		  $("#fixAreaPk").openComponent_fixArea({condition: {isService:'${intProd.isService}',custCode:'${intProd.custCode}',itemType1:'${intProd.itemType1}'}});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form"  class="form-search">
				<input type="hidden"  name="itemType1" style="width:160px;" value="${intProd.itemType1}" />
				<input type="hidden" id="isService" name="isService" value="${intProd.isService}" />
				<input type="hidden" id="custCode" name="custCode" value="${intProd.custCode}" />
				<input type="hidden" id="isCupboard" name="isCupboard" value="${intProd.isCupboard}" />
					<ul class="ul-form">
							<li> 
							<label>区域名称</label>
							<input type="text" id="fixAreaPk" name="fixAreaPk"  value="${intProd.fixAreaPk}" />
							</li>
							<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm"
								onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-system btn-sm"
								onclick="clearForm();">清空</button>
					</li>
					</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


