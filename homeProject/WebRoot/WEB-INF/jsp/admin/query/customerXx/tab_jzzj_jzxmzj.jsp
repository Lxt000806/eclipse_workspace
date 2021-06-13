<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_jzzj_jzxmzjmx",{
			height:180,
			rowNum: 10000,
			//caption: "基础增减明细&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"doExcelNow('基础增减明细','dataTable_jzzj_jzxmzjmx','page_form_excel')\">【导出excel】</a>",
			colModel : [
			    {name: "fixareadescr", index: "fixareadescr", width: 100, label: "区域名称", sortable: true, align: "left", count: true},
				{name: "baseitemdescr", index: "baseitemdescr", width: 150, label: "基装项目名称", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right", sum: true},
				{name: "uom", index: "uom", width: 70, label: "单位", sortable: true, align: "left"},
				{name: "unitprice", index: "unitprice", width: 80, label: "人工单价", sortable: true, align: "right"},
				{name: "material", index: "material", width: 80, label: "材料单价", sortable: true, align: "right"},
				{name: "cost", index: "cost", width: 70, label: "成本", sortable: true, align: "right", hidden: ${costRight=='0'?'true':'false'}},
				{name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "right", sum: true},
				{name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
            ]
		});
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_jzzj_jzxmzj",{
			url:"${ctx}/admin/customerXx/goJqGrid_jzzj_jzxmzj",
			postData:{custCode:'${customer.code}'},
			height:180,
			rowNum: 10000,
			colModel : [
			    {name: "no", index: "no", width: 100, label: "批次号", sortable: true, align: "left", count: true},
				{name: "statusdescr", index: "statusdescr", width: 90, label: "基装增减状态", sortable: true, align: "left"},
				{name: "date", index: "date", width: 150, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
				{name: "befamount", index: "befamount", width: 80, label: "优惠前金额", sortable: true, align: "left", sum: true},
				{name: "discamount", index: "discamount", width: 70, label: "优惠金额", sortable: true, align: "left", sum: true},
				{name: "amount", index: "amount", width: 70, label: "实际总价", sortable: true, align: "left", sum: true},
				{name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
            ],
            onSelectRow : function(id) {
            	var row = selectDataTableRow("dataTable_jzzj_jzxmzj");
            	$("#dataTable_jzzj_jzxmzjmx").jqGrid('setGridParam',{url : "${ctx}/admin/customerXx/goJqGrid_jzzj_jzxmzjmx?no="+row.no+"&custCode="+row.custcode});
            	$("#dataTable_jzzj_jzxmzjmx").jqGrid().trigger('reloadGrid');
            },
            gridComplete:function(){
            	var ids = $("#dataTable_jzzj_jzxmzj").getDataIDs();
				var rows = $("#dataTable_jzzj_jzxmzj").jqGrid("getRowData");
				var befamount=discamount=amount=0;
				for(var i=0;i<ids.length;i++){
				 	if(rows[i].statusdescr!="取消"){
				 		befamount+=parseFloat(rows[i].befamount);
				 		discamount+=parseFloat(rows[i].discamount);
				 		amount+=parseFloat(rows[i].amount);
				 	}
				 	$("#dataTable_jzzj_jzxmzj").footerData('set', {"befamount":befamount});
				 	$("#dataTable_jzzj_jzxmzj").footerData('set', {"discamount": discamount});
				 	$("#dataTable_jzzj_jzxmzj").footerData('set', {"amount": amount});
				 }
            }
            
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;border-bottom: 1px solid #dfdfdf;">
		<table id="dataTable_jzzj_jzxmzj"></table>
	</div>
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      基础增减明细&nbsp;&nbsp;<button type="button" class="btn btn-system" onclick="doExcelNow('基础增减明细','dataTable_jzzj_jzxmzjmx','page_form_excel')">导出excel</button>
	      </div>
	   </div>
	</div>
	<div class="pageContent" style="padding-top: 10px;border-top: 1px solid #dfdfdf;">
		<table id="dataTable_jzzj_jzxmzjmx"></table>
	</div>
</div>
