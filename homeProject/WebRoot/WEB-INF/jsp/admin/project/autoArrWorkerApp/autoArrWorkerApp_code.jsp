<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>AutoArrWorkerApp查询code</title>
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
			url:"${ctx}/admin/autoArrWorkerApp/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'PK',index : 'PK',width : 100,label:'pk',sortable : true,align : "left"}
			  ,
		      {name : 'ArrPK',index : 'ArrPK',width : 100,label:'arrPk',sortable : true,align : "left"}
			  ,
		      {name : 'AppPK',index : 'AppPK',width : 100,label:'appPk',sortable : true,align : "left"}
			  ,
		      {name : 'CustCode',index : 'CustCode',width : 100,label:'custCode',sortable : true,align : "left"}
			  ,
		      {name : 'WorkType12',index : 'WorkType12',width : 100,label:'workType12',sortable : true,align : "left"}
			  ,
		      {name : 'AppComeDate',index : 'AppComeDate',width : 100,label:'appComeDate',sortable : true,align : "left"}
			  ,
		      {name : 'CustType',index : 'CustType',width : 100,label:'custType',sortable : true,align : "left"}
			  ,
		      {name : 'ProjectMan',index : 'ProjectMan',width : 100,label:'projectMan',sortable : true,align : "left"}
			  ,
		      {name : 'PrjLevel',index : 'PrjLevel',width : 100,label:'prjLevel',sortable : true,align : "left"}
			  ,
		      {name : 'IsSupvr',index : 'IsSupvr',width : 100,label:'isSupvr',sortable : true,align : "left"}
			  ,
		      {name : 'SpcBuilder',index : 'SpcBuilder',width : 100,label:'spcBuilder',sortable : true,align : "left"}
			  ,
		      {name : 'SpcBldExpired',index : 'SpcBldExpired',width : 100,label:'spcBldExpired',sortable : true,align : "left"}
			  ,
		      {name : 'RegionCode',index : 'RegionCode',width : 100,label:'regionCode',sortable : true,align : "left"}
			  ,
		      {name : 'RegionCode2',index : 'RegionCode2',width : 100,label:'regionCode2',sortable : true,align : "left"}
			  ,
		      {name : 'RegIsSpcWorker',index : 'RegIsSpcWorker',width : 100,label:'regIsSpcWorker',sortable : true,align : "left"}
			  ,
		      {name : 'Department1',index : 'Department1',width : 100,label:'department1',sortable : true,align : "left"}
			  ,
		      {name : 'Area',index : 'Area',width : 100,label:'area',sortable : true,align : "left"}
			  ,
		      {name : 'WorkerCode',index : 'WorkerCode',width : 100,label:'workerCode',sortable : true,align : "left"}
			  ,
		      {name : 'ComeDate',index : 'ComeDate',width : 100,label:'comeDate',sortable : true,align : "left"}
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
							<td class="td-label">pk</td>
							<td class="td-value">
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${autoArrWorkerApp.pk}" />
							</td>
							<td class="td-label">arrPk</td>
							<td class="td-value">
							<input type="text" id="arrPk" name="arrPk" style="width:160px;"  value="${autoArrWorkerApp.arrPk}" />
							</td>
							<td class="td-label">appPk</td>
							<td class="td-value">
							<input type="text" id="appPk" name="appPk" style="width:160px;"  value="${autoArrWorkerApp.appPk}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${autoArrWorkerApp.custCode}" />
							</td>
							<td class="td-label">workType12</td>
							<td class="td-value">
							<input type="text" id="workType12" name="workType12" style="width:160px;"  value="${autoArrWorkerApp.workType12}" />
							</td>
							<td class="td-label">appComeDate</td>
							<td class="td-value">
							<input type="text" id="appComeDate" name="appComeDate" style="width:160px;"  value="${autoArrWorkerApp.appComeDate}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">custType</td>
							<td class="td-value">
							<input type="text" id="custType" name="custType" style="width:160px;"  value="${autoArrWorkerApp.custType}" />
							</td>
							<td class="td-label">projectMan</td>
							<td class="td-value">
							<input type="text" id="projectMan" name="projectMan" style="width:160px;"  value="${autoArrWorkerApp.projectMan}" />
							</td>
							<td class="td-label">prjLevel</td>
							<td class="td-value">
							<input type="text" id="prjLevel" name="prjLevel" style="width:160px;"  value="${autoArrWorkerApp.prjLevel}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">isSupvr</td>
							<td class="td-value">
							<input type="text" id="isSupvr" name="isSupvr" style="width:160px;"  value="${autoArrWorkerApp.isSupvr}" />
							</td>
							<td class="td-label">spcBuilder</td>
							<td class="td-value">
							<input type="text" id="spcBuilder" name="spcBuilder" style="width:160px;"  value="${autoArrWorkerApp.spcBuilder}" />
							</td>
							<td class="td-label">spcBldExpired</td>
							<td class="td-value">
							<input type="text" id="spcBldExpired" name="spcBldExpired" style="width:160px;"  value="${autoArrWorkerApp.spcBldExpired}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">regionCode</td>
							<td class="td-value">
							<input type="text" id="regionCode" name="regionCode" style="width:160px;"  value="${autoArrWorkerApp.regionCode}" />
							</td>
							<td class="td-label">regionCode2</td>
							<td class="td-value">
							<input type="text" id="regionCode2" name="regionCode2" style="width:160px;"  value="${autoArrWorkerApp.regionCode2}" />
							</td>
							<td class="td-label">regIsSpcWorker</td>
							<td class="td-value">
							<input type="text" id="regIsSpcWorker" name="regIsSpcWorker" style="width:160px;"  value="${autoArrWorkerApp.regIsSpcWorker}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">department1</td>
							<td class="td-value">
							<input type="text" id="department1" name="department1" style="width:160px;"  value="${autoArrWorkerApp.department1}" />
							</td>
							<td class="td-label">area</td>
							<td class="td-value">
							<input type="text" id="area" name="area" style="width:160px;"  value="${autoArrWorkerApp.area}" />
							</td>
							<td class="td-label">workerCode</td>
							<td class="td-value">
							<input type="text" id="workerCode" name="workerCode" style="width:160px;"  value="${autoArrWorkerApp.workerCode}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">comeDate</td>
							<td class="td-value">
							<input type="text" id="comeDate" name="comeDate" style="width:160px;"  value="${autoArrWorkerApp.comeDate}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${autoArrWorkerApp.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${autoArrWorkerApp.lastUpdatedBy}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${autoArrWorkerApp.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value" colspan="3">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${autoArrWorkerApp.actionLog}" />
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


