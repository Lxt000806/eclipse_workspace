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
    <title>人工费用管理——明细查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/laborFee/doLaborDetailExcel";
		doExcelAll(url);
	}
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	/**初始化表格*/
	$(function(){

		Global.LinkSelect.initSelect("${ctx}/admin/laborFee/laborFeeFeeType","itemType1","feeType");

		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/laborFee/goLaborDetailJqGrid",
			postData:{status:'1,2,3'},
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			sortable: true,
			colModel : [
				{name: "pk", index: "pk", width: 56, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "no", index: "no", width: 88, label: "单号", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 78, label: "材料类型1", sortable: true, align: "left"},
				{name: "address", index: "address", width: 149, label: "楼盘", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 96, label: "客户编号", sortable: true, align: "left"},
				{name: "customerdocumentno", index: "customerdocumentno", width: 81, label: "档案号", sortable: true, align: "left"},
				{name: "refaddress", index: "refaddress", width: 140, label: "关联楼盘", sortable: true, align: "left"},
				{name: "appczydecr", index: "appczydecr", width: 77, label: "申请人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 140, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "statusdescr", index: "statusdescr", width: 79, label: "状态", sortable: true, align: "left"},
				{name: "appsendno", index: "appsendno", width: 103, label: "送货单号", sortable: true, align: "left"},
				{name: "feetypedescr", index: "feetypedescr", width: 137, label: "费用类型", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 89, label: "金额", sortable: true, align: "right", sum: true},
				{name: "laborfeedetailremarks", index: "laborfeedetailremarks", width: 170, label: "备注", sortable: true, align: "left"},
				{name: "actname", index: "actname", width: 80, label: "账户名", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 160, label: "卡号", sortable: true, align: "left"},
				{name: "detailactname", index: "detailactname", width: 70, label: "账户名", sortable: true, align: "left"},
				{name: "detailcardid", index: "detailcardid", width: 137, label: "卡号", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 93, label: "凭证号", sortable: true, align: "left"},
				{name: "confirmczydecr", index: "confirmczydecr", width: 69, label: "审核人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "confirmdate", index: "confirmdate", width: 95, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "paydate", index: "paydate", width: 114, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
				{name: "shremarks", index: "shremarks", width: 196, label: "审核说明", sortable: true, align: "left"}
			],
		});
	});
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#status").val('');
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>单号</label>
						<input type="text" id="no" name="no" style="width:160px;" value="${laborFee.no }"/>
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" style="width: 160px;"></select> 
					</li>
					<li>
						<label>费用类型</label>
						<select id="feeType" name="feeType" style="width: 160px;"></select> 
					</li>
					<li>
						<label>户名</label>
						<input type="text" id="actName" name="actName" style="width:160px;" value="${laborFee.actName }"/>
					</li>
					<li>
						<label>申请时间从 </label>
						<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>审核时间从 </label>
						<input type="text" style="width:160px;" id="confirmDateFrom" name="confirmDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" style="width:160px;" id="confirmDateTo" name="confirmDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>付款时间从 </label>
						<input type="text" style="width:160px;" id="payDateFrom" name="payDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" style="width:160px;" id="payDateTo" name="payDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>状态</label>
						<house:xtdmMulit id="status" dictCode="LABORFEESTATUS" selectedValue="1,2,3"></house:xtdmMulit>    
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${laborFee.address }"/>
					</li>
					<li>
						<label>档案号</label>
						<input type="text" id="custDocumentNo" name="custDocumentNo"/>
					</li>
					<li>
						<label>备注</label>
						<input type="text" id="detailRemarks" name="detailRemarks" style="width:160px;" value="${laborFee.detailRemarks }"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						<button type="button" class="btn btn-sm btn-system" onclick="doExcel()" title="导出检索条件数据">导出Excel</button>
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
