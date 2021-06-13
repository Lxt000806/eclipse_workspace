<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--公司名称</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/company/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
		  {name : 'Code',index : 'Code',width : 100,label:'公司编号',sortable : true,align : "left"},
	      {name : 'Desc1',index : 'Desc1',width : 200,label:'英文名',sortable : true,align : "left"},
	      {name : 'Desc2',index : 'Desc2',width : 200,label:'中文名',sortable : true,align : "left"}
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
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${department1.expired }" />	
				<ul class="ul-form">
					<li>
						<label>公司编号</label> 
						<input type="text" id="Code" name="Code" style="width:160px;" value="${company.code }"/>
					</li>
					<li>
						<label>公司名称</label> 
						<input type="text"  id="Desc2" name="Desc2" style="width:160px;" value="${company.desc2 }"/>
					</li>
					<li>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div> 
</body>
</html>


