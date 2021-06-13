<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>CustLoan查询code</title>
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
			url:"${ctx}/admin/custLoan/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'CustCode',index : 'CustCode',width : 100,label:'custCode',sortable : true,align : "left"}
			  ,
		      {name : 'AgreeDate',index : 'AgreeDate',width : 100,label:'agreeDate',sortable : true,align : "left"}
			  ,
		      {name : 'Bank',index : 'Bank',width : 100,label:'bank',sortable : true,align : "left"}
			  ,
		      {name : 'FollowRemark',index : 'FollowRemark',width : 100,label:'followRemark',sortable : true,align : "left"}
			  ,
		      {name : 'SignRemark',index : 'SignRemark',width : 100,label:'signRemark',sortable : true,align : "left"}
			  ,
		      {name : 'ConfuseRemark',index : 'ConfuseRemark',width : 100,label:'confuseRemark',sortable : true,align : "left"}
			  ,
		      {name : 'Amount',index : 'Amount',width : 100,label:'amount',sortable : true,align : "left"}
			  ,
		      {name : 'FirstAmount',index : 'FirstAmount',width : 100,label:'firstAmount',sortable : true,align : "left"}
			  ,
		      {name : 'FirstDate',index : 'FirstDate',width : 100,label:'firstDate',sortable : true,align : "left"}
			  ,
		      {name : 'SecondAmount',index : 'SecondAmount',width : 100,label:'secondAmount',sortable : true,align : "left"}
			  ,
		      {name : 'SecondDate',index : 'SecondDate',width : 100,label:'secondDate',sortable : true,align : "left"}
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
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${custLoan.custCode}" />
							</td>
							<td class="td-label">agreeDate</td>
							<td class="td-value">
							<input type="text" id="agreeDate" name="agreeDate" style="width:160px;"  value="${custLoan.agreeDate}" />
							</td>
							<td class="td-label">bank</td>
							<td class="td-value">
							<input type="text" id="bank" name="bank" style="width:160px;"  value="${custLoan.bank}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">followRemark</td>
							<td class="td-value">
							<input type="text" id="followRemark" name="followRemark" style="width:160px;"  value="${custLoan.followRemark}" />
							</td>
							<td class="td-label">signRemark</td>
							<td class="td-value">
							<input type="text" id="signRemark" name="signRemark" style="width:160px;"  value="${custLoan.signRemark}" />
							</td>
							<td class="td-label">confuseRemark</td>
							<td class="td-value">
							<input type="text" id="confuseRemark" name="confuseRemark" style="width:160px;"  value="${custLoan.confuseRemark}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">amount</td>
							<td class="td-value">
							<input type="text" id="amount" name="amount" style="width:160px;"  value="${custLoan.amount}" />
							</td>
							<td class="td-label">firstAmount</td>
							<td class="td-value">
							<input type="text" id="firstAmount" name="firstAmount" style="width:160px;"  value="${custLoan.firstAmount}" />
							</td>
							<td class="td-label">firstDate</td>
							<td class="td-value">
							<input type="text" id="firstDate" name="firstDate" style="width:160px;"  value="${custLoan.firstDate}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">secondAmount</td>
							<td class="td-value">
							<input type="text" id="secondAmount" name="secondAmount" style="width:160px;"  value="${custLoan.secondAmount}" />
							</td>
							<td class="td-label">secondDate</td>
							<td class="td-value">
							<input type="text" id="secondDate" name="secondDate" style="width:160px;"  value="${custLoan.secondDate}" />
							</td>
							<td class="td-label">remark</td>
							<td class="td-value">
							<input type="text" id="remark" name="remark" style="width:160px;"  value="${custLoan.remark}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${custLoan.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${custLoan.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${custLoan.expired}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">actionLog</td>
							<td class="td-value" colspan="5">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${custLoan.actionLog}" />
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


