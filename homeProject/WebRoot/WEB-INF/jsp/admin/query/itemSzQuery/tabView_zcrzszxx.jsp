<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_zcrzszxx",{
		url:"${ctx}/admin/itemSzQuery/goZcrzszxxJqGrid",
		postData:{custCode:$("#code").val()},
        autowidth: false,
        height:510,
		width:1200, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "pn", index: "pn", width: 130, label: "项目", sortable: true, align: "left"},
			{name: "zc", index: "zc", width: 130, label: "主材（套外）", sortable: true, align: "right"},
			{name: "fw", index: "fw", width: 130, label: "服务性（套外）", sortable: true, align: "right"},
			{name: "jc", index: "jc", width: 130, label: "集成", sortable: true, align: "right"},
			{name: "rz", index: "rz", width: 130, label: "软装", sortable: true, align: "right"},
			{name: "cl", index: "cl", width: 130, label: "窗帘", sortable: true, align: "right"},
			{name: "jj", index: "jj", width: 130, label: "家具", sortable: true, align: "right"},
			{name: "hj", index: "hj", width: 130, label: "合计", sortable: true, align: "right", sum: true}
        ]
	});
});
</script>
<div class="panel panel-info">
	<table id="dataTable_zcrzszxx" ></table>
</div>
