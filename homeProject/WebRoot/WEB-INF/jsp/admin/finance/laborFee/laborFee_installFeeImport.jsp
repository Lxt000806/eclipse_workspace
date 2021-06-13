<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>导入集成安装费</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
var selectRows = [];
var nowPage="pre";
function save(){
	var cupIds=$("#checkFeeDataTable").jqGrid("getGridParam", "selarrrow");
	var intIds=$("#preFeeDataTable").jqGrid("getGridParam", "selarrrow");
	var nos=$("#nos").val();
	var custCodes=$("#custCodes").val();
	if(intIds.length == 0 && cupIds.length == 0){
		art.dialog({
			content:"请选择数据后保存"
		});				
		return ;
	}
	$.each(cupIds,function(i,id){
		var rowData = $("#checkFeeDataTable").jqGrid("getRowData", id);
		selectRows.push(rowData);
		if(custCodes != ""){
			custCodes += ",";
		}
		custCodes += rowData.appsendno;
	});
	$.each(intIds,function(i,id){
		var rowData = $("#preFeeDataTable").jqGrid("getRowData", id);
		selectRows.push(rowData);
		if(nos != ""){
			nos += ",";
		}
		nos += rowData.appsendno;
	}); 
	$("#nos").val(nos);
	$("#custCodes").val(custCodes);
  		art.dialog({content: "添加成功！",width: 200,time:800});
  		doQuery();
}
function closeAndReturn(){
	Global.Dialog.returnData = selectRows;
   	closeWin();
}
function doQuery(){
	$("#checkFeeDataTable").jqGrid("setGridParam",{
		datatype:"json",
   		postData:$("#dataForm").jsonForm(),
   		page:1,
   		sortname:''
  	}).trigger("reloadGrid");
  	
  	$("#preFeeDataTable").jqGrid("setGridParam",{
		datatype:"json",
   		postData:$("#dataForm").jsonForm(),
   		page:1,
   		sortname:''
  	}).trigger("reloadGrid");
}
function changePage(e){
	nowPage=e.id;
}
function clickQuery(){
	var dateFrom=$("#dateFrom").val();
	var dateTo=$("#dateTo").val();
	if(dateFrom==""){
		art.dialog({
			content: "开始时间不能为空！"
		});
		return;
	}
	if(dateTo==""){
		art.dialog({
			content: "结束时间不能为空！"
		});
		return;
	}
	//if(nowPage == "pre"){
		$("#preFeeDataTable").jqGrid("setGridParam",{
			datatype:"json",
	   		postData:$("#dataForm").jsonForm(),
	   		page:1,
	   		sortname:''
	  	}).trigger("reloadGrid");
	//}
	//if(nowPage == "check"){
		$("#checkFeeDataTable").jqGrid("setGridParam",{
			datatype:"json",
	   		postData:$("#dataForm").jsonForm(),
	   		page:1,
	   		sortname:''
	  	}).trigger("reloadGrid");
	//}
}
function clearData(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#dateFrom").val("");
	$("#dateTo").val("");
}

$(function(){
	replaceCloseEvt("inStallFeeImport", closeAndReturn);
});
</script>
</head>
<body >
<div class="body-box-form" >
	<div class="content-form">
		<div class="panel panel-system" >
    		<div class="panel-body" >
     			<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="saveBut" onclick="save()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeAndReturn()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="body-box-list">
		<div class="query-form"> 
			<form action="" method="post" id="dataForm" class="form-search" >
				<ul class="ul-form">
					<input type="text" hidden="true" id="nos" name="nos" style="width:160px;" value="${laborFee.nos }" />
					<input type="text" hidden="true" id="custCodes" name="custCodes" style="width:160px;" value="${laborFee.custCodes }" />
					<li>
						<label>日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							 value="<fmt:formatDate value='${laborFee.dateFrom}' pattern='yyyy-MM-dd'/>"  />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${laborFee.dateTo}' pattern='yyyy-MM-dd'/>"  />
					</li>
					<li class="search-group" >
						<button type="button" class="btn btn-sm btn-system" onclick="clickQuery()">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearData()">清空</button>
					</li>
				</ul>
			</form>
		</div>		
		<ul class="nav nav-tabs" >  
	        <li class="active" onClick="changePage(this)" id="pre">
	        	<a href="#tab_preFee" data-toggle="tab">预发</a>
	        </li>
	        <li class="" onClick="changePage(this)" id="check">
	        	<a href="#tab_checkFee" data-toggle="tab">结算</a>
	        </li>
	    </ul> 
	    <div class="tab-content">  
	        <div id="tab_preFee"  class="tab-pane fade in active"> 
	         	<jsp:include page="tab_preFee.jsp"></jsp:include>
	        </div> 
	        <div id="tab_checkFee"  class="tab-pane fade " > 
	         	<jsp:include page="tab_checkFee.jsp"></jsp:include>
	        </div> 
	    </div>  
	</div>
</div>
</body>
</html>
