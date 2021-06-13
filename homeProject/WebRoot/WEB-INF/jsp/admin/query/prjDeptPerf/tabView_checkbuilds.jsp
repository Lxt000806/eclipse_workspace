<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_checkbuilds",{
		url:"${ctx}/admin/PrjDeptPerf/goCheckBuildsJqGrid",
		postData:{empCode:$("#empCode").val(),checkDateFrom:$("#checkDateFrom").val(),checkDateTo:$("#checkDateTo").val(),
					custType:$("#custType").val(),department2:$("#department2").val(),code:$("#code").val()},
        autowidth: false,
        rowNum:10000000,
        height:400,
		width:800, 
		styleUI: 'Bootstrap',
		colModel : [
			{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
			{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "projectmandescr", index: "projectmandescr", width: 80, label: "项目经理", sortable: true, align: "left"},
			{name: "confirmbegin", index: "confirmbegin", width: 70, label: "开工日期", sortable: true, align: "left", formatter: formatTime},
			{name: "begincheckdate", index: "begincheckdate", width: 100, label: "考核开工日期", sortable: true, align: "left", formatter:formatDate},
			{name: "endcheckdate", index: "endcheckdate", width: 100, label: "竣工验收日期", sortable: true, align: "left", formatter:formatDate},
			{name: "enddate", index: "enddate", width: 70, label: "完工日期", sortable: true, align: "left", formatter: formatTime},
			{name: "custcheckdate", index: "custcheckdate", width: 70, label: "结算日期", sortable: true, align: "left", formatter: formatTime},
			{name: "actconfday", index: "actconfday", width: 90, label: "实际施工天数", sortable: true, align: "right" },
			{name: "constructday", index: "constructday", width: 70, label: "施工工期", sortable: true, align: "right"},
			{name: "checkamount", index: "checkamount", width: 70, label: "结算金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
			{name: "checkperf", index: "checkperf", width: 70, label: "结算业绩", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
			{name: "isontime", index: "isontime", width: 90, label: "是否按时完工", sortable: true, align: "left"},
			{name: "basechg", index: "basechg", width: 90, label: "基础增减金额", sortable: true, align: "right",sum:true},
			{name: "itemchg", index: "itemchg", width: 90, label: "材料增减金额", sortable: true, align: "right",sum:true},
        ]
	});
});
</script>
<table id="dataTable_checkbuilds"></table>
