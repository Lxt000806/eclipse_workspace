<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_rgfymx_qd",{
		url:"${ctx}/admin/itemSzQuery/goRgfymxJqGrid",
		postData:{custCode:$("#code").val()},
        autowidth: false,
        height:510,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "no", index: "no", width: 120, label: "费用申请批号", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 100, label: "材料类型1", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 100, label: "状态", sortable: true, align: "left"},
			{name: "actname", index: "actname", width: 100, label: "户名", sortable: true, align: "left"},
			{name: "pk", index: "pk", width: 100, label: "费用明细PK", sortable: true, align: "right"},
			{name: "feetypedescr", index: "feetypedescr", width: 120, label: "费用类型", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 70, label: "金额", sortable: true, align: "right", sum: true},
			{name: "documentno", index: "documentno", width: 120, label: "凭证号", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 220, label: "明细备注", sortable: true, align: "left"}
        ]
	});
});
</script>

<table id="dataTable_rgfymx_qd"></table>
