<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_ykmx_tc",{
		url:"${ctx}/admin/itemSzQuery/goYkmxJqGrid",
		postData:{custCode:$("#code").val()},
        autowidth: false,
        height:510,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left", hidden: true},
			{name: "custcode", index: "custcode", width: 94, label: "客户编号", sortable: true, align: "left", hidden: true},
			{name: "worktype1descr", index: "worktype1descr", width: 100, label: "工种分类1", sortable: true, align: "left"},
			{name: "worktype2descr", index: "worktype2descr", width: 100, label: "工种分类2", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 80, label: "类型", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 80, label: "金额", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
			{name: "iscreatedescr", index: "iscreatedescr", width: 80, label: "是否生成", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新日期", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 90, label: "过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 90, label: "操作", sortable: true, align: "left"}
        ]
	});
});
</script>
<table id="dataTable_ykmx_tc"></table>
