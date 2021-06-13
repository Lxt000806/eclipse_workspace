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
								<button type="button" class="btn btn-system " id="saveBtn">
									<span>保存</span>
								</button>
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
								<ul class="ul-form">
								<li>
								<label>  采购订单号</label>
									<input type="text" id="puno" name="puno" style="width:160px;" value="${purchaseDelay1.puno }" readonly="readonly"/>
								</li>
								<li>
								<label>到货日期</label>
									<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchaseDelay1.arriveDate }' pattern='yyyy-MM-dd'/>"  />
								</li>
								<li>
								<label class="control-textarea">延期说明</label>
									<textarea id="remarks" name="remarks" rows="2">${purchaseDelay1.remarks }</textarea>
								</li>
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
		postData:{puno:'${purchaseDelay.puno }'},
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
				{name:'pk', index:'PK', width:80, label:'pk', sortable:true ,align:"left",hidden:true},
				{name:'puno', index:'puno', width:80, label:'采购单号', sortable:true ,align:"left",},
				{name:'arrivedate', index:'arrivedate', width:80, label:'到货日期', sortable:true ,align:"left",formatter: formatDate},
				{name:'remarks', index:'remarks', width:80, label:'到货说明', sortable:true ,align:"left",},
				{name:'lastupdate', index:'lastupdate', width:80, label:'最后更新时间', sortable:true ,align:"left",formatter: formatDate},
				{name:'lastupdatedby', index:'lastupdatedby', width:80, label:'最后跟新人员', sortable:true ,align:"left",},
			],
	});
	
	//保存
	$("#saveBtn").on("click",function(){
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/purchase/doPurchaseDelay',
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: "采购单延期登记成功",
						time: 1000,
						beforeunload: function () {
		    				$("#dataTable").jqGrid("setGridParam",{postData:$("#dataForm").jsonForm(),page:1}).trigger("reloadGrid");
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
		 });
	});

});  
</script>
