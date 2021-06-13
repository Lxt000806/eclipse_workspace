<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_jcbg",{
		url:"${ctx}/admin/itemSzQuery/goJcbgJqGrid",
		postData:{custCode:$("#code").val()},
		height:510,
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "no", index: "no", width: 120, label: "增减单号", sortable: true, align: "left"},
			{name: "date", index: "date", width: 120, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
			{name: "setadd", index: "setadd", width: 120, label: "套餐外基础增项", sortable: true, align: "right", sum: true},
			{name: "setminus", index: "setminus", width: 120, label: "套餐内减项", sortable: true, align: "right", sum: true},
			{name: "managefee", index: "managefee", width: 120, label: "基础管理费", sortable: true, align: "right", sum: true},
			{name: "befamount", index: "befamount", width: 120, label: "优惠前金额", sortable: true, align: "right"},
			{name: "discamount", index: "discamount", width: 120, label: "优惠", sortable: true, align: "right"},
			{name: "amount", index: "amount", width: 120, label: "实际总价", sortable: true, align: "right"}
        ]
	});
});
</script>

<table id="dataTable_jcbg"></table>
