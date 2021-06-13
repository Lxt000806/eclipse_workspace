<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>BaseItemReq列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("baseItemReqAdd",{
		  title:"添加BaseItemReq",
		  url:"${ctx}/admin/baseItemReq/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("baseItemReqUpdate",{
		  title:"修改BaseItemReq",
		  url:"${ctx}/admin/baseItemReq/goUpdate?id="+ret.PK,
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
      Global.Dialog.showDialog("baseItemReqCopy",{
		  title:"复制BaseItemReq",
		  url:"${ctx}/admin/baseItemReq/goSave?id="+ret.PK,
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
      Global.Dialog.showDialog("baseItemReqView",{
		  title:"查看BaseItemReq",
		  url:"${ctx}/admin/baseItemReq/goDetail?id="+ret.PK,
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
	var url = "${ctx}/admin/baseItemReq/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/baseItemReq/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/baseItemReq/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'pk',index : 'pk',width : 100,label:'pk',sortable : true,align : "left",key : true},
		      {name : 'custcode',index : 'custcode',width : 100,label:'custCode',sortable : true,align : "left"},
		      {name : 'fixareapk',index : 'fixareapk',width : 100,label:'fixAreaPk',sortable : true,align : "left"},
		      {name : 'baseitemcode',index : 'baseitemcode',width : 100,label:'baseItemCode',sortable : true,align : "left"},
		      {name : 'qty',index : 'qty',width : 100,label:'qty',sortable : true,align : "left"},
		      {name : 'cost',index : 'cost',width : 100,label:'cost',sortable : true,align : "left"},
		      {name : 'unitprice',index : 'unitprice',width : 100,label:'unitPrice',sortable : true,align : "left"},
		      {name : 'lineamount',index : 'lineamount',width : 100,label:'lineAmount',sortable : true,align : "left"},
		      {name : 'remark',index : 'remark',width : 100,label:'remark',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 100,label:'lastUpdate',sortable : true,align : "left"},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'expired',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'actionLog',sortable : true,align : "left"},
		      {name : 'material',index : 'material',width : 100,label:'material',sortable : true,align : "left"},
		      {name : 'dispseq',index : 'dispseq',width : 100,label:'dispSeq',sortable : true,align : "left"},
		      {name : 'isoutset',index : 'isoutset',width : 100,label:'isOutSet',sortable : true,align : "left"},
		      {name : 'prjctrltype',index : 'prjctrltype',width : 100,label:'prjCtrlType',sortable : true,align : "left"},
		      {name : 'offerctrl',index : 'offerctrl',width : 100,label:'offerCtrl',sortable : true,align : "left"},
		      {name : 'materialctrl',index : 'materialctrl',width : 100,label:'materialCtrl',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${baseItemReq.expired}" />
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
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${baseItemReq.pk}" />
							</td>
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${baseItemReq.custCode}" />
							</td>
							<td class="td-label">fixAreaPk</td>
							<td class="td-value">
							<input type="text" id="fixAreaPk" name="fixAreaPk" style="width:160px;"  value="${baseItemReq.fixAreaPk}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">baseItemCode</td>
							<td class="td-value">
							<input type="text" id="baseItemCode" name="baseItemCode" style="width:160px;"  value="${baseItemReq.baseItemCode}" />
							</td>
							<td class="td-label">qty</td>
							<td class="td-value">
							<input type="text" id="qty" name="qty" style="width:160px;"  value="${baseItemReq.qty}" />
							</td>
							<td class="td-label">cost</td>
							<td class="td-value">
							<input type="text" id="cost" name="cost" style="width:160px;"  value="${baseItemReq.cost}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">unitPrice</td>
							<td class="td-value">
							<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"  value="${baseItemReq.unitPrice}" />
							</td>
							<td class="td-label">lineAmount</td>
							<td class="td-value">
							<input type="text" id="lineAmount" name="lineAmount" style="width:160px;"  value="${baseItemReq.lineAmount}" />
							</td>
							<td class="td-label">remark</td>
							<td class="td-value">
							<input type="text" id="remark" name="remark" style="width:160px;"  value="${baseItemReq.remark}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${baseItemReq.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${baseItemReq.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${baseItemReq.expired}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${baseItemReq.actionLog}" />
							</td>
							<td class="td-label">material</td>
							<td class="td-value">
							<input type="text" id="material" name="material" style="width:160px;"  value="${baseItemReq.material}" />
							</td>
							<td class="td-label">dispSeq</td>
							<td class="td-value">
							<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"  value="${baseItemReq.dispSeq}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">isOutSet</td>
							<td class="td-value">
							<input type="text" id="isOutSet" name="isOutSet" style="width:160px;"  value="${baseItemReq.isOutSet}" />
							</td>
							<td class="td-label">prjCtrlType</td>
							<td class="td-value">
							<input type="text" id="prjCtrlType" name="prjCtrlType" style="width:160px;"  value="${baseItemReq.prjCtrlType}" />
							</td>
							<td class="td-label">offerCtrl</td>
							<td class="td-value">
							<input type="text" id="offerCtrl" name="offerCtrl" style="width:160px;"  value="${baseItemReq.offerCtrl}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">materialCtrl</td>
							<td class="td-value" colspan="5">
							<input type="text" id="materialCtrl" name="materialCtrl" style="width:160px;"  value="${baseItemReq.materialCtrl}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input type="checkbox" id="expired_show" name="expired_show" value="${baseItemReq.expired}" onclick="hideExpired(this)" ${baseItemReq.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
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
            	<house:authorize authCode="BASEITEMREQ_ADD">
                	<li>
                    	<a href="#" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="BASEITEMREQ_COPY">
                	<li>
                    	<a href="#" class="a1" onclick="copy()">
					       <span>复制</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="BASEITEMREQ_UPDATE">
                	<li>
                    	<a href="#" class="a1" onclick="update()">
					       <span>修改</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="BASEITEMREQ_DELETE">
                	<li>
						<a href="#" class="a2" onclick="del()">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
				 <house:authorize authCode="BASEITEMREQ_VIEW">
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


