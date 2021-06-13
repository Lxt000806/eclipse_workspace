<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!--套餐内主材奖惩  -->
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_tc_tcnzcjc",{
		url:"${ctx}/admin/itemSzQuery/goTcnzcjcJqGrid",
		postData:{code:$("#code").val()},
        autowidth: false,
        height:510,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "fixareadescr", index: "fixareadescr", width: 100, label: "区域名称", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 250, label: "材料名称", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "right"},
			{name: "sendqty", index: "sendqty", width: 80, label: "发货数量", sortable: true, align: "right"},
			{name: "projectcost", index: "projectcost", width: 80, label: "结算价", sortable: true, align: "right"},
			{name: "jcje", index: "jcje", width: 80, label: "奖惩金额", sortable: true, align: "right", sum: true},
        ]
	});
});
</script>
<table id="dataTable_tc_tcnzcjc"></table>
