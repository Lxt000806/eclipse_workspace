<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--项目名称</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	var postData = $("#page_form").jsonForm();

        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/builder/goJqGrid",
			postData:postData,
			styleUI: 'Bootstrap',   
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'code',index : 'code',width : 100,label:'项目名称编号',sortable : true,align : "left"},
		      {name : 'descr',index : 'descr',width : 200,label:'项目名称',sortable : true,align : "left"},
		      {name : 'alias',index : 'alias',width : 200,label:'项目别名',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left"},
		      {name : 'department2',index : 'department2',width : 200,label:'department2',sortable : true,align : "left",hidden:true}
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
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="department2" name="department2" value="${builder.department2 }" />
					<ul class="ul-form">
						<li>
							<label>项目名称</label>
							<input type="text" id="descr" name="descr"  value="${builder.descr }" />
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


