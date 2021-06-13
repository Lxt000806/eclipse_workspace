<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>明细查询</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	}
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#documentNo").val('');
		$("#splcode").val('');
		$("#type").val('');
		$("#no").val('');
		$("#itemType1").val('');
		$("#status").val('');
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	$(function (){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		$("#puNo").openComponent_purchase({condition:{itemType1:$("#itemType1").val(),readonly:"1",},valueOnly:"1"});
		$("#splCode").openComponent_supplier();
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/payManage/goJqGridMxSelect",
			postData:{type:"${paymanage.type}"},
			styleUI: "Bootstrap",
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [		
		  	    {name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "no", index: "no", width: 80, label: "预付单号", sortable: true, align: "left"},
				{name: "typedescr", index: "typedescr", width: 86, label: "预付类型", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
				{name: "appdate", index: "appdate", width: 100, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "confirmdate", index: "confirmdate", width: 100, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "spldescr", index: "spldescr", width: 132, label: "供应商", sortable: true, align: "left"},
				{name: "puno", index: "puno", width: 80, label: "采购单号", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 80, label: "本次付款", sortable: true, align: "right", sum: true},
				{name: "paydate", index: "paydate", width: 103, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
				{name: "documentno", index: "documentno", width: 100, label: "凭证号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 300, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 138, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 105, label: "最后更新人员", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 88, label: "操作标志", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left", hidden: true}
            ]
		});
	}); 
	function doExcel(){
		var url = "${ctx}/admin/payManage/doExcelDetail"; 
		doExcelAll(url);
	} 
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li><label>预付类型</label> <house:xtdm id="type" dictCode="SPLPREPAYTYPE"></house:xtdm>
						</li>
						<li><label>预付单号</label> <input type="text" id="no" name="no" />
						</li>
						<li><label>供应商编号</label> <input type="text" id="splCode" name="splCode" />
						</li>
						<li><label>材料类型1</label> <select id="itemType1" name="itemType1"></select>
						</li>
						<li><label>采购单号</label> <input type="text" id="puNo" name="puNo" />
						</li>
						<li><label>状态</label> <house:xtdmMulit id="status" dictCode="SPLPREPAYSTATUS" selectedValue="1,2,3"></house:xtdmMulit>
						</li>
						<li>
							<label>付款日期从</label>
							<input type="text" id="payDateFrom" name="payDateFrom" class="i-date"  
								 onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="payDateTo" name="payDateTo" class="i-date"  
								 onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						
						<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<!--query-form-->
		<div class="btn-panel">
			<!--panelBar-->
			<div class="pageContent">
				<div id="content-list">
					<table id="dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>


