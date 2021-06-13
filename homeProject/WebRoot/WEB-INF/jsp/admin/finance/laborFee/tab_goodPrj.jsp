<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>集成费用导入</title>
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
			Global.JqGrid.initJqGrid("goodPrjDataTable",{
				url:"${ctx}/admin/laborFee/getGoodPrjJqGrid",
				postData:{
					nos:$("#nos").val(),
					custCodes:$("#custCodes").val(),
					jcazbtCustCodes:$("#jcazbtCustCodes").val(),
					jcazjfCustCodes:$("#jcazjfCustCodes").val(),
					cgazbtCustCodes:$("#cgazbtCustCodes").val(),
					cgazjfCustCodes:$("#cgazjfCustCodes").val(),
					dateFrom:$("#dateFrom").val(),
					dateTo:$("#dateTo").val(),
					no:$("#no").val()
				},
				height:330,
				rowNum:1000000,
				multiselect: true,
				colModel : [
					{name: "custcode", index: "custcode", width: 100, label: "客户编号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
					{name: "area", index: "area", width: 100, label: "面积", sortable: true, align: "right"},
					{name: "feetypedescr", index: "feetypedescr", width: 100, label: "费用类型", sortable: true, align: "right"},
					{name: "number", index: "number", width: 100, label: "数量", sortable: true, align: "right"},
					{name: "qtyfee", index: "qtyfee", width: 100, label: "单价", sortable: true, align: "right"},
					{name: "amount", index: "amount", width: 100, label: "金额", sortable: true, align: "right",formatter:DiyFmatter},
					{name: "feetype", index: "feetype", width: 100, label: "费用类型", sortable: true, align: "right",hidden:true},
					{name: "cardid", index: "cardid", width: 100, label: "卡号", sortable: true, align: "right",hidden:true},
					{name: "actname", index: "actname", width: 100, label: "户名", sortable: true, align: "right",hidden:true},
					{name: "regiondescr", index: "regiondescr", width: 200, label: "片区", sortable: true, align: "left", hidden: true},
					{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "结算状态", sortable: true, align: "right",hidden:true},
					{name: "documentno", index: "documentno", width: 100, label: "档案号", sortable: true, align: "right",hidden:true},
					{name: "custcheckdate", index: "custcheckdate", width: 100, label: "结算时间", sortable: true, align: "right",formatter:formatDate,hidden:true},
					{name: "confirmdate", index: "confirmdate", width: 100, label: "确认时间", sortable: true, align: "right",formatter:formatTime,hidden:true},
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
				<table id="goodPrjDataTable"></table>
			</div>
		</div>
	</body>
</html>
