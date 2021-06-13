<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>CustPayPre列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("custPayPreAdd",{
		  title:"添加CustPayPre",
		  url:"${ctx}/admin/custPayPre/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("custPayPreUpdate",{
		  title:"修改CustPayPre",
		  url:"${ctx}/admin/custPayPre/goUpdate?id="+ret.PK,
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
      Global.Dialog.showDialog("custPayPreCopy",{
		  title:"复制CustPayPre",
		  url:"${ctx}/admin/custPayPre/goSave?id="+ret.PK,
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
      Global.Dialog.showDialog("custPayPreView",{
		  title:"查看CustPayPre",
		  url:"${ctx}/admin/custPayPre/goDetail?id="+ret.PK,
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
	var url = "${ctx}/admin/custPayPre/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	$.form_submit($("#page_form").get(0), {
		"action": "${ctx}/admin/custPayPre/doExcel"
	});
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custPayPre/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'pk',index : 'pk',width : 100,label:'pk',sortable : true,align : "left",key : true},
		      {name : 'custcode',index : 'custcode',width : 100,label:'custCode',sortable : true,align : "left"},
		      {name : 'paytype',index : 'paytype',width : 100,label:'payType',sortable : true,align : "left"},
		      {name : 'baseper',index : 'baseper',width : 100,label:'basePer',sortable : true,align : "left"},
		      {name : 'itemper',index : 'itemper',width : 100,label:'itemPer',sortable : true,align : "left"},
		      {name : 'designfee',index : 'designfee',width : 100,label:'designFee',sortable : true,align : "left"},
		      {name : 'prepay',index : 'prepay',width : 100,label:'prePay',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 100,label:'lastUpdate',sortable : true,align : "left"},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'expired',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'actionLog',sortable : true,align : "left"},
		      {name : 'basepay',index : 'basepay',width : 100,label:'basePay',sortable : true,align : "left"},
		      {name : 'itempay',index : 'itempay',width : 100,label:'itemPay',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${custPayPre.expired}" />
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
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${custPayPre.pk}" />
							</td>
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${custPayPre.custCode}" />
							</td>
							<td class="td-label">payType</td>
							<td class="td-value">
							<input type="text" id="payType" name="payType" style="width:160px;"  value="${custPayPre.payType}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">basePer</td>
							<td class="td-value">
							<input type="text" id="basePer" name="basePer" style="width:160px;"  value="${custPayPre.basePer}" />
							</td>
							<td class="td-label">itemPer</td>
							<td class="td-value">
							<input type="text" id="itemPer" name="itemPer" style="width:160px;"  value="${custPayPre.itemPer}" />
							</td>
							<td class="td-label">designFee</td>
							<td class="td-value">
							<input type="text" id="designFee" name="designFee" style="width:160px;"  value="${custPayPre.designFee}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">prePay</td>
							<td class="td-value">
							<input type="text" id="prePay" name="prePay" style="width:160px;"  value="${custPayPre.prePay}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${custPayPre.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${custPayPre.lastUpdatedBy}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${custPayPre.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${custPayPre.actionLog}" />
							</td>
							<td class="td-label">basePay</td>
							<td class="td-value">
							<input type="text" id="basePay" name="basePay" style="width:160px;"  value="${custPayPre.basePay}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">itemPay</td>
							<td class="td-value" colspan="5">
							<input type="text" id="itemPay" name="itemPay" style="width:160px;"  value="${custPayPre.itemPay}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input type="checkbox" id="expired_show" name="expired_show" value="${custPayPre.expired}" onclick="hideExpired(this)" ${custPayPre.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
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
            	<house:authorize authCode="CUSTPAYPRE_ADD">
                	<li>
                    	<a href="#" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="CUSTPAYPRE_COPY">
                	<li>
                    	<a href="#" class="a1" onclick="copy()">
					       <span>复制</span>
						</a>	
                    </li>
                </house:authorize>
				<house:authorize authCode="CUSTPAYPRE_UPDATE">
                	<li>
                    	<a href="#" class="a1" onclick="update()">
					       <span>修改</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="CUSTPAYPRE_DELETE">
                	<li>
						<a href="#" class="a2" onclick="del()">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
				 <house:authorize authCode="CUSTPAYPRE_VIEW">
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


