<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<% String fromFlag = request.getParameter("fromFlag"); %>

	<script type="text/javascript">
	$(function(){
	 	Global.JqGrid.initJqGrid("dataTable_act",{
			rowNum: 10000,
			url:"${ctx}/admin/laborFee/goDetailCompanyJqGrid",
			height:270,
			postData:{no:$.trim("${laborFee.no }")},
			styleUI:"Bootstrap",
			colModel:[
				{name: "company", index: "company", width: 117, label: "公司", sortable: true, align: "left"},
				{name: "type", index: "type", width: 117, label: "类型", sortable: true, align: "left"},
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
