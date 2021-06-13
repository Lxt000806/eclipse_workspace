<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>工人人工合同管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#type").val("");
	}	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/workCon/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap',
			autoScroll: true, 
			colModel : [
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "workname", index: "workname", width: 80, label: "工人姓名", sortable: true, align: "left"},
				{name: "worktype1descr", index: "worktype1descr", width: 80, label: "工种分类1", sortable: true, align: "left"},
				{name: "worktype2descr", index: "worktype2descr", width: 80, label: "工种分类2", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 80, label: "金额", sortable: true, align: "right"},
				{name: "area", index: "area", width: 80, label: "面积", sortable: true, align: "right"},
				{name: "constructday", index: "constructday", width: 80, label: "施工工期", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"}
			],
		});
		$("#custCode").openComponent_customer();
		Global.LinkSelect.initSelect("${ctx}/admin/workCostDetail/workTypeByAuthority","workType1","workType2");
		//新增
		$("#add").on("click",function(){
			Global.Dialog.showDialog("goSave",{
				title:"工人人工合同管理--增加",
				url:"${ctx}/admin/workCon/goSave",
			    height:500,
			    width:550,
				returnFun:goto_query
			});
		});
		//复制
		$("#copy").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条记录！",
					width: 200
				});
				return;
			}
			Global.Dialog.showDialog("goCopy",{
				title:"工人人工合同管理--复制",
				url:"${ctx}/admin/workCon/goCopy?id="+ret.pk,
			    height:500,
			    width:550,
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
				title:"工人人工合同管理--编辑",
				url:"${ctx}/admin/workCon/goUpdate?id="+ret.pk,
			    height:500,
			    width:550,
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
			Global.Dialog.showDialog("goView",{
				title:"工人人工合同管理--查看",
				url:"${ctx}/admin/workCon/goView?id="+ret.pk,
			    height:500,
			    width:550,
				returnFun:goto_query
			});
		});
		
	});
	function doExcel(){
		var url = "${ctx}/admin/workCon/doExcel";
		doExcelAll(url);
	}
	function clearForm(){
		$("#page_form input[type='text']").val('');
	    $("#page_form select").val('');
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
					<li><label>客户编号</label> <input type="text" id="custCode" name="custCode"
						value="${workCon.custCode}" /></li>
						<li><label>工种分类1</label> <select id="workType1"
							name="workType1"
							onchange=""></select>
						</li>
						<li><label>工种分类2</label> <select id="workType2"
							name="workType2"
							onchange=""></select>
						</li>
					<li class="search-group"><input type="checkbox"
						id="expired_show" name="expired_show"
						value="${workCon.expired}" onclick="hideExpired(this)"
						${workCon.expired!='T' ?'checked':'' }/>
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
			<button type="button" class="btn btn-system" id="copy">
				<span>复制</span>
			</button>
			<button type="button" class="btn btn-system" id="update">
				<span>编辑</span>
			</button>
			<house:authorize authCode="WORKCON_VIEW">
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
