<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_jcclcbmx",{
        autowidth: false,
        height:220,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "itemtype2descr", index: "itemtype2descr", width: 100, label: "材料类型2", sortable: true, align: "left"},
			{name: "documentno", index: "documentno", width: 110, label: "凭证号", sortable: true, align: "left"},
			{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
			{name: "supplcodedescr", index: "supplcodedescr", width: 120, label: "供应商", sortable: true, align: "left"},
			{name: "sendtypedescr", index: "sendtypedescr", width: 90, label: "发货类型", sortable: true, align: "left"},
			{name: "no", index: "no", width: 100, label: "领料单号", sortable: true, align: "left"},
			{name: "othercost", index: "othercost", width: 90, label: "其它费用", sortable: true, align: "right"},
			{name: "othercostadj", index: "othercostadj", width: 100, label: "其它费用调整", sortable: true, align: "right"},
			{name: "projectothercost", index: "projectothercost", width: 130, label: "项目经理其它费用", sortable: true, align: "right"},
			{name: "cost", index: "cost", width: 100, label: "材料成本结算", sortable: true, align: "right", sum: true},
			{name: "projectamount", index: "projectamount", width: 130, label: "项目经理材料总价", sortable: true, align: "right", sum: true, hidden: true},
			{name: "typedescr", index: "typedescr", width: 100, label: "类型", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 80, label: "状态", sortable: true, align: "left"},
			{name: "paydate", index: "paydate", width: 120, label: "记账时间", sortable: true, align: "left", formatter: formatTime},
			{name: "remarks", index: "remarks", width: 170, label: "备注", sortable: true, align: "left"},
			{name: "itemappremarks", index: "itemappremarks", width: 170, label: "领料单备注", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 100, label: "材料类型1", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 90, label: "材料成本", sortable: true, align: "left", sum: true}
        ],
        gridComplete:function(){	
        	var id = $("#dataTable_jcclcbmx").jqGrid('getGridParam', 'selrow');
		    if (id) {
		      var ret = $("#dataTable_jcclcbmx").jqGrid("getRowData",id);
		      $("#dataTable_llmx_jcclcbmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goLlmxJqGrid",postData:{no:ret.no.trim(),custCode:$("#code").val()},page:1}).trigger("reloadGrid");
		       	
		    }
        },
        beforeSelectRow:function(rowid, e){
        	var ret = $("#dataTable_jcclcbmx").jqGrid("getRowData",rowid);
        	$("#dataTable_llmx_jcclcbmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goLlmxJqGrid",postData:{no:ret.no.trim(),custCode:$("#code").val()},page:1}).trigger("reloadGrid");
        }
	});
});
</script>

<table id="dataTable_jcclcbmx"></table>

<jsp:include page="tabView_llmx.jsp">
	<jsp:param value="jcclcbmx" name="fromJcclcbmx"/>
</jsp:include>
