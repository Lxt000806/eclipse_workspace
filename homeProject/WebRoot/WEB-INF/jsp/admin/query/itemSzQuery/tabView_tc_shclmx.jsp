<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_shclmx_tc",{
        autowidth: false,
        height:220,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
			{name: "itemtype2", index: "itemtype2", width: 80, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemtype2name", index: "itemtype2name", width: 105, label: "材料类型2型名称", sortable: true, align: "left"},
			{name: "documentno", index: "documentno", width: 80, label: "凭证号", sortable: true, align: "left"},
			{name: "materialcostjs", index: "materialcostjs", width: 90, label: "材料成本结算", sortable: true, align: "right", sum: true},
			{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
			{name: "supplcodedescr", index: "supplcodedescr", width: 170, label: "供应商", sortable: true, align: "left"},
			{name: "sendtypename", index: "sendtypename", width: 80, label: "发货类型", sortable: true, align: "left"},
			{name: "no", index: "no", width: 80, label: "领料编号", sortable: true, align: "left"},
			{name: "othercost", index: "othercost", width: 70, label: "其他费用", sortable: true, align: "right"},
			{name: "othercostadj", index: "othercostadj", width: 90, label: "其他费用调整", sortable: true, align: "right"},
			{name: "projectothercost", index: "projectothercost", width: 120, label: "项目经理其他费用", sortable: true, align: "right"},
			{name: "materialcost", index: "materialcost", width: 70, label: "材料成本", sortable: true, align: "right", sum: true},
			{name: "typename", index: "typename", width: 80, label: "领料类型", sortable: true, align: "left"},
			{name: "statusname", index: "statusname", width: 80, label: "状态", sortable: true, align: "left"},
			{name: "paydate", index: "paydate", width: 120, label: "记账时间", sortable: true, align: "left", formatter: formatTime},
			{name: "worktype1name", index: "worktype1name", width: 80, label: "工种分类1", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 170, label: "备注", sortable: true, align: "left"},
			{name: "itemappremarks", index: "itemappremarks", width: 170, label: "领料单备注", sortable: true, align: "left"},
			{name: "materworktype2", index: "materworktype2", width: 105, label: "工种分类2类编号", sortable: true, align: "left"},
			{name: "materworktype2name", index: "materworktype2name", width: 84, label: "工种分类2", sortable: true, align: "left"},
			{name: "preappno", index: "preappno", width: 84, label: "预申请单号", sortable: true, align: "left"},
			{name: "workername", index: "workername", width: 84, label: "申请工人", sortable: true, align: "left"},
        ],
        gridComplete:function(){	
        	var id = $("#dataTable_shclmx_tc").jqGrid('getGridParam', 'selrow');
		    if (id) {
		      var ret = $("#dataTable_shclmx_tc").jqGrid("getRowData",id);
		      $("#dataTable_llmx_shclmx_tc").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goLlmxJqGrid",postData:{no:ret.no.trim(),custCode:$("#code").val()},page:1}).trigger("reloadGrid");
		       	
		    }
        },
        beforeSelectRow:function(rowid, e){
        	var ret = $("#dataTable_shclmx_tc").jqGrid("getRowData",rowid);
        	$("#dataTable_llmx_shclmx_tc").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goLlmxJqGrid",postData:{no:ret.no.trim(),custCode:$("#code").val()},page:1}).trigger("reloadGrid");
        }
	});
});
</script>

<table id="dataTable_shclmx_tc"></table>

<jsp:include page="tabView_llmx.jsp">
	<jsp:param value="shclmx_tc" name="fromClcbmx"/>
</jsp:include>
