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
	<title>工程部工人申请查看工人</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_autoArr.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript">
var ExcTableId='dataTable_emp';
var tableName='设计师业绩明细表'; 
var businessManTimes=0;
var buyerTimes=0;
$(function(){

});
function changeToEmp(){
	ExcTableId='dataTable_emp';
	tableName='设计师业绩明细表';
	$("#designMan1").val("${softPerf.designMan}");
	$("#businessMan").val("");
	$("#buyer1").val("");
	$("#tabName").val("DESIGNER");
}
function changeToBuyer(){
	ExcTableId='dataTable_buyer';
	tableName='买手业绩明细表';
	$("#designMan1").val("");
	$("#businessMan").val("");
	$("#buyer1").val("${softPerf.designMan}");
	$("#tabName").val("BUYER");
	if(buyerTimes==0){
		$("#dataTable_buyer").jqGrid("setGridParam", {url:"${ctx}/admin/softPerf/goReportDetailJqGrid",postData:$("#dataForm").jsonForm(),page:1}).trigger("reloadGrid");
		buyerTimes=1;
	}
}
function changeToBusinessMan(){
	ExcTableId='dataTable_businessMan';
	tableName='业务员业绩明细表';
	$("#designMan1").val("");
	$("#businessMan").val("${softPerf.designMan}");
	$("#buyer1").val("");
	$("#tabName").val("SALESMAN");
	if(businessManTimes==0){
		$("#dataTable_businessMan").jqGrid("setGridParam", {url:"${ctx}/admin/softPerf/goReportDetailJqGrid",postData:$("#dataForm").jsonForm(),page:1}).trigger("reloadGrid");
		businessManTimes=1
	}
}
function query(){
	$("#dataTable_emp").jqGrid("setGridParam",{postData:$("#dataForm").jsonForm(),page:1}).trigger("reloadGrid");
	$("#dataTable_buyer").jqGrid("setGridParam", {postData:$("#dataForm").jsonForm(),page:1}).trigger("reloadGrid");
	$("#dataTable_businessMan").jqGrid("setGridParam", {postData:$("#dataForm").jsonForm(),page:1}).trigger("reloadGrid");
}
function doExcel(){
	var url = "${ctx}/admin/softPerf/doExcelReportDetail";
	doExcelAll(url,ExcTableId,"dataForm");
}	

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
			      		<button type="button" class="btn btn-system " onclick="doExcel()" title="导出当前excel数据" >
							<span>导出excel</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<form type="hidden" role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="no" name="no" value="${softPerf.no}"/>
					<input type="hidden" id="countType" name="countType" value="${softPerf.countType}" />
					<input type="hidden" id="department2" name="department2" value="${softPerf.department2}" />
					<input type="hidden" id="designMan1" name="designMan1" value="${softPerf.designMan}" />
					<input type="hidden" id="businessMan" name="businessMan"/>
					<input type="hidden" id="buyer1" name="buyer1"/>
					<input type="hidden" id="tabName" name="tabName"/>
  			</form>
			<div class="panel-body">
				<div class="container-fluid" >  
					<ul class="nav nav-tabs" > 
				        <li class="active"><a href="#tab_softPerfEmp" data-toggle="tab" onclick="changeToEmp()" >设计师明细</a></li>
				        <li class=""><a href="#tab_softPerfBuyer" data-toggle="tab" onclick="changeToBuyer()">买手明细</a></li>
				        <li class=""><a href="#tab_softPerfBusinessMan" data-toggle="tab" onclick="changeToBusinessMan()">业务员明细</a></li>
				    </ul> 
				    <div class="tab-content">  
				        <div id="tab_softPerfEmp" class="tab-pane fade in active"> 
				         	<jsp:include page="tab_softPerfEmp.jsp"></jsp:include>
				        </div> 
				        <div id="tab_softPerfBuyer" class="tab-pane fade "> 
				         	<jsp:include page="tab_softPerfBuyer.jsp"></jsp:include>
				        </div> 
				        <div id="tab_softPerfBusinessMan" class="tab-pane fade "> 
				         	<jsp:include page="tab_softPerfBusinessMan.jsp"></jsp:include>
				        </div> 
				    </div>  
				</div>
			</div>
		</div>	
	</body>	
</html>
