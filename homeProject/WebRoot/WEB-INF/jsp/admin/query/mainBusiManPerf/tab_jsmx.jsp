<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_jsmx",{
		url:"${ctx}/admin/mainBusiManPerf/goJsmxJqGrid",
		postData:{dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val(),custType:$("#custType").val(),empCode:$("#empCode").val()},
        autowidth: false,
        rowNum:10000000,
        height:400,
		styleUI: 'Bootstrap',
		loadonce:true,
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left",frozen : true},
			{name: "custcheckdate", index: "custcheckdate", width: 80, label: "结算时间", sortable: true, align: "left", formatter: formatDate,frozen : true},
			{name: "innerarea", index: "innerarea", width: 67, label: "套内面积", sortable: true, align: "right", sum: true},
			{name: "area", index: "area", width: 67, label: "签约面积", sortable: true, align: "right", sum: true},
			{name: "maincheck", index: "maincheck", width: 93, label: "主材结算金额", sortable: true, align: "right", sum: true},
			{name: "mainperf", index: "mainperf", width: 93, label: "主材结算业绩", sortable: true, align: "right", sum: true},
			{name: "mainprice",index : "mainprice",width : 80,label:"每平方单价",sortable : true,align : "right"},
			{name: "insetmaincheck", index: "insetmaincheck", width: 67, label: "套内结算", sortable: true, align: "right",sum:true},
			{name: "outsetmaincheck", index: "outsetmaincheck", width: 67, label: "套外结算", sortable: true, align: "right",sum:true},
			{name: "maincost",index : "maincost",width : 67,label:"主材成本",sortable : true,align : "right", sum: true},
			{name: "checkprofit", index: "checkprofit", width: 67, label: "结算利润", sortable: true, align: "right", sum: true},
			{name: "checkprofitrate", index: "checkprofitrate", width: 67, label: "结算利润率", sortable: true, align: "right"},
			{name: "mainprofit", index: "mainprofit", width: 80, label: "每平方毛利", sortable: true, align: "right"},
			{name: "checkchg", index: "checkchg", width: 93, label: "结算增减金额", sortable: true, align: "right", sum: true},
			{name: "outsetservcheck", index: "outsetservcheck", width: 104, label: "服务性套外结算", sortable: true, align: "right",sum:true},
			{name: "servcost", index: "servcost", width: 80, label: "服务性成本", sortable: true, align: "right",sum:true},
			{name: "servprofit", index: "servprofit", width: 80, label: "服务性利润", sortable: true, align: "right",sum:true},
			{name: "servprofitper", index: "servprofitper", width: 93, label: "服务性利润率", sortable: true, align: "right"},	
			{name: "whamount", index: "whamount", width: 120, label: "集采材料结算", sortable: true, align: "right", sum: true},
		],
		loadComplete: function(){
          	$('.ui-jqgrid-bdiv').scrollTop(0);
          	frozenHeightReset("dataTable_jsmx");
    	},           

	});
	$("#dataTable_jsmx").jqGrid("setFrozenColumns");
});
</script>
<table id="dataTable_jsmx"></table>
