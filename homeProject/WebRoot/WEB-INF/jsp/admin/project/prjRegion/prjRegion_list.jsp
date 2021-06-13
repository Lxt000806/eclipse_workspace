<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>工程大区管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjRegion/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "code", index: "Code", width: 75, label: "编码", sortable: true, align: "left"},
				{name: "descr", index: "Descr", width: 75, label: "大区名称", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 122, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 110, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 110, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionLog", width: 110, label: "操作日志", sortable: true, align: "left"}
		],
	});
		
		
		//查看
		$("#view").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("view",{
				title:"工程大区管理——查看",
				url:"${ctx}/admin/prjRegion/goDetail",
				postData:{id:ret.code},
				height:220,
				width:740,
				returnFun:goto_query
			});
		});
		//编辑
		$("#update").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("update",{
				title:"工程大区管理——编辑",
				url:"${ctx}/admin/prjRegion/goUpdate",
				postData:{id:ret.code},
				height:220,
				width:740,
				returnFun:goto_query
			});
		});
		//新增
		$("#add").on("click",function(){
			Global.Dialog.showDialog("add",{
				title:"工程大区管理——新增",
				url:"${ctx}/admin/prjRegion/goSave",
				height:220,
				width:740,
				returnFun:goto_query
			});
		});
	});
		function doExcel(){
		var url = "${ctx}/admin/prjRegion/doExcel";
		doExcelAll(url);
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<li><label>编码</label> <input type="text"
						id="code" name="code" style="width:160px;"
						value="${prjRegion.code}" />
					</li>
					<li><label>大区名称</label> <input type="text" id="descr"
						name="descr" style="width:160px;" value="${prjRegion.descr}" />
					</li>
					<li class="search-group"><input type="checkbox"
						id="expired" name="expired" value="${prjRegion.expired}"
						onclick="hideExpired(this)" ${prjRegion.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<button type="button" class="btn btn-system" id="add">
				<span>新增</span>
			</button>
			<button type="button" class="btn btn-system" id="update">
				<span>编辑</span>
			</button>
			<button type="button" class="btn btn-system" id="view">
				<span>查看</span>
			</button>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
