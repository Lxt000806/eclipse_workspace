<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>报表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_softPerf.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/prjJobManage/doSupplListExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function(){
    	Global.LinkSelect.initSelect("${ctx}/admin/prjJobManage/prjTypeByItemType1Auth", "itemType1", "jobType");
	
		$("#appCzy").openComponent_employee({showValue:"${uc.czybh}",showLabel:"${uc.zwxm}"});	
		$("#supplCode").openComponent_supplier();	
		
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/prjJobManage/goSupplListJqGrid",
			postData:{jobType:"0,1",appCZY:"${uc.czybh}"},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "prjjobno", index: "prjjobno", width: 75, label: "任务单号", sortable: true, align: "left"},
				{name: "item1descr", index: "item1descr", width: 75, label: "材料类型1", sortable: true, align: "left"},
				{name: "address", index: "address", width: 118, label: "楼盘", sortable: true, align: "left"},
				{name: "jobtypedescr", index: "jobtypedescr", width: 70, label: "任务类型", sortable: true, align: "left"},
				{name: "appdate", index: "appdate", width: 110, label: "申请日期", sortable: true, align: "left",formatter: formatDate},
				{name: "namechi", index: "namechi", width: 110, label: "申请人", sortable: true, align: "left"},
				{name: "phone", index: "phone", width: 90, label: "电话", sortable: true, align: "left"},
				{name: "desc2", index: "desc2", width: 90, label: "工程部", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 60, label: "状态", sortable: true, align: "left"},
				{name: "suppldescr", index: "suppldescr", width: 80, label: "供应商", sortable: true, align: "left",},
				{name: "date", index: "date", width: 90, label: "指派日期", sortable: true, align: "left",formatter: formatDate},
				{name: "recvdate", index: "recvdate", width: 90, label: "接收日期", sortable: true, align: "left",formatter: formatDate},
				{name: "plandate", index: "plandate", width: 90, label: "计划处理时间", sortable: true, align: "left",formatter: formatDate},
				{name: "completedate", index: "completedate", width: 90, label: "完成时间", sortable: true, align: "left",formatter: formatDate},
				{name: "jobremarks", index: "jobremarks", width: 218, label: "任务备注", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 218, label: "指派备注", sortable: true, align: "left"},
				{name: "supplremarks", index: "supplremarks", width: 218, label: "供应商备注", sortable: true, align: "left"},
			],
		});
		
		window.goto_query = function(){
			$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/prjJobManage/goSupplListJqGrid",}).trigger("reloadGrid");
		}
	});
	
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#openComponent_employee_appCzy").val('');
		$("#openComponent_supplier_supplCode").val('');
		$("#status").val('');
		$("#supplCode").val('');
		$("#appCzy").val('');
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>材料类型1</label>
						<select type="text" style="width:160px;" id="itemType1" name="itemType1" ></select>
					</li>
					<li>
						<label>任务类型</label>
						<select type="text" style="width:160px;" id="jobType" name="jobType"></select>
					</li>
					<li>
						<label>指派人</label>
						<input type="text" style="width:160px;" id="appCzy" name="appCzy" value="${uc.czybh }"/>
					</li>
					<li>
						<label>状态</label>
						<house:xtdmMulit id="status" dictCode="SUPPLJOBSTS" selectedValue="0,1"></house:xtdmMulit>                     
					</li>
					<li>
						<label>指派时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" style="width:160px;" id="address" name="address"/>
					</li>
					<li>
						<label>供应商</label>
						<input type="text" style="width:160px;" id="supplCode" name="supplCode"/>
					</li>
					<li>
						<label>接收时间从</label>
						<input type="text" id="recvDateFrom" name="recvDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="recvDateTo" name="recvDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>完成时间从</label>
						<input type="text" id="comfirmDateFrom" name="comfirmDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="comfirmDateTo" name="comfirmDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>任务单号</label>
						<input type="text" style="width:160px;" id="prjJobNo" name="prjJobNo"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
