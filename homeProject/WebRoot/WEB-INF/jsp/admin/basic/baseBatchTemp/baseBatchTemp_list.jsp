<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>基础批量报价模板</title>
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
			url:"${ctx}/admin/baseBatchTemp/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap',
			autoScroll: true, 
			colModel : [
				{name: "no", index: "no", width: 137, label: "模板编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 227, label: "模板名称", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 140, label: "模板适用的客户类型", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 139, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 97, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 108, label: "修改操作", sortable: true, align: "left"}
			],
		});
		//新增
		$("#add").on("click",function(){
			Global.Dialog.showDialog("goSave",{
				title:"基础批量报价模板--增加",
				url:"${ctx}/admin/baseBatchTemp/goSave",
			    height:300,
			    width:400,
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
				title:"基础批量报价模板--编辑",
				url:"${ctx}/admin/baseBatchTemp/goUpdate?id="+ret.no,
				height:300,
			    width:400,
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
				title:"基础批量报价模板--查看",
				url:"${ctx}/admin/baseBatchTemp/goDetail?id="+ret.no,
				height:300,
			    width:400,
				returnFun:goto_query
			});
		});
		//区域配置
		$("#area").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条记录！",
					width: 200
				});
				return;
			}
			Global.Dialog.showDialog("goArea",{
				title:"基础批量报价模板--区域配置",
				url:"${ctx}/admin/baseBatchTemp/goArea?id="+ret.no,
				height:680,
			    width:1000,
				returnFun:goto_query
			});
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/baseBatchTemp/doExcel";
		doExcelAll(url);
	}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" /> <input
					type="hidden" id="expired" name="expired" value="" />
				<ul class="ul-form">
					<li><label>模板编号</label> <input type="text" id="no" name="no"
						value="${baseBatchTemp.no}" /></li>
					<li><label>模板名称</label> <input type="text" id="descr"
						name="descr" value="${baseBatchTemp.descr}" />
					</li>
					<li><label>客户类型</label> <house:dict id="custType" dictCode=""
							sql="select code ,code+' '+desc1 descr from tcustType where expired='F'"
							sqlValueKey="Code" sqlLableKey="Descr"
							value="${baseBatchTemp.custType}">
						</house:dict>
					</li>
					<li class="search-group"><input type="checkbox"
						id="expired_show" name="expired_show"
						value="${cstrDatStd.expired}" onclick="hideExpired(this)"
						${cstrDatStd.expired!='T' ?'checked':'' }/>
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
			<house:authorize authCode="BASEBATCHTEMP_SAVE">
				<button type="button" class="btn btn-system" id="add">
					<span>新增</span>
				</button>
			</house:authorize>	
			<house:authorize authCode="BASEBATCHTEMP_UPDATE">
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
			</house:authorize>	
			<house:authorize authCode="BASEBATCHTEMP_FIXAREASET">
				<button type="button" class="btn btn-system" id="area">
					<span>区域配置</span>
				</button>
			</house:authorize>	
			<house:authorize authCode="BASEBATCHTEMP_VIEW">
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
			</house:authorize>
			<house:authorize authCode="BASEBATCHTEMP_EXCEL">
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
			</house:authorize>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
