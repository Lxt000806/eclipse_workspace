<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!--施工补贴 -->
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_qd_sgbt",{
		url:"${ctx}/admin/itemSzQuery/goSgbtJqGrid",
		postData:{code:$("#code").val()},
        autowidth: false,
        height:510,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "BaseCheckItemDescr", index: "BaseCheckItemDescr", width: 120, label: "基础结算项目名称", sortable: true, align: "left"},
			{name: "Qty", index: "Qty", width: 80, label: "数量", sortable: true, align: "right"},
			{name: "PrjMaterial", index: "PrjMaterial", width: 120, label: "项目经理材料单价", sortable: true, align: "right"},
			{name: "PrjOfferPri", index: "PrjOfferPri", width: 120, label: "项目经理人工单价", sortable: true, align: "right"},
			{name: "Total", index: "Total", width: 80, label: "总价", sortable: true, align: "right",sum:true},
        ]
	});
});
</script>
<table id="dataTable_qd_sgbt"></table>
