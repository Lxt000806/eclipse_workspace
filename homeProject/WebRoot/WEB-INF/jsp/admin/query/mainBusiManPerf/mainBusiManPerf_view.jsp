<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>主材管家业绩报表-查看</title>
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
		<input type="hidden" id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="dateTo" name="dateTo" value="<fmt:formatDate value='${customer.dateTo }'  pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="custType" name="custType" value="${customer.custType }" />
		<input type="hidden" id="empCode" name="empCode" value="${customer.empCode }" />
		<div  class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#tabView_jsmx" data-toggle="tab" >结算明细</a></li>  
			    <li><a href="#tabView_dlxsmx" data-toggle="tab" >独立销售明细</a></li>
			    <%--<li><a href="#tabView_ckclxsmx" data-toggle="tab" >集采材料结算明细</a></li> --%>   
			</ul>  
		    <div class="tab-content" >  
				<div id="tabView_jsmx"  class="tab-pane fade active in" > 
		         	<jsp:include page="tab_jsmx.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
				<div id="tabView_dlxsmx"  class="tab-pane fade" > 
	         		<jsp:include page="tab_dlxsmx.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
				<div id="tabView_ckclxsmx"  class="tab-pane fade" > 
	         		<jsp:include page="tab_ckclxsmx.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
			</div>
		</div>	
<script type="text/javascript" defer>
$("#excel").on("click",function(){
	var preName = $(".container-fluid .nav .active a").text();
	var tableId = $(".container-fluid .tab-content .active .ui-jqgrid .ui-jqgrid-view .ui-jqgrid-bdiv table").attr("id");
	doExcelNow(preName,tableId);
});
</script>
	</body>
</html>


