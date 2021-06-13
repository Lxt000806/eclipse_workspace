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
	<title>礼品出库记账新增</title>
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
	$("#appCzy").openComponent_employee();
	$("#custCode").openComponent_customer();
	$("#supplCode").openComponent_supplier();
	
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/giftCheckOut/goAppJqGrid",
		postData:{allDetail:'${giftCheckOut.allDetail}',whCode:'${giftCheckOut.whCode}'},
		multiselect: true,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "No", index: "No", width: 85, label: "领用单号", sortable: true, align: "left"},
			{name: "CustCode", index: "CustCode", width: 65, label: "客户编号", sortable: true, align: "left"},
			{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left",hidden:true},
			{name: "address", index: "address", width: 115, label: "楼盘", sortable: true, align: "left"},
			{name: "Type", index: "Type", width: 50, label: "类型", sortable: true, align: "left",hidden:true},
			{name: "typedescr", index: "typedescr", width: 65, label: "类型", sortable: true, align: "left"},
			{name: "Status", index: "Status", width: 60, label: "状态", sortable: true, align: "left",hidden:true},
			{name: "statusdescr", index: "statusdescr", width: 50, label: "状态", sortable: true, align: "left"},
			{name: "UseMan", index: "UseMan", width: 60, label: "使用人", sortable: true, align: "left",hidden:true},
			{name: "usedescr", index: "usedescr", width: 60, label: "使用人", sortable: true, align: "left"},
			{name: "Phone", index: "Phone", width: 90, label: "电话", sortable: true, align: "left"},
			{name: "itemsumcost", index: "itemsumcost", width: 60, label: "金额", sortable: true, align: "right"},
			{name: "ActNo", index: "ActNo", width: 60, label: "活动编号", sortable: true, align: "left",hidden:true},
			{name: "descr", index: "descr", width: 60, label: "活动名称", sortable: true, align: "left"},
			{name: "WHCode", index: "WHCode", width: 60, label: "仓库编号", sortable: true, align: "left"},
			{name: "SupplCode", index: "SupplCode", width: 60, label: "供应商编号", sortable: true, align: "left",hidden:true},
			{name: "suppldescr", index: "SupplCode", width: 70, label: "供应商名称", sortable: true, align: "left"},
			{name: "Date", index: "Date", width: 80, label: "申请日期", sortable: true, align: "left",formatter:formatDate},
			{name: "AppCZY", index: "AppCZY", width: 60, label: "申请人", sortable: true, align: "left",hidden:true},
			{name: "appdescr", index: "appdescr", width: 60, label: "申请人", sortable: true, align: "left"},
			{name: "SendCZY", index: "SendCZY", width: 60, label: "发货人", sortable: true, align: "left"},
			{name: "SendDate", index: "SendDate", width: 80, label: "发货日期", sortable: true, align: "left",formatter:formatDate},
			{name:'bm', index:'bm', width:80, label:'业务员', sortable:true ,align:"left",},
			{name:'dm', index:'dm', width:80, label:'设计师', sortable:true ,align:"left",},
			{name:'bd', index:'bd', width:80, label:'业务部', sortable:true ,align:"left",},
			{name:'dd', index:'dd', width:80, label:'设计部', sortable:true ,align:"left",},
			{name: "SendType", index: "SendType", width: 60, label: "发货类型", sortable: true, align: "left",hidden:true},
			{name: "sendtypedescr", index: "sendtypedescr", width: 60, label: "发货类型", sortable: true, align: "left"},
			{name: "IsCheckOut", index: "IsCheckOut", width: 60, label: "是否结算", sortable: true, align: "left",hidden:true},
			{name: "CheckOutNo", index: "CheckOutNo", width: 60, label: "结算单号", sortable: true, align: "left",hidden:true},
			{name: "CheckSeq", index: "CheckSeq", width: 60, label: "结算顺序号", sortable: true, align: "left",hidden:true},
			{name: "Remarks", index: "Remarks", width: 160, label: "备注", sortable: true, align: "left"},
			{name: "LastUpdate", index: "LastUpdate", width: 110, label: "最后修改时间", sortable: true, align: "left",formatter:formatTime},
			{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 60, label: "最后修改人员", sortable: true, align: "left"},
			
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
		//var oldNo = $.trim($("#oldNo").val());
		
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
					<input type="hidden" readonly="true" id="whCode" name="whCode" style="width:160px;" value="${giftCheckOut.whCode}"/>
						<ul class="ul-form">
							<li>
								<label>领用单号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${giftApp.no }"/>
							</li>
							<li>
								<label>申请人</label>
								<input type="text" id="appCzy" name="appCzy" style="width:160px;" value="${giftApp.appCzy }"/>
							</li>
							<li>
								<label>类型</label>
								<house:xtdmMulit id="type" dictCode="GIFTAPPTYPE" selectedValue="${giftApp.type}"></house:xtdmMulit>                     
							</li>
							<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${giftApp.custCode }"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${giftApp.address }"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='GIFTAPPSTATUS' and (CBM='Send' or CBM='Return')" selectedValue="Send,Return"></house:xtdmMulit>
							</li>
							<%-- <li>
								<label>申请时间从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>供应商</label>
								<input type="text" id="supplCode" name="supplCode" style="width:160px;" value="${giftApp.supplCode }"/>
							</li> --%>
							<li hidden="true">
								<label>已选单号</label>
								<input type="text" id="allDetail" name="allDetail" style="width:160px;" value="${giftCheckOut.allDetail}"/>
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
			<!--query-form-->
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
