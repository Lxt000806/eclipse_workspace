<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>采购销售申请</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
function doExcel() {
	var url ="${ctx}/admin/purchaseApp/doExcel";
	doExcelAll(url);
}
$(function(){
	$("#appCZY").openComponent_employee();	
	
	//初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchaseApp/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: "Bootstrap",
		postData:$("#page_form").jsonForm(),
		colModel : [
		  {name : "no",index : "no",width : 80,label:"申请编号",sortable : true,align : "left"} ,
		  {name : "itemtype1",index : "itemtype1",width : 80,label:"材料类型1",sortable : true,align : "left",hidden: true} ,
		  {name : "itemtype1descr",index : "itemtype1descr",width : 80,label:"材料类型1",sortable : true,align : "left"} ,
		  {name : "status",index : "status",width : 50,label:"状态",sortable : true,align : "left",hidden: true},
		  {name : "statusdescr",index : "statusdescr",width : 50,label:"状态",sortable : true,align : "left"},
		  {name : "whcode",index : "whcode",width : 120,label:"仓库编号",sortable : true,align : "left",hidden: true},
		  {name : "whdescr",index : "whdescr",width : 180,label:"仓库名称",sortable : true,align : "left"},
	      {name : "appczy",index : "appczy",width : 70,label:"申请人编号",sortable : true,align : "left",hidden: true},
	      {name : "appczydescr",index : "appczydescr",width : 70,label:"申请人",sortable : true,align : "left"},
	      {name : "appdate", index: "appdate", width: 70, label: "申请时间", sortable: true, align: "left", formatter:formatDate},
	      {name : "confirmczy",index : "confirmczy",width : 70,label:"审核人",sortable : true,align : "left",hidden: true},
	      {name : "confirmczydescr",index : "confirmczydescr",width : 70,label:"审核人",sortable : true,align : "left"},
	      {name : "confirmdate", index: "confirmdate", width:70 , label: "审核时间", sortable: true, align: "left", formatter:formatDate},
	      {name : "remark", index: "remark", width: 270, label: "备注", sortable: true, align: "left"},
	      {name : "lastupdate",index : "lastupdate",width : 100,label:"最后修改时间",sortable : true,align : "left", formatter:formatDate},
	      {name : "lastupdatedby",index : "lastupdatedby",width : 70,label:"最后修改人",sortable : true,align : "left",},
	      {name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left",},
		  {name : "actionlog",index : "actionlog",width : 70,label:"操作标志",sortable : true,align : "left"},
	    ]
	});
});

function save(){
	Global.Dialog.showDialog("save",{
		title:"采购申请——新增",
		url:"${ctx}/admin/purchaseApp/goSave",
		postData: {},
		height:680,
		width:1250,
		returnFun:goto_query
	});
}

function update(){
	var ret = selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条数据进行编辑",
		});
		return;
	}
	
	if(ret.status != "0" ){
		art.dialog({
			content:"申请状态的申请单才可编辑"
		});
		return;
	}
	
	Global.Dialog.showDialog("update",{
		title:"采购申请——编辑",
		url:"${ctx}/admin/purchaseApp/goUpdate",
		postData: {no: ret.no},
		height:680,
		width:1250,
		returnFun:goto_query
	});
}

function confirm(){
	var ret = selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条数据进行审核",
		});
		return;
	}
	
	if("0" != ret.status){
		art.dialog({
			content:"申请状态才能进行审核操作"
		});
		return;
	}
	
	Global.Dialog.showDialog("confirm",{
		title:"采购申请——审核",
		url:"${ctx}/admin/purchaseApp/goConfirm",
		postData: {no: ret.no},
		height:680,
		width:1250,
		returnFun:goto_query
	});
}

function reConfirm(){
	var ret = selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条数据进行审核退回"
		});
		return;
	}
	if("1" != ret.status){
		art.dialog({
			content:"审核状态才能进行反审核操作"
		});
		return;
	}
	
	Global.Dialog.showDialog("reConfirm",{
		title:"采购申请——反审核",
		url:"${ctx}/admin/purchaseApp/goReConfirm",
		postData: {no: ret.no},
		height:680,
		width:1250,
		returnFun:goto_query
	});
}

function view(){
	var ret = selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条数据进行查看"
		});
		return;
	}
	
	Global.Dialog.showDialog("view",{
		title:"采购申请——查看",
		url:"${ctx}/admin/purchaseApp/goView",
		postData: {no: ret.no},
		height:680,
		width:1250,
		returnFun:goto_query
	});
}

function purchCon(){
	Global.Dialog.showDialog("view",{
		title:"采购申请跟踪",
		url:"${ctx}/admin/purchaseApp/goPurchaseAppCon",
		postData: {},
		height:680,
		width:1250,
		returnFun:goto_query
	});
}

function print(){
	var ret = selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择需要打印的申请单",
		});
		return;
	}
	var reportName = "purchaseApp.jasper";
 	Global.Print.showPrint(reportName, {
		No:ret.no,
		SUBREPORT_DIR: "<%=jasperPath%>" ,
		LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
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
					   <label>材料类型1</label>
					   <select id="itemType1" name="itemType1"  value="${item.itemType1}"></select>
					</li>
					<li>
						<label>状态</label>
						<house:xtdm id="status" dictCode="PURAPPSTAT"></house:xtdm>                     
					</li>
					<li>
						<label>采购申请单号</label>
						<input type="text" id="no" name="no" style="width:160px;"/> 
					</li>
					<li>
						<label>申请人</label>
						<input type="text" id="appCZY" name="appCZY" style="width:160px;"/> 
					</li>
					<li>
						<label>申请时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${purchaseApp.dateFrom}' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${purchaseApp.dateTo}' pattern='yyyy-MM-dd'/>" />
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
            	<house:authorize authCode="PURCHASEAPP_SAVE">
					<button id="btnView" type="button" class="btn btn-system " onclick="save()">新增</button>	
				</house:authorize>	
				<house:authorize authCode="PURCHASEAPP_UPDATE">
					<button id="btnView" type="button" class="btn btn-system " onclick="update()">编辑</button>	
				</house:authorize>	
				<house:authorize authCode="PURCHASEAPP_CONFIRM">
					<button id="btnView" type="button" class="btn btn-system " onclick="confirm()">审核</button>	
				</house:authorize>	
				<house:authorize authCode="PURCHASEAPP_RECONFIRM">
					<button id="btnView" type="button" class="btn btn-system " onclick="reConfirm()">反审核</button>	
				</house:authorize>	
				<house:authorize authCode="PURCHASEAPP_VIEW">
					<button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>	
				</house:authorize>	
				<house:authorize authCode="PURCHASEAPP_PURCHASEAPPCON">
					<button id="btnView" type="button" class="btn btn-system " onclick="purchCon()">采购申请跟踪</button>	
				</house:authorize>	
				<house:authorize authCode="PURCHASEAPP_PRINT">
					<button id="btnView" type="button" class="btn btn-system " onclick="print()">打印</button>	
				</house:authorize>	
				<house:authorize authCode="PURCHASEAPP_EXCEL">
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


