<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>CustItemConfStat列表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#custType").val('');
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	}

	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custItemConfStat/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			postData:{
				dateFrom:$("#dateFrom").val(), 
				dateTo:$("#dateTo").val()
			},
			colModel : [
			  {name:"address", index:"address", width:140, label:"楼盘", sortable:true, align:"left"},
			  {name:"custtypedescr", index:"custtypedescr", width:95, label:"客户类型", sortable:true, align:"left"},
			  {name:"mainczydescr", index:"mainczydescr", width:95, label:"主材管家", sortable:true, align:"left"},
			  {name:"designczydescr", index:"designczydescr", width:85, label:"设计师", sortable:true, align:"left"},
			  {name:"department2descr", index:"department2descr", width:85, label:"设计所", sortable:true, align:"left"},
			  {name:"first", index:"first", width:125, label:"1阶段是否达标", sortable:true, align:"left"},
			  {name:"second", index:"second", width:125, label:"2阶段是否达标", sortable:true, align:"left"},
			  {name:"isallconfirm", index:"isallconfirm", width:145, label:"是否一次确认完成", sortable:true, align:"left"},
			  {name:"sdconfirmdate", index:"sdconfirmdate", width:145, label:"水电验收时间", sortable:true, align:"left",formatter: formatTime},
			  {name:"nsconfirmdate", index:"nsconfirmdate", width:145, label:"饰面验收时间", sortable:true, align:"left",formatter: formatTime},  
			  {name:"firstcondate", index:"firstcondate", width:145, label:"一次确认日期", sortable:true, align:"left",formatter: formatDate} , 
			  {name:"secondcondate", index:"secondcondate", width:145, label:"二次确认日期", sortable:true, align:"left",formatter: formatDate},  
            ]
		});
		$("#mainBusinessMan").openComponent_employee();
	});
	function goto_query(){
		if(!$("#dateFrom").val()&&!$("#dateTo").val()&&!$("#sdDateFrom").val()&&!$("#sdDateTo").val()&&!$("#nsDateFrom").val()&&!$("#nsDateTo").val() ){
			art.dialog({
				content:"确认日期、水电验收日期、饰面验收日期 ,请选择至少一项进行统计"
			});
			return;
		}
		$("#dataTable").jqGrid("setGridParam", {
			postData:$("#page_form").jsonForm(), 
			page:1, 
			sortname:""
		}).trigger("reloadGrid");
	}
	function doExcel(){
		doExcelAll("${ctx}/admin/custItemConfStat/doExcel");
	}
	</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form method="post" id="page_form" class="form-search">
				<input type="hidden" id="jsonString" name="jsonString" />
				<ul class="ul-form">
					<li><label>确认日期从</label> <input type="text" id="dateFrom" name="dateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${data.dateFrom }' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="dateTo" name="dateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${data.dateTo }' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>主材管家</label> <input type="text" id="mainBusinessMan" name="mainBusinessMan" />
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  ></house:custTypeMulit>
					</li>
					<li><label>水电日期从</label> <input type="text" id="sdDateFrom" name="sdDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd'})"
					   pattern='yyyy-MM-dd'/>
					</li>
					<li><label>至</label> <input type="text" id="dateTo" name="sdDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd'})"
					    pattern='yyyy-MM-dd'/>
					</li>
					<li><label>饰面日期从</label> <input type="text" id="nsDateFrom" name="nsDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd'})"
						 pattern='yyyy-MM-dd'/>
					</li>
					<li><label>至</label> <input type="text" id="nsDateTo" name="nsDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd'})"
						pattern='yyyy-MM-dd'/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
					</li>
				</ul>
			</form>
		</div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="CUSTITEMCONFSTAT_EXCEL">
					<button id="custItemConfStatExcel" type="button" class="btn btn-system" onclick="doExcel()">输出到Excel</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


