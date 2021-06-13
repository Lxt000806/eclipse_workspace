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
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
		var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/department2/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
			{name:'code',		index:'code',		width:60,	label:'编号',	sortable:true,align:"left",},
			{name:'desc1',		index:'desc1',		width:90,	label:'中文名',	sortable:true,align:"left",},
			{name:'department1descr',		index:'department1descr',		width:90,	label:'一级部门',	sortable:true,align:"left",},
			{name:'deptypedescr',		index:'deptypedescr',		width:90,	label:'部门类型',	sortable:true,align:"left",},
			{name:'plannum',		index:'plannum',		width:90,	label:'编制数',	sortable:true,align:"right",},
			{name:'lastupdate', index: 'lastupdate', width: 133, label: '最后修改时间', sortable: true, align: "left", formatter: formatTime},
			{name:'lastupdatedBy', index: 'lastupdatedBy', width: 115, label: '修改人', sortable: true, align: "left"},
			{name:'expired', index: 'expired', width: 102, label: '是否过期', sortable: true, align: "left"},
			{name:'actionlog', index: 'actionlog', width: 98, label: '操作', sortable: true, align: "left"},
			{name:'department1', index: 'department1', width: 98, label: '一级部门', sortable: true, align: "left",hidden:true}
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
					<input type="hidden" id="expired"  name="expired" value="${department2.expired }"/>
					<input type="hidden" id="department1"  name="department1" value="${department2.department1 }"/>
					<ul class="ul-form">
							<li>
								<label>编号</label>
								<input type="text" id="code" name="code"  value="${department2.code }"/>
							</li>
							<li>
								<label>中文名</label>
								<input type="text" id="desc1" name="desc1"  value="${department2.desc1 }"/>
							</li>
							<li><label>一级部门</label> <house:department1 id="department1"
									value="${department2.department1 }"></house:department1>
							</li>
							<li><label>部门类型</label> <house:xtdm id="depType"
									dictCode="DEPTYPE" value="${department2.depType }"></house:xtdm>
							</li>
							<li class="search-group">					
								<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${department2.expired }" onclick="hideExpired(this)" 
								 ${purchase.expired!='T'?'checked':'' } /><p>隐藏过期</p>
									<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
									<button type="button" class="btn btn-sm btn-system "
									onclick="clearForm();">清空</button></li>
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
