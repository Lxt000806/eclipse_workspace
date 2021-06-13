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
	<title>供应商促销</title>
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
$(function(){
	if("${supplier.code}"!=""){
		$("#supplCode").openComponent_supplier({showValue:"${supplier.code}",showLabel:"${supplier.descr}",readonly:true});	
	}else{
		$("#supplCode").openComponent_supplier();	
	}
	var postData = $("#page_form").jsonForm();
	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplProm/goDetailJqGrid",
		postData:postData,
		height:450,
		colModel : [
			{name:"pk",	index:"pk",	width:90,	label:"供应商促销pk",	sortable:true,align:"left",cellattr: "addCellAttr",count:true,hidden:true},
			{name:"no",	index:"no",	width:90,	label:"活动编号",	sortable:true,align:"left",cellattr: "addCellAttr",count:true},
			{name:"actdescr",	index:"actdescr",	width:90,	label:"活动名称",	sortable:true,align:"left",cellattr: "addCellAttr",count:true},
			{name:"itemtype3", 	index:"itemtype3",	width:80,	label:"材料类型3",sortable:true,align:"left" ,},
			{name:"itemdescr", 	index:"itemdescr",	width:80,	label:"品名",sortable:true,align:"left",hidden:true },
			{name:"itemsize",	index:"itemsize",	width:60,	label:"规格",		sortable:true,align:"left",}, 
			{name:"model",	index:"model",	width:80,	label:"型号",		sortable:true,align:"left",}, 
			{name:"unitprice",	index:"unitprice",	width:60,	label:"常规价",	sortable:true,align:"left",},
			{name:"promprice",	index:"promprice",	width:60,	label:"活动价",	sortable:true,align:"left",},
			{name:"cost",	index:"cost",	width:80,	label:"常规成本",	sortable:true,align:"left",hidden:true},
			{name:"promcost",index:"promcost",	width:80,	label:"活动成本",	sortable:true,align:"left",hidden:true},
		 	{name:"uom",	index:"uom",width:50,	label:"单位",	sortable:true,	align:"left",},
			{name:"suppldescr", index:"suppldescr",	width:120,	label:"供应商名称",sortable:true,align:"left",},
			{name:"supplcode", 	index:"supplcode",		width:80,	label:"供应商编号",sortable:true,align:"left",},
			{name:"remarks",	index:"remarks",	width:290,	label:"备注",		sortable:true,align:"left",}, 
		],
		ondblClickRow: function(){
			save();
		}
	});
});

function save(){
	var id = $("#dataTable").jqGrid("getGridParam","selrow");
	var row = $("#dataTable").jqGrid("getRowData", id);
	var selectRows = [];
	if(myRound("${itemChgDetail.cost }",4) <= row.promcost){
		art.dialog({
			content:"活动成本必须小于当前成本",
		});
		return;
	}
	selectRows.push(row);
	Global.Dialog.returnData = selectRows;
   	closeWin();
}

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBut" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value=""/>
					<input type="hidden" id="cost"  name="cost" value="${itemChgDetail.cost }"/>
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">	
						<li>
							<label>活动名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;"/>
						</li>
						<li>
							<label>供应商编号</label>
							<input type="text" id="supplCode" name="supplCode" style="width:160px;"/>
						</li>
						<li>
							<label>材料类型3</label>
							<input type="text" id="itemType3" name="ItemType3" style="width:160px;"/>
						</li>
						<li>
							<label>型号</label>
							<input type="text" id="model" name="model" style="width:160px;" value="${item.model }"/>
						</li>
						<li>
							<label>活动开始时间</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
						</li>
						<li class="search-group-shrink" >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>	
</html>
