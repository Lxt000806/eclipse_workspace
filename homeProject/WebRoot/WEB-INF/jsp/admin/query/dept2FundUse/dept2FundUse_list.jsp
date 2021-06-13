<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>资金占用统计</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
function view(){
	var ret = selectDataTableRow();
	var params=$("#page_form").jsonForm();
	params.supplierDepartment2=ret.department2;
    if (ret) {
      Global.Dialog.showDialog("dept2FundUseDetail",{
		  title:"资金占用明细-查看",
		  url:"${ctx}/admin/dept2FundUse/goView?",
		  postData:params,
		  height:650,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
         
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/dept2FundUse/doExcel";
	doExcelAll(url);
}
function goto_query(){
	if($.trim($("#dateFrom1").val())==''){
			art.dialog({content: "统计开始日期不能为空",width: 200});
			return false;
	} 
	if($.trim($("#dateTo1").val())==''){
			art.dialog({content: "统计结束日期不能为空",width: 200});
			return false;
	}
     var dateStart = Date.parse($.trim($("#dateFrom1").val()));
     var dateEnd = Date.parse($.trim($("#dateTo1").val()));
     if(dateStart>dateEnd){
    	 art.dialog({content: "统计开始日期不能大于结束日期",width: 200});
			return false;
     } 
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
/**初始化表格*/
$(function(){
        //初始化表格

		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/dept2FundUse/goJqGrid",
			postData:$("#page_form").jsonForm(),
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			colModel : [
			    {name: "department2", index: "department2", width: 100, label: "战队编号", sortable: true, align: "left", count: true ,hidden: true},
			 	{name: "dept2descr", index: "dept2descr", width: 100, label: "战队", sortable: true, align: "left", count: true},
			 	{name: "prepayfee", index: "prepayfee", width: 100, label: "预付款", sortable: true, align: "right", sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
				{name: "purarrfee", index: "purarrfee", width: 100, label: "采购入库", sortable: true, align: "right", sum: true, formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },       
				{name: "otherfee", index: "otherfee", width: 120, label: "采购结算差价", sortable: true, align: "right", sum: true,formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
				{name: "preamount", index: "preamount", width: 100, label: "预付抵扣", sortable: true, align: "right", sum: true,formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
			    {name: "laborfee", index: "laborfee", width: 100, label: "人工费用", sortable: true, align: "right", sum: true,formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} },
			    {name: "total", index: "total", width: 100, label: "合计", sortable: true, align: "right", sum: true,formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2} }
		    ],
        	
		});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form"  class="form-search">
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>统计日期</label>
						<input type="text" id="dateFrom1" name="dateFrom1" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.dateFrom1}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>					
						<label>至</label>
						<input type="text" id="dateTo1" name="dateTo1" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.dateTo1}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
            	<house:authorize authCode="DEPT2FUNDUSE_VIEW">
					<button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="DEPT2FUNDUSE_EXCEL">
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
		</div> 
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


