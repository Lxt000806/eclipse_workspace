<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<title>施工任务执行分析查看</title>
		<%@ include file="/commons/jsp/common.jsp" %>
		
<script type="text/javascript">
	function view(){
	
		if($("ul li a[href='#tab_prjTask']").parent().hasClass("active")){
			console.log("待执行业务");
			var ret = selectDataTableRow("dataTable");
		}
		if($("ul li a[href='#tab_prjDelayNoTrrigeTask']").parent().hasClass("active")){
			console.log("延期未触发业务");
			var ret = selectDataTableRow("dataTable_prjDelayNoTrrigerTask");
		}
		if (ret) {	
	    	Global.Dialog.showDialog("View",{
				title:"查看客户工程进度",
				url:"${ctx}/admin/prjTaskExecAnaly/goPrjProgView",
				postData:{code:ret.code},
				width:1100,
				height:715,
			});
	  	} else {
	  		art.dialog({
				content: "请选择一条记录"
			});
	  	}
	} 
	function doView(){
		
		
	}
</script>
	</head>
	<body>
		<form action="" method="post" id="page_form" class="form-search" hidden>
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" name="dateFrom" value="${data.dateFromView}"/>
			<input type="hidden" name="dateTo" value="${data.dateToView}"/>
			<input type="hidden" name="rcvCZY" value="${data.rcvCZY}"/>
		</form>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
		    		<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
						<button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
					</div>
				</div> 
			</div> 
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
				<li class="active">
					<a href="#tab_prjTask" data-toggle="tab">待执行任务</a>
				</li>
				<li class="">
					<a href="#tab_prjDelayNoTrrigeTask" data-toggle="tab">延期未触发任务</a>
				</li>
			</ul> 
			<div class="tab-content">  
				<div id="tab_prjTask" class="tab-pane fade in active"> 
					<jsp:include page="tab_prjTask.jsp"></jsp:include>
				</div> 
				<div id="tab_prjDelayNoTrrigeTask" class="tab-pane fade "> 
					<jsp:include page="tab_prjDelayNoTrrigerTask.jsp"></jsp:include>
				</div> 
		    </div>  
		</div>
	</body>
</html>


