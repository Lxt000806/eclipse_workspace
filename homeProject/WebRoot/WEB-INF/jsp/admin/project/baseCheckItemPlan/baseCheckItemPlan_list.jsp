<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>ItemPlan列表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#custStatus").val('');
	$.fn.zTree.getZTreeObj("tree_custStatus").checkAllNodes(false);
	$("#custType").val('');
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
}
function detail(){
	var ret = selectDataTableRow();
  	if (ret) {
		Global.Dialog.showDialog("detail",{
			title:"结算报价",
		  	url:"${ctx}/admin/baseCheckItemPlan/goDetail?code="+ret.code,
		  	height:window.screen.height-130,
		 	width:window.screen.width-40,
		 	returnFun: goto_query
		});
	}else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function view(){
	var ret = selectDataTableRow();
  	if (ret) {
		Global.Dialog.showDialog("detail",{
			title:"结算报价",
		  	url:"${ctx}/admin/baseCheckItemPlan/goDetail?code="+ret.code+"&m_umState=V",
		  	height:window.screen.height-130,
		 	width:window.screen.width-40,
		 	returnFun: goto_query
		});
	}else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
/**初始化表格*/
$(function(){
	var postData = $("#page_form").jsonForm();
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/baseCheckItemPlan/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-100,
		postData:{
		    custStatus: "4",
		    custType: "${baseCheckItemPlan.custType }",
		    dppStatus: $("#dppStatus").val()
		},
		colModel : [
		    {name: "code", index: "code", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 70, label: "客户名称", sortable: true, align: "left"},
			{name: "address", index: "address", width: 148, label: "楼盘", sortable: true, align: "left"},
			{name: "area", index: "area", width: 55, label: "面积", sortable: true, align: "right"},
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "custstatusdescr", index: "custstatusdescr", width: 70, label: "客户状态", sortable: true, align: "left"},
			{name: "layoutdescr", index: "layoutdescr", width: 55, label: "户型", sortable: true, align: "left"},
			{name: "dppstatusdescr", index: "dppstatusdescr", width: 90, label: "图纸审核状态", sortable: true, align: "left"},
			{name: "confirmbegin", index: "confirmbegin", width: 90, label: "实际开工日期", sortable: true, align: "left", formatter: formatDate},
			{name: "designmandescr", index: "designmandescr", width: 80, label: "设计师", sortable: true, align: "left"},
			{name: "businessmandescr", index: "businessmandescr", width: 80, label: "业务员", sortable: true, align: "left"},
			{name: "projectmandescr", index: "projectmandescr", width: 80, label: "项目经理", sortable: true, align: "left"},
			{name: "projectdept", index: "projectdept", width: 80, label: "工程部", sortable: true, align: "left"},
			{name: "offerpri", index: "offerpri", width: 80, label: "人工总价", sortable: true, align: "right"},
			{name: "material", index: "material", width: 80, label: "材料总价", sortable: true, align: "right"},
			{name: "prjofferpri", index: "prjofferpri", width: 120, label: "项目经理人工总价", sortable: true, align: "right"},
			{name: "prjmaterial", index: "prjmaterial", width: 120, label: "项目经理材料总价", sortable: true, align: "right"},
			{name: "iswateritemctrldescr", index: "iswateritemctrldescr", width: 120, label: "水电发包工人", sortable: true, align: "left"},
			{name: "iswaterctrldescr", index: "iswaterctrldescr", width: 120, label: "防水发包工人", sortable: true, align: "left"},
			{name: "checkplandate", index: "checkplandate", width: 120, label: "结算预算日期", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
           ],
	});
	$("#custCode").openComponent_customer();
});
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/baseCheckItemPlan/doExcel";
	doExcelAll(url);
}
</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="" /> 
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>客户编号</label> <input type="text" id="custCode"
						name="custCode" value="${baseCheckItemPlan.custCode}" />
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" value="${baseCheckItemPlan.address}" />
					</li>
					<li><label>客户状态</label> <house:xtdmMulit id="custStatus"
							dictCode="CUSTOMERSTATUS" selectedValue="4" unShowValue="1,2,3"></house:xtdmMulit>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  selectedValue="${baseCheckItemPlan.custType}"></house:custTypeMulit>
					</li>
					<li><label>签订日期从</label> <input type="text" id="signDateFrom"
						name="signDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${baseCheckItemPlan.signDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="signDateTo"
						name="signDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${baseCheckItemPlan.signDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>开工日期从</label> <input type="text"
						id="confirmBeginFrom" name="confirmBeginFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${baseCheckItemPlan.confirmBeginFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="confirmBeginTo"
						name="confirmBeginTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${baseCheckItemPlan.confirmBeginTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>图纸审核状态</label> 
					<select id="dppStatus" name="dppStatus">
						<option value="">请选择</option>
						<option value="0">未审核</option>
						<option value="1" selected="selected">已审核</option>
					</select>
					</li>
					<li><label>结算预算日期从</label> <input type="text" id="checkPlanDateFrom"
						name="checkPlanDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${baseCheckItemPlan.checkPlanDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="checkPlanDateTo"
						name="checkPlanDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${baseCheckItemPlan.checkPlanDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li class="search-group-shrink"><input type="checkbox"
						id="expired_show" name="expired_show"
						value="${baseCheckItemPlan.expired}" onclick="hideExpired(this)"
						${baseCheckItemPlan.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-system btn-sm"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-system btn-sm"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="BASECHECKITEMPLAN_DETAIL">
					<button type="button" class="btn btn-system " onclick="detail()">结算报价</button>
				</house:authorize>
				<house:authorize authCode="BASECHECKITEMPLAN_VIEW">
					<button type="button" class="btn btn-system " onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="BASECHECKITEMPLAN_EXCEL">
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
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


