<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_clys",{
			url:"${ctx}/admin/customerXx/goJqGrid_clys_zcys",
			postData:{custCode:'${intPerfDetail.custCode}',itemType1:'JC',isCupboard:'${intPerfDetail.isCupboard}',isIntPerfDetail:'1'},
			rowNum: 10000,
			height:425,
			colModel : [
			    {name: "fixareadescr", index: "fixareadescr", width: 70, label: "区域名称", sortable: true, align: "left", count: true},
			    {name :"intproddescr",index : "intproddescr",width : 100,label:"集成成品",sortable : true,align : "left"},
				{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 75, label: "材料类型2", sortable: true, align: "left"},
				{name: "projectqty", index: "projectqty", width: 80, label: "预估施工量", sortable: true, align: "right", sum: true},
				{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right", sum: true},
				{name: "uom", index: "uom", width: 45, label: "单位", sortable: true, align: "left"},
				{name: "unitprice", index: "unitprice", width: 65, label: "单价", sortable: true, align: "right"},
				{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right", sum: true},
				{name: "markup", index: "markup", width: 48, label: "折扣", sortable: true, align: "right"},
				{name: "processcost", index: "processcost", width: 70, label: "其他费用", sortable: true, align: "right", sum: true},
				{name: "lineamount", index: "lineamount", width: 65, label: "总价", sortable: true, align: "right", sum: true},
				{name: "remark", index: "remark", width: 120, label: "材料描述", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 111, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 85, label: "最后修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 48, label: "操作", sortable: true, align: "left"}
            ],
            grouping: true,
            groupingView: {
                groupField: ["itemtype2descr"],
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
		<table id="dataTable_clys"></table>
	</div>
	<font color="red">优惠金额：${intPerfDetail.disc}</font>
</div>
