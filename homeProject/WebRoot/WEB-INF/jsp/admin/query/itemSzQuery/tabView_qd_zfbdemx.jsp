<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_zfbdemx",{
        autowidth: false,
        height:510,
		width:1259, 
		rowNum: 10000,
		styleUI: 'Bootstrap',
		colModel : [
			{name: "code", index: "code", width: 100, label: "基装项目编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 220, label: "基装项目名称", sortable: true, align: "left"},
			{name: "fixareaname", index: "fixareaname", width: 90, label: "装修区域", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right", sum: true},
			{name: "cost", index: "cost", width: 70, label: "单价", sortable: true, align: "right"},
			{name: "allfbamount", index: "allfbamount", width: 80, label: "总发包定额", sortable: true, align: "right", sum: true},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外项目", sortable: true, align: "left"},
			{name: "worktype2", index: "worktype2", width: 110, label: "工种分类2类编号", sortable: true, align: "left"},
			{name: "worktype2name", index: "worktype2name", width: 80, label: "工种分类2", sortable: true, align: "left"},
			{name: "worktype1name", index: "worktype1name", width: 80, label: "工种分类1", sortable: true, align: "left"}
        ]
	});
});
</script>

<table id="dataTable_zfbdemx"></table>
