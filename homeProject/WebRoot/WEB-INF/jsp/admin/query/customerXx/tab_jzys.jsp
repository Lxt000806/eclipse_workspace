<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_jzys",{
			url:"${ctx}/admin/customerXx/goJqGrid_jzys",
			postData:{custCode:'${customer.code}'},
			rowNum: 10000,
			height:480,
			colModel : [
			    {name: "fixareadescr", index: "fixareadescr", width: 100, label: "区域", sortable: true, align: "left",count: true},
				{name: "baseitemdescr", index: "baseitemdescr", width: 311, label: "基础项目", sortable: true, align: "left"},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外项目", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right", sum: true},
				{name: "uom", index: "uom", width: 40, label: "单位", sortable: true, align: "left"},
				{name: "unitprice", index: "unitprice", width: 70, label: "人工单价", sortable: true, align: "right"},
				{name: "material", index: "material", width: 67, label: "材料单价", sortable: true, align: "right"},
				{name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "right", sum: true},
				{name: "remark", index: "remark", width: 247, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 126, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 58, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 48, label: "操作", sortable: true, align: "left"}
            ],
            grouping: true,
            groupingView: {
                groupField: ["fixareadescr"],
                groupColumnShow: [true],
                groupText: ["<b title=\"{0}({1})\">{0}({1})</b>"],
                groupOrder: ["asc"],
                groupCollapse: false
            }
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_jzys"></table>
	</div>
</div>
