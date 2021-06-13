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
			url:"${ctx}/admin/laborFee/goItemSendDetialJqGrid",
			postData:{no:$.trim("${appSendNo}")},
			height:$(document).height()-$("#content-list").offset().top-70,
			rowNum:10000000,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 90, label: "材料名称", sortable: true, align: "left"},
				{name: "sendqty", index: "sendqty", width: 87, label: "数量", sortable: true, align: "right",sum:true},
				{name: "uomdescr", index: "uomdescr", width: 90, label: "单位", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 70, label: "安装费", sortable: true, align: "right", sum: true, formatter: DiyFmatter},
				{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 79, label: "材料类型2", sortable: true, align: "left"},
				{name: "weight", index: "weight", width: 87, label: "重量", sortable: true, align: "right", sum:true},
				{name: "fixareadescr", index: "fixareadescr", width: 129, label: "区域", sortable: true, align: "left"}
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
