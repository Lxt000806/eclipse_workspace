<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>采购跟踪——延期</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>


</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
			<!-- panelBar -->
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
			</div>
			</div>
			</div>
			<div class="infoBox" id="infoBoxDiv"></div>
			<div class="edit-form" id="edit-form">
				<form action="" mothod="post" id="dataForm">
					<house:token></house:token>
					<table cellspacing="0" cellpadding="0" width="100%">
						<col width="72">
						<col width="150">
						<tbody>
							<tr hidden="true">
								<td class="td-label">no</td>
								<td class="td-value">
									<input type="text" id="no" name="no" style="width:160px;" value="${purchArr.no }" readonly="readonly"/>
								</td>
								<td class="td-label">puno</td>
								<td class="td-value">
									<input type="text" id="puno" name="puno" style="width:160px;" value="${purchArr.puno }" readonly="readonly"/>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
			<div id="content-list">
				<table id="dataTable1"></table>
			</div>
	</div>
</div>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$("#tabs").tabs();
$(function(){
		Global.JqGrid.initJqGrid("dataTable1",{
		height:250,
		rowNum:10000000,
		colModel : [
				{name:'ITCode', index:'ITCode', width:70, label:'材料编号', sortable:true ,align:"left",},
				{name:'itdescr', index:'itdescr', width:200, label:'材料名称', sortable:true ,align:"left",},
				{name:'ArrivQty', index:'ArrivQty', width:70, label:'到货数量', sortable:true ,align:"right", sum:true},
				{name:'ApportionFee', index:'ApportionFee', width:80, label:'费用分摊金额', sortable:true ,align:"right", sum:true},
				//{name:'whdescr', index:'whdescr', width:150, label:'到货仓库', sortable:true ,align:"right", sum:true},
				{name:'LastUpdate', index:'LastUpdate', width:150, label:'最后更新时间', sortable:true ,align:"left",formatter: formatTime},
				{name:'LastUpdatedBy', index:'LastUpdatedBy', width:80, label:'最后更新人员', sortable:true ,align:"left",},
				{name:'ActionLog', index:'ActionLog', width:80, label:'操作', sortable:true ,align:"left",},
			],
	});
	//初始化表格  
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchArr/goJqGrid",
		postData:{puno:'${purchArr.no }'},
		height:150,
		rowNum:10000000,
		colModel : [
			{name:'No', index:'No', width:110, label:'采购到货流水号', sortable:true ,align:"left",},
			{name:'PUNo', index:'PUNo', width:80, label:'采购单号', sortable:true ,align:"left",},
			//{name:'ArriveWHCode', index:'ArriveWHCode', width:150, label:'到货仓库', sortable:true ,align:"left",},
			{name:'whcodedescr', index:'whcodedescr', width:150, label:'到货仓库', sortable:true ,align:"left",},
			{name:'purchfee', index:'purchfee', width:70, label:'采购费', sortable:true ,align:"right",},
			{name:'Date', index:'Date', width:80, label:'日期', sortable:true ,align:"left",formatter: formatDate},
			{name:'Remarks', index:'Remarks', width:80, label:'备注', sortable:true ,align:"left",},
		],
		onSelectRow : function(id) {
        	var row = selectDataTableRow("dataTable");
           	$("#dataTable1").jqGrid('setGridParam',{url : "${ctx}/admin/purchArrDetail/goJqGrid?pano="+row.No});
           	$("#dataTable1").jqGrid().trigger('reloadGrid');
        }
	});

});  
</script>
