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
	<title>材料报价模板</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/itemPlanTemp/doExcel";
		doExcelAll(url);
	}
	
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	
		$("#designMan").openComponent_employee();	
		$("#builderCode").openComponent_builder();
		var postData = $("#page_form").jsonForm();
		var gridOption ={
			url:"${ctx}/admin/itemPlanTemp/goListJqGrid",
			height:$(document).height()-$("#content-list").offset().top-72,
			styleUI:"Bootstrap", 
			colModel : [
				{name: "no", index: "no", width: 80, label: "编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 120, label: "名称", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 90, label: "材料类型", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 300, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 90, label: "操作", sortable: true, align: "left",hidden:true}
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
		$("#save").on("click",function(){
			Global.Dialog.showDialog("Save",{
				title:"材料报价模板——新增",
				url:"${ctx}/admin/itemPlanTemp/goSave",
				height:650,
				width:850,
				returnFun:goto_query
			});
		});
		
		$("#update").on("click",function(){
			var ret = selectDataTableRow();

			Global.Dialog.showDialog("Update",{
				title:"材料报价模板——编辑",
				url:"${ctx}/admin/itemPlanTemp/goUpdate",
				postData:{id:ret.no},
				height:650,
				width:850,
				returnFun:goto_query
			});
		});
		
		$("#view").on("click",function(){
			var ret = selectDataTableRow();

			Global.Dialog.showDialog("View",{
				title:"材料报价模板——查看",
				url:"${ctx}/admin/itemPlanTemp/goView",
				postData:{id:ret.no},
				height:600,
				width:850,
				returnFun:goto_query
			});
		});
		
		
	});
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired"  name="expired" value="${baseItem.expired }"/>
					<ul class="ul-form">
						<div class="validate-group row" >
							<li>
								<label>名称</label>
								<input type="text" id="descr" name="descr"/>
							</li>
							<li class="form-validate">
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1"></select>
							</li>
							<li class="search-group" >
								<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${baseItem.expired }" onclick="hideExpired(this)" 
								 ${baseItem!='T'?'checked':'' } /><p>隐藏过期</p>
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
								<button id="clear" type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
							</li>
						</div>	
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
  			<div class="btn-group-sm">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				<house:authorize authCode="CLBJMB_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
					<span>导出excel</span>
				</button>
			</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
