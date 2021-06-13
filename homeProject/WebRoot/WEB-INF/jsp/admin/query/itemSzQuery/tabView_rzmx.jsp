<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_rzmx",{
		url:"${ctx}/admin/itemSzQuery/goRzmxJqGrid",
		postData:{custCode:$("#code").val()},
        autowidth: false,
        height:510,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "code", index: "code", width: 120, label: "材料类型编号", sortable: true, align: "left", hidden: true},
			{name: "descr", index: "descr", width: 120, label: "材料类型名称", sortable: true, align: "left"},
			{name: "预算", index: "预算", width: 90, label: "预算", sortable: true, align: "right", sum: true},
			{name: "增减项", index: "增减项", width: 90, label: "增减项", sortable: true, align: "right", sum: true},
			{name: "总价", index: "总价", width: 110, label: "总价", sortable: true, align: "right", sum: true},
			{name: "毛利率", index: "毛利率", width: 90, label: "毛利率", sortable: true, align: "right", sum: true},
			{name: "控制线", index: "控制线", width: 90, label: "控制线", sortable: true, align: "right", sum: true},
			{name: "材料成本", index: "材料成本", width: 90, label: "材料成本", sortable: true, align: "right", sum: true},
			{name: "人工费用", index: "人工费用", width: 90, label: "人工费用", sortable: true, align: "right", sum: true},
			{name: "成本小计", index: "成本小计", width: 90, label: "成本小计", sortable: true, align: "right", sum: true},
			{name: "余额", index: "余额", width: 90, label: "余额", sortable: true, align: "right", sum: true}
        ]
	});
});
</script>

<table id="dataTable_rzmx"></table>
