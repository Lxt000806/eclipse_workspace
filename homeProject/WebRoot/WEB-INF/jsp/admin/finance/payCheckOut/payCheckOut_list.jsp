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
	<title>收入记账列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<!-- 客户窗体组件导入 -->
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript"> 
	$(function(){
		// 单击客户查询按钮事件
		$("#custCode").openComponent_customer();

		var postData = $("#page_form").jsonForm();
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/payCheckOut/goJqGrid",
			postData:{status:"1,2,3" } ,
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 100, label: "记账单号", sortable: true, align: "left"},
				{name: "status", index: "status", width: 80, label: "账单状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "账单状态", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 70, label: "凭证号", sortable: true, align: "left",  },
				{name: "checkdate", index: "checkdate", width: 120, label: "记账日期", sortable: true, align: "left", formatter:formatTime },
				{name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left",  },
				{name: "appczydescr", index: "appczydescr", width: 80, label: "开单人", sortable: true, align: "left",  },
				{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left",formatter:formatTime },
				{name: "confirmczydescr", index: "confirmczydescr", width: 70, label: "审核人员", sortable: true, align: "left",  },
				{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left",formatter:formatTime},
				{name: "returnpayno", index: "returnpayno", width: 110, label: "工程退款单号", sortable: true, align: "left", },
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left",  formatter: formatTime },
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left" },
				{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left" },
			],
			ondblClickRow:function(){
				view();
			}
		});

		$("#save").on("click",function(){
			var selectedRow = selectDataTableRow('dataTable');
			Global.Dialog.showDialog("Save",{
				title:"收入记账管理——新增账单",
				url:"${ctx}/admin/payCheckOut/goSave",
				postData:{
					m_umState:"A"
				},
				height:700,
				width:1400,
				returnFun:goto_query
			});
		}); 

		$("#update").on("click",function(){
			var selectedRow = selectDataTableRow('dataTable');
			if($.trim(selectedRow.status) != "1"){
				art.dialog({
					content:'记账单状态为'+selectedRow.statusdescr+'，不允许进行编辑！',
				});
				return false;
			}
			Global.Dialog.showDialog("Update",{
				title:"收入记账管理——编辑",
				url:"${ctx}/admin/payCheckOut/goUpdate",
				postData:{
					no:selectedRow.no,m_umState:"M"
				},
				height:700,
				width:1400,
				returnFun:goto_query
			});
		});
		
		$("#check").on("click",function(){
			var selectedRow = selectDataTableRow('dataTable');
			if($.trim(selectedRow.status) != "1"){
				art.dialog({
					content:'记账单状态为'+selectedRow.statusdescr+'，不允许进行审核！',
				});
				return false;
			}
			Global.Dialog.showDialog("Confirm",{
				title:"收入记账管理——审核",
				url:"${ctx}/admin/payCheckOut/goUpdate",
				postData:{
					no:selectedRow.no,m_umState:"C"
				},
				height:700,
				width:1400,
				returnFun:goto_query
			});
		});
		
		$("#reCheck").on("click",function(){
			var selectedRow = selectDataTableRow('dataTable');
			if($.trim(selectedRow.status) != "2"){
				art.dialog({
					content:'记账单状态为'+selectedRow.statusdescr+'，不允许进行反审核！',
				});
				return false;
			}
			if(selectedRow.returnpayno != ""){
				art.dialog({
					content:'该记账单由工程退款管理['+selectedRow.returnpayno+']生成，不允许进行反审核！',
				});
				return false;
			}
			Global.Dialog.showDialog("reCheck",{
				title:"收入记账管理——反审核",
				url:"${ctx}/admin/payCheckOut/goUpdate",
				postData:{
					no:selectedRow.no,m_umState:"RC"
				},
				height:700,
				width:1400,
				returnFun:goto_query
			});
		}); 
	});

	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	function doExcel(){
		var url = "${ctx}/admin/payCheckOut/doExcel";
		doExcelAll(url);
	}
	function view(){
		var selectedRow = selectDataTableRow('dataTable');
		if(!selectedRow){
    		art.dialog({content: "请选择一条记录！",width: 200});
    		return false;
    	}
			
		Global.Dialog.showDialog("View",{
			title:"收入记账管理——查看",
			url:"${ctx}/admin/payCheckOut/goUpdate",
			postData:{
				no:selectedRow.no,m_umState:"V"
			},
			height:700,
			width:1400,
			returnFun:goto_query
		});
	}
	
	function doPrint(){
		var row = selectDataTableRow();
   		if(!row){
   			art.dialog({content: "请选择一条记录进行打印操作！"});
   			return false;
   		} 
       	Global.Print.showPrint("payCheckOut.jasper", {
			No: $.trim(row.no) ,
			Date: $.trim(row.date) ,
			LogoPath : "<%=basePath%>jasperlogo/",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
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
							<input type="text" id="no" name="no" style="width:160px;" value="${payCheckOut.no }"/>
						</li>
						<li>
							<label>状态</label>
							<house:xtdmMulit id="status" dictCode="WHChkOutStatus" selectedValue="1,2,3"></house:xtdmMulit>                     
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
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;" value="${payCheckOut.custCode}"/>
						</li>
						<li>
							<label>凭证号</label>
							<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${payCheckOut.documentno}"/>                                             
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
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
						</li>
					</ul>
			</form>
		</div>
		</div>
		<div class="clear_float"></div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<house:authorize authCode="PAYCHECKOUT_ADD">
							<button type="button" class="btn btn-system" id="save"><span>新增</span></a>
					</house:authorize>	
					<house:authorize authCode="PAYCHECKOUT_EDIT">
							<button type="button" class="btn btn-system" id="update"><span>编辑</span></a>
					</house:authorize>	
					<house:authorize authCode="PAYCHECKOUT_CONFIRM">							
							<button type="button" class="btn btn-system" id="check"><span>审核</span></a>
					</house:authorize>	
					<house:authorize authCode="PAYCHECKOUT_UNCONFIRM">
							<button type="button" class="btn btn-system" id="reCheck"><span>反审核</span></a>
					</house:authorize>	
					<house:authorize authCode="PAYCHECKOUT_VIEW">
							<button type="button" class="btn btn-system" id="view" onclick="view()"><span>查看</span></a>
					</house:authorize>
					<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
					<span>导出Excel</span></a>
					<button type="button" class="btn btn-system" onclick="doPrint()" >
					<span>打印</span></a>	
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
</body>	
</html>
