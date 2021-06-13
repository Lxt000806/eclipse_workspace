<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_clzj_${param.itemType1 }_${param.isService }mx",{
			height:180,
			rowNum: 10000,
			//caption: "${param.title }增减明细&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"doExcelNow('${param.title }增减明细','dataTable_clzj_${param.itemType1 }_${param.isService }mx','page_form_excel')\">【导出excel】</a>",
			colModel : [
				{name: "iscommi", index: "iscommi", width: 70, label: "是否提成", sortable: true, align: "left",hidden:${param.itemType1=='ZC' && param.isService=='1'?'false':'true' }},
			    {name: "fixareadescr", index: "fixareadescr", width: 70, label: "区域名称", sortable: true, align: "left", count: true},
				{name: "intproddescr", index: "intproddescr", width: 80, label: "集成成品", sortable: true, align: "left", hidden: ${param.itemType1=='JC'?'false':'true' }},
				{name: "itemdescr", index: "itemdescr", width: 224, label: "材料名称", sortable: true, align: "left"},
				{name: "sqlcodedescr", index: "sqlcodedescr", width: 70, label: "品牌", sortable: true, align: "left",hidden:${param.itemType1=='RZ'?'false':'true' }},
				{name: "itemtype2descr", index: "itemtype2descr", width: 70, label: "材料类型2", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right", sum: true},
				{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left"},
				{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable: true, align: "left"},
				{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "right", 
					hidden: ${costRight=='1' && fn:contains(itemRight,param.itemType1)==true && unShowCost!='1'?'false':'true'}},
				{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right", sum: true},
				{name: "markup", index: "markup", width: 48, label: "折扣", sortable: true, align: "right"},
				{name: "tmplineamount", index: "tmplineamount", width: 60, label: "小计", sortable: true, align: "right",hidden:${param.itemType1=='JC'?'true':'false' }},
				{name: "processcost", index: "processcost", width: 60, label: "其它费用", sortable: true, align: "right",hidden:${param.itemType1=='JC'?'true':'false' }},
				{name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "right", sum: true},
				{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "itemsetdescr", index: "itemsetdescr", width: 100, label: "套餐包", sortable: true, align: "left",
					hidden: ${param.itemType1=='RZ'?'false':'true'}},
				// 主材预算增加提成类型、提成比例，服务性产品、软装、集成预算不用加 add by zb
				{name: "commitypedescr", index: "commitypedescr", width: 80, label: "提成类型", sortable: true, align: "left",
			  		hidden: ${costRight=='1' && param.itemType1=='ZC' && param.isService=='0'?'false':'true'}},
				{name: "commiperc", index: "commiperc", width: 80, label: "提成比例", sortable: true, align: "right",
			  		hidden: ${costRight=='1' && param.itemType1=='ZC' && param.isService=='0'?'false':'true'}},
				{name: "lastupdate", index: "lastupdate", width: 111, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 58, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
            ]
		});
		//是否有权限而且是软装
		var isRZ = "${costRight}"=="1" && "${param.itemType1}"=="RZ"?false:true;
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_clzj_${param.itemType1 }_${param.isService }",{
			url:"${ctx}/admin/customerXx/goJqGrid_clzj_zczj",
			postData:{custCode:'${customer.code}',itemType1:'${param.itemType1 }',isService:'${param.isService }'},
			height:180,
			rowNum: 10000,
			colModel : [
				{name: "no", index: "no", width: 80, label: "批次号", sortable: true, align: "left", count: true},
				{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "customerdescr", index: "customerdescr", width: 80, label: "客户名称", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 90, label: "材料增减状态", sortable: true, align: "left"},
				{name: "date", index: "date", width: 115, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
				{name: "befamount", index: "befamount", width: 80, label: "优惠前金额", sortable: true, align: "right", sum: true},
				{name: "discamount", index: "discamount", width: 80, label: "优惠金额", sortable: true, align: "right", sum: true},
				{name: "amount", index: "amount", width: 80, label: "实际总价", sortable: true, align: "right", sum: true},
				{name: "disccost", index: "disccost", width: 80, label: "产品优惠", sortable: true, align: "right", sum: true, hidden: isRZ},
				{name: "remarks", index: "remarks", width: 100, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
            ],
            onSelectRow : function(id) {
            	var row = selectDataTableRow("dataTable_clzj_${param.itemType1 }_${param.isService }");
            	$("#dataTable_clzj_${param.itemType1 }_${param.isService }mx").jqGrid('setGridParam',{url : "${ctx}/admin/customerXx/goJqGrid_clzj_zczjmx?no="+row.no+"&custCode="+row.custcode});
            	$("#dataTable_clzj_${param.itemType1 }_${param.isService }mx").jqGrid().trigger('reloadGrid');
            }
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;border-bottom: 1px solid #dfdfdf;">
		<table id="dataTable_clzj_${param.itemType1 }_${param.isService }"></table>
	</div>
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      ${param.title }增减明细&nbsp;&nbsp;<button type="button" class="btn btn-system" onclick="doExcelNow('${param.title }增减明细','dataTable_clzj_${param.itemType1 }_${param.isService }mx','page_form_excel')">导出excel</button>
	      </div>
	   </div>
	</div>
	<div class="pageContent" style="padding-top: 10px;border-top: 1px solid #dfdfdf;">
		<table id="dataTable_clzj_${param.itemType1 }_${param.isService }mx"></table>
	</div>
</div>
