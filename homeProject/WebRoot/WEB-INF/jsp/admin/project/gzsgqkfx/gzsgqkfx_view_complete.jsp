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
	<title>已完成明细</title>
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
	var url = "${ctx}/admin/workTypeConstructDetail/doCompleteExcel";
	doExcelAll(url);
}
$(function(){
	$("#businessMan").openComponent_employee({showValue:'${resrCust.businessMan}',showLabel:'${resrCust.businessManDescr}'});	
	$("#builderCode").openComponent_builder({showValue:'${resrCust.builderCode}',showLabel:'${resrCust.builderDescr}'});	
	var postData = $("#page_form").jsonForm();
	var dateFrom =$.trim($("#dateFrom").val());
	var dateTo =$.trim($("#dateTo").val());
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workTypeConstructDetail/goCompleteJqGrid",
		postData:{
			workType12:'${workType12}',
			dateFrom:dateFrom,
			dateTo:dateTo,
			prjRegionCode:'${customer.prjRegionCode}',
			custType:'${customer.custType }',
			isSign:'${isSign}',
			department2:'${customer.department2}',
			workType12Dept:"${customer.workType12Dept}",
			workerClassify: "${customer.workerClassify}"
		} ,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'custcode',	index:'custcode',	width:120,	label:'客户编号',	sortable:true,align:"left" , hidden: true},
			{name:'address',	index:'WorkType12',	width:120,	label:'楼盘',	sortable:true,align:"left" ,},
			{name:'custtypedescr',	index:'WorkType12',	width:75,	label:'客户类型',	sortable:true,align:"left" ,},
			{name:'layoutdescr',	index:'WorkType12',	width:60,	label:'户型',	sortable:true,align:"left" ,},
			{name:'area',	index:'WorkType12',	width:60,	label:'面积',	sortable:true,align:"right" ,},
			{name:'worktype12',	index:'worktype12',	width:75,	label:'工种',	sortable:true,align:"left" , hidden: true},
			{name:'worktype12descr',	index:'WorkType12',	width:75,	label:'工种',	sortable:true,align:"left" ,},
			{name:'workerdescr',	index:'workerdescr',	width:75,	label:'工人姓名',	sortable:true,align:"left" ,},
			{name:'comedate',	index:'comedate',	width:90,	label:'工人进场时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'mincrtdate',	index:'mincrtdate',	width:90,	label:'首次签到时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'maxcrtdate',	index:'maxcrtdate',	width:90,	label:'最后签到时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'workcompletedate', index:'workcompletedate', width:90,	label:'工人完成时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'prjpassdate', index:'prjpassdate', width:90,	label:'初检时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'confdate',	index:'confdate',	width:90,	label:'验收通过时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'enddate',	index:'enddate',	width:90,	label:'完工时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'consdays',	index:'consdays',	width:68,	label:'施工天数',	sortable:true,align:"right" ,},
			{name:'realsigndays',	index:'realsigndays',	width:90,	label:'实际签到天数',	sortable:true,align:"right" ,},
			{name:'constructday',	index:'constructday',	width:68,	label:'标准工期',	sortable:true,align:"right" ,},
			{name:'isontime',	index:'isontime',	width:68,	label:'按时完成',	sortable:true,align:"left" ,},
			{name:'workload',	index:'workload',	width:80,	label:'完工工作量',	sortable:true,align:"right" ,},
			{name:'confdescr',	index:'confdescr',	width:75,	label:'验收人',	sortable:true,align:"left" ,},
			{name:'prjleveldescr',	index:'prjleveldescr',	width:75,	label:'验收评级',	sortable:true,align:"left" ,},
			{name:'projectmandescr',	index:'projectmandescr',	width:70,	label:'监理',	sortable:true,align:"left" ,},
			{name:'depa1descr',	index:'depa1descr',	width:75,	label:'工程事业部',	sortable:true,align:"left" ,},
			{name:'depa2descr',	index:'depa2descr',	width:90,	label:'工程部',	sortable:true,align:"left" ,},
			{name:'endintime',	index:'endintime',	width:90,	label:'及时完成',	sortable:true,align:"left" ,hidden:true},
		],
	});
	 $("#dataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[
			{startColumnName: 'ArrNum', numberOfColumns: 3, titleText: '工人安排'},
			{startColumnName: 'ConfirmNum', numberOfColumns: 7, titleText: '施工与验收'},
			{startColumnName: 'IsSignNum', numberOfColumns: 6, titleText: '当前情况'}
		],
	});

	$("#viewSignDetail").on("click", function () {
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("viewSignDetail",{
				title:"工种施工情况——签到明细",
				url:"${ctx}/admin/workTypeConstructDetail/goViewSignDetail",
				postData:{custCode: ret.custcode, workType12: ret.worktype12},
				height:539,
				width:1054,
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
});
</script>
</head>
	<body>
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system" id="viewSignDetail">
						<span>查看签到明细</span>
					</button>
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
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" 
								value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" 
								value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li>
							<label>客户类型</label>
							<house:xtdmMulit id="custType" dictCode="CUSTTYPE"  selectedValue="${customer.custType }">
							</house:xtdmMulit>
						</li> 
						<li>
							<label>签约类型</label>
							<house:xtdmMulit id="isSign" dictCode="WSIGNTYPE"  
							sqlValueKey="Code" sqlLableKey="Descr"  selectedValue="${isSign}"></house:xtdmMulit>
						</li>
						<li>
							<label>工人星级</label>
							<house:xtdm id="level" dictCode="WORKERLEVEL"></house:xtdm>
						</li> 
						<li><label>工程大区</label> <input type="text" id="prjRegionCode"
							name="prjRegionCode" style="width:160px;"
							value="${customer.prjRegionCode }" />
						</li>
						<li>
							<label>及时完成</label>
							<house:xtdm id="onTimeCmp" dictCode="YESNO" ></house:xtdm>
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
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
