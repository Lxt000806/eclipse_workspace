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
	<title>工程进度施工日志</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
      .SelectBG_yellow{
          background-color:yellow;
         }
 </style>
<script type="text/javascript"> 
$(function(){
	
	//初始化表格	
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjProg/goPrjLogJqGrid",
		postData:{custCode:'${prjProg.custCode}',prjJobType:'3,4,5,8'},
		height:$(document).height()-$("#content-list").offset().top-50,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "TypeDescr", index: "TypeDescr", width: 98, label: "任务类型", sortable: true, align: "left"},
				{name: "Remarks", index: "Remarks", width: 308, label: "任务说明", sortable: true, align: "left"},
				{name: "BeginDays", index: "BeginDays", width: 98, label: "开始日", sortable: true, align: "left"},
				{name: "EndDays", index: "EndDays", width: 98, label: "完成日", sortable: true, align: "left"},
				{name: "Days", index: "Days", width: 98, label: "执行天数", sortable: true, align: "left"},
				{name: "DelayDays", index: "DelayDays",hidden:true, width: 98, label: "超时天数", sortable: true, align: "left"},
				{name: "delaydayss", index: "delaydayss", width: 98, label: "超时天数", sortable: true, align: "left"},
				{name: "BeginDate", index: "BeginDate", width: 140, label: "开始时间", sortable: true, align: "left",formatter:formatTime},
				{name: "EndDate", index: "EndDate", width: 140, label: "完成时间", sortable: true, align: "left",formatter:formatTime},
				
			],
			gridComplete:function(){
            	var ids = $("#dataTable").getDataIDs();
            	 for(var i=0;i<ids.length;i++){
              	   var rowData = $("#dataTable").getRowData(ids[i]);
              	   if(rowData.TypeDescr=="工程进度"){
                     $('#'+ids[i]).find("td").addClass("SelectBG");
              	   }
              	   
             	 }
         	},
			
		});
		
});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="pageContent">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
								<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
									<span>关闭</span>
								</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<li>
								<label>任务类型</label>
								<house:xtdmMulit id="prjJobType" dictCode="PRJJOBTYPE" selectedValue="3,4,5,8"></house:xtdmMulit>                     
							</li>
							</li>
							<li>
								<label>是否超时</label>
								<house:xtdm  id="isDelay" dictCode="YESNO"   style="width:160px;" value="${prjProg.isDelay}"></house:xtdm>
							</li>
							<li hidden="true">
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${prjProg.custCode}"/>
							</li>
							<li  class="search-group">
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							</li>
						</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
							<button type="button" class="btn btn-system "  onclick="doExcelNow('施工日志')" title="导出检索条件数据">
								<span>导出excel</span>
							</button>	
					</div>
				</div>
				<div id="content-list">
					<table id= "dataTable"></table>
				</div> 
			</div>
	</body>	
</html>
