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
	<title>搜寻——采购单号</title>
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
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${purchase.itemType1 }',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thridSelect:'itemType3',
								thirdValue:'${item.itemType3 }',
							   });
	$("#whcode").openComponent_wareHouse({condition: {czybh:'${czybh}'}});
	$("#supplier").openComponent_supplier();
	$("#sino").openComponent_salesInvoice();
	var postData = $("#page_form").jsonForm();
	if('${purchase.readonly}'=="1"){
		document.getElementById("itemType1").disabled=true;
	}
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchase/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'no',			index:'no',			width:90,	label:'采购单号',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'type',		index:'type',		width:60,	label:'采购类型',	sortable:true,align:"left",hidden:true,},//,hidden:true
			{name:'typedescr',		index:'typedescr',		width:60,	label:'采购类型',	sortable:true,align:"left",},		
			{name:'sumamount',		index:'sumamount',		width:60,	label:'总金额',	sortable:true,align:"left",},
			{name:'samount',		index:'samount',		width:60,	label:'本次付款',	sortable:true,align:"left",},
			{name:'status', 	index:'status',		width:80,	label:'采购单状态',sortable:true,align:"left",hidden:true,},// hidden:true
			{name:'statusdescr', 	index:'statusdescr',		width:80,	label:'采购单状态',sortable:true,align:"left",},
			{name:'supplier',		index:'supplier',		width:80,	label:'供应商',	sortable:true,align:"left",hidden:true},
			{name:'supdescr',		index:'supdescr',		width:85,	label:'供应商',	sortable:true,align:"left",},
			{name:'SINo',		index:'SINo',		width:60,	label:'销售单号',	sortable:true,align:"left",},
			{name:'date',		index:'date',		width:150,	label:'采购日期',	sortable:true,align:"left",	formatter: formatTime},
		 	{name:'itemtype1',		index:'itemtype1',		width:60,	label:'材料类型1',	sortable:true,align:"left",hidden:true},
			{name:'itemtypedescr',	index:'itemtypedescr',	width:60,	label:'材料类型1',	sortable:true,align:"left",},
			{name:'address',	index:'address',	width:260,	label:'楼盘地址',	sortable:true,align:"left",},
			{name:'amount',		index:'amount',		width:60,	label:'材料金额',	sortable:true,align:"left",},
			{name:'othercost',		index:'othercost',		width:60,	label:'其他费用',	sortable:true,align:"left",},
			{name:'othercostadj',	index:'othercostadj',	width:60,	label:'其他费用调整',	sortable:true,align:"left",},
			{name:'allamount',		index:'allamount',		width:60,	label:'总金额',	sortable:true,align:"left",},
			{name:'remainamount',	index:'remainamount',	width:60,	label:'应付金额',	sortable:true,align:"left",},
			{name:'firstamount',	index:'firstamount',	width:60,	label:'已付定金',	sortable:true,align:"left",},
			{name:'remarks',		index:'remarks',		width:60,	label:'备注',	sortable:true,align:"left",},
			{name:'whcode',			index:'whcode',			width:60,	label:'仓库',	sortable:true,align:"left",hidden:true},
			{name:'whcodedescr',	index:'whcodedescr',	width:60,	label:'仓库',	sortable:true,align:"left",hidden:true},
			{name:'custcode',		index:'custcode',		width:60,	label:'客户编号',	sortable:true,align:"left",hidden:true},
			{name:'supdescr',		index:'supdescr',		width:60,	label:'supdescr',	sortable:true,align:"left",hidden:true},
		],
		  ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
           }
	});


});

</script>
</head>
	<body >
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired"  name="expired" value="${purchase.expired }"/>
				<input type="hidden" id="type"  name="type" value="${purchase.type }"/>
				<input type="hidden" id="status"  name="status" value="${purchase.status }"/>
				<input type="hidden" id="isCheckOut"  name="isCheckOut" value="${purchase.isCheckOut }"/>
				<input type="hidden" id="isSupplPrepaySelect"  name="isSupplPrepaySelect" value="${purchase.isSupplPrepaySelect}"/>
					<ul class="ul-form">
						<li> 
							<label>采购单号</label>
							
								<input type="text" id="no" name="no"  value="${purchase.no }"/>
							</li>
							<li> 
								<label>供应商编号</label>
							
								<input type="text" id="supplier" name="supplier"  value="${purchase.supplier }"/>
							</li>
							<li> 
								<label>楼盘地址</label>
								
								<input type="text" id="address" name="address"  value="${purchase.address }"/>
							</li>
					
							<li> 
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1"  value="${purchase.itemType1 }" ></select>
							</li>
							<li> 
								<label>销售单号</label>
							
								<input type="text" id="sino" name="sino"   value="${purchase.sino }"/>
							</li>
						
							<li> 
								<label>采购日期</label>
							
								<input type="text" id="dateFrom1" name="dateFrom1" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li> 
								<label>至</label>
							
								<input type="text" id="dateTo1" name="dateTo1" 	 class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
						
						
						<li>
								<label></label>
						<input type="checkbox" id="expired_show" name="expired_show"
										value="${purchase.expired}" onclick="hideExpired(this)"
										${purchase.expired!='T' ?'checked':'' }/>隐藏过期记录&nbsp; 
						</li>
						<li id="loadMore">
						<button type="button" class="btn btn-system btn-sm"
							onclick="goto_query();">查询</button>
						</li>
						<%-- <li hidden="true">
						<input type="text" id="itemType1" name="itemType1"  value="${purchase.itemType1 }"/>
						</li> --%>
					</ul>	
				</form>
			</div>
			
			
			<!--query-form-->
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
		</div>
	</body>	
</html>
