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
		var intInsCalTyp = "${intInsCalTyp}";
		/**初始化表格*/
		$(function(){
			$("#driver").openComponent_driver();
			Global.JqGrid.initJqGrid("intFeeDataTable",{
				url:"${ctx}/admin/laborFee/getIntFeeJqGrid",
				postData:{
					nos:$("#nos").val(),
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
					{name: "regiondescr", index: "regiondescr", width: 200, label: "片区", sortable: true, align: "left", hidden: true},
					{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
					{name: "area", index: "area", width: 100, label: "面积", sortable: true, align: "right"},
					{name: "amount", index: "amount", width: 100, label: "衣柜安装费", sortable: true, align: "right",formatter:DiyFmatter},
					{name: "bathamount", index: "bathamount", width: 100, label: "浴室柜安装费", sortable: true, align: "right",formatter:DiyFmatter,hidden:intInsCalTyp=="2"?true:false},
					{name: "cupdownhigh", index: "cupdownhigh", width: 100, label: "浴室吊顶延米", sortable: true, align: "right",hidden:intInsCalTyp=="2"?true:false},
					{name: "cupuphigh", index: "cupuphigh", width: 100, label: "浴室地柜延米", sortable: true, align: "right",hidden:intInsCalTyp=="2"?true:false},
					{name: "qtyfee", index: "qtyfee", width: 100, label: "单价", sortable: true, align: "right",hidden:intInsCalTyp=="2"?true:false},
					{name: "feetype", index: "feetype", width: 100, label: "费用类型", sortable: true, align: "right",hidden:true},
					{name: "workerdescr", index: "workerdescr", width: 100, label: "工人名称", sortable: true, align: "right",hidden:true},
					{name: "feetypedescr", index: "feetypedescr", width: 100, label: "费用类型", sortable: true, align: "right",hidden:true},
					{name: "cardid", index: "cardid", width: 100, label: "卡号", sortable: true, align: "right",hidden:true},
					{name: "actname", index: "actname", width: 100, label: "户名", sortable: true, align: "right",hidden:true},
					{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "结算状态", sortable: true, align: "right",hidden:true},
					{name: "documentno", index: "documentno", width: 100, label: "档案号", sortable: true, align: "right",hidden:true},
					{name: "custcheckdate", index: "custcheckdate", width: 100, label: "结算时间", sortable: true, align: "right",formatter:formatTime,hidden:true},
					{name: "remarks", index: "remarks", width: 170, label: "备注", sortable: true, align: "left",hidden:true},
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
				<table id="intFeeDataTable"></table>
			</div>
		</div>
	</body>
</html>
