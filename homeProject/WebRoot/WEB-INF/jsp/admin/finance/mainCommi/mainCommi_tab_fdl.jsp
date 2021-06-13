<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/mainCommi/goFdlJqGrid",
			postData:{no:"${no}"},
			height:450,
			rowNum:10000000,
			colModel : [
				{name: "pk", index: "pk", width: 70, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
				{name: "signdate", index: "signdate", width: 90, label: "签订日期", sortable: true, align: "left", formatter: formatDate},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
				{name: "checkamount_inset", index: "checkamount_inset", width: 70, label: "套内结算", sortable: true, align: "right",sum:true},
				{name: "mainplanamount_outset", index: "mainplanamount_outset", width: 100, label: "主材套外预算", sortable: true, align: "right",sum:true},
				{name: "mainchgamount_outset", index: "mainchgamount_outset", width: 100, label: "主材套外增减", sortable: true, align: "right",sum:true},
				{name: "maincheckamount_outset", index: "maincheckamount_outset", width: 100, label: "主材套外结算", sortable: true, align: "right",sum:true},
				{name: "servplanamount_outset", index: "servplanamount_outset", width: 120, label: "服务性套外预算", sortable: true, align: "right",sum:true},
				{name: "servchgamount_outset", index: "servchgamount_outset", width: 120, label: "服务性套外增减", sortable: true, align: "right",sum:true},
				{name: "servcheckamount_outset", index: "servcheckamount_outset", width: 120, label: "服务性套外结算", sortable: true, align: "right",sum:true},
				{name: "checkamount", index: "checkamount", width: 70, label: "结算总额", sortable: true, align: "right",sum:true},
				{name: "checkamount_centralpurch", index: "checkamount_centralpurch", width: 100, label: "集采材料结算", sortable: true, align: "right",sum:true},
				{name: "managercommi", index: "managercommi", width: 70, label: "经理提成", sortable: true, align: "right",sum:true},
				{name: "mainbusimandescr", index: "mainbusimandescr", width: 70, label: "主材管家", sortable: true, align: "left"},
				{name: "mainbusimancommi", index: "mainbusimancommi", width: 70, label: "管家提成", sortable: true, align: "right",sum:true},
				{name: "declaremancommi", index: "declaremancommi", width: 70, label: "报单提成", sortable: true, align: "right",sum:true},
				{name: "checkmancommi", index: "checkmancommi", width: 90, label: "结算员提成", sortable: true, align: "right",sum:true},
				{name: "directormancommi", index: "directormancommi", width: 90, label: "主管提成", sortable: true, align: "right",sum:true},
				{name: "deptfundcommi", index: "deptfundcommi", width: 70, label: "部门基金", sortable: true, align: "right",sum:true},
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
	   Global.JqGrid.initJqGrid("fdlDataTable",gridOption);
});


</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="fdlDataTable"></table>
	</div>
</div>



