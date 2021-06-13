<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>员工信息--列表</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text']").val('');
	} 
	function goto_query(){
		if($.trim($("#dateFrom").val())==''){
				art.dialog({content: "修改开始日期不能为空",width: 200});
				return false;
		} 
		if($.trim($("#dateTo").val())==''){
				art.dialog({content: "修改结束日期不能为空",width: 200});
				return false;
		}
	     var dateStart = Date.parse($.trim($("#dateFrom").val()));
	     var dateEnd = Date.parse($.trim($("#dateTo").val()));
	     if(dateStart>dateEnd){
	    	 art.dialog({content: "修改开始日期不能大于结束日期",width: 200});
				return false;
	     } 
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	}
	
	function doExcel() {
		var url = "${ctx}/admin/employee/doEmpTranLogExcel";
		doExcelAll(url);
	}
	
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			styleUI : "Bootstrap",
			url:"${ctx}/admin/employee/goEmpTranLogJqGrid",
		    postData: $("#page_form").jsonForm(),
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [
					{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden: true},
					{name: "number", index: "number", width: 70, label: "员工编号", sortable: true, align: "left"},
					{name: "namechi", index: "namechi", width: 70, label: "姓名", sortable: true, align: "left"},
					{name: "nowdeptdescr", index: "nowdeptdescr", width: 79, label: "当前部门", sortable: true, align: "left"},
					{name: "date", index: "date", width: 70, label: "修改日期", sortable: true, align: "left", formatter: formatTime},
					{name: "olddeptdescr", index: "olddeptdescr", width: 79, label: "原部门", sortable: true, align: "left"},
					{name: "deptdescr", index: "deptdescr", width: 79, label: "新部门", sortable: true, align: "left"},
					{name: "oldpositiondescr", index: "oldpositiondescr", width: 79, label: "原职位", sortable: true, align: "left"},
					{name: "positiondescr", index: "positiondescr", width: 79, label: "新职位", sortable: true, align: "left"},
					{name: "oldisleaddescr", index: "oldisleaddescr", width: 79, label: "原是否领导", sortable: true, align: "left"},
					{name: "isleaddescr", index: "isleaddescr", width: 79, label: "新是否领导", sortable: true, align: "left"},
					{name: "oldLeadleveldescr", index: "oldLeadleveldescr", width: 79, label: "原领导级别", sortable: true, align: "left"},
					{name: "leadleveldescr", index: "leadleveldescr", width: 79, label: "新领导级别", sortable: true, align: "left"},
					{name: "oldstatusdescr", index: "oldstatusdescr", width: 55, label: "原状态", sortable: true, align: "left"},
					{name: "statusdescr", index: "statusdescr", width: 55, label: "新状态", sortable: true, align: "left"},
					{name: "modifyczydescr", index: "modifyczydescr", width: 79, label: "修改人", sortable: true, align: "left"},
				], 
			});
		});
					
</script>
</head> 
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" id="expired"  name="expired" />
					<ul class="ul-form">
						<li>
						<label>修改日期</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${empTranLog.dateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>					
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${empTranLog.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
			        <li class="search-group"><input type="checkbox" id="expired" name="expired" value="F"
						 onclick="changeExpired(this)" checked />
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
			</div>
		</div>
		<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
		</div>
	</div>	
</body>
</html>
