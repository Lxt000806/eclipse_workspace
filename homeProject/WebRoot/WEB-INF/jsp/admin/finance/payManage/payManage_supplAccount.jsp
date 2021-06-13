<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--供应商卡号</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
	var postData=$("#page_form").jsonForm();
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/payManage/getSupplAccount",
		postData:{appEmp: "${supplierPrepay.appEmp }",splCode: "${supplierPrepay.splCode }"},
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',   
		colModel : [
			  {name : 'rcvactname',index : 'rcvactname',width : 150,label:'户名',sortable : true,align : "left",},
			  {name : 'rcvcardid',index : 'rcvcardid',width : 200,label:'卡号',sortable : true,align : "left",},
			  {name : 'rcvbank',index : 'rcvbank',width : 200,label:'银行',sortable : true,align : "left",},
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
		<div class="query-form" hidden="true">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" id="czybh" name="czybh" value="${czybh }" />
				<ul class="ul-form" id="detail_ul">
					<div class="validate-group row" >
						<li>		
							<label>户名</label>
							<input type="text" id="actName" name="actName"/>
						</li>
						<li class="search-group" >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>					
					</div>
				</ul>
			</form>
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


