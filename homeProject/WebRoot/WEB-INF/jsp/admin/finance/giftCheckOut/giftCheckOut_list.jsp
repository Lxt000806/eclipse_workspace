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
	<title>出库记账列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	$("#whCode").openComponent_wareHouse();	

	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/giftCheckOut/goJqGrid",
		//postData:{ } ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "No", index: "No", width: 110, label: "记账单号", sortable: true, align: "left",  },
			{name: "Status", index: "Status", width: 70, label: "账单状态", sortable: true, align: "left",hidden:true},
			{name: "statusdescr", index: "statusdescr", width: 70, label: "账单状态", sortable: true, align: "left",  },
			{name: "DocumentNo", index: "DocumentNo", width: 70, label: "凭证号", sortable: true, align: "left",  },
			{name: "CheckDate", index: "CheckDate", width: 80, label: "记账日期", sortable: true, align: "left", formatter:formatDate },
			{name: "Remarks", index: "Remarks", width: 125, label: "备注", sortable: true, align: "left",  },
			{name: "WHCode", index: "WHCode", width: 70, label: "仓库编号", sortable: true, align: "left", hidden:true },
			{name: "whdescr", index: "whdescr", width: 70, label: "仓库", sortable: true, align: "left",  },
			{name: "AppCZY", index: "AppCZY", width: 70, label: "申请人员", sortable: true, align: "left", hidden:true},
			{name: "appczydescr", index: "appczydescr", width: 70, label: "开单人员", sortable: true, align: "left", },
			{name: "Date", index: "Date", width: 80, label: "开单时间", sortable: true, align: "left",  formatter: formatDate },
			{name: "ConfirmDate", index: "ConfirmDate", width: 80, label: "审核日期", sortable: true, align: "left",formatter:formatDate  },
			{name: "ConfirmCZY", index: "ConfirmCZY", width: 110, label: "审核人员", sortable: true, align: "left",hidden:true  },
			{name: "confirmczydescr", index: "confirmczydescr", width: 110, label: "审核人员", sortable: true, align: "left",  },
			{name: "LastUpdate", index: "LastUpdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime  },
			{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 80, label: "最后修改人", sortable: true, align: "left",  },
			{name: "ActionLog", index: "ActionLog", width: 75, label: "最后修改人", sortable: true, align: "left", hidden:true },
			
		],
	});

	$("#save").on("click",function(){
	
		var ret= selectDataTableRow('dataTable');
		Global.Dialog.showDialog("Save",{
			title:"礼品出库记账——新增",
			url:"${ctx}/admin/giftCheckOut/goSave",
			height:700,
			width:1150,
			returnFun:goto_query
		});
	}); 
		
	$("#update").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if($.trim(ret.Status)!='1'){
			art.dialog({
				content:'只有申请状态才可编辑',
			});
			return false;
		}
		Global.Dialog.showDialog("Update",{
			title:"礼品出库记账——编辑",
			url:"${ctx}/admin/giftCheckOut/goUpdate",
			postData:{no:ret.No},
			height:700,
			width:1150,
			returnFun:goto_query
		});
	});
		
	$("#view").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		Global.Dialog.showDialog("View",{
			title:"礼品出库记账——查看",
			url:"${ctx}/admin/giftCheckOut/goView",
			postData:{no:ret.No},
			height:700,
			width:1150,
			returnFun:goto_query
		});
	});
	
	$("#check").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if($.trim(ret.Status)!='1'){
			art.dialog({
				content:'只有申请状态才可审核',
			});
			return false;
		}
		Global.Dialog.showDialog("updateRealAddress",{
			title:"礼品出库记账——审核",
			url:"${ctx}/admin/giftCheckOut/goCheck",
			postData:{no:ret.No},
			height:700,
			width:1150,
			returnFun:goto_query
		});
	});
	 
	$("#reCheck").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if($.trim(ret.Status)!='2'){
			art.dialog({
				content:'只有审核状态才可反审核',
			});
			return false;
		}
		Global.Dialog.showDialog("reCheck",{
			title:"礼品出库记账——反审核",
			url:"${ctx}/admin/giftCheckOut/goReCheck",
			postData:{no:ret.No},
			height:700,
			width:1150,
			returnFun:goto_query
		});
	}); 
	
	
	$('#print1').on("click",function(){
		var ret=selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
				content:'请选择一条数据进行打印',
			});
			return false;
		}
	   	var reportName = "giftCheckOut.jasper";
	   	Global.Print.showPrint(reportName, {
			CheckOutNo:ret.No,
			
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
	});
	
	$("#print").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("print",{
			title:"礼品出库记账——打印",
			url:"${ctx}/admin/giftCheckOut/goPrint?no="+ret.No,
			height:250,
			width:500,
		});
	});
	
});


function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#openComponent_wareHouse_whCode").val('');
	$("#openComponent_customer_custCode").val('');
	$("#openComponent_employee_appCzy").val('');
	$("#page_form select").val('');
	$("#type_NAME").val('');
	$("#status_NAME").val('');
	$("#whCode_NAME").val('');
	$("#status").val('');
	$("#dateFrom").val('');
	$("#dateTo").val('');
	$("#no").val('');
	$("#whCode").val('');
	$("#address").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_type").checkAllNodes(false);
} 


</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>记账单号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${giftCheckOut.no }"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdmMulit id="status" dictCode="WHChkOutStatus" selectedValue="1,2,3"></house:xtdmMulit>                     
							</li>
							<li>
								<label>开单时间</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>仓库</label>
								<input type="text" id="whCode" name="whCode" style="width:160px;" value="${giftCheckOut.whCode }"/>
							</li>
							<li>
								<label>记账时间</label>
								<input type="text" id="checkDateFrom" name="checkDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="checkDateTo" name="checkDateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>凭证号</label>
								<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${giftCheckOut.documentNo }"  />                                             
							</li>	
							<li>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
				<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<house:authorize authCode="GIFTCHECKOUT_ADD">
								<button type="button" class="btn btn-system "  id="save"><span>新增</span></a>
						</house:authorize>	
						<house:authorize authCode="GIFTCHECKOUT_EDIT">
								<button type="button" class="btn btn-system "  id="update"><span>编辑</span></a>
						</house:authorize>	
						<house:authorize authCode="GIFTCHECKOUT_CONFIRM">							
								<button type="button" class="btn btn-system "  id="check"><span>审核</span></a>
						</house:authorize>	
						<house:authorize authCode="GIFTCHECKOUT_UNCONFIRM">
								<button type="button" class="btn btn-system "  id="reCheck"><span>反审核</span></a>
						</house:authorize>	
						<house:authorize authCode="GIFTCHECKOUT_VIEW">
								<button type="button" class="btn btn-system "  id="view"><span>查看</span></a>
						</house:authorize>	
						<house:authorize authCode="GIFTCHECKOUT_PRINT">						
								<button type="button" class="btn btn-system "  id="print"><span>打印</span></a>
						</house:authorize>	
						<house:authorize authCode="GIFTCHECKOUT_EXCEL">
								<button type="button" class="btn btn-system "  onclick="doExcelNow('礼品出库记账表')" title="导出当前excel数据" >
								<span>导出Excel</span></a>
						</house:authorize>	
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
