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
	<title>资源客户管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/itemConfirm/doExcel";
	doExcelAll(url);
}
$(function(){
	$("#businessMan").openComponent_employee({showValue:'${resrCust.businessMan}',showLabel:'${resrCust.businessManDescr}'});	
	$("#builderCode").openComponent_builder({showValue:'${resrCust.builderCode}',showLabel:'${resrCust.builderDescr}'});	

	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemConfirm/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'Code',	index:'Code',	width:75,	label:'编号',	sortable:true,align:"left" ,},
			{name:'Descr',	index:'Descr',	width:90,	label:'名称',	sortable:true,align:"left" ,},
			{name:'itemtype1descr', index: 'itemtype1descr', width: 75, label: '材料类型1', sortable: true, align: "left",},
			{name:'ItemTimeCode',	index:'ItemTimeCode',	width:90,	label:'确认节点编号',	sortable:true,align:"left" , hidden:true},
			{name:'itemtimedescr',	index:'itemtimedescr',	width:130,	label:'确认节点',	sortable:true,align:"left" ,},
			{name:'prjitemdescr',	index:'prjitemdescr',	width:150,	label:'有效需求施工节点',	sortable:true,align:"left" ,},
			{name:'AvgSendDay',	index:'AvgSendDay',	width:90,	label:'平均发货天数',	sortable:true,align:"right" ,},
			{name:'dispatchorderdescr',	index:'dispatchorderdescr',	width:70,	label:'调度下单',	sortable:true,align:"left" ,},
			{name:'LastUpdate',	index:'LastUpdate',	width:120,	label:'最后修改时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'LastUpdatedBy',	index:'LastUpdatedBy',	width:90,	label:'最后修改人',	sortable:true,align:"left" ,},
			{name:'ActionLog',	index:'ActionLog',	width:90,	label:'操作类型',	sortable:true,align:"left" ,},
			{name:'Expired',	index:'Expired',	width:90,	label:'是否过期',	sortable:true,align:"left" ,},
			
		],
	});
	
	$("#save").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"施工材料分类——新增",
			url:"${ctx}/admin/itemConfirm/goSave",
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("update",{
			title:"施工材料分类——编辑",
			url:"${ctx}/admin/itemConfirm/goUpdate",
			postData:{code:ret.Code,},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("view",{
			title:"施工材料分类——查看",
			url:"${ctx}/admin/itemConfirm/goView",
			postData:{code:ret.Code,},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	
});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${itemTypeConfirm.code }"/>
							</li>
							<li>
								<label>名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${itemTypeConfirm.descr }"/>
							</li>
							<li>
								<label>确认节点编号</label>
								<house:dict id="itemTimeCode" dictCode="" sql="select Code,Descr from tConfItemTime " 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${itemTypeConfirm.itemTimeCode }"></house:dict>	
							</li>
							<li class="search-group-shrink" >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<house:authorize authCode="ITEMCONFIRM_SAVE">
							<button type="button" class="btn btn-system " id="save"><span>新增</span> 
						</house:authorize>
						<house:authorize authCode="ITEMCONFIRM_UPDATE">
							<button type="button" class="btn btn-system "  id="update"><span>编辑</span> 
						</house:authorize>
						<house:authorize authCode="ITEMCONFIRM_VIEW">
							<button type="button" class="btn btn-system "  id="view"><span>查看</span> 
						</house:authorize>
						<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
								<span>导出excel</span>
						</button>	
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
	</body>	
</html>
