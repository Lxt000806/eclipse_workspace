<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>客户管理列表页</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		
		<script type="text/javascript">
		$(function(){
			Global.JqGrid.initJqGrid("dataTable", {
				height:$(document).height()-$("#content-list").offset().top-95,
				url:"${ctx}/admin/custOrder/goJqGrid",
				colModel : [			
					{name: "pk", index: "pk", width: 75, label: "pk", sortable: true, align: "left", hidden:true},
					{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
					{name: "mobile1", index: "mobile1", width: 80, label: "手机号", sortable: true, align: "left"},
					{name: "date", index: "date", width: 120, label: "预约时间", sortable: true, align: "left", formatter: formatTime}
	            ]
			});
		});
		function doExcel(){
			var url = "${ctx}/admin/custOrder/doExcel";
			doExcelAll(url);
		}
		
		</script>
	</head>
	    
	<body>
		<div class="body-box-list" style="overflow-x:hidden">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>手机号</label>
							<input type="text" id="mobile1" name="mobile1" value="${data.mobile1}" />
						</li>
						<li>
							<label>客户名称</label>
							<input type="text" id="descr" name="descr" value="${data.descr}" />
						</li>
						<li class="search-group-shrink">
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					
					</ul>
				</form>
			</div>
			<div class="clear_float"> </div>
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="funBtn funBtn-system" onclick="doExcel()">输出至Excel</button>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>
</html>


