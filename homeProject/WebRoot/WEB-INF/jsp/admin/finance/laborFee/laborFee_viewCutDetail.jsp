<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>人工费用管理-编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_workCard.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/laborFee/doSendDetailExcel";
		doExcelAll(url);
	}
	$(function(){
		var gridOption = {
			url:"${ctx}/admin/cutCheckOut/goDetailJqGrid",
			postData:{no:$.trim("${cutCheckOutNo}"),iano:$.trim("${iano}")},
			height:$(document).height()-$("#content-list").offset().top-70,
			rowNum:10000000,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "iano", index: "iano", width: 100, label: "领料单号", sortable: true, align: "left",},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left",},
				{name: "itemdescr", index: "itemdescr", width: 150, label: "材料名称", sortable: true, align: "left"},
				{name: "fixareadescr", index: "fixareadescr", width: 107, label: "区域", sortable: true, align: "left"},
				{name: "fixareapk", index: "fixareapk", width: 98, label: "区域", sortable: true, align: "left",hidden:true},
				{name: "qty", index: "qty", width: 55, label: "数量", sortable: true, align: "right",},
				{name: "cuttypedescr", index: "cuttypedescr", width: 55, label: "切割方式", sortable: false, align: "left"},
				{name: "cutfee", index: "cutfee", width: 72, label: "加工单价", sortable: true, align: "right",},
				{name: "total", index: "total", width: 60, label: "总价", sortable: true, align: "right", sum: true,},
				{name: "remarks", index: "remarks", width: 150, label: "加工备注", sortable: true, align: "left",},
				{name: "oldcuttypedescr", index: "oldcuttypedescr", width: 85, label: "原切割方式", sortable: true, align: "left"},
				{name: "itemreqremarks", index: "itemreqremarks", width: 150, label: "材料需求备注", sortable: true, align: "left"},
				{name: "iscompletedescr", index: "iscompletedescr", width: 70, label: "是否完成", sortable: true, align: "left",},
				{name: "completedate", index: "completedate", width: 120, label: "完成时间", sortable: true, align: "left", formatter: formatTime},
				{name: "refpk", index: "refpk", width: 95, label: "领料明细pk", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 84, label: "材料编号", sortable: true, align: "left"},
				{name: "size", index: "size", width: 56, label: "规格", sortable: true, align: "left",hidden:true},
				{name: "iaqty", index: "iaqty", width: 70, label: "领料数量", sortable: true, align: "right",hidden:true},
				{name: "allowmodify", index: "allowmodify", width: 60, label: "是否允许修改单价", sortable: true, align: "left",hidden:true},
				{name: "iscomplete", index: "iscomplete", width: 70, label: "是否完成", sortable: false, align: "left",hidden:true},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
			],
			gridComplete:function(){
				var needSum1 = $("#dataTable").getCol("sendqty",false,"sum");
				$("#dataTable").footerData("set",{"sendqty":myRound(needSum1,2)});
				var needSum2 = $("#dataTable").getCol("weight",false,"sum");
				$("#dataTable").footerData("set",{"weight":myRound(needSum2,2)});
				var needSum3 = $("#dataTable").getCol("amount",false,"sum");
				$("#dataTable").footerData("set",{"amount":myRound(needSum3,2)});
			},
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	});
	function DiyFmatter (cellvalue, options, rowObject){ 
		return myRound(cellvalue,2);
	}
	</script>
</head>
  
<body>
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system">
   				<div class="panel-body">
      				<div class="btn-group-xs">
						<button type="button" class="btn btn-system "  onclick="doExcelNow('人工费用管理发货明细表')" title="导出检索条件数据">
								<span>导出excel</span>
							</button>	
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="edit-form" hidden="true">
				<div class="panel panel-info">  
	         		<div class="panel-body">
	         			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
							<input type="hidden" name="jsonString" value=""/>
						</form>
					</div>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>	
		</div>
	</div>
</body>
</html>
