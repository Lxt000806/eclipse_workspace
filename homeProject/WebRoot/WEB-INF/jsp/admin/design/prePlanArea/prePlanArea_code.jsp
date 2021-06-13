<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>prePlanArea查询code</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
	//改写窗口右上角关闭按钮事件
 	var titleCloseEle = parent.$("div[aria-describedby=dialog_select_prePlanArea]").children(".ui-dialog-titlebar");
 	$(titleCloseEle[0]).children("button").remove();
 	$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
 									+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\" ></span><span class=\"ui-button-text\">Close</span></button>");
 	$(titleCloseEle[0]).children("button").on("click", saveClose); 
 	
	var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemPlan/getAddPlanAreaJqgrid",
			height:$(document).height()-$("#content-list").offset().top-150,
			postData:postData,
			rowNum:10000,
			colModel : [
		        {name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "isdefaultarea", index: "isdefaultarea", width: 94, label: "isdefaultarea", sortable: true, align: "left",hidden:true},
				{name: "fixareatype", index: "fixareatype", width: 94, label: "fixareatype", sortable: true, align: "left",hidden:true},
				{name: "custcode", index: "custcode", width: 94, label: "custcode", sortable: true, align: "left",hidden:true},
				{name: "pavetype", index: "pavetype", width: 80, label: "铺贴类型", sortable: true, align: "left",hidden:true },
				{name: "fixareatypedescr", index: "fixareatypedescr", width: 80, label: "空间类型", sortable: true, align: "left",},
				{name: "descr", index: "descr", width: 140, label: "空间名称", sortable: true, align: "left", },
				{name: "area", index: "area", width: 58, label: "面积", sortable: true, align: "right", sum:true,},
				{name: "dispseq", index: "dispseq", width: 70, label: "显示顺序", sortable: true, align: "right", },
            ],
			ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
});
function saveClose(){
   	Global.Dialog.closeDialog();
}

</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form"  class="form-search">
			<input type="hidden" id="custCode" name="custCode" style="width:160px;"  value="${prePlanArea.custCode}" />
				<ul class="ul-form">
					<li>
							<label>空间名称</label>
							<input type="text" id="descr" name="descr"  value="${prePlanArea.descr}" />
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


