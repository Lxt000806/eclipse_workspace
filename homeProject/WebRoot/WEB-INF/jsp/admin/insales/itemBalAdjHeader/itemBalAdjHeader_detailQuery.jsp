<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>库存余额明细查询</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_item.js?v=${v}"
	type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_workCard.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#itemType1").val("");
			$("#headerType").val("");
			$.fn.zTree.getZTreeObj("tree_headerType").checkAllNodes(false);
			$("#headerStatus").val("");
			$.fn.zTree.getZTreeObj("tree_headerStatus").checkAllNodes(false);
		}	
	$(function(){
		$("#itCode").openComponent_item();
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemBalAdjDetail/goDetailJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'ibhno', index:'ibhno', width:90, label:'仓库调整编号', sortable:true ,align:"left"},
				{name:'whcodedescr', index:'whcodedescr', width:150, label:'仓库', sortable:true ,align:"left"},
				{name:'headerstatus', index:'headerstatus', width:65, label:'状态', sortable:true ,align:"left"},
				{name:'headertype', index:'headertype', width:75, label:'调整类型', sortable:true ,align:"left"},
				{name: "adjreasondescr", index: "adjreasondescr", width: 80, label: "调整原因", sortable: true, align: "left"},
				{name:'appdate', index: 'appdate', width: 80, label: '申请日期', sortable: true, align: "left", formatter: formatDate},
				{name:'confirmdate', index: 'confirmdate', width: 80, label: '审核日期', sortable: true, align: "left", formatter: formatDate},
				{name:'date', index: 'date', width: 80, label: '变动日期', sortable: true, align: "left", formatter: formatDate},
				{name:'itemtype1descr', index:'itemtype1descr', width:75, label:'材料类型1', sortable:true ,align:"left"},
				{name:'itemtype2descr', index:'itemtype2descr', width:75, label:'材料类型2', sortable:true ,align:"left"},
				{name:'pk', index:'pk', width:105, label:'PK', sortable:true ,align:"left", hidden:true},
				{name:'itcode', index:'itcode', width:70, label:'材料编号', sortable:true ,align:"left"},
				{name:'itdescr', index:'itdescr', width:250, label:'材料名称', sortable:true ,align:"left"},
				{name:'uomdescr', index:'uomdescr', width:50, label:'单位', sortable:true, align:"left"},
				{name:'adjqty',	index:'adjqty',width:80, label:'调整数量', sortable : true,align : "right",sum:true},
				{name:'adjcost',index:'adjcost',width:70, label:'成本单价', sortable:true,align:"right",},
				{name:'headerremarks',index:'headerremarks',width:220, label:'备注', sortable:true,align:"left"},	
		],
			});
		});
		
		function doExcel(){
		var url = "${ctx}/admin/itemBalAdjDetail/doDetailQueryExcel";
		doExcelAll(url);
	}
	</script>
</head>
<body >
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>仓库调整编号</label> 
						<input type="text" name="ibhNo">
					</li>
					<li>
						<label for="adjReason">调整原因</label>
						<house:xtdm id="adjReason" dictCode="AdjReason" style="width:160px;"/>
					</li>
					<li><label>材料类型1</label> <select id="itemType1"
						name="itemType1"></select>
					</li>
					
					<li><label>材料编号</label> <input type="text" id="itCode"
						name="itCode" />
					</li>
					<li><label>仓库</label> <input type="text" id="wHCode"
						name="wHCode" />
					</li>
					<li><label>备注</label> <input type="text" id="headerRemarks"
						name="headerRemarks" style="width:160px;"
						 />
					</li>
					<li><label>变动日期从</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>

					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>状态</label> <house:xtdmMulit id="headerStatus"
							dictCode="BALADJSTATUS"
							selectedValue="1,2,3"></house:xtdmMulit>
					</li>
					<li><label>调整类型</label> <house:xtdmMulit id="headerType"
							dictCode="ADJTYP"
							selectedValue="1,2,3"></house:xtdmMulit>
					</li>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm" >
				<button type="button" class="btn btn-system" id="excel"
					onclick="doExcel()">
					<span>导出Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager" style="height:25px;width:1000px"></div>
		</div>
	</div>
</body>
</html>
