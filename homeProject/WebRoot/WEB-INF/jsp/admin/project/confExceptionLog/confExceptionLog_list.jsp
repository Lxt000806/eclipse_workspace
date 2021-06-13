<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>审核异常日志查询</title>
	<%@ include file="/commons/jsp/common.jsp"%>

	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;"/>
					</li>
					<li>
						<label>类型</label>
						<house:xtdmMulit id="type" dictCode="CONFEXCELOGTYPE" ></house:xtdmMulit>
					</li>
					<li>
						<label>日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
					</li>
					
					<li>
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
	<script type="text/javascript" defer>
	var postData = $("#page_form").jsonForm();
	$(function(){
		$("#workerCode").openComponent_worker();
	
		Global.JqGrid.initJqGrid("dataTable", {
			url: "${ctx}/admin/confExceptionLog/goJqGrid",
			postData: postData,
			sortable: true,
			height: $(document).height() - $("#content-list").offset().top - 70,
			styleUI: "Bootstrap", 
			colModel: [
				{name:"pk", index:"pk", width:80, label:"pk", sortable:true, align:"left",hidden:true},
				{name:"address", index:"address", width:160, label:"楼盘", sortable:true, align:"left"},
				{name:"custtypedescr", index:"custtypedescr", width:80, label:"客户类型", sortable:true, align:"left"},
				{name:"typedescr", index:"typedescr", width:80, label:"类型", sortable:true, align:"left"},
				{name:"refno", index:"refno", width:80, label:"相关单号", sortable:true, align:"left"},
				{name:"paynum", index:"paynum", width:80, label:"付款期数", sortable:true, align:"right"},
				{name:"balanceamount", index:"balanceamount", width:80, label:"差额", sortable:true, align:"right"},
				{name:"cost", index:"cost", width:80, label:"成本", sortable:true, align:"left"},
				{name:"remarks", index:"remarks", width:200, label:"备注", sortable:true, align:"left"}, 
				{name:"lastupdate", index:"lastupdate", width:125, label:"最后修改时间", sortable:true, align:"left", formatter:formatTime}, 
				{name:"lastupdatedby", index:"lastupdatedby", width:125, label:"最后修改人", sortable:true, align:"left"}, 
			]
		});
		
	});
	function doExcel(){
		var url = "${ctx}/admin/confExceptionLog/doExcel";
		doExcelAll(url);
	}
	
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#type").val("");
		$.fn.zTree.getZTreeObj("tree_type").checkAllNodes(false);
	} 
	</script>
</body>
</html>
