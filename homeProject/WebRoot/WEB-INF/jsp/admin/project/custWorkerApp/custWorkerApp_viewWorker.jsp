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
								<label>客户编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }"/>
							</li>
							<li>
								<label>楼盘地址</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }"/>
							</li>
							<li>
								<label>客户类型</label>
								<house:xtdm  id="custType" dictCode="CUSTTYPE"   style="width:160px;" value="${customer.custType}"></house:xtdm>
							</li>
							<li>
								<label>面积</label>
								<input type="text" id="area" name="area" style="width:160px;" value="${customer.area }"/>
							</li>
							<li>
								<label>标准工期</label>
								<input type="text" id="prjnormday" name="prjnormday" style="width:160px;" value="${prjnormday }"/>
							</li>
					</ul>
				</form>
			</div>
			
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_qqgzl" data-toggle="tab">砌墙工作量</a></li>
			        <li class=""><a href="#tab_smgzl" data-toggle="tab">饰面工作量</a></li>
			        <li class=""><a href="#tab_mzgzl" data-toggle="tab">木作工作量</a></li>
			        <li class=""><a href="#tab_fsgzl" data-toggle="tab">防水工作量</a></li>
			        <li class=""><a href="#tab_gzde" data-toggle="tab">工种定额</a></li>
			    </ul> 
			    <div class="tab-content">  
			        <div id="tab_qqgzl" class="tab-pane fade in active"> 
			         	<jsp:include page="tab_qqgzl.jsp"></jsp:include>
			        </div> 
			        <div id="tab_smgzl" class="tab-pane fade "> 
			         	<jsp:include page="tab_smgzl.jsp"></jsp:include>
			        </div> 
			    
			        <div id="tab_mzgzl" class="tab-pane fade "> 
			         	<jsp:include page="tab_mzgzl.jsp"></jsp:include>
			        </div> 
			        
			        <div id="tab_fsgzl" class="tab-pane fade "> 
			         	<jsp:include page="tab_fsgzl.jsp"></jsp:include>
			        </div> 
			        <div id="tab_gzde" class="tab-pane fade>
			        	<jsp:include page="tab_gzde.jsp"></jsp:include>
			        </div>
			    </div>  
			</div>
		</div>
	</body>	
</html>
