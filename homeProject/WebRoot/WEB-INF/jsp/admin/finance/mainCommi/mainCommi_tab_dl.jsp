<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
	.ui-jqgrid {
		width: 1320px !important;
	}
</style>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/mainCommi/goDlJqGrid",
			height:450,
			postData:{no:"${no}"},
			rowNum:10000000,
			colModel : [
				{name: "pk", index: "pk", width: 70, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "signdate", index: "signdate", width: 90, label: "签订日期", sortable: true, align: "left", formatter: formatDate},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
				{name: "saletypedescr", index: "saletypedescr", width: 70, label: "销售类型", sortable: true, align: "left"},
				{name: "maincheckamount", index: "maincheckamount", width: 70, label: "主材结算", sortable: true, align: "right",sum:true},
				{name: "servcheckamount", index: "servcheckamount", width: 90, label: "服务性结算", sortable: true, align: "right",sum:true},
				{name: "checkamount", index: "checkamount", width: 70, label: "结算总额", sortable: true, align: "right",sum:true},
				{name: "elecsaleamount", index: "elecsaleamount", width: 90, label: "电器销售额", sortable: true, align: "right",sum:true},
				{name: "eleccost", index: "eleccost", width: 70, label: "电器成本", sortable: true, align: "right",sum:true},
				{name: "businessmandescr", index: "businessmandescr", width: 70, label: "业务员", sortable: true, align: "left"},
				{name: "businessmancommi", index: "businessmancommi", width: 90, label: "业务员提成", sortable: true, align: "right",sum:true},
				{name: "department1descr", index: "department1descr", width: 120, label: "业务员一级部门", sortable: true, align: "left"},
				{name: "checkamount_centralpurch", index: "checkamount_centralpurch", width: 100, label: "集采材料结算", sortable: true, align: "right",sum:true},
				{name: "managercommi", index: "managercommi", width: 70, label: "经理提成", sortable: true, align: "right",sum:true},
				{name: "mainbusimandescr", index: "mainbusimandescr", width: 70, label: "主材管家", sortable: true, align: "left"},
				{name: "mainbusimancommi", index: "mainbusimancommi", width: 70, label: "管家提成", sortable: true, align: "right",sum:true},
				{name: "declaremancommi", index: "declaremancommi", width: 70, label: "报单提成", sortable: true, align: "right",sum:true},
				{name: "declaremandescr", index: "declaremandescr", width: 70, label: "报单员", sortable: true, align: "left"},
				{name: "directormancommi", index: "directormancommi", width: 70, label: "主管提成", sortable: true, align: "right",sum:true},
				{name: "totalcommi", index: "totalcommi", width: 70, label: "合计提成", sortable: true, align: "right",sum:true},
				{name: "ismodifieddescr", index: "ismodifieddescr", width: 70, label: "人工修改", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 130, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
			],
			 ondblClickRow:function(){
				view();
	         },	
	         loadonce:true
 		};
	   Global.JqGrid.initJqGrid("dlDataTable",gridOption);
});


</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="dlDataTable"></table>
	</div>
</div>



