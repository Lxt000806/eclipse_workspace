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
	if("OPEN"!="${purchase.status}"){
		$("#addPurchFee").attr("disabled","disabled");
		$("#updatePurchFee").attr("disabled","disabled");
	}

	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchase/getPurchFeeList",
		postData:{no:"${purchase.no}"},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'no', index:'no', width:80, label:'费用单号', sortable:true ,align:"left",},
			{name:'puno', index:'puno', width:80, label:'采购单号', sortable:true ,align:"left",},
			{name:'statusdescr', index:'statusdescr', width:80, label:'状态', sortable:true ,align:"left",},
			{name:'billstatus', index:'billstatus', width:80, label:'状态', sortable:true ,align:"left",hidden:true},
			{name:'billstatusdescr', index:'billstatusdescr', width:80, label:'发票状态', sortable:true ,align:"left",},
			{name:'whcode', index:'whcode', width:80, label:'柜号', sortable:true ,align:"left",},
			{name:'arriveno', index:'arriveno', width:80, label:'到货单号', sortable:true ,align:"left",},
			{name:'lastupdate', index:'lastupdate', width:120, label:'最后修改时间', sortable:true ,align:"left",formatter:formatDate},
			{name:'lastupdatedby', index:'lastupdatedby', width:80, label:'最后修改人', sortable:true ,align:"left",},
		],
	});
	
	//采购费
	$("#addPurchFee").on("click",function(){
		Global.Dialog.showDialog("addPurchFee",{ 
			title:"采购单——采购费用录入",
 			url:"${ctx}/admin/purchase/goPurchFeeSave",
 			postData:{puno:"${purchase.no}",m_umState:"A"},
 			height: 700,
			width:850,
         	returnFun:goto_query
		});
	});
	
	//采购费
	$("#updatePurchFee").on("click",function(){
		var ret = selectDataTableRow();
        	if (ret) {
        		if(ret.statusdescr == "已分摊"){
        			art.dialog({
		       			content: "已分摊状态不允许修改"
		       		});
		       		return;
        		}	
				Global.Dialog.showDialog("updatePurchFee",{ 
	             	title:"采购单——采购费用修改",
	              	url:"${ctx}/admin/purchase/goPurchFeeSave",
	              	postData:{puno:"${purchase.no }",no:ret.no,m_umState:"M"},
	             	height: 700,
	             	width:850,
	             	returnFun:goto_query
             	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
		}
	});
	
	//采购费
	$("#viewPurchFee").on("click",function(){
		var ret = selectDataTableRow();
        	if (ret) {
				Global.Dialog.showDialog("updatePurchFee",{ 
	             	title:"采购单——采购费用查看",
	              	url:"${ctx}/admin/purchase/goPurchFeeView",
	              	postData:{puno:"${purchase.no }",no:ret.no,m_umState:"M"},
	             	height: 700,
	             	width:850,
	             	returnFun:goto_query
             	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
		}
	});
	
	$("#updateBillStatus").on("click",function(){
		var ret = selectDataTableRow();
        	if (ret) {
				Global.Dialog.showDialog("updateBillStatus",{ 
	             	title:"采购单——修改发票状态",
	              	url:"${ctx}/admin/purchase/goUpdateBillStatus",
	              	postData:{puno:"${purchase.no }",no:ret.no,m_umState:"M",billStatus:ret.billstatus},
	             	height: 700,
	             	width:850,
	             	returnFun:goto_query
             	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
		}
	});
});

</script>
</head>
	<body >
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="addPurchFee" >
							<span>新增</span>
						</button>
						<button type="button" class="btn btn-system " id="updatePurchFee" >
							<span>编辑</span>
						</button>
						<button type="button" class="btn btn-system " id="updateBillStatus" >
							<span>发票状态</span>
						</button>
						<button type="button" class="btn btn-system " id="viewPurchFee" >
							<span>查看</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form" hidden="true">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="no"  name="no" value="${purchase.no }"/>
					<ul class="ul-form">
					</ul>	
				</form>
			</div>
			<!--query-form-->
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
		</div>
	</body>	
</html>
