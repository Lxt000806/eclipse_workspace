<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>验收填写项目</title>
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
		var url ="${ctx}/admin/confirmCustomFiled/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/confirmCustomFiled/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"code", index:"code", label:"编号", width:80, sortable: true, align:"left",}, 
				{name:"descr", index:"descr", label:"名称", width:80, sortable: true, align:"left",}, 
				{name:"prjitem", index:"prjitem", label:"节点编号", width:80, sortable: true, align:"left",hidden:true}, 
				{name:"prjitemdescr", index:"prjitemdescr", label:"施工节点", sortable: true, width:80,align:"left",}, 
				{name:"type", index:"type", label:"类型编号", sortable: true, width:80,align:"left", hidden: true}, 
				{name:"typedescr", index:"typedescr", label:"填写类型", sortable: true, width:80, align:"left",}, 
				{name:"options", index:"option", label:"列表项目", sortable: true, width:100,align:"left",}, 
				{name:"dispseq", index:"dispseq", label:"顺序", width:60, sortable: true, align:"right",}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"验收填写项目值——新增",
			postData:{},
			url:"${ctx}/admin/confirmCustomFiled/goSave",
			height:320,
			width:765,
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
			title:"验收填写项目值——编辑",
			postData:{code: ret.code},
			url:"${ctx}/admin/confirmCustomFiled/goUpdate",
			height:320,
			width:765,
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
		Global.Dialog.showDialog("update",{
			title:"验收填写项目值——复制",
			postData:{code: ret.code},
			url:"${ctx}/admin/confirmCustomFiled/goCopy",
			height:320,
			width:765,
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
			title:"验收填写项目值——查看",
			postData:{code: ret.code},
			url:"${ctx}/admin/confirmCustomFiled/goView",
			height:320,
			width:765,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');

	}
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="expired"  name="expired" value=""/>
				<ul class="ul-form">
					<li class="form-validate">
						<label>名称</label>
						<input type="text" id="descr" name="descr" style="width:160px;" value=""/>
					</li>
					<li>
						<label>施工节点</label>
						<house:dict id="prjItem" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
						    sql="select Code, Descr  from tPrjItem1 where Expired = 'F' order by Seq"></house:dict>
					</li>
					<li class="form-validate">
						<label>填写类型</label>
						<house:xtdm id="type" dictCode="CONFFILEDTYPE" ></house:xtdm>                     
					</li>
					<li class="search-group">
						<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${confirmCustomFiled.expired }" onclick="hideExpired(this)" 
							 ${confirmCustomFiled.expired!='T'?'checked':'' } /><p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="CONFIRMCUSTOMFILED_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="CONFIRMCUSTOMFILED_COPY">
					<button type="button" class="btn btn-system" onclick="copy()">
						复制
					</button>
				</house:authorize>
				<house:authorize authCode="CONFIRMCUSTOMFILED_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="CONFIRMCUSTOMFILED_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">
						查看
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
