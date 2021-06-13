<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<!DOCTYPE html>
<html>
<head>
	<title>领料退回-明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
		$(function(){
			Global.JqGrid.initJqGrid("returnDetailDataTable",{
				url:"${ctx}/admin/itemApp/getReturnDetailJqGrid",
				postData:{
					itemCode:"${data.itemCode}",
					custCode:"${data.custCode}"
				},
				height:400,
				rowNum: 10000,
				styleUI:"Bootstrap",
				colModel:[
					{name: "no", index: "no", width: 122, label: "退货申请单号", sortable: true, align: "left"},
					{name: "date", index: "date", width: 153, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name: "itemdescr", index: "itemdescr", width: 257, label: "材料名称", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 108, label: "数量", sortable: true, align: "right", sum: true}
				]
			}); 
		});
	</script>
</head>
    
<body>
	<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState }"/>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">退出</button>
				</div>
			</div>
		</div>
		<div>
			<table id="returnDetailDataTable"></table>
		</div>
	</div>
</body>
</html>


