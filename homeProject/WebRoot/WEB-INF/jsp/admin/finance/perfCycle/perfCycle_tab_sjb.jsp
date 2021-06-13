<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
		    rowNum:10000000,
			height:315,
			searchBtn:true,
			colModel : [
				{name: "dept1descr", index: "dept1descr", width: 116, label: "一级部门", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 116, label: "二级部门", sortable: true, align: "left"},
				{name: "sumrecalperf", index: "sumrecalperf", width: 107, label: "业绩", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "pkperf", index: "pkperf", width: 107, label: "PK业绩", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "sumcontractfee", index: "sumcontractfee", width: 135, label: "合同额+设计费+税金", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "contractfee", index: "contractfee", width: 115, label: "合同额+税金", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "designfee", index: "designfee", width: 115, label: "设计费", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "num", index: "num", width: 86, label: "名次", sortable: true, align: "right"},
				{name: "laderdescr", index: "laderdescr", width: 96, label: "部门领导", sortable: true, align: "left"},
				{name: "posichgdate", index: "posichgdate", width: 109, label: "任命时间", sortable: true, align: "left", formatter: formatDate}
			], 
			loadonce:true
 		};
	   Global.JqGrid.initJqGrid("sjbDataTable",gridOption);
});
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="sjbDataTable"></table>
	</div>
</div>



