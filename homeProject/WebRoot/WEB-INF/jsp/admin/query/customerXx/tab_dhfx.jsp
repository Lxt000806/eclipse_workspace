<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_dhfx_mx",{
			height:200,
			rowNum: 10000,
			//caption: "采购明细&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"doExcelNow('采购明细','dataTable_dhfx_mx','page_form_excel')\">【导出excel】</a>",
			colModel : [
			    {name: "no", index: "no", width: 120, label: "采购单号", sortable: true, align: "left", count: true},
				{name: "date", index: "date", width: 200, label: "采购日期", sortable: true, align: "left", formatter: formatTime},
				{name: "arrivedate", index: "arrivedate", width: 200, label: "预计到货日期", sortable: true, align: "left", formatter: formatTime},
				{name: "statusdescr", index: "statusdescr", width: 200, label: "采购单状态", sortable: true, align: "left"},
				{name: "qtycal", index: "qtycal", width: 150, label: "采购数量", sortable: true, align: "right", sum: true},
				{name: "arrivqty", index: "arrivqty", width: 200, label: "已到货数量", sortable: true, align: "right", sum: true}
            ]
		});
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_dhfx_main",{
			url:"${ctx}/admin/customerXx/goJqGrid_dhfx_main",
			height:170,
			rowNum: 10000,
			colModel : [
			    {name: "itemtype1descr", index: "itemtype1descr", width: 100, label: "材料类型1", sortable: true, align: "left", count: true},
				{name: "itemcode", index: "itemcode", width: 100, label: "材料编号", sortable: true, align: "left"},
				{name: "itdescr", index: "itdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
				{name: "sqldescr", index: "sqldescr", width: 120, label: "品牌", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 100, label: "需求数量", sortable: true, align: "left", sum: true},
				{name: "sendqty", index: "sendqty", width: 100, label: "发货数量", sortable: true, align: "left", sum: true},
				{name: "qty_sendqty", index: "qty_sendqty", width: 100, label: "待发货数量", sortable: true, align: "left", sum: true},
				{name: "qtycal", index: "qtycal", width: 70, label: "采购数量", sortable: true, align: "left", sum: true},
				{name: "arrivqty", index: "arrivqty", width: 100, label: "采购到货数量", sortable: true, align: "left", sum: true},
				{name: "useqty", index: "useqty", width: 70, label: "可用数量", sortable: true, align: "left", sum: true}
            ],
            onSelectRow : function(id) {
            	var row = selectDataTableRow("dataTable_dhfx_main");
            	$("#dataTable_dhfx_mx").jqGrid('setGridParam',{url : "${ctx}/admin/customerXx/goJqGrid_dhfx_mx?itcode="+row.itemcode+"&custCode=${customer.code}"});
            	$("#dataTable_dhfx_mx").jqGrid().trigger('reloadGrid');
            }
		});
});
</script>
<style>
<!--
.ui-jqgrid-titlebar .ui-jqgrid-caption{
width: 100%!important;
}
-->
</style>
<div class="body-box-list">
	<div class="query-form" style="border: 0px;">
		<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
			<input type="hidden" id="expired" name="expired" value="${itemReq.expired }" />
			<input type="hidden" id="custCode" name="custCode" value="${customer.code }" />
				<ul class="ul-form">
					<li style="width: 250px;">
						<label style="width: 60px;">材料类型1</label>
						<house:DataSelect id="itemType1" className="ItemType1" keyColumn="code" valueColumn="descr" 
						value="${itemReq.itemType1 }" filterValue="expired='F'" style="width:120px;"></house:DataSelect>
					</li>
					<li style="width: 150px;">
						<input type="checkbox" id="expired_show" name="expired_show" value="${itemReq.expired }" style="margin-left: 30px;"
						onclick="hideExpired(this)" ${itemReq.expired=='F'?'checked':'' }/>显示全部到货记录
					</li>
					<li style="width: 250px;">
						<button type="button" class="btn  btn-system btn-sm" onclick="goto_query('dataTable_dhfx_main');">查询</button>
						<button type="button" class="btn btn-system btn-sm" onclick="viewKcfx();">可用库存分析</button>
					</li>
				</ul>
					
		</form>
	</div>
	<!--query-form-->
	<div class="pageContent" style="padding-top: 10px;border-bottom: 1px solid #dfdfdf;">
		<table id="dataTable_dhfx_main"></table>
	</div>
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      采购明细&nbsp;&nbsp;<button type="button" class="btn btn-system" onclick="doExcelNow('采购明细','dataTable_dhfx_mx','page_form_excel')">导出excel</button>
	      </div>
	   </div>
	</div>
	<div class="pageContent" style="margin-top: 5px;border-top: 1px solid #dfdfdf;">
		<table id="dataTable_dhfx_mx"></table>
	</div>
</div>
