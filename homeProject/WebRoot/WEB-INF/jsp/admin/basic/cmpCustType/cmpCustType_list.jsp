<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>公司销售产品管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_company.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#type").val("");
	}	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/cmpCustType/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap',
			autoScroll: true, 
			colModel : [
				{name: "pk", index: "pk", width: 137, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "company", index: "company", width:180 , label: "公司", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "cmpnyname", index: "cmpnyname", width: 120, label: "公司名称", sortable: true, align: "left"},
				{name: "logofile", index: "logofile", width: 80, label: "logo文件", sortable: true, align: "left"},
				{name: "cmpnyfullname", index: "cmpnyfullname", width:200 , label: "公司全称", sortable: true, align: "left"},
				{name: "cmpnyaddress", index: "cmpnyaddress", width: 330, label: "公司地址", sortable: true, align: "left"},
				{name: "payeedescr", index: "payeedescr", width: 110, label: "工程款收款单位", sortable: true, align: "left"},
				{name: "designpayeedescr", index: "designpayeedescr", width: 110, label: "设计费收款单位", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
			],
		});
		$("#cmpCode").openComponent_company();
		//新增
		$("#add").on("click",function(){
			Global.Dialog.showDialog("goSave",{
				title:"公司销售产品管理--增加",
				url:"${ctx}/admin/cmpCustType/goSave",
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
				title:"公司销售产品管理--编辑",
				url:"${ctx}/admin/cmpCustType/goUpdate?id="+ret.pk,
			    height:320,
			    width:700,
				returnFun:goto_query
			});
		});
		//编辑
		$("#copy").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条记录！",
					width: 200
				});
				return;
			}
			Global.Dialog.showDialog("goSave",{
				title:"公司销售产品管理--复制",
				url:"${ctx}/admin/cmpCustType/goSave?id="+ret.pk,
			    height:320,
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
				title:"公司销售产品管理--查看",
				url:"${ctx}/admin/cmpCustType/goDetail?id="+ret.pk,
			    height:320,
			    width:700,
				returnFun:goto_query
			});
		});
		
		//合同范本上传		
		$("#addContractTemp").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条记录！",
					width: 200
				});
				return;
			}
			Global.Dialog.showDialog("goDetail",{
				title:"公司销售产品管理--合同范本上传",
				url:"${ctx}/admin/cmpCustType/goContractTemp?id="+ret.pk,
			    height:325,
			    width:750,
				returnFun:goto_query
			});
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/cmpCustType/doExcel";
		doExcelAll(url);
	}
	function clearForm(){
		$("#page_form input[type='text']").val('');
	    $("#page_form select").val('');
	    $("#custType").val('');
	    $.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
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
					<li><label>公司</label> <input type="text" id="cmpCode" name="cmpCode"
						value="${cmpCustType.cmpCode}" /></li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li class="search-group"><input type="checkbox"
						id="expired_show" name="expired_show"
						value="${cmpCustType.expired}" onclick="hideExpired(this)"
						${cmpCustType.expired!='T' ?'checked':'' }/>
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
			<house:authorize authCode="CMPCUSTTTYPE_SAVE">
				<button type="button" class="btn btn-system" id="add">
					<span>新增</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CMPCUSTTTYPE_SAVE">
				<button type="button" class="btn btn-system" id="copy">
					<span>复制</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CMPCUSTTTYPE_UPDATE">
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CMPCUSTTTYPE_VIEW">
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CMPCUSTTYPE_CONTRACTTEMP">
				<button type="button" class="btn btn-system" id="addContractTemp">
					<span>合同范本</span>
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
