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
	<title>搜寻——采购单号</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjProgTemp/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'No',		index:'No',		width:90,	label:'模板编号',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'Descr',		index:'Descr',		width:250,	label:'模板名称',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'LastUpdate',		index:'LastUpdate',		width:140,	label:'最后修改时间',	sortable:true,align:"left",formatter: formatTime},
			{name:'LastUpdatedBy',		index:'LastUpdatedBy',		width:90,	label:'操作人员',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'Expired',		index:'Expired',		width:90,	label:'是否过期',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'ActionLog',		index:'ActionLog',		width:90,	label:'操作代码',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'Remarks',		index:'Remarks',		width:90,	label:'备注',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'CustType',			index:'CustType',		width:90,	label:'客户类型',	sortable:true,align:"left",cellattr: 'addCellAttr', hidden:true},
			{name:'custtypedescr',			index:'custtypedescr',		width:90,	label:'客户类型',	sortable:true,align:"left",cellattr: 'addCellAttr'},
			{name:'Type',			index:'Type',		width:90,	label:'类型',	sortable:true,align:"left",cellattr: 'addCellAttr', hidden:true},
			
			],
		  ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
	});


});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="expired"  name="expired" value="${prjProTemp.expired }"/>
					<ul class="ul-form">
							<li> 
							<label>模板编号</label>
							
								<input type="text" id="no" name="no"  value="${prjProTemp.no }"/>
							</li>
							<li> 
							<label>模板名称</label>
							
								<input type="text" id="descr" name="descr" value="${prjProTemp.descr }"/>
							</li>
							
						<li id="loadMore">
						<button type="button" class="btn btn-system btn-sm"
							onclick="goto_query();">查询</button>
						
					</li>
					</ul>
				</form>
			</div>
			
		
			<!--query-form-->
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
			</div>
	</body>	
</html>
