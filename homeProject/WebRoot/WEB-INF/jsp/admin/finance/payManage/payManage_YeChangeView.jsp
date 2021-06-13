<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>预付金变动明细</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#splCode").val('');
		$("#splDescr").val('');
		$("#itemType1").val('');
	} 
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/payManage/goJqGridYeChangeSelect",
			postData:$("#page_form").jsonForm(),
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: "Bootstrap",
			colModel : [		
				{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "date", index: "date", width: 100, label: "变动日期", sortable: true, align: "left", formatter: formatTime},
				{name: "trsamount", index: "trsamount", width: 80, label: "变动金额", sortable: true, align: "right"},
				{name: "aftamount", index: "aftamount", width: 80, label: "变动后金额", sortable: true, align: "right"},
				{name: "prefixdesc", index: "prefixdesc", width: 100, label: "单据类型", sortable: true, align: "left"},
				{name: "document", index: "document", width: 100, label: "档案号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 306, label: "备注", sortable: true, align: "left"}
	           ]
		});
	});  
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
					<div class="panel panel-info" style="margin-bottom: 8px";>
					<div class="panel-body" >
						<li><label>商家编号</label> <input type="text" id="code" name="code" value="${supplier.code}"
							disabled="true" />
						</li>
						<li><label>商家名称</label> <input type="text" id="descr" name="descr" value="${supplier.descr}"
							disabled="true" />
						</li>
						<li><label>供应商类型</label> <input type="text" id="itemtype1descr" name="itemtype1descr"
							value="${supplier.itemType1}" disabled="true" />
						</li>
						<li><label>预付金余额</label> <input type="text" id="prepaybalance" name="prepaybalance"
							value="${supplier.prepayBalance}" disabled="true" />
						</li>
					</div>
					</div>
						<li><label>变动日期</label> <input type="text" id="DateF" name="DateF" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${supplier.dateF}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li><label>至</label> <input type="text" id="DateT" name="DateT" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${supplier.dateT}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
						</li>
					
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system" onclick="doExcelNow('预付金变动明细')">导出excel</button>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


