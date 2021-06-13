<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_yshfbdemx",{
		styleUI: 'Bootstrap',
		rowNum: 10000,
        autowidth: false,
        height:510,
		width:1259, 
		colModel : [
			{name: "code", index: "code", width: 90, label: "基装项目编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 180, label: "基装项目名称", sortable: true, align: "left"},
			{name: "fixareaname", index: "fixareaname", width: 100, label: "装修区域", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right"},
			{name: "cost", index: "cost", width: 70, label: "单价", sortable: true, align: "right"},
			{name: "preamount", index: "preamount", width: 70, label: "预算金额", sortable: true, align: "right", sum: true},
			{name: "prefbamount", index: "prefbamount", width: 90, label: "预算发包定额", sortable: true, align: "right", sum: true},
			{name: "worktype2", index: "worktype2", width: 110, label: "工种分类2类编号", sortable: true, align: "left"},
			{name: "worktype2name", index: "worktype2name", width: 80, label: "工种分类2", sortable: true, align: "left"},
			{name: "worktype1name", index: "worktype1name", width: 80, label: "工种分类1", sortable: true, align: "left"}
        ]
	});
});
</script>

<table id="dataTable_yshfbdemx"></table>
