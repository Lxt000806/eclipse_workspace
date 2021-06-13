<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>库位管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label for="pk">编号</label>
						<input type="text" id="pk" name="pk" style="width: 160px;"/>
					</li>
					<li>
						<label for="desc1">名称</label>
						<input type="text" id="desc1" name="desc1" style="width: 160px;"/>
					</li>
					<li>
						<label for="whcode">仓库编号</label>
						<input type="text" id="whcode" name="whcode" style="width:160px;" value=""/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${wareHousePosi.expired}" 
							onclick="hideExpired(this)" ${wareHousePosi.expired!='T' ?'checked':''}/>
						<label for="expired_show" style="margin-left: -3px;text-align: left;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button id="clear" type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="WAREHOUSEPOSI_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" id="update">
					<span>修改</span>
				</button>
				<house:authorize authCode="WAREHOUSEPOSI_VIEW">
					<button type="button" class="btn btn-system" id="viewBtn" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>输出到Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>

<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	$("#whcode").openComponent_wareHouse();

	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,/* 列重排 */
		url: "${ctx}/admin/wareHousePosi/goJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left"},
			{name: "desc1", index: "desc1", width: 50, label: "名称", sortable: true, align: "left"},
			{name: "whdescr", index: "whdescr", width: 130, label: "仓库", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
		],
		ondblClickRow: function(){/* 双击触发事件 */
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("Save", {
			title : "库位管理--新增",
			url : "${ctx}/admin/wareHousePosi/goWin",
			height : 320,
			width : 460,
			returnFun : goto_query
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
			title:"库位管理--编辑",
			url:"${ctx}/admin/wareHousePosi/goWin",
			postData:{pk:ret.pk,m_umState:"M",expired:"T"},
			height : 320,
			width : 460,
			returnFun:goto_query
		});
	});

	/* 清空下拉选择树checked状态 */
	$("#clear").on("click",function(){
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
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
		title:"库位管理——查看",
		url:"${ctx}/admin/wareHousePosi/goWin",
		postData:{pk:ret.pk,m_umState:"V",expired:"T"},
		height : 320,
		width : 460,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/wareHousePosi/doExcel";
	doExcelAll(url);
}
</script>
</html>
