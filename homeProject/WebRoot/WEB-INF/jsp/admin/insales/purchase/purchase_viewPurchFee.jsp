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
	<title>采购费用查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	$("#no").openComponent_purchase();	

	Global.JqGrid.initJqGrid("dataTableDetail",{
		height:220,
		rowNum:10000000,
		colModel : [
			{name:'puno', index:'puno', width:80, label:'采购单号', sortable:true ,align:"left",hidden:true},
			{name:'no', index:'no', width:80, label:'采购单号', sortable:true ,align:"left",},
			{name:'pk', index:'pk', width:80, label:'pk', sortable:true ,align:"left",hidden:true},
			{name:'feetype', index:'feetype', width:80, label:'费用类型', sortable:true ,align:"left",hidden:true},
			{name:'feetypedescr', index:'feetypedescr', width:80, label:'费用类型', sortable:true ,align:"left",},
			{name:'amount', index:'amount', width:80, label:'费用', sortable:true ,align:"left",sum:true},
			{name:'remarks', index:'remarks', width:80, label:'备注', sortable:true ,align:"left",},
			{name:'lastupdate', index:'lastupdate', width:80, label:'最后修改时间', sortable:true ,align:"left",formatter:formatDate},
			{name:'lastupdatedby', index:'lastupdatedby', width:80, label:'最后修改人', sortable:true ,align:"left",},
			{name:'actionlog', index:'actionlog', width:80, label:'actionlog', sortable:true ,align:"left",hidden:true},
		],
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchase/getPurchFeeList",
		postData:{no:""},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		height:250,
		colModel : [
			{name:'no', index:'no', width:80, label:'费用单号', sortable:true ,align:"left",},
			{name:'puno', index:'puno', width:80, label:'采购单号', sortable:true ,align:"left",},
			{name:'statusdescr', index:'statusdescr', width:80, label:'状态', sortable:true ,align:"left",},
			{name:'whcode', index:'whcode', width:80, label:'柜号', sortable:true ,align:"left",},
			{name:'amount', index:'amount', width:80, label:'采购费', sortable:true ,align:"left",},
			{name:'billstatusdescr', index:'billstatusdescr', width:80, label:'发票状态', sortable:true ,align:"left",},
			{name:'arriveno', index:'arriveno', width:80, label:'到货单号', sortable:true ,align:"left",},
			{name:'lastupdate', index:'lastupdate', width:80, label:'最后修改时间', sortable:true ,align:"left",formatter:formatDate},
			{name:'lastupdatedby', index:'lastupdatedby', width:80, label:'最后修改人', sortable:true ,align:"left",},
		],
		onSelectRow : function(id) {
        	var row = selectDataTableRow("dataTable");
           	$("#dataTableDetail").jqGrid('setGridParam',{url : "${ctx}/admin/purchase/getPurchFeeDetail?no="+row.no});
           	$("#dataTableDetail").jqGrid().trigger('reloadGrid');
        }
	});
	
});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value="${purchase.expired }"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">	
							<li>
								<label>采购单号</label>
								<input type="text" id="no" name="no" style="width:160px;"/>
							</li>
							<li>
								<label>采购日期</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>状态</label>
								<house:xtdm  id="status" dictCode="PurchFeeStatus"   style="width:160px;"></house:xtdm>
							</li>
							<li class="search-group-shrink" >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>	
					</form>
				</div>
			</div>
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
				</div> 
				<div id="content-list">
					<table id="dataTableDetail"></table>
				</div>
			</div>
	</body>	
</html>
