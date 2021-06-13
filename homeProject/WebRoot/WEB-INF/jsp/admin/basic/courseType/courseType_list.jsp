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
	<title>课程类型管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${courseType.expired }"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>编号</label> 
						<input type="text" id="code" name="code" style="width:160px;" value="${courseType.code }"/>
					</li>
					<li>
						<label>课程类型名称</label> 
						<input type="text" id="descr" name="descr" style="width:160px;" value="${courseType.descr }"/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${courseType.expired }" onclick="hideExpired(this)" ${courseType.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<house:authorize authCode="COURSETYPE_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
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
	</div>
</body>

<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/courseType/goJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name:"code", index:"code", width:100, label:"编号", sortable:true, align:"left"},
			{name:"descr", index:"descr", width:100, label:"课程类型名称", sortable:true, align:"left"},
			{name : "lastupdate",index : "lastupdate",width : 150,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
      		{name : "lastupdatedby",index : "lastupdatedby",width : 100,label:"修改人",sortable : true,align : "left"},
     		{name : "expired",index : "expired",width : 100,label:"过期标志",sortable : true,align : "left"},
    		{name : "actionlog",index : "actionlog",width : 100,label:"操作日志",sortable : true,align : "left"}
		]
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("Save", {
			title : "课程类型管理——新增",
			url : "${ctx}/admin/courseType/goUpdate",
			height : 300,
			width : 625,
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
			title:"课程类型管理——编辑",
			url:"${ctx}/admin/courseType/goUpdate",
			postData:{
				m_umState:"M",
				code:$.trim(ret.code),
				descr:ret.descr,
				expired:ret.expired,
			},
			height:300,
			width:625,
			returnFun:goto_query
		});
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
	Global.Dialog.showDialog("update",{
		title:"课程类型管理——查看",
		url:"${ctx}/admin/courseType/goUpdate",
		postData:{
			m_umState:"V",
			code:$.trim(ret.code),
			descr:ret.descr,
			expired:ret.expired,
		},
		height:320,
		width:625,
		returnFun:goto_query
	});	
}

function doExcel(){
	var url = "${ctx}/admin/courseType/doExcel";
	doExcelAll(url);
}
</script>
</html>
