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
	<title>查看计划</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/empWorkPlan/goViewJqGrid",
		postData:$("#page_form").jsonForm(),
		height:300,
		styleUI: 'Bootstrap', 
		rowNum:100000000,
		colModel : [
			  {name : 'Date',index : 'Date',width : 70,label:'日期',sortable : true,align : "left",formatter: formatDate},
		      {name : 'WeekDescr',index : 'WeekDescr',width : 60,label:'星期',sortable : true,align : "left"},
		      {name : 'PlanMeasure',index : 'PlanMeasure',width : 70,label:'计划',sortable : true,align : "right",editable:true,edittype:'text',sum:true,excelLabel:"计划量房"},	
		      {name : 'RealMeasure',index : 'RealMeasure',width : 70,label:'实际',sortable : true,align : "right",editable:true,edittype:'text',sum:true,excelLabel:"实际量房"},	
		      {name : 'PlanArrive',index : 'PlanArrive',width : 70,label:'计划',sortable : true,align : "right",editable:true,edittype:'text',sum:true,excelLabel:"计划到店"},	
		      {name : 'RealArrive',index : 'RealArrive',width : 70,label:'实际',sortable : true,align : "right",editable:true,edittype:'text',sum:true,excelLabel:"实际到店"},	
		      {name : 'PlanSet',index : 'PlanSet',width : 70,label:'计划',sortable : true,align : "right",editable:true,edittype:'text',sum:true,excelLabel:"计划下定"},
		      {name : 'RealSet',index : 'RealSet',width : 70,label:'实际',sortable : true,align : "right",editable:true,edittype:'text',sum:true,excelLabel:"计划实际"},
		      {name : 'PlanSign',index : 'PlanSign',width : 70,label:'计划',sortable : true,align : "right",editable:true,edittype:'text',sum:true,excelLabel:"计划签单"},		 
		      {name : 'RealSign',index : 'RealSign',width : 70,label:'实际',sortable : true,align : "right",editable:true,edittype:'text',sum:true,excelLabel:"实际签单"},		 
		      {name : 'PlanAddCust',index : 'PlanAddCust',width : 70,label:'计划',sortable : true,align : "right",editable:true,edittype:'text',sum:true,hidden:"${empWorkPlan.planCzyType}".trim()=="2"?true:false,excelLabel:"计划新增"},	
		      {name : 'RealAddCust',index : 'RealAddCust',width : 70,label:'实际',sortable : true,align : "right",editable:true,edittype:'text',sum:true,hidden:"${empWorkPlan.planCzyType}".trim()=="2"?true:false,excelLabel:"实际新增"},	
		      {name : 'PlanContactCust',index : 'PlanContactCust',width : 70,label:'计划',sortable : true,align : "right",editable:true,edittype:'text',sum:true,hidden:"${empWorkPlan.planCzyType}".trim()=="2"?true:false,excelLabel:"计划联系"},	
		      {name : 'RealContactCust',index : 'RealContactCust',width : 70,label:'实际',sortable : true,align : "right",editable:true,edittype:'text',sum:true,hidden:"${empWorkPlan.planCzyType}".trim()=="2"?true:false,excelLabel:"实际联系"},	
		      {name : 'ConNum',index : 'ConNum',width : 70,label:'记录数',sortable : true,align : "right",editable:true,edittype:'text',sum:true},	
		      {name : 'ContractFee',index : 'ContractFee',width : 70,label:'合同额',sortable : true,align : "right",editable:true,edittype:'text',sum:true},	
		      {name : 'PlaneNum',index : 'PlaneNum',width : 70,label:'出图数',sortable : true,align : "right",editable:true,edittype:'text',sum:true,hidden:"${empWorkPlan.planCzyType}".trim()=="1"?true:false},	
		      {name : 'OnTimePlaneNum',index : 'OnTimePlaneNum',width : 90,label:'及时出图数',sortable : true,align : "right",editable:true,edittype:'text',sum:true,hidden:"${empWorkPlan.planCzyType}".trim()=="1"?true:false},	
		],
		loadonce:true
	});
	 $("#dataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[
				{startColumnName: 'PlanMeasure', numberOfColumns: 2, titleText: '量房'},
				{startColumnName: 'PlanArrive', numberOfColumns: 2, titleText: '到店'},
				{startColumnName: 'PlanSet', numberOfColumns: 2, titleText: '下定'},
				{startColumnName: 'PlanSign', numberOfColumns: 2, titleText: '签单'},
				{startColumnName: 'PlanAddCust', numberOfColumns: 2, titleText: '新增客户'},
				{startColumnName: 'PlanContactCust', numberOfColumns: 2, titleText: '联系客户'}
		],
	});
});
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="doExcel"
						onclick="doExcelNow('计划表', 'dataTable', 'page_form');">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" id="pk" name="pk" value="${empWorkPlan.pk}" />
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="form-validate row">
							<li class="form-validate"><label>计划人类型</label> <house:xtdm
									id="planCzyType" dictCode="PLANCZYTYPE"
									value="${empWorkPlan.planCzyType}" disabled="true"></house:xtdm>
							</li>
							<li class="form-validate"><label>计划开始时间</label> <input
								type="text" id="planBeginDate" name="planBeginDate"
								class="i-date"
								value="<fmt:formatDate value='${empWorkPlan.planBeginDate}' pattern='yyyy-MM-dd'/>"
								readonly />
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li id="tabsalesInvoice" class="active"><a href="#tab_detail"
					data-toggle="tab">计划表</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab_detail" class="tab-pane fade in active">
					<div id="content-list">
						<table id="dataTable"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
