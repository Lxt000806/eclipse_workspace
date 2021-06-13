<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>供应商结算集成安装费页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("dataTable",{
				height:330,
				styleUI:"Bootstrap",
				url:"${ctx}/admin/supplierCheck/goJqGridIntInstall",
				postData:{
					custCode:"${data.custCode}"
				},
				rowNum:10000,
				colModel:[
					{name: "no", index: "no", width: 100, label: "费用申请批号", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 70, label: "材料类型1", sortable: true, align: "left"},
					{name: "statusdescr", index: "statusdescr", width: 60, label: "状态", sortable: true, align: "left"},
					{name: "actname", index: "actname", width: 80, label: "户名", sortable: true, align: "left"},
					{name: "pk", index: "pk", width: 80, label: "费用明细PK", sortable: true, align: "left"},
					{name: "feetypedescr", index: "feetypedescr", width: 70, label: "费用类型", sortable: true, align: "left"},
					{name: "amount", index: "amount", width: 60, label: "金额", sortable: true, align: "right", sum: true},
					{name: "iano", index: "iano", width: 70, label: "领料单号", sortable: true, align: "left"},
					{name: "sendno", index: "sendno", width: 80, label: "发货单号", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 120, label: "明细备注", sortable: true, align: "left"}
				]
			});
		});
		</script>
	</head>
	<body>
		<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState}"/>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>	
					</div>
				</div>
			</div>
			<table id="dataTable"></table>
		</div>
	</body>
</html>
