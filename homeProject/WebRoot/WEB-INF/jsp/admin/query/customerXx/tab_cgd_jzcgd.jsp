<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_cgd_${param.itemType1 }mx",{
			height:200,
			rowNum: 10000,
			//caption: "${param.title }采购明细&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"doExcelNow('${param.title }采购明细','dataTable_cgd_${param.itemType1 }mx','page_form_excel')\">【导出excel】</a>",
			colModel : [
				{name: "pk", index: "pk", width: 90, label: "编号", sortable: true, align: "left", count: true, hidden: true},
				{name: "itcode", index: "itcode", width: 100, label: "材料编号", sortable: true, align: "left"},
				{name: "itdescr", index: "itdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
				{name: "sqlcodedescr", index: "sqlcodedescr", width: 100, label: "品牌", sortable: true, align: "left"},
				{name: "qtycal", index: "qtycal", width: 90, label: "采购数量", sortable: true, align: "right"},
				{name: "arrivqty", index: "arrivqty", width: 90, label: "到货数量", sortable: true, align: "right"},
				{name: "unitdescr", index: "unitdescr", width: 90, label: "单位", sortable: true, align: "left"},
				{name: "unitprice", index: "unitprice", width: 90, label: "单价", sortable: true, align: "right",hidden:${costRight=='0'?'true':'false'}},
				{name: "amount", index: "amount", width: 100, label: "总价", sortable: true, align: "right", sum: true,hidden:${costRight=='0'?'true':'false'}},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"}
            ]
		});
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_cgd_${param.itemType1 }",{
			url:"${ctx}/admin/customerXx/goJqGrid_cgd_jzcgd",
			postData:{custCode:'${customer.code}',itemType1:'${param.itemType1 }'},
			height:150,
			rowNum: 10000,
			colModel : [
			    {name: "no", index: "no", width: 80, label: "采购单号", sortable: true, align: "left", count: true},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "采购单状态", sortable: true, align: "left"},
				{name: "date", index: "date", width: 125, label: "采购日期", sortable: true, align: "left", formatter: formatTime},
				{name: "appitemdate", index: "appitemdate", width: 125, label: "订货日期", sortable: true, align: "left", formatter: formatTime},
				{name: "arrivedate", index: "arrivedate", width: 125, label: "预计到货日期", sortable: true, align: "left", formatter: formatTime,hidden:${param.itemType1=='JZ'?'false':'true'}},
				{name: "typedescr", index: "typedescr", width: 70, label: "采购类型", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 67, label: "材料类型1", sortable: true, align: "left"},
				{name: "isservicedescr", index: "isservicedescr", width: 90, label: "是否服务产品", sortable: true, align: "left"},
				{name: "delivtypedescr", index: "delivtypedescr", width: 80, label: "配送地点", sortable: true, align: "left"},
				{name: "warehouse", index: "warehouse", width: 80, label: "仓库名称", sortable: true, align: "left"},
				{name: "suppldescr", index: "suppldescr", width: 180, label: "供应商名称", sortable: true, align: "left"},
				{name: "amountshow", index: "amountshow", width: 60, label: "金额", sortable: true, align: "left", sum: true,hidden:${costRight=='0'?'true':'false'}},
				{name: "remainamountshow", index: "remainamountshow", width: 95, label: "应付/付退余款", sortable: true, align: "left", sum: true,hidden:${costRight=='0'?'true':'false'}},
				{name: "firstamountshow", index: "firstamountshow", width: 90, label: "付/付退订金", sortable: true, align: "left", sum: true,hidden:${costRight=='0'?'true':'false'}},
				{name: "secondamountshow", index: "secondamountshow", width: 95, label: "付/付退到货款", sortable: true, align: "left", sum: true,hidden:${costRight=='0'?'true':'false'}},
				{name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left"},
				{name: "ischeckoutdescr", index: "ischeckoutdescr", width: 60, label: "是否结账", sortable: true, align: "left"},
				{name: "checkoutno", index: "checkoutno", width: 80, label: "结账单号", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 125, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
            ],
            onSelectRow : function(id) {
            	var row = selectDataTableRow("dataTable_cgd_${param.itemType1 }");
            	$("#dataTable_cgd_${param.itemType1 }mx").jqGrid('setGridParam',{url : "${ctx}/admin/customerXx/goJqGrid_cgd_jzcgdmx?no="+row.no+"&custCode="+row.custcode});
            	$("#dataTable_cgd_${param.itemType1 }mx").jqGrid().trigger('reloadGrid');
            }
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;border-bottom: 1px solid #dfdfdf;">
		<table id="dataTable_cgd_${param.itemType1 }"></table>
	</div>
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      ${param.title }采购明细&nbsp;&nbsp;<button type="button" class="btn btn-system" onclick="doExcelNow('${param.title }采购明细','dataTable_cgd_${param.itemType1 }mx','page_form_excel')">导出excel</button>
	      </div>
	   </div>
	</div>
	<div class="pageContent" style="padding-top: 10px;border-top: 1px solid #dfdfdf;">
		<table id="dataTable_cgd_${param.itemType1 }mx"></table>
	</div>
</div>
