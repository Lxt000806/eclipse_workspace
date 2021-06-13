<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>预算修改日志查询</title>
	<%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script> 
	<script src="${resourceRoot}/pub/component_itemBatchHeader.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function containDiff(){
	if ($(obj).is(":checked")){
		$("#containDiff").val("1");
	}else{
		$("#containDiff").val("");
	}
	
}
function goto_query(){
	if($.trim($("#no").val())==''){
			art.dialog({content: "批次编号不能为空",width: 200});
			return false;
	} 
	if($.trim($("#whCode").val())==''){
			art.dialog({content: "仓库不能为空",width: 200});
			return false;
	}
	if($("#containDiff").is(':checked'))  $("#containDiff").val('1');
	else $("#containDiff").val('');
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/pdcyfx/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}
//导出EXCEL
function doExcel(){
	doExcelNow("盘点差异分析","dataTable","page_form");
}
   
 
/**初始化表格*/
$(function(){
	    //初始化材料类型1，2，3三级联动
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		Global.LinkSelect.setSelect({firstSelect:'itemType1',
									firstValue:'${item.itemType1 }',
									secondSelect:'itemType2',
									secondValue:'${item.itemType2 }',
									thridSelect:'itemType3',
									thirdValue:'${item.itemType3 }',});
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			colModel : [
			 	{name: "itcode", index: "itcode", width: 100, label: "材料编码", sortable: true, align: "left", count: true},
				{name: "itname", index: "itname", width: 220, label: "材料名称", sortable: true, align: "left"},
				{name: "qtycal", index: "qtycal", width: 120, label: "记账数量", sortable: true, align: "right", sum: true},
				{name: "qty", index: "qty", width: 120, label: "盘点数量", sortable: true, align: "right", sum: true},
				{name: "qtynum", index: "qtynum", width: 120, label: "差异数量", sortable: true, align: "right", sum: true}
		    ],
			 rowNum:100000,  
			 pager :'1',
		    
           	
		});
	   $("#whCode").openComponent_wareHouse(); 
	   $("#no").openComponent_itemBatchHeader({condition:{batchType:"2",isMultiselect:"1"}});        
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>批次编号</label> <input type="text" id="no" name="no" />
					</li>
					<li><label>仓库</label> <input type="text" id="whCode" name="whCode" />
					</li>
					<li><label>包含无差异</label> <input type="checkbox" id="containDiff" name="containDiff"  />
					</li>			
					<li><label>材料类型1</label> <select id="itemType1" name="itemType1"></select>
					</li>
					<li><label>材料类型2</label> <select id="itemType2" name="itemType2"></select>
					</li>
					<li><label>材料类型3</label> <select id="itemType3" name="itemType3"></select>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		
		<div class="btn-panel" >
      		<div class="btn-group-sm"  >   
				<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
			</div>
		</div> 
			
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
		
	</div>
</body>
</html>


