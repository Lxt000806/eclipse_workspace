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
	<title>结算工地减项分析</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_prjRegion.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	$("#projectMan").openComponent_employee();	
 	var dateFrom =$.trim($("#dateFrom").val());
 	var dateTo =$.trim($("#dateTo").val());
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjMinusAnaly/goJqGrid",
		postData:{dateFrom:dateFrom,dateTo:dateTo,itemType1:"ZC",showTpe:"3"},
		height:$(document).height()-$("#content-list").offset().top-78,
		styleUI: "Bootstrap",
		loadonce: true,
		rowNum:10000,
		colModel : [
			{name:"designdept1",	index:"designdept1",	width:90,	label:"事业部",	sortable:true,align:"left" ,},
			{name:"designdept2",	index:"designdept2",	width:80,	label:"设计部",	sortable:true,align:"left" ,},
			{name:"address",	index:"address",	width:190,	label:"楼盘",	sortable:true,align:"left" ,},
			{name:"custtypedescr",	index:"custtypedescr",	width:90,	label:"客户类型",	sortable:true,align:"left"},
			{name:"area",	index:"area",	width:70,	label:"面积",	sortable:true,align:"right"},
			{name:"designmandescr",	index:"designmandescr",	width:90,	label:"设计师",	sortable:true,align:"left" ,},
			{name:"projectmandescr",	index:"projectmandescr",	width:90,	label:"项目经理",	sortable:true,align:"left" ,},
			{name:"jcdesigndescr",	index:"jcdesigndescr",	width:90,	label:"集成设计师",	sortable:true,align:"left" ,},
			{name:"prjdept2",	index:"prjdept2",	width:90,	label:"工程部",	sortable:true,align:"left" ,},
			{name:"itemtypedescr",	index:"itemtypedescr",	width:90,	label:"材料分类",	sortable:true,align:"left" ,},
			{name:"iscup",	index:"iscup",	width:90,	label:"是否橱柜",	sortable:true,align:"left" ,},
			{name:"planamount",	index:"planamount",	width:65,	label:"预算金额",	sortable:true,align:"right" ,sum:true},
			{name:"allcost",	index:"allcost",	width:90,	label:"结算金额",	sortable:true,align:"right" ,sum:true},
			{name:"discamount",	index:"discamount",	width:90,	label:"减项金额",	sortable:true,align:"right" ,sum:true,formatter:formatMoney},
			{name:"discper",	index:"discper",	width:65,	label:"减项占比",	sortable:true,align:"right",formatter:divFormat},
		],
	});
	
	Global.JqGrid.initJqGrid("dataTableDesign",{
		url:"${ctx}/admin/prjMinusAnaly/goJqGrid",
		postData:{dateFrom:dateFrom,dateTo:dateTo,itemType1:"ZC",showTpe:"1"},
		height:$(document).height()-$("#content-list").offset().top-78,
		styleUI: "Bootstrap",
		loadonce: true,
		rowNum:10000,
		colModel : [
			{name:"designdept1",	index:"designdept1",	width:90,	label:"事业部",	sortable:true,align:"left" ,},
			{name:"designdept2",	index:"designdept2",	width:80,	label:"设计部",	sortable:true,align:"left" ,},
			{name:"designmandescr",	index:"designmandescr",	width:90,	label:"设计师",	sortable:true,align:"left" ,},
			{name:"designman",	index:"designman",	width:90,	label:"设计师",	sortable:true,align:"left" , hidden: true},
			{name:"discamount",	index:"discamount",	width:90,	label:"减项金额",	sortable:true,align:"right" ,sum:true,formatter:formatMoney},
		],
	});
	
	Global.JqGrid.initJqGrid("dataTableProject",{
		url:"${ctx}/admin/prjMinusAnaly/goJqGrid",
		postData:{dateFrom:dateFrom,dateTo:dateTo,itemType1:"ZC",showTpe:"2"},
		height:$(document).height()-$("#content-list").offset().top-78,
		styleUI: "Bootstrap",
		loadonce: true,
		rowNum:10000,
		colModel : [
			{name:"projectmandescr",	index:"projectmandescr",	width:90,	label:"项目经理",	sortable:true,align:"left" ,},
			{name:"projectman",	index:"projectman",	width:90,	label:"项目经理",	sortable:true,align:"left" , hidden: true},
			{name:"prjdept2",	index:"prjdept2",	width:90,	label:"工程部",	sortable:true,align:"left" ,},
			{name:"discamount",	index:"discamount",	width:65,	label:"减项金额",	sortable:true,align:"right",sum:true,formatter:formatMoney},
		],
	});
	
	function divFormat (cellvalue, options, rowObject){ 
	    return myRound(cellvalue*100,4)+"%";
	}	
	
	function formatMoney(cellvalue, options, rowObject){
		return myRound(cellvalue,0);
	}
	
	window.goto_query = function(){
		if($("#dateFrom").val()=="" || $("#dateTo").val() ==""){
			art.dialog({
				content:"请填写完整时间段",
			});
			return;
		}
		if($("#itemType1").val()=="" ||$("#itemType1").val()==null){
			art.dialog({
				content:"请选择材料类型",
			});
			return;
		}
		if($("#itemType1").val()=="ZC"){
			jQuery("#dataTable").setGridParam().showCol("itemtypedescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("iscup").trigger("reloadGrid");
		}
		if($("#itemType1").val()=="JC"){
			jQuery("#dataTable").setGridParam().showCol("iscup").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("itemtypedescr").trigger("reloadGrid");
		}
		console.log($("#showType").val());
		if($("#showType").val()=="1"){
			$("#tabDesign").show();
			$("#tabProject").hide();
			$("#tabDetail").hide();
			$("#view").show();
			$("#dataTableDesign").jqGrid("setGridParam",{datatype:"json",postData:$("#page_form").jsonForm(),page:1,
					url:"${ctx}/admin/prjMinusAnaly/goJqGrid",}).trigger("reloadGrid");
		}else if($("#showType").val()=="2"){
			$("#tabDesign").hide();
			$("#tabProject").show();
			$("#tabDetail").hide();
			$("#view").show();
			$("#dataTableProject").jqGrid("setGridParam",{datatype:"json",postData:$("#page_form").jsonForm(),page:1,
					url:"${ctx}/admin/prjMinusAnaly/goJqGrid",}).trigger("reloadGrid");
		}else if($("#showType").val()=="3"){
			$("#tabDesign").hide();
			$("#tabProject").hide();
			$("#tabDetail").show();
			$("#view").hide();
			$("#dataTable").jqGrid("setGridParam",{datatype:"json",postData:$("#page_form").jsonForm(),page:1,
					url:"${ctx}/admin/prjMinusAnaly/goJqGrid",}).trigger("reloadGrid");
		}else {
			return;
		}
		postData=$("#page_form").jsonForm();
	};
	$("#view").on("click", function () {
		var tableId = "";
		if ("1" != postData.showType && "2" != postData.showType) {
			return;
		} else if ("2" == postData.showType) {
			tableId = "dataTableProject";
		} else if ("1" == postData.showType) {
			tableId = "dataTableDesign";
		}
		var ret = selectDataTableRow(tableId);
		if (ret) {
			var data = postData;
			if ("1" == data.showType) {
				data.designMan = ret.designman;
			} else {
				data.projectMan = ret.projectman;
			}
			Global.Dialog.showDialog("view",{
				title:"结算工地减项分析--查看明细",
				url:"${ctx}/admin/prjMinusAnaly/goView",
				postData:data,
				width:1050,
				height:650,
				// returnFun:goto_query
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	});
});

function chgSel(){
	if($("#itemType1").val()=="ZC"){
		$("#isCup").hide();
		$("#itemType").show();
		$("#isCupboare").val("");
	}
	if($("#itemType1").val()=="JC"){
		$("#isCup").show();
		$("#itemType").hide();
		$("#itemType12").val("");
		$.fn.zTree.getZTreeObj("tree_itemType12").checkAllNodes(false);
		$("#itemType12_NAME").val("");
	}
}

function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#openComponent_employee_projectMan").val('');
		$("#projectMan").val('');
		$("#custType").val('');
		$("#itemType12").val('');
		
		$("#splStatusDescr_NAME").val('');
		$("#itemType12_NAME").val('');
		$("#custType_NAME").val('');
		
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_itemType12").checkAllNodes(false);
	} 

function doExcel(){
 	var tableName = "dataTable";
 	if($("#showType").val()=="1"){
 		tableName = "dataTableDesign";
 	}else if($("#showType").val()=="2"){
 		tableName = "dataTableProject";
 	}else if($("#showType").val()=="3"){
 		tableName = "dataTable";
 	}
	var url = "${ctx}/admin/prjMinusAnaly/doExcel";
	doExcelAll(url,tableName,"page_form");
}

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>结算日期从</label> 
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>至</label> 
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>统计类型</label>
							<select id="showType" name="showType" style="width:160px" >
								<option value="1" selected>按设计师</option>
								<option value="2">按项目经理</option>
								<option value="3">按明细</option>
							</select>
						</li>
						<li>
							<label>材料类型</label>
							<select id="itemType1" name="itemType1" style="width:160px" onchange="chgSel()">
								<option value="ZC" selected>主材</option>
								<option value="JC">集成</option>
							</select>
						</li>
						<!-- <li>
							<label>项目经理</label> 
							<input type="text" id="projectMan" name="projectMan"/>
						</li> -->
						<li>
							<label>事业部</label> 
							<house:dict id="department1"  dictCode="" sql="select * from tDepartment1 where DepType='0' and expired='F' "
							 sqlLableKey="desc1" sqlValueKey="code" value="${customer.department1 }"></house:dict>
						</li>
						<li>
							<label>工程部</label> 
							<house:dict id="department2"  dictCode="" sql="select * from tDepartment2 where DepType='3' and expired='F' "
							sqlLableKey="desc1" sqlValueKey="code" value="${customer.department2 }"></house:dict>
						</li>
						<li>
							<label>客户类型</label> 
							<house:xtdmMulit id="custType" dictCode="CUSTTYPE" selectedValue="${customer.custType }"></house:xtdmMulit>
						</li>
						<li id="itemType">
							<label>材料分类</label>
							<house:DictMulitSelect id="itemType12" dictCode="" sql="
							select code ,descr from tItemType12  where ItemType1 ='ZC' " 
							sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
						</li>
						<li id="isCup" hidden="true">
							<label>是否橱柜</label>
							<select id="isCupboard" name="isCupboard" style="width:160px">
								<option value="" selected>请选择...</option>
								<option value="1" selected>是</option>
								<option value="0">否</option>
							</select>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system" id="view" title="根据设计师、项目经理查看明细">
						<span>查看明细</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
						<span>导出excel</span>
					</button>
				</div>
			</div>
			<div id="content-list">
				<div id="tabDesign">
					<table id= "dataTableDesign"></table>
				</div>
				<div id="tabProject" hidden="true">
					<table id= "dataTableProject"  ></table>
				</div>
				<div id="tabDetail" hidden="true">
					<table id= "dataTable" ></table>
				</div>
			</div>
		</div>
	</body>	
</html>
