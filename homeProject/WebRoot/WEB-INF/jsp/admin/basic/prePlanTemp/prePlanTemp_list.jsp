<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>快速预报价模板管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#type").val("");
		}	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prePlanTemp/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap',
		autoScroll: true, 
		colModel : [
			{name: "no", index: "no", width: 75, label: "编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 170, label: "名称", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 75, label: "客户类型", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 80, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
		],
	});
		//新增
		$("#add").on("click",function(){
				Global.Dialog.showDialog("goSave",{
					title:"快速预报价模板管理--增加",
					url:"${ctx}/admin/prePlanTemp/goSave",
				    height:700,
				    width:1350,
					returnFun:goto_query
				});
		});
		//编辑
		$("#update").on("click",function(){
				var ret = selectDataTableRow();
				if(!ret){
					art.dialog({
								content:"请选择一条记录！",
								width: 200
							});
					return;
				}
				Global.Dialog.showDialog("goUpdate",{
					title:"快速预报价模板管理--编辑",
					url:"${ctx}/admin/prePlanTemp/goUpdate?id="+ret.no,
				    height:700,
				    width:1350,
					returnFun:goto_query
				});
		});
		//查看
		$("#view").on("click",function(){
				var ret = selectDataTableRow();
				if(!ret){
					art.dialog({
								content:"请选择一条记录！",
								width: 200
							});
					return;
				}
				Global.Dialog.showDialog("goDetail",{
					title:"快速预报价模板管理--查看",
					url:"${ctx}/admin/prePlanTemp/goDetail?id="+ret.no,
				    height:700,
				    width:1350,
					returnFun:goto_query
				});
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/prePlanTemp/doExcel";
		doExcelAll(url);
	}
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#custType").val("");
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
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
					<li class="form-validate"><label>编码</label> <input type="text"
						id="no"  name="no" style="width:160px;"
						value="${prePlanTemp.no}" />
					</li>
					<li class="form-validate"><label>名称</label> <input type="text"
						id="descr" name="descr" style="width:160px;" value="${prePlanTemp.descr}"/>
					</li>
						<li>
							<label>客户类型</label>
							<house:DictMulitSelect id="custType" dictCode="" sql="select code ,desc1 descr from tcustType where expired='F'  " 
							sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
						</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${prePlanTemp.expired}"
						onclick="hideExpired(this)" ${prePlanTemp.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
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

			<house:authorize authCode="PREPLANTEMP_VIEW">
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
			</house:authorize>
			<button type="button" class="btn btn-system" onclick="doExcel()">
				<span>导出excel</span>
			</button>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
