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
	<title>工地施工天数分析</title>
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
$(function(){
	$("#projectMan").openComponent_employee();	
 	var dateFrom =$.trim($("#dateFrom").val());
 	var dateTo =$.trim($("#dateTo").val());
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/gdsgts/goJqGrid",
		postData:{dateFrom:dateFrom,dateTo:dateTo},
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		loadonce: true,
		rowNum:100000,
		colModel : [
			{name:'address',	index:'address',	width:190,	label:"楼盘",	sortable:true,align:"left" ,},
			{name:'custtypedescr',	index:'custtypedescr',	width:90,	label:"客户类型",	sortable:true,align:"left" ,},
			{name:'area',	index:'area',	width:50,	label:"面积",	sortable:true,align:"right" ,},
			{name:'projectmandescr',	index:'projectmandescr',	width:90,	label:"监理",	sortable:true,align:"left" ,},
			{name:'department2descr',	index:'department2descr',	width:90,	label:"工程部",	sortable:true,align:"left" ,},
			{name:'plandays',	index:'plandays',	width:90,	label:"计划施工天数",	sortable:true,align:"right" ,avg:true},
			{name:'begindate',	index:'begindate',	width:90,	label:"开始时间",	sortable:true,align:"left" ,formatter:formatDate},
			{name:'enddate',	index:'enddate',	width:90,	label:"结束时间",	sortable:true,align:"left" ,formatter:formatDate},
			{name:'alldays',	index:'alldays',	width:65,	label:"总天数",	sortable:true,align:"right" ,avg:true},
			{name:'workerdays',	index:'workerdays',	width:90,	label:"工人施工天数",	sortable:true,align:"right" ,avg:true},
			{name:'workerper',	index:'workerper',	width:90,	label:"工人施工占比",	sortable:true,align:"right" ,formatter:divFormat},
			{name:'qq',	index:'qq',	width:65,	label:"砌墙",	sortable:true,align:"right",avg:true},
			{name:'sd',	index:'sd',	width:65,	label:"水电",	sortable:true,align:"right",avg:true},
			{name:'zp',	index:'zp',	width:65,	label:"找平",	sortable:true,align:"right",avg:true},
			{name:'mz',	index:'mz',	width:65,	label:"木作",	sortable:true,align:"right",avg:true},
			{name:'sm',	index:'sm',	width:65,	label:"饰面",	sortable:true,align:"right",avg:true},
			{name:'yq',	index:'yq',	width:65,	label:"油漆",	sortable:true,align:"right",avg:true},
		],
	});
	
	function divFormat (cellvalue, options, rowObject){ 
	    return myRound(myRound(cellvalue,3)*100,3)+"%";
	}	
	window.goto_query = function(){
		if($("#dateFrom").val()=="" || $("#dateTo").val() ==""){
			art.dialog({
				content:"请填写完整时间段",
			});
			return;
		}
		var dateFrom=new Date($.trim($("#dateFrom").val()));
		$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,
					url:"${ctx}/admin/gdsgts/goJqGrid",}).trigger("reloadGrid");
	}
});

function doExcel(){
	var url = "${ctx}/admin/gdsgts/goJqGrid";
	doExcelAll(url);
}

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>完工日期从</label> 
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
							<label>项目经理</label> 
							<input type="text" id="projectMan" name="projectMan"  />
						</li>
						<li>
							<label>二级部门</label> <house:dict id="department2"
							dictCode="" sql="select * from tDepartment2 where DepType='3' and expired='F' "
							sqlLableKey="desc1" sqlValueKey="code" value="${customer.department2 }"></house:dict>
						</li>
						<li><label>客户类型</label> <house:xtdmMulit id="custType"
							dictCode="CUSTTYPE" selectedValue="${customer.custType }"></house:xtdmMulit>
						</li>
						<li>
							<label>施工天数从</label> 
							<input type="text" id="daysFrom" name="daysFrom"/>
						</li>
						<li>
							<label>至</label> 
							<input type="text" id="daysTo" name="daysTo"/>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
						<span>导出excel</span>
					</button>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>
		</div>
	</body>	
</html>
