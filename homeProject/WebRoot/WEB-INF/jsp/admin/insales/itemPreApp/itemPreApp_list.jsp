<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemPreApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("itemPreAppAdd",{
		  title:"添加ItemPreApp",
		  url:"${ctx}/admin/itemPreApp/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemPreAppUpdate",{
		  title:"修改ItemPreApp",
		  url:"${ctx}/admin/itemPreApp/goUpdate?id="+ret.No,
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
      Global.Dialog.showDialog("itemPreAppCopy",{
		  title:"复制ItemPreApp",
		  url:"${ctx}/admin/itemPreApp/goSave?id="+ret.No,
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
      Global.Dialog.showDialog("itemPreAppView",{
		  title:"查看ItemPreApp",
		  url:"${ctx}/admin/itemPreApp/goDetail?id="+ret.No,
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
	var url = "${ctx}/admin/itemPreApp/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	$.form_submit($("#page_form").get(0), {
		"action": "${ctx}/admin/itemPreApp/doExcel"
	});
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemPreApp/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'No',index : 'No',width : 100,label:'no',sortable : true,align : "left"}
			  ,
		      {name : 'CustCode',index : 'CustCode',width : 100,label:'custCode',sortable : true,align : "left"}
			  ,
		      {name : 'IsSetItem',index : 'IsSetItem',width : 100,label:'isSetItem',sortable : true,align : "left"}
			  ,
		      {name : 'Status',index : 'Status',width : 100,label:'status',sortable : true,align : "left"}
			  ,
		      {name : 'AppCZY',index : 'AppCZY',width : 100,label:'appCzy',sortable : true,align : "left"}
			  ,
		      {name : 'Date',index : 'Date',width : 100,label:'date',sortable : true,align : "left"}
			  ,
		      {name : 'ConfirmCZY',index : 'ConfirmCZY',width : 100,label:'confirmCzy',sortable : true,align : "left"}
			  ,
		      {name : 'ConfirmDate',index : 'ConfirmDate',width : 100,label:'confirmDate',sortable : true,align : "left"}
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
		      {name : 'ItemType1',index : 'ItemType1',width : 100,label:'itemType1',sortable : true,align : "left"}
			  
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${itemPreApp.expired}" />
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
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemPreApp.no}" />
							</td>
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${itemPreApp.custCode}" />
							</td>
							<td class="td-label">isSetItem</td>
							<td class="td-value">
							<input type="text" id="isSetItem" name="isSetItem" style="width:160px;"  value="${itemPreApp.isSetItem}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">status</td>
							<td class="td-value">
							<input type="text" id="status" name="status" style="width:160px;"  value="${itemPreApp.status}" />
							</td>
							<td class="td-label">appCzy</td>
							<td class="td-value">
							<input type="text" id="appCzy" name="appCzy" style="width:160px;"  value="${itemPreApp.appCzy}" />
							</td>
							<td class="td-label">date</td>
							<td class="td-value">
							<input type="text" id="date" name="date" style="width:160px;"  value="${itemPreApp.date}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">confirmCzy</td>
							<td class="td-value">
							<input type="text" id="confirmCzy" name="confirmCzy" style="width:160px;"  value="${itemPreApp.confirmCzy}" />
							</td>
							<td class="td-label">confirmDate</td>
							<td class="td-value">
							<input type="text" id="confirmDate" name="confirmDate" style="width:160px;"  value="${itemPreApp.confirmDate}" />
							</td>
							<td class="td-label">remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${itemPreApp.remarks}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemPreApp.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemPreApp.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemPreApp.expired}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemPreApp.actionLog}" />
							</td>
							<td class="td-label">itemType1</td>
							<td class="td-value" colspan="3">
							<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemPreApp.itemType1}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input type="checkbox" id="expired_show" name="expired_show" value="${itemPreApp.expired}" onclick="hideExpired(this)" ${itemPreApp.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
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
            	<house:authorize authCode="ITEMPREAPP_SAVE">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="ITEMPREAPP_COPY">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="copy()">
					       <span>复制</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="ITEMPREAPP_UPDATE">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="update()">
					       <span>修改</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="ITEMPREAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a2" onclick="del()">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
				 <house:authorize authCode="ITEMPREAPP_VIEW">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="view()">
					       <span>查看</span>
						</a>	
                    </li>
                </house:authorize>
					<li>
						<a href="javascript:void(0)" class="a3" onclick="doExcel()" title="导出检索条件数据">
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


