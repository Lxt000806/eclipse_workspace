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
	<title>前端计划与分析</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#planCzy").openComponent_employee({showValue:"${empWorkPlan.planCzy}",showLabel:"${empWorkPlan.planCzyDescr}"});	
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/empWorkPlan/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap',
		colModel : [
			{name:'pk',	index:'pk',	width:90,	label:'pk',	sortable:true,align:"left" ,hidden:true },
			{name:'planczydescr',	index:'planczydescr',	width:90,	label:'计划人',	sortable:true,align:"left"  },
			{name:'planczytypedescr',	index:'planczytypedescr',	width:90,	label:'计划类型',	sortable:true,align:"left"  },
			{name:'planbegindate',	index:'planbegindate',	width:100,	label:'计划开始时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'lastupdatedby',	index:'lastupdatedby',	width:90,	label:'最后操作人员',	sortable:true,align:"left",},
			{name:'lastupdate',	index:'lastupdate',	width:150,	label:'最后修改时间',	sortable:true,align:"left",formatter: formatTime},
			{name:'expired',	index:'expired',	width:60,	label:'过期',	sortable:true,align:"left",},
			{name:'actionlog',	index:'actionlog',	width:80,	label:'操作日志',	sortable:true,align:"left",},
		],
	});
	
	$("#designPlan").on("click",function(){
		var result=isExistsPlan("2");
		if(result=="1"){
			art.dialog({
				content:"已存在本周和下周计划",
			});
			return;
		}
		Global.Dialog.showDialog("addDesignPlan",{
			title:"前端计划与分析——新增设计计划",
			url:"${ctx}/admin/empWorkPlan/goAddDesignPlan",
			height:600,
			width:650,
			returnFun:goto_query
		});
	});
	
	$("#businessPlan").on("click",function(){
		var result=isExistsPlan("1");
		if(result=="1"){
			art.dialog({
				content:"已存在本周和下周计划",
			});
			return;
		}
		Global.Dialog.showDialog("addBusiNessPlan",{
			title:"前端计划与分析——新增业务计划",
			url:"${ctx}/admin/empWorkPlan/goAddBusinessPlan",
			height:600,
			width:789,
			returnFun:goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		var width=660;
		if(ret.planczytypedescr=="业务员"){
			width=799;
		}
		var lastDayOfWeek=getAddDate(ret.planbegindate, 6);
		if(lastDayOfWeek<formatDate(new Date())){
			art.dialog({
				content:"非本周或下周计划，无法编辑",
			});
			return;
		}
		if(ret){
			Global.Dialog.showDialog("Update",{
				title:"前端计划与分析——编辑计划",
				url:"${ctx}/admin/empWorkPlan/goUpdate",
				postData:{id:ret.pk},
				height:600,
				width:width,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		var width=1080;
		if(ret.planczytypedescr=="业务员"){
			width=1200;
		}
		if(ret){
			Global.Dialog.showDialog("View",{
				title:"前端计划与分析——查看计划",
				url:"${ctx}/admin/empWorkPlan/goView",
				postData:{id:ret.pk},
				height:600,
				width:width,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
});

function doExcel(){
	var url = "${ctx}/admin/empWorkPlan/doExcel";
	doExcelAll(url);
}

 function clearForm(){
 	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#openComponent_employee_planCzy").val('');
} 

function isExistsPlan(planCzyType){
	var result="0";
	$.ajax({
		url:"${ctx}/admin/empWorkPlan/isExistsPlan",
		type:"post",
		data:{planCzyType:planCzyType,planCzy:"${USER_CONTEXT_KEY.czybh}"},
		dataType:"json",
		cache:false,
		async:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
		},
		success:function(obj){
			result=obj;
		}
	});
	return result;
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="expired" name="expired" value="${empWorkPlan.expired }" />
						<ul class="ul-form">
							<li>
								<label>计划开始时间从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>计划人</label>
								<input type="text" id="planCzy" name="planCzy" style="width:160px;"/>
							</li>
							<li>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
							</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
   			  			<house:authorize authCode="EMPWORKPLAN_DESIGNPLAN">
							<button type="button" class="btn btn-system " id="designPlan"><span>新增设计计划</span> 
						</house:authorize>
						<house:authorize authCode="EMPWORKPLAN_BUSINESSPLAN">
							<button type="button" class="btn btn-system " id="businessPlan"><span>新增业务计划</span> 
						</house:authorize>
						<house:authorize authCode="EMPWORKPLAN_UPDATE">
							<button type="button" class="btn btn-system "  id="update"><span>编辑计划</span> 
						</house:authorize>
						<house:authorize authCode="EMPWORKPLAN_VIEW">
							<button type="button" class="btn btn-system "  id="view"><span>查看计划</span> 
						</house:authorize>
						<button type="button" class="btn btn-system "  onclick="doExcel()" ><span>导出excel</span>
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
	</body>	
</html>
