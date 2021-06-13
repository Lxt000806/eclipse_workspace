<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_khhtxx_cqht",{
			url:"${ctx}/admin/customerXx/goJqGrid_khhtxx_cqht",
			postData:{custCode:'${customer.code}'},
			rowNum: 10000,
			height:450,
			colModel : [
			    {name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
			    {name: "documentno", index: "documentno", width: 80, label: "原档案号", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "原客户类型", sortable: true, align: "left"},
				{name: "contracttypedescr", index: "contracttypedescr", width: 80, label: "原合同类型", sortable: true, align: "left"},
				{name: "contractfee", index: "contractfee", width: 90, label: "原合同总金额", sortable: true, align: "right"},
				{name: "tax", index: "tax", width: 60, label: "原税金", sortable: true, align: "right"},
				{name: "designfee", index: "designfee", width: 80, label: "原设计费", sortable: true, align: "right"},
				{name: "area", index: "area", width: 60, label: "原面积", sortable: true, align: "right"},
				{name: "basefee_comp", index: "basefee_comp", width: 80, label: "基础综合费", sortable: true, align: "right"},
				{name: "basefee_dirct", index: "basefee_dirct", width: 80, label: "基础直接费", sortable: true, align: "right"},
				{name: "longfee", index: "longfee", width: 80, label: "远程费", sortable: true, align: "right"},
				{name: "mainfee", index: "mainfee", width: 80, label: "主材费", sortable: true, align: "right"},
				{name: "maindisc", index: "maindisc", width: 80, label: "主材优惠", sortable: true, align: "right"},
				{name: "mainservfee", index: "mainservfee", width: 90, label: "服务性产品费", sortable: true, align: "right"},
				{name: "softfee", index: "softfee", width: 80, label: "软装费", sortable: true, align: "right"},
				{name: "softdisc", index: "softdisc", width: 80, label: "软装优惠", sortable: true, align: "right"},
				{name: "softfee_furniture", index: "softfee_furniture", width: 80, label: "家具款", sortable: true, align: "right"},
				{name: "integratefee", index: "integratefee", width: 80, label: "集成费", sortable: true, align: "right"},
				{name: "integratedisc", index: "integratedisc", width: 80, label: "集成优惠", sortable: true, align: "right"},
				{name: "cupboardfee", index: "cupboardfee", width: 80, label: "橱柜费", sortable: true, align: "right"},
				{name: "cupboarddisc", index: "cupboarddisc", width: 80, label: "橱柜优惠", sortable: true, align: "right"},
				{name: "firstpay", index: "firstpay", width: 80, label: "首付款", sortable: true, align: "right"},
				{name: "basedisc", index: "basedisc", width: 80, label: "协议优惠", sortable: true, align: "right"},
				{name: "signdate", index: "signdate", width: 125, label: "签订时间", sortable: true, align: "left", formatter: formatTime},
				{name: "newsigndate", index: "newsigndate", width: 125, label: "重签时间", sortable: true, align: "left", formatter: formatTime},
				{name: "remarks", index: "remarks", width: 150, label: "重签说明", sortable: true, align: "left"},
				{name: "hadcalcperfdescr", index: "hadcalcperfdescr", width: 120, label: "原签单业绩统计标志", sortable: true, align: "left"},
				{name: "perfpk", index: "perfpk", width: 70, label: "业绩PK", sortable: true, align: "left"},
				{name: "hadcalcbackperfdescr", index: "hadcalcbackperfdescr", width: 100, label: "是否已扣减业绩", sortable: true, align: "left"},
				{name: "backperfpk", index: "backperfpk", width: 80, label: "扣减业绩PK", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"}
            ]
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_khhtxx_cqht"></table>
	</div>
</div>
