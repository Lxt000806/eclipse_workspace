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
	<title>资源客户操作日志</title>
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
	
});

function changeOrderBy(){
 	var workType12 = $.trim($("#workType12").val());
	if(workType12=="11"){
		$("#orderBy").val("2");
	}else{
		$("#orderBy").val("1");
	}
}
 function printApp(){
 			var workType12 = $.trim($("#workType12").val());
 			var department1 = $.trim($("#department1").val());
 			var dateFrom = $.trim($("#dateFrom").val());
 			var dateTo = $.trim($("#dateTo").val());
 			var isPrjSpc = $.trim($("#isPrjSpc").val());
 			var orderBy = $.trim($("#orderBy").val());

		if(workType12==''){
			art.dialog({
				content:'请选择工种',
			});
			return false;
		}

	   	var reportName = "custWorkerApp.jasper";
	   	Global.Print.showPrint(reportName, {
			WorkType12:workType12,
			Department1:department1,
			DateFrom:dateFrom,
			DateTo:dateTo,
			IsPrjSpc:isPrjSpc,
			orderBy:orderBy,
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
	}
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
   			 	<div class="panel-body" >
     				 <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
						<button type="button" class="btn btn-system "  onclick="printApp();">
							<span>打印</span>
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
								<label><span class="required">*</span>工种</label>
								<house:DataSelect id="workType12" className="WorkType12" onchange="changeOrderBy()" keyColumn="code" valueColumn="descr" value="${custWorkerApp.workType12 }" ></house:DataSelect>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm  id="status" dictCode="WORKERAPPSTS"   style="width:160px;" value="1" disabled="true"></house:xtdm>
							</li>
							<li>
								<label>工程事业部</label>
								<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where 1=1 and DepType='3' and expired='F'   " 
								sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department1 }"  ></house:DictMulitSelect>
							</li>
							<li>
								<label>专盘</label>
								<house:xtdm  id="isPrjSpc" dictCode="YESNO"   style="width:160px;" value="${custWorkerApp.isPrjSpc}"></house:xtdm>
							</li>
							<li>
								<label>申报时间从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="${custWorkerApp.appDate }" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="${custWorkerApp.appDate }" />
							</li>
							<li>
								<label>排序列</label>
 								<select id="orderBy" name="orderBy" style="width: 160px;" >
 									<option value="">请选择...</option>
 									<option value="1">1 申报时间</option>
 									<option value="2">2 区域</option>
 								</select>
							</li>
						</ul>	
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>
