<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>施工任务监控</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var tableId="dataTable_addResrCust";
var excelName="新增资源客户";
var searchType="1";
$(function(){
	var gridOption_addResrCust ={
		url:"${ctx}/admin/businessPlanResultAnaly/goDetailJqGrid?searchType=1",
		postData:$("#page_form").jsonForm(),
		height:400,
		styleUI:"Bootstrap",
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
			{name: "recentlycondatedescr",	index:"recentlycondatedescr",	width:120,	label:"最近联系时间",	sortable:true,align:"left"},
			{name: "crtdate", index: "crtdate", width: 125, label: "创建时间", sortable: true, align: "left", formatter: formatTime},
			{name: "remarks", index: "remarks", width: 350, label: "跟踪内容", sortable: true, align: "left"},
			{name: "custresstatdescr", index: "custresstatdescr", width: 100, label: "资源客户状态", sortable: true, align: "left"},
		],
	};
	Global.JqGrid.initJqGrid("dataTable_addResrCust",gridOption_addResrCust);
	
	var gridOption_dispResrCust ={
		url:"${ctx}/admin/businessPlanResultAnaly/goDetailJqGrid?searchType=8",
		postData:$("#page_form").jsonForm(),
		height:400,
		styleUI:"Bootstrap",
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
			{name: "recentlycondatedescr",	index:"recentlycondatedescr",	width:120,	label:"最近联系时间",	sortable:true,align:"left"},
			{name: "crtdate", index: "crtdate", width: 125, label: "创建时间", sortable: true, align: "left", formatter: formatTime},
			{name: "crtczydescr", index: "crtczydescr", width: 80, label: "创建人", sortable: true, align: "left"},
			{name: "dispatchdate", index: "dispatchdate", width: 125, label: "派单时间", sortable: true, align: "left", formatter: formatTime},
			{name: "remarks", index: "remarks", width: 350, label: "跟踪内容", sortable: true, align: "left"},
			{name: "custresstatdescr", index: "custresstatdescr", width: 100, label: "资源客户状态", sortable: true, align: "left"},
		],
	};
	Global.JqGrid.initJqGrid("dataTable_dispResrCust",gridOption_dispResrCust);
	
	var gridOption_addCust ={
		url:"${ctx}/admin/businessPlanResultAnaly/goDetailJqGrid?searchType=2",
		postData:$("#page_form").jsonForm(),
		height:400,
		rowNum : 10000000,
		styleUI:"Bootstrap",
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
			{name: "area",	index:"area",	width:60,	label:"面积",	sortable:true,align:"right"},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 100, label: "设计师", sortable: true, align: "left"},
			{name: "crtdate", index: "crtdate", width: 125, label: "创建时间", sortable: true, align: "left", formatter: formatTime},
			{name: "measuredate", index: "measuredate", width: 125, label: "量房时间", sortable: true, align: "left", formatter: formatTime},
			{name: "visitdate", index: "visitdate", width: 125, label: "到店时间", sortable: true, align: "left", formatter: formatTime},
			{name: "setdate", index: "setdate", width: 125, label: "下定时间", sortable: true, align: "left", formatter: formatTime},
			{name: "signdate", index: "signdate", width: 125, label: "签单时间", sortable: true, align: "left", formatter: formatTime},
		],
		loadonce:true
	};
	Global.JqGrid.initJqGrid("dataTable_addCust",gridOption_addCust);
	
	var gridOption_measure={
		url:"${ctx}/admin/businessPlanResultAnaly/goDetailJqGrid?searchType=3",
		postData:$("#page_form").jsonForm(),
		height:400,
		rowNum : 10000000,
		styleUI:"Bootstrap",
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
			{name: "area",	index:"area",	width:60,	label:"面积",	sortable:true,align:"right"},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 100, label: "设计师", sortable: true, align: "left"},
			{name: "crtdate", index: "crtdate", width: 125, label: "创建时间", sortable: true, align: "left", formatter: formatTime},
			{name: "measuredate", index: "measuredate", width: 125, label: "量房时间", sortable: true, align: "left", formatter: formatTime},
			{name: "visitdate", index: "visitdate", width: 125, label: "到店时间", sortable: true, align: "left", formatter: formatTime},
			{name: "setdate", index: "setdate", width: 125, label: "下定时间", sortable: true, align: "left", formatter: formatTime},
			{name: "signdate", index: "signdate", width: 125, label: "签单时间", sortable: true, align: "left", formatter: formatTime},
		],
		loadonce:true
	};
	Global.JqGrid.initJqGrid("dataTable_measure",gridOption_measure);
	
	var gridOption_visit={
		url:"${ctx}/admin/businessPlanResultAnaly/goDetailJqGrid?searchType=4",
		postData:$("#page_form").jsonForm(),
		height:400,
		rowNum : 10000000,
		styleUI:"Bootstrap",
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
			{name: "area",	index:"area",	width:60,	label:"面积",	sortable:true,align:"right"},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 100, label: "设计师", sortable: true, align: "left"},
			{name: "crtdate", index: "crtdate", width: 125, label: "创建时间", sortable: true, align: "left", formatter: formatTime},
			{name: "measuredate", index: "measuredate", width: 125, label: "量房时间", sortable: true, align: "left", formatter: formatTime},
			{name: "visitdate", index: "visitdate", width: 125, label: "到店时间", sortable: true, align: "left", formatter: formatTime},
			{name: "setdate", index: "setdate", width: 125, label: "下定时间", sortable: true, align: "left", formatter: formatTime},
			{name: "signdate", index: "signdate", width: 125, label: "签单时间", sortable: true, align: "left", formatter: formatTime},
		],
		loadonce:true
	};
	Global.JqGrid.initJqGrid("dataTable_visit",gridOption_visit);
	
	var gridOption_set={
		url:"${ctx}/admin/businessPlanResultAnaly/goDetailJqGrid?searchType=5",
		postData:$("#page_form").jsonForm(),
		height:400,
		rowNum : 10000000,
		styleUI:"Bootstrap",
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
			{name: "area",	index:"area",	width:60,	label:"面积",	sortable:true,align:"right"},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 100, label: "设计师", sortable: true, align: "left"},
			{name: "crtdate", index: "crtdate", width: 125, label: "创建时间", sortable: true, align: "left", formatter: formatTime},
			{name: "measuredate", index: "measuredate", width: 125, label: "量房时间", sortable: true, align: "left", formatter: formatTime},
			{name: "visitdate", index: "visitdate", width: 125, label: "到店时间", sortable: true, align: "left", formatter: formatTime},
			{name: "setdate", index: "setdate", width: 125, label: "下定时间", sortable: true, align: "left", formatter: formatTime},
			{name: "signdate", index: "signdate", width: 125, label: "签单时间", sortable: true, align: "left", formatter: formatTime},
		],
		loadonce:true
	};
	Global.JqGrid.initJqGrid("dataTable_set",gridOption_set);
	
	var gridOption_sign={
		url:"${ctx}/admin/businessPlanResultAnaly/goDetailJqGrid?searchType=6",
		postData:$("#page_form").jsonForm(),
		height:400,
		rowNum : 10000000,
		styleUI:"Bootstrap",
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
			{name: "area",	index:"area",	width:60,	label:"面积",	sortable:true,align:"right"},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 100, label: "设计师", sortable: true, align: "left"},
			{name: "crtdate", index: "crtdate", width: 125, label: "创建时间", sortable: true, align: "left", formatter: formatTime},
			{name: "measuredate", index: "measuredate", width: 125, label: "量房时间", sortable: true, align: "left", formatter: formatTime},
			{name: "visitdate", index: "visitdate", width: 125, label: "到店时间", sortable: true, align: "left", formatter: formatTime},
			{name: "setdate", index: "setdate", width: 125, label: "下定时间", sortable: true, align: "left", formatter: formatTime},
			{name: "signdate", index: "signdate", width: 125, label: "签单时间", sortable: true, align: "left", formatter: formatTime},
		],
		loadonce:true
	};
	Global.JqGrid.initJqGrid("dataTable_sign",gridOption_sign);
	
	var gridOption_con={
		url:"${ctx}/admin/businessPlanResultAnaly/goDetailJqGrid?searchType=7",
		postData:$("#page_form").jsonForm(),
		height:400,
		styleUI:"Bootstrap",
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 80, label: "跟踪类型", sortable: true, align: "left"},
			{name: "condate", index: "condate", width: 125, label: "联系时间", sortable: true, align: "left", formatter: formatTime},
			{name: "remarks",	index:"remarks",	width:400,	label:"联系内容",	sortable:true,align:"left"},
			{name: "conduration", index: "conduration", width: 80, label: "通话时长", sortable: true, align: "right"},
		],
	};
	Global.JqGrid.initJqGrid("dataTable_con",gridOption_con);
});
function save(){	
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/prjMsg/doSave',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
				$("#_form_token_uniq_id").val(obj.token.token);
				art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
};

function setTable(id,name,type){
	tableId=id;
	excelName=name;
	searchType=type;
}

function doExcel(){
	doExcelAll("${ctx}/admin/businessPlanResultAnaly/doDetailExcel?searchType="+searchType+"&excelName="+excelName,tableId);
}
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      	  <button type="button" class="btn btn-system "   onclick="doExcel()">导出excel</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<form role="form" class="form-search" id="page_form" action=""
		method="post" target="targetFrame">
		<input type="hidden" name="jsonString" value="" /> 
		<input type="hidden" name="department2" value="${customer.department2}" />
		<input type="hidden" name="department1" value="${customer.department1}" />
		<input type="hidden" name="empCode" value="${customer.empCode}" /> 
		<input type="hidden" id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="dateTo" name="dateTo" value="<fmt:formatDate value='${customer.dateTo }'  pattern='yyyy-MM-dd'/>" />
	</form>
</div>
<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li class="active">
				<a href="#addResrCust" data-toggle="tab" onclick="setTable('dataTable_addResrCust','新增资源客户','1')">新增资源客户</a>
			</li>
			<li class="">
				<a href="#dispResrCust" data-toggle="tab" onclick="setTable('dataTable_dispResrCust','派单资源客户','8')">派单资源客户</a>
			</li>
			<li class="">
				<a href="#addCust" data-toggle="tab" onclick="setTable('dataTable_addCust','新增意向客户','2')">新增意向客户</a>
			</li>
			<li class="">
				<a href="#measure" data-toggle="tab" onclick="setTable('dataTable_measure','量房','3')">量房</a>
			</li>
			<li class="">
				<a href="#visit" data-toggle="tab" onclick="setTable('dataTable_visit','到店','4')">到店</a>
			</li>
			<li class="">
				<a href="#set" data-toggle="tab" onclick="setTable('dataTable_set','下定','5')">下定</a>
			</li>
			<li class="">
				<a href="#sign" data-toggle="tab" onclick="setTable('dataTable_sign','签单','6')">签单</a>
			</li>
			<li class="">
				<a href="#con" data-toggle="tab" onclick="setTable('dataTable_con','联系客户','7')">联系客户</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="addResrCust" class="tab-pane active">
				<div class="pageContent">
					<div class="body-box-list" style="margin-top: 5px;">
						<table id="dataTable_addResrCust"></table>
						<div id="dataTable_addResrCustPager"></div>
					</div>
				</div>
			</div>
			<div id="dispResrCust" class="tab-pane fade "> 
	         	<div class="pageContent">
					<div class="body-box-list" style="margin-top: 5px;">
						<table id="dataTable_dispResrCust"></table>
						<div id="dataTable_dispResrCustPager"></div>
					</div>
				</div> 
	        </div> 
			<div id="addCust" class="tab-pane fade "> 
	         	<div class="pageContent">
					<div class="body-box-list" style="margin-top: 5px;">
						<table id="dataTable_addCust"></table>
					</div>
				</div> 
	        </div> 
	        <div id="measure" class="tab-pane fade "> 
	         	<div class="pageContent">
					<div class="body-box-list" style="margin-top: 5px;">
						<table id="dataTable_measure"></table>
					</div>
				</div> 
	        </div>
	        <div id="visit" class="tab-pane fade "> 
	         	<div class="pageContent">
					<div class="body-box-list" style="margin-top: 5px;">
						<table id="dataTable_visit"></table>
					</div>
				</div> 
	        </div> 
	        <div id="set" class="tab-pane fade "> 
	         	<div class="pageContent">
					<div class="body-box-list" style="margin-top: 5px;">
						<table id="dataTable_set"></table>
					</div>
				</div> 
	        </div>
	        <div id="sign" class="tab-pane fade "> 
	         	<div class="pageContent">
					<div class="body-box-list" style="margin-top: 5px;">
						<table id="dataTable_sign"></table>
					</div>
				</div> 
	        </div>
	         <div id="con" class="tab-pane fade "> 
	         	<div class="pageContent">
					<div class="body-box-list" style="margin-top: 5px;">
						<table id="dataTable_con"></table>
						<div id="dataTable_conPager"></div>
					</div>
				</div> 
	        </div>     
		</div>
	</div> 
</body>
</html>


