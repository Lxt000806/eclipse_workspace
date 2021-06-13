<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var gridOption = {
		url:"${ctx}/admin/giftCheckOut/goDetail_custDepaJqGrid",
		postData:{no:'${giftCheckOut.no}'},
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'address', index:'address', width:180, label:'楼盘', sortable:true ,align:"left", },
			{name:'custsource', index:'custsource', width:80, label:'客户来源', sortable:true ,align:"left", },
			{name:'netchaneldescr', index:'netchaneldescr', width:80, label:'渠道', sortable:true ,align:"left" },
			{name:'department', index:'department', width:80, label:'部门', sortable:true ,align:"left", },
			{name:'cost', index:'cost', width:80, label:'金额', sortable:true ,align:"right",formatter: 'number',formatoptions: { decimalPlaces: 2 },sum:true, },
			{name:'abstract', index:'abstract', width:500, label:'摘要', sortable:true ,align:"left", },
		],
		gridComplete:function(){
			var cost = parseFloat($("#dataTable_custDepa").getCol('cost', false, 'sum'));
			var cost = myRound(cost,2);
			$("#dataTable_custDepa").footerData('set', {"cost": cost});
		}
	};
		Global.JqGrid.initJqGrid("dataTable_custDepa",gridOption);

});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<div id="content-list">
		<table id="dataTable_custDepa" style="overflow: auto;"></table>
	</div>
</div>




