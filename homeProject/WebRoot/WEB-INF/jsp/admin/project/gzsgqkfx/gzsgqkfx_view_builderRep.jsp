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
	<title>停工报备明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_prjRegion.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/workTypeConstructDetail/doBuilderRepExcel";
	doExcelAll(url);
}
$(function(){
	$("#businessMan").openComponent_employee({showValue:'${resrCust.businessMan}',showLabel:'${resrCust.businessManDescr}'});	
	$("#builderCode").openComponent_builder({showValue:'${resrCust.builderCode}',showLabel:'${resrCust.builderDescr}'});	
	$("#prjRegionCode").openComponent_prjRegion({showValue:'${customer.prjRegionCode}',showLabel:'${customer.prjRegionCodeDescr}'});
	var postData = $("#page_form").jsonForm();
	var dateFrom =$.trim($("#dateFrom").val());
	var dateTo =$.trim($("#dateTo").val());
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workTypeConstructDetail/goBuilderRepJqGrid",
		postData:{workType12:'${workType12}',dateFrom:dateFrom,dateTo:dateTo,prjRegionCode:'${customer.prjRegionCode}',
			custType:'${customer.custType }',isSign:'${isSign}',department2:'${customer.department2}',workType12Dept:"${customer.workType12Dept}"} ,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'issign',	index:'issign',	width:120,	label:'issign',	sortable:true,align:"left" ,hidden:true},
			{name:'address',	index:'address',	width:120,	label:'楼盘',	sortable:true,align:"left" ,},
			{name:'workerdescr',	index:'workerdescr',	width:90,	label:'工人名称',	sortable:true,align:"left" ,},
			{name:'worktype12descr',	index:'worktypedescr',	width:90,	label:'工种',	sortable:true,align:"left" ,},
			{name:'begindate',	index:'begindate',	width:90,	label:'停工开始时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'enddate',	index:'enddate',	width:90,	label:'停工结束时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'stopdays',	index:'stopdays',	width:90,	label:'停工天数',	sortable:true,align:"right" ,},
			{name:'projectmandescr',	index:'projectmandescr',	width:90,	label:'监理',	sortable:true,align:"left" ,},
			{name:'depa1descr',	index:'depa1descr',	width:90,	label:'工程事业部',	sortable:true,align:"left" ,},
			{name:'depa2descr',	index:'depa2descr',	width:90,	label:'工程部',	sortable:true,align:"left" ,},

		],
	});
	
	 $("#dataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[{startColumnName: 'ArrNum', numberOfColumns: 3, titleText: '工人安排'},
						{startColumnName: 'ConfirmNum', numberOfColumns: 7, titleText: '施工与验收'},
						{startColumnName: 'IsSignNum', numberOfColumns: 6, titleText: '当前情况'}
		],
	});
});
 


</script>
</head>
	<body>
		<div class="panel panel-system" >
		    <div class="panel-body" >
		      	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
							<span>导出excel</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="body-box-list">
			<div class="query-form" hidden="true">
			  	<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="workType12" name="workType12" value="${workType12}" />
					<input type="hidden" id="department2" name="department2" value="${customer.department2}" />
					<input type="hidden" id="workType12Dept" name="workType12Dept" value="${customer.workType12Dept}" />
					<ul class="ul-form">
						<li>
							<label>日期从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li>
							<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li>
							<label>客户类型</label> 
							<house:xtdmMulit id="custType" dictCode="CUSTTYPE" selectedValue="${customer.custType }"></house:xtdmMulit>
						</li>
						<li>
							<label>签约类型</label>
							<house:xtdmMulit id="isSign" dictCode="WSIGNTYPE" sqlValueKey="Code" 
								sqlLableKey="Descr"  selectedValue="${isSign}"></house:xtdmMulit>
						</li>
						<li>
							<label>工程大区</label> 
							<input type="text" id="prjRegionCode" name="prjRegionCode" 
									style="width:160px;" value="${customer.prjRegionCode }" />
						</li>
						<li>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>	
</html>
