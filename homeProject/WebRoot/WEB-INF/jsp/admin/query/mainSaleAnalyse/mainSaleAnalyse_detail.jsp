<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>主材独立销售查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	var excelTableId = "dataTable_chgDetail";
	var excelName = "主材独立销售分析-材料增减";
	
	function setExcel(tableId,name){
		excelTableId = tableId;
		excelName = name;
	}
	
	function doExcel(){
		doExcelNow(excelName,excelTableId);
	}
	</script>
</head>
	<body>
  	 	<div class="body-box-form">
  			<div class="content-form">
  				<div class="panel panel-system">
			   	 	<div class="panel-body">
			    		<div class="btn-group-xs">
							<button type="button" class="btn btn-system" onclick="doExcel()">
								<span>导出excel</span>
							</button>
							<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
								<span>关闭</span>
							</button>
						</div>
					</div>	
				</div>
				<div class="query-form" hidden="true">
					<form role="form" class="form-search" id="page_form" action=""
							method="post" target="targetFrame">
						<input type="hidden" id="expired" name="expired" value="${customer.expired }"/> 
						<input type="hidden" id="role" name="role" value="${customer.role}"/>
						<input type="hidden" id="empCode" name="empCode" value="${customer.empCode}"/>
						<input type="hidden" id="custType" name="custType" value="${customer.custType}"/>
						<input type="hidden" id="supplierCode" name="supplierCode" value="${customer.supplierCode}"/>
						<input type="hidden" id="itemType12" name="itemType12" value="${customer.itemType12}"/>
						<input type="hidden" id="department1" name="department1" value="${customer.department1}"/>
						<input type="hidden" id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>" />
						<input type="hidden" id="dateTo" name="dateTo" value="<fmt:formatDate value='${customer.dateTo }'  pattern='yyyy-MM-dd'/>" />
						<input type="hidden" name="jsonString" value=""/>
					</form>
				</div>
				<div class="container-fluid" >  
					<ul class="nav nav-tabs" > 
				        <li class="active"><a href="#tab_chgDetail" onclick="setExcel('dataTable_chgDetail','主材独立销售分析-材料增减')" data-toggle="tab">材料增减</a></li>
				        <li class=""><a href="#tab_itemPlan" onclick="setExcel('dataTable_itemPlan','主材独立销售分析-材料预算')" data-toggle="tab">材料预算</a></li>
				    </ul> 
				    <div class="tab-content">  
				        <div id="tab_chgDetail"  class="tab-pane fade in active">
				         	<jsp:include page="tab_chgDetail.jsp"></jsp:include>
				        </div> 
				        <div id="tab_itemPlan" class="tab-pane fade"> 
				         	<jsp:include page="tab_itemPlan.jsp"></jsp:include>
				        </div> 
				    </div>
				</div>
  			</div> 
	</body>
</html>

















