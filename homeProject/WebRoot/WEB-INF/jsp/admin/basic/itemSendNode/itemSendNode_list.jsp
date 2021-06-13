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
	<title>材料配送节点列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/itemSendNode/doExcel";
	doExcelAll(url);
}
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemSendNode/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'code',	index:'code',	width:75,	label:'编号',	sortable:true,align:"left" ,},
			{name:'descr',	index:'descr',	width:90,	label:'名称',	sortable:true,align:"left" ,},
			{name:'typedescr',	index:'typedescr',	width:90,	label:'类型',	sortable:true,align:"left"},
			{name:'workerclassifydescr',	index:'workerclassifydescr',	width:90,	label:'适用施工类型',	sortable:true,align:"left" },
			{name:'itemtype1descr',	index:'itemtype1descr',	width:90,	label:'材料类型1',	sortable:true,align:"left" },
			{name:'type',	index:'type',	width:90,	label:'类型',	sortable:true,align:"left" , hidden:true},
			{name:'workerclassify',	index:'workerclassify',	width:90,	label:'适用施工类型',	sortable:true,align:"left" ,hidden:true},
			{name:'itemtype1',	index:'itemtype1',	width:90,	label:'材料类型1',	sortable:true,align:"left" ,hidden:true},
			{name:'lastupdate',	index:'lastupdate',	width:120,	label:'最后修改时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'lastupdatedby',	index:'lastupdatedby',	width:90,	label:'最后修改人',	sortable:true,align:"left" ,},
			{name:'actionlog',	index:'actionlog',	width:90,	label:'操作类型',	sortable:true,align:"left" ,},
			{name:'expired',	index:'expired',	width:90,	label:'是否过期',	sortable:true,align:"left" ,},
			
		],
	});
	
	$("#save").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"材料配送节点——新增",
			url:"${ctx}/admin/itemSendNode/goSave",
			height: 650,
			width:1100,
			returnFun:goto_query
		});
	});
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("update",{
			title:"材料配送节点——编辑",
			url:"${ctx}/admin/itemSendNode/goUpdate",
			postData:{code:ret.code},
			height: 650,
			width:1100,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("view",{
			title:"材料配送节点——查看",
			url:"${ctx}/admin/itemSendNode/goView",
			postData:{code:ret.code},
			height: 650,
			width:1100,
			returnFun:goto_query
		});
	});
	
	
});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>编号</label>
								<input type="text" id="code" name="code" style="width:160px;"/>
							</li>
							<li>
								<label>名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"/>
							</li>
							<li>
								<label>类型</label>
								<house:xtdm id="type" dictCode="SENDNODETYPE" ></house:xtdm>
							</li>
							<li>
								<label>适用施工分类</label>
								<house:xtdm id="workerClassify" dictCode="WORKERCLASSIFY" ></house:xtdm>
							</li>
							<li>
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width: 160px;"></select>
							</li>
							<li class="search-group-shrink" >
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
						<house:authorize authCode="ITEMSENDNODE_SAVE">
							<button type="button" class="btn btn-system " id="save"><span>新增</span> 
						</house:authorize>
						<house:authorize authCode="ITEMSENDNODE_UPDATE">
							<button type="button" class="btn btn-system "  id="update"><span>编辑</span> 
						</house:authorize>
						<house:authorize authCode="ITEMSENDNODE_VIEW">
							<button type="button" class="btn btn-system "  id="view"><span>查看</span> 
						</house:authorize>
						<house:authorize authCode="ITEMSENDNODE_EXCEL">
							<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
									<span>导出excel</span>
							</button>
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
