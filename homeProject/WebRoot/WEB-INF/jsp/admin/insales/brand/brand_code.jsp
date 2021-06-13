<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>brand查询code</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
//初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
	var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/brand/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			postData:postData,
			styleUI: 'Bootstrap',   
			colModel : [
		        {name: "code", index: "code", width: 124, label: "品牌编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 146, label: "品牌名称", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 204, label: "材料类型2", sortable: true, align: "left"}
			  
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
				<input type="hidden" id="expired" name="expired"
					value="${brand.expired}" />
					<ul class="ul-form">
						<li>
							<label>品牌编号</label>
							<input type="text" id="code" name="code"  value="${brand.code}" />
						</li>	
							<li>
							 <label>品牌名称</label>
							<input type="text" id="descr" name="descr"   value="${brand.descr}" />
							</li>
							<li> 
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" ></select>
							</li>
							<li> 
							<label>材料类型2</label>
							<select id="itemType2" name="itemType2" ></select>
							</li>
						
					<li class="search-group" >
				
					<input type="checkbox" id="expired_show" name="expired_show"
										value="${brand.expired}" onclick="hideExpired(this)"
										${brand.expired!='T' ?'checked':'' }/><p>隐藏过期</p>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
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


