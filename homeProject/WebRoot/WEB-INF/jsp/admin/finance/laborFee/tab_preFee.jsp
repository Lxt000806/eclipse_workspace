<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>预发安装费导入</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_driver.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			$("#driver").openComponent_driver();
			Global.JqGrid.initJqGrid("preFeeDataTable",{
				url:"${ctx}/admin/laborFee/goPreFeeJqGrid",
				postData:{
					nos:$("#nos").val(),
					dateFrom:$("#dateFrom").val(),
					dateTo:$("#dateTo").val()
				},
				height:330,
				rowNum:10000000,
				multiselect: true,
				colModel : [
					{name: "appsendno", index: "appsendno", width: 100, label: "发货单号", sortable: true, align: "left"},
					{name: "custcode", index: "custcode", width: 100, label: "客户编号", sortable: true, align: "left",hidden:true},
					{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
					{name: "regiondescr", index: "regiondescr", width: 200, label: "片区", sortable: true, align: "left", hidden: true},
					{name: "itemtype2descr", index: "itemtype2descr", width: 100, label: "材料类型2", sortable: true, align: "left"},
					{name: "amount", index: "amount", width: 100, label: "安装费", sortable: true, align: "right", formatter: "number", formatoptions: {decimalPlaces: 2, thousandsSeparator: ""}},
					{name: "feetype", index: "feetype", width: 100, label: "费用类型", sortable: true, align: "right",hidden:true},
					{name: "workerdescr", index: "workerdescr", width: 100, label: "工人名称", sortable: true, align: "right",hidden:true},
					{name: "feetypedescr", index: "feetypedescr", width: 100, label: "费用类型", sortable: true, align: "right",hidden:true},
					{name: "cardid", index: "cardid", width: 100, label: "卡号", sortable: true, align: "right",hidden:true},
					{name: "actname", index: "actname", width: 100, label: "户名", sortable: true, align: "right",hidden:true},
					{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "客户结算时间", sortable: true, align: "right",hidden:true},
					{name: "documentno", index: "documentno", width: 100, label: "档案号", sortable: true, align: "right",hidden:true},
					{name: "custcheckdate", index: "custcheckdate", width: 100, label: "档案号", sortable: true, align: "right",formatter:formatDate,hidden:true},
					{name: "workercode", index: "workercode", width: 100, label: "工人编号", sortable: true, align: "left",hidden:true},
					{name: "custworkpk", index: "custworkpk", width: 100, label: "工地工人pk", sortable: true, align: "left",hidden:true},
					{name: "iano", index: "iano", width: 75, label: "领料单号", sortable: true, align: "left",},
					{name: "issetitem", index: "issetitem", width: 75, label: "是否套餐", sortable: true, align: "left",hidden:true},
					{name: "issetitemdescr", index: "issetitemdescr", width: 75, label: "是否套餐", sortable: true, align: "left",hidden:true},
					{name: "refcustcode", index: "refcustcode", width: 75, label: "关联客户", sortable: true, align: "left",hidden:true},
	            ],
			});
			function DiyFmatter (cellvalue, options, rowObject){ 
			    return myRound(cellvalue,2);
			}
		});
	</script>
	</head>
	<body>
		<div class="body-box-list">
			<div>
				<table id="preFeeDataTable"></table>
			</div>
		</div>
	</body>
</html>
