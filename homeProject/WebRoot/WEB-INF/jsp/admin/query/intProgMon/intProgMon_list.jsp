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
	<title>集成进度跟踪</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<style>
	#dataTable_notAppPager_left{
		width: 150px !important;
	}
	#dataTable_notAppPager_right{
		width: 150px !important;
	}
</style>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
var ExcTableId="dataTable";
var tableName="集成进度跟踪_未出货明细";
var countType="1";
var itemType2="CG";
$(function(){
	$("#content-list-notInstall").hide();
	$("#content-list-notDeliver").hide();
	//$("#content-list-notApp").hide();
	$("#content-list-notSetWorker").hide();
	$("#content-list").hide();
	$("#supplierCode").openComponent_supplier();
	changeType();	
	var postData = $("#page_form").jsonForm();
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-200,
		//url:"${ctx}/admin/IntProgMon/goJqGrid",
		//postData:{itemType2:"CG",countType:"1",dateTo:'${customer.dateTo}',constructStatus:"1"},
		styleUI: "Bootstrap", 
		sortable: true,
		colModel : [
			{name: "楼盘", index: "楼盘", width: 150, label: "楼盘", sortable: true, align: "left",},
			{name: "衣柜下单时间", index: "衣柜下单时间", width: 100, label: "下单时间", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜下单时间", index: "橱柜下单时间", width: 100, label: "下单时间", sortable: true, align: "left",formatter:formatDate},
			{name: "衣柜计划发货时间", index: "衣柜计划发货时间", width: 100, label: "计划发货时间", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜计划发货时间", index: "橱柜计划发货时间", width: 100, label: "计划发货时间", sortable: true, align: "left",formatter:formatDate},
			{name: "衣柜出货申请日期", index: "衣柜出货申请日期", width: 100, label: "申请日期", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜出货申请日期", index: "橱柜出货申请日期", width: 100, label: "申请日期", sortable: true, align: "left",formatter:formatDate},
			{name: "衣柜供应商", index: "衣柜供应商", width: 95, label: "供应商", sortable: true, align: "left",},
			{name: "橱柜供应商", index: "橱柜供应商", width: 95, label: "供应商", sortable: true, align: "left",},
			{name: "集成部", index: "集成部", width: 95, label: "集成部", sortable: true, align: "left",},
			{name: "项目经理", index: "项目经理", width: 95, label: "项目经理", sortable: true, align: "left",},
			{name: "department2descr", index: "department2descr", width: 95, label: "工程部", sortable: true, align: "left",},
			{name: "集成设计师", index: "集成设计师", width: 95, label: "集成设计师", sortable: true, align: "left",},
			{name: "饰面验收时间", index: "饰面验收时间", width: 110, label: "饰面验收时间", sortable: true, align: "left",formatter:formatTime},
			{name: "底漆验收时间", index: "底漆验收时间", width: 110, label: "底漆验收时间", sortable: true, align: "left",formatter:formatTime},
			{name: "remarks", index: "remarks", width: 110, label: "备注", sortable: true, align: "left",},
		],
	});
	
	Global.JqGrid.initJqGrid("dataTable_notInstall",{
		height:$(document).height()-$("#content-list").offset().top-200,
		styleUI: "Bootstrap", 
		sortable: true,
		colModel : [
			{name: "楼盘", index: "楼盘", width: 150, label: "楼盘", sortable: true, align: "left",},
			{name: "衣柜下单时间", index: "衣柜下单时间", width: 100, label: "下单时间", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜下单时间", index: "橱柜下单时间", width: 100, label: "下单时间", sortable: true, align: "left",formatter:formatDate},
			{name: "衣柜发货时间", index: "衣柜发货时间", width: 100, label: "实际出货时间", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜发货时间", index: "橱柜发货时间", width: 100, label: "实际出货时间", sortable: true, align: "left",formatter:formatDate},
			{name: "衣柜预计安装日期", index: "衣柜预计安装日期", width: 100, label: "预计安装日期", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜预计安装日期", index: "橱柜预计安装日期", width: 100, label: "预计安装日期", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜供应商", index: "橱柜供应商", width: 95, label: "供应商", sortable: true, align: "left",},
			{name: "衣柜供应商", index: "衣柜供应商", width: 95, label: "供应商", sortable: true, align: "left",},
			{name: "集成部", index: "集成部", width: 95, label: "集成部", sortable: true, align: "left",},
			{name: "项目经理", index: "项目经理", width: 95, label: "项目经理", sortable: true, align: "left",},
			{name: "department2descr", index: "department2descr", width: 95, label: "工程部", sortable: true, align: "left",},
			{name: "集成设计师", index: "集成设计师", width: 95, label: "集成设计师", sortable: true, align: "left",},
			//{name: "remarks", index: "remarks", width: 110, label: "备注", sortable: true, align: "left",},
			
		],
	});
	
	Global.JqGrid.initJqGrid("dataTable_notDeliver",{
		height:$(document).height()-$("#content-list").offset().top-200,
		styleUI: "Bootstrap", 
		sortable: true,
		colModel : [
			{name: "楼盘", index: "楼盘", width: 150, label: "楼盘", sortable: true, align: "left",},
			{name: "衣柜实际安装时间", index: "衣柜实际安装时间", width: 100, label: "实际安装时间", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜实际安装时间", index: "橱柜实际安装时间", width: 100, label: "实际安装时间", sortable: true, align: "left",formatter:formatDate},
			{name: "衣柜预计交付时间", index: "衣柜预计交付时间", width: 100, label: "预计交付时间", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜预计交付时间", index: "橱柜预计交付时间", width: 100, label: "预计交付时间", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜供应商", index: "橱柜供应商", width: 95, label: "供应商", sortable: true, align: "left",},
			{name: "衣柜供应商", index: "衣柜供应商", width: 95, label: "供应商", sortable: true, align: "left",},
			{name: "集成部", index: "集成部", width: 95, label: "集成部", sortable: true, align: "left",},
			{name: "项目经理", index: "项目经理", width: 95, label: "项目经理", sortable: true, align: "left",},
			{name: "department2descr", index: "department2descr", width: 95, label: "工程部", sortable: true, align: "left",},
			{name: "集成设计师", index: "集成设计师", width: 95, label: "集成设计师", sortable: true, align: "left",},
			//{name: "remarks", index: "remarks", width: 110, label: "备注", sortable: true, align: "left",},
		],
	});
	// 未下单明细
	Global.JqGrid.initJqGrid("dataTable_notApp",{
		height:$(document).height()-$("#content-list").offset().top-200,
		url:"${ctx}/admin/IntProgMon/goNotAppJqGrid",
		postData:{itemType2:"CG",countType:"1",dateTo:'${customer.dateTo}',constructStatus:"1"},
		styleUI: "Bootstrap", 
		sortable: true,
		colModel : [
			{name: "code", index: "code", width: 150, label: "客户编号", sortable: true, align: "left",hidden:true},
			{name: "address", index: "address", width: 220, label: "楼盘", sortable: true, align: "left",},
			{name: "appdate", index: "appdate", width: 100, label: "测量申请日期", sortable: true, align: "left",formatter:formatDate},
			{name: "designdate", index: "designdate", width: 100, label: "设计完成日期", sortable: true, align: "left",formatter:formatDate},
			{name: "preappdate", index: "preappdate", width: 100, label: "预计下单日期", sortable: true, align: "left",formatter:formatDate},
			{name: "cupappdate", index: "cupappdate", width: 100, label: "橱柜下单日期", sortable: true, align: "left",formatter:formatDate,hidden:true},
			{name: "intappdate", index: "intappdate", width: 100, label: "集成下单日期", sortable: true, align: "left",formatter:formatDate,hidden:true},
			// {name: "department2", index: "department2", width: 95, label: "集成部", sortable: true, align: "left",hidden:true},
			// {name: "intdeptdescr", index: "intdeptdescr", width: 95, label: "集成部", sortable: true, align: "left",},
			{name: "cupdesign", index: "cupdesign", width: 95, label: "橱柜设计师", sortable: true, align: "left",hidden:true},
			{name: "cupdesigndescr", index: "cupdesigndescr", width: 95, label: "橱柜设计师", sortable: true, align: "left",},
			{name: "intdesign", index: "intdesign", width: 95, label: "集成设计师", sortable: true, align: "left",hidden:true},
			{name: "intdesigndescr", index: "intdesigndescr", width: 95, label: "集成设计师", sortable: true, align: "left",},
			{name: "reqnum", index: "reqnum", width: 95, label: "需求数量", sortable: true, align: "left",hidden:true},
			{name: "overtime", index: "overtime", width: 95, label: "超时天数", sortable: true, align: "left",},
			{name: "dealremark", index: "dealremark", width: 150, label: "处理说明", sortable: true, align: "left",},
			{name: "iaappdate", index: "iaappdate", width: 100, label: "最早领料时间", sortable: true, align: "left",formatter:formatDate},
		],
	});
	// 未安排工人明细
	Global.JqGrid.initJqGrid("dataTable_notSetWorker",{
		height:$(document).height()-$("#content-list").offset().top-67,
		styleUI: "Bootstrap", 
		sortable: true,
		colModel : [
			{name: "楼盘", index: "楼盘", width: 150, label: "楼盘", sortable: true, align: "left",},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left",},
			{name: "衣柜发货时间", index: "衣柜发货时间", width: 100, label: "实际出货时间", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜发货时间", index: "橱柜发货时间", width: 100, label: "实际出货时间", sortable: true, align: "left",formatter:formatDate},
			{name: "橱柜供应商", index: "橱柜供应商", width: 120, label: "供应商", sortable: true, align: "left",},
			{name: "衣柜供应商", index: "衣柜供应商", width: 120, label: "供应商", sortable: true, align: "left",},
			{name: "集成部", index: "集成部", width: 95, label: "集成部", sortable: true, align: "left",},
			{name: "项目经理", index: "项目经理", width: 95, label: "项目经理", sortable: true, align: "left",},
			{name: "项目经理电话", index: "项目经理电话", width: 110, label: "项目经理电话", sortable: true, align: "left",},
			{name: "department2descr", index: "department2descr", width: 95, label: "工程部", sortable: true, align: "left",},
			{name: "集成设计师", index: "集成设计师", width: 95, label: "集成设计师", sortable: true, align: "left",},
			{name: "板材面积", index: "板材面积", width: 95, label: "板材面积", sortable: true, align: "right",sum:true},
			{name: "安装费", index: "安装费", width: 95, label: "安装费", sortable: true, align: "right",sum:true},
		],
	});
	jQuery("#dataTable").setGridParam().hideCol("衣柜计划发货时间").trigger("reloadGrid");
	jQuery("#dataTable").setGridParam().hideCol("衣柜下单时间").trigger("reloadGrid");
	jQuery("#dataTable").setGridParam().hideCol("衣柜供应商").trigger("reloadGrid");
	jQuery("#dataTable").setGridParam().hideCol("衣柜出货申请日期").trigger("reloadGrid");
	goto_query();
	
});
function goto_query(){
		if(countType=="1"){
			ExcTableId="dataTable";
			tableName="集成进度跟踪_未出货明细";
		}else if(countType=="2"){
			ExcTableId="dataTable_notInstall";
			tableName="集成进度跟踪_未安装明细";
		}else if(countType=="3"){
			ExcTableId="dataTable_notDeliver";
			tableName="集成进度跟踪_未交付明细";
		}else if(countType=="4"){
			ExcTableId="dataTable_notApp";
			tableName="集成进度跟踪_未下单明细";
		}else if(countType=="5"){
			ExcTableId="dataTable_notSetWorker";
			tableName="集成进度跟踪_未安排工人明细";
		}
		if(countType=='1'){
			$("#content-list").show();
			$("#content-list-notInstall").hide();
			$("#content-list-notDeliver").hide();
			$("#content-list-notApp").hide();
			$("#content-list-notSetWorker").hide();
			$("#dataTable").jqGrid("setGridParam",{datatype:"json",postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/IntProgMon/goJqGrid",}).trigger("reloadGrid");
			if(itemType2=="CG"){
				jQuery("#dataTable").setGridParam().hideCol("衣柜计划发货时间").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("衣柜下单时间").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("衣柜供应商").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("衣柜出货申请日期").trigger("reloadGrid");
				
				jQuery("#dataTable").setGridParam().showCol("橱柜下单时间").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("橱柜计划发货时间").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("橱柜供应商").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("橱柜出货申请日期").trigger("reloadGrid");
			} else {
				jQuery("#dataTable").setGridParam().showCol("衣柜计划发货时间").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("衣柜下单时间").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("衣柜供应商").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("衣柜出货申请日期").trigger("reloadGrid");
				
				jQuery("#dataTable").setGridParam().hideCol("橱柜下单时间").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("橱柜计划发货时间").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("橱柜供应商").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("橱柜出货申请日期").trigger("reloadGrid");
			}
		}else if(countType=='2'){
			$("#content-list").hide();
			$("#content-list-notDeliver").hide();
			$("#content-list-notInstall").show();
			$("#content-list-notApp").hide();
			$("#content-list-notSetWorker").hide();
			$("#dataTable_notInstall").jqGrid("setGridParam",{datatype:"json",postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/IntProgMon/goNotInstallJqGrid",}).trigger("reloadGrid");
			if(itemType2=="CG"){
			    jQuery("#dataTable_notInstall").setGridParam().hideCol("衣柜下单时间").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().hideCol("衣柜发货时间").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().hideCol("衣柜预计安装日期").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().hideCol("衣柜供应商").trigger("reloadGrid");
				
				jQuery("#dataTable_notInstall").setGridParam().showCol("橱柜下单时间").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().showCol("橱柜发货时间").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().showCol("橱柜预计安装日期").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().showCol("橱柜供应商").trigger("reloadGrid");
			} else {
			    jQuery("#dataTable_notInstall").setGridParam().showCol("衣柜下单时间").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().showCol("衣柜发货时间").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().showCol("衣柜预计安装日期").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().showCol("衣柜供应商").trigger("reloadGrid");
				
				jQuery("#dataTable_notInstall").setGridParam().hideCol("橱柜下单时间").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().hideCol("橱柜发货时间").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().hideCol("橱柜预计安装日期").trigger("reloadGrid");
				jQuery("#dataTable_notInstall").setGridParam().hideCol("橱柜供应商").trigger("reloadGrid");
			}
		}else if ("3" == countType){
			$("#content-list").hide();
			$("#content-list-notInstall").hide();
			$("#content-list-notDeliver").show();
			$("#content-list-notApp").hide();
			$("#content-list-notSetWorker").hide();
			$("#dataTable_notDeliver").jqGrid("setGridParam",{datatype:"json",postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/IntProgMon/goNotDeliverJqGrid",}).trigger("reloadGrid");
			if(itemType2=="CG"){
				jQuery("#dataTable_notDeliver").setGridParam().hideCol("衣柜实际安装时间").trigger("reloadGrid");
				jQuery("#dataTable_notDeliver").setGridParam().hideCol("衣柜预计交付时间").trigger("reloadGrid");
				jQuery("#dataTable_notDeliver").setGridParam().hideCol("衣柜供应商").trigger("reloadGrid");
				jQuery("#dataTable_notDeliver").setGridParam().showCol("橱柜实际安装时间").trigger("reloadGrid");
				jQuery("#dataTable_notDeliver").setGridParam().showCol("橱柜预计交付时间").trigger("reloadGrid");
				jQuery("#dataTable_notDeliver").setGridParam().showCol("橱柜供应商").trigger("reloadGrid");
			}else {
				jQuery("#dataTable_notDeliver").setGridParam().showCol("衣柜实际安装时间").trigger("reloadGrid");
				jQuery("#dataTable_notDeliver").setGridParam().showCol("衣柜预计交付时间").trigger("reloadGrid");
				jQuery("#dataTable_notDeliver").setGridParam().showCol("衣柜供应商").trigger("reloadGrid");
				jQuery("#dataTable_notDeliver").setGridParam().hideCol("橱柜实际安装时间").trigger("reloadGrid");
				jQuery("#dataTable_notDeliver").setGridParam().hideCol("橱柜预计交付时间").trigger("reloadGrid");
				jQuery("#dataTable_notDeliver").setGridParam().hideCol("橱柜供应商").trigger("reloadGrid");
			}
		}else if (countType==''||"4" == countType) {//添加第四张表：未下单明细--add by zb
			$("#content-list").hide();
			$("#content-list-notInstall").hide();
			$("#content-list-notDeliver").hide();
			$("#content-list-notApp").show();
			$("#content-list-notSetWorker").hide();
			$("#dataTable_notApp").jqGrid("setGridParam",
				{
					datatype:"json",
					postData:$("#page_form").jsonForm(),
					page:1,
					url:"${ctx}/admin/IntProgMon/goNotAppJqGrid",
				}
			).trigger("reloadGrid");
			
		}else if ("5" == countType) {//添加第五张表：未安排工人明细--add by zb
			$("#content-list").hide();
			$("#content-list-notInstall").hide();
			$("#content-list-notDeliver").hide();
			$("#content-list-notApp").hide();
			$("#content-list-notSetWorker").show();
			$("#dataTable_notSetWorker").jqGrid("setGridParam",
				{
					datatype:"json",
					postData:$("#page_form").jsonForm(),
					page:1,
					url:"${ctx}/admin/IntProgMon/goNotSetWorkerJqGrid",
				}
			).trigger("reloadGrid");
			if(itemType2=="CG"){
				jQuery("#dataTable_notSetWorker").setGridParam()
				.hideCol("衣柜发货时间")
				.hideCol("衣柜供应商")
				.showCol("橱柜发货时间")
				.showCol("橱柜供应商")
				.hideCol("板材面积").trigger("reloadGrid");
			} else {
				jQuery("#dataTable_notSetWorker").setGridParam()
				.showCol("衣柜发货时间")
				.showCol("衣柜供应商")
				.hideCol("橱柜发货时间")
				.hideCol("橱柜供应商")
				.showCol("板材面积").trigger("reloadGrid");
			}
		}
	};


function changeType(){
	countType=$.trim($("#countType").val());
	if($.trim($("#countType").val())!='1'){
		$("#cantInstall_li").attr("hidden","true");
		$("#cantInstall").val("");
	}else{
		$("#cantInstall_li").removeAttr("hidden","true");
	}
	
	if("4" == $.trim($("#countType").val())){//当表为未下单明细时，隐藏集成部 --add by zb
		$("#department2").parent().hide();
		$("#department2").val("");
		$("#designCompleted").parent().show();
	}else{
		$("#department2").parent().show();
		$("#designCompleted").parent().hide();
	}
}
function changeItemType2(){
	itemType2=$.trim($("#itemType2").val());
}
function doExcel(){
	var url = "${ctx}/admin/IntProgMon/doExcel";
	doExcelAll(url,ExcTableId,"page_form");
}
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#openComponent_supplier_supplierCode").val('');
	$("#department2").val("");
	document.getElementById('itemType2').style.display = 'CG';
	document.getElementById('countType').style.display = '1';
}

function toggleDesignCompleted(obj) {
    obj.value = obj.checked ? "1" : "0";
}

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="itemType1" id="itemType1"/>
					<ul class="ul-form">
						<li>
							<label>材料类型2</label>
							<select id="itemType2" name="itemType2" style="width:160px" onchange="changeItemType2()">
								<option value="CG">橱柜</option>
								<option value="YG">衣柜</option>
							</select>
						</li>
						<li>
			              <label>计划时间小于</label>
			              <input type="text" id="dateTo"
			                     name="dateTo" class="i-date"
			                     onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d}',})"
			                     value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>"/>
			            </li>
			            <li>
			            	<label>供应商</label>
			            	<input type="text" id="supplierCode" name="supplierCode" style="width:160px "/>
			            </li>
			            <li class="form-validate">
							<label>集成部</label>
							<house:dict id="department2" dictCode="" sql="select * from tdepartment2 where expired='F' and department1='15'" sqlValueKey="Code" sqlLableKey="Desc2" ></house:dict>
						</li>
						<li>
							<label>统计类型</label>
							<select id="countType" name="countType" style="width:160px" onchange="changeType()">
								<option value="4">未下单明细</option>
								<option value="1">未出货明细</option>
								<option value="5">未安排工人明细</option>
								<option value="2">未安装明细</option>
								<option value="3">未交付明细</option>	
							</select>
						</li>
						<li id="cantInstall_li" >
							<label>不能安装</label>
							<house:xtdm id="cantInstall" dictCode="YESNO" ></house:xtdm>
						</li>
						<li><!-- 集成进度跟踪，增加施工状态查询条件。  --赵斌 -->
							<label>施工状态</label>
							<house:xtdmMulit id="constructStatus" dictCode="CONSTRUCTSTATUS" selectedValue="1"></house:xtdmMulit>
						</li>
						<li>
			            	<label>楼盘</label>
			            	<input type="text" id="address" name="address" style="width:160px "/>
			            </li>
						<li>
			            	<label>设计完成</label>
			            	<house:xtdm id="designCompleted" dictCode="YESNO"></house:xtdm>
			            </li>
						<li class="search-group" >
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<!--query-form-->
		<!-- panelBar -->
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
				<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
					<span>导出excel</span>
				</button>
			</div>
		</div>
				<!-- panelBar End -->
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
		<div id="content-list-notInstall">
			<table id= "dataTable_notInstall"></table>
			<div id="dataTable_notInstallPager"></div>
		</div>
		<div id="content-list-notDeliver">
			<table id= "dataTable_notDeliver"></table>
			<div id="dataTable_notDeliverPager"></div>
		</div>
		<div id="content-list-notApp"><!-- 未下单明细 -->
			<table id= "dataTable_notApp"></table>
			<div id="dataTable_notAppPager"></div>
		</div>
		<div id="content-list-notSetWorker"><!-- 未安排工人明细 -->
			<table id= "dataTable_notSetWorker"></table>
			<div id="dataTable_notSetWorkerPager"></div>
		</div>
	</body>	
</html>
