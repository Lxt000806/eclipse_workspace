<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<% String fromFlag = request.getParameter("fromFlag"); %>

	<script type="text/javascript">
	$(function(){
	 	Global.JqGrid.initJqGrid("dataTable_act",{
			rowNum: 10000,
			url:"${ctx}/admin/laborFee/goDetailActNameJqGrid",
			height:270,
			postData:{no:$.trim("${laborFee.no }")},
			styleUI:"Bootstrap",
			colModel:[
				{name: "actname", index: "actname", width: 117, label: "户名", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 235, label: "卡号", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 127, label: "金额", sortable: true, align: "right", sum: true}
			],
		});
	});
	</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div id="content-list">
		<table id="dataTable_act"></table>
	</div>
</div>
