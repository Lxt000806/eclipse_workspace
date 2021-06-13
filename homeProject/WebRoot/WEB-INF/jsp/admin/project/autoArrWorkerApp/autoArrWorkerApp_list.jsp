<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>AutoArrWorkerApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("autoArrWorkerAppAdd",{
		  title:"添加AutoArrWorkerApp",
		  url:"${ctx}/admin/autoArrWorkerApp/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("autoArrWorkerAppUpdate",{
		  title:"修改AutoArrWorkerApp",
		  url:"${ctx}/admin/autoArrWorkerApp/goUpdate?id="+ret.PK,
		  height:600,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function copy(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("autoArrWorkerAppCopy",{
		  title:"复制AutoArrWorkerApp",
		  url:"${ctx}/admin/autoArrWorkerApp/goSave?id="+ret.PK,
		  height:600,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("autoArrWorkerAppView",{
		  title:"查看AutoArrWorkerApp",
		  url:"${ctx}/admin/autoArrWorkerApp/goDetail?id="+ret.PK,
		  height:600,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/autoArrWorkerApp/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/autoArrWorkerApp/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/autoArrWorkerApp/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'pk',index : 'pk',width : 100,label:'pk',sortable : true,align : "left",key : true},
		      {name : 'arrpk',index : 'arrpk',width : 100,label:'arrPk',sortable : true,align : "left"},
		      {name : 'apppk',index : 'apppk',width : 100,label:'appPk',sortable : true,align : "left"},
		      {name : 'custcode',index : 'custcode',width : 100,label:'custCode',sortable : true,align : "left"},
		      {name : 'worktype12',index : 'worktype12',width : 100,label:'workType12',sortable : true,align : "left"},
		      {name : 'appcomedate',index : 'appcomedate',width : 100,label:'appComeDate',sortable : true,align : "left"},
		      {name : 'custtype',index : 'custtype',width : 100,label:'custType',sortable : true,align : "left"},
		      {name : 'projectman',index : 'projectman',width : 100,label:'projectMan',sortable : true,align : "left"},
		      {name : 'prjlevel',index : 'prjlevel',width : 100,label:'prjLevel',sortable : true,align : "left"},
		      {name : 'issupvr',index : 'issupvr',width : 100,label:'isSupvr',sortable : true,align : "left"},
		      {name : 'spcbuilder',index : 'spcbuilder',width : 100,label:'spcBuilder',sortable : true,align : "left"},
		      {name : 'spcbldexpired',index : 'spcbldexpired',width : 100,label:'spcBldExpired',sortable : true,align : "left"},
		      {name : 'regioncode',index : 'regioncode',width : 100,label:'regionCode',sortable : true,align : "left"},
		      {name : 'regioncode2',index : 'regioncode2',width : 100,label:'regionCode2',sortable : true,align : "left"},
		      {name : 'regisspcworker',index : 'regisspcworker',width : 100,label:'regIsSpcWorker',sortable : true,align : "left"},
		      {name : 'department1',index : 'department1',width : 100,label:'department1',sortable : true,align : "left"},
		      {name : 'area',index : 'area',width : 100,label:'area',sortable : true,align : "left"},
		      {name : 'workercode',index : 'workercode',width : 100,label:'workerCode',sortable : true,align : "left"},
		      {name : 'comedate',index : 'comedate',width : 100,label:'comeDate',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 100,label:'lastUpdate',sortable : true,align : "left"},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'expired',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'actionLog',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${autoArrWorkerApp.expired}" />
				<input type="hidden" name="jsonString" value="" />
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
									<input type="checkbox" id="expired_show" name="expired_show" value="${autoArrWorkerApp.expired}" onclick="hideExpired(this)" ${autoArrWorkerApp.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
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
			<!--panelBar-->
			<div class="panelBar">
            	<ul>
            	<house:authorize authCode="AUTOARRWORKERAPP_ADD">
                	<li>
                    	<a href="#" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="AUTOARRWORKERAPP_COPY">
                	<li>
                    	<a href="#" class="a1" onclick="copy()">
					       <span>复制</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="AUTOARRWORKERAPP_UPDATE">
                	<li>
                    	<a href="#" class="a1" onclick="update()">
					       <span>修改</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="AUTOARRWORKERAPP_DELETE">
                	<li>
						<a href="#" class="a2" onclick="del()">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
				 <house:authorize authCode="AUTOARRWORKERAPP_VIEW">
                	<li>
                    	<a href="#" class="a1" onclick="view()">
					       <span>查看</span>
						</a>	
                    </li>
                </house:authorize>
					<li>
						<a href="#" class="a3" onclick="doExcel()" title="导出检索条件数据">
							<span>导出excel</span>
						</a>
					</li>
                    <li class="line"></li>
                </ul>
				<div class="clear_float"> </div>
			</div><!--panelBar end-->
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


