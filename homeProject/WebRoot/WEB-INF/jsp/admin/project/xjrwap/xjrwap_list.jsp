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
	<title>巡检任务安排</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/xjrwap/doExcel";
	doExcelAll(url);
}
function isExpired(obj){
	if ($(obj).is(':checked')){
		$('#expired').val('T');
	}else{
		$('#expired').val('F');
	}
}
$(function(){
	$("#checkCZY").openComponent_employee();	

	var postData = $("#page_form").jsonForm();
	//初始化表格
	var gridOption ={
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "no", index: "no", width: 100, label: "编号", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 100, label: "巡检类型", sortable: true, align: "left"},
			{name: "checkczy", index: "checkczy", width: 131, label: "巡检人", sortable: true, align: "left",hidden:true},
			{name: "checkczydescr", index: "checkczydescr", width: 131, label: "巡检人", sortable: true, align: "left"},
			{name: "ischeckdeptdescr", index: "ischeckdeptdescr", width: 89, label: "是否巡检部", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 194, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 96, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "expired", index: "expired", width: 98, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 116, label: "操作标志", sortable: true, align: "left"},
			{name: "lastupdatedby", index: "lastupdatedby", width: 121, label: "最后操作人", sortable: true, align: "left"}			
		],
	};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	$("#save").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"巡检任务安排——增加",
			url:"${ctx}/admin/xjrwap/goSave",
			height:700,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#copy").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
	        	content: "请选择一条记录"
	        });
		}
		Global.Dialog.showDialog("Update",{
			title:"巡检任务安排——复制",
			url:"${ctx}/admin/xjrwap/goCopy",
			postData:{no:ret.no},
			height:700,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
	        	content: "请选择一条记录"
	        });
		}
		Global.Dialog.showDialog("Update",{
			title:"巡检任务安排——编辑",
			url:"${ctx}/admin/xjrwap/goUpdate",
			postData:{no:ret.no},
			height:700,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
	        	content: "请选择一条记录"
	        });
		}
		Global.Dialog.showDialog("View",{
			title:"巡检任务安排——查看",
			url:"${ctx}/admin/xjrwap/goView",
			postData:{no:ret.no},
			height:700,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#updatePrjMan").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
	        	content: "请选择一条记录"
	        });
		}
		Global.Dialog.showDialog("View",{
			title:"巡检任务安排——重点巡检监理",
			url:"${ctx}/admin/xjrwap/goUpdatePrjMan",
			//postData:{no:ret.no},
			height:700,
			width:850,
			returnFun:goto_query
		});
	});
	
	$("#print").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
		        	content: "请选择一条记录"
		        });
			}
			var reportName = "xjrwap.jasper";
				Global.Print.showPrint(reportName, {
				planNo:ret.no,
				SUBREPORT_DIR: "<%=jasperPath%>" 
			});
		});	
	window.goto_query = function(){
		$("#dataTable").jqGrid("setGridParam",{sortname:'',postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/xjrwap/goJqGrid"}).trigger("reloadGrid");
	}
});
function doExcel(){
	var url = "${ctx}/admin/xjrwap/doExcel";
	doExcelAll(url);
}


</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  	<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value="${progCheckPlan.expired }"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>计划巡检日期从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>巡检类型</label>
								<house:xtdm id="type" dictCode="CHECKPLANTYPE"  value="${progCheckPlan.type }" ></house:xtdm>
							</li>
							<li>
								<label>编号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${progCheckPlan.no }"/>
							</li>
							<li>
								<label>巡检人</label>
								<input type="text" id="checkCZY" name="checkCZY" style="width:160px;" value="${progCheckPlan.checkCZY }"/>
							</li>
							<li class="search-group">
								<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${progCheckPlan.expired }" onclick="hideExpired(this)" 
								 ${progCheckPlan.expired!='T'?'checked':'' } /><p>隐藏过期</p>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
   			  		<house:authorize authCode="XJRWAP_SAVE">
						<button type="button" class="btn btn-system " id="save"><span>新增</span> 
						</button>
					</house:authorize>
					<house:authorize authCode="XJRWAP_COPY">
						<button type="button" class="btn btn-system " id="copy"><span>复制</span> 
						</button>
					</house:authorize>
					<house:authorize authCode="XJRWAP_UPDATE">
						<button type="button" class="btn btn-system "  id="update"><span>编辑</span> 
							</button>
					</house:authorize>
					<house:authorize authCode="XJRWAP_VIEW">
						<button type="button" class="btn btn-system "  id="view"><span>查看</span>
							</button>
					</house:authorize>
					<house:authorize authCode="XJRWAP_UPDATEPRJMAN">
						<button type="button" class="btn btn-system "  id="updatePrjMan"><span>重点巡检监理</span>
							</button>
					</house:authorize>
						<button type="button" class="btn btn-system "  onclick="doExcel()" >
							<span>导出excel</span>
							</button>
					<house:authorize authCode="XJRWAP_PRINT">
						<button type="button" class="btn btn-system " id="print"  >
								<span>打印</span>
							</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
	</body>	
</html>
