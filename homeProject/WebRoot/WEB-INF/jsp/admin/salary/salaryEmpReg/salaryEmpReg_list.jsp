<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>薪酬档案员工挂证</title>
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
		var url ="${ctx}/admin/salaryEmpReg/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/salaryEmpReg/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"pk", index:"pk", label:"pk", width:100, sortable: true, align:"left",hidden:true}, 
				{name:"salaryemp", index:"salaryemp", label:"员工编号", width:100, sortable: true, align:"left"}, 
				{name:"empname", index:"empname", label:"员工姓名", sortable: true, width:100,align:"left",}, 
				{name:"socialinsurparam", index:"socialinsurparam", label:"社保公积金参数pk", sortable: true, width:100,align:"left",hidden:true}, 
				{name:"descr", index:"descr", label:"社保公积金参数", sortable: true, width:100,align:"left",}, 
				{name:"remarks", index:"remarks", label:"备注", sortable: true, width:210,align:"left",}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", width:100, sortable: true, align:"left",formatter:formatTime}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left",}, 
				{name:"actionlog", index:"actionlog", label:"操作标识", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"员工挂证管理——新增",
			postData:{},
			url:"${ctx}/admin/salaryEmpReg/goSave",
			height:320,
			width:720,
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
			title:"员工挂证管理——编辑",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/salaryEmpReg/goUpdate",
			height:320,
			width:720,
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
			title:"员工挂证管理——查看",
			postData:{pk :ret.pk},
			url:"${ctx}/admin/salaryEmpReg/goView",
			height:320,
			width:720,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function regDel(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行删除操作",
			});
			return;
		}
		
		art.dialog({
			content:"是否确定删除该挂证信息",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/salaryEmpReg/doDelete",
					type:"post",
					data:{pk:ret.pk},
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						art.dialog({
							content: obj.msg,
						});
						$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
				return true;
			}
		});	

	}
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" name="expired" id="expired" value=""/>
				<ul class="ul-form">
					<li>
						<label>查询</label>
						<input type="text" id="queryCondition" name="queryCondition"style="width:160px;" placeholder="姓名/工号/身份证号"/>
					</li>

					<li class="search-group">
						<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${salaryEmpReg.expired }" onclick="hideExpired(this)" 
								 ${salaryEmpReg.expired!='T'?'checked':'' } /><p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SALARYEMPREG_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYEMPREG_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYEMPREG_DELETE">
					<button type="button" class="btn btn-system" onclick="regDel()">
						删除
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYEMPREG_VIEW">
					<button id="btnView" type="button" class="btn btn-system" onclick="view()">
						查看
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYEMPREG_EXCEL">
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
