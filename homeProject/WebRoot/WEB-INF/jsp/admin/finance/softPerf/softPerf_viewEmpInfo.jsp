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
	<title>业绩归属员工信息--新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		$("#empCode").openComponent_employee({showValue:"${empForSoftPerf.empCode}",showLabel:"${emp.nameChi}"});	
		
	});
	function validateRefresh(){
			$("#dataForm").data("bootstrapValidator")
			                   .updateStatus("openComponent_employee_code", "NOT_VALIDATED",null)  
			                   .validateField("openComponent_employee_code")
	                    .updateStatus("crtDate", "NOT_VALIDATED",null)  
	                    .validateField("crtDate")  ;
	}
	
	function validateRefresh_choice(){
		 $("#dataForm").data("bootstrapValidator")
	                 .updateStatus("openComponent_employee_code", "NOT_VALIDATED",null)  
	                 .validateField("openComponent_employee_code");                    
	}
	function fillData(ret){
		validateRefresh_choice();
	}
	
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="pk" id="pk" value="${empForSoftPerf.pk }"/>
						<ul class="ul-form">
							<div class="validate-group row">
							<li>
								<label>员工编号</label>
								<input type="text" id="empCode" name="empCode" style="width:160px;" value="${empForSoftPerf.empCode }"/>
							</li>
							<li>
								<label>一级部门</label>
								<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${empForSoftPerf.department1 }"></house:department1>
							</li>
							<li>
								<label>二级部门</label>
								<house:department2 id="department2" dictCode="${empForSoftPerf.department1 }" value="${empForSoftPerf.department2 }" onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
							</li>
							<li>
								<label>业绩归属开始时间</label>
								<input type="text" id="beginDate" name="beginDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${empForSoftPerf.beginDate}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label>业绩归属结束时间</label>
								<input type="text" id="endDate" name="endDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${empForSoftPerf.endDate}' pattern='yyyy-MM-dd'/>"/>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>