<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>仓库滞销品分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemWHBal/goJqGrid_ckzxp",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'itcode',index : 'itcode',width : 100,label:'材料编号',sortable : true,align : "left"},
		      {name : 'itcodedescr',index : 'itcodedescr',width : 200,label:'材料名称',sortable : true,align : "left"},
		      {name : 'qcqty',index : 'qcqty',width :100,label:'期初数量',sortable : true,align : "right",sum:true},
		      {name : 'qcje',index : 'qcje',width : 100,label:'期初金额',sortable : true,align : "right",sum:true},
		      {name : 'rkqty',index : 'rkqty',width : 100,label:'入库数量',sortable : true,align : "right",sum:true},
		      {name : 'rkje',index : 'rkje',width : 100,label:'入库金额',sortable : true,align : "right",sum:true},
		      {name : 'ckqty',index : 'ckqty',width : 100,label:'出库数量',sortable : true,align : "right",sum:true},
		      {name : 'ckje',index : 'ckje',width : 100,label:'出库金额',sortable : true,align : "right",sum:true},
		      {name : 'kcqty',index : 'kcqty',width : 100,label:'库存数量',sortable : true,align : "right",sum:true},
		      {name : 'kcje',index : 'kcje',width : 100,label:'库存金额',sortable : true,align : "right",sum:true},
		      {name : 'zxqty',index : 'zxqty',width : 100,label:'滞销数量',sortable : true,align : "right",sum:true},
		      {name : 'zxje',index : 'zxje',width : 100,label:'滞销金额',sortable : true,align : "right",sum:true}
            ]
		});
});
function goto_query(){
	if ($("#whCode").val()==''){
		art.dialog({
			content: "请选择仓库",
			width: 200
		});
		return;
	}
	if ($("#dateFrom").val()=='' || $("#dateTo").val()==''){
		art.dialog({
			content: "请选择变动日期",
			width: 200
		});
		return;
	}
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	var str_itcode = $("#jqgh_dataTable_itcode").html();
	var str_itcodedescr = $("#jqgh_dataTable_itcodedescr").html();
	if ($("#tjfs").val()=='0'){
		$("#jqgh_dataTable_itcode").html(str_itcode.replace('材料类型2编号','材料编号'));
		$("#jqgh_dataTable_itcodedescr").html(str_itcodedescr.replace('材料类型2名称','材料名称'));
	}else{
		$("#jqgh_dataTable_itcode").html(str_itcode.replace('材料编号','材料类型2编号'));
		$("#jqgh_dataTable_itcodedescr").html(str_itcodedescr.replace('材料名称','材料类型2名称'));
	}
	
}
function clearForm(){
	$("#page_form select").val('');
	$("#whCode").val('');
	$("#whCode_NAME").val('');
	$.fn.zTree.getZTreeObj("tree_whCode").checkAllNodes(false);
	$("#tjfs").val("0");
}
function printCkzxp(){
   	var reportName = "ckzxp.jasper";
   	Global.Print.showPrint(reportName, {
		itemType1: $("#itemType1").val(),
		itemType2: $("#itemType2").val(),
		itCode: $("#itemType2").val(),
		whCode: $("#whCode").val(),
		dateFrom: $("#dateFrom").val(),
		dateTo: $("#dateTo").val(),
		LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
		SUBREPORT_DIR: "<%=jasperPath%>" 
	});
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/itemWHBal/doExcel";
	doExcelAll(url);
}
</script>
</head>
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="expired" name="expired" value="${customer.expired }" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
				<div class="col-sm-4">
				<li>
					<label>材料类型1</label>
					<house:DataSelect id="itemType1" className="ItemType1" keyColumn="code" valueColumn="descr" filterValue="expired='F'" 
					value="${itemWHBal.itemType1 }" onchange="changeItemType1(this,'itemType2')"></house:DataSelect>
				</li>
				</div>
				<div class="col-sm-4">
				<li>
					<label>材料类型2</label>
					<house:DataSelect id="itemType2" className="ItemType2" keyColumn="code" valueColumn="descr" filterValue="1=2" value="${itemWHBal.itemType2 }"></house:DataSelect>
				</li>
				</div>
				<div class="col-sm-4">
				<li>
					<label>变动日期从</label>
					<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemWHBal.dateFrom }' pattern='yyyy-MM-dd'/>"/>
				</li>
				</div>
				<div class="col-sm-4">
				<li>
					<label>至</label>
					<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemWHBal.dateTo }' pattern='yyyy-MM-dd'/>"/>
				</li>
				</div>
				<div class="col-sm-4">
				<li>
					<label>仓库编号</label>
					<house:DictMulitSelect id="whCode" dictCode="" sql="select code,desc1 from tWareHouse" sqlLableKey="desc1" sqlValueKey="code" selectedValue="${itemWHBal.whCode }"></house:DictMulitSelect>
				</li>
				</div>
				<div class="col-sm-4">
				<li>
					<label>统计方式</label>
					<select id="tjfs" name="tjfs" style="width: 165px;">
						<option value="0" selected="selected">0 按材料编号</option>
						<option value="1">1 按材料类型2</option>
					</select>
				</li>
				</div>
				<div class="col-sm-4">
				<li>
					<label></label>
					<input type="checkbox" id="expired_show" name="expired_show" value="${itemWHBal.expired }" onclick="hideExpired(this)" ${itemWHBal.expired!='T'?'checked':'' }/>隐藏非滞销品&nbsp;
				</li>
				</div>
				<div class="col-sm-4">
				<li id="loadMore" >
					<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
				</li>
				</div>
			</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			 <div class="btn-panel" >
		    	<div class="btn-group-sm"  >
                    	<button type="button" class="btn btn-system " onclick="printCkzxp()">打印</button>
						<button type="button" class="btn btn-system " onclick="doExcelNow('仓库滞销品分析')">导出excel</button>
		    	</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


