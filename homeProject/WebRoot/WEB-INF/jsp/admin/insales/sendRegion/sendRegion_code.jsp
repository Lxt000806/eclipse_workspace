<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Item列表</title>
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
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/sendRegion/goJqGrid",
		postData: postData,
		height:385,
		styleUI: 'Bootstrap', 
		colModel : [
		  		{name: "no", index: "no", width: 90, label: "编号", sortable: true, align: "left"},
				{name: "desc1", index: "desc1", width: 226, label: "英文名", sortable: true, align: "left", hidden: true},
				{name: "descr", index: "descr", width: 173, label: "名称", sortable: true, align: "left"},
				{name: "transfee", index: "transfee", width: 100, label: "运费", sortable: true, align: "left"},
				{name: "distancetypedescr", index: "distancetypedescr", width: 104, label: "距离类型", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 416, label: "备注", sortable: true, align: "left"}
        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
	$("#descr").focus();
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
						<ul class="ul-form">
							<li> 
								<label >名称</label>
								<input type="text" id="descr" style="width:160px" name="descr"  value="${sendRegion.descr}" />
							</li>
								<li> 
								<label >备注</label>
								<input type="text" id="remarks" style="width:160px" name="remarks"  value="${sendRegion.remarks}" />
							</li>
						<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${sendRegion.expired}"
						onclick="hideExpired(this)" ${sendRegion.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button></li>
					</ul>
			</form>
		</div>
		<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
								<button type="button" class="btn btn-system "  id="view"><span>查看</span></button>
					</div>
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


