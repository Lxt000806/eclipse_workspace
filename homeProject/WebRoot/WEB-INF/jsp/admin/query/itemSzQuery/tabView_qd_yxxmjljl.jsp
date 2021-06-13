<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_yxxmjljl",{
		url:"${ctx}/admin/itemSzQuery/goYxxmjljlJqGrid",
		postData:{custCode:$("#code").val()},
        autowidth: false,
        height:510,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "no", index: "no", width: 125, label: "工人工资申请编号", sortable: true, align: "left"},
			{name: "date", index: "date", width: 140, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
			{name: "applyman", index: "applyman", width: 80, label: "申请人", sortable: true, align: "left"},
			{name: "worktype1name", index: "worktype1name", width: 100, label: "工种分类1", sortable: true, align: "left"},
			{name: "worktype2", index: "worktype2", width: 105, label: "工种分类2类编号", sortable: true, align: "left"},
			{name: "worktype2name", index: "worktype2name", width: 100, label: "工种分类2", sortable: true, align: "left"},
			{name: "appamount", index: "appamount", width: 80, label: "申请金额", sortable: true, align: "right"},
			{name: "confirmamount", index: "confirmamount", width: 100, label: "人工成本", sortable: true, align: "right", sum: true},
			{name: "salarytypename", index: "salarytypename", width: 100, label: "工资类型", sortable: true, align: "left"},
			{name: "kzx", index: "kzx", width: 80, label: "控制线", sortable: true, align: "left"},
			{name: "ljjlje", index: "ljjlje", width: 100, label: "累计奖励金额", sortable: true, align: "right"},
			{name: "yue", index: "yue", width: 80, label: "余额", sortable: true, align: "right"},
			{name: "statusname", index: "statusname", width: 80, label: "状态", sortable: true, align: "left"},
			{name: "actname", index: "actname", width: 80, label: "户名", sortable: true, align: "left"},
			{name: "iswithholdname", index: "iswithholdname", width: 80, label: "是否预扣", sortable: true, align: "left"},
			{name: "withholdno", index: "withholdno", width: 90, label: "预扣单号", sortable: true, align: "left"},
			{name: "documentno", index: "documentno", width: 100, label: "凭证号", sortable: true, align: "left"},
			{name: "payczy", index: "payczy", width: 100, label: "出纳签字人", sortable: true, align: "left"},
			{name: "paydate", index: "paydate", width: 140, label: "签字日期", sortable: true, align: "left", formatter: formatTime},
			{name: "pk", index: "pk", width: 100, label: "明细流水号", sortable: true, align: "left"}
        ]
	});
});
</script>

<table id="dataTable_yxxmjljl"></table>
