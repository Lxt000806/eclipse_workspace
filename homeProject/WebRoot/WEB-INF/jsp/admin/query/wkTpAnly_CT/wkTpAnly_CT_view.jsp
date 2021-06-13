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
	<title>工种施工分析按工地类型查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
/*修改无法导出excel的问题——zb*/	
/*function doExcel(){
	var url = "${ctx}/admin/WkTpAnly_CT/doDetailExcel";
	doExcelAll(url);
}*/
$(function(){
	var postData = $("#page_form").jsonForm();
	var dateFrom =$.trim($("#dateFrom").val());
	var dateTo =$.trim($("#dateTo").val());
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/WkTpAnly_CT/goDetailJqGrid",
		postData:{code:'${workType12.code}',dateFrom:dateFrom,dateTo:dateTo,
		constructType:'${workType12.constructType}',isSign:'${workType12.isSign}'
					,area:'${area }',layOut:'${layOut }'
					} ,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'楼盘',	index:'楼盘',	width:180,	label:'楼盘地址',	sortable:true,align:"left" ,},
			{name:'工种',	index:'工种',	width:120,	label:'工种',	sortable:true,align:"left" ,},
			{name:'工人',	index:'工人',	width:120,	label:'工人',	sortable:true,align:"left" ,},
			{name:'星级',	index:'星级',	width:120,	label:'星级',	sortable:true,align:"left" ,},
			{name:'进场时间',	index:'进场时间',	width:120,	label:'进场时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'验收时间',	index:'验收时间',	width:120,	label:'验收时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'计划工期',	index:'计划工期',	width:120,	label:'工期',	sortable:true,align:"right" ,},
			{name:'拖期天数',	index:'拖期天数',	width:120,	label:'拖期天数',	sortable:true,align:"right" ,},

		],
	});
});
 


</script>
</head>
	<body>
	<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system "  onclick="doExcelNow('工种施工完成情况表_')" title="工种施工完成情况表_">
							<span>导出excel</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="workType12" name="workType12" value="${workType12}" />
					<input type="hidden" id="layOut" name="layOut" value="${layOut}" />
					<input type="hidden" id="area" name="area" value="${area}" />
					<ul class="ul-form">
						<li class="form-validate">
							<label>工种</label>
							<house:dict id="code" dictCode="" 
								sql="select a.* from tWorkType12 a where a.expired='F' " sqlValueKey="Code" sqlLableKey="Descr" 
									value="${workType12.code}"></house:dict>
						</li>
						<li>
							<label>日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" value="<fmt:formatDate value='${workType12.dateFrom}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" value="<fmt:formatDate value='${workType12.dateTo}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
	</body>	
</html>
