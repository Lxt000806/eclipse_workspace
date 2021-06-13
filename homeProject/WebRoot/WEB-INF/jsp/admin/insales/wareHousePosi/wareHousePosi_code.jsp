<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--库位信息</title>
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
		url:"${ctx}/admin/wareHousePosi/goCodeJqGrid",
		postData: postData,
		styleUI: "Bootstrap", 
		height:$(document).height()-$("#content-list").offset().top-40,
		rowNum:1000000,
		colModel : [
		  {name : "pk",index : "pk",width : 100,label:"编号",sortable : true,align : "left"},
	      {name : "desc1",index : "desc1",width : 200,label:"中文名称",sortable : true,align : "left"}
        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid("getRowData", rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        },
        gridComplete: function(){
			Global.JqGrid.setSelection("dataTable","pk","${wareHousePosi.pk}");
		}
	});

});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="czybh" name="czybh" value="${wareHousePosi.czybh}"/>
				<input type="hidden" id="whcode" name="whcode" value="${wareHousePosi.whcode}"/>
				<ul class="ul-form">
					<li>
						<label>中文名</label>
						<input type="text" id="desc1" name="desc1"   value="${wareHousePosi.desc1}"/>
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


