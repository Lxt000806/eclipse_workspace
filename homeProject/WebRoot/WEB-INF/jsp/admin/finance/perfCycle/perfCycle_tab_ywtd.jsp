<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
		    rowNum:10000000,
			height:325,
			searchBtn:true,
			colModel : [
				{name: "desc1", index: "desc1", width: 96, label: "名称", sortable: true, align: "left"},
				{name: "sumrecalperf", index: "sumrecalperf", width: 107, label: "业绩", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "pkperf", index: "pkperf", width: 107, label: "PK业绩", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "sumcontractfee", index: "sumcontractfee", width: 135, label: "合同额+设计费+税金", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "contractfee", index: "contractfee", width: 115, label: "合同额+税金", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "designfee", index: "designfee", width: 115, label: "设计费", sortable: true, align: "right",sorttype:"number", sum: true},
			], 
			loadonce:true
 		};
	   Global.JqGrid.initJqGrid("ywtdDataTable",gridOption);
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
		<table id="ywtdDataTable"></table>
	</div>
</div>



