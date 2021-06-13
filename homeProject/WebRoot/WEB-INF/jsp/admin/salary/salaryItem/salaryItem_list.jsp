<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>薪酬管理系统指标</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
<META HTTP-EQUIV="expires" CONTENT="0"/>
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	::-webkit-input-placeholder { /* Chrome */
	  color: #cccccc;
	}

	</style>
<script type="text/javascript">
	/**导出EXCEL*/
	function doExcel() {
		var url ="${ctx}/admin/salaryItem/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/salaryItem/goJqGrid",
			postData:{status:"1"},
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"code", index:"code", label:"薪酬项目代码", width:100, sortable: true, align:"left"}, 
				{name:"descr", index:"descr", label:"项目名称", width:100, sortable: true, align:"left"}, 
				{name:"isadjustabledescr", index:"isadjustabledescr", label:"支持手动调整", sortable: true, width:100,align:"left",}, 
				{name:"issysretentiondescr", index:"issysretentiondescr", label:"系统保留标识", sortable: true, width:100,align:"left",}, 
				{name:"itemlevel", index:"itemlevel", label:"计算优先级", width:100, sortable: true, align:"left",}, 
				{name:"itemgroupdescr", index:"itemgroupdescr", label:"薪酬项目分组", sortable: true, align:"left",}, 
				{name:"remarks", index:"remarks", label:"薪酬项目说明", width:180,sortable: true, align:"left",}, 
				{name:"precision", index:"precision", label:"数值精度",width:75, sortable: true, align:"right",}, 
				{name:"typedescr", index:"typedescr", label:"属性",width:75, sortable: true, align:"left",}, 
				{name:"statusdescr", index:"statusdescr", label:"状态",width:75, sortable: true, align:"left",}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", width:100, sortable: true, align:"left",formatter:formatTime}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left",}, 
				{name:"actionlog", index:"actionlog", label:"操作标识", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"薪酬项目——新增",
			postData:{},
			url:"${ctx}/admin/salaryItem/goSave",
			height:375,
			width:730,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function update(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		
		Global.Dialog.showDialog("update",{
			title:"薪酬项目——编辑",
			postData:{code:ret.code},
			url:"${ctx}/admin/salaryItem/goUpdate",
			height:375,
			width:730,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function view(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		
		Global.Dialog.showDialog("view",{
			title:"薪酬项目——查看",
			postData:{code:ret.code},
			url:"${ctx}/admin/salaryItem/goView",
			height:425,
			width:730,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function categoryDefine(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		
		Global.Dialog.showDialog("view",{
			title:"薪酬项目——计算规则定义",
			postData:{code:ret.code},
			url:"${ctx}/admin/salaryItem/goCategoryDefine",
			height:750,
			width:1100,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function personalDefine(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		
		Global.Dialog.showDialog("view",{
			title:"薪酬项目——个人定义",
			postData:{code:ret.code},
			url:"${ctx}/admin/salaryItem/goPersonalDefine",
			height:750,
			width:1100,
	        resizable: true,
			returnFun:goto_query
		});	
	}
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>查询</label>
						<input type="text" id="queryCondition" name="queryCondition"style="width:160px;" placeholder="指标名称/代码"/>
					</li>
					<li class="form-validate">
						<label>统计属性</label>
						<house:xtdm  id="type" dictCode="SALITEMTYPE" style="width:160px;"></house:xtdm>
					</li>
					<li class="form-validate">
						<label>状态</label>
						<house:xtdm  id="status" dictCode="SALENABLESTAT" style="width:160px;" value="1"></house:xtdm>
					</li>
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SALARYITEM_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYITEM_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYITEM_CATEGORYDEFINE">
					<button id="btnView" type="button" class="btn btn-system" onclick="categoryDefine()">
						计算规则定义
					</button>
				</house:authorize>
				<%-- <house:authorize authCode="SALARYITEM_PERSONALDEFINE">
					<button id="btnView" type="button" class="btn btn-system" onclick="personalDefine()">
						个人定义
					</button>
				</house:authorize> --%>
				<house:authorize authCode="SALARYITEM_VIEW">
					<button id="btnView" type="button" class="btn btn-system" onclick="view()">
						查看
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYITEM_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						导出到Excel
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
