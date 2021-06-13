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
	<title>基础增减明细查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/baseItemChg/doDetailExcel";
	doExcelAll(url);
}	
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/baseItemType1/baseItemType","baseItemChgType1","baseItemChgType2");
		$("#custCode").openComponent_customer();
		$("#code").openComponent_baseItem();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/baseItemChg/goDetailJqGrid",
		postData:{status:'${purchase.status}'},
		height:450,
		colModel : [
			{name:'no',	index:'no',	width:80,label:'增减单号',	sortable:true,align:"left"},
			{name:'baseitemtype1descr',	index:'baseitemtype1descr',	width:80,label:'基装类型1',	sortable:true,align:"left"},
			{name:'custcode',	index:'custcode',	width:80,label:'客户编号',	sortable:true,align:"left"},
			{name:'custdescr',	index:'custdescr',	width:80,label:'客户名称',	sortable:true,align:"left"},
			{name:'address',	index:'address',	width:220,label:'楼盘地址',	sortable:true,align:"left"},
			{name:'statusdescr',	index:'statusdescr',	width:80,label:'基装增减状态',	sortable:true,align:"left"},
			{name:'confirmdate',	index:'confirmdate',	width:80,label:'审核时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'fixareadescr',	index:'fixareadescr',	width:80,label:'区域名称',	sortable:true,align:"left"},
			{name:'baseitemcode',	index:'baseitemcode',	width:80,label:'基装项目编号',	sortable:true,align:"left"},
			{name:'baseitemdescr',	index:'baseitemdescr',	width:120,label:'基装项目名称',	sortable:true,align:"left"},
			{name:'reqqty',	index:'reqqty',	width:80,label:'需求数量',	sortable:true,align:"right"},
			{name:'chgqty',	index:'chgqty',	width:80,label:'变更数量',	sortable:true,align:"right"},
			{name:'uomdescr',	index:'uomdescr',	width:80,label:'单位',	sortable:true,align:"left"},
			{name:'offerpri',	index:'offerpri',	width:80,label:'人工单价',	sortable:true,align:"right"},
			{name:'materialprice',	index:'materialprice',	width:80,label:'材料单价',	sortable:true,align:"right"},
			{name:'amount',	index:'amount',	width:80,label:'总价',	sortable:true,align:"right"},
			{name:'isoutsetdescr',	index:'isoutsetdescr',	width:80,label:'是否套餐外',	sortable:true,align:"left"},
			{name:'remarks',	index:'remarks',	width:280,label:'备注',	sortable:true,align:"left"},
			{name: 'baseitemsetno', index: 'baseitemsetno', width: 68, label: '套餐包', sortable: true, align: 'left'},
            {name: 'ismainitem', index: 'ismainitem', width: 68, label: '主项目', sortable: true, align: 'left', hidden: true},
            {name: 'ismainitemdescr', index: 'ismainitemdescr', width: 68, label: '主项目', sortable: true, align: 'left'},
			{name:'lastupdatedby',	index:'lastupdatedby',	width:80,label:'最后修改人',	sortable:true,align:"left"},
			{name:'lastupdate',	index:'lastupdate',	width:80,label:'最后修改日期',	sortable:true,align:"left",formatter: formatDate},
			{name:'actionlog',	index:'actionlog',	width:80,label:'操作',	sortable:true,align:"left"},
		],
	});
	
});
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#openComponent_customer_custCode").val('');
	$("#openComponent_baseItem_code").val('');
	$("#status").val('');
	$("#code").val('');
	$("#custCode").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
} 
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
						<button type="button" class="btn btn-system"  onclick="doExcel()" title="导出检索条件数据">导出Excel</button>
						
					</div>
				</div>
			</div>
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value="${purchase.expired }"/>
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">	
						<li>
							<label>增减单号</label>
							<input type="text" id="no" name="no" style="width:160px;" />
						</li>
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;" />
						</li>
						<li>
							<label>楼盘地址</label>
							<input type="text" id="address" name="address" style="width:160px;" />
						</li>
						<li>
							<label>基装类型1</label> 
							<select type="text" id="baseItemChgType1" name="baseItemChgType1" ></select>
						</li>
						<li>
							<label>基装类型2</label>
							<select type="text" id="baseItemChgType2" name="baseItemChgType2"></select>
						</li>
						<li>
							<label>基装项目编号</label>
							<input type="text" id="code" name="code" style="width:160px;" />
						</li>
						<li>
							<label>审核日期</label>
							<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${baseItemChg.confirmDateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>	
							<label>至</label>
							<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${baseItemChg.confirmDateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>增减单状态</label>
							<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='ITEMCHGSTATUS'" 
							selectedValue="${baseItemChg.status}"></house:xtdmMulit>
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
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>	
</html>
