<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var gridOption = {
		url:"${ctx}/admin/supplierHdmpgl/goDetail_giftJqGrid",
		postData:{ticketNo:'${hdmpgl.ticketNo}',actNo:'${hdmpgl.actNo}'},
		height:$(document).height()-$("#content-list").offset().top-110,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'actname', index:'actname', width:120, label:'活动名称', sortable:true ,align:"left", },
			{name:'ticketno', index:'ticketno', width:80, label:'门票号', sortable:true ,align:"left",hidden:true },
			{name:'custdescr', index:'custdescr', width:80, label:'客户名称', sortable:true ,align:"left",hidden:true  },
			{name:'address', index:'address', width:150, label:'楼盘地址', sortable:true ,align:"left",hidden:true  },
			{name:'itemdescr', index:'itemdescr', width:110, label:'礼品名称', sortable:true ,align:"left" },
			{name:'qty', index:'qty', width:80, label:'礼品数量', sortable:true ,align:"right" },
			{name:'uomdescr', index:'uomdescr', width:60, label:'单位', sortable:true ,align:"left" },
			{name:'lastupdate', index:'lastupdate', width:100, label:'最后修改时间', sortable:true ,align:"left",formatter: formatTime },
			{name:'lastupdatedby', index:'lastupdatedby', width:110, label:'最后修改人', sortable:true ,align:"left",hidden:true},
			{name:'zwxm', index:'zwxm', width:80, label:'最后修改人', sortable:true ,align:"left" },
			
		],
	};
		Global.JqGrid.initJqGrid("dataTable_gift",gridOption);

});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<div id="content-list">
		<table id="dataTable_gift" style="overflow: auto;"></table>
	</div>
</div>




