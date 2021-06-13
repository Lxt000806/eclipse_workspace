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
	<title>基础项目算法管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>

</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>编码</label> 
						<input type="text" id="code"  name="code" style="width:160px;" value="${baseAlgorithm.no}" />
					</li>
					<li>
						<label>名称</label> 
						<input type="text" id="descr" name="descr" style="width:160px;" value="${baseAlgorithm.descr}"/>
					</li>
					<li>
						<input type="checkbox" id="expired" name="expired" value="${baseAlgorithm.expired}" 
							onclick="hideExpired(this)" ${baseAlgorithm.expired!='T' ?'checked':'' }/>
						<label for="expired" style="width: 50px;margin-left: -3px;text-align: left;">隐藏过期</label>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="BASEALGORITHM_ADD">
				<button type="button" class="btn btn-system" id="add">
					新增
				</button>
			</house:authorize>
			<house:authorize authCode="BASEALGORITHM_UPDATE">
				<button type="button" class="btn btn-system" id="update">
					编辑
				</button>
			</house:authorize>
			<house:authorize authCode="BASEALGORITHM_VIEW">
				<button type="button" class="btn btn-system" id="view" onclick="view()">
					查看
				</button>
			</house:authorize>
			<house:authorize authCode="BASEALGORITHM_EXCEL">
				<button type="button" class="btn btn-system" onclick="doExcel()">
					导出excel
				</button>
			</house:authorize>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/baseAlgorithm/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "code", index: "code", width: 50, label: "编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 160, label: "名称", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 300, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 80, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
		],
		ondblClickRow: function(){
			view();
		},
	});
	//新增
	$("#add").on("click",function(){
		Global.Dialog.showDialog("goSave",{
			title:"基础项目算法管理--增加",
			url:"${ctx}/admin/baseAlgorithm/goSave",
		    height:611,
		    width:741,
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
			title:"基础项目算法管理--编辑",
			url:"${ctx}/admin/baseAlgorithm/goUpdate",
			postData:{code:ret.code},
		    height:611,
		    width:741,
			returnFun:goto_query
		});
	});
	
});
//查看
function view(){
	var ret = selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条记录！",
			width: 200
		});
		return;
	}
	Global.Dialog.showDialog("goDetail",{
		title:"基础项目算法管理--查看",
		url:"${ctx}/admin/baseAlgorithm/goUpdate",
		postData:{code:ret.code,m_umState:"V"},
	    height:611,
	    width:741,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/baseAlgorithm/doExcel";
	doExcelAll(url);
}
</script>
</html>
