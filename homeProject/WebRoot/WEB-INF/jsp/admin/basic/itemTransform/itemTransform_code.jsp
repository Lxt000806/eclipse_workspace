<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>基础预算模板获取编号</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#type").val("");
		}	
	$(function(){
		var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemTransform/goJqGrid",
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 75, label: "编号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 170, label: "说明", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 75, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 150, label: "材料名称", sortable: true, align: "left"},
				{name: "cost", index: "cost", width: 120, label: "材料成本", sortable: true, align: "left", hidden : true},
				{name: "uomdescr", index: "uomdescr", width: 120, label: "单位", sortable: true, align: "left", hidden : true},
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
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="itemType1" name="itemType1" value="${itemTransform.itemType1}" />
				<ul class="ul-form">
					<li class="form-validate"><label>编号</label> <input type="text"
						id="no"  name="no" style="width:160px;"
						value="${itemTransform.no}" />
					</li>
					<li class="form-validate"><label>加工说明</label> <input type="text"
						id="remarks" name="remarks" style="width:160px;" />
					</li>
					<li class="form-validate"><label>材料</label> <input type="text"
						id="itemDescr" name="itemDescr" style="width:160px;" />
					</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${itemTransform.expired}"
						onclick="hideExpired(this)" ${prePlanTemp.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
