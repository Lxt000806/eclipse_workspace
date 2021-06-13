<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
	$(function(){
		initWithHoldDataTable(230);
	});
	function initWithHoldDataTable(height){
		Global.JqGrid.initJqGrid("withHoldDataTable", {
			height:height,
			url:"#",
			rowNum: 10000,
			styleUI:"Bootstrap",
			colModel:[
				{name: "no", index: "no", width: 80, label: "采购单号", sortable: true, align: "left"},
				{name: "appno", index: "appno", width: 80, label: "领料单号", sortable: true, align: "left"},
				{name: "isservicedescr", index: "isservicedescr", width: 90, label: "是否服务产品", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 100, label: "档案号", sortable: true, align: "left"},
				{name: "typedescr", index: "typedescr", width: 85, label: "采购单类型", sortable: true, align: "left"},
				{name: "xmjljszj", index: "xmjljszj", width: 120, label: "项目经理结算总价", sortable: true, align: "right", sum: true},
				{name: "xmjljsygj", index: "xmjljsygj", width: 120, label: "项目经理结算预扣价", sortable: true, align: "right"},
				{name: "xmjljscj", index: "xmjljscj", width: 70, label: "差额", sortable: true, align: "right"},
				{name: "checkstatusdescr", index: "checkstatusdescr", width: 85, label: "客户结算状态", sortable: true, align: "left"}
			],
			gridComplete:function(){
				withHoldDataTableComplete();
			}
		}); 
	}
	function withHoldDataTableComplete(){
	
		var ids = $("#withHoldDataTable").jqGrid("getDataIDs");

		if(ids.length > 0){
			$("#a_tabView_withHold").html("预扣单（"+ids.length+"）");
			$("#a_tabView_withHold").css("display", "block");
		}else{
			$("#a_tabView_withHold").css("display", "none");
		}
	}
</script>
<table id="withHoldDataTable"></table>
