<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>签单数据统计</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_perfCycle.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/perfCycle/doSignDataExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function(){
		$("#no").openComponent_perfCycle();	
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-110,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 80, label: "档案号", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 87, label: "客户类型", sortable: true, align: "left"},
				{name: "signdate", index: "signdate", width: 88, label: "签订时间", sortable: true, align: "left", formatter: formatDate},
				{name: "freebaseamount", index: "freebaseamount", width: 90, label: "甲醛水电", sortable: true, align: "right", sum: true, title:"甲醛治理和水电延保"},
				
			],
			
		});
	});
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#page_form select").val("");	
	}
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/perfCycle/goSignDataJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
			
					<li>
						<label>统计周期</label>
						<input type="text" style="width:160px;" id="no" name="no" />
					</li>
					
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
