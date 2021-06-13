<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>操作员专盘列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function add(){
		Global.Dialog.showDialog("czySpcBuilderAdd",{
		  	title:"添加操作员专盘",
		  	url:"${ctx}/admin/czySpcBuilder/goSave",
		  	height: 320,
		  	width:600,
		  	returnFun: goto_query
		});
	}
	
	function del(){
		var url = "${ctx}/admin/czySpcBuilder/doDelete";
		beforeDel(url,"pk");
	}
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/czySpcBuilder/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-80,
			colModel : [
			  {name : "pk",index : "pk",width : 50,label:"pk",sortable : true,align : "left",hidden: true},
			  {name : "czybh",index : "czybh",width : 100,label:"操作员编号",sortable : true,align : "left"},
			  {name : "zwxm",index : "zwxm",width : 100,label:"操作员姓名",sortable : true,align : "left"},
			  {name : "spcbuilder",index : "spcbuilder",width : 100,label:"专盘编号",sortable : true,align : "left"},
			  {name : "spcbuilderdescr",index : "spcbuilderdescr",width : 200,label:"专盘名称",sortable : true,align : "left"}
            ]
		});
		$("#czybh").openComponent_czybm();
		$("#spcBuilder").openComponent_builder();
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>操作员编号</label>
						<input type="text" id="czybh" name="czybh" style="width:160px;" value="${czySpcBuilder.czybh }" />
					</li>
					<li>
						<label>专盘编号</label>
						<input type="text" id="spcBuilder" name="spcBuilder" style="width:160px;" value="${czySpcBuilder.spcBuilder}" />
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="btn-panel">
			 <div class="btn-group-sm">
            	<button type="button" class="btn btn-system" onclick="add()">添加</button>
                <button type="button" class="btn btn-system" onclick="del()">删除</button>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div> 
</body>
</html>
