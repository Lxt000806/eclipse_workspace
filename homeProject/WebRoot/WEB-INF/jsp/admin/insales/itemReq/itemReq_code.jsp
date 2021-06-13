<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemReq查询code</title>
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
			url:"${ctx}/admin/itemReq/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'PK',index : 'PK',width : 100,label:'pk',sortable : true,align : "left"}
			  ,
		      {name : 'CustCode',index : 'CustCode',width : 100,label:'custCode',sortable : true,align : "left"}
			  ,
		      {name : 'FixAreaPK',index : 'FixAreaPK',width : 100,label:'fixAreaPk',sortable : true,align : "left"}
			  ,
		      {name : 'IntProdPK',index : 'IntProdPK',width : 100,label:'intProdPk',sortable : true,align : "left"}
			  ,
		      {name : 'ItemCode',index : 'ItemCode',width : 100,label:'itemCode',sortable : true,align : "left"}
			  ,
		      {name : 'ItemType1',index : 'ItemType1',width : 100,label:'itemType1',sortable : true,align : "left"}
			  ,
		      {name : 'Qty',index : 'Qty',width : 100,label:'qty',sortable : true,align : "left"}
			  ,
		      {name : 'SendQty',index : 'SendQty',width : 100,label:'sendQty',sortable : true,align : "left"}
			  ,
		      {name : 'Cost',index : 'Cost',width : 100,label:'cost',sortable : true,align : "left"}
			  ,
		      {name : 'UnitPrice',index : 'UnitPrice',width : 100,label:'unitPrice',sortable : true,align : "left"}
			  ,
		      {name : 'BefLineAmount',index : 'BefLineAmount',width : 100,label:'befLineAmount',sortable : true,align : "left"}
			  ,
		      {name : 'Markup',index : 'Markup',width : 100,label:'markup',sortable : true,align : "left"}
			  ,
		      {name : 'LineAmount',index : 'LineAmount',width : 100,label:'lineAmount',sortable : true,align : "left"}
			  ,
		      {name : 'Remark',index : 'Remark',width : 100,label:'remark',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdate',index : 'LastUpdate',width : 100,label:'lastUpdate',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"}
			  ,
		      {name : 'Expired',index : 'Expired',width : 100,label:'expired',sortable : true,align : "left"}
			  ,
		      {name : 'ActionLog',index : 'ActionLog',width : 100,label:'actionLog',sortable : true,align : "left"}
			  ,
		      {name : 'ProcessCost',index : 'ProcessCost',width : 100,label:'processCost',sortable : true,align : "left"}
			  ,
		      {name : 'DispSeq',index : 'DispSeq',width : 100,label:'dispSeq',sortable : true,align : "left"}
			  ,
		      {name : 'IsService',index : 'IsService',width : 100,label:'isService',sortable : true,align : "left"}
			  ,
		      {name : 'IsCommi',index : 'IsCommi',width : 100,label:'isCommi',sortable : true,align : "left"}
			  
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
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${itemReq.pk}" />
							</td>
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${itemReq.custCode}" />
							</td>
							<td class="td-label">fixAreaPk</td>
							<td class="td-value">
							<input type="text" id="fixAreaPk" name="fixAreaPk" style="width:160px;"  value="${itemReq.fixAreaPk}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">intProdPk</td>
							<td class="td-value">
							<input type="text" id="intProdPk" name="intProdPk" style="width:160px;"  value="${itemReq.intProdPk}" />
							</td>
							<td class="td-label">itemCode</td>
							<td class="td-value">
							<input type="text" id="itemCode" name="itemCode" style="width:160px;"  value="${itemReq.itemCode}" />
							</td>
							<td class="td-label">itemType1</td>
							<td class="td-value">
							<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemReq.itemType1}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">qty</td>
							<td class="td-value">
							<input type="text" id="qty" name="qty" style="width:160px;"  value="${itemReq.qty}" />
							</td>
							<td class="td-label">sendQty</td>
							<td class="td-value">
							<input type="text" id="sendQty" name="sendQty" style="width:160px;"  value="${itemReq.sendQty}" />
							</td>
							<td class="td-label">cost</td>
							<td class="td-value">
							<input type="text" id="cost" name="cost" style="width:160px;"  value="${itemReq.cost}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">unitPrice</td>
							<td class="td-value">
							<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"  value="${itemReq.unitPrice}" />
							</td>
							<td class="td-label">befLineAmount</td>
							<td class="td-value">
							<input type="text" id="befLineAmount" name="befLineAmount" style="width:160px;"  value="${itemReq.befLineAmount}" />
							</td>
							<td class="td-label">markup</td>
							<td class="td-value">
							<input type="text" id="markup" name="markup" style="width:160px;"  value="${itemReq.markup}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lineAmount</td>
							<td class="td-value">
							<input type="text" id="lineAmount" name="lineAmount" style="width:160px;"  value="${itemReq.lineAmount}" />
							</td>
							<td class="td-label">remark</td>
							<td class="td-value">
							<input type="text" id="remark" name="remark" style="width:160px;"  value="${itemReq.remark}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemReq.lastUpdate}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemReq.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemReq.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemReq.actionLog}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">processCost</td>
							<td class="td-value">
							<input type="text" id="processCost" name="processCost" style="width:160px;"  value="${itemReq.processCost}" />
							</td>
							<td class="td-label">dispSeq</td>
							<td class="td-value">
							<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"  value="${itemReq.dispSeq}" />
							</td>
							<td class="td-label">isService</td>
							<td class="td-value">
							<input type="text" id="isService" name="isService" style="width:160px;"  value="${itemReq.isService}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">isCommi</td>
							<td class="td-value" colspan="5">
							<input type="text" id="isCommi" name="isCommi" style="width:160px;"  value="${itemReq.isCommi}" />
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


