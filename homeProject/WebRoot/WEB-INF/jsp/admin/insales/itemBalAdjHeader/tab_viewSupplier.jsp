<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_supplier",{
			url:"${ctx}/admin/itemBalAdjDetail/goSupplierJqGrid",
			postData:{ibhNo:'${itemBalAdjHeader.no }'},
		styleUI: 'Bootstrap', 
		    height:300,
			colModel : [
				{name:'supplcode', index:'supplcode', width:75, label:'供应商编号', sortable:true ,align:"left", },
				{name:'supplierdescr', index:'supplierdescr', width:145, label:'供应商名称', sortable:true ,align:"left"},
				{name:'allcost', index:'allcost', width:65, label:'金额', sortable:true ,align:"left"},
			],
		});

});
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<li><label></label></li>
	<div id="content-list">
		<table id="dataTable_supplier" style="overflow: auto;"></table>
	</div>
</div>
