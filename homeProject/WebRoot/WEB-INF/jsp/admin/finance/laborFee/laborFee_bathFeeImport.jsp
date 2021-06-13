<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>导入浴室柜安装费</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
var selectRows = [];
var excelTable="dataTable";
var excelName="浴室柜安装费";
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		// url:"${ctx}/admin/laborFee/getBathFeeJqGrid",
		postData:{
			custCodes:$("#custCodes").val(),
			dateFrom:$("#dateFrom").val(),
			dateTo:$("#dateTo").val(),
			toilet:"${toilet}",
			cabinet:"${cabinet}"
		},
		height:$(document).height()-$("#content-list").offset().top-38,
		multiselect: true,
		rowNum:10000000,
		colModel : [
			// {name: "no", index: "no", width: 100, label: "发货单号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
			{name: "regiondescr", index: "regiondescr", width: 200, label: "片区", sortable: true, align: "left", hidden: true},
			{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 100, label: "安装费", sortable: true, align: "right",formatter:DiyFmatter},
			{name: "toiletsendamount", index: "toiletsendamount", width: 100, label: "马桶发货数量", sortable: true, align: "right",formatter:DiyFmatter},
			{name: "cabinetsendamount", index: "cabinetsendamount", width: 110, label: "浴室柜发货数量", sortable: true, align: "right",formatter:DiyFmatter},
			{name: "toiletqty", index: "toiletqty", width: 100, label: "马桶申报数量", sortable: true, align: "right",formatter:DiyFmatter},
			{name: "cabinetqty", index: "cabinetqty", width: 110, label: "浴室柜申报数量", sortable: true, align: "right",formatter:DiyFmatter},
			{name: "feetype", index: "feetype", width: 100, label: "费用类型", sortable: true, align: "right",hidden:true},
			{name: "feetypedescr", index: "feetypedescr", width: 100, label: "费用类型", sortable: true, align: "right",hidden:true},
			{name: "cardid", index: "cardid", width: 100, label: "卡号", sortable: true, align: "right",hidden:true},
			{name: "actname", index: "actname", width: 100, label: "户名", sortable: true, align: "right",hidden:true},
			{name: "custcode", index: "custcode", width: 100, label: "客户编号", sortable: true, align: "left",hidden:true},
			{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "结算状态", sortable: true, align: "right",hidden:true},
			{name: "documentno", index: "documentno", width: 100, label: "档案号", sortable: true, align: "right",hidden:true},
			{name: "custcheckdate", index: "custcheckdate", width: 100, label: "结算日期", sortable: true, align: "right",formatter:formatDate,hidden:true},
			{name: "paydate", index: "paydate", width: 100, label: "确认时间", sortable: true, align: "right",formatter:formatDate,hidden:true},
			{name: "workercode", index: "workercode", width: 100, label: "工人编号", sortable: true, align: "right",hidden:true},
			{name: "custworkpk", index: "custworkpk", width: 100, label: "工地工人pk", sortable: true, align: "right",hidden:true},
			{name: "refcustcode", index: "refcustcode", width: 100, label: "关联客户", sortable: true, align: "right",},
			{name: "no", index: "no", width: 100, label: "领料单号", sortable: true, align: "right",hidden:true},
			{name: "appsendno", index: "appsendno", width: 100, label: "发货单号", sortable: true, align: "left",hidden:true},
		],
	});
	
	Global.JqGrid.initJqGrid("dataTable_notApp",{
		// url:"${ctx}/admin/laborFee/getBathFeeJqGrid",
		postData:{
			custCodes:$("#custCodes").val(),
			dateFrom:$("#dateFrom").val(),
			dateTo:$("#dateTo").val(),
			toilet:"${toilet}",
			cabinet:"${cabinet}"
		},
		height:$(document).height()-$("#content-list").offset().top-38,
		rowNum:10000000,
		colModel : [
			{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
			{name: "toiletsendamount", index: "toiletsendamount", width: 100, label: "马桶发货数量", sortable: true, align: "right",formatter:DiyFmatter},
			{name: "cabinetsendamount", index: "cabinetsendamount", width: 110, label: "浴室柜发货数量", sortable: true, align: "right",formatter:DiyFmatter},
		],
	});
	
	replaceCloseEvt("cupFeeImport", closeAndReturn);
	
	function DiyFmatter (cellvalue, options, rowObject){ 
		return myRound(cellvalue,2);
	}
}); 
function save(){
	var bathIds=$("#dataTable").jqGrid("getGridParam", "selarrrow");
	var custCodes=$("#custCodes").val();
	if(bathIds.length == 0){
		art.dialog({
			content:"请选择数据后保存"
		});				
		return ;
	}
	$.each(bathIds,function(i,id){
		var rowData = $("#dataTable").jqGrid("getRowData", id);
		rowData.feetype = rowData.feetype.toUpperCase();
		selectRows.push(rowData);
		if ("${isHaveSendNo}" == "0") {
			rowData.appsendno='';
		}
		if(custCodes != ""){
			custCodes += ",";
		}
		custCodes += rowData.custcode;
	});
	$("#custCodes").val(custCodes);
	art.dialog({content: "添加成功！",width: 200,time:800});
	search();
}
function closeAndReturn(){
	Global.Dialog.returnData = selectRows;
   	closeWin();
}
function search() {
	if ("" == $("#dateFrom").val()) {
		art.dialog({
			content:"请填写开始时间"
		});				
		return ;
	}
	$(".s-ico").css("display","none");
	$("#dataTable").jqGrid("setGridParam",
		{
			postData:$("#page_form").jsonForm(),
			url:"${ctx}/admin/laborFee/getBathFeeJqGrid?isNotApp=0",
			page:1,
			sortname:''
		}).trigger("reloadGrid");
		
	$("#dataTable_notApp").jqGrid("setGridParam",
	{
		postData:$("#page_form").jsonForm(),
		url:"${ctx}/admin/laborFee/getBathFeeJqGrid?isNotApp=1",
		page:1,
		sortname:''
	}).trigger("reloadGrid");
}

function changePage(table,name){
	excelTable=table;
	excelName=name;
}

function doExcel(){
	doExcelNow(excelName,excelTable,"page_form");
}
</script>
</head>
<body >
	<div class="body-box-form" >
		<div class="content-form">
			<div class="panel panel-system" >
	    		<div class="panel-body" >
	     			<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBut" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="excel" onclick="doExcel()">
							<span>导出Excel</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeAndReturn()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="body-box-list">
			<div class="query-form"> 
				<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<input type="text" hidden="true" id="custCodes" name="custCodes" style="width:160px;" value="${laborFee.custCodes }" />
						<li>
							<label>时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								 value="<fmt:formatDate value='${laborFee.dateFrom}' pattern='yyyy-MM-dd'/>"  />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${laborFee.dateTo}' pattern='yyyy-MM-dd'/>"  />
						</li>
						<li class="search-group" >
							<button type="button" class="btn btn-sm btn-system" onclick="search()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</ul>
				</form>
			</div>		
			<ul class="nav nav-tabs" >  
		        <li class="active" onClick="changePage('dataTable','浴室柜安装费')" id="cup">
		        	<a href="#tab_BathFee" data-toggle="tab">浴室柜安装费</a>
		        </li>
		        <li onClick="changePage('dataTable_notApp','未申报')" id="notApp">
		        	<a href="#tab_notApp" data-toggle="tab">未申报</a>
		        </li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_BathFee"  class="tab-pane fade in active"> 
		        	<div id="content-list">
		        		<table id="dataTable"></table>
		        	</div>
		        </div> 
		        <div id="tab_notApp"  class="tab-pane fade "> 
		        	<div id="content-list">
		        		<table id="dataTable_notApp"></table>
		        	</div>
		        </div> 
		    </div>  
		</div>
	</div>
</body>
</html>
