<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料销售分析--按单品</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
	function doExcel() {
		var url ="${ctx}/admin/itemSaleAnalyse/doExcel";
		doExcelAll(url);
	}
$(function(){
	$("#supplCode").openComponent_supplier();	
	$("#code").openComponent_item();	
	//初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemSaleAnalyse/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: "Bootstrap",
		postData:$("#page_form").jsonForm(),
		colModel : [
		  {name : "code",index : "code",width : 65,label:"材料编号",sortable : true,align : "left"} ,
		  {name : "descr",index : "descr",width : 200,label:"材料名称",sortable : true,align : "left"} ,
		  {name : "uomdescr",index : "uomdescr",width : 50,label:"单位",sortable : true,align : "left"},
		  {name : "suppldescr",index : "suppldescr",width : 120,label:"供应商名称",sortable : true,align : "left"},
	      {name : "planqty",index : "planqty",width : 70,label:"预算数量",sortable : true,align : "right"},
	      {name : "planprice", index: "planprice", width: 70, label: "预算金额", sortable: true, align: "right",sum: true},
	      {name : "chgqty",index : "chgqty",width : 70,label:"增减数量",sortable : true,align : "right"},
	      {name : "chgprice", index: "chgprice", width:70 , label: "增减金额", sortable: true, align: "right",sum: true},
	      {name : "saleqty", index: "saleqty", width: 70, label: "销售数量", sortable: true, align: "right",sum: true},
	      {name : "saleprice",index : "saleprice",width : 70,label:"销售金额",sortable : true,align : "right",},
	      {name : "chgcost",index : "chgcost",width : 70,label:"变更成本",sortable : true,align : "right",hidden: true},
	      {name : "plancost",index : "plancost",width : 70,label:"预算成本",sortable : true,align : "right",hidden: true},
		  {name : "profit",index : "profit",width : 70,label:"毛利润",sortable : true,align : "right",sum: true},
	    ]
	});
});

function view(){
	var ret = selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条数据进行查看",
		});
		return;
	}
	
	var postData = $("#page_form").jsonForm();
	postData["code"] = ret.code;
	Global.Dialog.showDialog("view",{
		title:"明细",
		url:"${ctx}/admin/itemSaleAnalyse/goView",
		postData: postData,
		height:680,
		width:1200,
	});
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${item.dateFrom}' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${item.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
					   <label>材料类型1</label>
					   <select id="itemType1" name="itemType1"  value="${item.itemType1}"></select>
					</li>
					<li>
					   <label>材料类型2</label>
					   <select id="itemType2" name="itemType2"  value="${item.itemType2}"></select>
					</li>
					<li>
						<label>供应商编号</label>
						<input type="text" id="supplCode" name="supplCode" value="${item.supplCode}"/>
					</li>
					<li>
						<label>材料编号</label>
						<input type="text" id="code" name="code" value="${item.code}"/>
					</li>
					<li>
						<label>是否套餐</label>
						<house:xtdm  id="isOutSet" dictCode="YESNO" style="width:160px;" value="${item.isOutSet}"></house:xtdm>
					</li>
					
					<li id="loadMore" >
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm"  >
            	<house:authorize authCode="ITEMSALEANALYSE_VIEW">
					<button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>	
				</house:authorize>	
				<house:authorize authCode="ITEMSALEANALYSE_EXCEL">
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


