<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--仓库信息</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
	var postData = $("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/wareHouse/goJqGridCode",
		postData: postData,
		styleUI: 'Bootstrap', 
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
		  {name : 'code',index : 'code',width : 100,label:'仓库编号',sortable : true,align : "left"},
	      {name : 'desc1',index : 'desc1',width : 200,label:'仓库名称',sortable : true,align : "left"}
        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        },
        // 增加选中已选择值的方法 --add by zb
        gridComplete: function(){
			Global.JqGrid.setSelection("dataTable","code","${wareHouse.code}");
		}
	});

	if("${wareHouse.showLastSendSupplier}" == "1"){
		$("#lastSendSupplierLi").removeAttr("hidden");
	}				
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="czybh" name="czybh" value="${wareHouse.czybh}" />
				<input type="hidden" id="ctrlItemType1" name="ctrlItemType1" value="${wareHouse.ctrlItemType1}" />
				<input type="hidden" id="isManagePosi" name="isManagePosi" value="${wareHouse.isManagePosi}" />
				<input type="hidden" id="itemType1" name="itemType1" value="${wareHouse.itemType1}" />
					<ul class="ul-form">
						<li>
							<label>中文名</label>
							<input type="text" id="desc1" name="desc1"   value="${wareHouse.desc1 }" />
						</li>
						<li style="display: none"> 
							<label>虚拟号</label>
							<input type="text" id="delXNCK" name="delXNCK"  value="${wareHouse.delXNCK }" />
						</li>
						<li id="lastSendSupplierLi" hidden>
							<label>土建最后发货商</label>
							<input type="text" id="lastSendSupplierDescr" name="lastSendSupplierDescr"  value="${wareHouse.lastSendSupplierDescr}"/>
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


