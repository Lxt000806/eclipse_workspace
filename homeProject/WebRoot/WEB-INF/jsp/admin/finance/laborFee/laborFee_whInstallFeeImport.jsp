<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>导入搬运费</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
var selectRows = [];
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		postData:{
			dateFrom:$("#dateFrom").val(),
			dateTo:$("#dateTo").val(),
			nos:$("#nos").val(),
			no:"${laborFee.no}",
		},
		height:$(document).height()-$("#content-list").offset().top-38,
		multiselect: true,
		rowNum:1000000,
		colModel : [
			{name: "documentno", index: "documentno", width: 80, label: "档案号", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 100, label: "客户编号", sortable: true, align: "left",hidden:true},
			{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
			{name: "regiondescr", index: "regiondescr", width: 200, label: "片区", sortable: true, align: "left", hidden: true},
			{name: "appsendno", index: "appsendno", width: 100, label: "发货单号", sortable: true, align: "left"},
			{name: "iano", index: "iano", width: 100, label: "领料单号", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 100, label: "装卸费", sortable: true, align: "right",formatter:DiyFmatter},	
			{name: "feetype", index: "feetype", width: 100, label: "费用类型", sortable: true, align: "right",hidden:true},
			{name: "feetypedescr", index: "feetypedescr", width: 100, label: "费用类型", sortable: true, align: "right",hidden:true},
			{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "结算状态", sortable: true, align: "right",hidden:true},
			{name: "custcheckdate", index: "custcheckdate", width: 100, label: "结算日期", sortable: true, align: "right",formatter:formatDate,hidden:true},
			{name: "hadamount", index: "hadamount", width: 100, label: "已报销金额", sortable: true, align: "right",formatter:DiyFmatter,hidden:true},		
			{name: "refcustcode", index: "refcustcode", width: 100, label: "关联客户", sortable: true, align: "right",hidden:true},		
		],
	});
	 //改写窗口右上角关闭按钮事件
	 replaceCloseEvt("whInstallFeeImport", closeAndReturn);
}); 
function DiyFmatter (cellvalue, options, rowObject){ 
	return myRound(cellvalue,2);
}
function save(){
	var ids=$("#dataTable").jqGrid("getGridParam", "selarrrow");
	if(ids.length == 0){
		art.dialog({
			content:"请选择数据后保存"
		});				
		return ;
	}
	$.each(ids,function(i,id){
		var rowData = $("#dataTable").jqGrid("getRowData", id);
		rowData.feetype = rowData.feetype.toUpperCase();
		rowData.sendnohaveamount = 0;
		selectRows.push(rowData);
		if(nos != ""){
			nos += ",";
		}
		nos += rowData.appsendno;	
	});
	$("#nos").val(nos);
	art.dialog({content: "添加成功！",width: 200,time:800});
	search();
}
function closeAndReturn(){
	Global.Dialog.returnData = selectRows;
   	closeWin();
}
function search() {
	var dateFrom=$("#dateFrom").val();
	var dateTo=$("#dateTo").val();
	if(dateFrom==""){
		art.dialog({
			content: "开始时间不能为空！"
		});
		return;
	}
	if(dateTo==""){
		art.dialog({
			content: "结束时间不能为空！"
		});
		return;
	}
	$(".s-ico").css("display","none");
	$("#dataTable").jqGrid("setGridParam",{
		postData:$("#page_form").jsonForm(),
		url:"${ctx}/admin/laborFee/goWhInstallFeeJqGrid",
		page:1,
		sortname:''
	}).trigger("reloadGrid");
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
					<input hidden="true" id="nos"name="nos" value="${laborFee.nos}">
					<input hidden="true" id="no"name="no" value="${laborFee.no}">
					<ul class="ul-form">
						<li>
							<label>发货日期从</label>
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
		        <li class="active"  id="tab_whInstallFee">
		        	<a href="#tab_whInstallFee" data-toggle="tab">仓库装卸费</a>
		        </li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_whInstallFee"  class="tab-pane fade in active"> 
		        	<div id="content-list">
		        		<table id="dataTable"></table>
		        	</div>
		        </div> 
		    </div>  
		</div>
	</div>
</body>
</html>
