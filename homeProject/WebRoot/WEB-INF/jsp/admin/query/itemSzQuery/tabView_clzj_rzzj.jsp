<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_clzj_rzzj",{
		url:"${ctx}/admin/itemSzQuery/goClzjJqGrid",
		postData:{custCode:$("#code").val(),type:"RZ"},
        autowidth: false,
        height:205,
		width:1300, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "no", index: "no", width: 100, label: "批次号", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 70, label: "材料类型1", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "customerdescr", index: "customerdescr", width: 70, label: "客户名称", sortable: true, align: "left"},
			{name: "address", index: "address", width: 100, label: "楼盘", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 95, label: "材料增减状态", sortable: true, align: "left"},
			{name: "date", index: "date", width: 120, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
			{name: "befamount", index: "befamount", width: 80, label: "优惠前金额", sortable: true, align: "right", sum: true},
			{name: "discamount", index: "discamount", width: 70, label: "优惠金额", sortable: true, align: "right", sum: true},
			{name: "amount", index: "amount", width: 70, label: "实际总价", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 50, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 60, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 40, label: "操作", sortable: true, align: "left"}
        ],
        gridComplete:function(){
        	$("#dataTable_clzj_zjmx").jqGrid("clearGridData");
			var ret = selectDataTableRow("dataTable_clzj_rzzj");
			if(ret){
				$("#dataTable_clzj_zjmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goClzjZjmxJqGrid",postData:{no:ret.no,},page:1}).trigger("reloadGrid");
			}
        },
        beforeSelectRow:function(rowid, e){
			var ret = $("#dataTable_clzj_rzzj").jqGrid("getRowData",rowid);
			if(ret){
				$("#dataTable_clzj_zjmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goClzjZjmxJqGrid",postData:{no:ret.no,},page:1}).trigger("reloadGrid");
			}
        }
	});
});
</script>

<table id="dataTable_clzj_rzzj"></table>
