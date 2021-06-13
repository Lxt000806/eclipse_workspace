<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>项目经理结算分析-查看</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>

	</head>
	<body>
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system" id="excel">
						<span>导出Excel</span>
					</button>
					<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<form action="" method="post" id="page_form" class="form-search">
			<house:token></house:token>
			<input type="hidden" name="jsonString" value=""/>
		</form>
		<input type="hidden" id="custCheckDateFrom" name="custCheckDateFrom" value="<fmt:formatDate value='${customer.custCheckDateFrom }'  pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="custCheckDateTo" name="custCheckDateTo" value="<fmt:formatDate value='${customer.custCheckDateTo }'  pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="custType" name="custType" value="${customer.custType }" />
		<input type="hidden" id="projectMan" name="projectMan" value="${customer.projectMan }" />
		<input type="hidden" id="containOilPaint" name="containOilPaint" value="${customer.containOilPaint }" />
		<input type="hidden" id="department2" name="department2" value="${customer.department2 }" />
		
		<div  class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#tabView_detail" data-toggle="tab" >楼盘明细</a></li>  
			</ul>  
		    <div class="tab-content" >  
				<div id="tabView_detail"  class="tab-pane fade active in" > 
		         	<jsp:include page="prjManCheckAnaly_tab_detail.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
			</div>
		</div>	
<script type="text/javascript" defer>
$("#excel").on("click",function(){
	var preName = $(".container-fluid .nav .active a").text();
	var tableId = $(".container-fluid .tab-content .active .ui-jqgrid .ui-jqgrid-view .ui-jqgrid-bdiv table").attr("id");
	doExcelNow("项目经理结算分析-"+preName,tableId);
});
</script>
	</body>
</html>


