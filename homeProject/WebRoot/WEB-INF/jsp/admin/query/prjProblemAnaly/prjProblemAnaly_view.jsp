<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<title>工地问题分析—查看</title>
		<%@ include file="/commons/jsp/common.jsp" %>
		<script type="text/javascript">
		/**初始化表格*/
		$(function(){
		
			Global.JqGrid.initJqGrid("dataTable", {
				url:"${ctx}/admin/prjProblemAnaly/goJqGridView",
				styleUI: "Bootstrap", 
				height: 450,
				rowNum: 10000,
				postData: {
					prjDepartment2: "${prjProblem.prjDepartment2}",
					promDeptCode:  "${prjProblem.promDeptCode}",
					statistcsMethod:  "${prjProblem.statistcsMethod}"
				},
				colModel : [
					{name: "no", index: "no", width: 70, label: "编号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
					{name: "appczydescr", index: "appczydescr", width: 60, label: "提报人", sortable: true, align: "left"},
					{name: "appdate", index: "appdate", width: 120, label: "提报时间", sortable: true, align: "left", formatter: formatTime},
					{name: "promdeptdescr", index: "promdeptdescr", width: 70, label: "责任部门", sortable: true, align: "left"},
					{name: "promtypedescr", index: "promtypedescr", width: 70, label: "责任分类", sortable: true, align: "left"},
			      	{name: "prjprompropdescr", index: "prjprompropdescr", width: 70, label: "责任性质", sortable: true, align: "left"},
					{name: "isbringstopdescr", index: "isbringstopdescr", width: 70, label: "导致停工", sortable: true, align: "left"},
					{name: "stopdays", index: "stopdays", width: 70, label: "停工天数", sortable: true, align: "right"},
					{name: "remarks", index: "remarks", width: 200, label: "问题描述", sortable: true, align: "left"}
			    ],
			});
		});
		</script>
	</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<table id="dataTable"></table>
			</div>	
		</div>
	</body>
</html>


