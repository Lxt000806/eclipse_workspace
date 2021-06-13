<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>基装类型1--列表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
$(function() {
	  //初始化表格
	  Global.JqGrid.initJqGrid("dataTable",{
	        url : "${ctx}/admin/baseItemType1/goJqGrid",
			height : $(document).height()- $("#content-list").offset().top - 70,
			styleUI : "Bootstrap",
			colModel : [
				{name: "code", index: "code", width: 100, label: "基装类型1型编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 120, label: "基装类型1型名称", sortable: true, align: "left"},
				{name: "dispseq", index: "dispseq", width: 70, label: "显示顺序", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastUpdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"}
			 ]
	    });
});
function add(){
	Global.Dialog.showDialog("baseItemType1",{
		title:"基装类型1--添加",
		url:"${ctx}/admin/baseItemType1/goSave",
		width:400,
		height:300,
		returnFun:goto_query
	});
}
function copy() {
	var ret = selectDataTableRow();
	if (ret) {
		Global.Dialog.showDialog("baseItemType1", {
			title : "基装类型1--复制",
			url : "${ctx}/admin/baseItemType1/goCopy?id="+ ret.code,
			height : 300,
			width : 400,
			returnFun : goto_query
			});
	} else {
		art.dialog({
		content : "请选择一条记录"
			});
		}
}
function update() {
	var ret = selectDataTableRow();
	if (ret) {
		Global.Dialog.showDialog("baseItemType1", {
			title : "基装类型1--编辑",
			url : "${ctx}/admin/baseItemType1/goUpdate?id="+ ret.code,
			height : 300,
			width : 400,
			returnFun : goto_query
			});
	} else {
		art.dialog({
		content : "请选择一条记录"
			});
		}
}
function view() {
	var ret = selectDataTableRow();
	if (ret) {
		Global.Dialog.showDialog("baseItemType1", {
	    	title : "基装类型1--查看",
			url : "${ctx}/admin/baseItemType1/goDetail?id=" + ret.code,
		    height : 300,
			width : 400,
			returnFun : goto_query
			});
	} else {
		art.dialog({
		content : "请选择一条记录"
			});
		}
}			
function doExcel() {
	var url = "${ctx}/admin/baseItemType1/doExcel";
	doExcelAll(url);
} 		
</script>
 </head>
 <body>
 	<div class="body-box-list">
 		<div class="query-form">
		<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" id="expired"  name="expired" value="${uom.expired}"/>
			<ul class="ul-form">
				<li><label>编号</label> <input type="text" id="code" name="code" /></li>
				<li><label>基装类型1名称</label> <input type="text" id="descr" name="descr" /></li>
			    <li class="search-group-shrink">
					<input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" checked/>   
					<P>隐藏过期</p>
					<button type="summit" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
					<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
				</li>
			</ul>
		</form>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system " onclick="add()">
				<span>新增</span>
				</button>
				<button type="button" class="btn btn-system " onclick="copy()">
				<span>复制</span>
				</button>
				<button type="button" class="btn btn-system " onclick="update()">
				<span>编辑</span>
				</button>
				<house:authorize authCode="BASEITEMTYPE1_VIEW">
				<button type="button" class="btn btn-system " onclick="view()">
				<span>查看</span>
				</button>
				</house:authorize>
				<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
			</div>
		</div>
	</div>		
 		<div id="content-list">
   		<table id="dataTable"></table>
   		<div id="dataTablePager"></div>
	</div>
</div>
</body>
</html>
 
