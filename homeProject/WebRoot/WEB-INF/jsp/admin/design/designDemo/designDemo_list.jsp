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
	<title>设计案例</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/designDemo/doExcel";
		doExcelAll(url);
	}
	
	$(function(){
		$("#designMan").openComponent_employee();	
		$("#builderCode").openComponent_builder();
		var postData = $("#page_form").jsonForm();
		var gridOption ={
			url:"${ctx}/admin/designDemo/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-72,
			styleUI:"Bootstrap", 
			colModel : [
				{name: "no", index: "no", width: 80, label: "编号", sortable: true, align: "left",},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left",},
				{name: "address", index: "address", width: 170, label: "楼盘", sortable: true, align: "left",},
				{name: "descr", index: "descr", width: 120, label: "项目名称", sortable: true, align: "left",},
				{name: "designdescr", index: "designdescr", width: 80, label: "设计师", sortable: true, align: "left"},
				{name: "styledescr", index: "styledescr", width: 80, label: "设计风格", sortable: true, align: "left",},
				{name: "layoutdescr", index: "layoutdescr", width: 85, label: "户型", sortable: true, align: "left",},
				{name: "area", index: "area", width: 85, label: "面积", sortable: true, align: "right",},
				{name: "designremark", index: "designremark", width: 165, label: "设计说明", sortable: true, align: "left",},
				{name: "ispustdescr", index: "ispustdescr", width: 85, label: "是否播报", sortable: true, align: "left",},
				{name: "lastupdate", index: "lastupdate", width: 90, label: "最后更新时间", sortable: true, align: "left",formatter:formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后更新人员", sortable: true, align: "left",},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left",},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left",},
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
		//安排
		$("#save").on("click",function(){
			Global.Dialog.showDialog("Save",{
				title:"设计案例——新增",
				url:"${ctx}/admin/designDemo/goSave",
				postData:{no:""},
				height:740,
				width:1300,
				returnFun:goto_query
			});
		});
		
		//编辑
		$("#update").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("update",{
				title:"设计案例——编辑",
				url:"${ctx}/admin/designDemo/goUpdate",
				postData:{no:ret.no},
				height:770,
				width:1300,
				returnFun:goto_query
			});
		});
		console.log(111);
		$("#view").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("view",{
				title:"设计案例——查看",
				url:"${ctx}/admin/designDemo/goView?no="+ret.no,
				//postData:{no:ret.no},
				height:740,
				width:1300,
				returnFun:goto_query
			});
		});
	});
	
	function  clearForm(){
		$("#page_form input[type='text']").val('');
		$("#openComponent_builder_builderCode").val('');
		$("#openComponent_employee_designMan").val('');
		$("#builderCode").val('');
		$("#designMan").val('');
		$.fn.zTree.getZTreeObj("tree_designSty").checkAllNodes(false);
		$("#designSty").val('');
		$("#designSty_NAME").val('');
		$("#page_form select").val('');
		
	}
	
	
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired"  name="expired" value="${purchase.expired }"/>
					<ul class="ul-form">
						<div class="validate-group row" >
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address"/>
							</li>
							<li>
								<label>项目编号</label>
								<input type="text" id="builderCode" name="builderCode"/>
							</li>
							<li>
								<label>设计师</label>
								<input type="text" id="designMan" name="designMan"/>
							</li>
							<li class="form-validate">
								<label>户型</label>
								<house:xtdm id="layout" dictCode="DLAYOUT"  style="width:160px;"></house:xtdm>
							</li>
							<li>
								<label>设计风格</label>
								<house:xtdmMulit id="designSty" dictCode="DDESIGNSTL" selectedValue=""></house:xtdmMulit>                     
							</li>
							<li>
								<label>面积</label>
 								<select id="areaArrange" name="areaArrange" style="width: 160px;" >
 									<option value="">请选择...</option>
 									<option value="1">1 60以下</option>
 									<option value="2">2 60~80</option>
 									<option value="3">3 80~100</option>
 									<option value="4">4 100以上</option>
 								</select>
							</li>
							<li>
								<label>是否播报</label>
								<house:xtdm id="isPushCust" dictCode="YESNO"  style="width:160px;"></house:xtdm>
							</li>
							<li class="search-group" >
								<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${designDemo.expired }" onclick="hideExpired(this)" 
								 ${designDemo!='T'?'checked':'' } /><p>隐藏过期</p>
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
								<button id="clear" type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
							</li>
						</div>	
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
  			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
				<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
					<span>导出excel</span>
				</button>
			</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
