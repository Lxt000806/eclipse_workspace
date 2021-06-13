<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
</head>
<body onload="code()">
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="body-box-list">
			<div class="query-form">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="department2" id="department2" value="${worker.department2 }"/>
					<input type="hidden" name="statisticalMethods" id="statisticalMethods" value="${worker.statisticalMethods }"/>
					<input type="hidden" name="workType12" id="workType12" value="${worker.workType12 }"/>
				<ul class="ul-form">
					<div class="validate-group">
					<li>
						<label>工人编号</label>
						<input type="text" id="code" name="code" style="width:160px;" value="${worker.code }"/>
					</li>
					<li>
					<label >开始日期</label>
						<input type="text" id="beginDate" name="beginDate" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${worker.beginDate}' pattern='yyyy-MM-dd'/>"  readonly="true"/>	
					</li>
					<li>
						<label >结束日期</label>
						<input type="text" id="endDate" name="endDate" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${worker.endDate}' pattern='yyyy-MM-dd'/>" />
					</li>		
				</ul>
			</form>
			</div>
		</div>
		<div class="infoBox" id="infoBoxDiv"></div>
				<div class="infoBox" id="infoBoxDiv"></div>
		<div  class="container-fluid" >  
	     <ul class="nav nav-tabs" >
	        <li id="tabbuilder" class="active"><a href="#tab_builder" data-toggle="tab">在建工地</a></li>  
	        <li id="tabarrange" class=""><a href="#tab_arrange" data-toggle="tab">安排工地</a></li>
	        <li id="tabcomplete" class=""><a href="#tab_complete" data-toggle="tab">完工工地</a></li>
	        <li id="tabnoPass" class=""><a href="#tab_noPass" data-toggle="tab">验收不通过</a></li>
	        <li id="tabconfirmAmount" class=""><a href="#tab_confirmAmount" data-toggle="tab">工资明细</a></li>
	        <li id="tabcrtDate" class=""><a href="#tab_crtDate" data-toggle="tab">签到天数</a></li>
	    </ul>  
	    <div class="tab-content">  
			<div id="tab_builder"  class="tab-pane fade in active"> 
		    	<jsp:include page="sg_builder.jsp"></jsp:include>
			</div>  
			<div id="tab_arrange"  class="tab-pane fade "> 
	         	<jsp:include page="sg_arrange.jsp"></jsp:include>
			</div>
			<div id="tab_complete"  class="tab-pane fade "> 
		    	<jsp:include page="sg_complete.jsp"></jsp:include>
			</div>  
			<div id="tab_noPass"  class="tab-pane fade "> 
	         	<jsp:include page="sg_noPass.jsp"></jsp:include>
			</div>
			<div id="tab_confirmAmount"  class="tab-pane fade "> 
		    	<jsp:include page="sg_confirmAmount.jsp"></jsp:include>
			</div>  
			<div id="tab_crtDate"  class="tab-pane fade "> 
	         	<jsp:include page="sg_crtDate.jsp"></jsp:include>
			</div>
		</div>	
	</div>	
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>  
<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
function code(){
	$("#code").openComponent_worker({showValue:'${worker.code}',showLabel:'${worker.nameChi}',readonly:true});
	}
</script>
</body>
</html>
