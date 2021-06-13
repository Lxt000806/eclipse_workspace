<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_lld_llmx",{
        autowidth: false,
        height:165,
		width:1300, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "pk", index: "pk", width: 60, label: "编号", sortable: true, align: "left",hidden:true},
			{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 241, label: "材料名称", sortable: true, align: "left"},
			{name: "no", index: "no", width: 80, label: "批次号", sortable: true, align: "left", hidden: true},
			{name: "reqpk", index: "reqpk", width: 80, label: "领料标识", sortable: true, align: "left", hidden: true},
			{name: "fixareapk", index: "fixareapk", width: 110, label: "fixareapk", sortable: true, align: "left", hidden: true},
			{name: "fixareadescr", index: "fixareadescr", width: 138, label: "装修区域", sortable: true, align: "left"},
			{name: "intprodpk", index: "intprodpk", width: 84, label: "intprodpk", sortable: true, align: "left", hidden: true},
			{name: "intproddescr", index: "intproddescr", width: 80, label: "集成产品", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 81, label: "数量", sortable: true, align: "right", sum: true},
			{name: "bcost", index: "bcost", width: 90, label: "材料成本", sortable: true, align: "right", sum: true},
			{name: "sendqty", index: "sendqty", width: 87, label: "发货数量", sortable: true, align: "right", sum: true},
			{name: "reqqty", index: "reqqty", width: 80, label: "需求数量", sortable: true, align: "right", sum: true},
			{name: "sendedqty", index: "sendedqty", width: 111, label: "总共已发货数量", sortable: true, align: "right", sum: true},
			{name: "confirmedqty", index: "confirmedqty", width: 85, label: "已审核数量", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 163, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 84, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "lastupdatedby", index: "lastupdatedby", width: 84, label: "更新人员", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 84, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 84, label: "操作", sortable: true, align: "left", hidden: true}
        ]
	});
});
</script>

<table id="dataTable_lld_llmx"></table>
