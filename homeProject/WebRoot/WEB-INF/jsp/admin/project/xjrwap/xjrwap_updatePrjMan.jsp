<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";

%>
<!DOCTYPE html>
<html>
<head>
	<title>重点巡检监理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	
	//初始化表格	
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/xjrwap/goPrjJqGrid",
		height:$(document).height()-$("#content-list").offset().top-50,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "PK", index: "PK", width: 98, label: "监理编号", sortable: true, align: "left",hidden:true},
			{name: "ProjectMan", index: "ProjectMan", width: 98, label: "监理编号", sortable: true, align: "left"},
			{name: "projectdescr", index: "projectdescr", width: 98, label: "监理", sortable: true, align: "left"},
			{name: "LastUpdate", index: "LastUpdate", width: 98, label: "最后修改时间", sortable: true, align: "left",formatter:formatDate},
			{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 98, label: "最后修改人", sortable: true, align: "left"},
		],
	});
	
	$("#add").on("click",function(){
		Global.Dialog.showDialog("save",{
			  title:"重点巡检监理——增加",
			  url:"${ctx}/admin/xjrwap/goAddPrjMan",
			  height: 380,
			  width:650,
			  returnFun:goto_query
		 });
	});
	
	$("#del").on("click",function(){
		var ret = selectDataTableRow();
		art.dialog({
			content:"是否删除",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/xjrwap/doDel",
					type:'post',
					data:{pk:ret.PK},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						art.dialog({
							content:'删除成功',
						});
						$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
					return true;
			}
		});	
	});
	
});

</script>
</head>
	<body>
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="add">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system " id="del">
						<span>删除</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
					<button type="button" class="btn btn-system "  onclick="doExcelNow('重点巡检监理表')" title="导出检索条件数据">
						<span>导出excel</span>
					</button>	
				</div>
			</div>
		</div>
		<div class="body-box-list">
				<form role="form" hidden= "true" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
				</form>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
		</div> 
	</body>	
</html>
