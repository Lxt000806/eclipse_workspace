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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>供应商促销管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${supplProm.expired}" />
				<input type="hidden" name="jsonString" value=""/><%-- 导出EXCEL用 --%>
				<ul class="ul-form">
					<li>
						<label for="supplCode">供应商编码</label>
						<input type="text" id="supplCode" name="supplCode"/>
					</li>
					<li>
						<label for="descr">活动名称</label>
						<input type="text" id="descr" name="descr"/>
					</li>
					<li>
						<label>促销时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${supplProm.expired }" 
							onclick="hideExpired(this)" ${supplProm.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system" id="add">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
                	<house:authorize authCode="SUPPLPROM_VIEW">
	                	<button type="button" class="btn btn-system" id="view">
							<span>查看</span>
						</button>
					</house:authorize>
					<button type="button" class="btn btn-system" id="excel">
						<span>输出到Excel</span>
					</button>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
	<script type="text/javascript">
		function view(){
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("view",{
					title:"供应商促销管理——查看",
					url:"${ctx}/admin/supplProm/goView",
					postData: {m_umState: "V", no: ret.no, expired: "T"},
					height: 691,
					width: 968,
				});
			} else {
				art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		/**初始化表格*/
		$(function(){
			$("#supplCode").openComponent_supplier();
	        //初始化表格
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/supplProm/goJqGrid",
				height:$(document).height()-$("#content-list").offset().top-70,
				postData: $("#page_form").jsonForm(),
				colModel : [
					{name: "no",index: "no",width: 60,label:"编号",sortable: true,align: "left"},
					{name: "descr",index: "descr",width: 80,label:"活动名称",sortable: true,align: "left"},
					{name: "supplcode",index: "supplcode",width: 90,label:"供应商编码",sortable: true,align: "left"},
					{name: "suppldescr",index: "suppldescr",width: 80,label:"供应商",sortable: true,align: "left"},
					{name: "begindate",index: "begindate",width: 100,label:"开始时间",sortable: true,align: "left",formatter: formatDate},
					{name: "enddate",index: "enddate",width: 100,label:"结束时间",sortable: true,align: "left",formatter: formatDate},
					{name: "remarks",index: "remarks",width: 180,label:"备注",sortable: true,align: "left"},
					{name: "lastupdate",index: "lastupdate",width: 120,label:"最后修改时间",sortable: true,align: "left",formatter: formatTime},
					{name: "lastupdatedby",index: "lastupdatedby",width: 70,label:"修改人",sortable: true,align: "left"},
					{name: "expired",index: "expired",width: 80,label:"过期标志",sortable: true,align: "left"},
					{name: "actionlog",index: "actionlog",width: 80,label:"操作日志",sortable: true,align: "left"}
				],
	            ondblClickRow: function () {
	            	view()
	            }
			});
			$("#view").on("click", function () {
				view();
			});
			$("#excel").on("click", function () {
				var url = "${ctx}/admin/supplProm/doExcel";
				doExcelAll(url);
			});
			$("#add").on("click", function (){
				Global.Dialog.showDialog("add",{
					title: "供应商促销管理——新增",
					url: "${ctx}/admin/supplProm/goAdd",
					postData: {m_umState: "A"},
					height: 691,
					width: 968,
					returnFun: goto_query
				});
			});
			$("#update").on("click", function (){
				var ret = selectDataTableRow();
				if (ret) {
					Global.Dialog.showDialog("update",{
						title: "供应商促销管理——编辑",
						url: "${ctx}/admin/supplProm/goUpdate",
						postData: {m_umState: "M", no: ret.no, expired: "T"},
						height: 691,
						width: 968,
						returnFun: goto_query
					});
				} else {
					art.dialog({
						content: "请选择一条记录"
					});
				}
			});
		});
	</script>
</body>
</html>
