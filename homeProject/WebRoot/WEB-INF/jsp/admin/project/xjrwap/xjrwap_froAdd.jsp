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
	<title>巡检楼盘前端导入</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#appCZY").openComponent_employee();
	
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/xjrwap/goFroAddJqGrid",
		postData:{arr:'${arr}'},
		multiselect: true,
		rowNum : 1000000,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "custcode", index: "custcode", width: 185, label: "客户编号", sortable: true, align: "left" ,hidden:true},
			{name: "address", index: "address", width: 185, label: "楼盘", sortable: true, align: "left",count:true},
			{name: "projectmandescr", index: "projectmandescr", width: 107, label: "项目经理", sortable: true, align: "left"},
			{name: "appczydescr", index: "appczydescr", width: 89, label: "申请人", sortable: true, align: "left"},
			{name: "appdate", index: "appdate", width: 91, label: "申请时间", sortable: true, align: "left", formatter: formatTime},
			{name: "remarks", index: "remarks", width: 262, label: "说明", sortable: true, align: "left"},
			{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden:true}
		],
	});
	//全选
	$("#selAll").on("click",function(){
		Global.JqGrid.jqGridSelectAll("dataTable",true);
	});
	//全不选
	$("#selNone").on("click",function(){
		Global.JqGrid.jqGridSelectAll("dataTable",false);
	});
	//保存	
	$("#saveBtn").on("click",function(){
     	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
     	if(ids.length==0){
     		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
     		return;
     	}
     	var selectRows = [];
 		$.each(ids,function(k,id){
 			var row = $("#dataTable").jqGrid('getRowData', id);
 			selectRows.push(row);
 		});
 		Global.Dialog.returnData = selectRows;
 		closeWin();
	  });
});
 function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#openComponent_supplier_supplCode").val('');
	$("#openComponent_customer_custCode").val('');
	$("#openComponent_employee_appCzy").val('');
	$("#page_form select").val('');
	$("#type_NAME").val('');
	$("#status_NAME").val('');
	$("#status").val('');
	$("#dateFrom").val('');
	$("#dateTo").val('');
	$("#no").val('');
	$("#supplier").val('');
	$("#address").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_type").checkAllNodes(false);
} 

</script>
</head>
	<body>
		<div class="body-box-form">
				<div class="panel panel-system" >
   					<div class="panel-body" >
      					<div class="btn-group-xs" >
							<button type="button" class="btn btn-system " id="saveBtn">
								<span>保存</span>
							</button>
							<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
								<span>关闭</span>
							</button>
						</div>
					</div>	
				</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
			 		 <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<input type="hidden" name="jsonString" value="" />
						<input type="hidden" id="arr" name="arr" value="${arr}" />
						<ul class="ul-form">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${progCheckPlan.address }"/>
							</li>
							<li>
								<label>申请人</label>
								<input type="text" id="appCZY" name="appCZY" style="width:160px;" value="${progCheckPlan.appCZY }"/>
							</li>
							<li>
								<label>申请日期</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
			</div> 
		</div>
	</body>	
</html>
