<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>薪酬预领管理</title>
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
		var url ="${ctx}/admin/salaryAdvance/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/salaryAdvance/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"salaryemp", index:"salaryemp", label:"员工编号", width:80, sortable: true, align:"left"}, 
				{name:"empname", index:"empname", label:"员工姓名", width:80, sortable: true, align:"left"}, 
				{name:"advancewage", index:"advancewage", label:"预领金额", sortable: true, width:80,align:"left",}, 
				{name:"dept1descr", index:"dept1descr", label:"一级部门", sortable: true, width:80,align:"left",}, 
				{name:"remarks", index:"remarks", label:"说明", width:180, sortable: true, align:"left",}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true,width:80, align:"left",formatter:formatDate}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:80, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期",width:75, sortable: true, align:"left",}, 
				{name:"actionlog", index:"actionlog", label:"操作类型", width:75, sortable: true, align:"left"}, 
			]
		});
	});
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"薪酬预领——新增",
			postData:{},
			url:"${ctx}/admin/salaryAdvance/goSave",
			height:345,
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
			title:"薪酬预领——编辑",
			postData:{salaryEmp:ret.salaryemp},
			url:"${ctx}/admin/salaryAdvance/goUpdate",
			height:345,
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
			title:"薪酬预领——查看",
			postData:{salaryEmp:ret.salaryemp},
			url:"${ctx}/admin/salaryAdvance/goView",
			height:345,
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
						<input type="text" id="queryCondition" name="queryCondition"style="width:160px;" placeholder="身份证/姓名/工号"/>
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
				<house:authorize authCode="SALARYADVANCE_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYADVANCE_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYADVANCE_VIEW">
					<button id="btnView" type="button" class="btn btn-system" onclick="view()">
						查看
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYADVANCE_EXCEL">
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
