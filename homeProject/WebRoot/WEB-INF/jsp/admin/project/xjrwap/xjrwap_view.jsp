<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>巡检楼盘查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <body>
  	 <div class="body-box-form">
  	
  		<div class="content-form">
  			<!-- panelBar -->
  			<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<!-- edit-form -->
  			<div class="panel panel-info" >  
			<div class="panel-body" style="vertical-align:middle;margin:-11px;">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row" >
							<li>
								<label>编号</label>
									<input type="text" id="No" name="No" style="width:160px;"   value="${progCheckPlan.no }" readonly="readonly"/>                                             
							</li>
							<li>
								<label>巡检类型</label>
								<house:xtdm id="type" dictCode="CHECKPLANTYPE"  value="${progCheckPlan.type }" ></house:xtdm>
							</li>	
							<li>
								<label>巡检人</label>
								<input type="text" id="checkCZY" name="checkCZY" style="width:160px;"  value="${progCheckPlan.checkCZY }" />                                             
							</li>
							</div>
							<div class="validate-group row" >
							<li>
								<label>计划巡检日期</label>
								<input type="text" id="crtDate" name="crtDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${progCheckPlan.crtDate}' pattern='yyyy-MM-dd'/>"  disabled="true"/>
							</li>	
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${progCheckPlan	.remarks }</textarea>
  							</li>
  							</div>
						</ul>
  				</form>
  				</div>
  				</div>
			<div class="btn-panel" >
    			  <div class="btn-group-sm" style="vertical-align:middle;margin-top:-15px;"  >
						<button type="button" class="btn btn-system " onclick="doExcelNow('巡检任务安排明细')" title="导出当前excel数据" >
							<span>导出excel</span>
						</button>
				</div>
			</div>	
			<ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">楼盘信息</a></li>
	   	 </ul>
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
  		</div>
  	</div> 
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#checkCZY").openComponent_employee({showValue:'${progCheckPlan.checkCZY}',showLabel:'${progCheckPlan.checkCZYDescr}'});


	var lastCellRowId;
	var gridOption = {
		url:"${ctx}/admin/xjrwap/goDetailJqGrid",
		postData:{planNo:'${progCheckPlan.no}'},
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left", hidden: true},
			{name: "apppk", index: "apppk", width: 50, label: "申请编号", sortable: true, align: "left", hidden: true},
			{name: "address", index: "address", width: 170, label: "楼盘", sortable: true, align: "left",count:true},
			{name: "custcode", index: "custcode", width: 112, label: "客户编号", sortable: true, align: "left",},
			{name: "status", index: "status", width: 115, label: "状态", sortable: true, align: "left",hidden:true},
			{name: "statusdescr", index: "statusdescr", width: 115, label: "状态", sortable: true, align: "left"},
			{name: "appczydescr", index: "appczydescr", width: 105, label: "巡检人员", sortable: true, align: "left"},
			{name: "appdate", index: "appdate", width: 129, label: "巡检日期", sortable: true, align: "left", formatter: formatDate},
			{name: "appczy", index: "appczy", width: 129, label: "申请人", sortable: true, align: "left",hidden:true},
			{name: "checkno", index: "checkno", width: 101, label: "巡检单号", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 101, label: "最后修改时间", sortable: true, align: "left",hidden:true},
			{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后修改人员", sortable: true, align: "left",hidden:true},
			{name: "expired", index: "expired", width: 101, label: "是否过期", sortable: true, align: "left",hidden:true},
			{name: "planno", index: "planno", width: 101, label: "planno", sortable: true, align: "left",hidden:true},
		],
	};
		Global.JqGrid.initJqGrid("dataTable",gridOption);


});
</script>
  </body>
</html>

















