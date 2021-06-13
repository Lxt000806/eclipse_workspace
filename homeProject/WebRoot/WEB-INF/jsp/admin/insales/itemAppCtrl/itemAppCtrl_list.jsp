<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemAppCtrl列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("itemAppCtrlAdd",{
		  title:"添加ItemAppCtrl",
		  url:"${ctx}/admin/itemAppCtrl/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemAppCtrlUpdate",{
		  title:"修改ItemAppCtrl",
		  url:"${ctx}/admin/itemAppCtrl/goUpdate?id="+ret.CustType,
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
      Global.Dialog.showDialog("itemAppCtrlCopy",{
		  title:"复制ItemAppCtrl",
		  url:"${ctx}/admin/itemAppCtrl/goSave?id="+ret.CustType,
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
      Global.Dialog.showDialog("itemAppCtrlView",{
		  title:"查看ItemAppCtrl",
		  url:"${ctx}/admin/itemAppCtrl/goDetail?id="+ret.CustType,
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
	var url = "${ctx}/admin/itemAppCtrl/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	$.form_submit($("#page_form").get(0), {
		"action": "${ctx}/admin/itemAppCtrl/doExcel"
	});
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemAppCtrl/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'CustType',index : 'CustType',width : 100,label:'custType',sortable : true,align : "left",key : true}
			  ,
			  {name : 'ItemType1',index : 'ItemType1',width : 100,label:'itemType1',sortable : true,align : "left",key : true}
			  ,
		      {name : 'CanInPlan',index : 'CanInPlan',width : 100,label:'canInPlan',sortable : true,align : "left"}
			  ,
		      {name : 'CanOutPlan',index : 'CanOutPlan',width : 100,label:'canOutPlan',sortable : true,align : "left"}
			  
            ]
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
							<td class="td-label">custType</td>
							<td class="td-value">
							<input type="text" id="custType" name="custType" style="width:160px;"  value="${itemAppCtrl.custType}" />
							</td>
							<td class="td-label">itemType1</td>
							<td class="td-value">
							<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemAppCtrl.itemType1}" />
							</td>
							<td class="td-label">canInPlan</td>
							<td class="td-value">
							<input type="text" id="canInPlan" name="canInPlan" style="width:160px;"  value="${itemAppCtrl.canInPlan}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">canOutPlan</td>
							<td class="td-value" colspan="5">
							<input type="text" id="canOutPlan" name="canOutPlan" style="width:160px;"  value="${itemAppCtrl.canOutPlan}" />
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
			<!--panelBar-->
			<div class="panelBar">
            	<ul>
            	<house:authorize authCode="ITEMAPPCTRL_SAVE">
                	<li>
                    	<a href="#" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="ITEMAPPCTRL_COPY">
                	<li>
                    	<a href="#" class="a1" onclick="copy()">
					       <span>复制</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="ITEMAPPCTRL_UPDATE">
                	<li>
                    	<a href="#" class="a1" onclick="update()">
					       <span>修改</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="ITEMAPPCTRL_DELETE">
                	<li>
						<a href="#" class="a2" onclick="del()">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
				 <house:authorize authCode="ITEMAPPCTRL_VIEW">
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


