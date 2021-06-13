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
	
	
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	

<script type="text/javascript"> 
var selectRows = [];
var nowPage="cup";
$(function(){
	$("#workerCode").openComponent_worker();	
	$("#viewQty").attr("disabled",true);
	replaceCloseEvt("intFeeImport", closeAndReturn);
}); 
function save(){
	var cupIds=$("#cupFeeDataTable").jqGrid("getGridParam", "selarrrow");
	var intIds=$("#intFeeDataTable").jqGrid("getGridParam", "selarrrow");
	var prjIds=$("#goodPrjDataTable").jqGrid("getGridParam", "selarrrow");
	var nos=$("#nos").val();
	var custCodes=$("#custCodes").val();
	var jcazbtCustCodes=$("#jcazbtCustCodes").val();
	var jcazjfCustCodes=$("#jcazjfCustCodes").val();
	var cgazbtCustCodes=$("#cgazbtCustCodes").val();
	var cgazjfCustCodes=$("#cgazjfCustCodes").val();
	if(intIds.length == 0 && cupIds.length == 0 && prjIds.length == 0){
		art.dialog({
			content:"请选择数据后保存"
		});				
		return ;
	}
	$.each(cupIds,function(i,id){
		var rowData = $("#cupFeeDataTable").jqGrid("getRowData", id);
		rowData.feetype = rowData.feetype.toUpperCase();
		selectRows.push(rowData);
		if(custCodes != ""){
			custCodes += ",";
		}
		custCodes += rowData.custcode;
	});
	$.each(intIds,function(i,id){
		var rowData = $("#intFeeDataTable").jqGrid("getRowData", id);
		rowData.feetype = rowData.feetype.toUpperCase();
		if("${intInsCalTyp}"=="1"){
			rowData.amount = parseFloat(rowData.amount)+parseFloat(rowData.bathamount)+"";
		}
		selectRows.push(rowData);
		if(nos != ""){
			nos += ",";
		}
		nos += rowData.custcode;
	}); 
	$.each(prjIds,function(i,id){
		var rowData = $("#goodPrjDataTable").jqGrid("getRowData", id);
		rowData.feetype = rowData.feetype.toUpperCase();
		selectRows.push(rowData);
		if(rowData.feetype == "JCAZBT"){
			if(jcazbtCustCodes != ""){
				jcazbtCustCodes += ",";
			}
			jcazbtCustCodes += rowData.custcode;
		}
		if(rowData.feetype == "CGAZBT"){
			if(cgazbtCustCodes != ""){
				cgazbtCustCodes += ",";
			}
			cgazbtCustCodes += rowData.custcode;
		}
		if(rowData.feetype == "JCAZJF"){
			if(jcazjfCustCodes != ""){
				jcazjfCustCodes += ",";
			}
			jcazjfCustCodes += rowData.custcode;
		}
		if(rowData.feetype == "CGAZJF"){
			if(cgazjfCustCodes != ""){
				cgazjfCustCodes += ",";
			}
			cgazjfCustCodes += rowData.custcode;
		}
	}); 
	$("#nos").val(nos);
	$("#custCodes").val(custCodes);
	$("#jcazbtCustCodes").val(jcazbtCustCodes);
	$("#cgazbtCustCodes").val(cgazbtCustCodes);
	$("#jcazjfCustCodes").val(jcazjfCustCodes);
	$("#cgazjfCustCodes").val(cgazjfCustCodes);
	art.dialog({content: "添加成功！",width: 200,time:800});
	doQuery();
}
function closeAndReturn(){
	Global.Dialog.returnData = selectRows;
   	closeWin();
}
function doQuery(){
	$("#cupFeeDataTable").jqGrid("setGridParam",{
		datatype:"json",
   		postData:$("#dataForm").jsonForm(),
   		page:1,
   		sortname:''
  	}).trigger("reloadGrid");
  	
  	$("#intFeeDataTable").jqGrid("setGridParam",{
		datatype:"json",
   		postData:$("#dataForm").jsonForm(),
   		page:1,
   		sortname:''
  	}).trigger("reloadGrid");
  	
  	$("#goodPrjDataTable").jqGrid("setGridParam",{
		datatype:"json",
   		postData:$("#dataForm").jsonForm(),
   		page:1,
   		sortname:''
  	}).trigger("reloadGrid");
}
function changePage(e){
	nowPage=e.id;
	if(nowPage == "int"){
		$("#viewQty").removeAttr("disabled");
	}else{
		$("#viewQty").attr("disabled",true);
	}
}
function clickQuery(){
	if(nowPage == "int"){
		$("#intFeeDataTable").jqGrid("setGridParam",{
			datatype:"json",
	   		postData:$("#dataForm").jsonForm(),
	   		page:1,
	   		sortname:''
	  	}).trigger("reloadGrid");
	}
	if(nowPage == "cup"){
		$("#cupFeeDataTable").jqGrid("setGridParam",{
			datatype:"json",
	   		postData:$("#dataForm").jsonForm(),
	   		page:1,
	   		sortname:''
	  	}).trigger("reloadGrid");
	}
	if(nowPage == "goodPrj"){
		$("#goodPrjDataTable").jqGrid("setGridParam",{
			datatype:"json",
	   		postData:$("#dataForm").jsonForm(),
	   		page:1,
	   		sortname:''
	  	}).trigger("reloadGrid");
	}
}
function clearData(){
	$("#page_form input[type='text']").val('');
	$("#openComponent_worker_workerCode").val('');
	$("#workerCode").val("");
	$("#page_form select").val('');
	$("#dateFrom").val("");
	$("#dateTo").val("");
}
function viewQty(){
	console.log($("#dataForm").jsonForm());
	Global.Dialog.showDialog("viewIntQty", {
		title : "查看衣柜平方数",
		url : "${ctx}/admin/laborFee/goViewIntQty",
		postData : $("#dataForm").jsonForm(),
		height : 650,
		width : 900
	});
}
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
					<button style="align:left" type="button" class="btn btn-system "
						 onclick="viewQty()" id="viewQty">
						<span>查看衣柜平方数 </span>
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
					<input type="text" hidden="true" id="no" name="no" style="width:160px;" value="${laborFee.no }" />
					<input type="text" hidden="true" id="nos" name="nos" style="width:160px;" value="${laborFee.nos }" />
					<input type="text" hidden="true" id="custCodes" name="custCodes" style="width:160px;" value="${laborFee.custCodes }" />
					<input type="text" hidden="true" id="jcazbtCustCodes" name="jcazbtCustCodes" style="width:160px;" value="${laborFee.jcazbtCustCodes }" />
					<input type="text" hidden="true" id="cgazbtCustCodes" name="cgazbtCustCodes" style="width:160px;" value="${laborFee.cgazbtCustCodes }" />
					<input type="text" hidden="true" id="jcazjfCustCodes" name="jcazjfCustCodes" style="width:160px;" value="${laborFee.jcazjfCustCodes }" />
					<input type="text" hidden="true" id="cgazjfCustCodes" name="cgazjfCustCodes" style="width:160px;" value="${laborFee.cgazjfCustCodes }" />
					<li>
						<label>工人</label> 
						<input type="text" name="workerCode" id="workerCode" style="width:160px;"/>
					</li>
					<li>
						<label>时间从</label>
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
	        <li class="active" onClick="changePage(this)" id="cup">
	        	<a href="#tab_CupFee" data-toggle="tab">橱柜安装费</a>
	        </li>
	        <li class="" onClick="changePage(this)" id="int">
	        	<a href="#tab_intFee" data-toggle="tab">集成安装费</a>
	        </li>
	        <li class="" onClick="changePage(this)" id="goodPrj">
	        	<a href="#tab_goodPrj" data-toggle="tab">优秀工地</a>
	        </li>
	    </ul> 
	    <div class="tab-content">  
	        <div id="tab_CupFee"  class="tab-pane fade in active"> 
	         	<jsp:include page="tab_CupFee.jsp"></jsp:include>
	        </div> 
	        <div id="tab_intFee"  class="tab-pane fade " > 
	         	<jsp:include page="tab_intFee.jsp"></jsp:include>
	        </div> 
	        <div id="tab_goodPrj"  class="tab-pane fade " > 
	         	<jsp:include page="tab_goodPrj.jsp"></jsp:include>
	        </div> 
	    </div>  
	</div>
</div>
</body>
</html>
