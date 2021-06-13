<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE html>
<html>
<head>
	<title>搜寻——领用发货单</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemApp.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	$("#custCode").openComponent_customer({showValue:"${laborFee.custCode}"});	 
	$("#whCode").openComponent_wareHouse();	 
	$("#iaNo").openComponent_itemApp();	 

	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1","itemType2");
	Global.LinkSelect.setSelect({firstSelect:"itemType1",
								 firstValue:"${laborFee.itemType1}",
								 secondSelect:"itemType2",
								 secondValue:"${laborFee.itemType2 }",
								});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_detail",{
		height:210,
		rowNum: 10000,
		colModel : [
			{name: "itemcode", index: "itemcode", width: 111, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 280, label: "材料名称", sortable: true, align: "left"},
			{name: "sendqty", index: "sendqty", width: 87, label: "数量", sortable: true, align: "left"},
			{name: "uomdescr", index: "uomdescr", width: 90, label: "单位", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 179, label: "区域", sortable: true, align: "left"},
			{name: "azfee", index: "azfee", width: 179, label: "安装费", sortable: true, align: "right"},
           ]
	});
		
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/laborFee/goItemSendNoJqGrid",
		postData:{custCode:"${laborFee.custCode}",itemType1:"${laborFee.itemType1}"},	
		height:220,
		rowNum:10000000,
		colModel : [
			{name: "issetitem", index: "issetitem", width: 114, label: "套餐材料", sortable: true, align: "left",hidden:true},
			{name: "no", index: "no", width: 114, label: "领料发货单号", sortable: true, align: "left"},
			{name: "date", index: "date", width: 136, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
			{name: "iano", index: "iano", width: 106, label: "领料申请编号", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 93, label: "领料单类型", sortable: true, align: "left"},
			{name: "documentno", index: "documentno", width: 81, label: "档案号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 187, label: "楼盘", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 99, label: "客户名", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 91, label: "材料类型1", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 104, label: "材料类型2", sortable: true, align: "left"},
			{name: "senddate", index: "senddate", width: 113, label: "送货日期", sortable: true, align: "left", formatter: formatTime},
			{name: "sendtype", index: "sendtype", width: 82, label: "送货类型", sortable: true, align: "left"},
			{name: "date", index: "date", width: 135, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
			{name: "remarks", index: "remarks", width: 160, label: "备注", sortable: true, align: "left"}			
		],
		onSelectRow : function(id) {
			var row = selectDataTableRow("dataTable");
			$("#dataTable_detail").jqGrid('setGridParam',{url : "${ctx}/admin/laborFee/goItemSendDetialJqGrid?no="+row.no});
			$("#dataTable_detail").jqGrid().trigger('reloadGrid');
		},
		ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
          	Global.Dialog.returnData = selectRow;
          	Global.Dialog.closeDialog();
		}	
	});
});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">	
							<li>
								<label>发货单号</label>
								<input type="text" id="no" name="no" style="width:160px;"/>
							</li>
							<li>
								<label>领料申请编号</label>
								<input type="text" id="iaNo" name="iaNo" style="width:160px;"/>
							</li>
							<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" />
							</li>
							<li>
								<label>仓库</label>
								<input id="whCode" name="whCode" style="width: 160px;"  ></input>
							</li>
							<li>
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width: 160px;" value="ZC"></select>
							</li>
							<li>
								<label>材料类型2</label>
								<select id="itemType2" name="itemType2" style="width: 160px;" ></select>
							</li>
							 <li>
								<label>发货日期从 </label>
								<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<label>至</label>
								<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
								<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
							</li>
						</ul>
				</form>
			</div>
			</div>
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>
				<div id="content-list">
					<ul class="nav nav-tabs" > 
				        <li class="active"><a href="" data-toggle="tab">发货明细</a></li>
				    </ul> 
					<table id= "dataTable_detail"></table>
				</div> 
			</div>
	</body>	
</html>
