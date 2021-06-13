<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<style>
		.SelectBG{
          	background-color:#FF7575!important;
        }  
	</style>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false);">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;"/>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${specItemReq.address}" readonly="true" />
						</li>
						<li>
							<label>客户类型</label>
							<house:xtdm id="custType" dictCode="CUSTTYPE" style="width:160px;" value="${specItemReq.custType}"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>橱柜地柜延米</label>
							<input type="text" id="cupDownHigh" name="cupDownHigh" style="width:160px;" 
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								value="${specItemReq.cupDownHigh}" />
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>橱柜吊柜延米</label>
							<input type="text" id="cupUpHigh" name="cupUpHigh" style="width:160px;" 
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								value="${specItemReq.cupUpHigh}"/>
						</li>
						<li>
							<label>橱柜修改人</label>
							<input type="text" id="cupLastupdatedBy" name="cupLastupdatedBy" style="width:160px;"/>
						</li>
						<li>
							<label>橱柜修改日期</label>
							<input type="text" id="cupLastUpdate" name="cupLastUpdate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${specItemReq.cupLastUpdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true"/>
						</li>
						<li>
							<label>衣柜修改人</label>
							<input type="text" id="intLastupdatedBy" name="intLastupdatedBy" style="width:160px;"/>
						</li>
						<li>
							<label>衣柜修改日期</label>
							<input type="text" id="intLastupdate" name="intLastupdate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${specItemReq.intLastupdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>浴室柜地柜延米</label>
							<input type="text" id="bathDownHigh" name="bathDownHigh" style="width:160px;" 
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								value="${specItemReq.bathDownHigh}" />
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>浴室柜吊柜延米</label>
							<input type="text" id="bathUpHigh" name="bathUpHigh" style="width:160px;" 
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								value="${specItemReq.bathUpHigh}"/>
						</li>
						<li>
							<label>集成灶</label>
							<house:xtdm id="hasStove" dictCode="HAVENO" style="width:160px;" value="${specItemReq.hasStove}"/>
						</li>
						<li>
							<label>自购五金（橱柜）</label>
							<house:xtdm id="isSelfMetal_Cup" dictCode="YESNO" style="width:160px;" value="${specItemReq.isSelfMetal_Cup}"/>
						</li>
						<li>
							<label>自购五金（衣柜）</label>
							<house:xtdm id="isSelfMetal_Int" dictCode="YESNO" style="width:160px;" value="${specItemReq.isSelfMetal_Int}"/>
						</li>
						<li>
							<label>自购水槽</label>
							<house:xtdm id="isSelfSink" dictCode="YESNO" style="width:160px;" value="${specItemReq.isSelfSink}"/>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#specItemReq_tabView_cup" data-toggle="tab">橱柜解单数据</a></li>  
			    <li><a href="#specItemReq_tabView_int" data-toggle="tab">衣柜解单数据</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="specItemReq_tabView_cup"  class="tab-pane fade in active"> 
		         	<div class="body-box-list">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="view" onclick="view1()">
									<span>查看</span>
								</button>
								<button type="button" class="btn btn-system" id="outExcel" onclick="doExcelNow('橱柜解单数据','dataTable_cup')">
									<span>导出Excel</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable_cup"></table>
							<!-- <div id="dataTablePager"></div> -->
						</div>
					</div>
				</div> 
				<div id="specItemReq_tabView_int"  class="tab-pane fade"> 
		         	<div class="body-box-list">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="view" onclick="view2()">
									<span>查看</span>
								</button>
								<button type="button" class="btn btn-system" id="outExcel" onclick="doExcelNow('衣柜解单数据','dataTable_int')">
									<span>导出Excel</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable_int"></table>
							<!-- <div id="dataTablePager"></div> -->
						</div>
					</div>
				</div> 
			</div>	
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {
	$("#custCode").openComponent_customer({
		readonly:true,
		showValue:"${specItemReq.custCode}",
		showLabel:"${specItemReq.custName}",
	});
	$("#cupLastupdatedBy").openComponent_employee({
		readonly:true,
		showValue:"${specItemReq.cupLastupdatedBy}",
		showLabel:"${specItemReq.cupLastupdatedByDescr}"
	});
	$("#intLastupdatedBy").openComponent_employee({
		readonly:true,
		showValue:"${specItemReq.intLastupdatedBy}",
		showLabel:"${specItemReq.intLastupdatedByDescr}"
	});
	$("#custType").prop("disabled",true);

	var gridOption = {
		url:"${ctx}/admin/specItemReq/goDetailJqGrid",
		postData:{custCode:"${specItemReq.custCode}"},
		sortable: true,
		height : $(document).height()-$("#content-list").offset().top - 63,
		rowNum : 10000000,
		colModel : [
			{name: "pk", index: "pk", width: 70, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "iscupboard", index: "iscupboard", width: 80, label: "是否橱柜", sortable: true, align: "left",hidden:true},
			{name: "iscupboarddescr", index: "iscupboarddescr", width: 80, label: "是否橱柜", sortable: true, align: "left"},
			{name: "itemtype1", index: "itemtype1", width: 90, label: "材料类型1", sortable: true, align: "left",hidden:true},
			{name: "itemtype1descr", index: "itemtype1descr", width: 90, label: "材料类型1", sortable: true, align: "left",hidden:true},
			{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品pk", sortable: true, align: "left",hidden:true},
			{name: "intprodpkdescr", index: "intprodpkdescr", width: 80, label: "成品名称", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right",sum:true},
			{name: "appqty", index: "appqty", width: 90, label: "已申请数量", sortable: true, align: "right"},
			{name: "movecost", index: "movecost", width: 60, label: "移动平均成本", sortable: true, align: "right",hidden:true},
			{name: "price", index: "price", width: 60, label: "单价", sortable: true, align: "right"},
			{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "right"},
			{name: "totalcost", index: "totalcost", width: 90, label: "成本总价", sortable: true, align: "right",sum:true},
			{name: "remark", index: "remark", width: 200, label: "描述", sortable: true, align: "left"},
			{name: "selectBG", index: "selectBG", width: 60, label: "错误标记", sortable: true, align: "right",hidden:true},
			{name : "lastupdate",index : "lastupdate",width : 125,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime, hidden:true},
      		{name : "lastupdatedby",index : "lastupdatedby",width : 60,label:"修改人",sortable : true,align : "left", hidden:true},
     		{name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left", hidden:true},
    		{name : "actionlog",index : "actionlog",width : 50,label:"操作",sortable : true,align : "left", hidden:true},
		],
		loadonce: true,
		ondblClickRow: function(){
			view();
		},
	};
	
	Global.JqGrid.initJqGrid("dataTable_cup",$.extend(gridOption,{
		postData:{
			custCode:"${specItemReq.custCode}",
			iscupboard:"1"
		},
		gridComplete:function(){
			var qtySum1 = $("#dataTable_cup").getCol("qty",false,"sum");
			$("#dataTable_cup").footerData("set",{"qty":myRound(qtySum1,2)});
			var totalcostSum1 = $("#dataTable_cup").getCol("totalcost",false,"sum");
			$("#dataTable_cup").footerData("set",{"totalcost":myRound(totalcostSum1,2)});
		},
	}));
	Global.JqGrid.initJqGrid("dataTable_int",$.extend(gridOption,{
		postData:{
			custCode:"${specItemReq.custCode}",
			iscupboard:"0"
		},
		gridComplete:function(){
			var qtySum2 = $("#dataTable_int").getCol("qty",false,"sum");
			$("#dataTable_int").footerData("set",{"qty":myRound(qtySum2,2)});
			var totalcostSum2 = $("#dataTable_int").getCol("totalcost",false,"sum");
			$("#dataTable_int").footerData("set",{"totalcost":myRound(totalcostSum2,2)});
		},
	}));

	if ("1" != $.trim("${czybm.costRight}")) {
		jQuery("#dataTable_cup").setGridParam()
			.hideCol("price").hideCol("cost").hideCol("totalcost")
			.trigger("reloadGrid");
		jQuery("#dataTable_int").setGridParam()
			.hideCol("price").hideCol("cost").hideCol("totalcost")
			.trigger("reloadGrid");
	}
	// $("#dataTable_cup").jqGrid("setGridParam", {url: url,postData: postData,}).trigger("reloadGrid");
});

function view1(){
	var ret=selectDataTableRow("dataTable_cup");
	if(!ret){
		art.dialog({content: "请选择一条记录进行查看操作",width: 220});
		return false;
	}
	
	Global.Dialog.showDialog("detailView1",{
		title:"拆单详细——查看",
		url:"${ctx}/admin/specItemReq/goDetailSave",
		postData:{
			isCupboard:ret.iscupboard,
			m_umState:"V",
			itemType2:ret.iscupboard==1?"018":"025",
			custCode:"${specItemReq.custCode}",
			intProdPK:ret.intprodpk==0?"":ret.intprodpk,
			intProdPKDescr:ret.intprodpkdescr,
			itemCode:ret.itemcode,
			itemDescr:ret.itemdescr,
			qty:ret.qty,
			cost:ret.movecost,
			price:ret.price,
			itCost:ret.cost,
			remark:ret.remark,
			appQty:ret.appqty,
		},
		height:385,
		width:450,
	});
}

function view2(){
	var ret=selectDataTableRow("dataTable_int");
	if(!ret){
		art.dialog({content: "请选择一条记录进行查看操作",width: 220});
		return false;
	}
	
	Global.Dialog.showDialog("detailView2",{
		title:"拆单详细——查看",
		url:"${ctx}/admin/specItemReq/goDetailSave",
		postData:{
			isCupboard:ret.iscupboard,
			m_umState:"V",
			itemType2:ret.iscupboard==1?"018":"025",
			custCode:"${specItemReq.custCode}",
			intProdPK:ret.intprodpk==0?"":ret.intprodpk,
			intProdPKDescr:ret.intprodpkdescr,
			itemCode:ret.itemcode,
			itemDescr:ret.itemdescr,
			qty:ret.qty,
			cost:ret.movecost,
			price:ret.price,
			itCost:ret.cost,
			remark:ret.remark,
			appQty:ret.appqty,
		},
		height:385,
		width:450,
	});
}
</script>
</html>
