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
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript">
var ExcTableId='dataTable_tile';
var tableName='瓷砖明细表'; 
$(function(){
	$("#arrPK").openComponent_autoArr();	
	$("#workerCode").openComponent_worker();	
	$("#custCode").openComponent_customer();
	
});
function changeToTile(){
	ExcTableId='dataTable_tile';
	tableName='瓷砖明细表';
}
function changeToFloor(){
	ExcTableId='dataTable_floor';
	tableName='地板明细表';
}
function changeToToilet(){
	ExcTableId='dataTable_toilet';
	tableName='卫浴明细表';
}
function query(){
	$("#dataTable_hasArrGd").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	$("#dataTable_noArrGd").jqGrid("setGridParam", {postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	$("#dataTable_noArrGr").jqGrid("setGridParam", {postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}	



</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
			      		<button type="button" class="btn btn-system " onclick="doExcelNow(tableName,ExcTableId,'page_form')" title="导出当前excel数据" >
							<span>导出excel</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			 <form type="hidden" role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
  			</form>
			<div class="panel-body">
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_tile" data-toggle="tab" onclick="changeToTile()" >瓷砖</a></li>
			        <li class=""><a href="#tab_floor" data-toggle="tab" onclick="changeToFloor()">地板</a></li>
			        <li class=""><a href="#tab_toilet" data-toggle="tab" onclick="changeToToilet()">卫浴</a></li>
			    </ul> 
			    <div class="tab-content">  
			        <div id="tab_tile" class="tab-pane fade in active"> 
			         	<jsp:include page="tab_tile.jsp"></jsp:include>
			        </div> 
			        <div id="tab_floor" class="tab-pane fade "> 
			         	<jsp:include page="tab_floor.jsp"></jsp:include>
			        </div> 
			        <div id="tab_toilet" class="tab-pane fade "> 
			         	<jsp:include page="tab_toilet.jsp"></jsp:include>
			        </div> 
			    </div>  
			</div>
		</div>
	</body>	
</html>
