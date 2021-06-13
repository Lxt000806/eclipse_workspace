<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
	<script type="text/javascript">
	//校验函数
	$(function() {
		$("#page_form").setTable();
		var gridOption = {
			url:"${ctx}/admin/purchase/goPurchaseJqGridAll",
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [
				  {name : 'checkoutno',index : 'checkoutno',width : 100,label:'checkoutno',sortable : true,align : "left",hidden:true},
		  		  {name : 'no',index : 'no',width : 85,label:'采购单号',sortable : true,align : "left"},
		  		  {name : 'appno',index : 'appno',width : 85,label:'领料单号',sortable : true,align : "left"},
		  		  {name : 'itemtype2',index : 'itemtype2',width : 85,label:'材料类型2',sortable : true,align : "left", hidden: true},
		  	  	  {name : 'itemtype2descr',index : 'itemtype2descr',width : 85,label:'材料类型2',sortable : true,align : "left"},
		  		  {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
		  		  {name : 'documentno',index : 'documentno',width : 70,label:'编号',sortable : true,align : "left"},
		  	      {name : 'date',index : 'date',width : 75,label:'采购日期',sortable : true,align : "left",formatter:formatTime},
		  	      {name : 'othercost',index : 'othercost',width : 60,label:'加工费',sortable : true,align : "left"},
		  	      {name : 'othercostadj',index : 'othercostadj',width : 60,label:'配送费',editable:true,editrules: {number:true,required:true},sortable : true,align : "left"},
		  	      {name : 'splamount',index : 'splamount',width : 70,label:'对账金额',editable:true,editrules: {number:true,required:true},sortable : true,align : "left"},
			      {name : 'hjamount',index : 'hjamount',width : 70,label:'合计金额',sortable : true,align : "left"},
		  	      {name : 'remarks',index : 'remarks',width : 150,label:'备注',sortable : true,align : "left"},
		  	      {name : 'checkoutno',index : 'checkoutno',width : 85,label:'结算单号',sortable : true,align : "left"},
		  	  	  {name : 'checkoutdate',index : 'checkoutdate',width : 75,label:'结算日期',sortable : true,align : "left",formatter:formatTime},
		  		  {name : 'checkoutstatusdescr',index : 'checkoutstatusdescr',width : 85,label:'结算单状态',sortable : true,align : "left"}
		  	      ]
		};
		
		//初始化采购单结算
		Global.JqGrid.initJqGrid("splCheckOutAllDataTable",gridOption);
		
	});
	</script>
	<style type="text/css">
	
	</style>
</head>
<body>
<div class="body-box-list">
		<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
		</div>
		<div class="query-form" style="margin-bottom: 15px;">
			<form action="" method="post" id="page_form" >
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="25.4%"/>
						<col  width="82"/>
						<col  width="25.3%"/>
						<col  width="82" />
						<col  width="25.4%"/>
					<tbody>
						<tr class="td-btn">	
							<td class="td-label">采购类型</td>
							<td class="td-value">
							<house:xtdm id="type" dictCode="PURCHTYPE" value="${purchase.type }"></house:xtdm>
							</td>
							<td class="td-label">采购日期从</td>
							<td class="td-value">
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.dateFrom}' pattern='yyyy-MM-dd'/>" />
							</td>							
							<td class="td-label">至</td>
							<td class="td-value">
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.dateTo}' pattern='yyyy-MM-dd'/>" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">楼盘地址</td>
							<td class="td-value">
							<input type="text" id="address" name="address" style="width:160px;" value="${purchase.address }"/>
							</td>							
							<td class="td-label">是否结算</td>
							<td class="td-value">
							<house:xtdm id="isCheckOut" dictCode="YESNO" value="${purchase.isCheckOut }"></house:xtdm>
							</td>
							<td class="td-label">结算单号</td>
							<td class="td-value" colspan="1">
							<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;" value="${purchase.checkOutNo }"/>
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input onclick="goto_query('splCheckOutAllDataTable');"  class="i-btn-s" type="button"  value="查询"/>
									<input onclick="clearForm();"  class="i-btn-s" type="button"  value="清空"/>
								</div>
						  	</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "splCheckOutAllDataTable"></table>
				<div id="splCheckOutAllDataTablePager"></div>
			</div> 
		</div>
</html>
