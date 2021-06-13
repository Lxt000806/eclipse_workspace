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
	<title>到货单查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	
function doExcel(){
	var url = "${ctx}/admin/purchArr/doPurchArrExcel";
	doExcelAll(url);
}	
	
$(function(){
	$("#whcode").openComponent_wareHouse();	
	$("#itCode").openComponent_item();	
	$("#custCode").openComponent_customer({condition:{purchStatus:'1'}});	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	//初始化表格
	
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchArr/goPurchArrJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
				{name: "custcode", index: "custcode", width: 104, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 164, label: "楼盘", sortable: true, align: "left"},
				{name: "no", index: "no", width: 110, label: "到货单号", sortable: true, align: "left"},
				{name: "puno", index: "puno", width: 100, label: "采购单号", sortable: true, align: "left"},
				{name: "date", index: "date", width: 143, label: "到货日期", sortable: true, align: "left", formatter: formatTime},
				{name: "itemtype1descr", index: "itemtype1descr", width: 93, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 90, label: "材料类型2", sortable: true, align: "left"},
				{name: "whdescr", index: "whdescr", width: 131, label: "仓库", sortable: true, align: "left"},
				{name: "itcode", index: "itcode", width: 75, label: "材料编号", sortable: true, align: "left"},
				{name: "itdescr", index: "itdescr", width: 123, label: "材料名称", sortable: true, align: "left"},
				{name: "unitdescr", index: "unitdescr", width: 55, label: "单位", sortable: true, align: "left"},
				{name: "sqlcodedescr", index: "sqlcodedescr", width: 90, label: "品牌", sortable: true, align: "left"},
				{name: "qtycal", index: "qtycal", width: 71, label: "采购数量", sortable: true, align: "left"},
				{name: "cost", index: "cost", width: 71, label: "成本单价", sortable: true, align: "left"},
				{name: "arrivqty", index: "arrivqty", width: 72, label: "到货数量", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 247, label: "到货备注", sortable: true, align: "left"}
				],
		
	});
	if('${costRight}'=='0'){
		jQuery("#dataTable").setGridParam().hideCol("cost").trigger("reloadGrid");
	}


});
 function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$("#remarks").val('');
	$("#address").val('');
	$("#page_form input[type='text']").val('');
	$("#splStatusDescr").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_splStatusDescr").checkAllNodes(false);
} 
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="costRight"  name="costRight" value="${costRight }"/>
					<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
								<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${purchArr.custCode }"/>
							</li>
								<li>
								<label>到货单号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${purchArr.no }"/>
							</li>
								<li>
								<label>采购单号</label>
								<input type="text" id="puno" name="puno" style="width:160px;" value="${purchArr.puno }"/>
							</li>
								<li>
								<label>材料编号</label>
								<input type="text" id="itCode" name="itCode" style="width:160px;" value="${purchArr.itCode }"/>
							</li>
								<li>
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width: 160px;"  ></select>
							</li>
								<li>
								<label>到货日期</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
								<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
								<li>
								<label>仓库</label>
								<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchArr.whcode }"/>
							</li>
								<li>
								<label>到货备注</label>
								<input type="text" id="remarks" name="remarks" style="width:160px;" value="${purchArr.remarks }"/>
							</li>
							<li class="search-group">
								<input type="checkbox" id="expired_show" name="expired_show" value="${purchArr.expired }" onclick="hideExpired(this)" ${purchase.expired!='T'?'checked':'' }/><p>隐藏过期</p>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="doExcel();"  >导出Excel</button>
							</li>							
							</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
		
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
