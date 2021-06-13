<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>主材选定--查询</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">
$(function(){
	//初始化表格
    var  height=$(document).height()-$("#content-list").offset().top-70;
	Global.JqGrid.initJqGrid("confirmResultDataTable",{
		url:"${ctx}/admin/custItemConfirm/goConfirmResultJqGrid?custCode=${custItemConfDate.custCode}",
		height:height,
		styleUI: 'Bootstrap',
		footerrow:false,
		colModel : [
			{name : 'confitemtypedescr',index : 'confitemtypedescr',width : 100,label:'施工材料分类',align:"left",sortable : false},
		    {name : 'itemconfstatusdescr',index : 'itemconfstatusdescr',width : 100,label:'确认状态',align : "left",sortable : false},
		    {name : 'itemtimecode',index : 'itemtimecode',width : 100,label:'确认节点',align : "left",sortable : false,hidden:true},
		    {name : 'itemtimedescr',index : 'itemtimedescr',width : 100,label:'确认节点',align : "left",sortable : false},
		    {name : 'confirmdate',index : 'confirmdate',width : 75,label:'节点完成时间',align : "left",sortable : false, formatter: formatTime}
        ]
	});
	Global.JqGrid.initJqGrid("confirmTimeDataTable",{
		url:"${ctx}/admin/itemConfirmInform/goJqGrid?custCode=${custItemConfDate.custCode}",
		height:height,
		width:940,  
        autowidth: false,
        shrinkToFit: true, 
        styleUI: 'Bootstrap',   
		footerrow:false,
		colModel : [
	    	{name : 'itemtimecode',index : 'itemtimecode',width : 100,label:'确认节点',align : "left",sortable : false,hidden:true},
	     	{name : 'itemtimedescr',index : 'itemtimedescr',width : 100,label:'确认节点',align : "left",sortable : false},
	     	{name : 'informdate',index : 'informdate',width : 75,label:'预约时间',align : "left",sortable : false, formatter: formatTime},
	    	{name : 'plancomedate',index : 'plancomedate',width : 75,label:'计划到司时间',align : "left",sortable : false, formatter: formatTime},
	    	{name : 'informremark',index : 'informremark',width : 100,label:'预约说明',align : "left",sortable : false}
        ]
	});
		
	Global.JqGrid.initJqGrid("custItemConfProgDataTable",{
		url:"${ctx}/admin/custItemConfProg/goJqGrid?custCode=${custItemConfDate.custCode}",
		rowNum: 10000,
		height:235,
		width:940,  
        autowidth: false,
        shrinkToFit: true,
        styleUI: 'Bootstrap',    
		colModel :[
			{name : 'no',index : 'no',width : 100,label:'过程编号',align:"left",sortable : false},
			{name : 'custcode',index : 'custcode',width : 100,label:'客户编号',align:"left",sortable : false},
		    {name : 'itemconfstatusdescr',index : 'itemconfstatusdescr',width : 100,label:'确认状态',align : "left",sortable : false},
		    {name : 'confirmdate',index : 'confirmdate',width : 75,label:'确认时间',align : "left",sortable : false, formatter: formatTime},
		    {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',align:"left",sortable : false}
        ],
        onSelectRow : function(id) {
        	var row = selectDataTableRow("custItemConfProgDataTable");
        	if(row) $("#custItemConfProgDtDataTable").jqGrid('setGridParam',{url : "${ctx}/admin/custItemConfProgDt/goJqGrid?confProgNo="+row.no}).trigger('reloadGrid');
        },
        gridComplete:function(){
         	setTimeout(function(){
         	var row = selectDataTableRow("custItemConfProgDataTable");
         	if(row) $("#custItemConfProgDtDataTable").jqGrid('setGridParam',{url : "${ctx}/admin/custItemConfProgDt/goJqGrid?confProgNo="+row.no}).trigger('reloadGrid');
         	},200);

         }
	});
	Global.JqGrid.initJqGrid("custItemConfProgDtDataTable",{
		height:230,
		width:940,  
	    autowidth: false,
	    shrinkToFit: true, 
	    styleUI: 'Bootstrap',   
		rowNum: 10000,
		caption: "材料选定过程明细",
		colModel : [
		     {name : 'confitemtypedescr',index : 'confitemtypedescr',width : 100,label:'材料分类',align:"left",sortable : false},
		     {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',align:"left",sortable : false}
        ]
	});
	Global.JqGrid.initJqGrid("prjProgDataTable",{
		url:"${ctx}/admin/prjProg/goJqGrid?custCode=${custItemConfDate.custCode}",
		height:398,
		width:940,  
	    autowidth: false,
	    shrinkToFit: true,
		styleUI: 'Bootstrap',
		colModel : [
			{name: "PK", index: "PK", width: 170, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "PrjItem", index: "PrjItem", width: 170, label: "施工项目", sortable: true, align: "left",hidden:true},
			{name: "prjdescr", index: "prjdescr", width: 120, label: "施工项目", sortable: true, align: "left"},
			{name: "PlanBegin", index: "PlanBegin", width: 85, label: "计划开始日期", sortable: true, align: "left",formatter: formatDate},
			{name: "BeginDate", index: "BeginDate", width: 85, label: "开始日期", sortable: true, align: "left", formatter: formatDate},
			{name: "PlanEnd", index: "planEnd", width: 85, label: "计划结束日期", sortable: true, align: "left",formatter: formatDate},
			{name: "EndDate", index: "EndDate", width: 80, label: "结束日期", sortable: true, align: "left", formatter: formatDate},
			{name: "ConfirmCZY", index: "ConfirmCZY", width: 60, label: "验收人", sortable: true, align: "left",hidden:true},
			{name: "confirmdescr", index: "confirmdescr", width: 60, label: "验收人", sortable: true, align: "left"},
			{name: "ConfirmDate", index: "ConfirmDate", width: 80, label: "验收日期", sortable: true, align: "left", formatter: formatDate},
			{name: "prjleveldescr", index: "prjleveldescr", width: 60, label: "验收评级", sortable: true, align: "left"},
			{name: "ConfirmDesc", index: "ConfirmDesc", width: 120, label: "验收说明", sortable: true, align: "left"}						
		]
	});
});
function doExcel(){
	if ($(".nav-tabs li a[href='#tab_confirmResult']").parent().hasClass("active")){
		doExcelNow('主材选定跟踪_确认结果-${customer.address }','confirmResultDataTable','page_form_excel');
	}else if($(".nav-tabs li a[href='#tab_confirmTime']").parent().hasClass("active")){
		doExcelNow('主材选定跟踪_预约明细-${customer.address }','confirmTimeDataTable','page_form_excel');
	}else if($(".nav-tabs li a[href='#tab_confirmStatus']").parent().hasClass("active")){
		doExcelNow('主材选定跟踪_确认明细-${customer.address }','custItemConfProgDataTable','page_form_excel');
	}else if($(".nav-tabs li a[href='#tab_prjProg']").parent().hasClass("active")){
		doExcelNow('主材选定跟踪_工地进度-${customer.address }','prjProgDataTable','page_form_excel');
	}
}
</script>
</head>
<body onunload="closeWin()">
	<div class="body-box-form">
		<div class="panel panel-system">
			<form action="" method="post" id="page_form_excel">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="panel-body">
				<div class="btn-group-xs">
					<house:authorize authCode="CUSTITEMCONFIRM_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()">输出至excel</button>
					</house:authorize>
					<button type="button" class="btn btn-system "
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>

		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_confirmResult"
					data-toggle="tab">确认结果</a>
				</li>
				<li class=""><a href="#tab_confirmTime" data-toggle="tab">预约明细</a>
				</li>
				<li class=""><a href="#tab_confirmStatus" data-toggle="tab">确认明细</a>
				</li>
				<li class=""><a href="#tab_prjProg" data-toggle="tab">工地进度</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tab_confirmResult" class="tab-pane fade in active">
					<div id="content-list">
						<table id="confirmResultDataTable"></table>
						<div id="confirmResultDataTablePager"></div>
					</div>
				</div>
				<div id="tab_confirmTime" class="tab-pane fade ">
					<div id="content-list">
						<table id="confirmTimeDataTable"></table>
						<div id="confirmTimeDataTablePager"></div>
					</div>
				</div>
				<div id="tab_confirmStatus" class="tab-pane fade ">
					<div id="content-list">
						<table id="custItemConfProgDataTable"></table>
					</div>
					<div id="content-list" style="margin-top: 5px;">
						<table id="custItemConfProgDtDataTable"></table>
					</div>
				</div>
				<div id="tab_prjProg" class="tab-pane fade ">
					<div id="content-list">
						<table id="prjProgDataTable"></table>
						<div id="prjProgDataTablePager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
