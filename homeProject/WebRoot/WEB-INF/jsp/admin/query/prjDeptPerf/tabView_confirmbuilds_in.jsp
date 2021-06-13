<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_confbuilds_in",{
		url:"${ctx}/admin/PrjDeptPerf/goConfBuildsInJqGrid",
		postData:{dept2Code:$("#dept2Code").val(),dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val(),custType:$("#custType").val()},
        autowidth: false,
        rowNum:10000000,
        height:400,
		width:800, 
		styleUI: 'Bootstrap',
		colModel : [
			{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
			{name: "projectmandescr", index: "projectmandescr", width: 100, label: "项目经理", sortable: true, align: "left"},
			{name: "confirmbegin", index: "confirmbegin", width: 70, label: "开工日期", sortable: true, align: "left", formatter: formatTime},
			{name: "contractfee", index: "contractfee", width: 100, label: "合同金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
        ]
	});
});
</script>
<table id="dataTable_confbuilds_in"></table>
