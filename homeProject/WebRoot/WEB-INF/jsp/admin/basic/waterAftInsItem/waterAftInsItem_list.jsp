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
	<title>水电后期安装材料配置列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/waterAftInsItem/doExcel";
	doExcelAll(url);
}
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/waterAftInsItem/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'no',	index:'no',	width:75, label:'编号', sortable:true,align:"left"},
			{name:'itemtype2descr',	index:'itemtype2descr',	width:90,	label:'材料类型2',	sortable:true,align:"left" },
			{name:'remarks', index:'remarks', width:120, label:'说明', sortable:true,align:"left"},
			{name:'lastupdate',	index:'lastupdate',	width:120,	label:'最后修改时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'lastupdatedby',	index:'lastupdatedby',	width:90,	label:'最后修改人',	sortable:true,align:"left" ,},
			{name:'actionlog',	index:'actionlog',	width:90,	label:'操作类型',	sortable:true,align:"left" ,},
			{name:'expired',	index:'expired',	width:90,	label:'是否过期',	sortable:true,align:"left" ,},
		],
	});
	
	$("#save").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"水电后期安装材料配置——新增",
			url:"${ctx}/admin/waterAftInsItem/goSave",
			height: 700,
			width:1000,
			returnFun:goto_query
		});
	});
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("update",{
			title:"水电后期安装材料配置——编辑",
			url:"${ctx}/admin/waterAftInsItem/goUpdate",
			postData:{no:ret.no},
			height: 700,
			width:1000,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("view",{
			title:"水电后期安装材料配置——查看",
			url:"${ctx}/admin/waterAftInsItem/goView",
			postData:{no:ret.no},
			height: 700,
			width:1000,
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
								<input type="text" id="no" name="no" style="width:160px;"/>
							</li>
							<li>
								<label>材料类型1</label> 
								<select id="itemType1" name="itemType1"></select>
							</li>
							<li>
								<label>材料类型2</label> 
								<select id="itemType2" name="itemType2"></select>
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
					<house:authorize authCode="WATERAFTINSITEM_ADD">
						<button type="button" class="btn btn-system " id="save"><span>新增</span> 
					</house:authorize>
					<house:authorize authCode="WATERAFTINSITEM_UPDATE">
						<button type="button" class="btn btn-system "  id="update"><span>编辑</span> 
					</house:authorize>
					<house:authorize authCode="WATERAFTINSITEM_VIEW">
						<button type="button" class="btn btn-system "  id="view"><span>查看</span> 
					</house:authorize>
					<house:authorize authCode="WATERAFTINSITEM_EXCEL">
						<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
								<span>导出excel</span>
						</button>
					</house:authorize>	
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
	</body>	
</html>
