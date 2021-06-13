<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/prjProgCheck/goPrjCheckJqGrid",
			postData:{custCode:"${customer.code}"},
		    rowNum:10000000,
			height:390,
		styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 100, label: "施工项目", sortable: true, align: "left",hidden:true} 	,		
				{name: "custcode", index: "custcode", width: 100, label: "施工项目", sortable: true, align: "left",hidden:true} 	,		
				{name: "prjitem", index: "prjitem", width: 100, label: "施工项目", sortable: true, align: "left",hidden:true} 	,		
				{name: "descr", index: "descr", width: 100, label: "施工项目", sortable: true, align: "left"} 	,		
				{name: "statusdescr", index: "statusdescr", width: 75, label: "工地状态", sortable: true, align: "left"} ,			
				{name: "ismodifydescr", index: "ismodifydescr", width: 75, label: "是否整改", sortable: true, align: "left"} ,			
				{name: "safepromdescr", index: "safepromdescr", width: 75, label: "安全问题", sortable: true, align: "left"} 	,		
				{name: "visualpromdescr", index: "visualpromdescr", width: 75, label: "形象问题", sortable: true, align: "left"} ,			
				{name: "artpromdescr", index: "artpromdescr", width: 75, label: "工艺问题", sortable: true, align: "left"} ,			
				{name: "remarks", index: "remarks", width: 100, label: "巡检说明", sortable: true, align: "left"} 	,		
				{name: "checkczydescr", index: "checkczydescr", width: 75, label: "巡检人", sortable: true, align: "left"} ,			
				{name: "date", index: "date", width: 100, label: "巡检日期", sortable: true, align: "left",formatter:formatTime}		,	
				{name: "isupprjprogdescr", index: "isupprjprogdescr", width: 80, label: "巡检更改进度", sortable: true, align: "left"} 	,		
				{name: "address", index: "address", width: 150, label: "地理位置", sortable: true, align: "left"} 		,	
				{name: "errposidescr", index: "errposidescr", width: 100, label: "位置正常", sortable: true, align: "left"} 	,		
			], 
 		};
       //初始化送货申请明细
	   Global.JqGrid.initJqGrid("dataTable_check",gridOption);


});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button style="align:left" type="button" class="btn btn-system "  id="viewCheckPhoto">
							<span>查看巡检图片 </span>
						</button>
		            </div>  
		          </div> 
		          </div> 		
			<div class="clear_float"></div>
			<!--query-form-->
				<div id="content-list" >
					<table id="dataTable_check"></table>
				</div>
		</div>



