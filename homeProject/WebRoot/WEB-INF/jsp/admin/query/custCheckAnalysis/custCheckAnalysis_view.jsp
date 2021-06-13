<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户结算分析—查看</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	/**初始化表格*/
	var tableId="checkDetailTable";
	var preName="结算明细";
	var url = "${ctx}/admin/custCheckAnalysis/goJqGridCheckDetail";
	$(function(){
	
		Global.JqGrid.initJqGrid("checkDetailTable", {
			url:"${ctx}/admin/custCheckAnalysis/goJqGridCheckDetail",
			styleUI: "Bootstrap", 
			height: 330,
			rowNum: 10000,
			postData: {
				dateFrom: $("#dateFrom").val(),
				dateTo: $("#dateTo").val(),
				role: "${data.role}",
				statistcsMethod: "${data.statistcsMethod}",
				department1: "${data.department1}",
				department2: "${data.department2}",
				custtype: "${data.custtype}",
				constructType: "${data.constructType}",
				isContainSoft: "${data.isContainSoft}"
			},
			colModel : [
				{name: "address", index: "address", width: 100, label: "楼盘", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
				{name: "area", index: "area", width: 50, label: "面积", sortable: true, align: "right"},
				{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 70, label: "工程部", sortable: true, align: "left"},
				{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
				{name: "designdepart2descr", index: "designdepart2descr", width: 70, label: "设计部", sortable: true, align: "left"},
		      	{name: "custcheckdate", index: "custcheckdate", width: 100, label: "结算日期", sortable: true, align: "left", formatter: formatTime},
				{name: "checkamount", index: "checkamount", width: 70, label: "结算金额", sortable: true, align: "right", sum: true},
				{name: "contractfee", index: "contractfee", width: 80, label: "签单合同额", sortable: true, align: "right", sum: true},
				{name: "sumchgamount", index: "sumchgamount", width: 70, label: "材料增减", sortable: true, align: "right", sum: true},
				{name: "sumbasechgamount", index: "sumbasechgamount", width: 70, label: "基础增减", sortable: true, align: "right", sum: true}
		    ],
		});
		Global.JqGrid.initJqGrid("returnOrderDetailTable", {
			url:"${ctx}/admin/custCheckAnalysis/goJqGridReturnDetail",
			styleUI: "Bootstrap", 
			height: 330,
			rowNum: 10000,
			postData: {
				dateFrom: $("#dateFrom").val(),
				dateTo: $("#dateTo").val(),
				role: "${data.role}",
				statistcsMethod: "${data.statistcsMethod}",
				department1: "${data.department1}",
				department2: "${data.department2}",
				custtype: "${data.custtype}",
				constructType: "${data.constructType}",
				isContainSoft: "${data.isContainSoft}",
				returnFlag: 1
			},
			colModel : [
				{name: "address", index: "address", width: 100, label: "楼盘", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 120, label: "客户类型", sortable: true, align: "left"},
				{name: "area", index: "area", width: 80, label: "面积", sortable: true, align: "right"},
				{name: "designmandescr", index: "designmandescr", width: 120, label: "设计师", sortable: true, align: "left"},
				{name: "designdepart2descr", index: "designdepart2descr", width: 120, label: "设计部", sortable: true, align: "left"},
				{name: "contractfee", index: "contractfee", width: 100, label: "签单金额", sortable: true, align: "right", sum: true},
		      	{name: "signdate", index: "signdate", width: 100, label: "签订时间", sortable: true, align: "left", formatter: formatTime},
		      	{name: "returndate", index: "returndate", width: 100, label: "退单日期", sortable: true, align: "left", formatter: formatTime}
		    ],
		});
		Global.JqGrid.initJqGrid("returnDetailTable", {
			url:"${ctx}/admin/custCheckAnalysis/goJqGridReturnDetail",
			styleUI: "Bootstrap", 
			height: 350,
			rowNum: 10000,
			postData: {
				dateFrom: $("#dateFrom").val(),
				dateTo: $("#dateTo").val(),
				role: "${data.role}",
				statistcsMethod: "${data.statistcsMethod}",
				department1: "${data.department1}",
				department2: "${data.department2}",
				custtype: "${data.custtype}",
				constructType: "${data.constructType}",
				isContainSoft: "${data.isContainSoft}",
				returnFlag: 0
			},
			colModel : [
				{name: "address", index: "address", width: 100, label: "楼盘", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 120, label: "客户类型", sortable: true, align: "left"},
				{name: "area", index: "area", width: 80, label: "面积", sortable: true, align: "right"},
				{name: "designmandescr", index: "designmandescr", width: 120, label: "设计师", sortable: true, align: "left"},
				{name: "designdepart2descr", index: "designdepart2descr", width: 120, label: "设计部", sortable: true, align: "left"},
		      	{name: "setdate", index: "setdate", width: 100, label: "下定日期", sortable: true, align: "left", formatter: formatTime},
		      	{name: "returndate", index: "returndate", width: 100, label: "退订日期", sortable: true, align: "left", formatter: formatTime}
		    ],
		});
	});
	function doExcleLocal(){
		doExcelNow(preName,tableId,"page_form");
	}
	function chgDateTableId(tableName){
		tableId = tableName;
		if(tableName =="checkDetailTable"){
			preName = "结算明细";
		}
		if(tableName =="returnOrderDetailTable"){
			preName = "退单明细";
		}
		if(tableName =="returnDetailTable"){
			preName = "退定明细";
		}
	}
	</script>
</head>
<body>
	<div class="body-box-list">
	
		<input type="hidden" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
				value="<fmt:formatDate value='${data.dateFrom}' pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
				value="<fmt:formatDate value='${data.dateTo}' pattern='yyyy-MM-dd'/>" />
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
    				<button id="closeBut" type="button" class="btn btn-system" onclick="doExcleLocal()">导出excel</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div  class="container-fluid" >  
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
				</form>
				<ul class="nav nav-tabs" >
				    <li class="active" onclick="chgDateTableId('checkDetailTable')"><a href="#checkDetail" data-toggle="tab">结算明细</a></li>  
				    <li onclick="chgDateTableId('returnOrderDetailTable')"><a href="#returnOrderDetail" data-toggle="tab">退单明细</a></li>  
				    <li onclick="chgDateTableId('returnDetailTable')"><a href="#returnDetail" data-toggle="tab">退订明细</a></li>  
				</ul>  
			    <div class="tab-content">  
					<div id="checkDetail"  class="tab-pane fade in active">
						<table id="checkDetailTable"></table>
					</div> 
					<div id="returnOrderDetail"  class="tab-pane fade">
						<table id="returnOrderDetailTable"></table>
					</div> 
					<div id="returnDetail"  class="tab-pane fade">
						<table id="returnDetailTable"></table>
					</div> 
				</div>	
			</div>	
		</div>
	</div>
</body>
</html>


