<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>签单业绩预估</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function(){
		if ("${customer.type}"=="1"){
			$("#designPerfTab").show()
			$("#tab_designPerf").show()	
		}else if ("${customer.type}"=="2") {
			$("#businessPerfTab").show()
			$("#tab_businessPerf").show()
			
			$("#designPerfTab").removeClass("active");
			$("#businessPerfTab").addClass("active"); 
			$("#tab_businessPerf").addClass("tab-pane fade in active"); 
			
		}else{
			$("#businessPerfTab").show()
			$("#tab_businessPerf").show()
			$("#designPerfTab").show()
			$("#tab_designPerf").show()	
		}
	});
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
			<!--panelBar-->
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
						<span style="color:red">说明：业绩与提成为预估数据，供参考。实际金额以达到发放标准，实际发放金额为准。</span>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="height:45px;margin-bottom:5px; ">
			<div class="panel-body" style="padding-top:5px; "  >
				<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
					<input type="hidden" name="jsonString" value="" /> 
					<ul class="ul-form">
						<li><label>楼盘</label> <input type="text" id="address" name="address" value="${customer.address}"
							readonly="readonly" />
						</li>
						<li><label>面积</label> <input type="text" id="area" name="area" value="${customer.area}"
							readonly="readonly" />
						</li>
						<li><label>客户类型</label> <input type="text" id="custType" name="custType" value="${custType.desc1}"
							readonly="readonly" />
						</li>
					</ul>
				</form>	 
			</div>
		</div>
		<form action="" method="post" id="perf_dataForm" class="form-search" target="targetFrame" style="padding-top:0px; ">
				<div class="container-fluid">
					<div class="container-fluid">
						<ul class="nav nav-tabs">
							<li class="active" style="display: none" id="designPerfTab"><a href="#tab_designPerf" data-toggle="tab" >设计师</a></li>
							<li class="" style="display: none" id="businessPerfTab"><a href="#tab_businessPerf" data-toggle="tab">业务员</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab_designPerf" class="tab-pane fade in active" style="display: none">
								<jsp:include page="tab_designPerf.jsp"></jsp:include>
							</div>
							<div id="tab_businessPerf" class="tab-pane fade" style="display: none">
								<jsp:include page="tab_businessPerf.jsp"></jsp:include>
							</div>
						</div>
					</div>
			</div>
	  	</form>
		
	</div>
</body>
</html>
