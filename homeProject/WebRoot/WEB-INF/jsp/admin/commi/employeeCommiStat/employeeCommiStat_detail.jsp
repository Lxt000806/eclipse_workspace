<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>主材管家工作量明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
$(function(){
	showTabs();
});

//根据传进来的名称显示对应tab
function showTabs(){
	var showTabs = "${showTabs}";
	if(showTabs != ""){
		if(showTabs.indexOf("主材提成") != -1){
			showTabs += "主材独立销售提成,";
		}
		if(showTabs.indexOf("集成提成") != -1){
			showTabs += "集成独立销售提成,";
		}
		if(showTabs.indexOf("软装提成") != -1){
			showTabs += "新软装独立销售提成,";
			showTabs += "旧软装独立销售提成,";
		}
		var obj = $("ul li a");
		var i = 0 ;
		$.each(obj,function(k,v){
			if (showTabs.indexOf($(v).text())  == -1 ){
				$(v).parent().addClass("hidden");
				$($(v).attr("href")).removeClass("in active");
			}else{
				if(i == 0){
					$(v).parent().addClass("active");
					$($(v).attr("href")).addClass("in active");
				}
				i++;
			}
		});
	}	
}

function goto_query(table,form){
	$("#"+table).jqGrid("setGridParam",{postData:$("#"+form).jsonForm(),page:1}).trigger("reloadGrid");
}

function doExcel(){
    if ($("ul li a[href='#tab_businessCommi']").parent().hasClass("active")){	
		doExcelNow("员工提成统计_业务员提成","dataTable_businessCommi","page_form");
	}else if  ($("ul li a[href='#tab_designCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_设计师提成","dataTable_designCommi","page_form");
	}else if  ($("ul li a[href='#tab_againCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_翻单员提成","dataTable_againCommi","page_form");
	}else if  ($("ul li a[href='#tab_sceneCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_现场设计师提成","dataTable_sceneCommi","page_form");
   	}else if  ($("ul li a[href='#tab_designFeeCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_设计费提成","dataTable_designFeeCommi","page_form"); 
	}else if  ($("ul li a[href='#tab_mainBusiCommi']").parent().hasClass("active") && $("ul li a[href='#tab_mainCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_主材提成","dataTable_mainBusiCommi","page_form");
	}else if  ($("ul li a[href='#tab_intBusiCommi']").parent().hasClass("active") && $("ul li a[href='#tab_intCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_集成提成","dataTable_intBusiCommi","page_form");	
	}else if  ($("ul li a[href='#tab_softBusiCommi']").parent().hasClass("active") && $("ul li a[href='#tab_softCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_软装提成","dataTable_softBusiCommi","page_form");	
	}else if  ($("ul li a[href='#tab_basePersonalCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_基础个性化提成","dataTable_basePersonalCommi","page_form");	
	}else if  ($("ul li a[href='#tab_supplCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_商家返利提成","dataTable_supplCommi","page_form");
	}else if  ($("ul li a[href='#tab_mainIndCommi']").parent().hasClass("active") && $("ul li a[href='#tab_intCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_主材独立销售提成","dataTable_mainIndCommi","page_form");
	}else if  ($("ul li a[href='#tab_intIndCommi']").parent().hasClass("active") && $("ul li a[href='#tab_intCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_集成独立销售提成","dataTable_intIndCommi","page_form");	
	}else if  ($("ul li a[href='#tab_softIndCommi']").parent().hasClass("active") && $("ul li a[href='#tab_softCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_新软装独立销售提成","dataTable_softIndCommi","page_form");	
	}else if  ($("ul li a[href='#tab_softOldCommi']").parent().hasClass("active") && $("ul li a[href='#tab_softCommi']").parent().hasClass("active")){
		doExcelNow("员工提成统计_旧软装独立销售提成","dataTable_softOldCommi","page_form");	
	}		  
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="query-form" hidden>
			<form action="" method="post" id="page_form" >
				 <input type="hidden" name="jsonString" value="" />
				 <input type="hidden" id="dateFrom" name="dateFrom" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.dateFrom}' pattern='yyyy-MM-dd'/>" />
				 <input type="hidden" id="dateTo" name="dateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.dateTo}' pattern='yyyy-MM-dd'/>" />
			</form>
		</div>
		<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	     					<button type="button" class="btn btn-system "   onclick="doExcel()">导出excel</button>
							<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
		  </div>
		<div  class="container-fluid"> 
		     <ul class="nav nav-tabs" >
		     	<li id="businessCommi" class="active">
		     		<a href="#tab_businessCommi" data-toggle="tab" >业务提成</a>
			    </li>
		        <li id="designCommi" class=" ">
		        	<a href="#tab_designCommi" data-toggle="tab" >设计提成</a>
			    </li>
			    <li id="againCommi" class=" ">
		        	<a href="#tab_againCommi" data-toggle="tab" >翻单提成</a>
			    </li>
			    <li id="sceneCommi" class=" ">
		        	<a href="#tab_sceneCommi" data-toggle="tab" >现场提成</a>
			    </li>
			     <li id="mainBusiCommi" class=" ">
		        	<a href="#tab_designFeeCommi" data-toggle="tab" >设计费提成</a>
			    </li>
			    <li id="mainBusiCommi" class=" ">
		        	<a href="#tab_mainCommi" data-toggle="tab" >主材提成</a>
			    </li>
			    <li id="intBusiCommi" class=" ">
		        	<a href="#tab_intCommi" data-toggle="tab" >集成提成</a>
			    </li>
			    <li id="softCommi" class=" ">
		        	<a href="#tab_softCommi" data-toggle="tab" >软装提成</a>
			    </li>
			     <li id="basePersonalCommi" class=" ">
		        	<a href="#tab_basePersonalCommi" data-toggle="tab" >个性化提成</a>
			    </li>
			    <li id="supplCommi" class=" ">
		        	<a href="#tab_supplCommi" data-toggle="tab" >返利提成</a>
			    </li>
			   
		    </ul>  
		    <div class="tab-content"> 
		    	<div id="tab_businessCommi" class="tab-pane fade in active" >
					<jsp:include page="tab_businessCommi.jsp"></jsp:include>
				</div> 
				<div id="tab_designCommi" class="tab-pane fade " >
					<jsp:include page="tab_designCommi.jsp"></jsp:include>
				</div> 
				<div id="tab_againCommi" class="tab-pane fade " >
					<jsp:include page="tab_againCommi.jsp"></jsp:include>
				</div>
				<div id="tab_sceneCommi" class="tab-pane fade " >
					<jsp:include page="tab_sceneCommi.jsp"></jsp:include>
				</div>
				<div id="tab_designFeeCommi" class="tab-pane fade " >
					<jsp:include page="tab_designFeeCommi.jsp"></jsp:include>
				</div>
				<div id="tab_mainCommi" class="tab-pane fade " >
					<jsp:include page="tab_mainCommi.jsp"></jsp:include>
				</div>
				<div id="tab_intCommi" class="tab-pane fade " >
					<jsp:include page="tab_intCommi.jsp"></jsp:include>
				</div>
				<div id="tab_softCommi" class="tab-pane fade " >
					<jsp:include page="tab_softCommi.jsp"></jsp:include>
				</div>
				<div id="tab_basePersonalCommi" class="tab-pane fade " >
					<jsp:include page="tab_basePersonalCommi.jsp"></jsp:include>
				</div>
				<div id="tab_supplCommi" class="tab-pane fade " >
					<jsp:include page="tab_supplCommi.jsp"></jsp:include>
				</div>
		
			</div>	
		</div>
	</div>
</body>
</html>

