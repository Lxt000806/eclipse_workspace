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
	<title>工程部工人申请查看自动安排</title>
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
$(function(){
	$("#arrPK").openComponent_autoArr();	
	$("#workerCode").openComponent_worker();	
	$("#custCode").openComponent_customer();
	
});

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
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>批次号</label>
								<input type="text" id="arrPK" name="arrPK" style="width:160px;" value="${custWorkerApp.arrPK }"/>
							</li>
							<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${custWorkerApp.custCode }"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${custWorkerApp.address }"/>
							</li>
							<li>
								<label>工人编号</label>
								<input type="text" id="workerCode" name="workerCode" style="width:160px;" value="${custWorkerApp.workerCode }"/>
							</li>
							<li>
								<label>工种</label>
								 <house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value="${custWorkerApp.workType12 }"></house:DataSelect>
							</li>
							<li >
								<button type="button" class="btn  btn-sm btn-system " onclick="query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
					</ul>
				</form>
			</div>
			
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_noArrGD" data-toggle="tab">未安排工地</a></li>
			        <li class=""><a href="#tab_noArrGR" data-toggle="tab">未安排工人</a></li>
			        <li class=""><a href="#tab_hasArrGD" data-toggle="tab">已安排工地</a></li>
			    </ul> 
			    <div class="tab-content">  
			        <div id="tab_noArrGD" class="tab-pane fade in active"> 
			         	<jsp:include page="tab_noArrGD.jsp"></jsp:include>
			        </div> 
			        <div id="tab_noArrGR" class="tab-pane fade "> 
			         	<jsp:include page="tab_noArrGR.jsp"></jsp:include>
			        </div> 
			    
			        <div id="tab_hasArrGD" class="tab-pane fade "> 
			         	<jsp:include page="tab_hasArrGD.jsp"></jsp:include>
			        </div> 
			    
			    </div>  
			</div>
		</div>
	</body>	
</html>
