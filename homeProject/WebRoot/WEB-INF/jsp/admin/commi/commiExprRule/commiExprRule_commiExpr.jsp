<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>计算公式管理--计算公式配置</title>
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
		var url ="${ctx}/admin/commiExpr/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/commiExpr/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"pk", index:"pk", label:"pk", width:80, sortable: true, align:"left", hidden:true}, 
				{name:"descr", index:"descr", label:"名称", width:120, sortable: true, align:"left"}, 
				{name:"expr", index:"expr", label:"提成公式", width:350, sortable: true, align:"left"}, 
				{name:"exprremarks", index:"exprremarks", label:"提成公式说明", width:350, sortable: true, align:"left"}, 
				{name:"rightcardinalexpr", index:"rightcardinalexpr", label:"右边销售基数公式", width:350, sortable: true, align:"left"}, 
				{name:"rightcardinalexprremarks", index:"rightcardinalexprremarks", label:"右边销售基数公式说明", width:350, sortable: true, align:"left"}, 
				{name:"remarks", index:"remarks", label:"备注", width:200, sortable: true, align:"left"}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function goSave(){
		Global.Dialog.showDialog("commiExprSave",{
			title:"公式管理——新增",
			postData:{},
			url:"${ctx}/admin/commiExpr/goSave",
			height:450,
			width:758,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function goUpdate(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("commiExprUpdate",{
			title:"公式管理——编辑",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/commiExpr/goUpdate",
			height:450,
			width:758,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function goView(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("commiExprView",{
			title:"公式管理——查看",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/commiExpr/goView",
			height:450,
			width:758,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function doExpired(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行过期操作",
			});
			return;
		} 
		art.dialog({
			content:"是否确定过期该记录？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/commiExpr/doExpired",
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
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
			</div>
		</div>	
	</div>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="expired"  name="expired" value="${commiExpr.expired }"/>
				<ul class="ul-form">
					<li>
						<label>名称</label>		
						<input type="text" id="descr" name="descr"style="width:160px;" placeholder="公式名称"/>
					</li>
					<li class="search-group">					
						<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${commiExpr.expired }" onclick="hideExpired(this)" 
								 ${commiExpr.expired!='T'?'checked':'' } /><p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" onclick="goSave()">
					新增
				</button>
				<button type="button" class="btn btn-system" onclick="goUpdate()">
					编辑
				</button>
				<button type="button" class="btn btn-system" onclick="goView()">
					查看
				</button>
				<button type="button" class="btn btn-system" onclick="doExpired()">
					过期
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
