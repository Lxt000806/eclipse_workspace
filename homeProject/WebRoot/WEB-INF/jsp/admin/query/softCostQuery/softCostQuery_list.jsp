<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>软装收支明细查询</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
//导出EXCEL
function doExcel(){
 	var url = "${ctx}/admin/softCostQuery/doExcel";
 	pageFormId="page_form";
	tableId="dataTable"
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}
	var pageFormId=pageFormId?pageFormId:"page_form";
	var colModel = $("#"+tableId).jqGrid('getGridParam','colModel');
	var rows = $("#"+tableId).jqGrid("getRowData");
	if (!rows || rows.length==0){
		art.dialog({
			content: "无数据导出"
		});
		return;
	}
	var datas = {
		colModel: JSON.stringify(colModel),
		rows: JSON.stringify(rows),
	};
	$.form_submit($("#"+pageFormId).get(0), {
		"action": url,
		"jsonString": JSON.stringify(datas)
	});
}
function goto_query(){
	if ($.trim($("#address").val())==''){	
	    if ($.trim($("#custCheckDateFrom").val())==''){
	    	art.dialog({content: "结算开始日期不能为空！",width: 200});
			return false;
	    }
	    if ($.trim($("#custCheckDateTo").val())==''){
	    	art.dialog({content: "结算结束日期不能为空！",width: 200});
			return false;
	    }
		var begindate=$("#custCheckDateFrom").val();
		var endDate=$("#custCheckDateFromTo").val();
		if (begindate>endDate){
			art.dialog({content: "结算开始日期不能大于结束日期！",width: 200});
			return false;
		}
	}
	removecss();//移除掉上一次的css样式，否则只有对上次有效
    changecss();
    $("#dataTable").jqGrid('destroyFrozenColumns');
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/softCostQuery/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	
}
function colAdd(col){
	var arrayTitle = ["销售额","材料成本","人工费用","成本小计","提成"];
	var arrayItemType12Descr =JSON.parse('${arrayItemType12Descr}');
	for(var j=0;j<arrayTitle.length;j++){
		for(var i=0;i<arrayItemType12Descr.length;i++){
	  		col.push(
	  			{name: arrayTitle[j]+arrayItemType12Descr[i], index: arrayTitle[j]+arrayItemType12Descr[i], width: 70, label: arrayItemType12Descr[i], sortable: true, align: "right",sum: true}
			);		
	    };
	    col.push(
	    		{name: arrayTitle[j]+"小计", index: arrayTitle[j]+"小计", width: 70, label:"小计", sortable: true, align: "right",sum: true}
		);	
	};
	return col;
}
//修改小计值	
function setTotalCol(){	  
    var rowIds = $("#dataTable").jqGrid('getDataIDs'); 
    var arrayTitle = ["销售额","材料成本","人工费用","成本小计","提成"];
	var arrayItemType12Descr =JSON.parse('${arrayItemType12Descr}'); 
	console.dir(rowIds);
    if(rowIds){  
	    for(var k = 0; k < rowIds.length;  k++) {
	    	var sum=0,value=0;
	    	for(var j=0;j<arrayTitle.length;j++){
				for(var i=0;i<arrayItemType12Descr.length;i++){
					value=parseFloat($("#dataTable").jqGrid("getCell",rowIds[k], arrayTitle[j]+arrayItemType12Descr[i]));//获取行某列的值
			  		sum=sum+value;	
			    };
	       		$("#dataTable").setCell(rowIds[k], arrayTitle[j]+"小计",sum);
	       		sum=0; 
			};
	   } 
	   for(var j=0;j<arrayTitle.length;j++){
			var  sumValue=$("#dataTable").getCol(arrayTitle[j]+"小计", false, 'sum'); 
			var s=arrayTitle[j]+"小计";
		    var footerData = {};
			footerData[s] = sumValue;
			 $("#dataTable").footerData('set', footerData);
		}; 
	}
}	
//移除掉上一次检索的css
function removecss(){
	$("div.frozen-div").remove();
}
//frozen并且分组的时候,有冻结列和未冻结列的高度不同。
function changecss(){  
	$(".frozen-div table thead tr:eq(1) th:eq(0) div").css({"height":"46.5px"});	
	
}		
/**初始化表格*/
$(function(){
    //初始化表格
	var  col =[];		
	col.push (
		{name: "code", index: "code", width: 70, label: "客户编号", sortable: true, align:"left",count:true,frozen: true},
		{name: "address", index: "address", width: 110, label: "楼盘", sortable: true, align:"left",frozen: true},
		{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align:"left",frozen: true}	
	);
   colAdd(col); 
    var gridOption ={
		height:$(document).height()-$("#content-list").offset().top-110,
		colModel :col,
		rowNum:100000,  
   		pager :'1',
		gridComplete: function() {
			setTotalCol();	
		}
	};	
    Global.JqGrid.initJqGrid("dataTable", gridOption);
    //表头分组  begin	
	var arrayItemType12Descr =JSON.parse('${arrayItemType12Descr}');
	var len=arrayItemType12Descr.length+1;
	var sColumnName=arrayItemType12Descr[0];
	$("#dataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[  
			{startColumnName: "销售额"+sColumnName, numberOfColumns: len, titleText: "销售额"},
			{startColumnName: "材料成本"+sColumnName, numberOfColumns: len, titleText: "材料成本"},
			{startColumnName: "人工费用"+sColumnName, numberOfColumns: len, titleText: "人工费用"},
			{startColumnName: "成本小计"+sColumnName, numberOfColumns: len, titleText: "成本小计"},
			{startColumnName: "提成"+sColumnName, numberOfColumns: len, titleText: "提成"}
		],
	});
});
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
						 <label>客户结算日期从</label>
							<input type="text" style="width:160px;" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.custCheckDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>到</label>
							<input type="text" style="width:160px;" id="custCheckDateTo" name="custCheckDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.custCheckDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<%-- <li>
							<label>客户类型</label>
								<house:xtdmMulit id="custType" dictCode="" sql="select rtrim(Code) Code, rtrim(Desc1) Descr from tCusttype order by dispSeq " 
								sqlValueKey="Code" sqlLableKey="Descr"  selectedValue="1,3,4,5,8,10,11"></house:xtdmMulit>
						</li> --%>
						 <li>
							<label>客户类型</label>
								<house:custTypeMulit id="custType" selectedValue="1,3,4,5,8,10,11"></house:custTypeMulit>
						</li> 
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" />
						</li>
					</ul>		
					<ul class="ul-form">
					<li id="loadMore" >
						<button type="button"  class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
					</li>				
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SOFTCOSTQUERY_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">输出到Excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


