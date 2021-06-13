<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--员工卡号</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
	var postData=$("#page_form").jsonForm();
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/wfProcInst/getSupplAccountJqGrid",
		postData:{no:"${splCheckOut.no}"},
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',   
		colModel : [
	      {name : 'rcvactname',index : 'rcvactname',width : 80,label:'收款户名',sortable : true,align : "left"},
	      {name : 'rcvcardid',index : 'rcvcardid',width : 250,label:'收款账户',sortable : true,align : "left"},
	      {name : 'rcvbank',index : 'rcvbank',width : 250,label:'收款银行',sortable : true,align : "left"},
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
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


