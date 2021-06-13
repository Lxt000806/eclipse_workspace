<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>巡检验收</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	

<script type="text/javascript"> 
function doCheckExcel(){
	var url = "${ctx}/admin/prjProgCheck/doCheckExcel";
	doExcelAll(url);
}
$(function(){


}); 

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$("#remarks").val('');
	$("#address").val('');
	$("#page_form input[type='text']").val('');
	$("#splStatusDescr").val('');
	$.fn.zTree.getZTreeObj("tree_prjItem").checkAllNodes(false);
} 


</script>
</head>
<body >
<div class="body-box-form" >
	<!-- <div class="content-form">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
	</div> -->
	<div class="container-fluid" >  
		<ul class="nav nav-tabs" >  
	        <li class="active"><a href="#tab_giftItemDetail" data-toggle="tab">赠送项目明细</a></li>       
	       	<c:if test="${customer.status=='1' || customer.status=='2'  || customer.status=='3'}">			
	        	<li class=""><a href="#tab_giftItemAll" data-toggle="tab">赠送项目汇总</a></li>
			</c:if>
	    </ul> 
	    <div class="tab-content" >  
	        <div id="tab_giftItemDetail"  class="tab-pane fade in active"> 
	         	<jsp:include page="tab_giftItemDetail.jsp"></jsp:include>
	        </div> 
	        <div id="tab_giftItemAll"  class="tab-pane fade "> 
	         	<jsp:include page="tab_giftItemAll.jsp"></jsp:include>
	        </div> 
	    </div>  
	</div>
</div>
</body>
</html>
