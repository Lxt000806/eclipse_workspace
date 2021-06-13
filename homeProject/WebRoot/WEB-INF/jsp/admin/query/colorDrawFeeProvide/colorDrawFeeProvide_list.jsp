<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE html>
<html>
<head>
	<title>材料签单统计-主材明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
       .SelectBG{
           background-color:red;
           }
</style>

<script type="text/javascript"> 

$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-100,
		postData:$("#page_form").jsonForm(),
		url:"${ctx}/admin/colorDrawFeeProvide/goJqGrid",
		styleUI: 'Bootstrap',
		colModel : [
			{name: "address", index: "address", width: 180, label: "楼盘", sortable: true, align: "left", count: true},
			{name: "confirmdate", index: "confirmdate", width: 120, label: "图纸变更审核日期", sortable: true, align: "left",formatter:formatTime},
			{name: "drawnochg", index: "drawnochg", width: 80, label: "图纸无变更", sortable: true, align: "left", hidden: true},
			{name: "drawnochgdescr", index: "drawnochgdescr", width: 80, label: "图纸无变更", sortable: true, align: "left"},
			{name: "isfullcolordrawdescr", index: "isfullcolordrawdescr", width: 80, label: "效果图类型", sortable: true, align: "left"},
			{name: "drawqty", index: "drawqty", width: 100, label: "普通效果图数量", sortable: true, align: "right"},
			{name: "draw3dqty", index: "draw3dqty", width: 100, label: "3D效果图数量", sortable: true, align: "right"},
			{name: "qqconfirmdate", index: "qqconfirmdate", width: 120, label: "砌墙节点验收日期", sortable: true, align: "left",formatter:formatTime},
	     ],
	});
});

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/colorDrawFeeProvide/doExcel";
	doExcelAll(url);
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
							   <label>变更审核日期</label>
							   <input type="text" id="dateFrom"
							    name="dateFrom"  class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
                               <label>砌墙验收日期</label>
                               <input type="text" id="confirmWallDateFrom"
                                   name="confirmWallDateFrom"  class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                            </li>
                            <li>
                                <label>至</label>
                                <input type="text" id="confirmWallDateTo"
                                    name="confirmWallDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                            </li>
                            <li>
                            	<label>楼盘</label>
                            	<input type="text" id="address" name="address"  /> 
							<li >
								<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
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
						<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				  </div>
				</div>
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
