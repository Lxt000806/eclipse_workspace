<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemConfirmInform查询code</title>
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
			url:"${ctx}/admin/itemConfirmInform/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'PK',index : 'PK',width : 100,label:'pk',sortable : true,align : "left"}
			  ,
		      {name : 'CustCode',index : 'CustCode',width : 100,label:'custCode',sortable : true,align : "left"}
			  ,
		      {name : 'ItemTimeCode',index : 'ItemTimeCode',width : 100,label:'itemTimeCode',sortable : true,align : "left"}
			  ,
		      {name : 'InformDate',index : 'InformDate',width : 100,label:'informDate',sortable : true,align : "left"}
			  ,
		      {name : 'PlanComeDate',index : 'PlanComeDate',width : 100,label:'planComeDate',sortable : true,align : "left"}
			  ,
		      {name : 'InformRemark',index : 'InformRemark',width : 100,label:'informRemark',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdate',index : 'LastUpdate',width : 100,label:'lastUpdate',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"}
			  ,
		      {name : 'ActionLog',index : 'ActionLog',width : 100,label:'actionLog',sortable : true,align : "left"}
			  ,
		      {name : 'Expired',index : 'Expired',width : 100,label:'expired',sortable : true,align : "left"}
			  
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
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${itemConfirmInform.pk}" />
							</td>
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${itemConfirmInform.custCode}" />
							</td>
							<td class="td-label">itemTimeCode</td>
							<td class="td-value">
							<input type="text" id="itemTimeCode" name="itemTimeCode" style="width:160px;"  value="${itemConfirmInform.itemTimeCode}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">informDate</td>
							<td class="td-value">
							<input type="text" id="informDate" name="informDate" style="width:160px;"  value="${itemConfirmInform.informDate}" />
							</td>
							<td class="td-label">planComeDate</td>
							<td class="td-value">
							<input type="text" id="planComeDate" name="planComeDate" style="width:160px;"  value="${itemConfirmInform.planComeDate}" />
							</td>
							<td class="td-label">informRemark</td>
							<td class="td-value">
							<input type="text" id="informRemark" name="informRemark" style="width:160px;"  value="${itemConfirmInform.informRemark}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemConfirmInform.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemConfirmInform.lastUpdatedBy}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemConfirmInform.actionLog}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">expired</td>
							<td class="td-value" colspan="5">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemConfirmInform.expired}" />
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


