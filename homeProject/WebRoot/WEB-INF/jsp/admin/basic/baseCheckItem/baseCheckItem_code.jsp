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
		url:"${ctx}/admin/baseCheckItem/goCodeJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
	        {name: "code", index: "code", width: 120, label: "基础结算项目编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 240, label: "基础结算项目名称", sortable: true, align: "left"},
			{name: "baseitemtype1descr", index: "baseitemtype1descr", width: 80, label: "基装类型1", sortable: true, align: "left"},
			{name: "baseitemtype2descr", index: "baseitemtype2descr", width: 80, label: "基装类型2", sortable: true, align: "left"},
			{name: "worktype12descr", index: "worktype12descr", width: 87, label: "工种类型12", sortable: true, align: "left"},
			{name: "worktype12", index: "worktype12", width: 88, label: "工种类型12", sortable: true, align: "left",hidden:true},
			{name: "worktype1descr", index: "worktype1descr", width: 87, label: "工种类型1", sortable: true, align: "left"},
			{name: "worktype1", index: "worktype1", width: 88, label: "工种类型1", sortable: true, align: "left",hidden:true},
			{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "offerpri", index: "offerpri", width: 70, label: "人工单价", sortable: true, align: "right"},
			{name: "material", index: "material", width: 70, label: "材料单价", sortable: true, align: "right"},
			{name: "prjofferpri", index: "prjofferpri", width: 120, label: "项目经理人工单价", sortable: true, align: "right"},
			{name: "prjmaterial", index: "prjmaterial", width: 120, label: "项目经理材料单价", sortable: true, align: "right"},
			{name: "remark", index: "remark", width: 200, label: "备注", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 70, label: "类型", sortable: true, align: "left"},
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
				<ul class="ul-form">
					<input type="hidden" id="workType12" name="workType12" value="${baseCheckItem.workType12 }" />
					<input type="hidden" id="custCode" name="custCode" value="${baseCheckItem.custCode }" />
					<li>
						<label>基础结算项目编号</label> 
						<input type="text" id="code"
							name="code"  value="${baseCheckItem.code}" />
					</li>
					<li>
						<label>基础结算项目名称</label> 
						<input type="text" id="descr"
							name="descr"  value="${baseCheckItem.descr}" />
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
