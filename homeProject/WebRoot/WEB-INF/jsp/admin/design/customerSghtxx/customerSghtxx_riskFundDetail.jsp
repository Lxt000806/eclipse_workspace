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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<style>
		.table-responsive {
			margin: 0px;
		}
		.ui-jqgrid-hdiv {
			width: auto !important;
		}
		.ui-jqgrid-bdiv {
			width: auto !important;
		}
	</style>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBtn">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript">
		var originalData, originalDataTable;
		$(function() {
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/customerSghtxx/getRiskFundJqGrid",
				postData:{code: "${custCode}"},
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI: "Bootstrap", 
				rowNum : 10000000,
				colModel : [
					{name: "no", index: "no", width: 80, label: "定责编号", sortable: true, align: "left"},
					{name: "faulttypedescr", index: "faulttypedescr", width: 100, label: "责任人类型", sortable: true, align: "left"},
					{name: "empnamechi", index: "empnamechi", width: 70, label: "员工姓名", sortable: true, align: "left"},
					{name: "workernamechi", index: "workernamechi", width: 70, label: "工人姓名", sortable: true, align: "left"},
					{name: "suppldescr", index: "suppldescr", width: 70, label: "供应商", sortable: true, align: "left"},
					{name: "amount", index: "amount", width: 80, label: "金额", sortable: true, align: "right",sum:true},
					{name: "riskfund", index: "riskfund", width: 80, label: "风控基金", sortable: true, align: "right",sum:true},
					{name: "issalarydescr", index: "issalarydescr", width: 70, label: "正常工资", sortable: true, align: "left"},
					{name: "confirmamount", index: "confirmamount", width: 70, label: "已发工资", sortable: true, align: "right"},
					{name: "remark", index: "remark", width: 180, label: "备注", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
				],
				loadComplete: function(){
					originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
				}
			});
			originalData = $("#page_form").serialize();
			$("#closeBtn").on("click",function(){
				doClose();
			});
		});
		function doClose() {
			closeWin();
		}
	</script>
</html>
