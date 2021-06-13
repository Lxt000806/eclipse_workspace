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
	<title>设计管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
      .SelectBG_yellow{
          background-color:yellow;
         }
	</style>
<script type="text/javascript"> 
$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-82,
		colModel : [
			{name:'address',	index:'address',	width:290,	label:'楼盘',	sortable:true,align:"left",},
			{name:'qqconfirmdate',	index:'qqconfirmdate',	width:140,	label:'砌墙验收时间',	sortable:true,align:"left",formatter:formatTime,},
			{name:'confirmdate',	index:'confirmdate',	width:140,	label:'变更图纸确认日期',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'mainconfirmdate',	index:'mainconfirmdate',	width:140,	label:'主材选定日期',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'scenedesignman',	index:'scenedesignman',	width:90,	label:'现场设计师',	sortable:true,align:"left",},
			{name:'mainemployee',	index:'mainemployee',	width:90,	label:'主材管家',	sortable:true,align:"left" ,},
		],
	}
	$.extend(gridOption,{
		url:"${ctx}/admin/custDoc/goDocChgConByJqGrid",
		postData:$("#page_form").jsonForm(),
	});
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
});
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
	}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>砌墙验收日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;"
									 onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									 value="<fmt:formatDate value='${custDoc.dateFrom}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  
									value="<fmt:formatDate value='${custDoc.dateTo}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>图纸变更完成</label>
							<house:xtdm  id="docChgCompleted" dictCode="YESNO" style="width:160px;"></house:xtdm>
						</li>
						<li >
							<label>选材完成</label>
							<house:xtdm  id="itemConfirmCompleted" dictCode="YESNO" style="width:160px;"></house:xtdm>
						</li>
						<li class="search-group-shrink">
							<button type="button" class="btn  btn-sm btn-system "
								onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system "
								onclick="clearForm();">清空</button>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		<div class="body-box-form" >
			<div class="container-fluid">  
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div>	
			</div>
		</div>
	</body>	
</html>
