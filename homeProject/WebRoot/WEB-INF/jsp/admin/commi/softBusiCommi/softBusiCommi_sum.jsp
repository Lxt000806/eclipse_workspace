<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>提成汇总查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemCommiCycle.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	} 
	/**初始化表格*/
	$(function(){
		$("#empCode").openComponent_employee();
		$("#commiNo").openComponent_itemCommiCycle({
			showValue:"${softBusiCommi.commiNo}"
		});
		
		var postData = $("#page_form").jsonForm();
		
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/softBusiCommi/goSumJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			postData:postData,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "commino", index: "commino", width: 80, label: "周期编号", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 180, label: "楼盘", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 80, label: "档案号", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 120, label: "二级部门", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 80, label: "干系人", sortable: true, align: "left"},
				{name: "thisamount", index: "thisamount", width: 85, label: "本次提成额", sortable: true, align: "right"},
			],
		});
	});
	
	function doExcel(){
		var url = "${ctx}/admin/softBusiCommi/doSumExcel";
		doExcelAll(url);
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
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="isInd" value="${softBusiCommi.isInd}" />
				<ul class="ul-form">
					<c:if test="${softBusiCommi.crtMon != null}">
						<li>
							<label>生成月份</label>
							<input type="text" id="crtMon" name="crtMon" class="i-date" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" value="${softBusiCommi.crtMon }"/>
						</li>
					</c:if>
					<c:if test="${softBusiCommi.commiNo != null}">
						<li>
							<label>提成周期</label>
							<input type="text" id="commiNo" name="commiNo" />
						</li>
					</c:if>
					<li>
						<label>楼盘</label> 
						<input type="text" id="address" name="address" />
					</li>
					<li>
						<label>干系人</label> 
						<input type="text" id="empCode" name="empCode"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
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
