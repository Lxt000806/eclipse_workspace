<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var gridOption = {
		url:"${ctx}/admin/giftCheckOut/goDetail_depaJqGrid",
		postData:{no:'${giftCheckOut.no}'},
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'department', index:'department', width:80, label:'部门', sortable:true ,align:"left" },
			{name:'netchaneldescr', index:'netchaneldescr', width:80, label:'渠道', sortable:true ,align:"left" },
			{name:'cost', index:'cost', width:80, label:'金额', sortable:true ,align:"right",sum:true,formatter: 'number',formatoptions: { decimalPlaces: 2 } },
		],
		gridComplete:function(){
			var cost = parseFloat($("#dataTable_depa").getCol('cost', false, 'sum'));
			cost = myRound(cost,2);
			$("#dataTable_depa").footerData('set', {"cost": cost});
		}
	};
		Global.JqGrid.initJqGrid("dataTable_depa",gridOption);

});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<div id="content-list">
		<table id="dataTable_depa" style="overflow: auto;"></table>
	</div>
</div>




