<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>提成公式管理</title>
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
		var url ="${ctx}/admin/commiExprRule/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/commiExprRule/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"pk", index:"pk", label:"pk", width:80, sortable: true, align:"left",hidden:true}, 
				{name:"role", index:"role", label:"角色", width:80, sortable: true, align:"left",hidden:true}, 
				{name:"roledescr", index:"roledescr", label:"角色", width:80, sortable: true, align:"left",}, 
				{name:"custtype", index:"custtype", label:"客户类型编号", width:80, sortable: true, align:"left",hidden:true}, 
				{name:"custtypedescr", index:"custtypedescr", label:"客户类型", width:80, sortable: true, align:"left"}, 
				{name:"department", index:"department", label:"客户类型", width:80, sortable: true, align:"left",hidden:true}, 
				{name:"departmentdescr", index:"departmentdescr", label:"部门", width:80, sortable: true, align:"left"}, 
				{name:"prior", index:"prior", label:"优先级", sortable: true, width:80,align:"right",}, 
				{name:"precommiexprpk", index:"precommiexprpk", label:"预发提成公式pk", sortable: true, width:120,align:"left",hidden:true}, 
				{name:"precommiexprdescr", index:"precommiexprdescr", label:"预发提成公式", sortable: true, width:180,align:"left",hidden:true}, 
				{name:"precommiexpr", index:"precommiexprdescr", label:"预发提成公式", sortable: true, width:250,align:"left",}, 
				{name:"precommiexprremarks", index:"precommiexprremarks", label:"预发提成公式", sortable: true, width:250,align:"left",}, 
				{name:"checkcommiexprpk", index:"checkcommiexprpk", label:"结算提成公式pk", sortable: true, width:120,align:"left",hidden:true}, 
				{name:"checkcommiexprdescr", index:"checkcommiexprdescr", label:"结算提成公式", width:180, sortable: true, align:"left",hidden:true}, 
				{name:"checkcommiexpr", index:"checkcommiexpr", label:"结算提成公式", width:250, sortable: true, align:"left",}, 
				{name:"checkcommiexprremarks", index:"checkcommiexprremarks", label:"结算提成公式", width:250, sortable: true, align:"left",}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"计算公式规则——新增",
			postData:{},
			url:"${ctx}/admin/commiExprRule/goSave",
			height:300,
			width:760,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function update(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行编辑",
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"计算公式规则——编辑",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/commiExprRule/goUpdate",
			height:300,
			width:760,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function copy(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行复制",
			});
			return;
		}
		Global.Dialog.showDialog("copy",{
			title:"计算公式规则——复制",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/commiExprRule/goCopy",
			height:300,
			width:760,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function view(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行查看",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"计算公式规则——查看",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/commiExprRule/goView",
			height:300,
			width:760,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function goCommiExpr(){
		Global.Dialog.showDialog("CommiExpr",{
			title:"公式管理",
			postData:{},
			url:"${ctx}/admin/commiExprRule/goCommiExpr",
			height:650,
			width:1130,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function delDetail(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行删除操作",
			});
			return;
		} 
		art.dialog({
			content:"是否确定删除该记录？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/commiExprRule/doDel",
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
				<ul class="ul-form">
					<li class="form-validate">
						<label>角色</label>
						<house:dict id="role" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
						    sql="select Code,code+' '+Descr Descr from tRoll where Expired = 'F' and Code in ('01','24','00','63')"></house:dict>
					</li>
					<li class="form-validate">
						<label>客户类型</label>
						<house:dict id="custType" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
						    sql="select Code,code+' '+Desc1 Descr from tCustType where Expired = 'F'"></house:dict>
					</li>
					<li>
						<label>部门</label>
						<house:DictMulitSelect id="department" dictCode="" 
							sql=" select Code, desc1 Descr from tDepartment where Expired = 'F' "
							sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
					</li>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="COMMIEXPRRULE_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="COMMIEXPRRULE_COPY">
					<button type="button" class="btn btn-system" onclick="copy()">
						复制
					</button>
				</house:authorize>
				<house:authorize authCode="COMMIEXPRRULE_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="COMMIEXPRRULE_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">
						查看
					</button>
				</house:authorize>
				<house:authorize authCode="COMMIEXPRRULE_COMMIEXPR">
					<button type="button" class="btn btn-system" onclick="goCommiExpr()">
						公式管理
					</button>
				</house:authorize>
				<house:authorize authCode="COMMIEXPRRULE_DEL">
					<button type="button" class="btn btn-system" onclick="delDetail()">
						删除
					</button>
				</house:authorize>
				<house:authorize authCode="COMMIEXPRRULE_EXCEL">
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
