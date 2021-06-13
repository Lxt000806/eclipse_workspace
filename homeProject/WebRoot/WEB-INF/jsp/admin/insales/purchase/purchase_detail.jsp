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
	<title>采购明细查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
var itemtype1;
function doExcel(){
	var url = "${ctx}/admin/purchase/doDetailExcel";
	doExcelAll(url);
}	
$(function(){
	$("#whcode").openComponent_wareHouse({condition: {czybh:'${czybh}'}});	
	$("#supplier").openComponent_supplier();	
	$("#department2").openComponent_department2();	
	$("#itCode").openComponent_item();	
	$("#sino").openComponent_salesInvoice();	
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchase/goDetailJqGrid",
		postData:{status:'${purchase.status}'},
		height:450,
		colModel : [
			{name:'no',			index:'no',			width:90,	label:'采购单号',	sortable:true,align:"left",cellattr: 'addCellAttr',count:true},
			{name:'status', 	index:'status',		width:80,	label:'采购单状态',sortable:true,align:"left",hidden:true,},
			{name:'itcode', 	index:'itcode',		width:80,	label:'itcode',sortable:true,align:"left",hidden:true},
			{name:'statusdescr', 	index:'statusdescr',		width:80,	label:'采购单状态',sortable:true,align:"left",},
			{name:'date',		index:'date',		width:120,	label:'采购日期',	sortable:true,align:"left",	formatter: formatDate},
			{name:'type',		index:'type',		width:60,	label:'采购类型',	sortable:true,align:"left",hidden:true,},
			{name:'typedescr',	index:'typedescr',	width:60,	label:'采购类型',	sortable:true,align:"left",},
			{name:'itemtypedescr',index:'itemtypedescr',			width:90,	label:'材料类型1',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'delivtype',	index:'delivtype',	width:50,	label:'配送地点',		sortable:true,align:"left",hidden:true}, 
			{name:'delivtypedescr',	index:'delivtypedescr',	width:60,	label:'配送地点',		sortable:true,align:"left",}, 
		 	{name:'whcode',		index:'whcode',width:80,	label:'仓库编号',	sortable:true,	align:"left",hidden:true	},
		 	{name:'whcodedescr',index:'whcodedescr',width:135,	label:'仓库名称',	sortable:true,	align:"left",},
			{name:'custdescr',	index:'custdescr',	width:90,	label:'客户名称',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'address',	index:'address',	width:130,	label:'楼盘地址',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'supdescr',	index:'supdescr',	width:90,	label:'供应商名称',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name: "itcode", index: "itcode", width: 80, label: "材料编号 ", sortable: true, align: "left", sum: true},
			{name: "itdescr", index: "itdescr", width: 160, label: "材料名称", sortable: true, align: "left", sum: true},
			{name: "qtycal", index: "qtycal", width: 80, label: "数量", sortable: true, align: "right", sum: true},
			{name: "arrivqty", index: "arrivqty", width: 80, label: "已到货数量", sortable: true, align: "right", sum: true},
			{name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "right", },
			{name: "amount", index: "amount", width: 80, label: "总价", sortable: true, align: "right", sum: true},
			{name:'remarks',	index:'remarks',	width:290,	label:'备注',		sortable:true,align:"left",}, 
		],
	});
		if('${costRight}'=='0'){
			jQuery("#dataTable").setGridParam().hideCol("unitprice").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("amount").trigger("reloadGrid");
		}
	
});
function getItemType1(){
	 itemtype1 = $.trim($("#itemType1").val());
	 if(itemtype1!=''){
	 	$("#itCode").openComponent_item({condition:{itemType1:itemtype1,readonly:'1'}});
	 }else{
	 	$("#itCode").openComponent_item();
	 }
}
function query(){
	var department2= $.trim($('#department2').val());
	var dateFrom1= $.trim($('#dateFrom1').val());
	var dateTo1= $.trim($('#dateTo1').val());
	if(department2!=''&&department2!=null){
		if(dateFrom1==''||dateFrom1==null){
			art.dialog({
				content:'请选择采购日期',
			});
			return false;
		}
		if(dateTo1==''||dateTo1==null){
			art.dialog({
				content:'请选择采购日期',
			});
			return false;
		}
	}
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}

 function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$("#remarks").val('');
	$("#address").val('');
	$("#page_form input[type='text']").val('');
	$("#splStatusDescr").val('');
	//$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_splStatusDescr").checkAllNodes(false);
} 
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
								<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
									<span>关闭</span>
								</button>
					</div>
				</div>
			</div>
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value="${purchase.expired }"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">	
							</li>
								<li>
								<label>采购单号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }"/>
							</li>
								<li>
								<label>供应商编号</label>
								<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier }"/>
							</li>
								<li>
								<label>采购单状态</label>
								<house:xtdmMulit id="status" dictCode="PURCHSTATUS" selectedValue="${purchase.status}"></house:xtdmMulit>                     
							</li>
								<li>
								<label>仓库编号</label>
								<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }"/>
							</li>
								<li>
								<label>采购类型</label>
							<house:xtdm id="type" dictCode="PURCHTYPE"  style="width:160px;" ></house:xtdm>
							</li>
								<li>
								<label>是否结账</label>
								<house:xtdm id="isCheckOut" dictCode="YESNO"  style="width:160px;"></house:xtdm>
							</li>
								<li>
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" onchange="getItemType1()" style="width: 160px;"  ></select>
							</li>
							</li>
								<li>
								<label>材料类型2</label>
								<select id="itemType2" name="itemType2"   style="width: 160px;"  ></select>
							</li>
								<li>
								<label>材料编号</label>
								<input type="text" id="itCode" name="itCode"  style="width:160px;" value="${purchase.itCode }" />
							</li>
								<li>
								<label>采购日期</label>
								<input type="text" id="dateFrom1" name="dateFrom1" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
								<li>
								<label>至</label>
								<input type="text" id="dateTo1" name="dateTo1" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
								<li>
								<label>结算单状态</label>
								<house:xtdmMulit  id="splStatusDescr" dictCode="SPLCKOTSTATUS" selectedValue="${purchase.splStatusDescr}"></house:xtdmMulit>                     
 							</li>
								<li>
								<label>配送方式</label>
								<house:xtdm  id="delivType" dictCode="PURCHDELIVTYPE"   style="width:160px;" value="${purchase.delivType}"></house:xtdm>
							</li>
								<li>
								<label>楼盘地址</label>
								<input type="text" id="address" name="address"  style="width:160px;" value="${purchase.address }" />
							</li>
								<li>
								<label>归属部门</label>
								<input type="text" id="department2" name="department2"  style="width:160px;" value="${purchase.department2}" />
							</li>
							<li>
							    <label>结算单号</label>
							    <input type="text" id="checkOutNo" name="checkOutNo" />
							</li>
							<li class="search-group-shrink" >
								<button type="button" class="btn  btn-sm btn-system " onclick="query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
								<button type="button" class="btn btn-sm btn-system  "  onclick="doExcel()" title="导出检索条件数据">导出Excel</button>
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
