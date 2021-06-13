<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>SplCheckOut列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("splCheckOutAdd",{
		  title:"添加SplCheckOut",
		  url:"${ctx}/admin/splCheckOut/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("splCheckOutUpdate",{
		  title:"修改SplCheckOut",
		  url:"${ctx}/admin/splCheckOut/goUpdate?id="+ret.No,
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
      Global.Dialog.showDialog("splCheckOutCopy",{
		  title:"复制SplCheckOut",
		  url:"${ctx}/admin/splCheckOut/goSave?id="+ret.No,
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
      Global.Dialog.showDialog("splCheckOutView",{
		  title:"查看SplCheckOut",
		  url:"${ctx}/admin/splCheckOut/goDetail?id="+ret.No,
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
	var url = "${ctx}/admin/splCheckOut/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	$.form_submit($("#page_form").get(0), {
		"action": "${ctx}/admin/splCheckOut/doExcel"
	});
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/splCheckOut/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'No',index : 'No',width : 100,label:'no',sortable : true,align : "left",key : true}
			  ,
		      {name : 'SplCode',index : 'SplCode',width : 100,label:'splCode',sortable : true,align : "left"}
			  ,
		      {name : 'Date',index : 'Date',width : 100,label:'date',sortable : true,align : "left"}
			  ,
		      {name : 'PayType',index : 'PayType',width : 100,label:'payType',sortable : true,align : "left"}
			  ,
		      {name : 'BeginDate',index : 'BeginDate',width : 100,label:'beginDate',sortable : true,align : "left"}
			  ,
		      {name : 'EndDate',index : 'EndDate',width : 100,label:'endDate',sortable : true,align : "left"}
			  ,
		      {name : 'PayAmount',index : 'PayAmount',width : 100,label:'payAmount',sortable : true,align : "left"}
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
		      {name : 'Remark',index : 'Remark',width : 100,label:'remark',sortable : true,align : "left"}
			  ,
		      {name : 'OtherCost',index : 'OtherCost',width : 100,label:'otherCost',sortable : true,align : "left"}
			  ,
		      {name : 'status',index : 'status',width : 100,label:'status',sortable : true,align : "left"}
			  ,
		      {name : 'ConfirmCZY',index : 'ConfirmCZY',width : 100,label:'confirmCzy',sortable : true,align : "left"}
			  ,
		      {name : 'ConfirmDate',index : 'ConfirmDate',width : 100,label:'confirmDate',sortable : true,align : "left"}
			  ,
		      {name : 'DocumentNo',index : 'DocumentNo',width : 100,label:'documentNo',sortable : true,align : "left"}
			  ,
		      {name : 'PayCZY',index : 'PayCZY',width : 100,label:'payCzy',sortable : true,align : "left"}
			  ,
		      {name : 'PayDate',index : 'PayDate',width : 100,label:'payDate',sortable : true,align : "left"}
			  ,
		      {name : 'PaidAmount',index : 'PaidAmount',width : 100,label:'paidAmount',sortable : true,align : "left"}
			  ,
		      {name : 'NowAmount',index : 'NowAmount',width : 100,label:'nowAmount',sortable : true,align : "left"}
			  ,
		      {name : 'PreAmount',index : 'PreAmount',width : 100,label:'preAmount',sortable : true,align : "left"}
			  
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${splCheckOut.expired}" />
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
							<input type="text" id="no" name="no" style="width:160px;"  value="${splCheckOut.no}" />
							</td>
							<td class="td-label">splCode</td>
							<td class="td-value">
							<input type="text" id="splCode" name="splCode" style="width:160px;"  value="${splCheckOut.splCode}" />
							</td>
							<td class="td-label">date</td>
							<td class="td-value">
							<input type="text" id="date" name="date" style="width:160px;"  value="${splCheckOut.date}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">payType</td>
							<td class="td-value">
							<input type="text" id="payType" name="payType" style="width:160px;"  value="${splCheckOut.payType}" />
							</td>
							<td class="td-label">beginDate</td>
							<td class="td-value">
							<input type="text" id="beginDate" name="beginDate" style="width:160px;"  value="${splCheckOut.beginDate}" />
							</td>
							<td class="td-label">endDate</td>
							<td class="td-value">
							<input type="text" id="endDate" name="endDate" style="width:160px;"  value="${splCheckOut.endDate}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">payAmount</td>
							<td class="td-value">
							<input type="text" id="payAmount" name="payAmount" style="width:160px;"  value="${splCheckOut.payAmount}" />
							</td>
							<td class="td-label">remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${splCheckOut.remarks}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${splCheckOut.lastUpdate}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${splCheckOut.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${splCheckOut.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${splCheckOut.actionLog}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">remark</td>
							<td class="td-value">
							<input type="text" id="remark" name="remark" style="width:160px;"  value="${splCheckOut.remark}" />
							</td>
							<td class="td-label">otherCost</td>
							<td class="td-value">
							<input type="text" id="otherCost" name="otherCost" style="width:160px;"  value="${splCheckOut.otherCost}" />
							</td>
							<td class="td-label">status</td>
							<td class="td-value">
							<input type="text" id="status" name="status" style="width:160px;"  value="${splCheckOut.status}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">confirmCzy</td>
							<td class="td-value">
							<input type="text" id="confirmCzy" name="confirmCzy" style="width:160px;"  value="${splCheckOut.confirmCzy}" />
							</td>
							<td class="td-label">confirmDate</td>
							<td class="td-value">
							<input type="text" id="confirmDate" name="confirmDate" style="width:160px;"  value="${splCheckOut.confirmDate}" />
							</td>
							<td class="td-label">documentNo</td>
							<td class="td-value">
							<input type="text" id="documentNo" name="documentNo" style="width:160px;"  value="${splCheckOut.documentNo}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">payCzy</td>
							<td class="td-value">
							<input type="text" id="payCzy" name="payCzy" style="width:160px;"  value="${splCheckOut.payCzy}" />
							</td>
							<td class="td-label">payDate</td>
							<td class="td-value">
							<input type="text" id="payDate" name="payDate" style="width:160px;"  value="${splCheckOut.payDate}" />
							</td>
							<td class="td-label">paidAmount</td>
							<td class="td-value">
							<input type="text" id="paidAmount" name="paidAmount" style="width:160px;"  value="${splCheckOut.paidAmount}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">nowAmount</td>
							<td class="td-value">
							<input type="text" id="nowAmount" name="nowAmount" style="width:160px;"  value="${splCheckOut.nowAmount}" />
							</td>
							<td class="td-label">preAmount</td>
							<td class="td-value" colspan="3">
							<input type="text" id="preAmount" name="preAmount" style="width:160px;"  value="${splCheckOut.preAmount}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input type="checkbox" id="expired_show" name="expired_show" value="${splCheckOut.expired}" onclick="hideExpired(this)" ${splCheckOut.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
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
            	<house:authorize authCode="SPLCHECKOUT_SAVE">
                	<li>
                    	<a href="#" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="SPLCHECKOUT_COPY">
                	<li>
                    	<a href="#" class="a1" onclick="copy()">
					       <span>复制</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="SPLCHECKOUT_UPDATE">
                	<li>
                    	<a href="#" class="a1" onclick="update()">
					       <span>修改</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="SPLCHECKOUT_DELETE">
                	<li>
						<a href="#" class="a2" onclick="del()">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
				 <house:authorize authCode="SPLCHECKOUT_VIEW">
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


