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
		var url ="${ctx}/admin/salaryInd/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/salaryInd/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"code", index:"code", label:"编号", width:80, sortable: true, align:"left"}, 
				{name:"descr", index:"descr", label:"名称", width:80, sortable: true, align:"left"}, 
				{name:"indtypedescr", index:"indtypedescr", label:"指标分类", sortable: true, width:80,align:"left",}, 
				{name:"status", index:"indlevel", label:"状态", sortable: true, width:60,align:"left",hidden:true}, 
				{name:"statusdescr", index:"statusdescr", label:"状态", width:60, sortable: true, align:"left",}, 
				{name:"indlevel", index:"indlevel", label:"指标级别", sortable: true,width:80, align:"left",hidden:true}, 
				{name:"indleveldescr", index:"indleveldescr", label:"指标级别", width:80, sortable: true, align:"left"}, 
				{name:"objtype", index:"objtype", label:"适用对象",width:75, sortable: true, align:"left",hidden:true}, 
				{name:"objtypedescr", index:"objtypedescr", label:"适用对象", width:75, sortable: true, align:"left"}, 
				{name:"periodtype", index:"periodtype", label:"计算周期", sortable: true, width:75, align:"left",hidden:true}, 
				{name:"periodtypedescr", index:"periodtypedescr", label:"计算周期", width:75, sortable: true, align:"left"}, 
				{name:"remarks", index:"remarks", label:"备注", width:200, sortable: true, align:"left"}, 
				{name:"indunit", index:"indunit", label:"单位", width:60, sortable: true, align:"left"}, 
				{name:"calcmode", index:"calcmode", label:"计算方式", sortable: true, align:"left",hidden:true}, 
				{name:"calcmodedescr", index:"calcmodedescr", label:"计算方式", width:75, sortable: true, align:"left"}, 
				{name:"formula", index:"formula", label:"计算公式", sortable: true, width:100, align:"left" ,hidden:true}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"系统指标——新增",
			postData:{},
			url:"${ctx}/admin/salaryInd/goSave",
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
			title:"系统指标——编辑",
			postData:{code:ret.code},
			url:"${ctx}/admin/salaryInd/goUpdate",
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
			title:"系统指标——查看",
			postData:{code:ret.code},
			url:"${ctx}/admin/salaryInd/goView",
			height:375,
			width:730,
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
						<input type="text" id="quertCondition" name="quertCondition"style="width:160px;" placeholder="指标名称/代码"/>
					</li>
					<li>
						<label>适用对象</label>
						<house:xtdm  id="objType" dictCode="SALOBJTYPE" style="width:160px;"></house:xtdm>
					</li>
					<li>
						<label>指标级别</label>
						<house:xtdm  id="indLevel" dictCode="SALINDLEVEL" style="width:160px;"></house:xtdm>
					</li>
					<li>
						<label>统计周期</label>
						<house:xtdm  id="periodType" dictCode="SALPERIODTYPE" style="width:160px;"></house:xtdm>
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
				<house:authorize authCode="SALARYIND_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYIND_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYIND_VIEW">
					<button id="btnView" type="button" class="btn btn-system" onclick="view()">
						查看
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYIND_EXCEL">
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
