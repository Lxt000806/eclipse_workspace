<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_dlxsmx",{
		url:"${ctx}/admin/mainBusiManPerf/goDlxsmxJqGrid",
		postData:{dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val(),custType:$("#custType").val(),empCode:$("#empCode").val()},
        autowidth: false,
        rowNum:10000000,
        height:400,
		styleUI: 'Bootstrap',
		colModel : [
			{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
			{name: "zcdlxs",index : "zcdlxs",width : 90,label:"付款金额",sortable : true,align : "right",sum:true},
			{name: "dqdlxs",index : "dqdlxs",width : 90,label:"电器销售额",sortable : true,align : "right",sum:true},
			{name: "date", index: "date", width: 120, label: "付款时间", sortable: true, align: "left", formatter: formatTime},
			{name: "custcheckdate", index: "custcheckdate", width: 120, label: "客户结算时间", sortable: true, align: "left", formatter: formatTime},
        ],
        loadonce:true
	});
});
</script>
<table id="dataTable_dlxsmx"></table>
