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
	<title>工地验收分析</title>
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
	var dateFrom =$.trim($("#dateFrom").val());
 	var dateTo =$.trim($("#dateTo").val());
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/gdysfx/goJqGrid",
		postData:{dateFrom:dateFrom,dateTo:dateTo} ,
		rowNum:10000,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'namechi',	index:'namechi',	width:90,	label:'专员',	sortable:true,align:"left",},
			{name:'posidescr',	index:'posidescr',	width:90,	label:'职位',	sortable:true,align:"left",},
			{name:'department2descr',	index:'department2descr',	width:90,	label:'部门',	sortable:true,align:"left",},
			{name:'prjitemdescr',	index:'prjitemdescr',	width:90,	label:'节点',	sortable:true,align:"left",},
			{name:'confirmpass',	index:'confirmpass',	width:90,	label:'通过数',	sortable:true,align:"right",sum:true},
			{name:'confirmnopass',	index:'confirmnopass',	width:90,	label:'未通过数',	sortable:true,align:"right",sum:true},
			{name:'amount',	index:'amount',	width:90,	label:'总数',	sortable:true,align:"right",sum:true},
			{name:'siteConfirm',	index:'siteConfirm',	width:90,	label:'现场验收数',	sortable:true,align:"right",sum:true},
			{name:'passrate',	index:'passrate',	width:90,	label:'通过率',	sortable:true,align:"right",formatter:DiyFmatter},
			{name:'worktype12descr',	index:'worktype12descr',	width:90,	label:'对应工种',	sortable:true,align:"left",},
			{name:'signrate',	index:'signrate',	width:90,	label:'工种签到率',	sortable:true,align:"right",formatter:DiyFmatter},
			{name:'intimerate',	index:'intimerate',	width:100,	label:'工种完工及时率',	sortable:true,align:"right",formatter:format},

		],
	});
	function DiyFmatter (cellvalue, options, rowObject){ 
	    return myRound(cellvalue*100,4)+"%";
	}
	function format(cellvalue, options, rowObject){
	    return myRound(cellvalue,2)+"%";
	}
	
});
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
}
function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#prjItem_NAME").val('');
		$.fn.zTree.getZTreeObj("tree_prjItem").checkAllNodes(false);
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
							<label>日期从</label> <input type="text" id="dateFrom"
							name="dateFrom" class="i-date" style="width:160px;"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})"
							value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li><label>至</label> <input type="text" id="dateTo"
							name="dateTo" class="i-date" style="width:160px;"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})"
							value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li>
							<label>二级部门</label>
							<house:dict id="department2"
								dictCode=""
								sql="select * from tDepartment2 where DepType='3' and expired='F' "
								sqlLableKey="desc1" sqlValueKey="code"
								value="${customer.department2 }"></house:dict>
						</li>
						<li>
						<label>验收节点</label>
							<house:DictMulitSelect id="prjItem" dictCode="" 
							sql="select a.Code,a.Descr from tPrjItem1 a  " 
							sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
						</li>
						<li>
							<label>一级区域</label>
							<house:DictMulitSelect id="region" dictCode="" 
								sql="SELECT Code, Descr FROM  dbo.tRegion WHERE expired='F' ORDER BY CAST(Code as Integer) ASC  " 
								sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
						</li>
						
						<li>
							<button type="button" class="btn  btn-sm btn-system "
								onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system "
								onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel" >
  			  <div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " onclick="doExcelNow('工地验收分析报表','dataTable','page_form')" title="导出当前excel数据" >
						<span>导出excel</span>
					</button>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
			</div> 
		</div>
	</body>	
</html>
