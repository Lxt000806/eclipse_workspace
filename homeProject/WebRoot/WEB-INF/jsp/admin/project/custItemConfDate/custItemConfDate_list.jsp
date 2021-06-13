<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>CustItemConfDate列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("custItemConfDateAdd",{
		  title:"添加CustItemConfDate",
		  url:"${ctx}/admin/custItemConfDate/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("custItemConfDateUpdate",{
		  title:"修改CustItemConfDate",
		  url:"${ctx}/admin/custItemConfDate/goUpdate?id="+ret.CustCode,
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
      Global.Dialog.showDialog("custItemConfDateCopy",{
		  title:"复制CustItemConfDate",
		  url:"${ctx}/admin/custItemConfDate/goSave?id="+ret.CustCode,
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
      Global.Dialog.showDialog("custItemConfDateView",{
		  title:"查看CustItemConfDate",
		  url:"${ctx}/admin/custItemConfDate/goDetail?id="+ret.CustCode,
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
	var url = "${ctx}/admin/custItemConfDate/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	$.form_submit($("#page_form").get(0), {
		"action": "${ctx}/admin/custItemConfDate/doExcel"
	});
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custItemConfDate/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'custcode',index : 'custcode',width : 100,label:'custCode',sortable : true,align : "left",key : true},
			  {name : 'itemtimecode',index : 'itemtimecode',width : 100,label:'itemTimeCode',sortable : true,align : "left",key : true},
		      {name : 'confirmdate',index : 'confirmdate',width : 100,label:'confirmDate',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 100,label:'lastUpdate',sortable : true,align : "left"},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'actionLog',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'expired',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${custItemConfDate.expired}" />
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
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${custItemConfDate.custCode}" />
							</td>
							<td class="td-label">itemTimeCode</td>
							<td class="td-value">
							<input type="text" id="itemTimeCode" name="itemTimeCode" style="width:160px;"  value="${custItemConfDate.itemTimeCode}" />
							</td>
							<td class="td-label">confirmDate</td>
							<td class="td-value">
							<input type="text" id="confirmDate" name="confirmDate" style="width:160px;"  value="${custItemConfDate.confirmDate}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${custItemConfDate.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${custItemConfDate.lastUpdatedBy}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${custItemConfDate.actionLog}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">expired</td>
							<td class="td-value" colspan="5">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${custItemConfDate.expired}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input type="checkbox" id="expired_show" name="expired_show" value="${custItemConfDate.expired}" onclick="hideExpired(this)" ${custItemConfDate.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
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
            	<house:authorize authCode="CUSTITEMCONFDATE_ADD">
                	<li>
                    	<a href="#" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="CUSTITEMCONFDATE_COPY">
                	<li>
                    	<a href="#" class="a1" onclick="copy()">
					       <span>复制</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="CUSTITEMCONFDATE_UPDATE">
                	<li>
                    	<a href="#" class="a1" onclick="update()">
					       <span>修改</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="CUSTITEMCONFDATE_DELETE">
                	<li>
						<a href="#" class="a2" onclick="del()">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
				 <house:authorize authCode="CUSTITEMCONFDATE_VIEW">
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


