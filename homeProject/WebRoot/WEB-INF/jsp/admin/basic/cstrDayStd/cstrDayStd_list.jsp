<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>施工工期配置</title>
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
			url:"${ctx}/admin/cstrDayStd/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap',
			autoScroll: true, 
			colModel : [
				{name: "pk", index: "pk", width: 77, label: "编号", sortable: true, align: "left", hidden: true},
				{name: "custtypedescr", index: "custtypedescr", width: 110, label: "客户类型", sortable: true, align: "left"},
				{name: "layoutdescr", index: "layoutdescr", width: 85, label: "户型", sortable: true, align: "left"},
				{name: "fromarea", index: "fromarea", width: 91, label: "从面积", sortable: true, align: "right"},
				{name: "toarea", index: "toarea", width: 92, label: "到面积", sortable: true, align: "right"},
				{name: "constructday", index: "constructday", width: 111, label: "施工工期", sortable: true, align: "right"},
				{name: "prior", index: "prior", width: 92, label: "优先级", sortable: true, align: "right"},
				{name: "lastupdatedby", index: "lastupdatedby", width: 111, label: "最后修改人员", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 158, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "expired", index: "expired", width: 85, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 142, label: "操作日志", sortable: true, align: "left"}
			],
		});
		//新增
		$("#add").on("click",function(){
			Global.Dialog.showDialog("goSave",{
				title:"施工工期配置--增加",
				url:"${ctx}/admin/cstrDayStd/goSave",
			    height:300,
			    width:700,
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
				title:"施工工期配置--编辑",
				url:"${ctx}/admin/cstrDayStd/goUpdate?id="+ret.pk,
				height:300,
			    width:700,
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
				title:"施工工期配置--查看",
				url:"${ctx}/admin/cstrDayStd/goDetail?id="+ret.pk,
				height:300,
			    width:700,
				returnFun:goto_query
			});
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/cstrDayStd/doExcel";
		doExcelAll(url);
	}
	function del(){
		var ret = selectDataTableRow();
		if (ret) {
			art.dialog({
				content : "确定要删除该条记录吗？",
				ok : function() {
					$.ajax({
						url : "${ctx}/admin/cstrDayStd/doDelete?deleteIds=" + ret.pk,
						type : "post",
						dataType : "json",
						cache : false,
						error : function(obj) {
							art.dialog({
								content : "删除出错,请重试",
								time : 1000,
								beforeunload : function() {
									goto_query();
								}
							});
						},
						success : function(obj) {
							if (obj.rs) {
								goto_query();
							} else {
								$("#_form_token_uniq_id").val(obj.token.token);
								art.dialog({
									content : obj.msg,
									width : 200
								});
							}
						}
					});
				},
				cancel : function() {
					goto_query();
				}
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
	function clearForm(){
		$("#custType").val("");
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
					<li><label>客户类型</label> <house:dict id="custType"
								dictCode=""
								sql="select code ,code+' '+desc1 descr from tcustType where expired='F'"
								sqlValueKey="Code" sqlLableKey="Descr"
								value="${cstrDayStd.custType}">
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
			<button type="button" class="btn btn-system" id="add">
				<span>新增</span>
			</button>
			<button type="button" class="btn btn-system" id="update">
				<span>编辑</span>
			</button>
			<house:authorize authCode="CSTRDAYSTD_VIEW">
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
			</house:authorize>
			<button type="button" class="btn btn-system" onclick="del()">
				<span>删除</span>
			</button>
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
