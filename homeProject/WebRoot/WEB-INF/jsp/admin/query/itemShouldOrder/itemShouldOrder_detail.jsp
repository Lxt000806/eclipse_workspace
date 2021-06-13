<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>查看明细</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemShouldOrder/goDetailJqGrid",
			postData:{
				confItemType:"${customer.confItemType}",code:"${customer.code}", viewAll:"${customer.viewAll}",
			},
			height:500,
			colModel : [
			   {name: "fixareadescr",index : "fixareadescr",width : 100,label:"区域",sortable : true,align : "left"},
		       {name: "itemcode",index : "itemcode",width : 80,label:"材料编号",sortable : true,align : "left"},
		       {name: "itemdescr",index : "itemdescr",width : 200,label:"材料名称",sortable : true,align : "left"},
		       {name: "qty", index: "qty", width: 70, label: "需求数量", sortable: true, align: "right",sum:true},
		       {name: "itemtype2descr",index : "itemtype2descr",width : 80,label:"材料类型2",sortable : true,align : "left"},
		       {name: "itemtype3descr",index : "itemtype3descr",width : 80,label:"材料类型3",sortable : true,align : "left"},
            ] ,
            rowNum:100000,  
	   		pager :'1',
		}); 
});

</script>
</head>
    
<body>
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
			<button type="button" class="btn btn-system "
					onclick="doExcelNow('应下单材料明细','dataTable', 'page_form')">导出excel</button>
			<button type="button" class="btn btn-system "
				onclick="closeWin(false)">关闭</button>
			</div>
		</div>
		<form action="" method="post" id="page_form" class="form-search">
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" name="viewAll" id="viewAll "value="${customer.viewAll }" />
		</form>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id="dataTable"></table>
		</div>
	</div>
</body>
</html>


