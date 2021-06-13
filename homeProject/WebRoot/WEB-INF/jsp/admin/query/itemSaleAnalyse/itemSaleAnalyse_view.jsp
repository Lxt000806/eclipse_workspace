<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>材料销售分析--按单品--明细</title>
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
	$("#confirmCzy").openComponent_employee();	
	$("#lastUpdateBy").openComponent_employee();	
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
	<div class="content-form">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form" hidden="true">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" name="jsonString" value="" />
				<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" 
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${item.dateFrom}' pattern='yyyy-MM-dd'/>"/>
				<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${item.dateTo}' pattern='yyyy-MM-dd'/>" />
			    <input type="text" id="itemType1" name="itemType1"  value="${item.itemType1}"></input>
			    <input type="text" id="itemType2" name="itemType2"  value="${item.itemType2}"></input>
				<input type="text" id="supplCode" name="supplCode" value="${item.supplCode}"/>
				<input type="text" id="code" name="code" value="${item.code}"/>
				<input type="text" id="isOutSet" name="isOutSet" value="${item.isOutSet}"/>
			</form>
		</div>
	</div>
	<div class="container-fluid" >  
		<ul class="nav nav-tabs" >  
			<li class="active"><a href="#tab_planDetail" data-toggle="tab">预算明细</a></li>
			<li class=""><a href="#tab_chgDetail" data-toggle="tab">增减明细</a></li>
		</ul> 
		<div class="tab-content" style="border-top:1px solid rgb(221,221,221)">  
			<div id="tab_planDetail"  class="tab-pane fade in active"> 
	         	<jsp:include page="tab_planDetail.jsp"></jsp:include>
	        </div> 
	        <div id="tab_chgDetail"  class="tab-pane fade "> 
	         	<jsp:include page="tab_chgDetail.jsp"></jsp:include>
	        </div> 
	    </div>  
	</div>
</div>
</body>
</html>
