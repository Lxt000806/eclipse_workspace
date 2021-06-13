<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<title>巡检覆盖率分析查看</title>
		<%@ include file="/commons/jsp/common.jsp" %>
		
		<script type="text/javascript">
			function doExcelView(){
				doExcelAll("${ctx}/admin/progCheckCoverage/doExcel", "viewDataTable", "progCheckCoverageDataForm");
			}
			$(function(){
				Global.JqGrid.initJqGrid("viewDataTable",{
					url:"${ctx}/admin/progCheckCoverage/goJqGridView",
					postData:{
						type:"${data.type}",
						dateFrom:"${data.dateFromView}",
						dateTo:"${data.dateToView}",
						isCheckDept:"${data.isCheckDeptView}"
					},
					height:350,
					colModel : [		
						{name: "address", index: "address", width: 200, label: "工地", sortable: true, align: "left"},
						{name: "department2descr", index: "department2descr", width: 80, label: "工程部", sortable: true, align: "left"},
						{name: "progchecknum", index: "progchecknum", width: 80, label: "巡检次数", sortable: true, align: "right"},	
						{name: "minprogcheckdate", index: "minprogcheckdate", width: 140, label: "最先巡检时间", sortable: true, align: "left", formatter: formatTime},
						{name: "maxprogcheckdate", index: "maxprogcheckdate", width: 140, label: "最后巡检时间", sortable: true, align: "left", formatter: formatTime},
						{name: "confirmbegin", index: "confirmbegin", width: 140, label: "实际开工日期", sortable: true, align: "left", formatter: formatTime, hidden: true},
						{name: "prjitemdescr", index: "prjitemdescr", width: 140, label: "实际进度", sortable: true, align: "left", hidden: true}
		            ]
				});
				if("${data.type}" == "0"){
					$("#viewDataTable").jqGrid("showCol", "confirmbegin");
					$("#viewDataTable").jqGrid("showCol", "prjitemdescr");
				}
			});
			
		</script>
	</head>
	<body>
	
		<form action="" method="post" id="progCheckCoverageDataForm" class="form-search" hidden>
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" name="type" value="${data.type}"/>
			<input type="hidden" name="dateFrom" value="${data.dateFromView}"/>
			<input type="hidden" name="dateTo" value="${data.dateToView}"/>
			<input type="hidden" name="isCheckDept" value="${data.isCheckDeptView}"/>
		</form>
		<div class="body-box-list">
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
					<button type="button" class="btn btn-system " onclick="doExcelView()">输出至Excel</button>
				</div>
			</div> 
			<table id= "viewDataTable"></table>  
			<div id= "viewDataTablePager"></div>  
		</div>
	</body>
</html>


