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
	<title>采购申请跟踪</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
var itemtype1;
function doExcel(){
	var url = "${ctx}/admin/purchaseApp/doConExcel";
	doExcelAll(url);
}	
$(function(){
	$("#appCZY").openComponent_employee();	

	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchaseApp/goPurchConJqGrid",
		postData:{status:"${purchase.status}"},
		height:430,
		colModel : [
			{name:"no",	index:"no",	width:90,label:"采购申请单号", sortable:true,align:"left"},
			{name:"status", index:"status",	width:80,label:"申请状态",sortable:true,align:"left",hidden:true},
			{name:"statusdescr", statusdescr:"status",width:80,label:"申请状态",sortable:true,align:"left",},
			{name:"itemtype1", index:"itemtype1",width:80,label:"材料类型1",sortable:true,align:"left",hidden:true},
			{name:"itemtype1descr", index:"itemtype1descr",	width:80,label:"材料类型1",sortable:true,align:"left",},
			{name:"appdate", index:"appdate",width:80,label:"申请日期",sortable:true,align:"left",formatter:formatDate},
			{name:"appczy", index:"appczy",	width:80,label:"申请人",sortable:true,align:"left",hidden:true},
			{name:"appczydescr", index:"appczydescr",width:80,label:"申请人",sortable:true,align:"left",},
			{name:"appconfirmdate", index:"appconfirmdate",	width:80,label:"申请审核日期",sortable:true,align:"left",formatter:formatDate},
			{name:"appconfirmczy", index:"appconfirmczy",width:80,	label:"申请审核人",sortable:true,align:"left",},
			{name:"appremark", 	index:"appremark",width:80,	label:"申请备注",sortable:true,align:"left",},
			{name:"puno", index:"puno",width:80,label:"采购订单编号",sortable:true,align:"left",},
			{name:"pustatus", index:"pustatus",	width:80,label:"订单状态",sortable:true,align:"left",},
			{name:"purchaseremark", 	index:"purchaseremark",width:80,	label:"订单备注",sortable:true,align:"left",},
			{name:"puappdate", index:"puappdate",width:80,label:"订单申请日期",sortable:true,align:"left",formatter:formatDate},
			{name:"preconfirmdate", index:"preconfirmdate",width:80,label:"预付款日期",sortable:true,align:"left",formatter:formatDate},
			{name:"puarrivedate", index:"puarrivedate",width:80, label:"预计到货日期",sortable:true,align:"left",formatter:formatDate},
			{name:"puconfirmdate", index:"puconfirmdate", width:80, label:"实际到货日期",sortable:true,align:"left",formatter:formatDate},
		],
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
							<label>采购申请单号</label>
							<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }"/>
						</li>
						<li>
							<label>申请时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${purchaseApp.dateFrom}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${purchaseApp.dateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>采购申请状态</label>
							<house:xtdm id="status" dictCode="PURAPPSTAT"></house:xtdm>                     
						</li>	
						<li>
							<label>申请人</label>
							<input type="text" id="appCZY" name="appCZY" style="width:160px;"/> 
						</li>
						<li>
							<label>采购单状态</label>
							<house:xtdm id="puStatus" dictCode="PURCHSTATUS"></house:xtdm>                     
						</li>
						<li class="search-group-shrink" >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							<button type="button" class="btn btn-sm btn-system  "  onclick="doExcel()" title="导出检索条件数据">导出Excel</button>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>	
</html>
