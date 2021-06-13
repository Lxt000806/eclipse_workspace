<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemReturnArrive查询code</title>
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
			url:"${ctx}/admin/itemReturnArrive/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'No',index : 'No',width : 100,label:'no',sortable : true,align : "left"}
			  ,
		      {name : 'ReturnNo',index : 'ReturnNo',width : 100,label:'returnNo',sortable : true,align : "left"}
			  ,
		      {name : 'DriverCode',index : 'DriverCode',width : 100,label:'driverCode',sortable : true,align : "left"}
			  ,
		      {name : 'Address',index : 'Address',width : 100,label:'address',sortable : true,align : "left"}
			  ,
		      {name : 'DriverRemark',index : 'DriverRemark',width : 100,label:'driverRemark',sortable : true,align : "left"}
			  ,
		      {name : 'ArriveDate',index : 'ArriveDate',width : 100,label:'arriveDate',sortable : true,align : "left"}
			  ,
		      {name : 'ActionLog',index : 'ActionLog',width : 100,label:'actionLog',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdate',index : 'LastUpdate',width : 100,label:'lastUpdate',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"}
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
							<td class="td-label">no</td>
							<td class="td-value">
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemReturnArrive.no}" />
							</td>
							<td class="td-label">returnNo</td>
							<td class="td-value">
							<input type="text" id="returnNo" name="returnNo" style="width:160px;"  value="${itemReturnArrive.returnNo}" />
							</td>
							<td class="td-label">driverCode</td>
							<td class="td-value">
							<input type="text" id="driverCode" name="driverCode" style="width:160px;"  value="${itemReturnArrive.driverCode}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">address</td>
							<td class="td-value">
							<input type="text" id="address" name="address" style="width:160px;"  value="${itemReturnArrive.address}" />
							</td>
							<td class="td-label">driverRemark</td>
							<td class="td-value">
							<input type="text" id="driverRemark" name="driverRemark" style="width:160px;"  value="${itemReturnArrive.driverRemark}" />
							</td>
							<td class="td-label">arriveDate</td>
							<td class="td-value">
							<input type="text" id="arriveDate" name="arriveDate" style="width:160px;"  value="${itemReturnArrive.arriveDate}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemReturnArrive.actionLog}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemReturnArrive.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemReturnArrive.lastUpdatedBy}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">expired</td>
							<td class="td-value" colspan="5">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemReturnArrive.expired}" />
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


