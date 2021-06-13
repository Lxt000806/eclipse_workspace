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
	<title>工作日期安排</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
		    <form class="form-search" id="page_form" action="" method="post" target="targetFrame">
		    	<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="expired" name="expired" value=""/>
				<ul class="ul-form">
					<li>
						<label>时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>到</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button id="clear" type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="WORKINGDATE_HOLISET">
				<button type="button" class="btn btn-system" id="holiSet">
					<span>节假日设置</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKINGDATE_EDIT">
				<button type="button" class="btn btn-system" id="edit">
					<span>编辑</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKINGDATE_VIEW">
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
		  	</house:authorize>
		  	<house:authorize authCode="WORKINGDATE_EXCEL">
				<button type="button" class="btn btn-system" id="excel">
					<span>导出excel</span>
				</button>
		  	</house:authorize>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div> 
	<script>
		$(function(){
			var postData = $("#page_form").jsonForm();
			var gridOption = {
				url: "${ctx}/admin/workingDate/goJqGrid",
				postData: postData,
				height: $(document).height()-$("#content-list").offset().top-70,
				styleUI: "Bootstrap", 
				colModel: [
					{name: "date", index: "date", width: 120, label: "日期", sortable: true, align: "left", formatter: formatTime},
					{name: "holitype", index: "holitype", width: 80, label: "节假日类型", sortable: true, align: "left", hidden: true},
					{name: "holitypedescr", index: "holitypedescr", width: 80, label: "节假日类型", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left"},
				],
				ondblClickRow: function(){
					view();
				}
			};
			
			Global.JqGrid.initJqGrid("dataTable",gridOption);

			$("#edit").on("click",function(){
				ret=selectDataTableRow();
				if(!ret){
					art.dialog({
		       			content: "请选择一条记录",
		       		});
		       		return;
				}
				Global.Dialog.showDialog("edit",{
					title:"工作日期安排——编辑",
					url:"${ctx}/admin/workingDate/goEdit",
					postData:{
						m_umState:"M",
						date:ret.date,
						holiType:ret.holitype
					},
					height:216,
					width:455,
					returnFun:goto_query
				});
			});
			
			$("#holiSet").on("click",function(){
				ret=selectDataTableRow();
				if(!ret){
					art.dialog({
		       			content: "请选择一条记录",
		       		});
		       		return;
				}
				Global.Dialog.showDialog("holiSet",{
					title:"工作日期安排——节假日设置",
					url:"${ctx}/admin/workingDate/goHoliSet",
					postData:{
						date:ret.date,
						holiType:ret.holitype
					},
					height:567,
					width:881,
					returnFun:goto_query
				});
			});

			$("#view").on("click", function () {
				view();
			});

			$("#excel").on("click", function () {
				doExcel();
			});
		});

		function view(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
		  			content: "请选择一条记录",
		  		});
		  		return;
			}
			Global.Dialog.showDialog("view",{
				title:"工作日期安排——查看",
				url:"${ctx}/admin/workingDate/goView",
				postData:{
					m_umState:"V",
					date:ret.date,
					holiType:ret.holitype
				},
				height:216,
				width:455,
				// returnFun:goto_query
			});
		}

		function doExcel(){
			var url = "${ctx}/admin/workingDate/doExcel";
			doExcelAll(url);
		}
	</script>
</body>	
</html>
