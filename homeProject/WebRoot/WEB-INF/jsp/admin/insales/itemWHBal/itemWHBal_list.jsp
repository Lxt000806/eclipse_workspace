<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
String costRight = uc.getCostRight();
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemWHBal列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
		#itemType2DataTablePager {
			width: auto !important;
		}
	</style>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}"
	type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}"
	type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_brand.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	var queryType = $("#queryType").val(); //全局查询方式
function doExcelList (){
	if ("2" == queryType) {
		doItemType2Excel();
	} else {
		var url = "${ctx}/admin/itemWHBal/doExcelList";
		doExcelAll(url);
	}
}
function doExcelItemList (){
	if ("2" == queryType) {
		doItemType2Excel();
	} else {
		var url = "${ctx}/admin/itemWHBal/doExcelItemList";
		doExcelAll(url,'itemDataTable');
	}
}
function doItemType2Excel() { //按材料类型2汇总导出Excel
	var url = "${ctx}/admin/itemWHBal/doItemType2ExcelList";
	doExcelAll(url,"itemType2DataTable");
}
function add(){
	Global.Dialog.showDialog("itemWHBalAdd",{
		  title:"添加ItemWHBal",
		  url:"${ctx}/admin/itemWHBal/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemWHBalUpdate",{
		  title:"修改ItemWHBal",
		  url:"${ctx}/admin/itemWHBal/goUpdate?id="+ret.WHCode,
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
      Global.Dialog.showDialog("itemWHBalCopy",{
		  title:"复制ItemWHBal",
		  url:"${ctx}/admin/itemWHBal/goSave?id="+ret.WHCode,
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

function view(tableId){
	var ret = selectDataTableRow(tableId);
    if (ret) {
      Global.Dialog.showDialog("wareHouseAnalyseView",{
		  title:"可用库存分析",
		  url:"${ctx}/admin/wareHouseCx/goAnalyseDetail?id="+ret.itcode,
		  height:600,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function updateSale(){
	var tableId = "dataTable";
	if ($("#content-list").css("display")=="none"){
		tableId = "itemDataTable";
	}
	var ret = selectDataTableRow(tableId);
    if (ret) {
      Global.Dialog.showDialog("itemWHBalUpdateSale",{
		  title:"修改销售策略",
		  url:"${ctx}/admin/itemWHBal/goModifySale?id="+ret.itcode,
		  height:350,
		  width:500,
		  returnFun:goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function orderAnalysis(){
     Global.Dialog.showDialog("orderAnalysis",{
		title:"订货分析",
		url:"${ctx}/admin/itemWHBal/goOrderAnalysis",
		height:730,
		width:1260,
		returnFun:goto_query
	});
}

function del(){
	var url = "${ctx}/admin/itemWHBal/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	$.form_submit($("#page_form").get(0), {
		"action": "${ctx}/admin/itemWHBal/doExcel"
	});
}
//刷新sum并取两位小数
/**
 * 刷新sum并取两位小数
 * colModel_name 列名
 * table_name 表名
 */
function refreshSum(colModel_name, table_name) {
	table_name = table_name?table_name:"dataTable";
	var colModel_name_sum = myRound($("#"+table_name).getCol(colModel_name,false,"sum"), 2);
	var sumObj = {}; //json先要用{}定义，再传参
	sumObj[colModel_name] = colModel_name_sum;//要用参数作为名字时，用[]
	$("#"+table_name).footerData("set", sumObj);
}
/**初始化表格*/
$(function(){
		//初始化材料类型1，2，3三级联动
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
		Global.JqGrid.initJqGrid("dataTable",{
			//url:"${ctx}/admin/itemWHBal/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			postData:$("#page_form").jsonForm(),
			styleUI: 'Bootstrap',
			colModel : [
			 {name: "itcode", index: "itcode", width: 80, label: "材料编号", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "sqlcodedescr", index: "sqlcodedescr", width: 60, label: "品牌", sortable: true, align: "left"},
			{name: "qtycal", index: "qtycal", width: 70, label: "数量", sortable: true, align: "right", sum: true},
			{name: "avgcost", index: "avgcost", width: 60, label: "单价", sortable: true, align: "right",hidden:true},
			{name: "costamount", index: "costamount", width: 100, label: "库存成本金额", sortable: true, align: "right", sum: true,hidden:true},
			{name: "whcode", index: "whcode", width: 70, label: "仓库编号", sortable: true, align: "left"},
			{name: "desc1", index: "desc1", width: 90, label: "仓库名称", sortable: true, align: "left"},
			//{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "hassampledescr", index: "hassampledescr", width: 70, label: "是否上样", sortable: true, align: "left"},
			{name: "salestragydescr", index: "salestragydescr", width: 70, label: "销售策略", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 60, label: "操作", sortable: true, align: "left"}
            ],sortorder:"desc",sortname:'lastupdate'
		});
		Global.JqGrid.initJqGrid("itemDataTable",{
			//url:"${ctx}/admin/itemWHBal/goItemJqGrid",
			rowNum: 10000,
			postData:$("#page_form").jsonForm(),
			height:$(document).height()-$("#content-list2").offset().top-290,
			styleUI: 'Bootstrap',
			colModel : [
			{name: "itcode", index: "itcode", width: 80, label: "材料编号", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "totalqty", index: "totalqty", width: 70, label: "库存数量", sortable: true, align: "right"},
			{name: "costamount", index: "costamount", width: 80, label: "库存金额", sortable: true, align: "right", sum: true, /*formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}*/},
			{name: "applyqty", index: "applyqty", width: 80, label: "申请领用量", sortable: true, align: "right"},
			{name: "cgzqty", index: "cgzqty", width: 100, label: "未到货采购数量", sortable: true, align: "right"},
			{name: "useqty", index: "useqty", width: 70, label: "可用数量", sortable: true, align: "right"},
			{name: "minqty", index: "minqty", width: 70, label: "最小库存", sortable: true, align: "right"},
			{name: "send7", index: "send7", width: 75, label: "近7日发货", sortable: true, align: "right"},
			{name: "send30", index: "send30", width: 75, label: "近30日发货", sortable: true, align: "right"},
			{name: "send60", index: "send60", width: 75, label: "近60日发货", sortable: true, align: "right"},
			{name: "sendzb", index: "sendzb", width: 75, label: "发货占比%", sortable: true, align: "right"},
			{name: "ygxqqty", index: "ygxqqty", width: 80, label: "预估需求量", sortable: true, align: "right",hidden:true},
			{name: "xqqty", index: "xqqty", width: 70, label: "需求数量", sortable: true, align: "right"},
			{name: "allxqqty", index: "allxqqty", width: 70, label: "总需求量", sortable: true, align: "right",hidden:true},
			{name: "yqrxqqty", index: "yqrxqqty", width: 90, label: "已确认需求量", sortable: true, align: "right"},
			{name: "ddqrwfhqty", index: "ddqrwfhqty", width: 110, label: "到点确认需求量", sortable: true, align: "right"},
			{name: "xqzb", index: "xqzb", width: 75, label: "需求占比%", sortable: true, align: "right"},
			{name: "sampleqty", index: "sampleqty", width: 80, label: "样品库数量", sortable: true, align: "right"},
			{name: "jdxqqty", index: "jdxqqty", width: 80, label: "进度需求量", sortable: true, align: "right"},
			{name: "confirmedqty", index: "confirmedqty", width: 110, label: "审核状态领料数量", sortable: true, align: "right"},
			{name: "openqty", index: "openqty", width: 110, label: "申请状态领料数量", sortable: true, align: "right"},
			{name: "salesinvoiceqty", index: "salesinvoiceqty", width: 100, label: "未发货销售数量", sortable: true, align: "right"},
			//{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "hassampledescr", index: "hassampledescr", width: 70, label: "是否上样", sortable: true, align: "left"},
			{name: "salestragydescr", index: "salestragydescr", width: 70, label: "销售策略", sortable: true, align: "left"}
			],
			loadComplete: function(){
				refreshSum("costamount", "itemDataTable");
			},
		});
		//增加【库存金额】，成本查看权限控制 add by zb
		if ("1" != "${sessionScope.USER_CONTEXT_KEY.costRight}") $("#itemDataTable").setGridParam().hideCol("costamount").trigger("reloadGrid");
		// 按材料类型2
		Global.JqGrid.initJqGrid("itemType2DataTable",{
			// url:"${ctx}/admin/itemWHBal/goItemType2JqGrid",
			postData:$("#page_form").jsonForm(),
			height:$(document).height()-$("#content-list3").offset().top-225,
			styleUI: "Bootstrap",
			colModel : [
				{name: "itemtype2", index: "itemtype2", width: 80, label: "材料类型2", sortable: true, align: "left", hidden: true},
				{name: "itemtype2descr", index: "itemtype2descr", width: 100, label: "材料类型2", sortable: true, align: "left"},
				{name: "qtycal", index: "qtycal", width: 80, label: "数量", sortable: true, align: "right"},
				{name: "costamount", index: "costamount", width: 80, label: "总金额", sortable: true, align: "right"},
			]
		});

		if("<%=czylb %>"=="1"){
			if ("<%=costRight %>"=="1"){
				$("#dataTable").jqGrid('showCol', "avgcost");
	       	 	$("#dataTable").jqGrid('showCol', "costamount");
			}
       	 	$("#whCode").openComponent_wareHouse();
       	 	$("#itCode").openComponent_item();
			$("#sqlCode").openComponent_brand();
       }else{
       		$("#whCode").openComponent_wareHouse({condition:{czybh:"<%=czybh %>"}});
       		$("#itCode").openComponent_item({readonly:true});
			$("#sqlCode").openComponent_brand({readonly:true});
			$("#sqlCodeLi").hide();
			$("#itCodeLi").hide();
       }
      $('input','#page_form').bind('keydown',function(e){
      		if(e.keyCode==13){
      			goto_query();
      		}
      });  
	$("#itemDataTablePager_left").width(80);
	$("#itemDataTablePager_center").width(900);
	$("#itemDataTablePager_right").attr("align","left");
	$("#load_itemDataTable").css({"left":"500px"});
});
function change(ele){
	queryType = ele.value;
	if(queryType=="1"){
		$("#content-list").css("display","none");
		$("#content-list2").css("display","");
		$("#content-list3").css("display","none");
		$("#only").css("display","inline");
		$("#id_constructStatus").css("display","inline");
		$("#percentRequirs_Li").css("display","inline");
		$("#query").css("display","none");
		$("#query2").css("display","block");
		$("#excel").css("display","none");
		$("#excel2").css("display","block");
		$("#analyse").css("display","none");
		$("#analyse2").css("display","block");
		$("#contain").css("display","inline");
	}else if(queryType=="0") {
		$("#content-list2").css("display","none");
		$("#content-list").css("display","");
		$("#content-list3").css("display","none");
		$("#query2").css("display","none");
		$("#query").css("display","block");
		$("#excel2").css("display","none");
		$("#excel").css("display","block");
		$("#analyse2").css("display","none");
		$("#analyse").css("display","block");
		$("#only").css("display","none");
		$("#id_constructStatus").css("display","none");
		$("#percentRequirs_Li").css("display","none");
		$("#contain").css("display","none");
	} else if(queryType=="2") { // 增加按材料类型2查询方式
		$("#content-list").css("display","none");
		$("#content-list2").css("display","none");
		$("#content-list3").css("display","");
		$("#only").css("display","none");
		$("#id_constructStatus").css("display","none");
		$("#percentRequirs_Li").css("display","none");
		$("#contain").css("display","none");
	}
}
//刷新表格
function goto_query(){
	var tableId = "dataTable";
	if ($("#content-list").css("display")=="none"){
		tableId = "itemDataTable";
	}
	if ($("#queryType").val()==null){
		art.dialog({
			content: "请选择查询方式！",
			width: 200
		});
		return false;
	}
	if ($("#queryType").val()=="1"){
		if ($("#whCode").val()=="" && $("#sqlCode").val()=="" && $("#itemType1").val()==""
			 && $("#department2").val()=="" && $("#itCode").val()==""){
			art.dialog({
				content: "请选择查询条件！",
				width: 200
			});
			return false;
		}
		$("#"+tableId).jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:'',url:"${ctx}/admin/itemWHBal/goItemJqGrid"}).trigger("reloadGrid");
	}else if ($("#queryType").val()=="0") {
		$("#"+tableId).jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:'',url:"${ctx}/admin/itemWHBal/goJqGrid",}).trigger("reloadGrid");
	} else if ($("#queryType").val()=="2") {
		$("#itemType2DataTable").jqGrid("setGridParam",{
			postData:$("#page_form").jsonForm(),
			page:1,
			sortname:"",
			url:"${ctx}/admin/itemWHBal/goItemType2JqGrid",
		}).trigger("reloadGrid");
	}
}
function checkOnlyWarn(){
	if ($("#onlyWarn").is(":checked")){
		$("#onlyWarn").val("1");
	}else{
		$("#onlyWarn").val("0");
	}
}
function changeContain(){
	if ($("#containClearItem").is(":checked")){
		$("#containClearItem").val("1");
	}else{
		$("#containClearItem").val("0");
	}
}

function getItemType2(){
	
	var treeObj = $.fn.zTree.getZTreeObj("tree_itemType2"); //材料类型2树
	var nodes = treeObj.getNodes(); 
	var child = nodes[0]; 
	var itemType1 = $("#itemType1").val();
	
	// 获取材料类型1对应的材料类型2
	$.ajax({
		url:"${ctx}/admin/item/getAuthItemType2ByItemType1",
		type: "post",
		data: {itemType1:itemType1, expired: "F"},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	// 材料类型1改变 清空原本材料类型2的值和选项
	    	$("#itemType2").val("");
	    	$("#itemType2_NAME").val("");
			// 更新下拉树
			child.children = obj;
			// 刷新树
			treeObj.refresh();
	    }
	});
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#openComponent_wareHouse_whCode").val('');
	$("#openComponent_brand_sqlCode").val('');
	$("#openComponent_item_itCode").val('');
	$("#page_form select").val('');
	
	$("#itemType2_NAME").val('');
	$("#itemType2").val('');
	$.fn.zTree.getZTreeObj("tree_itemType2").checkAllNodes(false);
} 

</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form"  >  
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="${itemWHBal.expired}" />
				<input type="hidden" id="czybh" name="czybh" value="<%=czybh%>" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>仓库编号</label>
						<input type="text" id="whCode" name="whCode"   value="${itemWHBal.whCode}" />
					</li>
					<li>	
						<label>仓库名称</label>	
						<input type="text" id="desc1" name="desc1"  value="${itemWHBal.desc1}" />
					</li>
					<li id="sqlCodeLi">	
						<label>品牌</label>
						<input type="text" id="sqlCode" name="sqlCode"   value="${itemWHBal.sqlCode}" />
					</li>		
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" onchange="getItemType2()"></select>
					</li>
					<li>
						<label>材料类型2</label>
						<house:DictMulitSelect id="itemType2" dictCode="" sql=" select code, descr from titemType2 where 1<>1" 
						sqlLableKey="descr" sqlValueKey="code"></house:DictMulitSelect>
					</li>
					<li>			
						<label>查询方式</label>
						<select id="queryType" name="queryType" onchange="change(this)">
							<option value="0">明细查询</option>
							<option value="1">按材料汇总</option>
							<option value="2">按材料类型2汇总</option>
						</select>
					</li>
					<li>			
						<label>归属部门</label>
						<house:dict id="department2" dictCode="" sql="select Desc2,Code from tDepartment2 where Expired='F' order by Department1,DispSeq " 
							sqlValueKey="Code" sqlLableKey="Desc2"  ></house:dict>	
					</li>
					<li id="itCodeLi">		
						<label>材料编号</label>
						<input type="text" id="itCode" name="itCode"  value="${itemWHBal.itCode}" />
					</li>
					<li>		
						<label>材料名称</label>
						<input type="text" id="descr" name="descr"  value="${itemWHBal.descr}" />
					</li>	
					<li id="id_constructStatus" style="display: none;">
						<label>施工状态</label>
						<house:xtdm id="constructStatus" dictCode="CONSTRUCTSTATUS" value="${itemWHBal.constructStatus }"></house:xtdm>
					</li>
					<li id="percentRequirs_Li" style="display: none;"><!-- erp修改20190507 add by zb on 20190510 -->
						<label>需求占比</label>
						<select id="percentRequir" name="percentRequirs">
							<option value>请选择...</option>
							<option value="80">大于80%</option>
							<option value="120">大于120%</option>
							<option value="150">大于150%</option>
						</select>
					</li>
					<li  id="only" style="display: none">
						<label></label>
						<input type="checkbox" id="onlyWarn" name="onlyWarn" onclick="checkOnlyWarn()"/>只显示库存预警材料
					</li>
					<li  id="contain" style="display: none">
						<label></label>
						<input type="checkbox" id="containClearItem" name="containClearItem" value="0" onclick="changeContain()"/>包含清库存材料
					</li>
					<li class="search-group">
<!-- 							<input type="checkbox" id="expired_show" name="expired_show" -->
<!-- 										value="${itemWHBal.expired}" onclick="hideExpired(this)" -->
<!-- 										${itemWHBal.expired!='T' ?'checked':'' }/> -->
<!-- 										<p>隐藏过期</p> -->
						<button type="button" class="btn btn-system btn-sm" id="query" onclick="goto_query();">查询</button>
						<button style="display: none" class="btn btn-system btn-sm" id="query2" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-system btn-sm"  onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
 		<div class="btn-panel" >
      		<div class="btn-group-sm"  style="padding-left: 5px;">
	    		<house:authorize authCode="SUPPLIER_ITEMWHBAL_ANALYSIS">
		      		<button id="analyse" type="button" style="float: left;margin-right: 10px;margin-left: 0px" class="btn btn-system " onclick="view('dataTable')">可用库存分析</button>
		       		<button type="button" class="btn btn-system" style="display: none;float: left;margin-right: 10px;margin-left: 0px" id="analyse2" onclick="view('itemDataTable')">可用库存分析</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIER_ITEMWHBAL_MODIFYSALE">
	       			<button type="button" class="btn btn-system" style="float: left;margin-right: 10px;margin-left: 0px" onclick="updateSale()">修改销售策略</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIER_ITEMWHBAL_ORDERANALYSIS">
	       			<button type="button" class="btn btn-system" style="float: left;margin-right: 10px;margin-left: 0px" onclick="orderAnalysis()">订货分析</button>
				</house:authorize>
	     		<button id="excel" type="button" class="btn btn-system" style="margin-left: 0px"  onclick="doExcelList()">导出excel</button>
	     		<button id="excel2" style="display: none;margin-left: 0px"  type="button" class="btn btn-system "  onclick="doExcelItemList()">导出excel</button>
	      	</div>
		</div>
	 	<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list2" style="display: none">
				<table id="itemDataTable"></table>
				<div id="itemDataTablePager"></div>
			</div>
			<div id="content-list3" style="display: none">
				<table id="itemType2DataTable"></table>
				<div id="itemType2DataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


