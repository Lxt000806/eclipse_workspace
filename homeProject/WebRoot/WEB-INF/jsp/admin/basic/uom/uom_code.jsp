<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--度量单位</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	var postData = $("#page_form").jsonForm();
     //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/uom/goJqGrid",
		postData:postData,
		styleUI: 'Bootstrap',   
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
		  {name: "code", index: "code", width: 103, label: "度量单位编号", sortable: true, align: "left"},
		  {name: "descr", index: "descr", width: 120, label: "度量单位名称", sortable: true, align: "left"}
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
			<form action="" method="post" id="page_form" class="form-search">
				<ul class="ul-form">
					<li>
						<label>度量单位编号</label> 
						<input type="text" id="code" name="code" value="${uom.code}" />
					</li>
					<li>
						<label>度量单位名称</label>
						 <input type="text" id="descr" name="descr" value="${uom.descr }" />
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


