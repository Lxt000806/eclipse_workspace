<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>工程部业绩分析-查看</title>
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
						<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
					<button type="button" class="btn btn-system" id="excel">
						<span>导出Excel</span>
					</button>
				</div>
			</div>
		</div>
		<form action="" method="post" id="page_form" class="form-search">
			<house:token></house:token>
			<input type="hidden" name="jsonString" value=""/>
		</form>
		<input type="hidden" id="empCode" name="empCode" value="${dept2.empCode }" />
		<input type="hidden" id="prjDeptLeaderCode" name="prjDeptLeaderCode" value="${dept2.prjDeptLeaderCode }" />
		<input type="hidden" id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${dept2.dateFrom }'  pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="dateTo" name="dateTo" value="<fmt:formatDate value='${dept2.dateTo }'  pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="checkDateFrom" name="checkDateFrom" value="<fmt:formatDate value='${dept2.checkDateFrom }'  pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="checkDateTo" name="checkDateTo" value="<fmt:formatDate value='${dept2.checkDateTo }'  pattern='yyyy-MM-dd'/>" />
		<input type="hidden" id="custType" name="custType" value="${dept2.custType }" />
		<input type="hidden" id="department2" name="department2" value="${dept2.department2 }" />
		
		<div  class="container-fluid" >  
			<ul class="nav nav-tabs" >
			  <!--   <li><a href="#tabView_confirmbuilds" data-toggle="tab">在建工地</a></li>   -->
			    <li class="active"><a href="#tabView_checkbuilds" data-toggle="tab" >结算工地</a></li>  
			    <li><a href="#tabView_reorderbuilds" data-toggle="tab" >签单工地</a></li>  
			    <li><a href="#tabView_changedPerformance" data-toggle="tab" >增减业绩</a></li>  
			    <!-- <li ><a href="#tabView_confirmbuilds_in" data-toggle="tab">借入开工工地</a></li>  
			    <li><a href="#tabView_checkbuilds_in" data-toggle="tab" >借入结算工地</a></li>  
			    <li><a href="#tabView_confirmbuilds_out" data-toggle="tab">借出开工工地</a></li>  
			    <li><a href="#tabView_checkbuilds_out" data-toggle="tab" >借出结算工地</a></li>   -->
			</ul>  
		    <div class="tab-content" >  
				<%-- <div id="tabView_confirmbuilds"  class="tab-pane fade active in " > 
		         	<jsp:include page="tabView_confirmbuilds.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div>  --%>
				<div id="tabView_checkbuilds"  class="tab-pane fade active in" > 
		         	<jsp:include page="tabView_checkbuilds.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
				<div id="tabView_reorderbuilds"  class="tab-pane fade" > 
	         		<jsp:include page="tabView_reorderbuilds.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
				<div id="tabView_changedPerformance"  class="tab-pane fade" > 
	         		<jsp:include page="tabView_changedPerformance.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
				<%-- <div id="tabView_confirmbuilds_in"  class="tab-pane fade " > 
		         	<jsp:include page="tabView_confirmbuilds_in.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
				<div id="tabView_checkbuilds_in"  class="tab-pane fade" > 
		         	<jsp:include page="tabView_checkbuilds_in.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
				<div id="tabView_confirmbuilds_out"  class="tab-pane fade " > 
		         	<jsp:include page="tabView_confirmbuilds_out.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
				<div id="tabView_checkbuilds_out"  class="tab-pane fade" > 
		         	<jsp:include page="tabView_checkbuilds_out.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
 --%>
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


