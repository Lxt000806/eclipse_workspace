<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>发货时限管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		//初始化材料类型1，2，3三级联动
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2");
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/sendTime/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap',
			autoScroll: true, 
			colModel : [
				{name: "no", index: "no", width: 110, label: "发货时限编号", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 120, label: "材料类型1", sortable: true, align: "left"},
				{name: "producttypedescr", index: "producttypedescr", width: 120, label: "产品类型", sortable: true, align: "left"},
				{name: "issetitemdescr", index: "issetitemdescr", width: 120, label: "是否限制材料", sortable: true, align: "left"},
				{name: "sendday", index: "sendday", width: 80, label: "送货天数", sortable: true, align: "right"},
				{name: "prior", index: "prior", width: 80, label: "优先级", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 150, label: "上次更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "上次更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 100, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
				Global.Dialog.returnData = selectRow;
				Global.Dialog.closeDialog();
			},
		});
	});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" >
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="" /> 
				<ul class="ul-form">
					<li class="form-validate"><label>发货时限编号</label> <input type="text"
						id="no"  name="no" style="width:160px;"
						value="${sendTime.no}" />
					</li>
					<li><label>材料类型1</label> <select id="itemType1"
						name="itemType1"></select>
					</li>
					<li><label>产品类型</label> <house:xtdm id="productType"
									dictCode="APPPRODUCTTYPE" value="${sendTime.productType}"
									></house:xtdm>
					</li>
					<li class="search-group"><input type="checkbox" id="expired_show"
						name="expired_show" value="${sendTime.expired}"
						onclick="hideExpired(this)" ${sendTime.expired!='T' ?'checked':'' }/>
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
