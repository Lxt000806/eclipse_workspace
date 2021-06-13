<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_clzj_zjmx",{
        autowidth: false,
        height:170,
		width:1300, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "iscommi", index: "iscommi", width: 80, label: "是否提成", sortable: true, align: "center",formatter:"checkbox"},
			{name: "fixareadescr", index: "fixareadescr", width: 100, label: "区域名称", sortable: true, align: "left" },
			{name: "intproddescr", index: "intproddescr", width: 140, label: "集成成品", sortable: true, align: "left" },
			{name: "itemdescr", index: "itemdescr", width: 140, label: "材料名称", sortable: true, align: "left"},
			{name: "sqlcodedescr", index: "sqlcodedescr", width: 140, label: "品牌", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 100, label: "材料类型2", sortable: true, align: "left" },
			{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right", sum: true },
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left" },
			{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable: true, align: "right" },
			{name: "cost", index: "cost", width: 60, label: "cost", sortable: true, align: "right" },
			{name: "beflineamount", index: "beflineamount", width: 60, label: "折扣前金额", sortable: true, align: "right", sum: true },
			{name: "markup", index: "markup", width: 60, label: "折扣", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return Math.ceil(cellvalue)+"%";}},
			{name: "tmplineamount", index: "tmplineamount", width: 60, label: "小计", sortable: true, align: "right"},
			{name: "processcost", index: "processcost", width: 60, label: "其他费用", sortable: true, align: "right"},
			{name: "lineamount", index: "lineamount", width: 60, label: "总价", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 60, label: "操作", sortable: true, align: "left"}
        ]
	});
});
</script>

<table id="dataTable_clzj_zjmx"></table>
