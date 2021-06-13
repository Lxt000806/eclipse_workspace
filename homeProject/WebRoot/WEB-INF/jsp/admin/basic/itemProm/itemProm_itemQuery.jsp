<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>促销材料查询</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		//初始化材料类型1，2，3三级联动
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2");
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemProm/goItemJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			colModel : [
				{name: "itemcode", index: "itemcode", width: 80, label: "材料编码", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 225, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
				{name: "suppldescr", index: "suppldescr", width: 145, label: "供应商", sortable: true, align: "left"},
				{name: "price", index: "price", width: 70, label: "促销价", sortable: true, align: "right"},
				{name: "cost", index: "cost", width: 80, label: "促销进价", sortable: true, align: "right"},
				{name: "oldprice", index: "oldprice", width: 70, label: "原售价", sortable: true, align: "right"},
				{name: "oldcost", index: "oldcost", width: 70, label: "原进价", sortable: true, align: "right"},
				{name: "remark", index: "remark", width: 200, label: "备注", sortable: true, align: "left"}
			],
		});
		$("#itemCode").openComponent_item();
		$("#supplCode").openComponent_supplier();
	});
	</script>
</head>
<body >
	<div class="body-box-list">
		<div class="btn-panel">
			<div class="btn-group-sm" >
				<button type="button" class="btn btn-system" id="excel"
					onclick="closeWin(false)">
					<span>关闭</span>
				</button>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>材料类型1</label> <select id="itemType1"
						name="itemType1"></select>
					</li>
					<li><label>供应商</label> <input type="text" id="supplCode"
						name="supplCode" />
					</li>
					<li><label>材料编码</label> <input type="text" id="itemCode"
						name="itemCode" />
					</li>
					<li class="search-group-shrink"><input type="checkbox"
						id="expired_show" name="expired_show"
						value="${itemProm.expired}" onclick="hideExpired(this)"
						${itemProm.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-system btn-sm"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-system btn-sm"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager" style="height:25px;width:1000px"></div>
		</div>
	</div>
</body>
</html>
