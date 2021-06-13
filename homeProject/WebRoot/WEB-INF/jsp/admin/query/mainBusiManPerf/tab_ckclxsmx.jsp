<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_ckclxsmx",{
		// url:"${ctx}/admin/mainBusiManPerf/goCkclxsmxJqGrid",
		postData:{dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val(),custType:$("#custType").val(),empCode:$("#empCode").val()},
        autowidth: false,
        rowNum:10000000,
        height:400,
		styleUI: 'Bootstrap',
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
			{name: "whamount", index: "whamount", width: 120, label: "集采材料结算", sortable: true, align: "right", sum: true}
		],
		loadonce:true
	});
});
</script>
<table id="dataTable_ckclxsmx"></table>
