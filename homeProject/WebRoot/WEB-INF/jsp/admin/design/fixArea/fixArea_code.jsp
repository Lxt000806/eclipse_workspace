<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>FixArea查询code</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
	var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/fixArea/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			postData:postData,
			styleUI: 'Bootstrap',
			rowNum:10000,
			colModel : [
		        {name: "PK", index: "PK", width: 100, label: "区域编号", sortable: true, align: "left"},
				{name: "Descr", index: "Descr", width: 120, label: "区域名称", sortable: true, align: "left"},
				{name: "CustCode", index: "CustCode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "custdescr", index: "custdescr", width: 100, label: "客户名称", sortable: true, align: "left"},
				{name: "PrePlanAreaPK", index: "PrePlanAreaPK", width: 100, label: "空间pk", sortable: true, align: "left", hidden: true},
			  	{name: "preplanareadescr", index: "preplanareadescr", width: 100, label: "空间名称", sortable: true, align: "left"}
            ],
			ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
});

function add(){
	Global.Dialog.showDialog("fixAreaAdd",{
		  title:"新增装修区域",
		  url:"${ctx}/admin/fixArea/goSave",
		  postData:{custCode:"${fixArea.custCode}",isService:"${fixArea.isService}",itemType1:"${fixArea.itemType1}"}, 
		   height:500,
		  width:400,
		  returnFun: goto_query
	});
}
function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("fixAreaUpdate",{
		  title:"修改装修区域",
		  url:"${ctx}/admin/fixArea/goUpdate?id="+ret.PK,
		  height:500,
		  width:400,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form"  class="form-search">
			<input type="hidden" id="custCode" name="custCode" style="width:160px;"  value="${fixArea.custCode}" />
			<input  type="hidden" id="itemType1" name="itemType1" style="width:160px;"  value="${fixArea.itemType1}" />
			<input type="hidden" id="isService" name="isService" style="width:160px;"  value="${fixArea.isService}" />
				<ul class="ul-form">
					<li>
							<label>区域名称</label>
							<input type="text" id="descr" name="descr"  value="${fixArea.descr}" />
					</li>		
					<li id="loadMore" >
								<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
					</li>
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


