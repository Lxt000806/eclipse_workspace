<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>流程数据分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
	<script src="${resourceRoot}/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${resourceRoot}/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${resourceRoot}/js/workflow.js" type="text/javascript"></script>
	<style type="text/css">
		.SelectBG{
			background-color:white!important;
		}
		.SelectBlack{
			background-color:white!important;
			color:black!important;
		}
	</style>
<script type="text/javascript">
function doExcel(){
	var url = "${ctx}/admin/wfProcInst/doAllWfProcExcel";
	doExcelAll(url);
}
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/wfProcInst/getExpenseAdvanceJqGrid",
		postData:{status:"1"},
		height:$(document).height()-$("#content-list").offset().top-80,
		colModel : [
		  	{name: "namechi", index: "namechi", width: 90, label: "员工姓名", sortable: true, align: "left",}, 
			{name: "empcode", index: "empcode", width: 90, label: "员工编号", sortable: true, align: "left",},
			{name: "amount", index: "amount", width: 90, label: "余额", sortable: true, align: "left",}, 
			{name: "lastupdate", index: "lastupdate", width:120, label: "最后修改时间", sortable: true, align: "left",formatter:formatDate},
			{name: "lastupdatedby", index: "lastupdatedby", width:120, label: "最后修改人", sortable: true, align: "left", },
	    ],
	});
});

function refund() {
 	var	ret= selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	
	console.log(ret.amount);
    Global.Dialog.showDialog("refund", {
		title: "借款余额——还款",
		url: "${ctx }/admin/wfProcInst/goRefund",
		postData:{number:ret.empcode, advanceAmount:ret.amount},
		height: 350,
		width: 820,
		returnFun: goto_query
    });
}

function expenseAdvanceTran(){
	var	ret= selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	
	Global.Dialog.showDialog("goExpenseAdvanceTran",{
		title:"借款余额——明细变动",
		url:"${ctx}/admin/wfProcInst/goExpenseAdvanceTran",
		postData:{number:ret.empcode},
		height: 690,
		width:1080,
		returnFun: goto_query
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
						<label>员工姓名</label>
						<input type="text" id="nameChi" name="nameChi" style="width:160px;"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" onclick="refund()">还款</button>
				<button type="button" class="btn btn-system" onclick="expenseAdvanceTran()">变动明细</button>
			</div>
		</div>
		<!--query-form-->
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


