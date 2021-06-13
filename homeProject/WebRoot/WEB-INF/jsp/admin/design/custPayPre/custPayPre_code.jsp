<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>CustPayPre查询code</title>
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
			url:"${ctx}/admin/custPayPre/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'PK',index : 'PK',width : 100,label:'pk',sortable : true,align : "left"}
			  ,
		      {name : 'CustCode',index : 'CustCode',width : 100,label:'custCode',sortable : true,align : "left"}
			  ,
		      {name : 'PayType',index : 'PayType',width : 100,label:'payType',sortable : true,align : "left"}
			  ,
		      {name : 'BasePer',index : 'BasePer',width : 100,label:'basePer',sortable : true,align : "left"}
			  ,
		      {name : 'ItemPer',index : 'ItemPer',width : 100,label:'itemPer',sortable : true,align : "left"}
			  ,
		      {name : 'DesignFee',index : 'DesignFee',width : 100,label:'designFee',sortable : true,align : "left"}
			  ,
		      {name : 'PrePay',index : 'PrePay',width : 100,label:'prePay',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdate',index : 'LastUpdate',width : 100,label:'lastUpdate',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"}
			  ,
		      {name : 'Expired',index : 'Expired',width : 100,label:'expired',sortable : true,align : "left"}
			  ,
		      {name : 'ActionLog',index : 'ActionLog',width : 100,label:'actionLog',sortable : true,align : "left"}
			  ,
		      {name : 'BasePay',index : 'BasePay',width : 100,label:'basePay',sortable : true,align : "left"}
			  ,
		      {name : 'ItemPay',index : 'ItemPay',width : 100,label:'itemPay',sortable : true,align : "left"}
			  
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
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${custPayPre.pk}" />
							</td>
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${custPayPre.custCode}" />
							</td>
							<td class="td-label">payType</td>
							<td class="td-value">
							<input type="text" id="payType" name="payType" style="width:160px;"  value="${custPayPre.payType}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">basePer</td>
							<td class="td-value">
							<input type="text" id="basePer" name="basePer" style="width:160px;"  value="${custPayPre.basePer}" />
							</td>
							<td class="td-label">itemPer</td>
							<td class="td-value">
							<input type="text" id="itemPer" name="itemPer" style="width:160px;"  value="${custPayPre.itemPer}" />
							</td>
							<td class="td-label">designFee</td>
							<td class="td-value">
							<input type="text" id="designFee" name="designFee" style="width:160px;"  value="${custPayPre.designFee}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">prePay</td>
							<td class="td-value">
							<input type="text" id="prePay" name="prePay" style="width:160px;"  value="${custPayPre.prePay}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${custPayPre.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${custPayPre.lastUpdatedBy}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${custPayPre.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${custPayPre.actionLog}" />
							</td>
							<td class="td-label">basePay</td>
							<td class="td-value">
							<input type="text" id="basePay" name="basePay" style="width:160px;"  value="${custPayPre.basePay}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">itemPay</td>
							<td class="td-value" colspan="5">
							<input type="text" id="itemPay" name="itemPay" style="width:160px;"  value="${custPayPre.itemPay}" />
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


