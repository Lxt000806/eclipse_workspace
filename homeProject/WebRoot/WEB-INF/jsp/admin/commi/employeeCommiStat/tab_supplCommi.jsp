<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //设计提成
		Global.JqGrid.initJqGrid("dataTable_supplCommi",{
			url: "${ctx}/admin/commiCycle/goSupplierRebateJqGrid",          
			postData:{empCode:"${employee.number}",dateFrom: $("#dateFrom").val(), dateTo :$("#dateTo").val() },
			rowNum: 100000000,
			height:400,
			colModel : [
				{name: "pk", index: "pk", width: 50, label: "PK", sortable: true, align: "left", hidden: true},
				{name: "date", index: "date", width: 80, label: "销售日期", sortable: true, align: "left", formatter: formatDate},
				{name: "itemtype1", index: "itemtype1", width: 80, label: "材料类型1", sortable: true, align: "left", hidden: true},
				{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
				{name: "supplcode", index: "supplcode", width: 80, label: "供应商名称", sortable: true, align: "left", hidden: true},
				{name: "suppldescr", index: "suppldescr", width: 120, label: "供应商名称", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 120, label: "材料名称", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 100, label: "返利总金额", sortable: true, align: "right",sum:true},
				//{name: "empcode", index: "empcode", width: 80, label: "干系人", sortable: true, align: "left", hidden: true},
				//{name: "empname", index: "empname", width: 80, label: "干系人", sortable: true, align: "left"},
				{name: "commiamount", index: "commiamount", width: 100, label: "业务员提成", sortable: true, align: "right",sum:true},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
            ],
		});
});
</script>
<div class="body-box-list">
	<div class="panel panel-system">
	</div>
	<div class="pageContent">
		<table id="dataTable_supplCommi"></table>
	</div>
</div>
