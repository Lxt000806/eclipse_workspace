<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--OA操作员编号</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
	#gview_dataTable {
		margin: 0px;
	}
</style>
<script type="text/javascript">
$(function(){
	var postData = $("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/actUser/goJqGrid",
		postData: postData,
		styleUI: "Bootstrap", 
		height:$(document).height()-$("#content-list").offset().top-40,
		rowNum:1000000,
		colModel : [
		  {name : "ID_",index : "ID_",width : 150,label:"编号",sortable : true,align : "left"},
	      {name : "FIRST_",index : "FIRST_",width : 100,label:"中文名称",sortable : true,align : "left"},
	      {name : "LAST_",index : "LAST_",width : 100,label:"中文姓氏",sortable : true,align : "left"}
        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid("getRowData", rowid);
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
						<label>编号</label>
						<input type="text" id="id" name="id"   value="${actUser.id}"/>
					</li>
					<li>
						<label>用户名称</label>
						<input type="text" id="first" name="first"   value="${actUser.first}"/>
					</li>
					<li>
						<label>用户姓氏</label>
						<input type="text" id="last" name="last"   value="${actUser.last}"/>
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
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>
		</div> 
	</div>
</body>
</html>
