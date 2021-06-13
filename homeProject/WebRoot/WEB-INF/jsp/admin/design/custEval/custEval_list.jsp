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
	<title>客户评价查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/baseItem/doExcel";
		doExcelAll(url);
	}
	
	$(function(){
		$("#custCode").openComponent_customer();	
		$("#projectMan").openComponent_employee();	
		$("#designMan").openComponent_employee();	
		var postData = $("#page_form").jsonForm();
		var gridOption ={
			url:"${ctx}/admin/custEval/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-72,
			styleUI:"Bootstrap", 
			colModel : [
				{name: "code", index: "code", width: 102, label: "客户编号", sortable: true, align: "left"},
				{name: "custdescr", index: "custdescr", width: 102, label: "客户名称", sortable: true, align: "left"},
				{name: "projectdescr", index: "projectdescr", width: 134, label: "项目经理", sortable: true, align: "left"},
				{name: "prjscore", index: "prjscore", width: 104, label: "项目经理评分", sortable: true, align: "left"},
				{name: "designdescr", index: "designdescr", width: 170, label: "设计师", sortable: true, align: "left"},
				{name: "designscore", index: "designscore", width: 133, label: "设计师评分", sortable: true, align: "left", },
				{name: "lastupdate", index: "lastupdate", width: 115, label: "评价时间", sortable: true, align: "left",formatter: formatTime},
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	});
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="validate-group row" >
							<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode"/>
							</li>
							<li>
								<label>项目经理</label>
								<input type="text" id="projectMan" name="projectMan"/>
							</li>
							<li>
								<label>设计师</label>
								<input type="text" id="designMan" name="designMan"/>
							</li>
								<li>
									<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								</li>						
						</div>	
					</ul>
				</form>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
