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
	<title> 班组在建数</title>
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
function doExcel(){
	var url = "${ctx}/admin/workTypeConstructDetail/doOndoExcel";
	doExcelAll(url);
}
$(function(){
	$("#businessMan").openComponent_employee({showValue:'${resrCust.businessMan}',showLabel:'${resrCust.businessManDescr}'});	
	$("#builderCode").openComponent_builder({showValue:'${resrCust.builderCode}',showLabel:'${resrCust.builderDescr}'});	
	$("#prjRegionCode").openComponent_prjRegion({showValue:'${customer.prjRegionCode}',showLabel:'${customer.prjRegionCodeDescr}'});
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workTypeConstructDetail/goOndoJqGrid",
		postData:{workType12:'${workType12}',prjRegionCode:'${customer.prjRegionCode}',
			custType:'${customer.custType }',isSign:'${isSign}',department2:'${customer.department2}',workType12Dept:"${customer.workType12Dept}",
			workerClassify:"${customer.workerClassify}"} ,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'workercode',	index:'workercode',	width:90,	label:'班组',	sortable:true,align:"left" ,hidden:true},
			{name:'workerdescr',	index:'workerdescr',	width:90,	label:'班组',	sortable:true,align:"left" ,},
			{name:'worktypedescr',	index:'worktypedescr',	width:90,	label:'工种',	sortable:true,align:"left" ,},
			{name:'issigndescr',	index:'issigndescr',	width:90,	label:'是否签约',	sortable:true,align:"left" ,},
			{name:'leveldescr',	index:'leveldescr',	width:90,	label:'班组等级',	sortable:true,align:"left" ,},
			{name:'consnum',	index:'consnum',	width:90,	label:'在建套数',	sortable:true,align:"right" ,sum:true},

		],
	});
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("view",{
				title:"工种施工情况——班组在建工地",
				url:"${ctx}/admin/workTypeConstructDetail/goViewDetail",
				postData:{workerCode:ret.workercode,department2:"${customer.department2}"},
				height:700,
				width:1150,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
});
 


</script>
</head>
	<body>
	<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
							<button type="button" class="btn btn-system " id="view"><span>查看</span> 
						<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
								<span>导出excel</span>
							</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="body-box-list">
			<div class="query-form" hidden="true">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="workType12" name="workType12" value="${workType12}" />
					<input type="hidden" id="department2" name="department2" value="${customer.department2}" />
					<input type="hidden" id=workType12Dept name="workType12Dept" value="${customer.workType12Dept}" />
					<ul class="ul-form">
							<li>
								<label>签约类型</label>
								<house:xtdmMulit id="isSign" dictCode="WSIGNTYPE"  
								sqlValueKey="Code" sqlLableKey="Descr"  selectedValue="${isSign}"></house:xtdmMulit>
							</li> 
							<li><label>工程大区</label> <input type="text" id="prjRegionCode"
								name="prjRegionCode" style="width:160px;"
								value="${customer.prjRegionCode }" />
							</li>
							<li >
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
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
	</body>	
</html>
