<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemApp明细查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript">
var parentWin=window.opener;
$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-105,
		styleUI: 'Bootstrap',
		colModel : [
		  {name : 'confirmdate', index: 'confirmdate', width: 75, label: '审批日期', sortable: true, align: "left", formatter: formatTime},
		  {name : 'no', index: 'no', width: 80, label: '领料单号', sortable: true, align: "left"},
		  {name : 'itemcode',index : 'itemcode',width : 60,label:'材料编号',align : "left",count:true},
		  {name : 'itemdescr',index : 'itemdescr',width : 150,label:'材料名称',align : "left"},
		  {name : 'qty',index : 'qty',width : 40,label:'数量',align : "right",sum:true},
		  {name : 'uomdescr',index : 'uomdescr',width : 60,label:'单位',align : "left"},
		  {name : 'projectcost',index : 'projectcost',width : 110,label:'项目经理结算价',align : "right",sum:true,hidden:true},
		  {name : 'processcost',index : 'processcost',width : 90,label:'其他费用',align : "right",sum:true},
		  {name : 'projectallcost',index : 'projectallcost',width : 90,label:'结算总价',align : "right",sum:true,hidden:true},
		  {name : 'whcodedescr',index : 'whcodedescr', width: 80, label: '仓库名称', sortable: true, align: "left"},
		  {name : 'remarks',index : 'remarks',width : 120,label:'备注',align : "left"},
		  {name : 'projectman',index : 'projectman',width : 60,label:'项目经理',align : "left"},
		  {name : 'phone',index : 'phone',width : 100,label:'电话',align : "left"},
		  {name : 'address',index : 'address',width : 120,label:'楼盘',align : "left"},
		  {name : 'documentno',index : 'documentno',width : 80,label:'编号',align : "left"}
          ],
          
	});
	if ($.trim("${projectCostRight}") == "1") {
		$("#dataTable").jqGrid("showCol", "projectcost");
		$("#dataTable").jqGrid("showCol", "projectallcost");
	}
	
	$("#whcode").openComponent_wareHouse({condition: {czybh:'${czybh}'}});
});

function goto_query(){
	var tableId = 'dataTable';	
	$("#"+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/supplierItemApp/goJqGridDetailList",postData:$("#page_form").jsonForm(),page:1,}).trigger("reloadGrid");
}

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/supplierItemApp/doExcelDetailList";
	doExcelAll(url);
} 

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$("#splStatus").val('');
	$.fn.zTree.getZTreeObj("tree_splStatus").checkAllNodes(false);
}
	
</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-list">
	<div class="panel panel-system">
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
	      	<button type="button" class="btn btn-system " onclick="doExcel()">导出Excel</button>
	      	<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      	</div>
	   </div>
	</div>
	
    <div class="query-form">
    	<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
    		<input type="hidden" id="expired" name="expired" value="${itemApp.expired }"/>
            <input type="hidden" id="module" name="module" value="SupplierItemApp"/>
			<input type="hidden" name="jsonString" value="" />
			
			<ul class="ul-form">
				<li>
					<label>审核日期</label>
					<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date"
	                    style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
	                    value="<fmt:formatDate value='${itemApp.confirmDateFrom}' pattern='yyyy-MM-dd'/>"/>
                </li>	
				<li>
					<label>至</label>
					<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" style="width:160px;"
	                    onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
	                    value="<fmt:formatDate value='${itemApp.confirmDateTo}' pattern='yyyy-MM-dd'/>"/>
                </li>
				<li>
					<label>单据状态</label>
					<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='ITEMAPPSTATUS' and (CBM='CONFIRMED' or CBM='SEND')" selectedValue="CONFIRMED,SEND"></house:xtdmMulit>
				</li>
				<li>
					<label>发货日期</label>
					<input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date"
	                    style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
	                    value="<fmt:formatDate value='${itemApp.sendDateFrom}' pattern='yyyy-MM-dd'/>"/>
                </li>
				<li>
					<label>至</label>
					<input type="text" id="sendDateTo" name="sendDateTo" class="i-date" style="width:160px;"
	                    onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
	                    value="<fmt:formatDate value='${itemApp.sendDateTo}' pattern='yyyy-MM-dd'/>"/>
                </li>
				<li>
				<label>供应商状态</label>
					<house:xtdmMulit id="splStatus" dictCode="APPSPLSTATUS" unShowValue="0,1"></house:xtdmMulit>
				</li> 
				<li>
				<label>楼盘</label>
					<input type="text" id="custAddress" name="custAddress" style="width:160px;"/>
				</li>
				<li>
				<label>仓库编号</label>
					<input type="text" id="whcode" name="whcode" style="width:160px;" value="${itemApp.whcode}"/>
				</li>
				<li>
				<label>发货类型</label>
					<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="${itemApp.sendType}"></house:xtdm>
				</li>
				<li id="loadMore" >
					<button type="button" class="btn btn-system btn-sm" onclick="goto_query();" >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();" >清空</button>
				</li>
			</ul>
    	</form>
    </div>

    <div id="content-list">
        <table id="dataTable"></table>
        <div id="dataTablePager"></div>
    </div>
</div>
</body>
</html>
