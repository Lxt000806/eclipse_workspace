<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var gridOption = {
		url: "${ctx}/admin/supplierCheck/goJqGridMainItemByCustDept?checkOutNo="+$("#no").val(),
		height:255,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'address', index:'address', width:180, label:'楼盘', sortable:true ,align:"left", },
			{name:'netchaneldescr', index:'netchaneldescr', width:80, label:'渠道', sortable:true ,align:"left", },
			{name:'department', index:'department', width:80, label:'部门', sortable:true ,align:"left", },
			{name:'cost', index:'cost', width:80, label:'金额', sortable:true ,align:"right",sum:true },
			{name:'abstract', index:'abstract', width:500, label:'摘要', sortable:true ,align:"left", },
		],
		loadonce: true,
	};
	Global.JqGrid.initJqGrid("dataTableByCustDept",gridOption);

});
 </script>
 <input type="hidden" id="firstFlag" name="firstFlag" value="1"/>
<input type="hidden" id="maxCheckSeq" name="maxCheckSeq" value="0"/>
<div id="content-list-custDept">
	<table id="dataTableByCustDept"></table>
</div>




