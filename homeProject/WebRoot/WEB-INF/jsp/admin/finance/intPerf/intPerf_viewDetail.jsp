<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>集成业绩查看明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
<script type="text/javascript">
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<form type="hidden" role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
  			</form>
				<div class="container-fluid" >  
					<ul class="nav nav-tabs" > 
				        <li class="active"><a href="#tab_itemPlan" data-toggle="tab" >预算</a></li>
				        <li class=""><a href="#tab_itemChg" data-toggle="tab">增减</a></li>
				    </ul> 
				    <div class="tab-content">  
				        <div id="tab_itemPlan" class="tab-pane fade in active"> 
				         	<jsp:include page="intPerf_tab_ItemPlan.jsp"></jsp:include>
				        </div> 
				         <div id="tab_itemChg" class="tab-pane fade "> 
				         	<jsp:include page="intPerf_tab_ItemChg.jsp"></jsp:include>
				        </div>  
				    </div>  
				</div>
		</div>	
	</body>	
</html>
