<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemReturnDetail列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("itemReturnDetailAdd",{
		  title:"添加ItemReturnDetail",
		  url:"${ctx}/admin/itemReturnDetail/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemReturnDetailUpdate",{
		  title:"修改ItemReturnDetail",
		  url:"${ctx}/admin/itemReturnDetail/goUpdate?id="+ret.PK,
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
      Global.Dialog.showDialog("itemReturnDetailCopy",{
		  title:"复制ItemReturnDetail",
		  url:"${ctx}/admin/itemReturnDetail/goSave?id="+ret.PK,
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
      Global.Dialog.showDialog("itemReturnDetailView",{
		  title:"查看ItemReturnDetail",
		  url:"${ctx}/admin/itemReturnDetail/goDetail?id="+ret.PK,
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
	var url = "${ctx}/admin/itemReturnDetail/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	$.form_submit($("#page_form").get(0), {
		"action": "${ctx}/admin/itemReturnDetail/doExcel"
	});
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemReturnDetail/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'PK',index : 'PK',width : 100,label:'pk',sortable : true,align : "left",key : true}
			  ,
		      {name : 'No',index : 'No',width : 100,label:'no',sortable : true,align : "left"}
			  ,
		      {name : 'AppDTPK',index : 'AppDTPK',width : 100,label:'appDtpk',sortable : true,align : "left"}
			  ,
		      {name : 'ItemCode',index : 'ItemCode',width : 100,label:'itemCode',sortable : true,align : "left"}
			  ,
		      {name : 'Qty',index : 'Qty',width : 100,label:'qty',sortable : true,align : "left"}
			  ,
		      {name : 'Remarks',index : 'Remarks',width : 100,label:'remarks',sortable : true,align : "left"}
			  ,
		      {name : 'ArriveNo',index : 'ArriveNo',width : 100,label:'arriveNo',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdate',index : 'LastUpdate',width : 100,label:'lastUpdate',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"}
			  ,
		      {name : 'Expired',index : 'Expired',width : 100,label:'expired',sortable : true,align : "left"}
			  ,
		      {name : 'ActionLog',index : 'ActionLog',width : 100,label:'actionLog',sortable : true,align : "left"}
			  
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${itemReturnDetail.expired}" />
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
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${itemReturnDetail.pk}" />
							</td>
							<td class="td-label">no</td>
							<td class="td-value">
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemReturnDetail.no}" />
							</td>
							<td class="td-label">appDtpk</td>
							<td class="td-value">
							<input type="text" id="appDtpk" name="appDtpk" style="width:160px;"  value="${itemReturnDetail.appDtpk}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">itemCode</td>
							<td class="td-value">
							<input type="text" id="itemCode" name="itemCode" style="width:160px;"  value="${itemReturnDetail.itemCode}" />
							</td>
							<td class="td-label">qty</td>
							<td class="td-value">
							<input type="text" id="qty" name="qty" style="width:160px;"  value="${itemReturnDetail.qty}" />
							</td>
							<td class="td-label">remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${itemReturnDetail.remarks}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">arriveNo</td>
							<td class="td-value">
							<input type="text" id="arriveNo" name="arriveNo" style="width:160px;"  value="${itemReturnDetail.arriveNo}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemReturnDetail.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemReturnDetail.lastUpdatedBy}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemReturnDetail.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value" colspan="3">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemReturnDetail.actionLog}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input type="checkbox" id="expired_show" name="expired_show" value="${itemReturnDetail.expired}" onclick="hideExpired(this)" ${itemReturnDetail.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
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
            	<house:authorize authCode="ITEMRETURNDETAIL_SAVE">
                	<li>
                    	<a href="#" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="ITEMRETURNDETAIL_COPY">
                	<li>
                    	<a href="#" class="a1" onclick="copy()">
					       <span>复制</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="ITEMRETURNDETAIL_UPDATE">
                	<li>
                    	<a href="#" class="a1" onclick="update()">
					       <span>修改</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="ITEMRETURNDETAIL_DELETE">
                	<li>
						<a href="#" class="a2" onclick="del()">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
				 <house:authorize authCode="ITEMRETURNDETAIL_VIEW">
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


