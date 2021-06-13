<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>供应商费用类型管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript" defer></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${supplFeeType.expired}"/>
				<input type="hidden" name="jsonString"/>
				<input type="hidden" name="m_umState" value="L" />
				<ul class="ul-form">
					<li>
						<label>编号</label> 
						<input type="text" id="code" name="code" style="width:160px;"/>
					</li>
					<li>
						<label>名称</label> 
						<input type="text" id="descr" name="descr" style="width:160px;"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " id="clear" 
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SUPPLFEETYPE_ADD">
					<button type="button" class="btn btn-system" id="add">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLFEETYPE_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLFEETYPE_DELETE">
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLFEETYPE_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" id="excel">
					<span>导出Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
	<script type="text/javascript">
		var postData = $("#page_form").jsonForm();
		$(function(){
			Global.JqGrid.initJqGrid("dataTable", {
				url: "${ctx}/admin/supplFeeType/goJqGrid",
				postData:postData ,
				sortable: true,
				height: $(document).height() - $("#content-list").offset().top - 68,
				styleUI: "Bootstrap", 
				colModel: [
					{name : "code",index : "code",width : 70,label:"编号",sortable : true,align : "left"},
					{name : "descr",index : "descr",width : 70,label:"名称",sortable : true,align : "left"},
					{name : "isdefault",index : "isdefault",width : 80,label:"是否默认值",sortable : true,align : "left",hidden: true},
					{name : "isdefaultdescr",index : "isdefaultdescr",width : 80,label:"是否默认值",sortable : true,align : "left"},
					{name : "lastupdate",index : "lastupdate",width : 150,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
					{name : "lastupdatedby",index : "lastupdatedby",width : 70,label:"修改人",sortable : true,align : "left"},
					{name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
					{name : "actionlog",index : "actionlog",width : 70,label:"操作日志",sortable : true,align : "left"}
				],
				ondblClickRow: function(){
					view();
				}
			});
			$("#excel").on("click", function() {
				doExcel();
			});
			$("#add").on("click",function(){
				Global.Dialog.showDialog("add",{
					title:"供应商费用类型——新增",
					url:"${ctx}/admin/supplFeeType/goAdd",
					postData:{
						m_umState:"A",
					},
					height:268,
					width:455,
					returnFun:goto_query
				});
			});
			$("#update").on("click",function(){
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				Global.Dialog.showDialog("update",{
					title:"供应商费用类型——编辑",
					url:"${ctx}/admin/supplFeeType/goUpdate",
					postData:{
						code:ret.code,
						m_umState:"M",
					},
					height:268,
					width:455,
					returnFun:goto_query
				});
			});
			$("#delete").on("click",function(){
				var url = "${ctx}/admin/supplFeeType/doDelete";
				beforeDel(url,"code","删除该信息");
				returnFun: goto_query;
				return true;
			});
			$("#view").on("click", function () {
				view();
			});
		});
		function view(){
			var ret=selectDataTableRow();
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("view",{
				title:"供应商费用类型——查看",
				url:"${ctx}/admin/supplFeeType/goView",
				postData:{
					m_umState:"V",
					code:ret.code,
				},
				height:268,
				width:455,
				returnFun:goto_query
			});
		}
		function doExcel(){
			var url = "${ctx}/admin/supplFeeType/doExcel";
			doExcelAll(url);
		}
	</script>
</body>
</html>
