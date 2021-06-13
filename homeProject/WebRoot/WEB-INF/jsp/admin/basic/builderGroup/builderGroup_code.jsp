<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--项目大类</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/builderGroup/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',   
		colModel : [
		  {name : 'Code',index : 'Code',width : 100,label:'项目大类编号',sortable : true,align : "left"},
	      {name : 'Descr',index : 'Descr',width : 200,label:'项目大类名称',sortable : true,align : "left"},
	      {name : 'Remarks',index : 'Remarks',width : 200,label:'备注',sortable : true,align : "left"}
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
				<ul class="ul-form">
						<li>
							<label>项目大类名称</label>
						
							<input type="text" id="descr" name="descr"   value="${builderGroup.descr }" />
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


