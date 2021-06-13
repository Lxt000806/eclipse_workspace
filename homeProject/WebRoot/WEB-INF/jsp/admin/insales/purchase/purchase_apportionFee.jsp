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
	<title>选择分摊费用</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTableDetail",{
		height:250,
		rowNum:10000000,
		colModel : [
			{name:'puno', index:'puno', width:80, label:'采购单号', sortable:true ,align:"left",hidden:true},
			{name:'no', index:'no', width:80, label:'采购单号', sortable:true ,align:"left",},
			{name:'pk', index:'pk', width:80, label:'pk', sortable:true ,align:"left",hidden:true},
			{name:'feetype', index:'feetype', width:80, label:'费用类型', sortable:true ,align:"left",hidden:true},
			{name:'feetypedescr', index:'feetypedescr', width:80, label:'费用类型', sortable:true ,align:"left",},
			{name:'amount', index:'amount', width:80, label:'费用', sortable:true ,align:"left",sum:true},
			{name:'remarks', index:'remarks', width:80, label:'备注', sortable:true ,align:"left",},
			{name:'lastupdate', index:'lastupdate', width:80, label:'最后修改时间', sortable:true ,align:"left",formatter:formatDate},
			{name:'lastupdatedby', index:'lastupdatedby', width:80, label:'最后修改人', sortable:true ,align:"left",},
			{name:'actionlog', index:'actionlog', width:80, label:'actionlog', sortable:true ,align:"left",hidden:true},
		],
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchase/getPurchFeeList",
		postData:{no:"${purchaseFee.puno}",status:"1"},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		height:250,
		colModel : [
			{name:'no', index:'no', width:80, label:'费用单号', sortable:true ,align:"left",},
			{name:'puno', index:'puno', width:80, label:'采购单号', sortable:true ,align:"left",},
			{name:'statusdescr', index:'statusdescr', width:80, label:'状态', sortable:true ,align:"left",},
			{name:'whcode', index:'whcode', width:80, label:'柜号', sortable:true ,align:"left",},
			{name:'lastupdate', index:'lastupdate', width:80, label:'最后修改时间', sortable:true ,align:"left",formatter:formatDate},
			{name:'lastupdatedby', index:'lastupdatedby', width:80, label:'最后修改人', sortable:true ,align:"left",},
		],
		onSelectRow : function(id) {
        	var row = selectDataTableRow("dataTable");
           	$("#dataTableDetail").jqGrid('setGridParam',{url : "${ctx}/admin/purchase/getPurchFeeDetail?no="+row.no});
           	$("#dataTableDetail").jqGrid().trigger('reloadGrid');
        }
	});
	
	$("#apportionFee").on("click",function(){
		var ids =$("#dataTableDetail").getDataIDs();
		if(ids == null || ids == '' || ids.length <= 0){
			art.dialog({
				content:'费用明细无数据，请稍后再试',
			});
			return false;
		}
        var row = selectDataTableRow("dataTable");
		var sumValue = $("#dataTableDetail").getCol('amount', false, 'sum');
		var selectRows = [];
		var datas;
		 	selectRows.push(sumValue);
		 	selectRows.push(row.no);
			Global.Dialog.returnData = selectRows;
			closeWin();
	});
	
});

</script>
</head>
	<body >
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="apportionFee" >
							<span>分摊</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form" hidden="true">
				<form action="" method="post" id="page_form" class="form-search" hidden="true">
					<input type="hidden" id="puno"  name="puno" value="${purchaseFee.puno }"/>
					<ul class="ul-form">
					</ul>	
				</form>
			</div>
			<!--query-form-->
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
				</div> 
				<div id="content-list">
					<table id="dataTableDetail"></table>
				</div>
			</div>
		</div>
	</body>	
</html>
