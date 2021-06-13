<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemReq列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("itemReqAdd",{
		  title:"添加ItemReq",
		  url:"${ctx}/admin/itemReq/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemReqUpdate",{
		  title:"修改ItemReq",
		  url:"${ctx}/admin/itemReq/goUpdate?id="+ret.PK,
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
      Global.Dialog.showDialog("itemReqCopy",{
		  title:"复制ItemReq",
		  url:"${ctx}/admin/itemReq/goSave?id="+ret.PK,
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
      Global.Dialog.showDialog("itemReqView",{
		  title:"查看ItemReq",
		  url:"${ctx}/admin/itemReq/goDetail?id="+ret.PK,
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
	var url = "${ctx}/admin/itemReq/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	$.form_submit($("#page_form").get(0), {
		"action": "${ctx}/admin/itemReq/doExcel"
	});
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemReq/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'PK',index : 'PK',width : 100,label:'pk',sortable : true,align : "left"}
			  ,
		      {name : 'CustCode',index : 'CustCode',width : 100,label:'custCode',sortable : true,align : "left"}
			  ,
		      {name : 'FixAreaPK',index : 'FixAreaPK',width : 100,label:'fixAreaPk',sortable : true,align : "left"}
			  ,
		      {name : 'IntProdPK',index : 'IntProdPK',width : 100,label:'intProdPk',sortable : true,align : "left"}
			  ,
		      {name : 'ItemCode',index : 'ItemCode',width : 100,label:'itemCode',sortable : true,align : "left"}
			  ,
		      {name : 'ItemType1',index : 'ItemType1',width : 100,label:'itemType1',sortable : true,align : "left"}
			  ,
		      {name : 'Qty',index : 'Qty',width : 100,label:'qty',sortable : true,align : "left"}
			  ,
		      {name : 'SendQty',index : 'SendQty',width : 100,label:'sendQty',sortable : true,align : "left"}
			  ,
		      {name : 'Cost',index : 'Cost',width : 100,label:'cost',sortable : true,align : "left"}
			  ,
		      {name : 'UnitPrice',index : 'UnitPrice',width : 100,label:'unitPrice',sortable : true,align : "left"}
			  ,
		      {name : 'BefLineAmount',index : 'BefLineAmount',width : 100,label:'befLineAmount',sortable : true,align : "left"}
			  ,
		      {name : 'Markup',index : 'Markup',width : 100,label:'markup',sortable : true,align : "left"}
			  ,
		      {name : 'LineAmount',index : 'LineAmount',width : 100,label:'lineAmount',sortable : true,align : "left"}
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
			  ,
		      {name : 'ProcessCost',index : 'ProcessCost',width : 100,label:'processCost',sortable : true,align : "left"}
			  ,
		      {name : 'DispSeq',index : 'DispSeq',width : 100,label:'dispSeq',sortable : true,align : "left"}
			  ,
		      {name : 'IsService',index : 'IsService',width : 100,label:'isService',sortable : true,align : "left"}
			  ,
		      {name : 'IsCommi',index : 'IsCommi',width : 100,label:'isCommi',sortable : true,align : "left"}
			  
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${itemReq.expired}" />
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
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${itemReq.pk}" />
							</td>
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${itemReq.custCode}" />
							</td>
							<td class="td-label">fixAreaPk</td>
							<td class="td-value">
							<input type="text" id="fixAreaPk" name="fixAreaPk" style="width:160px;"  value="${itemReq.fixAreaPk}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">intProdPk</td>
							<td class="td-value">
							<input type="text" id="intProdPk" name="intProdPk" style="width:160px;"  value="${itemReq.intProdPk}" />
							</td>
							<td class="td-label">itemCode</td>
							<td class="td-value">
							<input type="text" id="itemCode" name="itemCode" style="width:160px;"  value="${itemReq.itemCode}" />
							</td>
							<td class="td-label">itemType1</td>
							<td class="td-value">
							<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemReq.itemType1}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">qty</td>
							<td class="td-value">
							<input type="text" id="qty" name="qty" style="width:160px;"  value="${itemReq.qty}" />
							</td>
							<td class="td-label">sendQty</td>
							<td class="td-value">
							<input type="text" id="sendQty" name="sendQty" style="width:160px;"  value="${itemReq.sendQty}" />
							</td>
							<td class="td-label">cost</td>
							<td class="td-value">
							<input type="text" id="cost" name="cost" style="width:160px;"  value="${itemReq.cost}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">unitPrice</td>
							<td class="td-value">
							<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"  value="${itemReq.unitPrice}" />
							</td>
							<td class="td-label">befLineAmount</td>
							<td class="td-value">
							<input type="text" id="befLineAmount" name="befLineAmount" style="width:160px;"  value="${itemReq.befLineAmount}" />
							</td>
							<td class="td-label">markup</td>
							<td class="td-value">
							<input type="text" id="markup" name="markup" style="width:160px;"  value="${itemReq.markup}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lineAmount</td>
							<td class="td-value">
							<input type="text" id="lineAmount" name="lineAmount" style="width:160px;"  value="${itemReq.lineAmount}" />
							</td>
							<td class="td-label">remark</td>
							<td class="td-value">
							<input type="text" id="remark" name="remark" style="width:160px;"  value="${itemReq.remark}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemReq.lastUpdate}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemReq.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemReq.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemReq.actionLog}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">processCost</td>
							<td class="td-value">
							<input type="text" id="processCost" name="processCost" style="width:160px;"  value="${itemReq.processCost}" />
							</td>
							<td class="td-label">dispSeq</td>
							<td class="td-value">
							<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"  value="${itemReq.dispSeq}" />
							</td>
							<td class="td-label">isService</td>
							<td class="td-value">
							<input type="text" id="isService" name="isService" style="width:160px;"  value="${itemReq.isService}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">isCommi</td>
							<td class="td-value" colspan="5">
							<input type="text" id="isCommi" name="isCommi" style="width:160px;"  value="${itemReq.isCommi}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input type="checkbox" id="expired_show" name="expired_show" value="${itemReq.expired}" onclick="hideExpired(this)" ${itemReq.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
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
            	<house:authorize authCode="ITEMREQ_SAVE">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="ITEMREQ_COPY">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="copy()">
					       <span>复制</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="ITEMREQ_UPDATE">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="update()">
					       <span>修改</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="ITEMREQ_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a2" onclick="del()">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
				 <house:authorize authCode="ITEMREQ_VIEW">
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


