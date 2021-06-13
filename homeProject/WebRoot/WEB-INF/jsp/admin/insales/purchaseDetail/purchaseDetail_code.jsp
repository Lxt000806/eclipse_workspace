<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>PurchaseDetail查询code</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/purchaseDetail/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'PK',index : 'PK',width : 100,label:'pk',sortable : true,align : "left"}
			  ,
		      {name : 'PUNo',index : 'PUNo',width : 100,label:'puno',sortable : true,align : "left"}
			  ,
		      {name : 'ITCode',index : 'ITCode',width : 100,label:'itcode',sortable : true,align : "left"}
			  ,
		      {name : 'QtyCal',index : 'QtyCal',width : 100,label:'qtyCal',sortable : true,align : "left"}
			  ,
		      {name : 'UnitPrice',index : 'UnitPrice',width : 100,label:'unitPrice',sortable : true,align : "left"}
			  ,
		      {name : 'Amount',index : 'Amount',width : 100,label:'amount',sortable : true,align : "left"}
			  ,
		      {name : 'Remarks',index : 'Remarks',width : 100,label:'remarks',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdate',index : 'LastUpdate',width : 100,label:'lastUpdate',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"}
			  ,
		      {name : 'Expired',index : 'Expired',width : 100,label:'expired',sortable : true,align : "left"}
			  ,
		      {name : 'ActionLog',index : 'ActionLog',width : 100,label:'actionLog',sortable : true,align : "left"}
			  ,
		      {name : 'ArrivQty',index : 'ArrivQty',width : 100,label:'arrivQty',sortable : true,align : "left"}
			  ,
		      {name : 'ProjectCost',index : 'ProjectCost',width : 100,label:'projectCost',sortable : true,align : "left"}
			  
            ],
			ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
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
							<td class="td-label">pk</td>
							<td class="td-value">
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${purchaseDetail.pk}" />
							</td>
							<td class="td-label">puno</td>
							<td class="td-value">
							<input type="text" id="puno" name="puno" style="width:160px;"  value="${purchaseDetail.puno}" />
							</td>
							<td class="td-label">itcode</td>
							<td class="td-value">
							<input type="text" id="itcode" name="itcode" style="width:160px;"  value="${purchaseDetail.itcode}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">qtyCal</td>
							<td class="td-value">
							<input type="text" id="qtyCal" name="qtyCal" style="width:160px;"  value="${purchaseDetail.qtyCal}" />
							</td>
							<td class="td-label">unitPrice</td>
							<td class="td-value">
							<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"  value="${purchaseDetail.unitPrice}" />
							</td>
							<td class="td-label">amount</td>
							<td class="td-value">
							<input type="text" id="amount" name="amount" style="width:160px;"  value="${purchaseDetail.amount}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${purchaseDetail.remarks}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${purchaseDetail.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${purchaseDetail.lastUpdatedBy}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${purchaseDetail.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${purchaseDetail.actionLog}" />
							</td>
							<td class="td-label">arrivQty</td>
							<td class="td-value">
							<input type="text" id="arrivQty" name="arrivQty" style="width:160px;"  value="${purchaseDetail.arrivQty}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">projectCost</td>
							<td class="td-value" colspan="5">
							<input type="text" id="projectCost" name="projectCost" style="width:160px;"  value="${purchaseDetail.projectCost}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input onclick="goto_query();"  class="i-btn-s" type="button"  value="检索"/>
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
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


