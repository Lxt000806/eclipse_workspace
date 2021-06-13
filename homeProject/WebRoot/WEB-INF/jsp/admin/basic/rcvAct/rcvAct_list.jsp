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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>收款账户管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${rcvAct.expired }"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>账户编号</label> 
						<input type="text" id="code" name="code" style="width:160px;" value="${rcvAct.code }"/></li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${rcvAct.expired }" 
							onclick="hideExpired(this)" ${rcvAct.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
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
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<house:authorize authCode="RCVACT_VIEW">
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
	</div>
</body>

<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/rcvAct/goJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name:"Code", index:"Code", width:100, label:"账户编号", sortable:true, align:"left"},
			{name:"Descr", index:"Descr", width:100, label:"账户名称", sortable:true, align:"left"},
			{name:"CardID", index:"CardID", width:120, label:"收款账号", sortable:true, align:"left"},
			{name:"payeedescr", index:"payeedescr", width:80, label:"收款单位", sortable:true, align:"left"},
			{name:"BankCode", index:"BankCode", width:80, label:"银行", sortable:true, align:"left", hidden:true},
			{name:"bankdescr", index:"bankdescr", width:80, label:"银行", sortable:true, align:"left"},
			{name:"adminname", index:"adminname", width:80, label:"管理员", sortable:true, align:"left"},
			{name:"allowtransdescr", index:"allowtransdescr", width:80, label:"转账收款", sortable:true, align:"left"},
			{name : "LastUpdate",index : "LastUpdate",width : 150,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
      		{name : "LastUpdatedBy",index : "LastUpdatedBy",width : 100,label:"修改人",sortable : true,align : "left"},
     		{name : "Expired",index : "Expired",width : 100,label:"过期标志",sortable : true,align : "left"},
    		{name : "ActionLog",index : "ActionLog",width : 100,label:"操作日志",sortable : true,align : "left"}
		],
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("Save", {
			title : "收款账户管理——新增",
			url : "${ctx}/admin/rcvAct/goSave",
			height : 420,
			width : 450,
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
			title:"收款账户管理——编辑",
			url:"${ctx}/admin/rcvAct/goUpdate",
			postData:ret,
			height:420,
			width:450,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"收款账户管理——查看",
			url:"${ctx}/admin/rcvAct/goDetail",
			postData:ret,
			height:380,
			width:635,
			returnFun:goto_query
		});
	});
});

function doExcel(){
	var url = "${ctx}/admin/rcvAct/doExcel";
	doExcelAll(url);
}
</script>
</html>
