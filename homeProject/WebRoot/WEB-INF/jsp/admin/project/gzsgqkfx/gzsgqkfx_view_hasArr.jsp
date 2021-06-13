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
	<title>已安排明细</title>
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
	var url = "${ctx}/admin/workTypeConstructDetail/doHasArrExcel";
	doExcelAll(url);
} 
$(function(){
	$("#businessMan").openComponent_employee({showValue:'${resrCust.businessMan}',showLabel:'${resrCust.businessManDescr}'});	
	$("#builderCode").openComponent_builder({showValue:'${resrCust.builderCode}',showLabel:'${resrCust.builderDescr}'});	
	var postData = $("#page_form").jsonForm();
	var dateFrom =$.trim($("#dateFrom").val());
	var dateTo =$.trim($("#dateTo").val());
	var custType =$.trim($("#custType").val());
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workTypeConstructDetail/goHasArrJqGrid",
		postData:{workType12:'${workType12}',dateFrom:dateFrom,dateTo:dateTo,prjRegionCode:'${customer.prjRegionCode}',
			custType:'${customer.custType }',isSign:'${isSign}',department2:'${customer.department2}',workType12Dept:"${customer.workType12Dept}",
			workerClassify:"${customer.workerClassify}"} ,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'address',	index:'address',	width:120,	label:'楼盘',	sortable:true,align:"left" ,},
			{name:'custtypedescr',	index:'custtypedescr',	width:75,	label:'客户类型',	sortable:true,align:"left" ,},
			{name:'layoutdescr',	index:'layoutdescr',	width:68,	label:'户型',	sortable:true,align:"left" ,},
			{name:'area',	index:'area',	width:68,	label:'面积',	sortable:true,align:"right" ,},
			{name:'appdate',	index:'appdate',	width:90,	label:'申报时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'appcomedate',	index:'appcomedate',	width:90,	label:'申报进场时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'worktype12descr',	index:'worktype12descr',	width:75,	label:'工种',	sortable:true,align:"left" ,},
			{name:'workerdescr',	index:'workerdescr',	width:70,	label:'工人名称',	sortable:true,align:"left" ,},
			{name:'comedate',	index:'comedate',	width:90,	label:'进场时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'mincrtdate',	index:'mincrtdate',	width:90,	label:'首次签到时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'maxcrtdate',	index:'maxcrtdate',	width:90,	label:'最后签到时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'projectmandescr',	index:'projectmandescr',	width:70,	label:'监理',	sortable:true,align:"left" ,},
			{name:'depa1descr',	index:'depa1descr',	width:90,	label:'工程事业部',	sortable:true,align:"left" ,},
			{name:'depa2descr',	index:'depa2descr',	width:90,	label:'工程部',	sortable:true,align:"left" ,},
			{name:'arrworkload',index:'arrworkload',	width:90,	label:'安排工资额',	sortable:true,align:"right" ,sum:true},
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
					<input type="hidden" id="workerClassify" name="workerClassify" value="${customer.workerClassify}" />
				
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
							<house:xtdmMulit id="custType" dictCode="CUSTTYPE"  selectedValue="${customer.custType }"></house:xtdmMulit>
						</li> 
						<li>
							<label>签约类型</label>
							<house:xtdmMulit id="isSign" dictCode="WSIGNTYPE"  
							sqlValueKey="Code" sqlLableKey="Descr"  selectedValue="${isSign}"></house:xtdmMulit>
						</li>
						<li><label>工程大区</label> <input type="text" id="prjRegionCode"
							name="prjRegionCode" style="width:160px;"
							value="${customer.prjRegionCode }" />
						</li>
						<li >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
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
