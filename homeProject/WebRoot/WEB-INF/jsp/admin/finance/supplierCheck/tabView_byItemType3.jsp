<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initEditJqGrid("dataTableByItemType3", {
		height: 255,
		url: "${ctx}/admin/supplierCheck/goJqGridMainItemByItemType3?checkOutNo="+$("#no").val(),
		rowNum: 10000,
		styleUI: "Bootstrap",
		colModel:[
			{name: "itemtype3", index: "itemtype3", width: 150, label: "材料类型3", sortable: true, align: "left", hidden: true},
			{name: "itemtype3descr", index: "itemtype3descr", width: 150, label: "材料类型3", sortable: true, align: "left", },
			{name: "amount", index: "amount", width: 80, label: "总金额", sortable: true, align: "left",sum:true},
			{name: "projectcost", index: "projectcost", width: 115, label: "项目经理结算总价", sortable: true, align: "left",sum:true},
		],
		loadonce: true,
	}); 
});	
</script>
<input type="hidden" id="firstFlag" name="firstFlag" value="1"/>
<input type="hidden" id="maxCheckSeq" name="maxCheckSeq" value="0"/>
<div id="content-list-itemType3">
	<table id="dataTableByItemType3"></table>
</div>
