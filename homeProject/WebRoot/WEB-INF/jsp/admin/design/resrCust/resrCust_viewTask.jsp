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
	<title>查看任务</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/excelTask/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		rowNum:10000000,
		postData:{url:"/admin/ResrCust/loadExcel",dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val()},
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'pk',	index:'pk',	width:200,	label:'pk',	sortable:true,align:"left",hidden:true},
				{name:'filename',	index:'filename',	width:250,	label:'文件名',	sortable:true,align:"left",},
				{name:'importplan',	index:'importplan',	width:90,	label:'导入方案',	sortable:true,align:"left"},
				{name:'emps',	index:'emps',	width:150,	label:'导入给何人',	sortable:true,align:"left"},
				{name:'status',	index:'status',	width:90,	label:'任务状态',	sortable:true,align:"left"},
				{name:'lastupdate',	index:'lastupdate',	width:120,	label:'创建时间',	sortable:true,align:"left",formatter: formatTime},
				{name:'result',	index:'result',	width:110,	label:'导入结果',	sortable:true,align:"left"},
		],
	});
});

function viewFailedTask(){
	var ret = selectDataTableRow();
	if(ret){
		Global.Dialog.showDialog("viewFailedTask",{
			title:"失败任务明细",
			url:"${ctx}/admin/ResrCust/goFailedTask",
			postData:{taskPK:ret.pk},
			height:600,
			width:1070,
		});
	}else{
		art.dialog({
			content:"请选择一条数据",
		});
	}
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " 
						onclick="viewFailedTask()">
						<span>查看失败明细</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<ul class="ul-form">
							<li>
								<label>创建日期从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${resrCust.dateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>						
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${resrCust.dateTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>状态</label>
								<house:xtdm id="status" dictCode="EXCELTASKSTAT" ></house:xtdm>                     
							</li>
							<li>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
							</ul>
				</form>
			</div>
			</div>
		<div id="content-list">
			<table id= "dataTable"></table>
		</div>
	</div>
</body>
</html>
