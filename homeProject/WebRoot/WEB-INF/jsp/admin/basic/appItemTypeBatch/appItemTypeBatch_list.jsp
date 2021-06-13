<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>下单材料类型批次</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
		    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
		    	<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="expired" name="expired" value=""/>
				<ul class="ul-form">
					<li>
						<label>批次号</label>
						<input type="text" id="code" name="code" style="width:160px;"/>
					</li>
					<li>
						<label>名称</label>
						<input type="text" id="descr" name="descr" style="width:160px;"/>
					</li>
					<%-- <house:dict id="descr" dictCode="" sql="select distinct Descr from tConfItemType where Expired = 'F'" 
							sqlValueKey="Descr" sqlLableKey="Descr" value=""/> --%>
					<li>
						<label>是否套餐材料</label>
						<house:xtdm id="isSetItem" dictCode="YESNO" style="width:160px;" value=""/>
					</li>
					<li>
						<label>提示一起下单</label>
						<house:xtdm id="infoAppAll" dictCode="YESNO" style="width:160px;" value=""/>
					</li>
					<li class="search-group">
						<input type="checkbox" id="expired_show" name="expired_show" value="${appItemTypeBatch.expired}" 
							onclick="hideExpired(this)" ${appItemTypeBatch.expired!='T' ?'checked':''}/>
						<p>隐藏过期</p>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
 			<div class="btn-group-sm">
			<button type="button" class="btn btn-system" id="save">
				<span>新增</span>
			</button>
			<button type="button" class="btn btn-system" id="update">
				<span>编辑</span>
			</button>
			<house:authorize authCode="APPITEMTYPEBATCH_VIEW">
				<button type="button" class="btn btn-system" id="view" onclick="view()">
					<span>查看</span>
				</button>
		  	</house:authorize>
		  	<button type="button" class="btn btn-system" id="delete">
				<span>删除</span>
			</button>
			<button type="button" class="btn btn-system" onclick="doExcel()">
				<span>导出excel</span>
			</button>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div> 
</body>	
<script>
$(function(){
	var postData = $("#page_form").jsonForm();
	var gridOption ={
		url:"${ctx}/admin/appItemTypeBatch/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI:"Bootstrap", 
		colModel : [
			{name: "Code", index: "Code", width: 60, label: "批次号", sortable: true, align: "left"},
			{name: "Descr", index: "Descr", width: 70, label: "名称", sortable: true, align: "left"},
			{name: "issetitemdescr", index: "issetitemdescr", width: 100, label: "是否套餐材料", sortable: true, align: "left"},
			{name: "infoappalldescr", index: "infoappalldescr", width: 100, label: "提示一起下单", sortable: true, align: "left"},
			{name: "DispSeq", index: "DispSeq", width: 70, label: "显示顺序", sortable: true, align: "right"},
			{name: "LastUpdate", index: "LastUpdate", width: 142, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 60, label: "修改人", sortable: true, align: "left"},
			{name: "Expired", index: "Expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "ActionLog", index: "ActionLog", width: 50, label: "操作", sortable: true, align: "left"}
		],
		ondblClickRow: function(){
			view();
		}
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	$("#save").on("click",function(){
		Global.Dialog.showDialog("save",{
			title:"下单材料类型批次——新增",
			url:"${ctx}/admin/appItemTypeBatch/goSave",
			height:600,
			width:800,
			returnFun:goto_query
		});
	});
	
	$("#update").on("click",function(){
		ret=selectDataTableRow();
		if(!ret){
			art.dialog({
       			content: "请选择一条记录",
       		});
       		return;
		}
		Global.Dialog.showDialog("update",{
			title:"下单材料类型批次——编辑",
			url:"${ctx}/admin/appItemTypeBatch/goUpdate",
			postData:{code:ret.Code},
			height:600,
			width:800,
			returnFun:goto_query
		});
	});
	
	$("#delete").on("click",function(){
		var url = "${ctx}/admin/appItemTypeBatch/doDelete";
		beforeDel(url,"Code","删除该记录");
		returnFun: goto_query;
		return true;
	});
	
});

function view(){
	ret=selectDataTableRow();
	if(!ret){
		art.dialog({
      			content: "请选择一条记录",
      		});
      		return;
	}
	Global.Dialog.showDialog("view",{
		title:"下单材料类型批次——查看",
		url:"${ctx}/admin/appItemTypeBatch/goView",
		postData:{code:ret.Code},
		height:600,
		width:800,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/appItemTypeBatch/doExcel";
	doExcelAll(url);
}
</script>
</html>
