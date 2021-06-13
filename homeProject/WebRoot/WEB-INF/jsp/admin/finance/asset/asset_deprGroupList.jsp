<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>固定资产管理——计提折旧查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	$(function(){
		$("#department").openComponent_department();	

		/**初始化表格*/
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/asset/getDeprGroupListBySql",
			postData:{periodFrom:$("#periodFrom").val(),periodTo:$("#periodTo").val()},
			height:$(document).height()-$("#content-list").offset().top-102,
			styleUI: "Bootstrap", 
			colModel : [
			  {name : "assettypedescr",index : "assettypedescr",width : 75,label:"资产类型",sortable : true,align : "left",count:true},
			  {name : "deptdescr",index : "deptdescr",width : 75,label:"部门",sortable : true,align : "left"},
			  {name : "depramount",index : "depramount",width : 75,label:"本月折旧",sortable : true,align : "right"},
			],
		});
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBtn" onclick="closeWin(true)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>折旧月份</label>
						<input type="text" id="periodFrom" name="periodFrom" class="i-date" 
							style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"
							value="<fmt:formatDate value='${asset.dateFrom }' pattern='yyyyMM'/>"/>
					</li>
					<%--<li>
						<label>至</label>
						<input type="text" id="periodTo" name="periodTo" class="i-date" 
							style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"
							value="<fmt:formatDate value='${asset.dateFrom }' pattern='yyyyMM'/>"/>
					</li>
					--%><li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
