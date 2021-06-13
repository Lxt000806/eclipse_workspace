<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>采购部分到货统计</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	var tableId="dataTable";
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			colModel : [
			    {name: "itemcode", index: "itemcode", width: 100, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 260, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 120, label: "材料类型2", sortable: true, align: "left"},
				{name: "arrivqty", index: "arrivqty", width: 80, label: "到货数量", sortable: true, align: "right",sum: true},
				{name: "totalcost", index: "totalcost", width: 112, label: "成本总价", sortable: true, align: "right",formatter: 'number', formatoptions: { decimalPlaces: 2 },hidden:true,sum: true},
				{name: "qtycal", index: "qtycal", width: 80, label: "库存数量", sortable: true, align: "right"},
	           ]
		});
		if('${purchase.costRight}'=='1'){
			$("#dataTable").jqGrid('showCol', "totalcost");
		}
	
		$("#whcode").openComponent_wareHouse();
 		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
				
	});
	
	function goto_query(){
		var postData = $("#page_form").jsonForm();
		if(!postData.itemType1){
			art.dialog({ 
				content: "请选择材料类型1！"
			});
			return;
		}
		if(!postData.whcode){
			art.dialog({ 
				content: "请选择仓库！"
			});
			return;
		}
		$("#"+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/purchasePartArrive/goJqGrid",postData:postData,page:1}).trigger("reloadGrid");
	}
	
	function detail() {
		var postData = $("#page_form").jsonForm();
		var ret = selectDataTableRow(tableId);
	 	if(ret){
	 	 	var params=$("#page_form").jsonForm();
	 	 	if(params.statistcsMethod == '1'){
	 	 		params.itCode = ret.itemcode;
	 	 	}else{
	 	 		params.itemType2 = ret.itemtype2
	 	 	}
			Global.Dialog.showDialog("view",{
				title:"采购部分到货统计明细",
				url:"${ctx}/admin/purchasePartArrive/goDetail",
				postData:params,
				height:700,
				width:1000,
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	}     
	//导出EXCEL
	function doExcel(){
		var url = "${ctx}/admin/purchasePartArrive/doExcel";
		doExcelAll(url,tableId);
	}
	function change(ele){
		if(ele.value=="2"){
			tableId="itemType2DataTable";
			$("#content-list2").css("display","block");
			$("#content-list").css("display","none");
			Global.JqGrid.initJqGrid("itemType2DataTable",{
				url:"${ctx}/admin/purchasePartArrive/goJqGrid",
				postData:$("#page_form").jsonForm(),
				height:$(document).height()-$("#content-list2").offset().top-100,
				styleUI: 'Bootstrap',
				colModel : [
				    {name: "itemtype2", index: "itemtype2", width: 160, label: "材料类型2", sortable: true, align: "left",hidden:true},
					{name: "itemtype2descr", index: "itemtype2descr", width: 120, label: "材料类型2", sortable: true, align: "left"},
					{name: "arrivqty", index: "arrivqty", width: 95, label: "到货数量", sortable: true, align: "right",sum: true},
					{name: "totalcost", index: "totalcost", width: 112, label: "成本总价", sortable: true, align: "right",hidden:true,sum: true}
	            ]
			});
			if('${purchase.costRight}'=='1'){
				$("#itemType2DataTable").jqGrid('showCol', "totalcost");
			}
		}else{
			tableId="dataTable";
			$("#content-list").css("display","block");
			$("#content-list2").css("display","none");
			if('${purchase.costRight}'=='1'){
				$("#dataTable").jqGrid('showCol', "avgcost");
				$("#dataTable").jqGrid('showCol', "totalcost");
			}
	
		}
	}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="F" /> 
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>仓库编号</label>
						<input type="text" id="whcode" name="whcode"  />
					</li>
					<li>
						<label>材料类型1</label> <select id="itemType1" name="itemType1"></select>
					</li>
					<li>
						<label>到货日期小于</label> 
						<input type="text" id="arriveDate" name="arriveDate" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${purchase.arriveDate}" pattern='yyyy-MM-dd'/>" />
					</li>
					
					<li>
						<label>统计方式</label> 
						<select id="statistcsMethod" name="statistcsMethod" onchange="change(this)">
							<option value="1">按材料汇总</option>
							<option value="2">按材料类型2汇总</option>
						</select>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="PURCHASEPARTARRIVE_VIEW">
					<button type="button" class="btn btn-system" onclick="detail()">查看</button>
				</house:authorize>
				<house:authorize authCode="PURCHASEPARTARRIVE_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
				<div id="content-list2"  style="display: none">
					<table id= "itemType2DataTable"></table>
					<div id="itemType2DataTablePager"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>


