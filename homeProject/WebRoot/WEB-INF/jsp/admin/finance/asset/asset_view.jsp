<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>固定资产查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	$("#assetType").openComponent_assetType({readonly:"true",showLabel:"${asset.assetTypeDescr}",showValue:"${asset.assetType}"});
	$("#department").openComponent_department({showValue:"${asset.department}",showLabel:"${asset.deptDescr}"});	
	$("#useMan").openComponent_employee({showValue:"${asset.useMan}",showLabel:"${asset.useManDescr}"});
	$("#decCZY").openComponent_employee({showValue:"${asset.decCZY}",showLabel:"${asset.decCzyDescr}"});	
}); 
</script>
</head>
<body >
<div class="body-box-form" >
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
	</div>
	<div class="container-fluid" style="margin-top:5px" >  
		<ul class="nav nav-tabs" >  
	        <li class="active"><a href="#tab_assetMain" data-toggle="tab">主页签</a></li>
	        <li class=""><a href="#tab_assetDec" data-toggle="tab">资产减少</a></li>
	        <li class=""><a href="#tab_assetChg" data-toggle="tab">资产变动</a></li>
	        <li class=""><a href="#tab_assetDepr" data-toggle="tab">资产折旧</a></li>
	    </ul> 
	    <div class="tab-content">  
	        <div id="tab_assetMain" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_assetMain.jsp"></jsp:include>
	        </div> 
	        <div id="tab_assetDec" class="tab-pane fade "> 
	         	<jsp:include page="tab_assetDec.jsp"></jsp:include>
	        </div> 
	        <div id="tab_assetChg" class="tab-pane fade"> 
	         	<jsp:include page="tab_assetChg.jsp"></jsp:include>
	        </div> 
	        <div id="tab_assetDepr" class="tab-pane fade"> 
	         	<jsp:include page="tab_assetDepr.jsp"></jsp:include>
	        </div> 
	    </div>  
	</div>
</div>
</body>
</html>
