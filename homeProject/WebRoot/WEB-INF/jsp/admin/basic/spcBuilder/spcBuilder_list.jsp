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
	<title>专盘信息管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){

	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/spcBuilder/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'Code',	index:'Code',	width:90,	label:'专盘编号',	sortable:true,align:"left" ,},
			{name:'Descr',	index:'Descr',	width:90,	label:'专盘名称',	sortable:true,align:"left" ,},
			{name:'LeaderCode',	index:'LeaderCode',	width:90,	label:'队长编码',	sortable:true,align:"left" ,},
			{name:'leaderdescr',	index:'leaderdescr',	width:90,	label:'队长',	sortable:true,align:"left" ,},
			{name:'typedescr',	index:'typedescr',	width:90,	label:'楼盘类型',	sortable:true,align:"left" ,},
			{name:'buildertypedescr',	index:'buildertypedescr',	width:70,	label:'项目类型',	sortable:true,align:"left" ,},
			{name:'DelivDate',	index:'DelivDate',	width:90,	label:'交房时间',	sortable:true,align:"left" ,formatter: formatDate},
			{name:'TotalQty',	index:'TotalQty',	width:70,	label:'总户数',	sortable:true,align:"right" ,},
			{name:'DelivQty',	index:'DelivQty',	width:70,	label:'交房数',	sortable:true,align:"right" ,},
			{name:'TotalBeginQty',	index:'TotalBeginQty',	width:90,	label:'总开工套数',	sortable:true,align:"right" ,},
			{name:'SelfDecorQty',	index:'SelfDecorQty',	width:90,	label:'业主自装户数',	sortable:true,align:"right" ,},
			{name:'DecorCmpBeginQty',	index:'DecorCmpBeginQty',	width:90,	label:'装修公司开工户数',	sortable:true,align:"right" ,},
			{name:'DecorCmp1',	index:'DecorCmp1',	width:80,	label:'装修公司1',	sortable:true,align:"left" ,},
			{name:'DecorCmp1Qty',	index:'DecorCmp1Qty',	width:70,	label:'套数1',	sortable:true,align:"right" ,},
			{name:'DecorCmp2',	index:'DecorCmp2',	width:80,	label:'装修公司2',	sortable:true,align:"left" ,},
			{name:'DecorCmp2Qty',	index:'DecorCmp2Qty',	width:70,	label:'套数2',	sortable:true,align:"right" ,},
			{name:'DecorCmp3',	index:'DecorCmp3',	width:80,	label:'装修公司3',	sortable:true,align:"left" ,},
			{name:'DecorCmp3Qty',	index:'DecorCmp3Qty',	width:70,	label:'套数3',	sortable:true,align:"right" ,},
			{name:'DecorCmp4',	index:'DecorCmp4',	width:80,	label:'装修公司4',	sortable:true,align:"left" ,},
			{name:'DecorCmp4Qty',	index:'DecorCmp4Qty',	width:70,	label:'套数4',	sortable:true,align:"right" ,},
			{name:'DecorCmp5',	index:'DecorCmp5',	width:80,	label:'装修公司5',	sortable:true,align:"left" ,},
			{name:'DecorCmp5Qty',	index:'DecorCmp5Qty',	width:70,	label:'套数5',	sortable:true,align:"right" ,},
			{name:'Remarks',	index:'Remarks',	width:130,	label:'备注',	sortable:true,align:"left" ,},
			{name:'LastUpdatedBy',	index:'LastUpdatedBy',	width:100,	label:'最后修改人员',	sortable:true,align:"left" ,},
			{name:'LastUpdate',	index:'LastUpdate',	width:105,	label:'最后修改时间',	sortable:true,align:"left" ,formatter: formatTime},
		
		],
	});
	
	$("#save").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"专盘管理——新增",
			url:"${ctx}/admin/spcBuilder/goSave",
			height:700,
			width:1250,
			returnFun:goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("update",{
			title:"专盘管理——编辑",
			url:"${ctx}/admin/spcBuilder/goUpdate",
			postData:{code:ret.Code},
			height:700,
			width:1250,
			returnFun:goto_query
		});
	});
	
	$("#leaderUpdate").on("click",function(){
		var ret=selectDataTableRow();
		if($.trim("${czybh }")!=$.trim(ret.LeaderCode)){
			art.dialog({
				content:"只有战队队长能编辑自己的专盘"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"专盘管理——编辑",
			url:"${ctx}/admin/spcBuilder/goLeaderUpdate",
			postData:{code:ret.Code},
			height:700,
			width:1250,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("view",{
			title:"专盘管理——查看",
			url:"${ctx}/admin/spcBuilder/goView",
			postData:{code:ret.Code},
			height:700,
			width:1250,
			returnFun:goto_query
		});
	});		
	
});
function doExcel(){
	var url = "${ctx}/admin/spcBuilder/doExcel";
	doExcelAll(url);
}

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>专盘名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${spcBuilder.descr }"/>
							</li>
							<li >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
							</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<house:authorize authCode="SPCBUILDER_ADD">
							<button type="button" class="btn btn-system " id="save"><span>新增</span> 
						</house:authorize>
						<house:authorize authCode="SPCBUILDER_UPDATE">
							<button type="button" class="btn btn-system "  id="update"><span>编辑</span> 
						</house:authorize>
						<house:authorize authCode="SPCBUILDER_VIEW">
							<button type="button" class="btn btn-system "  id="view"><span>查看</span> 
						</house:authorize>
						<house:authorize authCode="SPCBUILDER_LEADERUPDATE">
							<button type="button" class="btn btn-system "  id="leaderUpdate"><span>队长编辑</span> 
						</house:authorize>
						<house:authorize authCode="SPCBUILDER_EXCEL">
							<button type="button" class="btn btn-system "  onclick="doExcel()" >
								<span>导出excel</span>
						</house:authorize>
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
	</body>	
</html>
