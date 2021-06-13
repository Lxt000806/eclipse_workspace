<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--员工</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}

/**初始化表格*/
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:415,
		url:"${ctx}/admin/itemPlan/getRepClauseDetail",
		postData:{code: "${customer.code}"},
		multiselect:true,
		styleUI: "Bootstrap", 
		colModel : [
			{name: "detail", index: "detail", width: 500, label: "补充协议", sortable: true, align: "left",},
		], 
	});
});
function save(){
	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
	var selectRows = [];
	$.each(ids,function(k,id){
		var row = $("#dataTable").jqGrid('getRowData', id);
		selectRows.push(row.detail);
	});
	Global.Dialog.returnData = selectRows;
	closeWin();
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system" onclick="save()">完成</button>
					<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div> 
	</div>
</body>
</html>


