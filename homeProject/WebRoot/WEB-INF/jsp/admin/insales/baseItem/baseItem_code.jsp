<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>BaseItem查询code</title>
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
		Global.LinkSelect.initSelect("${ctx}/admin/baseItem/baseItemType","baseItemType1","baseItemType2");
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/baseItem/goJqGrid",
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
		        {name: "code", index: "code", width: 102, label: "基础项目编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 162, label: "基础项目名称", sortable: true, align: "left"},
				{name: "baseitemtype1descr", index: "baseitemtype1descr", width: 114, label: "基础类型1", sortable: true, align: "left"},
				{name: "baseitemtype1", index: "baseitemtype1", width: 100, label: "基础材料类型1", sortable: true, align: "left", hidden: true},
				{name: "baseitemtype2descr", index: "baseitemtype2descr", width: 106, label: "基础类型2", sortable: true, align: "left"},
				{name: "marketprice", index: "marketprice", width: 75, label: "市场价", sortable: true, align: "left"},
				{name: "offerpri", index: "offerpri", width: 78, label: "人工单价", sortable: true, align: "left"},
				{name: "material", index: "material", width: 78, label: "材料单价", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 204, label: "备注", sortable: true, align: "left"},
				{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left", hidden: true},
				{name: "categorydescr", index: "categorydescr", width: 88, label: "项目类型", sortable: true, align: "left", hidden: true},
				{name: "category", index: "category", width: 88, label: "项目类型", sortable: true, align: "left", hidden: true},
				{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
				{name: "allowpricerise", index: "allowpricerise", width: 68, label: "价格上浮", sortable: true, align: "left", hidden: true},
				{name: "isfixprice", index: "isfixprice", width: 20, label: "是否固定价格", sortable: true, align: "left", hidden: true},
            ],
			ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
});
function hideNoSetBaseCheckItem(obj){
	if ($(obj).is(":checked")){
		$("#noSetBaseCheckItem").val("T");
	}else{
		$("#noSetBaseCheckItem").val("F");
	}
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="custTypeType" name="custTypeType" style="width:160px;" value="${baseItem.custTypeType}" />
				<input type="hidden" id="customerType" name="customerType" style="width:160px;" value="${baseItem.customerType}" />
				<input type="hidden" id="prjType" name="prjType" style="width:160px;" value="${baseItem.prjType}" />
				<input type="hidden" id="category" name="category" style="width:160px;" value="${baseItem.category}" />
				<input type="hidden" id="custType" name="custType" style="width:160px;" value="${baseItem.custType}" />
				<input type="hidden" id="canUseComBaseItem" name="canUseComBaseItem" style="width:160px;" value="${baseItem.canUseComBaseItem}" />
				<input type="hidden" id="isOutSet" name="isOutSet" style="width:160px;" value="${baseItem.isOutSet}" />
				
				<ul class="ul-form">
					<li>
					<label>基础项目编号</label> 
					<input type="text" id="code"
						name="code"  value="${baseItem.code}" />
					</li>
					<li>
					<label>基础项目名称</label> 
					<input type="text" id="descr"
						name="descr"  value="${baseItem.descr}" />
					</li>
					<li>
					<label>基装类型1</label> 
					<select id="baseItemType1"
						name="baseItemType1"></select>
					</li>
					<li>
					<label>基装类型2</label> 
					<select id="baseItemType2"
						name="baseItemType2" ></select>
					</li>
					<li>
						<input type="checkbox" id="noSetBaseCheckItem" name="noSetBaseCheckItem" value=""
							onclick="hideNoSetBaseCheckItem(this)" />
						<label for="noSetBaseCheckItem" style="margin-left: -3px;width: 110px;">未设置基础结算项目</label>
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
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>
