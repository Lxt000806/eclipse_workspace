<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>余额查询</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	}
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#splCode").val('');
		$("#splDescr").val('');
		$("#itemType1").val('');
	} 
	function changeView(){
		var ret = selectDataTableRow();
	    if (ret) {    	
	    	Global.Dialog.showDialog("gochangeView",{
				title:"预付金变动明细",
				url:"${ctx}/admin/payManage/gochangeView",
				postData:{Code:ret.code,Descr:ret.descr,PrepayBalance:ret.prepaybalance,ItemType1:ret.itemtype1descr},				
				height:700,
				width:1200,
				returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function doExcel(){
		var url = "${ctx}/admin/payManage/doExcelForYe";
		doExcelAll(url);
	}
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:"${supplierPrepay.itemType1}",
			disabled:"true"
		};
		Global.LinkSelect.setSelect(dataSet);
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/payManage/goJqGridYeSelect",
			postData:{type:"${supplierPrepay.type}",splCode:"0434"},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: "Bootstrap",
			colModel : [		
					{name: "code", index: "code", width: 80, label: "商家编码", sortable: true, align: "left"},
					{name: "descr", index: "descr", width: 150, label: "商家名称", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 100, label: "供应商类型", sortable: true, align: "left"},
					{name: "prepaybalance", index: "prepaybalance", width: 100, label: "预付金余额", sortable: true, align: "right"},
					{name: "sumfirstamount", index: "sumfirstamount", width: 100, label: "未核销定金", sortable: true, align: "right"},
					{name: "sumprepay", index: "sumprepay", width: 100, label: "预付合计", sortable: true, align: "right"},
					{name: "lastupdate", index: "lastupdate", width: 100, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "修改人", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"}
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
					<button type="button" class="btn btn-system" onclick="changeView()">变动明细</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li><label>商家编号</label> <input type="text" id="code" name="code" /></li>
						<li><label>商家名称</label> <input type="text" id="descr" name="descr" /></li>
						<li><label>供应商类型</label> <select id="itemType1" name="itemType1"></select></li>
						<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button></li>
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
					<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
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


