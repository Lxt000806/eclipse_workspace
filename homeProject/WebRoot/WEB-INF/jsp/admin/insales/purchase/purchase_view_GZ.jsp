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
	<title>采购跟踪——延期</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>


</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
			<!-- panelBar -->
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="infoBox" id="infoBoxDiv"></div>
  			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
							<ul class="ul-form" >
								<li>
								<label><span class="required">*</span> 采购订单号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }" readonly="readonly"/>
								</li>
								<li>
								<label>到货状态</label>
									<house:xtdm   id="arriveStatus" dictCode="TPURARVSTATUS"  style="width:166px;" value="${purchase.arriveStatus }" disabled="true"></house:xtdm>
								</li>
								<li>
								<label class="control-textarea">到货说明</label>
									<textarea id="arriveRemark" name="arriveRemark" rows="3">${purchase.arriveRemark }</textarea>
								</li>
							</ul>	
				</form>
				</div>
			</div>
			
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">采购明细单</a></li>
		    </ul> 	
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
			
		</div>
	</div>
</div>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$("#tabs").tabs();
$(function(){	
	//初始化表格  
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchaseDelay/goJqGrid",
		postData:{puno:'${purchase.no }'},
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
				{name:'pk', index:'PK', width:80, label:'pk', sortable:true ,align:"left",hidden:true},
				{name:'puno', index:'puno', width:80, label:'采购单号', sortable:true ,align:"left",},
				{name:'arrivedate', index:'arrivedate', width:80, label:'到货日期', sortable:true ,align:"left",formatter: formatDate},
				{name:'remarks', index:'remarks', width:80, label:'延期说明', sortable:true ,align:"left",},
				{name:'lastupdate', index:'lastupdate', width:80, label:'最后更新时间', sortable:true ,align:"left",formatter: formatDate},
				{name:'lastupdatedby', index:'lastupdatedby', width:80, label:'最后更新人员', sortable:true ,align:"left",},
			],
	});

});  
</script>
