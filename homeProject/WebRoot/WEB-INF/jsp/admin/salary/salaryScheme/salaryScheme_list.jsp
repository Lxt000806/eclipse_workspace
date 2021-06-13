<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>薪酬方案管理</title>
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
		var url ="${ctx}/admin/salaryScheme/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/salaryScheme/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"pk", index:"pk", label:"pk", width:50, sortable: true, align:"left",hidden:true}, 
				{name:"descr", index:"descr", label:"名称", width:130, sortable: true, align:"left"}, 
				{name:"salaryschemetypedescr", index:"salaryschemetypedescr", label:"方案类型", sortable: true, width:100,align:"left",}, 
				{name:"cmpdescr", index:"cmpdescr", label:"公司名称", sortable: true, width:80,align:"left",hidden:true}, 
				{name:"statusdescr", index:"statusdescr", label:"状态", sortable: true, width:80,align:"left",}, 
				{name:"remarks", index:"remarks", label:"备注", width:200, sortable: true, align:"left",}, 
				{name:"beginmon", index:"beginmon", label:"开始月份", width:80,sortable: true, align:"left",}, 
				{name:"endmon", index:"endmon", label:"结束月份" , width:80, sortable: true, align:"left",}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", width:100, sortable: true, align:"left",formatter:formatTime}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left",}, 
				{name:"actionlog", index:"actionlog", label:"操作标识", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"薪酬方案——新增",
			postData:{},
			url:"${ctx}/admin/salaryScheme/goSave",
			height:690,
			width:920,
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
			title:"薪酬方案——编辑",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/salaryScheme/goUpdate",
			height:690,
			width:920,
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
			title:"薪酬方案——查看",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/salaryScheme/goView",
			height:690,
			width:920,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function empList(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		
		Global.Dialog.showDialog("empList",{
			title:"适用人员"+"【"+ret.descr+"】",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/salaryScheme/goEmpList",
			height:690,
			width:1080,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function paymentList(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		
		Global.Dialog.showDialog("paymentList",{
			title:"分账定义"+"【"+ret.descr+"】",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/salaryScheme/goPaymentList",
			height:690,
			width:1080,
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
						<label>方案名称</label>
						<input type="text" id="descr" name="descr"style="width:160px;" placeholder=""/>
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
				<house:authorize authCode="SALARYSCHEME_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYSCHEME_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYSCHEME_EMPLIST">
					<button id="btnView" type="button" class="btn btn-system" onclick="empList()">
						适用人员名单
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYSCHEME_VIEW">
					<button id="btnView" type="button" class="btn btn-system" onclick="view()">
						查看
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYSCHEME_PAYMENTLIST">
					<button id="btnView" type="button" class="btn btn-system" onclick="paymentList()">
						分账定义
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
